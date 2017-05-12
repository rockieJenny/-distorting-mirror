package com.millennialmedia.android;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.squareup.otto.Bus;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import org.apache.http.conn.util.InetAddressUtils;

public final class MMSDK {
    private static final String BASE_URL_TRACK_EVENT = "http://ads.mp.mydas.mobi/pixel?id=";
    static final int CACHE_REQUEST_TIMEOUT = 30000;
    static final int CLOSE_ACTIVITY_DURATION = 400;
    static String COMMA = ",";
    public static final String DEFAULT_APID = "28911";
    public static final String DEFAULT_BANNER_APID = "28913";
    public static final String DEFAULT_RECT_APID = "28914";
    static final String EMPTY = "";
    static final int HANDSHAKE_REQUEST_TIMEOUT = 3000;
    static final String JSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss ZZZZ";
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_ERROR = 0;
    public static final int LOG_LEVEL_INFO = 2;
    @Deprecated
    public static final int LOG_LEVEL_INTERNAL = 4;
    @Deprecated
    public static final int LOG_LEVEL_PRIVATE_VERBOSE = 5;
    public static final int LOG_LEVEL_VERBOSE = 3;
    static final int OPEN_ACTIVITY_DURATION = 600;
    static final String PREFS_NAME = "MillennialMediaSettings";
    static final int REQUEST_TIMEOUT = 10000;
    public static final String SDKLOG = "MMSDK";
    public static final String VERSION = "5.3.0-c3980670.a";
    @Deprecated
    static boolean disableAdMinRefresh = false;
    private static String getMMdidValue = null;
    private static boolean hasSpeechKit;
    private static boolean isBroadcastingEvents;
    static int logLevel;
    static String macId;
    static Handler mainHandler = new Handler(Looper.getMainLooper());
    private static int nextDefaultId = 1897808289;

    static class Event {
        public static final String INTENT_CALENDAR_EVENT = "calendar";
        public static final String INTENT_EMAIL = "email";
        public static final String INTENT_EXTERNAL_BROWSER = "browser";
        public static final String INTENT_MAPS = "geo";
        public static final String INTENT_MARKET = "market";
        public static final String INTENT_PHONE_CALL = "tel";
        public static final String INTENT_STREAMING_VIDEO = "streamingVideo";
        public static final String INTENT_TXT_MESSAGE = "sms";
        private static final String KEY_ERROR = "error";
        static final String KEY_INTENT_TYPE = "intentType";
        static final String KEY_INTERNAL_ID = "internalId";
        static final String KEY_PACKAGE_NAME = "packageName";
        protected static final String TAG = Event.class.getName();

        Event() {
        }

        protected static void logEvent(final String logString) {
            MMLog.d("Logging event to: %s", logString);
            ThreadUtils.execute(new Runnable() {
                public void run() {
                    try {
                        new HttpGetRequest().get(logString);
                    } catch (Exception e) {
                        MMLog.e(Event.TAG, "Error logging event: ", e);
                    }
                }
            });
        }

        static void adSingleTap(final MMAdImpl adImpl) {
            if (adImpl != null) {
                MMSDK.runOnUiThread(new Runnable() {
                    public void run() {
                        if (adImpl != null && adImpl.requestListener != null) {
                            try {
                                adImpl.requestListener.onSingleTap(adImpl.getCallingAd());
                            } catch (Exception exception) {
                                MMLog.e(MMSDK.SDKLOG, "Exception raised in your RequestListener: ", exception);
                            }
                        }
                    }
                });
                if (MMSDK.isBroadcastingEvents) {
                    sendIntent(adImpl.getContext(), new Intent(MMBroadcastReceiver.ACTION_OVERLAY_TAP), adImpl.internalId);
                    sendIntent(adImpl.getContext(), new Intent(MMBroadcastReceiver.ACTION_AD_SINGLE_TAP), adImpl.internalId);
                }
            }
        }

