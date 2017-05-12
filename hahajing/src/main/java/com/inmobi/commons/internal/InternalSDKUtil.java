package com.inmobi.commons.internal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.cache.CacheController;
import com.inmobi.commons.cache.CacheController.Validator;
import com.inmobi.commons.thirdparty.Base64;
import com.inmobi.commons.uid.UID;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import org.json.JSONException;
import org.json.JSONObject;

public class InternalSDKUtil {
    public static final String ACTION_CONNECTIVITY_UPDATE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String ACTION_RECEIVER_REFERRER = "com.android.vending.INSTALL_REFERRER";
    public static final String ACTION_SHARE_INMID = "com.inmobi.share.id";
    public static final String BASE_LOG_TAG = "[InMobi]";
    public static final String IM_PREF = "impref";
    public static final String INMOBI_SDK_RELEASE_DATE = "20150212";
    public static final String INMOBI_SDK_RELEASE_VERSION = "4.5.3";
    public static final String KEY_LTVID = "ltvid";
    public static final String LOGGING_TAG = "[InMobi]-4.5.3";
    public static final String PRODUCT_COMMONS = "commons";
    private static CommonsConfig a = new CommonsConfig();
    private static String b;
    private static Context c = null;
    private static Map<String, String> d = new HashMap();
    private static Validator e = new Validator() {
        public boolean validate(Map<String, Object> map) {
            return InternalSDKUtil.a(map);
        }
    };
    private static boolean f = true;

    @TargetApi(17)
    static class a {
        static String a(Context context) {
            return WebSettings.getDefaultUserAgent(context);
        }
    }

    public static Context getContext() {
        return c;
    }

    public static void setContext(Context context) {
        if (c == null) {
            c = context.getApplicationContext();
            if (InMobi.getAppId() != null) {
                a();
                try {
                    CacheController.getConfig(PRODUCT_COMMONS, context, d, e);
                } catch (CommonsException e) {
                    Log.internal(PRODUCT_COMMONS, "Unable retrive config for commons product");
                }
            }
        }
    }

    public static String byteToHex(byte b) {
        try {
            char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            return new String(new char[]{cArr[(b >> 4) & 15], cArr[b & 15]});
        } catch (Exception e) {
            return null;
        }
    }

    public static String xorWithKey(String str, String str2) {
        String str3 = "";
        try {
            byte[] bytes = str.getBytes(HttpRequest.CHARSET_UTF8);
            byte[] bArr = new byte[bytes.length];
            byte[] bytes2 = str2.getBytes(HttpRequest.CHARSET_UTF8);
            for (int i = 0; i < bytes.length; i++) {
                bArr[i] = (byte) (bytes[i] ^ bytes2[i % bytes2.length]);
            }
            return new String(Base64.encode(bArr, 2), HttpRequest.CHARSET_UTF8);
        } catch (Throwable e) {
            Log.debug(LOGGING_TAG, "Exception in xor with random integer", e);
            return str3;
        }
    }

