package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMException;
import com.millennialmedia.android.MMInterstitial;
import com.millennialmedia.android.RequestListener;
import java.util.Collections;

public final class cc extends bd {
    private static final String b = cc.class.getSimpleName();
    private boolean c;
    private final String d;
    private MMInterstitial e;
    private RequestListener f;

    final class a implements RequestListener {
        final /* synthetic */ cc a;

        private a(cc ccVar) {
            this.a = ccVar;
        }

        public void requestFailed(MMAd mMAd, MMException mMException) {
            this.a.d(Collections.emptyMap());
            gd.a(3, cc.b, "Millennial MMAdView Interstitial failed to load ad.");
        }

        public void requestCompleted(MMAd mMAd) {
            gd.a(3, cc.b, "Millennial MMAdView returned interstitial ad: " + System.currentTimeMillis());
            if (!this.a.c) {
                this.a.e.display();
            }
        }

        public void MMAdOverlayLaunched(MMAd mMAd) {
            this.a.a(Collections.emptyMap());
            gd.a(3, cc.b, "Millennial MMAdView Interstitial overlay launched." + System.currentTimeMillis());
        }

        public void MMAdRequestIsCaching(MMAd mMAd) {
            gd.a(3, cc.b, "Millennial MMAdView Interstitial request is caching.");
        }

        public void MMAdOverlayClosed(MMAd mMAd) {
            this.a.c(Collections.emptyMap());
            gd.a(3, cc.b, "Millennial MMAdView Interstitial overlay closed.");
        }

        public void onSingleTap(MMAd mMAd) {
            this.a.b(Collections.emptyMap());
            gd.a(3, cc.b, "Millennial MMAdView Interstitial tapped.");
        }
    }

    public cc(Context context, r rVar, Bundle bundle) {
        super(context, rVar);
        this.d = bundle.getString("com.flurry.millennial.MYAPIDINTERSTITIAL");
    }

    public void a() {
        this.e = new MMInterstitial((Activity) c());
        this.e.setApid(this.d);
        this.f = new a();
        this.e.setListener(this.f);
        this.e.fetch();
        this.c = this.e.display();
        if (this.c) {
            gd.a(3, b, "Millennial MMAdView Interstitial ad displayed immediately:" + System.currentTimeMillis() + " " + this.c);
        } else {
            gd.a(3, b, "Millennial MMAdView Interstitial ad did not display immediately:" + System.currentTimeMillis() + " " + this.c);
        }
    }
}
