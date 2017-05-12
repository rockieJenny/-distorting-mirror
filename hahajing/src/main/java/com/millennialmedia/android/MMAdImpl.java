package com.millennialmedia.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RelativeLayout.LayoutParams;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONObject;

abstract class MMAdImpl implements MMAd {
    static final String BANNER = "b";
    static final String INTERSTITIAL = "i";
    private static final String TAG = "MMAdImpl";
    private static long nextAdViewId = 1;
    String adType = "i";
    String apid = MMSDK.DEFAULT_APID;
    WeakReference<Context> contextRef;
    MMAdImplController controller;
    boolean ignoreDensityScaling = false;
    long internalId;
    boolean isFinishing;
    long lastAdRequest;
    long linkForExpansionId;
    protected MMRequest mmRequest;
    MMWebViewClient mmWebViewClient;
    MMWebViewClientListener mmWebViewClientListener;
    JSONObject obj;
    RequestListener requestListener;
    String userData;

    static class BasicWebViewClientListener extends MMWebViewClientListener {
        WeakReference<MMAdImpl> adImplRef;

        BasicWebViewClientListener(MMAdImpl adImpl) {
            this.adImplRef = new WeakReference(adImpl);
        }

        void onPageStarted(String url) {
            MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
            if (adImpl != null) {
                adImpl.setClickable(false);
            }
        }

        public void onPageFinished(String url) {
            MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
            if (adImpl != null) {
                adImpl.setClickable(true);
                if (adImpl.controller != null && adImpl.controller.webView != null) {
                    synchronized (adImpl.controller.webView) {
                        if (adImpl.controller.webView.hasWindowFocus()) {
                            adImpl.controller.webView.setMraidViewableVisible();
                        } else {
                            adImpl.controller.webView.setMraidViewableHidden();
                        }
                    }
                }
            }
        }
    }

    static class MMAdImplRedirectionListenerImpl extends RedirectionListenerImpl {
        WeakReference<MMAdImpl> adImplRef;

        public MMAdImplRedirectionListenerImpl(MMAdImpl adImpl) {
            if (adImpl != null) {
                this.adImplRef = new WeakReference(adImpl);
                this.creatorAdImplInternalId = adImpl.internalId;
            }
        }

        public void updateLastVideoViewedTime() {
            MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
            if (adImpl != null && adImpl.adType != null) {
                HandShake.sharedHandShake(adImpl.getContext()).updateLastVideoViewedTime(adImpl.getContext(), adImpl.adType);
            }
        }

        public boolean isActivityStartable(Uri uri) {
            MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
            if (adImpl != null) {
                Context c = adImpl.getContext();
                if (c != null && (c instanceof Activity) && ((Activity) c).isFinishing()) {
                    return false;
                }
            }
            return true;
        }

        public void startingActivity(Uri destinationUri) {
            super.startingActivity(destinationUri);
            if (destinationUri.getScheme().equalsIgnoreCase("http") || destinationUri.getScheme().equalsIgnoreCase("https")) {
                MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
                if (adImpl != null) {
                    Event.overlayOpened(adImpl);
                }
            }
        }
    }

    abstract MMAd getCallingAd();

    public MMAdImpl(Context context) {
        this.contextRef = new WeakReference(context);
        this.mmWebViewClientListener = new BasicWebViewClientListener(this);
        synchronized (MMAdImpl.class) {
            this.internalId = nextAdViewId;
            nextAdViewId++;
            MMLog.v(TAG, String.format("Assigning MMAdImpl internal id: %d", new Object[]{Long.valueOf(this.internalId)}));
        }
    }

    public void setApid(String apid) {
        if (!(apid == null || apid.isEmpty())) {
            HandShake.apid = apid;
        }
        this.apid = apid;
    }

    public String getApid() {
        return this.apid;
    }

    public void setListener(RequestListener listener) {
        this.requestListener = listener;
    }

    public RequestListener getListener() {
        return this.requestListener;
    }

    public void setIgnoresDensityScaling(boolean ignoresDensityScaling) {
        this.ignoreDensityScaling = ignoresDensityScaling;
    }

    public boolean getIgnoresDensityScaling() {
        return this.ignoreDensityScaling;
    }

    public void setMMRequest(MMRequest request) {
        this.mmRequest = request;
    }

    public MMRequest getMMRequest() {
        return this.mmRequest;
    }

