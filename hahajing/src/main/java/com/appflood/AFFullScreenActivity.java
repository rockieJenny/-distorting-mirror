package com.appflood;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import com.appflood.c.a;
import com.appflood.c.b;
import com.appflood.c.e;
import com.appflood.c.f;
import com.appflood.e.c;
import com.appflood.e.j;
import com.appflood.mraid.AFBannerWebView;
import com.flurry.android.AdCreative;
import com.google.android.gms.cast.TextTrackStyle;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.HashMap;

public class AFFullScreenActivity extends Activity implements b {
    private RelativeLayout a = null;
    private float b = 480.0f;
    private float c = 800.0f;
    private boolean d = false;
    private int e = 0;
    private a f;
    private int g = 0;
    private int h = 0;
    private View i;
    private com.appflood.f.b j;
    private int k = 0;
    private int l = 0;

    private void a() {
        if (this.a != null) {
            this.a.removeAllViews();
        }
        if (this.f != null) {
            this.f.d();
            this.f = null;
        }
        e.a().a(false, j.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, Integer.valueOf(4)));
        finish();
    }

    private void b() {
        if (f.g()) {
            a();
        } else {
            f.a(new Runnable(this) {
                private /* synthetic */ AFFullScreenActivity a;

                {
                    this.a = r1;
                }

                public final void run() {
                    this.a.a();
                }
            });
        }
    }

    static /* synthetic */ void j(AFFullScreenActivity aFFullScreenActivity) {
        Animation animationSet = new AnimationSet(true);
        Animation scaleAnimation = new ScaleAnimation(0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, 1, 0.5f, 1, 0.5f);
        animationSet.addAnimation(new AlphaAnimation(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE));
        animationSet.addAnimation(scaleAnimation);
        animationSet.setStartOffset(0);
        animationSet.setDuration(500);
        animationSet.setInterpolator(new AccelerateInterpolator());
        aFFullScreenActivity.a.startAnimation(animationSet);
        aFFullScreenActivity.a.setVisibility(0);
    }

    public void onClick() {
    }

    public void onClose() {
        b();
    }

    public void onComplete() {
    }

    public void onCreate(Bundle bundle) {
        boolean z = true;
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                z = extras.getBoolean("isFull");
                this.d = extras.getBoolean("isPortrait");
                this.e = extras.getInt("titlebar");
            }
            if (this.d) {
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
            requestWindowFeature(1);
            if (z) {
                getWindow().setFlags(1024, 1024);
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            getWindow().setBackgroundDrawableResource(17170445);
            this.b = (float) displayMetrics.widthPixels;
            this.c = (float) (displayMetrics.heightPixels - this.e);
            this.k = com.appflood.AFListActivity.AnonymousClass1.a((Context) this, 3);
            if (this.a == null) {
                this.a = new RelativeLayout(this);
                this.a.setBackgroundColor(-939524096);
                this.a.setVisibility(4);
            }
            setContentView(this.a);
            this.g = (int) this.b;
            this.h = (int) this.c;
            this.f = new a(this);
            this.f.b(15);
            this.f.a((b) this);
            this.f.a(this.g, this.h, 7);
        } catch (Throwable th) {
            j.b(th, "onCreate");
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onFinish(HashMap<String, Object> hashMap) {
        "result = " + hashMap + "w " + this.b + " h " + this.c;
        j.a();
        boolean booleanValue = ((Boolean) hashMap.get("result")).booleanValue();
        final int intValue = ((Integer) hashMap.get("w")).intValue();
        final int intValue2 = ((Integer) hashMap.get("h")).intValue();
        final String str = (String) hashMap.get(AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
        final boolean booleanValue2 = ((Boolean) (hashMap.containsKey("resize") ? hashMap.get("resize") : Boolean.valueOf(false))).booleanValue();
        if (booleanValue) {
            this.l = 0;
            f.a(new Runnable(this) {
                private /* synthetic */ AFFullScreenActivity e;

                public final void run() {
                    int a = (int) (((double) this.e.k) * 2.5d);
                    if (!booleanValue2) {
                        LayoutParams layoutParams;
                        this.e.a.removeAllViews();
                        if (this.e.i instanceof AFBannerWebView) {
                            ((AFBannerWebView) this.e.i).destroy();
                        }
                        int e = this.e.g;
                        int f = this.e.h;
                        "ww " + e + " hh " + f;
                        j.a();
                        if ("html".equals(str)) {
                            f = -1;
                            e = -1;
                        } else if (intValue > 0 && intValue2 > 0) {
                            if (AdCreative.kFormatBanner.equals(str)) {
                                if (((float) e) > this.e.b - ((float) a)) {
                                    e = (int) (this.e.b - ((float) a));
                                    f = (intValue2 * e) / intValue;
                                }
                                if (((float) f) > this.e.c - ((float) a)) {
                                    f = (int) (this.e.c - ((float) a));
                                    e = (intValue * f) / intValue2;
                                }
                            } else {
                                e = intValue;
                                f = intValue2;
                            }
                        }
                        if (e > 0 && f > 0) {
                            if (this.e.j == null) {
                                this.e.j = new com.appflood.f.b(this.e, this.e.k);
                            }
                            layoutParams = new RelativeLayout.LayoutParams(e, f);
                            layoutParams.addRule(13);
                            this.e.a.addView(this.e.j, layoutParams);
                            e -= a;
                            f -= a;
                        }
                        layoutParams = new RelativeLayout.LayoutParams(e, f);
                        layoutParams.addRule(13);
                        this.e.i = this.e.f.c();
                        this.e.a.addView(this.e.i, layoutParams);
                        AFFullScreenActivity.j(this.e);
                        " ww " + e + " hh " + f + " strokesie " + this.e.k;
                        j.a();
                    } else if (intValue > 0 && intValue2 > 0) {
                        LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(intValue, intValue2);
                        layoutParams2.addRule(13);
                        if (this.e.j == null) {
                            this.e.j = new com.appflood.f.b(this.e, this.e.k);
                        }
                        this.e.a.updateViewLayout(this.e.j, layoutParams2);
                        layoutParams2 = new RelativeLayout.LayoutParams(intValue - a, intValue2 - a);
                        layoutParams2.addRule(13);
                        " 666666666   " + this.e.a;
                        j.a();
                        this.e.a.updateViewLayout(this.e.i, layoutParams2);
                    }
                }
            });
        } else if (!hashMap.containsKey("video_error")) {
            int i = this.l + 1;
            this.l = i;
            if (i > 1) {
                b();
            } else {
                this.f.b();
            }
        } else {
            return;
        }
        e.a().a(booleanValue, 4);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        try {
            b();
        } catch (Throwable th) {
        }
        return true;
    }
}
