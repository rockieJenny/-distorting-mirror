package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ch {
    private static final String a = ch.class.getSimpleName();
    private final String b;
    private final ci c;
    private final TreeSet<ap> d;
    private final TreeSet<ap> e;
    private a f;
    private r g;
    private ci h;
    private x i;
    private ap j;
    private ap k;
    private int l;
    private long m;
    private long n;
    private long o;
    private long p;
    private long q;
    private final fy<hj> r = new fy<hj>(this) {
        final /* synthetic */ ch a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((hj) fxVar);
        }

        public void a(hj hjVar) {
            if (a.REQUEST.equals(this.a.f)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.a.g();
                    }
                });
            } else if (a.CSRTB_AWAIT_AUCTION.equals(this.a.f)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.a.h();
                    }
                });
            } else if (a.SELECT.equals(this.a.f)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.a.i();
                    }
                });
            } else if (a.PRERENDER.equals(this.a.f)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.a.j();
                    }
                });
            }
        }
    };
    private final fy<ad> s = new fy<ad>(this) {
        final /* synthetic */ ch a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ad) fxVar);
        }

        public void a(final ad adVar) {
            fp.a().b(new hq(this) {
                final /* synthetic */ AnonymousClass4 b;

                public void safeRun() {
                    this.b.a.a(adVar.a, adVar.b);
                }
            });
        }
    };
    private final fy<cj> t = new fy<cj>(this) {
        final /* synthetic */ ch a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((cj) fxVar);
        }

        public void a(final cj cjVar) {
            if (this.a.h == cjVar.a) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass5 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.a.f();
                    }
                });
            } else if (this.a.c == cjVar.a) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass5 b;

                    public void safeRun() {
                        this.b.a.a(cjVar.c);
                    }
                });
            }
        }
    };

    enum a {
        NONE,
        REQUEST,
        CSRTB_AUCTION_REQUIRED,
        CSRTB_AWAIT_AUCTION,
        SELECT,
        PREPARE,
        PRERENDER
    }

    public ch(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("adSpace cannot be null");
        }
        this.b = str;
        this.c = new ci(str);
        this.d = new TreeSet();
        this.e = new TreeSet();
        this.f = a.NONE;
        e();
    }

    public synchronized void a() {
        e();
        this.c.a();
        this.d.clear();
    }

    public synchronized void a(r rVar, ci ciVar, x xVar) {
        if (!(rVar == null || ciVar == null || xVar == null)) {
            gd.a(3, a, "fetchAd: adObject=" + rVar);
            if (a.NONE.equals(this.f)) {
                this.g = rVar;
                this.i = xVar;
                this.h = ciVar;
                if (fj.a().c()) {
                    i.a().l().g();
                    if (this.d.isEmpty()) {
                        this.d.addAll(this.i.c());
                    }
                    if (this.d.isEmpty()) {
                        a(a.REQUEST);
                        if (15000 > 0) {
                            gd.a(3, a, "Setting ad request timeout for " + 15000 + " ms");
                            this.m = 15000 + System.currentTimeMillis();
                        }
                        this.h.a(this.g, this.i, null);
                    } else {
                        this.j = (ap) this.d.pollFirst();
                        a(a.SELECT);
                        fp.a().b(new hq(this) {
                            final /* synthetic */ ch a;

                            {
                                this.a = r1;
                            }

                            public void safeRun() {
                                this.a.l();
                            }
                        });
                    }
                } else {
                    gd.a(5, a, "There is no network connectivity (ad will not fetch)");
                    cq.a(this.g, av.kNoNetworkConnectivity);
                    e();
                }
            }
        }
    }

    public synchronized void b() {
        this.d.clear();
    }

    public synchronized void c() {
        if (this.h != null) {
            this.h.b();
        }
        e();
    }

    private synchronized void e() {
        gd.a(3, a, "Fetch finished for adObject:" + this.g + " adSpace:" + this.b);
        this.c.b();
        fn.a().a((Object) this);
        a(a.NONE);
        if (this.i != null) {
            this.i.a(this.e);
        }
        this.e.clear();
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 0;
    }

    private synchronized void a(a aVar) {
        if (aVar == null) {
            aVar = a.NONE;
        }
        gd.a(3, a, "Setting state from " + this.f + " to " + aVar + " for adspace: " + this.b);
        if (a.NONE.equals(this.f) && !a.NONE.equals(aVar)) {
            gd.a(3, a, "Adding fetch listeners for adspace: " + this.b);
            hk.a().a(this.r);
            fz.a().a("com.flurry.android.sdk.AssetStatusEvent", this.s);
            fz.a().a("com.flurry.android.sdk.AdResponseEvent", this.t);
        } else if (a.NONE.equals(aVar) && !a.NONE.equals(this.f)) {
            gd.a(3, a, "Removing fetch listeners for adspace: " + this.b);
            hk.a().b(this.r);
            fz.a().a(this.s);
            fz.a().a(this.t);
        }
        this.f = aVar;
    }

    private synchronized void f() {
        if (a.REQUEST.equals(this.f)) {
            this.d.addAll(this.i.c());
            if (!this.d.isEmpty()) {
                this.j = (ap) this.d.pollFirst();
            }
            a(a.SELECT);
            fp.a().b(new hq(this) {
                final /* synthetic */ ch a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.l();
                }
            });
        }
    }

    private synchronized void a(List<ap> list) {
        if (a.CSRTB_AWAIT_AUCTION.equals(this.f)) {
            if (list != null) {
                if (!list.isEmpty() && list.size() <= 1) {
                    ap apVar = (ap) list.get(0);
                    if (apVar.a().renderTime) {
                        List list2 = apVar.a().adFrames;
                        if (list2 == null || list2.isEmpty() || ((AdFrame) list2.get(0)).binding == 6) {
                            n();
                        } else {
                            String str = null;
                            if (apVar.a() != null) {
                                Map map = apVar.a().clientSideRtbPayload;
                                if (map != null && map.containsKey("GROUP_ID")) {
                                    str = (String) map.get("GROUP_ID");
                                }
                            }
                            if (str == null) {
                                Collection collection = apVar.a().adFrames;
                                List list3 = this.k.a().adFrames;
                                list3.clear();
                                list3.addAll(collection);
                                apVar.a().adFrames = list3;
                                apVar.a().groupId = this.k.a().groupId;
                                if (apVar.a().clientSideRtbPayload != null && apVar.a().clientSideRtbPayload.isEmpty()) {
                                    apVar.a().clientSideRtbPayload = this.k.a().clientSideRtbPayload;
                                }
                                this.j = apVar;
                            } else {
                                this.j = apVar;
                            }
                            a(a.SELECT);
                            fp.a().b(new hq(this) {
                                final /* synthetic */ ch a;

                                {
                                    this.a = r1;
                                }

                                public void safeRun() {
                                    this.a.l();
                                }
                            });
                        }
                    } else {
                        n();
                    }
                }
            }
            n();
        }
    }

    private synchronized void g() {
        if (this.m > 0 && System.currentTimeMillis() > this.m) {
            cq.a(this.g, av.kUnfilled);
            e();
        }
    }

    private synchronized void h() {
        if (this.n > 0 && System.currentTimeMillis() > this.n) {
            n();
        }
    }

    private synchronized void i() {
        if (this.p > 0 && System.currentTimeMillis() > this.p) {
            a(this.j, av.kVASTResolveTimeout);
            e();
        } else if (this.o > 0 && System.currentTimeMillis() > this.o) {
            m();
            l();
        }
    }

    private synchronized void j() {
        if (this.q > 0 && System.currentTimeMillis() > this.q) {
            fn.a().a((Object) this);
            a(this.j, av.kPrerenderDownloadTimeout);
            e();
        }
    }

    private synchronized void a(String str, ah ahVar) {
        if (a.SELECT.equals(this.f) && i.a().l().a(this.j, str)) {
            gd.a(3, a, "Detected asset status change for asset:" + str + " status:" + ahVar);
            if (ah.COMPLETE.equals(ahVar) || ah.ERROR.equals(ahVar)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ ch a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.l();
                    }
                });
            }
        }
    }

    private synchronized void k() {
        if (a.CSRTB_AUCTION_REQUIRED.equals(this.f)) {
            if (this.j == null) {
                gd.a(6, a, "An auction is required, but there is no ad unit!");
                cq.a(this.g, av.kMissingAdController);
                e();
            } else {
                a(a.CSRTB_AWAIT_AUCTION);
                long j = this.j.a().preRenderTimeoutMillis;
                if (j > 0) {
                    gd.a(3, a, "Setting CSRTB auction timeout for " + j + " ms");
                    this.n = j + System.currentTimeMillis();
                }
                this.k = this.j;
                this.c.a(this.g, null, this.j);
            }
        }
    }

    private synchronized void l() {
        if (a.SELECT.equals(this.f)) {
            if (this.g.e() == null) {
                cq.a(this.g, av.kNoContext);
                e();
            } else if (hn.a()) {
                cq.a(this.g, av.kDeviceLocked);
                e();
            } else {
                aa l = i.a().l();
                loop0:
                while (this.j != null) {
                    List list = this.j.a().adFrames;
                    if (list != null && !list.isEmpty()) {
                        af a;
                        long j;
                        int i;
                        int i2 = 0;
                        while (i2 < list.size()) {
                            if (((AdFrame) list.get(i2)).binding != 6) {
                                cy d = this.j.d(i2);
                                if (d != null && !d.c()) {
                                    if (!d.d()) {
                                        long j2 = this.j.a().preRenderTimeoutMillis;
                                        if (j2 > 0 && this.p == 0) {
                                            gd.a(3, a, "Setting VAST resolve timeout for " + j2 + " ms");
                                            this.p = j2 + System.currentTimeMillis();
                                        }
                                        a(this.j, i2, d);
                                    }
                                    this.p = 0;
                                    if (!this.j.o()) {
                                        gd.a(3, a, "Pre-caching not required for ad");
                                        break;
                                    }
                                    a = l.a(this.j);
                                    j = (long) this.j.a().preCacheAdSkippableTimeLimitMillis;
                                    gd.a(3, a, "Pre-caching required for ad, AdUnitCachedStatus: " + a + ", skip time limit: " + j);
                                    if (!af.COMPLETE.equals(a) && j > 0 && this.o == 0) {
                                        gd.a(3, a, "Setting skip timer for " + j + " ms");
                                        this.o = System.currentTimeMillis() + j;
                                    }
                                    if (af.COMPLETE.equals(a)) {
                                        gd.a(3, a, "Pre-caching completed, ad may proceed");
                                        break;
                                    } else if (af.IN_PROGRESS.equals(a)) {
                                        if (j != 0) {
                                            if (j > 0) {
                                                if (System.currentTimeMillis() > this.o) {
                                                    gd.a(3, a, "Waiting for skip timer");
                                                    break;
                                                } else {
                                                    gd.a(3, a, "Skip timer expired");
                                                    m();
                                                }
                                            } else {
                                                continue;
                                            }
                                        } else {
                                            gd.a(3, a, "No skip timer");
                                            m();
                                        }
                                    } else if (j != 0) {
                                        gd.a(3, a, "No skip timer");
                                        m();
                                    } else {
                                        i = this.l + 1;
                                        this.l = i;
                                        if (i <= 1) {
                                            gd.a(3, a, "Too many precaching attempts, precaching failed");
                                            a(this.j, av.kPrecachingDownloadFailed);
                                            e();
                                        } else {
                                            i = l.b(this.j);
                                            if (i <= 0) {
                                                gd.a(3, a, "Requesting " + i + " asset(s), attempt #" + this.l);
                                            } else {
                                                gd.a(3, a, "No assets to cache");
                                            }
                                        }
                                    }
                                } else {
                                    i2++;
                                }
                            } else {
                                this.j.a().renderTime = true;
                                a(a.CSRTB_AUCTION_REQUIRED);
                                k();
                                break loop0;
                            }
                        }
                        this.p = 0;
                        if (!this.j.o()) {
                            a = l.a(this.j);
                            j = (long) this.j.a().preCacheAdSkippableTimeLimitMillis;
                            gd.a(3, a, "Pre-caching required for ad, AdUnitCachedStatus: " + a + ", skip time limit: " + j);
                            gd.a(3, a, "Setting skip timer for " + j + " ms");
                            this.o = System.currentTimeMillis() + j;
                            if (af.COMPLETE.equals(a)) {
                                gd.a(3, a, "Pre-caching completed, ad may proceed");
                                break;
                            } else if (af.IN_PROGRESS.equals(a)) {
                                if (j != 0) {
                                    gd.a(3, a, "No skip timer");
                                    m();
                                } else if (j > 0) {
                                    continue;
                                } else if (System.currentTimeMillis() > this.o) {
                                    gd.a(3, a, "Waiting for skip timer");
                                    break;
                                } else {
                                    gd.a(3, a, "Skip timer expired");
                                    m();
                                }
                            } else if (j != 0) {
                                i = this.l + 1;
                                this.l = i;
                                if (i <= 1) {
                                    i = l.b(this.j);
                                    if (i <= 0) {
                                        gd.a(3, a, "No assets to cache");
                                    } else {
                                        gd.a(3, a, "Requesting " + i + " asset(s), attempt #" + this.l);
                                    }
                                } else {
                                    gd.a(3, a, "Too many precaching attempts, precaching failed");
                                    a(this.j, av.kPrecachingDownloadFailed);
                                    e();
                                }
                            } else {
                                gd.a(3, a, "No skip timer");
                                m();
                            }
                        } else {
                            gd.a(3, a, "Pre-caching not required for ad");
                            break;
                        }
                    }
                    a(this.j, av.kInvalidAdUnit);
                    e();
                    break;
                }
                if (this.j == null) {
                    i.a().a(null, aw.EV_UNFILLED, true, Collections.emptyMap());
                    cq.a(this.g, av.kUnfilled);
                    e();
                } else {
                    a(a.PREPARE);
                    fp.a().a(new hq(this) {
                        final /* synthetic */ ch a;

                        {
                            this.a = r1;
                        }

                        public void safeRun() {
                            this.a.o();
                        }
                    });
                }
            }
        }
    }

    private synchronized void a(ap apVar, int i, cy cyVar) {
        final String e = cyVar.e();
        hr gkVar = new gk();
        gkVar.a(e);
        gkVar.a(20000);
        gkVar.b(new gy());
        final cy cyVar2 = cyVar;
        final int i2 = i;
        final ap apVar2 = apVar;
        gkVar.a(new com.flurry.sdk.gk.a<Void, String>(this) {
            final /* synthetic */ ch e;

            public void a(gk<Void, String> gkVar, String str) {
                gd.a(3, ch.a, "VAST resolver: HTTP status code is:" + gkVar.e() + " for url: " + e);
                cy cyVar = null;
                if (gkVar.c()) {
                    gd.a(3, ch.a, "VAST resolver response:" + str + " for url: " + e);
                    cyVar = cy.a(cyVar2, da.a(str));
                }
                if (cyVar == null) {
                    gd.a(3, ch.a, "VAST resolver failed for frame: " + i2);
                    apVar2.a(i2, new com.flurry.sdk.cy.a().a().b());
                } else {
                    gd.a(3, ch.a, "VAST resolver successful for frame: " + i2);
                    apVar2.a(i2, cyVar);
                }
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass11 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.e.l();
                    }
                });
            }
        });
        fn.a().a((Object) this, gkVar);
    }

    private synchronized void m() {
        if (a.SELECT.equals(this.f)) {
            gd.a(3, a, "Precaching required for incomplete ad unit, skipping ad group -- adspace: " + this.b + " groupId: " + this.j.a().groupId);
            this.e.add(this.j);
            this.j = null;
            this.e.addAll(this.d);
            this.d.clear();
            this.d.addAll(this.i.c());
            if (!this.d.isEmpty()) {
                this.j = (ap) this.d.pollFirst();
            }
            f.a().a("precachingAdGroupSkipped", 1);
            this.l = 0;
            this.o = 0;
        }
    }

    private synchronized void n() {
        if (a.CSRTB_AUCTION_REQUIRED.equals(this.f) || a.CSRTB_AWAIT_AUCTION.equals(this.f)) {
            Object obj;
            for (a a : cr.a((AdFrame) this.j.a().adFrames.get(0), new b(aw.EV_UNFILLED, null, null, null, null))) {
                if (au.AC_NEXT_AD_UNIT.equals(a.a())) {
                    obj = null;
                    break;
                }
            }
            int i = 1;
            co.a(aw.EV_UNFILLED, Collections.emptyMap(), this.g.e(), this.g, this.j, 0);
            if (obj == 1) {
                a(this.j, av.kCSRTBAuctionTimeout);
            }
            e();
        }
    }

    private synchronized void o() {
        hp.a();
        if (a.PREPARE.equals(this.f)) {
            gd.a(3, a, "Preparing ad");
            if (this.g.e() == null) {
                a(this.j, av.kNoContext);
                e();
            } else {
                co.a(aw.EV_FILLED, Collections.emptyMap(), this.g.e(), this.g, this.j, 1);
                this.g.a(this.j);
                a(a.PRERENDER);
                fp.a().b(new hq(this) {
                    final /* synthetic */ ch a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.p();
                    }
                });
            }
        }
    }

    private synchronized void p() {
        synchronized (this) {
            if (a.PRERENDER.equals(this.f)) {
                gd.a(3, a, "Pre-rendering ad");
                List list = this.j.a().adFrames;
                for (int i = 0; i < list.size(); i++) {
                    cy d = this.j.d(i);
                    if (d != null && (!d.c() || d.d())) {
                        a(this.j, av.kInvalidVASTAd);
                        e();
                        break;
                    }
                }
                aa l = i.a().l();
                if (this.j.o()) {
                    gd.a(3, a, "Precaching required for ad, copying assets");
                    if (af.COMPLETE.equals(l.a(this.j))) {
                        f.a().a("precachingAdAssetsAvailable", 1);
                        if (!i.a().l().a(this.g, this.j)) {
                            gd.a(3, a, "Could not copy required ad assets");
                            f.a().a("precachingAdAssetCopyFailed", 1);
                            a(this.j, av.kPrecachingCopyFailed);
                            e();
                        }
                    } else {
                        gd.a(3, a, "Ad assets incomplete");
                        f.a().a("precachingAdAssetsIncomplete", 1);
                        a(this.j, av.kPrecachingMissingAssets);
                        e();
                    }
                } else if (this.j.n()) {
                    gd.a(3, a, "Precaching optional for ad, copying assets");
                    i.a().l().a(this.g, this.j);
                }
                co.a(aw.EV_PREPARED, Collections.emptyMap(), this.g.e(), this.g, this.j, 0);
                AdFrame adFrame = (AdFrame) list.get(0);
                if (adFrame.binding == 1) {
                    gd.a(3, a, "Binding is HTML_URL, pre-render required");
                    long j = this.j.a().preRenderTimeoutMillis;
                    if (j > 0) {
                        gd.a(3, a, "Setting pre-render timeout for " + j + " ms");
                        this.q = j + System.currentTimeMillis();
                    }
                    a(this.j, adFrame.display);
                } else {
                    cq.a(this.g);
                    e();
                }
            }
        }
    }

    private synchronized void a(final ap apVar, final String str) {
        gd.a(3, a, "Pre-render: HTTP get for url: " + str);
        hr gkVar = new gk();
        gkVar.a(str);
        gkVar.a(20000);
        gkVar.b(new gy());
        gkVar.a(new com.flurry.sdk.gk.a<Void, String>(this) {
            final /* synthetic */ ch c;

            public void a(gk<Void, String> gkVar, String str) {
                gd.a(3, ch.a, "Prerender: HTTP status code is:" + gkVar.e() + " for url: " + str);
                if (gkVar.c()) {
                    apVar.b(str);
                    cq.a(this.c.g);
                    this.c.e();
                    return;
                }
                this.c.a(apVar, av.kPrerenderDownloadFailed);
                this.c.e();
            }
        });
        fn.a().a((Object) this, gkVar);
    }

    private synchronized void a(ap apVar, av avVar) {
        Map hashMap = new HashMap();
        hashMap.put("preRender", "true");
        String str = "errorCode";
        if (avVar == null) {
            avVar = av.kUnknown;
        }
        hashMap.put(str, Integer.toString(avVar.a()));
        co.a(aw.EV_RENDER_FAILED, hashMap, this.g.e(), this.g, apVar, 0);
    }
}
