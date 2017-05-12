package com.immersion.hapticmediasdk.utils;

public class Log {
    private static final boolean a = false;
    public static int b044A044Aъъъ044A = 48;
    public static int b044Aъ044Aъъ044A = 1;
    public static int bъъ044Aъъ044A = 0;
    public static int bъъъ044Aъ044A = 2;

    public Log() {
        if (((b044A044Aъъъ044A + b044Aъ044Aъъ044A) * b044A044Aъъъ044A) % bъ044A044Aъъ044A() != bъъ044Aъъ044A) {
            b044A044Aъъъ044A = 0;
            bъъ044Aъъ044A = b044A044A044Aъъ044A();
        }
    }

    public static int b044A044A044Aъъ044A() {
        return 1;
    }

    public static int b044Aъъ044Aъ044A() {
        return 1;
    }

    public static int bъ044A044Aъъ044A() {
        return 2;
    }

    public static void d(String str, String str2) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void e(java.lang.String r2, java.lang.String r3) {
        /*
        android.util.Log.e(r2, r3);
    L_0x0003:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000c;
            default: goto L_0x0007;
        };
    L_0x0007:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x000c;
            case 1: goto L_0x0003;
            default: goto L_0x000b;
        };
    L_0x000b:
        goto L_0x0007;
    L_0x000c:
        r0 = b044A044A044Aъъ044A();
        r1 = b044Aъ044Aъъ044A;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bъъъ044Aъ044A;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0024;
            default: goto L_0x001a;
        };
    L_0x001a:
        r0 = b044A044A044Aъъ044A();
        b044A044Aъъъ044A = r0;
        r0 = 56;
        bъъ044Aъъ044A = r0;
    L_0x0024:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.Log.e(java.lang.String, java.lang.String):void");
    }

    public static void i(String str, String str2) {
        int i = b044A044Aъъъ044A;
        switch ((i * (b044Aъъ044Aъ044A() + i)) % bъъъ044Aъ044A) {
            case 0:
                break;
            default:
                b044A044Aъъъ044A = 75;
                bъъ044Aъъ044A = 9;
                break;
        }
        android.util.Log.i(str, str2);
    }

    public static void v(String str, String str2) {
        while (true) {
            switch (1) {
                case null:
                    break;
                case 1:
                    return;
                default:
                    while (true) {
                        switch (1) {
                            case null:
                                break;
                            case 1:
                                return;
                            default:
                        }
                    }
            }
        }
    }

    public static void w(String str, String str2) {
        android.util.Log.w(str, str2);
    }
}
