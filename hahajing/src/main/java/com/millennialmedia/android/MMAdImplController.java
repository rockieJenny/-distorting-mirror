package com.millennialmedia.android;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RelativeLayout.LayoutParams;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

class MMAdImplController implements AdCacheTaskListener {
    static final long NO_ID_RETURNED = -4;
    private static final String TAG = "MMAdImplController";
    private static final Map<Long, MMAdImplController> saveableControllers = new ConcurrentHashMap();
    private static final Map<Long, WeakReference<MMAdImplController>> weakUnsaveableAdRef = new ConcurrentHashMap();
    volatile WeakReference<MMAdImpl> adImplRef;
    volatile long linkedAdImplId;
    RequestAdRunnable requestAdRunnable;
    volatile MMWebView webView;

    private class RequestAdRunnable implements Runnable {
        String adUrl;
        HttpMMHeaders mmHeaders;

        private RequestAdRunnable() {
        }

        boolean failWithErrorMessage(MMException mmError) {
            MMLog.e(MMAdImplController.TAG, mmError.getMessage());
            return fail(mmError);
        }

        boolean failWithInfoMessage(MMException mmError) {
            MMLog.i(MMAdImplController.TAG, mmError.getMessage());
            return fail(mmError);
        }

        boolean fail(MMException mmError) {
            MMAdImpl adImpl = null;
            if (MMAdImplController.this.adImplRef != null) {
                adImpl = (MMAdImpl) MMAdImplController.this.adImplRef.get();
            }
            Event.requestFailed(adImpl, mmError);
            return false;
        }

        public void run() {
            try {
                if (MMAdImplController.this.adImplRef != null) {
                    MMAdImpl adImpl = (MMAdImpl) MMAdImplController.this.adImplRef.get();
                    if (adImpl == null || !MMSDK.isConnected(adImpl.getContext())) {
                        failWithInfoMessage(new MMException("No network available, can't call for ads.", 11));
                        MMAdImplController.this.requestAdRunnable = null;
                        return;
                    }
                    if (isAdUrlBuildable()) {
                        try {
                            HttpResponse httpResponse = new HttpGetRequest().get(this.adUrl);
                            if (httpResponse == null) {
                                failWithErrorMessage(new MMException("HTTP response is null.", 14));
                                MMAdImplController.this.requestAdRunnable = null;
                                return;
                            } else if (!isHandledResponse(httpResponse)) {
                                MMAdImplController.this.requestAdRunnable = null;
                                return;
                            }
                        } catch (Exception e) {
                            failWithErrorMessage(new MMException("Ad request HTTP error. " + e.getMessage(), 14));
                            MMAdImplController.this.requestAdRunnable = null;
                            return;
                        }
                    }
                    return;
                }
                MMAdImplController.this.requestAdRunnable = null;
            } catch (Exception e2) {
                failWithInfoMessage(new MMException("Request not filled, can't call for ads.", 14));
            } finally {
                MMAdImplController.this.requestAdRunnable = null;
            }
        }

        private boolean isAdUrlBuildable() {
            this.adUrl = null;
            MMAdImpl adImpl = null;
            if (MMAdImplController.this.adImplRef != null) {
                adImpl = (MMAdImpl) MMAdImplController.this.adImplRef.get();
            }
            if (adImpl != null) {
                try {
                    Map<String, String> paramsMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                    adImpl.insertUrlAdMetaValues(paramsMap);
                    MMSDK.insertUrlCommonValues(adImpl.getContext(), paramsMap);
                    paramsMap.put("ua", adImpl.controller.getUserAgent());
                    StringBuilder adUrlBuilder = new StringBuilder(HandShake.getAdUrl());
                    MMLog.d(MMAdImplController.TAG, paramsMap.entrySet().toString());
                    for (Entry<String, String> entry : paramsMap.entrySet()) {
                        adUrlBuilder.append(String.format("%s=%s&", new Object[]{entry.getKey(), URLEncoder.encode((String) entry.getValue(), HttpRequest.CHARSET_UTF8)}));
                    }
                    adUrlBuilder.delete(adUrlBuilder.length() - 1, adUrlBuilder.length());
                    this.adUrl = adUrlBuilder.toString();
                    MMLog.d(MMAdImplController.TAG, String.format("Calling for an advertisement: %s", new Object[]{this.adUrl}));
                } catch (Exception e) {
                    return failWithErrorMessage(new MMException(e));
                }
            }
            failWithInfoMessage(new MMException(25));
            return true;
        }

