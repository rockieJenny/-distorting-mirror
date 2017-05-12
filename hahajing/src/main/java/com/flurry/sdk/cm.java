package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.impl.ads.protocol.v13.SdkLogRequest;
import com.flurry.sdk.at.a;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cm implements hf {
    private static final String a = cm.class.getSimpleName();
    private final Map<String, at> b = Collections.synchronizedMap(new HashMap());
    private bc c;
    private h d;
    private g e;
    private ef f;
    private eo g;
    private File h;
    private fw<List<at>> i;
    private String j;

    public void a(Context context) {
        this.c = new bc();
        this.d = new h();
        this.e = new g();
        this.e.a();
        this.f = new ei();
        this.g = new ej();
        this.h = context.getFileStreamPath(f());
        this.i = new fw(context.getFileStreamPath(g()), ".yflurryadlog.", 1, new ha<List<at>>(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public gx<List<at>> a(int i) {
                return new gw(new a(new as.a()));
            }
        });
        this.j = cw.a(context);
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.l();
            }
        });
    }

    public void b(Context context) {
        this.d.b();
        i.a().k().b();
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                i.a().l().e();
            }
        });
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                i.a().i().d();
            }
        });
    }

    public void c(Context context) {
        if (!ft.a().c()) {
            i.a().d().d(context);
            i.a().e().c(context);
        }
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.k();
            }
        });
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                i.a().t();
            }
        });
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                i.a().u();
            }
        });
    }

    public void a() {
        this.d.a();
        i.a().d().a();
        i.a().e().a();
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.j();
            }
        });
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                i.a().l().f();
            }
        });
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                i.a().i().c();
            }
        });
    }

    public bc b() {
        return this.c;
    }

    public g c() {
        return this.e;
    }

    public ef d() {
        return this.f;
    }

    public eo e() {
        return this.g;
    }

    String f() {
        return ".flurryadlog." + Integer.toString(fp.a().d().hashCode(), 16);
    }

    String g() {
        return ".yflurryadlog." + Long.toString(hp.i(fp.a().d()), 16);
    }

    public synchronized void h() {
        fp.a().b(new hq(this) {
            final /* synthetic */ cm a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.j();
            }
        });
    }

    private synchronized void j() {
        SdkLogRequest a = a(new ArrayList(this.b.values()));
        if (a != null) {
            i.a().g().a(a, j.a().i(), fp.a().d(), "" + fq.a());
        }
        m();
    }

    private SdkLogRequest a(List<at> list) {
        List a = cr.a((List) list);
        if (a.isEmpty()) {
            gd.a(3, a, "List of adLogs is empty");
            return null;
        }
        String d = fp.a().d();
        List e = cr.e();
        SdkLogRequest sdkLogRequest = new SdkLogRequest();
        sdkLogRequest.apiKey = d;
        sdkLogRequest.adReportedIds = e;
        sdkLogRequest.sdkAdLogs = a;
        sdkLogRequest.testDevice = false;
        sdkLogRequest.agentTimestamp = System.currentTimeMillis();
        sdkLogRequest.agentVersion = Integer.toString(fq.a());
        gd.a(3, a, "Got ad log request:" + sdkLogRequest.toString());
        return sdkLogRequest;
    }

    private synchronized void k() {
        gd.a(4, a, "Saving AdLog data.");
        this.i.a(new ArrayList(this.b.values()));
    }

    private synchronized void l() {
        gd.a(4, a, "Loading AdLog data.");
        List list = (List) this.i.a();
        if (list != null) {
            b(list);
        } else if (this.h.exists()) {
            gd.a(4, a, "Legacy AdLog data found, converting.");
            list = l.b(this.h);
            if (list != null) {
                b(list);
            }
            this.h.delete();
            k();
        }
    }

    public synchronized void a(String str, aw awVar, boolean z, Map<String, String> map) {
        if (awVar != null) {
            gd.a(3, a, "logAdEvent(" + str + ", " + awVar + ", " + z + ", " + map + ")");
            a(str).a(new as(awVar.a(), z, fd.a().f(), map));
        }
    }

    public String i() {
        return this.j;
    }

    private void b(List<at> list) {
        for (at atVar : list) {
            this.b.put(atVar.b(), atVar);
        }
    }

    public at a(String str) {
        at atVar = (at) this.b.get(str);
        if (atVar == null) {
            atVar = new at(str);
            if (this.b.size() < 32767) {
                this.b.put(atVar.b(), atVar);
            }
        }
        return atVar;
    }

    private void m() {
        this.b.clear();
        this.i.b();
    }
}
