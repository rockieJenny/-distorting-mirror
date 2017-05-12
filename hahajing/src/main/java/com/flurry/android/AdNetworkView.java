package com.flurry.android;

import android.content.Context;
import com.flurry.sdk.aw;
import com.flurry.sdk.ec;
import com.flurry.sdk.r;
import java.util.Map;

public abstract class AdNetworkView extends ec {
    private final AdCreative a;

    public AdNetworkView(Context context, r rVar, AdCreative adCreative) {
        super(context, rVar, null);
        this.a = adCreative;
    }

    public AdNetworkView(Context context, AdCreative adCreative) {
        super(context, null, null);
        this.a = adCreative;
    }

    public AdCreative getAdCreative() {
        return this.a;
    }

    public void onAdFilled(Map<String, String> map) {
        super.onEvent(aw.EV_FILLED, map);
    }

    public void onAdUnFilled(Map<String, String> map) {
        super.onEvent(aw.EV_UNFILLED, map);
    }

    public void onAdPrepared(Map<String, String> map) {
        super.onEvent(aw.EV_PREPARED, map);
    }

    public void onAdShown(Map<String, String> map) {
        super.onEvent(aw.EV_RENDERED, map);
    }

    public void onAdClicked(Map<String, String> map) {
        super.onEvent(aw.EV_CLICKED, map);
    }

    public void onAdClosed(Map<String, String> map) {
        super.onEvent(aw.EV_AD_CLOSED, map);
    }

    public void onRenderFailed(Map<String, String> map) {
        super.onEvent(aw.EV_RENDER_FAILED, map);
    }
}
