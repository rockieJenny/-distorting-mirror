package com.givewaygames.house_ads;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.givewaygames.ads.HouseAds;
import com.givewaygames.camera.utils.AnalyticsHelper;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.utils.Log;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdWrapper {
    AdView adView;
    ViewGroup externalLayout;
    HouseAds houseAds;
    FrameLayout internalLayout;
    final boolean isAmazon;
    int lastAdSize = 0;

    private class InternalAdListener extends AdListener {
        private InternalAdListener() {
        }

        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
            if (Log.isD) {
                Log.d("AdWrapper", "Failed to get ad.");
            }
            if (errorCode != 3) {
                AnalyticsHelper.getInstance().sendEvent("ads", "ad_failed", String.valueOf(errorCode), 0);
            } else if (AdWrapper.this.lastAdSize > 0) {
                AdWrapper.this.adView.destroy();
                AdWrapper.this.adView = null;
                AdWrapper adWrapper = AdWrapper.this;
                adWrapper.lastAdSize--;
                AdWrapper.this.requestAd(AdWrapper.this.lastAdSize);
            }
        }
    }

    public AdWrapper(boolean isAmazon) {
        this.isAmazon = isAmazon;
    }

    public void setupInternalAd(Activity activity) {
        this.houseAds = HouseAds.getInstance(activity, AnalyticsHelper.getInstance().getTracker(), this.isAmazon);
        this.internalLayout.setVisibility(0);
        if (this.internalLayout.getChildCount() == 0) {
            this.internalLayout.addView(activity.getLayoutInflater().inflate(R.layout.ads_giveway_layout, null));
        }
        setupInternalApp(this.internalLayout.getChildAt(0));
    }

    public void setInternalLayout(FrameLayout internalLayout) {
        this.internalLayout = internalLayout;
    }

    public void setExternalLayout(ViewGroup externalLayout) {
        boolean isDifferent = this.externalLayout != externalLayout;
        this.externalLayout = externalLayout;
        if (isDifferent && this.adView != null) {
            ((ViewGroup) this.adView.getParent()).removeView(this.adView);
            this.externalLayout.addView(this.adView);
        }
    }

    public void setupInternalApp(View v) {
        this.houseAds.loadRandomHouseAdFromUninstalled(v, true);
    }

    public void setupExternalAd(Activity activity) {
        requestAd(activity.getResources().getInteger(R.integer.max_ad_type));
    }

    void requestAd(int type) {
        RelativeLayout rl = (RelativeLayout) this.externalLayout.findViewById(R.id.external_ad);
        if (this.adView == null) {
            AdSize adSize = type == 0 ? AdSize.BANNER : type == 1 ? AdSize.FULL_BANNER : AdSize.LEADERBOARD;
            this.lastAdSize = type;
            try {
                this.adView = new AdView(this.externalLayout.getContext());
                this.adView.setAdSize(adSize);
                this.adView.setAdUnitId("ca-app-pub-6981944082913039/7885781703");
                this.adView.setAdListener(new InternalAdListener());
                rl.addView(this.adView);
            } catch (OutOfMemoryError e) {
                Toast.makeText(R.string.low_memory, 0);
                return;
            }
        }
        loadAd();
    }

    private void loadAd() {
        if (this.adView != null) {
            this.adView.loadAd(new Builder().addTestDevice("E518AE5D6F3875A6E5D7AFC9ACF7B115").addTestDevice("9943245345A18479849AC989F8C21C76").addTestDevice("85CDF4A21EB64FE24466E7D486F3C8BB").addTestDevice("E8B10528976F05738433A4A20A01F28A").addTestDevice("CD9A05E923052F8C257652BFF8F011F0").addTestDevice("2DBF22C9AB4E91D973C0D4278725817B").addTestDevice("B1503D5B34C835E27E79E9CD34D521C8").build());
        }
    }

    public void onDestroy() {
        if (this.adView != null) {
            this.adView.destroy();
            this.adView = null;
        }
    }
}
