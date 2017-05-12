package com.jirbo.adcolony;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.VideoView;
import com.immersion.hapticmediasdk.HapticContentSDK;
import com.immersion.hapticmediasdk.HapticContentSDKFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public abstract class ADCVideo extends Activity {
    static int a;
    static int b;
    static int c;
    static boolean d;
    static boolean e;
    static boolean f;
    static boolean g;
    static Activity h;
    static boolean i;
    static boolean j;
    int A;
    String B = "";
    String C = "";
    boolean D = true;
    boolean E = true;
    e F;
    ae G;
    AdColonyAd H;
    HapticContentSDK I;
    String J;
    boolean K;
    boolean L = true;
    String M = "Your purchase will begin shortly!";
    VideoView N;
    FrameLayout O;
    FrameLayout P;
    FrameLayout Q;
    Rect R = new Rect();
    ADCImage S;
    a T;
    FileInputStream U;
    boolean k;
    boolean l;
    boolean m;
    boolean n;
    boolean o;
    boolean p;
    double q;
    double r;
    long s;
    long t;
    int u;
    int v;
    int w;
    int x;
    int y;
    int z;

    class a extends View {
        Rect a = new Rect();
        final /* synthetic */ ADCVideo b;

        public a(ADCVideo aDCVideo, Activity activity) {
            this.b = aDCVideo;
            super(activity);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawARGB(255, 0, 0, 0);
            getDrawingRect(this.a);
            this.b.S.a(canvas, (this.a.width() - this.b.S.f) / 2, (this.a.height() - this.b.S.g) / 2);
            invalidate();
        }
    }

    static void a() {
        a = 0;
        d = false;
        e = false;
        g = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        int i;
        int i2 = 1;
        a.ab = false;
        super.onCreate(savedInstanceState);
        this.K = a.i("haptics_enabled");
        this.J = a.j("haptics_filepath");
        this.M = a.j("in_progress");
        this.C = a.j("video_filepath");
        if (this.K) {
            try {
                this.I = HapticContentSDKFactory.GetNewSDKInstance(0, this);
                this.I.openHaptics(this.J);
            } catch (Exception e) {
                e.printStackTrace();
                this.K = false;
            }
            if (this.I == null) {
                this.K = false;
            }
        }
        h = this;
        if (a.i("video_enabled")) {
            z = false;
        } else {
            z = true;
        }
        a.R = z;
        if (a.i("end_card_enabled")) {
            z = false;
        } else {
            z = true;
        }
        a.Q = z;
        a.S = a.i("load_timeout_enabled");
        a.T = a.h("load_timeout");
        for (i = 0; i < a.ah.size(); i++) {
            if (a.ah.get(i) != null) {
                AdColonyNativeAdView adColonyNativeAdView = (AdColonyNativeAdView) a.ah.get(i);
                if (adColonyNativeAdView.aa != null) {
                    adColonyNativeAdView.P.setVisibility(4);
                }
                if (adColonyNativeAdView.N != null) {
                    adColonyNativeAdView.N.setVisibility(4);
                }
            }
        }
        this.H = a.K;
        if (this.H == null) {
            finish();
            return;
        }
        if (a.i("v4iap_enabled")) {
            this.H.u = AdColonyIAPEngagement.AUTOMATIC;
            this.H.t = true;
            this.H.m = a.j("product_id");
        }
        e = this.H.s;
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        if (a.m) {
            i = getResources().getConfiguration().orientation;
            int i3 = (i == 0 || i == 6 || i == 2) ? 6 : 7;
            a.w = i3;
            if (VERSION.SDK_INT < 10 || Build.MODEL.equals("Kindle Fire")) {
                if (Build.MODEL.equals("Kindle Fire")) {
                    getRequestedOrientation();
                    switch (((WindowManager) getSystemService("window")).getDefaultDisplay().getRotation()) {
                        case 0:
                            break;
                        case 1:
                            i2 = 0;
                            break;
                        case 2:
                            i2 = 9;
                            break;
                        default:
                            i2 = 8;
                            break;
                    }
                }
                i2 = i;
                a.w = i2;
                setRequestedOrientation(i2);
            } else {
                setRequestedOrientation(a.w);
            }
        } else if (VERSION.SDK_INT >= 10) {
            setRequestedOrientation(6);
        } else {
            setRequestedOrientation(0);
        }
        setVolumeControlStream(3);
        this.F = new e(this);
        this.O = new FrameLayout(this);
        this.G = new ae(this);
        this.Q = new FrameLayout(this);
        this.T = new a(this, this);
        this.S = new ADCImage(a.j("browser_icon"));
        AdColonyBrowser.A = false;
        a.L = this;
        a.M = this;
    }

    public void onResume() {
        i = true;
        super.onResume();
        if (a.a()) {
            this.p = true;
            finish();
        }
        b();
        if (this.D) {
            this.D = false;
            if (!d) {
                if (this.G.Q) {
                    this.P.addView(this.G.a);
                }
                if (this.G.Q) {
                    this.P.setVisibility(4);
                }
                if (Build.MODEL.equals("Kindle Fire")) {
                    this.G.m = 20;
                }
                if (Build.MODEL.equals("SCH-I800")) {
                    this.G.m = 25;
                }
                this.O.addView(this.F, new LayoutParams(this.y, this.z, 17));
                if (this.G.Q) {
                    this.O.addView(this.P, new LayoutParams(this.u, this.v - this.G.m, 17));
                }
                this.O.addView(this.G, new LayoutParams(this.u, this.v, 17));
            }
        }
        if (g) {
            this.Q.removeView(this.T);
            this.Q.addView(this.T);
            setContentView(this.Q);
        } else {
            setContentView(this.O);
            if (d) {
                this.s = System.currentTimeMillis();
            }
        }
        this.F.a(this.G);
        this.F.a(this.G);
        try {
            this.U = new FileInputStream(this.C);
            this.F.a(this.U.getFD());
            if (!j) {
                onWindowFocusChanged(true);
            }
            if (a.R) {
                this.G.a();
                this.G.c();
            }
        } catch (IOException e) {
            a.e("Unable to play video: " + a.j("video_filepath"));
            this.p = true;
            finish();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (a.ab) {
            a.v = true;
            a.L = null;
            a.ab = true;
        } else {
            a.v = true;
            a.L = null;
            a.ab = true;
        }
    }

    boolean b() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        this.u = defaultDisplay.getWidth();
        this.v = defaultDisplay.getHeight();
        this.A = ViewCompat.MEASURED_STATE_MASK;
        getWindow().setBackgroundDrawable(new ColorDrawable(this.A));
        int i = this.u;
        int i2 = this.v;
        this.y = i;
        this.z = i2;
        if (!a.B) {
            return false;
        }
        a.B = false;
        return true;
    }

    public void onWindowFocusChanged(boolean has_focus) {
        if (has_focus) {
            j = false;
            if (d || !i) {
                if (g) {
                    if (this.N != null) {
                        this.N.seekTo(b);
                        this.N.start();
                        return;
                    }
                    if (this.Q != null) {
                        this.Q.removeAllViews();
                    }
                    setContentView(this.O);
                    return;
                } else if (d) {
                    this.G.invalidate();
                    return;
                } else {
                    return;
                }
            } else if (this.F != null) {
                if (c != 0) {
                    a = c;
                }
                c = 0;
                this.F.seekTo(a);
                if (a.m) {
                    Handler handler = new Handler();
                    Runnable anonymousClass1 = new Runnable(this) {
                        final /* synthetic */ ADCVideo a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.F.setBackgroundColor(0);
                        }
                    };
                    this.F.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                    handler.postDelayed(anonymousClass1, 900);
                } else {
                    this.F.setBackgroundColor(0);
                }
                if (!w.H) {
                    this.G.S = false;
                    this.F.start();
                    if (this.K) {
                        if (this.L) {
                            this.I.play();
                        } else {
                            this.I.resume();
                        }
                        this.L = false;
                    }
                }
                this.G.requestFocus();
                this.G.invalidate();
                return;
            } else {
                return;
            }
        }
        if (i) {
            if (this.K) {
                this.I.pause();
            }
            a = this.F.getCurrentPosition();
            this.F.pause();
        }
        j = true;
    }

    public void onPause() {
        i = false;
        if (a.G == null || !this.p) {
        }
        if (!g) {
            b = 0;
        } else if (this.N != null) {
            b = this.N.getCurrentPosition();
            this.N.stopPlayback();
        }
        if (d) {
            View view = new View(this);
            view.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            setContentView(view);
            this.t = System.currentTimeMillis();
            if (!isFinishing()) {
                this.r += ((double) (this.t - this.s)) / 1000.0d;
            }
        }
        if (this.F != null) {
            if (this.F.getCurrentPosition() != 0) {
                a = this.F.getCurrentPosition();
            }
            this.F.a();
            this.F.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            if (this.K) {
                this.I.pause();
            }
        } else {
            a = 0;
        }
        this.G.A = true;
        this.G.I = false;
        this.G.H = false;
        this.G.J = false;
        this.G.u = 0;
        this.G.t = 0;
        this.G.invalidate();
        super.onPause();
    }

    public boolean onKeyUp(int keycode, KeyEvent event) {
        if (w.I != null && w.I.onKeyDown(keycode, event)) {
            return true;
        }
        if (keycode == 4) {
            if (d) {
                if (g) {
                    this.N.stopPlayback();
                    g = false;
                    this.Q.removeAllViews();
                    setContentView(this.O);
                } else if (this.G != null && this.G.t == 0) {
                    a.ab = true;
                    this.G.f();
                }
            } else if (this.G != null && w.I != null) {
                Iterator it = w.I.o.iterator();
                while (it.hasNext()) {
                    ((ADCImage) it.next()).a();
                }
                w.I = null;
                w.H = false;
                this.F.start();
            } else if (this.G != null && this.G.M && this.G.O) {
                a.ab = true;
                this.G.g();
            }
            return true;
        } else if (keycode == 82) {
            return true;
        } else {
            return super.onKeyUp(keycode, event);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == 4) {
            return true;
        }
        return super.onKeyDown(keycode, event);
    }

    void a(String str) {
        this.B = str;
        g = true;
        this.N = new VideoView(this);
        this.N.setVideoURI(Uri.parse(str));
        new MediaController(this).setMediaPlayer(this.N);
        this.N.setLayoutParams(new LayoutParams(this.u, this.v, 17));
        this.Q.addView(this.N);
        this.Q.addView(this.T);
        setContentView(this.Q);
        this.N.setOnCompletionListener(new OnCompletionListener(this) {
            final /* synthetic */ ADCVideo a;

            {
                this.a = r1;
            }

            public void onCompletion(MediaPlayer media_player) {
                this.a.setContentView(this.a.O);
                this.a.Q.removeAllViews();
                ADCVideo.g = false;
            }
        });
        this.N.setOnPreparedListener(new OnPreparedListener(this) {
            final /* synthetic */ ADCVideo a;

            {
                this.a = r1;
            }

            public void onPrepared(MediaPlayer media_player) {
                this.a.Q.removeViewAt(1);
            }
        });
        this.N.start();
    }
}
