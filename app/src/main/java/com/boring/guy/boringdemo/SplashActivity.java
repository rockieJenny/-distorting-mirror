package com.boring.guy.boringdemo;

import android.app.Activity;
import android.os.Bundle;

import com.boring.guy.boringdemo.ad.AdConstant;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;

/**
 * Created by rockie on 2017/5/13.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AdLoader.Builder builder = new AdLoader.Builder(getApplicationContext(), AdConstant.ADMOB_SPLASH_ID);
        builder.forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
            @Override
            public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {

            }
        }).forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
            @Override
            public void onContentAdLoaded(NativeContentAd nativeContentAd) {

            }
        });
    }
}
