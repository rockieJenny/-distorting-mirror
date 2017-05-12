package com.google.ads.mediation.flurry.impl;

import android.util.Log;
import com.flurry.android.ads.FlurryAdBanner;
import com.flurry.android.ads.FlurryAdBannerListener;
import com.flurry.android.ads.FlurryAdErrorType;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.mediation.flurry.FlurryAdapter;

public class c implements FlurryAdBannerListener {
    final /* synthetic */ FlurryAdapter a;
    private final String b;

    private c(FlurryAdapter flurryAdapter) {
        this.a = flurryAdapter;
        this.b = getClass().getSimpleName();
    }

    public void onFetched(FlurryAdBanner adBanner) {
        Log.d(this.b, "onFetched(" + adBanner.toString() + ")");
        if (this.a.f != null) {
            this.a.f.displayAd();
        }
        if (this.a.d != null) {
            Log.v(FlurryAdapter.a, "Calling onReceivedAd for Banner");
            this.a.d.onReceivedAd(this.a);
        }
    }

    public void onRendered(FlurryAdBanner adBanner) {
        Log.d(this.b, "onRendered(" + adBanner.toString() + ")");
    }

    public void onShowFullscreen(FlurryAdBanner adBanner) {
        Log.d(this.b, "onShowFullscreen(" + adBanner.toString() + ")");
        if (this.a.d != null) {
            Log.v(FlurryAdapter.a, "Calling onPresentScreen for Banner");
            this.a.d.onPresentScreen(this.a);
        }
    }

    public void onCloseFullscreen(FlurryAdBanner adBanner) {
        Log.d(this.b, "onCloseFullscreen(" + adBanner.toString() + ")");
        if (this.a.d != null) {
            Log.v(FlurryAdapter.a, "Calling onDismissScreen for Banner");
            this.a.d.onDismissScreen(this.a);
        }
    }

    public void onAppExit(FlurryAdBanner adBanner) {
        Log.d(this.b, "onAppExit(" + adBanner.toString() + ")");
        if (this.a.d != null) {
            Log.v(FlurryAdapter.a, "Calling onLeaveApplication for Banner");
            this.a.d.onLeaveApplication(this.a);
        }
    }

    public void onClicked(FlurryAdBanner adBanner) {
        Log.d(this.b, "onClicked " + adBanner.toString());
        if (this.a.d != null) {
            Log.v(FlurryAdapter.a, "Calling onClick for Banner");
            this.a.d.onClick(this.a);
        }
    }

    public void onVideoCompleted(FlurryAdBanner adBanner) {
        Log.d(this.b, "onVideoCompleted " + adBanner.toString());
    }

    public void onError(FlurryAdBanner adBanner, FlurryAdErrorType adErrorType, int errorCode) {
        Log.d(this.b, "onError(" + adBanner.toString() + adErrorType.toString() + errorCode + ")");
        if (this.a.d == null) {
            return;
        }
        if (FlurryAdErrorType.FETCH.equals(adErrorType)) {
            Log.v(FlurryAdapter.a, "Calling onFailedToReceiveAd for Interstitial with errorCode: " + ErrorCode.NO_FILL);
            this.a.d.onFailedToReceiveAd(this.a, ErrorCode.NO_FILL);
        } else if (FlurryAdErrorType.RENDER.equals(adErrorType)) {
            Log.v(FlurryAdapter.a, "Calling onFailedToReceiveAd for Interstitial with errorCode: " + ErrorCode.INTERNAL_ERROR);
            this.a.d.onFailedToReceiveAd(this.a, ErrorCode.INTERNAL_ERROR);
        }
    }
}
