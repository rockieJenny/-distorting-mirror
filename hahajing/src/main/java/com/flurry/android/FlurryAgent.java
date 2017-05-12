package com.flurry.android;

import android.app.Application;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.os.Build.VERSION;
import com.flurry.sdk.eq;
import com.flurry.sdk.fp;
import com.flurry.sdk.fq;
import com.flurry.sdk.fr;
import com.flurry.sdk.gd;
import com.flurry.sdk.hc;
import com.flurry.sdk.hg;
import com.flurry.sdk.hp;
import com.flurry.sdk.hu;
import java.util.Date;
import java.util.Map;

public final class FlurryAgent {
    private static final String a = FlurryAgent.class.getSimpleName();

    private FlurryAgent() {
    }

    public static int getAgentVersion() {
        return fq.a();
    }

    public static String getReleaseVersion() {
        return fq.f();
    }

    public static void setLogEnabled(boolean z) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (z) {
            gd.b();
        } else {
            gd.a();
        }
    }

    public static void setLogLevel(int i) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else {
            gd.a(i);
        }
    }

    public static void setVersionName(String str) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String versionName passed to setVersionName was null.");
        } else {
            hg.a().a("VersionName", (Object) str);
        }
    }

    public static void setReportLocation(boolean z) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else {
            hg.a().a("ReportLocation", (Object) Boolean.valueOf(z));
        }
    }

    public static void setLocation(float f, float f2) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
            return;
        }
        Location location = new Location("Explicit");
        location.setLatitude((double) f);
        location.setLongitude((double) f2);
        hg.a().a("ExplicitLocation", (Object) location);
    }

    public static void clearLocation() {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else {
            hg.a().a("ExplicitLocation", null);
        }
    }

    public static void setContinueSessionMillis(long j) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (j < 5000) {
            gd.b(a, "Invalid time set for session resumption: " + j);
        } else {
            hg.a().a("ContinueSessionMillis", (Object) Long.valueOf(j));
        }
    }

    public static void setLogEvents(boolean z) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else {
            hg.a().a("LogEvents", (Object) Boolean.valueOf(z));
        }
    }

    public static void setCaptureUncaughtExceptions(boolean z) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else {
            hg.a().a("CaptureUncaughtExceptions", (Object) Boolean.valueOf(z));
        }
    }

    public static void addOrigin(String str, String str2) {
        addOrigin(str, str2, null);
    }

    public static void addOrigin(String str, String str2, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("originName not specified");
        } else if (str2 == null || str2.length() == 0) {
            throw new IllegalArgumentException("originVersion not specified");
        } else {
            try {
                fr.a().a(str, str2, map);
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
    }

    public static synchronized void init(Context context, String str) {
        synchronized (FlurryAgent.class) {
            if (VERSION.SDK_INT < 10) {
                gd.b(a, "Device SDK Version older than 10");
            } else if (context == null) {
                throw new NullPointerException("Null context");
            } else {
                if (str != null) {
                    if (str.length() != 0) {
                        try {
                            hu.a();
                            fp.a(context, str);
                        } catch (Throwable th) {
                            gd.a(a, "", th);
                        }
                    }
                }
                throw new IllegalArgumentException("apiKey not specified");
            }
        }
    }

    @Deprecated
    public static void onStartSession(Context context, String str) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (context instanceof Application) {
            throw new NullPointerException("Cannot start a session with an Application context");
        } else if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Api key not specified");
        } else if (fp.a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            try {
                hc.a().b(context);
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
    }

    public static void onStartSession(Context context) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (context instanceof Application) {
            throw new NullPointerException("Cannot start a session with an Application context");
        } else if (fp.a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            try {
                hc.a().b(context);
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
    }

    public static void onEndSession(Context context) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (context instanceof Application) {
            throw new IllegalArgumentException("Cannot end a session with an Application context");
        } else if (fp.a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before ending a session");
        } else {
            try {
                hc.a().c(context);
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
    }

    public static boolean isSessionActive() {
        boolean z = false;
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else {
            try {
                z = hc.a().f();
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
        return z;
    }

    public static FlurryEventRecordStatus logEvent(String str) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = eq.a().a(str);
            } catch (Throwable th) {
                gd.a(a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to logEvent was null.");
        } else if (map == null) {
            gd.b(a, "String parameters passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = eq.a().a(str, (Map) map);
            } catch (Throwable th) {
                gd.a(a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static FlurryEventRecordStatus logEvent(String str, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = eq.a().a(str, z);
            } catch (Throwable th) {
                gd.a(a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to logEvent was null.");
        } else if (map == null) {
            gd.b(a, "String parameters passed to logEvent was null.");
        } else {
            try {
                flurryEventRecordStatus = eq.a().a(str, (Map) map, z);
            } catch (Throwable th) {
                gd.a(a, "Failed to log event: " + str, th);
            }
        }
        return flurryEventRecordStatus;
    }

    public static void endTimedEvent(String str) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                eq.a().b(str);
            } catch (Throwable th) {
                gd.a(a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    public static void endTimedEvent(String str, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to endTimedEvent was null.");
        } else if (map == null) {
            gd.b(a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                eq.a().b(str, map);
            } catch (Throwable th) {
                gd.a(a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    @Deprecated
    public static void onError(String str, String str2, String str3) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            gd.b(a, "String message passed to onError was null.");
        } else if (str3 == null) {
            gd.b(a, "String errorClass passed to onError was null.");
        } else {
            try {
                eq.a().a(str, str2, str3);
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
    }

    public static void onError(String str, String str2, Throwable th) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            gd.b(a, "String message passed to onError was null.");
        } else if (th == null) {
            gd.b(a, "Throwable passed to onError was null.");
        } else {
            try {
                eq.a().a(str, str2, th);
            } catch (Throwable th2) {
                gd.a(a, "", th2);
            }
        }
    }

    @Deprecated
    public static void onEvent(String str) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to onEvent was null.");
        } else {
            try {
                eq.a().c(str);
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
    }

    @Deprecated
    public static void onEvent(String str, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String eventId passed to onEvent was null.");
        } else if (map == null) {
            gd.b(a, "Parameters Map passed to onEvent was null.");
        } else {
            try {
                eq.a().c(str, map);
            } catch (Throwable th) {
                gd.a(a, "", th);
            }
        }
    }

    public static void onPageView() {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
            return;
        }
        try {
            eq.a().e();
        } catch (Throwable th) {
            gd.a(a, "", th);
        }
    }

    @Deprecated
    public static void setLocationCriteria(Criteria criteria) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        }
    }

    public static void setAge(int i) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (i > 0 && i < 110) {
            hg.a().a("Age", (Object) Long.valueOf(new Date(new Date(System.currentTimeMillis() - (((long) i) * 31449600000L)).getYear(), 1, 1).getTime()));
        }
    }

    public static void setGender(byte b) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
            return;
        }
        switch (b) {
            case (byte) 0:
            case (byte) 1:
                hg.a().a("Gender", (Object) Byte.valueOf(b));
                return;
            default:
                hg.a().a("Gender", (Object) Byte.valueOf((byte) -1));
                return;
        }
    }

    public static void setUserId(String str) {
        if (VERSION.SDK_INT < 10) {
            gd.b(a, "Device SDK Version older than 10");
        } else if (str == null) {
            gd.b(a, "String userId passed to setUserId was null.");
        } else {
            hg.a().a("UserId", (Object) hp.b(str));
        }
    }
}
