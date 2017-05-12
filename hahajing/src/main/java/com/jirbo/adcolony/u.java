package com.jirbo.adcolony;

import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import com.inmobi.monetization.internal.Ad;
import com.jirbo.adcolony.ADCDownload.Listener;
import java.util.ArrayList;

class u implements Listener {
    d a;
    ArrayList<a> b = new ArrayList();
    ArrayList<a> c = new ArrayList();
    int d = 0;
    boolean e = false;

    static class a {
        String a;
        String b;
        String c;
        String d;
        double e;
        int f;
        int g;
        int h;
        boolean i;
        boolean j;
        boolean k;

        a() {
        }
    }

    u(d dVar) {
        this.a = dVar;
    }

    void a(String str, AdColonyAd adColonyAd) {
        if (this.a != null && this.a.b != null && this.a.b.j != null && this.a.b.j.n != null && this.a.b.j.n.a(str) != null) {
            l.a.a("Ad request for zone ").b((Object) str);
            ab a = this.a.b.j.n.a(str);
            if (a != null && a.h != null && a.h.a != null) {
                g gVar = new g();
                if (a.ad == 0) {
                    gVar.b("request_denied", false);
                } else {
                    gVar.b("request_denied", true);
                }
                gVar.b("request_denied_reason", a.ad);
                a("request", a.h.a, gVar, adColonyAd);
                l.a.a("Tracking ad request - URL : ").b(a.h.a);
            }
        }
    }

    void a(String str, g gVar) {
        f fVar = this.a.b.j.k;
        if (fVar != null) {
            a(str, fVar.h.e(str), gVar);
        }
        w wVar = this.a.b.j.l;
        if (wVar != null) {
            a(str, (ArrayList) wVar.d.get(str));
        }
    }

    void b(String str, AdColonyAd adColonyAd) {
        a(str, null, adColonyAd);
    }

    void a(String str, g gVar, AdColonyAd adColonyAd) {
        if (str == null) {
            l.d.b((Object) "No such event type:").b((Object) str);
            return;
        }
        if (str.equals("start") || str.equals("native_start")) {
            v vVar = a.l.e;
            vVar.i++;
        }
        if (gVar == null) {
            gVar = new g();
            gVar.b("replay", adColonyAd.s);
        }
        gVar.b("s_imp_count", a.l.e.i);
        a(str, adColonyAd.i.t.D.e(str), gVar, adColonyAd);
        a(str, (ArrayList) adColonyAd.i.q.C.get(str));
    }

    void a(String str, String str2, g gVar) {
        a(str, str2, gVar, null);
    }

    void a(String str, String str2, g gVar, AdColonyAd adColonyAd) {
        if (str2 != null && !str2.equals("")) {
            if (gVar == null) {
                gVar = new g();
            }
            String b = ab.b();
            if (adColonyAd != null) {
                gVar.b("asi", adColonyAd.l);
            }
            gVar.b(AnalyticsSQLiteHelper.EVENT_LIST_SID, this.a.e.j);
            gVar.b("guid", b);
            gVar.b("guid_key", ab.b(b + "DUBu6wJ27y6xs7VWmNDw67DD"));
            a aVar = new a();
            aVar.a = str;
            aVar.b = str2;
            l.a.b((Object) "EVENT ---------------------------");
            l.a.a("EVENT - TYPE = ").b((Object) str);
            l.a.a("EVENT - URL  = ").b((Object) str2);
            aVar.c = gVar.q();
            if (str.equals("reward_v4vc")) {
                aVar.d = gVar.e("v4vc_name");
                aVar.h = gVar.g("v4vc_amount");
            }
            this.b.add(aVar);
            this.e = true;
            a.r = true;
        }
    }

