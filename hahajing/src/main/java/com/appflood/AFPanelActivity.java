package com.appflood;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.appflood.b.b;
import com.appflood.c.d;
import com.appflood.c.e;
import com.appflood.c.f;
import com.appflood.e.c;
import com.appflood.e.j;
import com.appflood.f.g;
import com.appflood.f.h;
import com.google.android.gms.cast.TextTrackStyle;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.TimerTask;

public class AFPanelActivity extends Activity {
    private static int u = 5;
    private RelativeLayout a = null;
    private g b = null;
    private h c = null;
    private ImageView d = null;
    private ImageView e = null;
    private RelativeLayout f = null;
    private RelativeLayout g = null;
    private float h = 480.0f;
    private float i = 800.0f;
    private int j = 58;
    private int k = 70;
    private int l = 16;
    private int m = 16;
    private float n;
    private float o;
    private int p = 58;
    private int q = 25;
    private int r = 0;
    private float s = TextTrackStyle.DEFAULT_FONT_SCALE;
    private float t = TextTrackStyle.DEFAULT_FONT_SCALE;
    private int v;
    private boolean w = true;
    private boolean x = false;

    private void a() {
        this.g.setClickable(false);
        Animation translateAnimation = this.r == 0 ? new TranslateAnimation(0.0f, 0.0f, 0.0f, (-this.i) - 8.0f) : new TranslateAnimation(0.0f, 0.0f, 0.0f, this.i + 8.0f);
        translateAnimation.setDuration(500);
        this.a.startAnimation(translateAnimation);
        f.a(new TimerTask(this) {
            final /* synthetic */ AFPanelActivity a;

            {
                this.a = r1;
            }

            public final void run() {
                f.a(new Runnable(this) {
                    private /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        try {
                            this.a.a.f.removeAllViews();
                            this.a.a.a.removeAllViews();
                            this.a.a.finish();
                        } catch (Throwable th) {
                        }
                    }
                });
            }
        }, 500);
        e.a().a(false, j.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, Integer.valueOf(2)));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                this.r = extras.getInt("showType");
                this.w = extras.getBoolean("isFull");
                this.x = extras.getBoolean("isPortrait");
                this.v = extras.getInt("titlebar");
            }
            requestWindowFeature(1);
            if (this.x) {
                if (c.j >= 9) {
                    setRequestedOrientation(7);
                } else {
                    setRequestedOrientation(1);
                }
            } else if (c.j >= 9) {
                setRequestedOrientation(6);
            } else {
                setRequestedOrientation(0);
            }
            if (this.w) {
                getWindow().setFlags(1024, 1024);
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.h = (float) displayMetrics.widthPixels;
            this.i = (float) displayMetrics.heightPixels;
            if (!this.w) {
                this.i -= (float) this.v;
            }
            getWindow().setBackgroundDrawableResource(17170445);
            if (this.x) {
                this.t = this.h / 480.0f;
                this.j = (int) (this.t * 58.0f);
                this.l = (int) (16.0f * this.t);
                this.n = (((float) ((u * 2) + 448)) * this.h) / 480.0f;
                this.o = (this.n * 655.0f) / 448.0f;
                this.s = this.h / 480.0f;
                if ((this.o + ((float) this.j)) + ((float) this.l) > this.i) {
                    this.o = (((float) ((u * 2) + 655)) * this.i) / 800.0f;
                    this.n = (this.o * 448.0f) / 655.0f;
                    this.s = this.i / 800.0f;
                }
                this.j = (int) (this.t * 58.0f);
                this.l = (int) (16.0f * this.t);
                this.m = (int) ((this.h - this.n) / 2.0f);
                this.k = (int) (((this.i - ((float) this.j)) - this.o) - ((float) this.l));
                this.p = (int) (this.t * 58.0f);
                this.q = (int) (25.0f * this.t);
            } else {
                this.t = this.i / 480.0f;
                this.j = (int) (61.0f * this.t);
                this.k = (int) (20.0f * this.t);
                this.o = 391.0f * this.t;
                this.n = (this.o * 722.0f) / 391.0f;
                this.s = this.i / 480.0f;
                if (this.n > this.h) {
                    this.n = (722.0f * this.h) / 800.0f;
                    this.o = (this.n * 391.0f) / 722.0f;
                    this.k = (int) (((this.i - ((float) this.j)) - ((float) this.l)) - this.o);
                    this.s = this.h / 800.0f;
                }
                this.m = (int) ((this.h - this.n) / 2.0f);
                this.l = (int) (((this.i - this.o) - ((float) this.j)) / 2.0f);
                this.p = (int) (this.t * 58.0f);
                this.q = (int) (25.0f * this.t);
            }
            this.a = new RelativeLayout(this);
            this.a.setBackgroundColor(-16379600);
            this.c = new h(this, this.s);
            this.c.setId(1);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, this.j);
            layoutParams.addRule(10);
            this.a.addView(this.c, layoutParams);
            this.f = new RelativeLayout(this);
            this.f.setBackgroundColor(-16379600);
            layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(3, 1);
            this.a.addView(this.f, layoutParams);
            this.e = new ImageView(this);
            this.g = new RelativeLayout(this);
            layoutParams = new RelativeLayout.LayoutParams(this.q, this.q);
            layoutParams.addRule(13);
            this.g.addView(this.e, layoutParams);
            this.g.setOnClickListener(new OnClickListener(this) {
                private /* synthetic */ AFPanelActivity a;

                {
                    this.a = r1;
                }

                public final void onClick(View view) {
                    try {
                        this.a.a();
                    } catch (Throwable th) {
                    }
                }
            });
            layoutParams = new RelativeLayout.LayoutParams(this.p, this.p);
            layoutParams.addRule(10);
            layoutParams.addRule(11);
            this.a.addView(this.g, layoutParams);
            this.b = new g(this, this.x, this.s, u);
            RelativeLayout relativeLayout = this.f;
            View view = this.b;
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) this.n, (int) this.o);
            layoutParams2.leftMargin = this.m;
            layoutParams2.topMargin = this.l;
            relativeLayout.addView(view, layoutParams2);
            new RelativeLayout.LayoutParams(-2, -2).addRule(13);
            this.d = new ImageView(this);
            layoutParams = new RelativeLayout.LayoutParams((int) this.n, this.k);
            layoutParams.leftMargin = this.m;
            layoutParams.addRule(12);
            this.d.setLayoutParams(layoutParams);
            if (!this.x) {
                this.d.setVisibility(8);
            }
            this.f.addView(this.d, layoutParams);
            setContentView(this.a, new RelativeLayout.LayoutParams(-1, -1));
            if (!j.g(d.x)) {
                new b(d.x).f();
            }
            new b(d.z + "closeBtn2.png", (byte) 0).a(this.e);
            new b(d.z + "slogan.png", (byte) 0).a(this.d);
            new b(d.z + (this.x ? "14K-STAR.jpg" : "24K-STAR.jpg"), (byte) 0).a(this.f);
            Animation translateAnimation = this.r == 0 ? new TranslateAnimation(0.0f, 0.0f, -this.i, 0.0f) : new TranslateAnimation(0.0f, 0.0f, this.i, 0.0f);
            translateAnimation.setDuration(500);
            this.a.startAnimation(translateAnimation);
        } catch (Throwable th) {
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        try {
            a();
        } catch (Throwable th) {
        }
        return true;
    }
}
