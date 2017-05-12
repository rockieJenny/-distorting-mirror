package com.millennialmedia.android;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import com.squareup.otto.Bus;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MMWebView extends WebView {
    static final String JS_INTERFACE_NAME = "interface";
    static final String PROPERTY_BANNER_TYPE = "PROPERTY_BANNER_TYPE";
    static final String PROPERTY_EXPANDING = "PROPERTY_EXPANDING";
    static final String PROPERTY_STATE = "PROPERTY_STATE";
    private static final String TAG = "MMWebView";
    private HttpMMHeaders _lastHeaders;
    long creatorAdImplId;
    int currentColor;
    String currentUrl;
    boolean hadFirstRecordingCreation = false;
    boolean hadFirstSpeechKitCreation = false;
    volatile boolean isExpanding;
    boolean isSendingSize = true;
    volatile boolean isUserClosedResize;
    volatile boolean isVisible = false;
    volatile String mraidState;
    int oldHeight = -50;
    int oldWidth = -50;
    volatile boolean requiresPreAdSizeFix;
    final GestureDetector tapDetector;
    final String userAgent;

    private static class BannerGestureListener extends SimpleOnGestureListener {
        WeakReference<MMWebView> webRef;

        public boolean onSingleTapConfirmed(MotionEvent e) {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView != null) {
                MMAdView adView = webView.getMMAdView();
                if (adView != null) {
                    Event.adSingleTap(adView.adImpl);
                }
            }
            return false;
        }

        public BannerGestureListener(MMWebView webView) {
            this.webRef = new WeakReference(webView);
        }
    }

    private static class MyWebChromeClient extends WebChromeClient {
        private static final String KEY_USE_GEO = "mm_use_geo_location";
        WeakReference<MMWebView> webRef;

        MyWebChromeClient(MMWebView webView) {
            this.webRef = new WeakReference(webView);
        }

        public void onGeolocationPermissionsShowPrompt(final String origin, final Callback callback) {
            if (!isFirstGeoRequest()) {
                callback.invoke(origin, false, false);
            } else if (retrieveUseGeo()) {
                callback.invoke(origin, true, true);
            } else {
                MMWebView webView = (MMWebView) this.webRef.get();
                if (webView != null) {
                    Activity activity = webView.getActivity();
                    if (activity != null) {
                        Builder builder = new Builder(activity);
                        builder.setTitle(getApplicationName(activity));
                        builder.setMessage("Would like to use your Current Location.").setPositiveButton("Allow", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MyWebChromeClient.this.saveUseGeo(true);
                                callback.invoke(origin, true, true);
                            }
                        }).setNegativeButton("Don't Allow", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MyWebChromeClient.this.saveUseGeo(false);
                                callback.invoke(origin, false, false);
                            }
                        });
                        builder.create().show();
                    }
                }
            }
        }

        private boolean isFirstGeoRequest() {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView == null || webView.getContext().getSharedPreferences("MillennialMediaSettings", 0).contains(KEY_USE_GEO)) {
                return false;
            }
            return true;
        }

        private boolean retrieveUseGeo() {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView != null) {
                return webView.getContext().getSharedPreferences("MillennialMediaSettings", 0).getBoolean(KEY_USE_GEO, false);
            }
            return false;
        }

        private void saveUseGeo(boolean allow) {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView != null) {
                Editor editor = webView.getContext().getSharedPreferences("MillennialMediaSettings", 0).edit();
                editor.putBoolean(KEY_USE_GEO, allow);
                editor.commit();
            }
        }

        private String getApplicationName(Context context) {
            ApplicationInfo ai;
            PackageManager pm = context.getApplicationContext().getPackageManager();
            try {
                ai = pm.getApplicationInfo(context.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                ai = null;
            }
            return (String) (ai != null ? pm.getApplicationLabel(ai) : "This app");
        }

        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            super.onConsoleMessage(message, lineNumber, sourceID);
        }

        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView != null) {
                if (webView.getContext() != webView.getContext().getApplicationContext()) {
                    return super.onJsAlert(view, url, message, result);
                }
                Toast.makeText(webView.getContext(), message, 0).show();
            }
            return true;
        }

        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView != null) {
                if (webView.getContext() != webView.getContext().getApplicationContext()) {
                    return super.onJsBeforeUnload(view, url, message, result);
                }
                Toast.makeText(webView.getContext(), message, 0).show();
            }
            return true;
        }

        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView != null) {
                if (webView.getContext() != webView.getContext().getApplicationContext()) {
                    return super.onJsConfirm(view, url, message, result);
                }
                Toast.makeText(webView.getContext(), message, 0).show();
            }
            return true;
        }

        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            MMWebView webView = (MMWebView) this.webRef.get();
            if (webView != null) {
                if (webView.getContext() != webView.getContext().getApplicationContext()) {
                    return super.onJsPrompt(view, url, message, defaultValue, result);
                }
                Toast.makeText(webView.getContext(), message, 0).show();
            }
            return true;
        }
    }

    private static class WebTouchListener implements OnTouchListener {
        WeakReference<MMWebView> webRef;

        WebTouchListener(MMWebView webView) {
            this.webRef = new WeakReference(webView);
        }

        public boolean onTouch(View v, MotionEvent event) {
            boolean consume;
            MMWebView webView = (MMWebView) this.webRef.get();
            if (event.getAction() == 2) {
                consume = true;
            } else {
                consume = false;
            }
            if (webView == null) {
                return consume;
            }
            if (consume && webView.canScroll()) {
                return true;
            }
            return false;
        }
    }

    public MMWebView(Context context, long internalId) {
        super(context);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setOnTouchListener(new WebTouchListener(this));
        this.mraidState = "loading";
        this.creatorAdImplId = internalId;
        MMLog.v(TAG, String.format("Assigning WebView internal id: %d", new Object[]{Long.valueOf(this.creatorAdImplId)}));
        setId((int) (15063 + this.creatorAdImplId));
        if (HandShake.sharedHandShake(context).hardwareAccelerationEnabled) {
            enableHardwareAcceleration();
        } else {
            disableAllAcceleration();
        }
        setWebChromeClient(new MyWebChromeClient(this));
        WebSettings webSettings = getSettings();
        this.userAgent = webSettings.getUserAgentString();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(-1);
        webSettings.setDefaultTextEncodingName(HttpRequest.CHARSET_UTF8);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        if (VERSION.SDK_INT >= 17) {
            MMLog.i(TAG, "Disabling user gesture requirement for media playback");
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.tapDetector = new GestureDetector(context.getApplicationContext(), new BannerGestureListener(this));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        if (measuredHeight == 0) {
            measuredHeight = heightSize;
        }
        int measuredWidth = widthSize;
        if (this.requiresPreAdSizeFix) {
            setMeasuredDimension(1, 1);
        } else {
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    public void setBackgroundColor(int color) {
        this.currentColor = color;
        if (color == 0) {
            enableSoftwareAcceleration();
        }
        super.setBackgroundColor(color);
    }

    void disableAllAcceleration() {
        if (VERSION.SDK_INT >= 11) {
            MMLog.i(TAG, "Disabling acceleration");
            setLayerType(0, null);
        }
    }

    private boolean needsSamsungJBOpenGlFixNoAcceleration() {
        int version = Integer.parseInt(VERSION.SDK);
        if ("Nexus S".equals(Build.MODEL) && "samsung".equals(Build.MANUFACTURER) && (version == 16 || version == 17)) {
            return true;
        }
        return false;
    }

    void enableSoftwareAcceleration() {
        if (!needsSamsungJBOpenGlFixNoAcceleration()) {
            try {
                WebView.class.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this, new Object[]{Integer.valueOf(1), null});
                MMLog.d(TAG, "Enable softwareAcceleration");
            } catch (Exception e) {
            }
        }
    }

    void enableHardwareAcceleration() {
        if (!needsSamsungJBOpenGlFixNoAcceleration()) {
            try {
                WebView.class.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this, new Object[]{Integer.valueOf(2), null});
                MMLog.d(TAG, "Enabled hardwareAcceleration");
            } catch (Exception e) {
            }
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == 0) {
            requestFocus();
        }
        if (this.tapDetector != null) {
            this.tapDetector.onTouchEvent(e);
        }
        if (e.getAction() == 1) {
            MMLog.v(TAG, String.format("Ad clicked: action=%d x=%f y=%f", new Object[]{Integer.valueOf(e.getAction()), Float.valueOf(e.getX()), Float.valueOf(e.getY())}));
        }
        return super.onTouchEvent(e);
    }

    void setWebViewContent(String webContent, String adUrl, final MMAdImpl adImpl) {
        if (webContent != null && adUrl != null && adImpl != null) {
            String content;
            unresizeToDefault(adImpl);
            resetSpeechKit();
            final String baseUrl = adUrl.substring(0, adUrl.lastIndexOf("/") + 1);
            if (MMSDK.logLevel >= 5) {
                MMLog.v(TAG, String.format("Received ad with base url %s.", new Object[]{baseUrl}));
                MMLog.v(TAG, webContent);
            }
            if (adImpl.isTransitionAnimated()) {
                animateTransition(adImpl);
            }
            if (adImpl.ignoreDensityScaling) {
                content = "<head><meta name=\"viewport\" content=\"target-densitydpi=device-dpi\" /></head>" + webContent;
            } else {
                content = webContent;
            }
            if (MRaid.hasMraidLocally(adImpl.getContext())) {
                content = MRaid.injectMraidJs(adImpl.getContext(), content);
            } else {
                MMLog.e(TAG, "MMJS is not downloaded");
            }
            final String finalContent = content;
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    if (HandShake.sharedHandShake(MMWebView.this.getContext()).hardwareAccelerationEnabled) {
                        MMWebView.this.enableHardwareAcceleration();
                    } else if (MMWebView.this.currentColor == 0) {
                        MMWebView.this.enableSoftwareAcceleration();
                    } else {
                        MMWebView.this.disableAllAcceleration();
                    }
                    MMAd ad = adImpl.getCallingAd();
                    if (ad != null && (ad instanceof MMLayout)) {
                        ((MMLayout) ad).removeVideo();
                    }
                    MMWebView.this.isSendingSize = true;
                    MMWebView.this.loadDataWithBaseURL(baseUrl, finalContent, "text/html", HttpRequest.CHARSET_UTF8, null);
                }
            });
        }
    }

    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        this.currentUrl = baseUrl;
        try {
            super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
        } catch (Exception e) {
            MMLog.e(TAG, "Error hit when calling through to loadDataWithBaseUrl", e);
        }
    }

    void setWebViewContent(String webContent, String baseUrl, Context context) {
        if (webContent != null && baseUrl != null) {
            final String finalBaseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
            resetSpeechKit();
            String content = webContent;
            if (MRaid.hasMraidLocally(context)) {
                content = MRaid.injectMraidJs(context, content);
            } else {
                MMLog.e(TAG, "MMJS is not downloaded");
            }
            final String finalContent = content;
            if (MMSDK.logLevel >= 5) {
                MMLog.v(TAG, String.format("Received ad with base url %s.", new Object[]{baseUrl}));
                MMLog.v(TAG, webContent);
            }
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    if (HandShake.sharedHandShake(MMWebView.this.getContext()).hardwareAccelerationEnabled) {
                        MMWebView.this.enableHardwareAcceleration();
                    } else if (MMWebView.this.currentColor == 0) {
                        MMWebView.this.enableSoftwareAcceleration();
                    } else {
                        MMWebView.this.disableAllAcceleration();
                    }
                    MMWebView.this.isSendingSize = true;
                    MMWebView.this.loadDataWithBaseURL(finalBaseUrl, finalContent, "text/html", HttpRequest.CHARSET_UTF8, null);
                }
            });
        }
    }

    void resetSpeechKit() {
        BridgeMMSpeechkit.releaseSpeechKit();
        this.hadFirstSpeechKitCreation = false;
        this.hadFirstRecordingCreation = false;
    }

    void animateTransition(final MMAdImpl adImpl) {
        FutureTask<Void> future = new FutureTask(new Callable<Void>() {
            public Void call() {
                try {
                    MMWebView.this.buildDrawingCache();
                    Bitmap cache = MMWebView.this.getDrawingCache();
                    if (cache != null) {
                        adImpl.prepareTransition(Bitmap.createBitmap(cache));
                    }
                    MMWebView.this.destroyDrawingCache();
                } catch (Exception e) {
                    MMLog.e(MMWebView.TAG, "Animation exception: ", e);
                }
                return null;
            }
        });
        MMSDK.runOnUiThread(future);
        try {
            future.get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e2) {
        }
    }

    String getUserAgent() {
        return this.userAgent;
    }

    void updateArgumentsWithSettings(Map<String, String> arguments) {
        arguments.put(PROPERTY_BANNER_TYPE, isParentBannerAd() ? "true" : "false");
        arguments.put(PROPERTY_STATE, this.mraidState);
        arguments.put(PROPERTY_EXPANDING, String.valueOf(this.creatorAdImplId));
    }

    boolean isParentBannerAd() {
        if (getParent() != null) {
            return ((ViewGroup) getParent()) instanceof MMAdView;
        }
        return false;
    }

    synchronized void setMraidResize(final DTOResizeParameters resizeParams) {
        if (MMSDK.hasSetTranslationMethod()) {
            final MMAdView adView = getMMAdView();
            this.isUserClosedResize = false;
            MMLog.d(TAG, "New DTOResizeParameters = " + resizeParams);
            if (adView != null) {
                MMSDK.runOnUiThread(new Runnable() {
                    public void run() {
                        synchronized (MMWebView.this) {
                            MMWebView.this.isSendingSize = true;
                            adView.handleMraidResize(resizeParams);
                            handleMraidResize(resizeParams);
                            MMWebView.this.loadUrl("javascript:MMJS.sdk.setState('resized');");
                            MMWebView.this.mraidState = "resized";
                        }
                    }

                    private void handleMraidResize(DTOResizeParameters resizeParams) {
                        MMAdView mMAdView = adView;
                        mMAdView.getClass();
                        BannerBounds bounds = new BannerBounds(resizeParams);
                        setUnresizeParameters();
                        bounds.modifyLayoutParams(MMWebView.this.getLayoutParams());
                    }

                    private void setUnresizeParameters() {
                        if (MMWebView.this.hasDefaultResizeParams()) {
                            LayoutParams oldParams = MMWebView.this.getLayoutParams();
                            MMWebView.this.oldWidth = oldParams.width;
                            MMWebView.this.oldHeight = oldParams.height;
                            if (MMWebView.this.oldWidth <= 0) {
                                MMWebView.this.oldWidth = MMWebView.this.getWidth();
                            }
                            if (MMWebView.this.oldHeight <= 0) {
                                MMWebView.this.oldHeight = MMWebView.this.getHeight();
                            }
                        }
                    }
                });
            }
        }
    }

    private boolean hasDefaultResizeParams() {
        return this.oldWidth == -50 && this.oldHeight == -50;
    }

    synchronized void unresizeToDefault(MMAdImpl adImpl) {
        if (MMSDK.hasSetTranslationMethod() && isMraidResized() && adImpl != null) {
            MMAd ad = adImpl.getCallingAd();
            if (ad instanceof MMAdView) {
                final MMAdView adView = (MMAdView) ad;
                this.isUserClosedResize = true;
                MMSDK.runOnUiThread(new Runnable() {
                    public void run() {
                        synchronized (MMWebView.this) {
                            adView.handleUnresize();
                            handleUnresize();
                            MMWebView.this.setMraidDefault();
                            MMWebView.this.isSendingSize = true;
                            MMWebView.this.invalidate();
                        }
                    }

                    void handleUnresize() {
                        if (MMSDK.hasSetTranslationMethod() && !MMWebView.this.hasDefaultResizeParams()) {
                            LayoutParams params = MMWebView.this.getLayoutParams();
                            params.width = MMWebView.this.oldWidth;
                            params.height = MMWebView.this.oldHeight;
                            MMWebView.this.oldWidth = -50;
                            MMWebView.this.oldHeight = -50;
                            MMWebView.this.requestLayout();
                        }
                    }
                });
            }
        }
    }

    boolean canScroll() {
        return getParent() instanceof MMAdView;
    }

    MMAdView getMMAdView() {
        if (getParent() instanceof MMAdView) {
            return (MMAdView) getParent();
        }
        return null;
    }

    MMLayout getMMLayout() {
        if (getParent() instanceof MMLayout) {
            return (MMLayout) getParent();
        }
        return null;
    }

    synchronized Activity getActivity() {
        Activity context;
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            Context context2 = ((ViewGroup) parent).getContext();
            if (context2 != null && (context2 instanceof MMActivity)) {
                context = (MMActivity) context2;
            }
        }
        context = null;
        return context;
    }

    public void loadUrl(final String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http")) {
                this.currentUrl = url;
            }
            MMLog.v(TAG, "loadUrl @@" + url);
            if (MMSDK.isUiThread()) {
                try {
                    super.loadUrl(url);
                    return;
                } catch (Exception e) {
                    return;
                }
            }
            MMSDK.runOnUiThread(new Runnable() {
                public void run() {
                    MMWebView.this.loadUrl(url);
                }
            });
        }
    }

    synchronized AdViewOverlayView getAdViewOverlayView() {
        AdViewOverlayView parent;
        ViewParent parent2 = getParent();
        if (parent2 == null || !(parent2 instanceof AdViewOverlayView)) {
            parent = null;
        } else {
            parent = (AdViewOverlayView) parent2;
        }
        return parent;
    }

    synchronized MMAdView getBanner() {
        MMAdView parent;
        ViewParent parent2 = getParent();
        if (parent2 == null || !(parent2 instanceof MMAdView)) {
            parent = null;
        } else {
            parent = (MMAdView) parent2;
        }
        return parent;
    }

    public void onPauseWebView() {
        if (VERSION.SDK_INT >= 11) {
            try {
                WebView.class.getMethod("onPause", new Class[0]).invoke(this, new Object[0]);
            } catch (Exception e) {
                MMLog.w(TAG, "No onPause()");
            }
        }
    }

    public void onResumeWebView() {
        if (!isParentBannerAd() && VERSION.SDK_INT >= 19) {
            Activity adActivity = getActivity();
            if (adActivity != null) {
                adActivity.setRequestedOrientation(14);
            }
        }
        if (VERSION.SDK_INT >= 11) {
            try {
                WebView.class.getMethod("onResume", new Class[0]).invoke(this, new Object[0]);
            } catch (Exception e) {
                MMLog.w(TAG, "No onResume()");
            }
        }
    }

    void setMraidPlacementTypeInterstitial() {
        loadUrl("javascript:MMJS.sdk.setPlacementType('interstitial');");
    }

    void setMraidPlacementTypeInline() {
        loadUrl("javascript:MMJS.sdk.setPlacementType('inline');");
    }

    void setMraidDefault() {
        loadUrl("javascript:MMJS.sdk.setState('default')");
        this.mraidState = Bus.DEFAULT_IDENTIFIER;
        this.isSendingSize = true;
    }

    void setMraidHidden() {
        loadUrl("javascript:MMJS.sdk.setState('hidden')");
        this.mraidState = "hidden";
    }

    void setMraidViewableHidden() {
        loadUrl("javascript:MMJS.sdk.setViewable(false)");
        this.isVisible = false;
    }

    void setMraidViewableVisible() {
        loadUrl("javascript:MMJS.sdk.setViewable(true)");
        this.isVisible = true;
    }

    void setmicrophoneAudioLevelChange(double level) {
        loadUrl("javascript:MMJS.sdk.microphoneAudioLevelChange(" + level + ")");
    }

    void setmicrophoneStateChange(String state) {
        loadUrl("javascript:MMJS.sdk.microphoneStateChange('" + state + "')");
    }

    void setMraidExpanded() {
        loadUrl("javascript:MMJS.sdk.setState('expanded');");
        this.mraidState = "expanded";
        this.hadFirstSpeechKitCreation = false;
        this.hadFirstRecordingCreation = false;
        this.isSendingSize = true;
    }

    void setMraidReady() {
        loadUrl("javascript:MMJS.sdk.ready();");
    }

    void setAdProperties() {
        loadUrl("javascript:MMJS.sdk.setAdProperties(" + new AdProperties(getContext()).getAdProperties(this) + ");");
    }

    void setAdSize() {
        loadUrl("javascript:MMJS.sdk.setAdSize(" + Utils.getViewDimensions(this) + ");");
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (getContext().getResources().getDisplayMetrics() != null && this.isSendingSize) {
            setAdSize();
            if (!(getHeight() == 1 && getWidth() == 1)) {
                MMSDK.runOnUiThreadDelayed(new Runnable() {
                    public void run() {
                        MMWebView.this.isSendingSize = false;
                    }
                }, 800);
            }
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public String toString() {
        return "MMWebView originally from(" + this.creatorAdImplId + ") MRaidState(" + this.mraidState + ")." + super.toString();
    }

    boolean isCurrentParent(long internalId) {
        ViewParent parent = getParent();
        if (parent == null) {
            return false;
        }
        MMLog.w(TAG, "Id check for parent: " + internalId + " versus " + ((MMLayout) parent).adImpl.internalId);
        return internalId == ((MMLayout) parent).adImpl.internalId;
    }

    void removeFromParent() {
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this);
        }
    }

    boolean isMraidResized() {
        return "resized".equals(this.mraidState);
    }

    boolean isOriginalUrl(String url) {
        return (!TextUtils.isEmpty(this.currentUrl) && url.equals(this.currentUrl + "?")) || url.equals(this.currentUrl + "#");
    }

    void setLastHeaders(HttpMMHeaders lastHeaders) {
        this._lastHeaders = lastHeaders;
    }

    HttpMMHeaders getLastHeaders() {
        return this._lastHeaders;
    }

    String getAdId() {
        if (this._lastHeaders == null || TextUtils.isEmpty(this._lastHeaders.acid)) {
            return "DEFAULT_AD_ID";
        }
        return this._lastHeaders.acid;
    }

    boolean allowSpeechCreationCommands() {
        if (this.hadFirstSpeechKitCreation) {
            return allowRecordingCommands();
        }
        this.hadFirstSpeechKitCreation = true;
        if (isInterstitial() && this.isVisible) {
            return true;
        }
        return false;
    }

    boolean allowMicrophoneCreationCommands() {
        if (this.hadFirstRecordingCreation) {
            return allowRecordingCommands();
        }
        this.hadFirstRecordingCreation = true;
        if (isInterstitial() && this.isVisible) {
            return true;
        }
        return false;
    }

    boolean allowRecordingCommands() {
        return hasWindowFocus() && isInterstitial();
    }

    private boolean isInterstitial() {
        return getBanner() == null;
    }

    void enableSendingSize() {
        this.isSendingSize = true;
    }
}
