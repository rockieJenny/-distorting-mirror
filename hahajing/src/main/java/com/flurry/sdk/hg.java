package com.flurry.sdk;

import android.location.Criteria;
import android.location.Location;

public class hg extends hh {
    public static final Integer a = Integer.valueOf(195);
    public static final Integer b = Integer.valueOf(5);
    public static final Integer c = Integer.valueOf(3);
    public static final Integer d = Integer.valueOf(0);
    public static final String e = null;
    public static final Boolean f = Boolean.valueOf(true);
    public static final Boolean g = Boolean.valueOf(true);
    public static final String h = null;
    public static final Boolean i = Boolean.valueOf(true);
    public static final Criteria j = null;
    public static final Location k = null;
    public static final Long l = Long.valueOf(10000);
    public static final Boolean m = Boolean.valueOf(true);
    public static final Long n = null;
    public static final Byte o = Byte.valueOf((byte) -1);
    private static hg p;

    public static synchronized hg a() {
        hg hgVar;
        synchronized (hg.class) {
            if (p == null) {
                p = new hg();
            }
            hgVar = p;
        }
        return hgVar;
    }

    public static synchronized void b() {
        synchronized (hg.class) {
            if (p != null) {
                p.d();
            }
            p = null;
        }
    }

    private hg() {
        c();
    }

    public void c() {
        a("AgentVersion", (Object) a);
        a("ReleaseMajorVersion", (Object) b);
        a("ReleaseMinorVersion", (Object) c);
        a("ReleasePatchVersion", (Object) d);
        a("ReleaseBetaVersion", (Object) "");
        a("VersionName", (Object) e);
        a("CaptureUncaughtExceptions", (Object) f);
        a("UseHttps", (Object) g);
        a("ReportUrl", (Object) h);
        a("ReportLocation", (Object) i);
        a("ExplicitLocation", (Object) k);
        a("ContinueSessionMillis", (Object) l);
        a("LogEvents", (Object) m);
        a("Age", (Object) n);
        a("Gender", (Object) o);
        a("UserId", (Object) "");
    }
}
