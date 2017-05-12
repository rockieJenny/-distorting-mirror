package com.immersion.hapticmediasdk;

import android.content.Context;
import android.webkit.URLUtil;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.utils.Log;
import java.io.File;

public class MediaPlaybackSDK extends HapticContentSDK {
    private static final String a = "HapticContentSDK";
    public static int b044A044Aъъ044Aъ = 1;
    public static int b044Aъъъ044Aъ = 13;
    public static int bъ044Aъъ044Aъ = 0;
    public static int bъъ044Aъ044Aъ = 2;
    private int b;

    public MediaPlaybackSDK(Context context) {
        try {
            super(0, context);
            if (((b044Aъъъ044Aъ + b044A044Aъъ044Aъ) * b044Aъъъ044Aъ) % bъъ044Aъ044Aъ != bъ044Aъъ044Aъ) {
                b044Aъъъ044Aъ = b044Aъ044Aъ044Aъ();
                bъ044Aъъ044Aъ = b044Aъ044Aъ044Aъ();
            }
            try {
                this.b = 400;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.lang.String r7) {
        /*
        r6 = this;
        r0 = 0;
        r1 = "https";
        r2 = "http";
        r1 = r7.replaceFirst(r1, r2);
        r2 = new rrrrrr.rrccrr;	 Catch:{ MalformedURLException -> 0x003f }
        r2.<init>(r6, r1);	 Catch:{ MalformedURLException -> 0x003f }
        r1 = new java.lang.Thread;
        r3 = "ping url";
        r1.<init>(r2, r3);
        r1.start();
        monitor-enter(r2);
        r1 = -100;
        r6.b = r1;	 Catch:{ all -> 0x004a }
    L_0x001d:
        r1 = r6.b;	 Catch:{ all -> 0x004a }
        if (r1 >= 0) goto L_0x0029;
    L_0x0021:
        r4 = 100;
        r2.wait(r4);	 Catch:{ InterruptedException -> 0x0027 }
        goto L_0x001d;
    L_0x0027:
        r1 = move-exception;
        goto L_0x001d;
    L_0x0029:
        switch(r0) {
            case 0: goto L_0x0030;
            case 1: goto L_0x0029;
            default: goto L_0x002c;
        };
    L_0x002c:
        switch(r0) {
            case 0: goto L_0x0030;
            case 1: goto L_0x0029;
            default: goto L_0x002f;
        };
    L_0x002f:
        goto L_0x002c;
    L_0x0030:
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r3 = r6.b;	 Catch:{ all -> 0x004a }
        if (r1 > r3) goto L_0x003d;
    L_0x0036:
        r1 = r6.b;	 Catch:{ all -> 0x004a }
        r3 = 399; // 0x18f float:5.59E-43 double:1.97E-321;
        if (r1 > r3) goto L_0x003d;
    L_0x003c:
        r0 = 1;
    L_0x003d:
        monitor-exit(r2);	 Catch:{ all -> 0x004a }
    L_0x003e:
        return r0;
    L_0x003f:
        r1 = move-exception;
        r2 = "HapticContentSDK";
        r1 = r1.getMessage();
        com.immersion.hapticmediasdk.utils.Log.e(r2, r1);
        goto L_0x003e;
    L_0x004a:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x004a }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaPlaybackSDK.a(java.lang.String):boolean");
    }

    private int b(String str) {
        if (str == null) {
            Log.e(a, "invalid local hapt file url: null");
            return -4;
        }
        File file = new File(str);
        if (!file.isFile()) {
            Log.e(a, "invalid local hapt file url: directory");
            return -4;
        } else if (file.canRead()) {
            this.mMediaTaskManager.setHapticsUrl(str, false);
            return this.mMediaTaskManager.transitToState(SDKStatus.INITIALIZED);
        } else {
            Log.e(a, "could not access local hapt file: permission denied");
            return -3;
        }
    }

    public static int b044A044A044Aъ044Aъ() {
        return 2;
    }

    public static int b044Aъ044Aъ044Aъ() {
        return 61;
    }

    public static /* synthetic */ int bллл043B043Bл(MediaPlaybackSDK mediaPlaybackSDK, int i) {
        if (((b044Aъ044Aъ044Aъ() + b044A044Aъъ044Aъ) * b044Aъ044Aъ044Aъ()) % bъъ044Aъ044Aъ != bъ044Aъъ044Aъ) {
            b044Aъъъ044Aъ = 4;
            bъ044Aъъ044Aъ = b044Aъ044Aъ044Aъ();
        }
        try {
            mediaPlaybackSDK.b = i;
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    public static int bъ044A044Aъ044Aъ() {
        return 1;
    }

    public int openHaptics(String str) {
        SDKStatus sDKStatus = getSDKStatus();
        if (sDKStatus != SDKStatus.STOPPED && sDKStatus != SDKStatus.NOT_INITIALIZED && sDKStatus != SDKStatus.INITIALIZED && sDKStatus != SDKStatus.STOPPED_DUE_TO_ERROR) {
            return -1;
        }
        int transitToState = this.mMediaTaskManager.transitToState(SDKStatus.NOT_INITIALIZED);
        if (transitToState != 0) {
            return transitToState;
        }
        if (!URLUtil.isValidUrl(str)) {
            return b(str);
        }
        if (URLUtil.isHttpUrl(str) || URLUtil.isHttpsUrl(str)) {
            if (a(str)) {
                this.mMediaTaskManager.setHapticsUrl(str, true);
                return this.mMediaTaskManager.transitToState(SDKStatus.INITIALIZED);
            }
            Log.e(a, "could not access hapt file url: Inaccessible URL");
            return -2;
        } else if (URLUtil.isFileUrl(str)) {
            return b(str);
        } else {
            Log.e(a, "could not access hapt file url: unsupposted protocol");
            return -5;
        }
    }
}
