package com.google.ads.mediation.millennial;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMException;
import com.millennialmedia.android.MMInterstitial;
import com.millennialmedia.android.MMRequest;
import com.millennialmedia.android.RequestListener;

public final class MillennialAdapter implements MediationBannerAdapter<MillennialAdapterExtras, MillennialAdapterServerParameters>, MediationInterstitialAdapter<MillennialAdapterExtras, MillennialAdapterServerParameters> {
    public static final int ID_BANNER = 835823882;
    private MMAdView adView;
    private MediationBannerListener bannerListener;
    private MMInterstitial interstitial;
    private MediationInterstitialListener interstitialListener;
    private FrameLayout wrappedAdView;

    private class BannerListener implements RequestListener {
        private BannerListener() {
        }

        public void MMAdOverlayClosed(MMAd ad) {
            MillennialAdapter.this.bannerListener.onDismissScreen(MillennialAdapter.this);
        }

        public void MMAdOverlayLaunched(MMAd ad) {
            MillennialAdapter.this.bannerListener.onPresentScreen(MillennialAdapter.this);
        }

        public void MMAdRequestIsCaching(MMAd ad) {
        }

        public void onSingleTap(MMAd ad) {
            MillennialAdapter.this.bannerListener.onClick(MillennialAdapter.this);
        }

        public void requestCompleted(MMAd ad) {
            MillennialAdapter.this.bannerListener.onReceivedAd(MillennialAdapter.this);
        }

        public void requestFailed(MMAd ad, MMException arg1) {
            MillennialAdapter.this.bannerListener.onFailedToReceiveAd(MillennialAdapter.this, ErrorCode.NO_FILL);
        }
    }

    private class InterstitialListener implements RequestListener {
        private InterstitialListener() {
        }

        public void MMAdOverlayClosed(MMAd ad) {
            MillennialAdapter.this.interstitialListener.onDismissScreen(MillennialAdapter.this);
        }

        public void MMAdOverlayLaunched(MMAd ad) {
            MillennialAdapter.this.interstitialListener.onPresentScreen(MillennialAdapter.this);
        }

        public void MMAdRequestIsCaching(MMAd ad) {
        }

        public void onSingleTap(MMAd ad) {
        }

        public void requestCompleted(MMAd ad) {
            MillennialAdapter.this.interstitialListener.onReceivedAd(MillennialAdapter.this);
        }

        public void requestFailed(MMAd ad, MMException exception) {
            if (exception.getCode() == 17) {
                MillennialAdapter.this.interstitialListener.onReceivedAd(MillennialAdapter.this);
            } else {
                MillennialAdapter.this.interstitialListener.onFailedToReceiveAd(MillennialAdapter.this, ErrorCode.NO_FILL);
            }
        }
    }

