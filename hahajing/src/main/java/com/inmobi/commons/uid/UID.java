package com.inmobi.commons.uid;

import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;

public class UID {
    public static final String KEY_AID = "AID";
    public static final String KEY_APPENDED_ID = "AIDL";
    public static final String KEY_FACEBOOK_ID = "FBA";
    public static final String KEY_GPID = "GPID";
    public static final String KEY_IMID = "IMID";
    public static final String KEY_LOGIN_ID = "LID";
    public static final String KEY_LTVID = "LTVID";
    public static final String KEY_ODIN1 = "O1";
    public static final String KEY_SESSION_ID = "SID";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_UM5_ID = "UM5";
    private static UID a;
    private Map<String, Boolean> b;
    private Map<String, Boolean> c;
    private Map<String, Boolean> d = new HashMap();
    private String e;
    private String f = null;

    private UID() {
        this.d.put(KEY_FACEBOOK_ID, Boolean.valueOf(false));
        this.d.put(KEY_GPID, Boolean.valueOf(true));
        this.d.put(KEY_LOGIN_ID, Boolean.valueOf(true));
        this.d.put(KEY_LTVID, Boolean.valueOf(true));
        this.d.put(KEY_ODIN1, Boolean.valueOf(true));
        this.d.put(KEY_SESSION_ID, Boolean.valueOf(true));
        this.d.put(KEY_UM5_ID, Boolean.valueOf(true));
        this.d.put(KEY_IMID, Boolean.valueOf(true));
        this.d.put(KEY_APPENDED_ID, Boolean.valueOf(true));
    }

    public static UID getInstance() {
        if (a == null) {
            a = new UID();
        }
        return a;
    }

    public void init() {
        a.g();
        printPublisherTestId();
        a.a(InternalSDKUtil.getContext());
    }

    public void refresh() {
        a.g();
    }

    public void setPublisherDeviceIdMaskMap(int i) {
        this.b = a(i);
    }

    public void setCommonsDeviceIdMaskMap(Map<String, Boolean> map) {
        this.c = map;
    }

    public Map<String, String> getRawUidMap(Map<String, Boolean> map) {
        return getUidMap(map, null, false);
    }

    public String getEncryptedJSON(Map<String, Boolean> map) {
        return new JSONObject(InternalSDKUtil.getEncodedMap(getMapForEncryption(map))).toString();
    }

    public Map<String, String> getMapForEncryption(Map<String, Boolean> map) {
        String num = Integer.toString(new Random().nextInt());
        String encryptRSA = InternalSDKUtil.encryptRSA(new JSONObject(getUidMap(map, num, true)).toString());
        Map<String, String> hashMap = new HashMap();
        hashMap.put(AdTrackerConstants.UIDMAP, encryptRSA);
        hashMap.put(AdTrackerConstants.UIDKEY, num);
        hashMap.put(AdTrackerConstants.UKEYVER, a.a());
        return hashMap;
    }

    public String getEncodedJSON(Map<String, Boolean> map) {
        Map uidMap = getUidMap(map, null, false);
        InternalSDKUtil.getEncodedMap(uidMap);
        return new JSONObject(uidMap).toString();
    }

    private Map<String, Boolean> a(int i) {
        Map<String, Boolean> hashMap = new HashMap();
        if ((i & 4) == 4 || i == 0) {
            hashMap.put(KEY_FACEBOOK_ID, Boolean.valueOf(false));
        }
        if ((i & 2) == 2 || i == 0) {
            hashMap.put(KEY_ODIN1, Boolean.valueOf(false));
        }
        if ((i & 8) == 8 || i == 0) {
            hashMap.put(KEY_UM5_ID, Boolean.valueOf(false));
        }
        return hashMap;
    }

    private Map<String, Boolean> a(Map<String, Boolean> map) {
        Map<String, Boolean> hashMap = new HashMap(this.d);
        if (map == null && this.c == null && this.b == null) {
            return hashMap;
        }
        if (map == null) {
            map = new HashMap();
        }
        if (this.c == null) {
            this.c = new HashMap();
        }
        if (this.b == null) {
            this.b = new HashMap();
        }
        for (String str : this.c.keySet()) {
            hashMap.put(str, Boolean.valueOf((Boolean.valueOf(map.get(str) == null ? true : ((Boolean) map.get(str)).booleanValue()).booleanValue() & Boolean.valueOf(this.c.get(str) == null ? true : ((Boolean) this.c.get(str)).booleanValue()).booleanValue()) & Boolean.valueOf(this.b.get(str) == null ? true : ((Boolean) this.b.get(str)).booleanValue()).booleanValue()));
        }
        return hashMap;
    }

