package com.immersion.hapticmediasdk.controllers;

import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Log;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileReaderFactory {
    private static final String a = "FileReaderFactory";
    public static int b044604460446ц0446ц = 2;
    public static int b0446ц044604460446ц = 0;
    public static int b0446ц0446ц0446ц = 72;
    public static int bц0446ц04460446ц = 1;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public FileReaderFactory() {
        /*
        r2 = this;
        r0 = b0446ц0446ц0446ц;
        r1 = bц04460446ц0446ц();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b044604460446ц0446ц;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001a;
            default: goto L_0x000e;
        };
    L_0x000e:
        r0 = bццц04460446ц();
        b0446ц0446ц0446ц = r0;
        r0 = bццц04460446ц();
        b044604460446ц0446ц = r0;
    L_0x001a:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0023;
            case 1: goto L_0x001a;
            default: goto L_0x001e;
        };
    L_0x001e:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x001a;
            case 1: goto L_0x0023;
            default: goto L_0x0022;
        };
    L_0x0022:
        goto L_0x001e;
    L_0x0023:
        r2.<init>();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.FileReaderFactory.<init>():void");
    }

    private static int a(String str, FileManager fileManager) {
        File hapticStorageFile;
        FileChannel fileChannel = null;
        String str2 = null;
        if (fileManager != null) {
            try {
                hapticStorageFile = fileManager.getHapticStorageFile(str);
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                    return 0;
                } catch (Exception e2) {
                    throw e2;
                }
            }
        } else if (str == null) {
            return 0;
        } else {
            hapticStorageFile = new File(str);
        }
        if (hapticStorageFile.length() == 0) {
            return -1;
        }
        if (null == null) {
            fileChannel = new RandomAccessFile(hapticStorageFile, "r").getChannel();
        }
        if (fileChannel == null) {
            return 0;
        }
        int a = a(fileChannel);
        fileChannel.close();
        while (true) {
            try {
                str2.length();
            } catch (Exception e3) {
                b0446ц0446ц0446ц = bццц04460446ц();
                return a;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int a(java.nio.channels.FileChannel r6) {
        /*
        r4 = 4;
        r0 = 0;
        r1 = 4;
        r1 = java.nio.ByteBuffer.allocate(r1);	 Catch:{ IOException -> 0x005d }
        r2 = java.nio.ByteOrder.LITTLE_ENDIAN;	 Catch:{ IOException -> 0x005d }
        r1.order(r2);	 Catch:{ IOException -> 0x005d }
        r2 = bццц04460446ц();
        r3 = bц04460446ц0446ц();
        r2 = r2 + r3;
        r3 = bццц04460446ц();
        r2 = r2 * r3;
        r3 = bц0446044604460446ц();
        r2 = r2 % r3;
        r3 = b0446ц044604460446ц;
        if (r2 == r3) goto L_0x002d;
    L_0x0023:
        r2 = bццц04460446ц();
        b0446ц0446ц0446ц = r2;
        r2 = 93;
        b0446ц044604460446ц = r2;
    L_0x002d:
        r2 = 0;
        r1.position(r2);	 Catch:{ IOException -> 0x005d }
        r2 = 16;
        r2 = r6.read(r1, r2);	 Catch:{ IOException -> 0x005d }
        if (r2 == r4) goto L_0x0062;
    L_0x0039:
        return r0;
    L_0x003a:
        switch(r0) {
            case 0: goto L_0x0041;
            case 1: goto L_0x003a;
            default: goto L_0x003d;
        };	 Catch:{ IOException -> 0x005d }
    L_0x003d:
        switch(r0) {
            case 0: goto L_0x0041;
            case 1: goto L_0x003a;
            default: goto L_0x0040;
        };	 Catch:{ IOException -> 0x005d }
    L_0x0040:
        goto L_0x003d;
    L_0x0041:
        r2 = 4;
        r3.position(r2);	 Catch:{ IOException -> 0x005d }
        r2 = r3.getInt();	 Catch:{ IOException -> 0x005d }
        r2 = r2 + 8;
        r2 = 20;
        r3.position(r2);	 Catch:{ IOException -> 0x005d }
        r2 = new com.immersion.content.HapticHeaderUtils;	 Catch:{ IOException -> 0x005d }
        r2.<init>();	 Catch:{ IOException -> 0x005d }
        r2.setEncryptedHSI(r3, r1);	 Catch:{ IOException -> 0x005d }
        r0 = r2.getMajorVersionNumber();	 Catch:{ IOException -> 0x005d }
        goto L_0x0039;
    L_0x005d:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0039;
    L_0x0062:
        r1.flip();	 Catch:{ IOException -> 0x005d }
        r1 = r1.getInt();	 Catch:{ IOException -> 0x005d }
        r2 = r1 + 28;
        r3 = java.nio.ByteBuffer.allocate(r2);	 Catch:{ IOException -> 0x005d }
        r4 = java.nio.ByteOrder.LITTLE_ENDIAN;	 Catch:{ IOException -> 0x005d }
        r3.order(r4);	 Catch:{ IOException -> 0x005d }
        r4 = 0;
        r4 = r6.read(r3, r4);	 Catch:{ IOException -> 0x005d }
        if (r4 == r2) goto L_0x003a;
    L_0x007c:
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.FileReaderFactory.a(java.nio.channels.FileChannel):int");
    }

    public static int b0446цц04460446ц() {
        return 0;
    }

    public static int bц0446044604460446ц() {
        return 2;
    }

    public static int bц04460446ц0446ц() {
        return 1;
    }

    public static int bццц04460446ц() {
        return 47;
    }

    public static IHapticFileReader getHapticFileReaderInstance(String str, FileManager fileManager) {
        try {
            switch (a(str, fileManager)) {
                case -1:
                    Log.i(a, "Can't retrieve Major version! Not enough bytes available yet.");
                    return null;
                case 1:
                    return new MemoryMappedFileReader(str, fileManager);
                case 2:
                    return new MemoryAlignedFileReader(str, fileManager, 2);
                case 3:
                    return new MemoryAlignedFileReader(str, fileManager, 3);
                default:
                    Log.e(a, "Unsupported HAPT file version");
                    while (true) {
                        switch (null) {
                            case null:
                                return null;
                            case 1:
                                break;
                            default:
                                while (true) {
                                    switch (null) {
                                        case null:
                                            return null;
                                        case 1:
                                            break;
                                        default:
                                    }
                                }
                        }
                    }
            }
        } catch (Error e) {
            e.printStackTrace();
            if (((b0446ц0446ц0446ц + bц0446ц04460446ц) * b0446ц0446ц0446ц) % b044604460446ц0446ц != b0446цц04460446ц()) {
                return null;
            }
            b0446ц0446ц0446ц = bццц04460446ц();
            bц0446ц04460446ц = bццц04460446ц();
            return null;
        }
        e.printStackTrace();
        if (((b0446ц0446ц0446ц + bц0446ц04460446ц) * b0446ц0446ц0446ц) % b044604460446ц0446ц != b0446цц04460446ц()) {
            return null;
        }
        b0446ц0446ц0446ц = bццц04460446ц();
        bц0446ц04460446ц = bццц04460446ц();
        return null;
    }
}
