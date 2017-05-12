package com.immersion.hapticmediasdk.utils;

public class RuntimeInfo {
    public static int b0415Е0415ЕЕ0415 = 1;
    public static int bЕ04150415ЕЕ0415 = 2;
    public static int bЕЕ0415ЕЕ0415 = 88;
    public static int bЕЕЕ0415Е0415;
    private boolean a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public RuntimeInfo() {
        /*
        r3 = this;
        r2 = 1;
        r0 = bЕЕ0415ЕЕ0415;
        r1 = b0415Е0415ЕЕ0415;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЕ04150415ЕЕ0415;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0019;
            default: goto L_0x000d;
        };
    L_0x000d:
        r0 = b041504150415ЕЕ0415();
        bЕЕ0415ЕЕ0415 = r0;
        r0 = b041504150415ЕЕ0415();
        b0415Е0415ЕЕ0415 = r0;
    L_0x0019:
        r3.<init>();
    L_0x001c:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0024;
            case 1: goto L_0x001c;
            default: goto L_0x0020;
        };
    L_0x0020:
        switch(r2) {
            case 0: goto L_0x001c;
            case 1: goto L_0x0024;
            default: goto L_0x0023;
        };
    L_0x0023:
        goto L_0x0020;
    L_0x0024:
        r3.a = r2;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.RuntimeInfo.<init>():void");
    }

    public static int b041504150415ЕЕ0415() {
        return 53;
    }

    public synchronized boolean areHapticsEnabled() {
        boolean z;
        try {
            z = this.a;
            if (((bЕЕ0415ЕЕ0415 + b0415Е0415ЕЕ0415) * bЕЕ0415ЕЕ0415) % bЕ04150415ЕЕ0415 != bЕЕЕ0415Е0415) {
                bЕЕ0415ЕЕ0415 = 88;
                bЕЕЕ0415Е0415 = 88;
            }
        } catch (Exception e) {
            throw e;
        }
        return z;
    }

    public synchronized void mute() {
        if (((bЕЕ0415ЕЕ0415 + b0415Е0415ЕЕ0415) * bЕЕ0415ЕЕ0415) % bЕ04150415ЕЕ0415 != bЕЕЕ0415Е0415) {
            bЕЕ0415ЕЕ0415 = b041504150415ЕЕ0415();
            bЕЕЕ0415Е0415 = b041504150415ЕЕ0415();
        }
        try {
            this.a = false;
        } catch (Exception e) {
            throw e;
        }
        return;
    }

    public synchronized void unmute() {
        this.a = true;
    }
}
