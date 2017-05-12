package com.appflood.c;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.appflood.AppFlood.AFRequestDelegate;
import com.appflood.b.b;
import com.appflood.e.j;
import com.appflood.e.k;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.Random;
import java.util.TimerTask;
import org.json.JSONObject;
import org.xmlrpc.android.IXMLRPCSerializer;

public final class c {
    JSONObject[] a;
    JSONObject b;
    public View c;
    boolean d = false;
    boolean e = true;
    boolean f = true;
    public boolean g = true;
    int h = 17;
    a i = null;
    private int j = -1;
    private float k = 5.0f;

    public interface a {
        void a();

        void a(boolean z);
    }

    public c(View view, a aVar) {
        this.c = view;
        this.f = false;
        this.d = true;
        this.h = 13;
        this.k = 0.0f;
        this.i = aVar;
        if (d.H) {
            d.a(new com.appflood.c.d.a(this) {
                private /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public final void a() {
                    c cVar = this.a;
                    g.a().a(cVar.h, new AFRequestDelegate(cVar) {
                        private /* synthetic */ c a;

                        {
                            this.a = r1;
                        }

                        public final void onFinish(JSONObject jSONObject) {
                            JSONObject[] jSONObjectArr;
                            int a = j.a(jSONObject, "result", 0);
                            try {
                                jSONObjectArr = (JSONObject[]) jSONObject.get(IXMLRPCSerializer.TYPE_ARRAY);
                            } catch (Throwable e) {
                                j.b(e, "json erro ");
                                jSONObjectArr = null;
                            }
                            if (a == 1 && jSONObjectArr != null) {
                                c cVar = this.a;
                                "setArray length = " + jSONObjectArr.length;
                                j.a();
                                if (jSONObjectArr.length > 0) {
                                    cVar.a = jSONObjectArr;
                                    if (cVar.a != null && cVar.a.length > 0) {
                                        f.a(new TimerTask(cVar) {
                                            private /* synthetic */ c a;

                                            {
                                                this.a = r1;
                                            }

                                            public final void run() {
                                                this.a.a();
                                            }
                                        }, 0);
                                    }
                                } else if (cVar.i != null && cVar.e) {
                                    cVar.i.a(false);
                                }
                            } else if (this.a.i != null && this.a.e) {
                                this.a.i.a(false);
                                this.a.e = false;
                            }
                        }
                    }, false);
                }
            });
        }
    }

    private int a(int i) {
        return this.h == 13 ? new Random().nextInt(this.a.length) : (i + 1) % this.a.length;
    }

    final void a() {
        if (this.c != null) {
            int i = 0;
            while (i < this.a.length) {
                try {
                    int a = a(this.j + i);
                    this.b = this.a[a];
                    "current json " + this.b + "  iiii " + a;
                    j.a();
                    b bVar = new b(this.a[a]);
                    if (bVar.e()) {
                        Bitmap d = bVar.d();
                        if (d != null) {
                            this.j = a;
                            a(d);
                            return;
                        }
                    }
                    i++;
                } catch (Throwable th) {
                    j.b(th, "error in refreshView");
                    return;
                }
            }
            this.j = a(this.j);
            this.b = this.a[this.j];
            new b(this.a[this.j]).a(new com.appflood.b.b.a(this) {
                private /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public final void a(int i) {
                    this.a.a(null);
                }

                public final void a(b bVar) {
                    this.a.a(bVar.d());
                }
            }).f();
        }
    }

    final void a(Bitmap bitmap) {
        if (this.c != null) {
            if (bitmap != null) {
                try {
                    if (this.k > 0.0f) {
                        bitmap = com.appflood.AFListActivity.AnonymousClass1.a(bitmap, this.k);
                    }
                } catch (Throwable th) {
                    j.b(th, "error in getRoundedCornerBitmap");
                }
            }
            if (bitmap == null) {
                try {
                    if (this.a.length <= 1) {
                        f.a(new Runnable(this) {
                            private /* synthetic */ c a;

                            {
                                this.a = r1;
                            }

                            public final void run() {
                                try {
                                    if (this.a.i != null && this.a.e) {
                                        this.a.i.a(false);
                                        this.a.e = false;
                                    }
                                } catch (Throwable th) {
                                }
                            }
                        });
                    } else {
                        a();
                    }
                } catch (Throwable th2) {
                }
            } else {
                if (this.a.length > 1) {
                    new b(this.a[a(this.j)]).f();
                    if (this.f) {
                        f.a(new TimerTask(this) {
                            private /* synthetic */ c a;

                            {
                                this.a = r1;
                            }

                            public final void run() {
                                this.a.a();
                            }
                        }, (long) d.B);
                    }
                }
                f.a(new Runnable(this) {
                    private /* synthetic */ c b;

                    public final void run() {
                        try {
                            if (this.b.c != null) {
                                bitmap.getWidth();
                                bitmap.getHeight();
                                "apptype " + j.a(this.b.b, "app_type", "");
                                j.a();
                                if (this.b.c instanceof ImageView) {
                                    ((ImageView) this.b.c).setImageBitmap(bitmap);
                                } else {
                                    this.b.c.setBackgroundDrawable(new BitmapDrawable(bitmap));
                                }
                                if (this.b.i != null) {
                                    if (this.b.d && (this.b.h == 10 || this.b.h == 13)) {
                                        int width = bitmap.getWidth();
                                        int height = bitmap.getHeight();
                                        this.b.b.put("width_bitmap", width);
                                        this.b.b.put("height_bitmap", height);
                                        a aVar = this.b.i;
                                        JSONObject jSONObject = this.b.b;
                                        aVar.a();
                                    }
                                    if (this.b.e) {
                                        this.b.i.a(true);
                                        this.b.e = false;
                                    }
                                }
                                if (this.b.c != null && this.b.c.isShown()) {
                                    new b(j.a(this.b.b, "show_cb_url", "")).f();
                                }
                            }
                        } catch (Throwable th) {
                            j.b(th, "error in adView.setImageBitmap");
                        }
                    }
                });
                try {
                    JSONObject jSONObject = this.a[this.j];
                    if (this.c != null) {
                        String a = j.a(jSONObject, "click_url", "");
                        if (!a.contains("://")) {
                            a = k.b(a, null).toString();
                        }
                        final String a2 = j.a(jSONObject, "back_url", "");
                        this.c.setOnClickListener(new OnClickListener(this) {
                            private /* synthetic */ c c;

                            public final void onClick(View view) {
                                try {
                                    if (!j.g(a2)) {
                                        new b(a2, null).b(true);
                                    }
                                    e a = e.a();
                                    String str = AnalyticsSQLiteHelper.EVENT_LIST_TYPE;
                                    int i = this.c.h == 13 ? 128 : this.c.h == 10 ? 4 : 1;
                                    a.a(true, j.a(str, Integer.valueOf(i)));
                                } catch (Throwable th) {
                                    return;
                                }
                                if (!this.c.f && this.c.g && this.c.a.length > 1) {
                                    this.c.a();
                                }
                                this.c.c.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(a)));
                            }
                        });
                    }
                } catch (Throwable th3) {
                    j.b(th3, "error in generateView");
                }
            }
        }
    }
}
