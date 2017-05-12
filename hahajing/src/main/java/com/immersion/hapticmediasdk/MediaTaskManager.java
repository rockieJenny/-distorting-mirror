package com.immersion.hapticmediasdk;

import android.content.Context;
import android.os.Handler;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.controllers.HapticPlaybackThread;
import com.immersion.hapticmediasdk.controllers.MediaController;
import com.immersion.hapticmediasdk.utils.Log;
import com.immersion.hapticmediasdk.utils.RuntimeInfo;

public class MediaTaskManager implements Runnable {
    private static final String a = "MediaTaskManager";
    public static int b04150415ЕЕЕЕ = 2;
    public static int bЕ04150415ЕЕЕ = 0;
    public static int bЕ0415ЕЕЕЕ = 1;
    public static int bС04210421042104210421 = 37;
    private final Object b;
    private final Object c;
    private long d;
    private long e;
    private Handler f;
    private volatile SDKStatus g;
    private MediaController h;
    private String i;
    private boolean j;
    private Context k;
    private RuntimeInfo l;

    public MediaTaskManager(Handler handler, Context context, RuntimeInfo runtimeInfo) {
        if (((bС04210421042104210421 + bЕ0415ЕЕЕЕ) * bС04210421042104210421) % b04150415ЕЕЕЕ != b0415ЕЕЕЕЕ()) {
            bС04210421042104210421 = 47;
            bЕ0415ЕЕЕЕ = bЕЕ0415ЕЕЕ();
        }
        try {
            this.b = new Object();
            try {
                this.c = new Object();
                this.g = SDKStatus.NOT_INITIALIZED;
                this.f = handler;
                this.k = context;
                this.l = runtimeInfo;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private int a() {
        try {
            this.f.removeCallbacks(this);
            if (!(this.h == null || d() == 0)) {
                Log.e(a, "Could not dispose haptics, reset anyway.");
            }
            try {
                this.i = null;
                this.d = 0;
                this.g = SDKStatus.NOT_INITIALIZED;
                int i = bС04210421042104210421;
                switch ((i * (b0415Е0415ЕЕЕ() + i)) % b04150415ЕЕЕЕ) {
                    case 0:
                        break;
                    default:
                        bС04210421042104210421 = bЕЕ0415ЕЕЕ();
                        bЕ0415ЕЕЕЕ = 55;
                        break;
                }
                return 0;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private int a(SDKStatus sDKStatus) {
        int i = bС04210421042104210421;
        switch ((i * (bЕ0415ЕЕЕЕ + i)) % b04150415ЕЕЕЕ) {
            case 0:
                break;
            default:
                bС04210421042104210421 = 19;
                bЕ0415ЕЕЕЕ = bЕЕ0415ЕЕЕ();
                break;
        }
        try {
            try {
                this.f.removeCallbacks(this);
                this.g = sDKStatus;
                if (this.i == null) {
                    return -4;
                }
                this.h = new MediaController(this.f.getLooper(), this);
                Handler controlHandler = this.h.getControlHandler();
                this.h.initHapticPlayback(new HapticPlaybackThread(this.k, this.i, controlHandler, this.j, this.l));
                return 0;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private int b() {
        this.f.removeCallbacks(this);
        int onPrepared = this.h.onPrepared();
        if (onPrepared == 0) {
            this.g = SDKStatus.PLAYING;
            Handler handler = this.f;
            int i = bС04210421042104210421;
            switch ((i * (bЕ0415ЕЕЕЕ + i)) % b04150415ЕЕЕЕ) {
                case 0:
                    break;
                default:
                    bС04210421042104210421 = bЕЕ0415ЕЕЕ();
                    bЕ04150415ЕЕЕ = 68;
                    break;
            }
            handler.postDelayed(this, 1500);
        }
        return onPrepared;
    }

    public static int b0415Е0415ЕЕЕ() {
        return 1;
    }

    public static int b0415ЕЕЕЕЕ() {
        return 0;
    }

    public static int bЕЕ0415ЕЕЕ() {
        return 54;
    }

    private int c() {
        try {
            this.f.removeCallbacks(this);
            this.d = 0;
            if (((bС04210421042104210421 + bЕ0415ЕЕЕЕ) * bС04210421042104210421) % b04150415ЕЕЕЕ != bЕ04150415ЕЕЕ) {
                bС04210421042104210421 = bЕЕ0415ЕЕЕ();
                bЕ04150415ЕЕЕ = bЕЕ0415ЕЕЕ();
            }
            try {
                int stopHapticPlayback = this.h.stopHapticPlayback();
                if (stopHapticPlayback == 0) {
                    this.g = SDKStatus.STOPPED;
                }
                return stopHapticPlayback;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int d() {
        /*
        r3 = this;
        r0 = r3.c();
        if (r0 != 0) goto L_0x0030;
    L_0x0006:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0006;
            default: goto L_0x000a;
        };
    L_0x000a:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0006;
            case 1: goto L_0x000f;
            default: goto L_0x000e;
        };
    L_0x000e:
        goto L_0x000a;
    L_0x000f:
        r1 = r3.h;
        r2 = r3.f;
        r1.onDestroy(r2);
        r1 = bС04210421042104210421;
        r2 = bЕ0415ЕЕЕЕ;
        r1 = r1 + r2;
        r2 = bС04210421042104210421;
        r1 = r1 * r2;
        r2 = b04150415ЕЕЕЕ;
        r1 = r1 % r2;
        r2 = bЕ04150415ЕЕЕ;
        if (r1 == r2) goto L_0x002d;
    L_0x0025:
        r1 = 80;
        bС04210421042104210421 = r1;
        r1 = 44;
        bЕ04150415ЕЕЕ = r1;
    L_0x002d:
        r1 = 0;
        r3.h = r1;
    L_0x0030:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.d():int");
    }

    private int e() {
        int i = 2;
        try {
            this.f.removeCallbacks(this);
            try {
                int onPause = this.h.onPause();
                if (onPause == 0) {
                    while (true) {
                        try {
                            i /= 0;
                        } catch (Exception e) {
                            bС04210421042104210421 = bЕЕ0415ЕЕЕ();
                            this.g = SDKStatus.PAUSED;
                        }
                    }
                }
                return onPause;
            } catch (Exception e2) {
                throw e2;
            }
        } catch (Exception e22) {
            throw e22;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int f() {
        /*
        r4 = this;
        r0 = 0;
        r1 = r4.f;
        r1.removeCallbacks(r4);
        r1 = r4.f;
        r2 = 1500; // 0x5dc float:2.102E-42 double:7.41E-321;
        r1 = r1.postDelayed(r4, r2);
        if (r1 == 0) goto L_0x0034;
    L_0x0010:
        switch(r0) {
            case 0: goto L_0x0018;
            case 1: goto L_0x0010;
            default: goto L_0x0013;
        };
    L_0x0013:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0010;
            case 1: goto L_0x0018;
            default: goto L_0x0017;
        };
    L_0x0017:
        goto L_0x0013;
    L_0x0018:
        r1 = bЕЕ0415ЕЕЕ();
        r2 = bЕ0415ЕЕЕЕ;
        r1 = r1 + r2;
        r2 = bЕЕ0415ЕЕЕ();
        r1 = r1 * r2;
        r2 = b04150415ЕЕЕЕ;
        r1 = r1 % r2;
        r2 = bЕ04150415ЕЕЕ;
        if (r1 == r2) goto L_0x0033;
    L_0x002b:
        r1 = 70;
        bС04210421042104210421 = r1;
        r1 = 50;
        bЕ04150415ЕЕЕ = r1;
    L_0x0033:
        return r0;
    L_0x0034:
        r0 = -1;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.f():int");
    }

    private int g() {
        try {
            int onPause = this.h.onPause();
            if (onPause == 0) {
                if (((bС04210421042104210421 + bЕ0415ЕЕЕЕ) * bС04210421042104210421) % b04150415ЕЕЕЕ != b0415ЕЕЕЕЕ()) {
                    bС04210421042104210421 = 64;
                    bЕ04150415ЕЕЕ = 32;
                }
                this.g = SDKStatus.PAUSED_DUE_TO_TIMEOUT;
            }
            return onPause;
        } catch (Exception e) {
            throw e;
        }
    }

    private int h() {
        try {
            int onPause = this.h.onPause();
            if (onPause == 0) {
                this.g = SDKStatus.PAUSED_DUE_TO_BUFFERING;
            }
            if (((bС04210421042104210421 + bЕ0415ЕЕЕЕ) * bС04210421042104210421) % b04150415ЕЕЕЕ != bЕ04150415ЕЕЕ) {
                bС04210421042104210421 = 29;
                bЕ04150415ЕЕЕ = bЕЕ0415ЕЕЕ();
            }
            return onPause;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int i() {
        /*
        r2 = this;
        r0 = bС04210421042104210421;
        r1 = bЕ0415ЕЕЕЕ;
        r0 = r0 + r1;
        r1 = bС04210421042104210421;
        r0 = r0 * r1;
        r1 = b04150415ЕЕЕЕ;
        r0 = r0 % r1;
        r1 = bЕ04150415ЕЕЕ;
        if (r0 == r1) goto L_0x0017;
    L_0x000f:
        r0 = 78;
        bС04210421042104210421 = r0;
        r0 = 14;
        bЕ04150415ЕЕЕ = r0;
    L_0x0017:
        r0 = r2.b();
        if (r0 != 0) goto L_0x0021;
    L_0x001d:
        r0 = r2.f();
    L_0x0021:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x002a;
            case 1: goto L_0x0021;
            default: goto L_0x0025;
        };
    L_0x0025:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0021;
            case 1: goto L_0x002a;
            default: goto L_0x0029;
        };
    L_0x0029:
        goto L_0x0025;
    L_0x002a:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.i():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int SeekTo(int r4) {
        /*
        r3 = this;
        r2 = 1;
        r0 = bЕЕ0415ЕЕЕ();
        r1 = b0415Е0415ЕЕЕ();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b04150415ЕЕЕЕ;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0018;
            default: goto L_0x0011;
        };
    L_0x0011:
        r0 = 10;
        bС04210421042104210421 = r0;
        r0 = 6;
        bЕ0415ЕЕЕЕ = r0;
    L_0x0018:
        r0 = (long) r4;
        r3.setMediaTimestamp(r0);
        r0 = r3.h;
        r0.seekTo(r4);
        r0 = r3.getSDKStatus();
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;
        if (r0 != r1) goto L_0x0037;
    L_0x0029:
        switch(r2) {
            case 0: goto L_0x0029;
            case 1: goto L_0x0030;
            default: goto L_0x002c;
        };
    L_0x002c:
        switch(r2) {
            case 0: goto L_0x0029;
            case 1: goto L_0x0030;
            default: goto L_0x002f;
        };
    L_0x002f:
        goto L_0x002c;
    L_0x0030:
        r0 = r3.h;
        r0 = r0.prepareHapticPlayback();
    L_0x0036:
        return r0;
    L_0x0037:
        r0 = 0;
        goto L_0x0036;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.SeekTo(int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getMediaReferenceTime() {
        /*
        r4 = this;
        r1 = r4.c;
        monitor-enter(r1);
        r2 = r4.e;	 Catch:{ all -> 0x0007 }
        monitor-exit(r1);	 Catch:{ all -> 0x0007 }
        return r2;
    L_0x0007:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0007 }
    L_0x0009:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0012;
            case 1: goto L_0x0009;
            default: goto L_0x000d;
        };
    L_0x000d:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0012;
            default: goto L_0x0011;
        };
    L_0x0011:
        goto L_0x000d;
    L_0x0012:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.getMediaReferenceTime():long");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getMediaTimestamp() {
        /*
        r4 = this;
        r1 = r4.c;
        monitor-enter(r1);
        r2 = r4.d;	 Catch:{ all -> 0x0007 }
        monitor-exit(r1);	 Catch:{ all -> 0x0007 }
        return r2;
    L_0x0007:
        r0 = move-exception;
    L_0x0008:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0011;
            default: goto L_0x000c;
        };	 Catch:{ all -> 0x0007 }
    L_0x000c:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x0011;
            case 1: goto L_0x0008;
            default: goto L_0x0010;
        };	 Catch:{ all -> 0x0007 }
    L_0x0010:
        goto L_0x000c;
    L_0x0011:
        monitor-exit(r1);	 Catch:{ all -> 0x0007 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.getMediaTimestamp():long");
    }

    public SDKStatus getSDKStatus() {
        SDKStatus sDKStatus;
        synchronized (this.b) {
            sDKStatus = this.g;
        }
        return sDKStatus;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r3 = this;
        r2 = 1;
        java.lang.System.currentTimeMillis();
        r0 = bС04210421042104210421;
        r1 = bЕ0415ЕЕЕЕ;
        r0 = r0 + r1;
        r1 = bС04210421042104210421;
        r0 = r0 * r1;
        r1 = b04150415ЕЕЕЕ;
        r0 = r0 % r1;
        r1 = bЕ04150415ЕЕЕ;
        if (r0 == r1) goto L_0x001b;
    L_0x0013:
        r0 = 91;
        bС04210421042104210421 = r0;
        r0 = 30;
        bЕ04150415ЕЕЕ = r0;
    L_0x001b:
        switch(r2) {
            case 0: goto L_0x001b;
            case 1: goto L_0x0022;
            default: goto L_0x001e;
        };
    L_0x001e:
        switch(r2) {
            case 0: goto L_0x001b;
            case 1: goto L_0x0022;
            default: goto L_0x0021;
        };
    L_0x0021:
        goto L_0x001e;
    L_0x0022:
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_TIMEOUT;
        r3.transitToState(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.run():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHapticsUrl(java.lang.String r4, boolean r5) {
        /*
        r3 = this;
        r2 = 0;
        r1 = r3.b;
        monitor-enter(r1);
        r3.i = r4;	 Catch:{ all -> 0x000a }
        r3.j = r5;	 Catch:{ all -> 0x000a }
        monitor-exit(r1);	 Catch:{ all -> 0x000a }
        return;
    L_0x000a:
        r0 = move-exception;
    L_0x000b:
        switch(r2) {
            case 0: goto L_0x0012;
            case 1: goto L_0x000b;
            default: goto L_0x000e;
        };	 Catch:{ all -> 0x000a }
    L_0x000e:
        switch(r2) {
            case 0: goto L_0x0012;
            case 1: goto L_0x000b;
            default: goto L_0x0011;
        };	 Catch:{ all -> 0x000a }
    L_0x0011:
        goto L_0x000e;
    L_0x0012:
        monitor-exit(r1);	 Catch:{ all -> 0x000a }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.setHapticsUrl(java.lang.String, boolean):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setMediaReferenceTime() {
        /*
        r4 = this;
        r1 = r4.c;
        monitor-enter(r1);
        r0 = r4.g;	 Catch:{ all -> 0x001f }
    L_0x0005:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0005;
            case 1: goto L_0x000e;
            default: goto L_0x0009;
        };	 Catch:{ all -> 0x001f }
    L_0x0009:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0005;
            default: goto L_0x000d;
        };	 Catch:{ all -> 0x001f }
    L_0x000d:
        goto L_0x0009;
    L_0x000e:
        r2 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001f }
        if (r0 != r2) goto L_0x0017;
    L_0x0012:
        r0 = r4.h;	 Catch:{ all -> 0x001f }
        r0.waitHapticStopped();	 Catch:{ all -> 0x001f }
    L_0x0017:
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x001f }
        r4.e = r2;	 Catch:{ all -> 0x001f }
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        return;
    L_0x001f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.setMediaReferenceTime():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setMediaTimestamp(long r6) {
        /*
        r5 = this;
        r4 = 1;
        r1 = r5.c;
        monitor-enter(r1);
        r0 = r5.g;	 Catch:{ all -> 0x0020 }
        r2 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x0020 }
        if (r0 != r2) goto L_0x000f;
    L_0x000a:
        r0 = r5.h;	 Catch:{ all -> 0x0020 }
        r0.waitHapticStopped();	 Catch:{ all -> 0x0020 }
    L_0x000f:
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x0020 }
        r5.e = r2;	 Catch:{ all -> 0x0020 }
    L_0x0015:
        switch(r4) {
            case 0: goto L_0x0015;
            case 1: goto L_0x001c;
            default: goto L_0x0018;
        };	 Catch:{ all -> 0x0020 }
    L_0x0018:
        switch(r4) {
            case 0: goto L_0x0015;
            case 1: goto L_0x001c;
            default: goto L_0x001b;
        };	 Catch:{ all -> 0x0020 }
    L_0x001b:
        goto L_0x0018;
    L_0x001c:
        r5.d = r6;	 Catch:{ all -> 0x0020 }
        monitor-exit(r1);	 Catch:{ all -> 0x0020 }
        return;
    L_0x0020:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0020 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.setMediaTimestamp(long):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int transitToState(com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus r7) {
        /*
        r6 = this;
        r1 = 0;
        r0 = -1;
        r2 = r6.b;
        monitor-enter(r2);
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.NOT_INITIALIZED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x000f;
    L_0x0009:
        r0 = r6.a();	 Catch:{ all -> 0x001e }
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
    L_0x000e:
        return r0;
    L_0x000f:
        r3 = rrrrrr.crccrr.b042504250425ХХ0425;	 Catch:{ all -> 0x001e }
        r4 = r6.g;	 Catch:{ all -> 0x001e }
        r4 = r4.ordinal();	 Catch:{ all -> 0x001e }
        r3 = r3[r4];	 Catch:{ all -> 0x001e }
        switch(r3) {
            case 1: goto L_0x0021;
            case 2: goto L_0x002a;
            case 3: goto L_0x0067;
            case 4: goto L_0x0091;
            case 5: goto L_0x00dd;
            case 6: goto L_0x00f2;
            case 7: goto L_0x0119;
            default: goto L_0x001c;
        };	 Catch:{ all -> 0x001e }
    L_0x001c:
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
        goto L_0x000e;
    L_0x001e:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
        throw r0;
    L_0x0021:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.INITIALIZED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x0025:
        r0 = r6.a(r7);	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x002a:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x0051;
    L_0x002e:
        r0 = r6.i();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0033:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x0037:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.g = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0040:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00f9;
    L_0x0044:
        r0 = r6.h;	 Catch:{ all -> 0x001e }
        r4 = r6.d;	 Catch:{ all -> 0x001e }
        r1 = (int) r4;	 Catch:{ all -> 0x001e }
        r0.setRequestBufferPosition(r1);	 Catch:{ all -> 0x001e }
        r0 = r6.i();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0051:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x005a;
    L_0x0055:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x005a:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x005e:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.g = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0067:
        r3 = 1;
        switch(r3) {
            case 0: goto L_0x0067;
            case 1: goto L_0x006f;
            default: goto L_0x006b;
        };	 Catch:{ all -> 0x001e }
    L_0x006b:
        switch(r1) {
            case 0: goto L_0x006f;
            case 1: goto L_0x0067;
            default: goto L_0x006e;
        };	 Catch:{ all -> 0x001e }
    L_0x006e:
        goto L_0x006b;
    L_0x006f:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x0078;
    L_0x0073:
        r0 = r6.f();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0078:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x0081;
    L_0x007c:
        r0 = r6.e();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0081:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_TIMEOUT;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x00aa;
    L_0x0085:
        r0 = "MediaTaskManager";
        r1 = "Haptic playback is paused due to update time-out. Call update() to resume playback";
        com.immersion.hapticmediasdk.utils.Log.w(r0, r1);	 Catch:{ all -> 0x001e }
        r0 = r6.g();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0091:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00a3;
    L_0x0095:
        r0 = r6.h;	 Catch:{ all -> 0x001e }
        r4 = r6.d;	 Catch:{ all -> 0x001e }
        r1 = (int) r4;	 Catch:{ all -> 0x001e }
        r0.setRequestBufferPosition(r1);	 Catch:{ all -> 0x001e }
        r0 = r6.i();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00a3:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00c5;
    L_0x00a7:
        r0 = r1;
        goto L_0x001c;
    L_0x00aa:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_BUFFERING;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x00bb;
    L_0x00ae:
        r0 = r6.h();	 Catch:{ all -> 0x001e }
        r1 = "MediaTaskManager";
        r3 = "Haptic playback is paused due to slow data buffering...";
        com.immersion.hapticmediasdk.utils.Log.w(r1, r3);	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00bb:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x0033;
    L_0x00bf:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00c5:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x00cf;
    L_0x00c9:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00cf:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x00d3:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.g = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00dd:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_TIMEOUT;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x0040;
    L_0x00e1:
        r0 = r1;
        goto L_0x001c;
    L_0x00e4:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x00e8:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.g = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00f2:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_BUFFERING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x0123;
    L_0x00f6:
        r0 = r1;
        goto L_0x001c;
    L_0x00f9:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x0104;
    L_0x00fd:
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        r6.g = r0;	 Catch:{ all -> 0x001e }
        r0 = r1;
        goto L_0x001c;
    L_0x0104:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x00e4;
    L_0x0108:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x010e:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x0135;
    L_0x0112:
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        r6.g = r0;	 Catch:{ all -> 0x001e }
        r0 = r1;
        goto L_0x001c;
    L_0x0119:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x014d;
    L_0x011d:
        r0 = r6.i();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0123:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x010e;
    L_0x0127:
        r0 = r6.h;	 Catch:{ all -> 0x001e }
        r4 = r6.d;	 Catch:{ all -> 0x001e }
        r1 = (int) r4;	 Catch:{ all -> 0x001e }
        r0.setRequestBufferPosition(r1);	 Catch:{ all -> 0x001e }
        r0 = r6.i();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0135:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x013f;
    L_0x0139:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x013f:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x0143:
        r0 = r6.c();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.g = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x014d:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x001c;
    L_0x0151:
        r0 = r1;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.transitToState(com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus):int");
    }
}
