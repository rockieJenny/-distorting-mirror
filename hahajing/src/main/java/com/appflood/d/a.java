package com.appflood.d;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.appflood.c.f;
import com.appflood.d.b.b;
import com.appflood.d.b.c;
import com.appflood.d.b.d;
import com.appflood.e.h;
import com.appflood.e.i;
import com.appflood.e.j;
import com.appflood.e.k;
import com.appflood.mraid.AFBannerWebView;
import com.appflood.mraid.s;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONObject;

public final class a extends RelativeLayout implements OnCompletionListener, OnErrorListener, OnSeekCompleteListener, OnClickListener, com.appflood.b.b.a, com.appflood.d.b.a, b, c, d {
    private int A;
    private int B;
    private int C;
    private int D;
    private com.appflood.c.b E;
    private HashMap<String, Object> F;
    private HashMap<String, Object> G;
    private HashMap<String, Object> H;
    private ArrayList<HashMap<String, Object>> I;
    private boolean J;
    private AFBannerWebView K;
    private boolean L;
    private HashMap<String, String> a;
    private SurfaceView b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private boolean g;
    private a h;
    private b i;
    private boolean j;
    private RelativeLayout k;
    private ImageView l;
    private TextView m;
    private com.appflood.f.d n;
    private Button o;
    private String p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    class a extends RelativeLayout implements OnSeekBarChangeListener {
        private Button a;
        private Button b;
        private SeekBar c;
        private /* synthetic */ a d;

        public a(a aVar, Context context) {
            this.d = aVar;
            super(context);
            a(context);
        }

        private void a(Context context) {
            new com.appflood.b.b(com.appflood.c.d.z + "video_controller_bg1.jpg", (byte) 0).a((View) this);
            this.a = new Button(context);
            this.a.setId(21760);
            this.a.setBackgroundColor(0);
            com.appflood.AFListActivity.AnonymousClass1.a(this.a, "pause_press.png", "pause_pressed.png");
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.d.y, this.d.y);
            layoutParams.addRule(15);
            layoutParams.addRule(9);
            layoutParams.leftMargin = com.appflood.AFListActivity.AnonymousClass1.a(context, 2);
            addView(this.a, layoutParams);
            this.b = new Button(context);
            this.b.setId(26112);
            this.b.setBackgroundColor(0);
            this.b.setVisibility(8);
            com.appflood.AFListActivity.AnonymousClass1.a(this.b, "next_press.png", "next_pressed.png");
            layoutParams = new RelativeLayout.LayoutParams((this.d.y * 3) / 4, (this.d.y * 3) / 4);
            layoutParams.addRule(11);
            layoutParams.addRule(15);
            layoutParams.leftMargin = this.d.y / 4;
            layoutParams.rightMargin = com.appflood.AFListActivity.AnonymousClass1.a(context, 2);
            addView(this.b, layoutParams);
            this.c = new SeekBar(context);
            this.c.setId(35209);
            this.c.setBackgroundColor(0);
            this.c.setProgress(0);
            this.c.setMax(100);
            this.c.setSecondaryProgress(100);
            LayerDrawable layerDrawable = (LayerDrawable) this.c.getProgressDrawable();
            Drawable[] drawableArr = new Drawable[layerDrawable.getNumberOfLayers()];
            for (int i = 0; i < layerDrawable.getNumberOfLayers(); i++) {
                ClipDrawable clipDrawable;
                ClipDrawable clipDrawable2;
                switch (layerDrawable.getId(i)) {
                    case 16908288:
                        drawableArr[i] = new ColorDrawable(0);
                        " bgg i =  " + i;
                        j.a();
                        break;
                    case 16908301:
                        clipDrawable = (ClipDrawable) layerDrawable.getDrawable(i);
                        clipDrawable2 = new ClipDrawable(new ColorDrawable(-1282264), 3, 1);
                        clipDrawable2.setLevel(clipDrawable.getLevel());
                        " progress  i =  " + i + "  llll " + clipDrawable.getLevel();
                        j.a();
                        drawableArr[i] = clipDrawable2;
                        break;
                    case 16908303:
                        clipDrawable = (ClipDrawable) layerDrawable.getDrawable(i);
                        clipDrawable2 = new ClipDrawable(new ColorDrawable(ViewCompat.MEASURED_STATE_MASK), 3, 1);
                        clipDrawable2.setLevel(clipDrawable.getLevel());
                        " second  i =  " + i + "  llll " + clipDrawable.getLevel();
                        j.a();
                        drawableArr[i] = clipDrawable2;
                        break;
                    default:
                        break;
                }
            }
            Drawable layerDrawable2 = new LayerDrawable(drawableArr);
            layerDrawable2.setId(0, 16908288);
            layerDrawable2.setId(2, 16908301);
            layerDrawable2.setId(1, 16908303);
            this.c.setProgressDrawable(layerDrawable2);
            com.appflood.AFListActivity.AnonymousClass1.a(this.c, "mMaxHeight", Integer.valueOf(com.appflood.AFListActivity.AnonymousClass1.a(getContext(), 5)));
            com.appflood.AFListActivity.AnonymousClass1.a(this.c, "mMinHeight", Integer.valueOf(com.appflood.AFListActivity.AnonymousClass1.a(getContext(), 5)));
            View view = this.c;
            StateListDrawable stateListDrawable = new StateListDrawable();
            com.appflood.b.b bVar = new com.appflood.b.b(com.appflood.c.d.z + "slide_pressed.png", (byte) 0);
            bVar.a(new h(stateListDrawable, view));
            bVar.f();
            com.appflood.b.b bVar2 = new com.appflood.b.b(com.appflood.c.d.z + "slide_press.png", (byte) 0);
            bVar2.a(new i(stateListDrawable, view));
            bVar2.f();
            this.c.setThumbOffset(com.appflood.AFListActivity.AnonymousClass1.a(getContext(), 5));
            this.c.setOnSeekBarChangeListener(this);
            layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(15);
            layoutParams.addRule(0, this.b.getId());
            layoutParams.addRule(1, this.a.getId());
            layoutParams.leftMargin = com.appflood.AFListActivity.AnonymousClass1.a(context, 5);
            layoutParams.rightMargin = com.appflood.AFListActivity.AnonymousClass1.a(context, 5);
            addView(this.c, layoutParams);
        }

