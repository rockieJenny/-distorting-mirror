package com.flurry.sdk;

import android.os.Build;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.flurry.sdk.gl.a;
import java.net.HttpCookie;
import java.util.List;

public class n {
    private static final String a = n.class.getSimpleName();
    private long b = 10000;
    private String c;
    private final Runnable d = new hq(this) {
        final /* synthetic */ n a;

        {
            this.a = r1;
        }

        public void safeRun() {
            this.a.g();
        }
    };
    private final fy<ff> e = new fy<ff>(this) {
        final /* synthetic */ n a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ff) fxVar);
        }

        public void a(ff ffVar) {
            this.a.g();
        }
    };
    private final fy<fi> f = new fy<fi>(this) {
        final /* synthetic */ n a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((fi) fxVar);
        }

        public void a(fi fiVar) {
            if (fiVar.a) {
                this.a.g();
            }
        }
    };

    static /* synthetic */ long a(n nVar, int i) {
        long j = nVar.b << i;
        nVar.b = j;
        return j;
    }

    public n() {
        fz.a().a("com.flurry.android.sdk.IdProviderFinishedEvent", this.e);
        fz.a().a("com.flurry.android.sdk.NetworkStateEvent", this.f);
        g();
    }

    public void a() {
        fp.a().c(this.d);
        h();
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return fe.a().e() ? "0" : "1";
    }

    public String d() {
        StringBuilder stringBuilder = new StringBuilder();
        HttpCookie httpCookie = new HttpCookie("B", b());
        httpCookie.setDomain(".yahoo.com");
        stringBuilder.append(httpCookie.toString());
        if (!fe.a().e()) {
            stringBuilder.append(";");
            HttpCookie httpCookie2 = new HttpCookie("AO", c());
            httpCookie.setDomain(".yahoo.com");
            stringBuilder.append(httpCookie2.toString());
        }
        return stringBuilder.toString();
    }

    private void g() {
        if (TextUtils.isEmpty(this.c) && fe.a().c()) {
            String d = fe.a().d();
            String e = e();
            StringBuilder stringBuilder = new StringBuilder("select bid from data.utilities where _di='");
            if (TextUtils.isEmpty(d)) {
                stringBuilder.append(hp.a(hp.f(e))).append("'");
            } else {
                e = hp.a(hp.f(d));
                stringBuilder.append(e).append("' and _diaid='").append(e).append("' and _diaidu='").append(d).append("'");
            }
            gj gjVar = new gj();
            gjVar.a("q", stringBuilder.toString());
            fn.a().a((Object) this);
            hr gkVar = new gk();
            gkVar.a("https://analytics.query.yahoo.com/v1/public/yql?" + gjVar.b());
            gkVar.a(0);
            gkVar.a(a.kGet);
            gkVar.a(new gk.a<Void, Void>(this) {
                final /* synthetic */ n a;

                {
                    this.a = r1;
                }

                public void a(gk<Void, Void> gkVar, Void voidR) {
                    gd.a(3, n.a, "BCookie request: HTTP status code is:" + gkVar.e());
                    if (gkVar.c()) {
                        List<String> b = gkVar.b("Set-Cookie");
                        if (b != null) {
                            for (String parse : b) {
                                List<HttpCookie> parse2 = HttpCookie.parse(parse);
                                if (parse2 != null) {
                                    for (HttpCookie httpCookie : parse2) {
                                        if (HttpCookie.domainMatches(".yahoo.com", httpCookie.getDomain()) && "B".equalsIgnoreCase(httpCookie.getName())) {
                                            gd.a(3, n.a, "Found BCookie");
                                            this.a.c = httpCookie.getValue();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (TextUtils.isEmpty(this.a.c)) {
                        n.a(this.a, 1);
                        gd.a(3, n.a, "BCookie request failed, backing off: " + this.a.b + "ms");
                        fp.a().b(this.a.d, this.a.b);
                        return;
                    }
                    this.a.h();
                }
            });
            fn.a().a((Object) this, gkVar);
        }
    }

    public static String e() {
        String str = Build.SERIAL;
        try {
            if (TextUtils.isEmpty(str)) {
                str = Secure.getString(fp.a().c().getContentResolver(), "android_id");
            }
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(str)) {
            return fe.a().f();
        }
        return str;
    }

    private void h() {
        fz.a().b("com.flurry.android.sdk.NetworkStateEvent", this.f);
        fz.a().b("com.flurry.android.sdk.IdProviderFinishedEvent", this.e);
    }
}
