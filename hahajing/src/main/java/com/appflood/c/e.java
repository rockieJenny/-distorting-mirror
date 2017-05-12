package com.appflood.c;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.appflood.AFFullScreenActivity;
import com.appflood.AFInterstitialActivity;
import com.appflood.AFListActivity;
import com.appflood.AFPanelActivity;
import com.appflood.AppFlood.AFEventDelegate;
import com.appflood.AppFlood.AFRequestDelegate;
import com.appflood.b.b;
import com.appflood.c.d.a;
import com.appflood.e.c;
import com.appflood.e.j;
import com.google.android.gms.drive.DriveFile;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class e {
    private static e c;
    public AFEventDelegate a = null;
    public boolean b = false;

    public class AnonymousClass10 implements a {
        final /* synthetic */ AFRequestDelegate a;
        final /* synthetic */ e b;

        public AnonymousClass10(e eVar, AFRequestDelegate aFRequestDelegate) {
            this.b = eVar;
            this.a = aFRequestDelegate;
        }

        public final void a() {
            if (j.g(d.t)) {
                e eVar = this.b;
                e.a(this.a, false, null);
                return;
            }
            new b(d.t, null).a(new b.a(this) {
                private /* synthetic */ AnonymousClass10 a;

                {
                    this.a = r1;
                }

                public final void a(int i) {
                    e eVar = this.a.b;
                    e.a(this.a.a, false, null);
                }

                public final void a(b bVar) {
                    JSONObject e = j.e(bVar.c());
                    if (j.a(e, "ret", -1) == 0) {
                        JSONArray f = j.f(j.a(e, "data", null));
                        e eVar = this.a.b;
                        e.a(this.a.a, true, f);
                        return;
                    }
                    e eVar2 = this.a.b;
                    e.a(this.a.a, false, null);
                }
            }).b(true);
        }
    }

    public class AnonymousClass1 implements a {
        private /* synthetic */ Activity a;
        private /* synthetic */ int b;

        public AnonymousClass1(Activity activity, int i) {
            this.a = activity;
            this.b = i;
        }

        public final void a() {
            Intent intent = new Intent(this.a, AFListActivity.class);
            intent.putExtra("isFull", com.appflood.AFListActivity.AnonymousClass1.a(this.a));
            intent.putExtra("isPortrait", com.appflood.AFListActivity.AnonymousClass1.b(this.a));
            intent.putExtra("showType", this.b);
            com.appflood.AFListActivity.AnonymousClass1.a(this.a, intent);
        }
    }

    class AnonymousClass2 implements b.a {
        private /* synthetic */ int a;
        private /* synthetic */ int b;
        private /* synthetic */ AtomicInteger c;
        private /* synthetic */ AFRequestDelegate d;

        AnonymousClass2(int i, int i2, AtomicInteger atomicInteger, AFRequestDelegate aFRequestDelegate) {
            this.a = i;
            this.b = i2;
            this.c = atomicInteger;
            this.d = aFRequestDelegate;
        }

        public final void a(int i) {
            try {
                if (this.d != null) {
                    JSONObject a = j.a("result", Boolean.valueOf(false));
                    a.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 2);
                    this.d.onFinish(a);
                }
            } catch (Throwable th) {
            }
        }

        public final void a(b bVar) {
            try {
                if (this.a < this.b && this.c.incrementAndGet() == this.b && this.d != null) {
                    JSONObject a = j.a("result", Boolean.valueOf(true));
                    a.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 2);
                    this.d.onFinish(a);
                }
            } catch (Throwable th) {
            }
        }
    }

    public class AnonymousClass3 implements a {
        private /* synthetic */ int a;
        private /* synthetic */ AFRequestDelegate b;
        private /* synthetic */ int c;
        private /* synthetic */ e d;

        public AnonymousClass3(e eVar, int i, AFRequestDelegate aFRequestDelegate, int i2) {
            this.d = eVar;
            this.a = i;
            this.b = aFRequestDelegate;
            this.c = i2;
        }

        public final void a() {
            j.a();
            if ((this.a & 4) > 0 && this.b != null) {
                JSONObject a = j.a("result", Boolean.valueOf(true));
                try {
                    a.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.b.onFinish(a);
            }
            if ((this.a & 2) > 0) {
                e eVar = this.d;
                AFRequestDelegate aFRequestDelegate = this.b;
                JSONObject[] jSONObjectArr = g.a().a;
                if (jSONObjectArr != null) {
                    j.a();
                    int length = jSONObjectArr.length;
                    AtomicInteger atomicInteger = new AtomicInteger();
                    for (int i = 0; i < jSONObjectArr.length; i++) {
                        new b(jSONObjectArr[i]).a(new AnonymousClass2(i, length, atomicInteger, aFRequestDelegate)).f();
                    }
                } else if (aFRequestDelegate != null) {
                    try {
                        JSONObject a2 = j.a("result", Boolean.valueOf(false));
                        a2.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 2);
                        aFRequestDelegate.onFinish(a2);
                    } catch (Exception e2) {
                    }
                }
            }
            if ((this.a & 8) > 0) {
                e eVar2 = this.d;
                g.a().a(this.c, this.b);
            }
        }
    }

    class AnonymousClass4 implements a {
        private /* synthetic */ Context a;
        private /* synthetic */ int b;
        private /* synthetic */ int c;
        private /* synthetic */ int d;
        private /* synthetic */ int e;
        private /* synthetic */ AFRequestDelegate f;

        AnonymousClass4(Context context, int i, int i2, int i3, int i4, AFRequestDelegate aFRequestDelegate) {
            this.a = context;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = aFRequestDelegate;
        }

        public final void a() {
            g.a().a(this.a, this.b, this.c, this.d, this.e, this.f, true);
        }
    }

    class AnonymousClass5 implements a {
        private /* synthetic */ Activity a;

        AnonymousClass5(Activity activity) {
            this.a = activity;
        }

        public final void a() {
            Intent intent = new Intent(this.a, AFFullScreenActivity.class);
            intent.putExtra("isFull", com.appflood.AFListActivity.AnonymousClass1.a(this.a));
            intent.putExtra("isPortrait", com.appflood.AFListActivity.AnonymousClass1.b(this.a));
            intent.putExtra("titlebar", com.appflood.AFListActivity.AnonymousClass1.c(this.a));
            com.appflood.AFListActivity.AnonymousClass1.a(this.a, intent);
        }
    }

    public class AnonymousClass6 implements a {
        private /* synthetic */ Activity a;
        private /* synthetic */ int b;

        public AnonymousClass6(Activity activity, int i) {
            this.a = activity;
            this.b = i;
        }

        public final void a() {
            Intent intent = new Intent(this.a, AFPanelActivity.class);
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            intent.putExtra("showType", this.b);
            intent.putExtra("titlebar", com.appflood.AFListActivity.AnonymousClass1.c(this.a));
            intent.putExtra("isPortrait", com.appflood.AFListActivity.AnonymousClass1.b(this.a));
            intent.putExtra("isFull", com.appflood.AFListActivity.AnonymousClass1.a(this.a));
            com.appflood.AFListActivity.AnonymousClass1.a(this.a, intent);
        }
    }

    public class AnonymousClass7 implements a {
        private /* synthetic */ Activity a;
        private /* synthetic */ e b;

        public AnonymousClass7(e eVar, Activity activity) {
            this.b = eVar;
            this.a = activity;
        }

        public final void a() {
            if (com.appflood.AFListActivity.AnonymousClass1.b(this.a)) {
                Intent intent = new Intent(this.a, AFInterstitialActivity.class);
                intent.putExtra("isFull", com.appflood.AFListActivity.AnonymousClass1.a(this.a));
                this.a.startActivity(intent);
                return;
            }
            this.b.a(this.a);
        }
    }

    public class AnonymousClass8 implements a {
        final /* synthetic */ AFRequestDelegate a;
        final /* synthetic */ e b;
        private /* synthetic */ int c;

        public AnonymousClass8(e eVar, AFRequestDelegate aFRequestDelegate, int i) {
            this.b = eVar;
            this.a = aFRequestDelegate;
            this.c = i;
        }

        public final void a() {
            if (j.g(d.v)) {
                e eVar = this.b;
                e.a(this.a, false, -5);
                return;
            }
            new b(d.v.replace("{1}", Integer.toString(this.c)), null).a(new b.a(this) {
                private /* synthetic */ AnonymousClass8 a;

                {
                    this.a = r1;
                }

                public final void a(int i) {
                    e eVar = this.a.b;
                    e.a(this.a.a, false, -5);
                }

                public final void a(b bVar) {
                    String c = bVar.c();
                    e eVar = this.a.b;
                    e.a(this.a.a, true, j.a(c, -5));
                }
            }).b(true);
        }
    }

    public class AnonymousClass9 implements a {
        final /* synthetic */ AFRequestDelegate a;
        final /* synthetic */ e b;

        public AnonymousClass9(e eVar, AFRequestDelegate aFRequestDelegate) {
            this.b = eVar;
            this.a = aFRequestDelegate;
        }

        public final void a() {
            if (j.g(d.u)) {
                e eVar = this.b;
                e.b(this.a, false, -1);
                return;
            }
            new b(d.u, null).a(new b.a(this) {
                private /* synthetic */ AnonymousClass9 a;

                {
                    this.a = r1;
                }

                public final void a(int i) {
                    e eVar = this.a.b;
                    e.b(this.a.a, false, -1);
                }

                public final void a(b bVar) {
                    String c = bVar.c();
                    e eVar = this.a.b;
                    e.b(this.a.a, true, j.a(c, -1));
                }
            }).b(true);
        }
    }

    private e() {
    }

    public static e a() {
        if (c == null) {
            c = new e();
        }
        return c;
    }

    public static void a(Context context, int i, int i2, int i3, int i4, AFRequestDelegate aFRequestDelegate) {
        d.a(new AnonymousClass4(context, i, i2, i3, i4, aFRequestDelegate));
    }

    static /* synthetic */ void a(AFRequestDelegate aFRequestDelegate, boolean z, int i) {
        try {
            aFRequestDelegate.onFinish(j.a("success", Boolean.valueOf(z)).put("point", i));
        } catch (Throwable th) {
        }
    }

    public static void a(AFRequestDelegate aFRequestDelegate, boolean z, JSONArray jSONArray) {
        try {
            aFRequestDelegate.onFinish(j.a("success", Boolean.valueOf(z)).put("data", jSONArray));
        } catch (Throwable th) {
        }
    }

    static /* synthetic */ void b(AFRequestDelegate aFRequestDelegate, boolean z, int i) {
        try {
            aFRequestDelegate.onFinish(j.a("success", Boolean.valueOf(z)).put("point", i));
        } catch (Throwable th) {
        }
    }

    public final void a(Activity activity) {
        if (!this.b) {
            j.c("AppFlood not initialized");
        } else if (activity == null) {
            j.c("activity is null");
        } else {
            d.a(activity, new AnonymousClass5(activity));
        }
    }

    public final synchronized void a(Context context, String str, String str2, String str3, String str4, int i, int i2) {
        boolean z = true;
        synchronized (this) {
            if (!this.b) {
                d.F = i;
                j.d("Papaya AppFlood SDK init version V2.21");
                try {
                    c.a(context);
                    if (i2 != 0) {
                        z = i2 == 1 ? false : c.a();
                    }
                    d.a(z);
                    if (!d.a) {
                        str3 = str;
                    }
                    d.D = str3;
                    if (!d.a) {
                        str4 = str2;
                    }
                    d.E = str4;
                    f.a(context, true);
                    f.a();
                } catch (Throwable th) {
                    j.a(th, "initialize failed");
                }
                this.b = true;
            }
        }
    }

    public final void a(boolean z, int i) {
        if (this.a != null) {
            this.a.onFinish(z, i);
        }
    }

    public final void a(final boolean z, final JSONObject jSONObject) {
        if (this.a != null) {
            f.a(new TimerTask(this) {
                private /* synthetic */ e c;

                public final void run() {
                    try {
                        if (z) {
                            this.c.a.onClick(jSONObject);
                        } else {
                            this.c.a.onClose(jSONObject);
                        }
                    } catch (Throwable th) {
                    }
                }
            }, 0);
        }
    }

    public final synchronized void b() {
        if (this.b) {
            j.c("dddddddddddddddd");
            this.a = null;
            f.f();
            this.b = false;
            d.j = false;
            d.D = null;
            d.E = null;
            d.k = null;
            d.m = null;
            d.n = null;
            d.t = null;
            d.u = null;
            d.v = null;
            d.B = 30000;
            d.y = null;
            d.o = null;
            d.p = null;
            d.q = null;
            d.r = null;
            d.H = true;
            d.G = 0;
        }
    }
}