        public final Button a() {
            return this.a;
        }

        public final void a(int i) {
            this.c.setMax(i);
            this.c.setSecondaryProgress(i);
        }

        public final Button b() {
            return this.b;
        }

        public final void b(final int i) {
            if (f.g()) {
                this.c.setProgress(i);
            } else {
                f.a(new Runnable(this) {
                    private /* synthetic */ a b;

                    public final void run() {
                        this.b.c.setProgress(i);
                    }
                });
            }
        }

        public final int c() {
            return this.c.getProgress();
        }

        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (!z) {
                return;
            }
            if (this.d.i.a()) {
                this.d.c(i);
                return;
            }
            if (this.d.e) {
                this.d.C = i;
            } else {
                this.d.i.a(i);
            }
            this.d.a(s.REWIND);
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
        }

        public final void setEnabled(boolean z) {
            super.setEnabled(z);
            this.c.setEnabled(z);
            this.a.setEnabled(z);
        }
    }

    public a(Context context, AFBannerWebView aFBannerWebView, JSONObject jSONObject) {
        super(context);
        this.c = false;
        this.d = false;
        this.e = false;
        this.f = false;
        this.g = false;
        this.j = true;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.J = false;
        this.L = false;
        this.K = aFBannerWebView;
        this.J = true;
        this.p = j.a(jSONObject, "url", "");
        this.s = j.a(jSONObject, "width", 0);
        this.t = j.a(jSONObject, "height", 0);
    }

    public a(Context context, HashMap<String, Object> hashMap) {
        super(context);
        this.c = false;
        this.d = false;
        this.e = false;
        this.f = false;
        this.g = false;
        this.j = true;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.J = false;
        this.L = false;
        this.a = new HashMap();
        a(context);
        this.F = hashMap;
        Object obj = ((HashMap) this.F.get("VAST")).get("Ad");
        if (obj != null) {
            if (obj instanceof ArrayList) {
                List list = (ArrayList) obj;
                if (!j.a(list)) {
                    if (this.G == null) {
                        this.G = (HashMap) list.get(0);
                    } else {
                        int indexOf = list.indexOf(this.G);
                        if (indexOf < list.size() - 1) {
                            this.G = (HashMap) list.get(indexOf + 1);
                        }
                    }
                }
            } else if (obj instanceof HashMap) {
                this.G = (HashMap) obj;
            }
        }
        HashMap hashMap2 = (HashMap) this.G.get("InLine");
        this.a.put("Impression", (String) hashMap2.get("Impression"));
        hashMap2 = (HashMap) hashMap2.get("Creatives");
        Object obj2 = hashMap2.get("Creative");
        if (obj2 instanceof ArrayList) {
            this.I = (ArrayList) obj2;
        } else if ((obj2 instanceof HashMap) && this.I == null) {
            this.I.add(hashMap2);
        }
        d();
        if (!j.g(null)) {
            this.e = true;
        }
    }

    private void a(int i, int i2) {
        if (i > 0 && i2 > 0 && this.q > 0 && this.r > 0) {
            int i3;
            int i4;
            float f = ((float) this.r) / ((float) this.q);
            float f2 = ((float) (((this.x + i2) + (this.z << 1)) + this.w)) / ((float) i);
            "resetLayout adRatio : " + f + " vv=  " + f2 + " mWidth " + this.q;
            j.a();
            if (f > f2) {
                i3 = this.q;
                i4 = (i2 * i3) / i;
            } else {
                i4 = this.r - ((this.x + (this.z << 1)) + this.w);
                i3 = (i4 * i) / i2;
            }
            View view = this.e ? this.l : this.b;
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = i3;
            layoutParams.height = i4;
            "resized w " + i3 + " resizedH " + i4 + " higher " + ((this.x + (this.z << 1)) + this.w);
            j.a();
            this.k.updateViewLayout(view, layoutParams);
            LayoutParams layoutParams2 = this.k.getLayoutParams();
            this.u = i3;
            this.v = ((i4 + this.x) + (this.z << 1)) + this.w;
            layoutParams2.height = this.v;
            ((ViewGroup) this.k.getParent()).updateViewLayout(this.k, layoutParams2);
            Context context = getContext();
            if (this.n != null && this.n.getVisibility() == 0) {
                a(context, false);
            }
            a(context, true);
            if (this.E != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("result", Boolean.valueOf(true));
                hashMap.put("w", Integer.valueOf(this.u));
                hashMap.put("h", Integer.valueOf(this.v));
                this.E.onFinish(hashMap);
            }
        }
    }

    private void a(Context context) {
        setBackgroundColor(0);
        this.k = new RelativeLayout(context);
        this.k.setBackgroundColor(0);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        if (this.e) {
            this.l = new ImageView(context);
            this.l.setScaleType(ScaleType.FIT_XY);
            this.l.setOnClickListener(this);
            this.k.addView(this.l, layoutParams);
            com.appflood.b.b bVar = new com.appflood.b.b(null);
            bVar.a((com.appflood.b.b.a) this);
            bVar.f();
        } else {
            this.b = new SurfaceView(context);
            this.b.setBackgroundColor(0);
            this.b.setOnClickListener(this);
            this.k.addView(this.b, layoutParams);
            this.i = new b(this.b, this.p);
            this.i.a((c) this);
            this.i.a((OnCompletionListener) this);
            this.i.a((OnSeekCompleteListener) this);
            this.i.a((com.appflood.d.b.a) this);
            this.i.a((d) this);
            this.i.a((OnErrorListener) this);
            this.i.a((b) this);
        }
        layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(13);
        addView(this.k, layoutParams);
        this.w = com.appflood.AFListActivity.AnonymousClass1.a(context, 34);
        this.x = com.appflood.AFListActivity.AnonymousClass1.a(context, 20);
        this.y = com.appflood.AFListActivity.AnonymousClass1.a(context, 30);
        this.z = com.appflood.AFListActivity.AnonymousClass1.a(context, 6);
        this.k.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.o = new Button(context);
        com.appflood.AFListActivity.AnonymousClass1.a(this.o, "close_press.png", "close_pressed.png");
        this.o.setOnClickListener(this);
        layoutParams = new RelativeLayout.LayoutParams(this.x, this.x);
        layoutParams.addRule(11);
        layoutParams.addRule(10);
        layoutParams.topMargin = this.z;
        layoutParams.rightMargin = this.z;
        this.k.addView(this.o, layoutParams);
        this.h = new a(this, context);
        this.h.a().setOnClickListener(this);
        this.h.b().setOnClickListener(this);
        layoutParams = new RelativeLayout.LayoutParams(-1, this.w);
        layoutParams.addRule(12);
        this.k.addView(this.h, layoutParams);
        a(getContext(), true);
    }

    private void a(Context context, boolean z) {
        if (this.n == null) {
            this.n = new com.appflood.f.d(context);
        }
        if ((this.n.getParent() != null) != z) {
            if (z) {
                int i = this.s > this.t ? this.t : this.s;
                LayoutParams layoutParams = new RelativeLayout.LayoutParams(i / 3, i / 3);
                "show progress!!!!!!!!!!! size " + (i / 3);
                j.a();
                layoutParams.addRule(13);
                this.k.addView(this.n, layoutParams);
                this.n.setVisibility(0);
                return;
            }
            j.a();
            this.k.removeView(this.n);
            this.n.setVisibility(4);
        }
    }

    private void a(s sVar) {
        "fireTrackEvent " + sVar.toString();
        j.a();
        if (this.J) {
            this.K.a(sVar);
            return;
        }
        switch (sVar) {
            case IMPRESSION:
                k.c((String) this.a.get("start"));
                k.c((String) this.a.get("Impression"));
                return;
            case CLICK:
                k.c((String) this.a.get("ClickTracking"));
                String str = ((String) this.a.get("ClickThrough")).toString();
                if (!j.g(str)) {
                    Context context = getContext();
                    if (!j.g(str)) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(str));
                        context.startActivity(intent);
                        return;
                    }
                    return;
                }
                return;
            case PAUSE:
                k.c((String) this.a.get("pause"));
                return;
            case RESUME:
            case REWIND:
                return;
            case COMPLETE:
                k.c((String) this.a.get("complete"));
                return;
            default:
                return;
        }
    }

    private void b() {
        j.a();
        if (this.e) {
            this.C = 0;
            d(this.D);
            this.h.a(this.D);
        }
        f.a(new Runnable(this) {
            private /* synthetic */ a a;

            {
                this.a = r1;
            }

            public final void run() {
                if (this.a.e) {
                    if (!this.a.f) {
                        a.h(this.a);
                    }
                    if (this.a.C >= this.a.D && !this.a.g) {
                        this.a.a(s.COMPLETE);
                        this.a.c();
                    }
                }
                int i = this.a.e ? this.a.C : this.a.i.g();
                if (!this.a.f) {
                    if (i != this.a.h.c()) {
                        this.a.h.b(i);
                        this.a.a(this.a.getContext(), false);
                    } else {
                        this.a.a(this.a.getContext(), true);
                    }
                }
                if (!this.a.c && i / 1000 == this.a.A / 1000) {
                    j.a();
                    this.a.c = true;
                    if (!this.a.J) {
                        k.c((String) this.a.a.get("firstQuartile"));
                    }
                }
                if (!this.a.d && i / 1000 == this.a.B / 1000) {
                    j.a();
                    this.a.d = true;
                    if (!this.a.J) {
                        k.c((String) this.a.a.get("thirdQuartile"));
                    }
                }
                if (!this.a.hasWindowFocus() && this.a.i.e()) {
                    this.a.i.c();
                    this.a.j = false;
                } else if (!(!this.a.hasWindowFocus() || this.a.f || this.a.j)) {
                    this.a.i.d();
                }
                if (!this.a.g) {
                    f.a((Runnable) this, 500);
                }
            }
        }, 500);
    }

    static /* synthetic */ void b(a aVar, final Context context) {
        aVar.a(aVar.s, aVar.t);
        if (f.g()) {
            aVar.a(context, false);
        } else {
            f.a(new Runnable(aVar) {
                private /* synthetic */ a b;

                public final void run() {
                    this.b.a(context, false);
                }
            });
        }
        aVar.b();
    }

    private void b(HashMap<String, Object> hashMap) {
        if (hashMap != null || hashMap.size() > 0) {
            for (Entry entry : hashMap.entrySet()) {
                String str = (String) entry.getKey();
                String str2 = "";
                Object value = entry.getValue();
                String str3 = value instanceof String ? (String) value : value instanceof HashMap ? (String) ((HashMap) value).get("data") : str2;
                if (!(j.g(str) || j.g(str3))) {
                    this.a.put(str, str3);
                }
            }
        }
    }

    private void c() {
        a(s.NEXT);
        if (!this.e) {
            this.i.b();
            this.i.h();
        }
        if (this.E != null) {
            this.E.onComplete();
        }
        a();
    }

    private void c(int i) {
        this.i.a(i);
        this.i.d();
        this.f = false;
        com.appflood.AFListActivity.AnonymousClass1.a(this.h.a(), "pause_press.png", "pause_pressed.png");
    }

    private void d() {
        if (this.H == null) {
            this.H = (HashMap) this.I.get(0);
        } else {
            int indexOf = this.I.indexOf(this.H);
            if (indexOf < this.I.size() - 1) {
                this.H = (HashMap) this.I.get(indexOf + 1);
            }
        }
        if (this.H.containsKey("Linear")) {
            HashMap hashMap;
            String toLowerCase;
            HashMap hashMap2 = (HashMap) this.H.get("Linear");
            this.D = j.h((String) hashMap2.get("Duration")) * 1000;
            Object obj = hashMap2.get("MediaFiles");
            if (obj instanceof HashMap) {
                hashMap = (HashMap) obj;
            } else if (obj instanceof ArrayList) {
                Iterator it = ((ArrayList) obj).iterator();
                hashMap = null;
                while (it.hasNext()) {
                    hashMap2 = (HashMap) it.next();
                    toLowerCase = ((String) hashMap2.get(AnalyticsSQLiteHelper.EVENT_LIST_TYPE)).toLowerCase();
                    if (!(toLowerCase.contains("flash") || toLowerCase.contains("flv"))) {
                        if (toLowerCase.contains("mp4")) {
                            this.e = false;
                        } else if (toLowerCase.contains("image")) {
                            this.e = true;
                        }
                        hashMap = hashMap2;
                    }
                }
            } else {
                hashMap = null;
            }
            this.s = ((Integer) hashMap.get("width")).intValue();
            this.t = ((Integer) hashMap.get("height")).intValue();
            this.p = (String) hashMap.get("data");
            hashMap2 = (HashMap) hashMap.get("TrackingEvents");
            if (hashMap2 != null || hashMap2.size() > 0) {
                ArrayList arrayList;
                obj = hashMap2.get("Tracking");
                if (obj instanceof ArrayList) {
                    arrayList = (ArrayList) obj;
                } else if (obj instanceof HashMap) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add((HashMap) obj);
                    arrayList = arrayList2;
                } else {
                    arrayList = null;
                }
                if (arrayList != null) {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        HashMap hashMap3 = (HashMap) it2.next();
                        String str = (String) hashMap3.get("event");
                        toLowerCase = (String) hashMap3.get("data");
                        if (!(j.g(str) || j.g(toLowerCase))) {
                            this.a.put(str, toLowerCase);
                        }
                    }
                }
            }
            b((HashMap) hashMap.get("VideoClicks"));
        } else {
            this.H.containsKey("NonLinearAds");
        }
        this.H.containsKey("CompanionAds");
    }

    private void d(int i) {
        this.A = i / 4;
        this.B = (i * 3) / 4;
    }

    static /* synthetic */ int h(a aVar) {
        int i = aVar.C + 500;
        aVar.C = i;
        return i;
    }

    public final void a() {
        a(s.CLOSE);
        this.g = true;
        if (this.i != null) {
            this.i.h();
        }
    }

    public final void a(int i) {
    }

    public final void a(com.appflood.b.b bVar) {
        final Bitmap d = bVar.d();
        if (d != null) {
            this.s = d.getWidth();
            this.t = d.getHeight();
            f.a(new Runnable(this) {
                private /* synthetic */ a b;

                public final void run() {
                    a.b(this.b, this.b.getContext());
                    this.b.l.setImageBitmap(d);
                }
            });
        }
    }

    public final void a(com.appflood.c.b bVar) {
        this.E = bVar;
        if (this.E != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("result", Boolean.valueOf(true));
            hashMap.put("w", Integer.valueOf(this.u));
            hashMap.put("h", Integer.valueOf(this.v));
            this.E.onFinish(hashMap);
        }
    }

    public final void a(HashMap<String, Integer> hashMap) {
        this.s = ((Integer) hashMap.get("result_width")).intValue();
        this.t = ((Integer) hashMap.get("result_height")).intValue();
        a(this.s, this.t);
    }

    public final void a(boolean z) {
        " onFinished view result " + z;
        j.a();
        if (z) {
            this.f = false;
            this.i.d();
            int f = this.i.f();
            d(f);
            this.h.a(f);
            this.L = false;
            b();
            a(s.IMPRESSION);
            return;
        }
        c();
    }

    public final void b(int i) {
        if (i > 0) {
            this.f = true;
            this.h.b(i);
            a(getContext(), true);
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.q = getLayoutParams().width;
        this.r = getLayoutParams().height;
        if (this.q <= 0 || this.r <= 0) {
            f.a(new Runnable(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public final void run() {
                    this.a.q = this.a.getWidth();
                    this.a.r = this.a.getHeight();
                    if (this.a.q <= 0 || this.a.r <= 0) {
                        f.a((Runnable) this, 500);
                    } else {
                        f.a(new Runnable(this) {
                            private /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public final void run() {
                                "mVideoWidth " + this.a.a.s + " " + this.a.a.t;
                                j.a();
                                this.a.a.a(this.a.a.getContext());
                                this.a.a.a(this.a.a.s, this.a.a.t);
                            }
                        });
                    }
                }
            }, 500);
        }
    }

    public final void onClick(View view) {
        if (view == this.h.a()) {
            if (this.i.a()) {
                c(1);
            } else if (this.f) {
                if (!this.e) {
                    this.i.d();
                }
                a(s.RESUME);
                this.f = false;
                com.appflood.AFListActivity.AnonymousClass1.a(this.h.a(), "pause_press.png", "pause_pressed.png");
            } else {
                if (!this.e) {
                    this.i.c();
                }
                this.f = true;
                a(s.PAUSE);
                com.appflood.AFListActivity.AnonymousClass1.a(this.h.a(), "play_press.png", "play_pressed.png");
            }
        } else if (view == this.h.b()) {
            c();
        } else if (view == this.o) {
            a();
            if (this.E != null) {
                this.E.onClose();
            }
        } else if (view == null) {
        } else {
            if (view == this.b || view == this.l) {
                a(s.CLICK);
            }
        }
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        j.a();
        a(s.COMPLETE);
        if (this.L) {
            Context context = getContext();
            a(context, false);
            this.m = new TextView(context);
            this.m.setTextSize(20.0f);
            this.m.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            this.m.setGravity(17);
            this.m.setText("Please try later");
            this.k.addView(this.m, this.b.getLayoutParams());
            if (this.E != null) {
                j.a();
                HashMap hashMap = new HashMap();
                hashMap.put("result", Boolean.valueOf(false));
                hashMap.put("w", Integer.valueOf(this.u));
                hashMap.put("h", Integer.valueOf(this.v));
                hashMap.put("video_error", Boolean.valueOf(true));
                this.E.onFinish(hashMap);
                return;
            }
            return;
        }
        this.i.a(this.i.f() - 1);
        this.i.c();
        this.f = true;
        this.i.b();
        this.h.b(this.i.f());
        com.appflood.AFListActivity.AnonymousClass1.a(this.h.a(), "play_press.png", "play_pressed.png");
    }

    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        j.a(null, " error what " + i + " extra " + i2);
        this.L = true;
        return false;
    }

    public final void onSeekComplete(MediaPlayer mediaPlayer) {
        j.a();
    }
}
