package com.flurry.sdk;

import android.content.Context;
import android.os.Bundle;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.flurry.android.AdCreative;
import com.flurry.android.AdNetworkView;
import java.util.Collections;

public class bp extends AdNetworkView implements AdListener {
    private static final String a = bp.class.getSimpleName();
    private final String b;
    private final String c;
    private final boolean d;
    private AdView e;

    public bp(Context context, r rVar, AdCreative adCreative, Bundle bundle) {
        super(context, rVar, adCreative);
        this.b = bundle.getString("com.flurry.fan.MY_APP_ID");
        this.c = bundle.getString("com.flurry.fan.MYTEST_AD_DEVICE_ID");
        this.d = bundle.getBoolean("com.flurry.fan.test");
        setFocusable(true);
    }

    public void initLayout() {
        gd.a(4, a, "FAN banner initLayout.");
        this.e = new AdView(getContext(), this.b, AdSize.BANNER_320_50);
        this.e.setAdListener(this);
        addView(this.e);
        if (this.d) {
            AdSettings.addTestDevice(this.c);
        }
        this.e.loadAd();
    }

    public void onAdLoaded(Ad ad) {
        onAdShown(Collections.emptyMap());
        gd.a(4, a, "FAN banner onAdLoaded.");
    }

    public void onError(Ad ad, AdError adError) {
        onRenderFailed(Collections.emptyMap());
        if (this.e != null) {
            this.e.destroy();
            this.e = null;
        }
        gd.a(6, a, "FAN banner onError.");
    }

    public void onAdClicked(Ad ad) {
        gd.a(4, a, "FAN banner onAdClicked.");
        onAdClicked(Collections.emptyMap());
    }

    public void onActivityDestroy() {
        gd.a(4, a, "FAN banner onDestroy.");
        if (this.e != null) {
            this.e.destroy();
            this.e = null;
        }
        super.onActivityDestroy();
    }
}
