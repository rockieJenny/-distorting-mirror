package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.concurrent.ScheduledExecutorService;

@TargetApi(14)
class AutoSessionAnalyticsManager extends SessionAnalyticsManager {
    private final ActivityLifecycleCallbacks activityLifecycleCallbacks;
    private final Application application;

    public AutoSessionAnalyticsManager(Application application, String appBundleId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, SessionAnalyticsFilesManager filesManager, HttpRequestFactory httpRequestFactory) {
        this(application, appBundleId, installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, appVersionCode, appVersionName, filesManager, ExecutorUtils.buildSingleThreadScheduledExecutorService("Crashlytics Trace Manager"), httpRequestFactory);
    }

    AutoSessionAnalyticsManager(Application application, String appBuildId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, SessionAnalyticsFilesManager filesManager, ScheduledExecutorService executor, HttpRequestFactory httpRequestFactory) {
        super(application, appBuildId, installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, appVersionCode, appVersionName, filesManager, executor, httpRequestFactory);
        this.activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle bundle) {
                AutoSessionAnalyticsManager.this.onCreate(activity);
            }

            public void onActivityDestroyed(Activity activity) {
                AutoSessionAnalyticsManager.this.onDestroy(activity);
            }

            public void onActivityPaused(Activity activity) {
                AutoSessionAnalyticsManager.this.onPause(activity);
            }

            public void onActivityResumed(Activity activity) {
                AutoSessionAnalyticsManager.this.onResume(activity);
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                AutoSessionAnalyticsManager.this.onSaveInstanceState(activity);
            }

            public void onActivityStarted(Activity activity) {
                AutoSessionAnalyticsManager.this.onStart(activity);
            }

            public void onActivityStopped(Activity activity) {
                AutoSessionAnalyticsManager.this.onStop(activity);
            }
        };
        this.application = application;
        CommonUtils.logControlled(Answers.getInstance().getContext(), "Registering activity lifecycle callbacks for session analytics.");
        application.registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
    }

    public void disable() {
        CommonUtils.logControlled(Answers.getInstance().getContext(), "Unregistering activity lifecycle callbacks for session analytics");
        this.application.unregisterActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
        super.disable();
    }
}
