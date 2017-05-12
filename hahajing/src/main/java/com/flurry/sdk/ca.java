package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import com.flurry.android.AdCreative;
import com.flurry.android.AdNetworkView;
import com.millennialmedia.android.MMAd;
import com.millennialmedia.android.MMAdView;
import com.millennialmedia.android.MMException;
import com.millennialmedia.android.MMSDK;
import com.millennialmedia.android.RequestListener;
import java.util.Collections;

public final class ca extends AdNetworkView {
    private static final String a = ca.class.getSimpleName();
    private final String b;
    private final String c;
    private MMAdView d;
    private RequestListener e;

    final class a implements RequestListener {
        final /* synthetic */ ca a;

        private a(ca caVar) {
            this.a = caVar;
        }

        public void requestFailed(MMAd mMAd, MMException mMException) {
            this.a.onRenderFailed(Collections.emptyMap());
            gd.a(3, ca.a, "Millennial MMAdView failed to load ad with error: " + mMException);
        }

        public void requestCompleted(MMAd mMAd) {
            this.a.onAdShown(Collections.emptyMap());
            gd.a(3, ca.a, "Millennial MMAdView returned ad." + System.currentTimeMillis());
        }

        public void MMAdOverlayLaunched(MMAd mMAd) {
            gd.a(3, ca.a, "Millennial MMAdView banner overlay launched.");
        }

        public void MMAdRequestIsCaching(MMAd mMAd) {
            gd.a(3, ca.a, "Millennial MMAdView banner request is caching.");
        }

        public void MMAdOverlayClosed(MMAd mMAd) {
            gd.a(3, ca.a, "Millennial MMAdView banner overlay closed.");
        }

        public void onSingleTap(MMAd mMAd) {
            this.a.onAdClicked(Collections.emptyMap());
            gd.a(3, ca.a, "Millennial MMAdView banner tapped.");
        }
    }

    public ca(Context context, r rVar, AdCreative adCreative, Bundle bundle) {
        super(context, rVar, adCreative);
        this.b = bundle.getString("com.flurry.millennial.MYAPID");
        this.c = bundle.getString("com.flurry.millennial.MYAPIDRECTANGLE");
        setFocusable(true);
    }

    MMAdView getAdView() {
        return this.d;
    }

    RequestListener getAdListener() {
        return this.e;
    }

    public void initLayout() {
        gd.a(3, a, "Millennial initLayout");
        int width = getAdCreative().getWidth();
        int height = getAdCreative().getHeight();
        int a = cb.a(new Point(width, height));
        if (-1 == a) {
            gd.a(3, a, "Could not find Millennial AdSize that matches size " + width + "x" + height);
            gd.a(3, a, "Could not load Millennial Ad");
            return;
        }
        Point a2 = cb.a(a);
        if (a2 == null) {
            gd.a(3, a, "Could not find Millennial AdSize that matches size " + width + "x" + height);
            gd.a(3, a, "Could not load Millennial Ad");
            return;
        }
        height = a2.x;
        int i = a2.y;
        gd.a(3, a, "Determined Millennial AdSize as " + height + "x" + i);
        this.d = new MMAdView((Activity) getContext());
        setId(MMSDK.getDefaultAdId());
        this.d.setApid(this.b);
        if (2 == a) {
            this.d.setApid(this.c);
        }
        this.d.setWidth(height);
        this.d.setHeight(i);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setGravity(17);
        this.e = new a();
        this.d.setListener(this.e);
        addView(this.d);
        this.d.getAd();
    }

    public void onActivityDestroy() {
        gd.a(3, a, "Millennial onDestroy");
        if (this.d != null) {
            this.d = null;
        }
        super.onActivityDestroy();
    }
}
