package com.immersion.hapticmediasdk.controllers;

import android.os.Handler;
import android.os.Looper;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.MediaTaskManager;
import com.immersion.hapticmediasdk.utils.Profiler;
import java.util.concurrent.atomic.AtomicInteger;
import rrrrrr.crrrrr;
import rrrrrr.rcrrrr;

public class MediaController {
    private static final String a = "MediaController";
    private static final int b = 1000;
    public static int b04460446ццц0446 = 35;
    public static int b0446ц0446цц0446 = 1;
    public static int bц04460446цц0446 = 2;
    public static int bцц0446цц0446 = 0;
    private static final int c = 200;
    private AtomicInteger d;
    private AtomicInteger e;
    private Handler f;
    private HapticPlaybackThread g;
    private Profiler h;
    private MediaTaskManager i;
    private Runnable j;

    public MediaController(Looper looper, MediaTaskManager mediaTaskManager) {
        try {
            this.d = new AtomicInteger();
            this.e = new AtomicInteger();
            if (((b04460446ццц0446 + b0446ц0446цц0446) * b04460446ццц0446) % bц04460446цц0446 != bцц0446цц0446) {
                b04460446ццц0446 = b044604460446цц0446();
                bцц0446цц0446 = b044604460446цц0446();
            }
            try {
                this.h = new Profiler();
                this.j = new rcrrrr(this);
                this.i = mediaTaskManager;
                this.f = new crrrrr(this, looper);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private int a() {
        this.g.pauseHapticPlayback();
        return 0;
    }

    private void a(int i) {
        this.d.set(i);
        this.i.transitToState(SDKStatus.PAUSED_DUE_TO_BUFFERING);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r5, long r6) {
        /*
        r4 = this;
    L_0x0000:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = r4.g;
        r1 = b04460446ццц0446;
        r2 = b0446ц0446цц0446;
        r1 = r1 + r2;
        r2 = b04460446ццц0446;
        r1 = r1 * r2;
        r2 = bц04460446цц0446;
        r1 = r1 % r2;
        r2 = bцц0446цц0446;
        if (r1 == r2) goto L_0x0021;
    L_0x001a:
        r1 = 10;
        b04460446ццц0446 = r1;
        r1 = 4;
        bцц0446цц0446 = r1;
    L_0x0021:
        r0.playHapticForPlaybackPosition(r5, r6);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.a(int, long):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.os.Message r6) {
        /*
        r5 = this;
        r4 = 0;
        r0 = r6.getData();
        r1 = "haptic_download_exception";
        r0 = r0.getSerializable(r1);
        r0 = (java.lang.Exception) r0;
        r1 = r0 instanceof com.immersion.hapticmediasdk.models.HttpUnsuccessfulException;
        if (r1 == 0) goto L_0x0037;
    L_0x0011:
        r1 = r0;
        r1 = (com.immersion.hapticmediasdk.models.HttpUnsuccessfulException) r1;
        r2 = "MediaController";
        r3 = new java.lang.StringBuilder;
    L_0x0018:
        switch(r4) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0018;
            default: goto L_0x001b;
        };
    L_0x001b:
        switch(r4) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0018;
            default: goto L_0x001e;
        };
    L_0x001e:
        goto L_0x001b;
    L_0x001f:
        r3.<init>();
        r4 = "caught HttpUnsuccessfulExcetion http status code = ";
        r3 = r3.append(r4);
        r1 = r1.getHttpStatusCode();
        r1 = r3.append(r1);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.e(r2, r1);
    L_0x0037:
        r1 = "MediaController";
        r2 = b04460446ццц0446;
        r3 = b0446ц0446цц0446;
        r2 = r2 + r3;
        r3 = b04460446ццц0446;
        r2 = r2 * r3;
        r3 = bц04460446цц0446;
        r2 = r2 % r3;
        r3 = bцц0446цц0446;
        if (r2 == r3) goto L_0x0052;
    L_0x0048:
        r2 = b044604460446цц0446();
        b04460446ццц0446 = r2;
        r2 = 98;
        bцц0446цц0446 = r2;
    L_0x0052:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "HapticDownloadError: ";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.immersion.hapticmediasdk.utils.Log.e(r1, r0);
        r0 = r5.i;
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;
        r0.transitToState(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.a(android.os.Message):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(boolean r7) {
        /*
        r6 = this;
        r2 = 0;
        r0 = r6.g;
        r0 = r0.isStarted();
        r1 = r2;
    L_0x0008:
        if (r7 == 0) goto L_0x0030;
    L_0x000a:
        if (r0 != 0) goto L_0x002c;
    L_0x000c:
        r3 = r6.g;
        monitor-enter(r3);
        r0 = r6.g;	 Catch:{ InterruptedException -> 0x0033 }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0.wait(r4);	 Catch:{ InterruptedException -> 0x0033 }
    L_0x0016:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0016;
            case 1: goto L_0x001e;
            default: goto L_0x001a;
        };
    L_0x001a:
        switch(r2) {
            case 0: goto L_0x001e;
            case 1: goto L_0x0016;
            default: goto L_0x001d;
        };
    L_0x001d:
        goto L_0x001a;
    L_0x001e:
        monitor-exit(r3);	 Catch:{ all -> 0x002d }
        r0 = r6.g;
        r0 = r0.isStarted();
        r1 = r1 + 1;
        if (r7 != 0) goto L_0x0008;
    L_0x0029:
        r3 = 5;
        if (r1 < r3) goto L_0x0008;
    L_0x002c:
        return;
    L_0x002d:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x002d }
        throw r0;
    L_0x0030:
        if (r0 == 0) goto L_0x002c;
    L_0x0032:
        goto L_0x000c;
    L_0x0033:
        r0 = move-exception;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.a(boolean):void");
    }

    public static /* synthetic */ void b043B043Bлллл(MediaController mediaController, int i, long j) {
        int i2 = b04460446ццц0446;
        switch ((i2 * (b0446ц0446цц0446 + i2)) % bц04460446цц0446) {
            case 0:
                break;
            default:
                b04460446ццц0446 = 79;
                bцц0446цц0446 = 74;
                break;
        }
        try {
            mediaController.a(i, j);
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ void b043Bл043Bллл(com.immersion.hapticmediasdk.controllers.MediaController r2, android.os.Message r3) {
        /*
    L_0x0000:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = b04460446ццц0446;
        r1 = b0446ц0446цц0446;
        r0 = r0 + r1;
        r1 = b04460446ццц0446;
        r0 = r0 * r1;
        r1 = bц04460446цц0446;
        r0 = r0 % r1;
        r1 = bц0446ц0446ц0446();
        if (r0 == r1) goto L_0x0021;
    L_0x001a:
        r0 = 31;
        b04460446ццц0446 = r0;
        r0 = 2;
        bцц0446цц0446 = r0;
    L_0x0021:
        r2.a(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.b043Bл043Bллл(com.immersion.hapticmediasdk.controllers.MediaController, android.os.Message):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ java.util.concurrent.atomic.AtomicInteger b043Bллллл(com.immersion.hapticmediasdk.controllers.MediaController r2) {
        /*
    L_0x0000:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = b04460446ццц0446;
        r1 = b0446ц0446цц0446;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bц04460446цц0446;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001d;
            default: goto L_0x0015;
        };
    L_0x0015:
        r0 = 31;
        b04460446ццц0446 = r0;
        r0 = 73;
        bцц0446цц0446 = r0;
    L_0x001d:
        r0 = r2.e;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.b043Bллллл(com.immersion.hapticmediasdk.controllers.MediaController):java.util.concurrent.atomic.AtomicInteger");
    }

    public static int b044604460446цц0446() {
        return 5;
    }

    public static int b04460446ц0446ц0446() {
        return 2;
    }

    public static /* synthetic */ AtomicInteger bБ04110411041104110411(MediaController mediaController) {
        try {
            AtomicInteger atomicInteger = mediaController.d;
            if (((b04460446ццц0446 + b0446ц0446цц0446) * b04460446ццц0446) % bц04460446цц0446 != bц0446ц0446ц0446()) {
                b04460446ццц0446 = 20;
                bцц0446цц0446 = 78;
            }
            return atomicInteger;
        } catch (Exception e) {
            throw e;
        }
    }

    public static /* synthetic */ HapticPlaybackThread bл043B043Bллл(MediaController mediaController) {
        int i = b04460446ццц0446;
        switch ((i * (b0446ц0446цц0446 + i)) % bц04460446цц0446) {
            case 0:
                break;
            default:
                b04460446ццц0446 = 93;
                bцц0446цц0446 = b044604460446цц0446();
                break;
        }
        try {
            return mediaController.g;
        } catch (Exception e) {
            throw e;
        }
    }

    public static /* synthetic */ MediaTaskManager bл043Bлллл(MediaController mediaController) {
        MediaTaskManager mediaTaskManager = mediaController.i;
        if (((b04460446ццц0446 + b0446ц0446цц0446) * b04460446ццц0446) % bц04460446цц0446 != bцц0446цц0446) {
            b04460446ццц0446 = 52;
            bцц0446цц0446 = 73;
        }
        return mediaTaskManager;
    }

    public static int bц0446ц0446ц0446() {
        return 0;
    }

    public Handler getControlHandler() {
        if (((b04460446ццц0446 + b0446ц0446цц0446) * b04460446ццц0446) % bц04460446цц0446 != bцц0446цц0446) {
            b04460446ццц0446 = 97;
            bцц0446цц0446 = 45;
        }
        return this.f;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getCurrentPosition() {
        /*
        r2 = this;
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
        r0 = b04460446ццц0446;
        r1 = b0446ц0446цц0446;
        r0 = r0 + r1;
        r1 = b04460446ццц0446;
        r0 = r0 * r1;
        r1 = bц04460446цц0446;
        r0 = r0 % r1;
        r1 = bцц0446цц0446;
        if (r0 == r1) goto L_0x001f;
    L_0x0017:
        r0 = 51;
        b04460446ццц0446 = r0;
        r0 = 63;
        bцц0446цц0446 = r0;
    L_0x001f:
        r0 = r2.i;
        r0 = r0.getMediaTimestamp();
        r0 = (int) r0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.getCurrentPosition():int");
    }

    public long getReferenceTimeForCurrentPosition() {
        int i = b04460446ццц0446;
        switch ((i * (b0446ц0446цц0446 + i)) % bц04460446цц0446) {
            case 0:
                break;
            default:
                b04460446ццц0446 = b044604460446цц0446();
                bцц0446цц0446 = 38;
                break;
        }
        try {
            return this.i.getMediaReferenceTime();
        } catch (Exception e) {
            throw e;
        }
    }

    public void initHapticPlayback(HapticPlaybackThread hapticPlaybackThread) {
        try {
            this.g = hapticPlaybackThread;
            this.g.start();
            if (((b04460446ццц0446 + b0446ц0446цц0446) * b04460446ццц0446) % bц04460446цц0446 != bцц0446цц0446) {
                b04460446ццц0446 = b044604460446цц0446();
                bцц0446цц0446 = 24;
            }
            try {
                a(true);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public boolean isPlaying() {
        try {
            return this.i.getSDKStatus() == SDKStatus.PLAYING;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDestroy(android.os.Handler r4) {
        /*
        r3 = this;
        r1 = 0;
        r0 = r3.g;
        if (r0 == 0) goto L_0x0017;
    L_0x0005:
        r0 = r3.g;
        r0.quitHapticPlayback();
        r3.a(r1);
    L_0x000d:
        switch(r1) {
            case 0: goto L_0x0014;
            case 1: goto L_0x000d;
            default: goto L_0x0010;
        };
    L_0x0010:
        switch(r1) {
            case 0: goto L_0x0014;
            case 1: goto L_0x000d;
            default: goto L_0x0013;
        };
    L_0x0013:
        goto L_0x0010;
    L_0x0014:
        r0 = 0;
        r3.g = r0;
    L_0x0017:
        r0 = r3.i;
        r1 = b04460446ццц0446;
        r2 = b0446ц0446цц0446;
        r1 = r1 + r2;
        r2 = b04460446ццц0446;
        r1 = r1 * r2;
        r2 = bц04460446цц0446;
        r1 = r1 % r2;
        r2 = bцц0446цц0446;
        if (r1 == r2) goto L_0x0032;
    L_0x0028:
        r1 = b044604460446цц0446();
        b04460446ццц0446 = r1;
        r1 = 29;
        bцц0446цц0446 = r1;
    L_0x0032:
        r4.removeCallbacks(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.onDestroy(android.os.Handler):void");
    }

    public int onPause() {
        int a = a();
        int i = b04460446ццц0446;
        switch ((i * (b0446ц0446цц0446 + i)) % bц04460446цц0446) {
            case 0:
                break;
            default:
                b04460446ццц0446 = 39;
                bцц0446цц0446 = 73;
                break;
        }
        return a;
    }

    public int onPrepared() {
        return prepareHapticPlayback();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void playbackStarted() {
        /*
        r4 = this;
        r1 = 1;
        r0 = r4.g;
        if (r0 == 0) goto L_0x0032;
    L_0x0005:
        switch(r1) {
            case 0: goto L_0x0005;
            case 1: goto L_0x000c;
            default: goto L_0x0008;
        };
    L_0x0008:
        switch(r1) {
            case 0: goto L_0x0005;
            case 1: goto L_0x000c;
            default: goto L_0x000b;
        };
    L_0x000b:
        goto L_0x0008;
    L_0x000c:
        r0 = r4.g;
        r1 = b04460446ццц0446;
        r2 = b0446ц0446цц0446;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bц04460446цц0446;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x0026;
            default: goto L_0x001a;
        };
    L_0x001a:
        r1 = b044604460446цц0446();
        b04460446ццц0446 = r1;
        r1 = b044604460446цц0446();
        bцц0446цц0446 = r1;
    L_0x0026:
        r0 = r0.getHandler();
        r1 = r4.j;
        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0.postDelayed(r1, r2);
    L_0x0031:
        return;
    L_0x0032:
        r0 = "MediaController";
        r1 = "Can't start periodic sync since haptic playback thread stopped.";
        com.immersion.hapticmediasdk.utils.Log.e(r0, r1);
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.playbackStarted():void");
    }

    public int prepareHapticPlayback() {
        try {
            this.h.startTiming();
            try {
                HapticPlaybackThread hapticPlaybackThread = this.g;
                int i = this.d.get();
                int i2 = b04460446ццц0446;
                switch ((i2 * (b0446ц0446цц0446 + i2)) % bц04460446цц0446) {
                    case 0:
                        break;
                    default:
                        b04460446ццц0446 = b044604460446цц0446();
                        bцц0446цц0446 = 98;
                        break;
                }
                hapticPlaybackThread.prepareHapticPlayback(i, this.e.incrementAndGet());
                return 0;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void seekTo(int i) {
        AtomicInteger atomicInteger = this.d;
        if (i <= 0) {
            i = 0;
        }
        atomicInteger.set(i);
        if (this.g != null) {
            Handler handler = this.g.getHandler();
            int i2 = b04460446ццц0446;
            switch ((i2 * (b0446ц0446цц0446 + i2)) % bц04460446цц0446) {
                case 0:
                    break;
                default:
                    b04460446ццц0446 = 66;
                    bцц0446цц0446 = 5;
                    break;
            }
            handler.removeCallbacks(this.j);
            this.g.removePlaybackCallbacks();
        }
    }

    public void setRequestBufferPosition(int i) {
        try {
            AtomicInteger atomicInteger = this.d;
            int b044604460446цц0446 = b044604460446цц0446();
            switch ((b044604460446цц0446 * (b0446ц0446цц0446 + b044604460446цц0446)) % bц04460446цц0446) {
                case 0:
                    break;
                default:
                    b04460446ццц0446 = 8;
                    bцц0446цц0446 = b044604460446цц0446();
                    break;
            }
            atomicInteger.set(i);
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int stopHapticPlayback() {
        /*
        r4 = this;
        r3 = 0;
        r0 = r4.d;
        r0.set(r3);
        r0 = r4.g;
    L_0x0008:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0010;
            default: goto L_0x000c;
        };
    L_0x000c:
        switch(r3) {
            case 0: goto L_0x0010;
            case 1: goto L_0x0008;
            default: goto L_0x000f;
        };
    L_0x000f:
        goto L_0x000c;
    L_0x0010:
        r0.stopHapticPlayback();
        r0 = r4.g;
        r1 = b04460446ццц0446;
        r2 = b0446ц0446цц0446;
        r1 = r1 + r2;
        r2 = b04460446ццц0446;
        r1 = r1 * r2;
        r2 = bц04460446цц0446;
        r1 = r1 % r2;
        r2 = bцц0446цц0446;
        if (r1 == r2) goto L_0x002c;
    L_0x0024:
        r1 = 48;
        b04460446ццц0446 = r1;
        r1 = 63;
        bцц0446цц0446 = r1;
    L_0x002c:
        r0 = r0.getHandler();
        r1 = r4.j;
        r0.removeCallbacks(r1);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.stopHapticPlayback():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitHapticStopped() {
        /*
        r6 = this;
        r1 = 0;
        r0 = r6.g;
        r0 = r0.isStopped();
        r2 = r0;
        r0 = r1;
    L_0x0009:
        if (r2 != 0) goto L_0x002d;
    L_0x000b:
        r2 = 5;
        if (r0 >= r2) goto L_0x002d;
    L_0x000e:
        r2 = r6.g;
        monitor-enter(r2);
        r3 = r6.g;	 Catch:{ InterruptedException -> 0x002e }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r3.wait(r4);	 Catch:{ InterruptedException -> 0x002e }
    L_0x0018:
        monitor-exit(r2);	 Catch:{ all -> 0x0022 }
        r2 = r6.g;
        r2 = r2.isStopped();
        r0 = r0 + 1;
        goto L_0x0009;
    L_0x0022:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0022 }
    L_0x0024:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0024;
            case 1: goto L_0x002c;
            default: goto L_0x0028;
        };
    L_0x0028:
        switch(r1) {
            case 0: goto L_0x002c;
            case 1: goto L_0x0024;
            default: goto L_0x002b;
        };
    L_0x002b:
        goto L_0x0028;
    L_0x002c:
        throw r0;
    L_0x002d:
        return;
    L_0x002e:
        r3 = move-exception;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.waitHapticStopped():void");
    }
}