        private boolean isHandledResponse(HttpResponse httpResponse) {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity == null) {
                failWithInfoMessage(new MMException("Null HTTP entity", 14));
                return false;
            } else if (httpEntity.getContentLength() == 0) {
                failWithInfoMessage(new MMException("Millennial ad return failed. Zero content length returned.", 14));
                return false;
            } else {
                saveMacId(httpResponse);
                Header httpHeader = httpEntity.getContentType();
                if (httpHeader == null || httpHeader.getValue() == null) {
                    failWithInfoMessage(new MMException("Millennial ad return failed. HTTP Header value null.", 15));
                    return false;
                }
                if (httpHeader.getValue().toLowerCase().startsWith("application/json")) {
                    isHandledJsonResponse(httpEntity);
                } else if (httpHeader.getValue().toLowerCase().startsWith("text/html")) {
                    Header xHeader = httpResponse.getFirstHeader("X-MM-Video");
                    this.mmHeaders = new HttpMMHeaders(httpResponse.getAllHeaders());
                    if (xHeader != null && xHeader.getValue().equalsIgnoreCase("true")) {
                        MMAdImpl adImpl = null;
                        if (MMAdImplController.this.adImplRef != null) {
                            adImpl = (MMAdImpl) MMAdImplController.this.adImplRef.get();
                        }
                        if (adImpl != null) {
                            Context context = adImpl.getContext();
                            HandShake.sharedHandShake(context).updateLastVideoViewedTime(context, adImpl.adType);
                        }
                    }
                    isHandledHtmlResponse(httpEntity);
                } else {
                    failWithInfoMessage(new MMException("Millennial ad return failed. Invalid (JSON/HTML expected) mime type returned.", 15));
                    return false;
                }
                return true;
            }
        }

        private void saveMacId(HttpResponse httpResponse) {
            for (Header cookieHeader : httpResponse.getHeaders("Set-Cookie")) {
                String value = cookieHeader.getValue();
                int index = value.indexOf("MAC-ID=");
                if (index >= 0) {
                    int endIndex = value.indexOf(59, index);
                    if (endIndex > index) {
                        MMSDK.macId = value.substring(index + 7, endIndex);
                    }
                }
            }
        }

