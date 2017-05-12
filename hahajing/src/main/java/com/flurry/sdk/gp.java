package com.flurry.sdk;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class gp {
    private fy<fi> a = new fy<fi>(this) {
        final /* synthetic */ gp a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((fi) fxVar);
        }

        public void a(fi fiVar) {
            gd.a(4, this.a.c, "onNetworkStateChanged : isNetworkEnable = " + fiVar.a);
            if (fiVar.a) {
                this.a.e();
            }
        }
    };
    protected final String c;
    Set<String> d = new HashSet();
    gr e;
    protected String f = "defaultDataKey_";

    public interface a {
        void a();
    }

    protected abstract void a(byte[] bArr, String str, String str2);

    public gp(String str, String str2) {
        this.c = str2;
        fz.a().a("com.flurry.android.sdk.NetworkStateEvent", this.a);
        a(str);
    }

    protected void a(hq hqVar) {
        fp.a().b(hqVar);
    }

    protected void a(final String str) {
        a(new hq(this) {
            final /* synthetic */ gp b;

            public void safeRun() {
                this.b.e = new gr(str);
            }
        });
    }

    public void b(byte[] bArr, String str, String str2) {
        a(bArr, str, str2, null);
    }

    public void a(byte[] bArr, String str, String str2, a aVar) {
        if (bArr == null || bArr.length == 0) {
            gd.a(6, this.c, "Report that has to be sent is EMPTY or NULL");
            return;
        }
        c(bArr, str, str2);
        a(aVar);
    }

    public int d() {
        return this.d.size();
    }

    protected void c(final byte[] bArr, final String str, final String str2) {
        a(new hq(this) {
            final /* synthetic */ gp d;

            public void safeRun() {
                this.d.d(bArr, str, str2);
            }
        });
    }

    protected void e() {
        a(null);
    }

    protected void a(final a aVar) {
        a(new hq(this) {
            final /* synthetic */ gp b;

            public void safeRun() {
                this.b.g();
                if (aVar != null) {
                    aVar.a();
                }
            }
        });
    }

    protected boolean f() {
        return d() <= 5;
    }

    public String a(String str, String str2) {
        return this.f + str + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + str2;
    }

    protected void d(byte[] bArr, String str, String str2) {
        String a = a(str, str2);
        gq gqVar = new gq(bArr);
        String a2 = gqVar.a();
        new fw(fp.a().c().getFileStreamPath(gq.a(a2)), ".yflurrydatasenderblock.", 1, new ha<gq>(this) {
            final /* synthetic */ gp a;

            {
                this.a = r1;
            }

            public gx<gq> a(int i) {
                return new com.flurry.sdk.gq.a();
            }
        }).a(gqVar);
        gd.a(5, this.c, "Saving Block File " + a2 + " at " + fp.a().c().getFileStreamPath(gq.a(a2)));
        this.e.a(gqVar, a);
    }

    protected void g() {
        if (fj.a().c()) {
            List<String> a = this.e.a();
            if (a == null || a.isEmpty()) {
                gd.a(4, this.c, "No more reports to send.");
                return;
            }
            for (String str : a) {
                if (f()) {
                    List c = this.e.c(str);
                    gd.a(4, this.c, "Number of not sent blocks = " + c.size());
                    for (int i = 0; i < c.size(); i++) {
                        String str2 = (String) c.get(i);
                        if (!this.d.contains(str2)) {
                            if (!f()) {
                                break;
                            }
                            gq gqVar = (gq) new fw(fp.a().c().getFileStreamPath(gq.a(str2)), ".yflurrydatasenderblock.", 1, new ha<gq>(this) {
                                final /* synthetic */ gp a;

                                {
                                    this.a = r1;
                                }

                                public gx<gq> a(int i) {
                                    return new com.flurry.sdk.gq.a();
                                }
                            }).a();
                            if (gqVar == null) {
                                gd.a(6, this.c, "Internal ERROR! Cannot read!");
                                this.e.a(str2, str);
                            } else {
                                byte[] b = gqVar.b();
                                if (b == null || b.length == 0) {
                                    gd.a(6, this.c, "Internal ERROR! Report is empty!");
                                    this.e.a(str2, str);
                                } else {
                                    gd.a(5, this.c, "Reading block info " + str2);
                                    this.d.add(str2);
                                    a(b, str2, str);
                                }
                            }
                        }
                    }
                } else {
                    return;
                }
            }
            return;
        }
        gd.a(5, this.c, "Reports were not sent! No Internet connection!");
    }

    protected void a(final String str, final String str2, int i) {
        a(new hq(this) {
            final /* synthetic */ gp c;

            public void safeRun() {
                if (!this.c.e.a(str, str2)) {
                    gd.a(6, this.c.c, "Internal error. Block wasn't deleted with id = " + str);
                }
                if (!this.c.d.remove(str)) {
                    gd.a(6, this.c.c, "Internal error. Block with id = " + str + " was not in progress state");
                }
            }
        });
    }

    protected void b(final String str, String str2) {
        a(new hq(this) {
            final /* synthetic */ gp b;

            public void safeRun() {
                if (!this.b.d.remove(str)) {
                    gd.a(6, this.b.c, "Internal error. Block with id = " + str + " was not in progress state");
                }
            }
        });
    }

    protected void c(String str, String str2) {
        if (!this.e.a(str, str2)) {
            gd.a(6, this.c, "Internal error. Block wasn't deleted with id = " + str);
        }
        if (!this.d.remove(str)) {
            gd.a(6, this.c, "Internal error. Block with id = " + str + " was not in progress state");
        }
    }
}
