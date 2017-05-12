package com.immersion.hapticmediasdk;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.immersion.hapticmediasdk.utils.Log;
import com.immersion.hapticmediasdk.utils.RuntimeInfo;

public abstract class HapticContentSDK {
    public static final int INACCESSIBLE_URL = -2;
    public static final int INVALID = -1;
    public static final int MALFORMED_URL = -4;
    public static final int PERMISSION_DENIED = -3;
    public static final int SDKMODE_MEDIAPLAYBACK = 0;
    public static final int SUCCESS = 0;
    public static final int UNSUPPORTED_PROTOCOL = -5;
    private static final String a = "HapticContentSDK";
    public static final int b044404440444ф04440444 = 10000;
    public static int b044604460446ццц = 25;
    public static int b0446цц0446цц = 1;
    public static final int bф04440444ф04440444 = 1500;
    public static int bц0446ц0446цц = 2;
    public static int bццц0446цц;
    private HandlerThread b;
    private Handler c;
    private Context d;
    private RuntimeInfo e;
    public boolean mDisposed = false;
    public MediaTaskManager mMediaTaskManager;
    public SDKStatus mSDKStatus = SDKStatus.NOT_INITIALIZED;

    public enum SDKStatus {
        NOT_INITIALIZED,
        INITIALIZED,
        PLAYING,
        STOPPED,
        STOPPED_DUE_TO_ERROR,
        PAUSED,
        PAUSED_DUE_TO_TIMEOUT;
        
        public static int b04170417ЗЗЗЗ = 0;
        public static int b0417ЗЗЗЗЗ = 2;
        public static int b044Aъ044A044A044A044A = 6;
        public static int bъ044A044A044A044A044A = 1;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r7 = 4;
            r6 = 3;
            r5 = 2;
            r4 = 1;
            r3 = 0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "NOT_INITIALIZED";
            r0.<init>(r1, r3);
            NOT_INITIALIZED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "INITIALIZED";
            r0.<init>(r1, r4);
            INITIALIZED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "PLAYING";
            r0.<init>(r1, r5);
            PLAYING = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "STOPPED";
            r0.<init>(r1, r6);
            STOPPED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "STOPPED_DUE_TO_ERROR";
            r0.<init>(r1, r7);
            STOPPED_DUE_TO_ERROR = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "PAUSED";
            r2 = 5;
            r0.<init>(r1, r2);
            PAUSED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "PAUSED_DUE_TO_TIMEOUT";
            r2 = 6;
            r0.<init>(r1, r2);
            PAUSED_DUE_TO_TIMEOUT = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "PAUSED_DUE_TO_BUFFERING";
            r2 = 7;
            r0.<init>(r1, r2);
        L_0x004e:
            switch(r4) {
                case 0: goto L_0x004e;
                case 1: goto L_0x0055;
                default: goto L_0x0051;
            };
        L_0x0051:
            switch(r3) {
                case 0: goto L_0x0055;
                case 1: goto L_0x004e;
                default: goto L_0x0054;
            };
        L_0x0054:
            goto L_0x0051;
        L_0x0055:
            PAUSED_DUE_TO_BUFFERING = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = bЗ0417ЗЗЗЗ();
            r2 = bъ044A044A044A044A044A;
            r1 = r1 + r2;
            r2 = bЗ0417ЗЗЗЗ();
            r1 = r1 * r2;
            r2 = b0417ЗЗЗЗЗ;
            r1 = r1 % r2;
            r2 = b04170417ЗЗЗЗ;
            if (r1 == r2) goto L_0x0078;
        L_0x006c:
            r1 = bЗ0417ЗЗЗЗ();
            b044Aъ044A044A044A044A = r1;
            r1 = bЗ0417ЗЗЗЗ();
            b04170417ЗЗЗЗ = r1;
        L_0x0078:
            r1 = "DISPOSED";
            r2 = 8;
            r0.<init>(r1, r2);
            DISPOSED = r0;
            r0 = 9;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus[r0];
            r1 = NOT_INITIALIZED;
            r0[r3] = r1;
            r1 = INITIALIZED;
            r0[r4] = r1;
            r1 = PLAYING;
            r0[r5] = r1;
            r1 = STOPPED;
            r0[r6] = r1;
            r1 = STOPPED_DUE_TO_ERROR;
            r0[r7] = r1;
            r1 = 5;
            r2 = PAUSED;
            r0[r1] = r2;
            r1 = 6;
            r2 = PAUSED_DUE_TO_TIMEOUT;
            r0[r1] = r2;
            r1 = 7;
            r2 = PAUSED_DUE_TO_BUFFERING;
            r0[r1] = r2;
            r1 = 8;
            r2 = DISPOSED;
            r0[r1] = r2;
            a = r0;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.<clinit>():void");
        }

