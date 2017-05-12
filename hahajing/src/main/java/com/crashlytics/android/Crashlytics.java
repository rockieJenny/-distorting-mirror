package com.crashlytics.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.Settings.SettingsAccess;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;

public class Crashlytics extends Kit<Void> implements KitGroup {
    static final float CLS_DEFAULT_PROCESS_DELAY = 1.0f;
    static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
    static final String COLLECT_CUSTOM_LOGS = "com.crashlytics.CollectCustomLogs";
    static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    static final String CRASHLYTICS_REQUIRE_BUILD_ID = "com.crashlytics.RequireBuildId";
    static final boolean CRASHLYTICS_REQUIRE_BUILD_ID_DEFAULT = true;
    static final int DEFAULT_MAIN_HANDLER_TIMEOUT_SEC = 4;
    static final int MAX_ATTRIBUTES = 64;
    static final int MAX_ATTRIBUTE_SIZE = 1024;
    private static final String PREF_ALWAYS_SEND_REPORTS_KEY = "always_send_reports_opt_in";
    private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
    public static final String TAG = "Fabric";
    private final ConcurrentHashMap<String, String> attributes;
    private String buildId;
    private final ExecutorService crashHandlerExecutor;
    private float delay;
    private boolean disabled;
    private CrashlyticsUncaughtExceptionHandler handler;
    private HttpRequestFactory httpRequestFactory;
    private String installerPackageName;
    private final Collection<Kit<Boolean>> kits;
    private CrashlyticsListener listener;
    private String packageName;
    private final PinningInfoProvider pinningInfo;
    private final long startTime;
    private String userEmail;
    private String userId;
    private String userName;
    private String versionCode;
    private String versionName;

    public static class Builder {
        private float delay = GroundOverlayOptions.NO_DIMENSION;
        private boolean disabled = false;
        private CrashlyticsListener listener;
        private PinningInfoProvider pinningInfoProvider;

        public Builder delay(float delay) {
            if (delay <= 0.0f) {
                throw new IllegalArgumentException("delay must be greater than 0");
            } else if (this.delay > 0.0f) {
                throw new IllegalStateException("delay already set.");
            } else {
                this.delay = delay;
                return this;
            }
        }

        public Builder listener(CrashlyticsListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("listener must not be null.");
            } else if (this.listener != null) {
                throw new IllegalStateException("listener already set.");
            } else {
                this.listener = listener;
                return this;
            }
        }

