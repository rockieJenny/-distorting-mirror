package com.flurry.sdk;

import java.util.Locale;

public class fq {
    private static final String a = fq.class.getSimpleName();

    public static int a() {
        int intValue = ((Integer) hg.a().a("AgentVersion")).intValue();
        gd.a(4, a, "getAgentVersion() = " + intValue);
        return intValue;
    }

    public static int b() {
        return ((Integer) hg.a().a("ReleaseMajorVersion")).intValue();
    }

    public static int c() {
        return ((Integer) hg.a().a("ReleaseMinorVersion")).intValue();
    }

    public static int d() {
        return ((Integer) hg.a().a("ReleasePatchVersion")).intValue();
    }

    public static String e() {
        return (String) hg.a().a("ReleaseBetaVersion");
    }

    public static String f() {
        String str;
        if (e().length() > 0) {
            str = ".";
        } else {
            str = "";
        }
        return String.format(Locale.getDefault(), "Flurry_Android_%d_%d.%d.%d%s%s", new Object[]{Integer.valueOf(a()), Integer.valueOf(b()), Integer.valueOf(c()), Integer.valueOf(d()), str, e()});
    }
}
