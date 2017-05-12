package com.flurry.sdk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.lang.ref.WeakReference;

public class q extends o implements s {
    private static final String a = q.class.getSimpleName();
    private a b = a.INIT;
    private WeakReference<RelativeLayout> c = new WeakReference(null);
    private boolean d;
    private long e;
    private long f;

    enum a {
        INIT,
        READY,
        DISPLAY,
        NEXT
    }

    public q(Context context, ViewGroup viewGroup, String str) {
        super(context, viewGroup, str);
    }

    public void a() {
        fp.a().a(new hq(this) {
            final /* synthetic */ q a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.t();
            }
        });
        super.a();
    }

    public void b() {
        super.b();
    }

    public void c() {
        super.c();
        this.f = this.e;
    }

    public ci i() {
        return i.a().c().a(g(), cr.b(), l()).a();
    }

    public x j() {
        return i.a().c().a(g(), cr.b(), l()).b();
    }

    public void a(ap apVar, long j) {
        Object obj = (s() == null || s().getChildCount() <= 0) ? null : 1;
        if (obj != null) {
            a(j);
        } else {
            h().a((r) this, i(), j());
        }
    }

    protected void r() {
        super.r();
        if (this.e > 0) {
            this.f -= System.currentTimeMillis() - q();
            if (this.f <= 0) {
                if (A()) {
                    gd.a(3, a, "Rotating banner for adSpace: " + g());
                    h().a((r) this, i(), j());
                }
                this.f = this.e;
            }
        }
    }

    protected void a(d dVar) {
        super.a(dVar);
        if (com.flurry.sdk.d.a.kOnFetched.equals(dVar.b)) {
            synchronized (this) {
                if (a.INIT.equals(this.b)) {
                    this.b = a.READY;
                } else if (a.DISPLAY.equals(this.b)) {
                    this.b = a.NEXT;
                }
            }
            if (this.d || a.NEXT.equals(this.b)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ q a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.y();
                    }
                });
            }
        }
    }

    public RelativeLayout s() {
        return (RelativeLayout) this.c.get();
    }

    public void a(RelativeLayout relativeLayout) {
        this.c = new WeakReference(relativeLayout);
    }

    public void t() {
        hp.a();
        RelativeLayout relativeLayout = (RelativeLayout) this.c.get();
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
        this.c.clear();
    }

    public boolean u() {
        boolean equals;
        synchronized (this) {
            equals = a.READY.equals(this.b);
        }
        return equals;
    }

    public void v() {
        this.d = false;
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

    public void w() {
        synchronized (this) {
            if (a.INIT.equals(this.b)) {
                cq.b(this, av.kNotReady);
            } else if (a.READY.equals(this.b)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ q a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.y();
                    }
                });
            } else if (a.DISPLAY.equals(this.b) || a.NEXT.equals(this.b)) {
                cq.b(this);
            }
        }
    }

    public void x() {
        this.d = true;
        synchronized (this) {
            if (a.INIT.equals(this.b)) {
                h().a((r) this, i(), j());
            } else if (a.READY.equals(this.b)) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ q a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.y();
                    }
                });
            } else if (a.DISPLAY.equals(this.b) || a.NEXT.equals(this.b)) {
                cq.b(this);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void y() {
        /*
        r4 = this;
        com.flurry.sdk.hp.b();
        monitor-enter(r4);
        r0 = com.flurry.sdk.q.a.READY;	 Catch:{ all -> 0x005e }
        r1 = r4.b;	 Catch:{ all -> 0x005e }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x005e }
        if (r0 != 0) goto L_0x001a;
    L_0x000e:
        r0 = com.flurry.sdk.q.a.NEXT;	 Catch:{ all -> 0x005e }
        r1 = r4.b;	 Catch:{ all -> 0x005e }
        r0 = r0.equals(r1);	 Catch:{ all -> 0x005e }
        if (r0 != 0) goto L_0x001a;
    L_0x0018:
        monitor-exit(r4);	 Catch:{ all -> 0x005e }
    L_0x0019:
        return;
    L_0x001a:
        r0 = com.flurry.sdk.q.a.DISPLAY;	 Catch:{ all -> 0x005e }
        r4.b = r0;	 Catch:{ all -> 0x005e }
        monitor-exit(r4);	 Catch:{ all -> 0x005e }
        r0 = 3;
        r1 = a;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "render banner (";
        r2 = r2.append(r3);
        r2 = r2.append(r4);
        r3 = ")";
        r2 = r2.append(r3);
        r2 = r2.toString();
        com.flurry.sdk.gd.a(r0, r1, r2);
        r0 = r4.e();
        r1 = r4.f();
        r2 = com.flurry.sdk.fj.a();
        r2 = r2.c();
        if (r2 != 0) goto L_0x0061;
    L_0x0050:
        r0 = 5;
        r1 = a;
        r2 = "There is no network connectivity (ad will not display)";
        com.flurry.sdk.gd.a(r0, r1, r2);
        r0 = com.flurry.sdk.av.kNoNetworkConnectivity;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x005e:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x005e }
        throw r0;
    L_0x0061:
        if (r0 != 0) goto L_0x0069;
    L_0x0063:
        r0 = com.flurry.sdk.av.kNoContext;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x0069:
        if (r1 != 0) goto L_0x0071;
    L_0x006b:
        r0 = com.flurry.sdk.av.kNoViewGroup;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x0071:
        r0 = r4.n();
        if (r0 != 0) goto L_0x007d;
    L_0x0077:
        r0 = com.flurry.sdk.av.kMissingAdController;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x007d:
        r1 = r0.a();
        if (r1 != 0) goto L_0x0089;
    L_0x0083:
        r0 = com.flurry.sdk.av.kInvalidAdUnit;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x0089:
        r2 = com.flurry.android.impl.ads.protocol.v13.AdViewType.BANNER;
        r3 = r1.adViewType;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0099;
    L_0x0093:
        r0 = com.flurry.sdk.av.kIncorrectClassForAdSpace;
        com.flurry.sdk.cq.a(r4, r0);
        goto L_0x0019;
    L_0x0099:
        r2 = com.flurry.sdk.ax.BANNER;
        r0 = r0.d();
        r0 = r2.equals(r0);
        if (r0 != 0) goto L_0x00ac;
    L_0x00a5:
        r0 = com.flurry.sdk.av.kIncorrectClassForAdSpace;
        com.flurry.sdk.cq.a(r4, r0);
        goto L_0x0019;
    L_0x00ac:
        r0 = com.flurry.sdk.cr.b();
        r1 = r1.screenOrientation;
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x00bf;
    L_0x00b8:
        r0 = com.flurry.sdk.av.kWrongOrientation;
        com.flurry.sdk.cq.b(r4, r0);
        goto L_0x0019;
    L_0x00bf:
        r4.o();
        r0 = com.flurry.sdk.fp.a();
        r1 = new com.flurry.sdk.q$5;
        r1.<init>(r4);
        r0.a(r1);
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.q.y():void");
    }

    private void z() {
        hp.a();
        a(0);
        p();
        eg.a(e(), (s) this);
        cq.b(this);
    }

    private boolean A() {
        if (hn.a()) {
            gd.a(3, a, "Device is locked: banner will NOT rotate for adSpace: " + g());
            return false;
        } else if (this.c.get() != null) {
            return true;
        } else {
            gd.a(3, a, "No banner holder: banner will NOT rotate for adSpace: " + g());
            return false;
        }
    }

    private void a(long j) {
        gd.a(3, a, "Scheduled banner rotation for adSpace: " + g());
        this.e = j;
        this.f = j;
    }
}
