package com.jirbo.adcolony;

import com.givewaygames.camera.ui.FragmentAlertDialog;
import com.givewaygames.gwgl.shader.pixel.MirrorPixelShader;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.monetization.internal.Ad;
import com.inmobi.re.controller.JSController;
import java.util.ArrayList;
import java.util.HashMap;

class n {

    static class a {
        String a;
        String b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h;
        boolean i;
        boolean j;
        boolean k;
        boolean l;
        boolean m;
        boolean n;
        boolean o;
        n p;
        x q;
        m r;
        c s;
        b t;
        h u;
        aa v;
        p w;
        y x;

        a() {
        }

        boolean a() {
            if (!this.r.a()) {
                return false;
            }
            if (this.s.a && !this.s.a()) {
                return false;
            }
            if (this.w.a && !this.w.a()) {
                return false;
            }
            if ((!this.u.d || this.u.a()) && this.v.a() && this.x.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.e("uuid");
            this.b = gVar.e("title");
            this.c = gVar.g("ad_campaign_id");
            this.d = gVar.g("ad_id");
            this.e = gVar.g("ad_group_id");
            this.f = gVar.g("cpcv_bid");
            this.g = gVar.g("net_earnings");
            this.h = gVar.g("expires");
            this.i = gVar.h("enable_in_app_store");
            this.j = gVar.h("video_events_on_replays");
            this.k = gVar.h("test_ad");
            this.l = gVar.h(JSController.FULL_SCREEN);
            this.m = gVar.h("house_ad");
            this.n = gVar.h("contracted");
            this.p = new n();
            if (!this.p.a(gVar.b("limits"))) {
                return false;
            }
            this.q = new x();
            if (!this.q.a(gVar.b("third_party_tracking"))) {
                return false;
            }
            this.r = new m();
            if (!this.r.a(gVar.b("in_app_browser"))) {
                return false;
            }
            this.w = new p();
            if (!this.w.a(gVar.b(Ad.AD_TYPE_NATIVE))) {
                return false;
            }
            this.s = new c();
            if (!this.s.a(gVar.b("v4vc"))) {
                return false;
            }
            this.t = new b();
            if (!this.t.a(gVar.b("ad_tracking"))) {
                return false;
            }
            this.u = new h();
            if (!this.u.a(gVar.b("companion_ad"))) {
                return false;
            }
            this.v = new aa();
            if (!this.v.a(gVar.b("video"))) {
                return false;
            }
            this.x = new y();
            if (this.x.a(gVar.b("v4iap"))) {
                return true;
            }
            return false;
        }

        void b() {
            this.s.b();
            this.r.b();
            this.w.b();
            this.u.b();
            this.v.c();
        }
    }

    static class aa {
        boolean a;
        int b;
        int c;
        String d;
        String e;
        String f;
        String g;
        String h;
        String i;
        String j;
        double k;
        g l;
        g m;
        g n;
        k o;

        aa() {
        }

        boolean a() {
            if (!this.a) {
                return true;
            }
            if (!a.l.c.a(this.d)) {
                return false;
            }
            if (!this.l.a()) {
                return false;
            }
            if (!this.m.a()) {
                return false;
            }
            if (!this.o.a()) {
                return false;
            }
            if (!this.n.a()) {
                return false;
            }
            if (a.l.b.j.i.equals("online") && !q.c()) {
                a.ad = 9;
                return l.c.b("Video not ready due to VIEW_FILTER_ONLINE");
            } else if (a.l.b.j.i.equals("wifi") && !q.a()) {
                a.ad = 9;
                return l.c.b("Video not ready due to VIEW_FILTER_WIFI");
            } else if (a.l.b.j.i.equals("cell") && !q.b()) {
                a.ad = 9;
                return l.c.b("Video not ready due to VIEW_FILTER_CELL");
            } else if (!a.l.b.j.i.equals("offline") || !q.c()) {
                return true;
            } else {
                a.ad = 9;
                return l.c.b("Video not ready due to VIEW_FILTER_OFFLINE");
            }
        }

        String b() {
            return a.l.c.b(this.d);
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.a) {
                return true;
            }
            this.b = gVar.g("width");
            this.c = gVar.g("height");
            this.d = gVar.e("url");
            this.e = gVar.e("last_modified");
            this.f = gVar.e("video_frame_rate");
            this.g = gVar.e("audio_channels");
            this.h = gVar.e("audio_codec");
            this.i = gVar.e("audio_sample_rate");
            this.j = gVar.e("video_codec");
            this.k = gVar.f("duration");
            this.l = new g();
            if (!this.l.a(gVar.b("skip_video"))) {
                return false;
            }
            this.m = new g();
            if (!this.m.a(gVar.b("in_video_engagement"))) {
                return false;
            }
            this.o = new k();
            if (!this.o.a(gVar.b("haptic"))) {
                return false;
            }
            this.n = new g();
            if (this.n.a(gVar.b("in_video_engagement").b("image_overlay"))) {
                return true;
            }
            return false;
        }

        void c() {
            a.l.c.a(this.d, this.e);
            this.m.b();
            this.l.b();
            this.o.b();
            this.n.b();
        }
    }

    static class ab {
        String a;
        int b;
        int c;
        int d;
        boolean e;
        boolean f;
        ArrayList<String> g;
        ac h;
        d i;
        ad j;
        ag k;

        ab() {
        }

        boolean a() {
            return a(false, true);
        }

