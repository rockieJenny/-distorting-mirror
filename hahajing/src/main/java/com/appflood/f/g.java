package com.appflood.f;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.appflood.b.b.a;
import com.appflood.c.d;
import com.appflood.c.e;
import com.appflood.e.b;
import com.appflood.e.j;
import com.appflood.e.k;
import com.givewaygames.goofyglass.BuildConfig;
import com.google.android.gms.cast.TextTrackStyle;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlrpc.android.IXMLRPCSerializer;

public final class g extends RelativeLayout {
    private float a = TextTrackStyle.DEFAULT_FONT_SCALE;
    private int b;
    private b c = new b((float) ((this.b << 1) + BuildConfig.VERSION_CODE), (float) ((this.b << 1) + 162));
    private b d = new b((float) ((this.b << 1) + 448), (float) ((this.b << 1) + 295));
    private boolean e;
    private Context f;
    private e g = null;
    private f h = null;
    private i[] i = null;
    private Paint j = new Paint();
    private b k = new b();
    private RectF l = new RectF();

    public g(Context context, boolean z, float f, int i) {
        boolean z2 = false;
        super(context);
        this.f = context;
        this.e = z;
        this.a = f;
        this.b = i;
        try {
            this.i = new i[4];
            if (this.e) {
                this.c = new b((float) ((i << 1) + BuildConfig.VERSION_CODE), (float) ((i << 1) + 162));
                this.d = new b((float) ((i << 1) + 448), (float) ((i << 1) + 295));
            } else {
                this.c = new b((float) ((i << 1) + 169), (float) ((i << 1) + TransportMediator.KEYCODE_MEDIA_RECORD));
                this.d = new b((float) ((i << 1) + 486), (float) ((i << 1) + 251));
            }
            this.c.a(f);
            this.d.a(f);
            setWillNotDraw(false);
            if (this.e) {
                a();
            } else {
                b();
            }
            if (!j.g(d.m)) {
                if (com.appflood.c.g.a().a != null) {
                    z2 = true;
                }
                if (z2) {
                    a(com.appflood.c.g.a().a);
                }
                new com.appflood.b.b(d.m, null).a(new a(this) {
                    private /* synthetic */ g b;

                    public final void a(int i) {
                        e.a().a(false, 2);
                    }

                    public final void a(com.appflood.b.b bVar) {
                        JSONObject e = j.e(bVar.c());
                        int a = j.a(e, "ret", -1);
                        e = j.e(j.a(e, "data", null));
                        d.x = j.a(e, "show_cb_url", "");
                        String a2 = j.a(e, "data", "");
                        if (a == 0) {
                            JSONArray f = j.f(a2);
                            JSONObject[] jSONObjectArr = new JSONObject[f.length()];
                            for (int i = 0; i < f.length(); i++) {
                                try {
                                    jSONObjectArr[i] = f.getJSONObject(i);
                                } catch (Throwable th) {
                                    j.a(th, "Failed to get a jsonobject from JsonArray");
                                }
                            }
                            if (z2) {
                                com.appflood.c.g.a().a = jSONObjectArr;
                            } else {
                                this.b.a(jSONObjectArr);
                            }
                        }
                    }
                }).f();
            }
        } catch (Throwable th) {
        }
    }

