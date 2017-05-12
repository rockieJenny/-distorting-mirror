package com.inmobi.commons.analytics.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Base64OutputStream;
import com.inmobi.commons.analytics.bootstrapper.AnalyticsInitializer;
import com.inmobi.commons.analytics.events.AnalyticsEventsWrapper.IMItemType;
import com.inmobi.commons.analytics.events.AnalyticsEventsWrapper.IMSectionStatus;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.zip.GZIPOutputStream;
import org.json.JSONObject;

public class AnalyticsUtils {
    public static final String ANALYTICS_LOGGING_TAG = "[InMobi]-[Analytics]-4.5.3";
    public static final String INITIALIZE_NOT_CALLED = "Please call InMobi.initialize or startSession before calling any events API";
    private static String a;
    private static boolean b;

    public static String getWebviewUserAgent() {
        return a;
    }

    public static void setWebviewUserAgent(String str) {
        a = str;
    }

    public static synchronized void setStartHandle(boolean z) {
        synchronized (AnalyticsUtils.class) {
            b = z;
        }
    }

    public static synchronized boolean getStartHandle() {
        boolean z;
        synchronized (AnalyticsUtils.class) {
            z = b;
        }
        return z;
    }

    public static synchronized long getMaxdbcount() {
        long maxDbEvents;
        synchronized (AnalyticsUtils.class) {
            maxDbEvents = (long) AnalyticsInitializer.getConfigParams().getMaxDbEvents();
        }
        return maxDbEvents;
    }

    public static synchronized String getMaxevents() {
        String str;
        synchronized (AnalyticsUtils.class) {
            str = AnalyticsInitializer.getConfigParams().getGetParamsLimit() + "";
        }
        return str;
    }

    public static synchronized int getMaxstring() {
        int maxValLength;
        synchronized (AnalyticsUtils.class) {
            maxValLength = AnalyticsInitializer.getConfigParams().getMaxValLength();
        }
        return maxValLength;
    }

    public static synchronized int getMaxparamskey() {
        int maxKeyLength;
        synchronized (AnalyticsUtils.class) {
            maxKeyLength = AnalyticsInitializer.getConfigParams().getMaxKeyLength();
        }
        return maxKeyLength;
    }

    public static synchronized int getExtraParamsLimit() {
        int extraParamsLimit;
        synchronized (AnalyticsUtils.class) {
            extraParamsLimit = AnalyticsInitializer.getConfigParams().getExtraParamsLimit();
        }
        return extraParamsLimit;
    }

    public static synchronized long getTimeinterval() {
        long pingInterval;
        synchronized (AnalyticsUtils.class) {
            pingInterval = (long) AnalyticsInitializer.getConfigParams().getPingInterval();
        }
        return pingInterval;
    }

    public static synchronized int getMaxRetryBeforeDiscard() {
        int maxRetryBeforeCacheDiscard;
        synchronized (AnalyticsUtils.class) {
            maxRetryBeforeCacheDiscard = AnalyticsInitializer.getConfigParams().getMaxRetryBeforeCacheDiscard();
        }
        return maxRetryBeforeCacheDiscard;
    }

