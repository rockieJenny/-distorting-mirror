package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.events.GZIPQueueFileEventStorage;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.util.Map;

public class Answers extends Kit<Boolean> {
    static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    static final long FIRST_LAUNCH_INTERVAL_IN_MS = 3600000;
    static final String PREFKEY_ANALYTICS_LAUNCHED = "analytics_launched";
    static final String SESSION_ANALYTICS_FILE_EXTENSION = ".tap";
    static final String SESSION_ANALYTICS_FILE_NAME = "session_analytics.tap";
    private static final String SESSION_ANALYTICS_TO_SEND_DIR = "session_analytics_to_send";
    public static final String TAG = "Answers";
    private long installedAt;
    private String packageName;
    private PreferenceStore preferenceStore;
    private SessionAnalyticsManager sessionAnalyticsManager;
    private String versionCode;
    private String versionName;

    public static Answers getInstance() {
        return (Answers) Fabric.getKit(Answers.class);
    }

    @SuppressLint({"NewApi"})
    protected boolean onPreExecute() {
        try {
            this.preferenceStore = new PreferenceStoreImpl(Fabric.getKit(Answers.class));
            Context context = getContext();
            PackageManager packageManager = context.getPackageManager();
            this.packageName = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.packageName, 0);
            this.versionCode = Integer.toString(packageInfo.versionCode);
            this.versionName = packageInfo.versionName == null ? IdManager.DEFAULT_VERSION_NAME : packageInfo.versionName;
            if (VERSION.SDK_INT >= 9) {
                this.installedAt = packageInfo.firstInstallTime;
            } else {
                this.installedAt = new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir).lastModified();
            }
            return true;
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Error setting up app properties", e);
            return false;
        }
    }

    protected Boolean doInBackground() {
        Context context = getContext();
        initializeSessionAnalytics(context);
        try {
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            if (settingsData == null) {
                return Boolean.valueOf(false);
            }
            if (settingsData.featuresData.collectAnalytics) {
                this.sessionAnalyticsManager.setAnalyticsSettingsData(settingsData.analyticsSettingsData, getOverridenSpiEndpoint());
                return Boolean.valueOf(true);
            }
            CommonUtils.logControlled(context, "Disabling analytics collection based on settings flag value.");
            this.sessionAnalyticsManager.disable();
            return Boolean.valueOf(false);
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Error dealing with settings", e);
            return Boolean.valueOf(false);
        }
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:answers";
    }

    public String getVersion() {
        return "1.0.1.21";
    }

    @TargetApi(14)
    private void initializeSessionAnalytics(Context context) {
        try {
            SessionAnalyticsFilesManager analyticsFilesManager = new SessionAnalyticsFilesManager(context, new SessionEventTransform(), new SystemCurrentTimeProvider(), new GZIPQueueFileEventStorage(getContext(), getSdkDirectory(), SESSION_ANALYTICS_FILE_NAME, SESSION_ANALYTICS_TO_SEND_DIR));
            IdManager idManager = getIdManager();
            Map<DeviceIdentifierType, String> deviceIdentifiers = idManager.getDeviceIdentifiers();
            String installationId = idManager.getAppInstallIdentifier();
            String androidId = (String) deviceIdentifiers.get(DeviceIdentifierType.ANDROID_ID);
            String advertisingId = (String) deviceIdentifiers.get(DeviceIdentifierType.ANDROID_ADVERTISING_ID);
            String betaDeviceToken = (String) deviceIdentifiers.get(DeviceIdentifierType.FONT_TOKEN);
            String osVersion = idManager.getOsVersionString();
            String deviceModel = idManager.getModelName();
            Application application = (Application) getContext().getApplicationContext();
            if (application == null || VERSION.SDK_INT < 14) {
                this.sessionAnalyticsManager = new SessionAnalyticsManager(context, context.getPackageName(), installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, this.versionCode, this.versionName, analyticsFilesManager, new DefaultHttpRequestFactory(Fabric.getLogger()));
            } else {
                this.sessionAnalyticsManager = new AutoSessionAnalyticsManager(application, context.getPackageName(), installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, this.versionCode, this.versionName, analyticsFilesManager, new DefaultHttpRequestFactory(Fabric.getLogger()));
            }
            if (isFirstLaunch(this.installedAt)) {
                Fabric.getLogger().d(TAG, "First launch");
                onApplicationInstall();
            }
        } catch (Throwable e) {
            CommonUtils.logControlledError(context, "Crashlytics failed to initialize session analytics.", e);
        }
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(getContext(), CRASHLYTICS_API_ENDPOINT);
    }

    public void onException(LoggedException exception) {
        if (this.sessionAnalyticsManager != null) {
            this.sessionAnalyticsManager.onError(exception.getSessionId());
        }
    }

    public void onException(FatalException exception) {
        if (this.sessionAnalyticsManager != null) {
            this.sessionAnalyticsManager.onCrash(exception.getSessionId());
        }
    }

    @SuppressLint({"CommitPrefEdits"})
    void onApplicationInstall() {
        if (this.sessionAnalyticsManager != null) {
            this.sessionAnalyticsManager.onInstall();
            this.preferenceStore.save(this.preferenceStore.edit().putBoolean(PREFKEY_ANALYTICS_LAUNCHED, true));
        }
    }

    boolean getAnalyticsLaunched() {
        return this.preferenceStore.get().getBoolean(PREFKEY_ANALYTICS_LAUNCHED, false);
    }

    boolean isFirstLaunch(long installedAt) {
        return !getAnalyticsLaunched() && installedRecently(installedAt);
    }

    boolean installedRecently(long installedAt) {
        return System.currentTimeMillis() - installedAt < FIRST_LAUNCH_INTERVAL_IN_MS;
    }

    File getSdkDirectory() {
        return new FileStoreImpl(this).getFilesDir();
    }
}