        private boolean isHandledJsonResponse(HttpEntity httpEntity) {
            MMAdImpl adImpl = null;
            if (MMAdImplController.this.adImplRef != null) {
                adImpl = (MMAdImpl) MMAdImplController.this.adImplRef.get();
            }
            if (adImpl != null) {
                if (adImpl.isBanner()) {
                    return failWithErrorMessage(new MMException("Millennial ad return unsupported format.", 15));
                }
                try {
                    VideoAd videoAd = (VideoAd) CachedAd.parseJSON(HttpGetRequest.convertStreamToString(httpEntity.getContent()));
                    if (videoAd != null && videoAd.isValid()) {
                        MMLog.i(MMAdImplController.TAG, "Cached video ad JSON received: " + videoAd.getId());
                        if (videoAd.isExpired()) {
                            MMLog.i(MMAdImplController.TAG, "New ad has expiration date in the past. Not downloading ad content.");
                            videoAd.delete(adImpl.getContext());
                            Event.requestFailed(adImpl, new MMException(15));
                        } else if (AdCache.loadNextCachedAd(adImpl.getContext(), adImpl.getCachedName()) != null) {
                            MMLog.i(MMAdImplController.TAG, "Previously fetched ad exists in the playback queue. Not downloading ad content.");
                            videoAd.delete(adImpl.getContext());
                            Event.requestFailed(adImpl, new MMException(17));
                        } else {
                            AdCache.save(adImpl.getContext(), videoAd);
                            if (videoAd.isOnDisk(adImpl.getContext())) {
                                MMLog.d(MMAdImplController.TAG, "Cached ad is valid. Moving it to the front of the queue.");
                                AdCache.setNextCachedAd(adImpl.getContext(), adImpl.getCachedName(), videoAd.getId());
                                Event.fetchStartedCaching(adImpl);
                                Event.requestCompleted(adImpl);
                            } else {
                                Event.logEvent(videoAd.cacheMissURL);
                                MMLog.d(MMAdImplController.TAG, "Downloading ad...");
                                Event.fetchStartedCaching(adImpl);
                                videoAd.downloadPriority = 3;
                                AdCache.startDownloadTask(adImpl.getContext(), adImpl.getCachedName(), videoAd, adImpl.controller);
                            }
                        }
                    }
                } catch (Exception illegalE) {
                    illegalE.printStackTrace();
                    return failWithInfoMessage(new MMException("Millennial ad return failed. Invalid response data.", illegalE));
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                    return failWithInfoMessage(new MMException("Millennial ad return failed. " + ioe, ioe));
                }
            }
            return true;
        }

