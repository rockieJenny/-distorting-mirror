package com.jirbo.adcolony;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

class a {
    static boolean A = false;
    static boolean B = false;
    static boolean C = true;
    static boolean D = false;
    static boolean E = false;
    static boolean F = false;
    static AdColonyAdapter G = null;
    static boolean H = false;
    static boolean I = false;
    static h J = null;
    static AdColonyAd K = null;
    static ADCVideo L = null;
    static ADCVideo M = null;
    static a N = null;
    static b O = null;
    static boolean P = false;
    static boolean Q = false;
    static boolean R = false;
    static boolean S = false;
    static int T = 0;
    static String U = null;
    static String V = null;
    static String W = null;
    static String X = null;
    static String Y = null;
    static ArrayList<String> Z = new ArrayList();
    public static final boolean a = false;
    static c aa = new c();
    static boolean ab = false;
    static long ac = 0;
    static int ad = 0;
    static ArrayList<Bitmap> ae = new ArrayList();
    static ArrayList<AdColonyV4VCListener> af = new ArrayList();
    static ArrayList<AdColonyAdAvailabilityListener> ag = new ArrayList();
    static ArrayList<AdColonyNativeAdView> ah = new ArrayList();
    static HashMap ai = null;
    private static Activity aj = null;
    public static final boolean b = false;
    public static final boolean c = false;
    public static final boolean d = false;
    public static String e = null;
    public static final String f = null;
    public static final int g = 0;
    public static final int h = 1;
    public static final int i = 2;
    public static final int j = 3;
    static final String k = "AdColony";
    static d l = new d();
    static boolean m;
    static int n = 2;
    static boolean o;
    static boolean p;
    static boolean q;
    static boolean r;
    static boolean s;
    static boolean t;
    static boolean u = false;
    static boolean v = true;
    static int w = 0;
    static double x = 1.0d;
    static boolean y = false;
    static boolean z = false;

    static class a extends Handler {
        AdColonyAd a;

        a() {
        }

        public void a(AdColonyAd adColonyAd) {
            if (adColonyAd == null) {
                this.a = a.K;
            } else {
                this.a = adColonyAd;
            }
            sendMessage(obtainMessage(1));
        }

        public void b(AdColonyAd adColonyAd) {
            if (adColonyAd == null) {
                this.a = a.K;
            } else {
                this.a = adColonyAd;
            }
            sendMessage(obtainMessage(0));
        }

