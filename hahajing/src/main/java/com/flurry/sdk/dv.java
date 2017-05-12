package com.flurry.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import java.util.concurrent.atomic.AtomicBoolean;

class dv extends MediaController {
    private static final String a = dv.class.getSimpleName();
    private static final int b = hn.b(16);
    private static final int c = hn.b(5);
    private static final int d = hn.b(35);
    private RelativeLayout e;
    private PopupWindow f;
    private dy g;
    private Bitmap h;
    private Bitmap i;
    private Bitmap j;
    private ImageButton k;
    private ImageButton l;
    private ImageButton m;
    private ep n;
    private int o = 0;
    private int p = 0;
    private AtomicBoolean q = new AtomicBoolean(false);

    public dv(Context context, dy dyVar) {
        super(context);
        a(context, dyVar);
    }

    public void a(int i) {
        if (!this.q.get()) {
            if (this.p != i || this.o <= 3) {
                gd.a(3, a, "Update UI with visible flag: " + i);
                this.p = i;
                if (this.f != null) {
                    if (!this.f.isShowing()) {
                        this.f.update(0, 0, d(), e());
                        this.f.showAtLocation(this.e, 17, 0, 0);
                    }
                    b(i);
                    c(i);
                    e(i);
                    d(i);
                    return;
                }
                return;
            }
            gd.a(3, a, "No change in visible flag." + this.o);
        }
    }

    public void a(int i, int i2) {
        if (!this.q.get() && this.f != null) {
            gd.a(3, a, "Update screenSizeChanged: " + d() + "*" + e());
            this.f.update(0, 0, d(), e());
            this.f.showAtLocation(this.e, 17, 0, 0);
            b(this.p);
            c(this.p);
            e(this.p);
            d(this.p);
        }
    }

    public void a(float f, float f2) {
        if (this.n != null) {
            this.o = ((int) f2) / 1000;
            int i = (((int) f) / 1000) - this.o;
            this.n.b((int) f);
            this.n.a(i);
        }
    }

    private void a(Context context, dy dyVar) {
        if (context != null) {
            this.g = dyVar;
            c();
            a(context);
            b(context);
            c(context);
            d(context);
            e(context);
            this.e.setFocusableInTouchMode(true);
            this.e.setOnKeyListener(new OnKeyListener(this) {
                final /* synthetic */ dv a;

                {
                    this.a = r1;
                }

                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() != 4 || keyEvent.getAction() != 1) {
                        return false;
                    }
                    if (this.a.g == null || !this.a.b()) {
                        return true;
                    }
                    this.a.g.g();
                    return true;
                }
            });
        }
    }

    private void c() {
        el elVar = new el();
        elVar.h();
        this.h = elVar.e();
        this.j = elVar.g();
        this.i = elVar.f();
    }

    private void a(Context context) {
        gd.a(3, a, "Update initLayout Video: " + context.toString());
        this.f = new PopupWindow(context);
        this.q.set(false);
        this.e = new RelativeLayout(context);
        this.e.setBackgroundColor(0);
        this.f.setBackgroundDrawable(null);
        this.f.setFocusable(true);
        this.f.setContentView(this.e);
        this.f.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ dv a;

            {
                this.a = r1;
            }

            public void onDismiss() {
                this.a.q.set(true);
            }
        });
    }

    private void b(Context context) {
        this.l = new ImageButton(context);
        this.l.setPadding(0, 0, 0, 0);
        this.l.setImageBitmap(this.h);
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(ViewCompat.MEASURED_STATE_MASK);
        gradientDrawable.setShape(1);
        gradientDrawable.setAlpha(178);
        if (VERSION.SDK_INT >= 16) {
            this.l.setBackground(gradientDrawable);
        } else {
            this.l.setBackgroundDrawable(gradientDrawable);
        }
        this.l.setVisibility(4);
        this.l.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ dv a;

            {
                this.a = r1;
            }

            public void onClick(View view) {
                if (this.a.g != null) {
                    this.a.g.g();
                }
            }
        });
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.addRule(10);
        layoutParams.setMargins(c, c, c, c);
        this.e.addView(this.l, layoutParams);
    }

    private void c(Context context) {
        this.n = new ep(context, d, d);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12);
        layoutParams.addRule(9);
        layoutParams.setMargins(b, b, b, b);
        this.n.a().setVisibility(0);
        this.e.addView(this.n.a(), layoutParams);
    }

    private void d(Context context) {
        this.m = new ImageButton(context);
        this.m.setPadding(0, 0, 0, 0);
        this.m.setBackgroundColor(0);
        this.m.setImageBitmap(this.j);
        this.m.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ dv a;

            {
                this.a = r1;
            }

            public void onClick(View view) {
                if (this.a.g != null) {
                    this.a.g.h();
                }
            }
        });
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.addRule(12);
        layoutParams.setMargins(b, b, b, b);
        this.m.setVisibility(0);
        this.e.addView(this.m, layoutParams);
    }

    private void e(Context context) {
        this.k = new ImageButton(context);
        this.k.setPadding(0, 0, 0, 0);
        this.k.setBackgroundColor(0);
        this.k.setImageBitmap(this.i);
        this.k.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ dv a;

            {
                this.a = r1;
            }

            public void onClick(View view) {
                if (this.a.g != null) {
                    this.a.k.setVisibility(4);
                    this.a.g.i();
                }
            }
        });
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        layoutParams.setMargins(b, b, b, b);
        this.k.setVisibility(0);
        this.e.addView(this.k, layoutParams);
    }

    private void b(int i) {
        if (((i & 1) > 0 ? 1 : 0) != 0) {
            this.l.setVisibility(0);
        } else {
            this.l.setVisibility(4);
        }
    }

    private void c(int i) {
        if (((i & 2) > 0 ? 1 : 0) != 0) {
            this.n.a().setVisibility(0);
        } else {
            this.n.a().setVisibility(4);
        }
    }

    private void d(int i) {
        if (((i & 4) > 0 ? 1 : 0) != 0) {
            this.m.setVisibility(0);
        } else {
            this.m.setVisibility(4);
        }
    }

    private void e(int i) {
        if (((i & 8) > 0 ? 1 : 0) != 0) {
            this.k.setVisibility(0);
        } else {
            this.k.setVisibility(4);
        }
    }

    private int d() {
        return hn.c().widthPixels;
    }

    private int e() {
        return hn.c().heightPixels;
    }

    public void a() {
        if (this.f != null) {
            gd.a(3, a, "Reset video view: ");
            if (this.f.isShowing() && !this.q.get()) {
                a(0);
                this.f.setFocusable(false);
                this.f.dismiss();
            }
            this.f = null;
        }
    }

    public boolean b() {
        return this.l.isShown();
    }
}
