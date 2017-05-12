package com.jirbo.adcolony;

import android.os.Build.VERSION;
import com.givewaygames.ads.BuildConfig;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.PlusShare;
import io.fabric.sdk.android.services.common.AbstractSpiCall;

class c {
    static String c = "https://androidads21.adcolony.com/configure";
    String A;
    String B;
    String C;
    String D;
    String E;
    String F;
    String G;
    String H;
    String I;
    String J;
    String K;
    String L;
    String M;
    boolean N;
    d a;
    String b = "2.2.0";
    int d = 300;
    int e = 0;
    boolean f = false;
    boolean g = false;
    String h;
    g i = new g();
    String j;
    String[] k;
    ab l;
    a m;
    double n = 0.0d;
    String o = AbstractSpiCall.ANDROID_CLIENT_TYPE;
    String p = "android_native";
    String q = BuildConfig.VERSION_NAME;
    String r = com.givewaygames.goofyglass.BuildConfig.FLAVOR;
    boolean s = false;
    String t;
    String u;
    String v;
    String w;
    String x = "";
    String y;
    String z;

    c(d dVar) {
        this.a = dVar;
    }

    void a(String str) {
        if (str == null) {
            str = "";
        }
        for (String split : r8.split(",")) {
            String split2;
            String[] split3 = split2.split(":");
            if (split3.length == 2) {
                String str2 = split3[0];
                split2 = split3[1];
                if (str2.equals("version")) {
                    this.q = split2;
                } else if (str2.equals("store")) {
                    if (split2.toLowerCase().equals(com.givewaygames.goofyglass.BuildConfig.FLAVOR) || split2.toLowerCase().equals("amazon")) {
                        this.r = split2;
                    } else {
                        throw new AdColonyException("Origin store in client options must be set to either 'google' or 'amazon'");
                    }
                } else if (str2.equals("skippable")) {
                    this.s = false;
                }
            } else if (split3[0].equals("skippable")) {
                this.s = false;
            }
        }
    }

