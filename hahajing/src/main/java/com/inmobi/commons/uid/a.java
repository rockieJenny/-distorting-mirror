package com.inmobi.commons.uid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.inmobi.commons.IMIDType;
import com.inmobi.commons.data.DemogInfo;
import com.inmobi.commons.data.DeviceInfo;
import com.inmobi.commons.internal.FileOperations;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.Date;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: UIDHelper */
class a {
    private static final Uri a = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    private static AdvertisingId b = null;

    static String a() {
        return "1";
    }

    static String a(String str) {
        return InternalSDKUtil.getDigested(str, CommonUtils.SHA1_INSTANCE);
    }

    static String b() {
        return DemogInfo.getIDType(IMIDType.ID_SESSION);
    }

    static String c() {
        return DemogInfo.getIDType(IMIDType.ID_LOGIN);
    }

    static String b(String str) {
        return InternalSDKUtil.getDigested(str, CommonUtils.MD5_INSTANCE);
    }

    static String d() {
        try {
            Cursor query = InternalSDKUtil.getContext().getContentResolver().query(a, new String[]{"aid"}, null, null, null);
            if (query == null || !query.moveToFirst()) {
                return null;
            }
            String string = query.getString(query.getColumnIndex("aid"));
            if (string == null || string.length() == 0) {
                return null;
            }
            return string;
        } catch (Exception e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to retrieve Facebook attrib id: " + e);
            return null;
        }
    }

    static String e() {
        try {
            return (String) Class.forName("com.inmobi.commons.uid.PlatformId").getDeclaredMethod("getAndroidId", new Class[]{Context.class}).invoke(null, new Object[]{InternalSDKUtil.getContext()});
        } catch (Exception e) {
            return null;
        }
    }

    static AdvertisingId f() {
        return b;
    }

    static void g() {
        b = new AdvertisingId();
        b.a(FileOperations.getPreferences(InternalSDKUtil.getContext(), InternalSDKUtil.IM_PREF, "gpid"));
        b.a(FileOperations.getBooleanPreferences(InternalSDKUtil.getContext(), InternalSDKUtil.IM_PREF, "limitadtrck"));
        new Thread(new Runnable() {
            public void run() {
                try {
                    Class cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
                    Class cls2 = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
                    Object invoke = cls.getDeclaredMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(null, new Object[]{InternalSDKUtil.getContext()});
                    String str = (String) cls2.getDeclaredMethod("getId", (Class[]) null).invoke(invoke, (Object[]) null);
                    a.b.a(str);
                    FileOperations.setPreferences(InternalSDKUtil.getContext(), InternalSDKUtil.IM_PREF, "gpid", str);
                    Boolean bool = (Boolean) cls2.getDeclaredMethod("isLimitAdTrackingEnabled", (Class[]) null).invoke(invoke, (Object[]) null);
                    a.b.a(bool.booleanValue());
                    FileOperations.setPreferences(InternalSDKUtil.getContext(), InternalSDKUtil.IM_PREF, "limitadtrck", bool.booleanValue());
                } catch (Throwable e) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception getting advertiser id", e);
                }
            }
        }).start();
    }

    static boolean h() {
        try {
            return GooglePlayServicesUtil.isGooglePlayServicesAvailable(InternalSDKUtil.getContext()) == 0;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    static boolean i() {
        AdvertisingId f = f();
        if (f != null) {
            return f.isLimitAdTracking();
        }
        return false;
    }

    protected static void a(Context context) {
        String preferences = FileOperations.getPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_IMID);
        long longPreferences = FileOperations.getLongPreferences(context, InternalSDKUtil.IM_PREF, "timestamp");
        if (preferences == null) {
            preferences = UUID.randomUUID().toString();
            FileOperations.setPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_IMID, preferences);
            FileOperations.setPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_AID, DeviceInfo.getAid());
            FileOperations.setPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_APPENDED_ID, DeviceInfo.getAid());
        }
        if (longPreferences == 0) {
            FileOperations.setPreferences(context, InternalSDKUtil.IM_PREF, "timestamp", new Date().getTime());
        }
        Intent intent = new Intent();
        intent.setAction(InternalSDKUtil.ACTION_SHARE_INMID);
        intent.putExtra(UID.KEY_IMID, preferences);
        intent.putExtra(UID.KEY_APPENDED_ID, FileOperations.getPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_APPENDED_ID));
        intent.putExtra("timestamp", FileOperations.getLongPreferences(context, InternalSDKUtil.IM_PREF, "timestamp"));
        intent.putExtra(UID.KEY_AID, DeviceInfo.getAid());
        context.sendBroadcast(intent);
    }

    static String b(Context context) {
        return FileOperations.getPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_IMID);
    }

    static String c(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            CharSequence preferences = FileOperations.getPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_AID);
            if (preferences != null) {
                jSONObject.put("p", preferences);
            }
            Object preferences2 = FileOperations.getPreferences(context, InternalSDKUtil.IM_PREF, UID.KEY_APPENDED_ID);
            if (preferences2 != null && preferences2.contains(preferences)) {
                preferences2 = preferences2.replace(preferences, "");
            }
            if (!(preferences2 == null || preferences2.trim() == "")) {
                if (preferences2.charAt(0) == ',') {
                    preferences2 = preferences2.substring(1);
                }
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(preferences2);
                jSONObject.put("s", jSONArray);
            }
            return jSONObject.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