        boolean a(boolean z, boolean z2) {
            if (!z2) {
                return a(z);
            }
            if (!this.e || !this.f) {
                a.ad = 1;
                return l.c.b("Ad is not ready to be played, as zone " + this.a + " is disabled or inactive.");
            } else if (this.i.a() == 0 || this.g.size() == 0) {
                a.ad = 5;
                return l.c.b("Ad is not ready to be played, as AdColony currently has no videos available to be played in zone " + this.a + ".");
            } else {
                a i;
                int size = this.g.size();
                for (int i2 = 0; i2 < size; i2++) {
                    i = i();
                    if (i == null) {
                        return l.c.b("Ad is not ready to be played due to an unknown error.");
                    }
                    if (i.a()) {
                        break;
                    }
                    k();
                }
                i = null;
                if (i == null) {
                    a.ad = 6;
                    return l.c.b("Ad is not ready to be played as required assets are still downloading or otherwise missing.");
                } else if (a(i) == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        boolean a(boolean z) {
            if (!z) {
                a.h();
            }
            if (!this.e || !this.f || this.i.a() == 0 || this.g.size() == 0) {
                return false;
            }
            a i;
            int size = this.g.size();
            for (int i2 = 0; i2 < size; i2++) {
                i = i();
                if (i == null) {
                    return false;
                }
                if (i.a()) {
                    break;
                }
                k();
            }
            i = null;
            if (i == null || a(i) == 0) {
                return false;
            }
            return true;
        }

        boolean b() {
            if (this.b <= 1) {
                return false;
            }
            a.l.g.b = true;
            ag agVar = this.k;
            int i = agVar.b;
            agVar.b = i + 1;
            if (i == 0) {
                return false;
            }
            if (this.k.b >= this.b) {
                this.k.b = 0;
            }
            return true;
        }

        int a(int i, int i2) {
            if (i2 <= 0) {
                return 0;
            }
            if (i == -1) {
                return i2;
            }
            if (i >= i2) {
                i = i2;
            }
            return i;
        }

        void c() {
            a.l.b.e();
        }

        synchronized int d() {
            return a(i());
        }

        synchronized int a(a aVar) {
            int i;
            int b = a.l.h.b(this.a);
            i = -1;
            int i2 = aVar.p.g;
            if (i2 == 0 || a.ac == 0 || (System.currentTimeMillis() - a.ac) / 1000 < ((long) i2)) {
                i2 = aVar.p.h;
                if (i2 > 0) {
                    i = a(-1, i2 - a.l.h.a(aVar.d, (double) ((System.currentTimeMillis() - a.ac) / 1000)));
                }
                if (i != 0 || i2 == 0) {
                    i2 = this.c;
                    if (i2 > 0) {
                        i = a(i, i2 - b);
                    }
                    if (i == 0) {
                        a.ad = 5;
                        i = l.c.c("Ad is not ready to be played due to the daily play cap for zone " + this.a);
                    } else {
                        i2 = this.d;
                        if (i2 > 0) {
                            i = a(i, i2 - a.l.h.a(this.a));
                        }
                        if (i == 0) {
                            a.ad = 3;
                            i = l.c.c("Ad is not ready to be played due to the session play cap for zone " + this.a);
                        } else {
                            if (this.j.a) {
                                i2 = this.j.b.a;
                                if (i2 > 0) {
                                    i = a(i, i2 - b);
                                }
                                if (i == 0) {
                                    a.ad = 4;
                                    i = l.c.c("Ad is not ready to be played due to the V4VC daily play cap.");
                                } else {
                                    b = this.j.b.c;
                                    i2 = this.j.b.b;
                                    if (i2 > 0) {
                                        i = a(i, i2 - a.l.h.a(this.a, (double) b));
                                    }
                                    if (i == 0) {
                                        a.ad = 4;
                                        i = l.c.c("Ad is not ready to be played due to the V4VC custom play cap.");
                                    }
                                }
                            }
                            b = aVar.d;
                            i2 = aVar.p.a;
                            if (i2 > 0) {
                                i = a(i, i2 - a.l.h.a(b));
                            }
                            if (i == 0) {
                                a.ad = 7;
                                c();
                                i = l.c.c("Ad is not ready to be played due to the daily play cap.");
                            } else {
                                i2 = aVar.p.f;
                                if (i2 > 0) {
                                    i = a(i, i2 - a.l.h.b(b));
                                }
                                if (i == 0) {
                                    a.ad = 7;
                                    c();
                                    i = l.c.c("Ad is not ready to be played due to the weekly play cap.");
                                } else {
                                    i2 = aVar.p.e;
                                    if (i2 > 0) {
                                        i = a(i, i2 - a.l.h.c(b));
                                    }
                                    if (i == 0) {
                                        a.ad = 7;
                                        c();
                                        i = l.c.c("Ad is not ready to be played due to the monthly play cap.");
                                    } else {
                                        i2 = aVar.p.d;
                                        if (i2 > 0) {
                                            i = a(i, i2 - a.l.h.d(b));
                                        }
                                        if (i == 0) {
                                            a.ad = 7;
                                            c();
                                            i = l.c.c("Ad is not ready to be played due to the half-year play cap.");
                                        } else {
                                            i2 = aVar.p.c;
                                            int i3 = aVar.p.b;
                                            if (i3 > 0) {
                                                i = a(i, i3 - a.l.h.a(b, (double) i2));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    c();
                    a.ad = 7;
                    i = l.c.c("Ad is not ready to be played due to the volatile play cap.");
                }
            } else {
                c();
                a.ad = 7;
                i = l.c.c("The volatile expiration for this ad has been hit.");
            }
            return i;
        }

        boolean e() {
            return b(true);
        }

        boolean b(boolean z) {
            if (!z) {
                return f();
            }
            if (!this.e || !this.f) {
                a.ad = 1;
                return l.c.b("Ad is not ready, as zone " + this.a + " is disabled or inactive.");
            } else if (this.i.a() == 0) {
                a.ad = 5;
                return l.c.b("Ad is not ready, as there are currently no ads to play in zone " + this.a + ".");
            } else if (!this.i.b().s.a) {
                return true;
            } else {
                a.ad = 14;
                return l.c.b("Ad is not ready, as zone " + this.a + " is V4VC enabled and must be played using an AdColonyV4VCAd object.");
            }
        }

        boolean f() {
            if (!this.e || !this.f || this.i.a() == 0 || this.i.b().s.a) {
                return false;
            }
            return true;
        }

        boolean g() {
            return c(true);
        }

        boolean c(boolean z) {
            if (!z) {
                return h();
            }
            if (!this.e || !this.f) {
                a.ad = 1;
                return l.c.b("Ad is not ready, as zone " + this.a + " is disabled or inactive.");
            } else if (this.i.a() == 0) {
                a.ad = 5;
                return l.c.b("Ad is not ready, as there are currently no ads to play in zone " + this.a + ".");
            } else if (this.i.b().s.a) {
                return true;
            } else {
                a.ad = 15;
                return l.c.b("Ad is not ready, as zone " + this.a + " is not V4VC enabled and must be played using an AdColonyVideoAd object.");
            }
        }

        boolean h() {
            if (this.e && this.f && this.i.a() != 0 && this.i.b().s.a) {
                return true;
            }
            return false;
        }

        a i() {
            if (this.g.size() > 0) {
                return this.i.a((String) this.g.get(this.k.c % this.g.size()));
            }
            return null;
        }

        a j() {
            if (this.g.size() > 0) {
                return this.i.b(this.k.c % this.g.size());
            }
            return null;
        }

        void k() {
            if (this.g.size() > 0) {
                this.k.c = (this.k.c + 1) % this.g.size();
            }
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.e("uuid");
            this.e = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            this.f = gVar.h("active");
            if (!this.e || !this.f) {
                return true;
            }
            this.b = gVar.g("play_interval");
            this.c = gVar.g("daily_play_cap");
            this.d = gVar.g("session_play_cap");
            this.g = new ArrayList();
            ArrayList d = gVar.d("play_order");
            this.g = d;
            if (d == null) {
                return false;
            }
            this.h = new ac();
            if (!this.h.a(gVar.b("tracking"))) {
                return false;
            }
            this.i = new d();
            if (!this.i.a(gVar.c("ads"))) {
                return false;
            }
            this.j = new ad();
            if (!this.j.a(gVar.b("v4vc"))) {
                return false;
            }
            this.k = a.l.g.a(this.a);
            return true;
        }

        void l() {
            if (this.e && this.f) {
                for (int i = 0; i < this.i.a(); i++) {
                    this.i.a(i).b();
                }
            }
        }
    }

    static class ac {
        String a;

        ac() {
        }

        boolean a(g gVar) {
            if (gVar != null) {
                this.a = gVar.a("request", null);
            }
            return true;
        }
    }

    static class ad {
        boolean a;
        z b;
        int c;
        String d;
        boolean e;
        int f;

        ad() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.a) {
                return true;
            }
            this.b = new z();
            if (!this.b.a(gVar.b("limits"))) {
                return false;
            }
            this.c = gVar.g("reward_amount");
            this.d = gVar.e("reward_name");
            this.e = gVar.h("client_side");
            this.f = gVar.g("videos_per_reward");
            return true;
        }
    }

    static class ae {
        ArrayList<ab> a;

        ae() {
        }

        boolean a(c cVar) {
            this.a = new ArrayList();
            if (cVar == null) {
                return false;
            }
            for (int i = 0; i < cVar.i(); i++) {
                ab abVar = new ab();
                if (!abVar.a(cVar.b(i))) {
                    return false;
                }
                this.a.add(abVar);
            }
            return true;
        }

        int a() {
            return this.a.size();
        }

        ab a(int i) {
            return (ab) this.a.get(i);
        }

        ab b() {
            return (ab) this.a.get(0);
        }

        ab a(String str) {
            for (int i = 0; i < this.a.size(); i++) {
                ab abVar = (ab) this.a.get(i);
                if (abVar.a.equals(str)) {
                    return abVar;
                }
            }
            l.a.a("No such zone: ").b((Object) str);
            return null;
        }
    }

    static class b {
        String A;
        String B;
        String C;
        g D = new g();
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;
        String h;
        String i;
        String j;
        String k;
        String l;
        String m;
        String n;
        String o;
        String p;
        String q;
        String r;
        String s;
        String t;
        String u;
        String v;
        String w;
        String x;
        String y;
        String z;

        b() {
        }

        boolean a(g gVar) {
            if (gVar != null) {
                this.a = gVar.a("replay", null);
                this.b = gVar.a("card_shown", null);
                this.c = gVar.a("html5_interaction", null);
                this.d = gVar.a(FragmentAlertDialog.ARG_CANCEL_TEXT, null);
                this.e = gVar.a(AdTrackerConstants.GOAL_DOWNLOAD, null);
                this.f = gVar.a("skip", null);
                this.g = gVar.a("info", null);
                this.h = gVar.a("custom_event", null);
                this.i = gVar.a("midpoint", null);
                this.j = gVar.a("card_dissolved", null);
                this.k = gVar.a("start", null);
                this.l = gVar.a("third_quartile", null);
                this.m = gVar.a("complete", null);
                this.n = gVar.a("continue", null);
                this.o = gVar.a("in_video_engagement", null);
                this.p = gVar.a("reward_v4vc", null);
                this.r = gVar.a("first_quartile", null);
                this.q = gVar.a("v4iap", null);
                this.s = gVar.a("video_expanded", null);
                this.t = gVar.a("sound_mute", null);
                this.u = gVar.a("sound_unmute", null);
                this.v = gVar.a("video_paused", null);
                this.w = gVar.a("video_resumed", null);
                this.x = gVar.a("native_start", null);
                this.y = gVar.a("native_first_quartile", null);
                this.z = gVar.a("native_midpoint", null);
                this.A = gVar.a("native_third_quartile", null);
                this.B = gVar.a("native_complete", null);
                this.C = gVar.a("native_overlay_click", null);
                this.D.b("replay", this.a);
                this.D.b("card_shown", this.b);
                this.D.b("html5_interaction", this.c);
                this.D.b(FragmentAlertDialog.ARG_CANCEL_TEXT, this.d);
                this.D.b(AdTrackerConstants.GOAL_DOWNLOAD, this.e);
                this.D.b("skip", this.f);
                this.D.b("info", this.g);
                this.D.b("custom_event", this.h);
                this.D.b("midpoint", this.i);
                this.D.b("card_dissolved", this.j);
                this.D.b("start", this.k);
                this.D.b("third_quartile", this.l);
                this.D.b("complete", this.m);
                this.D.b("continue", this.n);
                this.D.b("in_video_engagement", this.o);
                this.D.b("reward_v4vc", this.p);
                this.D.b("first_quartile", this.r);
                this.D.b("v4iap", this.q);
                this.D.b("video_expanded", this.s);
                this.D.b("sound_mute", this.t);
                this.D.b("sound_unmute", this.u);
                this.D.b("video_paused", this.v);
                this.D.b("video_resumed", this.w);
                this.D.b("native_start", this.x);
                this.D.b("native_first_quartile", this.y);
                this.D.b("native_midpoint", this.z);
                this.D.b("native_third_quartile", this.A);
                this.D.b("native_complete", this.B);
                this.D.b("native_overlay_click", this.C);
            }
            return true;
        }
    }

    static class c {
        boolean a;
        u b;
        s c;

        c() {
        }

        boolean a() {
            if (this.b.a() && this.c.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.a) {
                return true;
            }
            this.b = new u();
            if (!this.b.a(gVar.b("pre_popup"))) {
                return false;
            }
            this.c = new s();
            if (this.c.a(gVar.b("post_popup"))) {
                return true;
            }
            return false;
        }

        void b() {
            if (this.a) {
                this.b.b();
                this.c.b();
            }
        }
    }

    static class d {
        ArrayList<a> a = new ArrayList();

        d() {
        }

        boolean a(c cVar) {
            if (cVar == null) {
                return false;
            }
            for (int i = 0; i < cVar.i(); i++) {
                a aVar = new a();
                if (!aVar.a(cVar.b(i))) {
                    return false;
                }
                this.a.add(aVar);
            }
            return true;
        }

        void a(a aVar) {
            this.a.add(aVar);
        }

        int a() {
            return this.a.size();
        }

        a a(int i) {
            return (a) this.a.get(i);
        }

        a b() {
            return (a) this.a.get(0);
        }

        a a(String str) {
            for (int i = 0; i < this.a.size(); i++) {
                a aVar = (a) this.a.get(i);
                if (aVar.a.equals(str)) {
                    return aVar;
                }
            }
            return null;
        }

        a b(int i) {
            while (i < this.a.size()) {
                a aVar = (a) this.a.get(i);
                if (aVar.w.a) {
                    return aVar;
                }
                i++;
            }
            for (int i2 = 0; i2 < this.a.size(); i2++) {
                aVar = (a) this.a.get(i2);
                if (aVar.w.a) {
                    return aVar;
                }
            }
            return null;
        }
    }

    static class e {
        boolean a;
        boolean b;
        String c;
        String d;
        boolean e = false;
        boolean f;
        double g;
        String h;
        String i;
        String j;
        f k;
        w l;
        ArrayList<String> m;
        ae n;
        i o;

        e() {
        }

        boolean a(String str) {
            return a(str, false, true);
        }

        boolean a(String str, boolean z, boolean z2) {
            if (!this.a) {
                return false;
            }
            ab a = this.n.a(str);
            if (a != null) {
                return a.a(z, z2);
            }
            return false;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            this.b = gVar.h("log_screen_overlay");
            this.c = gVar.e("last_country");
            this.d = gVar.e("last_ip");
            this.f = gVar.h("collect_iap_enabled");
            this.g = gVar.f("media_pool_size");
            this.h = gVar.e("log_level");
            this.i = gVar.e("view_network_pass_filter");
            this.j = gVar.e("cache_network_pass_filter");
            this.e = gVar.h("hardware_acceleration_disabled");
            if (this.i == null || this.i.equals("")) {
                this.i = "all";
            }
            if (this.j == null || this.j.equals("")) {
                this.j = "all";
            }
            this.k = new f();
            if (!this.k.a(gVar.b("tracking"))) {
                return false;
            }
            this.l = new w();
            if (!this.l.a(gVar.b("third_party_tracking"))) {
                return false;
            }
            this.m = gVar.d("console_messages");
            l.a.b((Object) "Parsing zones");
            this.n = new ae();
            if (!this.n.a(gVar.c("zones"))) {
                return false;
            }
            this.o = new i();
            if (!this.o.a(gVar.b("device"))) {
                return false;
            }
            l.a.b((Object) "Finished parsing app info");
            return true;
        }

        void a() {
            l.a.b((Object) "Caching media");
            if (this.a) {
                for (int i = 0; i < this.n.a(); i++) {
                    this.n.a(i).l();
                }
            }
        }
    }

    static class f {
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;
        g h;

        f() {
        }

        boolean a(g gVar) {
            if (gVar != null) {
                this.a = gVar.a("update", null);
                this.b = gVar.a("install", null);
                this.c = gVar.a("dynamic_interests", null);
                this.d = gVar.a("user_meta_data", null);
                this.e = gVar.a("in_app_purchase", null);
                this.g = gVar.a("session_end", null);
                this.f = gVar.a("session_start", null);
                this.h = new g();
                this.h.b("update", this.a);
                this.h.b("install", this.b);
                this.h.b("dynamic_interests", this.c);
                this.h.b("user_meta_data", this.d);
                this.h.b("in_app_purchase", this.e);
                this.h.b("session_end", this.g);
                this.h.b("session_start", this.f);
                f fVar = new f("iap_cache.txt");
                c c = k.c(fVar);
                if (c != null) {
                    for (int i = 0; i < c.i(); i++) {
                        a.l.d.a("in_app_purchase", c.a(i, new g()));
                    }
                    fVar.c();
                    a.aa.j();
                }
                a.F = true;
            }
            return true;
        }
    }

    static class g {
        boolean a;
        int b;
        int c;
        int d;
        int e;
        String f;
        String g;
        String h;
        String i;
        String j;
        String k;
        String l;
        String m;
        String n;
        String o;
        String p;

        g() {
        }

        boolean a() {
            if (!this.a) {
                return true;
            }
            if (!a.l.c.a(this.f)) {
                return false;
            }
            if (a.l.c.a(this.h)) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.a(MirrorPixelShader.UNIFORM_ENABLED, true);
            this.e = gVar.g("delay");
            this.b = gVar.g("width");
            this.c = gVar.g("height");
            this.d = gVar.g("scale");
            this.f = gVar.e("image_normal");
            this.g = gVar.e("image_normal_last_modified");
            this.h = gVar.e("image_down");
            this.i = gVar.e("image_down_last_modified");
            this.j = gVar.e("click_action");
            this.k = gVar.e("click_action_type");
            this.l = gVar.e(PlusShare.KEY_CALL_TO_ACTION_LABEL);
            this.m = gVar.e("label_rgba");
            this.n = gVar.e("label_shadow_rgba");
            this.o = gVar.e("label_html");
            this.p = gVar.e("event");
            return true;
        }

        void b() {
            a.l.c.a(this.f, this.g);
            a.l.c.a(this.h, this.i);
        }
    }

    static class h {
        String a;
        int b;
        int c;
        boolean d;
        boolean e;
        boolean f;
        double g;
        v h;
        j i;

        h() {
        }

        boolean a() {
            if (this.i.a && !this.i.a()) {
                return false;
            }
            if (!this.d) {
                return true;
            }
            if (this.h.a() || this.i.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.d = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.d) {
                return true;
            }
            this.a = gVar.e("uuid");
            this.b = gVar.g("ad_id");
            this.c = gVar.g("ad_campaign_id");
            this.e = gVar.h("dissolve");
            this.f = gVar.h("enable_in_app_store");
            this.g = gVar.f("dissolve_delay");
            this.h = new v();
            if (!this.h.a(gVar.b("static"))) {
                return false;
            }
            this.i = new j();
            if (this.i.a(gVar.b("html5"))) {
                return true;
            }
            return false;
        }

        void b() {
            if (this.d) {
                this.h.b();
                this.i.b();
            }
        }
    }

    static class i {
        String a;

        i() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, null);
            a.Y = this.a;
            return true;
        }
    }

    static class j {
        boolean a;
        double b;
        boolean c;
        boolean d;
        String e;
        o f;
        String g;
        l h;
        g i;
        g j;

        j() {
        }

        boolean a() {
            if (!q.c()) {
                a.ad = 8;
                return l.c.b("Ad not ready due to no network connection.");
            } else if (this.a && this.f.a() && this.h.a() && this.i.a() && this.j.a()) {
                return true;
            } else {
                return false;
            }
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            this.b = gVar.f("load_timeout");
            this.c = gVar.h("load_timeout_enabled");
            this.d = gVar.h("load_spinner_enabled");
            this.e = gVar.e("background_color");
            this.g = gVar.e("html5_tag");
            this.f = new o();
            if (!this.f.a(gVar.b("mraid_js"))) {
                return false;
            }
            this.h = new l();
            if (!this.h.a(gVar.b("background_logo"))) {
                return false;
            }
            this.i = new g();
            if (!this.i.a(gVar.b("replay"))) {
                return false;
            }
            this.j = new g();
            if (this.j.a(gVar.b("close"))) {
                return true;
            }
            return false;
        }

        void b() {
            if (this.a) {
                if (this.f != null) {
                    this.f.b();
                }
                if (this.h != null) {
                    this.h.b();
                }
                if (this.i != null) {
                    this.i.b();
                }
                if (this.j != null) {
                    this.j.b();
                }
            }
        }
    }

    static class k {
        boolean a;
        String b;
        String c;
        String d;

        k() {
        }

        boolean a() {
            if (this.a && !a.l.c.a(this.c)) {
                return false;
            }
            return true;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.a(MirrorPixelShader.UNIFORM_ENABLED, false);
            this.c = gVar.e("file_url");
            this.d = gVar.e("last_modified");
            return true;
        }

        void b() {
            a.l.c.a(this.c, this.d);
        }
    }

    static class l {
        int a;
        int b;
        int c;
        String d;
        String e;
        boolean f;

        l() {
        }

        boolean a() {
            if (this.f) {
                return a.l.c.a(this.d);
            }
            return true;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.f = gVar.a(MirrorPixelShader.UNIFORM_ENABLED, true);
            this.a = gVar.g("width");
            this.b = gVar.g("height");
            this.c = gVar.g("scale");
            this.d = gVar.e("image");
            this.e = gVar.e("image_last_modified");
            if (!this.e.equals("")) {
                return true;
            }
            this.e = gVar.e("last_modified");
            return true;
        }

        void b() {
            a.l.c.a(this.d, this.e);
        }
    }

    static class m {
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;
        l h;
        g i;
        g j;
        g k;
        g l;
        g m;

        m() {
        }

        boolean a() {
            if (a.l.c.a(this.a) && a.l.c.a(this.c) && a.l.c.a(this.e) && this.h.a() && this.i.a() && this.j.a() && this.k.a() && this.l.a() && this.m.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.e("tiny_glow_image");
            this.b = gVar.e("tiny_glow_image_last_modified;");
            this.c = gVar.e("background_bar_image");
            this.d = gVar.e("background_bar_image_last_modified");
            this.e = gVar.e("background_tile_image");
            this.f = gVar.e("background_tile_image_last_modified");
            this.g = gVar.e("background_color");
            this.h = new l();
            if (!this.h.a(gVar.b("logo"))) {
                return false;
            }
            this.h = new l();
            if (!this.h.a(gVar.b("logo"))) {
                return false;
            }
            this.i = new g();
            if (!this.i.a(gVar.b("stop"))) {
                return false;
            }
            this.j = new g();
            if (!this.j.a(gVar.b("back"))) {
                return false;
            }
            this.k = new g();
            if (!this.k.a(gVar.b("close"))) {
                return false;
            }
            this.l = new g();
            if (!this.l.a(gVar.b("forward"))) {
                return false;
            }
            this.m = new g();
            if (this.m.a(gVar.b("reload"))) {
                return true;
            }
            return false;
        }

        void b() {
            a.l.c.a(this.a, this.b);
            a.l.c.a(this.c, this.d);
            a.l.c.a(this.e, this.f);
            this.h.b();
            this.i.b();
            this.j.b();
            this.k.b();
            this.l.b();
            this.m.b();
        }
    }

    static class n {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h;

        n() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.g("daily_play_cap");
            this.b = gVar.g("custom_play_cap");
            this.c = gVar.g("custom_play_cap_period");
            this.d = gVar.g("total_play_cap");
            this.e = gVar.g("monthly_play_cap");
            this.f = gVar.g("weekly_play_cap");
            this.g = gVar.g("volatile_expiration");
            this.h = gVar.g("volatile_play_cap");
            return true;
        }
    }

    static class o {
        boolean a;
        String b;
        String c;

        o() {
        }

        boolean a() {
            if (this.a && !a.l.c.a(this.b)) {
                return false;
            }
            return true;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.a) {
                return true;
            }
            this.b = gVar.e("url");
            this.c = gVar.e("last_modified");
            return true;
        }

        void b() {
            a.l.c.a(this.b, this.c);
        }
    }

    static class p {
        boolean a;
        boolean b;
        String c;
        String d;
        String e;
        String f;
        String g;
        String h;
        String i;
        q j;
        l k;
        l l;

        p() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            this.c = gVar.e("poster_image");
            this.d = gVar.e("advertiser_name");
            this.e = gVar.e(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
            this.f = gVar.e("title");
            this.g = gVar.e("thumb_image");
            this.h = gVar.e("poster_image_last_modified");
            this.i = gVar.e("thumb_image_last_modified");
            this.k = new l();
            if (!this.k.a(gVar.b("mute"))) {
                return false;
            }
            this.b = this.k.f;
            this.l = new l();
            if (!this.l.a(gVar.b("unmute"))) {
                return false;
            }
            this.j = new q();
            if (this.j.a(gVar.b("overlay"))) {
                return true;
            }
            return false;
        }

        boolean a() {
            if (this.a && a.l.c.a(this.c) && a.l.c.a(this.g) && this.k.a() && this.l.a()) {
                return true;
            }
            return false;
        }

        void b() {
            a.l.c.a(this.c, this.h);
            a.l.c.a(this.g, this.i);
            this.k.b();
            this.l.b();
        }
    }

    static class q {
        boolean a;
        boolean b;
        String c;
        String d;
        String e;

        q() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.a) {
                return true;
            }
            this.b = gVar.h("in_app");
            this.c = gVar.e("click_action_type");
            this.e = gVar.e("click_action");
            this.d = gVar.e(PlusShare.KEY_CALL_TO_ACTION_LABEL);
            return true;
        }
    }

    static class r {
        int a;
        String b;
        int c;
        int d;
        String e;
        String f;
        String g;
        String h;
        String i;
        String j;
        String k;
        l l;
        g m;

        r() {
        }

        boolean a() {
            if (a.l.c.a(this.e) && this.l.a() && this.m.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            this.a = gVar.g("scale");
            this.b = gVar.e("label_reward");
            this.c = gVar.g("width");
            this.d = gVar.g("height");
            this.e = gVar.e("image");
            this.f = gVar.e("image_last_modified");
            this.g = gVar.e(PlusShare.KEY_CALL_TO_ACTION_LABEL);
            this.h = gVar.e("label_rgba");
            this.i = gVar.e("label_shadow_rgba");
            this.j = gVar.e("label_fraction");
            this.k = gVar.e("label_html");
            this.l = new l();
            if (!this.l.a(gVar.b("logo"))) {
                return false;
            }
            this.m = new g();
            if (this.m.a(gVar.b("option_done"))) {
                return true;
            }
            return false;
        }

        void b() {
            a.l.c.a(this.e, this.f);
            this.l.b();
            this.m.b();
        }
    }

    static class s {
        String a;
        String b;
        l c;
        r d;

        s() {
        }

        boolean a() {
            if (a.l.c.a(this.a) && this.c.a() && this.d.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            this.a = gVar.e("background_image");
            this.b = gVar.e("background_image_last_modified");
            this.c = new l();
            if (!this.c.a(gVar.b("background_logo"))) {
                return false;
            }
            this.d = new r();
            if (this.d.a(gVar.b("dialog"))) {
                return true;
            }
            return false;
        }

        void b() {
            a.l.c.a(this.a, this.b);
            this.d.b();
        }
    }

    static class t {
        int a;
        String b;
        int c;
        int d;
        String e;
        String f;
        String g;
        String h;
        String i;
        String j;
        String k;
        l l;
        g m;
        g n;

        t() {
        }

        boolean a() {
            if (a.l.c.a(this.e) && this.l.a() && this.m.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            this.a = gVar.g("scale");
            this.b = gVar.e("label_reward");
            this.c = gVar.g("width");
            this.d = gVar.g("height");
            this.e = gVar.e("image");
            this.f = gVar.e("image_last_modified");
            this.g = gVar.e(PlusShare.KEY_CALL_TO_ACTION_LABEL);
            this.h = gVar.e("label_rgba");
            this.i = gVar.e("label_shadow_rgba");
            this.j = gVar.e("label_fraction");
            this.k = gVar.e("label_html");
            this.l = new l();
            if (!this.l.a(gVar.b("logo"))) {
                return false;
            }
            this.m = new g();
            if (!this.m.a(gVar.b("option_yes"))) {
                return false;
            }
            this.n = new g();
            if (this.n.a(gVar.b("option_no"))) {
                return true;
            }
            return false;
        }

        void b() {
            a.l.c.a(this.e, this.f);
            this.l.b();
            this.m.b();
            this.n.b();
        }
    }

    static class u {
        String a;
        String b;
        l c;
        t d;

        u() {
        }

        boolean a() {
            if (a.l.c.a(this.a) && this.c.a() && this.d.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            this.a = gVar.e("background_image");
            this.b = gVar.e("background_image_last_modified");
            this.c = new l();
            if (!this.c.a(gVar.b("background_logo"))) {
                return false;
            }
            this.d = new t();
            if (this.d.a(gVar.b("dialog"))) {
                return true;
            }
            return false;
        }

        void b() {
            a.l.c.a(this.a, this.b);
            this.c.b();
            this.d.b();
        }
    }

    static class v {
        boolean a;
        int b;
        int c;
        String d;
        String e;
        g f;
        g g;
        g h;
        g i;

        v() {
        }

        boolean a() {
            if (!this.a) {
                return true;
            }
            if (!a.l.c.a(this.d)) {
                return false;
            }
            if (!this.h.a()) {
                return false;
            }
            if (!this.i.a()) {
                return false;
            }
            if (!this.g.a()) {
                return false;
            }
            if (this.f.a()) {
                return true;
            }
            return false;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.a) {
                return true;
            }
            this.b = gVar.g("width");
            this.c = gVar.g("height");
            this.d = gVar.e("background_image");
            this.e = gVar.e("background_image_last_modified");
            if (a.f != null) {
                this.d = a.f;
            }
            this.h = new g();
            if (!this.h.a(gVar.b("replay"))) {
                return false;
            }
            this.i = new g();
            if (!this.i.a(gVar.b("continue"))) {
                return false;
            }
            this.g = new g();
            if (!this.g.a(gVar.b(AdTrackerConstants.GOAL_DOWNLOAD))) {
                return false;
            }
            this.f = new g();
            if (this.f.a(gVar.b("info"))) {
                return true;
            }
            return false;
        }

        void b() {
            if (this.a) {
                a.l.c.a(this.d, this.e);
                this.h.b();
                this.i.b();
                this.g.b();
                this.f.b();
            }
        }
    }

    static class w {
        ArrayList<String> a = new ArrayList();
        ArrayList<String> b = new ArrayList();
        ArrayList<String> c = new ArrayList();
        HashMap<String, ArrayList<String>> d = new HashMap();

        w() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            ArrayList d = gVar.d("update");
            this.a = d;
            if (d == null) {
                return false;
            }
            d = gVar.d("install");
            this.b = d;
            if (d == null) {
                return false;
            }
            d = gVar.d("session_start");
            this.c = d;
            if (d == null) {
                return false;
            }
            this.d.put("update", this.a);
            this.d.put("install", this.b);
            this.d.put("session_start", this.c);
            return true;
        }
    }

    static class x {
        ArrayList<String> A = new ArrayList();
        ArrayList<String> B = new ArrayList();
        HashMap<String, ArrayList<String>> C = new HashMap();
        ArrayList<String> a = new ArrayList();
        ArrayList<String> b = new ArrayList();
        ArrayList<String> c = new ArrayList();
        ArrayList<String> d = new ArrayList();
        ArrayList<String> e = new ArrayList();
        ArrayList<String> f = new ArrayList();
        ArrayList<String> g = new ArrayList();
        ArrayList<String> h = new ArrayList();
        ArrayList<String> i = new ArrayList();
        ArrayList<String> j = new ArrayList();
        ArrayList<String> k = new ArrayList();
        ArrayList<String> l = new ArrayList();
        ArrayList<String> m = new ArrayList();
        ArrayList<String> n = new ArrayList();
        ArrayList<String> o = new ArrayList();
        ArrayList<String> p = new ArrayList();
        ArrayList<String> q = new ArrayList();
        ArrayList<String> r = new ArrayList();
        ArrayList<String> s = new ArrayList();
        ArrayList<String> t = new ArrayList();
        ArrayList<String> u = new ArrayList();
        ArrayList<String> v = new ArrayList();
        ArrayList<String> w = new ArrayList();
        ArrayList<String> x = new ArrayList();
        ArrayList<String> y = new ArrayList();
        ArrayList<String> z = new ArrayList();

        x() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.d("replay");
            this.b = gVar.d("card_shown");
            this.c = gVar.d("html5_interaction");
            this.d = gVar.d(FragmentAlertDialog.ARG_CANCEL_TEXT);
            this.e = gVar.d(AdTrackerConstants.GOAL_DOWNLOAD);
            this.f = gVar.d("skip");
            this.g = gVar.d("info");
            this.h = gVar.d("midpoint");
            this.i = gVar.d("card_dissolved");
            this.j = gVar.d("start");
            this.k = gVar.d("third_quartile");
            this.l = gVar.d("complete");
            this.m = gVar.d("continue");
            this.n = gVar.d("in_video_engagement");
            this.o = gVar.d("reward_v4vc");
            this.p = gVar.d("first_quartile");
            this.q = gVar.d("v4iap");
            this.r = gVar.d("video_expanded");
            this.s = gVar.d("sound_mute");
            this.t = gVar.d("sound_unmute");
            this.u = gVar.d("video_paused");
            this.v = gVar.d("video_resumed");
            this.w = gVar.d("native_start");
            this.x = gVar.d("native_first_quartile");
            this.y = gVar.d("native_midpoint");
            this.z = gVar.d("native_third_quartile");
            this.A = gVar.d("native_complete");
            this.B = gVar.d("native_overlay_click");
            this.C.put("replay", this.a);
            this.C.put("card_shown", this.b);
            this.C.put("html5_interaction", this.c);
            this.C.put(FragmentAlertDialog.ARG_CANCEL_TEXT, this.d);
            this.C.put(AdTrackerConstants.GOAL_DOWNLOAD, this.e);
            this.C.put("skip", this.f);
            this.C.put("info", this.g);
            this.C.put("midpoint", this.h);
            this.C.put("card_dissolved", this.i);
            this.C.put("start", this.j);
            this.C.put("third_quartile", this.k);
            this.C.put("complete", this.l);
            this.C.put("continue", this.m);
            this.C.put("in_video_engagement", this.n);
            this.C.put("reward_v4vc", this.o);
            this.C.put("first_quartile", this.p);
            this.C.put("v4iap", this.q);
            this.C.put("video_expanded", this.r);
            this.C.put("sound_mute", this.s);
            this.C.put("sound_unmute", this.t);
            this.C.put("video_paused", this.u);
            this.C.put("video_resumed", this.v);
            this.C.put("native_start", this.w);
            this.C.put("native_first_quartile", this.x);
            this.C.put("native_midpoint", this.y);
            this.C.put("native_third_quartile", this.z);
            this.C.put("native_complete", this.A);
            this.C.put("native_overlay_click", this.B);
            return true;
        }
    }

    static class y {
        String a;
        String b;
        boolean c;

        y() {
        }

        boolean a() {
            return true;
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.c = gVar.h(MirrorPixelShader.UNIFORM_ENABLED);
            if (!this.c) {
                return true;
            }
            this.a = (String) gVar.d("product_ids").get(0);
            this.b = gVar.e("in_progress");
            return true;
        }
    }

    static class z {
        int a;
        int b;
        int c;

        z() {
        }

        boolean a(g gVar) {
            if (gVar == null) {
                return false;
            }
            this.a = gVar.g("daily_play_cap");
            this.b = gVar.g("custom_play_cap");
            this.c = gVar.g("custom_play_cap_period");
            return true;
        }
    }

    n() {
    }
}
