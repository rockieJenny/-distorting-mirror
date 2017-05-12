package com.jirbo.adcolony;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.monetization.internal.Ad;
import com.inmobi.re.controller.JSController;
import java.io.FileInputStream;

public class AdColonyNativeAdView extends FrameLayout implements OnCompletionListener, OnErrorListener, OnPreparedListener {
    boolean A = true;
    boolean B;
    boolean C;
    boolean D;
    boolean E;
    boolean F;
    boolean G;
    AdColonyInterstitialAd H;
    AdColonyNativeAdListener I;
    AdColonyNativeAdMutedListener J;
    ADCImage K;
    ADCImage L;
    ADCImage M;
    ImageView N;
    b O;
    View P;
    Bitmap Q;
    ADCImage R;
    ImageView S;
    boolean T = false;
    Button U;
    String V = "";
    String W = "";
    String Z = "";
    TextView a;
    MediaPlayer aa;
    Surface ab;
    String ac;
    String ad;
    String ae;
    String af;
    String ag;
    String ah;
    String ai;
    String aj = "";
    AdColonyIAPEngagement ak = AdColonyIAPEngagement.NONE;
    int al;
    int am;
    int an;
    int ao;
    int ap;
    int aq = -3355444;
    int ar = ViewCompat.MEASURED_STATE_MASK;
    int as;
    ab at;
    a au;
    float av = 0.25f;
    float aw = 0.25f;
    float ax;
    FileInputStream ay;
    TextView b;
    TextView c;
    Activity d;
    String e;
    String f;
    ViewGroup g;
    SurfaceTexture h;
    int i;
    int j;
    boolean k;
    boolean l;
    boolean m;
    boolean n;
    boolean o;
    boolean p;
    boolean q;
    boolean r;
    boolean s;
    boolean t;
    boolean u;
    boolean v;
    boolean w;
    boolean x;
    boolean y = true;
    boolean z;

    class a extends TextureView implements SurfaceTextureListener {
        boolean a;
        boolean b;
        final /* synthetic */ AdColonyNativeAdView c;

        a(AdColonyNativeAdView adColonyNativeAdView, Context context) {
            this(adColonyNativeAdView, context, false);
        }

        a(AdColonyNativeAdView adColonyNativeAdView, Context context, boolean z) {
            this.c = adColonyNativeAdView;
            super(context);
            this.a = false;
            this.b = false;
            setSurfaceTextureListener(this);
            setWillNotDraw(false);
            this.a = z;
        }

