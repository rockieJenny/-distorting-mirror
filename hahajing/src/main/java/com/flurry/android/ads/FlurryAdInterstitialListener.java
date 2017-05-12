package com.flurry.android.ads;

public interface FlurryAdInterstitialListener {
    void onAppExit(FlurryAdInterstitial flurryAdInterstitial);

    void onClicked(FlurryAdInterstitial flurryAdInterstitial);

    void onClose(FlurryAdInterstitial flurryAdInterstitial);

    void onDisplay(FlurryAdInterstitial flurryAdInterstitial);

    void onError(FlurryAdInterstitial flurryAdInterstitial, FlurryAdErrorType flurryAdErrorType, int i);

    void onFetched(FlurryAdInterstitial flurryAdInterstitial);

    void onRendered(FlurryAdInterstitial flurryAdInterstitial);

    void onVideoCompleted(FlurryAdInterstitial flurryAdInterstitial);
}
