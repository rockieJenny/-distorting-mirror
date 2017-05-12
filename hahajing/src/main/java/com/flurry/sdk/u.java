package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.flurry.android.FlurryAdListener;
import java.lang.ref.WeakReference;

public class u implements r, s {
    private static final String a = u.class.getSimpleName();
    private final int b;
    private final WeakReference<Context> c;
    private final WeakReference<ViewGroup> d;
    private final String e;
    private a f;
    private final ch g;
    private ap h;
    private ap i;
    private long j;
    private final fy<hj> k = new fy<hj>(this) {
        final /* synthetic */ u a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((hj) fxVar);
        }

        public void a(hj hjVar) {
            this.a.y();
            this.a.j = System.currentTimeMillis();
        }
    };
    private final fy<d> l = new fy<d>(this) {
        final /* synthetic */ u a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((d) fxVar);
        }

        public void a(final d dVar) {
            if (dVar.a == this.a && dVar.b != null) {
                this.a.a(dVar);
                final FlurryAdListener b = j.a().b();
                if (b != null) {
                    final String g = dVar.a.g();
                    fp.a().a(new hq(this) {
                        final /* synthetic */ AnonymousClass2 d;

                        public void safeRun() {
                            switch (dVar.b) {
                                case kOnFetched:
                                    b.spaceDidReceiveAd(g);
                                    return;
                                case kOnFetchFailed:
                                    b.spaceDidFailToReceiveAd(g);
                                    return;
                                case kOnRendered:
                                    b.onRendered(g);
                                    return;
                                case kOnRenderFailed:
                                    b.onRenderFailed(g);
                                    return;
                                case kOnAppExit:
                                    b.onApplicationExit(g);
                                    return;
                                case kOnClicked:
                                    b.onAdClicked(g);
                                    return;
                                case kOnVideoCompleted:
                                    b.onVideoCompleted(g);
                                    return;
                                case kOnOpen:
                                    b.onAdOpened(g);
                                    return;
                                case kOnClose:
                                    b.onAdClosed(g);
                                    return;
                                default:
                                    return;
                            }
                        }
                    });
                }
            }
        }
    };
    private WeakReference<RelativeLayout> m;
    private boolean n;
    private long o;
    private long p;

    enum a {
        INIT,
        READY,
        DISPLAY,
        NEXT
    }

    public u(Context context, ViewGroup viewGroup, String str) {
        i a = i.a();
        if (a == null) {
            throw new IllegalStateException("A session must be started before ad objects may be instantiated.");
        }
        this.b = cr.a();
        this.c = new WeakReference(context);
        this.d = new WeakReference(viewGroup);
        this.e = str;
        this.f = a.INIT;
        this.g = new ch(str);
        this.m = new WeakReference(null);
        a.e().a(context, str, this);
        u();
        w();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        a();
    }

    public int d() {
        return this.b;
    }

    public void a() {
        v();
        x();
        i.a().e().a(g(), this);
        aa l = i.a().l();
        if (l != null) {
            l.a((r) this);
        }
        fp.a().a(new hq(this) {
            final /* synthetic */ u a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.n();
            }
        });
        if (this.g != null) {
            this.g.a();
        }
    }

    public void b() {
        v();
    }

    public void c() {
        u();
        this.p = this.o;
    }

    public Context e() {
        return (Context) this.c.get();
    }

    public ViewGroup f() {
        return (ViewGroup) this.d.get();
    }

    public String g() {
        return this.e;
    }

    public ch h() {
        return this.g;
    }

    public ci i() {
        return i.a().c().a(g(), cr.b(), l()).a();
    }

    public x j() {
        return i.a().c().a(g(), cr.b(), l()).b();
    }

    public ap k() {
        return this.i;
    }

    public e l() {
        return j.a().d();
    }

    public void a(ap apVar) {
        this.h = apVar;
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            h().b();
            j().a(str);
        }
    }

    public void a(ap apVar, long j) {
        if (apVar.d().equals(ax.BANNER)) {
            Object obj = (s() == null || s().getChildCount() <= 0) ? null : 1;
            if (obj != null) {
                a(j);
                return;
            } else {
                h().a((r) this, i(), j());
                return;
            }
        }
        h().a((r) this, i(), j());
    }

    public void m() {
        this.g.c();
    }

    public RelativeLayout s() {
        return (RelativeLayout) this.m.get();
    }

    public void a(RelativeLayout relativeLayout) {
        this.m = new WeakReference(relativeLayout);
    }

    public void n() {
        RelativeLayout relativeLayout = (RelativeLayout) this.m.get();
        if (relativeLayout != null) {
            while (relativeLayout.getChildCount() > 0) {
                View childAt = relativeLayout.getChildAt(0);
                relativeLayout.removeView(childAt);
                if (childAt instanceof ec) {
                    ((ec) childAt).onActivityDestroy();
                }
            }
            ViewGroup f = f();
            if (f != null) {
                f.removeView(relativeLayout);
                f.setBackgroundColor(0);
            }
        }
        this.m.clear();
    }

    public boolean o() {
        boolean equals;
        synchronized (this) {
            equals = a.READY.equals(this.f);
        }
        return equals;
    }

    public void p() {
        this.n = false;
        synchronized (this) {
            if (a.INIT.equals(this.f)) {
                h().a((r) this, i(), j());
            } else if (a.READY.equals(this.f)) {
                cq.a(this);
            } else if (a.DISPLAY.equals(this.f) || a.NEXT.equals(this.f)) {
                this.f = a.INIT;
                h().a((r) this, i(), j());
            }
        }
    }

    public void q() {
        synchronized (this) {
            if (a.INIT.equals(this.f) || a.NEXT.equals(this.f)) {
                cq.b(this, av.kNotReady);
            } else if (a.READY.equals(this.f)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ u a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.A();
                    }
                });
            } else if (a.DISPLAY.equals(this.f)) {
                cq.b(this);
            }
        }
    }

    public boolean r() {
        this.n = true;
        boolean z = false;
        synchronized (this) {
            if (a.INIT.equals(this.f)) {
                h().a((r) this, i(), j());
            } else if (a.READY.equals(this.f)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ u a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.A();
                    }
                });
                z = true;
            } else if (a.DISPLAY.equals(this.f) || a.NEXT.equals(this.f)) {
                h().a((r) this, i(), j());
            }
        }
        return z;
    }

    private void u() {
        this.j = System.currentTimeMillis();
        hk.a().a(this.k);
    }

    private void v() {
        hk.a().b(this.k);
    }

    private void w() {
        fz.a().a("com.flurry.android.impl.ads.AdStateEvent", this.l);
    }

    private void x() {
        fz.a().a(this.l);
    }

    private void y() {
        if (this.o > 0) {
            this.p -= System.currentTimeMillis() - this.j;
            if (this.p <= 0) {
                if (C()) {
                    gd.a(3, a, "Rotating banner for adSpace: " + g());
                    h().a((r) this, i(), j());
                }
                this.p = this.o;
            }
        }
    }

    private void a(d dVar) {
        if (com.flurry.sdk.d.a.kOnFetched.equals(dVar.b) || com.flurry.sdk.d.a.kOnFetchFailed.equals(dVar.b)) {
            int b = j().b();
            if (b == 0) {
                gd.a(3, a, "Starting ad request from EnsureCacheNotEmpty size: " + b);
                i().a((r) this, j(), null);
            }
        }
        if (com.flurry.sdk.d.a.kOnFetched.equals(dVar.b)) {
            synchronized (this) {
                if (a.INIT.equals(this.f)) {
                    this.f = a.READY;
                } else if (a.DISPLAY.equals(this.f)) {
                    this.f = a.NEXT;
                }
            }
            if (this.n || a.NEXT.equals(this.f)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ u a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.A();
                    }
                });
            }
        }
    }

    protected void t() {
        hp.b();
        if (!this.h.o() && this.h.n()) {
            gd.a(3, a, "Precaching optional for ad, copying assets before display");
            i.a().l().a((r) this, this.h);
        }
    }

    private void z() {
        this.i = this.h;
        this.h = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void A() {
        /*
        r6 = this;
        r5 = 3;
        com.flurry.sdk.hp.b();
        monitor-enter(r6);
        r0 = com.flurry.sdk.u.a.READY;	 Catch:{ all -> 0x0067 }
        r1 = r6.f;	 Catch:{ all -> 0x0067 }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x0067 }
        if (r0 != 0) goto L_0x001b;
    L_0x000f:
        r0 = com.flurry.sdk.u.a.NEXT;	 Catch:{ all -> 0x0067 }
        r1 = r6.f;	 Catch:{ all -> 0x0067 }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x0067 }
        if (r0 != 0) goto L_0x001b;
    L_0x0019:
        monitor-exit(r6);	 Catch:{ all -> 0x0067 }
    L_0x001a:
        return;
    L_0x001b:
        r0 = com.flurry.sdk.u.a.READY;	 Catch:{ all -> 0x0067 }
        r1 = r6.f;	 Catch:{ all -> 0x0067 }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x0067 }
        if (r0 == 0) goto L_0x0078;
    L_0x0025:
        r0 = r6.h;	 Catch:{ all -> 0x0067 }
        if (r0 == 0) goto L_0x0078;
    L_0x0029:
        r0 = com.flurry.sdk.j.a();	 Catch:{ all -> 0x0067 }
        r2 = r0.b();	 Catch:{ all -> 0x0067 }
        r0 = 3;
        r1 = a;	 Catch:{ all -> 0x0067 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0067 }
        r3.<init>();	 Catch:{ all -> 0x0067 }
        r4 = "Firing shouldDisplayAd, listener=";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0067 }
        r3 = r3.append(r2);	 Catch:{ all -> 0x0067 }
        r3 = r3.toString();	 Catch:{ all -> 0x0067 }
        com.flurry.sdk.gd.a(r0, r1, r3);	 Catch:{ all -> 0x0067 }
        if (r2 == 0) goto L_0x0078;
    L_0x004c:
        r1 = 0;
        r3 = r6.e;	 Catch:{ Throwable -> 0x006d }
        r0 = com.flurry.sdk.ax.BANNER;	 Catch:{ Throwable -> 0x006d }
        r4 = r6.h;	 Catch:{ Throwable -> 0x006d }
        r4 = r4.d();	 Catch:{ Throwable -> 0x006d }
        r0 = r0.equals(r4);	 Catch:{ Throwable -> 0x006d }
        if (r0 == 0) goto L_0x006a;
    L_0x005d:
        r0 = com.flurry.android.FlurryAdType.WEB_BANNER;	 Catch:{ Throwable -> 0x006d }
    L_0x005f:
        r0 = r2.shouldDisplayAd(r3, r0);	 Catch:{ Throwable -> 0x006d }
    L_0x0063:
        if (r0 != 0) goto L_0x0078;
    L_0x0065:
        monitor-exit(r6);	 Catch:{ all -> 0x0067 }
        goto L_0x001a;
    L_0x0067:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0067 }
        throw r0;
    L_0x006a:
        r0 = com.flurry.android.FlurryAdType.WEB_TAKEOVER;	 Catch:{ Throwable -> 0x006d }
        goto L_0x005f;
    L_0x006d:
        r0 = move-exception;
        r2 = 6;
        r3 = a;	 Catch:{ all -> 0x0067 }
        r4 = "AdListener.shouldDisplayAd";
        com.flurry.sdk.gd.a(r2, r3, r4, r0);	 Catch:{ all -> 0x0067 }
        r0 = r1;
        goto L_0x0063;
    L_0x0078:
        r0 = com.flurry.sdk.u.a.DISPLAY;	 Catch:{ all -> 0x0067 }
        r6.f = r0;	 Catch:{ all -> 0x0067 }
        monitor-exit(r6);	 Catch:{ all -> 0x0067 }
        r0 = a;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "render banner (";
        r1 = r1.append(r2);
        r1 = r1.append(r6);
        r2 = ")";
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.flurry.sdk.gd.a(r5, r0, r1);
        r0 = r6.e();
        r1 = r6.f();
        r2 = com.flurry.sdk.fj.a();
        r2 = r2.c();
        if (r2 != 0) goto L_0x00bc;
    L_0x00ad:
        r0 = 5;
        r1 = a;
        r2 = "There is no network connectivity (ad will not display)";
        com.flurry.sdk.gd.a(r0, r1, r2);
        r0 = com.flurry.sdk.av.kNoNetworkConnectivity;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x00bc:
        if (r0 != 0) goto L_0x00c5;
    L_0x00be:
        r0 = com.flurry.sdk.av.kNoContext;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x00c5:
        if (r1 != 0) goto L_0x00ce;
    L_0x00c7:
        r0 = com.flurry.sdk.av.kNoViewGroup;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x00ce:
        r0 = r6.h;
        if (r0 != 0) goto L_0x00d9;
    L_0x00d2:
        r0 = com.flurry.sdk.av.kMissingAdController;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x00d9:
        r1 = r0.a();
        if (r1 != 0) goto L_0x00e6;
    L_0x00df:
        r0 = com.flurry.sdk.av.kInvalidAdUnit;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x00e6:
        r2 = r1.combinable;
        r3 = 1;
        if (r2 != r3) goto L_0x00f2;
    L_0x00eb:
        r0 = com.flurry.sdk.av.kInvalidAdUnit;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x00f2:
        r2 = com.flurry.android.impl.ads.protocol.v13.AdViewType.LEGACY;
        r3 = r1.adViewType;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0103;
    L_0x00fc:
        r0 = com.flurry.sdk.av.kIncorrectClassForAdSpace;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x0103:
        r2 = com.flurry.sdk.ax.BANNER;
        r3 = r0.d();
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0122;
    L_0x010f:
        r2 = com.flurry.sdk.ax.TAKEOVER;
        r0 = r0.d();
        r0 = r2.equals(r0);
        if (r0 != 0) goto L_0x0122;
    L_0x011b:
        r0 = com.flurry.sdk.av.kIncorrectClassForAdSpace;
        com.flurry.sdk.cq.a(r6, r0);
        goto L_0x001a;
    L_0x0122:
        r0 = com.flurry.sdk.cr.b();
        r1 = r1.screenOrientation;
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x0135;
    L_0x012e:
        r0 = com.flurry.sdk.av.kWrongOrientation;
        com.flurry.sdk.cq.b(r6, r0);
        goto L_0x001a;
    L_0x0135:
        r6.t();
        r0 = com.flurry.sdk.fp.a();
        r1 = new com.flurry.sdk.u$7;
        r1.<init>(r6);
        r0.a(r1);
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.u.A():void");
    }

    private void B() {
        hp.a();
        a(0);
        z();
        if (ax.BANNER.equals(this.i.d())) {
            eg.a(e(), (s) this);
        } else {
            en a = i.a().q().a(e(), this);
            if (a != null) {
                a.a();
            }
        }
        cq.b(this);
    }

    private boolean C() {
        if (hn.a()) {
            gd.a(3, a, "Device is locked: banner will NOT rotate for adSpace: " + g());
            return false;
        } else if (this.m.get() != null) {
            return true;
        } else {
            gd.a(3, a, "No banner holder: banner will NOT rotate for adSpace: " + g());
            return false;
        }
    }

    private void a(long j) {
        gd.a(3, a, "Scheduled banner rotation for adSpace: " + g());
        this.o = j;
        this.p = j;
    }
}
