package com.flurry.sdk;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.flurry.sdk.ec.a;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class dq extends ec implements dx {
    private static final String b = dq.class.getSimpleName();
    protected du a;
    private boolean c = true;
    private boolean d = false;

    protected abstract int getViewParams();

    protected dq(Context context, r rVar, a aVar) {
        super(context, rVar, aVar);
        this.a = new du(context);
        this.a.a((dx) this);
        k();
        showProgressDialog();
    }

    protected void onViewLoadTimeout() {
        a(aw.EV_AD_WILL_CLOSE, Collections.emptyMap());
    }

    public void setAutoPlay(boolean z) {
        gd.a(3, b, "Video setAutoPlay: " + z);
        this.c = z;
    }

    public void a(int i) {
        if (this.a != null) {
            if (this.a.a()) {
                dismissProgressDialog();
                this.a.a(i);
            } else {
                showProgressDialog();
            }
            this.a.b(getViewParams());
            this.d = false;
        }
    }

    public void c() {
        if (this.a != null) {
            gd.a(3, b, "Video pause: ");
            i();
            j();
            this.a.b();
            this.d = true;
        }
    }

    public void d() {
        if (this.a != null) {
            gd.a(3, b, "Video suspend: ");
            c();
            this.a.d();
        }
    }

    public void setVideoUri(Uri uri) {
        gd.a(3, b, "Video set video uri: " + uri);
        if (this.a != null) {
            dt m = getAdController().m();
            this.a.a(uri, m.a() > this.a.f() ? m.a() : this.a.f());
        }
    }

    public void b(String str) {
        gd.a(3, b, "Video Prepared: " + str);
        if (this.a != null) {
            this.a.b(getViewParams());
        }
        if (this.d) {
            dismissProgressDialog();
            return;
        }
        dt m = getAdController().m();
        int a = m.a();
        if (this.a != null && (this.c || a > 3)) {
            a(a);
        }
        a(aw.EV_RENDERED, Collections.emptyMap());
        m.h(true);
        dismissProgressDialog();
    }

    public void a(String str, float f, float f2) {
        a(f, f2);
        if (this.a != null) {
            this.a.b(getViewParams());
        }
    }

    public void a(String str, int i, int i2, int i3) {
        gd.a(3, b, "Video Error: " + str);
        if (this.a != null) {
            this.a.d();
        }
        onViewError();
        Map hashMap = new HashMap();
        hashMap.put("errorCode", Integer.toString(av.kVideoPlaybackError.a()));
        hashMap.put("frameworkError", Integer.toString(i2));
        hashMap.put("implError", Integer.toString(i3));
        a(aw.EV_RENDER_FAILED, hashMap);
        dismissProgressDialog();
        l();
    }

    public void a(String str) {
        gd.a(3, b, "Video Completed: " + str);
        boolean g = g();
        a(aw.EV_VIDEO_COMPLETED, Collections.emptyMap());
        l();
        if (g) {
            h();
        }
    }

    private boolean g() {
        return getAdFrameIndex() == getAdUnit().adFrames.size() + -1;
    }

    private void h() {
        ea eaVar = new ea();
        eaVar.e = ea.a.CLOSE_ACTIVITY;
        eaVar.b();
    }

    public void a() {
        gd.a(3, b, "Video Close clicked: ");
        a(aw.EV_AD_WILL_CLOSE, Collections.emptyMap());
        onViewClose();
    }

    public void e() {
        gd.a(3, b, "Video More Info clicked: ");
        a(aw.EV_CLICKED, Collections.emptyMap());
    }

    public void b() {
        gd.a(3, b, "Video Play clicked: ");
        a(0);
    }

    protected void a(aw awVar, Map<String, String> map) {
        co.a(awVar, map, getContext(), getAdObject(), getAdController(), 0);
    }

    protected Uri c(String str) {
        Uri uri = null;
        try {
            gd.a(3, b, "Precaching: Getting video from cache: " + str);
            File a = i.a().l().a(getAdObject(), str);
            if (a != null) {
                uri = Uri.parse("file://" + a.getAbsolutePath());
            }
        } catch (Throwable e) {
            gd.a(3, b, "Precaching: Error accessing cached file.", e);
        }
        if (uri != null) {
            return uri;
        }
        gd.a(3, b, "Precaching: Error using cached file. Loading with url: " + str);
        return Uri.parse(str);
    }

    public void initLayout() {
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        addView(this.a.e(), layoutParams);
        showProgressDialog();
    }

    public void cleanupLayout() {
        d();
        dismissProgressDialog();
        if (this.a != null) {
            this.a.j();
            this.a = null;
        }
    }

    private void a(float f, float f2) {
        if (this.a != null) {
            dt m = getAdController().m();
            if (f2 >= 0.0f && !m.b()) {
                m.a(true);
                a(aw.EV_VIDEO_START, Collections.emptyMap());
            }
            float f3 = f2 / f;
            if (f3 >= 0.25f && !m.d()) {
                m.c(true);
                a(aw.EV_VIDEO_FIRST_QUARTILE, Collections.emptyMap());
            }
            if (f3 >= 0.5f && !m.e()) {
                m.d(true);
                a(aw.EV_VIDEO_MIDPOINT, Collections.emptyMap());
            }
            if (f3 >= 0.75f && !m.f()) {
                m.e(true);
                a(aw.EV_VIDEO_THIRD_QUARTILE, Collections.emptyMap());
            }
        }
    }

    private void i() {
        dt m = getAdController().m();
        int c = this.a.c();
        if (c > 0) {
            m.a(c);
            getAdController().a(m);
        }
    }

    private void j() {
        getAdController().m().b(getViewParams());
    }

    private void k() {
        if (cn.a(getContext())) {
            setOrientation(4);
        } else {
            setOrientation(6);
        }
    }

    private void l() {
        setOrientation(4);
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        k();
    }

    public void onActivityResume() {
        super.onActivityResume();
        int a = getAdController().m().a();
        if (this.a == null) {
            return;
        }
        if (this.c || a > 3) {
            a(a);
        }
    }

    public void onActivityPause() {
        super.onActivityPause();
        c();
    }

    public void onActivityStop() {
        super.onActivityStop();
        d();
    }

    public void onActivityDestroy() {
        super.onActivityDestroy();
        i.a().l().a(getAdObject());
        i.a().l().g();
    }
}
