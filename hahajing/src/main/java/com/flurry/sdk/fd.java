package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.fj.a;

public class fd implements gg {
    private static final String a = fd.class.getSimpleName();

    public static synchronized fd a() {
        fd fdVar;
        synchronized (fd.class) {
            fdVar = (fd) fp.a().a(fd.class);
        }
        return fdVar;
    }

    public void a(Context context) {
        hb.a(fo.class);
        fz.a();
        hk.a();
        hg.a();
        fr.a();
        fj.a();
        fe.a();
        fk.a();
        fh.a();
        fe.a();
        fm.a();
        fg.a();
        fn.a();
    }

    public void b() {
        fn.b();
        fg.b();
        fm.b();
        fe.b();
        fh.b();
        fk.b();
        fe.b();
        fj.b();
        fr.b();
        hg.b();
        hk.b();
        fz.b();
        hb.b(fo.class);
    }

    public long c() {
        fo i = i();
        if (i != null) {
            return i.b();
        }
        return 0;
    }

    public long d() {
        fo i = i();
        if (i != null) {
            return i.c();
        }
        return 0;
    }

    public long e() {
        fo i = i();
        if (i != null) {
            return i.d();
        }
        return -1;
    }

    public long f() {
        fo i = i();
        if (i != null) {
            return i.f();
        }
        return 0;
    }

    public long g() {
        fo i = i();
        if (i != null) {
            return i.e();
        }
        return 0;
    }

    public a h() {
        return fj.a().d();
    }

    private fo i() {
        return a(hc.a().e());
    }

    private fo a(hb hbVar) {
        if (hbVar == null) {
            return null;
        }
        return (fo) hbVar.c(fo.class);
    }
}