    void a(String str, ArrayList<String> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                String str2 = (String) arrayList.get(i);
                a aVar = new a();
                aVar.a = str;
                aVar.b = str2;
                aVar.k = true;
                this.b.add(aVar);
            }
            this.e = true;
            a.r = true;
        }
    }

    void a(double d, AdColonyAd adColonyAd) {
        double d2 = adColonyAd.n;
        if (d >= d2) {
            if (d2 < 0.25d && d >= 0.25d) {
                if (AdColony.isZoneV4VC(adColonyAd.g) || !adColonyAd.k.equals(Ad.AD_TYPE_NATIVE)) {
                    b("first_quartile", adColonyAd);
                } else {
                    b("native_first_quartile", adColonyAd);
                }
            }
            if (d2 < 0.5d && d >= 0.5d) {
                if (AdColony.isZoneV4VC(adColonyAd.g) || !adColonyAd.k.equals(Ad.AD_TYPE_NATIVE)) {
                    b("midpoint", adColonyAd);
                } else {
                    b("native_midpoint", adColonyAd);
                }
            }
            if (d2 < 0.75d && d >= 0.75d) {
                if (AdColony.isZoneV4VC(adColonyAd.g) || !adColonyAd.k.equals(Ad.AD_TYPE_NATIVE)) {
                    b("third_quartile", adColonyAd);
                } else {
                    b("native_third_quartile", adColonyAd);
                }
            }
            if (d2 < 1.0d && d >= 1.0d && !adColonyAd.k.equals(Ad.AD_TYPE_NATIVE)) {
                l.a.a("Tracking ad event - complete");
                g gVar = new g();
                if (adColonyAd.r) {
                    gVar.b("ad_slot", adColonyAd.h.k.d);
                } else {
                    gVar.b("ad_slot", adColonyAd.h.k.d);
                }
                gVar.b("replay", adColonyAd.s);
                a("complete", gVar, adColonyAd);
            }
            adColonyAd.n = d;
        }
    }

    void a() {
        b();
        this.d = 0;
    }

    void b() {
        a.r = true;
        c c = k.c(new f("tracking_info.txt"));
        if (c != null) {
            this.b.clear();
            for (int i = 0; i < c.i(); i++) {
                g b = c.b(i);
                a aVar = new a();
                aVar.a = b.e(AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
                aVar.b = b.e("url");
                aVar.c = b.a("payload", null);
                aVar.f = b.g("attempts");
                aVar.k = b.h("third_party");
                if (aVar.a.equals("v4vc_callback") || aVar.a.equals("reward_v4vc")) {
                    aVar.d = b.e("v4vc_name");
                    aVar.h = b.g("v4vc_amount");
                }
                this.b.add(aVar);
            }
        }
        l.a.a("Loaded ").a(this.b.size()).b((Object) " events");
    }

    void c() {
        this.c.clear();
        this.c.addAll(this.b);
        this.b.clear();
        c cVar = new c();
        for (int i = 0; i < this.c.size(); i++) {
            a aVar = (a) this.c.get(i);
            if (!aVar.i) {
                this.b.add(aVar);
                i gVar = new g();
                gVar.b(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, aVar.a);
                gVar.b("url", aVar.b);
                if (aVar.c != null) {
                    gVar.b("payload", aVar.c);
                }
                gVar.b("attempts", aVar.f);
                if (aVar.d != null) {
                    gVar.b("v4vc_name", aVar.d);
                    gVar.b("v4vc_amount", aVar.h);
                }
                if (aVar.k) {
                    gVar.b("third_party", true);
                }
                cVar.a(gVar);
            }
        }
        this.c.clear();
        l.a.a("Saving tracking_info (").a(this.b.size()).b((Object) " events)");
        k.a(new f("tracking_info.txt"), cVar);
    }

    void d() {
        if (this.e) {
            this.e = false;
            c();
        }
        e();
    }

    void e() {
        if (this.b.size() != 0) {
            while (this.b.size() > 1000) {
                this.b.remove(this.b.size() - 1);
            }
            if (q.c()) {
                double c = ab.c();
                for (int i = 0; i < this.b.size(); i++) {
                    a aVar = (a) this.b.get(i);
                    if (aVar.e < c && !aVar.j) {
                        if (this.d != 5) {
                            this.d++;
                            aVar.j = true;
                            if (aVar.a.equals("v4vc_callback")) {
                                a.Z.add(aVar.b);
                            }
                            ADCDownload a = new ADCDownload(this.a, aVar.b, this).a(aVar);
                            if (aVar.k) {
                                a.h = true;
                            }
                            if (aVar.c != null) {
                                a.a("application/json", aVar.c);
                            }
                            l.b.a("Submitting '").a(aVar.a).b((Object) "' event.");
                            a.b();
                            a.r = true;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public void on_download_finished(ADCDownload download) {
        int i = 10000;
        a.r = true;
        this.e = true;
        this.d--;
        a aVar = (a) download.e;
        l.a.a("on_download_finished - event.type = ").b(aVar.a);
        aVar.j = false;
        boolean z = download.i;
        if (z && aVar.c != null) {
            g b = k.b(download.n);
            if (b != null) {
                z = b.e("status").equals("success");
                if (z && aVar.a.equals("reward_v4vc")) {
                    if (b.h("v4vc_status")) {
                        String e = b.e("v4vc_callback");
                        if (e.length() > 0) {
                            a aVar2 = new a();
                            aVar2.a = "v4vc_callback";
                            aVar2.b = e;
                            aVar2.d = aVar.d;
                            aVar2.h = aVar.h;
                            this.b.add(aVar2);
                        } else {
                            if (a.L != null) {
                                a.L.o = true;
                            }
                            l.a.b((Object) "Client-side V4VC success");
                        }
                    } else {
                        l.a.b((Object) "Client-side V4VC failure");
                    }
                }
            } else {
                z = false;
            }
        }
        if (z && aVar.a.equals("v4vc_callback")) {
            l.a.b((Object) "v4vc_callback response:").b(download.n);
            if (download.n.indexOf("vc_success") != -1) {
                if (a.L != null) {
                    a.L.o = true;
                }
                l.a.b((Object) "v4vc_callback success");
                this.a.a(true, aVar.d, aVar.h);
            } else if (download.n.indexOf("vc_decline") == -1 && download.n.indexOf("vc_noreward") == -1) {
                l.c.a("Server-side V4VC failure: ").b(download.c);
                z = false;
            } else {
                l.c.a("Server-side V4VC failure: ").b(download.c);
                l.a.b((Object) "v4vc_callback declined");
                this.a.a(false, "", 0);
            }
        }
        if (z) {
            l.a.a("Event submission SUCCESS for type: ").b(aVar.a);
            aVar.i = true;
        } else {
            l.a.a("Event submission FAILED for type: ").a(aVar.a).a(" on try ").b(aVar.f + 1);
            aVar.f++;
            if (aVar.f >= 24) {
                l.d.b((Object) "Discarding event after 24 attempts to report.");
                aVar.i = true;
                if (aVar.a.equals("v4vc_callback")) {
                    this.a.a(false, "", 0);
                }
            } else {
                int i2 = 20;
                if (aVar.g > 0) {
                    i2 = aVar.g * 3;
                }
                if (i2 <= 10000) {
                    i = i2;
                }
                l.a.a("Retrying in ").a(i).a(" seconds (attempt ").a(aVar.f).b((Object) ")");
                aVar.g = i;
                aVar.e = ab.c() + ((double) i);
            }
        }
        if (!this.a.e.b) {
            c();
        }
    }
}
