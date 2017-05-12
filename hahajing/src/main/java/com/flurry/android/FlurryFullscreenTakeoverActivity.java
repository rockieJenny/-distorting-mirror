package com.flurry.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.flurry.sdk.aw;
import com.flurry.sdk.co;
import com.flurry.sdk.cx;
import com.flurry.sdk.ea;
import com.flurry.sdk.ec;
import com.flurry.sdk.ec.a;
import com.flurry.sdk.ee;
import com.flurry.sdk.ek;
import com.flurry.sdk.fp;
import com.flurry.sdk.fx;
import com.flurry.sdk.fy;
import com.flurry.sdk.fz;
import com.flurry.sdk.gd;
import com.flurry.sdk.hc;
import com.flurry.sdk.hq;
import com.flurry.sdk.i;
import com.flurry.sdk.r;
import java.util.Collections;
import java.util.Map;

public final class FlurryFullscreenTakeoverActivity extends Activity {
    public static final String EXTRA_KEY_AD_OBJECT_ID = "ad_object_id";
    public static final String EXTRA_KEY_AD_OBJECT_LEGACY = "ad_object_legacy";
    public static final String EXTRA_KEY_CLOSE_AD = "close_ad";
    public static final String EXTRA_KEY_SEND_Y_COOKIES = "send_y_cookies";
    public static final String EXTRA_KEY_URL = "url";
    private static final String a = FlurryFullscreenTakeoverActivity.class.getSimpleName();
    private ViewGroup b;
    private ec c;
    private boolean d;
    private r e;
    private ek f;
    private boolean g = false;
    private fy<ea> h = new fy<ea>(this) {
        final /* synthetic */ FlurryFullscreenTakeoverActivity a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ea) fxVar);
        }

        public void a(final ea eaVar) {
            fp.a().a(new hq(this) {
                final /* synthetic */ AnonymousClass1 b;

                public void safeRun() {
                    switch (eaVar.e) {
                        case RELOAD_ACTIVITY:
                            gd.a(3, FlurryFullscreenTakeoverActivity.a, "RELOAD_ACTIVITY Event was fired for adObject:" + eaVar.a.d() + " for url:" + eaVar.b + " and should Close Ad:" + eaVar.c);
                            this.b.a.f = new ek(eaVar.a, eaVar.b, eaVar.c, eaVar.d);
                            this.b.a.e = this.b.a.f.c();
                            if (this.b.a.e == null) {
                                gd.b(FlurryFullscreenTakeoverActivity.a, "Cannot launch Activity. No Ad Controller");
                                this.b.a.finish();
                                return;
                            }
                            this.b.a.f();
                            this.b.a.e();
                            this.b.a.a(true);
                            this.b.a.d();
                            return;
                        case CLOSE_ACTIVITY:
                            gd.a(FlurryFullscreenTakeoverActivity.a, "CLOSE_ACTIVITY Event was fired :");
                            this.b.a.finish();
                            return;
                        default:
                            return;
                    }
                }
            });
        }
    };
    private a i = new a(this) {
        final /* synthetic */ FlurryFullscreenTakeoverActivity a;

        {
            this.a = r1;
        }

        public void a() {
            gd.a(FlurryFullscreenTakeoverActivity.a, "onViewBack");
            if (this.a.f == null || !this.a.f.b()) {
                this.a.removeViewState();
                this.a.loadViewState();
                this.a.a(true);
                this.a.d();
                return;
            }
            this.a.e();
            this.a.finish();
        }

        public void b() {
            gd.a(FlurryFullscreenTakeoverActivity.a, "onViewClose");
            this.a.e();
            this.a.finish();
        }

        public void c() {
            gd.a(FlurryFullscreenTakeoverActivity.a, "onViewError");
            this.a.e();
            this.a.finish();
        }
    };

    public void onCreate(Bundle bundle) {
        gd.a(3, a, "onCreate");
        setTheme(16973831);
        super.onCreate(bundle);
        if (fp.a() == null) {
            gd.a(3, a, "Flurry core not initialized.");
            finish();
            return;
        }
        cx.a(getWindow());
        setVolumeControlStream(3);
        this.b = new RelativeLayout(this);
        c();
        f();
        a(true);
        fireEvent(aw.INTERNAL_EV_AD_OPENED, Collections.emptyMap(), 0);
    }

    private void a(boolean z) {
        this.g = z;
    }

    private boolean b() {
        return this.g;
    }

    public void onStart() {
        super.onStart();
        gd.a(3, a, "onStart");
        hc.a().b((Context) this);
        registerActivityEvent();
        d();
        if (this.c != null) {
            this.c.onActivityStart();
        }
    }

    protected void onRestart() {
        gd.a(3, a, "onRestart");
        super.onRestart();
        loadViewState();
    }

    protected void onResume() {
        gd.a(3, a, "onActivityResume");
        super.onResume();
        if (this.c != null) {
            this.c.onActivityResume();
        }
    }

    protected void onPause() {
        gd.a(3, a, "onPause");
        super.onPause();
        if (this.c != null) {
            this.c.onActivityPause();
        }
    }

    public void onStop() {
        super.onStop();
        gd.a(3, a, "onStop");
        if (this.c != null) {
            this.c.onActivityStop();
        }
        a(false);
        unregisterActivityEvent();
        hc.a().c(this);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        gd.a(3, a, "onConfigurationChanged");
        if (this.c != null) {
            this.c.onConfigurationChanged();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        gd.a(3, a, "onDestroy");
        if (this.c != null) {
            this.c.onActivityDestroy();
        }
        if (!(this.e == null || this.e.k() == null)) {
            this.e.k().u();
            this.e.k().b(false);
        }
        fireEvent(aw.EV_AD_CLOSED, Collections.emptyMap(), 0);
    }

    public void finish() {
        synchronized (this) {
            if (this.d) {
                return;
            }
            this.d = true;
            super.finish();
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        gd.a(3, a, "onKeyUp");
        if (i != 4 || this.c == null) {
            return super.onKeyUp(i, keyEvent);
        }
        this.c.onBackKey();
        return true;
    }

    private void c() {
        boolean booleanExtra = getIntent().getBooleanExtra(EXTRA_KEY_AD_OBJECT_LEGACY, false);
        int intExtra = getIntent().getIntExtra(EXTRA_KEY_AD_OBJECT_ID, 0);
        String stringExtra = getIntent().getStringExtra("url");
        boolean booleanExtra2 = getIntent().getBooleanExtra(EXTRA_KEY_CLOSE_AD, true);
        boolean booleanExtra3 = getIntent().getBooleanExtra(EXTRA_KEY_SEND_Y_COOKIES, false);
        i a = i.a();
        this.e = booleanExtra ? a.e().a(intExtra) : a.d().a(intExtra);
        if (this.e == null) {
            gd.b(a, "Cannot launch Activity. No ad object.");
            finish();
            return;
        }
        this.f = new ek(this.e, stringExtra, booleanExtra2, booleanExtra3);
        this.e.k().b(true);
    }

    private void d() {
        if (this.f == null) {
            finish();
            return;
        }
        gd.a(3, a, "Load View in Activity: " + this.f.toString());
        ec a = ee.a(this, this.f.c(), this.f.a(), this.i, this.f.d(), b());
        if (a != null) {
            e();
            a(a);
            this.c.initLayout();
        }
        a(false);
    }

    private void a(ec ecVar) {
        e();
        this.c = ecVar;
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        this.b.addView(ecVar, layoutParams);
        setContentView(this.b);
    }

    public void registerActivityEvent() {
        fz.a().a("com.flurry.android.impl.ads.views.ActivityEvent", this.h);
    }

    public void unregisterActivityEvent() {
        fz.a().a(this.h);
    }

    private void e() {
        if (this.c != null) {
            this.c.cleanupLayout();
            this.b.removeAllViews();
        }
    }

    public void fireEvent(aw awVar, Map<String, String> map, int i) {
        gd.a(a, "fireEvent(event=" + awVar + ",params=" + map + ")");
        co.a(awVar, map, this, this.e, this.e.k(), i);
    }

    private void f() {
        if (this.f != null) {
            gd.a(a, "Save view state: " + this.f.toString());
            this.e.k().a(this.f);
        }
    }

    public void loadViewState() {
        this.f = this.e.k().t();
        if (this.f == null) {
            finish();
        } else {
            gd.a(a, "Load view state: " + this.f.toString());
        }
    }

    public void removeViewState() {
        if (this.e.k() != null) {
            gd.a(a, "Remove view state: " + this.e.k().s().toString());
        }
    }
}