    private MMRequest createMMRequest(MediationAdRequest mediationAdRequest, MillennialAdapterExtras extras) {
        MMRequest mmRequest = new MMRequest();
        if (extras == null) {
            extras = new MillennialAdapterExtras();
        }
        String keywords = null;
        if (!(mediationAdRequest == null || mediationAdRequest.getKeywords() == null)) {
            keywords = TextUtils.join(",", mediationAdRequest.getKeywords());
            mmRequest.setKeywords(keywords);
        }
        String mmKeywords = extras.getKeywords();
        if (!(mmKeywords == null || TextUtils.isEmpty(mmKeywords))) {
            if (TextUtils.isEmpty(keywords)) {
                keywords = mmKeywords;
            } else {
                keywords = keywords + "," + mmKeywords;
            }
            mmRequest.setKeywords(keywords);
        }
        if (extras.getChildren() != null) {
            mmRequest.setChildren(extras.getChildren().getDescription());
        }
        if (!(mediationAdRequest == null || mediationAdRequest.getAgeInYears() == null)) {
            mmRequest.setAge(mediationAdRequest.getAgeInYears().toString());
        }
        if (extras.getAge() != -1) {
            mmRequest.setAge(Integer.toString(extras.getAge()));
        }
        if (!(mediationAdRequest == null || mediationAdRequest.getGender() == null)) {
            switch (mediationAdRequest.getGender()) {
                case MALE:
                    mmRequest.setGender(MMRequest.GENDER_MALE);
                    break;
                case FEMALE:
                    mmRequest.setGender(MMRequest.GENDER_FEMALE);
                    break;
                case UNKNOWN:
                    mmRequest.setGender("unknown");
                    break;
            }
        }
        if (extras.getGender() != null) {
            mmRequest.setGender(extras.getGender().getDescription());
        }
        if (extras.getIncomeInUsDollars() != null) {
            mmRequest.setIncome(extras.getIncomeInUsDollars().toString());
        }
        if (!(mediationAdRequest == null || mediationAdRequest.getLocation() == null)) {
            MMRequest.setUserLocation(mediationAdRequest.getLocation());
        }
        if (extras.getLocation() != null) {
            MMRequest.setUserLocation(extras.getLocation());
        }
        if (extras.getPostalCode() != null) {
            mmRequest.setZip(extras.getPostalCode());
        }
        if (extras.getMaritalStatus() != null) {
            mmRequest.setMarital(extras.getMaritalStatus().getDescription());
        }
        if (extras.getEthnicity() != null) {
            mmRequest.setEthnicity(extras.getEthnicity().getDescription());
        }
        if (extras.getPolitics() != null) {
            mmRequest.setPolitics(extras.getPolitics().getDescription());
        }
        if (extras.getEducation() != null) {
            mmRequest.setEducation(extras.getEducation().getDescription());
        }
        return mmRequest;
    }

    private static int dip(int pixels, Context context) {
        return (int) TypedValue.applyDimension(1, (float) pixels, context.getResources().getDisplayMetrics());
    }

    public Class<MillennialAdapterExtras> getAdditionalParametersType() {
        return MillennialAdapterExtras.class;
    }

    public Class<MillennialAdapterServerParameters> getServerParametersType() {
        return MillennialAdapterServerParameters.class;
    }

    public void requestBannerAd(MediationBannerListener listener, Activity activity, MillennialAdapterServerParameters serverParameters, AdSize adSize, MediationAdRequest mediationAdRequest, MillennialAdapterExtras extras) {
        LayoutParams wrappedLayoutParams;
        this.bannerListener = listener;
        this.adView = new MMAdView(activity);
        if (adSize.isSizeAppropriate(320, 53)) {
            this.adView.setWidth(320);
            this.adView.setHeight(53);
            wrappedLayoutParams = new LayoutParams(dip(320, activity), dip(53, activity));
        } else {
            this.adView.setWidth(adSize.getWidth());
            this.adView.setHeight(adSize.getHeight());
            wrappedLayoutParams = new LayoutParams(dip(adSize.getWidth(), activity), dip(adSize.getHeight(), activity));
        }
        this.adView.setApid(serverParameters.apid);
        this.adView.setMMRequest(createMMRequest(mediationAdRequest, extras));
        this.adView.setListener(new BannerListener());
        this.wrappedAdView = new FrameLayout(activity);
        this.wrappedAdView.setLayoutParams(wrappedLayoutParams);
        this.wrappedAdView.addView(this.adView);
        this.adView.setId(ID_BANNER);
        this.adView.getAd();
    }

    public void requestInterstitialAd(MediationInterstitialListener listener, Activity activity, MillennialAdapterServerParameters serverParameters, MediationAdRequest mediationAdRequest, MillennialAdapterExtras extras) {
        this.interstitialListener = listener;
        this.interstitial = new MMInterstitial(activity);
        this.interstitial.setApid(serverParameters.apid);
        this.interstitial.setMMRequest(createMMRequest(mediationAdRequest, extras));
        this.interstitial.setListener(new InterstitialListener());
        this.interstitial.fetch();
    }

    public void showInterstitial() {
        this.interstitial.display();
    }

    public void destroy() {
    }

    public View getBannerView() {
        return this.wrappedAdView;
    }
}
