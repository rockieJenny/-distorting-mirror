package com.flurry.sdk;

public class hl {
    private static final String a = hl.class.getSimpleName();
    private long b = 1000;
    private boolean c = true;
    private boolean d = false;
    private hq e = new hq(this) {
        final /* synthetic */ hl a;

        {
            this.a = r1;
        }

        public void safeRun() {
            new hj().b();
            if (this.a.c && this.a.d) {
                fp.a().b(this.a.e, this.a.b);
            }
        }
    };

    public void a(long j) {
        this.b = j;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public synchronized void a() {
        if (!this.d) {
            fp.a().b(this.e, this.b);
            this.d = true;
        }
    }

    public synchronized void b() {
        if (this.d) {
            fp.a().c(this.e);
            this.d = false;
        }
    }
}
