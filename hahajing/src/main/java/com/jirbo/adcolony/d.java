package com.jirbo.adcolony;

import android.content.Intent;
import com.inmobi.monetization.internal.Ad;
import java.util.ArrayList;

class d {
    c a = new c(this);
    b b = new b(this);
    o c = new o(this);
    u d = new u(this);
    v e = new v(this);
    ADCStorage f = new ADCStorage(this);
    ah g = new ah(this);
    t h = new t(this);
    ArrayList<j> i = new ArrayList();
    ArrayList<j> j = new ArrayList();
    volatile boolean k;
    boolean l;
    boolean m;
    a n = new a();

    d() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void a(com.jirbo.adcolony.j r3) {
        /*
        r2 = this;
        r1 = r2.i;
        monitor-enter(r1);
        r0 = com.jirbo.adcolony.a.d();	 Catch:{ all -> 0x0019 }
        if (r0 != 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r1);	 Catch:{ all -> 0x0019 }
    L_0x000a:
        return;
    L_0x000b:
        r0 = r2.i;	 Catch:{ all -> 0x0019 }
        r0.add(r3);	 Catch:{ all -> 0x0019 }
        r0 = r2.k;	 Catch:{ all -> 0x0019 }
        if (r0 != 0) goto L_0x0017;
    L_0x0014:
        r2.g();	 Catch:{ all -> 0x0019 }
    L_0x0017:
        monitor-exit(r1);	 Catch:{ all -> 0x0019 }
        goto L_0x000a;
    L_0x0019:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0019 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jirbo.adcolony.d.a(com.jirbo.adcolony.j):void");
    }

    void a() {
        if (!this.l && a.d()) {
            while (true) {
                try {
                    if (!this.l || (!this.k && this.i.size() > 0)) {
                        this.l = true;
                        this.j.addAll(this.i);
                        this.i.clear();
                        for (int i = 0; i < this.j.size(); i++) {
                            if (this.j.get(i) != null) {
                                ((j) this.j.get(i)).a();
                            }
                        }
                        this.j.clear();
                    }
                } catch (RuntimeException e) {
                    this.l = false;
                    this.j.clear();
                    this.i.clear();
                    a.a(e);
                    return;
                }
            }
            this.l = false;
        }
    }

    void b() {
        this.k = true;
        AnonymousClass1 anonymousClass1 = new j(this, this) {
            final /* synthetic */ d a;

            void a() {
                this.o.e.c();
            }
        };
    }

    void c() {
        this.k = false;
        AnonymousClass2 anonymousClass2 = new j(this, this) {
            final /* synthetic */ d a;

            void a() {
                this.o.e.d();
            }
        };
    }

    void d() {
        AnonymousClass3 anonymousClass3 = new j(this, this) {
            final /* synthetic */ d a;

            void a() {
                this.o.e.e();
            }
        };
    }

    synchronized void a(final AdColonyAd adColonyAd) {
        this.a.n = 0.0d;
        l.a.b((Object) "Tracking ad event - start");
        ag agVar = adColonyAd.h.k;
        agVar.d++;
        if (!adColonyAd.b()) {
            adColonyAd.h.k();
            this.h.a(adColonyAd.g, adColonyAd.i.d);
        }
        AnonymousClass4 anonymousClass4 = new j(this, this) {
            final /* synthetic */ d b;

            void a() {
                if (AdColony.isZoneV4VC(adColonyAd.g) || !adColonyAd.k.equals(Ad.AD_TYPE_NATIVE)) {
                    this.b.a("start", "{\"ad_slot\":" + adColonyAd.h.k.d + ", \"replay\":" + adColonyAd.s + "}", adColonyAd);
                }
            }
        };
    }

    void a(double d, AdColonyAd adColonyAd) {
        final double d2 = d;
        final AdColonyAd adColonyAd2 = adColonyAd;
        AnonymousClass5 anonymousClass5 = new j(this, this) {
            final /* synthetic */ d c;

            void a() {
                this.o.d.a(d2, adColonyAd2);
            }
        };
    }

    synchronized void a(boolean z, String str, int i) {
        a.O.a(z, str, i);
    }

    synchronized void a(boolean z, AdColonyAd adColonyAd) {
        int i = 0;
        int i2 = 1;
        synchronized (this) {
            if (adColonyAd != null) {
                a(1.0d, adColonyAd);
                if (!z && adColonyAd.b()) {
                    adColonyAd.h.k();
                    this.h.a(adColonyAd.g, adColonyAd.i.d);
                    AdColonyV4VCAd adColonyV4VCAd = (AdColonyV4VCAd) a.K;
                    final String rewardName = adColonyV4VCAd.getRewardName();
                    final int rewardAmount = adColonyV4VCAd.getRewardAmount();
                    int viewsPerReward = adColonyV4VCAd.getViewsPerReward();
                    if (viewsPerReward > 1) {
                        int c = this.h.c(adColonyV4VCAd.getRewardName()) + 1;
                        if (c < viewsPerReward) {
                            i2 = 0;
                            i = c;
                        }
                        this.h.b(adColonyV4VCAd.getRewardName(), i);
                    }
                    if (i2 != 0) {
                        if (adColonyV4VCAd.h.j.e) {
                            a(true, rewardName, rewardAmount);
                        }
                        final AdColonyAd adColonyAd2 = adColonyAd;
                        AnonymousClass6 anonymousClass6 = new j(this, this) {
                            final /* synthetic */ d d;

                            void a() {
                                g gVar = new g();
                                gVar.b("v4vc_name", rewardName);
                                gVar.b("v4vc_amount", rewardAmount);
                                this.o.d.a("reward_v4vc", gVar, adColonyAd2);
                            }
                        };
                    }
                }
            }
        }
    }

    void a(final String str, final String str2) {
        AnonymousClass7 anonymousClass7 = new j(this, this) {
            final /* synthetic */ d c;

            void a() {
                this.o.d.a(str, k.b(str2));
            }
        };
    }

    void a(String str, String str2, AdColonyAd adColonyAd) {
        final String str3 = str;
        final String str4 = str2;
        final AdColonyAd adColonyAd2 = adColonyAd;
        AnonymousClass8 anonymousClass8 = new j(this, this) {
            final /* synthetic */ d d;

            void a() {
                this.o.d.a(str3, k.b(str4), adColonyAd2);
            }
        };
    }

    synchronized double a(String str) {
        double f;
        try {
            f = this.a.i.f(str);
        } catch (RuntimeException e) {
            a.a(e);
            f = 0.0d;
        }
        return f;
    }

    synchronized int b(String str) {
        int g;
        try {
            g = this.a.i.g(str);
        } catch (RuntimeException e) {
            a.a(e);
            g = 0;
        }
        return g;
    }

    synchronized boolean c(String str) {
        boolean h;
        try {
            h = this.a.i.h(str);
        } catch (RuntimeException e) {
            a.a(e);
            h = false;
        }
        return h;
    }

    synchronized String d(String str) {
        String e;
        try {
            e = this.a.i.e(str);
        } catch (RuntimeException e2) {
            a.a(e2);
            e = null;
        }
        return e;
    }

    synchronized String e() {
        return this.b.c();
    }

    synchronized String f() {
        return this.b.d();
    }

    synchronized int e(String str) {
        return this.h.c(str);
    }

    synchronized void a(String str, int i) {
        this.h.b(str, i);
    }

    synchronized boolean f(String str) {
        return a(str, false, true);
    }

    synchronized boolean a(String str, boolean z, boolean z2) {
        boolean z3 = false;
        synchronized (this) {
            try {
                if (a.d()) {
                    if (this.b.b(str, z)) {
                        z3 = this.b.j.n.a(str).b(z2);
                    }
                }
            } catch (RuntimeException e) {
                a.a(e);
            }
        }
        return z3;
    }

    synchronized boolean g(String str) {
        return b(str, false, true);
    }

    synchronized boolean b(String str, boolean z, boolean z2) {
        boolean z3 = false;
        synchronized (this) {
            try {
                if (a.d()) {
                    if (this.b.b(str, z)) {
                        z3 = this.b.j.n.a(str).c(z2);
                    }
                }
            } catch (RuntimeException e) {
                a.a(e);
            }
        }
        return z3;
    }

    synchronized void a(AdColonyVideoAd adColonyVideoAd) {
        this.a.b(adColonyVideoAd.g);
    }

    synchronized void a(AdColonyInterstitialAd adColonyInterstitialAd) {
        this.a.b(adColonyInterstitialAd.g);
    }

    synchronized boolean b(AdColonyVideoAd adColonyVideoAd) {
        boolean z = false;
        synchronized (this) {
            try {
                a.K = adColonyVideoAd;
                Object obj = adColonyVideoAd.g;
                if (f(obj)) {
                    l.a.a("Showing ad for zone ").b(obj);
                    a(adColonyVideoAd);
                    z = b((AdColonyAd) adColonyVideoAd);
                }
            } catch (RuntimeException e) {
                a.a(e);
            }
        }
        return z;
    }

    synchronized boolean b(AdColonyInterstitialAd adColonyInterstitialAd) {
        boolean z = false;
        synchronized (this) {
            try {
                a.K = adColonyInterstitialAd;
                Object obj = adColonyInterstitialAd.g;
                if (f(obj)) {
                    l.a.a("Showing ad for zone ").b(obj);
                    a(adColonyInterstitialAd);
                    z = b((AdColonyAd) adColonyInterstitialAd);
                }
            } catch (RuntimeException e) {
                a.a(e);
            }
        }
        return z;
    }

    synchronized ab h(String str) {
        return this.b.j.n.a(str);
    }

    synchronized void a(AdColonyV4VCAd adColonyV4VCAd) {
        this.a.c(adColonyV4VCAd.g);
    }

    synchronized boolean b(AdColonyV4VCAd adColonyV4VCAd) {
        boolean z = false;
        synchronized (this) {
            try {
                a.K = adColonyV4VCAd;
                Object obj = adColonyV4VCAd.g;
                if (g(obj)) {
                    l.a.a("Showing v4vc for zone ").b(obj);
                    a(adColonyV4VCAd);
                    z = b((AdColonyAd) adColonyV4VCAd);
                }
            } catch (RuntimeException e) {
                a.a(e);
            }
        }
        return z;
    }

    synchronized boolean b(AdColonyAd adColonyAd) {
        boolean z;
        if (this.a.l.b()) {
            a.K.f = 3;
            z = false;
        } else {
            a(adColonyAd);
            ADCVideo.a();
            if (a.m) {
                l.a.b((Object) "Launching AdColonyOverlay");
                a.b().startActivity(new Intent(a.b(), AdColonyOverlay.class));
            } else {
                l.a.b((Object) "Launching AdColonyFullscreen");
                a.b().startActivity(new Intent(a.b(), AdColonyFullscreen.class));
            }
            z = true;
        }
        return z;
    }

    synchronized void a(String str, String str2, String[] strArr) {
        try {
            a(a.n);
            l.c.a("==== Configuring AdColony ").a(this.a.b).b((Object) " ====");
            l.a.a("package name: ").b(ab.d());
            this.a.j = str2;
            this.a.k = strArr;
            this.a.a(str);
            this.n.a();
        } catch (RuntimeException e) {
            a.a(e);
        }
    }

    synchronized void g() {
        if (!a.c()) {
            try {
                a();
                if (!a.p) {
                    if (g.n() != null || this.n.b() > 5.0d) {
                        this.a.a();
                        a.p = true;
                    }
                    a.r = true;
                }
                this.b.f();
                this.c.e();
                this.e.b();
                this.d.d();
                this.h.d();
                this.g.d();
            } catch (RuntimeException e) {
                a.a(e);
            }
        }
    }

    void a(int i) {
        a.a(i);
    }
}