    public String getJSON(Map<String, Boolean> map) {
        return new JSONObject(getRawUidMap(map)).toString();
    }

    public void setLtvId(String str) {
        this.e = str;
    }

    private String a() {
        return this.e;
    }

    public Map<String, String> getUidMap(Map<String, Boolean> map, String str, boolean z) {
        Object a;
        Map a2 = a((Map) map);
        Map<String, String> hashMap = new HashMap();
        if (this.f == null) {
            this.f = a.e();
        }
        if (((Boolean) a2.get(KEY_ODIN1)).booleanValue() && !a.h()) {
            a = a.a(this.f);
            if (z) {
                a = InternalSDKUtil.xorWithKey(a, str);
            }
            hashMap.put(KEY_ODIN1, a);
        }
        if (((Boolean) a2.get(KEY_FACEBOOK_ID)).booleanValue()) {
            a = a.d();
            if (a != null) {
                if (z) {
                    a = InternalSDKUtil.xorWithKey(a, str);
                }
                hashMap.put(KEY_FACEBOOK_ID, a);
            }
        }
        if (((Boolean) a2.get(KEY_UM5_ID)).booleanValue() && !a.h()) {
            a = a.b(this.f);
            if (z) {
                a = InternalSDKUtil.xorWithKey(a, str);
            }
            hashMap.put(KEY_UM5_ID, a);
        }
        if (((Boolean) a2.get(KEY_LOGIN_ID)).booleanValue()) {
            a = a.c();
            if (a != null) {
                if (z) {
                    a = InternalSDKUtil.xorWithKey(a, str);
                }
                hashMap.put(KEY_LOGIN_ID, a);
            }
        }
        if (((Boolean) a2.get(KEY_SESSION_ID)).booleanValue()) {
            a = a.b();
            if (a != null) {
                if (z) {
                    a = InternalSDKUtil.xorWithKey(a, str);
                }
                hashMap.put(KEY_SESSION_ID, a);
            }
        }
        if (((Boolean) a2.get(KEY_LTVID)).booleanValue()) {
            a = a();
            if (a != null) {
                if (z) {
                    a = InternalSDKUtil.xorWithKey(a, str);
                }
                hashMap.put(KEY_LTVID, a);
            }
        }
        if (((Boolean) a2.get(KEY_GPID)).booleanValue()) {
            AdvertisingId f = a.f();
            if (f != null) {
                a = f.getAdId();
                if (a != null) {
                    if (z) {
                        a = InternalSDKUtil.xorWithKey(a, str);
                    }
                    hashMap.put(KEY_GPID, a);
                }
            }
        }
        if (((Boolean) a2.get(KEY_IMID)).booleanValue()) {
            a = a.b(InternalSDKUtil.getContext());
            if (a != null) {
                if (z) {
                    a = InternalSDKUtil.xorWithKey(a, str);
                }
                hashMap.put(KEY_IMID, a);
            }
        }
        if (((Boolean) a2.get(KEY_APPENDED_ID)).booleanValue()) {
            a = a.c(InternalSDKUtil.getContext());
            if (a != null) {
                if (z) {
                    a = InternalSDKUtil.xorWithKey(a, str);
                }
                hashMap.put(KEY_APPENDED_ID, a);
            }
        }
        return hashMap;
    }

    public void printPublisherTestId() {
        try {
            if (a.h()) {
                AdvertisingId f = a.f();
                if (f != null) {
                    String adId = f.getAdId();
                    if (adId != null) {
                        Log.debug(InternalSDKUtil.LOGGING_TAG, "Publisher device Id is " + adId);
                        return;
                    }
                    return;
                }
                return;
            }
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Publisher device Id is " + a.a(a.e()));
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get publisher device id", e);
        }
    }

    public boolean isLimitAdTrackingEnabled() {
        return a.i();
    }
}
