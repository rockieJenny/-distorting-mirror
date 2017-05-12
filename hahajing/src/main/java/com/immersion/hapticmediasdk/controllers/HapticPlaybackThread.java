package com.immersion.hapticmediasdk.controllers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.immersion.content.EndpointWarp;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Profiler;
import com.immersion.hapticmediasdk.utils.RuntimeInfo;
import java.util.ArrayList;
import java.util.Iterator;
import rrrrrr.ccrcrr;
import rrrrrr.crcrrr;
import rrrrrr.rccrrr;
import rrrrrr.rrcrrr;

public class HapticPlaybackThread extends Thread {
    private static final long D = 100;
    private static final int E = 5;
    public static final int HAPTIC_BYTES_AVAILABLE_TO_DOWNLOAD = 3;
    public static final int HAPTIC_DOWNLOAD_ERROR = 8;
    public static final String HAPTIC_DOWNLOAD_EXCEPTION_KEY = "haptic_download_exception";
    public static final int HAPTIC_PAUSE_PLAYBACK = 5;
    public static final int HAPTIC_PLAYBACK_FOR_TIME_CODE = 2;
    public static final int HAPTIC_PLAYBACK_IS_READY = 6;
    public static final int HAPTIC_QUIT_PLAYBACK = 9;
    public static final int HAPTIC_SET_BUFFERING_POSITION = 1;
    public static final int HAPTIC_STOP_PLAYBACK = 4;
    public static final int PAUSE_AV_FOR_HAPTIC_BUFFERING = 7;
    private static final String a = "HapticPlaybackThread";
    private static final int b = Integer.MIN_VALUE;
    public static int b0427042704270427ЧЧ = 1;
    public static int b0427Ч0427Ч0427Ч = 0;
    public static int bЧ042704270427ЧЧ = 86;
    public static int bЧЧЧЧ0427Ч = 2;
    private static final String c = "playback_timecode";
    private static final String d = "playback_uptime";
    private RuntimeInfo A;
    private boolean B;
    private FileManager C;
    private final Runnable F;
    private final Runnable G;
    public volatile boolean b044404440444фф0444;
    public Context bф04440444фф0444;
    public volatile boolean bффф0444ф0444;
    private int e = 0;
    private final String f;
    private Handler g;
    private final Handler h;
    private HapticDownloadThread i;
    private Looper j;
    private IHapticFileReader k;
    private EndpointWarp l;
    private final Profiler m = new Profiler();
    private Object n = new Object();
    private Object o = new Object();
    private int p;
    private int q;
    private int r;
    private long s;
    private int t;
    private int u;
    private int v;
    private long w;
    private boolean x;
    private boolean y;
    private ArrayList z;