        private boolean isHandledHtmlResponse(HttpEntity httpEntity) {
            MMAdImpl adImpl = null;
            try {
                if (MMAdImplController.this.adImplRef != null) {
                    adImpl = (MMAdImpl) MMAdImplController.this.adImplRef.get();
                }
                if (adImpl != null) {
                    if (adImpl.isBanner()) {
                        if (adImpl.controller != null) {
                            adImpl.controller.setLastHeaders(this.mmHeaders);
                            adImpl.controller.setWebViewContent(HttpGetRequest.convertStreamToString(httpEntity.getContent()), this.adUrl);
                        }
                        Event.requestCompleted(adImpl);
                    } else {
                        InterstitialAd interstitialAd = new InterstitialAd();
                        interstitialAd.content = HttpGetRequest.convertStreamToString(httpEntity.getContent());
                        interstitialAd.setId(adImpl.adType);
                        interstitialAd.adUrl = this.adUrl;
                        interstitialAd.mmHeaders = this.mmHeaders;
                        if (MMSDK.logLevel >= 5) {
                            MMLog.v(MMAdImplController.TAG, String.format("Received interstitial ad with url %s.", new Object[]{interstitialAd.adUrl}));
                            MMLog.v(MMAdImplController.TAG, interstitialAd.content);
                        }
                        AdCache.save(adImpl.getContext(), interstitialAd);
                        AdCache.setNextCachedAd(adImpl.getContext(), adImpl.getCachedName(), interstitialAd.getId());
                        Event.fetchStartedCaching(adImpl);
                        Event.requestCompleted(adImpl);
                    }
                }
                return true;
            } catch (Exception ioe) {
                return failWithErrorMessage(new MMException("Exception raised in HTTP stream: " + ioe, ioe));
            }
        }
    }

    private MMAdImplController(MMAdImpl adImpl) {
        MMLog.d(TAG, "**************** creating new controller.");
        this.adImplRef = new WeakReference(adImpl);
        if (adImpl.linkForExpansionId != 0) {
            linkForExpansion(adImpl);
            this.webView = getWebViewFromExistingAdImpl(adImpl);
        } else if (!(adImpl instanceof MMInterstitialAdImpl)) {
            if (adImpl.isBanner()) {
                this.webView = new MMWebView(adImpl.getContext().getApplicationContext(), adImpl.internalId);
                this.webView.requiresPreAdSizeFix = true;
                return;
            }
            this.webView = new MMWebView(adImpl.getContext(), adImpl.internalId);
        }
    }

    void linkForExpansion(MMAdImpl expansionAdImpl) {
        MMAdImpl adImpl = getAdImplWithId(expansionAdImpl.linkForExpansionId);
        if (adImpl != null) {
            this.linkedAdImplId = expansionAdImpl.linkForExpansionId;
            adImpl.controller.linkedAdImplId = expansionAdImpl.internalId;
            adImpl.linkForExpansionId = expansionAdImpl.internalId;
        }
    }

    static synchronized void assignAdViewController(MMAdImpl adImpl) {
        synchronized (MMAdImplController.class) {
            if (adImpl.controller != null) {
                if (!saveableControllers.containsValue(adImpl.controller)) {
                    if (adImpl.isLifecycleObservable()) {
                        saveableControllers.put(Long.valueOf(adImpl.internalId), adImpl.controller);
                        if (weakUnsaveableAdRef.containsKey(Long.valueOf(adImpl.internalId))) {
                            weakUnsaveableAdRef.remove(Long.valueOf(adImpl.internalId));
                        }
                    } else if (!weakUnsaveableAdRef.containsKey(Long.valueOf(adImpl.internalId))) {
                        weakUnsaveableAdRef.put(Long.valueOf(adImpl.internalId), new WeakReference(adImpl.controller));
                    }
                }
                MMLog.d(TAG, adImpl + " - Has a controller");
            } else {
                MMLog.d(TAG, "*****************************************assignAdViewController for " + adImpl);
                MMAdImplController controller = (MMAdImplController) saveableControllers.get(Long.valueOf(adImpl.internalId));
                if (controller == null) {
                    WeakReference<MMAdImplController> controllerRef = (WeakReference) weakUnsaveableAdRef.get(Long.valueOf(adImpl.internalId));
                    if (controllerRef != null) {
                        controller = (MMAdImplController) controllerRef.get();
                    }
                    if (controller == null) {
                        controller = new MMAdImplController(adImpl);
                        if (adImpl.isLifecycleObservable()) {
                            saveableControllers.put(Long.valueOf(adImpl.internalId), controller);
                        } else {
                            weakUnsaveableAdRef.put(Long.valueOf(adImpl.internalId), new WeakReference(controller));
                        }
                    }
                }
                adImpl.controller = controller;
                controller.adImplRef = new WeakReference(adImpl);
                if (!(controller.webView == null || (adImpl instanceof MMInterstitialAdImpl))) {
                    setupWebView(adImpl);
                }
            }
        }
    }

    private static synchronized void setupWebView(MMAdImpl adImpl) {
        synchronized (MMAdImplController.class) {
            MMAdImplController controller = adImpl.controller;
            if (controller.webView != null) {
                controller.webView.setWebViewClient(adImpl.getMMWebViewClient());
                if (!controller.webView.isCurrentParent(adImpl.internalId)) {
                    LayoutParams layoutParams;
                    if (adImpl.isBanner()) {
                        layoutParams = new LayoutParams(-2, -2);
                        if (controller.webView.isMraidResized()) {
                            controller.webView.unresizeToDefault(adImpl);
                        }
                    } else {
                        layoutParams = new LayoutParams(-2, -1);
                    }
                    controller.webView.removeFromParent();
                    adImpl.addView(controller.webView, layoutParams);
                }
            }
        }
    }

    static synchronized boolean attachWebViewFromOverlay(MMAdImpl overlayAdImpl) {
        boolean z = false;
        synchronized (MMAdImplController.class) {
            if (overlayAdImpl != null) {
                MMLog.d(TAG, "attachWebViewFromOverlay with " + overlayAdImpl);
                if (!(overlayAdImpl.controller == null || overlayAdImpl.controller.webView == null)) {
                    overlayAdImpl.controller.webView.resetSpeechKit();
                }
                MMAdImpl bannerAdImpl = getAdImplWithId(overlayAdImpl.linkForExpansionId);
                if (!(bannerAdImpl == null || bannerAdImpl.controller == null)) {
                    if (bannerAdImpl.controller.webView == null) {
                        if (!(overlayAdImpl.controller == null || overlayAdImpl.controller.webView == null)) {
                            bannerAdImpl.controller.webView = overlayAdImpl.controller.webView;
                            overlayAdImpl.removeView(overlayAdImpl.controller.webView);
                            overlayAdImpl.controller.webView = null;
                        }
                    }
                    bannerAdImpl.controller.webView.setMraidDefault();
                    bannerAdImpl.controller.webView.setWebViewClient(bannerAdImpl.getMMWebViewClient());
                    z = true;
                }
            }
        }
        return z;
    }

    static synchronized MMWebView getWebViewFromExistingAdImpl(MMAdImpl requestorAdImpl) {
        MMWebView mmWebView;
        synchronized (MMAdImplController.class) {
            MMLog.i(TAG, "getWebViewFromExistingLayout(" + requestorAdImpl.internalId + " taking from " + requestorAdImpl.linkForExpansionId + ")");
            mmWebView = null;
            MMAdImpl holderAdImpl = getAdImplWithId(requestorAdImpl.linkForExpansionId);
            if (!(holderAdImpl == null || holderAdImpl.controller == null)) {
                mmWebView = holderAdImpl.controller.webView;
                holderAdImpl.controller.webView = null;
            }
        }
        return mmWebView;
    }

    static synchronized MMAdImpl getAdImplWithId(long internalId) {
        MMAdImpl mMAdImpl = null;
        synchronized (MMAdImplController.class) {
            if (internalId != NO_ID_RETURNED) {
                MMAdImplController controller = (MMAdImplController) saveableControllers.get(Long.valueOf(internalId));
                if (controller == null) {
                    WeakReference<MMAdImplController> controllerRef = (WeakReference) weakUnsaveableAdRef.get(Long.valueOf(internalId));
                    if (controllerRef != null) {
                        controller = (MMAdImplController) controllerRef.get();
                    }
                }
                if (controller != null) {
                    mMAdImpl = (MMAdImpl) controller.adImplRef.get();
                }
            }
        }
        return mMAdImpl;
    }

    static synchronized void removeAdViewController(MMAdImpl adImpl) {
        synchronized (MMAdImplController.class) {
            if (adImpl.controller != null) {
                if (adImpl.isLifecycleObservable()) {
                    saveableControllers.put(Long.valueOf(adImpl.internalId), adImpl.controller);
                    if (weakUnsaveableAdRef.get(Long.valueOf(adImpl.internalId)) != null) {
                        weakUnsaveableAdRef.remove(Long.valueOf(adImpl.internalId));
                    }
                } else {
                    weakUnsaveableAdRef.put(Long.valueOf(adImpl.internalId), new WeakReference(adImpl.controller));
                }
                MMLog.d(TAG, "****************RemoveAdviewcontroller - " + adImpl);
                if (adImpl.isFinishing) {
                    saveableControllers.remove(Long.valueOf(adImpl.internalId));
                    weakUnsaveableAdRef.remove(Long.valueOf(adImpl.internalId));
                }
                MMAdImplController controller = adImpl.controller;
                adImpl.controller = null;
                MMLog.d(TAG, "****************RemoveAdviewcontroller - controllers " + controllersToString());
                if (controller.webView != null) {
                    MMLog.d(TAG, "****************RemoveAdviewcontroller - controller!=null, expanding=" + controller.webView.isExpanding);
                    adImpl.removeView(controller.webView);
                    controller.webView.isExpanding = false;
                    if (adImpl.isFinishing && adImpl.linkForExpansionId == 0) {
                        controller.webView.loadData("<html></html>", "text/html", HttpRequest.CHARSET_UTF8);
                        controller.webView.resetSpeechKit();
                        controller.webView = null;
                    }
                }
            }
        }
    }

    void requestAd() {
        MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
        if (adImpl == null) {
            MMLog.e(TAG, MMException.getErrorCodeMessage(25));
            Event.requestFailed(adImpl, new MMException(25));
        } else if (!adImpl.isRefreshable()) {
            Event.requestFailed(adImpl, new MMException(16));
        } else if (!MMSDK.isUiThread()) {
            MMLog.e(TAG, MMException.getErrorCodeMessage(3));
            Event.requestFailed(adImpl, new MMException(3));
        } else if (HandShake.sharedHandShake(adImpl.getContext()).kill) {
            MMLog.i(TAG, "The server is no longer allowing ads.");
            Event.requestFailed(adImpl, new MMException(16));
        } else {
            try {
                MMLog.d(TAG, "adLayout - requestAd");
                requestAdInternal(adImpl);
            } catch (Exception e) {
                MMLog.e(TAG, "There was an exception with the ad request. ", e);
                e.printStackTrace();
            }
        }
    }

    private void requestAdInternal(MMAdImpl adImpl) {
        if (adImpl.apid == null) {
            MMException error = new MMException("MMAdView found with a null apid. New ad requests on this MMAdView are disabled until an apid has been assigned.", 1);
            MMLog.e(TAG, error.getMessage());
            Event.requestFailed(adImpl, error);
        } else if (adImpl.isBanner() || !isDownloadingCachedAd(adImpl)) {
            synchronized (this) {
                if (this.requestAdRunnable != null) {
                    MMLog.i(TAG, MMException.getErrorCodeMessage(12));
                    Event.requestFailed(adImpl, new MMException(12));
                    return;
                }
                this.requestAdRunnable = new RequestAdRunnable();
                ThreadUtils.execute(this.requestAdRunnable);
            }
        }
    }

    private synchronized boolean isDownloadingCachedAd(MMAdImpl adImpl) {
        boolean z = true;
        synchronized (this) {
            Context context = adImpl.getContext();
            if (HandShake.sharedHandShake(context).isAdTypeDownloading(adImpl.adType)) {
                MMLog.i(TAG, "There is a download in progress. Defering call for new ad");
                Event.requestFailed(adImpl, new MMException(12));
            } else {
                MMLog.d(TAG, "No download in progress.");
                CachedAd incompleteAd = AdCache.loadIncompleteDownload(context, adImpl.getCachedName());
                if (incompleteAd != null) {
                    MMLog.i(TAG, "Last ad wasn't fully downloaded. Download again.");
                    Event.fetchStartedCaching(adImpl);
                    AdCache.startDownloadTask(context, adImpl.getCachedName(), incompleteAd, this);
                } else {
                    MMLog.i(TAG, "No incomplete downloads.");
                    z = false;
                }
            }
        }
        return z;
    }

    void loadUrl(String url) {
        if (!TextUtils.isEmpty(url) && this.webView != null) {
            this.webView.loadUrl(url);
        }
    }

    public void downloadCompleted(CachedAd ad, boolean success) {
        MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
        if (adImpl == null) {
            MMLog.e(TAG, MMException.getErrorCodeMessage(25));
            return;
        }
        if (success) {
            AdCache.setNextCachedAd(adImpl.getContext(), adImpl.getCachedName(), ad.getId());
        }
        if (success) {
            Event.requestCompleted(adImpl);
        } else {
            Event.requestFailed(adImpl, new MMException(15));
        }
    }

    int checkReason(MMAdImpl adImpl, CachedAd ad) {
        if (ad.isExpired()) {
            MMLog.d(TAG, String.format("%s is expired.", new Object[]{ad.getId()}));
            return 21;
        } else if (!ad.isOnDisk(adImpl.getContext())) {
            MMLog.d(TAG, String.format("%s is not on disk.", new Object[]{ad.getId()}));
            return 22;
        } else if (HandShake.sharedHandShake(adImpl.getContext()).canDisplayCachedAd(adImpl.adType, ad.deferredViewStart)) {
            return 100;
        } else {
            MMLog.d(TAG, String.format("%s cannot be shown at this time.", new Object[]{ad.getId()}));
            return 24;
        }
    }

    int isAdAvailable(MMAdImpl adImpl) {
        CachedAd ad = AdCache.loadNextCachedAd(adImpl.getContext(), adImpl.getCachedName());
        if (ad == null) {
            MMLog.i(TAG, "No next ad.");
            return 20;
        } else if (ad.canShow(adImpl.getContext(), adImpl, true)) {
            return 0;
        } else {
            return checkReason(adImpl, ad);
        }
    }

    int display(MMAdImpl adImpl) {
        CachedAd ad = AdCache.loadNextCachedAd(adImpl.getContext(), adImpl.getCachedName());
        if (ad == null) {
            return 20;
        }
        if (!ad.canShow(adImpl.getContext(), adImpl, true)) {
            return checkReason(adImpl, ad);
        }
        Event.displayStarted(adImpl);
        AdCache.setNextCachedAd(adImpl.getContext(), adImpl.getCachedName(), null);
        ad.show(adImpl.getContext(), adImpl.internalId);
        HandShake.sharedHandShake(adImpl.getContext()).updateLastVideoViewedTime(adImpl.getContext(), adImpl.adType);
        return 0;
    }

    void setWebViewContent(String webContent, String url) {
        if (this.webView != null) {
            this.webView.setWebViewContent(webContent, url, (MMAdImpl) this.adImplRef.get());
        }
    }

    void setLastHeaders(HttpMMHeaders lastHeaders) {
        if (this.webView != null) {
            this.webView.setLastHeaders(lastHeaders);
        }
    }

    HttpMMHeaders getLastHeaders() {
        if (this.webView == null) {
            return null;
        }
        return this.webView.getLastHeaders();
    }

    public String getDefaultUserAgentString(Context context) {
        return System.getProperty("http.agent");
    }

    String getUserAgent() {
        String userAgent = null;
        MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
        if (adImpl != null) {
            Context context = adImpl.getContext();
            if (context != null) {
                userAgent = getDefaultUserAgentString(context);
            }
        }
        if (TextUtils.isEmpty(userAgent)) {
            return Build.MODEL;
        }
        return userAgent;
    }

    static String controllersToString() {
        return weakUnsaveableAdRef.toString() + " SAVED:" + saveableControllers.toString();
    }

    public String toString() {
        MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
        StringBuilder sb = new StringBuilder();
        if (adImpl != null) {
            sb.append(adImpl + "-LinkInC=" + this.linkedAdImplId);
        }
        return sb.toString() + " w/" + this.webView;
    }

    public void downloadStart(CachedAd ad) {
    }

    void unresizeToDefault() {
        if (this.webView != null) {
            this.webView.unresizeToDefault((MMAdImpl) this.adImplRef.get());
        }
    }

    void loadWebContent(String content, String adUrl) {
        MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
        if (adImpl != null && this.webView != null) {
            this.webView.setWebViewContent(content, adUrl, adImpl);
        }
    }

    static void destroyOtherInlineVideo(Context context) {
        for (Entry<Long, MMAdImplController> entry : saveableControllers.entrySet()) {
            MMAdImplController controller = (MMAdImplController) entry.getValue();
            if (controller != null) {
                MMAdImpl adImpl = (MMAdImpl) controller.adImplRef.get();
                if (adImpl != null) {
                    MMAd ad = adImpl.getCallingAd();
                    if (ad != null && (ad instanceof MMLayout)) {
                        ((MMLayout) ad).removeVideo();
                    }
                }
            }
        }
    }
}
