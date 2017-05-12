package com.millennialmedia.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.givewaygames.gwgl.shader.pixel.MirrorPixelShader;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlrpc.android.IXMLRPCSerializer;

class BridgeMMDevice extends MMJSObject {
    private static final String CALL = "call";
    private static final String COMPOSE_EMAIL = "composeEmail";
    private static final String COMPOSE_SMS = "composeSms";
    private static final String ENABLE_HARDWARE_ACCEL = "enableHardwareAcceleration";
    private static final String GET_AVAIL_SCHEMES = "getAvailableSchemes";
    private static final String GET_INFO = "getInfo";
    private static final String GET_LOCATION = "getLocation";
    private static final String GET_ORIENTATION = "getOrientation";
    private static final String IS_SCHEME_AVAIL = "isSchemeAvailable";
    private static final String OPEN_APP_STORE = "openAppStore";
    private static final String OPEN_URL = "openUrl";
    private static final String SET_MMDID = "setMMDID";
    private static final String SHOW_MAP = "showMap";
    private static final String TAG = "BridgeMMDevice";
    private static final String TWEET = "tweet";

    BridgeMMDevice() {
    }

    public MMJSResponse setMMDID(Map<String, String> arguments) {
        String mmdid = (String) arguments.get("mmdid");
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return null;
        }
        HandShake.sharedHandShake(context).setMMdid(context, mmdid);
        return MMJSResponse.responseWithSuccess("MMDID is set");
    }

    public MMJSResponse enableHardwareAcceleration(Map<String, String> arguments) {
        MMLog.d(TAG, "hardware accel call" + arguments);
        String enabled = (String) arguments.get(MirrorPixelShader.UNIFORM_ENABLED);
        MMWebView webView = (MMWebView) this.mmWebViewRef.get();
        if (webView == null || webView == null) {
            return null;
        }
        if (Boolean.parseBoolean(enabled)) {
            webView.enableHardwareAcceleration();
        } else {
            webView.disableAllAcceleration();
        }
        return MMJSResponse.responseWithSuccess();
    }

    public MMJSResponse getAvailableSchemes(Map<String, String> map) {
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return null;
        }
        HandShake handShake = HandShake.sharedHandShake(context);
        MMJSResponse response = new MMJSResponse();
        response.result = 1;
        response.response = handShake.getSchemesJSONArray(context);
        return response;
    }

    public MMJSResponse isSchemeAvailable(Map<String, String> arguments) {
        String scheme = (String) arguments.get("scheme");
        if (!scheme.contains(":")) {
            scheme = scheme + ":";
        }
        Context context = (Context) this.contextRef.get();
        if (!(scheme == null || context == null)) {
            if (context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(scheme)), 65536).size() > 0) {
                return MMJSResponse.responseWithSuccess(scheme);
            }
        }
        return MMJSResponse.responseWithError(scheme);
    }

    public MMJSResponse getOrientation(Map<String, String> map) {
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return null;
        }
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == 0) {
            orientation = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getOrientation();
        }
        MMJSResponse response = new MMJSResponse();
        response.result = 1;
        switch (orientation) {
            case 2:
                response.response = "landscape";
                return response;
            default:
                response.response = "portrait";
                return response;
        }
    }

    public MMJSResponse getInfo(Map<String, String> map) {
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return null;
        }
        MMJSResponse response = new MMJSResponse();
        response.result = 1;
        response.response = getDeviceInfo(context);
        return response;
    }

    static JSONObject getDeviceInfo(Context context) {
        JSONArray jsonCookieArray;
        JSONException e;
        JSONObject jSONObject;
        JSONObject jsonObject = null;
        try {
            JSONObject jsonObject2 = new JSONObject();
            try {
                jsonObject2.put("sdkVersion", MMSDK.VERSION);
                jsonObject2.put("connection", MMSDK.getConnectionType(context));
                jsonObject2.put("platform", "Android");
                if (VERSION.RELEASE != null) {
                    jsonObject2.put("version", VERSION.RELEASE);
                }
                if (Build.MODEL != null) {
                    jsonObject2.put("device", Build.MODEL);
                }
                jsonObject2.put("mmdid", MMSDK.getMMdid(context));
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                jsonObject2.put("density", new Float(metrics.density));
                jsonObject2.put("height", new Integer(metrics.heightPixels));
                jsonObject2.put("width", new Integer(metrics.widthPixels));
                Locale locale = Locale.getDefault();
                if (locale != null) {
                    jsonObject2.put("language", locale.getLanguage());
                    jsonObject2.put("country", locale.getCountry());
                }
                JSONObject jsonCookieObject = new JSONObject();
                try {
                    jsonCookieObject.put(IXMLRPCSerializer.TAG_NAME, "MAC-ID");
                    jsonCookieObject.put("path", "/");
                    jsonCookieObject.put("value", MMSDK.macId);
                    jsonCookieArray = new JSONArray();
                } catch (JSONException e2) {
                    e = e2;
                    jSONObject = jsonCookieObject;
                    jsonObject = jsonObject2;
                    MMLog.e(TAG, "Bridge getting deviceInfo json exception: ", e);
                    return jsonObject;
                }
                try {
                    jsonCookieArray.put(jsonCookieObject);
                    jsonObject2.put("cookies", jsonCookieArray);
                    jSONObject = jsonCookieObject;
                    return jsonObject2;
                } catch (JSONException e3) {
                    e = e3;
                    jSONObject = jsonCookieObject;
                    JSONArray jSONArray = jsonCookieArray;
                    jsonObject = jsonObject2;
                    MMLog.e(TAG, "Bridge getting deviceInfo json exception: ", e);
                    return jsonObject;
                }
            } catch (JSONException e4) {
                e = e4;
                jsonObject = jsonObject2;
                MMLog.e(TAG, "Bridge getting deviceInfo json exception: ", e);
                return jsonObject;
            }
        } catch (JSONException e5) {
            e = e5;
            MMLog.e(TAG, "Bridge getting deviceInfo json exception: ", e);
            return jsonObject;
        }
    }

    public MMJSResponse getLocation(Map<String, String> map) {
        JSONException e;
        if (MMRequest.location == null) {
            return MMJSResponse.responseWithError("location object has not been set");
        }
        MMJSResponse response;
        JSONObject jsonObject = null;
        try {
            JSONObject jsonObject2 = new JSONObject();
            try {
                jsonObject2.put("lat", Double.toString(MMRequest.location.getLatitude()));
                jsonObject2.put("long", Double.toString(MMRequest.location.getLongitude()));
                if (MMRequest.location.hasAccuracy()) {
                    jsonObject2.put("ha", Float.toString(MMRequest.location.getAccuracy()));
                    jsonObject2.put("va", Float.toString(MMRequest.location.getAccuracy()));
                }
                if (MMRequest.location.hasSpeed()) {
                    jsonObject2.put("spd", Float.toString(MMRequest.location.getSpeed()));
                }
                if (MMRequest.location.hasBearing()) {
                    jsonObject2.put("brg", Float.toString(MMRequest.location.getBearing()));
                }
                if (MMRequest.location.hasAltitude()) {
                    jsonObject2.put("alt", Double.toString(MMRequest.location.getAltitude()));
                }
                jsonObject2.put("tslr", Long.toString(MMRequest.location.getTime()));
                jsonObject = jsonObject2;
            } catch (JSONException e2) {
                e = e2;
                jsonObject = jsonObject2;
                MMLog.e(TAG, "Bridge getLocation json exception: ", e);
                response = new MMJSResponse();
                response.result = 1;
                response.response = jsonObject;
                return response;
            }
        } catch (JSONException e3) {
            e = e3;
            MMLog.e(TAG, "Bridge getLocation json exception: ", e);
            response = new MMJSResponse();
            response.result = 1;
            response.response = jsonObject;
            return response;
        }
        response = new MMJSResponse();
        response.result = 1;
        response.response = jsonObject;
        return response;
    }

    public MMJSResponse showMap(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String location = (String) arguments.get("location");
        if (context == null || location == null) {
            return null;
        }
        MMLog.d(TAG, String.format("Launching Google Maps: %s", new Object[]{location}));
        IntentUtils.startActivity(context, new Intent("android.intent.action.VIEW", Uri.parse("geo:" + location)));
        Event.intentStarted(context, Event.INTENT_MAPS, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        return MMJSResponse.responseWithSuccess("Map successfully opened");
    }

    public MMJSResponse call(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String number = (String) arguments.get("number");
        if (context == null || number == null) {
            return null;
        }
        Intent intent;
        MMLog.d(TAG, String.format("Dialing Phone: %s", new Object[]{number}));
        if (Boolean.parseBoolean((String) arguments.get("dial")) && context.checkCallingOrSelfPermission("android.permission.CALL_PHONE") == 0) {
            intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + number));
        } else {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("tel:" + number));
        }
        IntentUtils.startActivity(context, intent);
        Event.intentStarted(context, Event.INTENT_PHONE_CALL, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        return MMJSResponse.responseWithSuccess();
    }

    public MMJSResponse composeSms(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String number = (String) arguments.get("number");
        String message = (String) arguments.get("message");
        if (context == null || number == null) {
            return null;
        }
        MMLog.d(TAG, String.format("Creating sms: %s", new Object[]{number}));
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("sms:" + number));
        if (message != null) {
            intent.putExtra("sms_body", message);
        }
        IntentUtils.startActivity(context, intent);
        Event.intentStarted(context, Event.INTENT_TXT_MESSAGE, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        return MMJSResponse.responseWithSuccess("SMS Sent");
    }

    public MMJSResponse composeEmail(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String recipients = (String) arguments.get("recipient");
        String subject = (String) arguments.get("subject");
        String message = (String) arguments.get("message");
        if (context == null) {
            return null;
        }
        MMLog.d(TAG, "Creating email");
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.setType("plain/text");
        if (recipients != null) {
            emailIntent.putExtra("android.intent.extra.EMAIL", recipients.split(","));
        }
        if (subject != null) {
            emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
        }
        if (message != null) {
            emailIntent.putExtra("android.intent.extra.TEXT", message);
        }
        IntentUtils.startActivity(context, emailIntent);
        Event.intentStarted(context, "email", getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        return MMJSResponse.responseWithSuccess();
    }

    public MMJSResponse openUrl(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String url = (String) arguments.get("url");
        if (context == null || url == null) {
            return MMJSResponse.responseWithError("URL could not be opened");
        }
        MMLog.d(TAG, String.format("Opening: %s", new Object[]{url}));
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if (intent.getScheme().startsWith("http") || intent.getScheme().startsWith("https")) {
            Event.intentStarted(context, Event.INTENT_EXTERNAL_BROWSER, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        }
        IntentUtils.startActivity(context, intent);
        return MMJSResponse.responseWithSuccess("Overlay opened");
    }

    public MMJSResponse openAppStore(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String id = (String) arguments.get("appId");
        String referrer = (String) arguments.get(AdTrackerConstants.REFERRER);
        if (context == null || id == null) {
            return null;
        }
        MMLog.d(TAG, String.format("Opening marketplace: %s", new Object[]{id}));
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.MANUFACTURER.equals("Amazon")) {
            intent.setData(Uri.parse(String.format("amzn://apps/android?p=%s", new Object[]{id})));
        } else if (referrer != null) {
            intent.setData(Uri.parse(String.format("market://details?id=%s&referrer=%s", new Object[]{id, URLEncoder.encode(referrer)})));
        } else {
            intent.setData(Uri.parse("market://details?id=" + id));
        }
        Event.intentStarted(context, Event.INTENT_MARKET, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        IntentUtils.startActivity(context, intent);
        return MMJSResponse.responseWithSuccess();
    }

    public MMJSResponse tweet(Map<String, String> map) {
        return null;
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if ("call".equals(name)) {
            return call(arguments);
        }
        if (COMPOSE_EMAIL.equals(name)) {
            return composeEmail(arguments);
        }
        if (COMPOSE_SMS.equals(name)) {
            return composeSms(arguments);
        }
        if (ENABLE_HARDWARE_ACCEL.equals(name)) {
            return enableHardwareAcceleration(arguments);
        }
        if (GET_AVAIL_SCHEMES.equals(name)) {
            return getAvailableSchemes(arguments);
        }
        if (GET_INFO.equals(name)) {
            return getInfo(arguments);
        }
        if (GET_LOCATION.equals(name)) {
            return getLocation(arguments);
        }
        if (GET_ORIENTATION.equals(name)) {
            return getOrientation(arguments);
        }
        if (IS_SCHEME_AVAIL.equals(name)) {
            return isSchemeAvailable(arguments);
        }
        if (OPEN_APP_STORE.equals(name)) {
            return openAppStore(arguments);
        }
        if (OPEN_URL.equals(name)) {
            return openUrl(arguments);
        }
        if (SET_MMDID.equals(name)) {
            return setMMDID(arguments);
        }
        if (SHOW_MAP.equals(name)) {
            return showMap(arguments);
        }
        if (TWEET.equals(name)) {
            return tweet(arguments);
        }
        return null;
    }
}