        static void intentStarted(Context context, String intentType, long adImplId) {
            if (MMSDK.isBroadcastingEvents && intentType != null) {
                sendIntent(context, new Intent(MMBroadcastReceiver.ACTION_INTENT_STARTED).putExtra(KEY_INTENT_TYPE, intentType), adImplId);
            }
        }

        static void fetchStartedCaching(final MMAdImpl adImpl) {
            if (adImpl == null) {
                MMLog.w(MMSDK.SDKLOG, "No Context in the listener: ");
                return;
            }
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    if (adImpl != null && adImpl.requestListener != null) {
                        try {
                            adImpl.requestListener.MMAdRequestIsCaching(adImpl.getCallingAd());
                        } catch (Exception e) {
                            MMLog.e(MMSDK.SDKLOG, "Exception raised in your RequestListener: ", e);
                        }
                    }
                }
            });
            if (MMSDK.isBroadcastingEvents) {
                sendIntent(adImpl.getContext(), new Intent(MMBroadcastReceiver.ACTION_FETCH_STARTED_CACHING), adImpl.internalId);
            }
        }

        static void displayStarted(MMAdImpl adImpl) {
            if (adImpl == null) {
                MMLog.w(MMSDK.SDKLOG, "No Context in the listener: ");
                return;
            }
            if (MMSDK.isBroadcastingEvents) {
                sendIntent(adImpl.getContext(), new Intent(MMBroadcastReceiver.ACTION_DISPLAY_STARTED), adImpl.internalId);
            }
            overlayOpened(adImpl);
        }

        static void overlayOpened(final MMAdImpl adImpl) {
            if (adImpl == null) {
                MMLog.w(MMSDK.SDKLOG, "No Context in the listener: ");
                return;
            }
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    if (adImpl != null && adImpl.requestListener != null) {
                        try {
                            adImpl.requestListener.MMAdOverlayLaunched(adImpl.getCallingAd());
                        } catch (Exception exception) {
                            MMLog.e(MMSDK.SDKLOG, "Exception raised in your RequestListener: ", exception);
                        }
                    }
                }
            });
            overlayOpenedBroadCast(adImpl.getContext(), adImpl.internalId);
        }

        static void overlayOpenedBroadCast(Context context, long adImplId) {
            if (MMSDK.isBroadcastingEvents) {
                sendIntent(context, new Intent(MMBroadcastReceiver.ACTION_OVERLAY_OPENED), adImplId);
            }
        }

        static void overlayClosed(final MMAdImpl adImpl) {
            if (adImpl == null) {
                MMLog.w(MMSDK.SDKLOG, "No Context in the listener: ");
                return;
            }
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    if (adImpl != null && adImpl.requestListener != null) {
                        try {
                            adImpl.requestListener.MMAdOverlayClosed(adImpl.getCallingAd());
                        } catch (Exception exception) {
                            MMLog.e(MMSDK.SDKLOG, "Exception raised in your RequestListener: ", exception);
                        }
                    }
                }
            });
            if (MMSDK.isBroadcastingEvents && adImpl.getContext() != null) {
                sendIntent(adImpl.getContext(), new Intent(MMBroadcastReceiver.ACTION_OVERLAY_CLOSED), adImpl.internalId);
            }
        }

        static void requestCompleted(final MMAdImpl adImpl) {
            if (adImpl == null) {
                MMLog.w(MMSDK.SDKLOG, "No Context in the listener: ");
                return;
            }
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    if (adImpl != null && adImpl.requestListener != null) {
                        try {
                            adImpl.requestListener.requestCompleted(adImpl.getCallingAd());
                        } catch (Exception exception) {
                            MMLog.e(MMSDK.SDKLOG, "Exception raised in your RequestListener: ", exception);
                        }
                    }
                }
            });
            if (MMSDK.isBroadcastingEvents) {
                sendIntent(adImpl.getContext(), new Intent(adImpl.getRequestCompletedAction()), adImpl.internalId);
            }
        }

        static void requestFailed(final MMAdImpl adImpl, final MMException error) {
            if (adImpl == null) {
                MMLog.w(MMSDK.SDKLOG, "No Context in the listener: ");
                return;
            }
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    if (adImpl != null && adImpl.requestListener != null) {
                        try {
                            adImpl.requestListener.requestFailed(adImpl.getCallingAd(), error);
                        } catch (Exception exception) {
                            MMLog.e(MMSDK.SDKLOG, "Exception raised in your RequestListener: ", exception);
                        }
                    }
                }
            });
            if (MMSDK.isBroadcastingEvents) {
                sendIntent(adImpl.getContext(), new Intent(adImpl.getRequestFailedAction()).putExtra(KEY_ERROR, error), adImpl.internalId);
            }
        }

        private static final void sendIntent(Context context, Intent intent, long adImplId) {
            if (context != null) {
                String type;
                intent.addCategory(MMBroadcastReceiver.CATEGORY_SDK);
                if (adImplId != -4) {
                    intent.putExtra(KEY_INTERNAL_ID, adImplId);
                }
                intent.putExtra(KEY_PACKAGE_NAME, context.getPackageName());
                if (TextUtils.isEmpty(intent.getStringExtra(KEY_INTENT_TYPE))) {
                    type = "";
                } else {
                    type = String.format(" Type[%s]", new Object[]{intent.getStringExtra(KEY_INTENT_TYPE)});
                }
                MMLog.v(MMSDK.SDKLOG, " @@ Intent: " + intent.getAction() + " " + type + " for " + adImplId);
                context.sendBroadcast(intent);
            }
        }
    }

    private MMSDK() {
    }

    static {
        hasSpeechKit = false;
        try {
            System.loadLibrary("nmsp_speex");
            hasSpeechKit = true;
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public static int getDefaultAdId() {
        int i;
        synchronized (MMSDK.class) {
            i = nextDefaultId + 1;
            nextDefaultId = i;
        }
        return i;
    }

    public static void resetCache(Context context) {
        AdCache.resetCache(context);
    }

    public static void setBroadcastEvents(boolean enable) {
        isBroadcastingEvents = enable;
    }

    public static boolean getBroadcastEvents() {
        return isBroadcastingEvents;
    }

    @Deprecated
    public static void setLogLevel(int level) {
        switch (level) {
            case 0:
                MMLog.setLogLevel(6);
                return;
            case 1:
                MMLog.setLogLevel(3);
                return;
            case 2:
                MMLog.setLogLevel(4);
                return;
            case 3:
                MMLog.setLogLevel(2);
                return;
            default:
                MMLog.setLogLevel(4);
                return;
        }
    }

    @Deprecated
    public static int getLogLevel() {
        return MMLog.getLogLevel();
    }

    static void runOnUiThread(Runnable action) {
        if (isUiThread()) {
            action.run();
        } else {
            mainHandler.post(action);
        }
    }

    static void runOnUiThreadDelayed(Runnable action, long delayMillis) {
        mainHandler.postDelayed(action, delayMillis);
    }

    static boolean isUiThread() {
        return mainHandler.getLooper() == Looper.myLooper();
    }

    static boolean isConnected(Context context) {
        boolean z = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            z = false;
        }
        return z;
    }

    static String getIpAddress(Context context) {
        try {
            StringBuilder ips = new StringBuilder();
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (ips.length() > 0) {
                            ips.append(',');
                        }
                        String inetAddressHost = inetAddress.getHostAddress().toUpperCase();
                        if (InetAddressUtils.isIPv4Address(inetAddressHost)) {
                            ips.append(inetAddressHost);
                        } else {
                            int delim = inetAddressHost.indexOf(37);
                            ips.append(delim < 0 ? inetAddressHost : inetAddressHost.substring(0, delim));
                        }
                    }
                }
            }
            return ips.toString();
        } catch (Exception ex) {
            MMLog.e(SDKLOG, "Exception getting ip information: ", ex);
            return "";
        }
    }

    static String getConnectionType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return "unknown";
        }
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            return "offline";
        }
        int type = connectivityManager.getActiveNetworkInfo().getType();
        int subType = connectivityManager.getActiveNetworkInfo().getSubtype();
        if (type == 1) {
            return "wifi";
        }
        if (type != 0) {
            return "unknown";
        }
        switch (subType) {
            case 1:
                return "gprs";
            case 2:
                return "edge";
            case 3:
                return "umts";
            case 4:
                return "cdma";
            case 5:
                return "evdo_0";
            case 6:
                return "evdo_a";
            case 7:
                return "1xrtt";
            case 8:
                return "hsdpa";
            case 9:
                return "hsupa";
            case 10:
                return "hspa";
            case 11:
                return "iden";
            case 12:
                return "evdo_b";
            case 13:
                return "lte";
            case 14:
                return "ehrpd";
            case 15:
                return "hspap";
            default:
                return "unknown";
        }
    }

    static synchronized String getMMdid(Context context) {
        String str = null;
        synchronized (MMSDK.class) {
            if (getMMdidValue != null) {
                str = getMMdidValue;
            } else {
                String mmdid = Secure.getString(context.getContentResolver(), "android_id");
                if (mmdid != null) {
                    StringBuilder mmdidBuilder = new StringBuilder("mmh_");
                    try {
                        mmdidBuilder.append(byteArrayToString(MessageDigest.getInstance(CommonUtils.MD5_INSTANCE).digest(mmdid.getBytes())));
                        mmdidBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                        mmdidBuilder.append(byteArrayToString(MessageDigest.getInstance("SHA1").digest(mmdid.getBytes())));
                        str = mmdidBuilder.toString();
                        getMMdidValue = str;
                    } catch (Exception e) {
                        MMLog.e(SDKLOG, "Exception calculating hash: ", e);
                    }
                }
            }
        }
        return str;
    }

    static synchronized void setMMdid(String value) {
        synchronized (MMSDK.class) {
            getMMdidValue = value;
        }
    }

    static String byteArrayToString(byte[] ba) {
        StringBuilder hex = new StringBuilder(ba.length * 2);
        for (int i = 0; i < ba.length; i++) {
            hex.append(String.format("%02X", new Object[]{Byte.valueOf(ba[i])}));
        }
        return hex.toString();
    }

    private static String getDensityString(Context context) {
        return Float.toString(getDensity(context));
    }

    static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    static void checkPermissions(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") == -1) {
            createMissingPermissionDialog(context, "INTERNET permission").show();
        }
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == -1) {
            createMissingPermissionDialog(context, "ACCESS_NETWORK_STATE permission").show();
        }
    }

    private static AlertDialog createMissingPermissionDialog(Context context, String permission) {
        AlertDialog dialog = new Builder(context).create();
        dialog.setTitle("Whoops!");
        dialog.setMessage(String.format("The developer has forgot to declare the %s in the manifest file. Please reach out to the developer to remove this error.", new Object[]{permission}));
        dialog.setButton(-3, "OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
        return dialog;
    }

    static void checkActivity(Context context) {
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context, "com.millennialmedia.android.MMActivity"), 128);
        } catch (NameNotFoundException e) {
            MMLog.e(SDKLOG, "Activity MMActivity not declared in AndroidManifest.xml", e);
            e.printStackTrace();
            createMissingPermissionDialog(context, "MMActivity class").show();
        }
    }

    static boolean isCachedVideoSupportedOnDevice(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != -1 && (!VERSION.SDK.equalsIgnoreCase("8") || (Environment.getExternalStorageState().equals("mounted") && AdCache.isExternalEnabled));
    }

    static String getMcc(Context context) {
        Configuration config = getConfiguration(context);
        if (config.mcc == 0) {
            String networkOperator = getNetworkOperator(context);
            if (networkOperator != null && networkOperator.length() >= 6) {
                return networkOperator.substring(0, 3);
            }
        }
        return String.valueOf(config.mcc);
    }

    static String getMnc(Context context) {
        Configuration config = getConfiguration(context);
        if (config.mnc == 0) {
            String networkOperator = getNetworkOperator(context);
            if (networkOperator != null && networkOperator.length() >= 6) {
                return networkOperator.substring(3);
            }
        }
        return String.valueOf(config.mnc);
    }

    static Configuration getConfiguration(Context context) {
        return context.getResources().getConfiguration();
    }

    static String getNetworkOperator(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
    }

    static String getCn(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
    }

    static void insertUrlCommonValues(Context context, Map<String, String> paramsMap) {
        MMLog.d(SDKLOG, "executing getIDThread");
        paramsMap.put("density", getDensityString(context));
        paramsMap.put("hpx", getDpiHeight(context));
        paramsMap.put("wpx", getDpiWidth(context));
        paramsMap.put("sk", hasSpeechKit(context));
        paramsMap.put("mic", Boolean.toString(hasMicrophone(context)));
        String aaidValue = null;
        String ateValue = "true";
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            Info info = getAdvertisingInfo(context);
            if (info != null) {
                aaidValue = getAaid(info);
                if (aaidValue != null && info.isLimitAdTrackingEnabled()) {
                    ateValue = "false";
                }
            }
        }
        if (aaidValue != null) {
            paramsMap.put("aaid", aaidValue);
            paramsMap.put("ate", ateValue);
        } else {
            String mmdid = getMMdid(context);
            if (mmdid != null) {
                paramsMap.put("mmdid", mmdid);
            }
        }
        if (isCachedVideoSupportedOnDevice(context)) {
            paramsMap.put("cachedvideo", "true");
        } else {
            paramsMap.put("cachedvideo", "false");
        }
        if (Build.MODEL != null) {
            paramsMap.put("dm", Build.MODEL);
        }
        if (VERSION.RELEASE != null) {
            paramsMap.put("dv", "Android" + VERSION.RELEASE);
        }
        paramsMap.put("sdkversion", VERSION);
        paramsMap.put("mcc", getMcc(context));
        paramsMap.put("mnc", getMnc(context));
        String cn = getCn(context);
        if (!TextUtils.isEmpty(cn)) {
            paramsMap.put("cn", cn);
        }
        Locale locale = Locale.getDefault();
        if (locale != null) {
            paramsMap.put("language", locale.getLanguage());
            paramsMap.put("country", locale.getCountry());
        }
        try {
            String pkid = context.getPackageName();
            paramsMap.put("pkid", pkid);
            PackageManager pm = context.getPackageManager();
            paramsMap.put("pknm", pm.getApplicationLabel(pm.getApplicationInfo(pkid, 0)).toString());
        } catch (Exception e) {
            MMLog.e(SDKLOG, "Can't insert package information", e);
        }
        String schemes = HandShake.sharedHandShake(context).getSchemesList(context);
        if (schemes != null) {
            paramsMap.put("appsids", schemes);
        }
        String vid = AdCache.getCachedVideoList(context);
        if (vid != null) {
            paramsMap.put("vid", vid);
        }
        try {
            String connectionType = getConnectionType(context);
            StatFs statFs;
            if (AdCache.isExternalStorageAvailable(context)) {
                statFs = new StatFs(AdCache.getCacheDirectory(context).getAbsolutePath());
            } else {
                statFs = new StatFs(context.getFilesDir().getPath());
            }
            String freeSpace = Long.toString(((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize()));
            String devicePluggedIn = null;
            String deviceBatteryLevel = null;
            Intent intent = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (intent != null) {
                devicePluggedIn = intent.getIntExtra("plugged", 0) == 0 ? "false" : "true";
                deviceBatteryLevel = Integer.toString((int) (((float) intent.getIntExtra("level", 0)) * (100.0f / ((float) intent.getIntExtra("scale", 100)))));
            }
            if (deviceBatteryLevel != null && deviceBatteryLevel.length() > 0) {
                paramsMap.put("bl", deviceBatteryLevel);
            }
            if (devicePluggedIn != null && devicePluggedIn.length() > 0) {
                paramsMap.put("plugged", devicePluggedIn);
            }
            if (freeSpace.length() > 0) {
                paramsMap.put("space", freeSpace);
            }
            if (connectionType != null) {
                paramsMap.put("conn", connectionType);
            }
            String ip = URLEncoder.encode(getIpAddress(context), HttpRequest.CHARSET_UTF8);
            if (!TextUtils.isEmpty(ip)) {
                paramsMap.put("pip", ip);
            }
        } catch (Exception e2) {
            MMLog.e(SDKLOG, "Exception inserting common parameters: ", e2);
        }
        MMRequest.insertLocation(paramsMap);
    }

    static String getAaid(Info adInfo) {
        if (adInfo == null) {
            return null;
        }
        return adInfo.getId();
    }

    static Info getAdvertisingInfo(Context context) {
        Info adInfo = null;
        try {
            adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        } catch (IOException e) {
            MMLog.e(SDKLOG, "Unrecoverable error connecting to Google Play services (e.g.,the old version of the service doesnt support getting AdvertisingId", e);
        } catch (GooglePlayServicesNotAvailableException e2) {
            MMLog.e(SDKLOG, "Google Play services is not available entirely.", e2);
        } catch (IllegalStateException e3) {
            MMLog.e(SDKLOG, "IllegalStateException: ", e3);
        } catch (GooglePlayServicesRepairableException e4) {
            MMLog.e(SDKLOG, "Google Play Services is not installed, up-to-date, or enabled", e4);
        }
        return adInfo;
    }

    static String getDpiWidth(Context context) {
        return Integer.toString(context.getResources().getDisplayMetrics().widthPixels);
    }

    static String getDpiHeight(Context context) {
        return Integer.toString(context.getResources().getDisplayMetrics().heightPixels);
    }

    public static void trackEvent(Context context, String eventId) {
        if (!TextUtils.isEmpty(eventId)) {
            String mmdid = getMMdid(context);
            if (!TextUtils.isEmpty(mmdid)) {
                HttpUtils.executeUrl(BASE_URL_TRACK_EVENT + eventId + "&mmdid=" + mmdid);
            }
        }
    }

    public static void trackConversion(Context context, String goalId) {
        MMConversionTracker.trackConversion(context, goalId, null);
    }

    public static void trackConversion(Context context, String goalId, MMRequest request) {
        MMConversionTracker.trackConversion(context, goalId, request);
    }

    private static String hasSpeechKit(Context context) {
        if (hasSpeechKit && hasRecordAudioPermission(context)) {
            return "true";
        }
        return "false";
    }

    public static void initialize(Context context) {
        HandShake handShake = HandShake.sharedHandShake(context);
        handShake.sendInitRequest();
        handShake.startSession();
    }

    static boolean hasMicrophone(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.microphone");
    }

    static boolean hasRecordAudioPermission(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.RECORD_AUDIO") == 0;
    }

    static int getMediaVolume(Context context) {
        return ((AudioManager) context.getApplicationContext().getSystemService("audio")).getStreamVolume(3);
    }

    static void printDiagnostics(MMAdImpl adImpl) {
        if (adImpl != null) {
            final Context context = adImpl.getContext();
            MMLog.i(SDKLOG, String.format("MMAd External ID: %d", new Object[]{Integer.valueOf(adImpl.getId())}));
            MMLog.i(SDKLOG, String.format("MMAd Internal ID: %d", new Object[]{Long.valueOf(adImpl.internalId)}));
            MMLog.i(SDKLOG, String.format("APID: %s", new Object[]{adImpl.apid}));
            String str = SDKLOG;
            String str2 = "SD card is %savailable.";
            Object[] objArr = new Object[1];
            objArr[0] = AdCache.isExternalStorageAvailable(context) ? "" : "not ";
            MMLog.i(str, String.format(str2, objArr));
            if (context != null) {
                String str3;
                MMLog.i(SDKLOG, String.format("Package: %s", new Object[]{context.getPackageName()}));
                MMLog.i(SDKLOG, String.format("MMDID: %s", new Object[]{getMMdid(context)}));
                MMLog.i(SDKLOG, "Permissions:");
                str = SDKLOG;
                str2 = "android.permission.ACCESS_NETWORK_STATE is %spresent";
                objArr = new Object[1];
                objArr[0] = context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == -1 ? "not " : "";
                MMLog.i(str, String.format(str2, objArr));
                str = SDKLOG;
                str2 = "android.permission.INTERNET is %spresent";
                objArr = new Object[1];
                objArr[0] = context.checkCallingOrSelfPermission("android.permission.INTERNET") == -1 ? "not " : "";
                MMLog.i(str, String.format(str2, objArr));
                str = SDKLOG;
                str2 = "android.permission.WRITE_EXTERNAL_STORAGE is %spresent";
                objArr = new Object[1];
                objArr[0] = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == -1 ? "not " : "";
                MMLog.i(str, String.format(str2, objArr));
                str = SDKLOG;
                str2 = "android.permission.VIBRATE is %spresent";
                objArr = new Object[1];
                objArr[0] = context.checkCallingOrSelfPermission("android.permission.VIBRATE") == -1 ? "not " : "";
                MMLog.i(str, String.format(str2, objArr));
                str = SDKLOG;
                str2 = "android.permission.ACCESS_COARSE_LOCATION is %spresent";
                objArr = new Object[1];
                objArr[0] = context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == -1 ? "not " : "";
                MMLog.i(str, String.format(str2, objArr));
                str = SDKLOG;
                str2 = "android.permission.ACCESS_FINE_LOCATION is %spresent";
                objArr = new Object[1];
                if (context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == -1) {
                    str3 = "not ";
                } else {
                    str3 = "";
                }
                objArr[0] = str3;
                MMLog.i(str, String.format(str2, objArr));
                MMLog.i(SDKLOG, "Cached Ads:");
                AdCache.iterateCachedAds(context, 2, new Iterator() {
                    boolean callback(CachedAd ad) {
                        String str = MMSDK.SDKLOG;
                        String str2 = "%s %s is %son disk. Is %sexpired.";
                        Object[] objArr = new Object[4];
                        objArr[0] = ad.getTypeString();
                        objArr[1] = ad.getId();
                        objArr[2] = ad.isOnDisk(context) ? "" : "not ";
                        objArr[3] = ad.isExpired() ? "" : "not ";
                        MMLog.i(str, String.format(str2, objArr));
                        return true;
                    }
                });
            }
        }
    }

    static String getSupportsSms(Context context) {
        return String.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.telephony"));
    }

    static boolean getSupportsCalendar() {
        return VERSION.SDK_INT >= 14;
    }

    static String getSupportsTel(Context context) {
        return String.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.telephony"));
    }

    static String getOrientation(Context context) {
        switch (context.getResources().getConfiguration().orientation) {
            case 1:
                return "portrait";
            case 2:
                return "landscape";
            case 3:
                return "square";
            default:
                return Bus.DEFAULT_IDENTIFIER;
        }
    }

    static final String getOrientationLocked(Context context) {
        return System.getString(context.getContentResolver(), "accelerometer_rotation").equals("1") ? "false" : "true";
    }

    static boolean removeAccelForJira1164() {
        return Integer.parseInt(VERSION.SDK) >= 14;
    }

    static boolean hasSetTranslationMethod() {
        return Integer.parseInt(VERSION.SDK) >= 11;
    }

    static boolean supportsFullScreenInline() {
        return Integer.parseInt(VERSION.SDK) >= 11;
    }
}
