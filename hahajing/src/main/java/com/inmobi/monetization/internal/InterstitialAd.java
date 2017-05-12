package com.inmobi.monetization.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import com.inmobi.androidsdk.IMBrowserActivity;
import com.inmobi.commons.ads.cache.AdDatabaseHelper;
import com.inmobi.commons.data.DeviceInfo;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.network.Response;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.configs.NetworkEventType;
import com.inmobi.monetization.internal.imai.IMAIController;
import com.inmobi.monetization.internal.imai.IMAIController.InterstitialAdStateListener;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.container.IMWebView.IMWebViewListener;
import com.inmobi.re.container.mraidimpl.ResizeDimensions;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.xmlrpc.android.IXMLRPCSerializer;

public class InterstitialAd extends Ad implements InterstitialAdStateListener {
    long b = 0;
    boolean c = true;
    private Activity d;
    private long e;
    private IMWebView f;
    private long g = 0;
    private Object h = null;
    private Response i = null;
    private boolean j = false;
    private boolean k = false;
    private a l = new a(this);
    private IMWebViewListener m = new IMWebViewListener(this) {
        final /* synthetic */ InterstitialAd a;

        {
            this.a = r1;
        }

        public void onExpand() {
        }

        public void onExpandClose() {
        }

        public void onLeaveApplication() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onLeaveApplication();
            }
        }

        public void onError() {
            Log.debug(Constants.LOG_TAG, "Error loading the interstitial ad ");
            this.a.l.removeMessages(301);
            this.a.f = null;
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
            }
        }

        public void onShowAdScreen() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onShowAdScreen();
            }
        }

        public void onDismissAdScreen() {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onDismissAdScreen();
            }
        }

        public void onResize(ResizeDimensions resizeDimensions) {
        }

        public void onResizeClose() {
        }

        public void onUserInteraction(Map<String, String> map) {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onAdInteraction(map);
            }
        }

        public void onIncentCompleted(Map<Object, Object> map) {
            if (this.a.mAdListener != null) {
                this.a.mAdListener.onIncentCompleted(map);
            }
        }

        public void onDisplayFailed() {
            this.a.b();
        }
    };

    static class a extends Handler {
        private final WeakReference<InterstitialAd> a;

        public a(InterstitialAd interstitialAd) {
            this.a = new WeakReference(interstitialAd);
        }

        public void handleMessage(Message message) {
            InterstitialAd interstitialAd = (InterstitialAd) this.a.get();
            if (interstitialAd != null) {
                try {
                    switch (message.what) {
                        case 301:
                            interstitialAd.f.cancelLoad();
                            interstitialAd.f.stopLoading();
                            interstitialAd.f.deinit();
                            interstitialAd.f = null;
                            interstitialAd.collectMetrics(interstitialAd.i, System.currentTimeMillis() - interstitialAd.e, NetworkEventType.RENDER_TIMEOUT);
                            if (interstitialAd.mAdListener != null) {
                                interstitialAd.mAdListener.onAdRequestFailed(AdErrorCode.AD_RENDERING_TIMEOUT);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                } catch (Throwable e) {
                    Log.internal(Constants.LOG_TAG, "Exception handling message in Interstitial", e);
                }
            }
        }
    }

    public InterstitialAd(Activity activity, String str) {
        super(str);
        this.d = activity;
        this.j = initialize();
    }

    public InterstitialAd(Activity activity, long j) {
        super(j);
        this.d = activity;
        this.j = initialize();
    }

    protected boolean initialize() {
        if (this.d == null) {
            Log.debug(Constants.LOG_TAG, "Activity cannot be null");
            return false;
        }
        this.d = b.a(this.d);
        f();
        if (d()) {
            this.h = e();
        }
        return super.initialize();
    }

    protected Map<String, String> getAdFormatParams() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("format", AD_FORMAT.IMAI.toString().toLowerCase(Locale.getDefault()));
        hashMap.put("mk-ads", "1");
        hashMap.put("mk-ad-slot", String.valueOf(a()));
        hashMap.put(AdDatabaseHelper.COLUMN_ADTYPE, IXMLRPCSerializer.TYPE_INT);
        if (d()) {
            hashMap.put("playable", String.valueOf(1));
        }
        return hashMap;
    }

    public void handleResponse(c cVar, Response response) {
        try {
            this.i = response;
            this.d.runOnUiThread(new Runnable(this) {
                final /* synthetic */ InterstitialAd a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.c();
                }
            });
        } catch (Throwable e) {
            Log.debug(Constants.LOG_TAG, "Error retrieving ad ", e);
            if (this.mAdListener != null) {
                this.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
            }
        }
    }

    public void loadAd() {
        if (this.j) {
            this.k = false;
            f();
            super.loadAd();
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    private static int a() {
        if (DeviceInfo.isTablet(InternalSDKUtil.getContext())) {
            return 17;
        }
        return 14;
    }

    public void show(long j) {
        if (this.j) {
            this.g = j;
            show();
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    public void show() {
        try {
            if (this.j) {
                Log.debug(Constants.LOG_TAG, "Showing the Interstitial Ad. ");
                if (d() && this.h != null && a(this.h)) {
                    b(this.h);
                    return;
                } else {
                    b();
                    return;
                }
            }
            Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
        } catch (Throwable e) {
            Log.debug(Constants.LOG_TAG, "Error showing ad ", e);
        }
    }

    private void b() {
        try {
            if (this.mAdListener != null) {
                this.mAdListener.onShowAdScreen();
            }
            Intent intent = new Intent(this.d, IMBrowserActivity.class);
            intent.putExtra(IMBrowserActivity.EXTRA_BROWSER_ACTIVITY_TYPE, 101);
            intent.putExtra(IMBrowserActivity.ANIMATED, this.g);
            IMBrowserActivity.setWebview(this.f);
            IMBrowserActivity.setOriginalActivity(this.d);
            this.d.startActivity(intent);
        } catch (Throwable e) {
            Log.debug(Constants.LOG_TAG, "Error showing ad ", e);
        }
    }

    public void stopLoading() {
        if (this.j) {
            super.stopLoading();
            if (this.l != null && this.l.hasMessages(301)) {
                this.l.removeMessages(301);
            }
            this.k = true;
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    public void disableHardwareAcceleration() {
        if (this.j) {
            this.c = false;
            if (this.f != null) {
                this.f.disableHardwareAcceleration();
                return;
            }
            return;
        }
        Log.debug(Constants.LOG_TAG, "Please check for initialization error on the ad. The activity or appId cannot be null or blank");
    }

    private void c() {
        if (!this.k) {
            String responseBody;
            long currentTimeMillis = System.currentTimeMillis() - this.mFetchStartTime;
            if (this.i != null) {
                responseBody = this.i.getResponseBody();
            } else {
                responseBody = null;
            }
            if (responseBody != null) {
                String replace = responseBody.replace("@__imm_aft@", "" + currentTimeMillis);
                if (VERSION.SDK_INT <= 8) {
                    replace.replaceAll("%", "%25");
                }
                this.e = System.currentTimeMillis();
                this.l.sendEmptyMessageDelayed(301, (long) Initializer.getConfigParams().getRenderTimeOut());
                this.f.loadDataWithBaseURL("", replace, "text/html", null, null);
                return;
            }
            Log.debug(Constants.LOG_TAG, "Cannot load Ad. Invalid Ad Response");
            if (this.mAdListener != null) {
                this.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
            }
        }
    }

    public void onAdReady() {
        collectMetrics(this.i, System.currentTimeMillis() - this.e, NetworkEventType.RENDER_COMPLETE);
        if (!this.k) {
            this.l.removeMessages(301);
            if (this.mAdListener != null) {
                this.mAdListener.onAdRequestSucceeded();
            }
        }
    }

    public void onAdFailed() {
        this.l.removeMessages(301);
        if (!this.k && this.mAdListener != null) {
            this.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
        }
    }

    private boolean d() {
        try {
            Class.forName("com.inmobi.monetization.internal.thirdparty.PlayableAdsManager");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Object e() {
        try {
            return Class.forName("com.inmobi.monetization.internal.thirdparty.PlayableAdsManager").getConstructor(new Class[]{Activity.class, IMWebView.class}).newInstance(new Object[]{this.d, this.f});
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Exception creating playable ads", e);
            return null;
        }
    }

    private boolean a(Object obj) {
        try {
            Field declaredField = obj.getClass().getDeclaredField("mIsPlayableReady");
            declaredField.setAccessible(true);
            return ((Boolean) declaredField.get(obj)).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    private void b(Object obj) {
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod("show", new Class[]{IMAdListener.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, new Object[]{this.mAdListener});
        } catch (Exception e) {
            Log.internal(Constants.LOG_TAG, "Failed to display playable ad");
            b();
        }
    }

    private void f() {
        if (this.f == null) {
            this.f = new IMWebView(this.d, this.m, true, false);
            if (!this.c) {
                this.f.disableHardwareAcceleration();
            }
            IMAIController iMAIController = new IMAIController(this.f);
            iMAIController.setInterstitialAdStateListener(this);
            this.f.addJavascriptInterface(iMAIController, IMAIController.IMAI_BRIDGE);
        }
    }
}
