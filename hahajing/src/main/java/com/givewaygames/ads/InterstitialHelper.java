package com.givewaygames.ads;

import android.app.Activity;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import java.lang.ref.WeakReference;

public class InterstitialHelper extends AdListener {
    private static final boolean DEBUG = false;
    private static final int MSG_SHOW_AD = 0;
    private static final String PREF_HAS_SHOWN_SUBSCRIBE = "has_shown_subscribe_interstitial";
    private static final String PREF_LAST_SHOW_TIME = "last_time_ad_was_shown";
    public static final String TAG = "InterstitialHelper";
    WeakReference<Activity> activityWeakReference;
    private String adCode;
    PauseHandler handler = new PauseHandler() {
        public void processMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (InterstitialHelper.this.readyToShow == null || InterstitialHelper.this.readyToShow.isReadyToShowAd()) {
                        InterstitialHelper.this.showIfReadyOrWarmUp();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    boolean hasShownSubscribe = false;
    InterstitialAd interstitial;
    private boolean isAdFree;
    DefaultReadyToShow readyToShow;
    SafeToShow safeToShow;

    public class DefaultReadyToShow {
        private int baseTimeAddition = 60;
        private int baseTimeBetweenShow = 120;
        private long lastInteractionAt;
        private long lastResumedAt;
        private long lastShownAt;
        private int minimumSecondsAfterInteraction = 3;
        private int minimumSecondsAfterOnResume = 60;
        private int numTimesShown = 0;

        public DefaultReadyToShow(Activity activity) {
            this.lastShownAt = PreferenceManager.getDefaultSharedPreferences(activity).getLong(InterstitialHelper.PREF_LAST_SHOW_TIME, 0);
        }

        public void onResume() {
            this.lastResumedAt = System.currentTimeMillis();
        }

        public void onInteraction() {
            if (!InterstitialHelper.this.isAdFree) {
                InterstitialHelper.this.warmupIfNeeded();
                this.lastInteractionAt = System.currentTimeMillis();
                InterstitialHelper.this.handler.removeMessages(0);
                InterstitialHelper.this.handler.sendEmptyMessageDelayed(0, (long) ((this.minimumSecondsAfterInteraction * 1000) + 250));
            }
        }

        public boolean isReadyToShowAd() {
            long time = System.currentTimeMillis();
            long dtInteract = Math.abs(time - this.lastInteractionAt) / 1000;
            long dtShow = Math.abs(time - this.lastShownAt) / 1000;
            long showDelay = (long) (this.baseTimeBetweenShow + ((this.numTimesShown - 1) * this.baseTimeAddition));
            boolean checkResumed = Math.abs(time - this.lastResumedAt) / 1000 >= ((long) this.minimumSecondsAfterOnResume);
            boolean checkInteract = dtInteract >= ((long) this.minimumSecondsAfterInteraction);
            boolean checkShow = dtShow >= showDelay;
            if (checkResumed && checkInteract && checkShow) {
                return true;
            }
            return false;
        }

        public void notifyOfAdShown(Activity activity) {
            this.lastShownAt = System.currentTimeMillis();
            this.numTimesShown++;
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putLong(InterstitialHelper.PREF_LAST_SHOW_TIME, this.lastShownAt).commit();
        }
    }

    public interface SafeToShow {
        boolean isSafeToShowAd();
    }

    public InterstitialHelper(Activity activity, SafeToShow safeToShow, boolean isAdFree, String adCode) {
        this.activityWeakReference = new WeakReference(activity);
        this.readyToShow = new DefaultReadyToShow(activity);
        this.isAdFree = isAdFree;
        this.adCode = adCode;
        this.safeToShow = safeToShow;
        this.hasShownSubscribe = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(PREF_HAS_SHOWN_SUBSCRIBE, false);
        this.hasShownSubscribe = true;
    }

    public boolean showRemoveAdDialog() {
        return !this.hasShownSubscribe;
    }

    public void onPause() {
        this.handler.pause();
    }

    public void onResume() {
        this.handler.resume();
        this.readyToShow.onResume();
    }

    public void onInteraction() {
        this.readyToShow.onInteraction();
    }

    private void warmupIfNeeded() {
        if (this.interstitial == null) {
            warmupInterstitial();
        }
    }

    private void showIfReadyOrWarmUp() {
        if (showRemoveAdDialog()) {
            Activity activity = (Activity) this.activityWeakReference.get();
            if (activity != null) {
                new MailingListSignupDialogFragment().show(((FragmentActivity) activity).getSupportFragmentManager(), SubscribeManager.FRAG_MAILING_LIST_SIGNUP);
                this.readyToShow.notifyOfAdShown(activity);
                this.hasShownSubscribe = true;
                PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean(PREF_HAS_SHOWN_SUBSCRIBE, this.hasShownSubscribe).apply();
                return;
            }
        }
        InterstitialAd interstitialAd = this.interstitial;
        if (interstitialAd == null) {
            warmupInterstitial();
        } else if (interstitialAd.isLoaded()) {
            showInterstitial();
        }
    }

    private void warmupInterstitial() {
        Activity activity = (Activity) this.activityWeakReference.get();
        if (activity != null) {
            this.interstitial = new InterstitialAd(activity);
            this.interstitial.setAdUnitId(this.adCode);
            AdRequest adRequest = new Builder().addTestDevice("E518AE5D6F3875A6E5D7AFC9ACF7B115").addTestDevice("9943245345A18479849AC989F8C21C76").addTestDevice("85CDF4A21EB64FE24466E7D486F3C8BB").addTestDevice("E8B10528976F05738433A4A20A01F28A").addTestDevice("CD9A05E923052F8C257652BFF8F011F0").addTestDevice("BFF460B48282B7721173EF31F0E4DE4C").addTestDevice("B1503D5B34C835E27E79E9CD34D521C8").build();
            this.interstitial.setAdListener(this);
            this.interstitial.loadAd(adRequest);
        }
    }

    private void showInterstitial() {
        if (((Activity) this.activityWeakReference.get()) != null) {
            if ((this.safeToShow == null || this.safeToShow.isSafeToShowAd()) && this.interstitial.isLoaded()) {
                this.interstitial.show();
            }
        }
    }

    public void onAdLoaded() {
        super.onAdLoaded();
    }

    public void onAdClosed() {
        super.onAdClosed();
        this.interstitial = null;
        warmupInterstitial();
    }

    public void onAdFailedToLoad(int errorCode) {
        super.onAdFailedToLoad(errorCode);
        this.interstitial = null;
    }

    public void onAdOpened() {
        super.onAdOpened();
        Activity activity = (Activity) this.activityWeakReference.get();
        if (activity != null) {
            this.readyToShow.notifyOfAdShown(activity);
            this.interstitial = null;
        }
    }
}
