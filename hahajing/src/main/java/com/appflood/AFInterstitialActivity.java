package com.appflood;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.appflood.b.b;
import com.appflood.c.c;
import com.appflood.c.c.a;
import com.appflood.c.d;
import com.appflood.c.e;
import com.appflood.c.f;
import com.appflood.c.g;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.TimerTask;

public class AFInterstitialActivity extends Activity implements OnClickListener, a {
    private RelativeLayout a;
    private Button b;
    private Button c;
    private ImageView d;
    private LinearLayout e;
    private ProgressBar f;
    private TextView g;
    private c h = null;
    private float i = 1.5f;
    private float j = 1.5f;
    private boolean k = false;
    private int l = 418;
    private int m = 250;
    private int n = 3;
    private int o = 202;
    private int p = 217;
    private int q = 116;
    private int r = 490;
    private int s = 196;
    private int t = 43;

    private void b() {
        if (this.h != null) {
            this.h.c = null;
            this.h = null;
        }
        finish();
    }

    public final void a() {
        this.e.setVisibility(0);
        this.c.setVisibility(8);
        f.a(new TimerTask(this) {
            final /* synthetic */ AFInterstitialActivity a;

            {
                this.a = r1;
            }

            public final void run() {
                f.a(new Runnable(this) {
                    private /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        this.a.a.e.setVisibility(8);
                        this.a.a.c.setVisibility(0);
                    }
                });
            }
        }, (long) g.a().e);
    }

    public final void a(boolean z) {
        e.a().a(z, 128);
        if (z) {
            Animation animationSet = new AnimationSet(true);
            Animation scaleAnimation = new ScaleAnimation(0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, 1, 0.5f, 1, 0.5f);
            animationSet.addAnimation(new AlphaAnimation(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE));
            animationSet.addAnimation(scaleAnimation);
            animationSet.setStartOffset(0);
            animationSet.setDuration(500);
            animationSet.setInterpolator(new AccelerateInterpolator());
            this.a.startAnimation(animationSet);
            this.a.setVisibility(0);
            return;
        }
        b();
    }

    public void onClick(View view) {
        if (view == this.b) {
            c cVar = this.h;
            try {
                if (com.appflood.e.c.s == 1 && cVar.c != null) {
                    cVar.c.performClick();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        b();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        this.k = getIntent().getBooleanExtra("isFull", false);
        if (!this.k) {
            this.r -= com.appflood.AFListActivity.AnonymousClass1.c(this);
        }
        this.i = ((float) com.appflood.e.c.g) / 480.0f;
        this.j = ((float) com.appflood.e.c.h) / 800.0f;
        this.a = new RelativeLayout(this);
        this.a.setVisibility(8);
        new b(d.z + "interstitial_inter_bg.jpg", (byte) 0).a(this.a);
        setContentView(this.a, new LayoutParams(-1, -1));
        this.d = new ImageView(this);
        this.d.setId(101);
        int i = (int) (((float) this.n) * this.i);
        this.d.setPadding(i, i, i, i);
        this.d.setBackgroundColor(-1);
        this.d.setScaleType(ScaleType.FIT_XY);
        ViewGroup.LayoutParams layoutParams = new LayoutParams((int) (((float) this.l) * this.i), (int) (((float) this.m) * this.i));
        layoutParams.addRule(10);
        layoutParams.topMargin = (int) ((this.j * ((float) this.o)) - ((float) (com.appflood.e.c.g <= 320 ? 20 : 0)));
        layoutParams.addRule(14);
        this.a.addView(this.d, layoutParams);
        this.b = new Button(this);
        this.b.setId(102);
        com.appflood.AFListActivity.AnonymousClass1.a(this.b, "interstitial_download.png", "interstitial_download2.png");
        this.b.setOnClickListener(this);
        layoutParams = new LayoutParams((int) (((float) this.p) * this.i), (int) (((float) this.q) * this.i));
        layoutParams.addRule(10);
        layoutParams.addRule(14);
        layoutParams.topMargin = (int) ((this.j * ((float) this.r)) - ((float) (com.appflood.e.c.g <= 320 ? 5 : 0)));
        this.a.addView(this.b, layoutParams);
        this.c = new Button(this);
        com.appflood.AFListActivity.AnonymousClass1.a(this.c, "interstitial_continue_0.png", "interstitial_continue_1.png");
        this.c.setOnClickListener(this);
        this.c.setVisibility(8);
        ViewGroup.LayoutParams layoutParams2 = new LayoutParams((int) (((float) this.s) * this.i), (int) (((float) this.t) * this.i));
        layoutParams2.addRule(3, this.b.getId());
        layoutParams2.addRule(14);
        this.a.addView(this.c, layoutParams2);
        this.e = new LinearLayout(this);
        this.a.addView(this.e, layoutParams2);
        this.f = new ProgressBar(this, null, 16842873);
        layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 16;
        layoutParams2.leftMargin = 5;
        this.e.addView(this.f, layoutParams2);
        this.g = new TextView(this);
        this.g.setTextColor(-1);
        this.g.setTextSize(18.0f);
        this.g.setText(d.c);
        this.g.setGravity(17);
        this.e.addView(this.g, new LinearLayout.LayoutParams(-1, -1));
        this.h = new c(this.d, this);
        this.h.g = false;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && !(this.c.getVisibility() == 8 && this.a.getVisibility() == 0)) {
            b();
        }
        return true;
    }
}
