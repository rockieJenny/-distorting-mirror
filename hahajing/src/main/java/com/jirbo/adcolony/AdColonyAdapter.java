package com.jirbo.adcolony;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;

public class AdColonyAdapter implements MediationInterstitialAdapter, AdColonyAdListener {
    AdColonyVideoAd a;
    AdColonyV4VCAd b;
    AdColonyAdListener c;
    MediationInterstitialListener d;
    String e;
    boolean f;
    boolean g;
    boolean h;

    public void requestInterstitialAd(Context context, MediationInterstitialListener listener, Bundle server_parameters, MediationAdRequest mediation_ad_request, Bundle mediation_extras) {
        this.d = listener;
        this.c = this;
        a.G = this;
        if (mediation_extras == null || mediation_extras.getString("zone_id") == null) {
            this.b = new AdColonyV4VCAd().withListener(this.c);
            if (this.b.isReady()) {
                this.f = true;
                listener.onAdLoaded(this);
                return;
            }
            this.a = new AdColonyVideoAd().withListener(this.c);
            if (this.a.isReady()) {
                listener.onAdLoaded(this);
                return;
            } else {
                listener.onAdFailedToLoad(this, 3);
                return;
            }
        }
        this.e = mediation_extras.getString("zone_id");
        this.g = mediation_extras.getBoolean("show_pre_popup");
        this.h = mediation_extras.getBoolean("show_post_popup");
        this.f = AdColony.isZoneV4VC(this.e);
        if (this.f) {
            this.b = new AdColonyV4VCAd(this.e).withListener(this.c);
            if (this.g) {
                this.b.withConfirmationDialog();
            }
            if (this.h) {
                this.b.withResultsDialog();
            }
            if (this.b.isReady()) {
                listener.onAdLoaded(this);
                return;
            } else {
                listener.onAdFailedToLoad(this, 3);
                return;
            }
        }
        this.a = new AdColonyVideoAd(this.e).withListener(this.c);
        if (this.a.isReady()) {
            listener.onAdLoaded(this);
        } else {
            listener.onAdFailedToLoad(this, 3);
        }
    }

    public void showInterstitial() {
        if (this.f) {
            if (!this.b.isReady()) {
                this.b = new AdColonyV4VCAd(this.e).withListener(this.c);
            }
            this.b.show();
            return;
        }
        if (!this.a.isReady()) {
            this.a = new AdColonyVideoAd(this.e).withListener(this.c);
        }
        this.a.show();
    }

    public void onDestroy() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onAdColonyAdStarted(AdColonyAd temp_ad) {
        this.d.onAdOpened(this);
    }

    public void onAdColonyAdAttemptFinished(AdColonyAd temp_ad) {
        if (temp_ad.shown() || temp_ad.canceled()) {
            this.d.onAdClosed(this);
        }
    }

    protected void left_application() {
        if (this.d != null) {
            this.d.onAdLeftApplication(this);
        }
    }
}
