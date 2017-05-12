package com.appflood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import com.appflood.AppFlood.AFRequestDelegate;
import com.appflood.c.d;
import com.appflood.c.e;
import com.appflood.c.f;
import com.appflood.c.g;
import com.appflood.e.j;
import com.appflood.e.k;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;
import org.json.JSONObject;
import org.xmlrpc.android.IXMLRPCSerializer;

public class AFListActivity extends Activity {
    private g A;
    private float a = 480.0f;
    private float b = 800.0f;
    private float c = TextTrackStyle.DEFAULT_FONT_SCALE;
    private RelativeLayout d = null;
    private RelativeLayout e = null;
    private ImageView f = null;
    private ArrayList<HashMap<String, String>> g = null;
    private ArrayList<HashMap<String, String>> h = null;
    private ArrayList<HashMap<String, String>> i = null;
    private Bitmap j = null;
    private Bitmap k = null;
    private String l = null;
    private String m = null;
    private String n = null;
    private String o = null;
    private String p = null;
    private String q = null;
    private a r = null;
    private a s = null;
    private a t = null;
    private View u = null;
    private View v = null;
    private int w = 0;
    private float x = 25.0f;
    private int y = 20;
    private TabHost z;

    class a extends ListView {
        public a(AFListActivity aFListActivity, Context context) {
            super(context);
            setId(16908298);
            setDivider(new ColorDrawable(0));
            setDividerHeight((int) (12.0f * aFListActivity.c));
            View view = new View(aFListActivity);
            view.setBackgroundDrawable(new ColorDrawable(0));
            addHeaderView(view, null, false);
            view = new View(aFListActivity);
            view.setBackgroundDrawable(new ColorDrawable(0));
            addFooterView(view, null, false);
            setHeaderDividersEnabled(true);
            setFooterDividersEnabled(true);
            setPadding((int) (aFListActivity.c * 15.0f), 0, (int) (aFListActivity.c * 15.0f), 0);
        }
    }

    class b extends BaseAdapter {
        final /* synthetic */ AFListActivity a;
        private Context b;
        private ArrayList<HashMap<String, String>> c;

        public b(AFListActivity aFListActivity, Context context, ArrayList<HashMap<String, String>> arrayList) {
            this.a = aFListActivity;
            this.b = context;
            this.c = arrayList;
        }

        public final int getCount() {
            return this.c != null ? this.c.size() : 0;
        }

        public final /* synthetic */ Object getItem(int i) {
            return this.c != null ? (HashMap) this.c.get(i) : null;
        }

        public final long getItemId(int i) {
            return 0;
        }