    private void a() {
        int i = 0;
        int i2 = (int) (((float) (16 - (this.b * 1))) * this.a);
        this.g = new e(this.f, this.a, this.b);
        this.g.setId(5);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) this.d.a, (int) this.d.b);
        layoutParams.addRule(14);
        layoutParams.addRule(10);
        addView(this.g, layoutParams);
        RelativeLayout.LayoutParams[] layoutParamsArr = new RelativeLayout.LayoutParams[4];
        for (int i3 = 0; i3 < this.i.length; i3++) {
            this.i[i3] = new i(this.f, this.a, this.e);
            layoutParamsArr[i3] = new RelativeLayout.LayoutParams((int) this.c.a, (int) this.c.b);
        }
        this.i[0].setId(6);
        layoutParamsArr[0].addRule(9);
        layoutParamsArr[0].addRule(3, 5);
        layoutParamsArr[0].topMargin = i2;
        this.i[1].setId(7);
        layoutParamsArr[1].addRule(11);
        layoutParamsArr[1].addRule(6, 6);
        layoutParamsArr[2].addRule(9);
        layoutParamsArr[2].addRule(12);
        layoutParamsArr[3].addRule(11);
        layoutParamsArr[3].addRule(12);
        while (i < this.i.length) {
            addView(this.i[i], layoutParamsArr[i]);
            i++;
        }
    }

    static /* synthetic */ void a(g gVar, int i, Bitmap bitmap) {
        if (bitmap != null) {
            a aVar;
            if (i == 0) {
                try {
                    aVar = gVar.e ? gVar.g : gVar.h;
                } catch (Throwable th) {
                    j.b(th, "bitmapChanged error = " + i);
                    return;
                }
            }
            aVar = gVar.i[i - 1];
            aVar.a(bitmap);
        }
    }

    private void a(JSONObject[] jSONObjectArr) {
        e.a().a(true, 2);
        int i = 0;
        while (i < 5) {
            JSONObject jSONObject = jSONObjectArr[i];
            String a = j.a(jSONObject, IXMLRPCSerializer.TAG_NAME, null);
            String a2 = j.a(jSONObject, "desc", null);
            String a3 = j.a(jSONObject, "click_url", "");
            if (!a3.contains("://")) {
                a3 = k.b(a3, null).toString();
            }
            final String a4 = j.a(jSONObject, "back_url", "");
            a aVar = i == 0 ? this.e ? this.g : this.h : this.i[i - 1];
            aVar.a(a, a2);
            aVar.setOnClickListener(new OnClickListener(this) {
                private /* synthetic */ g c;

                public final void onClick(View view) {
                    try {
                        if (!j.g(a4)) {
                            new com.appflood.b.b(a4, null).b(true);
                        }
                        e.a().a(true, j.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, Integer.valueOf(2)));
                    } catch (Throwable th) {
                        return;
                    }
                    this.c.f.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(a3)));
                }
            });
            i++;
        }
        for (int i2 = 0; i2 < jSONObjectArr.length; i2++) {
            new com.appflood.b.b(jSONObjectArr[i2]).a(new a(this) {
                private /* synthetic */ g b;

                public final void a(int i) {
                    j.c("Panel image url connection failed statuscode = " + i);
                }

                public final void a(com.appflood.b.b bVar) {
                    try {
                        g.a(this.b, i2, bVar.d());
                    } catch (Throwable th) {
                        j.a(th, "Panel Image failed");
                    }
                }
            }).f();
        }
    }

    private void b() {
        int i = 0;
        int i2 = (int) (((float) (21 - (this.b * 2))) * this.a);
        int i3 = (int) (((float) (25 - this.b)) * this.a);
        int i4 = (int) (((float) (10 - this.b)) * this.a);
        this.h = new f(this.f, this.a, this.b);
        this.h.setId(5);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) this.d.a, (int) this.d.b);
        layoutParams.addRule(14);
        layoutParams.addRule(10);
        addView(this.h, layoutParams);
        RelativeLayout.LayoutParams[] layoutParamsArr = new RelativeLayout.LayoutParams[4];
        for (int i5 = 0; i5 < this.i.length; i5++) {
            this.i[i5] = new i(this.f, this.a, this.e);
            layoutParamsArr[i5] = new RelativeLayout.LayoutParams((int) this.c.a, (int) this.c.b);
        }
        this.i[0].setId(6);
        layoutParamsArr[0].addRule(9);
        layoutParamsArr[0].addRule(3, 5);
        layoutParamsArr[0].leftMargin = i3;
        layoutParamsArr[0].topMargin = -i4;
        this.i[1].setId(7);
        layoutParamsArr[1].addRule(1, 6);
        layoutParamsArr[1].addRule(6, 6);
        layoutParamsArr[1].leftMargin = -i2;
        this.i[2].setId(8);
        layoutParamsArr[2].addRule(1, 7);
        layoutParamsArr[2].addRule(6, 7);
        layoutParamsArr[2].leftMargin = -i2;
        layoutParamsArr[3].addRule(1, 8);
        layoutParamsArr[3].addRule(6, 7);
        layoutParamsArr[3].leftMargin = -i2;
        while (i < this.i.length) {
            addView(this.i[i], layoutParamsArr[i]);
            i++;
        }
    }

    protected final void onDraw(Canvas canvas) {
        try {
            if (!this.e) {
                b bVar = this.k;
                bVar.a = 722.0f;
                bVar.b = 131.0f;
                this.k.a(this.a);
                this.l.set(0.0f, (float) ((getHeight() - ((int) this.k.b)) - 1), (float) (((int) this.k.a) - 1), (float) (getHeight() - 1));
                this.j.setColor(-526083);
                this.j.setStyle(Style.FILL);
                canvas.drawRoundRect(this.l, 3.0f, 3.0f, this.j);
                this.j.setColor(-4010527);
                this.j.setStyle(Style.STROKE);
                this.j.clearShadowLayer();
                canvas.drawRoundRect(this.l, 3.0f, 3.0f, this.j);
            }
        } catch (Throwable th) {
        }
        super.onDraw(canvas);
    }
}
