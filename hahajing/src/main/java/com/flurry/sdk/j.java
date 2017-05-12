package com.flurry.sdk;

import com.flurry.android.FlurryAdListener;
import com.flurry.android.ICustomAdNetworkHandler;
import java.lang.ref.WeakReference;
import java.util.Map;

public class j {
    private static j a;
    private static final String b = j.class.getSimpleName();
    private WeakReference<FlurryAdListener> c = new WeakReference(null);
    private ICustomAdNetworkHandler d = null;
    private final e e = new e();
    private volatile String f = null;
    private volatile String g = null;
    private volatile boolean h = false;

    public static synchronized j a() {
        j jVar;
        synchronized (j.class) {
            if (a == null) {
                a = new j();
            }
            jVar = a;
        }
        return jVar;
    }

    private j() {
    }

    public void a(FlurryAdListener flurryAdListener) {
        this.c = new WeakReference(flurryAdListener);
    }

    public FlurryAdListener b() {
        return (FlurryAdListener) this.c.get();
    }

    public void a(ICustomAdNetworkHandler iCustomAdNetworkHandler) {
        this.d = iCustomAdNetworkHandler;
    }

    public ICustomAdNetworkHandler c() {
        return this.d;
    }

    public e d() {
        return this.e;
    }

    public void a(Map<String, String> map) {
        this.e.setUserCookies(map);
    }

    public void e() {
        this.e.clearUserCookies();
    }

    public void b(Map<String, String> map) {
        this.e.setKeywords(map);
    }

    public void f() {
        this.e.clearUserCookies();
    }

    public void a(boolean z) {
        this.e.setEnableTestAds(z);
    }

    public void a(String str) {
        this.e.setFixedAdId(str);
    }

    public void b(String str) {
        this.f = str;
    }

    public void c(String str) {
        this.g = str;
    }

    public String g() {
        if (this.f != null) {
            return this.f + "/v12/getAds.do";
        }
        if (j()) {
            return "https://ads.flurry.com/v12/getAds.do";
        }
        return "http://ads.flurry.com/v12/getAds.do";
    }

    public String h() {
        if (this.g != null) {
            return this.g;
        }
        if (j()) {
            return "https://adlog.flurry.com";
        }
        return "http://adlog.flurry.com";
    }

    public String i() {
        return h() + "/v2/postAdLog.do";
    }

    private boolean j() {
        return ((Boolean) hg.a().a("UseHttps")).booleanValue();
    }
}