    public HapticPlaybackThread(Context context, String str, Handler handler, boolean z, RuntimeInfo runtimeInfo) {
        super(a);
        int i = bЧ042704270427ЧЧ;
        switch ((i * (b0427042704270427ЧЧ + i)) % bЧЧЧЧ0427Ч) {
            case 0:
                break;
            default:
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                b0427042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                break;
        }
        this.x = false;
        this.y = false;
        this.b044404440444фф0444 = false;
        this.bффф0444ф0444 = false;
        this.B = false;
        this.F = new rrcrrr(this);
        this.G = new crcrrr(this);
        this.f = str;
        this.h = handler;
        this.bф04440444фф0444 = context;
        this.B = z;
        this.C = new FileManager(context);
        this.A = runtimeInfo;
        this.z = new ArrayList();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
        r2 = this;
    L_0x0000:
        r0 = r2.i;
        r0 = r0.isAlive();
        if (r0 == 0) goto L_0x003d;
    L_0x0008:
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r0 = r0 + r1;
        r1 = bЧ042704270427ЧЧ;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        r1 = bЧЧ0427Ч0427Ч();
        if (r0 == r1) goto L_0x0023;
    L_0x0019:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = 65;
        b0427042704270427ЧЧ = r0;
    L_0x0023:
        r0 = r2.i;
        r0.terminate();
        r0 = r2.i;
        r0.interrupt();
        java.lang.Thread.currentThread();
    L_0x0030:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0039;
            case 1: goto L_0x0030;
            default: goto L_0x0034;
        };
    L_0x0034:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0030;
            case 1: goto L_0x0039;
            default: goto L_0x0038;
        };
    L_0x0038:
        goto L_0x0034;
    L_0x0039:
        java.lang.Thread.yield();
        goto L_0x0000;
    L_0x003d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.a():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r9, long r10) {
        /*
        r8 = this;
        r4 = 0;
        r6 = 1;
        r0 = r8.y;
        if (r0 != 0) goto L_0x0045;
    L_0x0006:
        r0 = r8.k;	 Catch:{ Error -> 0x001f }
        if (r0 != 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = r8.l;	 Catch:{ Error -> 0x001f }
        if (r0 != 0) goto L_0x0040;
    L_0x000f:
        r0 = r8.k;	 Catch:{ Error -> 0x001f }
        r0 = r0.getEncryptedHapticHeader();	 Catch:{ Error -> 0x001f }
        if (r0 != 0) goto L_0x002a;
    L_0x0017:
        r0 = "HapticPlaybackThread";
        r1 = "corrupted hapt file or unsupported format";
        com.immersion.hapticmediasdk.utils.Log.e(r0, r1);	 Catch:{ Error -> 0x001f }
        goto L_0x000a;
    L_0x001f:
        r0 = move-exception;
        r1 = "HapticPlaybackThread";
        r0 = r0.getMessage();
        com.immersion.hapticmediasdk.utils.Log.e(r1, r0);
        goto L_0x000a;
    L_0x002a:
        r1 = new com.immersion.content.EndpointWarp;	 Catch:{ Error -> 0x001f }
        r2 = r8.bф04440444фф0444;	 Catch:{ Error -> 0x001f }
        r3 = r0.length;	 Catch:{ Error -> 0x001f }
        r1.<init>(r2, r0, r3);	 Catch:{ Error -> 0x001f }
        r8.l = r1;	 Catch:{ Error -> 0x001f }
        r0 = r8.l;	 Catch:{ Error -> 0x001f }
        if (r0 != 0) goto L_0x0040;
    L_0x0038:
        r0 = "HapticPlaybackThread";
        r1 = "Error creating endpointwarp";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);	 Catch:{ Error -> 0x001f }
        goto L_0x000a;
    L_0x0040:
        r0 = r8.l;	 Catch:{ Error -> 0x001f }
        r0.start();	 Catch:{ Error -> 0x001f }
    L_0x0045:
        r8.bффф0444ф0444 = r4;
        r8.y = r6;
        r8.v = r4;
        r1 = r8.n;
        monitor-enter(r1);
        r8.u = r9;	 Catch:{ all -> 0x0069 }
        r0 = r8.u;	 Catch:{ all -> 0x0069 }
        r8.t = r0;	 Catch:{ all -> 0x0069 }
        r2 = r8.w;	 Catch:{ all -> 0x0069 }
        r4 = 0;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x0062;
    L_0x005c:
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x0069 }
        r8.w = r2;	 Catch:{ all -> 0x0069 }
    L_0x0062:
        monitor-exit(r1);	 Catch:{ all -> 0x0069 }
        r8.s = r10;
        r8.h();
        goto L_0x000a;
    L_0x0069:
        r0 = move-exception;
    L_0x006a:
        switch(r6) {
            case 0: goto L_0x006a;
            case 1: goto L_0x0071;
            default: goto L_0x006d;
        };
    L_0x006d:
        switch(r6) {
            case 0: goto L_0x006a;
            case 1: goto L_0x0071;
            default: goto L_0x0070;
        };
    L_0x0070:
        goto L_0x006d;
    L_0x0071:
        monitor-exit(r1);	 Catch:{ all -> 0x0069 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.a(int, long):void");
    }

    private void a(Message message) {
        this.x = true;
        Message obtainMessage = this.h.obtainMessage(8);
        obtainMessage.setData(message.getData());
        this.h.sendMessage(obtainMessage);
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = 41;
            b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b() {
        /*
        r4 = this;
        r3 = 0;
        r0 = r4.i;
        if (r0 == 0) goto L_0x000a;
    L_0x0005:
        r4.a();
        r4.i = r3;
    L_0x000a:
        r1 = r4.o;
        monitor-enter(r1);
        r0 = r4.g;	 Catch:{ all -> 0x0049 }
        r2 = 0;
        r0.removeCallbacksAndMessages(r2);	 Catch:{ all -> 0x0049 }
        monitor-exit(r1);	 Catch:{ all -> 0x0049 }
        r0 = r4.j;
        if (r0 == 0) goto L_0x001f;
    L_0x0018:
        r0 = r4.j;
        r0.quit();
        r4.j = r3;
    L_0x001f:
        r0 = r4.k;
        if (r0 == 0) goto L_0x002a;
    L_0x0023:
        r0 = r4.k;
        r0.close();
        r4.k = r3;
    L_0x002a:
        r0 = r4.l;
        if (r0 == 0) goto L_0x0043;
    L_0x002e:
        r0 = r4.l;
        r0.stop();
        r0 = r4.l;
        r0.dispose();
    L_0x0038:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0041;
            case 1: goto L_0x0038;
            default: goto L_0x003c;
        };
    L_0x003c:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0038;
            case 1: goto L_0x0041;
            default: goto L_0x0040;
        };
    L_0x0040:
        goto L_0x003c;
    L_0x0041:
        r4.l = r3;
    L_0x0043:
        r0 = r4.C;
        r0.deleteHapticStorage();
        return;
    L_0x0049:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0049 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ void b04110411041104110411Б(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2, int r3, long r4) {
        /*
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r0 = r0 + r1;
        r1 = bЧ042704270427ЧЧ;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        r1 = b0427Ч0427Ч0427Ч;
        if (r0 == r1) goto L_0x0019;
    L_0x000f:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = 45;
        b0427Ч0427Ч0427Ч = r0;
    L_0x0019:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0019;
            case 1: goto L_0x0022;
            default: goto L_0x001d;
        };
    L_0x001d:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0022;
            case 1: goto L_0x0019;
            default: goto L_0x0021;
        };
    L_0x0021:
        goto L_0x001d;
    L_0x0022:
        r2.a(r3, r4);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04110411041104110411Б(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, int, long):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.immersion.content.EndpointWarp b0411041104110411Б0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r4) {
        /*
        r3 = 0;
        r1 = -1;
        r0 = 4;
        r2 = 0;
    L_0x0004:
        r0 = r0 / r2;
        goto L_0x0004;
    L_0x0006:
        r0 = move-exception;
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = r4.l;
    L_0x000f:
        switch(r3) {
            case 0: goto L_0x0016;
            case 1: goto L_0x000f;
            default: goto L_0x0012;
        };
    L_0x0012:
        switch(r3) {
            case 0: goto L_0x0016;
            case 1: goto L_0x000f;
            default: goto L_0x0015;
        };
    L_0x0015:
        goto L_0x0012;
    L_0x0016:
        return r0;
    L_0x0017:
        r0 = move-exception;
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
    L_0x001e:
        r0 = new int[r1];	 Catch:{ Exception -> 0x0006 }
        goto L_0x001e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411041104110411Б0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):com.immersion.content.EndpointWarp");
    }

    public static /* synthetic */ void b041104110411ББ0411(HapticPlaybackThread hapticPlaybackThread) {
        int i = bЧ042704270427ЧЧ;
        switch ((i * (b0427042704270427ЧЧ + i)) % bЧЧЧЧ0427Ч) {
            case 0:
                break;
            default:
                bЧ042704270427ЧЧ = 25;
                b0427Ч0427Ч0427Ч = 36;
                break;
        }
        try {
            hapticPlaybackThread.g();
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ int b04110411Б04110411Б(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2, int r3) {
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
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001f;
            default: goto L_0x0015;
        };
    L_0x0015:
        r0 = 74;
        bЧ042704270427ЧЧ = r0;
        r0 = b0427ЧЧЧ0427Ч();
        b0427Ч0427Ч0427Ч = r0;
    L_0x001f:
        r2.p = r3;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04110411Б04110411Б(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, int):int");
    }

    public static /* synthetic */ boolean b04110411Б0411Б0411(HapticPlaybackThread hapticPlaybackThread) {
        try {
            boolean z = hapticPlaybackThread.y;
            if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
            }
            return z;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ int b04110411ББ04110411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
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
        r0 = b0427ЧЧЧ0427Ч();
        r1 = b0427042704270427ЧЧ;
        r0 = r0 + r1;
        r1 = b0427ЧЧЧ0427Ч();
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        r1 = b0427Ч0427Ч0427Ч;
        if (r0 == r1) goto L_0x0028;
    L_0x001c:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = b0427ЧЧЧ0427Ч();
        b0427Ч0427Ч0427Ч = r0;
    L_0x0028:
        r0 = r2.u;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04110411ББ04110411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):int");
    }

    public static /* synthetic */ FileManager b04110411БББ0411(HapticPlaybackThread hapticPlaybackThread) {
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                bЧ042704270427ЧЧ = 90;
                return hapticPlaybackThread.C;
            }
        }
    }

    public static /* synthetic */ int b0411Б041104110411Б(HapticPlaybackThread hapticPlaybackThread, int i) {
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
            b0427Ч0427Ч0427Ч = 87;
        }
        hapticPlaybackThread.r = i;
        return i;
    }

    public static /* synthetic */ ArrayList b0411Б04110411Б0411(HapticPlaybackThread hapticPlaybackThread) {
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
            b0427Ч0427Ч0427Ч = 92;
        }
        try {
            return hapticPlaybackThread.z;
        } catch (Exception e) {
            throw e;
        }
    }

    public static /* synthetic */ Runnable b0411Б0411Б04110411(HapticPlaybackThread hapticPlaybackThread) {
        if (((b0427ЧЧЧ0427Ч() + b0427042704270427ЧЧ) * b0427ЧЧЧ0427Ч()) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
            b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
        }
        try {
            return hapticPlaybackThread.G;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ int b0411Б0411ББ0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3, int r4) {
        /*
        r2 = 0;
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r0 = r0 + r1;
        r1 = bЧ042704270427ЧЧ;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        r1 = b0427Ч0427Ч0427Ч;
        if (r0 == r1) goto L_0x001a;
    L_0x0010:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = 23;
        b0427Ч0427Ч0427Ч = r0;
    L_0x001a:
        switch(r2) {
            case 0: goto L_0x0021;
            case 1: goto L_0x001a;
            default: goto L_0x001d;
        };
    L_0x001d:
        switch(r2) {
            case 0: goto L_0x0021;
            case 1: goto L_0x001a;
            default: goto L_0x0020;
        };
    L_0x0020:
        goto L_0x001d;
    L_0x0021:
        r3.e = r4;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411Б0411ББ0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ java.lang.Runnable b0411ББ04110411Б(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3) {
        /*
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
        r0 = r3.F;
        r1 = bЧ042704270427ЧЧ;
        r2 = b0427042704270427ЧЧ;
        r1 = r1 + r2;
        r2 = bЧ042704270427ЧЧ;
        r1 = r1 * r2;
        r2 = bЧЧЧЧ0427Ч;
        r1 = r1 % r2;
        r2 = bЧЧ0427Ч0427Ч();
        if (r1 == r2) goto L_0x0025;
    L_0x001b:
        r1 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r1;
        r1 = 18;
        b0427Ч0427Ч0427Ч = r1;
    L_0x0025:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411ББ04110411Б(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):java.lang.Runnable");
    }

    public static /* synthetic */ void b0411ББ0411Б0411(HapticPlaybackThread hapticPlaybackThread) {
        String str = null;
        hapticPlaybackThread.e();
        while (true) {
            try {
                str.length();
            } catch (Exception e) {
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                return;
            }
        }
    }

    public static /* synthetic */ int b0411БББ04110411(HapticPlaybackThread hapticPlaybackThread, int i) {
        int i2 = hapticPlaybackThread.u + i;
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = 55;
            b0427Ч0427Ч0427Ч = 1;
        }
        hapticPlaybackThread.u = i2;
        return i2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.immersion.hapticmediasdk.controllers.IHapticFileReader b0411ББББ0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2, com.immersion.hapticmediasdk.controllers.IHapticFileReader r3) {
        /*
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
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r0 = r0 + r1;
        r1 = bЧ042704270427ЧЧ;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        r1 = b0427Ч0427Ч0427Ч;
        if (r0 == r1) goto L_0x0023;
    L_0x0017:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = b0427ЧЧЧ0427Ч();
        b0427Ч0427Ч0427Ч = r0;
    L_0x0023:
        r2.k = r3;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0411ББББ0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, com.immersion.hapticmediasdk.controllers.IHapticFileReader):com.immersion.hapticmediasdk.controllers.IHapticFileReader");
    }

    public static int b04270427Ч04270427Ч() {
        return 2;
    }

    public static int b0427ЧЧЧ0427Ч() {
        return 41;
    }

    public static /* synthetic */ void bБ0411041104110411Б(HapticPlaybackThread hapticPlaybackThread) {
        hapticPlaybackThread.d();
        int i = bЧ042704270427ЧЧ;
        switch ((i * (b0427042704270427ЧЧ + i)) % bЧЧЧЧ0427Ч) {
            case 0:
                return;
            default:
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
                return;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.immersion.hapticmediasdk.utils.RuntimeInfo bБ041104110411Б0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
        /*
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0016;
            default: goto L_0x000c;
        };
    L_0x000c:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = 64;
        b0427Ч0427Ч0427Ч = r0;
    L_0x0016:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0016;
            case 1: goto L_0x001f;
            default: goto L_0x001a;
        };
    L_0x001a:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0016;
            default: goto L_0x001e;
        };
    L_0x001e:
        goto L_0x001a;
    L_0x001f:
        r0 = r2.A;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bБ041104110411Б0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):com.immersion.hapticmediasdk.utils.RuntimeInfo");
    }

    public static /* synthetic */ void bБ04110411ББ0411(HapticPlaybackThread hapticPlaybackThread) {
        int b0427ЧЧЧ0427Ч = b0427ЧЧЧ0427Ч();
        switch ((b0427ЧЧЧ0427Ч * (b0427042704270427ЧЧ + b0427ЧЧЧ0427Ч)) % bЧЧЧЧ0427Ч) {
            case 0:
                break;
            default:
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
                break;
        }
        hapticPlaybackThread.f();
    }

    public static /* synthetic */ Handler bБ0411Б04110411Б(HapticPlaybackThread hapticPlaybackThread) {
        try {
            Handler handler = hapticPlaybackThread.g;
            int i = bЧ042704270427ЧЧ;
            switch ((i * (b0427042704270427ЧЧ + i)) % bЧЧЧЧ0427Ч) {
                case 0:
                    break;
                default:
                    bЧ042704270427ЧЧ = 9;
                    b0427Ч0427Ч0427Ч = 62;
                    break;
            }
            return handler;
        } catch (Exception e) {
            throw e;
        }
    }

    public static /* synthetic */ void bБ0411Б0411Б0411(HapticPlaybackThread hapticPlaybackThread) {
        hapticPlaybackThread.h();
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                return;
            }
        }
    }

    public static /* synthetic */ int bБ0411ББ04110411(HapticPlaybackThread hapticPlaybackThread, int i) {
        int[] iArr;
        while (true) {
            try {
                iArr = new int[-1];
            } catch (Exception e) {
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                while (true) {
                    try {
                        iArr = new int[-1];
                    } catch (Exception e2) {
                        bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                        try {
                            hapticPlaybackThread.t = i;
                            return i;
                        } catch (Exception e3) {
                            throw e3;
                        }
                    }
                }
            }
        }
    }

    public static /* synthetic */ int bББ041104110411Б(HapticPlaybackThread hapticPlaybackThread, int i) {
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % b04270427Ч04270427Ч() != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = 8;
            b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
        }
        try {
            hapticPlaybackThread.q = i;
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ long bББ0411Б04110411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3, long r4) {
        /*
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
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r0 = r0 + r1;
        r1 = bЧ042704270427ЧЧ;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        r1 = b0427Ч0427Ч0427Ч;
        if (r0 == r1) goto L_0x0021;
    L_0x0017:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = 90;
        b0427Ч0427Ч0427Ч = r0;
    L_0x0021:
        r3.w = r4;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bББ0411Б04110411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, long):long");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ void bБББ0411Б0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2, android.os.Message r3) {
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
        r0 = bЧ042704270427ЧЧ;
        r1 = bЧ0427Ч04270427Ч();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001f;
            default: goto L_0x0017;
        };
    L_0x0017:
        r0 = 56;
        bЧ042704270427ЧЧ = r0;
        r0 = 92;
        b0427Ч0427Ч0427Ч = r0;
    L_0x001f:
        r2.a(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bБББ0411Б0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, android.os.Message):void");
    }

    public static /* synthetic */ Object bББББ04110411(HapticPlaybackThread hapticPlaybackThread) {
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = 18;
            b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
        }
        try {
            return hapticPlaybackThread.n;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.immersion.hapticmediasdk.controllers.IHapticFileReader bБББББ0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
        /*
        r0 = bЧ042704270427ЧЧ;
        r1 = b0427042704270427ЧЧ;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЧЧЧЧ0427Ч;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0016;
            default: goto L_0x000c;
        };
    L_0x000c:
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
        r0 = 19;
        b0427Ч0427Ч0427Ч = r0;
    L_0x0016:
        r0 = r2.k;
    L_0x0018:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0021;
            case 1: goto L_0x0018;
            default: goto L_0x001c;
        };
    L_0x001c:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0018;
            case 1: goto L_0x0021;
            default: goto L_0x0020;
        };
    L_0x0020:
        goto L_0x001c;
    L_0x0021:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bБББББ0411(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):com.immersion.hapticmediasdk.controllers.IHapticFileReader");
    }

    public static int bЧ0427Ч04270427Ч() {
        return 1;
    }

    public static int bЧЧ0427Ч0427Ч() {
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
        r1 = this;
        r0 = 0;
        monitor-enter(r1);
    L_0x0002:
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0002;
            default: goto L_0x0005;
        };
    L_0x0005:
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0002;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0005;
    L_0x0009:
        r1.notifyAll();	 Catch:{ all -> 0x000e }
        monitor-exit(r1);	 Catch:{ all -> 0x000e }
        return;
    L_0x000e:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x000e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.c():void");
    }

    private void d() {
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
            bЧ042704270427ЧЧ = 74;
            b0427Ч0427Ч0427Ч = 21;
        }
        if (!this.x) {
            int i = this.r;
            this.r = i + 1;
            if (i == 5) {
                this.h.sendMessage(this.h.obtainMessage(7, this.p, 0));
                this.g.postDelayed(this.F, D);
            } else if (this.k == null || !this.k.bufferAtPlaybackPosition(this.p)) {
                this.g.postDelayed(this.F, D);
            } else if (this.q != Integer.MIN_VALUE) {
                this.h.sendMessage(this.h.obtainMessage(6, this.p, this.q));
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void e() {
        /*
        r5 = this;
        r4 = 0;
        r0 = 0;
        r5.b();	 Catch:{ Exception -> 0x0030 }
    L_0x0005:
        r0.length();	 Catch:{ Exception -> 0x0009 }
        goto L_0x0005;
    L_0x0009:
        r1 = move-exception;
        r1 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r1;
    L_0x0010:
        r0.length();	 Catch:{ Exception -> 0x0014 }
        goto L_0x0010;
    L_0x0014:
        r1 = move-exception;
        r1 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r1;
    L_0x001b:
        r0.length();	 Catch:{ Exception -> 0x001f }
        goto L_0x001b;
    L_0x001f:
        r0 = move-exception;
        r0 = 38;
        bЧ042704270427ЧЧ = r0;
        r5.b044404440444фф0444 = r4;
        r5.c();
    L_0x0029:
        return;
    L_0x002a:
        r5.b044404440444фф0444 = r4;
        r5.c();
        goto L_0x0029;
    L_0x0030:
        r0 = move-exception;
        r1 = "HapticPlaybackThread";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0055 }
        r2.<init>();	 Catch:{ all -> 0x0055 }
        r3 = "quit() : ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0055 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0055 }
        r0 = r2.append(r0);	 Catch:{ all -> 0x0055 }
        r0 = r0.toString();	 Catch:{ all -> 0x0055 }
        com.immersion.hapticmediasdk.utils.Log.e(r1, r0);	 Catch:{ all -> 0x0055 }
    L_0x004d:
        switch(r4) {
            case 0: goto L_0x002a;
            case 1: goto L_0x004d;
            default: goto L_0x0050;
        };
    L_0x0050:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x004d;
            case 1: goto L_0x002a;
            default: goto L_0x0054;
        };
    L_0x0054:
        goto L_0x0050;
    L_0x0055:
        r0 = move-exception;
        r5.b044404440444фф0444 = r4;
        r5.c();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.e():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() {
        /*
        r8 = this;
        r6 = 0;
        r5 = 1;
        r4 = 0;
        r8.y = r4;
        r0 = r8.l;
        if (r0 == 0) goto L_0x000f;
    L_0x000a:
        r0 = r8.l;
        r0.stop();
    L_0x000f:
        switch(r4) {
            case 0: goto L_0x0016;
            case 1: goto L_0x000f;
            default: goto L_0x0012;
        };
    L_0x0012:
        switch(r5) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0016;
            default: goto L_0x0015;
        };
    L_0x0015:
        goto L_0x0012;
    L_0x0016:
        r0 = r8.g;
        r1 = r8.F;
        r0.removeCallbacks(r1);
        r8.removePlaybackCallbacks();
        r1 = r8.n;
        monitor-enter(r1);
        r0 = 0;
        r8.u = r0;	 Catch:{ all -> 0x0035 }
        r0 = 0;
        r8.t = r0;	 Catch:{ all -> 0x0035 }
        r2 = 0;
        r8.w = r2;	 Catch:{ all -> 0x0035 }
        monitor-exit(r1);	 Catch:{ all -> 0x0035 }
        r8.v = r4;
        r8.s = r6;
        r8.bффф0444ф0444 = r5;
        return;
    L_0x0035:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0035 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.f():void");
    }

    private void g() {
        this.y = false;
        removePlaybackCallbacks();
        int i = bЧ042704270427ЧЧ;
        switch ((i * (bЧ0427Ч04270427Ч() + i)) % bЧЧЧЧ0427Ч) {
            case 0:
                return;
            default:
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
                return;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h() {
        /*
        r13 = this;
        r12 = 0;
        r0 = r13.y;
        if (r0 == 0) goto L_0x0057;
    L_0x0005:
        r1 = r13.n;
        monitor-enter(r1);
        r2 = r13.u;	 Catch:{ all -> 0x0058 }
        r4 = r13.t;	 Catch:{ all -> 0x0058 }
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        r0 = r13.k;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        r6 = r0.getBufferForPlaybackPosition(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        r0 = r13.k;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        r8 = (long) r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        r7 = r0.getHapticBlockIndex(r8);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        r0 = r13.k;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        r8 = (long) r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        r8 = r0.getBlockOffset(r8);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005b }
        if (r6 == 0) goto L_0x006e;
    L_0x0023:
        r0 = r13.s;
        r3 = r13.v;
        r10 = (long) r3;
        r10 = r10 + r0;
        r0 = new rrrrrr.ccrcrr;
        r2 = (long) r2;
        r4 = (long) r4;
        r1 = r13;
        r0.<init>(r1, r2, r4, r6, r7, r8);
        r1 = r13.o;
    L_0x0033:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0033;
            case 1: goto L_0x003b;
            default: goto L_0x0037;
        };
    L_0x0037:
        switch(r12) {
            case 0: goto L_0x003b;
            case 1: goto L_0x0033;
            default: goto L_0x003a;
        };
    L_0x003a:
        goto L_0x0037;
    L_0x003b:
        monitor-enter(r1);
        r2 = r13.z;	 Catch:{ all -> 0x006b }
        r2.add(r0);	 Catch:{ all -> 0x006b }
        monitor-exit(r1);	 Catch:{ all -> 0x006b }
        r1 = r13.g;
        r2 = r13.e;
        r2 = (long) r2;
        r2 = r2 + r10;
        r1.postAtTime(r0, r2);
        r0 = r13.v;
        r1 = r13.e;
        r0 = r0 + r1;
        r13.v = r0;
        r0 = r13.m;
        r0.startTimingII();
    L_0x0057:
        return;
    L_0x0058:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        throw r0;
    L_0x005b:
        r0 = move-exception;
        r13.y = r12;
        r0 = r13.h;
        r1 = r13.h;
        r3 = 7;
        r1 = r1.obtainMessage(r3, r2, r12);
        r0.sendMessage(r1);
        goto L_0x0057;
    L_0x006b:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x006b }
        throw r0;
    L_0x006e:
        r1 = r13.n;
        monitor-enter(r1);
        r0 = 0;
        r13.u = r0;	 Catch:{ all -> 0x0081 }
        r0 = 0;
        r13.t = r0;	 Catch:{ all -> 0x0081 }
        monitor-exit(r1);	 Catch:{ all -> 0x0081 }
        r13.v = r12;
        r0 = 0;
        r13.s = r0;
        r13.y = r12;
        goto L_0x0057;
    L_0x0081:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0081 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.h():void");
    }

    public Handler getHandler() {
        try {
            Handler handler = this.g;
            if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                b0427Ч0427Ч0427Ч = 58;
            }
            return handler;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isStarted() {
        boolean z = this.b044404440444фф0444;
        int i = bЧ042704270427ЧЧ;
        switch ((i * (b0427042704270427ЧЧ + i)) % bЧЧЧЧ0427Ч) {
            case 0:
                break;
            default:
                bЧ042704270427ЧЧ = 69;
                b0427Ч0427Ч0427Ч = b0427ЧЧЧ0427Ч();
                break;
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStopped() {
        /*
        r3 = this;
        r1 = 0;
        r0 = r3.bффф0444ф0444;
    L_0x0003:
        switch(r1) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0003;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r1) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0003;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        r1 = bЧ042704270427ЧЧ;
        r2 = bЧ0427Ч04270427Ч();
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bЧЧЧЧ0427Ч;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x0022;
            default: goto L_0x0018;
        };
    L_0x0018:
        r1 = 11;
        bЧ042704270427ЧЧ = r1;
        r1 = b0427ЧЧЧ0427Ч();
        b0427Ч0427Ч0427Ч = r1;
    L_0x0022:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.isStopped():boolean");
    }

    public void pauseHapticPlayback() {
        if (((bЧ042704270427ЧЧ + b0427042704270427ЧЧ) * bЧ042704270427ЧЧ) % b04270427Ч04270427Ч() != bЧЧ0427Ч0427Ч()) {
            bЧ042704270427ЧЧ = 98;
            b0427Ч0427Ч0427Ч = 68;
        }
        this.g.sendEmptyMessage(5);
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void playHapticForPlaybackPosition(int r7, long r8) {
        /*
        r6 = this;
        r5 = 2;
        r0 = 0;
        r1 = 0;
        r2 = 0;
        r6.removePlaybackCallbacks();
        r3 = r6.g;
        r3.removeMessages(r5);
        r3 = new android.os.Bundle;
    L_0x000e:
        switch(r0) {
            case 0: goto L_0x0015;
            case 1: goto L_0x000e;
            default: goto L_0x0011;
        };
    L_0x0011:
        switch(r0) {
            case 0: goto L_0x0015;
            case 1: goto L_0x000e;
            default: goto L_0x0014;
        };
    L_0x0014:
        goto L_0x0011;
    L_0x0015:
        r3.<init>();
        r4 = "playback_timecode";
        r3.putInt(r4, r7);
        r4 = "playback_uptime";
        r3.putLong(r4, r8);
        r4 = r6.g;
        r4 = r4.obtainMessage(r5);
    L_0x0028:
        r2.length();	 Catch:{ Exception -> 0x002c }
        goto L_0x0028;
    L_0x002c:
        r5 = move-exception;
        r5 = 75;
        bЧ042704270427ЧЧ = r5;
    L_0x0031:
        r0 = r0 / r1;
        goto L_0x0031;
    L_0x0033:
        r0 = move-exception;
        r0 = 38;
        bЧ042704270427ЧЧ = r0;
        r4.setData(r3);
        r0 = r6.g;
        r0.sendMessage(r4);
        return;
    L_0x0041:
        r0 = move-exception;
        r0 = b0427ЧЧЧ0427Ч();
        bЧ042704270427ЧЧ = r0;
    L_0x0048:
        r2.length();	 Catch:{ Exception -> 0x0033 }
        goto L_0x0048;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.playHapticForPlaybackPosition(int, long):void");
    }

    public void prepareHapticPlayback(int i, int i2) {
        this.g.removeMessages(1);
        this.g.sendMessage(this.g.obtainMessage(1, i, i2));
    }

    public void quitHapticPlayback() {
        try {
            if (!this.g.sendEmptyMessage(9)) {
                if (((bЧ042704270427ЧЧ + bЧ0427Ч04270427Ч()) * bЧ042704270427ЧЧ) % bЧЧЧЧ0427Ч != b0427Ч0427Ч0427Ч) {
                    bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                    b0427Ч0427Ч0427Ч = 16;
                }
                this.b044404440444фф0444 = false;
                try {
                    c();
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void removePlaybackCallbacks() {
        synchronized (this.o) {
            Iterator it = this.z.iterator();
            while (it.hasNext()) {
                this.g.removeCallbacks((ccrcrr) it.next());
            }
            this.z.clear();
        }
    }

    public void run() {
        String str = null;
        Process.setThreadPriority(-19);
        Looper.prepare();
        this.j = Looper.myLooper();
        this.g = new rccrrr();
        while (true) {
            try {
                str.length();
            } catch (Exception e) {
                bЧ042704270427ЧЧ = b0427ЧЧЧ0427Ч();
                this.i = new HapticDownloadThread(this.f, this.g, this.B, this.C);
                this.i.start();
                this.b044404440444фф0444 = true;
                c();
                Looper.loop();
                return;
            }
        }
    }

    public void stopHapticPlayback() {
        try {
            this.g.sendEmptyMessage(4);
            int i = bЧ042704270427ЧЧ;
            switch ((i * (b0427042704270427ЧЧ + i)) % bЧЧЧЧ0427Ч) {
                case 0:
                    return;
                default:
                    bЧ042704270427ЧЧ = 35;
                    b0427Ч0427Ч0427Ч = 24;
                    return;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void syncUpdate(int r11, long r12) {
        /*
        r10 = this;
        r8 = 1;
        r1 = r10.n;
        monitor-enter(r1);
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x003f }
        r4 = (long) r11;	 Catch:{ all -> 0x003f }
        r6 = r2 - r12;
        r4 = r4 + r6;
        r0 = (int) r4;	 Catch:{ all -> 0x003f }
        r4 = r10.u;	 Catch:{ all -> 0x003f }
        r6 = r10.w;	 Catch:{ all -> 0x003f }
        r2 = r2 - r6;
        r2 = (int) r2;	 Catch:{ all -> 0x003f }
        r2 = r2 + r4;
        r2 = r0 - r2;
        r3 = 50;
        r4 = java.lang.Math.abs(r2);	 Catch:{ all -> 0x003f }
        if (r3 >= r4) goto L_0x003d;
    L_0x001e:
        r3 = r10.u;	 Catch:{ all -> 0x003f }
        r2 = r2 + r3;
        r10.u = r2;	 Catch:{ all -> 0x003f }
    L_0x0023:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x002b;
            case 1: goto L_0x0023;
            default: goto L_0x0027;
        };	 Catch:{ all -> 0x003f }
    L_0x0027:
        switch(r8) {
            case 0: goto L_0x0023;
            case 1: goto L_0x002b;
            default: goto L_0x002a;
        };	 Catch:{ all -> 0x003f }
    L_0x002a:
        goto L_0x0027;
    L_0x002b:
        r2 = r10.u;	 Catch:{ all -> 0x003f }
        r10.t = r2;	 Catch:{ all -> 0x003f }
        r2 = r10.g;	 Catch:{ all -> 0x003f }
        r3 = r10.g;	 Catch:{ all -> 0x003f }
        r4 = 1;
        r5 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r3.obtainMessage(r4, r0, r5);	 Catch:{ all -> 0x003f }
        r2.sendMessage(r0);	 Catch:{ all -> 0x003f }
    L_0x003d:
        monitor-exit(r1);	 Catch:{ all -> 0x003f }
        return;
    L_0x003f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x003f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.syncUpdate(int, long):void");
    }
}
