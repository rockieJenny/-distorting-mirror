package com.jirbo.adcolony;

import android.os.Handler;
import android.os.Looper;
import com.flurry.android.AdCreative;
import com.jirbo.adcolony.ADCDownload.Listener;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;

class b implements Listener {
    d a;
    boolean b;
    boolean c;
    boolean d;
    boolean e = true;
    boolean f = false;
    double g;
    Handler h;
    Runnable i;
    e j = new e();

    b(d dVar) {
        this.a = dVar;
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        this.h = new Handler();
        this.i = new Runnable(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.e = true;
                if (this.a.f) {
                    this.a.e();
                }
            }
        };
    }

    void a() {
    }

    void b() {
        l.b.b((Object) "Attempting to load backup manifest from file.");
        f fVar = new f("manifest.txt");
        g b = k.b(fVar);
        if (b == null) {
            return;
        }
        if (a(b)) {
            this.b = true;
            this.j.a();
            return;
        }
        l.b.b((Object) "Invalid manifest loaded.");
        fVar.c();
        this.b = false;
    }

    String c() {
        if (!this.b) {
            return null;
        }
        String str = null;
        for (int i = 0; i < this.j.n.a(); i++) {
            ab a = this.j.n.a(i);
            if (a.e()) {
                str = a.a;
                if (a.a()) {
                    return a.a;
                }
            }
        }
        return str;
    }

    String d() {
        if (!this.b) {
            return null;
        }
        String str = null;
        for (int i = 0; i < this.j.n.a(); i++) {
            ab a = this.j.n.a(i);
            if (a.g()) {
                str = a.a;
                if (a.a()) {
                    return a.a;
                }
            }
        }
        return str;
    }

    boolean a(String str) {
        return a(str, true);
    }

    boolean a(String str, boolean z) {
        for (int i = 0; i < this.j.n.a(); i++) {
            ab a = this.j.n.a(i);
            if (a.c(z) && a.a.equals(str)) {
                return true;
            }
        }
        return false;
    }

    boolean b(String str) {
        return b(str, false);
    }

    boolean b(String str, boolean z) {
        if (z) {
            return c(str, z);
        }
        if (!this.b) {
            return l.c.b("Ads are not ready to be played, as they are still downloading.");
        }
        if (z) {
            return this.j.a(str, true, false);
        }
        return this.j.a(str, false, true);
    }

    boolean c(String str, boolean z) {
        if (!this.b) {
            return false;
        }
        if (z) {
            return this.j.a(str, true, false);
        }
        return this.j.a(str, false, true);
    }

    void e() {
        if (this.e || a.z) {
            this.e = false;
            this.c = true;
            this.f = false;
            this.h.postDelayed(this.i, 60000);
            return;
        }
        this.f = true;
    }

    void f() {
        if (ab.c() >= this.g) {
            this.c = true;
        }
        if (this.c) {
            this.c = false;
            if (g.c() >= 32) {
                this.g = ab.c() + 600.0d;
                g();
            }
        }
        if (q.c()) {
            if (!a.C) {
                a.h();
            }
            a.C = true;
            return;
        }
        if (a.C) {
            a.h();
        }
        a.C = false;
    }

    void g() {
        boolean z = true;
        a.r = true;
        l.b.b((Object) "Refreshing manifest");
        if (q.c()) {
            Object zVar = new z();
            c cVar = this.a.a;
            zVar.a(c.c);
            zVar.a("?app_id=");
            zVar.a(this.a.a.j);
            zVar.a("&zones=");
            if (this.a.a.k != null) {
                for (String str : this.a.a.k) {
                    if (z) {
                        z = false;
                    } else {
                        zVar.a(",");
                    }
                    zVar.a(str);
                }
            }
            String str2 = a.l.e.j == null ? "" : a.l.e.j;
            String str3 = "" + a.l.e.i;
            zVar.a(this.a.a.h);
            zVar.a("&carrier=");
            zVar.a(q.a(this.a.a.w));
            zVar.a("&network_type=");
            if (q.a()) {
                zVar.a("wifi");
            } else if (q.b()) {
                zVar.a("cell");
            } else {
                zVar.a(AdCreative.kFixNone);
            }
            zVar.a("&custom_id=");
            zVar.a(q.a(this.a.a.x));
            zVar.a("&sid=");
            zVar.a(str2);
            zVar.a("&s_imp_count=");
            zVar.a(str3);
            l.b.b((Object) "Downloading ad manifest from");
            l.b.b(zVar);
            new ADCDownload(this.a, zVar.toString(), this).b();
            return;
        }
        l.b.b((Object) "Not connected to network.");
        l.a.a("attempted_load:").a(this.d).a(" is_configured:").b(this.b);
        if (!this.d) {
            this.d = true;
            if (!this.b) {
                b();
            }
        }
    }

    public void on_download_finished(ADCDownload download) {
        a.r = true;
        if (download.i) {
            l.c.b((Object) "Finished downloading:");
            l.c.b(download.c);
            g b = k.b(download.n);
            if (b == null) {
                l.a.b((Object) "Invalid JSON in manifest.  Raw data:");
                l.a.b(download.n);
                return;
            } else if (a(b)) {
                l.b.b((Object) "Ad manifest updated.");
                new f("manifest.txt").a(download.n);
                this.b = true;
                this.j.a();
                if (this.j.i == null || this.j.i.equals("")) {
                    this.j.i = "all";
                }
                if (this.j.j == null || this.j.j.equals("")) {
                    this.j.j = "all";
                }
                a.h();
                return;
            } else {
                l.b.b((Object) "Invalid manifest.");
                return;
            }
        }
        l.c.b((Object) "Error downloading:");
        l.c.b(download.c);
    }

    boolean a(g gVar) {
        if (gVar == null || !gVar.e("status").equals("success") || !this.j.a(gVar.b(SettingsJsonConstants.APP_KEY))) {
            return false;
        }
        l.a.b((Object) "Finished parsing manifest");
        if (this.j.h.equalsIgnoreCase(AdCreative.kFixNone)) {
            a.a(2);
        } else {
            l.c.b((Object) "Enabling debug logging.");
            a.a(1);
        }
        return true;
    }
}
