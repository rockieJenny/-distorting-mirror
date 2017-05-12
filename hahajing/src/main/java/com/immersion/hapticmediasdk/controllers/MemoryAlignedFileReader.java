package com.immersion.hapticmediasdk.controllers;

import com.immersion.content.HapticHeaderUtils;
import com.immersion.content.HeaderUtils;
import com.immersion.hapticmediasdk.models.HapticFileInformation;
import com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Profiler;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import rrrrrr.rcrcrr;

public class MemoryAlignedFileReader implements IHapticFileReader {
    private static final String a = "MemoryAlignedFileReader";
    public static int b0415ЕЕ0415ЕЕ = 10;
    public static int bЕ041504150415ЕЕ = 1;
    public static int bЕ0415Е0415ЕЕ = 0;
    public static int bЕЕ04150415ЕЕ = 2;
    private static int h = 80;
    private static int i = 0;
    private static final int k = 1024;
    private static final int l = 3072;
    private static final int t = 16;
    private File b;
    private FileChannel c;
    private rcrcrr d;
    private rcrcrr e;
    private int f;
    private int g;
    private HapticFileInformation j;
    private String m;
    private FileManager n;
    private HeaderUtils o;
    private byte[] p;
    private final Profiler q;
    private int r;
    private int s;

    public MemoryAlignedFileReader(String str, HeaderUtils headerUtils) {
        try {
            this.f = 0;
            this.m = null;
            this.n = null;
            this.p = null;
            try {
                this.q = new Profiler();
                this.m = str;
                int i = b0415ЕЕ0415ЕЕ;
                switch ((i * (bЕ041504150415ЕЕ + i)) % bЕЕ04150415ЕЕ) {
                    case 0:
                        break;
                    default:
                        b0415ЕЕ0415ЕЕ = b0415Е04150415ЕЕ();
                        bЕ0415Е0415ЕЕ = 92;
                        break;
                }
                this.o = headerUtils;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public MemoryAlignedFileReader(String str, FileManager fileManager, int i) {
        try {
            if (((b0415ЕЕ0415ЕЕ + b04150415Е0415ЕЕ()) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ != bЕ0415Е0415ЕЕ) {
                b0415ЕЕ0415ЕЕ = b0415Е04150415ЕЕ();
                bЕ0415Е0415ЕЕ = b0415Е04150415ЕЕ();
            }
            this.f = 0;
            this.m = null;
            this.n = null;
            this.p = null;
            this.q = new Profiler();
            try {
                this.m = str;
                this.n = fileManager;
                this.o = new HapticHeaderUtils();
                this.f = i;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private int a(rcrcrr rrrrrr_rcrcrr, int i) {
        if (((b0415ЕЕ0415ЕЕ + bЕ041504150415ЕЕ) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ != bЕ0415Е0415ЕЕ) {
            b0415ЕЕ0415ЕЕ = 0;
            bЕ0415Е0415ЕЕ = b0415Е04150415ЕЕ();
        }
        try {
            try {
                return (i - rrrrrr_rcrcrr.mHapticDataOffset) % rrrrrr_rcrcrr.mMappedByteBuffer.capacity();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a() {
        /*
        r5 = this;
        r0 = 0;
        r1 = 0;
        r2 = r5.j;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        if (r2 == 0) goto L_0x0008;
    L_0x0006:
        r0 = 1;
    L_0x0007:
        return r0;
    L_0x0008:
        r2 = r5.b;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        if (r2 != 0) goto L_0x001a;
    L_0x000c:
        r2 = r5.n;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        if (r2 == 0) goto L_0x006d;
    L_0x0010:
        r2 = r5.n;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r3 = r5.m;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r2 = r2.getHapticStorageFile(r3);	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r5.b = r2;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
    L_0x001a:
        r2 = r5.c;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        if (r2 != 0) goto L_0x002e;
    L_0x001e:
        r2 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r3 = r5.b;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r4 = "r";
        r2.<init>(r3, r4);	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r1 = r2.getChannel();	 Catch:{ FileNotFoundException -> 0x007d, Exception -> 0x0063 }
        r5.c = r1;	 Catch:{ FileNotFoundException -> 0x007d, Exception -> 0x0063 }
        r1 = r2;
    L_0x002e:
        r2 = r5.c;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        if (r2 != 0) goto L_0x0068;
    L_0x0032:
        r1 = b0415ЕЕ0415ЕЕ;
        r2 = bЕ041504150415ЕЕ;
        r1 = r1 + r2;
        r2 = b0415ЕЕ0415ЕЕ;
        r1 = r1 * r2;
        r2 = bЕЕ04150415ЕЕ;
        r1 = r1 % r2;
        r2 = bЕ0415Е0415ЕЕ;
        if (r1 == r2) goto L_0x0007;
    L_0x0041:
        r1 = b0415Е04150415ЕЕ();
        b0415ЕЕ0415ЕЕ = r1;
        r1 = 96;
        bЕ0415Е0415ЕЕ = r1;
        goto L_0x0007;
    L_0x004c:
        r2 = move-exception;
    L_0x004d:
        r2 = "MemoryAlignedFileReader";
        r3 = "FileNotFoundException";
        com.immersion.hapticmediasdk.utils.Log.e(r2, r3);	 Catch:{ Exception -> 0x007b }
        r2 = r5.n;	 Catch:{ Exception -> 0x007b }
        r2.closeCloseable(r1);	 Catch:{ Exception -> 0x007b }
        r1 = r5.n;	 Catch:{ Exception -> 0x0061 }
        r2 = r5.c;	 Catch:{ Exception -> 0x0061 }
        r1.closeCloseable(r2);	 Catch:{ Exception -> 0x0061 }
        goto L_0x0007;
    L_0x0061:
        r0 = move-exception;
        throw r0;
    L_0x0063:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ Exception -> 0x0061 }
        goto L_0x0007;
    L_0x0068:
        r0 = r5.b();	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        goto L_0x0007;
    L_0x006d:
        r2 = r5.m;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        if (r2 == 0) goto L_0x0007;
    L_0x0071:
        r2 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r3 = r5.m;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r2.<init>(r3);	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        r5.b = r2;	 Catch:{ FileNotFoundException -> 0x004c, Exception -> 0x0063 }
        goto L_0x001a;
    L_0x007b:
        r0 = move-exception;
        throw r0;
    L_0x007d:
        r1 = move-exception;
        r1 = r2;
        goto L_0x004d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.a():boolean");
    }

    private boolean a(int i) {
        if (((b0415ЕЕ0415ЕЕ + b04150415Е0415ЕЕ()) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ != bЕ0415Е0415ЕЕ) {
            b0415ЕЕ0415ЕЕ = 31;
            bЕ0415Е0415ЕЕ = 17;
        }
        try {
            return this.g >= i;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int b(int r3) {
        /*
        r2 = this;
        r0 = 0;
        r1 = r2.o;
        if (r1 == 0) goto L_0x0026;
    L_0x0005:
        switch(r0) {
            case 0: goto L_0x000c;
            case 1: goto L_0x0005;
            default: goto L_0x0008;
        };
    L_0x0008:
        switch(r0) {
            case 0: goto L_0x000c;
            case 1: goto L_0x0005;
            default: goto L_0x000b;
        };
    L_0x000b:
        goto L_0x0008;
    L_0x000c:
        r0 = b0415ЕЕ0415ЕЕ;
        r1 = bЕ041504150415ЕЕ;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЕЕ04150415ЕЕ;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0020;
            default: goto L_0x0018;
        };
    L_0x0018:
        r0 = 53;
        b0415ЕЕ0415ЕЕ = r0;
        r0 = 85;
        bЕ0415Е0415ЕЕ = r0;
    L_0x0020:
        r0 = r2.o;
        r0 = r0.calculateByteOffsetIntoHapticData(r3);
    L_0x0026:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.b(int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b() {
        /*
        r8 = this;
        r6 = 4;
        r0 = 0;
        r1 = -1;
        r2 = 4;
        r2 = java.nio.ByteBuffer.allocate(r2);	 Catch:{ IOException -> 0x0080 }
        r3 = java.nio.ByteOrder.LITTLE_ENDIAN;	 Catch:{ IOException -> 0x0080 }
        r2.order(r3);	 Catch:{ IOException -> 0x0080 }
        r3 = 0;
        r2.position(r3);	 Catch:{ IOException -> 0x0080 }
        r3 = r8.c;	 Catch:{ IOException -> 0x0080 }
        r4 = 16;
        r3 = r3.read(r2, r4);	 Catch:{ IOException -> 0x0080 }
        if (r3 == r6) goto L_0x005c;
    L_0x001b:
        return r0;
    L_0x001c:
        r5 = 4;
        r4.position(r5);	 Catch:{ IOException -> 0x0080 }
        r5 = r4.getInt();	 Catch:{ IOException -> 0x0080 }
        r5 = r5 + 8;
        r5 = r5 - r3;
        r8.r = r5;	 Catch:{ IOException -> 0x0080 }
        r8.s = r3;	 Catch:{ IOException -> 0x0080 }
        r3 = 20;
        r4.position(r3);	 Catch:{ IOException -> 0x0080 }
        r3 = new byte[r2];	 Catch:{ IOException -> 0x0080 }
        r8.p = r3;	 Catch:{ IOException -> 0x0080 }
        r3 = r4.duplicate();	 Catch:{ IOException -> 0x0080 }
        r5 = r8.p;	 Catch:{ IOException -> 0x0080 }
        r6 = 0;
        r3.get(r5, r6, r2);	 Catch:{ IOException -> 0x0080 }
        r3 = r8.o;	 Catch:{ IOException -> 0x0080 }
        r3.setEncryptedHSI(r4, r2);	 Catch:{ IOException -> 0x0080 }
        r2 = r8.o;	 Catch:{ IOException -> 0x0080 }
        r2 = r2.calculateBlockSize();	 Catch:{ IOException -> 0x0080 }
        if (r2 <= 0) goto L_0x001b;
    L_0x004b:
        r2 = r2 * 2;
        i = r2;	 Catch:{ IOException -> 0x0080 }
        r2 = r8.o;	 Catch:{ IOException -> 0x0080 }
        r2 = r2.calculateBlockRate();	 Catch:{ IOException -> 0x0080 }
        if (r2 <= 0) goto L_0x001b;
    L_0x0057:
        h = r2;	 Catch:{ IOException -> 0x0080 }
    L_0x0059:
        r0 = new int[r1];	 Catch:{ Exception -> 0x0085 }
        goto L_0x0059;
    L_0x005c:
        r2.flip();	 Catch:{ IOException -> 0x0080 }
        r2 = r2.getInt();	 Catch:{ IOException -> 0x0080 }
    L_0x0063:
        switch(r0) {
            case 0: goto L_0x006a;
            case 1: goto L_0x0063;
            default: goto L_0x0066;
        };	 Catch:{ IOException -> 0x0080 }
    L_0x0066:
        switch(r0) {
            case 0: goto L_0x006a;
            case 1: goto L_0x0063;
            default: goto L_0x0069;
        };	 Catch:{ IOException -> 0x0080 }
    L_0x0069:
        goto L_0x0066;
    L_0x006a:
        r3 = r2 + 28;
        r4 = java.nio.ByteBuffer.allocate(r3);	 Catch:{ IOException -> 0x0080 }
        r5 = java.nio.ByteOrder.LITTLE_ENDIAN;	 Catch:{ IOException -> 0x0080 }
        r4.order(r5);	 Catch:{ IOException -> 0x0080 }
        r5 = r8.c;	 Catch:{ IOException -> 0x0080 }
        r6 = 0;
        r5 = r5.read(r4, r6);	 Catch:{ IOException -> 0x0080 }
        if (r5 == r3) goto L_0x001c;
    L_0x007f:
        goto L_0x001b;
    L_0x0080:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x001b;
    L_0x0085:
        r0 = move-exception;
        r0 = b0415Е04150415ЕЕ();
        b0415ЕЕ0415ЕЕ = r0;
        r0 = 1;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.b():boolean");
    }

    private static boolean b(rcrcrr rrrrrr_rcrcrr, int i) {
        if (i >= rrrrrr_rcrcrr.mHapticDataOffset) {
            return false;
        }
        if (((b0415ЕЕ0415ЕЕ + bЕ041504150415ЕЕ) * b0415ЕЕ0415ЕЕ) % b0415Е0415Е0415Е() == bЕ0415Е0415ЕЕ) {
            return true;
        }
        b0415ЕЕ0415ЕЕ = 22;
        bЕ0415Е0415ЕЕ = b0415Е04150415ЕЕ();
        return true;
    }

    public static int b04150415Е0415ЕЕ() {
        return 1;
    }

    public static int b0415Е04150415ЕЕ() {
        return 23;
    }

    public static int b0415Е0415Е0415Е() {
        return 2;
    }

    public static int bЕ04150415Е0415Е() {
        return 0;
    }

    private int c(int i) {
        return this.s + b(i);
    }

    private void c() throws NotEnoughHapticBytesAvailableException, IOException {
        String str = null;
        try {
            if (this.e == null) {
                while (true) {
                    try {
                        int[] iArr = new int[-1];
                    } catch (Exception e) {
                        b0415ЕЕ0415ЕЕ = b0415Е04150415ЕЕ();
                        while (true) {
                            try {
                                str.length();
                            } catch (Exception e2) {
                                b0415ЕЕ0415ЕЕ = 39;
                                while (true) {
                                    try {
                                        int[] iArr2 = new int[-1];
                                    } catch (Exception e3) {
                                        b0415ЕЕ0415ЕЕ = 45;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int i = this.e.mHapticDataOffset + 1024;
            this.d = this.e;
            try {
                this.e = d(i - (i / 2));
            } catch (Exception e4) {
                throw e4;
            }
        } catch (Exception e42) {
            throw e42;
        }
    }

    private static boolean c(rcrcrr rrrrrr_rcrcrr, int i) {
        try {
            if (i < rrrrrr_rcrcrr.mHapticDataOffset + rrrrrr_rcrcrr.mMappedByteBuffer.capacity()) {
                return false;
            }
            if (((b0415ЕЕ0415ЕЕ + bЕ041504150415ЕЕ) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ == bЕ04150415Е0415Е()) {
                return true;
            }
            b0415ЕЕ0415ЕЕ = b0415Е04150415ЕЕ();
            bЕ0415Е0415ЕЕ = b0415Е04150415ЕЕ();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    private int d() {
        try {
            if (this.o == null) {
                return 0;
            }
            try {
                int numChannels = this.o.getNumChannels();
                if (((b0415ЕЕ0415ЕЕ + bЕ041504150415ЕЕ) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ == bЕ0415Е0415ЕЕ) {
                    return numChannels;
                }
                b0415ЕЕ0415ЕЕ = b0415Е04150415ЕЕ();
                bЕ0415Е0415ЕЕ = b0415Е04150415ЕЕ();
                return numChannels;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private rcrcrr d(int i) throws IOException, NotEnoughHapticBytesAvailableException {
        try {
            this.q.startTiming();
            if (i < this.r) {
                int i2 = this.s + i;
                try {
                    int i3;
                    int f = f();
                    if ((i + 1024) + f <= this.r) {
                        f += 1024;
                        if (((b0415ЕЕ0415ЕЕ + bЕ041504150415ЕЕ) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ != bЕ0415Е0415ЕЕ) {
                            b0415ЕЕ0415ЕЕ = 31;
                            bЕ0415Е0415ЕЕ = 69;
                        }
                        i3 = f;
                    } else {
                        i3 = this.r - i;
                    }
                    if (i + i3 > this.g) {
                        throw new NotEnoughHapticBytesAvailableException("Not enough bytes available yet.");
                    }
                    MappedByteBuffer map = this.c.map(MapMode.READ_ONLY, (long) i2, (long) i3);
                    if (map != null) {
                        map.order(ByteOrder.BIG_ENDIAN);
                        rcrcrr rrrrrr_rcrcrr = new rcrcrr();
                        rrrrrr_rcrcrr.mMappedByteBuffer = map;
                        rrrrrr_rcrcrr.mHapticDataOffset = i;
                        return rrrrrr_rcrcrr;
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            return null;
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean d(rrrrrr.rcrcrr r3, int r4) {
        /*
        r0 = 1;
        r1 = b(r3, r4);
        if (r1 != 0) goto L_0x002c;
    L_0x0007:
        switch(r0) {
            case 0: goto L_0x0007;
            case 1: goto L_0x000e;
            default: goto L_0x000a;
        };
    L_0x000a:
        switch(r0) {
            case 0: goto L_0x0007;
            case 1: goto L_0x000e;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x000a;
    L_0x000e:
        r1 = c(r3, r4);
        if (r1 == 0) goto L_0x002d;
    L_0x0014:
        r1 = b0415Е04150415ЕЕ();
        r2 = bЕ041504150415ЕЕ;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bЕЕ04150415ЕЕ;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x002c;
            default: goto L_0x0022;
        };
    L_0x0022:
        r1 = b0415Е04150415ЕЕ();
        b0415ЕЕ0415ЕЕ = r1;
        r1 = 24;
        bЕ0415Е0415ЕЕ = r1;
    L_0x002c:
        return r0;
    L_0x002d:
        r0 = 0;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.d(rrrrrr.rcrcrr, int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void e() {
        /*
        r4 = this;
        r3 = 1;
        r0 = "MemoryAlignedFileReader";
        r1 = "%%%%%%%%%%% logBufferState %%%%%%%%%%%";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = r4.d;
        if (r0 == 0) goto L_0x0185;
    L_0x000c:
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW capacity = ";
        r1 = r1.append(r2);
        r2 = r4.d;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.capacity();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
    L_0x0033:
        switch(r3) {
            case 0: goto L_0x0033;
            case 1: goto L_0x003a;
            default: goto L_0x0036;
        };
    L_0x0036:
        switch(r3) {
            case 0: goto L_0x0033;
            case 1: goto L_0x003a;
            default: goto L_0x0039;
        };
    L_0x0039:
        goto L_0x0036;
    L_0x003a:
        r2 = "mCurrentMMW position = ";
        r1 = r1.append(r2);
        r2 = r4.d;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.position();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW remaining = ";
        r1 = r1.append(r2);
        r2 = r4.d;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.remaining();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW mHapticDataOffset = ";
        r1 = r1.append(r2);
        r2 = r4.d;
        r2 = r2.mHapticDataOffset;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW mHapticDataOffset + position = ";
        r1 = r1.append(r2);
        r2 = r4.d;
        r2 = r2.mHapticDataOffset;
        r3 = r4.d;
        r3 = r3.mMappedByteBuffer;
        r3 = r3.position();
        r2 = r2 + r3;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
    L_0x00b4:
        r0 = "MemoryAlignedFileReader";
        r1 = "--------------------------------------";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = r4.e;
        if (r0 == 0) goto L_0x018e;
    L_0x00bf:
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW capacity = ";
        r1 = r1.append(r2);
        r2 = r4.e;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.capacity();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW position = ";
        r1 = r1.append(r2);
        r2 = r4.e;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.position();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW remaining = ";
        r1 = r1.append(r2);
        r2 = r4.e;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.remaining();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW mHapticDataOffset = ";
        r1 = r1.append(r2);
        r2 = r4.e;
        r2 = r2.mHapticDataOffset;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW mHapticDataOffset + position = ";
        r1 = r1.append(r2);
        r2 = b0415ЕЕ0415ЕЕ;
        r3 = b04150415Е0415ЕЕ();
        r2 = r2 + r3;
        r3 = b0415ЕЕ0415ЕЕ;
        r2 = r2 * r3;
        r3 = bЕЕ04150415ЕЕ;
        r2 = r2 % r3;
        r3 = bЕ0415Е0415ЕЕ;
        if (r2 == r3) goto L_0x0165;
    L_0x0159:
        r2 = b0415Е04150415ЕЕ();
        b0415ЕЕ0415ЕЕ = r2;
        r2 = b0415Е04150415ЕЕ();
        bЕ0415Е0415ЕЕ = r2;
    L_0x0165:
        r2 = r4.e;
        r2 = r2.mHapticDataOffset;
        r3 = r4.e;
        r3 = r3.mMappedByteBuffer;
        r3 = r3.position();
        r2 = r2 + r3;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
    L_0x017d:
        r0 = "MemoryAlignedFileReader";
        r1 = "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        return;
    L_0x0185:
        r0 = "MemoryAlignedFileReader";
        r1 = "mCurrentMMW is null";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        goto L_0x00b4;
    L_0x018e:
        r0 = "MemoryAlignedFileReader";
        r1 = "mNextMMW is null";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        goto L_0x017d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.e():void");
    }

    private static boolean e(rcrcrr rrrrrr_rcrcrr, int i) {
        try {
            int i2 = i;
            int i3 = b0415ЕЕ0415ЕЕ;
            switch ((i3 * (bЕ041504150415ЕЕ + i3)) % bЕЕ04150415ЕЕ) {
                case 0:
                    break;
                default:
                    b0415ЕЕ0415ЕЕ = 4;
                    bЕ0415Е0415ЕЕ = 62;
                    break;
            }
            return c(rrrrrr_rcrcrr, i2 + i);
        } catch (Exception e) {
            throw e;
        }
    }

    private int f() {
        int i = 0;
        while ((i + 1024) % (i / 2) != 0) {
            i += 16;
        }
        return i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean bufferAtPlaybackPosition(int r8) {
        /*
        r7 = this;
        r1 = 1;
        r0 = 0;
        r2 = r7.a();
        if (r2 != 0) goto L_0x0009;
    L_0x0008:
        return r0;
    L_0x0009:
        r2 = r7.b(r8);
        r3 = r7.r;
        if (r2 >= r3) goto L_0x0008;
    L_0x0011:
        r3 = r7.d;
        if (r3 == 0) goto L_0x001d;
    L_0x0015:
        r3 = r7.d;
        r3 = d(r3, r2);
        if (r3 == 0) goto L_0x007f;
    L_0x001d:
        r3 = r7.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        if (r3 == 0) goto L_0x0031;
    L_0x0021:
        r3 = r7.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r3 = d(r3, r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        if (r3 != 0) goto L_0x0031;
    L_0x0029:
        r3 = r7.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r3 = e(r3, r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        if (r3 == 0) goto L_0x007c;
    L_0x0031:
        r3 = r7.d;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        if (r3 == 0) goto L_0x003b;
    L_0x0035:
        r3 = r7.d;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r3 = r3.mHapticDataOffset;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        if (r3 == r2) goto L_0x0041;
    L_0x003b:
        r3 = r7.d(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r7.d = r3;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
    L_0x0041:
        r3 = r7.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        if (r3 == 0) goto L_0x006d;
    L_0x0045:
        r3 = r7.e;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r3 = r3.mHapticDataOffset;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r4 = r2 + 1024;
        r5 = i;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r5 = r5 / 2;
        r4 = r4 - r5;
        r5 = b0415Е04150415ЕЕ();
        r6 = bЕ041504150415ЕЕ;
        r5 = r5 + r6;
        r6 = b0415Е04150415ЕЕ();
        r5 = r5 * r6;
        r6 = bЕЕ04150415ЕЕ;
        r5 = r5 % r6;
        r6 = bЕ0415Е0415ЕЕ;
        if (r5 == r6) goto L_0x006b;
    L_0x0063:
        r5 = 98;
        b0415ЕЕ0415ЕЕ = r5;
        r5 = 73;
        bЕ0415Е0415ЕЕ = r5;
    L_0x006b:
        if (r3 == r4) goto L_0x007a;
    L_0x006d:
        r2 = r2 + 1024;
        r3 = i;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r3 = r3 / 2;
        r2 = r2 - r3;
        r2 = r7.d(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
        r7.e = r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
    L_0x007a:
        r0 = r1;
        goto L_0x0008;
    L_0x007c:
        r7.c();	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0096, IOException -> 0x0093 }
    L_0x007f:
        r0 = r7.d;
        if (r0 == 0) goto L_0x0090;
    L_0x0083:
        r0 = r7.d;
        r0 = r0.mMappedByteBuffer;
        r3 = r7.d;
        r2 = r7.a(r3, r2);
        r0.position(r2);
    L_0x0090:
        r0 = r1;
        goto L_0x0008;
    L_0x0093:
        r1 = move-exception;
        goto L_0x0008;
    L_0x0096:
        r1 = move-exception;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.bufferAtPlaybackPosition(int):boolean");
    }

    public void close() {
        this.n.closeCloseable(this.c);
        this.o.dispose();
    }

    public long getBlockOffset(long j) {
        long j2 = j % ((long) h);
        int i = b0415ЕЕ0415ЕЕ;
        switch ((i * (bЕ041504150415ЕЕ + i)) % bЕЕ04150415ЕЕ) {
            case 0:
                break;
            default:
                b0415ЕЕ0415ЕЕ = b0415Е04150415ЕЕ();
                bЕ0415Е0415ЕЕ = 40;
                break;
        }
        return (j2 * 16) / ((long) h);
    }

    public int getBlockSizeMS() {
        int i = h;
        if (((b0415ЕЕ0415ЕЕ + bЕ041504150415ЕЕ) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ != bЕ0415Е0415ЕЕ) {
            b0415ЕЕ0415ЕЕ = 57;
            bЕ0415Е0415ЕЕ = 94;
        }
        return i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getBufferForPlaybackPosition(int r8) throws com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException {
        /*
        r7 = this;
        r0 = 0;
        r3 = 0;
        r1 = r7.d;
        if (r1 != 0) goto L_0x0007;
    L_0x0006:
        return r0;
    L_0x0007:
        r2 = r7.b(r8);
        r1 = r7.r;
        r4 = i;
        r1 = r1 - r4;
        if (r2 >= r1) goto L_0x0006;
    L_0x0012:
        r1 = i;	 Catch:{ Exception -> 0x00b0 }
        r1 = new byte[r1];	 Catch:{ Exception -> 0x00b0 }
        r4 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.remaining();	 Catch:{ Exception -> 0x00b0 }
        r5 = i;	 Catch:{ Exception -> 0x00b0 }
        if (r4 >= r5) goto L_0x0025;
    L_0x0022:
        r7.c();	 Catch:{ Exception -> 0x00b0 }
    L_0x0025:
        r4 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.position();	 Catch:{ Exception -> 0x00b0 }
        r5 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r5 = r5.mHapticDataOffset;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4 + r5;
        if (r4 < r2) goto L_0x0036;
    L_0x0034:
        if (r4 <= r2) goto L_0x004a;
    L_0x0036:
        r2 = r2 - r4;
        r4 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.position();	 Catch:{ Exception -> 0x00b0 }
        r2 = r2 + r4;
        if (r2 >= 0) goto L_0x009b;
    L_0x0042:
        r2 = r3;
    L_0x0043:
        r4 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r4.position(r2);	 Catch:{ Exception -> 0x00b0 }
    L_0x004a:
        r2 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r2 = r2.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r2 = r2.remaining();	 Catch:{ Exception -> 0x00b0 }
        r4 = b0415ЕЕ0415ЕЕ;
        r5 = bЕ041504150415ЕЕ;
        r5 = r5 + r4;
        r4 = r4 * r5;
        r5 = b0415Е0415Е0415Е();
        r4 = r4 % r5;
        switch(r4) {
            case 0: goto L_0x006c;
            default: goto L_0x0060;
        };
    L_0x0060:
        r4 = b0415Е04150415ЕЕ();
        b0415ЕЕ0415ЕЕ = r4;
        r4 = b0415Е04150415ЕЕ();
        bЕ0415Е0415ЕЕ = r4;
    L_0x006c:
        r4 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r5 = 0;
        r6 = i;	 Catch:{ Exception -> 0x00b0 }
        if (r2 >= r6) goto L_0x0098;
    L_0x0075:
        r4.get(r1, r5, r2);	 Catch:{ Exception -> 0x00b0 }
        r2 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r2 = r2.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r4 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.position();	 Catch:{ Exception -> 0x00b0 }
    L_0x0084:
        switch(r3) {
            case 0: goto L_0x008c;
            case 1: goto L_0x0084;
            default: goto L_0x0087;
        };	 Catch:{ Exception -> 0x00b0 }
    L_0x0087:
        r5 = 1;
        switch(r5) {
            case 0: goto L_0x0084;
            case 1: goto L_0x008c;
            default: goto L_0x008b;
        };	 Catch:{ Exception -> 0x00b0 }
    L_0x008b:
        goto L_0x0087;
    L_0x008c:
        r3 = i;	 Catch:{ Exception -> 0x00b0 }
        r3 = r3 / 2;
        r3 = r4 - r3;
        r2.position(r3);	 Catch:{ Exception -> 0x00b0 }
        r0 = r1;
        goto L_0x0006;
    L_0x0098:
        r2 = i;	 Catch:{ Exception -> 0x00b0 }
        goto L_0x0075;
    L_0x009b:
        r4 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r4 = r4.limit();	 Catch:{ Exception -> 0x00b0 }
        if (r4 >= r2) goto L_0x0043;
    L_0x00a5:
        r2 = r7.d;	 Catch:{ Exception -> 0x00b0 }
        r2 = r2.mMappedByteBuffer;	 Catch:{ Exception -> 0x00b0 }
        r2 = r2.limit();	 Catch:{ Exception -> 0x00b0 }
        r2 = r2 + -1;
        goto L_0x0043;
    L_0x00b0:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.getBufferForPlaybackPosition(int):byte[]");
    }

    public byte[] getEncryptedHapticHeader() {
        return this.p;
    }

    public int getHapticBlockIndex(long j) {
        try {
            int b = b((int) j);
            int i = this.f;
            if (((b0415ЕЕ0415ЕЕ + b04150415Е0415ЕЕ()) * b0415ЕЕ0415ЕЕ) % bЕЕ04150415ЕЕ != bЕ0415Е0415ЕЕ) {
                b0415ЕЕ0415ЕЕ = 2;
                bЕ0415Е0415ЕЕ = b0415Е04150415ЕЕ();
            }
            if (i == 2) {
                return b / 16;
            }
            if (this.f < 3) {
                return 0;
            }
            try {
                return b / (d() * 16);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public HapticFileInformation getHapticFileInformation() {
        return this.j;
    }

    public void setBlockSizeMS(int i) {
        int i2 = b0415ЕЕ0415ЕЕ;
        switch ((i2 * (b04150415Е0415ЕЕ() + i2)) % bЕЕ04150415ЕЕ) {
            case 0:
                break;
            default:
                b0415ЕЕ0415ЕЕ = b0415Е04150415ЕЕ();
                bЕ0415Е0415ЕЕ = b0415Е04150415ЕЕ();
                break;
        }
        try {
            h = i;
        } catch (Exception e) {
            throw e;
        }
    }

    public void setBytesAvailable(int i) {
        this.g = i;
        if (this.g <= 0) {
            this.g = i;
            a();
        }
    }
}
