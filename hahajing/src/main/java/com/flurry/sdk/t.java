package com.flurry.sdk;

import android.content.Context;

public class t extends o {
    private static final String a = t.class.getSimpleName();
    private a b = a.INIT;

    enum a {
        INIT,
        READY,
        DISPLAY,
        NEXT
    }

    public t(Context context, String str) {
        super(context, null, str);
    }

    public void a() {
        super.a();
    }

    public ci i() {
        return i.a().c().a(g(), cr.b(), l()).a();
    }

    public x j() {
        return i.a().c().a(g(), cr.b(), l()).b();
    }

    protected void a(d dVar) {
        super.a(dVar);
        if (com.flurry.sdk.d.a.kOnFetched.equals(dVar.b)) {
            synchronized (this) {
                synchronized (this) {
                    if (a.INIT.equals(this.b)) {
                        this.b = a.READY;
                    } else if (a.DISPLAY.equals(this.b)) {
                        this.b = a.NEXT;
                    }
                }
            }
            if (a.NEXT.equals(this.b)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ t a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.v();
                    }
                });
            }
        }
    }

    public boolean s() {
        boolean equals;
        synchronized (this) {
            equals = a.READY.equals(this.b);
        }
        return equals;
    }

    public void t() {
        synchronized (this) {
            if (a.INIT.equals(this.b)) {
                h().a((r) this, i(), j());
            } else if (a.READY.equals(this.b)) {
                cq.a(this);
            } else if (a.DISPLAY.equals(this.b) || a.NEXT.equals(this.b)) {
                cq.b(this);
            }
        }
    }

    public void u() {
        synchronized (this) {
            if (a.INIT.equals(this.b)) {
                cq.b(this, av.kNotReady);
            } else if (a.READY.equals(this.b)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ t a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.v();
                    }
                });
            } else if (a.DISPLAY.equals(this.b) || a.NEXT.equals(this.b)) {
                cq.b(this);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void v() {
        /*
        r4 = this;
        com.flurry.sdk.hp.b();
        monitor-enter(r4);
        r0 = com.flurry.sdk.t.a.READY;	 Catch:{ all -> 0x005a }
        r1 = r4.b;	 Catch:{ all -> 0x005a }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x005a }
        if (r0 != 0) goto L_0x001a;
    L_0x000e:
        r0 = com.flurry.sdk.t.a.NEXT;	 Catch:{ all -> 0x005a }
        r1 = r4.b;	 Catch:{ all -> 0x005a }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x005a }
        if (r0 != 0) goto L_0x001a;
    L_0x0018:
        monitor-exit(r4);	 Catch:{ all -> 0x005a }
    L_0x0019:
        return;
    L_0x001a:
        r0 = com.flurry.sdk.t.a.DISPLAY;	 Catch:{ all -> 0x005a }
        r4.b = r0;	 Catch:{ all -> 0x005a }
        monitor-exit(r4);	 Catch:{ all -> 0x005a }
        r0 = 3;
        r1 = a;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "render interstitial (";
        r2 = r2.append(r3);
        r2 = r2.append(r4);
        r3 = ")";
        r2 = r2.append(r3);
        r2 = r2.toString();
        com.flurry.sdk.gd.a(r0, r1, r2);
        r0 = r4.e();
        r1 = com.flurry.sdk.fj.a();
        r1 = r1.c();
        if (r1 != 0) goto L_0x005d;
    L_0x004c:
        r0 = 5;
        r1 = a;
        r2 = "There is no network connectivity (ad will not display)";
        com.flurry.sdk.gd.a(r0, r1, r2);
        r0 = com.flurry.sdk.av.kNoNetworkConnectivity;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x005a:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x005a }
        throw r0;
    L_0x005d:
        if (r0 != 0) goto L_0x0065;
    L_0x005f:
        r0 = com.flurry.sdk.av.kNoContext;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x0065:
        r0 = r4.n();
        if (r0 != 0) goto L_0x0071;
    L_0x006b:
        r0 = com.flurry.sdk.av.kMissingAdController;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x0071:
        r0 = r0.a();
        if (r0 != 0) goto L_0x007d;
    L_0x0077:
        r0 = com.flurry.sdk.av.kInvalidAdUnit;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x007d:
        r1 = r0.combinable;
        r2 = 1;
        if (r1 != r2) goto L_0x0088;
    L_0x0082:
        r0 = com.flurry.sdk.av.kInvalidAdUnit;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x0088:
        r1 = com.flurry.android.impl.ads.protocol.v13.AdViewType.INTERSTITIAL;
        r2 = r0.adViewType;
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x0098;
    L_0x0092:
        r0 = com.flurry.sdk.av.kIncorrectClassForAdSpace;
        com.flurry.sdk.cq.a(r4, r0);
        goto L_0x0019;
    L_0x0098:
        r1 = com.flurry.sdk.cr.b();
        r0 = r0.screenOrientation;
        r0 = r1.equals(r0);
        if (r0 != 0) goto L_0x00ab;
    L_0x00a4:
        r0 = com.flurry.sdk.av.kWrongOrientation;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x00ab:
        r4.o();
        r0 = com.flurry.sdk.fp.a();
        r1 = new com.flurry.sdk.t$3;
        r1.<init>(r4);
        r0.a(r1);
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.t.v():void");
    }

    private void w() {
        hp.a();
        p();
        en a = i.a().q().a(e(), this);
        if (a != null) {
            a.a();
        }
        cq.b(this);
    }
}
