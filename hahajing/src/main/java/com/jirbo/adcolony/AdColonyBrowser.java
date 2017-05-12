package com.jirbo.adcolony;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.google.android.gms.cast.TextTrackStyle;

public class AdColonyBrowser extends Activity {
    static boolean A = false;
    static boolean B = false;
    static boolean C = false;
    static boolean a = true;
    public static String url;
    static boolean v = false;
    static boolean w = false;
    static boolean x = false;
    static boolean y = false;
    static boolean z = true;
    WebView b;
    ADCImage c;
    ADCImage d;
    ADCImage e;
    ADCImage f;
    ADCImage g;
    ADCImage h;
    ADCImage i;
    ADCImage j;
    ADCImage k;
    RelativeLayout l;
    RelativeLayout m;
    boolean n = false;
    boolean o = false;
    boolean p = false;
    boolean q = false;
    ProgressBar r;
    DisplayMetrics s;
    a t;
    c u;

    class a extends View {
        Rect a = new Rect();
        Paint b = new Paint();
        final /* synthetic */ AdColonyBrowser c;

        public a(AdColonyBrowser adColonyBrowser, Activity activity) {
            this.c = adColonyBrowser;
            super(activity);
        }

        public void onDraw(Canvas canvas) {
            getDrawingRect(this.a);
            int height = (this.c.l.getHeight() - this.c.c.g) / 2;
            if (AdColonyBrowser.v) {
                this.c.j.a(canvas, this.c.c.f, height);
            } else {
                this.c.c.a(canvas, this.c.c.f, height);
            }
            if (AdColonyBrowser.w) {
                this.c.k.a(canvas, (this.c.c.c() + (this.c.l.getWidth() / 10)) + this.c.c.f, height);
            } else {
                this.c.f.a(canvas, (this.c.c.c() + (this.c.l.getWidth() / 10)) + this.c.c.f, height);
            }
            if (AdColonyBrowser.x) {
                this.c.d.a(canvas, (this.c.f.c() + this.c.f.f) + (this.c.l.getWidth() / 10), height);
            } else {
                this.c.e.a(canvas, (this.c.f.c() + this.c.f.f) + (this.c.l.getWidth() / 10), height);
            }
            this.c.g.a(canvas, this.c.l.getWidth() - (this.c.g.f * 2), height);
            if (this.c.n) {
                this.c.h.c((this.c.c.c() - (this.c.h.f / 2)) + (this.c.c.f / 2), (this.c.c.d() - (this.c.h.g / 2)) + (this.c.c.g / 2));
                this.c.h.a(canvas);
            }
            if (this.c.o) {
                this.c.h.c((this.c.f.c() - (this.c.h.f / 2)) + (this.c.f.f / 2), (this.c.f.d() - (this.c.h.g / 2)) + (this.c.f.g / 2));
                this.c.h.a(canvas);
            }
            if (this.c.p) {
                this.c.h.c((this.c.e.c() - (this.c.h.f / 2)) + (this.c.e.f / 2), (this.c.e.d() - (this.c.h.g / 2)) + (this.c.e.g / 2));
                this.c.h.a(canvas);
            }
            if (this.c.q) {
                this.c.h.c((this.c.g.c() - (this.c.h.f / 2)) + (this.c.g.f / 2), (this.c.g.d() - (this.c.h.g / 2)) + (this.c.g.g / 2));
                this.c.h.a(canvas);
            }
            a();
        }

