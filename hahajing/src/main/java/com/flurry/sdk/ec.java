package com.flurry.sdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ec extends RelativeLayout implements OnKeyListener {
    private static final String a = ec.class.getSimpleName();
    private final r b;
    private final a c;
    private ProgressDialog d;
    private AtomicBoolean e = new AtomicBoolean(false);
    private long f = Long.MIN_VALUE;
    private final fy<hj> g = new fy<hj>(this) {
        final /* synthetic */ ec a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((hj) fxVar);
        }

        public void a(hj hjVar) {
            if (System.currentTimeMillis() - this.a.f > 8000) {
                fp.a().a(new hq(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        gd.a(3, ec.a, "Failed to load view in 8 seconds.");
                        this.a.a.dismissProgressDialog();
                        this.a.a.removeTimerListener();
                        this.a.a.onViewLoadTimeout();
                    }
                });
            }
        }
    };

    public interface a {
        void a();

        void b();

        void c();
    }

    public ec(Context context, r rVar, a aVar) {
        super(context);
        this.b = rVar;
        this.c = aVar;
    }

    public void initLayout() {
    }

    public void cleanupLayout() {
        removeTimerListener();
    }

    protected void onViewBack() {
        if (this.c != null) {
            this.c.a();
        }
    }

    protected void onViewClose() {
        if (this.c != null) {
            this.c.b();
        }
    }

    protected void onViewError() {
        if (this.c != null) {
            this.c.c();
        }
    }

    protected void onViewLoadTimeout() {
    }

    public void onActivityStart() {
    }

    public void onActivityResume() {
    }

    public void onActivityPause() {
    }

    public void onActivityStop() {
        dismissProgressDialog();
    }

    public void onActivityDestroy() {
    }

    public void onConfigurationChanged() {
    }

    public boolean onBackKey() {
        return false;
    }

    public r getAdObject() {
        return this.b;
    }

    public ap getAdController() {
        return this.b.k();
    }

    public AdUnit getAdUnit() {
        return this.b.k().a();
    }

    public at getAdLog() {
        return this.b.k().b();
    }

    public at getAdLog(String str) {
        return this.b.k().a(str);
    }

    public int getAdFrameIndex() {
        return this.b.k().c();
    }

    public void setAdFrameIndex(int i) {
        this.b.k().a(i);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.e.set(true);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.e.set(false);
    }

    protected boolean isViewAttachedToActivity() {
        gd.a(3, a, "fViewAttachedToWindow " + this.e.get());
        return this.e.get();
    }

    protected void showProgressDialog() {
        if (getAdController().l()) {
            Context context = getContext();
            if (this.d == null) {
                if (context != null) {
                    gd.a(3, a, "Create and show progress bar");
                    this.d = new ProgressDialog(context);
                    this.d.setProgressStyle(0);
                    this.d.setMessage("Loading...");
                    this.d.setCancelable(true);
                    this.d.setCanceledOnTouchOutside(false);
                    this.d.setOnKeyListener(this);
                    this.d.show();
                    addTimerListener();
                    return;
                }
                gd.a(3, a, "Context is null, cannot create progress dialog.");
            } else if (!this.d.isShowing()) {
                gd.a(3, a, "Show progress bar.");
                this.d.show();
                addTimerListener();
            }
        }
    }

    protected void dismissProgressDialog() {
        if (this.d != null && this.d.isShowing()) {
            this.d.dismiss();
            this.d = null;
        }
        gd.a(3, a, "Dismiss progress bar.");
        this.f = Long.MIN_VALUE;
        removeTimerListener();
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        gd.a(3, a, "onkey,keycode=" + i + ",event=" + keyEvent.getAction());
        if (dialogInterface != this.d || i != 4 || keyEvent.getAction() != 1) {
            return false;
        }
        onEvent(aw.EV_AD_WILL_CLOSE, Collections.emptyMap());
        dialogInterface.dismiss();
        return true;
    }

    public void onEvent(aw awVar, Map<String, String> map) {
        co.a(awVar, map, getContext(), this.b, this.b.k(), 0);
    }

    protected void setOrientation(int i) {
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            if (getAdController().l()) {
                cn.a(activity, i, true);
            }
        }
    }

    public void addTimerListener() {
        this.f = System.currentTimeMillis();
        hk.a().a(this.g);
    }

    public void removeTimerListener() {
        this.f = Long.MIN_VALUE;
        hk.a().b(this.g);
    }
}
