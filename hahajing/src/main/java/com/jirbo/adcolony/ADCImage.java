package com.jirbo.adcolony;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Iterator;

public class ADCImage {
    static int k;
    static int l;
    Bitmap a;
    Bitmap b;
    Paint c;
    Rect d;
    Rect e;
    int f;
    int g;
    boolean h;
    int i;
    int j;
    boolean m;
    ArrayList<Bitmap> n;

    ADCImage(String filepath) {
        this(filepath, 1.0d);
    }

    ADCImage(String filepath, int width, int height) {
        this(filepath, 1.0d);
        b(width, height);
    }

    ADCImage(String filepath, boolean is_native, boolean down_state) {
        this(filepath, 1.0d, down_state, is_native);
    }

    ADCImage(String filepath, boolean down_state) {
        this(filepath, 1.0d, down_state);
    }

    ADCImage(String filepath, double scale) {
        this(filepath, scale, false);
    }

    ADCImage(String filepath, double scale, boolean down_state) {
        this(filepath, scale, down_state, false);
    }

    ADCImage(String filepath, double scale, boolean down_state, boolean is_native) {
        this.c = new Paint();
        this.d = new Rect();
        this.e = new Rect();
        this.n = new ArrayList();
        try {
            this.a = BitmapFactory.decodeStream(new FileInputStream(filepath));
            this.b = this.a;
            this.f = this.a.getWidth();
            this.g = this.a.getHeight();
            this.i = this.a.getWidth();
            this.j = this.a.getHeight();
            k = this.i;
            l = this.j;
            a(scale);
            this.h = true;
            if (down_state) {
                this.a = convertToMutable(this.a);
                int[] iArr = new int[(this.a.getWidth() * this.a.getHeight())];
                this.a.getPixels(iArr, 0, this.a.getWidth(), 0, 0, this.a.getWidth(), this.a.getHeight());
                int i = 0;
                while (i < iArr.length) {
                    if (iArr[i] < 255 && iArr[i] != 0) {
                        iArr[i] = ((iArr[i] >> 1) & 8355711) | ViewCompat.MEASURED_STATE_MASK;
                    }
                    i++;
                }
                this.a.setPixels(iArr, 0, this.a.getWidth(), 0, 0, this.a.getWidth(), this.a.getHeight());
                this.b = this.a;
            }
            if (!is_native) {
                a.ae.add(this.a);
                this.n.add(this.a);
            }
        } catch (Exception e) {
            e.printStackTrace();
            a.e("Failed to load image " + filepath);
            this.m = true;
        }
    }

    void a(int i, int i2) {
        if (this.a != null) {
            Bitmap createBitmap = Bitmap.createBitmap(this.b, 0, 0, this.i / 3, this.j);
            Bitmap createBitmap2 = Bitmap.createBitmap(this.b, (this.i * 2) / 3, 0, this.i / 3, this.j);
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(this.b, this.i / 3, 0, this.i / 3, this.j), i, this.j, false);
            int[] iArr = new int[((this.i / 3) * this.j)];
            int[] iArr2 = new int[((this.i / 3) * this.j)];
            createBitmap.getPixels(iArr, 0, this.i / 3, 0, 0, this.i / 3, this.j);
            createBitmap2.getPixels(iArr2, 0, this.i / 3, 0, 0, this.i / 3, this.j);
            createScaledBitmap.getPixels(new int[(createScaledBitmap.getWidth() * createScaledBitmap.getHeight())], 0, createScaledBitmap.getWidth(), 0, 0, createScaledBitmap.getWidth(), createScaledBitmap.getHeight());
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (i6 < createScaledBitmap.getHeight()) {
                if (i5 < this.i / 3) {
                    if (i4 < iArr.length) {
                        createScaledBitmap.setPixel(i5, i6, iArr[i4]);
                    }
                    i4++;
                } else if (i5 >= createScaledBitmap.getWidth() - (this.i / 3)) {
                    if (i3 < iArr2.length) {
                        createScaledBitmap.setPixel(i5, i6, iArr2[i3]);
                    }
                    i3++;
                }
                i5++;
                if (i5 == createScaledBitmap.getWidth()) {
                    i6++;
                    i5 = 0;
                }
            }
            this.a = createScaledBitmap;
            this.b = this.a;
            this.f = this.a.getWidth();
            this.g = this.a.getHeight();
            this.i = this.f;
            this.j = this.g;
            this.d.right = this.f;
            this.d.bottom = this.g;
        }
    }

    void b(int i, int i2) {
        if (this.a != null && !this.a.isRecycled()) {
            if (i != this.f || i2 != this.g) {
                this.a = Bitmap.createScaledBitmap(this.b, i, i2, true);
                this.f = i;
                this.g = i2;
                this.d.right = i;
                this.d.bottom = i2;
                a.ae.add(this.a);
                this.n.add(this.a);
            }
        }
    }

    void a(double d) {
        a(d, false);
    }

    void a(double d, boolean z) {
        if (this.a != null && !this.a.isRecycled()) {
            if (d != 1.0d) {
                int width = (int) (((double) this.b.getWidth()) * d);
                int height = (int) (((double) this.b.getHeight()) * d);
                if (!(width == this.f && height == this.g)) {
                    this.f = width;
                    this.g = height;
                    this.a = Bitmap.createScaledBitmap(this.b, this.f, this.g, true);
                    if (!z) {
                        a.ae.add(this.a);
                        this.n.add(this.a);
                    }
                }
                if (z) {
                    this.b.recycle();
                    this.b = null;
                }
            }
            this.d.right = this.f;
            this.d.bottom = this.g;
        }
    }

    void a() {
        Iterator it = this.n.iterator();
        while (it.hasNext()) {
            ((Bitmap) it.next()).recycle();
        }
        this.n.clear();
    }

    void c(int i, int i2) {
        this.e.left = i;
        this.e.top = i2;
        this.e.right = this.f + i;
        this.e.bottom = this.g + i2;
    }

    int[] b() {
        return new int[]{this.e.left, this.e.top};
    }

    int c() {
        return this.e.left;
    }

    int d() {
        return this.e.top;
    }

    void d(int i, int i2) {
        c((i - this.f) / 2, (i2 - this.g) / 2);
    }

    void a(Canvas canvas, int i, int i2) {
        c(i, i2);
        a(canvas);
    }

    void a(Canvas canvas) {
        if (this.a != null && !this.a.isRecycled()) {
            canvas.drawBitmap(this.a, this.d, this.e, this.c);
        }
    }

    public static Bitmap convertToMutable(Bitmap img_in) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            int i = k;
            int i2 = l;
            Config config = img_in.getConfig();
            FileChannel channel = randomAccessFile.getChannel();
            Buffer map = channel.map(MapMode.READ_WRITE, 0, (long) (img_in.getRowBytes() * i2));
            img_in.copyPixelsToBuffer(map);
            img_in = Bitmap.createBitmap(i, i2, config);
            map.position(0);
            img_in.copyPixelsFromBuffer(map);
            channel.close();
            randomAccessFile.close();
            file.delete();
            return img_in;
        } catch (Exception e) {
            e.printStackTrace();
            return img_in;
        }
    }
}