        public void handleMessage(Message m) {
            switch (m.what) {
                case 0:
                    a.a("skip", this.a);
                    if (a.K != null) {
                        a.K.f = 1;
                        a.K.a();
                        return;
                    }
                    return;
                case 1:
                    g gVar = new g();
                    if (a.M.G.Q) {
                        gVar.b("html5_endcard_loading_started", a.M.k);
                    }
                    if (a.M.G.Q) {
                        gVar.b("html5_endcard_loading_finished", a.M.l);
                    }
                    if (a.M.G.Q) {
                        gVar.b("html5_endcard_loading_time", a.M.q);
                    }
                    if (a.M.G.Q) {
                        gVar.b("html5_endcard_loading_timeout", a.M.m);
                    }
                    if (a.M.r < 60000.0d) {
                        gVar.b("endcard_time_spent", a.M.r);
                    }
                    gVar.b("endcard_dissolved", a.M.n);
                    ADCVideo aDCVideo = a.M;
                    gVar.b("replay", ADCVideo.e);
                    gVar.b("reward", a.M.o);
                    a.l.d.a("continue", gVar, this.a);
                    a.l.b.e();
                    if (a.K != null) {
                        a.K.f = 4;
                        a.K.a();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    static class b extends Handler {
        b() {
        }

        public void handleMessage(Message m) {
            boolean z;
            int i = 0;
            String str = (String) m.obj;
            int i2 = m.what;
            if (str != null) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                str = "";
            }
            AdColonyV4VCReward adColonyV4VCReward = new AdColonyV4VCReward(z, str, i2);
            while (i < a.af.size()) {
                ((AdColonyV4VCListener) a.af.get(i)).onAdColonyV4VCReward(adColonyV4VCReward);
                i++;
            }
        }

        public void a(boolean z, String str, int i) {
            if (!z) {
                str = null;
            }
            sendMessage(obtainMessage(i, str));
        }
    }

    a() {
    }

    static void a(Activity activity) {
        if (activity != aj && activity != null) {
            aj = activity;
            N = new a();
            O = new b();
            a aVar = new a();
        }
    }

    static void b(Activity activity) {
        p = false;
        a(activity);
        J = null;
        m = g.i();
        if (H) {
            H = false;
            o = false;
            l = new d();
        }
    }

    static boolean a() {
        if (aj == null) {
            return true;
        }
        return false;
    }

    static Activity b() {
        if (aj != null) {
            return aj;
        }
        throw new AdColonyException("AdColony.configure() must be called before any other AdColony methods. If you have called AdColony.configure(), the Activity reference you passed in via AdColony.configure() OR AdColony.resume() is null.");
    }

    static boolean c() {
        return H || q || !o;
    }

    static boolean d() {
        return (!o || H || q) ? false : true;
    }

    static void a(String str) {
        H = true;
        e(str);
    }

    static void a(RuntimeException runtimeException) {
        H = true;
        e(runtimeException.toString());
        runtimeException.printStackTrace();
    }

    static void e() {
        b();
    }

    static void a(int i) {
        boolean z;
        boolean z2 = false;
        n = i;
        l.a.f = i <= 0;
        l lVar = l.b;
        if (i <= 1) {
            z = true;
        } else {
            z = false;
        }
        lVar.f = z;
        lVar = l.c;
        if (i <= 2) {
            z = true;
        } else {
            z = false;
        }
        lVar.f = z;
        l lVar2 = l.d;
        if (i <= 3) {
            z2 = true;
        }
        lVar2.f = z2;
        if (i <= 0) {
            b("DEVELOPER LOGGING ENABLED");
        }
        if (i <= 1) {
            c("DEBUG LOGGING ENABLED");
        }
    }

    static boolean b(int i) {
        return n <= i;
    }

    static boolean f() {
        return n <= 0;
    }

    static boolean g() {
        return n <= 1;
    }

    static void a(int i, String str) {
        if (n <= i) {
            switch (i) {
                case 0:
                case 1:
                    Log.d(k, str);
                    return;
                case 2:
                    Log.i(k, str);
                    return;
                case 3:
                    Log.e(k, str);
                    return;
                default:
                    return;
            }
        }
    }

    static void b(String str) {
        a(0, str);
    }

    static void c(String str) {
        a(1, str);
    }

    static void d(String str) {
        a(2, str);
    }

    static void e(String str) {
        a(3, str);
    }

    static void f(String str) {
        Toast.makeText(b(), str, 0).show();
    }

    static double g(String str) {
        return l.a(str);
    }

    static int h(String str) {
        return l.b(str);
    }

    static boolean i(String str) {
        return l.c(str);
    }

    static String j(String str) {
        return l.d(str);
    }

    static void k(String str) {
        l.a(str, null);
    }

    static void a(String str, String str2) {
        l.a(str, str2);
    }

    static void a(String str, AdColonyAd adColonyAd) {
        l.a(str, null, adColonyAd);
    }

    static void a(String str, String str2, AdColonyAd adColonyAd) {
        l.a(str, str2, adColonyAd);
    }

    static void h() {
        if (l != null && ag.size() != 0 && ai != null) {
            for (Entry entry : ai.entrySet()) {
                boolean b;
                boolean a;
                boolean booleanValue = ((Boolean) entry.getValue()).booleanValue();
                if (AdColony.isZoneV4VC((String) entry.getKey())) {
                    b = l.b((String) entry.getKey(), true, false);
                } else {
                    b = l.a((String) entry.getKey(), true, false);
                }
                if (AdColony.isZoneNative((String) entry.getKey())) {
                    a = new AdColonyNativeAdView(b(), (String) entry.getKey(), 300, true).a(true);
                } else {
                    a = b;
                }
                if (booleanValue != a) {
                    ai.put(entry.getKey(), Boolean.valueOf(a));
                    for (int i = 0; i < ag.size(); i++) {
                        ((AdColonyAdAvailabilityListener) ag.get(i)).onAdColonyAdAvailabilityChange(a, (String) entry.getKey());
                    }
                }
            }
        }
    }

    static void a(AdColonyNativeAdView adColonyNativeAdView) {
        ah.add(adColonyNativeAdView);
    }

    static void a(j jVar) {
        l.a(jVar);
    }
}
