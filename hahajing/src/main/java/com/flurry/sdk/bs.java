package com.flurry.sdk;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RelativeLayout.LayoutParams;
import com.flurry.android.AdCreative;
import com.flurry.android.AdNetworkView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import java.util.Collections;

public final class bs extends AdNetworkView {
    private static final String a = bs.class.getSimpleName();
    private final String b;
    private final String c;
    private final boolean d;
    private AdView e;
    private AdListener f;

    final class a extends AdListener {
        final /* synthetic */ bs a;

        private a(bs bsVar) {
            this.a = bsVar;
        }

        public void onAdLoaded() {
            this.a.onAdShown(Collections.emptyMap());
            gd.a(4, bs.a, "GMS AdView onAdLoaded.");
        }

        public void onAdFailedToLoad(int i) {
            this.a.onRenderFailed(Collections.emptyMap());
            gd.a(5, bs.a, "GMS AdView onAdFailedToLoad: " + i + ".");
        }

        public void onAdOpened() {
            gd.a(4, bs.a, "GMS AdView onAdOpened.");
        }

        public void onAdClosed() {
            this.a.onAdClosed(Collections.emptyMap());
            gd.a(4, bs.a, "GMS AdView onAdClosed.");
        }

        public void onAdLeftApplication() {
            this.a.onAdClicked(Collections.emptyMap());
            gd.a(4, bs.a, "GMS AdView onAdLeftApplication.");
        }
    }

    public bs(Context context, r rVar, AdCreative adCreative, Bundle bundle) {
        super(context, rVar, adCreative);
        this.b = bundle.getString("com.flurry.gms.ads.MY_AD_UNIT_ID");
        this.c = bundle.getString("com.flurry.gms.ads.MYTEST_AD_DEVICE_ID");
        this.d = bundle.getBoolean("com.flurry.gms.ads.test");
        setFocusable(true);
    }

    AdView getAdView() {
        return this.e;
    }

    AdListener getAdListener() {
        return this.f;
    }

    private AdSize a(int i, int i2) {
        if (i >= 728 && i2 >= 90) {
            return AdSize.LEADERBOARD;
        }
        if (i >= 468 && i2 >= 60) {
            return AdSize.FULL_BANNER;
        }
        if (i >= 320 && i2 >= 50) {
            return AdSize.BANNER;
        }
        if (i >= 300 && i2 >= 250) {
            return AdSize.MEDIUM_RECTANGLE;
        }
        gd.a(3, a, "Could not find GMS AdSize that matches size");
        return null;
    }

    public void initLayout() {
        gd.a(4, a, "GMS AdView initLayout.");
        Context context = getContext();
        int width = getAdCreative().getWidth();
        int height = getAdCreative().getHeight();
        AdSize a = a(context, width, height);
        if (a == null) {
            gd.a(6, a, "Could not find GMS AdSize that matches {width = " + width + ", height " + height + "}");
            return;
        }
        gd.a(3, a, "Determined GMS AdSize as " + a + " that best matches {width = " + width + ", height = " + height + "}");
        this.f = new a();
        this.e = new AdView(context);
        this.e.setAdSize(a);
        this.e.setAdUnitId(this.b);
        this.e.setAdListener(this.f);
        setGravity(17);
        addView(this.e, new LayoutParams(a.getWidthInPixels(context), a.getHeightInPixels(context)));
        Builder builder = new Builder();
        if (this.d) {
            gd.a(3, a, "GMS AdView set to Test Mode.");
            builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            if (!TextUtils.isEmpty(this.c)) {
                builder.addTestDevice(this.c);
            }
        }
        this.e.loadAd(builder.build());
    }

    private AdSize a(Context context, int i, int i2) {
        int i3 = hn.i();
        int h = hn.h();
        if (i <= 0 || i > h) {
            i = h;
        }
        if (i2 <= 0 || i2 > i3) {
            i2 = i3;
        }
        return a(i, i2);
    }

    public void onActivityDestroy() {
        gd.a(4, a, "GMS AdView onDestroy.");
        if (this.e != null) {
            this.e.destroy();
            this.e = null;
        }
        super.onActivityDestroy();
    }
}