        public void a() {
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.c.r.getWidth(), this.c.r.getHeight());
            layoutParams.topMargin = (this.c.l.getHeight() - this.c.d.g) / 2;
            layoutParams.leftMargin = ((this.c.l.getWidth() / 10) + this.c.d.c()) + this.c.d.f;
            if (AdColonyBrowser.z && this.c.d.c() != 0) {
                this.c.m.removeView(this.c.r);
                this.c.m.addView(this.c.r, layoutParams);
                AdColonyBrowser.z = false;
            }
            if (this.c.r.getLayoutParams() != null) {
                this.c.r.getLayoutParams().height = this.c.d.g;
                this.c.r.getLayoutParams().width = this.c.d.f;
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (action == 0) {
                if (a(this.c.c, x, y) && AdColonyBrowser.v) {
                    this.c.n = true;
                    invalidate();
                    return true;
                } else if (a(this.c.f, x, y) && AdColonyBrowser.w) {
                    this.c.o = true;
                    invalidate();
                    return true;
                } else if (a(this.c.e, x, y)) {
                    this.c.p = true;
                    invalidate();
                    return true;
                } else if (a(this.c.g, x, y)) {
                    this.c.q = true;
                    invalidate();
                    return true;
                }
            }
            if (action == 1) {
                if (a(this.c.c, x, y) && AdColonyBrowser.v) {
                    this.c.b.goBack();
                    b();
                    return true;
                } else if (a(this.c.f, x, y) && AdColonyBrowser.w) {
                    this.c.b.goForward();
                    b();
                    return true;
                } else if (a(this.c.e, x, y) && AdColonyBrowser.x) {
                    this.c.b.stopLoading();
                    b();
                    return true;
                } else if (a(this.c.e, x, y) && !AdColonyBrowser.x) {
                    this.c.b.reload();
                    b();
                    return true;
                } else if (a(this.c.g, x, y)) {
                    AdColonyBrowser.C = true;
                    this.c.b.loadData("", "text/html", "utf-8");
                    AdColonyBrowser.w = false;
                    AdColonyBrowser.v = false;
                    AdColonyBrowser.x = false;
                    b();
                    this.c.finish();
                    return true;
                } else {
                    b();
                }
            }
            return false;
        }

        public void b() {
            this.c.n = false;
            this.c.o = false;
            this.c.p = false;
            this.c.q = false;
            invalidate();
        }

        public boolean a(ADCImage aDCImage, int i, int i2) {
            return i < (aDCImage.c() + aDCImage.f) + 16 && i > aDCImage.c() - 16 && i2 < (aDCImage.d() + aDCImage.g) + 16 && i2 > aDCImage.d() - 16;
        }
    }

    class b extends View {
        Rect a = new Rect();
        final /* synthetic */ AdColonyBrowser b;

        public b(AdColonyBrowser adColonyBrowser, Activity activity) {
            this.b = adColonyBrowser;
            super(activity);
        }

        public void onDraw(Canvas canvas) {
            if (!AdColonyBrowser.y) {
                canvas.drawARGB(255, 0, 0, 0);
                getDrawingRect(this.a);
                this.b.i.a(canvas, (this.a.width() - this.b.i.f) / 2, (this.a.height() - this.b.i.g) / 2);
                invalidate();
            }
        }
    }

    class c extends View {
        Paint a = new Paint();
        ADCImage b = new ADCImage(a.j("close_image_normal"));
        ADCImage c = new ADCImage(a.j("close_image_down"));
        final /* synthetic */ AdColonyBrowser d;

