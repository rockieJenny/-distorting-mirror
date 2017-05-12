package com.flurry.sdk;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class du implements dy, dz {
    private dx a;
    private dw b;
    private dv c;
    private RelativeLayout d;

    public du(Context context) {
        a(context);
    }

    public void a(dx dxVar) {
        this.a = dxVar;
    }

    public void a(Uri uri, int i) {
        if (uri != null && this.b != null) {
            this.b.a(uri, i);
        }
    }

    private void a(Context context) {
        if (context != null) {
            this.d = new RelativeLayout(context);
            this.b = new dw(context, this);
            this.c = new dv(context, this);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
            this.d.addView(this.b, layoutParams);
        }
    }

    public void a(int i) {
        if (this.b != null) {
            this.b.a(i);
        }
    }

    public boolean a() {
        if (this.b != null) {
            return this.b.a();
        }
        return false;
    }

    public void b(final int i) {
        fp.a().a(new hq(this) {
            final /* synthetic */ du b;

            public void safeRun() {
                if (this.b.c != null) {
                    this.b.c.a(i);
                }
            }
        });
    }

    public void b() {
        if (this.b != null) {
            this.b.b();
        }
    }

    public int c() {
        if (this.b != null) {
            return this.b.getCurrentPosition();
        }
        return 0;
    }

    public void d() {
        if (this.c != null) {
            this.c.a();
        }
        if (this.b != null && this.b.isPlaying()) {
            this.b.c();
        }
    }

    public View e() {
        return this.d;
    }

    public int f() {
        if (this.b != null) {
            return this.b.d();
        }
        return 0;
    }

    public void a(String str) {
        if (this.a != null) {
            this.a.b(str);
        }
        if (this.c != null && this.b != null) {
            this.c.setMediaPlayer(this.b);
        }
    }

    public void a(String str, final float f, final float f2) {
        if (this.a != null) {
            this.a.a(str, f, f2);
        }
        fp.a().a(new hq(this) {
            final /* synthetic */ du c;

            public void safeRun() {
                if (this.c.c != null) {
                    this.c.c.a(f, f2);
                }
            }
        });
    }

    public void b(String str) {
        if (this.a != null) {
            this.a.a(str);
        }
        if (this.c != null) {
            this.c.a();
        }
        if (this.b != null) {
            this.b.e();
        }
    }

    public void a(String str, int i, int i2, int i3) {
        if (this.a != null) {
            this.a.a(str, i, i2, i3);
        }
    }

    public void g() {
        if (this.a != null) {
            this.a.a();
        }
    }

    public void h() {
        if (this.a != null) {
            this.a.e();
        }
    }

    public void i() {
        if (this.a != null) {
            this.a.b();
        }
    }

    public void a(final int i, final int i2) {
        fp.a().a(new hq(this) {
            final /* synthetic */ du c;

            public void safeRun() {
                if (this.c.c != null) {
                    this.c.c.a(i, i2);
                }
            }
        });
    }

    public void j() {
        if (this.c != null) {
            this.c.a();
            this.c = null;
        }
        if (this.b != null) {
            this.b.e();
            this.b = null;
        }
    }
}
