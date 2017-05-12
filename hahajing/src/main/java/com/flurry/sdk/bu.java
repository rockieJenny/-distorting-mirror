package com.flurry.sdk;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import java.util.Collections;

public final class bu extends bd {
    private static final String b = bu.class.getSimpleName();
    private final String c;
    private final String d;
    private final boolean e;
    private final InterstitialAd f = new InterstitialAd(c());
    private final AdListener g = new a();

    final class a extends AdListener {
        final /* synthetic */ bu a;

        private a(bu buVar) {
            this.a = buVar;
        }

        public void onAdLoaded() {
            this.a.a(Collections.emptyMap());
            gd.a(4, bu.b, "GMS AdView onAdLoaded.");
            this.a.f.show();
        }

        public void onAdFailedToLoad(int i) {
            this.a.d(Collections.emptyMap());
            gd.a(5, bu.b, "GMS AdView onAdFailedToLoad: " + i + ".");
        }

        public void onAdOpened() {
            gd.a(4, bu.b, "GMS AdView onAdOpened.");
        }

        public void onAdClosed() {
            this.a.c(Collections.emptyMap());
            gd.a(4, bu.b, "GMS AdView onAdClosed.");
        }

        public void onAdLeftApplication() {
            this.a.b(Collections.emptyMap());
            gd.a(4, bu.b, "GMS AdView onAdLeftApplication.");
        }
    }

    public bu(Context context, r rVar, Bundle bundle) {
        super(context, rVar);
        this.c = bundle.getString("com.flurry.gms.ads.MY_AD_UNIT_ID");
        this.d = bundle.getString("com.flurry.gms.ads.MYTEST_AD_DEVICE_ID");
        this.e = bundle.getBoolean("com.flurry.gms.ads.test");
        this.f.setAdUnitId(this.c);
        this.f.setAdListener(this.g);
    }

    public void a() {
        Builder builder = new Builder();
        if (this.e) {
            gd.a(3, b, "GMS AdView set to Test Mode.");
            builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            if (!TextUtils.isEmpty(this.d)) {
                builder.addTestDevice(this.d);
            }
        }
        this.f.loadAd(builder.build());
    }
}