    void a() {
        String a;
        while (!AdColony.b && this.e < 60) {
            try {
                this.e++;
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
        this.e = 0;
        this.a.g.a();
        this.K = a(g.a, "");
        this.N = g.b;
        this.t = a(g.a(), "");
        if (this.K.equals("")) {
            a = a(ab.b(this.t), "");
        } else {
            a = "";
        }
        this.u = a;
        this.w = a(g.b(), "");
        if (this.y == null) {
            this.y = a(g.e(), "");
        }
        this.z = a(g.l(), "");
        this.A = a(g.m(), "");
        this.E = a(g.j(), "en");
        this.F = a(g.n(), "");
        this.G = a(g.o(), "");
        this.J = a("" + VERSION.SDK_INT, "");
        this.C = a(g.h(), "");
        this.D = "";
        this.H = a("" + g.c(), "");
        this.I = a("" + g.d(), "");
        a.W = this.G;
        a.X = this.b;
        if (a.m) {
            this.B = "tablet";
        } else {
            this.B = "phone";
        }
        this.v = "";
        if (ab.a(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE) || ab.a("com.android.market")) {
            this.v = com.givewaygames.goofyglass.BuildConfig.FLAVOR;
        }
        if (ab.a("com.amazon.venezia")) {
            if (this.v.length() > 0) {
                this.v += ",";
            }
            this.v += "amazon";
        }
        if (l.b.f) {
            l.b.a("sdk_version:").b(this.b);
            l.b.a("ad_manifest_url:").b(c);
            l.b.a("app_id:").b(this.j);
            l.b.a("zone_ids:").b(this.k);
            l.b.a("os_name:").b(this.o);
            l.b.a("sdk_type:").b(this.p);
            l.b.a("app_version:").b(this.q);
            l.b.a("origin_store:").b(this.r);
            l.b.a("skippable:").b(this.s);
            l.b.a("android_id:").b(this.t);
            l.b.a("android_id_sha1:").b(this.u);
            l.b.a("available_stores:").b(this.v);
            l.b.a("carrier_name:").b(this.w);
            l.b.a("custom_id:").b(this.x);
            l.b.a("device_id:").b(this.y);
            l.b.a("device_manufacturer:").b(this.z);
            l.b.a("device_model:").b(this.A);
            l.b.a("device_type:").b(this.B);
            l.b.a("imei:").b(this.C);
            l.b.a("imei_sha1:").b(this.D);
            l.b.a("language:").b(this.E);
            l.b.a("open_udid:").b(this.F);
            l.b.a("os_version:").b(this.G);
        }
        z zVar = new z();
        zVar.a("&os_name=");
        zVar.a(q.a(this.o));
        zVar.a("&os_version=");
        zVar.a(q.a(this.G));
        zVar.a("&device_api=");
        zVar.a(q.a(this.J));
        zVar.a("&app_version=");
        zVar.a(q.a(this.q));
        zVar.a("&android_id_sha1=");
        zVar.a(q.a(this.u));
        zVar.a("&device_id=");
        zVar.a(q.a(this.y));
        zVar.a("&open_udid=");
        zVar.a(q.a(this.F));
        zVar.a("&device_type=");
        zVar.a(q.a(this.B));
        zVar.a("&ln=");
        zVar.a(q.a(this.E));
        zVar.a("&device_brand=");
        zVar.a(q.a(this.z));
        zVar.a("&device_model=");
        zVar.a(q.a(this.A));
        zVar.a("&screen_width=");
        zVar.a(q.a("" + g.f()));
        zVar.a("&screen_height=");
        zVar.a(q.a("" + g.g()));
        zVar.a("&sdk_type=");
        zVar.a(q.a(this.p));
        zVar.a("&sdk_version=");
        zVar.a(q.a(this.b));
        zVar.a("&origin_store=");
        zVar.a(q.a(this.r));
        zVar.a("&available_stores=");
        zVar.a(q.a(this.v));
        zVar.a("&imei_sha1=");
        zVar.a(q.a(this.D));
        zVar.a("&memory_class=");
        zVar.a(q.a(this.H));
        zVar.a("&memory_used_mb=");
        zVar.a(q.a(this.I));
        zVar.a("&advertiser_id=");
        zVar.a(q.a(this.K));
        zVar.a("&limit_tracking=");
        zVar.a(this.N);
        this.h = zVar.toString();
        this.a.f.a();
        this.a.c.a();
        this.a.d.a();
        this.a.b.a();
        this.a.e.a();
        this.a.h.a();
        this.g = true;
        this.a.b.e();
        if (this.a.b.j.i == null || this.a.b.j.i.equals("")) {
            this.a.b.j.i = "all";
        }
        if (this.a.b.j.j == null || this.a.b.j.j.equals("")) {
            this.a.b.j.j = "all";
        }
    }

    String a(String str, String str2) {
        return str != null ? str : str2;
    }

    void b(String str) {
        a(str, null);
    }

    void a(String str, a aVar) {
        this.l = this.a.b.j.n.a(str);
        if (this.l != null) {
            if (aVar == null) {
                this.m = this.l.i();
            } else {
                this.m = aVar;
            }
            if (this.m != null) {
                o oVar = this.a.c;
                aa aaVar = this.m.v;
                this.i.b("video_enabled", aaVar.a);
                this.i.b("video_filepath", aaVar.b());
                this.i.b("video_width", aaVar.b);
                this.i.b("video_height", aaVar.c);
                this.i.b("video_duration", aaVar.k);
                this.i.b("engagement_delay", aaVar.m.e);
                this.i.b("skip_delay", aaVar.l.e);
                this.i.b("browser_close_image_normal", oVar.b(this.m.r.k.f));
                this.i.b("browser_close_image_down", oVar.b(this.m.r.k.h));
                this.i.b("browser_reload_image_normal", oVar.b(this.m.r.m.f));
                this.i.b("browser_reload_image_down", oVar.b(this.m.r.m.h));
                this.i.b("browser_back_image_normal", oVar.b(this.m.r.j.f));
                this.i.b("browser_back_image_down", oVar.b(this.m.r.j.h));
                this.i.b("browser_forward_image_normal", oVar.b(this.m.r.l.f));
                this.i.b("browser_forward_image_down", oVar.b(this.m.r.l.h));
                this.i.b("browser_stop_image_normal", oVar.b(this.m.r.i.f));
                this.i.b("browser_stop_image_down", oVar.b(this.m.r.i.h));
                this.i.b("browser_glow_button", oVar.b(this.m.r.a));
                this.i.b("browser_icon", oVar.b(this.m.r.h.d));
                this.i.b("mute", oVar.b(this.m.w.k.d));
                this.i.b("unmute", oVar.b(this.m.w.l.d));
                this.i.b("poster_image", oVar.b(this.m.w.c));
                this.i.b("thumb_image", oVar.b(this.m.w.g));
                this.i.b("advertiser_name", this.m.w.d);
                this.i.b(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, this.m.w.e);
                this.i.b("title", this.m.w.f);
                this.i.b("native_engagement_enabled", this.m.w.j.a);
                this.i.b("native_engagement_type", this.m.w.j.c);
                this.i.b("native_engagement_command", this.m.w.j.e);
                this.i.b("native_engagement_label", this.m.w.j.d);
                this.i.b("skip_video_image_normal", oVar.b(aaVar.l.f));
                this.i.b("skip_video_image_down", oVar.b(aaVar.l.h));
                this.i.b("engagement_image_normal", oVar.b(aaVar.m.f));
                this.i.b("engagement_image_down", oVar.b(aaVar.m.h));
                this.i.b("engagement_height", aaVar.m.c);
                this.i.b("image_overlay_enabled", aaVar.n.a);
                this.i.b("image_overlay_filepath", oVar.b(aaVar.n.f));
                this.i.b("haptics_enabled", aaVar.o.a);
                this.i.b("haptics_filepath", oVar.b(aaVar.o.c));
                this.i.b("v4iap_enabled", this.m.x.c);
                this.i.b("product_id", this.m.x.a);
                this.i.b("in_progress", this.m.x.b);
                b();
            }
        }
    }

    void c(String str) {
        this.l = this.a.b.j.n.a(str);
        this.m = this.l.i();
        o oVar = this.a.c;
        aa aaVar = this.m.v;
        this.i.b("video_enabled", aaVar.a);
        this.i.b("video_filepath", aaVar.b());
        this.i.b("video_width", aaVar.b);
        this.i.b("video_height", aaVar.c);
        this.i.b("video_duration", aaVar.k);
        this.i.b("engagement_delay", aaVar.m.e);
        this.i.b("skip_delay", aaVar.l.e);
        b();
        c cVar = this.m.s;
        this.i.b("pre_popup_bg", oVar.b(cVar.b.d.e));
        this.i.b("v4vc_logo", oVar.b(cVar.b.d.l.d));
        this.i.b("no_button_normal", oVar.b(cVar.b.d.n.f));
        this.i.b("no_button_down", oVar.b(cVar.b.d.n.h));
        this.i.b("yes_button_normal", oVar.b(cVar.b.d.m.f));
        this.i.b("yes_button_down", oVar.b(cVar.b.d.m.h));
        this.i.b("done_button_normal", oVar.b(cVar.c.d.m.f));
        this.i.b("done_button_down", oVar.b(cVar.c.d.m.h));
        this.i.b("browser_close_image_normal", oVar.b(this.m.r.k.f));
        this.i.b("browser_close_image_down", oVar.b(this.m.r.k.h));
        this.i.b("browser_reload_image_normal", oVar.b(this.m.r.m.f));
        this.i.b("browser_reload_image_down", oVar.b(this.m.r.m.h));
        this.i.b("browser_back_image_normal", oVar.b(this.m.r.j.f));
        this.i.b("browser_back_image_down", oVar.b(this.m.r.j.h));
        this.i.b("browser_forward_image_normal", oVar.b(this.m.r.l.f));
        this.i.b("browser_forward_image_down", oVar.b(this.m.r.l.h));
        this.i.b("browser_stop_image_normal", oVar.b(this.m.r.i.f));
        this.i.b("browser_stop_image_down", oVar.b(this.m.r.i.h));
        this.i.b("browser_glow_button", oVar.b(this.m.r.a));
        this.i.b("browser_icon", oVar.b(this.m.r.h.d));
        this.i.b("skip_video_image_normal", oVar.b(aaVar.l.f));
        this.i.b("skip_video_image_down", oVar.b(aaVar.l.h));
        this.i.b("engagement_image_normal", oVar.b(aaVar.m.f));
        this.i.b("engagement_image_down", oVar.b(aaVar.m.h));
        this.i.b("engagement_height", aaVar.m.c);
        this.i.b("image_overlay_enabled", aaVar.n.a);
        this.i.b("image_overlay_filepath", oVar.b(aaVar.n.f));
        this.i.b("haptics_enabled", aaVar.o.a);
        this.i.b("haptics_filepath", oVar.b(aaVar.o.c));
        this.i.b("v4iap_enabled", this.m.x.c);
        this.i.b("product_id", this.m.x.a);
        this.i.b("in_progress", this.m.x.b);
    }

    void b() {
        v vVar = this.m.u.h;
        j jVar = this.m.u.i;
        o oVar = this.a.c;
        if (this.m.u.d) {
            if (jVar.a()) {
                a.P = true;
                a.U = jVar.g;
                a.V = oVar.b(jVar.f.b);
                this.i.b("close_image_normal", oVar.b(jVar.j.f));
                this.i.b("close_image_down", oVar.b(jVar.j.h));
                this.i.b("reload_image_normal", oVar.b(jVar.i.f));
                this.i.b("reload_image_down", oVar.b(jVar.i.h));
            } else {
                a.P = false;
                this.i.b("end_card_filepath", oVar.b(vVar.d));
                this.i.b("info_image_normal", oVar.b(vVar.f.f));
                this.i.b("info_image_down", oVar.b(vVar.f.h));
                this.i.b("info_url", vVar.f.j);
                this.i.b("replay_image_normal", oVar.b(vVar.h.f));
                this.i.b("replay_image_down", oVar.b(vVar.h.h));
                this.i.b("continue_image_normal", oVar.b(vVar.i.f));
                this.i.b("continue_image_down", oVar.b(vVar.i.h));
                this.i.b("download_image_normal", oVar.b(vVar.g.f));
                this.i.b("download_image_down", oVar.b(vVar.g.h));
                this.i.b("download_url", vVar.g.j);
            }
            aa aaVar = this.m.v;
            this.i.b("end_card_enabled", this.m.u.d);
            this.i.b("load_timeout_enabled", this.m.u.i.c);
            this.i.b("load_timeout", this.m.u.i.b);
            this.i.b("hardware_acceleration_disabled", this.a.b.j.e);
            return;
        }
        this.i.b("end_card_enabled", this.m.u.d);
    }
}
