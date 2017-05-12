package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.LinearLayout.LayoutParams;
import com.flurry.android.AdCreative;
import com.flurry.android.AdNetworkView;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import java.util.Collections;
import java.util.Map;

public final class bw extends AdNetworkView {
    private static final String a = bw.class.getSimpleName();
    private final String b;
    private IMBanner c;
    private IMBannerListener d;

    final class a implements IMBannerListener {
        final /* synthetic */ bw a;

        private a(bw bwVar) {
            this.a = bwVar;
        }

        public void onShowBannerScreen(IMBanner iMBanner) {
            this.a.onAdClicked(Collections.emptyMap());
            gd.a(3, bw.a, "InMobi imAdView ad shown.");
        }

        public void onDismissBannerScreen(IMBanner iMBanner) {
            this.a.onAdClosed(Collections.emptyMap());
            gd.a(3, bw.a, "InMobi imAdView dismiss ad.");
        }

        public void onBannerRequestFailed(IMBanner iMBanner, IMErrorCode iMErrorCode) {
            this.a.onRenderFailed(Collections.emptyMap());
            gd.a(3, bw.a, "InMobi imAdView ad request failed. " + iMErrorCode.toString());
        }

        public void onBannerRequestSucceeded(IMBanner iMBanner) {
            this.a.onAdShown(Collections.emptyMap());
            gd.a(3, bw.a, "InMobi imAdView ad request completed.");
        }

        public void onLeaveApplication(IMBanner iMBanner) {
            gd.a(3, bw.a, "InMobi onLeaveApplication");
        }

        public void onBannerInteraction(IMBanner iMBanner, Map<String, String> map) {
            this.a.onAdClicked(Collections.emptyMap());
            gd.a(3, bw.a, "InMobi onBannerInteraction");
        }
    }

    bw(Context context, r rVar, AdCreative adCreative, Bundle bundle) {
        super(context, rVar, adCreative);
        this.b = bundle.getString("com.flurry.inmobi.MY_APP_ID");
        InMobi.initialize((Activity) getContext(), this.b);
        setFocusable(true);
    }

    IMBanner getAdView() {
        return this.c;
    }

    IMBannerListener getAdListener() {
        return this.d;
    }

    public void initLayout() {
        gd.a(3, a, "InMobi initLayout");
        int width = getAdCreative().getWidth();
        int height = getAdCreative().getHeight();
        int i = hn.i();
        int h = hn.h();
        if (width > 0 && width <= h) {
            h = width;
        }
        if (height > 0 && height <= i) {
            i = height;
        }
        h = bx.a(new Point(h, i));
        if (-1 != h) {
            this.c = new IMBanner((Activity) getContext(), this.b, h);
            height = 320;
            i = 50;
            Point a = bx.a(h);
            if (a != null) {
                height = a.x;
                i = a.y;
            }
            gd.a(3, a, "Determined InMobi AdSize as " + height + "x" + i);
            float f = hn.d().density;
            this.c.setLayoutParams(new LayoutParams((int) ((((float) height) * f) + 0.5f), (int) ((((float) i) * f) + 0.5f)));
            this.d = new a();
            this.c.setIMBannerListener(this.d);
            setGravity(17);
            addView(this.c);
            this.c.setRefreshInterval(-1);
            this.c.loadBanner();
            return;
        }
        gd.a(3, a, "Could not find InMobi AdSize that matches size " + width + "x" + height);
        gd.a(3, a, "Could not load InMobi Ad");
    }

    public void onActivityDestroy() {
        gd.a(3, a, "InMobi onDestroy");
        if (this.c != null) {
            this.c.destroy();
            this.c = null;
        }
        super.onActivityDestroy();
    }
}
