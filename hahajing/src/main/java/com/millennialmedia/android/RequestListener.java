package com.millennialmedia.android;

import android.util.Log;

public interface RequestListener {

    public static class RequestListenerImpl implements RequestListener {
        public void MMAdOverlayLaunched(MMAd mmAd) {
            Log.i(MMSDK.SDKLOG, "Millennial Media Ad View overlay launched");
        }

        public void MMAdOverlayClosed(MMAd mmAd) {
            Log.i(MMSDK.SDKLOG, "Millennial Media Ad View overlay closed");
        }

        public void MMAdRequestIsCaching(MMAd mmAd) {
            Log.i(MMSDK.SDKLOG, "Millennial Media Ad View caching request");
        }

        public void requestCompleted(MMAd mmAd) {
            Log.i(MMSDK.SDKLOG, "Ad request succeeded");
        }

        public void requestFailed(MMAd mmAd, MMException error) {
            Log.i(MMSDK.SDKLOG, String.format("Ad request failed with error: %d %s.", new Object[]{Integer.valueOf(error.getCode()), error.getMessage()}));
        }

        public void onSingleTap(MMAd mmAd) {
            Log.i(MMSDK.SDKLOG, "Ad tapped");
        }
    }

    void MMAdOverlayClosed(MMAd mMAd);

    void MMAdOverlayLaunched(MMAd mMAd);

    void MMAdRequestIsCaching(MMAd mMAd);

    void onSingleTap(MMAd mMAd);

    void requestCompleted(MMAd mMAd);

    void requestFailed(MMAd mMAd, MMException mMException);
}
