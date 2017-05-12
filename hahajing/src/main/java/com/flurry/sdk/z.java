package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.sdk.al.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class z {
    private static final String b = z.class.getSimpleName();
    final Map<String, ae> a = new HashMap();
    private final Map<String, ae> c = new LinkedHashMap();
    private final Map<String, ai> d = new LinkedHashMap();
    private a e = a.INIT;
    private final ak<byte[]> f;
    private final long g;
    private final int h;

    enum a {
        INIT,
        ACTIVE,
        PAUSED
    }

    z(String str, long j, long j2, boolean z) {
        int i = 1;
        this.f = new ak(new gt(), str, j, z);
        this.g = j2;
        if (Runtime.getRuntime().availableProcessors() > 1) {
            i = 2;
        }
        this.h = i;
    }

    public synchronized boolean a() {
        boolean z;
        z = a.ACTIVE.equals(this.e) || a.PAUSED.equals(this.e);
        return z;
    }

    public synchronized boolean b() {
        return a.ACTIVE.equals(this.e);
    }

    public synchronized boolean c() {
        return a.PAUSED.equals(this.e);
    }

    public synchronized List<ae> d() {
        List<ae> arrayList;
        arrayList = new ArrayList();
        synchronized (this.a) {
            arrayList.addAll(this.a.values());
        }
        return arrayList;
    }

    public synchronized void a(ae aeVar) {
        b(aeVar);
    }

    public synchronized void e() {
        if (!b()) {
            gd.a(3, b, "Precaching: Starting AssetCache");
            this.f.a();
            fp.a().b(new hq(this) {
                final /* synthetic */ z a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.o();
                    this.a.m();
                }
            });
            this.e = a.ACTIVE;
        }
    }

    public synchronized void f() {
        if (a()) {
            gd.a(3, b, "Precaching: Stopping AssetCache");
            n();
            this.f.b();
            this.e = a.INIT;
        }
    }

    public synchronized void g() {
        if (a()) {
            if (c()) {
                gd.a(3, b, "Precaching: Resuming AssetCache");
                fp.a().b(new hq(this) {
                    final /* synthetic */ z a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.o();
                        this.a.m();
                    }
                });
                this.e = a.ACTIVE;
            }
        }
    }

    public boolean a(String str, an anVar, long j) {
        if (!a() || TextUtils.isEmpty(str) || anVar == null) {
            return false;
        }
        if (!an.IMAGE.equals(anVar) && !an.VIDEO.equals(anVar)) {
            return false;
        }
        ae d = d(str);
        if (d == null) {
            d = new ae(str, anVar, j);
            synchronized (this.a) {
                this.a.put(d.a(), d);
            }
            c(d);
        } else if (!ah.COMPLETE.equals(d(d))) {
            c(d);
        }
        return true;
    }

    public void a(String str) {
        if (a() && !TextUtils.isEmpty(str)) {
            synchronized (this.a) {
                this.a.remove(str);
            }
            this.f.c(str);
        }
    }

    public void h() {
        if (a()) {
            for (ae a : j()) {
                a(a.a());
            }
            this.f.c();
        }
    }

    public ah b(String str) {
        if (a()) {
            return d(d(str));
        }
        return ah.NONE;
    }

    public b c(String str) {
        if (a() && !TextUtils.isEmpty(str)) {
            return this.f.a(str);
        }
        return null;
    }

    private ae d(String str) {
        if (!a() || TextUtils.isEmpty(str)) {
            return null;
        }
        ae aeVar;
        synchronized (this.a) {
            aeVar = (ae) this.a.get(str);
        }
        if (aeVar != null) {
            if (aeVar.d()) {
                gd.a(3, b, "Precaching: expiring cached asset: " + aeVar.a() + " asset exp: " + aeVar.c() + " device epoch" + System.currentTimeMillis());
                a(aeVar.a());
                aeVar = null;
            } else {
                d(aeVar);
                aeVar.e();
            }
        }
        return aeVar;
    }

    public void i() {
        if (a()) {
            for (ae d : j()) {
                d(d);
            }
        }
    }

    public List<ae> j() {
        List arrayList;
        synchronized (this.a) {
            arrayList = new ArrayList(this.a.values());
        }
        return arrayList;
    }

    public List<ae> k() {
        i();
        return j();
    }

    private void b(ae aeVar) {
        if (aeVar != null && !TextUtils.isEmpty(aeVar.a()) && !this.a.containsKey(aeVar.a())) {
            gd.a(3, b, "Precaching: adding cached asset info from persisted storage: " + aeVar.a() + " asset exp: " + aeVar.c() + " saved time: " + aeVar.f());
            synchronized (this.a) {
                this.a.put(aeVar.a(), aeVar);
            }
        }
    }

    private void c(ae aeVar) {
        if (aeVar != null) {
            ah d = d(aeVar);
            if (!ah.COMPLETE.equals(d)) {
                if (ah.IN_PROGRESS.equals(d) || ah.QUEUED.equals(d)) {
                    synchronized (this.c) {
                        if (!this.c.containsKey(aeVar.a())) {
                            this.c.put(aeVar.a(), aeVar);
                        }
                    }
                } else {
                    gd.a(3, b, "Precaching: Queueing asset:" + aeVar.a());
                    f.a().a("precachingDownloadRequested", 1);
                    a(aeVar, ah.QUEUED);
                    synchronized (this.c) {
                        this.c.put(aeVar.a(), aeVar);
                    }
                }
                fp.a().b(new hq(this) {
                    final /* synthetic */ z a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.m();
                    }
                });
            }
        }
    }

    private ah d(ae aeVar) {
        if (aeVar == null) {
            return ah.NONE;
        }
        if (aeVar.d()) {
            return ah.NONE;
        }
        if (ah.COMPLETE.equals(aeVar.b()) && !this.f.d(aeVar.a())) {
            a(aeVar, ah.EVICTED);
        }
        return aeVar.b();
    }

    private void m() {
        if (b()) {
            gd.a(3, b, "Precaching: Download files");
            synchronized (this.c) {
                Iterator it = this.c.values().iterator();
                while (it.hasNext()) {
                    ae aeVar = (ae) it.next();
                    if (this.f.d(aeVar.a())) {
                        gd.a(3, b, "Precaching: Asset already cached.  Skipping download:" + aeVar.a());
                        it.remove();
                        a(aeVar, ah.COMPLETE);
                    } else if (ah.IN_PROGRESS.equals(d(aeVar))) {
                        continue;
                    } else if (fn.a().b((Object) this) >= ((long) this.h)) {
                        gd.a(3, b, "Precaching: Download limit reached");
                        return;
                    } else {
                        e(aeVar);
                    }
                }
                gd.a(3, b, "Precaching: No more files to download");
            }
        }
    }

    private void e(final ae aeVar) {
        f.a().a("precachingDownloadStarted", 1);
        gd.a(3, b, "Precaching: Submitting for download: " + aeVar.a());
        ai amVar = new am(this.f, aeVar.a());
        amVar.a(aeVar.a());
        amVar.a(40000);
        amVar.a(this.f);
        amVar.a(new com.flurry.sdk.ai.a(this) {
            final /* synthetic */ z b;

            public void a(ai aiVar) {
                synchronized (this.b.d) {
                    this.b.d.remove(aeVar.a());
                }
                this.b.f(aeVar);
                if (aiVar.a()) {
                    long c = aiVar.c();
                    gd.a(3, z.b, "Precaching: Download success: " + aeVar.a() + " size: " + c);
                    aeVar.a(c);
                    this.b.a(aeVar, ah.COMPLETE);
                    f.a().a("precachingDownloadSuccess", 1);
                } else {
                    gd.a(3, z.b, "Precaching: Download error: " + aeVar.a());
                    this.b.a(aeVar, ah.ERROR);
                    f.a().a("precachingDownloadError", 1);
                }
                fp.a().b(new hq(this) {
                    final /* synthetic */ AnonymousClass4 a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.b.m();
                    }
                });
            }
        });
        amVar.d();
        synchronized (this.d) {
            this.d.put(aeVar.a(), amVar);
        }
        a(aeVar, ah.IN_PROGRESS);
    }

    private void f(ae aeVar) {
        if (aeVar != null) {
            synchronized (this.c) {
                this.c.remove(aeVar.a());
            }
        }
    }

    private void n() {
        gd.a(3, b, "Precaching: Cancelling in-progress downloads");
        synchronized (this.d) {
            for (Entry value : this.d.entrySet()) {
                ((ai) value.getValue()).e();
            }
            this.d.clear();
        }
        synchronized (this.c) {
            for (Entry value2 : this.c.entrySet()) {
                ae aeVar = (ae) value2.getValue();
                if (!ah.COMPLETE.equals(d(aeVar))) {
                    gd.a(3, b, "Precaching: Download cancelled: " + aeVar.f());
                    a(aeVar, ah.CANCELLED);
                }
            }
        }
    }

    private void a(ae aeVar, ah ahVar) {
        if (aeVar != null && ahVar != null && !ahVar.equals(aeVar.b())) {
            gd.a(3, b, "Asset status changed for asset:" + aeVar.a() + " from:" + aeVar.b() + " to:" + ahVar);
            aeVar.a(ahVar);
            ad adVar = new ad();
            adVar.a = aeVar.a();
            adVar.b = ahVar;
            adVar.b();
        }
    }

    private void o() {
        for (ae aeVar : j()) {
            if (!ah.COMPLETE.equals(d(aeVar))) {
                gd.a(3, b, "Precaching: expiring cached asset: " + aeVar.a() + " asset exp: " + aeVar.c() + " device epoch: " + System.currentTimeMillis());
                a(aeVar.a());
            }
        }
    }
}
