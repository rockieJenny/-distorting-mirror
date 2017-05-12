package com.millennialmedia.android;

import android.content.Context;

public final class MMInterstitial implements MMAd {
    private static final String TAG = "MMInterstitial";
    MMAdImpl adImpl;
    int externalId;

    class MMInterstitialAdImpl extends MMAdImpl {
        public MMInterstitialAdImpl(Context context) {
            super(context);
        }

        MMInterstitial getCallingAd() {
            return MMInterstitial.this;
        }
    }

    public MMInterstitial(Context context) {
        this.adImpl = new MMInterstitialAdImpl(context.getApplicationContext());
    }

    public void fetch() {
        if (this.adImpl == null || this.adImpl.requestListener == null) {
            fetchInternal();
        } else {
            fetch(this.adImpl.mmRequest, this.adImpl.requestListener);
        }
    }

    public void fetch(MMRequest request) {
        if (this.adImpl == null || this.adImpl.requestListener == null) {
            fetchInternal();
        } else {
            fetch(request, this.adImpl.requestListener);
        }
    }

    public void fetch(MMRequest request, RequestListener listener) {
        if (this.adImpl != null) {
            this.adImpl.mmRequest = request;
            this.adImpl.requestListener = listener;
        }
        fetchInternal();
    }

    private void fetchInternal() {
        if (isAdAvailable()) {
            MMLog.d(TAG, "Ad already fetched and ready for display...");
            Event.requestFailed(this.adImpl, new MMException(17));
            return;
        }
        MMLog.d(TAG, "Fetching new ad...");
        this.adImpl.requestAd();
    }

    public boolean isAdAvailable() {
        if (MMSDK.isUiThread()) {
            try {
                MMAdImplController.assignAdViewController(this.adImpl);
                if (this.adImpl.controller == null || this.adImpl.controller.isAdAvailable(this.adImpl) != 0) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                MMLog.e(TAG, "There was an exception checking for a cached ad. ", e);
                e.printStackTrace();
                return false;
            }
        }
        MMLog.e(TAG, MMException.getErrorCodeMessage(3));
        return false;
    }

    public boolean display() {
        return display(false);
    }

    public boolean display(boolean throwError) {
        if (MMSDK.isUiThread()) {
            try {
                int error = displayInternal();
                if (error != 0 && throwError) {
                    throw new MMException(error);
                } else if (error == 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                if (!throwError) {
                    return false;
                }
                throw new MMException(e);
            }
        }
        MMLog.e(TAG, MMException.getErrorCodeMessage(3));
        return false;
    }

    int displayInternal() {
        try {
            MMAdImplController.assignAdViewController(this.adImpl);
            if (this.adImpl.controller != null) {
                return this.adImpl.controller.display(this.adImpl);
            }
        } catch (Exception e) {
            MMLog.e(TAG, "There was an exception displaying a cached ad. ", e);
            e.printStackTrace();
        }
        return 100;
    }

    public void setApid(String apid) {
        this.adImpl.setApid(apid);
    }

    public String getApid() {
        return this.adImpl.getApid();
    }

    public void setListener(RequestListener listener) {
        this.adImpl.setListener(listener);
    }

    public RequestListener getListener() {
        return this.adImpl.getListener();
    }

    public void setIgnoresDensityScaling(boolean ignoresDensityScaling) {
        this.adImpl.setIgnoresDensityScaling(ignoresDensityScaling);
    }

    public boolean getIgnoresDensityScaling() {
        return this.adImpl.getIgnoresDensityScaling();
    }

    public void setMMRequest(MMRequest request) {
        this.adImpl.setMMRequest(request);
    }

    public MMRequest getMMRequest() {
        return this.adImpl.getMMRequest();
    }
}
