package com.flurry.sdk;

import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import com.flurry.android.impl.ads.protocol.v13.Configuration;
import com.flurry.sdk.fs.a;
import java.io.File;
import java.util.List;
import java.util.Map;

public class i implements gg {
    private static final String a = i.class.getSimpleName();
    private final fy<fs> b = new fy<fs>(this) {
        final /* synthetic */ i a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((fs) fxVar);
        }

        public void a(fs fsVar) {
            if (a.kPaused.equals(fsVar.b)) {
                this.a.f.b(fsVar.a);
                this.a.g.a(fsVar.a);
            } else if (a.kResumed.equals(fsVar.b)) {
                this.a.f.c(fsVar.a);
                this.a.g.b(fsVar.a);
            } else if (a.kDestroyed.equals(fsVar.b)) {
                this.a.f.d(fsVar.a);
                this.a.g.c(fsVar.a);
            }
        }
    };
    private final fy<cg> c = new fy<cg>(this) {
        final /* synthetic */ i a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((cg) fxVar);
        }

        public void a(cg cgVar) {
            synchronized (this.a) {
                if (this.a.r == null) {
                    this.a.r = cgVar.a;
                    this.a.a((((long) this.a.r.cacheSizeMb) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) * 1204, ((long) this.a.r.maxAssetSizeKb) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
                    cz.a(this.a.r.maxBitRateKbps);
                    this.a.h.a(this.a.r.sdkAssetUrl);
                    this.a.h.c();
                    return;
                }
            }
        }
    };
    private n d;
    private y e;
    private p f;
    private v g;
    private k h;
    private cf i;
    private ce j;
    private m k;
    private ba l;
    private aa m;
    private File n;
    private File o;
    private fw<List<az>> p;
    private fw<List<ae>> q;
    private Configuration r;

    public static synchronized i a() {
        i iVar;
        synchronized (i.class) {
            iVar = (i) fp.a().a(i.class);
        }
        return iVar;
    }

    public void a(Context context) {
        hb.a(cm.class);
        this.d = new n();
        this.e = new y();
        this.f = new p();
        this.g = new v();
        this.h = new k();
        this.i = new cf();
        this.j = new ce();
        this.k = new m();
        this.l = new ba();
        this.m = new aa();
        this.r = null;
        fz.a().a("com.flurry.android.sdk.ActivityLifecycleEvent", this.b);
        fz.a().a("com.flurry.android.sdk.AdConfigurationEvent", this.c);
        this.n = context.getFileStreamPath(w());
        this.o = context.getFileStreamPath(x());
        this.p = new fw(context.getFileStreamPath(y()), ".yflurryfreqcap.", 2, new ha<List<az>>(this) {
            final /* synthetic */ i a;

            {
                this.a = r1;
            }

            public gx<List<az>> a(int i) {
                return new gw(new az.a());
            }
        });
        this.q = new fw(context.getFileStreamPath(z()), ".yflurrycachedasset", 1, new ha<List<ae>>(this) {
            final /* synthetic */ i a;

            {
                this.a = r1;
            }

            public gx<List<ae>> a(int i) {
                return new gw(new ae.a());
            }
        });
        fp.a().b(new hq(this) {
            final /* synthetic */ i a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.A();
            }
        });
    }

    public void b() {
        fz.a().a(this.b);
        fz.a().a(this.c);
        if (this.e != null) {
            this.e.a();
            this.e = null;
        }
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        if (this.d != null) {
            this.d.a();
            this.d = null;
        }
        if (this.j != null) {
            this.j.b();
            this.j = null;
        }
        this.k = null;
        this.r = null;
        hb.b(cm.class);
    }

    public y c() {
        return this.e;
    }

    public p d() {
        return this.f;
    }

    public v e() {
        return this.g;
    }

    public k f() {
        return this.h;
    }

    public cf g() {
        return this.i;
    }

    public n h() {
        return this.d;
    }

    public ce i() {
        return this.j;
    }

    public m j() {
        return this.k;
    }

    public ba k() {
        return this.l;
    }

    public aa l() {
        return this.m;
    }

    public Configuration m() {
        return this.r;
    }

    public bc n() {
        cm v = v();
        if (v != null) {
            return v.b();
        }
        return null;
    }

    public g o() {
        cm v = v();
        if (v != null) {
            return v.c();
        }
        return null;
    }

    public ef p() {
        cm v = v();
        if (v != null) {
            return v.d();
        }
        return null;
    }

    public eo q() {
        cm v = v();
        if (v != null) {
            return v.e();
        }
        return null;
    }

    public void a(String str, aw awVar, boolean z, Map<String, String> map) {
        cm v = v();
        if (v != null) {
            v.a(str, awVar, z, map);
        }
    }

    public void r() {
        cm v = v();
        if (v != null) {
            v.h();
        }
    }

    public String s() {
        cm v = v();
        if (v != null) {
            return v.i();
        }
        return null;
    }

    private cm v() {
        return a(hc.a().e());
    }

    private cm a(hb hbVar) {
        if (hbVar == null) {
            return null;
        }
        return (cm) hbVar.c(cm.class);
    }

    public at a(String str) {
        cm v = v();
        if (v != null) {
            return v.a(str);
        }
        return null;
    }

    private String w() {
        return ".flurryfreqcap." + Integer.toString(fp.a().d().hashCode(), 16);
    }

    private String x() {
        return ".flurrycachedasset." + Integer.toString(fp.a().d().hashCode(), 16);
    }

    private String y() {
        return ".yflurryfreqcap." + Long.toString(hp.i(fp.a().d()), 16);
    }

    private String z() {
        return ".yflurrycachedasset" + Long.toString(hp.i(fp.a().d()), 16);
    }

    public synchronized void t() {
        gd.a(4, a, "Saving FreqCap data.");
        this.l.b();
        this.p.a(this.l.a());
    }

    private synchronized void A() {
        gd.a(4, a, "Loading FreqCap data.");
        List<az> list = (List) this.p.a();
        if (list != null) {
            for (az a : list) {
                this.l.a(a);
            }
            this.l.b();
        } else {
            if (this.n.exists()) {
                gd.a(4, a, "Legacy FreqCap data found, converting.");
                list = l.a(this.n);
                if (list != null) {
                    for (az a2 : list) {
                        this.l.a(a2);
                    }
                }
                this.l.b();
                this.n.delete();
                t();
            }
            this.l.b();
        }
    }

    private synchronized void a(long j, long j2) {
        if (!this.m.a()) {
            gd.a(3, a, "Precaching: initing from FlurryAdModule");
            this.m.a(j, j2);
            this.m.e();
            fp.a().b(new hq(this) {
                final /* synthetic */ i a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.B();
                }
            });
        }
    }

    public synchronized void u() {
        if (this.m.a()) {
            gd.a(4, a, "Saving CachedAsset data.");
            this.q.a(this.m.d());
        }
    }

    private synchronized void B() {
        if (this.m.a()) {
            gd.a(4, a, "Loading CachedAsset data.");
            List<ae> list = (List) this.q.a();
            if (list != null) {
                for (ae a : list) {
                    this.m.a(a);
                }
            } else if (this.o.exists()) {
                gd.a(4, a, "Legacy CachedAsset data found, deleting.");
                this.o.delete();
            }
        }
    }
}