        public c(AdColonyBrowser adColonyBrowser, Activity activity) {
            this.d = adColonyBrowser;
            super(activity);
            try {
                getClass().getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this, new Object[]{Integer.valueOf(1), null});
            } catch (Exception e) {
            }
            this.a.setColor(-3355444);
            this.a.setStrokeWidth(10.0f);
            this.a.setStyle(Style.STROKE);
            this.a.setShadowLayer(3.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, ViewCompat.MEASURED_STATE_MASK);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawRect(0.0f, 0.0f, (float) this.d.l.getWidth(), 10.0f, this.a);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = true;
        this.c = new ADCImage(a.j("browser_back_image_normal"));
        this.d = new ADCImage(a.j("browser_stop_image_normal"));
        this.e = new ADCImage(a.j("browser_reload_image_normal"));
        this.f = new ADCImage(a.j("browser_forward_image_normal"));
        this.g = new ADCImage(a.j("browser_close_image_normal"));
        this.h = new ADCImage(a.j("browser_glow_button"));
        this.i = new ADCImage(a.j("browser_icon"));
        this.j = new ADCImage(a.j("browser_back_image_normal"), true);
        this.k = new ADCImage(a.j("browser_forward_image_normal"), true);
        this.s = AdColony.activity().getResources().getDisplayMetrics();
        float f = ((float) this.s.widthPixels) / this.s.xdpi;
        float f2 = ((float) this.s.heightPixels) / this.s.ydpi;
        double sqrt = (Math.sqrt((double) ((this.s.widthPixels * this.s.widthPixels) + (this.s.heightPixels * this.s.heightPixels))) / Math.sqrt((double) ((f * f) + (f2 * f2)))) / 220.0d;
        if (sqrt > 1.8d) {
            sqrt = 1.8d;
        }
        z = true;
        v = false;
        w = false;
        C = false;
        this.c.a(sqrt);
        this.d.a(sqrt);
        this.e.a(sqrt);
        this.f.a(sqrt);
        this.g.a(sqrt);
        this.h.a(sqrt);
        this.j.a(sqrt);
        this.k.a(sqrt);
        this.r = new ProgressBar(this);
        this.r.setVisibility(4);
        this.m = new RelativeLayout(this);
        this.l = new RelativeLayout(this);
        this.l.setBackgroundColor(-3355444);
        if (a.m) {
            this.l.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (((double) this.c.g) * 1.5d)));
        } else {
            this.l.setLayoutParams(new RelativeLayout.LayoutParams(-1, (int) (((double) this.c.g) * 1.5d)));
        }
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().requestFeature(2);
        setVolumeControlStream(3);
        this.b = new WebView(this);
        this.b.getSettings().setJavaScriptEnabled(true);
        this.b.getSettings().setBuiltInZoomControls(true);
        this.b.getSettings().setUseWideViewPort(true);
        this.b.getSettings().setLoadWithOverviewMode(true);
        this.b.getSettings().setGeolocationEnabled(true);
        if (a) {
            if (a.m) {
                setRequestedOrientation(a.w);
            } else if (VERSION.SDK_INT >= 10) {
                setRequestedOrientation(6);
            } else {
                setRequestedOrientation(0);
            }
        }
        a = true;
        this.b.setWebChromeClient(new WebChromeClient(this) {
            final /* synthetic */ AdColonyBrowser a;

            {
                this.a = r1;
            }

            public void onProgressChanged(WebView view, int progress) {
                this.a.setProgress(progress * 1000);
            }

            public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        this.b.setWebViewClient(new WebViewClient(this) {
            final /* synthetic */ AdColonyBrowser a;

            {
                this.a = r1;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("market://") && !url.startsWith("amzn://")) {
                    return false;
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                if (a.L != null) {
                    a.L.startActivity(intent);
                }
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!AdColonyBrowser.C) {
                    AdColonyBrowser.x = true;
                    AdColonyBrowser.y = false;
                    this.a.r.setVisibility(0);
                }
                this.a.t.invalidate();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failing_url) {
                l.d.a("Error viewing URL: ").b((Object) description);
                this.a.finish();
            }

            public void onPageFinished(WebView view, String url) {
                if (!AdColonyBrowser.C) {
                    AdColonyBrowser.y = true;
                    AdColonyBrowser.x = false;
                    this.a.r.setVisibility(4);
                    AdColonyBrowser.v = this.a.b.canGoBack();
                    AdColonyBrowser.w = this.a.b.canGoForward();
                }
                this.a.t.invalidate();
            }
        });
        this.t = new a(this, this);
        this.u = new c(this, this);
        this.m.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
        this.m.addView(this.l);
        this.l.setId(12345);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, this.s.heightPixels - ((int) (((double) this.g.g) * 1.5d)));
        layoutParams.addRule(3, this.l.getId());
        this.m.addView(this.b, layoutParams);
        layoutParams = new RelativeLayout.LayoutParams(-2, 20);
        layoutParams.addRule(3, this.l.getId());
        layoutParams.setMargins(0, -10, 0, 0);
        this.m.addView(this.u, layoutParams);
        int i = this.s.widthPixels > this.s.heightPixels ? this.s.widthPixels : this.s.heightPixels;
        this.m.addView(this.t, new RelativeLayout.LayoutParams(i * 2, i * 2));
        layoutParams = new RelativeLayout.LayoutParams(-2, this.s.heightPixels - ((int) (((double) this.g.g) * 1.5d)));
        layoutParams.addRule(3, this.l.getId());
        this.m.addView(new b(this, this), layoutParams);
        setContentView(this.m);
        this.b.loadUrl(url);
        l.c.a("Viewing ").b(url);
    }

    public void onWindowFocusChanged(boolean has_focus) {
        super.onWindowFocusChanged(has_focus);
    }

    public void onPause() {
        super.onPause();
        this.t.b();
    }

    public void onResume() {
        super.onResume();
        z = true;
        this.t.invalidate();
    }

    public void onConfigurationChanged(Configuration new_config) {
        super.onConfigurationChanged(new_config);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, this.s.heightPixels - ((int) (1.5d * ((double) this.g.g))));
        layoutParams.addRule(3, this.l.getId());
        this.b.setLayoutParams(layoutParams);
        z = true;
        this.t.invalidate();
    }

    public void onDestroy() {
        if (!a.u && A) {
            for (int i = 0; i < a.ae.size(); i++) {
                ((Bitmap) a.ae.get(i)).recycle();
            }
            a.ae.clear();
        }
        A = false;
        B = false;
        super.onDestroy();
    }
}
