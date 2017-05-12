package com.flurry.sdk;

public class hu {
    private static final String a = hu.class.getSimpleName();
    private static boolean b;

    public static synchronized void a() {
        synchronized (hu.class) {
            if (!b) {
                gf.a(fd.class, 10);
                try {
                    gf.a(eq.class, 10);
                } catch (NoClassDefFoundError e) {
                    gd.a(3, a, "Analytics module not available");
                }
                try {
                    gf.a(hs.class, 10);
                } catch (NoClassDefFoundError e2) {
                    gd.a(3, a, "Crash module not available");
                }
                try {
                    gf.a(i.class, 10);
                } catch (NoClassDefFoundError e3) {
                    gd.a(3, a, "Ads module not available");
                }
                b = true;
            }
        }
    }
}
