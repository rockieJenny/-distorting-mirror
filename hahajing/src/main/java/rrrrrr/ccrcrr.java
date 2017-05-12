package rrrrrr;

import com.immersion.hapticmediasdk.controllers.HapticPlaybackThread;

public class ccrcrr implements Runnable {
    public static int b044A044Aъъ044A044A = 2;
    public static int b044Aъъъ044A044A = 45;
    public static int bъ044Aъъ044A044A = 1;
    private final byte[] a;
    private final long b;
    public final /* synthetic */ HapticPlaybackThread bХ0425ХХХ0425;
    private final long c;
    private final int d;
    private final long e;

    public ccrcrr(HapticPlaybackThread hapticPlaybackThread, long j, long j2, byte[] bArr, int i, long j3) {
        try {
            this.bХ0425ХХХ0425 = hapticPlaybackThread;
            this.a = bArr;
            this.b = j;
            int i2 = b044Aъъъ044A044A;
            switch ((i2 * (bъ044Aъъ044A044A + i2)) % b044A044Aъъ044A044A) {
                case 0:
                    break;
                default:
                    b044Aъъъ044A044A = 15;
                    bъ044Aъъ044A044A = bъъ044Aъ044A044A();
                    break;
            }
            try {
                this.c = j2;
                this.d = i;
                this.e = j3;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int bъъ044Aъ044A044A() {
        return 32;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r8 = this;
        r0 = r8.bХ0425ХХХ0425;
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04110411Б0411Б0411(r0);
        if (r0 == 0) goto L_0x007f;
    L_0x0008:
        r0 = r8.bХ0425ХХХ0425;
        r1 = r0.o;
        monitor-enter(r1);
        r0 = r8.bХ0425ХХХ0425;	 Catch:{ all -> 0x0083 }
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411Б04110411Б0411(r0);	 Catch:{ all -> 0x0083 }
        r0.remove(r8);	 Catch:{ all -> 0x0083 }
        monitor-exit(r1);	 Catch:{ all -> 0x0083 }
        r0 = r8.b;
        r2 = r8.c;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 < 0) goto L_0x0070;
    L_0x0021:
        r0 = r8.bХ0425ХХХ0425;
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bБ041104110411Б0411(r0);
        r0 = r0.areHapticsEnabled();
        if (r0 == 0) goto L_0x0049;
    L_0x002d:
        r0 = r8.bХ0425ХХХ0425;
        r1 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411041104110411Б0411(r0);
    L_0x0033:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0033;
            case 1: goto L_0x003c;
            default: goto L_0x0037;
        };
    L_0x0037:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x003c;
            case 1: goto L_0x0033;
            default: goto L_0x003b;
        };
    L_0x003b:
        goto L_0x0037;
    L_0x003c:
        r2 = r8.a;
        r0 = r8.a;
        r3 = r0.length;
        r4 = r8.e;
        r0 = r8.d;
        r6 = (long) r0;
        r1.update(r2, r3, r4, r6);
    L_0x0049:
        r0 = r8.bХ0425ХХХ0425;
        r1 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bББББ04110411(r0);
        monitor-enter(r1);
        r0 = r8.bХ0425ХХХ0425;	 Catch:{ all -> 0x0080 }
        r2 = r8.bХ0425ХХХ0425;	 Catch:{ all -> 0x0080 }
        r2 = r2.e;	 Catch:{ all -> 0x0080 }
        com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411БББ04110411(r0, r2);	 Catch:{ all -> 0x0080 }
        r0 = r8.bХ0425ХХХ0425;	 Catch:{ all -> 0x0080 }
        r2 = r8.bХ0425ХХХ0425;	 Catch:{ all -> 0x0080 }
        r2 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04110411ББ04110411(r2);	 Catch:{ all -> 0x0080 }
        com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bБ0411ББ04110411(r0, r2);	 Catch:{ all -> 0x0080 }
        r0 = r8.bХ0425ХХХ0425;	 Catch:{ all -> 0x0080 }
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x0080 }
        com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bББ0411Б04110411(r0, r2);	 Catch:{ all -> 0x0080 }
        monitor-exit(r1);	 Catch:{ all -> 0x0080 }
    L_0x0070:
        r0 = r8.bХ0425ХХХ0425;
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bБ0411Б04110411Б(r0);
        r1 = r8.bХ0425ХХХ0425;
        r1 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411Б0411Б04110411(r1);
        r0.post(r1);
    L_0x007f:
        return;
    L_0x0080:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0080 }
        throw r0;
    L_0x0083:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0083 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrcrr.run():void");
    }
}