    public static String getApplicationName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0));
        } catch (Exception e) {
            Log.internal(ANALYTICS_LOGGING_TAG, "Error retrieving application name");
            return null;
        }
    }

    public static String getAppVersion(Context context) {
        String str = null;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.internal(ANALYTICS_LOGGING_TAG, "Error retrieving application version");
            return str;
        }
    }

    public static Object getCountryISO(Context context) {
        Object iSO3Country;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        Object obj = new Object();
        if (telephonyManager.getNetworkCountryIso().equals("")) {
            try {
                iSO3Country = context.getResources().getConfiguration().locale.getISO3Country();
            } catch (MissingResourceException e) {
                iSO3Country = null;
            }
        } else {
            try {
                iSO3Country = telephonyManager.getNetworkCountryIso();
            } catch (MissingResourceException e2) {
                iSO3Country = null;
            }
        }
        if (iSO3Country == null || iSO3Country.equals("")) {
            return null;
        }
        return iSO3Country;
    }

    public static String convertToJSON(Map<String, String> map) {
        if (map.size() > getExtraParamsLimit()) {
            Log.verbose(ANALYTICS_LOGGING_TAG, "Analytics events - number of key-value pairs in attribute map exceeds " + getExtraParamsLimit());
            return null;
        }
        String jSONObject;
        try {
            JSONObject jSONObject2 = new JSONObject();
            for (String jSONObject3 : map.keySet()) {
                if (jSONObject3.length() <= 0 || jSONObject3.length() > getMaxparamskey()) {
                    Log.verbose(ANALYTICS_LOGGING_TAG, "Analytics events - key : " + jSONObject3 + " ,exceeds inmobi's limitation of " + getMaxparamskey() + " characters.");
                    return null;
                }
                String str = (String) map.get(jSONObject3);
                if (str.length() <= getMaxstring()) {
                    jSONObject2.put(jSONObject3, map.get(jSONObject3));
                } else {
                    Log.verbose(ANALYTICS_LOGGING_TAG, "Analytics events - value : " + str + " ,exceeds inmobi's limitation of " + getMaxstring() + " characters.");
                    return null;
                }
            }
            if (jSONObject2.length() > 0) {
                jSONObject3 = jSONObject2.toString();
                return jSONObject3;
            }
        } catch (Exception e) {
            Log.internal(ANALYTICS_LOGGING_TAG, "Unable to convert map to JSON");
        }
        jSONObject3 = null;
        return jSONObject3;
    }

    public static String convertLevelStatus(IMSectionStatus iMSectionStatus) {
        if (iMSectionStatus == IMSectionStatus.COMPLETED) {
            return "1";
        }
        if (iMSectionStatus == IMSectionStatus.FAILED) {
            return "2";
        }
        if (iMSectionStatus == IMSectionStatus.CANCELED) {
            return "3";
        }
        return null;
    }

    public static String convertItemType(IMItemType iMItemType) {
        if (iMItemType == IMItemType.CONSUMABLE) {
            return "1";
        }
        if (iMItemType == IMItemType.DURABLE) {
            return "2";
        }
        if (iMItemType == IMItemType.PERSONALIZATION) {
            return "3";
        }
        return null;
    }

    public static String getScreenWidth(Context context) {
        return "" + context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String getScreenHeight(Context context) {
        return "" + context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getDeviceDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static void asyncPingInternal(String str) {
        Throwable th;
        Throwable th2;
        HttpURLConnection httpURLConnection = null;
        try {
            String replaceAll = str.replaceAll("%25", "%");
            Log.debug(ANALYTICS_LOGGING_TAG, "Pinging URL: " + replaceAll);
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(replaceAll).openConnection();
            try {
                httpURLConnection2.setConnectTimeout(20000);
                httpURLConnection2.setRequestMethod(HttpRequest.METHOD_GET);
                httpURLConnection2.setRequestProperty("User-Agent", InternalSDKUtil.getUserAgent());
                Log.debug(ANALYTICS_LOGGING_TAG, "Async Ping Connection Response Code: " + httpURLConnection2.getResponseCode());
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
            } catch (Throwable e) {
                th = e;
                httpURLConnection = httpURLConnection2;
                th2 = th;
                try {
                    Log.debug(ANALYTICS_LOGGING_TAG, "Error doing async Ping. ", th2);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th2;
                }
            } catch (Throwable e2) {
                th = e2;
                httpURLConnection = httpURLConnection2;
                th2 = th;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th2;
            }
        } catch (Exception e3) {
            th2 = e3;
            Log.debug(ANALYTICS_LOGGING_TAG, "Error doing async Ping. ", th2);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    public static String getEventUrl() {
        return AnalyticsInitializer.getConfigParams().getEndPoints().getEventsUrl();
    }

    public static String compressPayload(String str) {
        String str2 = null;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream, 0);
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(base64OutputStream);
            gZIPOutputStream.write(str.getBytes(HttpRequest.CHARSET_UTF8));
            gZIPOutputStream.close();
            base64OutputStream.close();
            str2 = byteArrayOutputStream.toString();
        } catch (Throwable e) {
            Log.internal(ANALYTICS_LOGGING_TAG, "Exception compress sdk payload.", e);
        }
        return str2;
    }
}
