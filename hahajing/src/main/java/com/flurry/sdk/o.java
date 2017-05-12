package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.flurry.sdk.d.a;
import java.lang.ref.WeakReference;

public abstract class o implements r {
    private static final String a = o.class.getSimpleName();
    private final int b;
    private final WeakReference<Context> c;
    private final WeakReference<ViewGroup> d;
    private final String e;
    private final ch f;
    private long g;
    private final fy<hj> h = new fy<hj>(this) {
        final /* synthetic */ o a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((hj) fxVar);
        }

        public void a(hj hjVar) {
            this.a.r();
            this.a.g = System.currentTimeMillis();
        }
    };
    private final fy<d> i = new fy<d>(this) {
        final /* synthetic */ o a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((d) fxVar);
        }

        public void a(d dVar) {
            if (dVar.a == this.a && dVar.b != null) {
                this.a.a(dVar);
            }
        }
    };
    private ap j;
    private ap k;
    private e l;

    protected o(Context context, ViewGroup viewGroup, String str) {
        i a = i.a();
        if (a == null) {
            throw new IllegalStateException("A session must be started before ad objects may be instantiated.");
        }
        this.b = cr.a();
        this.c = new WeakReference(context);
        this.d = new WeakReference(viewGroup);
        this.e = str;
        this.f = new ch(str);
        a.d().a(context, this);
        s();
        u();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        a();
    }

    public void a() {
        t();
        v();
        i.a().d().b(e(), this);
        aa l = i.a().l();
        if (l != null) {
            l.a((r) this);
        }
        if (this.f != null) {
            this.f.a();
        }
        this.l = null;
    }

    public void b() {
        t();
    }

    public void c() {
        s();
    }

    public int d() {
        return this.b;
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
        return this.f;
    }

    public ci i() {
        return i.a().c().a(g(), null, l()).a();
    }

    public x j() {
        return i.a().c().a(g(), null, l()).b();
    }

    public ap k() {
        return this.k;
    }

    public e l() {
        return this.l;
    }

    public void a(ap apVar) {
        this.j = apVar;
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            h().b();
            j().a(str);
        }
    }

    public void a(ap apVar, long j) {
        h().a((r) this, i(), j());
    }

    public void m() {
        this.f.c();
    }

    public void a(e eVar) {
        this.l = eVar;
    }

    private void s() {
        this.g = System.currentTimeMillis();
        hk.a().a(this.h);
    }

    private void t() {
        hk.a().b(this.h);
    }

    private void u() {
        fz.a().a("com.flurry.android.impl.ads.AdStateEvent", this.i);
    }

    private void v() {
        fz.a().a(this.i);
    }

    protected ap n() {
        return this.j;
    }

    protected void o() {
        hp.b();
        if (!this.j.o() && this.j.n()) {
            gd.a(3, a, "Precaching optional for ad, copying assets before display");
            i.a().l().a((r) this, this.j);
        }
    }

    protected void p() {
        this.k = this.j;
        this.j = null;
    }

    protected long q() {
        return this.g;
    }

    protected void r() {
    }

    protected void a(d dVar) {
        if (a.kOnFetched.equals(dVar.b) || a.kOnFetchFailed.equals(dVar.b)) {
            int b = j().b();
            if (b == 0) {
                gd.a(3, a, "Starting ad request from EnsureCacheNotEmpty size: " + b);
                i().a((r) this, j(), null);
            }
        }
    }
}
