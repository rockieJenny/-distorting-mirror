package com.flurry.sdk;

import android.content.Context;
import android.os.SystemClock;

public class fo implements hf {
    private static final String a = fo.class.getSimpleName();
    private long b = 0;
    private long c = 0;
    private long d = -1;
    private long e = 0;
    private long f = 0;

    public void a(Context context) {
        this.b = System.currentTimeMillis();
        this.c = SystemClock.elapsedRealtime();
        fp.a().b(new hq(this) {
            final /* synthetic */ fo a;

            {
                this.a = r1;
            }

            public void safeRun() {
                fh.a().c();
            }
        });
    }

    public void b(Context context) {
        long c = hc.a().c();
        if (c > 0) {
            this.e = (System.currentTimeMillis() - c) + this.e;
        }
    }

    public void c(Context context) {
        this.d = SystemClock.elapsedRealtime() - this.c;
    }

    public void a() {
    }

    public long b() {
        return this.b;
    }

    public long c() {
        return this.c;
    }

    public long d() {
        return this.d;
    }

    public long e() {
        return this.e;
    }

    public synchronized long f() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.c;
        if (elapsedRealtime <= this.f) {
            elapsedRealtime = this.f + 1;
            this.f = elapsedRealtime;
        }
        this.f = elapsedRealtime;
        return this.f;
    }
}
