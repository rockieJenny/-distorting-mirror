package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.events.EventTransform;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventTransform implements EventTransform<SessionEvent> {
    private static final String ADVERTISING_ID_KEY = "advertisingId";
    private static final String ANDROID_ID_KEY = "androidId";
    private static final String APP_BUNDLE_ID_KEY = "appBundleId";
    private static final String APP_VERSION_CODE_KEY = "appVersionCode";
    private static final String APP_VERSION_NAME_KEY = "appVersionName";
    private static final String BETA_DEVICE_TOKEN_KEY = "betaDeviceToken";
    private static final String DETAILS_KEY = "details";
    private static final String DEVICE_MODEL_KEY = "deviceModel";
    private static final String EXECUTION_ID_KEY = "executionId";
    private static final String INSTALLATION_ID_KEY = "installationId";
    private static final String OS_VERSION_KEY = "osVersion";
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String TYPE_KEY = "type";

    SessionEventTransform() {
    }

    public byte[] toBytes(SessionEvent event) throws IOException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(APP_BUNDLE_ID_KEY, event.appBundleId);
            jsonObject.put(EXECUTION_ID_KEY, event.executionId);
            jsonObject.put(INSTALLATION_ID_KEY, event.installationId);
            jsonObject.put(ANDROID_ID_KEY, event.androidId);
            jsonObject.put(ADVERTISING_ID_KEY, event.advertisingId);
            jsonObject.put(BETA_DEVICE_TOKEN_KEY, event.betaDeviceToken);
            jsonObject.put(OS_VERSION_KEY, event.osVersion);
            jsonObject.put(DEVICE_MODEL_KEY, event.deviceModel);
            jsonObject.put(APP_VERSION_CODE_KEY, event.appVersionCode);
            jsonObject.put(APP_VERSION_NAME_KEY, event.appVersionName);
            jsonObject.put("timestamp", event.timestamp);
            jsonObject.put("type", event.type.toString());
            jsonObject.put(DETAILS_KEY, buildDetailsJsonFor(event.details));
            return jsonObject.toString().getBytes(HttpRequest.CHARSET_UTF8);
        } catch (JSONException e) {
            throw new IOException(e.getMessage());
        }
    }

    private JSONObject buildDetailsJsonFor(Map<String, String> details) throws JSONException {
        JSONObject detailsJson = new JSONObject();
        for (Entry<String, String> entry : details.entrySet()) {
            detailsJson.put((String) entry.getKey(), entry.getValue());
        }
        return detailsJson;
    }

    public SessionEvent fromBytes(byte[] bytes) throws IOException {
        return null;
    }
}
