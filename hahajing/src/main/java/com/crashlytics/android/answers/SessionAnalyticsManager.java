package com.crashlytics.android.answers;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.events.EventsHandler;
import io.fabric.sdk.android.services.events.EventsStrategy;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

class SessionAnalyticsManager extends EventsHandler<SessionEvent> {
    private final String advertisingId;
    private final String androidId;
    private final String appBundleId;
    private final String appVersionCode;
    private final String appVersionName;
    private final String betaDeviceToken;
    private final String deviceModel;
    private final String executionId;
    private final String installationId;
    private final String osVersion;

    public SessionAnalyticsManager(Context context, String appBundleId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, SessionAnalyticsFilesManager filesManager, HttpRequestFactory httpRequestFactory) {
        this(context, appBundleId, installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, appVersionCode, appVersionName, filesManager, ExecutorUtils.buildSingleThreadScheduledExecutorService("Crashlytics SAM"), httpRequestFactory);
    }

    SessionAnalyticsManager(Context context, String appBundleId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, SessionAnalyticsFilesManager filesManager, ScheduledExecutorService executor, HttpRequestFactory httpRequestFactory) {
        super(context, new EnabledSessionAnalyticsManagerStrategy(context, executor, filesManager, httpRequestFactory), filesManager, executor);
        this.appBundleId = appBundleId;
        this.installationId = installationId;
        this.androidId = androidId;
        this.osVersion = osVersion;
        this.advertisingId = advertisingId;
        this.deviceModel = deviceModel;
        this.appVersionCode = appVersionCode;
        this.appVersionName = appVersionName;
        this.betaDeviceToken = betaDeviceToken;
        this.executionId = UUID.randomUUID().toString();
    }

    public void onCrash(final String sessionId) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("onCrash called from main thread!!!");
        }
        executeSync(new Runnable() {
            public void run() {
                try {
                    SessionAnalyticsManager.this.strategy.recordEvent(SessionEvent.buildCrashEvent(SessionAnalyticsManager.this.appBundleId, SessionAnalyticsManager.this.executionId, SessionAnalyticsManager.this.installationId, SessionAnalyticsManager.this.androidId, SessionAnalyticsManager.this.advertisingId, SessionAnalyticsManager.this.betaDeviceToken, SessionAnalyticsManager.this.osVersion, SessionAnalyticsManager.this.deviceModel, SessionAnalyticsManager.this.appVersionCode, SessionAnalyticsManager.this.appVersionName, sessionId));
                } catch (Exception e) {
                    CommonUtils.logControlledError(Answers.getInstance().getContext(), "Crashlytics failed to record crash event", e);
                }
            }
        });
    }

    public void onInstall() {
        recordEventAsync(SessionEvent.buildEvent(this.appBundleId, this.executionId, this.installationId, this.androidId, this.advertisingId, this.betaDeviceToken, this.osVersion, this.deviceModel, this.appVersionCode, this.appVersionName, Type.INSTALL, new HashMap()), true);
    }

    public void onCreate(Activity activity) {
        recordEventAsync(Type.CREATE, activity, false);
    }

    public void onDestroy(Activity activity) {
        recordEventAsync(Type.DESTROY, activity, false);
    }

    public void onError(String sessionId) {
        recordEventAsync(SessionEvent.buildErrorEvent(this.appBundleId, this.executionId, this.installationId, this.androidId, this.advertisingId, this.betaDeviceToken, this.osVersion, this.deviceModel, this.appVersionCode, this.appVersionName, sessionId), false);
    }

    public void onPause(Activity activity) {
        recordEventAsync(Type.PAUSE, activity, false);
    }

    public void onResume(Activity activity) {
        recordEventAsync(Type.RESUME, activity, false);
    }

    public void onSaveInstanceState(Activity activity) {
        recordEventAsync(Type.SAVE_INSTANCE_STATE, activity, false);
    }

    public void onStart(Activity activity) {
        recordEventAsync(Type.START, activity, false);
    }

    public void onStop(Activity activity) {
        recordEventAsync(Type.STOP, activity, false);
    }

    private void recordEventAsync(Type type, Activity activity, boolean sendImmediately) {
        recordEventAsync(SessionEvent.buildActivityLifecycleEvent(this.appBundleId, this.executionId, this.installationId, this.androidId, this.advertisingId, this.betaDeviceToken, this.osVersion, this.deviceModel, this.appVersionCode, this.appVersionName, type, activity), sendImmediately);
    }

    void setAnalyticsSettingsData(final AnalyticsSettingsData analyticsSettingsData, final String protocolAndHostOverride) {
        executeAsync(new Runnable() {
            public void run() {
                try {
                    ((SessionAnalyticsManagerStrategy) SessionAnalyticsManager.this.strategy).setAnalyticsSettingsData(analyticsSettingsData, protocolAndHostOverride);
                } catch (Exception e) {
                    CommonUtils.logControlledError(Answers.getInstance().getContext(), "Crashlytics failed to set analytics settings data.", e);
                }
            }
        });
    }

    protected EventsStrategy<SessionEvent> getDisabledEventsStrategy() {
        return new DisabledSessionAnalyticsManagerStrategy();
    }
}
