package com.flurry.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.flurry.sdk.hh.a;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public class hc implements a {
    private static hc a;
    private static final String b = hc.class.getSimpleName();
    private final Map<Context, hb> c = new WeakHashMap();
    private final hd d = new hd();
    private final Object e = new Object();
    private long f;
    private long g;
    private hb h;
    private fy<he> i = new fy<he>(this) {
        final /* synthetic */ hc a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((he) fxVar);
        }

        public void a(he heVar) {
            this.a.h();
        }
    };
    private fy<fs> j = new fy<fs>(this) {
        final /* synthetic */ hc a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((fs) fxVar);
        }

        public void a(fs fsVar) {
            switch (fsVar.b) {
                case kStarted:
                    gd.a(3, hc.b, "Automatic onStartSession for context:" + fsVar.a);
                    this.a.d(fsVar.a);
                    return;
                case kStopped:
                    gd.a(3, hc.b, "Automatic onEndSession for context:" + fsVar.a);
                    this.a.e(fsVar.a);
                    return;
                case kDestroyed:
                    gd.a(3, hc.b, "Automatic onEndSession (destroyed) for context:" + fsVar.a);
                    this.a.e(fsVar.a);
                    return;
                default:
                    return;
            }
        }
    };

    public static synchronized hc a() {
        hc hcVar;
        synchronized (hc.class) {
            if (a == null) {
                a = new hc();
            }
            hcVar = a;
        }
        return hcVar;
    }

    public static synchronized void b() {
        synchronized (hc.class) {
            if (a != null) {
                fz.a().a(a.i);
                fz.a().a(a.j);
                hg.a().b("ContinueSessionMillis", a);
            }
            a = null;
        }
    }

    private hc() {
        hh a = hg.a();
        this.f = 0;
        this.g = ((Long) a.a("ContinueSessionMillis")).longValue();
        a.a("ContinueSessionMillis", (a) this);
        gd.a(4, b, "initSettings, ContinueSessionMillis = " + this.g);
        fz.a().a("com.flurry.android.sdk.ActivityLifecycleEvent", this.j);
        fz.a().a("com.flurry.android.sdk.FlurrySessionTimerEvent", this.i);
    }

    public long c() {
        return this.f;
    }

    public synchronized int d() {
        return this.c.size();
    }

    public hb e() {
        hb hbVar;
        synchronized (this.e) {
            hbVar = this.h;
        }
        return hbVar;
    }

    private void a(hb hbVar) {
        synchronized (this.e) {
            this.h = hbVar;
        }
    }

    private void b(hb hbVar) {
        synchronized (this.e) {
            if (this.h == hbVar) {
                this.h = null;
            }
        }
    }

    public synchronized void a(Context context) {
        if (!(context instanceof Application)) {
            if (ft.a().c() && (context instanceof Activity)) {
                gd.a(3, b, "bootstrap for context:" + context);
                d(context);
            }
        }
    }

    public synchronized void b(Context context) {
        if (!(context instanceof Application)) {
            if (!(ft.a().c() && (context instanceof Activity))) {
                gd.a(3, b, "Manual onStartSession for context:" + context);
                d(context);
            }
        }
    }

    public synchronized void c(Context context) {
        if (!(context instanceof Application)) {
            if (!(ft.a().c() && (context instanceof Activity))) {
                gd.a(3, b, "Manual onEndSession for context:" + context);
                e(context);
            }
        }
    }

    public synchronized boolean f() {
        boolean z;
        if (e() == null) {
            gd.a(2, b, "Session not found. No active session");
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public synchronized void g() {
        for (Entry entry : this.c.entrySet()) {
            ((hb) entry.getValue()).c((Context) entry.getKey());
        }
        this.c.clear();
        h();
    }

    synchronized void d(Context context) {
        if (((hb) this.c.get(context)) == null) {
            this.d.a();
            hb e = e();
            if (e == null) {
                e = new hb();
                gd.e(b, "Flurry session created for context:" + context);
                e.a(context);
            }
            this.c.put(context, e);
            a(e);
            gd.e(b, "Flurry session started for context:" + context);
            e.b(context);
            this.f = 0;
        } else if (ft.a().c()) {
            gd.a(3, b, "Session already started with context:" + context);
        } else {
            gd.e(b, "Session already started with context:" + context);
        }
    }

    synchronized void e(Context context) {
        hb hbVar = (hb) this.c.remove(context);
        if (hbVar != null) {
            gd.e(b, "Flurry session ended for context:" + context);
            hbVar.c(context);
            if (d() == 0) {
                this.d.a(this.g);
                this.f = System.currentTimeMillis();
            } else {
                this.f = 0;
            }
        } else if (ft.a().c()) {
            gd.a(3, b, "Session cannot be ended, session not found for context:" + context);
        } else {
            gd.e(b, "Session cannot be ended, session not found for context:" + context);
        }
    }

    synchronized void h() {
        int d = d();
        if (d > 0) {
            gd.a(5, b, "Session cannot be finalized, sessionContextCount:" + d);
        } else {
            final hb e = e();
            if (e == null) {
                gd.a(5, b, "Session cannot be finalized, current session not found");
            } else {
                gd.e(b, "Flurry session finalized");
                e.a();
                fp.a().b(new hq(this) {
                    final /* synthetic */ hc b;

                    public void safeRun() {
                        this.b.b(e);
                    }
                });
            }
        }
    }

    public void a(String str, Object obj) {
        if (str.equals("ContinueSessionMillis")) {
            this.g = ((Long) obj).longValue();
            gd.a(4, b, "onSettingUpdate, ContinueSessionMillis = " + this.g);
            return;
        }
        gd.a(6, b, "onSettingUpdate internal error!");
    }
}
