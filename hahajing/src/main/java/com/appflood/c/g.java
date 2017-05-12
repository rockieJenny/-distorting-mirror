package com.appflood.c;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import com.appflood.AppFlood.AFRequestDelegate;
import com.appflood.b.b;
import com.appflood.b.b.a;
import com.appflood.e.c;
import com.appflood.e.j;
import com.appflood.e.k;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlrpc.android.IXMLRPCSerializer;

public final class g {
    private static g l;
    public JSONObject[] a;
    JSONObject[] b;
    JSONObject c;
    JSONObject d;
    public int e = 8000;
    ArrayList<HashMap<String, String>> f = null;
    public ArrayList<HashMap<String, String>> g = null;
    public ArrayList<HashMap<String, String>> h = null;
    boolean i = true;
    boolean j = false;
    private JSONObject[][] k;

    private g() {
    }

    public static g a() {
        if (l == null) {
            l = new g();
        }
        return l;
    }

    static void a(JSONArray jSONArray) {
        if (jSONArray != null) {
            JSONObject[] jSONObjectArr = new JSONObject[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    jSONObjectArr[i] = jSONArray.getJSONObject(i);
                } catch (Throwable th) {
                    j.a(th, "Failed to get a jsonobject from JsonArray");
                }
            }
            a().b = jSONObjectArr;
        }
    }

    static void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            d.x = j.a(jSONObject, "show_cb_url", null);
            String a = j.a(jSONObject, "data", null);
            if (!j.g(a)) {
                JSONArray f = j.f(a);
                JSONObject[] jSONObjectArr = new JSONObject[f.length()];
                for (int i = 0; i < f.length(); i++) {
                    try {
                        jSONObjectArr[i] = f.getJSONObject(i);
                    } catch (Throwable th) {
                        j.a(th, "Failed to get a jsonobject from JsonArray");
                    }
                }
                a().a = jSONObjectArr;
            }
        }
    }

    public final ArrayList<HashMap<String, String>> a(int i) {
        return i == 2 ? this.f : i == 1 ? this.h : this.g;
    }

    public final synchronized void a(final int i, final AFRequestDelegate aFRequestDelegate) {
        if (!j.g(d.n)) {
            String str = "";
            if (i == 0) {
                str = "game";
            } else if (i == 1) {
                str = SettingsJsonConstants.APP_KEY;
            } else if (i == 4 || i == 3) {
                str = "mix";
            }
            new b(d.n + str, null).a(new a(this) {
                private /* synthetic */ g c;

                public final void a(int i) {
                    aFRequestDelegate.onFinish(j.a("result", Integer.valueOf(0)));
                }

                public final void a(b bVar) {
                    boolean z = true;
                    int i = 0;
                    try {
                        JSONObject e = j.e(bVar.c());
                        int a = j.a(e, "ret", -1);
                        "aaaaaaaaa TYPE = " + i + "  url = " + bVar.a().toString();
                        j.a();
                        if (a == 0) {
                            g gVar = this.c;
                            if (j.a(e, "use_default", 0) == 1) {
                                z = false;
                            }
                            gVar.i = z;
                            Object obj = "";
                            if (!this.c.i) {
                                obj = j.a(e, "bg_url", "");
                            }
                            if (i >= 3 || i == 2) {
                                if (this.c.h == null) {
                                    this.c.h = new ArrayList();
                                } else {
                                    this.c.h.clear();
                                }
                                if (this.c.g == null) {
                                    this.c.g = new ArrayList();
                                } else {
                                    this.c.g.clear();
                                }
                                if (i == 2) {
                                    if (this.c.f == null) {
                                        this.c.f = new ArrayList();
                                    } else {
                                        this.c.f.clear();
                                    }
                                }
                            } else if (i == 0) {
                                if (this.c.g == null) {
                                    this.c.g = new ArrayList();
                                } else {
                                    this.c.g.clear();
                                }
                            } else if (this.c.h == null) {
                                this.c.h = new ArrayList();
                            } else {
                                this.c.h.clear();
                            }
                            JSONArray f = j.f(j.a(e, "data", null));
                            while (i < f.length()) {
                                JSONObject jSONObject = f.getJSONObject(i);
                                HashMap hashMap = new HashMap();
                                hashMap.put(IXMLRPCSerializer.TAG_NAME, j.a(jSONObject, IXMLRPCSerializer.TAG_NAME, ""));
                                hashMap.put("des", j.a(jSONObject, "desc", ""));
                                hashMap.put("click_url", j.a(jSONObject, "click_url", ""));
                                hashMap.put("show_cb_url", j.a(jSONObject, "show_cb_url", ""));
                                hashMap.put("back_url", j.a(jSONObject, "back_url", ""));
                                hashMap.put("icon_url", j.a(jSONObject, "icon_url", ""));
                                if (i == 4 || i == 3 || i == 2) {
                                    if ("game".equals(j.a(jSONObject, "app_type", "game")) && this.c.g != null) {
                                        this.c.g.add(hashMap);
                                    } else if (this.c.h != null) {
                                        this.c.h.add(hashMap);
                                    }
                                    if (i == 2 && this.c.f != null) {
                                        this.c.f.add(hashMap);
                                    }
                                } else {
                                    ArrayList a2 = this.c.a(i);
                                    if (a2 != null) {
                                        a2.add(hashMap);
                                    }
                                }
                                i++;
                            }
                            if (aFRequestDelegate != null) {
                                aFRequestDelegate.onFinish(j.a("result", Integer.valueOf(1)).put("listbgurl", obj).put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 8));
                            }
                        } else if (aFRequestDelegate != null) {
                            aFRequestDelegate.onFinish(j.a("result", Integer.valueOf(0)).put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 8));
                        }
                    } catch (Throwable e2) {
                        j.a(e2, "Failed to get a JsonArray");
                    }
                }
            }).f();
        }
    }

    public final void a(int i, AFRequestDelegate aFRequestDelegate, boolean z) {
        boolean z2;
        final int i2;
        final boolean z3;
        final AFRequestDelegate aFRequestDelegate2;
        if (this.k == null) {
            this.k = new JSONObject[2][];
        }
        Object obj = a().b;
        final JSONObject a = j.a("result", Integer.valueOf(1));
        final boolean z4 = obj != null && obj.length > 0 && (i == 10 || i == 13);
        if (this.k[i == 17 ? 0 : 1] != null) {
            if (this.k[i == 17 ? 0 : 1].length > 0 && (i == 16 || i == 17)) {
                z2 = true;
                if (z4 || z2) {
                    a.put(IXMLRPCSerializer.TYPE_ARRAY, obj);
                    if (aFRequestDelegate != null) {
                        aFRequestDelegate.onFinish(a);
                    }
                    if (z) {
                        return;
                    }
                }
                if ((i == 13 || !z4) && !j.g(d.k)) {
                    i2 = i;
                    z3 = z;
                    aFRequestDelegate2 = aFRequestDelegate;
                    new b(d.l.replace("{1}", i), null).a(new a(this) {
                        private /* synthetic */ g g;

                        public final void a(int i) {
                            if (!z4 && !z2) {
                                f.a(new Runnable(this) {
                                    private /* synthetic */ AnonymousClass3 a;

                                    {
                                        this.a = r1;
                                    }

                                    public final void run() {
                                        try {
                                            if (aFRequestDelegate2 != null) {
                                                a.put("result", 0);
                                                aFRequestDelegate2.onFinish(a);
                                            }
                                        } catch (Throwable th) {
                                        }
                                    }
                                });
                            }
                        }

                        public final void a(b bVar) {
                            boolean z = true;
                            boolean z2 = false;
                            JSONObject e = j.e(bVar.c());
                            int a = j.a(e, "ret", -1);
                            JSONArray f = j.f(j.a(e, "data", null));
                            if (a == 0) {
                                JSONObject[] jSONObjectArr = new JSONObject[f.length()];
                                for (int i = 0; i < f.length(); i++) {
                                    try {
                                        jSONObjectArr[i] = f.getJSONObject(i);
                                    } catch (Throwable th) {
                                        j.a(th, "Failed to get a jsonobject from JsonArray");
                                    }
                                }
                                if (z4) {
                                    g.a().b = jSONObjectArr;
                                } else if (z2) {
                                    r0 = this.g;
                                    if (i2 == 17) {
                                        z2 = true;
                                    }
                                    r0.a(z2, jSONObjectArr);
                                } else {
                                    if (z3) {
                                        r0 = this.g;
                                        if (i2 != 17) {
                                            z = false;
                                        }
                                        r0.a(z, jSONObjectArr);
                                    }
                                    try {
                                        a.put(IXMLRPCSerializer.TYPE_ARRAY, jSONObjectArr);
                                    } catch (Throwable e2) {
                                        j.b(e2, "json error");
                                    }
                                    if (aFRequestDelegate2 != null) {
                                        aFRequestDelegate2.onFinish(a);
                                    }
                                }
                            } else if (aFRequestDelegate2 != null) {
                                try {
                                    a.put("result", 0);
                                    if (aFRequestDelegate2 != null) {
                                        aFRequestDelegate2.onFinish(a);
                                    }
                                } catch (Throwable e22) {
                                    j.b(e22, "json error");
                                }
                            }
                        }
                    }).f();
                }
                return;
            }
        }
        z2 = false;
        try {
            a.put(IXMLRPCSerializer.TYPE_ARRAY, obj);
        } catch (Throwable e) {
            j.b(e, "json error");
        }
        if (aFRequestDelegate != null) {
            aFRequestDelegate.onFinish(a);
        }
        if (z) {
            return;
        }
        if (i == 13) {
        }
        i2 = i;
        z3 = z;
        aFRequestDelegate2 = aFRequestDelegate;
        new b(d.l.replace("{1}", i), null).a(/* anonymous class already generated */).f();
    }

    public final void a(final Context context) {
        "send refer info refer =  " + c.y;
        j.a();
        if (!j.g(c.y) && !this.j && !j.g(d.s)) {
            this.j = true;
            b bVar = new b(d.s + URLEncoder.encode(c.y));
            bVar.a(new a(this) {
                private /* synthetic */ g b;

                public final void a(int i) {
                    this.b.j = false;
                }

                public final void a(b bVar) {
                    com.appflood.e.a.b(context, "google_refer");
                    c.y = null;
                    this.b.j = false;
                }
            });
            bVar.b(false);
        }
    }

    public final void a(Context context, int i, int i2, int i3, int i4, AFRequestDelegate aFRequestDelegate, boolean z) {
        if (!d.H) {
            return;
        }
        if ((d.F & 1) > 0 || (d.F & 4) > 0) {
            String str;
            b bVar;
            final JSONObject jSONObject;
            final boolean z2;
            final AFRequestDelegate aFRequestDelegate2;
            final boolean z3 = i4 == 15;
            Map hashMap = new HashMap();
            hashMap.put("width", Integer.valueOf(i));
            hashMap.put("height", Integer.valueOf(i2));
            JSONArray jSONArray = new JSONArray();
            jSONArray.put("jpg");
            jSONArray.put("mp4");
            jSONArray.put("png");
            jSONArray.put("gif");
            hashMap.put("format", jSONArray.toString());
            hashMap.put("duration", "[60,0]");
            JSONObject jSONObject2 = new JSONObject();
            try {
                int i5;
                PackageManager packageManager;
                jSONObject2.put("av", c.d);
                jSONObject2.put("br", c.l);
                jSONObject2.put("dn", c.c);
                jSONObject2.put("pm", c.k);
                jSONObject2.put("mf", c.m);
                jSONObject2.put("dp", c.n);
                jSONObject2.put("pn", c.f);
                jSONObject2.put("so", c.o);
                jSONObject2.put("sn", c.p);
                jSONObject2.put("sm", c.s);
                jSONObject2.put("mc", c.u);
                jSONObject2.put("wc", c.v);
                jSONObject2.put("lc", c.w);
                jSONObject2.put("ll", c.x);
                int i6 = c.g;
                int i7 = c.h;
                if (context instanceof Activity) {
                    boolean b = com.appflood.AFListActivity.AnonymousClass1.b((Activity) context);
                    if ((b && i6 > i7) || (!b && i6 < i7)) {
                        i5 = i6;
                        i6 = i7;
                        jSONObject2.put("sw", i6);
                        jSONObject2.put("sh", i5);
                        jSONObject2.put(AnalyticsEvent.EVENT_ID, context.getPackageName());
                        packageManager = context.getPackageManager();
                        str = (String) packageManager.getApplicationInfo(context.getPackageName(), 0).loadLabel(packageManager);
                        i6 = packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
                        " rtb rtb  label = " + str + " version =" + i6 + "  " + context.getPackageName();
                        j.a();
                        if (!j.g(str)) {
                            jSONObject2.put("na", str);
                        }
                        if (i6 > 0) {
                            jSONObject2.put("ver", i6);
                        }
                        jSONObject2.put("pos", i3);
                        jSONObject2.put("ua", c.z);
                        if (c.q != 0.0d) {
                            jSONObject2.put("xx", c.q);
                            jSONObject2.put("yy", c.r);
                        }
                        hashMap.put("data", k.d(jSONObject2.toString()));
                        str = j.a(d.i);
                        " UserData = " + str;
                        j.a();
                        if (!j.g(str)) {
                            hashMap.put("user_data", str);
                        }
                        if (!j.g(c.a)) {
                            hashMap.put("didsha1", com.appflood.e.a.a(c.a));
                            hashMap.put("didmd5", com.appflood.e.a.b(c.a));
                        }
                        if (!j.g(c.b)) {
                            hashMap.put("dpidsha1", com.appflood.e.a.a(c.b));
                            hashMap.put("dpidmd5", com.appflood.e.a.b(c.b));
                        }
                        if (!j.g(d.k)) {
                            bVar = new b(d.k.replace("{1}", String.valueOf(i4)), hashMap);
                            jSONObject2 = j.a("result", Integer.valueOf(1));
                            jSONObject = z3 ? this.d : this.c;
                            z + "  fullscreen " + z3 + " mHtmlBannerData " + jSONObject;
                            j.a();
                            if (jSONObject != null && ((z && !z3) || z3)) {
                                try {
                                    jSONObject2.put("data", jSONObject);
                                } catch (JSONException e) {
                                }
                                if (aFRequestDelegate != null) {
                                    aFRequestDelegate.onFinish(jSONObject2);
                                    if (z3) {
                                        this.c = null;
                                        return;
                                    } else {
                                        this.d = null;
                                        return;
                                    }
                                }
                            }
                            z2 = z;
                            aFRequestDelegate2 = aFRequestDelegate;
                            bVar.a(new a(this) {
                                private /* synthetic */ g f;

                                public final void a(int i) {
                                    try {
                                        if (aFRequestDelegate2 != null) {
                                            jSONObject2.put("result", 0);
                                            aFRequestDelegate2.onFinish(jSONObject2);
                                        }
                                    } catch (JSONException e) {
                                    }
                                }

                                public final void a(b bVar) {
                                    int i = 0;
                                    String c = bVar.c();
                                    " requestHtmlBanner finish " + bVar.a().toString();
                                    j.a();
                                    if (c.length() <= 200) {
                                        " cont " + c;
                                        j.a();
                                    }
                                    JSONObject e = j.e(c);
                                    if (j.a(e, "ret", 0) == 0) {
                                        try {
                                            e = j.a(e, "data");
                                            if (jSONObject != null) {
                                                i = 1;
                                            }
                                            if (z3 && i != 0) {
                                                this.f.d = e;
                                                return;
                                            } else if (!z2) {
                                                jSONObject2.put("data", e);
                                            } else if (z3) {
                                                this.f.d = e;
                                            } else {
                                                this.f.c = e;
                                                if (i != 0) {
                                                    return;
                                                }
                                            }
                                        } catch (JSONException e2) {
                                        }
                                    } else {
                                        jSONObject2.put("result", 0);
                                    }
                                    if (aFRequestDelegate2 != null) {
                                        aFRequestDelegate2.onFinish(jSONObject2);
                                    }
                                }
                            });
                            bVar.f();
                        }
                    }
                }
                i5 = i7;
                jSONObject2.put("sw", i6);
                jSONObject2.put("sh", i5);
                jSONObject2.put(AnalyticsEvent.EVENT_ID, context.getPackageName());
                packageManager = context.getPackageManager();
                str = (String) packageManager.getApplicationInfo(context.getPackageName(), 0).loadLabel(packageManager);
                i6 = packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
                " rtb rtb  label = " + str + " version =" + i6 + "  " + context.getPackageName();
                j.a();
                if (j.g(str)) {
                    jSONObject2.put("na", str);
                }
                if (i6 > 0) {
                    jSONObject2.put("ver", i6);
                }
            } catch (Throwable e2) {
                j.a(e2, "can't find the app");
            } catch (Throwable th) {
            }
            jSONObject2.put("pos", i3);
            jSONObject2.put("ua", c.z);
            if (c.q != 0.0d) {
                jSONObject2.put("xx", c.q);
                jSONObject2.put("yy", c.r);
            }
            hashMap.put("data", k.d(jSONObject2.toString()));
            str = j.a(d.i);
            " UserData = " + str;
            j.a();
            if (j.g(str)) {
                hashMap.put("user_data", str);
            }
            if (j.g(c.a)) {
                hashMap.put("didsha1", com.appflood.e.a.a(c.a));
                hashMap.put("didmd5", com.appflood.e.a.b(c.a));
            }
            if (j.g(c.b)) {
                hashMap.put("dpidsha1", com.appflood.e.a.a(c.b));
                hashMap.put("dpidmd5", com.appflood.e.a.b(c.b));
            }
            if (!j.g(d.k)) {
                bVar = new b(d.k.replace("{1}", String.valueOf(i4)), hashMap);
                jSONObject2 = j.a("result", Integer.valueOf(1));
                if (z3) {
                }
                z + "  fullscreen " + z3 + " mHtmlBannerData " + jSONObject;
                j.a();
                jSONObject2.put("data", jSONObject);
                if (aFRequestDelegate != null) {
                    aFRequestDelegate.onFinish(jSONObject2);
                    if (z3) {
                        this.c = null;
                        return;
                    } else {
                        this.d = null;
                        return;
                    }
                }
                z2 = z;
                aFRequestDelegate2 = aFRequestDelegate;
                bVar.a(/* anonymous class already generated */);
                bVar.f();
            }
        }
    }

    public final void a(boolean z, JSONObject[] jSONObjectArr) {
        this.k[z ? 0 : 1] = jSONObjectArr;
    }
}