        public Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
            if (pinningInfoProvider == null) {
                throw new IllegalArgumentException("pinningInfoProvider must not be null.");
            } else if (this.pinningInfoProvider != null) {
                throw new IllegalStateException("pinningInfoProvider already set.");
            } else {
                this.pinningInfoProvider = pinningInfoProvider;
                return this;
            }
        }

        public Builder disabled(boolean isDisabled) {
            this.disabled = isDisabled;
            return this;
        }

        public Crashlytics build() {
            if (this.delay < 0.0f) {
                this.delay = 1.0f;
            }
            return new Crashlytics(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
        }
    }

    private class OptInLatch {
        private final CountDownLatch latch;
        private boolean send;

        private OptInLatch() {
            this.send = false;
            this.latch = new CountDownLatch(1);
        }

        void setOptIn(boolean optIn) {
            this.send = optIn;
            this.latch.countDown();
        }

        boolean getOptIn() {
            return this.send;
        }

        void await() {
            try {
                this.latch.await();
            } catch (InterruptedException e) {
            }
        }
    }

    public Crashlytics() {
        this(1.0f, null, null, false);
    }

    Crashlytics(float delay, CrashlyticsListener listener, PinningInfoProvider pinningInfo, boolean disabled) {
        this(delay, listener, pinningInfo, disabled, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
    }

    Crashlytics(float delay, CrashlyticsListener listener, PinningInfoProvider pinningInfo, boolean disabled, ExecutorService crashHandlerExecutor) {
        this.userId = null;
        this.userEmail = null;
        this.userName = null;
        this.attributes = new ConcurrentHashMap();
        this.startTime = System.currentTimeMillis();
        this.delay = delay;
        this.listener = listener;
        this.pinningInfo = pinningInfo;
        this.disabled = disabled;
        this.crashHandlerExecutor = crashHandlerExecutor;
        this.kits = Collections.unmodifiableCollection(Arrays.asList(new Kit[]{new Answers(), new Beta()}));
    }

    public static Crashlytics getInstance() {
        try {
            return (Crashlytics) Fabric.getKit(Crashlytics.class);
        } catch (IllegalStateException ex) {
            Fabric.getLogger().e("Fabric", "Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()", null);
            throw ex;
        }
    }

    private static boolean ensureFabricWithCalled(String msg, Crashlytics instance) {
        if (instance != null && instance.handler != null) {
            return true;
        }
        Fabric.getLogger().e("Fabric", "Crashlytics must be initialized by calling Fabric.with(Context) " + msg, null);
        return false;
    }

    public static void logException(Throwable throwable) {
        if (!isDisabled()) {
            Crashlytics instance = getInstance();
            if (!ensureFabricWithCalled("prior to logging exceptions.", instance)) {
                return;
            }
            if (throwable == null) {
                Fabric.getLogger().log(5, "Fabric", "Crashlytics is ignoring a request to log a null exception.");
            } else {
                instance.handler.writeNonFatalException(Thread.currentThread(), throwable);
            }
        }
    }

    static void recordLoggedExceptionEvent(String sessionId) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers != null) {
            answers.onException(new LoggedException(sessionId));
        }
    }

    static void recordFatalExceptionEvent(String sessionId) {
        Answers answers = (Answers) Fabric.getKit(Answers.class);
        if (answers != null) {
            answers.onException(new FatalException(sessionId));
        }
    }

    public static void log(String msg) {
        doLog(3, "Fabric", msg);
    }

    private static void doLog(int priority, String tag, String msg) {
        if (!isDisabled()) {
            Crashlytics instance = getInstance();
            if (ensureFabricWithCalled("prior to logging messages.", instance)) {
                instance.handler.writeToLog(System.currentTimeMillis() - instance.startTime, formatLogMessage(priority, tag, msg));
            }
        }
    }

    public static void log(int priority, String tag, String msg) {
        doLog(priority, tag, msg);
        Fabric.getLogger().log(priority, "" + tag, "" + msg, true);
    }

    private static String formatLogMessage(int priority, String tag, String msg) {
        return CommonUtils.logPriorityToString(priority) + "/" + tag + " " + msg;
    }

    public static void setUserIdentifier(String identifier) {
        if (!isDisabled()) {
            getInstance().userId = sanitizeAttribute(identifier);
        }
    }

    public static void setUserName(String name) {
        if (!isDisabled()) {
            getInstance().userName = sanitizeAttribute(name);
        }
    }

    public static void setUserEmail(String email) {
        if (!isDisabled()) {
            getInstance().userEmail = sanitizeAttribute(email);
        }
    }

    File getSdkDirectory() {
        return new FileStoreImpl(this).getFilesDir();
    }

    public static void setString(String key, String value) {
        if (!isDisabled()) {
            if (key != null) {
                key = sanitizeAttribute(key);
                if (getInstance().attributes.size() < 64 || getInstance().attributes.containsKey(key)) {
                    if (value == null) {
                        value = "";
                    } else {
                        value = sanitizeAttribute(value);
                    }
                    getInstance().attributes.put(key, value);
                    return;
                }
                Fabric.getLogger().d("Fabric", "Exceeded maximum number of custom attributes (64)");
            } else if (getInstance().getContext() == null || !CommonUtils.isAppDebuggable(getInstance().getContext())) {
                Fabric.getLogger().e("Fabric", "Attempting to set custom attribute with null key, ignoring.", null);
            } else {
                throw new IllegalArgumentException("Custom attribute key must not be null.");
            }
        }
    }

    public static void setBool(String key, boolean value) {
        setString(key, Boolean.toString(value));
    }

    public static void setDouble(String key, double value) {
        setString(key, Double.toString(value));
    }

    public static void setFloat(String key, float value) {
        setString(key, Float.toString(value));
    }

    public static void setInt(String key, int value) {
        setString(key, Integer.toString(value));
    }

    public static void setLong(String key, long value) {
        setString(key, Long.toString(value));
    }

    Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    @Deprecated
    public synchronized void setListener(CrashlyticsListener listener) {
        Fabric.getLogger().w("Fabric", "Use of Crashlytics.setListener is deprecated.");
        if (listener == null) {
            throw new IllegalArgumentException("listener must not be null.");
        }
        this.listener = listener;
    }

    @Deprecated
    public void setDebugMode(boolean debug) {
        Fabric.getLogger().w("Fabric", "Use of Crashlytics.setDebugMode is deprecated.");
    }

    @Deprecated
    public boolean getDebugMode() {
        Fabric.getLogger().w("Fabric", "Use of Crashlytics.getDebugMode is deprecated.");
        getFabric();
        return Fabric.isDebuggable();
    }

    @Deprecated
    public static void setPinningInfoProvider(PinningInfoProvider pinningInfo) {
        Fabric.getLogger().w("Fabric", "Use of Crashlytics.setPinningInfoProvider is deprecated");
    }

    public static PinningInfoProvider getPinningInfoProvider() {
        return !isDisabled() ? getInstance().pinningInfo : null;
    }

    public boolean verifyPinning(URL url) {
        try {
            return internalVerifyPinning(url);
        } catch (Exception e) {
            Fabric.getLogger().e("Fabric", "Could not verify SSL pinning", e);
            return false;
        }
    }

    boolean internalVerifyPinning(URL url) {
        if (getPinningInfoProvider() == null) {
            return false;
        }
        HttpRequest httpRequest = this.httpRequestFactory.buildHttpRequest(HttpMethod.GET, url.toString());
        ((HttpsURLConnection) httpRequest.getConnection()).setInstanceFollowRedirects(false);
        httpRequest.code();
        return true;
    }

    public void crash() {
        new CrashTest().indexOutOfBounds();
    }

    protected boolean onPreExecute() {
        return onPreExecute(super.getContext());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean onPreExecute(android.content.Context r14) {
        /*
        r13 = this;
        r12 = 0;
        r0 = r13.disabled;
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        r0 = r12;
    L_0x0006:
        return r0;
    L_0x0007:
        r0 = io.fabric.sdk.android.Fabric.isDebuggable();
        r7 = io.fabric.sdk.android.services.common.ApiKey.getApiKey(r14, r0);
        if (r7 != 0) goto L_0x0013;
    L_0x0011:
        r0 = r12;
        goto L_0x0006;
    L_0x0013:
        r0 = new io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0.<init>(r1);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r13.httpRequestFactory = r0;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0 = r13.pinningInfo;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        if (r0 != 0) goto L_0x00fc;
    L_0x0022:
        r0 = r13.httpRequestFactory;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = 0;
        r0.setPinningInfoProvider(r1);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
    L_0x0028:
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = "Fabric";
        r2 = new java.lang.StringBuilder;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r2.<init>();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r3 = "Initializing Crashlytics ";
        r2 = r2.append(r3);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r3 = r13.getVersion();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r2 = r2.append(r3);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r2 = r2.toString();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0.i(r1, r2);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0 = r14.getPackageName();	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r13.packageName = r0;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r13.getIdManager();	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r0.getInstallerPackageName();	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r13.installerPackageName = r0;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r1 = "Fabric";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r2.<init>();	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r3 = "Installer package name is: ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r3 = r13.installerPackageName;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0.d(r1, r2);	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r11 = r14.getPackageManager();	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r13.packageName;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r1 = 0;
        r10 = r11.getPackageInfo(r0, r1);	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r10.versionCode;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = java.lang.Integer.toString(r0);	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r13.versionCode = r0;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r10.versionName;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        if (r0 != 0) goto L_0x010f;
    L_0x008d:
        r0 = "0.0";
    L_0x008f:
        r13.versionName = r0;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = io.fabric.sdk.android.services.common.CommonUtils.resolveBuildId(r14);	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        r13.buildId = r0;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
    L_0x0097:
        r0 = r13.getIdManager();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0.getBluetoothMacAddress();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0 = r13.buildId;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = r13.isRequiringBuildId(r14);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0 = r13.getBuildIdValidator(r0, r1);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = r13.packageName;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0.validate(r7, r1);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r9 = 0;
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r1 = "Fabric";
        r2 = "Installing exception handler...";
        r0.d(r1, r2);	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = new com.crashlytics.android.CrashlyticsUncaughtExceptionHandler;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r1 = java.lang.Thread.getDefaultUncaughtExceptionHandler();	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r2 = r13.listener;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r3 = r13.crashHandlerExecutor;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r4 = r13.buildId;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r5 = r13.getIdManager();	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r6 = r13;
        r0.<init>(r1, r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r13.handler = r0;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r13.handler;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r9 = r0.didPreviousInitializationComplete();	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r13.handler;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r0.ensureOpenSessionExists();	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = r13.handler;	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        java.lang.Thread.setDefaultUncaughtExceptionHandler(r0);	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
        r1 = "Fabric";
        r2 = "Successfully installed exception handler.";
        r0.d(r1, r2);	 Catch:{ Exception -> 0x0130, CrashlyticsMissingDependencyException -> 0x0108 }
    L_0x00ea:
        if (r9 == 0) goto L_0x013d;
    L_0x00ec:
        r0 = r13.getContext();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0 = io.fabric.sdk.android.services.common.CommonUtils.canTryConnection(r0);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        if (r0 == 0) goto L_0x013d;
    L_0x00f6:
        r13.finishInitSynchronously();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0 = r12;
        goto L_0x0006;
    L_0x00fc:
        r0 = r13.httpRequestFactory;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = new com.crashlytics.android.Crashlytics$1;	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1.<init>();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r0.setPinningInfoProvider(r1);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        goto L_0x0028;
    L_0x0108:
        r8 = move-exception;
        r0 = new io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
        r0.<init>(r8);
        throw r0;
    L_0x010f:
        r0 = r10.versionName;	 Catch:{ Exception -> 0x0113, CrashlyticsMissingDependencyException -> 0x0108 }
        goto L_0x008f;
    L_0x0113:
        r8 = move-exception;
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = "Fabric";
        r2 = "Error setting up app properties";
        r0.e(r1, r2, r8);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        goto L_0x0097;
    L_0x0121:
        r8 = move-exception;
        r0 = io.fabric.sdk.android.Fabric.getLogger();
        r1 = "Fabric";
        r2 = "Crashlytics was not started due to an exception during initialization";
        r0.e(r1, r2, r8);
        r0 = r12;
        goto L_0x0006;
    L_0x0130:
        r8 = move-exception;
        r0 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        r1 = "Fabric";
        r2 = "There was a problem installing the exception handler.";
        r0.e(r1, r2, r8);	 Catch:{ CrashlyticsMissingDependencyException -> 0x0108, Exception -> 0x0121 }
        goto L_0x00ea;
    L_0x013d:
        r0 = 1;
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.Crashlytics.onPreExecute(android.content.Context):boolean");
    }

    BuildIdValidator getBuildIdValidator(String buildId, boolean requireBuildId) {
        return new BuildIdValidator(buildId, requireBuildId);
    }

    protected Void doInBackground() {
        this.handler.markInitializationStarted();
        this.handler.cleanInvalidTempFiles();
        boolean reportingDisabled = true;
        try {
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            if (settingsData == null) {
                Fabric.getLogger().w("Fabric", "Received null settings, skipping initialization!");
                this.handler.markInitializationComplete();
                return null;
            }
            if (settingsData.featuresData.collectReports) {
                reportingDisabled = false;
                this.handler.finalizeSessions();
                CreateReportSpiCall call = getCreateReportSpiCall(settingsData);
                if (call != null) {
                    new ReportUploader(call).uploadReports(this.delay);
                } else {
                    Fabric.getLogger().w("Fabric", "Unable to create a call to upload reports.");
                }
            }
            if (reportingDisabled) {
                try {
                    Fabric.getLogger().d("Fabric", "Crash reporting disabled.");
                } catch (Exception e) {
                    Fabric.getLogger().e("Fabric", "Problem encountered during Crashlytics initialization.", e);
                } finally {
                    this.handler.markInitializationComplete();
                }
            }
            this.handler.markInitializationComplete();
            return null;
        } catch (Exception e2) {
            Fabric.getLogger().e("Fabric", "Error dealing with settings", e2);
        }
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:crashlytics";
    }

    private void finishInitSynchronously() {
        PriorityCallable<Void> callable = new PriorityCallable<Void>() {
            public Void call() throws Exception {
                return Crashlytics.this.doInBackground();
            }

            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        for (Dependency dep : getDependencies()) {
            callable.addDependency(dep);
        }
        Future<Void> future = getFabric().getExecutorService().submit(callable);
        Fabric.getLogger().d("Fabric", "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            future.get(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Fabric.getLogger().e("Fabric", "Crashlytics was interrupted during initialization.", e);
        } catch (ExecutionException e2) {
            Fabric.getLogger().e("Fabric", "Problem encountered during Crashlytics initialization.", e2);
        } catch (TimeoutException e3) {
            Fabric.getLogger().e("Fabric", "Crashlytics timed out during initialization.", e3);
        }
    }

    public String getVersion() {
        return "2.0.1.21";
    }

    String getPackageName() {
        return this.packageName;
    }

    String getInstallerPackageName() {
        return this.installerPackageName;
    }

    String getVersionName() {
        return this.versionName;
    }

    String getVersionCode() {
        return this.versionCode;
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(getInstance().getContext(), CRASHLYTICS_API_ENDPOINT);
    }

    String getBuildId() {
        return this.buildId;
    }

    boolean shouldPromptUserBeforeSendingCrashReports() {
        return ((Boolean) Settings.getInstance().withSettings(new SettingsAccess<Boolean>() {
            public Boolean usingSettings(SettingsData settingsData) {
                boolean z = false;
                if (!settingsData.featuresData.promptEnabled) {
                    return Boolean.valueOf(false);
                }
                if (!Crashlytics.this.shouldSendReportsWithoutPrompting()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }, Boolean.valueOf(false))).booleanValue();
    }

    boolean shouldSendReportsWithoutPrompting() {
        return new PreferenceStoreImpl(this).get().getBoolean(PREF_ALWAYS_SEND_REPORTS_KEY, false);
    }

    @SuppressLint({"CommitPrefEdits"})
    void setShouldSendUserReportsWithoutPrompting(boolean send) {
        PreferenceStore prefStore = new PreferenceStoreImpl(this);
        prefStore.save(prefStore.edit().putBoolean(PREF_ALWAYS_SEND_REPORTS_KEY, send));
    }

    CrashlyticsUncaughtExceptionHandler getHandler() {
        return this.handler;
    }

    String getUserIdentifier() {
        return getIdManager().canCollectUserIds() ? this.userId : null;
    }

    String getUserEmail() {
        return getIdManager().canCollectUserIds() ? this.userEmail : null;
    }

    String getUserName() {
        return getIdManager().canCollectUserIds() ? this.userName : null;
    }

    boolean canSendWithUserApproval() {
        return ((Boolean) Settings.getInstance().withSettings(new SettingsAccess<Boolean>() {
            public Boolean usingSettings(SettingsData settingsData) {
                boolean send = true;
                Activity activity = Crashlytics.this.getFabric().getCurrentActivity();
                if (!(activity == null || activity.isFinishing() || !Crashlytics.this.shouldPromptUserBeforeSendingCrashReports())) {
                    send = Crashlytics.this.getSendDecisionFromUser(activity, settingsData.promptData);
                }
                return Boolean.valueOf(send);
            }
        }, Boolean.valueOf(true))).booleanValue();
    }

    CreateReportSpiCall getCreateReportSpiCall(SettingsData settingsData) {
        if (settingsData != null) {
            return new DefaultCreateReportSpiCall(this, getOverridenSpiEndpoint(), settingsData.appData.reportsUrl, this.httpRequestFactory);
        }
        return null;
    }

    SessionSettingsData getSessionSettingsData() {
        SettingsData settingsData = Settings.getInstance().awaitSettingsData();
        return settingsData == null ? null : settingsData.sessionData;
    }

    private boolean getSendDecisionFromUser(Activity context, PromptSettingsData promptData) {
        final DialogStringResolver stringResolver = new DialogStringResolver(context, promptData);
        final OptInLatch latch = new OptInLatch();
        final Activity activity = context;
        final PromptSettingsData promptSettingsData = promptData;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                OnClickListener sendClickListener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        latch.setOptIn(true);
                        dialog.dismiss();
                    }
                };
                float density = activity.getResources().getDisplayMetrics().density;
                int textViewPadding = Crashlytics.this.dipsToPixels(density, 5);
                TextView textView = new TextView(activity);
                textView.setAutoLinkMask(15);
                textView.setText(stringResolver.getMessage());
                textView.setTextAppearance(activity, 16973892);
                textView.setPadding(textViewPadding, textViewPadding, textViewPadding, textViewPadding);
                textView.setFocusable(false);
                ScrollView scrollView = new ScrollView(activity);
                scrollView.setPadding(Crashlytics.this.dipsToPixels(density, 14), Crashlytics.this.dipsToPixels(density, 2), Crashlytics.this.dipsToPixels(density, 10), Crashlytics.this.dipsToPixels(density, 12));
                scrollView.addView(textView);
                builder.setView(scrollView).setTitle(stringResolver.getTitle()).setCancelable(false).setNeutralButton(stringResolver.getSendButtonTitle(), sendClickListener);
                if (promptSettingsData.showCancelButton) {
                    builder.setNegativeButton(stringResolver.getCancelButtonTitle(), new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            latch.setOptIn(false);
                            dialog.dismiss();
                        }
                    });
                }
                if (promptSettingsData.showAlwaysSendButton) {
                    builder.setPositiveButton(stringResolver.getAlwaysSendButtonTitle(), new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Crashlytics.this.setShouldSendUserReportsWithoutPrompting(true);
                            latch.setOptIn(true);
                            dialog.dismiss();
                        }
                    });
                }
                builder.show();
            }
        });
        Fabric.getLogger().d("Fabric", "Waiting for user opt-in.");
        latch.await();
        return latch.getOptIn();
    }

    private int dipsToPixels(float density, int dips) {
        return (int) (((float) dips) * density);
    }

    private static String sanitizeAttribute(String input) {
        if (input == null) {
            return input;
        }
        input = input.trim();
        if (input.length() > 1024) {
            return input.substring(0, 1024);
        }
        return input;
    }

    private boolean isRequiringBuildId(Context context) {
        return CommonUtils.getBooleanResourceValue(context, CRASHLYTICS_REQUIRE_BUILD_ID, true);
    }

    private static boolean isDisabled() {
        Crashlytics instance = getInstance();
        return instance == null || instance.disabled;
    }

    public Collection<? extends Kit> getKits() {
        return this.kits;
    }
}
