package com.crashlytics.android.answers;

import android.app.Activity;
import java.util.Collections;
import java.util.Map;

final class SessionEvent {
    public final String advertisingId;
    public final String androidId;
    public final String appBundleId;
    public final String appVersionCode;
    public final String appVersionName;
    public final String betaDeviceToken;
    public final Map<String, String> details;
    public final String deviceModel;
    public final String executionId;
    public final String installationId;
    public final String osVersion;
    private String stringRepresentation;
    public final long timestamp;
    public final Type type;

    enum Type {
        CREATE,
        START,
        RESUME,
        SAVE_INSTANCE_STATE,
        PAUSE,
        STOP,
        DESTROY,
        ERROR,
        CRASH,
        INSTALL
    }

    public static SessionEvent buildActivityLifecycleEvent(String appBundleId, String executionId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, Type type, Activity activity) {
        return buildEvent(appBundleId, executionId, installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, appVersionCode, appVersionName, type, Collections.singletonMap("activity", activity.getClass().getName()));
    }

    public static SessionEvent buildErrorEvent(String appBundleId, String executionId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, String sessionId) {
        return buildEvent(appBundleId, executionId, installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, appVersionCode, appVersionName, Type.ERROR, Collections.singletonMap("sessionId", sessionId));
    }

    public static SessionEvent buildCrashEvent(String appBundleId, String executionId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, String sessionId) {
        return buildEvent(appBundleId, executionId, installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, appVersionCode, appVersionName, Type.CRASH, Collections.singletonMap("sessionId", sessionId));
    }

    public static SessionEvent buildEvent(String appBundleId, String executionId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, Type type, Map<String, String> details) {
        return new SessionEvent(appBundleId, executionId, installationId, androidId, advertisingId, betaDeviceToken, osVersion, deviceModel, appVersionCode, appVersionName, System.currentTimeMillis(), type, details);
    }

    private SessionEvent(String appBundleId, String executionId, String installationId, String androidId, String advertisingId, String betaDeviceToken, String osVersion, String deviceModel, String appVersionCode, String appVersionName, long timestamp, Type type, Map<String, String> details) {
        this.appBundleId = appBundleId;
        this.executionId = executionId;
        this.installationId = installationId;
        this.androidId = androidId;
        this.osVersion = osVersion;
        this.advertisingId = advertisingId;
        this.betaDeviceToken = betaDeviceToken;
        this.deviceModel = deviceModel;
        this.appVersionCode = appVersionCode;
        this.appVersionName = appVersionName;
        this.timestamp = timestamp;
        this.type = type;
        this.details = details;
    }

    public String toString() {
        if (this.stringRepresentation == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(getClass().getSimpleName());
            sb.append(": appBundleId=");
            sb.append(this.appBundleId);
            sb.append(", executionId=");
            sb.append(this.executionId);
            sb.append(", installationId=");
            sb.append(this.installationId);
            sb.append(", androidId=");
            sb.append(this.androidId);
            sb.append(", advertisingId=");
            sb.append(this.advertisingId);
            sb.append(", betaDeviceToken=");
            sb.append(this.betaDeviceToken);
            sb.append(", osVersion=");
            sb.append(this.osVersion);
            sb.append(", deviceModel=");
            sb.append(this.deviceModel);
            sb.append(", appVersionCode=");
            sb.append(this.appVersionCode);
            sb.append(", appVersionName=");
            sb.append(this.appVersionName);
            sb.append(", timestamp=");
            sb.append(this.timestamp);
            sb.append(", type=");
            sb.append(this.type);
            sb.append(", details=");
            sb.append(this.details.toString());
            sb.append("]");
            this.stringRepresentation = sb.toString();
        }
        return this.stringRepresentation;
    }
}
