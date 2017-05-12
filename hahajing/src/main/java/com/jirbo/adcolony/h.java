package com.jirbo.adcolony;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import java.util.ArrayList;

class h extends View {
    static double p;
    static String q = "";
    static String r = "";
    static boolean s = true;
    static Paint t = new Paint(1);
    static float[] u = new float[80];
    int A;
    int B;
    int C;
    int D;
    int E;
    String F;
    AdColonyV4VCAd G;
    ADCImage a;
    ADCImage b;
    ADCImage c;
    ADCImage d;
    ADCImage e;
    ADCImage f;
    ADCImage g;
    ADCImage h;
    double i = 2.8d;
    double j = 2.05d;
    double k = 1.3d;
    double l = 2.5d;
    double m = 1.5d;
    boolean n;
    ArrayList<ADCImage> o = new ArrayList();
    AdColonyInterstitialAd v;
    long w = System.currentTimeMillis();
    int x;
    int y;
    int z;

    h() {
        super(a.b());
    }

    public boolean a() {
        double d = 2.5d;
        double d2 = 0.8d;
        if (this.a == null) {
            this.a = new ADCImage(a.j("pre_popup_bg"));
            this.b = new ADCImage(a.j("v4vc_logo"));
            this.c = new ADCImage(a.j("yes_button_normal"));
            this.d = new ADCImage(a.j("yes_button_down"));
            this.e = new ADCImage(a.j("no_button_normal"));
            this.f = new ADCImage(a.j("no_button_down"));
            this.h = new ADCImage(a.j("done_button_normal"));
            this.g = new ADCImage(a.j("done_button_down"));
            this.o.add(this.a);
            this.o.add(this.b);
            this.o.add(this.c);
            this.o.add(this.d);
            this.o.add(this.e);
            this.o.add(this.f);
            this.o.add(this.h);
            this.o.add(this.g);
            Display defaultDisplay = a.b().getWindowManager().getDefaultDisplay();
            int width = defaultDisplay.getWidth();
            int height = defaultDisplay.getHeight();
            double d3 = height > width ? ((double) (height - width)) / 360.0d : ((double) (width - height)) / 360.0d;
            if (d3 < 0.8d && !a.m) {
                this.n = true;
            }
            if (d3 <= 2.5d) {
                d = d3;
            }
            if (d >= 0.8d) {
                d2 = d;
            } else if (!a.m) {
                d2 = 1.7d;
            }
            p = d2;
            if (this.n) {
                this.i = 2.6d;
                this.j = 1.8d;
                this.k = 1.0d;
                this.l = 2.2d;
                this.m = 1.2d;
            }
            this.a.a(d2 / 1.8d);
            this.b.a(d2 / 1.8d);
            this.d.a(d2 / 1.8d);
            this.f.a(d2 / 1.8d);
            this.c.a(d2 / 1.8d);
            this.e.a(d2 / 1.8d);
            this.g.a(d2 / 1.8d);
            this.h.a(d2 / 1.8d);
            t.setTextSize((float) (18.0d * d2));
            if (this.n) {
                t.setTextSize((float) (d2 * 9.0d));
            }
            t.setFakeBoldText(true);
        }
        return true;
    }

    public h(String str, int i, AdColonyInterstitialAd adColonyInterstitialAd) {
        super(AdColony.activity());
        this.F = str;
        this.E = i;
        this.v = adColonyInterstitialAd;
        if (a()) {
            AdColony.activity().addContentView(this, new LayoutParams(-1, -1, 17));
        }
    }

    int a(String str) {
        t.getTextWidths(str, u);
        float f = 0.0f;
        for (int i = 0; i < str.length(); i++) {
            f += u[i];
        }
        return (int) f;
    }

    int b() {
        return (int) t.getTextSize();
    }

    void a(String str, int i, int i2, Canvas canvas) {
        int a = i - (a(str) / 2);
        t.setColor(-986896);
        canvas.drawText(str, (float) (a + 1), (float) (i2 + 1), t);
        t.setColor(-8355712);
        canvas.drawText(str, (float) a, (float) i2, t);
    }

    void b(String str, int i, int i2, Canvas canvas) {
        int a = i - (a(str) / 2);
        t.setColor(-8355712);
        canvas.drawText(str, (float) (a + 2), (float) (i2 + 2), t);
        t.setColor(-1);
        canvas.drawText(str, (float) a, (float) i2, t);
    }

    void c(String str, int i, int i2, Canvas canvas) {
        b(str, (this.c.f / 2) + i, ((this.c.g / 2) + i2) + ((b() * 4) / 10), canvas);
    }

    boolean a(int i, int i2, int i3, int i4) {
        if (i >= i3 && i2 >= i4 && i < this.c.f + i3 && i2 < this.c.g + i4) {
            return true;
        }
        return false;
    }

    void a(String str, String str2) {
        int a = a(str);
        q = "";
        r = "";
        String str3 = "";
        if (a > (this.a.f - a("WW")) - a(str2)) {
            s = false;
            a = 0;
            String str4 = str3;
            int i = 0;
            while (i < (this.a.f - a("WW")) - a(str2)) {
                str4 = str4 + str.charAt(a);
                a++;
                i = a(str4);
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < a) {
                if (str4.charAt(i2) != ' ' || i2 < 5) {
                    q = i3 < 5 ? str.substring(0, a) : q;
                    i = i3;
                } else {
                    q = str.substring(0, i2);
                    i = i2;
                }
                i2++;
                i3 = i;
            }
            r = i3 < 5 ? str.substring(a) : str.substring(i3);
            return;
        }
        s = true;
        q = str;
        r = "";
    }

    void c() {
        double d = this.n ? 12.0d : 16.0d;
        Display defaultDisplay = a.b().getWindowManager().getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        this.x = (width - this.a.f) / 2;
        this.y = ((height - this.a.g) / 2) - 80;
        this.z = this.x + (this.a.f / 2);
        this.A = this.y + (this.a.g / 2);
        this.D = this.y + ((int) (((double) this.a.g) - (((double) this.c.g) + (p * d))));
        this.B = this.x + ((int) (p * d));
        this.C = ((int) (((double) this.a.f) - ((d * p) + ((double) this.c.f)))) + this.x;
    }
}
