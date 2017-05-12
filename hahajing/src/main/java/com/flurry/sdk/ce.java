package com.flurry.sdk;

import android.text.TextUtils;
import android.widget.Toast;
import com.flurry.sdk.cd.a;
import com.flurry.sdk.cd.b;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ce extends go<cd> {
    private static final String a = ce.class.getSimpleName();
    private final fw<List<cd>> b = new fw(fp.a().c().getFileStreamPath(".yflurryreporter"), ".yflurryreporter", 2, new ha<List<cd>>(this) {
        final /* synthetic */ ce a;

        {
            this.a = r1;
        }

        public gx<List<cd>> a(int i) {
            if (i > 1) {
                return new gw(new a());
            }
            return new gw(new b());
        }
    });

    protected synchronized void a(List<cd> list) {
        hp.b();
        List list2 = (List) this.b.a();
        if (list2 != null) {
            list.addAll(list2);
        }
    }

    protected synchronized void b(List<cd> list) {
        hp.b();
        this.b.a(new ArrayList(list));
    }

    protected void a(final cd cdVar) {
        gd.a(3, a, "Sending next report for original url: " + cdVar.g() + " to current url:" + cdVar.h());
        hr gkVar = new gk();
        gkVar.a(cdVar.h());
        gkVar.a(100000);
        gkVar.a(gl.a.kGet);
        gkVar.a(false);
        if (cdVar.c()) {
            gkVar.a("Cookie", i.a().h().d());
        }
        gkVar.a(new gk.a<Void, Void>(this) {
            final /* synthetic */ ce b;

            public void a(final gk<Void, Void> gkVar, Void voidR) {
                gd.a(3, ce.a, "AsyncReportInfo request: HTTP status code is:" + gkVar.e());
                int e = gkVar.e();
                if (e >= 200 && e < 300) {
                    gd.a(3, ce.a, "Send report successful to url: " + gkVar.a());
                    this.b.c(cdVar);
                    if (gd.c() <= 3 && gd.d()) {
                        fp.a().a(new Runnable(this) {
                            final /* synthetic */ AnonymousClass2 b;

                            public void run() {
                                Toast.makeText(fp.a().c(), "ADS AR HTTP Response Code: " + gkVar.e() + " for url: " + gkVar.a(), 1).show();
                            }
                        });
                    }
                    this.b.a(cdVar, e);
                } else if (e < 300 || e >= 400) {
                    gd.a(3, ce.a, "Send report failed to url: " + gkVar.a());
                    this.b.d(cdVar);
                    if (cdVar.f() == 0) {
                        this.b.a(cdVar, e);
                    }
                } else {
                    String str = null;
                    List b = gkVar.b(HttpRequest.HEADER_LOCATION);
                    if (b != null && b.size() > 0) {
                        str = cv.b((String) b.get(0), cdVar.h());
                    }
                    if (TextUtils.isEmpty(str)) {
                        gd.a(3, ce.a, "Send report successful to url: " + gkVar.a());
                        this.b.c(cdVar);
                        if (gd.c() <= 3 && gd.d()) {
                            fp.a().a(new Runnable(this) {
                                final /* synthetic */ AnonymousClass2 b;

                                public void run() {
                                    Toast.makeText(fp.a().c(), "ADS AR HTTP Response Code: " + gkVar.e() + " for url: " + gkVar.a(), 1).show();
                                }
                            });
                        }
                        this.b.a(cdVar, e);
                        return;
                    }
                    gd.a(3, ce.a, "Send report redirecting to url: " + str);
                    cdVar.c(str);
                    this.b.a(cdVar);
                }
            }
        });
        fn.a().a((Object) this, gkVar);
    }

    private void a(cd cdVar, int i) {
        if (cdVar != null) {
            Map hashMap = new HashMap();
            hashMap.put("event", cdVar.a());
            hashMap.put("url", cdVar.g());
            hashMap.put("response", i + "");
            i.a().a(cdVar.b(), aw.EV_SEND_URL_STATUS_RESULT, true, hashMap);
        }
    }
}
