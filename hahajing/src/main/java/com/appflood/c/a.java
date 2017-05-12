package com.appflood.c;

import android.content.Context;
import android.view.View;
import com.appflood.AppFlood.AFRequestDelegate;
import com.appflood.b.b;
import com.appflood.e.j;
import com.appflood.mraid.AFBannerWebView;
import com.flurry.android.AdCreative;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.HashMap;
import java.util.TimerTask;
import org.json.JSONObject;

public final class a implements AFRequestDelegate, com.appflood.b.b.a, b {
    private boolean a = false;
    private b b;
    private int c = 14;
    private int d = 100;
    private int e = 100;
    private Context f;
    private boolean g = false;
    private boolean h = false;
    private View i;
    private JSONObject j;
    private int k = 0;
    private boolean l = false;
    private boolean m = false;

    public a(Context context) {
        this.f = context;
    }

    static /* synthetic */ void a(a aVar, HashMap hashMap) {
        aVar.g = false;
        if (aVar.g) {
            View aVar2 = new com.appflood.d.a(aVar.f, hashMap);
            aVar.i = aVar2;
            aVar2.a((b) aVar);
            return;
        }
        aVar2 = new AFBannerWebView(aVar.f, hashMap);
        aVar.i = aVar2;
        aVar2.a((b) aVar);
    }

    private void a(HashMap<String, Object> hashMap) {
        if (this.b != null) {
            this.b.onFinish(hashMap);
        }
        boolean booleanValue = ((Boolean) hashMap.get("result")).booleanValue();
        Object obj = hashMap.get("try");
        boolean booleanValue2 = obj != null ? ((Boolean) obj).booleanValue() : true;
        if (booleanValue) {
            if ((this.i instanceof AFBannerWebView) && ((AFBannerWebView) this.i).b()) {
                j.a();
            }
        } else if (booleanValue2) {
            " need Try mGoNextOnErrored = " + this.m;
            j.a();
            if (this.m && hashMap.containsKey("video_error")) {
                f.a(new TimerTask(this) {
                    private /* synthetic */ a a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        this.a.b();
                    }
                }, 1000);
            }
        }
    }

    private void c(boolean z) {
        if (this.b != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("result", Boolean.valueOf(false));
            hashMap.put("w", Integer.valueOf(0));
            hashMap.put("h", Integer.valueOf(0));
            hashMap.put("try", Boolean.valueOf(z));
            a(hashMap);
        }
    }

    private void e() {
        if (!d.H) {
            return;
        }
        if ((d.F & 1) > 0 || (d.F & 4) > 0) {
            g.a().a(this.f, this.d, this.e, this.k, this.c, this, this.l);
        }
    }

    public final void a() {
        this.m = true;
    }

    public final void a(int i) {
        c(true);
    }

    public final void a(int i, int i2, int i3) {
        this.d = i;
        this.e = i2;
        this.k = i3;
        d.a(new com.appflood.c.d.a(this) {
            private /* synthetic */ a a;

            {
                this.a = r1;
            }

            public final void a() {
                this.a.e();
            }
        });
    }

    public final void a(b bVar) {
        String c = bVar.c();
        if (c.length() <= 200) {
            " cont " + c;
            j.a();
        }
        JSONObject e = j.e(c);
        if (j.a(e, "ret", 0) == 0) {
            this.j = j.a(e, "data");
            String a = j.a(this.j, AnalyticsSQLiteHelper.EVENT_LIST_TYPE, "");
            this.g = !AdCreative.kFormatBanner.equals(a);
            " is video " + this.g + " type " + a + "  --------- ";
            j.a();
            c = j.a(this.j, "data", "");
            final HashMap hashMap = new HashMap();
            hashMap.put("click_cb_url", j.a(this.j, "click_cb_url", ""));
            hashMap.put("show_cb_url", j.a(this.j, "show_cb_url", ""));
            hashMap.put("redirect_url", j.a(this.j, "redirect_url", ""));
            hashMap.put("width", Integer.valueOf(j.a(this.j, "width", 0)));
            hashMap.put("height", Integer.valueOf(j.a(this.j, "height", 0)));
            hashMap.put("html_content", c);
            hashMap.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, a);
            f.a(new Runnable(this) {
                private /* synthetic */ a b;

                public final void run() {
                    a.a(this.b, hashMap);
                }
            });
            return;
        }
        c(false);
    }

    public final void a(b bVar) {
        this.b = bVar;
    }

    public final void a(boolean z) {
        this.l = z;
    }

    public final void b() {
        if (!this.h) {
            e();
        }
    }

    public final void b(int i) {
        this.c = i;
    }

    public final void b(boolean z) {
        this.a = z;
    }

    public final View c() {
        return this.i;
    }

    public final void d() {
        this.h = true;
        if (this.i instanceof AFBannerWebView) {
            ((AFBannerWebView) this.i).destroy();
        } else if (this.i instanceof com.appflood.d.a) {
            ((com.appflood.d.a) this.i).a();
        }
    }

    public final void onClick() {
        " onclick in bannerAdapter " + this.c;
        j.a();
        e.a().a(true, j.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, Integer.valueOf(this.c == 15 ? 4 : 1)));
    }

    public final void onClose() {
        if (this.b != null) {
            this.b.onClose();
        }
    }

    public final void onComplete() {
        if (this.b != null) {
            this.b.onComplete();
        }
        if (this.a && !this.h) {
            e();
        }
    }

    public final void onFinish(HashMap<String, Object> hashMap) {
        a((HashMap) hashMap);
    }

    public final void onFinish(JSONObject jSONObject) {
        int a = j.a(jSONObject, "result", 0);
        " onFinish " + a;
        j.a();
        if (a == 1) {
            this.j = j.a(jSONObject, "data");
            String a2 = j.a(this.j, AnalyticsSQLiteHelper.EVENT_LIST_TYPE, "");
            String a3 = j.a(this.j, "data", "");
            final HashMap hashMap = new HashMap();
            hashMap.put("click_cb_url", j.a(this.j, "click_cb_url", ""));
            hashMap.put("show_cb_url", j.a(this.j, "show_cb_url", ""));
            hashMap.put("redirect_url", j.a(this.j, "redirect_url", ""));
            hashMap.put("width", Integer.valueOf(j.a(this.j, "width", 0)));
            hashMap.put("height", Integer.valueOf(j.a(this.j, "height", 0)));
            hashMap.put("html_content", a3);
            hashMap.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, a2);
            f.a(new Runnable(this) {
                private /* synthetic */ a b;

                public final void run() {
                    a.a(this.b, hashMap);
                }
            });
            return;
        }
        c(false);
    }
}