    @SuppressLint({"TrulyRandom"})
    public static String encryptRSA(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        try {
            RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(new BigInteger("C10F7968CFE2C76AC6F0650C877806D4514DE58FC239592D2385BCE5609A84B2A0FBDAF29B05505EAD1FDFEF3D7209ACBF34B5D0A806DF18147EA9C0337D6B5B", 16), new BigInteger("010001", 16)));
            Cipher instance = Cipher.getInstance("RSA/ECB/nopadding");
            instance.init(1, rSAPublicKey);
            return new String(Base64.encode(a(str.getBytes(HttpRequest.CHARSET_UTF8), 1, instance), 0));
        } catch (Throwable e) {
            Log.debug(LOGGING_TAG, "Exception in encryptRSA", e);
            return null;
        }
    }

    private static byte[] a(byte[] bArr, int i, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
        int length;
        byte[] bArr2;
        int i2;
        byte[] bArr3 = new byte[0];
        byte[] bArr4 = new byte[0];
        if (i == 1) {
            length = bArr.length;
            bArr2 = new byte[64];
            bArr3 = bArr4;
            i2 = 0;
        } else {
            length = bArr.length;
            bArr2 = new byte[64];
            bArr3 = bArr4;
            i2 = 0;
        }
        while (i2 < length) {
            if (i2 > 0 && i2 % 64 == 0) {
                int i3;
                bArr3 = a(bArr3, cipher.doFinal(bArr2));
                if (i2 + 64 > length) {
                    i3 = length - i2;
                } else {
                    i3 = 64;
                }
                bArr2 = new byte[i3];
            }
            bArr2[i2 % 64] = bArr[i2];
            i2++;
        }
        return a(bArr3, cipher.doFinal(bArr2));
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        Object obj = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        System.arraycopy(bArr2, 0, obj, bArr.length, bArr2.length);
        return obj;
    }

    public static String getDigested(String str, String str2) {
        if (str != null) {
            try {
                if (!"".equals(str.trim())) {
                    MessageDigest instance = MessageDigest.getInstance(str2);
                    instance.update(str.getBytes());
                    byte[] digest = instance.digest();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (byte b : digest) {
                        stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
                    }
                    return stringBuffer.toString();
                }
            } catch (Throwable e) {
                Log.debug(LOGGING_TAG, "Exception in getting ODIN-1", e);
                return null;
            }
        }
        return "TEST_EMULATOR";
    }

    public static void initialize(Context context) {
        if (getContext() == null) {
            if (context == null) {
                c.getApplicationContext();
            } else {
                c = context.getApplicationContext();
            }
        }
        if (InMobi.getAppId() != null) {
            if (f) {
                f = false;
                a();
            }
            try {
                a(CacheController.getConfig(PRODUCT_COMMONS, context, d, e).getData());
            } catch (CommonsException e) {
                Log.internal(LOGGING_TAG, "IMCommons config init: IMCommonsException caught. Reason: " + e.toString());
            } catch (Exception e2) {
                Log.internal(LOGGING_TAG, "Exception while getting commons config data.");
            }
        }
    }

    static boolean a(Map<String, Object> map) {
        a();
        try {
            CommonsConfig commonsConfig = new CommonsConfig();
            commonsConfig.setFromMap(map);
            a = commonsConfig;
            ApiStatCollector.getLogger().setMetricConfigParams(commonsConfig.getApiStatsConfig());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void a() {
        d = UID.getInstance().getMapForEncryption(null);
    }

    public static CommonsConfig getConfig() {
        return a;
    }

    public static String getSavedUserAgent() {
        return b;
    }

    public static String getUserAgent() {
        try {
            if (b == null) {
                if (VERSION.SDK_INT >= 17) {
                    b = a.a(c);
                } else {
                    b = new WebView(c).getSettings().getUserAgentString();
                }
            }
            return b;
        } catch (Throwable e) {
            Log.internal(LOGGING_TAG, "Cannot get user agent", e);
            return b;
        }
    }

    public static boolean validateAppId(String str) {
        if (str == null) {
            Log.debug(LOGGING_TAG, "appId is null");
            return false;
        } else if (str.matches("(x)+")) {
            Log.debug(LOGGING_TAG, "appId is all xxxxxxx");
            return false;
        } else if (!"".equals(str.trim())) {
            return true;
        } else {
            Log.debug(LOGGING_TAG, "appId is all blank");
            return false;
        }
    }

    public static String getConnectivityType(Context context) {
        String str;
        Throwable e;
        try {
            if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        int type = activeNetworkInfo.getType();
                        int subtype = activeNetworkInfo.getSubtype();
                        if (type == 1) {
                            return "wifi";
                        }
                        if (type == 0) {
                            str = "carrier";
                            if (subtype == 1) {
                                try {
                                    return "gprs";
                                } catch (Exception e2) {
                                    e = e2;
                                    Log.internal(LOGGING_TAG, "Error getting the network info", e);
                                    return str;
                                }
                            } else if (subtype == 2) {
                                return "edge";
                            } else {
                                if (subtype == 3) {
                                    return "umts";
                                }
                                if (subtype == 0) {
                                    return "carrier";
                                }
                                return str;
                            }
                        }
                    }
                }
            }
            return null;
        } catch (Throwable e3) {
            Throwable th = e3;
            str = null;
            e = th;
            Log.internal(LOGGING_TAG, "Error getting the network info", e);
            return str;
        }
    }

    public static boolean checkNetworkAvailibility(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager.getActiveNetworkInfo() == null) {
                return false;
            }
            return connectivityManager.getActiveNetworkInfo().isConnected();
        } catch (Throwable e) {
            Log.internal(LOGGING_TAG, "Cannot check network state", e);
            return false;
        }
    }

    public static long getLongFromJSON(JSONObject jSONObject, String str, long j) {
        try {
            j = jSONObject.getLong(str);
        } catch (Exception e) {
            try {
                Log.debug(LOGGING_TAG, "JSON with property " + str + " found but has bad datatype(" + jSONObject.get(str).getClass() + "). Reverting to " + j);
            } catch (JSONException e2) {
            }
        }
        return j;
    }

    public static int getIntFromJSON(JSONObject jSONObject, String str, int i) {
        try {
            i = jSONObject.getInt(str);
        } catch (Exception e) {
            try {
                Log.debug(LOGGING_TAG, "JSON with property " + str + " found but has bad datatype(" + jSONObject.get(str).getClass() + "). Reverting to " + i);
            } catch (JSONException e2) {
            }
        }
        return i;
    }

    public static boolean getBooleanFromJSON(JSONObject jSONObject, String str, boolean z) {
        try {
            z = jSONObject.getBoolean(str);
        } catch (Exception e) {
            try {
                Log.debug(LOGGING_TAG, "JSON with property " + str + " found but has bad datatype(" + jSONObject.get(str).getClass() + "). Reverting to " + z);
            } catch (JSONException e2) {
            }
        }
        return z;
    }

    public static String getStringFromJSON(JSONObject jSONObject, String str, String str2) {
        try {
            str2 = jSONObject.getString(str);
        } catch (Exception e) {
            try {
                Log.debug(LOGGING_TAG, "JSON with property " + str + " found but has bad datatype(" + jSONObject.get(str).getClass() + "). Reverting to " + str2);
            } catch (JSONException e2) {
            }
        }
        return str2;
    }

    public static String getFinalRedirectedUrl(String str) {
        String str2;
        int i = 0;
        String str3 = str;
        while (true) {
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) new URL(str3).openConnection();
                httpURLConnection.setRequestProperty("User-Agent", getUserAgent());
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode < 300 || responseCode >= 400) {
                    str2 = str3;
                } else {
                    str2 = httpURLConnection.getHeaderField(HttpRequest.HEADER_LOCATION);
                    if (str2 == null) {
                        break;
                    } else if (httpURLConnection.getResponseCode() != 200) {
                        int i2 = i + 1;
                        if (i < 5) {
                            i = i2;
                            str3 = str2;
                        }
                    }
                }
                break;
            } catch (Throwable e) {
                Throwable th = e;
                String str4 = str3;
            }
        }
        str2 = str3;
        try {
            httpURLConnection.disconnect();
            return str2;
        } catch (Throwable e2) {
            Throwable th2 = e2;
            str4 = str2;
            th = th2;
            Log.internal(LOGGING_TAG, "Cannot get redirect url", th);
            return str4;
        }
    }

    public static void populate(Map<String, Object> map, Map<String, Object> map2, boolean z) {
        for (String str : map.keySet()) {
            if (map2.get(str) == null) {
                map2.put(str, map.get(str));
            }
            Object obj = map.get(str);
            Object obj2 = map2.get(str);
            if (!(obj instanceof Map) || !(obj2 instanceof Map)) {
                map2.put(str, obj);
            } else if (z) {
                populate((Map) obj, (Map) obj2, true);
            } else {
                map2.put(str, obj);
            }
        }
    }

    public static Map<String, Object> populateToNewMap(Map<String, Object> map, Map<String, Object> map2, boolean z) {
        Map hashMap = new HashMap();
        populate((Map) map2, hashMap, false);
        populate((Map) map, hashMap, z);
        return hashMap;
    }

    public static Object getObjectFromMap(Map<String, Object> map, String str) {
        Object obj = map.get(str);
        if (obj == null || !(obj instanceof Map)) {
            Log.internal(LOGGING_TAG, "Key " + str + " has illegal value");
            throw new IllegalArgumentException();
        } else if (!((Map) obj).isEmpty()) {
            return obj;
        } else {
            Log.internal(LOGGING_TAG, "Key " + str + " has empty object as value.");
            throw new IllegalArgumentException();
        }
    }

    public static boolean getBooleanFromMap(Map<String, Object> map, String str) {
        Object obj = map.get(str);
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        Log.internal(LOGGING_TAG, "Key " + str + " has illegal value");
        throw new IllegalArgumentException();
    }

    public static long getLongFromMap(Map<String, Object> map, String str, long j, long j2) {
        Object obj = map.get(str);
        if (obj instanceof Long) {
            long longValue = ((Long) obj).longValue();
            if (longValue <= j2 && longValue >= j) {
                return longValue;
            }
        }
        if (j < -2147483648L) {
            j = -2147483648L;
        }
        int i = (int) j;
        if (j2 > 2147483647L) {
            j2 = 2147483647L;
        }
        return (long) getIntFromMap(map, str, i, (long) ((int) j2));
    }

    public static int getIntFromMap(Map<String, Object> map, String str, int i, long j) {
        Object obj = map.get(str);
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            if (((long) intValue) <= j && intValue >= i) {
                return intValue;
            }
        }
        Log.internal(LOGGING_TAG, "Key " + str + " has illegal value");
        throw new IllegalArgumentException();
    }

    public static String getStringFromMap(Map<String, Object> map, String str) {
        Object obj = map.get(str);
        if (obj instanceof String) {
            return (String) obj;
        }
        Log.internal(LOGGING_TAG, "Key " + str + " has illegal value");
        throw new IllegalArgumentException();
    }

    public static boolean isInitializedSuccessfully(boolean z) {
        if (getContext() != null && InMobi.getAppId() != null) {
            return true;
        }
        if (z) {
            Log.debug(LOGGING_TAG, "InMobi not initialized. Call InMobi.initialize before using any InMobi API");
        }
        return false;
    }

    public static boolean isInitializedSuccessfully() {
        return isInitializedSuccessfully(true);
    }

    public static String getURLEncoded(String str) {
        String str2 = "";
        try {
            str2 = URLEncoder.encode(str, HttpRequest.CHARSET_UTF8);
        } catch (Throwable e) {
            Log.internal(LOGGING_TAG, "Exception URL encoding " + str, e);
        }
        return str2;
    }

    public static Map<String, String> getEncodedMap(Map<String, ? extends Object> map) {
        Map<String, String> hashMap = new HashMap();
        for (String str : map.keySet()) {
            try {
                hashMap.put(getURLEncoded(str), getURLEncoded(map.get(str).toString()));
            } catch (Throwable e) {
                Log.internal(LOGGING_TAG, "Exception Map encoding " + map.toString(), e);
            }
        }
        return hashMap;
    }

    public static String getURLDecoded(String str, String str2) {
        String str3 = "";
        try {
            str3 = URLDecoder.decode(str, str2);
        } catch (Throwable e) {
            Log.internal(LOGGING_TAG, "Exception URL decoding " + str, e);
        }
        return str3;
    }

    public static String getInMobiInternalVersion(String str) {
        String[] split = str.split("[.]");
        StringBuffer stringBuffer = new StringBuffer("");
        for (String valueOf : split) {
            try {
                stringBuffer.append("T").append((char) (Integer.valueOf(valueOf).intValue() + 65));
            } catch (NumberFormatException e) {
            }
        }
        if (stringBuffer.equals("")) {
            return "";
        }
        return stringBuffer.substring(1).toString();
    }

    public static String mapToJSONs(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (String str : map.keySet()) {
            try {
                jSONObject.put(str, map.get(str));
            } catch (JSONException e) {
                Log.internal(LOGGING_TAG, "Unable to convert map to JSON for key " + str);
            }
        }
        return jSONObject.toString();
    }

    public static String encodeMapAndconvertToDelimitedString(Map<String, ? extends Object> map, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str2 : map.keySet()) {
            try {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(str);
                }
                stringBuilder.append(String.format("%s=%s", new Object[]{getURLEncoded(str2), getURLEncoded(map.get(str2).toString())}));
            } catch (Throwable e) {
                Log.internal(LOGGING_TAG, "Exception Converting map to deliminated string " + map.toString(), e);
            }
        }
        return stringBuilder.toString();
    }

    public static String convertListToDelimitedString(List<?> list, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            try {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(str);
                }
                stringBuilder.append(list.get(i));
            } catch (Throwable e) {
                Log.internal(LOGGING_TAG, "Exception Converting map to deliminated string " + list.toString(), e);
            }
        }
        return stringBuilder.toString();
    }

    public static void populate(JSONObject jSONObject, JSONObject jSONObject2, boolean z) throws JSONException {
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            try {
                jSONObject2.get(str);
                try {
                    Object obj = jSONObject.get(str);
                    Object obj2 = jSONObject2.get(str);
                    if (!(obj instanceof JSONObject) || !(obj2 instanceof JSONObject)) {
                        jSONObject2.put(str, obj);
                    } else if (z) {
                        populate((JSONObject) obj, (JSONObject) obj2, true);
                    } else {
                        jSONObject2.put(str, obj);
                    }
                } catch (Throwable e) {
                    Log.internal(LOGGING_TAG, "Cannot populate to json exception", e);
                }
            } catch (JSONException e2) {
                jSONObject2.put(str, jSONObject.get(str));
            }
        }
    }

    public static String getLtvpSessionId() {
        return getContext().getSharedPreferences("inmobiAppAnalyticsSession", 0).getString("APP_SESSION_ID", null);
    }

    public static boolean isHexString(String str) {
        return str.matches("[0-9A-F]+");
    }

    public static JSONObject populateToNewJSON(JSONObject jSONObject, JSONObject jSONObject2, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        populate(jSONObject2, jSONObject3, false);
        populate(jSONObject, jSONObject3, z);
        return jSONObject3;
    }

    public static Map<String, Object> JSONToMap(JSONObject jSONObject) {
        try {
            Map<String, Object> hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                try {
                    hashMap.put(str, jSONObject.get(str));
                } catch (JSONException e) {
                }
            }
            return hashMap;
        } catch (Exception e2) {
            return null;
        }
    }

    public static String union(String str, String str2) {
        String[] split = str.split(",");
        for (int i = 0; i < split.length; i++) {
            if (!str2.contains(split[i])) {
                str2 = str2 + "," + split[i];
            }
        }
        return str2;
    }
}
