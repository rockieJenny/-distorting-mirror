package com.google.ads.mediation.flurry.impl;

import android.util.Log;
import com.flurry.android.ads.FlurryAdErrorType;
import com.flurry.android.ads.FlurryAdInterstitial;
import com.flurry.android.ads.FlurryAdInterstitialListener;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.mediation.flurry.FlurryAdapter;

public class d implements FlurryAdInterstitialListener {
    final /* synthetic */ FlurryAdapter a;
    private final String b;

    private d(FlurryAdapter flurryAdapter) {
        this.a = flurryAdapter;
        this.b = getClass().getSimpleName();
    }

    public void onFetched(FlurryAdInterstitial adInterstitial) {
        Log.d(this.b, "onFetched(" + adInterstitial.toString() + ")");
        if (this.a.g != null) {
            Log.v(FlurryAdapter.a, "Calling onReceivedAd for Interstitial");
            this.a.g.onReceivedAd(this.a);
        }
    }

    public void onRendered(FlurryAdInterstitial adInterstitial) {
        Log.d(this.b, "onRendered(" + adInterstitial.toString() + ")");
    }

    public void onDisplay(FlurryAdInterstitial adInterstitial) {
        Log.d(this.b, "onDisplay(" + adInterstitial.toString() + ")");
        if (this.a.g != null) {
            Log.v(FlurryAdapter.a, "Calling onPresentScreen for Interstitial");
            this.a.g.onPresentScreen(this.a);
        }
    }

    public void onClose(FlurryAdInterstitial adInterstitial) {
        Log.d(this.b, "onClose(" + adInterstitial.toString() + ")");
        if (this.a.g != null) {
            Log.v(FlurryAdapter.a, "Calling onDismissScreen for Interstitial");
            this.a.g.onDismissScreen(this.a);
        }
    }

    public void onAppExit(FlurryAdInterstitial adInterstitial) {
        Log.d(this.b, "onAppExit(" + adInterstitial.toString() + ")");
        if (this.a.g != null) {
            Log.v(FlurryAdapter.a, "Calling onLeaveApplication for Interstitial");
            this.a.g.onLeaveApplication(this.a);
        }
    }

    public void onClicked(FlurryAdInterstitial adInterstitial) {
        Log.d(this.b, "onClicked " + adInterstitial.toString());
    }

    public void onVideoCompleted(FlurryAdInterstitial adInterstitial) {
        Log.d(this.b, "onVideoCompleted " + adInterstitial.toString());
    }

    public void onError(FlurryAdInterstitial adBanner, FlurryAdErrorType adErrorType, int errorCode) {
        Log.d(this.b, "onError(" + adBanner.toString() + adErrorType.toString() + errorCode + ")");
        if (this.a.g == null) {
            return;
        }
        if (FlurryAdErrorType.FETCH.equals(adErrorType)) {
            Log.v(FlurryAdapter.a, "Calling onFailedToReceiveAd for Interstitial with errorCode: " + ErrorCode.NO_FILL);
            this.a.g.onFailedToReceiveAd(this.a, ErrorCode.NO_FILL);
        } else if (FlurryAdErrorType.RENDER.equals(adErrorType)) {
            Log.v(FlurryAdapter.a, "Calling onFailedToReceiveAd for Interstitial with errorCode: " + ErrorCode.INTERNAL_ERROR);
            this.a.g.onFailedToReceiveAd(this.a, ErrorCode.INTERNAL_ERROR);
        }
    }
}
