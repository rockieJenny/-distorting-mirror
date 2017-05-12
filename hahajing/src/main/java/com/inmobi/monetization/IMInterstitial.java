package com.inmobi.monetization;

import android.app.Activity;
import com.google.android.gms.games.quest.Quests;
import com.google.android.gms.location.LocationRequest;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.AdErrorCode;
import com.inmobi.monetization.internal.Constants;
import com.inmobi.monetization.internal.IMAdListener;
import com.inmobi.monetization.internal.InterstitialAd;
import com.inmobi.monetization.internal.InvalidManifestErrorMessages;
import java.util.HashMap;
import java.util.Map;

public class IMInterstitial {
    InterstitialAd a;
    boolean b = false;
    IMAdListener c = new IMAdListener(this) {
        final /* synthetic */ IMInterstitial a;

        {
            this.a = r1;
        }

        public void onShowAdScreen() {
            this.a.h = State.ACTIVE;
            this.a.a(102, null, null);
        }

        public void onLeaveApplication() {
            this.a.a(LocationRequest.PRIORITY_LOW_POWER, null, null);
        }

        public void onDismissAdScreen() {
            this.a.h = State.INIT;
            this.a.a(Quests.SELECT_RECENTLY_FAILED, null, null);
        }

        public void onAdRequestSucceeded() {
            this.a.h = State.READY;
            this.a.a(100, null, null);
        }

        public void onAdRequestFailed(AdErrorCode adErrorCode) {
            this.a.h = State.INIT;
            this.a.a(101, adErrorCode, null);
        }

        public void onAdInteraction(Map<String, String> map) {
            this.a.a(LocationRequest.PRIORITY_NO_POWER, null, map);
        }

        public void onIncentCompleted(Map<Object, Object> map) {
            this.a.a(106, null, map);
        }
    };
    private IMInterstitialListener d;
    private IMIncentivisedListener e;
    private long f = -1;
    private Activity g;
    private State h = State.INIT;
    private String i = null;

    public enum State {
        INIT,
        ACTIVE,
        LOADING,
        READY,
        UNKNOWN
    }

    public IMInterstitial(Activity activity, String str) {
        this.g = activity;
        this.i = str;
        a();
    }

    public IMInterstitial(Activity activity, long j) {
        this.g = activity;
        this.f = j;
        a();
    }

    public State getState() {
        return this.h;
    }

    private void a() {
        if (this.f > 0) {
            this.a = new InterstitialAd(this.g, this.f);
        } else {
            this.a = new InterstitialAd(this.g, this.i);
        }
        this.a.setAdListener(this.c);
    }

    public void loadInterstitial() {
        IMErrorCode iMErrorCode;
        if (this.a == null) {
            iMErrorCode = IMErrorCode.INVALID_REQUEST;
            Log.debug(Constants.LOG_TAG, "Interstitial ad is in ACTIVE state. Try again after sometime.");
            this.d.onInterstitialFailed(this, iMErrorCode);
        } else if (this.h == State.LOADING) {
            iMErrorCode = IMErrorCode.INVALID_REQUEST;
            iMErrorCode.setMessage(InvalidManifestErrorMessages.MSG_AD_DOWNLOAD);
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_AD_DOWNLOAD);
            this.d.onInterstitialFailed(this, iMErrorCode);
        } else if (this.h == State.ACTIVE) {
            iMErrorCode = IMErrorCode.INVALID_REQUEST;
            iMErrorCode.setMessage("Interstitial ad is in ACTIVE state. Try again after sometime.");
            Log.debug(Constants.LOG_TAG, "Interstitial ad is in ACTIVE state. Try again after sometime.");
            this.d.onInterstitialFailed(this, iMErrorCode);
        } else {
            this.h = State.LOADING;
            this.a.loadAd();
        }
    }

    public void stopLoading() {
        if (this.a != null) {
            this.a.stopLoading();
        }
    }

    public void setIMInterstitialListener(IMInterstitialListener iMInterstitialListener) {
        this.d = iMInterstitialListener;
    }

    public void disableHardwareAcceleration() {
        this.b = true;
    }

    @Deprecated
    public void destroy() {
    }

    public void setAppId(String str) {
        if (this.a != null) {
            this.a.setAppId(str);
        }
    }

    public void setSlotId(long j) {
        if (this.a != null) {
            this.a.setSlotId(j);
        }
    }

    public void show() {
        if (this.a == null || this.h != State.READY) {
            Log.debug(Constants.LOG_TAG, "Interstitial ad is not in the 'READY' state. Current state: " + this.h);
        } else {
            this.a.show();
        }
    }

    @Deprecated
    public void show(long j) {
        if (this.a != null) {
            this.a.show(j);
        }
    }

    public void setIMIncentivisedListener(IMIncentivisedListener iMIncentivisedListener) {
        this.e = iMIncentivisedListener;
    }

    public void setKeywords(String str) {
        if (str == null || "".equals(str.trim())) {
            Log.debug(Constants.LOG_TAG, "Keywords cannot be null or blank.");
        } else if (this.a != null) {
            this.a.setKeywords(str);
        }
    }

    public void setRequestParams(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            Log.debug(Constants.LOG_TAG, "Request params cannot be null or empty.");
        } else if (this.a != null) {
            this.a.setRequestParams(map);
        }
    }

    @Deprecated
    public void setRefTagParam(String str, String str2) {
        if (str == null || str2 == null) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_NIL_KEY_VALUE);
        } else if (str.trim().equals("") || str2.trim().equals("")) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_EMPTY_KEY_VALUE);
        } else {
            Map hashMap = new HashMap();
            hashMap.put(str, str2);
            if (this.a != null) {
                this.a.setRequestParams(hashMap);
            }
        }
    }

    private void a(final int i, final AdErrorCode adErrorCode, final Map<?, ?> map) {
        if (this.d != null) {
            this.g.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMInterstitial d;

                public void run() {
                    switch (i) {
                        case 100:
                            this.d.d.onInterstitialLoaded(this.d);
                            return;
                        case 101:
                            this.d.d.onInterstitialFailed(this.d, IMErrorCode.a(adErrorCode));
                            return;
                        case 102:
                            this.d.d.onShowInterstitialScreen(this.d);
                            return;
                        case Quests.SELECT_RECENTLY_FAILED /*103*/:
                            this.d.d.onDismissInterstitialScreen(this.d);
                            return;
                        case LocationRequest.PRIORITY_LOW_POWER /*104*/:
                            this.d.d.onLeaveApplication(this.d);
                            return;
                        case LocationRequest.PRIORITY_NO_POWER /*105*/:
                            this.d.d.onInterstitialInteraction(this.d, map);
                            return;
                        case 106:
                            if (this.d.e != null) {
                                this.d.e.onIncentCompleted(this.d, map);
                                return;
                            }
                            return;
                        default:
                            Log.debug(Constants.LOG_TAG, adErrorCode.toString());
                            return;
                    }
                }
            });
        }
    }
}
