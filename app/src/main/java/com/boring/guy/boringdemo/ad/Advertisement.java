package com.boring.guy.boringdemo.ad;

import com.boring.guy.boringdemo.ApplicationEx;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.NativeAd;

/**
 * Created by rockie on 2017/5/13.
 */
public class Advertisement {

    public void loadAd(){
        final NativeAd ad = new NativeAd(ApplicationEx.getInstance(),"");
        ad.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad adv) {
                if (adv != ad){
                    return;
                }
                ad.getAdTitle();
                ad.getAdSubtitle();
                ad.getAdIcon();
                ad.registerViewForInteraction(null);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }
        });

        ad.loadAd();
    }
}
