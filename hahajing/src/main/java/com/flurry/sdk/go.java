package com.flurry.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class go<ReportInfo extends gn> {
    private static final String a = go.class.getSimpleName();
    private final int b = Integer.MAX_VALUE;
    private final List<ReportInfo> c = new ArrayList();
    private boolean d;
    private int e;
    private long f;
    private final Runnable g = new hq(this) {
        final /* synthetic */ go a;

        {
            this.a = r1;
        }

        public void safeRun() {
            this.a.a();
        }
    };
    private final fy<fi> h = new fy<fi>(this) {
        final /* synthetic */ go a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((fi) fxVar);
        }

        public void a(fi fiVar) {
            if (fiVar.a) {
                this.a.a();
            }
        }
    };

    protected abstract void a(ReportInfo reportInfo);

    protected abstract void a(List<ReportInfo> list);

    protected abstract void b(List<ReportInfo> list);

    public go() {
        fz.a().a("com.flurry.android.sdk.NetworkStateEvent", this.h);
        this.f = 10000;
        this.e = -1;
        fp.a().b(new hq(this) {
            final /* synthetic */ go a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.a(this.a.c);
                this.a.a();
            }
        });
    }

    public void b() {
        fp.a().c(this.g);
        h();
    }

    public void c() {
        this.d = true;
    }

    public void d() {
        this.d = false;
        fp.a().b(new hq(this) {
            final /* synthetic */ go a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.a();
            }
        });
    }

    public synchronized void b(ReportInfo reportInfo) {
        if (reportInfo != null) {
            this.c.add(reportInfo);
            fp.a().b(new hq(this) {
                final /* synthetic */ go a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.a();
                }
            });
        }
    }

    protected synchronized void c(ReportInfo reportInfo) {
        reportInfo.a(true);
        fp.a().b(new hq(this) {
            final /* synthetic */ go a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.e();
            }
        });
    }

    protected synchronized void d(ReportInfo reportInfo) {
        reportInfo.i();
        fp.a().b(new hq(this) {
            final /* synthetic */ go a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.e();
            }
        });
    }

    private synchronized void a() {
        if (!this.d) {
            if (this.e >= 0) {
                gd.a(3, a, "Transmit is in progress");
            } else {
                g();
                if (this.c.isEmpty()) {
                    this.f = 10000;
                    this.e = -1;
                } else {
                    this.e = 0;
                    fp.a().b(new hq(this) {
                        final /* synthetic */ go a;

                        {
                            this.a = r1;
                        }

                        public void safeRun() {
                            this.a.e();
                        }
                    });
                }
            }
        }
    }

    private synchronized void e() {
        gn gnVar;
        hp.b();
        if (fj.a().c()) {
            while (this.e < this.c.size()) {
                List list = this.c;
                int i = this.e;
                this.e = i + 1;
                gnVar = (gn) list.get(i);
                if (!gnVar.e()) {
                    break;
                }
            }
            gnVar = null;
        } else {
            gd.a(3, a, "Network is not available, aborting transmission");
            gnVar = null;
        }
        if (gnVar == null) {
            f();
        } else {
            a(gnVar);
        }
    }

    private synchronized void f() {
        g();
        b(this.c);
        if (this.d) {
            gd.a(3, a, "Reporter paused");
            this.f = 10000;
        } else if (this.c.isEmpty()) {
            gd.a(3, a, "All reports sent successfully");
            this.f = 10000;
        } else {
            this.f <<= 1;
            gd.a(3, a, "One or more reports failed to send, backing off: " + this.f + "ms");
            fp.a().b(this.g, this.f);
        }
        this.e = -1;
    }

    private synchronized void g() {
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            gn gnVar = (gn) it.next();
            if (gnVar.e() || gnVar.f() >= Integer.MAX_VALUE || System.currentTimeMillis() > gnVar.d()) {
                it.remove();
            }
        }
    }

    private void h() {
        fz.a().b("com.flurry.android.sdk.NetworkStateEvent", this.h);
    }
}
