package com.immersion.content;

import java.nio.ByteBuffer;

public class HapticHeaderUtils extends HeaderUtils {
    private static final String b = "HapticHeaderUtils";
    public static int b042104210421С04210421 = 33;
    public static int b04210421С042104210421 = 0;
    public static int b0421СС042104210421 = 2;
    public static int bССС042104210421 = 1;
    long a;
    private byte[] c;
    private int d;

    public HapticHeaderUtils() {
        int i = b042104210421С04210421;
        switch ((i * (bССС042104210421 + i)) % b0421СС042104210421) {
            case 0:
                break;
            default:
                b042104210421С04210421 = 43;
                bССС042104210421 = bС0421С042104210421();
                break;
        }
        try {
        } catch (Exception e) {
            throw e;
        }
    }

    public static int b0421С0421042104210421() {
        return 1;
    }

    public static int bС0421С042104210421() {
        return 80;
    }

    public static int bСС0421042104210421() {
        return 0;
    }

    private native int calculateBlockRateNative(long j);

    private native int calculateBlockSizeNative(long j);

    private native int calculateByteOffsetIntoHapticDataNative(long j, int i);

    private native void disposeNative(long j);

    private native String getContentIdNative(long j);

    private native int getEncryptionNative(long j);

    private native int getMajorVersionNumberNative(long j);

    private native int getMinorVersionNumberNative(long j);

    private native int getNumChannelsNative(long j);

    private native long init(byte[] bArr, int i);

    public int calculateBlockRate() {
        if (((b042104210421С04210421 + b0421С0421042104210421()) * b042104210421С04210421) % b0421СС042104210421 != bСС0421042104210421()) {
            b042104210421С04210421 = bС0421С042104210421();
            b04210421С042104210421 = 12;
        }
        return calculateBlockRateNative(this.a);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int calculateBlockSize() {
        /*
        r2 = this;
        r0 = b042104210421С04210421;
        r1 = bССС042104210421;
        r0 = r0 + r1;
        r1 = b042104210421С04210421;
        r0 = r0 * r1;
        r1 = b0421СС042104210421;
        r0 = r0 % r1;
        r1 = b04210421С042104210421;
        if (r0 == r1) goto L_0x0019;
    L_0x000f:
        r0 = bС0421С042104210421();
        b042104210421С04210421 = r0;
        r0 = 84;
        b04210421С042104210421 = r0;
    L_0x0019:
        r0 = r2.a;
        r0 = r2.calculateBlockSizeNative(r0);
    L_0x001f:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0028;
            default: goto L_0x0023;
        };
    L_0x0023:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0028;
            case 1: goto L_0x001f;
            default: goto L_0x0027;
        };
    L_0x0027:
        goto L_0x0023;
    L_0x0028:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.calculateBlockSize():int");
    }

    public int calculateByteOffsetIntoHapticData(int i) {
        long j = this.a;
        int bС0421С042104210421 = bС0421С042104210421();
        switch ((bС0421С042104210421 * (bССС042104210421 + bС0421С042104210421)) % b0421СС042104210421) {
            case 0:
                break;
            default:
                b042104210421С04210421 = 89;
                b04210421С042104210421 = bС0421С042104210421();
                break;
        }
        return calculateByteOffsetIntoHapticDataNative(j, i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispose() {
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
        r0 = b042104210421С04210421;
        r1 = b0421С0421042104210421();
        r0 = r0 + r1;
        r1 = b042104210421С04210421;
        r0 = r0 * r1;
        r1 = b0421СС042104210421;
        r0 = r0 % r1;
        r1 = b04210421С042104210421;
        if (r0 == r1) goto L_0x0023;
    L_0x0019:
        r0 = bС0421С042104210421();
        b042104210421С04210421 = r0;
        r0 = 92;
        b04210421С042104210421 = r0;
    L_0x0023:
        r0 = r2.a;
        r2.disposeNative(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.dispose():void");
    }

    public String getContentUUID() {
        try {
            long j = this.a;
            if (((b042104210421С04210421 + b0421С0421042104210421()) * b042104210421С04210421) % b0421СС042104210421 != bСС0421042104210421()) {
                b042104210421С04210421 = 46;
                b04210421С042104210421 = 43;
            }
            return getContentIdNative(j);
        } catch (Exception e) {
            throw e;
        }
    }

    public int getEncryption() {
        long j = this.a;
        if (((b042104210421С04210421 + bССС042104210421) * b042104210421С04210421) % b0421СС042104210421 != b04210421С042104210421) {
            b042104210421С04210421 = bС0421С042104210421();
            b04210421С042104210421 = bС0421С042104210421();
        }
        return getEncryptionNative(j);
    }

    public int getMajorVersionNumber() {
        return getMajorVersionNumberNative(this.a);
    }

    public int getMinorVersionNumber() {
        if (((b042104210421С04210421 + b0421С0421042104210421()) * b042104210421С04210421) % b0421СС042104210421 != b04210421С042104210421) {
            b042104210421С04210421 = bС0421С042104210421();
            b04210421С042104210421 = bС0421С042104210421();
        }
        try {
            try {
                return getMinorVersionNumberNative(this.a);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public int getNumChannels() {
        if (((b042104210421С04210421 + b0421С0421042104210421()) * b042104210421С04210421) % b0421СС042104210421 != b04210421С042104210421) {
            b042104210421С04210421 = 92;
            b04210421С042104210421 = bС0421С042104210421();
        }
        try {
            return getNumChannelsNative(this.a);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setEncryptedHSI(ByteBuffer byteBuffer, int i) {
        this.d = i;
        this.c = new byte[this.d];
        byteBuffer.get(this.c, 0, this.d);
        this.a = init(this.c, this.d);
    }
}
