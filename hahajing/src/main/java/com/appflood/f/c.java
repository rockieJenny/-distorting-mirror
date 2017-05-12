package com.appflood.f;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import java.io.InputStream;
import java.util.Vector;

public final class c {
    private Bitmap A;
    private byte[] B = new byte[256];
    private int C = 0;
    private int D = 0;
    private int E = 0;
    private boolean F = false;
    private int G = 0;
    private int H;
    private short[] I;
    private byte[] J;
    private byte[] K;
    private byte[] L;
    private int M;
    int a = 0;
    Vector b;
    private InputStream c;
    private int d;
    private int e;
    private int f;
    private boolean g;
    private int h;
    private int[] i;
    private int[] j;
    private int[] k;
    private int l;
    private int m;
    private int n;
    private boolean o;
    private boolean p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private Bitmap z;

    static class a {
        public Bitmap a;

        public a(Bitmap bitmap) {
            this.a = bitmap;
        }
    }

    private boolean a() {
        return this.d != 0;
    }

    private int b() {
        int i = 0;
        try {
            i = this.c.read();
        } catch (Exception e) {
            this.d = 1;
        }
        return i;
    }

    private int[] b(int i) {
        int read;
        int i2 = 0;
        int i3 = i * 3;
        int[] iArr = null;
        byte[] bArr = new byte[i3];
        try {
            read = this.c.read(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            read = 0;
        }
        if (read < i3) {
            this.d = 1;
        } else {
            iArr = new int[256];
            read = 0;
            while (read < i) {
                i3 = i2 + 1;
                int i4 = bArr[i2] & 255;
                i2 = i3 + 1;
                int i5 = bArr[i3] & 255;
                i3 = i2 + 1;
                int i6 = bArr[i2] & 255;
                i2 = read + 1;
                iArr[read] = (((i4 << 16) | ViewCompat.MEASURED_STATE_MASK) | (i5 << 8)) | i6;
                read = i2;
                i2 = i3;
            }
        }
        return iArr;
    }

    private int c() {
        this.C = b();
        int i = 0;
        if (this.C > 0) {
            while (i < this.C) {
                try {
                    int read = this.c.read(this.B, i, this.C - i);
                    if (read == -1) {
                        break;
                    }
                    i += read;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (i < this.C) {
                this.d = 1;
            }
        }
        return i;
    }

    private void d() {
        Object obj = null;
        while (obj == null && !a()) {
            int b;
            switch (b()) {
                case 0:
                    break;
                case 33:
                    switch (b()) {
                        case 249:
                            b();
                            b = b();
                            this.D = (b & 28) >> 2;
                            if (this.D == 0) {
                                this.D = 1;
                            }
                            this.F = (b & 1) != 0;
                            this.G = g() * 10;
                            this.H = b();
                            b();
                            break;
                        case 255:
                            c();
                            String str = "";
                            for (b = 0; b < 11; b++) {
                                str = str + ((char) this.B[b]);
                            }
                            if (!str.equals("NETSCAPE2.0")) {
                                h();
                                break;
                            } else {
                                f();
                                break;
                            }
                        default:
                            h();
                            break;
                    }
                case 44:
                    this.r = g();
                    this.s = g();
                    this.t = g();
                    this.u = g();
                    int b2 = b();
                    this.o = (b2 & 128) != 0;
                    this.p = (b2 & 64) != 0;
                    this.q = 2 << (b2 & 7);
                    if (this.o) {
                        this.j = b(this.q);
                        this.k = this.j;
                    } else {
                        this.k = this.i;
                        if (this.l == this.H) {
                            this.m = 0;
                        }
                    }
                    b = 0;
                    if (this.F) {
                        b = this.k[this.H];
                        this.k[this.H] = 0;
                    }
                    int i = b;
                    if (this.k == null) {
                        this.d = 1;
                    }
                    if (a()) {
                        break;
                    }
                    int i2;
                    int[] iArr;
                    int i3;
                    Vector vector;
                    Bitmap bitmap;
                    int i4 = this.t * this.u;
                    if (this.L == null || this.L.length < i4) {
                        this.L = new byte[i4];
                    }
                    if (this.I == null) {
                        this.I = new short[4096];
                    }
                    if (this.J == null) {
                        this.J = new byte[4096];
                    }
                    if (this.K == null) {
                        this.K = new byte[FragmentTransaction.TRANSIT_FRAGMENT_OPEN];
                    }
                    int b3 = b();
                    int i5 = 1 << b3;
                    int i6 = i5 + 1;
                    int i7 = i5 + 2;
                    int i8 = -1;
                    int i9 = b3 + 1;
                    int i10 = (1 << i9) - 1;
                    for (b = 0; b < i5; b++) {
                        this.I[b] = (short) 0;
                        this.J[b] = (byte) b;
                    }
                    int i11 = 0;
                    int i12 = 0;
                    int i13 = 0;
                    int i14 = 0;
                    b2 = 0;
                    int i15 = 0;
                    int i16 = 0;
                    b = 0;
                    while (i16 < i4) {
                        if (i12 != 0) {
                            i2 = i10;
                            i10 = i13;
                            i13 = i15;
                            i15 = i9;
                            i9 = i12;
                            i12 = i14;
                            i14 = i8;
                            i8 = i7;
                        } else if (i15 < i9) {
                            if (b2 == 0) {
                                b2 = c();
                                if (b2 <= 0) {
                                    for (b = i11; b < i4; b++) {
                                        this.L[b] = (byte) 0;
                                    }
                                    h();
                                    if (a()) {
                                        this.M++;
                                        this.z = Bitmap.createBitmap(this.e, this.f, Config.ARGB_8888);
                                        iArr = new int[(this.e * this.f)];
                                        if (this.E > 0) {
                                            if (this.E == 3) {
                                                b = this.M - 2;
                                                if (b > 0) {
                                                    this.A = a(b - 1);
                                                } else {
                                                    this.A = null;
                                                }
                                            }
                                            if (this.A != null) {
                                                this.A.getPixels(iArr, 0, this.e, 0, 0, this.e, this.f);
                                                if (this.E == 2) {
                                                    b = 0;
                                                    if (!this.F) {
                                                        b = this.n;
                                                    }
                                                    for (i9 = 0; i9 < this.y; i9++) {
                                                        i10 = ((this.w + i9) * this.e) + this.v;
                                                        i12 = this.x + i10;
                                                        while (i10 < i12) {
                                                            iArr[i10] = b;
                                                            i10++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        i12 = 1;
                                        i10 = 8;
                                        i9 = 0;
                                        for (b = 0; b < this.u; b++) {
                                            if (this.p) {
                                                if (i9 >= this.u) {
                                                    i12++;
                                                    switch (i12) {
                                                        case 2:
                                                            i9 = 4;
                                                            break;
                                                        case 3:
                                                            i9 = 2;
                                                            i10 = 4;
                                                            break;
                                                        case 4:
                                                            i9 = 1;
                                                            i10 = 2;
                                                            break;
                                                    }
                                                }
                                                i3 = i9;
                                                i9 += i10;
                                                i13 = i3;
                                            } else {
                                                i13 = b;
                                            }
                                            i13 += this.s;
                                            if (i13 >= this.f) {
                                                i14 = this.e * i13;
                                                i15 = i14 + this.r;
                                                i13 = this.t + i15;
                                                if (this.e + i14 < i13) {
                                                    i13 = this.e + i14;
                                                }
                                                i14 = this.t * b;
                                                i2 = i15;
                                                while (i2 < i13) {
                                                    i15 = i14 + 1;
                                                    i14 = this.k[this.L[i14] & 255];
                                                    if (i14 == 0) {
                                                        iArr[i2] = i14;
                                                    }
                                                    i2++;
                                                    i14 = i15;
                                                }
                                            }
                                        }
                                        this.z = Bitmap.createBitmap(iArr, this.e, this.f, Config.ARGB_8888);
                                        vector = this.b;
                                        bitmap = this.z;
                                        i10 = this.G;
                                        vector.addElement(new a(bitmap));
                                        if (this.F) {
                                            this.k[this.H] = i;
                                        }
                                        this.E = this.D;
                                        this.v = this.r;
                                        this.w = this.s;
                                        this.x = this.t;
                                        this.y = this.u;
                                        this.A = this.z;
                                        this.n = this.m;
                                        this.D = 0;
                                        this.F = false;
                                        this.G = 0;
                                        this.j = null;
                                        break;
                                    }
                                    break;
                                }
                                b = 0;
                            }
                            i14 += (this.B[b] & 255) << i15;
                            i15 += 8;
                            b++;
                            b2--;
                        } else {
                            i2 = i14 & i10;
                            i14 >>= i9;
                            i15 -= i9;
                            if (i2 <= i7 && i2 != i6) {
                                if (i2 == i5) {
                                    i9 = b3 + 1;
                                    i10 = (1 << i9) - 1;
                                    i7 = i5 + 2;
                                    i8 = -1;
                                } else if (i8 == -1) {
                                    i13 = i12 + 1;
                                    this.K[i12] = this.J[i2];
                                    i12 = i13;
                                    i8 = i2;
                                    i13 = i2;
                                } else {
                                    int i17;
                                    if (i2 == i7) {
                                        i17 = i12 + 1;
                                        this.K[i12] = (byte) i13;
                                        i13 = i8;
                                    } else {
                                        i17 = i12;
                                        i13 = i2;
                                    }
                                    while (i13 > i5) {
                                        i12 = i17 + 1;
                                        this.K[i17] = this.J[i13];
                                        i13 = this.I[i13];
                                        i17 = i12;
                                    }
                                    i13 = this.J[i13] & 255;
                                    if (i7 < 4096) {
                                        i12 = i17 + 1;
                                        this.K[i17] = (byte) i13;
                                        this.I[i7] = (short) i8;
                                        this.J[i7] = (byte) i13;
                                        i8 = i7 + 1;
                                        if ((i8 & i10) == 0 && i8 < 4096) {
                                            i9++;
                                            i10 += i8;
                                        }
                                        i3 = i12;
                                        i12 = i14;
                                        i14 = i2;
                                        i2 = i10;
                                        i10 = i13;
                                        i13 = i15;
                                        i15 = i9;
                                        i9 = i3;
                                    }
                                }
                            }
                            for (b = i11; b < i4; b++) {
                                this.L[b] = (byte) 0;
                            }
                            h();
                            if (a()) {
                                this.M++;
                                this.z = Bitmap.createBitmap(this.e, this.f, Config.ARGB_8888);
                                iArr = new int[(this.e * this.f)];
                                if (this.E > 0) {
                                    if (this.E == 3) {
                                        b = this.M - 2;
                                        if (b > 0) {
                                            this.A = null;
                                        } else {
                                            this.A = a(b - 1);
                                        }
                                    }
                                    if (this.A != null) {
                                        this.A.getPixels(iArr, 0, this.e, 0, 0, this.e, this.f);
                                        if (this.E == 2) {
                                            b = 0;
                                            if (this.F) {
                                                b = this.n;
                                            }
                                            for (i9 = 0; i9 < this.y; i9++) {
                                                i10 = ((this.w + i9) * this.e) + this.v;
                                                i12 = this.x + i10;
                                                while (i10 < i12) {
                                                    iArr[i10] = b;
                                                    i10++;
                                                }
                                            }
                                        }
                                    }
                                }
                                i12 = 1;
                                i10 = 8;
                                i9 = 0;
                                for (b = 0; b < this.u; b++) {
                                    if (this.p) {
                                        i13 = b;
                                    } else {
                                        if (i9 >= this.u) {
                                            i12++;
                                            switch (i12) {
                                                case 2:
                                                    i9 = 4;
                                                    break;
                                                case 3:
                                                    i9 = 2;
                                                    i10 = 4;
                                                    break;
                                                case 4:
                                                    i9 = 1;
                                                    i10 = 2;
                                                    break;
                                            }
                                        }
                                        i3 = i9;
                                        i9 += i10;
                                        i13 = i3;
                                    }
                                    i13 += this.s;
                                    if (i13 >= this.f) {
                                        i14 = this.e * i13;
                                        i15 = i14 + this.r;
                                        i13 = this.t + i15;
                                        if (this.e + i14 < i13) {
                                            i13 = this.e + i14;
                                        }
                                        i14 = this.t * b;
                                        i2 = i15;
                                        while (i2 < i13) {
                                            i15 = i14 + 1;
                                            i14 = this.k[this.L[i14] & 255];
                                            if (i14 == 0) {
                                                iArr[i2] = i14;
                                            }
                                            i2++;
                                            i14 = i15;
                                        }
                                    }
                                }
                                this.z = Bitmap.createBitmap(iArr, this.e, this.f, Config.ARGB_8888);
                                vector = this.b;
                                bitmap = this.z;
                                i10 = this.G;
                                vector.addElement(new a(bitmap));
                                if (this.F) {
                                    this.k[this.H] = i;
                                }
                                this.E = this.D;
                                this.v = this.r;
                                this.w = this.s;
                                this.x = this.t;
                                this.y = this.u;
                                this.A = this.z;
                                this.n = this.m;
                                this.D = 0;
                                this.F = false;
                                this.G = 0;
                                this.j = null;
                            }
                        }
                        i7 = i9 - 1;
                        i9 = i11 + 1;
                        this.L[i11] = this.K[i7];
                        i16++;
                        i11 = i9;
                        i9 = i15;
                        i15 = i13;
                        i13 = i10;
                        i10 = i2;
                        i3 = i12;
                        i12 = i7;
                        i7 = i8;
                        i8 = i14;
                        i14 = i3;
                    }
                    for (b = i11; b < i4; b++) {
                        this.L[b] = (byte) 0;
                    }
                    h();
                    if (a()) {
                        this.M++;
                        this.z = Bitmap.createBitmap(this.e, this.f, Config.ARGB_8888);
                        iArr = new int[(this.e * this.f)];
                        if (this.E > 0) {
                            if (this.E == 3) {
                                b = this.M - 2;
                                if (b > 0) {
                                    this.A = a(b - 1);
                                } else {
                                    this.A = null;
                                }
                            }
                            if (this.A != null) {
                                this.A.getPixels(iArr, 0, this.e, 0, 0, this.e, this.f);
                                if (this.E == 2) {
                                    b = 0;
                                    if (this.F) {
                                        b = this.n;
                                    }
                                    for (i9 = 0; i9 < this.y; i9++) {
                                        i10 = ((this.w + i9) * this.e) + this.v;
                                        i12 = this.x + i10;
                                        while (i10 < i12) {
                                            iArr[i10] = b;
                                            i10++;
                                        }
                                    }
                                }
                            }
                        }
                        i12 = 1;
                        i10 = 8;
                        i9 = 0;
                        for (b = 0; b < this.u; b++) {
                            if (this.p) {
                                if (i9 >= this.u) {
                                    i12++;
                                    switch (i12) {
                                        case 2:
                                            i9 = 4;
                                            break;
                                        case 3:
                                            i9 = 2;
                                            i10 = 4;
                                            break;
                                        case 4:
                                            i9 = 1;
                                            i10 = 2;
                                            break;
                                    }
                                }
                                i3 = i9;
                                i9 += i10;
                                i13 = i3;
                            } else {
                                i13 = b;
                            }
                            i13 += this.s;
                            if (i13 >= this.f) {
                                i14 = this.e * i13;
                                i15 = i14 + this.r;
                                i13 = this.t + i15;
                                if (this.e + i14 < i13) {
                                    i13 = this.e + i14;
                                }
                                i14 = this.t * b;
                                i2 = i15;
                                while (i2 < i13) {
                                    i15 = i14 + 1;
                                    i14 = this.k[this.L[i14] & 255];
                                    if (i14 == 0) {
                                        iArr[i2] = i14;
                                    }
                                    i2++;
                                    i14 = i15;
                                }
                            }
                        }
                        this.z = Bitmap.createBitmap(iArr, this.e, this.f, Config.ARGB_8888);
                        vector = this.b;
                        bitmap = this.z;
                        i10 = this.G;
                        vector.addElement(new a(bitmap));
                        if (this.F) {
                            this.k[this.H] = i;
                        }
                        this.E = this.D;
                        this.v = this.r;
                        this.w = this.s;
                        this.x = this.t;
                        this.y = this.u;
                        this.A = this.z;
                        this.n = this.m;
                        this.D = 0;
                        this.F = false;
                        this.G = 0;
                        this.j = null;
                    }
                case 59:
                    obj = 1;
                    break;
                default:
                    this.d = 1;
                    break;
            }
        }
    }

    private void e() {
        int i;
        boolean z = true;
        String str = "";
        for (i = 0; i < 6; i++) {
            str = str + ((char) b());
        }
        if (str.startsWith("GIF")) {
            this.e = g();
            this.f = g();
            i = b();
            if ((i & 128) == 0) {
                z = false;
            }
            this.g = z;
            this.h = 2 << (i & 7);
            this.l = b();
            b();
            if (this.g && !a()) {
                this.i = b(this.h);
                this.m = this.i[this.l];
                return;
            }
            return;
        }
        this.d = 1;
    }

    private void f() {
        do {
            c();
            if (this.B[0] == (byte) 1) {
                byte[] bArr = this.B;
                bArr = this.B;
            }
            if (this.C <= 0) {
                return;
            }
        } while (!a());
    }

    private int g() {
        return b() | (b() << 8);
    }

    private void h() {
        do {
            c();
            if (this.C <= 0) {
                return;
            }
        } while (!a());
    }

    public final int a(InputStream inputStream) {
        this.d = 0;
        this.M = 0;
        this.b = new Vector();
        this.i = null;
        this.j = null;
        if (inputStream != null) {
            this.c = inputStream;
            e();
            if (!a()) {
                d();
                if (this.M < 0) {
                    this.d = 1;
                }
            }
        } else {
            this.d = 2;
        }
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.d;
    }

    public final Bitmap a(int i) {
        return (i < 0 || i >= this.M) ? null : ((a) this.b.elementAt(i)).a;
    }
}
