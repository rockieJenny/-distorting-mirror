package com.flurry.sdk;

import android.content.Context;
import android.os.Bundle;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import java.util.Collections;

public class br extends bd implements InterstitialAdListener {
    private static final String b = br.class.getSimpleName();
    private final String c;
    private final String d;
    private final boolean e;
    private InterstitialAd f;

    public br(Context context, r rVar, Bundle bundle) {
        super(context, rVar);
        this.c = bundle.getString("com.flurry.fan.MY_APP_ID");
        this.d = bundle.getString("com.flurry.fan.MYTEST_AD_DEVICE_ID");
        this.e = bundle.getBoolean("com.flurry.fan.test");
    }

    public void a() {
        Context c = c();
        if (this.e) {
            AdSettings.addTestDevice(this.d);
        }
        this.f = new InterstitialAd(c, this.c);
        this.f.setAdListener(this);
        this.f.loadAd();
    }

    public void onInterstitialDisplayed(Ad ad) {
        gd.a(4, b, "FAN interstitial onInterstitialDisplayed.");
        a(Collections.emptyMap());
    }

    public void onInterstitialDismissed(Ad ad) {
        gd.a(4, b, "FAN interstitial onInterstitialDismissed.");
        c(Collections.emptyMap());
    }

    public void onError(Ad ad, AdError adError) {
        gd.a(4, b, "FAN interstitial onError.");
        d(Collections.emptyMap());
        this.f.destroy();
        this.f = null;
    }

    public void onAdLoaded(Ad ad) {
        gd.a(4, b, "FAN interstitial onAdLoaded.");
        if (this.f != null && this.f.isAdLoaded()) {
            this.f.show();
        }
    }

    public void onAdClicked(Ad ad) {
        gd.a(4, b, "FAN interstitial onAdClicked.");
        b(Collections.emptyMap());
    }
}
