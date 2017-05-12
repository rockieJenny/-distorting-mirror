package com.google.ads.mediation.flurry;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdBanner;
import com.flurry.android.ads.FlurryAdInterstitial;
import com.flurry.android.ads.FlurryAdTargeting;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdRequest.Gender;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.flurry.impl.a;
import com.google.ads.mediation.flurry.impl.c;
import com.google.ads.mediation.flurry.impl.d;
import com.google.ads.mediation.flurry.impl.e;
import java.util.HashMap;
import java.util.Map;

public final class FlurryAdapter implements MediationBannerAdapter<FlurryAdapterExtras, FlurryAdapterServerParameters>, MediationInterstitialAdapter<FlurryAdapterExtras, FlurryAdapterServerParameters> {
    private static final String a = FlurryAdapter.class.getSimpleName();
    private Context b;
    private String c;
    private MediationBannerListener d;
    private ViewGroup e;
    private FlurryAdBanner f;
    private MediationInterstitialListener g;
    private FlurryAdInterstitial h;

    public Class<FlurryAdapterExtras> getAdditionalParametersType() {
        return FlurryAdapterExtras.class;
    }

    public Class<FlurryAdapterServerParameters> getServerParametersType() {
        return FlurryAdapterServerParameters.class;
    }

    public void requestBannerAd(MediationBannerListener listener, Activity activity, FlurryAdapterServerParameters serverParameters, AdSize adSize, MediationAdRequest mediationAdRequest, FlurryAdapterExtras extras) {
        Log.v(a, "Requesting Banner Ad");
        destroy();
        if (activity != null && serverParameters != null && adSize != null && mediationAdRequest != null) {
            a(extras);
            a aVar = new a();
            AdSize b = aVar.b(activity, adSize);
            if (b == null) {
                listener.onFailedToReceiveAd(this, ErrorCode.NO_FILL);
                return;
            }
            String a = a(serverParameters, extras);
            if (a == null) {
                a = aVar.a(activity, b);
                if (a == null) {
                    listener.onFailedToReceiveAd(this, ErrorCode.NO_FILL);
                    return;
                }
            }
            ViewGroup a2 = a((Context) activity, b);
            if (a2 != null || listener == null) {
                e.a().a(activity, serverParameters.projectApiKey);
                this.b = activity;
                this.c = a;
                this.e = a2;
                this.d = listener;
                this.f = new FlurryAdBanner(activity, a2, a);
                this.f.setListener(new c());
                this.f.setTargeting(a(mediationAdRequest));
                this.f.fetchAd();
                return;
            }
            listener.onFailedToReceiveAd(this, ErrorCode.NO_FILL);
        } else if (listener != null) {
            listener.onFailedToReceiveAd(this, ErrorCode.INVALID_REQUEST);
        }
    }

    public void requestInterstitialAd(MediationInterstitialListener listener, Activity activity, FlurryAdapterServerParameters serverParameters, MediationAdRequest mediationAdRequest, FlurryAdapterExtras extras) {
        Log.v(a, "Requesting Interstitial Ad");
        destroy();
        if (activity != null && serverParameters != null && mediationAdRequest != null) {
            a(extras);
            String a = a(serverParameters, extras);
            if (a == null) {
                listener.onFailedToReceiveAd(this, ErrorCode.NO_FILL);
                return;
            }
            this.b = activity;
            this.c = a;
            this.g = listener;
            e.a().a(activity, serverParameters.projectApiKey);
            this.h = new FlurryAdInterstitial(activity, this.c);
            this.h.setListener(new d());
            this.h.setTargeting(a(mediationAdRequest));
            this.h.fetchAd();
        } else if (listener != null) {
            listener.onFailedToReceiveAd(this, ErrorCode.INVALID_REQUEST);
        }
    }

    public void showInterstitial() {
        this.h.displayAd();
    }

    public void destroy() {
        Log.v(a, "Destroy Ad");
        this.e = null;
        this.d = null;
        if (this.f != null) {
            this.f.destroy();
            this.f = null;
        }
        this.g = null;
        if (this.h != null) {
            this.h.destroy();
            this.h = null;
        }
        this.c = null;
        if (this.b != null) {
            e.a().a(this.b);
            this.b = null;
        }
    }

    public View getBannerView() {
        return this.e;
    }

    private String a(FlurryAdapterServerParameters flurryAdapterServerParameters, FlurryAdapterExtras flurryAdapterExtras) {
        if (flurryAdapterExtras != null && flurryAdapterExtras.getAdSpace() != null) {
            return flurryAdapterExtras.getAdSpace();
        }
        if (flurryAdapterServerParameters != null) {
            return flurryAdapterServerParameters.adSpaceName;
        }
        return null;
    }

    private ViewGroup a(Context context, AdSize adSize) {
        int i = -1;
        int i2 = -2;
        if (!adSize.isFullWidth()) {
            i = adSize.getWidthInPixels(context);
        }
        if (!adSize.isAutoHeight()) {
            i2 = adSize.getHeightInPixels(context);
        }
        ViewGroup frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new LayoutParams(i, i2, 1));
        Log.v(a, "Banner view is created for {width = " + i + "px, height = " + i2 + "px}");
        return frameLayout;
    }

    private void a(FlurryAdapterExtras flurryAdapterExtras) {
        boolean z = flurryAdapterExtras != null && flurryAdapterExtras.isLogEnabled();
        if (z) {
            FlurryAgent.setLogLevel(4);
        } else {
            FlurryAgent.setLogEnabled(false);
        }
    }

    private FlurryAdTargeting a(MediationAdRequest mediationAdRequest) {
        int i = 0;
        if (mediationAdRequest == null) {
            return null;
        }
        Location location;
        Map hashMap;
        StringBuilder stringBuilder;
        FlurryAdTargeting flurryAdTargeting = new FlurryAdTargeting();
        flurryAdTargeting.setAge(mediationAdRequest.getAgeInYears() != null ? mediationAdRequest.getAgeInYears().intValue() : 0);
        if (mediationAdRequest.getGender() != null) {
            if (!mediationAdRequest.getGender().equals(Gender.FEMALE)) {
                if (mediationAdRequest.getGender().equals(Gender.MALE)) {
                    i = 1;
                }
            }
            flurryAdTargeting.setGender(i);
            location = mediationAdRequest.getLocation();
            if (location != null) {
                flurryAdTargeting.setLocation((float) location.getLatitude(), (float) location.getLongitude());
            }
            hashMap = new HashMap();
            if (!(mediationAdRequest.getKeywords() == null || mediationAdRequest.getKeywords().size() == 0)) {
                stringBuilder = new StringBuilder();
                for (String append : mediationAdRequest.getKeywords()) {
                    stringBuilder.append(append).append(" ");
                }
                hashMap.put("UserPreference", stringBuilder.toString());
            }
            flurryAdTargeting.setKeywords(hashMap);
            flurryAdTargeting.setEnableTestAds(mediationAdRequest.isTesting());
            return flurryAdTargeting;
        }
        i = -1;
        flurryAdTargeting.setGender(i);
        location = mediationAdRequest.getLocation();
        if (location != null) {
            flurryAdTargeting.setLocation((float) location.getLatitude(), (float) location.getLongitude());
        }
        hashMap = new HashMap();
        stringBuilder = new StringBuilder();
        while (r4.hasNext()) {
            stringBuilder.append(append).append(" ");
        }
        hashMap.put("UserPreference", stringBuilder.toString());
        flurryAdTargeting.setKeywords(hashMap);
        flurryAdTargeting.setEnableTestAds(mediationAdRequest.isTesting());
        return flurryAdTargeting;
    }
}