    int getId() {
        return -1;
    }

    void requestAd() {
        MMAdImplController.assignAdViewController(this);
        if (this.controller != null) {
            this.controller.requestAd();
        }
    }

    boolean isRefreshable() {
        if (MMSDK.disableAdMinRefresh) {
            MMLog.d(TAG, "Minimum adrefresh time ignored");
            return true;
        }
        long currentTime = System.currentTimeMillis();
        int lastRequestSecs = (int) ((currentTime - this.lastAdRequest) / 1000);
        if (((long) lastRequestSecs) >= HandShake.sharedHandShake(getContext()).adRefreshSecs) {
            this.lastAdRequest = currentTime;
            return true;
        }
        MMLog.d(TAG, String.format("Cannot request ad. Last ad request was %d seconds ago. Next ad can be requested in %d seconds.", new Object[]{Integer.valueOf(lastRequestSecs), Long.valueOf(adRefreshSecs - ((long) lastRequestSecs))}));
        return false;
    }

    boolean isTransitionAnimated() {
        return false;
    }

    void prepareTransition(Bitmap bitmap) {
    }

    void animateTransition() {
    }

    Context getContext() {
        if (this.contextRef != null) {
            return (Context) this.contextRef.get();
        }
        return null;
    }

    String getCachedName() {
        return (this.adType == null || this.apid == null) ? null : this.adType + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.apid;
    }

    static String[] getAdTypes() {
        return new String[]{BANNER, INTERSTITIAL};
    }

    String getRequestFailedAction() {
        return MMBroadcastReceiver.ACTION_FETCH_FAILED;
    }

    String getRequestCompletedAction() {
        return MMBroadcastReceiver.ACTION_FETCH_SUCCEEDED;
    }

    MMWebViewClient getMMWebViewClient() {
        MMLog.d(TAG, "Returning a client for user: DefaultWebViewClient, adimpl=" + this);
        return new BannerWebViewClient(this.mmWebViewClientListener, new MMAdImplRedirectionListenerImpl(this));
    }

    String getReqType() {
        return "fetch";
    }

    public boolean hasCachedVideoSupport() {
        return true;
    }

    public boolean isBanner() {
        return false;
    }

    void insertUrlAdMetaValues(Map<String, String> paramsMap) {
        Context context = getContext();
        paramsMap.put("apid", this.apid);
        paramsMap.put("do", MMSDK.getOrientation(context));
        paramsMap.put("olock", MMSDK.getOrientationLocked(context));
        if (!hasCachedVideoSupport()) {
            paramsMap.put("cachedvideo", "false");
        }
        paramsMap.put("reqtype", getReqType());
        if (this.mmRequest != null) {
            this.mmRequest.getUrlParams(paramsMap);
        }
        if (HandShake.sharedHandShake(context).canRequestVideo(context, this.adType)) {
            paramsMap.put("video", "true");
        } else {
            paramsMap.put("video", "false");
        }
        if (this.adType == null) {
            MMLog.e(TAG, "******* SDK DEFAULTED TO MMBannerAdBottom. THIS MAY AFFECT THE ADS YOU RECIEVE!!! **********");
            paramsMap.put("at", BANNER);
        } else if (this.adType.equals(BANNER) || this.adType.equals(INTERSTITIAL)) {
            paramsMap.put("at", this.adType);
        } else {
            MMLog.e(TAG, "******* ERROR: INCORRECT AD TYPE IN MMADVIEW OBJECT PARAMETERS (" + this.adType + ") **********");
        }
    }

    void removeView(MMWebView mmWebView) {
    }

    void addView(MMWebView webView, LayoutParams webLayoutParams) {
    }

    void setClickable(boolean clickable) {
    }

    public String toString() {
        return "AdType[(" + this.adType + ") InternalId(" + this.internalId + ") LinkedId(" + this.linkForExpansionId + ") isFinishing(" + this.isFinishing + ")]";
    }

    boolean isUpdatingMraid() {
        return (this.controller == null || this.controller.webView == null || this.controller.webView.isExpanding) ? false : true;
    }

    void unresizeToDefault() {
        if (this.controller != null) {
            this.controller.unresizeToDefault();
        }
    }

    boolean isLifecycleObservable() {
        return false;
    }

    void removeProgressBar() {
    }

    boolean isExpandingToUrl() {
        return false;
    }
}