        public final View getView(final int i, View view, ViewGroup viewGroup) {
            View cVar;
            if (view == null) {
                try {
                    cVar = new c(this.a, this.b);
                    c cVar2 = cVar;
                    View view2 = cVar;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    cVar = view;
                    j.b(th2, "error in getView");
                    cVar.setOnClickListener(new OnClickListener(this) {
                        private /* synthetic */ b b;

                        public final void onClick(View view) {
                            try {
                                String str = (String) ((HashMap) this.b.c.get(i)).get("back_url");
                                String str2 = (String) ((HashMap) this.b.c.get(i)).get("click_url");
                                if (!(str == null || str2 == null)) {
                                    new com.appflood.b.b(str, null).b(true);
                                    if (!str2.contains("://")) {
                                        str2 = k.b(str2, null).toString();
                                    }
                                    this.b.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str2)));
                                }
                            } catch (Throwable th) {
                                j.b(th, "error in onListItemClick");
                                return;
                            }
                            try {
                                e.a().a(true, j.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, Integer.valueOf(8)));
                            } catch (Throwable th2) {
                                j.b(th2, "error in fireEventDelegate");
                            }
                        }
                    });
                    return cVar;
                }
            }
            cVar2 = (c) view;
            view2 = view;
            try {
                cVar2.a((HashMap) this.c.get(i));
                cVar = view2;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                cVar = view2;
                th2 = th4;
                j.b(th2, "error in getView");
                cVar.setOnClickListener(/* anonymous class already generated */);
                return cVar;
            }
            cVar.setOnClickListener(/* anonymous class already generated */);
            return cVar;
        }
    }

    class c extends RelativeLayout {
        private TextView a;
        private TextView b;
        private ImageView c;
        private String d;

        public c(AFListActivity aFListActivity, Context context) {
            super(context);
            View imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.FIT_XY);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, (int) (92.0f * aFListActivity.c));
            layoutParams.addRule(14);
            if (aFListActivity.k != null) {
                imageView.setImageBitmap(aFListActivity.k);
            } else {
                new com.appflood.b.b(d.z + "newbg2.png", (byte) 0).a(imageView);
            }
            addView(imageView, layoutParams);
            this.c = new ImageView(context);
            this.c.setId(203);
            this.c.setScaleType(ScaleType.FIT_XY);
            Drawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{9.0f, 9.0f, 9.0f, 9.0f, 9.0f, 9.0f, 9.0f, 9.0f}, null, null));
            shapeDrawable.getPaint().setColor(-261724570);
            this.c.setBackgroundDrawable(shapeDrawable);
            this.c.setPadding(0, 0, 2, 2);
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) (aFListActivity.c * 72.0f), (int) (aFListActivity.c * 72.0f));
            layoutParams2.addRule(9);
            layoutParams2.setMargins((int) (aFListActivity.c * 10.0f), (int) (aFListActivity.c * 10.0f), (int) (aFListActivity.c * 10.0f), 0);
            addView(this.c, layoutParams2);
            this.a = new TextView(context);
            this.a.setTextSize((aFListActivity.x * aFListActivity.c) / getResources().getDisplayMetrics().density);
            this.a.setTextColor(Color.rgb(67, 95, 125));
            this.a.setTypeface(Typeface.DEFAULT_BOLD);
            this.a.setId(204);
            layoutParams2 = new RelativeLayout.LayoutParams(-1, (int) (34.0f * aFListActivity.c));
            layoutParams2.addRule(10);
            layoutParams2.addRule(1, 203);
            layoutParams2.setMargins(0, (int) (8.0f * aFListActivity.c), 0, 0);
            addView(this.a, layoutParams2);
            this.b = new TextView(context);
            this.b.setTextSize((((float) aFListActivity.y) * aFListActivity.c) / getResources().getDisplayMetrics().density);
            this.b.setTextColor(Color.rgb(158, 164, 173));
            layoutParams2 = new RelativeLayout.LayoutParams((int) (243.0f * aFListActivity.c), (int) (40.0f * aFListActivity.c));
            layoutParams2.addRule(3, 204);
            layoutParams2.addRule(1, 203);
            addView(this.b, layoutParams2);
            imageView = new ImageView(context);
            layoutParams = new RelativeLayout.LayoutParams((int) (81.0f * aFListActivity.c), (int) (34.5d * ((double) aFListActivity.c)));
            layoutParams.addRule(11);
            layoutParams.addRule(8, 203);
            layoutParams.setMargins(0, 0, (int) (11.0f * aFListActivity.c), 0);
            if (aFListActivity.j != null) {
                imageView.setImageBitmap(aFListActivity.j);
            } else {
                new com.appflood.b.b(d.z + d.f, (byte) 0).a(imageView);
            }
            addView(imageView, layoutParams);
        }

        protected final void a(HashMap<String, String> hashMap) {
            if (hashMap != null) {
                final String str;
                if (j.g((String) hashMap.get(IXMLRPCSerializer.TAG_NAME))) {
                    this.a.setText("");
                } else {
                    this.a.setText((CharSequence) hashMap.get(IXMLRPCSerializer.TAG_NAME));
                }
                if (j.g((String) hashMap.get("des"))) {
                    this.b.setText("");
                } else {
                    this.b.setText((CharSequence) hashMap.get("des"));
                }
                if (!j.g((String) hashMap.get("icon_url"))) {
                    str = (String) hashMap.get("icon_url");
                    if (!str.equals(this.d)) {
                        this.d = str;
                        new com.appflood.b.b(this.d, (byte) 0).a(new com.appflood.b.b.a(this) {
                            final /* synthetic */ c a;

                            public final void a(int i) {
                            }

                            public final void a(com.appflood.b.b bVar) {
                                if (str.equals(this.a.d)) {
                                    Bitmap d = bVar.d();
                                    if (d != null) {
                                        d = AnonymousClass1.a(d, 9.0f);
                                    }
                                    f.a(new Runnable(this) {
                                        private /* synthetic */ AnonymousClass1 b;

                                        public final void run() {
                                            try {
                                                this.b.a.c.setImageBitmap(d);
                                            } catch (Throwable th) {
                                                j.a(th, "set view image failed!");
                                            }
                                        }
                                    });
                                }
                            }
                        }).f();
                    }
                }
                str = (String) hashMap.get("show_cb_url");
                if (!j.g(str)) {
                    new com.appflood.b.b(str).f();
                    hashMap.put("show_cb_url_copy", str);
                    hashMap.put("show_cb_url", "");
                }
            }
        }
    }

    private void a() {
        String str = null;
        g gVar = this.A;
        if (j.g(str)) {
            this.l = d.z + "newbg" + d.A;
            this.m = d.z + "title" + d.A;
            this.n = d.z + "game" + d.A;
            this.o = d.z + SettingsJsonConstants.APP_KEY + d.A;
            this.p = d.z + "gameP" + d.A;
            this.q = d.z + "appP" + d.A;
        } else {
            CharSequence charSequence = "{1}";
            this.l = str.replace(charSequence, "50");
            this.m = str.replace(charSequence, "55");
            this.n = str.replace(charSequence, "51");
            this.o = str.replace(charSequence, "52");
            this.p = str.replace(charSequence, "53");
            this.q = str.replace(charSequence, "54");
        }
        this.h = this.A.g;
        this.i = this.A.h;
        this.g = this.A.a(this.w);
        if (this.h != null) {
            j.d("game length + " + this.h.size());
        }
        if (this.i != null) {
            j.d("appData lenth + " + this.i.size());
        }
        if (this.g != null) {
            j.d("alldata length + " + this.g.size());
        }
        if (this.w == 4 || this.w == 3) {
            if (this.h.size() <= 0 && this.i.size() <= 0) {
                e.a().a(false, 8);
                b();
                return;
            }
        } else if (this.g.size() <= 0) {
            e.a().a(false, 8);
            b();
            return;
        }
        e.a().a(true, 8);
        f.a(new Runnable(this) {
            private /* synthetic */ AFListActivity a;

            {
                this.a = r1;
            }

            public final void run() {
                AFListActivity aFListActivity;
                if (this.a.w != 4 && this.a.w != 3) {
                    aFListActivity = this.a;
                    AFListActivity.a(this.a.g);
                    this.a.r.setAdapter(new b(this.a, this.a, this.a.g));
                    new com.appflood.b.b(this.a.l, (byte) 0).a(this.a.r);
                } else if (this.a.h.size() > 0 || this.a.i.size() > 0) {
                    aFListActivity = this.a;
                    AFListActivity.a(this.a.h);
                    aFListActivity = this.a;
                    AFListActivity.a(this.a.i);
                    ListAdapter bVar = new b(this.a, this.a, this.a.h);
                    ListAdapter bVar2 = new b(this.a, this.a, this.a.i);
                    this.a.s.setAdapter(bVar);
                    this.a.t.setAdapter(bVar2);
                    if (this.a.w == 3) {
                        this.a.z.setCurrentTab(0);
                    } else {
                        this.a.z.setCurrentTab(1);
                    }
                    new com.appflood.b.b(this.a.l, (byte) 0).a(this.a.s);
                    new com.appflood.b.b(this.a.l, (byte) 0).a(this.a.t);
                    if (this.a.w == 3) {
                        new com.appflood.b.b(this.a.p, (byte) 0).a(this.a.u);
                        new com.appflood.b.b(this.a.o, (byte) 0).a(this.a.v);
                    } else {
                        new com.appflood.b.b(this.a.n, (byte) 0).a(this.a.u);
                        new com.appflood.b.b(this.a.q, (byte) 0).a(this.a.v);
                    }
                } else {
                    this.a.b();
                    return;
                }
                new com.appflood.b.b(this.a.m, (byte) 0).a(this.a.e);
                if (j.g(null)) {
                    new com.appflood.b.b(d.z + "newclose.png", (byte) 0).a(this.a.f);
                }
                AFListActivity.u(this.a);
            }
        });
    }

    static /* synthetic */ void a(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap hashMap = (HashMap) it.next();
            if (hashMap.containsKey("show_cb_url_copy")) {
                " list " + hashMap;
                j.a();
                hashMap.put("show_cb_url", (String) hashMap.get("show_cb_url_copy"));
                hashMap.remove("show_cb_url_copy");
            }
        }
    }

    private void b() {
        this.f.setClickable(false);
        this.j = null;
        this.k = null;
        Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, this.b);
        translateAnimation.setDuration(500);
        this.d.startAnimation(translateAnimation);
        f.a(new TimerTask(this) {
            final /* synthetic */ AFListActivity a;

            {
                this.a = r1;
            }

            public final void run() {
                f.a(new Runnable(this) {
                    private /* synthetic */ AnonymousClass9 a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        try {
                            if (this.a.a.d != null) {
                                this.a.a.d.removeAllViews();
                            }
                            this.a.a.finish();
                        } catch (Throwable th) {
                        }
                    }
                });
            }
        }, 500);
        try {
            e.a().a(false, j.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, Integer.valueOf(8)));
        } catch (Throwable e) {
            j.b(e, "error in fireEventDelegate");
        }
    }

    static /* synthetic */ void u(AFListActivity aFListActivity) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, aFListActivity.b, 0.0f);
        translateAnimation.setDuration(500);
        aFListActivity.d.startAnimation(translateAnimation);
        aFListActivity.d.setVisibility(0);
    }

    public void onCreate(Bundle bundle) {
        boolean z;
        boolean z2;
        super.onCreate(bundle);
        " appflood connected " + AppFlood.isConnected() + " request  " + d.n;
        j.a();
        if (!AppFlood.isConnected() && j.g(d.n)) {
            j.a();
            com.appflood.e.c.a(this);
            f.a((Context) this, false);
            f.a();
            d.w = com.appflood.e.a.a((Context) this, "token");
            d.z = com.appflood.e.a.a((Context) this, "static_url");
            String a = com.appflood.e.a.a((Context) this, "get_list");
            d.n = a;
            if (j.g(a)) {
                j.c(" close activity: load data failed");
                finish();
            }
        }
        this.A = g.a();
        requestWindowFeature(1);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            z = extras.getBoolean("isFull");
            z2 = extras.getBoolean("isPortrait");
            this.w = extras.getInt("showType");
        } else {
            z2 = true;
            z = true;
        }
        if (z) {
            getWindow().setFlags(1024, 1024);
        }
        if (z2) {
            if (com.appflood.e.c.j >= 9) {
                setRequestedOrientation(7);
            } else {
                setRequestedOrientation(1);
            }
        } else if (com.appflood.e.c.j >= 9) {
            setRequestedOrientation(6);
        } else {
            setRequestedOrientation(0);
        }
        if (com.appflood.e.c.j >= 15) {
            this.x = 20.0f;
            this.y = 15;
        }
        if (com.appflood.e.c.j < 4) {
            if (this.w == 4) {
                this.w = 1;
            } else if (this.w == 3) {
                this.w = 0;
            }
        }
        try {
            LayoutParams layoutParams;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            getWindow().setBackgroundDrawableResource(17170445);
            this.a = (float) displayMetrics.widthPixels;
            this.b = (float) displayMetrics.heightPixels;
            if (this.a > this.b) {
                this.c = this.a / 800.0f;
            } else {
                this.c = this.b / 800.0f;
            }
            this.d = new RelativeLayout(this);
            this.e = new RelativeLayout(this);
            this.e.setId(202);
            this.f = new ImageView(this);
            this.f.setPadding((int) (this.c * 10.0f), (int) (this.c * 10.0f), (int) (26.0f * this.c), (int) (this.c * 10.0f));
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) (66.0f * this.c), (int) (50.0f * this.c));
            layoutParams2.addRule(11);
            layoutParams2.addRule(15);
            this.e.addView(this.f, layoutParams2);
            this.f.setOnClickListener(new OnClickListener(this) {
                private /* synthetic */ AFListActivity a;

                {
                    this.a = r1;
                }

                public final void onClick(View view) {
                    this.a.b();
                }
            });
            layoutParams2 = new RelativeLayout.LayoutParams(-1, (int) (this.c * 58.0f));
            layoutParams2.addRule(10);
            this.d.addView(this.e, layoutParams2);
            View imageView = new ImageView(this);
            imageView.setId(16908292);
            new com.appflood.b.b(d.z + "noconnection.png", (byte) 0).a(imageView);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            if (this.w == 4 || this.w == 3) {
                this.s = new a(this, this);
                this.t = new a(this, this);
                imageView = new RelativeLayout(this);
                layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                View tabWidget = new TabWidget(this);
                tabWidget.setId(16908307);
                LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, (int) (65.0f * this.c));
                layoutParams3.addRule(10);
                imageView.addView(tabWidget, layoutParams3);
                tabWidget = new FrameLayout(this);
                tabWidget.setId(16908305);
                layoutParams3 = new RelativeLayout.LayoutParams(-1, -1);
                layoutParams3.topMargin = (int) (64.0f * this.c);
                layoutParams3.addRule(12);
                imageView.addView(tabWidget, layoutParams3);
                this.u = new View(this);
                this.v = new View(this);
                this.z = new TabHost(this);
                this.z.setId(16908306);
                this.z.addView(imageView, layoutParams);
                this.z.setup();
                TabSpec content = this.z.newTabSpec("Game").setContent(new TabContentFactory(this) {
                    private /* synthetic */ AFListActivity a;

                    {
                        this.a = r1;
                    }

                    public final View createTabContent(String str) {
                        return this.a.s;
                    }
                });
                TabSpec content2 = this.z.newTabSpec("App").setContent(new TabContentFactory(this) {
                    private /* synthetic */ AFListActivity a;

                    {
                        this.a = r1;
                    }

                    public final View createTabContent(String str) {
                        return this.a.t;
                    }
                });
                try {
                    Method method = content.getClass().getMethod("setIndicator", new Class[]{View.class});
                    method.invoke(content, new Object[]{this.u});
                    method.invoke(content2, new Object[]{this.v});
                } catch (Throwable e) {
                    j.b(e, "No such method");
                } catch (Throwable e2) {
                    j.b(e2, "Illegal argument");
                } catch (Throwable e22) {
                    j.b(e22, "Illegal access");
                } catch (Throwable e222) {
                    j.b(e222, "Invoke failed");
                }
                this.z.addTab(content);
                this.z.addTab(content2);
                this.z.setOnTabChangedListener(new OnTabChangeListener(this) {
                    private /* synthetic */ AFListActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onTabChanged(String str) {
                        if ("Game".equals(str)) {
                            new com.appflood.b.b(this.a.p, (byte) 0).a(this.a.u);
                            new com.appflood.b.b(this.a.o, (byte) 0).a(this.a.v);
                            return;
                        }
                        new com.appflood.b.b(this.a.q, (byte) 0).a(this.a.v);
                        new com.appflood.b.b(this.a.n, (byte) 0).a(this.a.u);
                    }
                });
                layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
                layoutParams2.topMargin = (int) (this.c * 58.0f);
                layoutParams2.addRule(12);
                this.d.addView(this.z, layoutParams2);
            } else {
                this.r = new a(this, this);
                this.d.addView(imageView);
                this.r.setEmptyView(imageView);
                layoutParams2 = new RelativeLayout.LayoutParams(-1, (int) (this.b - (this.c * 58.0f)));
                layoutParams2.addRule(3, 202);
                this.d.addView(this.r, layoutParams2);
            }
            Drawable gradientDrawable = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{-2038035, 14739181});
            gradientDrawable.setShape(0);
            imageView = new View(this);
            imageView.setBackgroundDrawable(gradientDrawable);
            layoutParams = new RelativeLayout.LayoutParams(-1, (int) (BitmapDescriptorFactory.HUE_ORANGE * this.c));
            layoutParams.addRule(12);
            this.d.addView(imageView, layoutParams);
            this.d.setVisibility(8);
            setContentView(this.d);
            g a2 = g.a();
            int i = this.w;
            switch (i) {
                case 0:
                    if (a2.h == null) {
                        z2 = false;
                        break;
                    } else {
                        z2 = true;
                        break;
                    }
                case 1:
                    if (a2.h == null) {
                        z2 = false;
                        break;
                    } else {
                        z2 = true;
                        break;
                    }
                case 2:
                    if (a2.a(i) == null) {
                        z2 = false;
                        break;
                    } else {
                        z2 = true;
                        break;
                    }
                case 3:
                case 4:
                    if (a2.h != null && a2.g != null) {
                        z2 = true;
                        break;
                    } else {
                        z2 = false;
                        break;
                    }
                    break;
                default:
                    z2 = false;
                    break;
            }
            if (z2) {
                a();
            } else {
                this.A.a(this.w, new AFRequestDelegate(this) {
                    private /* synthetic */ AFListActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onFinish(JSONObject jSONObject) {
                        if (j.a(jSONObject, "result", 0) == 1) {
                            this.a.a();
                            return;
                        }
                        e.a().a(false, 8);
                        this.a.b();
                    }
                });
            }
            new com.appflood.b.b(d.z + "newbg2.png", (byte) 0).a(new com.appflood.b.b.a(this) {
                private /* synthetic */ AFListActivity a;

                {
                    this.a = r1;
                }

                public static int a(Context context) {
                    int top;
                    int i = 0;
                    if (context instanceof Activity) {
                        Window window = ((Activity) context).getWindow();
                        Rect rect = new Rect();
                        window.getDecorView().getWindowVisibleDisplayFrame(rect);
                        int i2 = rect.top;
                        top = window.findViewById(16908290).getTop() - i2;
                        if (top < 0) {
                            top = 0;
                            i = i2;
                        } else {
                            i = i2;
                        }
                    } else {
                        top = 0;
                    }
                    " context " + context + " bar " + i + " title " + top;
                    j.a();
                    return top + i;
                }

                public static int a(Context context, int i) {
                    if (context != null) {
                        return i > 0 ? (int) ((context.getResources().getDisplayMetrics().density * ((float) i)) + 0.5f) : i;
                    } else {
                        j.a(null, "context is null for rp");
                        return i;
                    }
                }

                public static Bitmap a(Bitmap bitmap, float f) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    try {
                        Bitmap createBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                        Canvas canvas = new Canvas(createBitmap);
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(SupportMenu.CATEGORY_MASK);
                        canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) width, (float) height), f, f, paint);
                        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                        bitmap.recycle();
                        return createBitmap;
                    } catch (Throwable th) {
                        return bitmap;
                    }
                }

                private static Field a(Class cls, String str) {
                    while (cls != Object.class) {
                        try {
                            return cls.getDeclaredField(str);
                        } catch (NoSuchFieldException e) {
                            cls = cls.getSuperclass();
                        }
                    }
                    return null;
                }

                public static void a(Context context, Intent intent) {
                    try {
                        f.a(new com.appflood.e.d(context, intent));
                    } catch (Throwable th) {
                        j.a(th, "start activity error");
                    }
                }

                public static void a(Context context, String str) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel:" + str));
                    context.startActivity(intent);
                }

                public static void a(Context context, String str, String str2) {
                    Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + str));
                    intent.putExtra("sms_body", str2);
                    context.startActivity(intent);
                }

                public static void a(View view, String str, String str2) {
                    Drawable[] drawableArr = new BitmapDrawable[2];
                    com.appflood.b.b bVar = new com.appflood.b.b(d.z + str, (byte) 0);
                    bVar.a(new com.appflood.e.e(drawableArr, view));
                    bVar.f();
                    bVar = new com.appflood.b.b(d.z + str2, (byte) 0);
                    bVar.a(new com.appflood.e.f(drawableArr));
                    bVar.f();
                    view.setOnTouchListener(new com.appflood.e.g(drawableArr));
                }

                public static void a(Object obj, String str, Object obj2) {
                    Field a = AnonymousClass1.a(obj.getClass(), str);
                    if (a == null) {
                        throw new IllegalArgumentException("Could not find field [" + str + "] on target [" + obj + "]");
                    }
                    if (!(Modifier.isPublic(a.getModifiers()) && Modifier.isPublic(a.getDeclaringClass().getModifiers()))) {
                        a.setAccessible(true);
                    }
                    try {
                        a.set(obj, obj2);
                    } catch (Throwable e) {
                        Log.e("zbkc", "", e);
                    }
                }

                public static boolean a(Activity activity) {
                    return (activity.getWindow().getAttributes().flags & 1024) != 0;
                }

                public static boolean b(Activity activity) {
                    return activity.getResources().getConfiguration().orientation == 1;
                }

                public static int c(Activity activity) {
                    Rect rect = new Rect();
                    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    int i = rect.top;
                    int top = activity.getWindow().findViewById(16908290).getTop();
                    "contentTop = " + top + "  statusBarHeight " + i;
                    j.a();
                    int i2 = top - i;
                    top = Math.abs(i2);
                    if (top == 0) {
                        top = Math.abs(i);
                    }
                    "title bar = " + Math.abs(i2);
                    j.a();
                    return top;
                }

                public final void a(int i) {
                }

                public final void a(com.appflood.b.b bVar) {
                    try {
                        this.a.k = bVar.d();
                    } catch (Throwable th) {
                        j.b(th, "error in bigmapBg");
                    }
                }
            }).f();
            new com.appflood.b.b(d.z + d.f, (byte) 0).a(new com.appflood.b.b.a(this) {
                private /* synthetic */ AFListActivity a;

                {
                    this.a = r1;
                }

                public final void a(int i) {
                }

                public final void a(com.appflood.b.b bVar) {
                    try {
                        this.a.j = bVar.d();
                    } catch (Throwable th) {
                        j.b(th, "error in bigmapFree");
                    }
                }
            }).f();
        } catch (Throwable e2222) {
            j.b(e2222, "oncreat error");
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        b();
        return true;
    }
}