        public void onSurfaceTextureAvailable(SurfaceTexture texture, int w, int h) {
            if (texture == null) {
                this.c.r = true;
                this.c.N.setVisibility(8);
                return;
            }
            this.c.O.setVisibility(0);
            this.c.h = texture;
            if (!this.c.r && !this.a) {
                this.c.ab = new Surface(texture);
                if (this.c.aa != null) {
                    this.c.aa.release();
                }
                this.c.i = w;
                this.c.j = h;
                this.c.aa = new MediaPlayer();
                try {
                    this.c.ay = new FileInputStream(this.c.f);
                    this.c.aa.setDataSource(this.c.ay.getFD());
                    this.c.aa.setSurface(this.c.ab);
                    this.c.aa.setOnCompletionListener(this.c);
                    this.c.aa.setOnPreparedListener(this.c);
                    this.c.aa.setOnErrorListener(this.c);
                    this.c.aa.prepareAsync();
                    l.c.b((Object) "[ADC] Native Ad Prepare called.");
                    this.b = true;
                    Handler handler = new Handler();
                    Runnable anonymousClass1 = new Runnable(this) {
                        final /* synthetic */ a a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            if (!this.a.c.w && !this.a.c.x) {
                                this.a.b = false;
                                this.a.c.r = true;
                                this.a.c.N.setVisibility(8);
                            }
                        }
                    };
                    if (!this.b) {
                        handler.postDelayed(anonymousClass1, 1800);
                    }
                } catch (Exception e) {
                    this.c.r = true;
                    this.c.N.setVisibility(8);
                }
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int w, int h) {
            l.c.b((Object) "[ADC] onSurfaceTextureSizeChanged");
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            l.c.b((Object) "[ADC] Native surface destroyed");
            this.c.w = false;
            this.c.N.setVisibility(4);
            this.c.O.setVisibility(0);
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }

        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            float x = event.getX();
            float y = event.getY();
            if (action == 1 && a.v && (x <= ((float) ((this.c.an - this.c.L.f) + 8)) || y >= ((float) (this.c.L.g + 8)) || this.c.r || this.c.aa == null || !this.c.aa.isPlaying())) {
                a.K = this.c.H;
                a.l.a.a(this.c.e, this.c.H.i);
                ADCVideo.a();
                this.c.H.j = Ad.AD_TYPE_NATIVE;
                this.c.H.k = JSController.FULL_SCREEN;
                this.c.H.r = true;
                this.c.H.s = this.c.z;
                if ((this.c.w || this.c.r) && q.c()) {
                    if (this.c.I != null) {
                        this.c.I.onAdColonyNativeAdStarted(true, this.c);
                    }
                    if (this.c.aa == null || !this.c.aa.isPlaying()) {
                        this.c.H.o = 0.0d;
                        ADCVideo.c = 0;
                    } else {
                        ADCVideo.c = this.c.aa.getCurrentPosition();
                        this.c.H.o = this.c.H.n;
                        this.c.aa.pause();
                        this.c.r = true;
                    }
                    a.v = false;
                    a.l.d.b("video_expanded", this.c.H);
                    if (a.m) {
                        l.a.b((Object) "Launching AdColonyOverlay");
                        a.b().startActivity(new Intent(a.b(), AdColonyOverlay.class));
                    } else {
                        l.a.b((Object) "Launching AdColonyFullscreen");
                        a.b().startActivity(new Intent(a.b(), AdColonyFullscreen.class));
                    }
                    if (this.c.r) {
                        ag agVar = this.c.H.h.k;
                        agVar.d++;
                        a.l.a("start", "{\"ad_slot\":" + this.c.H.h.k.d + ", \"replay\":" + this.c.H.s + "}", this.c.H);
                        a.l.h.a(this.c.H.g, this.c.H.i.d);
                    }
                    this.c.z = true;
                }
            }
            return true;
        }
    }

    class b extends View {
        boolean a;
        final /* synthetic */ AdColonyNativeAdView b;

        public b(AdColonyNativeAdView adColonyNativeAdView, Context context) {
            this.b = adColonyNativeAdView;
            super(context);
        }

        public void onDraw(Canvas canvas) {
            this.b.g = (ViewGroup) getParent().getParent();
            Rect rect = new Rect();
            if (!(this.b.aa == null || this.b.aa.isPlaying() || !this.b.l)) {
                this.a = false;
            }
            if (getLocalVisibleRect(rect) && VERSION.SDK_INT >= 14 && this.b.w) {
                if ((!this.b.l || (this.b.l && (rect.top == 0 || rect.bottom - rect.top > this.b.getNativeAdHeight()))) && rect.bottom - rect.top > this.b.getNativeAdHeight() / 2) {
                    if (this.a || this.b.r || this.b.aa == null || this.b.aa.isPlaying() || this.b.x || this.b.H.a(true) || !this.b.q) {
                    }
                    if (!this.b.q) {
                        l.c.b((Object) "[ADC] Native Ad Starting");
                        this.b.b();
                        this.b.q = true;
                        this.b.H.j = Ad.AD_TYPE_NATIVE;
                        this.b.H.k = Ad.AD_TYPE_NATIVE;
                    } else if (!this.b.s && this.b.aa != null && q.c() && !this.b.aa.isPlaying() && !a.t) {
                        l.c.b((Object) "[ADC] Native Ad Resuming");
                        a.l.d.b("video_resumed", this.b.H);
                        if (!this.b.o) {
                            this.b.b(true);
                        }
                        this.b.setVolume(this.b.aw);
                        this.b.aa.seekTo(this.b.H.p);
                        this.b.aa.start();
                    } else if (!(this.b.r || this.b.q || a.l.a(this.b.H.g, true, false))) {
                        this.b.r = true;
                        setVisibility(0);
                        this.b.N.setVisibility(8);
                    }
                }
                this.a = true;
            } else {
                this.a = false;
            }
            if (!(this.b.r || q.c() || this.b.aa == null || this.b.aa.isPlaying())) {
                setVisibility(0);
                this.b.N.setVisibility(8);
                this.b.r = true;
            }
            if (!this.b.r && this.b.aa != null && this.b.aa.isPlaying()) {
                setVisibility(8);
                this.b.N.setVisibility(0);
            } else if (this.b.r || this.b.s) {
                canvas.drawARGB(255, 0, 0, 0);
                this.b.N.setVisibility(8);
                this.b.K.a(canvas, (this.b.an - this.b.K.f) / 2, (this.b.ao - this.b.K.g) / 2);
            }
            if (!this.b.x && !this.b.r) {
                invalidate();
            }
        }
    }

    public AdColonyNativeAdView(Activity context, String zone_id, int width) {
        int i;
        int i2;
        super(context);
        a.e();
        a.ah.add(this);
        a.ad = 0;
        this.d = context;
        this.e = zone_id;
        this.an = width;
        this.o = true;
        this.ax = a.b().getResources().getDisplayMetrics().density;
        Display defaultDisplay = a.b().getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 14) {
            Point point = new Point();
            defaultDisplay.getSize(point);
            i = point.x;
            i2 = point.y;
        } else {
            i = defaultDisplay.getWidth();
            i2 = defaultDisplay.getHeight();
        }
        if (i >= i2) {
            i = i2;
        }
        this.as = i;
        this.H = new AdColonyInterstitialAd(zone_id);
        this.H.j = Ad.AD_TYPE_NATIVE;
        this.H.k = Ad.AD_TYPE_NATIVE;
        a.l.d.a(zone_id, this.H);
        setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        a();
    }

    AdColonyNativeAdView(Activity context, String zone_id, int width, boolean is_private) {
        int i;
        int i2;
        super(context);
        a.e();
        this.d = context;
        this.e = zone_id;
        this.an = width;
        this.D = is_private;
        this.o = true;
        this.ax = a.b().getResources().getDisplayMetrics().density;
        Display defaultDisplay = a.b().getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 14) {
            Point point = new Point();
            defaultDisplay.getSize(point);
            i = point.x;
            i2 = point.y;
        } else {
            i = defaultDisplay.getWidth();
            i2 = defaultDisplay.getHeight();
        }
        if (i >= i2) {
            i = i2;
        }
        this.as = i;
        this.H = new AdColonyInterstitialAd(zone_id);
        this.H.j = Ad.AD_TYPE_NATIVE;
        this.H.k = Ad.AD_TYPE_NATIVE;
        setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        a();
    }

    void a() {
        float f;
        this.w = false;
        this.n = false;
        setWillNotDraw(false);
        this.H.x = this;
        if (this.y) {
            if (a.l == null || a.l.a == null || this.H == null || this.H.g == null || !a.l.a(this.H.g, true, false)) {
                this.r = true;
            } else {
                a.l.a.b(this.e);
            }
            this.H.b(true);
            this.at = this.H.h;
            this.f = a.j("video_filepath");
            this.ac = a.j("advertiser_name");
            this.ad = a.j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
            this.ae = a.j("title");
            this.af = a.j("poster_image");
            this.ag = a.j("unmute");
            this.ah = a.j("mute");
            this.ai = a.j("thumb_image");
            this.T = a.i("native_engagement_enabled");
            this.V = a.j("native_engagement_label");
            this.W = a.j("native_engagement_command");
            this.Z = a.j("native_engagement_type");
            this.G = a.i("v4iap_enabled");
            if (this.G) {
                this.ak = AdColonyIAPEngagement.AUTOMATIC;
            }
            this.aj = a.j("product_id");
            if (this.H.i == null || this.H.i.w == null) {
                this.v = true;
            } else {
                this.v = this.H.i.w.b;
            }
            if (this.at != null) {
                this.at.k();
            }
            if (this.H.i == null || this.H.i.w == null || !this.H.i.w.a || this.H.h == null) {
                a.ad = 13;
                return;
            }
            this.p = true;
            if (!this.D) {
                this.y = false;
            } else {
                return;
            }
        } else if (VERSION.SDK_INT < 14) {
            return;
        }
        this.al = this.H.i.v.b;
        this.am = this.H.i.v.c;
        this.ao = (int) (((double) this.am) * (((double) this.an) / ((double) this.al)));
        if (this.T) {
            this.U = new Button(a.b());
            this.U.setText(this.V);
            this.U.setGravity(17);
            this.U.setTextSize((float) ((int) (18.0d * (((double) this.an) / ((double) this.as)))));
            this.U.setPadding(0, 0, 0, 0);
            this.U.setBackgroundColor(this.aq);
            this.U.setTextColor(this.ar);
            this.U.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ AdColonyNativeAdView a;

                {
                    this.a = r1;
                }

                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == 0) {
                        float[] fArr = new float[3];
                        Color.colorToHSV(this.a.aq, fArr);
                        fArr[2] = fArr[2] * 0.8f;
                        this.a.U.setBackgroundColor(Color.HSVToColor(fArr));
                    } else if (action == 3) {
                        this.a.U.setBackgroundColor(this.a.aq);
                    } else if (action == 1) {
                        if (this.a.G) {
                            this.a.ak = AdColonyIAPEngagement.OVERLAY;
                            this.a.r = true;
                        } else {
                            if (this.a.Z.equals("install") || this.a.Z.equals("url")) {
                                a.l.d.b("native_overlay_click", this.a.H);
                                try {
                                    a.b().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.a.W)));
                                } catch (Exception e) {
                                    Toast.makeText(a.b(), "Unable to open store.", 0).show();
                                }
                            }
                            this.a.U.setBackgroundColor(this.a.aq);
                        }
                    }
                    return true;
                }
            });
        }
        this.K = new ADCImage(this.af, true, false);
        if (TextTrackStyle.DEFAULT_FONT_SCALE / (((float) this.K.f) / ((float) this.an)) > TextTrackStyle.DEFAULT_FONT_SCALE / (((float) this.K.g) / ((float) this.ao))) {
            f = TextTrackStyle.DEFAULT_FONT_SCALE / (((float) this.K.g) / ((float) this.ao));
        } else {
            f = TextTrackStyle.DEFAULT_FONT_SCALE / (((float) this.K.f) / ((float) this.an));
        }
        this.K.a((double) f, true);
        this.M = new ADCImage(this.ag, true, false);
        this.L = new ADCImage(this.ah, true, false);
        this.R = new ADCImage(this.ai, true, false);
        this.R.a((double) (TextTrackStyle.DEFAULT_FONT_SCALE / ((float) (((double) (((float) this.R.f) / ((float) this.an))) / ((((double) this.an) / 5.5d) / ((double) ((float) this.an)))))), true);
        this.L.a((double) (this.ax / 2.0f), true);
        this.M.a((double) (this.ax / 2.0f), true);
        this.O = new b(this, a.b());
        this.S = new ImageView(a.b());
        this.N = new ImageView(a.b());
        this.S.setImageBitmap(this.R.a);
        if (this.o) {
            this.N.setImageBitmap(this.L.a);
        } else {
            this.N.setImageBitmap(this.M.a);
        }
        LayoutParams layoutParams = new FrameLayout.LayoutParams(this.L.f, this.L.g, 48);
        layoutParams.setMargins(this.an - this.L.f, 0, 0, 0);
        this.N.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AdColonyNativeAdView a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                if (this.a.o) {
                    if (this.a.J != null) {
                        this.a.J.onAdColonyNativeAdMuted(this.a, true);
                    }
                    this.a.a(true, true);
                    this.a.u = true;
                } else if (this.a.Q == this.a.M.a) {
                    if (this.a.J != null) {
                        this.a.J.onAdColonyNativeAdMuted(this.a, false);
                    }
                    this.a.u = false;
                    this.a.a(false, true);
                }
            }
        });
        this.Q = this.L.a;
        if (this.r) {
            this.N.setVisibility(8);
        }
        if (this.s) {
            this.N.setVisibility(4);
        }
        if (VERSION.SDK_INT >= 14) {
            this.P = new a(this, a.b(), this.r);
        }
        if (VERSION.SDK_INT >= 14) {
            addView(this.P, new FrameLayout.LayoutParams(this.an, this.ao));
        }
        if (VERSION.SDK_INT < 14) {
            this.r = true;
        }
        addView(this.O, new FrameLayout.LayoutParams(this.an, this.ao));
        if (this.v && VERSION.SDK_INT >= 14 && this.A) {
            addView(this.N, layoutParams);
        }
        if (this.T) {
            addView(this.U, new FrameLayout.LayoutParams(this.an, this.ao / 5, 80));
        }
    }

    public boolean isReady() {
        if (this.H.a(true) && this.p && !this.C) {
            return true;
        }
        return false;
    }

    boolean a(boolean z) {
        if (this.H.a(true) && AdColony.isZoneNative(this.e)) {
            return true;
        }
        return false;
    }

    public int getNativeAdWidth() {
        return this.an;
    }

    public int getNativeAdHeight() {
        return this.T ? this.ao + (this.ao / 5) : this.ao;
    }

    public void setOverlayButtonColor(int color) {
        if (this.T) {
            this.U.setBackgroundColor(color);
        }
        this.aq = color;
    }

    public void setOverlayButtonTextColor(int color) {
        if (this.T) {
            this.U.setTextColor(color);
        }
        this.ar = color;
    }

    public void setOverlayButtonTypeface(Typeface tf, int style) {
        if (this.T) {
            this.U.setTypeface(tf, style);
        }
    }

    void a(boolean z, boolean z2) {
        if (z) {
            this.N.setImageBitmap(this.M.a);
            this.o = false;
            a(0.0f, z2);
            this.Q = this.M.a;
        } else if (!this.u && this.Q == this.M.a) {
            this.N.setImageBitmap(this.L.a);
            this.o = true;
            if (this.aa != null) {
                if (((double) this.aw) != 0.0d) {
                    a(this.aw, z2);
                } else {
                    a(0.25f, z2);
                }
            }
            this.Q = this.L.a;
        }
    }

    public void setMuted(boolean mute) {
        a(mute, false);
    }

    public void destroy() {
        l.c.b((Object) "[ADC] Native Ad Destroy called.");
        if (this.ab != null) {
            this.ab.release();
        }
        if (this.aa != null) {
            this.aa.release();
        }
        this.aa = null;
        a.ah.remove(this);
    }

    public ImageView getAdvertiserImage() {
        if (this.R == null) {
            this.R = new ADCImage(this.ai, true, false);
            this.R.a((double) (this.ax / 2.0f), true);
        }
        if (this.S == null) {
            this.S = new ImageView(a.b());
            this.S.setImageBitmap(this.R.a);
        }
        return this.S;
    }

    public String getTitle() {
        return this.ae;
    }

    public String getAdvertiserName() {
        return this.ac;
    }

    public String getDescription() {
        return this.ad;
    }

    public boolean canceled() {
        return this.F;
    }

    public boolean iapEnabled() {
        return this.G;
    }

    public String iapProductID() {
        return this.aj;
    }

    public AdColonyIAPEngagement iapEngagementType() {
        if (this.H == null || this.H.u != AdColonyIAPEngagement.END_CARD) {
            return this.ak;
        }
        return AdColonyIAPEngagement.END_CARD;
    }

    public AdColonyNativeAdView withListener(AdColonyNativeAdListener listener) {
        this.I = listener;
        this.H.w = listener;
        return this;
    }

    public AdColonyNativeAdView withMutedListener(AdColonyNativeAdMutedListener mute_listener) {
        this.J = mute_listener;
        return this;
    }

    public void pause() {
        l.c.b((Object) "[ADC] Native Ad Pause called.");
        if (this.aa != null && !this.r && this.aa.isPlaying() && VERSION.SDK_INT >= 14) {
            a.l.d.b("video_paused", this.H);
            this.s = true;
            this.aa.pause();
            this.O.setVisibility(0);
            this.N.setVisibility(4);
        }
    }

    public void resume() {
        l.c.b((Object) "[ADC] Native Ad Resume called.");
        if (this.aa != null && this.s && !this.r && VERSION.SDK_INT >= 14) {
            a.l.d.b("video_resumed", this.H);
            this.s = false;
            this.aa.seekTo(this.H.p);
            this.aa.start();
            this.O.setVisibility(4);
            this.N.setVisibility(0);
        }
    }

    void b(boolean z) {
        if (this.aa != null && this.N != null) {
            if (z) {
                this.aa.setVolume(0.0f, 0.0f);
                this.N.setImageBitmap(this.M.a);
                this.Q = this.M.a;
                return;
            }
            this.aa.setVolume(this.aw, this.aw);
            this.N.setImageBitmap(this.L.a);
            this.Q = this.L.a;
        }
    }

    void a(float f, boolean z) {
        if (VERSION.SDK_INT >= 14) {
            this.aw = f;
            if (this.aa != null && ((double) f) >= 0.0d && ((double) f) <= 1.0d) {
                if (!this.u) {
                    this.aa.setVolume(f, f);
                }
                if (!this.w) {
                    return;
                }
                g gVar;
                if (this.Q == this.M.a && ((double) f) > 0.0d && !this.u) {
                    gVar = new g();
                    gVar.b("user_action", z);
                    this.N.setImageBitmap(this.L.a);
                    this.Q = this.L.a;
                    a.l.d.a("sound_unmute", gVar, this.H);
                    this.o = true;
                } else if (this.Q == this.L.a && ((double) f) == 0.0d) {
                    gVar = new g();
                    gVar.b("user_action", z);
                    this.N.setImageBitmap(this.M.a);
                    this.Q = this.M.a;
                    a.l.d.a("sound_mute", gVar, this.H);
                    this.o = false;
                }
            } else if (((double) f) >= 0.0d && ((double) f) <= 1.0d) {
                this.av = f;
            }
        }
    }

    public void setVolume(float v) {
        a(v, false);
    }

    synchronized void b() {
        if ((this.r || this.aa == null || !this.aa.isPlaying()) && this.aa != null) {
            setVolume(this.aw);
            this.aa.start();
            a.l.a(this.H);
            this.H.q = true;
            if (this.I != null) {
                this.I.onAdColonyNativeAdStarted(false, this);
            }
        }
    }

    void c() {
        if (!this.r && this.aa != null && this.aa.isPlaying() && !this.s) {
            a.l.d.b("video_paused", this.H);
            this.aa.pause();
        }
    }

    public void onPrepared(MediaPlayer player) {
        l.c.b((Object) "[ADC] Native Ad onPrepared called.");
        this.w = true;
        if (this.Q == null || this.L.a == null) {
            this.O.setVisibility(0);
            this.N.setVisibility(8);
            this.r = true;
            this.aa = null;
            this.H.p = 0;
        } else if (this.o || !this.Q.equals(this.L.a)) {
            setVolume(this.aw);
        } else {
            b(true);
        }
    }

    public void onCompletion(MediaPlayer player) {
        this.O.setVisibility(0);
        this.N.setVisibility(8);
        this.H.j = Ad.AD_TYPE_NATIVE;
        this.H.k = Ad.AD_TYPE_NATIVE;
        this.H.q = true;
        this.r = true;
        if (this.aa != null) {
            this.aa.release();
        }
        this.aa = null;
        this.H.p = 0;
        g gVar = new g();
        gVar.b("ad_slot", this.H.h.k.d);
        gVar.b("replay", false);
        a.l.d.a("native_complete", gVar, this.H);
        if (this.I != null) {
            this.I.onAdColonyNativeAdFinished(false, this);
        }
        this.z = true;
    }

    public boolean onError(MediaPlayer player, int what, int extra) {
        this.O.setVisibility(0);
        this.N.setVisibility(8);
        this.r = true;
        this.w = true;
        this.aa = null;
        this.H.p = 0;
        return true;
    }

    public void onDraw(Canvas canvas) {
        if (this.g != null) {
            Rect rect = new Rect();
            if (!this.g.hasFocus()) {
                this.g.requestFocus();
            }
            if (!(this.r || this.aa == null)) {
                this.ap = this.aa.getCurrentPosition();
            }
            if (this.ap != 0) {
                this.H.p = this.ap;
            }
            getLocalVisibleRect(rect);
            boolean z = rect.bottom - rect.top > getNativeAdHeight() / 2;
            if ((z || this.l) && (!this.l || (z && (rect.bottom - rect.top >= getNativeAdHeight() || rect.top == 0)))) {
                if (this.r || this.aa == null || !this.aa.isPlaying()) {
                    if (!this.O.a) {
                        canvas.drawARGB(255, 0, 0, 0);
                    }
                } else if (this.w) {
                    this.H.j = Ad.AD_TYPE_NATIVE;
                    this.H.k = Ad.AD_TYPE_NATIVE;
                    a.l.a(((double) this.aa.getCurrentPosition()) / ((double) this.aa.getDuration()), this.H);
                    if (!this.E && this.aa.getCurrentPosition() > 2000) {
                        this.E = true;
                        a.l.a("native_start", "{\"ad_slot\":" + this.H.h.k.d + ", \"replay\":false}", this.H);
                    }
                } else {
                    canvas.drawARGB(255, 0, 0, 0);
                }
            } else if (!(this.r || this.aa == null || !this.aa.isPlaying() || this.s)) {
                l.c.b((Object) "[ADC] Scroll Pause");
                a.l.d.b("video_paused", this.H);
                this.aa.pause();
                this.O.setVisibility(0);
            }
            if (!this.x && !this.r) {
                invalidate();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (VERSION.SDK_INT >= 14) {
            return false;
        }
        if (event.getAction() == 1 && a.v && q.c()) {
            a.K = this.H;
            a.l.a.a(this.e, this.H.i);
            ADCVideo.a();
            this.H.s = this.z;
            this.H.r = true;
            this.H.j = Ad.AD_TYPE_NATIVE;
            this.H.k = JSController.FULL_SCREEN;
            a.v = false;
            a.l.d.b("video_expanded", this.H);
            if (this.I != null) {
                this.I.onAdColonyNativeAdStarted(true, this);
            }
            if (a.m) {
                l.a.b((Object) "Launching AdColonyOverlay");
                a.b().startActivity(new Intent(a.b(), AdColonyOverlay.class));
            } else {
                l.a.b((Object) "Launching AdColonyFullscreen");
                a.b().startActivity(new Intent(a.b(), AdColonyFullscreen.class));
            }
            if (this.r) {
                this.H.f = -1;
                ag agVar = this.H.h.k;
                agVar.d++;
                a.l.a("start", "{\"ad_slot\":" + this.H.h.k.d + ", \"replay\":" + this.H.s + "}", this.H);
                a.l.h.a(this.H.g, this.H.i.d);
            }
            this.r = true;
            this.z = true;
        }
        return true;
    }

    public void notifyAddedToListView() {
        if (this.k) {
            ((a) this.P).onSurfaceTextureAvailable(this.h, this.i, this.j);
        } else {
            this.k = true;
        }
    }

    public void prepareForListView() {
        this.l = true;
    }
}
