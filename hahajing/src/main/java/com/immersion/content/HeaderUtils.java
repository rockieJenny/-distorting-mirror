package com.immersion.content;

import java.nio.ByteBuffer;

public abstract class HeaderUtils {
    public static int b044Aъъ044A044Aъ = 1;
    public static int bъ044Aъ044A044Aъ = 2;
    public static int bъъъ044A044Aъ = 86;

    public static int b044A044Aъ044A044Aъ() {
        return 34;
    }

    public abstract int calculateBlockRate();

    public abstract int calculateBlockSize();

    public abstract int calculateByteOffsetIntoHapticData(int i);

    public abstract void dispose();

    public abstract String getContentUUID();

    public abstract int getEncryption();

    public abstract int getMajorVersionNumber();

    public abstract int getMinorVersionNumber();

    public abstract int getNumChannels();

    public abstract void setEncryptedHSI(ByteBuffer byteBuffer, int i);
}
