package com.inmobi.monetization.internal;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.inmobi.commons.AnimationType;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.network.Response;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.configs.NetworkEventType;
import com.inmobi.monetization.internal.imai.IMAIController;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.container.IMWebView.IMWebViewListener;
import com.inmobi.re.container.IMWebView.ViewState;
import com.inmobi.re.container.mraidimpl.ResizeDimensions;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class BannerAd extends Ad {
    protected static final String KEY_MANUAL_REFRESH = "u-rt";
    protected static final String KEY_TYPE_OF_ADREQ = "requestactivity";
    protected static final String VALUE_OF_ADREQ = "AdRequest";
    AnimationType b = AnimationType.ROTATE_HORIZONTAL_AXIS;
    private Activity c;
    private IMWebView d;
    private IMWebView e;
    private boolean f = true;
    private boolean g = false;
    private int h = 15;
    private long i = 0;
    private boolean j = true;
    private int k = Initializer.getConfigParams().getDefaultRefreshRate();
    private d l;
    private Animation m;
    public IMWebView mCurrentWebView;
    private Animation n;
    private long o = 0;
    private boolean p = false;
    private boolean q = true;
    private AtomicBoolean r = new AtomicBoolean(false);
    private Response s = null;
    private boolean t = false;
    private IMWebViewListener u = new IMWebViewListener(this) {
        final /* synthetic */ BannerAd a;

        {
            this.a = r1;
        }

        public void onExpandClose() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onDismissAdScreen();
            }
        }

        public void onExpand() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onShowAdScreen();
            }
        }

        public void onLeaveApplication() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onLeaveApplication();
            }
        }

        public void onError() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
            }
        }

        public void onShowAdScreen() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onShowAdScreen();
            }
            this.a.r.set(true);
        }

        public void onDismissAdScreen() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onDismissAdScreen();
            }
            this.a.r.set(false);
        }

        public void onResize(ResizeDimensions resizeDimensions) {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onShowAdScreen();
            }
        }

        public void onResizeClose() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onDismissAdScreen();
            }
        }

        public void onUserInteraction(Map<String, String> map) {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onAdInteraction(map);
            }
        }

        public void onIncentCompleted(Map<Object, Object> map) {
        }

        public void onDisplayFailed() {
        }
    };
    private a v = new a(this);
    private AnimationListener w = new AnimationListener(this) {
        final /* synthetic */ BannerAd a;

        {
            this.a = r1;
        }

        public void onAnimationEnd(Animation animation) {
            this.a.h();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    };

    static class a extends Handler {
        private final WeakReference<BannerAd> a;

        public a(BannerAd bannerAd) {
            this.a = new WeakReference(bannerAd);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r7) {
            /*
            r6 = this;
            r0 = r6.a;
            r0 = r0.get();
            r0 = (com.inmobi.monetization.internal.BannerAd) r0;
            if (r0 == 0) goto L_0x000f;
        L_0x000a:
            r1 = r7.what;	 Catch:{ Exception -> 0x0051 }
            switch(r1) {
                case 101: goto L_0x005f;
                case 102: goto L_0x0013;
                default: goto L_0x000f;
            };
        L_0x000f:
            super.handleMessage(r7);
            return;
        L_0x0013:
            r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0051 }
            r4 = r0.i;	 Catch:{ Exception -> 0x0051 }
            r2 = r2 - r4;
            r1 = r0.s;	 Catch:{ Exception -> 0x0051 }
            r4 = com.inmobi.monetization.internal.configs.NetworkEventType.RENDER_TIMEOUT;	 Catch:{ Exception -> 0x0051 }
            r0.collectMetrics(r1, r2, r4);	 Catch:{ Exception -> 0x0051 }
            r1 = r0.e();	 Catch:{ Exception -> 0x0051 }
            if (r1 == 0) goto L_0x005a;
        L_0x002b:
            r1 = 0;
            r0.d = r1;	 Catch:{ Exception -> 0x0051 }
        L_0x002f:
            r1 = r0.mCurrentWebView;	 Catch:{ Exception -> 0x0051 }
            if (r1 == 0) goto L_0x0045;
        L_0x0033:
            r1 = r0.mCurrentWebView;	 Catch:{ Exception -> 0x0051 }
            r1.cancelLoad();	 Catch:{ Exception -> 0x0051 }
            r1 = r0.mCurrentWebView;	 Catch:{ Exception -> 0x0051 }
            r1.stopLoading();	 Catch:{ Exception -> 0x0051 }
            r1 = r0.mCurrentWebView;	 Catch:{ Exception -> 0x0051 }
            r1.deinit();	 Catch:{ Exception -> 0x0051 }
            r1 = 0;
            r0.mCurrentWebView = r1;	 Catch:{ Exception -> 0x0051 }
        L_0x0045:
            r1 = r0.mAdListener;	 Catch:{ Exception -> 0x0051 }
            if (r1 == 0) goto L_0x000f;
        L_0x0049:
            r0 = r0.mAdListener;	 Catch:{ Exception -> 0x0051 }
            r1 = com.inmobi.monetization.internal.AdErrorCode.AD_RENDERING_TIMEOUT;	 Catch:{ Exception -> 0x0051 }
            r0.onAdRequestFailed(r1);	 Catch:{ Exception -> 0x0051 }
            goto L_0x000f;
        L_0x0051:
            r0 = move-exception;
            r1 = "[InMobi]-[Monetization]";
            r2 = "Exception hanlde message adview";
            com.inmobi.commons.internal.Log.internal(r1, r2, r0);
            goto L_0x000f;
        L_0x005a:
            r1 = 0;
            r0.e = r1;	 Catch:{ Exception -> 0x0051 }
            goto L_0x002f;
        L_0x005f:
            r1 = r0.c;	 Catch:{ Exception -> 0x0051 }
            r1 = r1.hasWindowFocus();	 Catch:{ Exception -> 0x0051 }
            if (r1 != 0) goto L_0x0071;
        L_0x0069:
            r0 = "[InMobi]-[Monetization]";
            r1 = "Activity is not in the foreground. New ad will not be loaded.";
            com.inmobi.commons.internal.Log.debug(r0, r1);	 Catch:{ Exception -> 0x0051 }
            goto L_0x000f;
        L_0x0071:
            r1 = com.inmobi.re.container.mraidimpl.MRAIDInterstitialController.isInterstitialDisplayed;	 Catch:{ Exception -> 0x0051 }
            r1 = r1.get();	 Catch:{ Exception -> 0x0051 }
            if (r1 == 0) goto L_0x0081;
        L_0x0079:
            r0 = "[InMobi]-[Monetization]";
            r1 = "Ad cannot be loaded.Interstitial Ad is displayed.";
            com.inmobi.commons.internal.Log.debug(r0, r1);	 Catch:{ Exception -> 0x0051 }
            goto L_0x000f;
        L_0x0081:
            r1 = 1;
            r0.a(r1);	 Catch:{ Exception -> 0x0051 }
            goto L_0x000f;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.inmobi.monetization.internal.BannerAd.a.handleMessage(android.os.Message):void");
        }
    }

    private void h() {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r5 = this;
        r4 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        r0 = r5.g;
        if (r0 != 0) goto L_0x0044;
    L_0x0006:
        r0 = r5.d();	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r0.removeAllViews();	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        if (r0 == 0) goto L_0x0032;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
    L_0x000f:
        r1 = r5.e();	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        if (r1 == 0) goto L_0x0045;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
    L_0x0015:
        r1 = new android.widget.RelativeLayout$LayoutParams;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r2 = -1;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r3 = -1;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r1.<init>(r2, r3);	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r2 = r5.d;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r5.mCurrentWebView = r2;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r2 = r5.d;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r0.addView(r2, r1);	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
    L_0x0025:
        r0 = r5.e();	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        if (r0 != 0) goto L_0x0074;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
    L_0x002b:
        r0 = 1;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
    L_0x002c:
        r5.b(r0);	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r5.f();	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
    L_0x0032:
        r0 = r5.mAdListener;
        if (r0 == 0) goto L_0x003b;
    L_0x0036:
        r0 = r5.mAdListener;
        r0.onAdRequestSucceeded();
    L_0x003b:
        r0 = r5.v;
        if (r0 == 0) goto L_0x0044;
    L_0x003f:
        r0 = r5.v;
        r0.removeMessages(r4);
    L_0x0044:
        return;
    L_0x0045:
        r1 = new android.widget.RelativeLayout$LayoutParams;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r2 = -1;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r3 = -1;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r1.<init>(r2, r3);	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r2 = r5.e;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r5.mCurrentWebView = r2;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r2 = r5.e;	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r0.addView(r2, r1);	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        goto L_0x0025;
    L_0x0056:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r1 = "[InMobi]-[Monetization]";	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r2 = "Error swapping banner ads";	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        com.inmobi.commons.internal.Log.debug(r1, r2, r0);	 Catch:{ Exception -> 0x0056, all -> 0x0076 }
        r0 = r5.mAdListener;
        if (r0 == 0) goto L_0x006a;
    L_0x0065:
        r0 = r5.mAdListener;
        r0.onAdRequestSucceeded();
    L_0x006a:
        r0 = r5.v;
        if (r0 == 0) goto L_0x0044;
    L_0x006e:
        r0 = r5.v;
        r0.removeMessages(r4);
        goto L_0x0044;
    L_0x0074:
        r0 = 0;
        goto L_0x002c;
    L_0x0076:
        r0 = move-exception;
        r1 = r5.mAdListener;
        if (r1 == 0) goto L_0x0080;
    L_0x007b:
        r1 = r5.mAdListener;
        r1.onAdRequestSucceeded();
    L_0x0080:
        r1 = r5.v;
        if (r1 == 0) goto L_0x0089;
    L_0x0084:
        r1 = r5.v;
        r1.removeMessages(r4);
    L_0x0089:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.inmobi.monetization.internal.BannerAd.h():void");
    }

    public BannerAd(Activity activity, IMBanner iMBanner, String str, int i) {
        super(str);
        this.h = i;
        this.c = activity;
        this.t = initialize();
    }

    public BannerAd(Activity activity, IMBanner iMBanner, long j, int i) {
        super(j);
        this.h = i;
        this.o = j;
        this.c = activity;
        this.t = initialize();
    }

    public void loadAd() {
        a(false);
    }

    public void refreshAd() {
        this.p = true;
        this.v.removeMessages(101);
        if (this.k > 0) {
            this.v.sendEmptyMessageDelayed(101, (long) (this.k * 1000));
        }
    }

    private void a(boolean z) {
        if (!this.t) {
            this.t = initialize();
        }
        if (this.t) {
            this.g = false;
            this.p = z;
            if (!g()) {
                super.loadAd();
            } else if (this.mAdListener != null) {
                AdErrorCode adErrorCode = AdErrorCode.INVALID_REQUEST;
                adErrorCode.setMessage("Ad click is in progress.Cannot load new ad");
                Log.debug(Constants.LOG_TAG, "Ad click is in progress.Cannot load new ad");
                this.mAdListener.onAdRequestFailed(adErrorCode);
            }
            this.v.removeMessages(101);
            if (this.k > 0) {
                this.v.sendEmptyMessageDelayed(101, (long) (this.k * 1000));
                return;
            }
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    public void stopRefresh() {
        if (this.t) {
            this.v.removeMessages(101);
        } else {
            Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
        }
    }

    protected boolean initialize() {
        if (this.h < 0) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_INVALID_AD_SIZE);
            return false;
        } else if (this.c == null) {
            Log.debug(Constants.LOG_TAG, "Activity cannot be null");
            return false;
        } else {
            this.c = b.a(this.c);
            if (this.d == null) {
                this.d = new IMWebView(this.c, this.u, false, false);
                if (!this.q) {
                    this.d.disableHardwareAcceleration();
                }
                this.d.addJavascriptInterface(new IMAIController(this.d), IMAIController.IMAI_BRIDGE);
            }
            if (this.e == null) {
                this.e = new IMWebView(this.c, this.u, false, false);
                this.mCurrentWebView = this.e;
                if (!this.q) {
                    this.e.disableHardwareAcceleration();
                }
                this.e.addJavascriptInterface(new IMAIController(this.e), IMAIController.IMAI_BRIDGE);
            }
            this.l = new d(this, this.w);
            return super.initialize();
        }
    }

    public View getView() {
        return this.mCurrentWebView;
    }

    private ViewGroup d() {
        ViewGroup viewGroup = (ViewGroup) this.d.getParent();
        if (viewGroup == null) {
            return (ViewGroup) this.e.getParent();
        }
        return viewGroup;
    }

    protected Map<String, String> getAdFormatParams() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("format", AD_FORMAT.IMAI.toString().toLowerCase(Locale.getDefault()));
        hashMap.put("mk-ads", "1");
        hashMap.put(KEY_TYPE_OF_ADREQ, VALUE_OF_ADREQ);
        if (this.p) {
            hashMap.put(KEY_MANUAL_REFRESH, String.valueOf(1));
        } else {
            hashMap.put(KEY_MANUAL_REFRESH, String.valueOf(0));
        }
        if (this.o > 0) {
            hashMap.put("placement-size", b() + "x" + c());
        }
        hashMap.put("mk-ad-slot", String.valueOf(this.h));
        return hashMap;
    }

    private boolean e() {
        return this.f;
    }

    public void handleResponse(c cVar, Response response) {
        if (response != null) {
            try {
                if (this.c != null) {
                    this.s = response;
                    this.c.runOnUiThread(new Runnable(this) {
                        final /* synthetic */ BannerAd a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.i();
                        }
                    });
                }
            } catch (Exception e) {
                Log.debug(Constants.LOG_TAG, "Failed to render banner ad");
                if (this.mAdListener != null) {
                    this.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
                }
            }
        }
    }

    public void disableHardwareAcceleration() {
        if (this.t) {
            this.q = false;
            if (this.mCurrentWebView != null) {
                this.mCurrentWebView.disableHardwareAcceleration();
                return;
            }
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    public void stopLoading() {
        if (this.t) {
            super.stopLoading();
            if (this.v != null && this.v.hasMessages(102)) {
                this.v.removeMessages(102);
            }
            this.g = true;
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    private void f() {
        try {
            if (this.e != null) {
                this.e.setBackgroundColor(0);
            }
            if (this.d != null) {
                this.d.setBackgroundColor(0);
            }
        } catch (Throwable e) {
            Log.debug(Constants.LOG_TAG, "Error setNormalBGColor", e);
        }
    }

    private void b(boolean z) {
        this.f = z;
        if (z) {
            this.d.deinit();
            this.d = null;
            return;
        }
        this.e.deinit();
        this.e = null;
    }

    private boolean g() {
        IMWebView iMWebView;
        if (e()) {
            iMWebView = this.e;
        } else {
            iMWebView = this.d;
        }
        String state = iMWebView.getState();
        Log.debug(Constants.LOG_TAG, "Current Ad State: " + state);
        if (ViewState.EXPANDED.toString().equalsIgnoreCase(state) || ViewState.RESIZED.toString().equalsIgnoreCase(state) || ViewState.RESIZING.toString().equalsIgnoreCase(state) || ViewState.EXPANDING.toString().equalsIgnoreCase(state)) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_AD_STATE);
            return true;
        } else if (iMWebView.isBusy()) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_AD_BUSY);
            return true;
        } else if (this.r.get()) {
            return true;
        } else {
            return false;
        }
    }

    private void i() {
        if (!this.g && this.s != null) {
            String responseBody = this.s.getResponseBody();
            long currentTimeMillis = System.currentTimeMillis() - this.mFetchStartTime;
            if (responseBody != null) {
                String replace = responseBody.replace("@__imm_aft@", "" + currentTimeMillis);
                if (VERSION.SDK_INT <= 8) {
                    replace.replaceAll("%", "%25");
                }
                if (e()) {
                    if (this.d == null) {
                        this.d = new IMWebView(this.c, this.u, false, false);
                        if (!this.q) {
                            this.d.disableHardwareAcceleration();
                        }
                    }
                    this.mCurrentWebView = this.d;
                } else {
                    if (this.e == null) {
                        this.e = new IMWebView(this.c, this.u, false, false);
                        if (!this.q) {
                            this.e.disableHardwareAcceleration();
                        }
                    }
                    this.mCurrentWebView = this.e;
                }
                this.mCurrentWebView.addJavascriptInterface(new IMAIController(this.mCurrentWebView), IMAIController.IMAI_BRIDGE);
                this.i = System.currentTimeMillis();
                this.v.sendEmptyMessageDelayed(102, (long) Initializer.getConfigParams().getRenderTimeOut());
                this.mCurrentWebView.resetMraid();
                this.mCurrentWebView.loadDataWithBaseURL("", replace, "text/html", null, null);
                collectMetrics(this.s, System.currentTimeMillis() - this.mFetchStartTime, NetworkEventType.RENDER_COMPLETE);
                if (this.j) {
                    h();
                    this.j = false;
                    return;
                } else if (this.b == AnimationType.ANIMATION_OFF) {
                    h();
                    return;
                } else {
                    this.l.a(this.b);
                    return;
                }
            }
            Log.debug(Constants.LOG_TAG, "Cannot load Ad. Invalid Ad Response");
            if (this.mAdListener != null) {
                this.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
            }
        }
    }

    public void setRefreshInterval(int i) {
        if (this.t) {
            int minimumRefreshRate = Initializer.getConfigParams().getMinimumRefreshRate();
            this.v.removeMessages(101);
            if (i <= 0) {
                this.k = 0;
                return;
            }
            if (i < minimumRefreshRate) {
                Log.debug(Constants.LOG_TAG, "Refresh Interval cannot be less than " + minimumRefreshRate + " seconds. Setting refresh rate to " + minimumRefreshRate);
                this.k = minimumRefreshRate;
            } else {
                this.k = i;
            }
            if (this.k != 0) {
                this.v.sendEmptyMessageDelayed(101, (long) (this.k * 1000));
                return;
            }
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    public void setAnimation(AnimationType animationType) {
        this.b = animationType;
    }

    Animation a() {
        return this.m;
    }

    void a(Animation animation) {
        this.m = animation;
    }

    void b(Animation animation) {
        this.n = animation;
    }

    int b() {
        try {
            ViewGroup d = d();
            if (d != null) {
                return d.getWidth();
            }
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Exception getting width of banner view", e);
        }
        return 0;
    }

    int c() {
        try {
            ViewGroup d = d();
            if (d != null) {
                return d.getHeight();
            }
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Exception getting height of banner view", e);
        }
        return 0;
    }

    void c(Animation animation) {
        try {
            ViewGroup d = d();
            if (d != null) {
                d.startAnimation(animation);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            Log.internal(Constants.LOG_TAG, "Exception animating  banner view", e);
        }
    }

    public void setAdSize(int i) {
        this.h = i;
    }
}
