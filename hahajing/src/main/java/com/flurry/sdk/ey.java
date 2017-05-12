package com.flurry.sdk;

import android.widget.Toast;
import com.flurry.sdk.hh.a;
import io.fabric.sdk.android.services.network.HttpRequest;

public class ey extends gp implements a {
    static String a = "http://data.flurry.com/aap.do";
    static String b = "https://data.flurry.com/aap.do";
    private static final String g = ey.class.getSimpleName();
    private String h;
    private boolean i;

    public ey() {
        this(null);
    }

    public ey(gp.a aVar) {
        super("Analytics", ey.class.getSimpleName());
        this.f = "AnalyticsData_";
        h();
        a(aVar);
    }

    private void h() {
        hh a = hg.a();
        this.i = ((Boolean) a.a("UseHttps")).booleanValue();
        a.a("UseHttps", (a) this);
        gd.a(4, g, "initSettings, UseHttps = " + this.i);
        String str = (String) a.a("ReportUrl");
        a.a("ReportUrl", (a) this);
        b(str);
        gd.a(4, g, "initSettings, ReportUrl = " + str);
    }

    public void a() {
        hg.a().b("UseHttps", (a) this);
        hg.a().b("ReportUrl", (a) this);
    }

    public void a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -239660092:
                if (str.equals("UseHttps")) {
                    obj2 = null;
                    break;
                }
                break;
            case 1650629499:
                if (str.equals("ReportUrl")) {
                    obj2 = 1;
                    break;
                }
                break;
        }
        switch (obj2) {
            case null:
                this.i = ((Boolean) obj).booleanValue();
                gd.a(4, g, "onSettingUpdate, UseHttps = " + this.i);
                return;
            case 1:
                String str2 = (String) obj;
                b(str2);
                gd.a(4, g, "onSettingUpdate, ReportUrl = " + str2);
                return;
            default:
                gd.a(6, g, "onSettingUpdate internal error!");
                return;
        }
    }

    private void b(String str) {
        if (!(str == null || str.endsWith(".do"))) {
            gd.a(5, g, "overriding analytics agent report URL without an endpoint, are you sure?");
        }
        this.h = str;
    }

    String b() {
        if (this.h != null) {
            return this.h;
        }
        if (this.i) {
            return b;
        }
        return a;
    }

    protected void a(byte[] bArr, final String str, final String str2) {
        String b = b();
        gd.a(4, g, "FlurryDataSender: start upload data " + bArr + " with id = " + str + " to " + b);
        hr gkVar = new gk();
        gkVar.a(b);
        gkVar.a(100000);
        gkVar.a(gl.a.kPost);
        gkVar.a(HttpRequest.HEADER_CONTENT_TYPE, "application/octet-stream");
        gkVar.a(new gt());
        gkVar.a((Object) bArr);
        gkVar.a(new gk.a<byte[], Void>(this) {
            final /* synthetic */ ey c;

            public void a(gk<byte[], Void> gkVar, Void voidR) {
                final int e = gkVar.e();
                if (e > 0) {
                    gd.e(ey.g, "Analytics report sent.");
                    gd.a(3, ey.g, "FlurryDataSender: report " + str + " sent. HTTP response: " + e);
                    if (gd.c() <= 3 && gd.d()) {
                        fp.a().a(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 b;

                            public void run() {
                                Toast.makeText(fp.a().c(), "SD HTTP Response Code: " + e, 0).show();
                            }
                        });
                    }
                    this.c.a(str, str2, e);
                    this.c.e();
                    return;
                }
                this.c.b(str, str2);
            }
        });
        fn.a().a((Object) this, gkVar);
    }

    protected void a(String str, String str2, final int i) {
        a(new hq(this) {
            final /* synthetic */ ey b;

            public void safeRun() {
                if (i == 200) {
                    eq.a().d();
                }
            }
        });
        super.a(str, str2, i);
    }
}
