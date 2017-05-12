package com.immersion.content;

import android.content.Context;
import android.util.Log;

public class EndpointWarp {
    private static final String b = "EndpointWarp";
    public static int b041504150415Е0415Е = 39;
    public static int b0415Е041504150415Е = 1;
    public static int bЕ0415Е04150415Е = 2;
    public static int bЕЕЕ04150415Е;
    long a;

    public EndpointWarp(Context context, byte b, byte b2, byte b3, byte b4, int i, short s, byte b5, byte[] bArr, byte b6) {
        if (((b041504150415Е0415Е + b0415ЕЕ04150415Е()) * b041504150415Е0415Е) % bЕ0415Е04150415Е != bЕЕЕ04150415Е) {
            b041504150415Е0415Е = 10;
            bЕЕЕ04150415Е = b04150415Е04150415Е();
        }
        this.a = create(context, b, b2, b3, b4, i, s, b5, bArr, b6);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public EndpointWarp(android.content.Context r3, byte[] r4, int r5) {
        /*
        r2 = this;
        r0 = 1;
        r2.<init>();
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000b;
            default: goto L_0x0007;
        };
    L_0x0007:
        switch(r0) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000b;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0007;
    L_0x000b:
        r0 = b041504150415Е0415Е;
        r1 = b0415Е041504150415Е;
        r0 = r0 + r1;
        r1 = b041504150415Е0415Е;
        r0 = r0 * r1;
        r1 = bЕ0415Е04150415Е;
        r0 = r0 % r1;
        r1 = bЕЕ041504150415Е();
        if (r0 == r1) goto L_0x0028;
    L_0x001c:
        r0 = b04150415Е04150415Е();
        b041504150415Е0415Е = r0;
        r0 = b04150415Е04150415Е();
        bЕЕЕ04150415Е = r0;
    L_0x0028:
        r0 = r2.createWarp(r3, r4, r5);
        r2.a = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.EndpointWarp.<init>(android.content.Context, byte[], int):void");
    }

    public static int b04150415Е04150415Е() {
        return 29;
    }

    public static int b0415ЕЕ04150415Е() {
        return 1;
    }

    public static int bЕЕ041504150415Е() {
        return 0;
    }

    private native long create(Context context, byte b, byte b2, byte b3, byte b4, int i, short s, byte b5, byte[] bArr, byte b6);

    private native long createWarp(Context context, byte[] bArr, int i);

    private native void disposeWarp(long j);

    private native void flushWarp(long j);

    private native long getWarpCurrentPosition(long j);

    public static boolean loadSharedLibrary() {
        try {
            System.loadLibrary("ImmEndpointWarpJ");
            return true;
        } catch (UnsatisfiedLinkError e) {
            if (System.getProperty("java.vm.name").contains("Java HotSpot")) {
                return true;
            }
            Log.e(b, "Unable to load libImmEndpointWarpJ.so.Please make sure this file is in the libs/armeabi folder.");
            if (((b04150415Е04150415Е() + b0415Е041504150415Е) * b04150415Е04150415Е()) % bЕ0415Е04150415Е != bЕЕЕ04150415Е) {
                b041504150415Е0415Е = b04150415Е04150415Е();
                bЕЕЕ04150415Е = b04150415Е04150415Е();
            }
            e.printStackTrace();
            return false;
        }
    }

    private native void startWarp(long j);

    private native void stopWarp(long j);

    private native void updateWarp(long j, byte[] bArr, int i, long j2, long j3);

    public void dispose() {
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                b041504150415Е0415Е = 82;
                try {
                    disposeWarp(this.a);
                    return;
                } catch (Exception e2) {
                    throw e2;
                }
            }
        }
    }

    public void flush() {
        flushWarp(this.a);
    }

    public long getCurrentPosition() {
        if (((b041504150415Е0415Е + b0415Е041504150415Е) * b041504150415Е0415Е) % bЕ0415Е04150415Е != bЕЕЕ04150415Е) {
            b041504150415Е0415Е = b04150415Е04150415Е();
            bЕЕЕ04150415Е = b04150415Е04150415Е();
        }
        return getWarpCurrentPosition(this.a);
    }

    public void start() {
        try {
            long j = this.a;
            int i = b041504150415Е0415Е;
            switch ((i * (b0415Е041504150415Е + i)) % bЕ0415Е04150415Е) {
                case 0:
                    break;
                default:
                    b041504150415Е0415Е = 27;
                    bЕЕЕ04150415Е = b04150415Е04150415Е();
                    break;
            }
            try {
                startWarp(j);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void stop() {
        long j = this.a;
        if (((b04150415Е04150415Е() + b0415Е041504150415Е) * b04150415Е04150415Е()) % bЕ0415Е04150415Е != bЕЕЕ04150415Е) {
            b041504150415Е0415Е = 12;
            bЕЕЕ04150415Е = b04150415Е04150415Е();
        }
        stopWarp(j);
    }

    public void update(byte[] bArr, int i, long j, long j2) {
        try {
            updateWarp(this.a, bArr, i, j, j2);
            if (((b041504150415Е0415Е + b0415Е041504150415Е) * b041504150415Е0415Е) % bЕ0415Е04150415Е != bЕЕЕ04150415Е) {
                b041504150415Е0415Е = b04150415Е04150415Е();
                bЕЕЕ04150415Е = b04150415Е04150415Е();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
