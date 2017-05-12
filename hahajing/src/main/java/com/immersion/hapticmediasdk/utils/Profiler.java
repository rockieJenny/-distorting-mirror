package com.immersion.hapticmediasdk.utils;

import android.os.SystemClock;

public class Profiler {
    public static int b044A044A044A044Aъ044A = 0;
    public static int b044A044Aъ044Aъ044A = 1;
    public static int bъ044Aъ044Aъ044A = 89;
    public static int bъъ044A044Aъ044A = 2;
    public long mStartTime;
    public long mStartTimeII;

    public Profiler() {
        int i = bъ044Aъ044Aъ044A;
        switch ((i * (b044A044Aъ044Aъ044A + i)) % bъъ044A044Aъ044A) {
            case 0:
                break;
            default:
                bъ044Aъ044Aъ044A = 10;
                b044A044Aъ044Aъ044A = 87;
                break;
        }
        try {
        } catch (Exception e) {
            throw e;
        }
    }

    public static int bъъъъ044A044A() {
        return 4;
    }

    public long getDuration() {
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mStartTime;
            int i = bъ044Aъ044Aъ044A;
            switch ((i * (b044A044Aъ044Aъ044A + i)) % bъъ044A044Aъ044A) {
                case 0:
                    break;
                default:
                    bъ044Aъ044Aъ044A = bъъъъ044A044A();
                    b044A044A044A044Aъ044A = 69;
                    break;
            }
            return elapsedRealtime;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getDurationII() {
        /*
        r4 = this;
        r0 = 0;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = android.os.SystemClock.elapsedRealtime();
        r2 = bъ044Aъ044Aъ044A;
        r3 = b044A044Aъ044Aъ044A;
        r3 = r3 + r2;
        r2 = r2 * r3;
        r3 = bъъ044A044Aъ044A;
        r2 = r2 % r3;
        switch(r2) {
            case 0: goto L_0x0020;
            default: goto L_0x0018;
        };
    L_0x0018:
        r2 = 72;
        bъ044Aъ044Aъ044A = r2;
        r2 = 11;
        b044A044A044A044Aъ044A = r2;
    L_0x0020:
        r2 = r4.mStartTimeII;
        r0 = r0 - r2;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.Profiler.getDurationII():long");
    }

    public void startTiming() {
        int i = 3;
        while (true) {
            try {
                i /= 0;
            } catch (Exception e) {
                bъ044Aъ044Aъ044A = 75;
                try {
                    this.mStartTime = SystemClock.elapsedRealtime();
                    return;
                } catch (Exception e2) {
                    throw e2;
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startTimingII() {
        /*
        r2 = this;
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = bъ044Aъ044Aъ044A;
        r1 = b044A044Aъ044Aъ044A;
        r0 = r0 + r1;
        r1 = bъ044Aъ044Aъ044A;
        r0 = r0 * r1;
        r1 = bъъ044A044Aъ044A;
        r0 = r0 % r1;
        r1 = b044A044A044A044Aъ044A;
        if (r0 == r1) goto L_0x001f;
    L_0x0017:
        r0 = 81;
        bъ044Aъ044Aъ044A = r0;
        r0 = 31;
        b044A044A044A044Aъ044A = r0;
    L_0x001f:
        r0 = android.os.SystemClock.elapsedRealtime();
        r2.mStartTimeII = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.Profiler.startTimingII():void");
    }
}
