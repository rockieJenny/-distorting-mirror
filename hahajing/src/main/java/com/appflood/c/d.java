package com.appflood.c;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.appflood.e.c;
import com.appflood.e.j;
import com.appflood.e.k;
import com.inmobi.commons.ads.cache.AdDatabaseHelper;
import com.inmobi.monetization.internal.Constants;
import io.fabric.sdk.android.services.common.IdManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import org.json.JSONObject;

public final class d {
    public static String A = "_EN.png";
    public static int B = 30000;
    public static int C = Constants.HTTP_TIMEOUT;
    public static String D = null;
    public static String E = null;
    public static int F = 255;
    public static int G;
    public static boolean H = true;
    public static boolean I = true;
    private static String J;
    private static String K;
    private static String L;
    private static String M = "get_token";
    private static ArrayList<a> N = new ArrayList();
    private static a O = null;
    private static Semaphore P = new Semaphore(1);
    private static Semaphore Q = new Semaphore(1);
    public static boolean a = false;
    public static String b;
    public static String c;
    public static String d;
    public static String e;
    public static String f;
    public static String g;
    public static URL h;
    public static HashMap<String, Object> i;
    public static boolean j = false;
    public static String k = null;
    public static String l;
    public static String m = null;
    public static String n = null;
    public static String o;
    public static String p;
    public static String q;
    public static String r;
    public static String s = null;
    public static String t = null;
    public static String u = null;
    public static String v = null;
    public static String w = null;
    public static String x = null;
    public static String y = null;
    public static String z = "";

    public interface a {
        void a();
    }

    static class b implements Runnable {
        Context a;

        class AnonymousClass2 implements a {
            final /* synthetic */ ProgressDialog a;

            AnonymousClass2(ProgressDialog progressDialog) {
                this.a = progressDialog;
            }

            public final void a() {
                f.a(new Runnable(this) {
                    private /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        try {
                            if (this.a.a != null && this.a.a.isShowing()) {
                                this.a.a.dismiss();
                            }
                        } catch (Throwable th) {
                            j.b(th, "something wrong when dialog dismiss");
                        }
                    }
                });
            }
        }

        public b(Context context) {
            this.a = context;
        }

