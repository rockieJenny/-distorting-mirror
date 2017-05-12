package com.google.ads.mediation.inmobi;

import android.app.Activity;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.inmobi.commons.AnimationType;
import com.inmobi.commons.GenderType;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.inmobi.monetization.IMIncentivisedListener;
import com.inmobi.monetization.IMInterstitial;
import com.inmobi.monetization.IMInterstitial.State;
import com.inmobi.monetization.IMInterstitialListener;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public final class InMobiAdapter implements MediationInterstitialAdapter<InMobiAdapterExtras, InMobiAdapterServerParameters>, MediationBannerAdapter<InMobiAdapterExtras, InMobiAdapterServerParameters> {
    private static final AdSize ADSIZE_INMOBI_AD_UNIT_120X600 = new AdSize(120, SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT);
    private static final AdSize ADSIZE_INMOBI_AD_UNIT_300X250 = new AdSize(300, 250);
    private static final AdSize ADSIZE_INMOBI_AD_UNIT_320X48 = new AdSize(320, 48);
    private static final AdSize ADSIZE_INMOBI_AD_UNIT_320X50 = new AdSize(320, 50);
    private static final AdSize ADSIZE_INMOBI_AD_UNIT_468X60 = new AdSize(468, 60);
    private static final AdSize ADSIZE_INMOBI_AD_UNIT_728X90 = new AdSize(728, 90);
    private static Boolean disableHardwareFlag = Boolean.valueOf(false);
    private static Boolean isAppInitialize = Boolean.valueOf(false);
    private IMInterstitial adInterstitial;
    private IMBanner adView;
    private MediationBannerListener bannerListener;
    private IMIncentivisedListener incentListener1 = new IMIncentivisedListener() {
        public void onIncentCompleted(IMInterstitial interstitial, Map<Object, Object> params) {
            for (Object obj : params.keySet()) {
                params.get(obj.toString()).toString();
            }
        }
    };
    private MediationInterstitialListener interstitialListener;
    private FrameLayout wrappedAdView;

    private class BannerListener implements IMBannerListener {
        private BannerListener() {
        }

        public void onBannerRequestSucceeded(IMBanner arg0) {
            InMobiAdapter.this.bannerListener.onReceivedAd(InMobiAdapter.this);
        }

        public void onBannerRequestFailed(IMBanner arg0, IMErrorCode arg1) {
            switch (arg1) {
                case INTERNAL_ERROR:
                    InMobiAdapter.this.bannerListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.INTERNAL_ERROR);
                    return;
                case INVALID_REQUEST:
                    InMobiAdapter.this.bannerListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.INVALID_REQUEST);
                    return;
                case NETWORK_ERROR:
                    InMobiAdapter.this.bannerListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.NETWORK_ERROR);
                    return;
                case NO_FILL:
                    InMobiAdapter.this.bannerListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.NO_FILL);
                    return;
                default:
                    InMobiAdapter.this.bannerListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.INVALID_REQUEST);
                    return;
            }
        }

        public void onDismissBannerScreen(IMBanner arg0) {
            InMobiAdapter.this.bannerListener.onDismissScreen(InMobiAdapter.this);
        }

        public void onShowBannerScreen(IMBanner arg0) {
            InMobiAdapter.this.bannerListener.onPresentScreen(InMobiAdapter.this);
        }

        public void onLeaveApplication(IMBanner arg0) {
            InMobiAdapter.this.bannerListener.onLeaveApplication(InMobiAdapter.this);
        }

        public void onBannerInteraction(IMBanner arg0, Map<String, String> map) {
            Log.d("onBannerInteraction", "onBannerInteraction called");
            InMobiAdapter.this.bannerListener.onClick(InMobiAdapter.this);
        }
    }

    private class InterstitialListener implements IMInterstitialListener {
        private InterstitialListener() {
        }

        public void onInterstitialFailed(IMInterstitial arg0, IMErrorCode arg1) {
            switch (arg1) {
                case INTERNAL_ERROR:
                    InMobiAdapter.this.interstitialListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.INTERNAL_ERROR);
                    return;
                case INVALID_REQUEST:
                    InMobiAdapter.this.interstitialListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.INVALID_REQUEST);
                    return;
                case NETWORK_ERROR:
                    InMobiAdapter.this.interstitialListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.NETWORK_ERROR);
                    return;
                case NO_FILL:
                    InMobiAdapter.this.interstitialListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.NO_FILL);
                    return;
                default:
                    InMobiAdapter.this.interstitialListener.onFailedToReceiveAd(InMobiAdapter.this, ErrorCode.INVALID_REQUEST);
                    return;
            }
        }

        public void onInterstitialLoaded(IMInterstitial arg0) {
            InMobiAdapter.this.interstitialListener.onReceivedAd(InMobiAdapter.this);
        }

        public void onDismissInterstitialScreen(IMInterstitial arg0) {
            InMobiAdapter.this.interstitialListener.onDismissScreen(InMobiAdapter.this);
        }

        public void onShowInterstitialScreen(IMInterstitial arg0) {
            InMobiAdapter.this.interstitialListener.onPresentScreen(InMobiAdapter.this);
        }

        public void onLeaveApplication(IMInterstitial arg0) {
            InMobiAdapter.this.interstitialListener.onLeaveApplication(InMobiAdapter.this);
        }

        public void onInterstitialInteraction(IMInterstitial arg0, Map<String, String> map) {
            Log.d("onInterstitialInteraction", "onInterstitialInteraction called");
        }
    }

    private void buildAdRequest(MediationAdRequest mediationAdRequest, InMobiAdapterExtras extras) {
        if (extras == null) {
            extras = new InMobiAdapterExtras();
        }
        if (mediationAdRequest.getAgeInYears() != null) {
            InMobi.setAge(mediationAdRequest.getAgeInYears().intValue());
        }
        if (extras.getAreaCode() != null) {
            InMobi.setAreaCode(extras.getAreaCode());
        }
        if (mediationAdRequest.getLocation() != null) {
            InMobi.setCurrentLocation(mediationAdRequest.getLocation());
        }
        if (mediationAdRequest.getBirthday() != null) {
            Calendar dob = Calendar.getInstance();
            dob.setTime(mediationAdRequest.getBirthday());
            InMobi.setDateOfBirth(dob);
        }
        if (extras.getEducation() != null) {
            InMobi.setEducation(extras.getEducation());
        }
        if (extras.getEthnicity() != null) {
            InMobi.setEthnicity(extras.getEthnicity());
        }
        if (mediationAdRequest.getGender() != null) {
            switch (mediationAdRequest.getGender()) {
                case MALE:
                    InMobi.setGender(GenderType.MALE);
                    break;
                case FEMALE:
                    InMobi.setGender(GenderType.FEMALE);
                    break;
                default:
                    InMobi.setGender(GenderType.UNKNOWN);
                    break;
            }
        }
        if (extras.getIncome() != null) {
            InMobi.setIncome(extras.getIncome().intValue());
        }
        if (extras.getAge() != null) {
            InMobi.setAge(extras.getAge().intValue());
        }
        if (extras.getInterests() != null) {
            InMobi.setInterests(TextUtils.join(", ", extras.getInterests()));
        }
        if (extras.getPostalCode() != null) {
            InMobi.setPostalCode(extras.getPostalCode());
        }
        InMobi.setDeviceIDMask(extras.getDeviceIdMask());
        if (extras.getSexualOrientations() != null) {
            InMobi.setSexualOrientation(extras.getSexualOrientations());
        }
        if (extras.getMartialStatus() != null) {
            InMobi.setMaritalStatus(extras.getMartialStatus());
        }
        if (extras.getLangauge() != null) {
            InMobi.setLanguage(extras.getLangauge());
        }
        if (extras.getHasChildren() != null) {
            InMobi.setHasChildren(extras.getHasChildren());
        }
        if (extras.getCity() != null && extras.getState() != null && extras.getCountry() != null) {
            InMobi.setLocationWithCityStateCountry(extras.getCity(), extras.getState(), extras.getCountry());
        }
    }

    public Class<InMobiAdapterExtras> getAdditionalParametersType() {
        return InMobiAdapterExtras.class;
    }

    public Class<InMobiAdapterServerParameters> getServerParametersType() {
        return InMobiAdapterServerParameters.class;
    }

    public void requestBannerAd(MediationBannerListener listener, Activity activity, InMobiAdapterServerParameters serverParameters, AdSize mediationAdSize, MediationAdRequest mediationAdRequest, InMobiAdapterExtras extras) {
        if (!isAppInitialize.booleanValue()) {
            InMobi.initialize(activity, serverParameters.appId);
            isAppInitialize = Boolean.valueOf(true);
        }
        if (VERSION.SDK_INT < 7) {
            listener.onFailedToReceiveAd(this, ErrorCode.INVALID_REQUEST);
        } else if (InMobi.getVersion().substring(0, 1).equals("4")) {
            int adSize;
            this.bannerListener = listener;
            AdSize bestFitSize = mediationAdSize.findBestSize(ADSIZE_INMOBI_AD_UNIT_320X48, ADSIZE_INMOBI_AD_UNIT_320X50, ADSIZE_INMOBI_AD_UNIT_468X60, ADSIZE_INMOBI_AD_UNIT_728X90, ADSIZE_INMOBI_AD_UNIT_300X250, ADSIZE_INMOBI_AD_UNIT_120X600);
            if (bestFitSize == ADSIZE_INMOBI_AD_UNIT_320X48) {
                adSize = 9;
            } else if (bestFitSize == ADSIZE_INMOBI_AD_UNIT_320X50) {
                adSize = 15;
            } else if (bestFitSize == ADSIZE_INMOBI_AD_UNIT_468X60) {
                adSize = 12;
                Log.e("ADSIZE_INMOBI_AD_UNIT_468x60", 12 + "");
            } else if (bestFitSize == ADSIZE_INMOBI_AD_UNIT_728X90) {
                adSize = 11;
                Log.e("INMOBI_AD_UNIT_728X90", 11 + "");
            } else if (bestFitSize == ADSIZE_INMOBI_AD_UNIT_300X250) {
                adSize = 10;
                Log.e("INMOBI_AD_UNIT_300X250", 10 + "");
            } else if (bestFitSize == ADSIZE_INMOBI_AD_UNIT_120X600) {
                adSize = 13;
                Log.e("INMOBI_AD_UNIT_120X600", 13 + "");
            } else {
                listener.onFailedToReceiveAd(this, ErrorCode.INVALID_REQUEST);
                return;
            }
            LayoutParams wrappedLayoutParams = new LayoutParams(bestFitSize.getWidthInPixels(activity), bestFitSize.getHeightInPixels(activity));
            this.adView = new IMBanner(activity, serverParameters.appId, adSize);
            this.adView.setRefreshInterval(-1);
            this.adView.setAnimationType(AnimationType.ANIMATION_OFF);
            if (mediationAdRequest.getKeywords() != null) {
                this.adView.setKeywords(TextUtils.join(", ", mediationAdRequest.getKeywords()));
            }
            HashMap<String, String> paramMap = new HashMap();
            paramMap.put("tp", "c_admob");
            this.adView.setRequestParams(paramMap);
            if (extras == null) {
                extras = new InMobiAdapterExtras();
            }
            if (!(extras.getRefTagKey() == null || extras.getRefTagValue() == null || extras.getRefTagKey().trim().equals("") || extras.getRefTagValue().trim().equals(""))) {
                this.adView.setRefTagParam(extras.getRefTagKey(), extras.getRefTagValue());
            }
            this.adView.setIMBannerListener(new BannerListener());
            if (disableHardwareFlag.booleanValue()) {
                this.adView.disableHardwareAcceleration();
            }
            this.wrappedAdView = new FrameLayout(activity);
            this.wrappedAdView.setLayoutParams(wrappedLayoutParams);
            this.adView.setLayoutParams(new LinearLayout.LayoutParams(bestFitSize.getWidthInPixels(activity), bestFitSize.getHeightInPixels(activity)));
            this.wrappedAdView.addView(this.adView);
            buildAdRequest(mediationAdRequest, extras);
            this.adView.loadBanner();
        } else {
            listener.onFailedToReceiveAd(this, ErrorCode.INVALID_REQUEST);
            Log.e("Invalid SDK VERSION", "Please integrate with new sdk" + InMobi.getVersion());
        }
    }

    public void requestInterstitialAd(MediationInterstitialListener listener, Activity activity, InMobiAdapterServerParameters serverParameters, MediationAdRequest mediationAdRequest, InMobiAdapterExtras extras) {
        if (!isAppInitialize.booleanValue()) {
            InMobi.initialize(activity, serverParameters.appId);
            isAppInitialize = Boolean.valueOf(true);
        }
        if (VERSION.SDK_INT < 7) {
            listener.onFailedToReceiveAd(this, ErrorCode.INVALID_REQUEST);
        } else if (InMobi.getVersion().substring(0, 1).equals("4")) {
            this.interstitialListener = listener;
            this.adInterstitial = new IMInterstitial(activity, serverParameters.appId);
            if (mediationAdRequest.getKeywords() != null) {
                this.adInterstitial.setKeywords(TextUtils.join(", ", mediationAdRequest.getKeywords()));
            }
            HashMap<String, String> paramMap = new HashMap();
            paramMap.put("tp", "c_admob");
            this.adInterstitial.setRequestParams(paramMap);
            this.adInterstitial.setIMInterstitialListener(new InterstitialListener());
            this.adInterstitial.setIMIncentivisedListener(this.incentListener1);
            if (disableHardwareFlag.booleanValue()) {
                this.adInterstitial.disableHardwareAcceleration();
            }
            buildAdRequest(mediationAdRequest, extras);
            this.adInterstitial.loadInterstitial();
        } else {
            listener.onFailedToReceiveAd(this, ErrorCode.INVALID_REQUEST);
            Log.e("Invalid SDK VERSION", "Please integrate with new sdk" + InMobi.getVersion());
        }
    }

    public void showInterstitial() {
        if (this.adInterstitial.getState() == State.READY) {
            this.adInterstitial.show();
        }
    }

    public void destroy() {
    }

    public View getBannerView() {
        return this.wrappedAdView;
    }

    public static void disableHardwareAcceleration() {
        disableHardwareFlag = Boolean.valueOf(true);
    }
}