        public static int b0417З0417ЗЗЗ() {
            return 2;
        }

        public static int bЗ0417ЗЗЗЗ() {
            return 37;
        }

        public static int bЗЗ0417ЗЗЗ() {
            return 1;
        }

        public static SDKStatus valueOfCaseInsensitive(String str) {
            try {
                for (SDKStatus sDKStatus : values()) {
                    if (((b044Aъ044A044A044A044A + bъ044A044A044A044A044A) * b044Aъ044A044A044A044A) % b0417З0417ЗЗЗ() != b04170417ЗЗЗЗ) {
                        b044Aъ044A044A044A044A = 55;
                        b04170417ЗЗЗЗ = bЗ0417ЗЗЗЗ();
                    }
                    if (str.equalsIgnoreCase(sDKStatus.name())) {
                        return sDKStatus;
                    }
                }
                return null;
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public HapticContentSDK(int i, Context context) {
        this.d = context;
        if (((b044604460446ццц + b0446цц0446цц) * b044604460446ццц) % bц0446ц0446цц != bццц0446цц) {
            b044604460446ццц = 24;
            bццц0446цц = b04460446ц0446цц();
        }
        this.e = new RuntimeInfo();
    }

    public static int b04460446ц0446цц() {
        return 96;
    }

    public static int bцц04460446цц() {
        return 2;
    }

    public int bБ04110411Б04110411() {
        try {
            if (this.d.getPackageManager().checkPermission("android.permission.VIBRATE", this.d.getPackageName()) == 0) {
                this.b = new HandlerThread("SDK Monitor");
                this.b.start();
                try {
                    this.c = new Handler(this.b.getLooper());
                    Handler handler = this.c;
                    int i = b044604460446ццц;
                    switch ((i * (b0446цц0446цц + i)) % bц0446ц0446цц) {
                        case 0:
                            break;
                        default:
                            b044604460446ццц = b04460446ц0446цц();
                            bццц0446цц = 93;
                            break;
                    }
                    this.mMediaTaskManager = new MediaTaskManager(handler, this.d, this.e);
                    return 0;
                } catch (Exception e) {
                    throw e;
                }
            }
            Log.e(a, "Failed to create a Haptic Content SDK instance.Vibrate permission denied.");
            return -3;
        } catch (Exception e2) {
            throw e2;
        }
    }

    public final void dispose() {
        if (getSDKStatus() != SDKStatus.DISPOSED) {
            this.mMediaTaskManager.transitToState(SDKStatus.NOT_INITIALIZED);
            this.b.quit();
            this.b = null;
            this.mMediaTaskManager = null;
            this.mDisposed = true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finalize() throws java.lang.Throwable {
        /*
        r2 = this;
        r2.dispose();	 Catch:{ Throwable -> 0x000c }
        super.finalize();
        return;
    L_0x0007:
        r0 = move-exception;
        super.finalize();
        throw r0;
    L_0x000c:
        r0 = move-exception;
        throw r0;	 Catch:{ all -> 0x0007 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.finalize():void");
    }

    public final SDKStatus getSDKStatus() {
        return this.mDisposed ? SDKStatus.DISPOSED : this.mMediaTaskManager.getSDKStatus();
    }

    public final String getVersion() {
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                b044604460446ццц = 88;
                return HapticMediaSDKVersion.Version;
            }
        }
    }

    public final int mute() {
        int i = b044604460446ццц;
        switch ((i * (b0446цц0446цц + i)) % bц0446ц0446цц) {
            case 0:
                break;
            default:
                b044604460446ццц = 16;
                bццц0446цц = b04460446ц0446цц();
                break;
        }
        try {
            if (getSDKStatus() == SDKStatus.DISPOSED) {
                return -1;
            }
            try {
                this.e.mute();
                return 0;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public abstract int openHaptics(String str);

    public final int pause() {
        String str = null;
        try {
            SDKStatus sDKStatus = getSDKStatus();
            if (sDKStatus != SDKStatus.DISPOSED) {
                if (sDKStatus == SDKStatus.STOPPED_DUE_TO_ERROR) {
                    while (true) {
                        try {
                            str.length();
                        } catch (Exception e) {
                            b044604460446ццц = 21;
                        }
                    }
                } else {
                    try {
                        return this.mMediaTaskManager.transitToState(SDKStatus.PAUSED);
                    } catch (Exception e2) {
                        throw e2;
                    }
                }
            }
            return -1;
        } catch (Exception e22) {
            throw e22;
        }
    }

    public final int play() {
        try {
            SDKStatus sDKStatus = getSDKStatus();
            if (sDKStatus != SDKStatus.INITIALIZED && sDKStatus != SDKStatus.STOPPED) {
                return -1;
            }
            this.mMediaTaskManager.setMediaTimestamp(0);
            MediaTaskManager mediaTaskManager = this.mMediaTaskManager;
            SDKStatus sDKStatus2 = SDKStatus.PLAYING;
            if (((b04460446ц0446цц() + b0446цц0446цц) * b04460446ц0446цц()) % bцц04460446цц() != bццц0446цц) {
                b044604460446ццц = b04460446ц0446цц();
                bццц0446цц = b04460446ц0446цц();
            }
            try {
                return mediaTaskManager.transitToState(sDKStatus2);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public final int resume() {
        SDKStatus sDKStatus = getSDKStatus();
        if (!(sDKStatus == SDKStatus.PAUSED || sDKStatus == SDKStatus.PLAYING)) {
            if (sDKStatus != SDKStatus.STOPPED) {
                return -1;
            }
            while (true) {
                try {
                    int[] iArr = new int[-1];
                } catch (Exception e) {
                    b044604460446ццц = 99;
                }
            }
        }
        this.mMediaTaskManager.setMediaReferenceTime();
        return this.mMediaTaskManager.transitToState(SDKStatus.PLAYING);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int seek(int r5) {
        /*
        r4 = this;
        r3 = 1;
        r0 = r4.getSDKStatus();
        r1 = b044604460446ццц;
        r2 = b0446цц0446цц;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bц0446ц0446цц;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x001b;
            default: goto L_0x0011;
        };
    L_0x0011:
        r1 = 56;
        b044604460446ццц = r1;
        r1 = b04460446ц0446цц();
        bццц0446цц = r1;
    L_0x001b:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.DISPOSED;
    L_0x001d:
        switch(r3) {
            case 0: goto L_0x001d;
            case 1: goto L_0x0024;
            default: goto L_0x0020;
        };
    L_0x0020:
        switch(r3) {
            case 0: goto L_0x001d;
            case 1: goto L_0x0024;
            default: goto L_0x0023;
        };
    L_0x0023:
        goto L_0x0020;
    L_0x0024:
        if (r0 == r1) goto L_0x002e;
    L_0x0026:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.NOT_INITIALIZED;
        if (r0 == r1) goto L_0x002e;
    L_0x002a:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;
        if (r0 != r1) goto L_0x0030;
    L_0x002e:
        r0 = -1;
    L_0x002f:
        return r0;
    L_0x0030:
        r0 = r4.mMediaTaskManager;
        r0 = r0.SeekTo(r5);
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.seek(int):int");
    }

    public final int stop() {
        SDKStatus sDKStatus = getSDKStatus();
        if (sDKStatus == SDKStatus.DISPOSED || sDKStatus == SDKStatus.NOT_INITIALIZED) {
            return -1;
        }
        int transitToState = this.mMediaTaskManager.transitToState(SDKStatus.STOPPED);
        while (true) {
            switch (1) {
                case null:
                    break;
                case 1:
                    return transitToState;
                default:
                    while (true) {
                        switch (1) {
                            case null:
                                break;
                            case 1:
                                return transitToState;
                            default:
                        }
                    }
            }
        }
    }

    public final int unmute() {
        try {
            if (getSDKStatus() == SDKStatus.DISPOSED) {
                return -1;
            }
            this.e.unmute();
            return 0;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int update(long r6) {
        /*
        r5 = this;
        r3 = 1;
        r0 = r5.getSDKStatus();
        r1 = b044604460446ццц;
        r2 = b0446цц0446цц;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bц0446ц0446цц;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x001b;
            default: goto L_0x0011;
        };
    L_0x0011:
        r1 = b04460446ц0446цц();
        b044604460446ццц = r1;
        r1 = 98;
        bццц0446цц = r1;
    L_0x001b:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;
        if (r0 == r1) goto L_0x0023;
    L_0x001f:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_TIMEOUT;
        if (r0 != r1) goto L_0x003a;
    L_0x0023:
        r0 = r5.mMediaTaskManager;
        r0.setMediaTimestamp(r6);
        r0 = r5.mMediaTaskManager;
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;
        r0 = r0.transitToState(r1);
    L_0x0030:
        return r0;
    L_0x0031:
        r0 = r5.mMediaTaskManager;
        r0.setMediaTimestamp(r6);
        r0 = 0;
        goto L_0x0030;
    L_0x0038:
        r0 = -1;
        goto L_0x0030;
    L_0x003a:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;
        if (r0 == r1) goto L_0x0042;
    L_0x003e:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_BUFFERING;
        if (r0 != r1) goto L_0x0038;
    L_0x0042:
        switch(r3) {
            case 0: goto L_0x0042;
            case 1: goto L_0x0031;
            default: goto L_0x0045;
        };
    L_0x0045:
        switch(r3) {
            case 0: goto L_0x0042;
            case 1: goto L_0x0031;
            default: goto L_0x0048;
        };
    L_0x0048:
        goto L_0x0045;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.update(long):int");
    }
}