        public final void run() {
            try {
                final ProgressDialog show = ProgressDialog.show(this.a, "", d.c, true, true);
                d.O = new a(this) {
                    final /* synthetic */ b b;

                    public final void a() {
                        f.a(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public final void run() {
                                try {
                                    if (show != null && show.isShowing()) {
                                        show.dismiss();
                                    }
                                    new Builder(this.a.b.a).setMessage(d.J).setPositiveButton(d.K, new OnClickListener(this) {
                                        private /* synthetic */ AnonymousClass1 a;

                                        {
                                            this.a = r1;
                                        }

                                        public final void onClick(DialogInterface dialogInterface, int i) {
                                            com.appflood.AFListActivity.AnonymousClass1.a(this.a.a.b.a, new Intent("android.settings.WIRELESS_SETTINGS"));
                                        }
                                    }).setNegativeButton(d.L, new OnClickListener() {
                                        public final void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).show();
                                } catch (Throwable th) {
                                    j.b(th, "something wrong when dialog popup");
                                }
                            }
                        });
                    }
                };
                d.d(new AnonymousClass2(show));
            } catch (Throwable th) {
                j.b(th, "showProgress failed");
            }
        }
    }

    public static void a(Context context, a aVar) {
        if (!j) {
            if (aVar != null) {
                d(aVar);
            }
            if (I) {
                f.a(new b(context));
            }
            i();
        } else if (aVar != null) {
            try {
                aVar.a();
            } catch (Throwable th) {
                j.a(th, "connection delegate error");
            }
        }
    }

    public static void a(a aVar) {
        if (!j) {
            if (aVar != null) {
                d(aVar);
            }
            i();
        } else if (aVar != null) {
            try {
                aVar.a();
            } catch (Throwable th) {
                j.a(th, "connection delegate error");
            }
        }
    }

    static /* synthetic */ void a(JSONObject jSONObject) {
        k = j.a(jSONObject, "get_banner_rtb", "");
        l = j.a(jSONObject, "get_banner", "");
        w = j.a(jSONObject, "token", "");
        m = j.a(jSONObject, "get_panel", "");
        n = j.a(jSONObject, "get_list", "");
        t = j.a(jSONObject, "get_raw_data", "");
        u = j.a(jSONObject, "query_credit", "");
        v = j.a(jSONObject, "update_credit", "");
        B = j.a(jSONObject, "timeout", 30000);
        y = j.a(jSONObject, "ppd", "");
        z = j.a(jSONObject, "static_url", "");
        j.a(jSONObject, "get_notification", "");
        j.a(jSONObject, "register_notification", "");
        j.a(jSONObject, "unregister_url", "");
        j.a(jSONObject, "register_icon", "");
        s = j.a(jSONObject, "referer_url", "");
        j.a(jSONObject, "scale", 0);
        g a = g.a();
        int a2 = j.a(jSONObject, "load_time", 0);
        "setInterstitial time = " + a2;
        j.a();
        if (a2 > 0) {
            a.e = a2 * 1000;
        }
        g.a().a(f.b());
        if (j.a(jSONObject, "should_show_ad", -1) >= 0) {
            H = false;
        }
        g.a();
        g.a(j.f(j.a(jSONObject, "banners", "")));
        g.a(j.a(jSONObject, "panels"));
        j = true;
        h();
    }

    static void a(boolean z) {
        a = z;
        if (z) {
            b = "免费热门游戏";
            J = "请连接网络以享受更多好玩的免费游戏！";
            K = "连接";
            L = "跳过";
            c = "载入中...";
            A = "_CN.png";
            e = "download_02_CN.png";
            f = "freebutton_02_CN.png";
            d = "下载";
            g = "http://data.appflood.cn/";
        } else {
            b = "More Free Games";
            J = "Connect to internet and enjoy more wonderful FREE games!";
            K = "Connect";
            L = "Skip";
            c = "Loading...";
            A = "_EN.png";
            e = "download_02.png";
            f = "freebutton_02.png";
            d = "Download";
            g = "http://data.appflood.com/";
        }
        try {
            h = new URL(g);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static void d(a aVar) {
        synchronized (N) {
            if (!N.contains(aVar)) {
                N.add(aVar);
            }
        }
    }

    private static void h() {
        ArrayList arrayList = new ArrayList();
        synchronized (N) {
            arrayList.addAll(N);
            N.clear();
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar != null) {
                try {
                    aVar.a();
                } catch (Throwable th) {
                    j.a(th, "connection delegate error");
                }
            }
        }
    }

    private static void i() {
        if (j.g(D) || j.g(E)) {
            j.c("app id or secret key is null!!");
        } else if (!P.tryAcquire()) {
        } else {
            if (j) {
                h();
                P.release();
                return;
            }
            Map hashMap = new HashMap();
            hashMap.put(IdManager.MODEL_FIELD, Integer.valueOf(6));
            hashMap.put("duid", k.d(c.a));
            hashMap.put("mac", k.d(c.e));
            hashMap.put("aid", k.d(c.b));
            hashMap.put("uuid", k.d(f.a));
            hashMap.put("app_key", D);
            hashMap.put("sm", Integer.valueOf(c.s));
            hashMap.put(AdDatabaseHelper.COLUMN_ADTYPE, Integer.valueOf(F));
            long currentTimeMillis = System.currentTimeMillis();
            hashMap.put("ts", Long.valueOf(currentTimeMillis));
            hashMap.put("signature", j.a(c.a + c.b + D + c.e + currentTimeMillis + E));
            hashMap.put("v", "V2.21".substring(1));
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("av", c.d);
                jSONObject.put("br", c.l);
                jSONObject.put("dn", c.c);
                jSONObject.put("pm", c.k);
                jSONObject.put("mf", c.m);
                jSONObject.put("dp", c.n);
                jSONObject.put("pn", c.f);
                jSONObject.put("so", c.o);
                jSONObject.put("sn", c.p);
                jSONObject.put("sm", c.s);
                jSONObject.put("mc", c.u);
                jSONObject.put("wc", c.v);
                jSONObject.put("lc", c.w);
                jSONObject.put("ll", c.x);
                if (c.q != 0.0d) {
                    jSONObject.put("xx", c.q);
                    jSONObject.put("yy", c.r);
                }
            } catch (Throwable th) {
            }
            hashMap.put("data", k.d(jSONObject.toString()));
            "connect  data = " + jSONObject;
            j.a();
            new com.appflood.b.b(k.a(g + M, hashMap)).a(new com.appflood.b.b.a() {
                public final void a(int i) {
                    j.c("login Failed. code = " + i);
                    if (d.O != null) {
                        try {
                            d.O.a();
                        } catch (Throwable th) {
                        }
                        d.O = null;
                    }
                    if (d.Q.tryAcquire()) {
                        f.a(new TimerTask() {
                            public final void run() {
                                try {
                                    d.Q.release();
                                    d.i();
                                } catch (Throwable th) {
                                    j.b(th, "error in reconnect");
                                }
                            }
                        }, (long) d.C);
                    }
                    d.P.release();
                }

                public final void a(com.appflood.b.b bVar) {
                    d.O = null;
                    String c = bVar.c();
                    JSONObject e = j.e(c);
                    int a = j.a(e, "ret", -1);
                    String a2 = j.a(e, "msg", null);
                    if (a == 0) {
                        e = j.a(e, "data");
                        if (e != null) {
                            d.a(e);
                            j.d("Login Success : " + c.length());
                        }
                    } else {
                        j.c("Login Failed. Error = " + a2);
                    }
                    d.P.release();
                }
            }).b(true);
        }
    }
}
