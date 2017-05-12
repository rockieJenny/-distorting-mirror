package com.jirbo.adcolony;

class v {
    d a;
    boolean b;
    boolean c;
    boolean d = false;
    boolean e = false;
    double f;
    double g;
    double h;
    int i;
    String j = "uuid";

    v(d dVar) {
        this.a = dVar;
    }

    void a() {
    }

    void b() {
        if (this.a.b.b) {
            if (this.d) {
                this.d = false;
                this.a.d.a("install", null);
            }
            if (this.e) {
                this.e = false;
                this.a.d.a("session_start", null);
            }
        }
    }

    void c() {
        l.b.b((Object) "AdColony resuming");
        a.r = true;
        if (this.b) {
            l.d.b((Object) "AdColony.onResume() called multiple times in succession.");
        }
        this.b = true;
        g();
        double c = ab.c();
        if (this.c) {
            if (c - this.g > ((double) this.a.a.d)) {
                a(this.h);
                this.f = c;
                h();
            }
            this.c = false;
            f();
        } else {
            this.f = c;
            h();
        }
        a.h();
    }

    void d() {
        l.b.b((Object) "AdColony suspending");
        a.r = true;
        if (!this.b) {
            l.d.b((Object) "AdColony.onPause() called without initial call to onResume().");
        }
        this.b = false;
        this.c = true;
        this.g = ab.c();
        f();
    }

    void e() {
        l.b.b((Object) "AdColony terminating");
        a.r = true;
        a(this.h);
        this.c = false;
        f();
    }

    void f() {
        g gVar = new g();
        gVar.b("allow_resume", this.c);
        gVar.b("start_time", this.f);
        gVar.b("finish_time", this.g);
        gVar.b("session_time", this.h);
        k.a(new f("session_info.txt"), gVar);
    }

    void g() {
        g b = k.b(new f("session_info.txt"));
        if (b != null) {
            this.c = b.h("allow_resume");
            this.f = b.f("start_time");
            this.g = b.f("finish_time");
            this.h = b.f("session_time");
            return;
        }
        this.d = true;
    }

    void h() {
        this.e = true;
        this.j = ab.b();
        this.h = 0.0d;
        this.i = 0;
        if (a.l != null && a.l.b != null && a.l.b.j != null && a.l.b.j.n != null) {
            for (int i = 0; i < a.l.b.j.n.a(); i++) {
                if (a.l.b.j.n.a(i).k != null) {
                    a.l.b.j.n.a(i).k.d = 0;
                }
            }
        }
    }

    void a(double d) {
        l.a.a("Submitting session duration ").b(d);
        g gVar = new g();
        gVar.b("session_length", (int) d);
        this.a.d.a("session_end", gVar);
    }
}
