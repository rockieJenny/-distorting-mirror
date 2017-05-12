package com.inmobi.commons.analytics.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.util.GregorianCalendar;
import java.util.UUID;

public class SessionInfo {
    private static String a;
    private static long b;
    private static String c;
    private static int d;

    public static synchronized boolean isUpdatedFromOldSDK(Context context) {
        boolean z;
        synchronized (SessionInfo.class) {
            z = context.getSharedPreferences("inmobiAppAnalyticsSession", 0).getBoolean("UPDATED_FROM_OLD_SDK", false);
        }
        return z;
    }

    public static synchronized void updatedFromOldSDK(Context context) {
        synchronized (SessionInfo.class) {
            Editor edit = context.getSharedPreferences("inmobiAppAnalyticsSession", 0).edit();
            edit.putBoolean("UPDATED_FROM_OLD_SDK", true);
            edit.commit();
        }
    }

    public static synchronized void resetFirstTime() {
        synchronized (SessionInfo.class) {
            d = 0;
        }
    }

    public static synchronized int getFirstTime() {
        int i;
        synchronized (SessionInfo.class) {
            i = d;
        }
        return i;
    }

    public static synchronized void storeFirstTime(Context context) {
        int i = 0;
        synchronized (SessionInfo.class) {
            try {
                int i2;
                long j;
                SharedPreferences sharedPreferences = context.getSharedPreferences("inmobiAppAnalyticsSession", 0);
                Editor edit = sharedPreferences.edit();
                long j2 = sharedPreferences.getLong("SESSION_TIMEM", -1);
                if (j2 != -1) {
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    gregorianCalendar.setTimeInMillis(j2);
                    gregorianCalendar.setFirstDayOfWeek(2);
                    GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
                    j2 = gregorianCalendar2.getTimeInMillis();
                    gregorianCalendar2.setFirstDayOfWeek(2);
                    if (gregorianCalendar.get(6) != gregorianCalendar2.get(6)) {
                        i = 1;
                    }
                    if (gregorianCalendar.get(3) != gregorianCalendar2.get(3)) {
                        i |= 2;
                    }
                    if (gregorianCalendar.get(2) != gregorianCalendar2.get(2)) {
                        i |= 4;
                    }
                    long j3 = j2;
                    i2 = i;
                    j = j3;
                } else {
                    j = new GregorianCalendar().getTimeInMillis();
                    i2 = 15;
                }
                edit.putLong("SESSION_TIMEM", j);
                edit.commit();
                d = i2;
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception storing session data first time", e);
            }
        }
    }

    public static synchronized void storeSessionId(Context context) {
        synchronized (SessionInfo.class) {
            try {
                a = UUID.randomUUID().toString();
                b = System.currentTimeMillis() / 1000;
                Editor edit = context.getSharedPreferences("inmobiAppAnalyticsSession", 0).edit();
                edit.putString("SESSION_ID", a);
                edit.putString("APP_SESSION_ID", a);
                edit.putLong("SESSION_TIME", b);
                edit.commit();
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception putting session id", e);
            }
        }
    }

    public static synchronized String getSessionId(Context context) {
        String str;
        synchronized (SessionInfo.class) {
            try {
                if (a != null) {
                    str = a;
                } else {
                    str = context.getSharedPreferences("inmobiAppAnalyticsSession", 0).getString("SESSION_ID", null);
                    a = str;
                }
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception getting session id", e);
                str = null;
            }
        }
        return str;
    }

    public static synchronized long getSessionTime(Context context) {
        long j;
        synchronized (SessionInfo.class) {
            try {
                if (b != 0) {
                    j = b;
                } else {
                    j = context.getSharedPreferences("inmobiAppAnalyticsSession", 0).getLong("SESSION_TIME", 0);
                    b = j;
                }
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception getting session time", e);
                j = 0;
            }
        }
        return j;
    }

    public static synchronized void removeSessionId(Context context) {
        synchronized (SessionInfo.class) {
            try {
                a = null;
                b = 0;
                Editor edit = context.getSharedPreferences("inmobiAppAnalyticsSession", 0).edit();
                edit.putString("SESSION_ID", null);
                edit.putString("SESSION_TIME", null);
                edit.commit();
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception removing session id", e);
            }
        }
    }

    public static void storeAppId(Context context, String str) {
        try {
            c = str;
            Editor edit = context.getSharedPreferences("inmobiAppAnalyticsAppId", 0).edit();
            edit.putString("APP_ID", str);
            edit.commit();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception storing app id", e);
        }
    }

    public static String getAppId(Context context) {
        try {
            if (c != null) {
                return c;
            }
            String string = context.getSharedPreferences("inmobiAppAnalyticsAppId", 0).getString("APP_ID", null);
            c = string;
            return string;
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception getting app id", e);
            return null;
        }
    }
}
