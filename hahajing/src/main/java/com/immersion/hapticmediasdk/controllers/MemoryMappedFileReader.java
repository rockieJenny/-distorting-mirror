package com.immersion.hapticmediasdk.controllers;

import com.immersion.hapticmediasdk.models.HapticFileInformation;
import com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Log;
import com.immersion.hapticmediasdk.utils.Profiler;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import rrrrrr.ccrrrr;

public class MemoryMappedFileReader implements IHapticFileReader {
    private static final String a = "MemoryMappedFileReader";
    public static int b044A044Aъъъъ = 1;
    public static int bъ044A044Aъъъ = 55;
    public static int bъ044Aъъъъ = 34;
    public static int bъъ044Aъъъ = 2;
    private static int g = 40;
    private static int h = 0;
    private static final int j = 4096;
    private static final int k = 12288;
    private File b;
    private FileChannel c;
    private ccrrrr d;
    private ccrrrr e;
    private int f;
    private HapticFileInformation i;
    private String l;
    private final Profiler m;
    private FileManager n;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r2 = 0;
    L_0x0001:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0009;
            default: goto L_0x0005;
        };
    L_0x0005:
        switch(r2) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0001;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0005;
    L_0x0009:
        r0 = 40;
        g = r0;
        r0 = bъ044A044Aъъъ;
        r1 = b044A044Aъъъъ;
        r0 = r0 + r1;
        r1 = bъ044A044Aъъъ;
        r0 = r0 * r1;
        r1 = bъъ044Aъъъ;
        r0 = r0 % r1;
        r1 = bъ044Aъъъъ;
        if (r0 == r1) goto L_0x0024;
    L_0x001c:
        r0 = 55;
        bъ044A044Aъъъ = r0;
        r0 = 34;
        bъ044Aъъъъ = r0;
    L_0x0024:
        h = r2;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.<clinit>():void");
    }

    public MemoryMappedFileReader(String str, FileManager fileManager) {
        try {
            if (((b044Aъъъъъ() + b044A044Aъъъъ) * b044Aъъъъъ()) % bъъ044Aъъъ != bъ044Aъъъъ) {
                bъ044Aъъъъ = b044Aъъъъъ();
            }
            this.m = new Profiler();
            try {
                this.l = str;
                this.n = fileManager;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(rrrrrr.ccrrrr r4, int r5) {
        /*
        r3 = this;
        r1 = 1;
        r0 = r4.mHapticDataOffset;
    L_0x0003:
        switch(r1) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r1) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        r0 = r5 - r0;
        r1 = bъ044A044Aъъъ;
        r2 = b044Aъ044Aъъъ();
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bъъ044Aъъъ;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x0026;
            default: goto L_0x001a;
        };
    L_0x001a:
        r1 = b044Aъъъъъ();
        bъ044A044Aъъъ = r1;
        r1 = b044Aъъъъъ();
        bъ044Aъъъъ = r1;
    L_0x0026:
        r1 = r4.mMappedByteBuffer;
        r1 = r1.capacity();
        r0 = r0 % r1;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.a(rrrrrr.ccrrrr, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a() {
        /*
        r5 = this;
        r0 = 0;
        r2 = 0;
        r1 = r5.i;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        if (r1 == 0) goto L_0x0033;
    L_0x0006:
        r0 = 1;
    L_0x0007:
        return r0;
    L_0x0008:
        r1 = r5.b;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        if (r1 != 0) goto L_0x0016;
    L_0x000c:
        r1 = r5.n;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        r3 = r5.l;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        r1 = r1.getHapticStorageFile(r3);	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        r5.b = r1;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
    L_0x0016:
        r1 = r5.c;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        if (r1 != 0) goto L_0x002a;
    L_0x001a:
        r3 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        r1 = r5.b;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        r4 = "r";
        r3.<init>(r1, r4);	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        r1 = r3.getChannel();	 Catch:{ FileNotFoundException -> 0x0058, Exception -> 0x0053 }
        r5.c = r1;	 Catch:{ FileNotFoundException -> 0x0058, Exception -> 0x0053 }
        r2 = r3;
    L_0x002a:
        r1 = r5.c;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        if (r1 == 0) goto L_0x0007;
    L_0x002e:
        r0 = r5.b();	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        goto L_0x0007;
    L_0x0033:
        r1 = 12288; // 0x3000 float:1.7219E-41 double:6.071E-320;
        r1 = r5.a(r1);	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0053 }
        if (r1 == 0) goto L_0x0008;
    L_0x003b:
        goto L_0x0007;
    L_0x003c:
        r1 = move-exception;
    L_0x003d:
        r3 = "MemoryMappedFileReader";
        r1 = r1.getMessage();
        com.immersion.hapticmediasdk.utils.Log.e(r3, r1);
        r1 = r5.n;
        r1.closeCloseable(r2);
        r1 = r5.n;
        r2 = r5.c;
        r1.closeCloseable(r2);
        goto L_0x0007;
    L_0x0053:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0007;
    L_0x0058:
        r1 = move-exception;
        r2 = r3;
        goto L_0x003d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.a():boolean");
    }

    private boolean a(int i) {
        if (this.f < i) {
            return false;
        }
        if (((b044Aъъъъъ() + b044A044Aъъъъ) * b044Aъъъъъ()) % bъъ044Aъъъ == bъ044Aъъъъ) {
            return true;
        }
        bъ044A044Aъъъ = 58;
        bъ044Aъъъъ = 75;
        return true;
    }

    private int b(int i) {
        int sampleHertz = i / (1000 / this.i.getSampleHertz());
        if (((bъ044A044Aъъъ + b044A044Aъъъъ) * bъ044A044Aъъъ) % bъъ044Aъъъ != bъ044Aъъъъ) {
            bъ044A044Aъъъ = 77;
            bъ044Aъъъъ = 64;
        }
        int bitsPerSample = this.i.getBitsPerSample();
        int numberOfChannels = this.i.getNumberOfChannels();
        float f = (float) ((bitsPerSample * numberOfChannels) / 8);
        bitsPerSample = (int) f;
        if (((float) (bitsPerSample * numberOfChannels)) / 8.0f > f) {
            bitsPerSample++;
        }
        return bitsPerSample * sampleHertz;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b() {
        /*
        r8 = this;
        r2 = 1;
        r6 = 4;
        r1 = 0;
        r0 = 4;
        r0 = java.nio.ByteBuffer.allocate(r0);	 Catch:{ IOException -> 0x0105 }
        r3 = java.nio.ByteOrder.LITTLE_ENDIAN;	 Catch:{ IOException -> 0x0105 }
        r0.order(r3);	 Catch:{ IOException -> 0x0105 }
        r3 = 0;
        r0.position(r3);	 Catch:{ IOException -> 0x0105 }
        r3 = r8.c;	 Catch:{ IOException -> 0x0105 }
        r4 = 16;
        r3 = r3.read(r0, r4);	 Catch:{ IOException -> 0x0105 }
        if (r3 == r6) goto L_0x001c;
    L_0x001b:
        return r1;
    L_0x001c:
        r0.flip();	 Catch:{ IOException -> 0x0105 }
        r0 = r0.getInt();	 Catch:{ IOException -> 0x0105 }
        r0 = r0 + 28;
        r3 = java.nio.ByteBuffer.allocate(r0);	 Catch:{ IOException -> 0x0105 }
        r4 = java.nio.ByteOrder.LITTLE_ENDIAN;	 Catch:{ IOException -> 0x0105 }
        r3.order(r4);	 Catch:{ IOException -> 0x0105 }
        r4 = r8.c;	 Catch:{ IOException -> 0x0105 }
        r6 = 0;
        r4 = r4.read(r3, r6);	 Catch:{ IOException -> 0x0105 }
    L_0x0036:
        switch(r1) {
            case 0: goto L_0x003d;
            case 1: goto L_0x0036;
            default: goto L_0x0039;
        };	 Catch:{ IOException -> 0x0105 }
    L_0x0039:
        switch(r2) {
            case 0: goto L_0x0036;
            case 1: goto L_0x003d;
            default: goto L_0x003c;
        };	 Catch:{ IOException -> 0x0105 }
    L_0x003c:
        goto L_0x0039;
    L_0x003d:
        if (r4 != r0) goto L_0x001b;
    L_0x003f:
        r3.flip();	 Catch:{ IOException -> 0x0105 }
        r4 = new com.immersion.hapticmediasdk.models.HapticFileInformation$Builder;	 Catch:{ IOException -> 0x0105 }
        r4.<init>();	 Catch:{ IOException -> 0x0105 }
        r0 = r8.b;	 Catch:{ IOException -> 0x0105 }
        r0 = r0.getAbsolutePath();	 Catch:{ IOException -> 0x0105 }
        r4.setFilePath(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = 4;
        r3.position(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.getInt();	 Catch:{ IOException -> 0x0105 }
        r0 = r0 + 8;
        r4.setTotalFileLength(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = 20;
        r3.position(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r4.setMajorVersion(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r4.setMinorVersion(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r4.setEncoding(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = 24;
        r3.position(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.getInt();	 Catch:{ IOException -> 0x0105 }
        r4.setSampleHertz(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r5 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r5 = r5 << 8;
        r0 = r0 | r5;
        r4.setBitsPerSample(r0);	 Catch:{ IOException -> 0x0105 }
        r5 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r4.setNumberOfChannels(r5);	 Catch:{ IOException -> 0x0105 }
        r6 = new int[r5];	 Catch:{ IOException -> 0x0105 }
        r0 = r1;
    L_0x009b:
        if (r0 >= r5) goto L_0x00a6;
    L_0x009d:
        r7 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r6[r0] = r7;	 Catch:{ IOException -> 0x0105 }
        r0 = r0 + 1;
        goto L_0x009b;
    L_0x00a6:
        r4.setActuatorArray(r6);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.get();	 Catch:{ IOException -> 0x0105 }
        r4.setCompressionScheme(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.position();	 Catch:{ IOException -> 0x0105 }
        r0 = r0 + 4;
        r3.position(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.getInt();	 Catch:{ IOException -> 0x0105 }
        r4.setHapticDataLength(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r3.position();	 Catch:{ IOException -> 0x0105 }
        r4.setHapticDataStartByteOffset(r0);	 Catch:{ IOException -> 0x0105 }
        r0 = r4.build();	 Catch:{ IOException -> 0x0105 }
        r8.i = r0;	 Catch:{ IOException -> 0x0105 }
        r0 = g;	 Catch:{ IOException -> 0x0105 }
        r3 = r8.i;	 Catch:{ IOException -> 0x0105 }
        r3 = r3.getSampleHertz();	 Catch:{ IOException -> 0x0105 }
        r0 = r0 * r3;
        r0 = r0 / 1000;
        r3 = bъ044A044Aъъъ;
        r4 = b044Aъ044Aъъъ();
        r4 = r4 + r3;
        r3 = r3 * r4;
        r4 = bъъ044Aъъъ;
        r3 = r3 % r4;
        switch(r3) {
            case 0: goto L_0x00f0;
            default: goto L_0x00e6;
        };
    L_0x00e6:
        r3 = b044Aъъъъъ();
        bъ044A044Aъъъ = r3;
        r3 = 51;
        bъ044Aъъъъ = r3;
    L_0x00f0:
        r3 = r8.i;	 Catch:{ IOException -> 0x0105 }
        r3 = r3.getBitsPerSample();	 Catch:{ IOException -> 0x0105 }
        r0 = r0 * r3;
        r3 = r8.i;	 Catch:{ IOException -> 0x0105 }
        r3 = r3.getNumberOfChannels();	 Catch:{ IOException -> 0x0105 }
        r0 = r0 * r3;
        r0 = r0 / 8;
        h = r0;	 Catch:{ IOException -> 0x0105 }
        r1 = r2;
        goto L_0x001b;
    L_0x0105:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.b():boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(rrrrrr.ccrrrr r3, int r4) {
        /*
        r0 = 1;
        r1 = 0;
        r2 = r3.mHapticDataOffset;
        if (r4 >= r2) goto L_0x001f;
    L_0x0006:
        r1 = bъ044A044Aъъъ;
        r2 = b044A044Aъъъъ;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bъъ044Aъъъ;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x001e;
            default: goto L_0x0012;
        };
    L_0x0012:
        r1 = b044Aъъъъъ();
        bъ044A044Aъъъ = r1;
        r1 = b044Aъъъъъ();
        bъ044Aъъъъ = r1;
    L_0x001e:
        return r0;
    L_0x001f:
        switch(r1) {
            case 0: goto L_0x0026;
            case 1: goto L_0x001f;
            default: goto L_0x0022;
        };
    L_0x0022:
        switch(r0) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0026;
            default: goto L_0x0025;
        };
    L_0x0025:
        goto L_0x0022;
    L_0x0026:
        r0 = r1;
        goto L_0x001e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.b(rrrrrr.ccrrrr, int):boolean");
    }

    public static int b044Aъ044A044Aъъ() {
        return 0;
    }

    public static int b044Aъ044Aъъъ() {
        return 1;
    }

    public static int b044Aъъъъъ() {
        return 73;
    }

    public static int bъ044A044A044Aъъ() {
        return 2;
    }

    private int c(int i) {
        try {
            HapticFileInformation hapticFileInformation = this.i;
            if (((bъ044A044Aъъъ + b044A044Aъъъъ) * bъ044A044Aъъъ) % bъъ044Aъъъ != bъ044Aъъъъ) {
                bъ044A044Aъъъ = 98;
                bъ044Aъъъъ = 21;
            }
            try {
                return hapticFileInformation.getHapticDataStartByteOffset() + b(i);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private void c() throws NotEnoughHapticBytesAvailableException, IOException {
        try {
            if (this.e != null) {
                int i = this.e.mHapticDataOffset + 4096;
                try {
                    this.d = this.e;
                    if (((b044Aъъъъъ() + b044A044Aъъъъ) * b044Aъъъъъ()) % bъ044A044A044Aъъ() != b044Aъ044A044Aъъ()) {
                        bъ044A044Aъъъ = 80;
                        bъ044Aъъъъ = 68;
                    }
                    this.e = d(i);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private static boolean c(ccrrrr rrrrrr_ccrrrr, int i) {
        if (i < rrrrrr_ccrrrr.mHapticDataOffset + rrrrrr_ccrrrr.mMappedByteBuffer.capacity()) {
            return false;
        }
        if (((bъ044A044Aъъъ + b044A044Aъъъъ) * bъ044A044Aъъъ) % bъъ044Aъъъ == bъ044Aъъъъ) {
            return true;
        }
        bъ044A044Aъъъ = b044Aъъъъъ();
        bъ044Aъъъъ = b044Aъъъъъ();
        return true;
    }

    private ccrrrr d(int i) throws IOException, NotEnoughHapticBytesAvailableException {
        this.m.startTiming();
        if (i < this.i.getHapticDataLength()) {
            int hapticDataStartByteOffset = this.i.getHapticDataStartByteOffset() + i;
            int hapticDataLength = i + 4096 <= this.i.getHapticDataLength() ? 4096 : this.i.getHapticDataLength() - i;
            if (i + hapticDataLength > this.f) {
                throw new NotEnoughHapticBytesAvailableException("Not enough bytes available yet.");
            }
            MappedByteBuffer map = this.c.map(MapMode.READ_ONLY, (long) hapticDataStartByteOffset, (long) hapticDataLength);
            if (map != null) {
                map.order(ByteOrder.LITTLE_ENDIAN);
                ccrrrr rrrrrr_ccrrrr = new ccrrrr();
                rrrrrr_ccrrrr.mMappedByteBuffer = map;
                rrrrrr_ccrrrr.mHapticDataOffset = i;
                return rrrrrr_ccrrrr;
            }
        }
        return null;
    }

    private void d() {
        Log.d(a, "%%%%%%%%%%% logBufferState %%%%%%%%%%%");
        if (this.d != null) {
            Log.d(a, "mCurrentMMW capacity = " + this.d.mMappedByteBuffer.capacity());
            Log.d(a, "mCurrentMMW position = " + this.d.mMappedByteBuffer.position());
            Log.d(a, "mCurrentMMW remaining = " + this.d.mMappedByteBuffer.remaining());
            Log.d(a, "mCurrentMMW mHapticDataOffset = " + this.d.mHapticDataOffset);
            String str = a;
            StringBuilder append = new StringBuilder().append("mCurrentMMW mHapticDataOffset + position = ");
            ccrrrr rrrrrr_ccrrrr = this.d;
            if (((b044Aъъъъъ() + b044A044Aъъъъ) * b044Aъъъъъ()) % bъ044A044A044Aъъ() != b044Aъ044A044Aъъ()) {
                bъ044A044Aъъъ = b044Aъъъъъ();
                bъ044Aъъъъ = b044Aъъъъъ();
            }
            Log.d(str, append.append(rrrrrr_ccrrrr.mHapticDataOffset + this.d.mMappedByteBuffer.position()).toString());
        } else {
            Log.d(a, "mCurrentMMW is null");
        }
        Log.d(a, "--------------------------------------");
        if (this.e != null) {
            Log.d(a, "mNextMMW capacity = " + this.e.mMappedByteBuffer.capacity());
            Log.d(a, "mNextMMW position = " + this.e.mMappedByteBuffer.position());
            Log.d(a, "mNextMMW remaining = " + this.e.mMappedByteBuffer.remaining());
            Log.d(a, "mNextMMW mHapticDataOffset = " + this.e.mHapticDataOffset);
            Log.d(a, "mNextMMW mHapticDataOffset + position = " + (this.e.mHapticDataOffset + this.e.mMappedByteBuffer.position()));
        } else {
            Log.d(a, "mNextMMW is null");
        }
        Log.d(a, "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }

    private static boolean d(ccrrrr rrrrrr_ccrrrr, int i) {
        try {
            if (!b(rrrrrr_ccrrrr, i)) {
                boolean c = c(rrrrrr_ccrrrr, i);
                if (((bъ044A044Aъъъ + b044A044Aъъъъ) * bъ044A044Aъъъ) % bъъ044Aъъъ != bъ044Aъъъъ) {
                    bъ044A044Aъъъ = 52;
                    bъ044Aъъъъ = 7;
                }
                if (!c) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    private static boolean e(ccrrrr rrrrrr_ccrrrr, int i) {
        int i2 = bъ044A044Aъъъ;
        switch ((i2 * (b044Aъ044Aъъъ() + i2)) % bъъ044Aъъъ) {
            case 0:
                break;
            default:
                bъ044A044Aъъъ = 57;
                bъ044Aъъъъ = 27;
                break;
        }
        try {
            try {
                return c(rrrrrr_ccrrrr, h + i);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean bufferAtPlaybackPosition(int r6) {
        /*
        r5 = this;
        r1 = 1;
        r0 = 0;
        r2 = -1;
        r3 = r5.a();	 Catch:{ Exception -> 0x0065 }
        if (r3 != 0) goto L_0x000a;
    L_0x0009:
        return r0;
    L_0x000a:
        r3 = r5.b(r6);	 Catch:{ Exception -> 0x0065 }
        r4 = r5.d;	 Catch:{ Exception -> 0x0065 }
        if (r4 == 0) goto L_0x001a;
    L_0x0012:
        r4 = r5.d;	 Catch:{ Exception -> 0x0065 }
        r4 = d(r4, r3);	 Catch:{ Exception -> 0x0065 }
        if (r4 == 0) goto L_0x006b;
    L_0x001a:
        r4 = r5.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        if (r4 == 0) goto L_0x002e;
    L_0x001e:
        r4 = r5.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        r4 = d(r4, r3);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        if (r4 != 0) goto L_0x002e;
    L_0x0026:
        r4 = r5.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        r4 = e(r4, r3);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        if (r4 == 0) goto L_0x0054;
    L_0x002e:
        r2 = r5.d;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        if (r2 == 0) goto L_0x0038;
    L_0x0032:
        r2 = r5.d;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        r2 = r2.mHapticDataOffset;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        if (r2 == r3) goto L_0x003e;
    L_0x0038:
        r2 = r5.d(r3);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        r5.d = r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
    L_0x003e:
        r2 = r5.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        if (r2 == 0) goto L_0x004a;
    L_0x0042:
        r2 = r5.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        r2 = r2.mHapticDataOffset;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        r4 = r3 + 4096;
        if (r2 == r4) goto L_0x0052;
    L_0x004a:
        r2 = r3 + 4096;
        r2 = r5.d(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
        r5.e = r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
    L_0x0052:
        r0 = r1;
        goto L_0x0009;
    L_0x0054:
        r5.c();	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x005a, IOException -> 0x007e }
    L_0x0057:
        r0 = new int[r2];	 Catch:{ Exception -> 0x0067 }
        goto L_0x0057;
    L_0x005a:
        r1 = move-exception;
        r2 = "MemoryMappedFileReader";
        r1 = r1.getMessage();	 Catch:{ Exception -> 0x0065 }
        com.immersion.hapticmediasdk.utils.Log.w(r2, r1);	 Catch:{ Exception -> 0x0065 }
        goto L_0x0009;
    L_0x0065:
        r0 = move-exception;
        throw r0;
    L_0x0067:
        r0 = move-exception;
        r0 = 5;
        bъ044A044Aъъъ = r0;
    L_0x006b:
        r0 = r5.d;	 Catch:{ Exception -> 0x0083 }
        if (r0 == 0) goto L_0x007c;
    L_0x006f:
        r0 = r5.d;	 Catch:{ Exception -> 0x0065 }
        r0 = r0.mMappedByteBuffer;	 Catch:{ Exception -> 0x0065 }
        r2 = r5.d;	 Catch:{ Exception -> 0x0083 }
        r2 = r5.a(r2, r3);	 Catch:{ Exception -> 0x0083 }
        r0.position(r2);	 Catch:{ Exception -> 0x0083 }
    L_0x007c:
        r0 = r1;
        goto L_0x0009;
    L_0x007e:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ Exception -> 0x0083 }
        goto L_0x0009;
    L_0x0083:
        r0 = move-exception;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.bufferAtPlaybackPosition(int):boolean");
    }

    public void close() {
        int i = bъ044A044Aъъъ;
        switch ((i * (b044Aъ044Aъъъ() + i)) % bъъ044Aъъъ) {
            case 0:
                break;
            default:
                bъ044A044Aъъъ = b044Aъъъъъ();
                bъ044Aъъъъ = 35;
                break;
        }
        try {
            this.n.closeCloseable(this.c);
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getBlockOffset(long r3) {
        /*
        r2 = this;
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
        r0 = bъ044A044Aъъъ;
        r1 = b044A044Aъъъъ;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bъъ044Aъъъ;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001d;
            default: goto L_0x0015;
        };
    L_0x0015:
        r0 = 89;
        bъ044A044Aъъъ = r0;
        r0 = 47;
        bъ044Aъъъъ = r0;
    L_0x001d:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.getBlockOffset(long):long");
    }

    public int getBlockSizeMS() {
        try {
            int i = g;
            if (((bъ044A044Aъъъ + b044A044Aъъъъ) * bъ044A044Aъъъ) % bъъ044Aъъъ != bъ044Aъъъъ) {
                bъ044A044Aъъъ = b044Aъъъъъ();
                bъ044Aъъъъ = b044Aъъъъъ();
            }
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getBufferForPlaybackPosition(int r7) throws com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException {
        /*
        r6 = this;
        r0 = 0;
        r4 = 0;
        r1 = r6.d;
        if (r1 != 0) goto L_0x0057;
    L_0x0006:
        return r0;
    L_0x0007:
        r2 = r6.d;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.position();
        r1 = r1 + r2;
        r2 = r6.i;
        r2 = r2.getHapticDataLength();
        if (r1 >= r2) goto L_0x0006;
    L_0x0018:
        r1 = h;	 Catch:{ Exception -> 0x007c }
        r1 = new byte[r1];	 Catch:{ Exception -> 0x007c }
        r2 = h;	 Catch:{ Exception -> 0x007c }
        r3 = r6.d;	 Catch:{ Exception -> 0x007c }
        r3 = r3.mMappedByteBuffer;	 Catch:{ Exception -> 0x007c }
        r3 = r3.remaining();	 Catch:{ Exception -> 0x007c }
        if (r2 < r3) goto L_0x008a;
    L_0x0028:
        r2 = r6.d;	 Catch:{ Exception -> 0x007c }
        r2 = r2.mMappedByteBuffer;	 Catch:{ Exception -> 0x007c }
        r3 = r2.remaining();	 Catch:{ Exception -> 0x007c }
        r2 = h;	 Catch:{ Exception -> 0x007c }
        r2 = r2 - r3;
        r4 = r6.d;	 Catch:{ Exception -> 0x007c }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x007c }
        r5 = 0;
        r4.get(r1, r5, r3);	 Catch:{ Exception -> 0x007c }
        if (r2 <= 0) goto L_0x0052;
    L_0x003d:
        r4 = r6.e;	 Catch:{ Exception -> 0x007c }
        if (r4 == 0) goto L_0x0052;
    L_0x0041:
        r4 = r6.e;	 Catch:{ Exception -> 0x007c }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x007c }
        r4 = r4.remaining();	 Catch:{ Exception -> 0x007c }
        if (r4 < r2) goto L_0x0081;
    L_0x004b:
        r4 = r6.e;	 Catch:{ Exception -> 0x007c }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x007c }
        r4.get(r1, r3, r2);	 Catch:{ Exception -> 0x007c }
    L_0x0052:
        r6.c();	 Catch:{ Exception -> 0x007c }
    L_0x0055:
        r0 = r1;
        goto L_0x0006;
    L_0x0057:
        r1 = r6.d;
        r2 = bъ044A044Aъъъ;
        r3 = b044A044Aъъъъ;
        r2 = r2 + r3;
        r3 = bъ044A044Aъъъ;
        r2 = r2 * r3;
        r3 = bъъ044Aъъъ;
        r2 = r2 % r3;
        r3 = bъ044Aъъъъ;
        if (r2 == r3) goto L_0x0072;
    L_0x0068:
        r2 = b044Aъъъъъ();
        bъ044A044Aъъъ = r2;
        r2 = 47;
        bъ044Aъъъъ = r2;
    L_0x0072:
        r1 = r1.mHapticDataOffset;
    L_0x0074:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0074;
            case 1: goto L_0x0007;
            default: goto L_0x0078;
        };
    L_0x0078:
        switch(r4) {
            case 0: goto L_0x0007;
            case 1: goto L_0x0074;
            default: goto L_0x007b;
        };
    L_0x007b:
        goto L_0x0078;
    L_0x007c:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0006;
    L_0x0081:
        r2 = r6.e;	 Catch:{ Exception -> 0x007c }
        r2 = r2.mMappedByteBuffer;	 Catch:{ Exception -> 0x007c }
        r2 = r2.remaining();	 Catch:{ Exception -> 0x007c }
        goto L_0x004b;
    L_0x008a:
        r2 = r6.d;	 Catch:{ Exception -> 0x007c }
        r2 = r2.mMappedByteBuffer;	 Catch:{ Exception -> 0x007c }
        r3 = 0;
        r4 = h;	 Catch:{ Exception -> 0x007c }
        r2.get(r1, r3, r4);	 Catch:{ Exception -> 0x007c }
        goto L_0x0055;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.getBufferForPlaybackPosition(int):byte[]");
    }

    public byte[] getEncryptedHapticHeader() {
        return new byte[0];
    }

    public int getHapticBlockIndex(long j) {
        return 0;
    }

    public HapticFileInformation getHapticFileInformation() {
        return this.i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setBlockSizeMS(int r3) {
        /*
        r2 = this;
        r0 = 1;
        g = r3;
    L_0x0003:
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        r0 = b044Aъъъъъ();
        r1 = b044Aъ044Aъъъ();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bъъ044Aъъъ;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001e;
            default: goto L_0x001a;
        };
    L_0x001a:
        r0 = 15;
        bъ044Aъъъъ = r0;
    L_0x001e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.setBlockSizeMS(int):void");
    }

    public void setBytesAvailable(int i) {
        this.f = i;
        if (((bъ044A044Aъъъ + b044Aъ044Aъъъ()) * bъ044A044Aъъъ) % bъъ044Aъъъ != b044Aъ044A044Aъъ()) {
            bъ044A044Aъъъ = 62;
            bъ044Aъъъъ = 4;
        }
        a();
    }
}
