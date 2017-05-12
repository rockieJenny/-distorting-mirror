package com.inmobi.monetization;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gms.games.quest.Quests;
import com.google.android.gms.location.LocationRequest;
import com.inmobi.commons.AnimationType;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.ThinICE;
import com.inmobi.monetization.internal.AdErrorCode;
import com.inmobi.monetization.internal.BannerAd;
import com.inmobi.monetization.internal.Constants;
import com.inmobi.monetization.internal.IMAdListener;
import com.inmobi.monetization.internal.InvalidManifestErrorMessages;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.imai.RequestResponseManager;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.HashMap;
import java.util.Map;

public final class IMBanner extends RelativeLayout {
    public static final int INMOBI_AD_UNIT_120X600 = 13;
    public static final int INMOBI_AD_UNIT_300X250 = 10;
    public static final int INMOBI_AD_UNIT_320X48 = 9;
    public static final int INMOBI_AD_UNIT_320X50 = 15;
    public static final int INMOBI_AD_UNIT_468X60 = 12;
    public static final int INMOBI_AD_UNIT_728X90 = 11;
    public static final int REFRESH_INTERVAL_MINIMUM = 0;
    public static final int REFRESH_INTERVAL_OFF = -1;
    IMAdListener a = new IMAdListener(this) {
        final /* synthetic */ IMBanner a;

        {
            this.a = r1;
        }

        public void onShowAdScreen() {
            this.a.a(102, null, null);
        }

        public void onLeaveApplication() {
            this.a.a(LocationRequest.PRIORITY_LOW_POWER, null, null);
        }

        public void onDismissAdScreen() {
            this.a.a(Quests.SELECT_RECENTLY_FAILED, null, null);
        }

        public void onAdRequestSucceeded() {
            this.a.a(100, null, null);
        }

        public void onAdRequestFailed(AdErrorCode adErrorCode) {
            this.a.a(101, adErrorCode, null);
        }

        public void onAdInteraction(Map<String, String> map) {
            this.a.a(LocationRequest.PRIORITY_NO_POWER, null, map);
        }

        public void onIncentCompleted(Map<Object, Object> map) {
        }
    };
    private BannerAd b;
    private IMBannerListener c;
    private boolean d = true;
    private Activity e;
    private String f = null;
    private long g = -1;
    private int h = 15;

    public IMBanner(Activity activity, String str, int i) {
        super(activity);
        this.f = str;
        this.h = i;
        this.e = activity;
        a();
    }

    public IMBanner(Activity activity, long j) {
        super(activity);
        this.e = activity;
        this.g = j;
        a();
    }

    public IMBanner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = (Activity) context;
        try {
            this.g = Long.parseLong(attributeSet.getAttributeValue(null, "slotId"));
            a();
        } catch (Exception e) {
            try {
                this.h = Integer.parseInt(attributeSet.getAttributeValue(null, "adSize"));
            } catch (Exception e2) {
            }
            try {
                this.f = attributeSet.getAttributeValue(null, "appId");
            } catch (Exception e3) {
            }
            a();
        }
    }

    private void a() {
        if (this.g > 0) {
            this.b = new BannerAd(this.e, this, this.g, 15);
        } else {
            this.b = new BannerAd(this.e, this, this.f, this.h);
        }
        this.b.setAdListener(this.a);
        setRefreshInterval(Initializer.getConfigParams().getDefaultRefreshRate());
        addView(this.b.getView(), new LayoutParams(-1, -1));
    }

    public void loadBanner() {
        if (this.b != null) {
            this.b.loadAd();
            return;
        }
        IMErrorCode iMErrorCode = IMErrorCode.INVALID_REQUEST;
        String str = "Banner Ad instance not created with valid paramters";
        iMErrorCode.setMessage(str);
        this.c.onBannerRequestFailed(this, iMErrorCode);
        Log.verbose(Constants.LOG_TAG, str);
    }

    public void stopLoading() {
        if (this.b != null) {
            this.b.stopLoading();
        }
    }

    public void setIMBannerListener(IMBannerListener iMBannerListener) {
        this.c = iMBannerListener;
    }

    public void setAnimationType(AnimationType animationType) {
        if (this.b != null) {
            this.b.setAnimation(animationType);
        }
    }

    public void disableHardwareAcceleration() {
        if (this.b != null) {
            this.b.disableHardwareAcceleration();
        }
    }

    @Deprecated
    public void destroy() {
    }

    public void setAdSize(int i) {
        if (this.b != null) {
            this.b.setAdSize(i);
            this.h = i;
        }
    }

    public void setSlotId(long j) {
        if (this.b != null) {
            this.b.setSlotId(j);
        }
    }

    public void setAppId(String str) {
        if (this.b != null) {
            this.b.setAppId(str);
        }
    }

    public void setRefreshInterval(int i) {
        if (this.b != null) {
            this.b.setRefreshInterval(i);
        }
    }

    protected final void onWindowVisibilityChanged(int i) {
        if (i == 0) {
            try {
                ThinICE.start(this.e);
            } catch (Exception e) {
                Log.internal(Constants.LOG_TAG, "Cannot start ice. Activity is null");
            }
            if (this.b != null) {
                this.b.refreshAd();
            }
        } else if (this.b != null) {
            this.b.stopRefresh();
        }
        RequestResponseManager requestResponseManager = new RequestResponseManager();
        requestResponseManager.init();
        requestResponseManager.processClick(this.e.getApplicationContext(), null);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        b();
        Log.debug(Constants.LOG_TAG, "onAttachedToWindow");
        this.d = true;
    }

    protected void onDetachedFromWindow() {
        this.d = false;
        super.onDetachedFromWindow();
    }

    private void a(final int i, final AdErrorCode adErrorCode, final Map<?, ?> map) {
        if (!this.d) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_CALL_BACK);
        } else if (this.c != null) {
            this.e.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMBanner d;

                public void run() {
                    try {
                        switch (i) {
                            case 100:
                                this.d.c.onBannerRequestSucceeded(this.d);
                                return;
                            case 101:
                                this.d.c.onBannerRequestFailed(this.d, IMErrorCode.a(adErrorCode));
                                return;
                            case 102:
                                this.d.c.onShowBannerScreen(this.d);
                                return;
                            case Quests.SELECT_RECENTLY_FAILED /*103*/:
                                this.d.c.onDismissBannerScreen(this.d);
                                return;
                            case LocationRequest.PRIORITY_LOW_POWER /*104*/:
                                this.d.c.onLeaveApplication(this.d);
                                return;
                            case LocationRequest.PRIORITY_NO_POWER /*105*/:
                                this.d.c.onBannerInteraction(this.d, map);
                                return;
                            default:
                                Log.debug(Constants.LOG_TAG, adErrorCode.toString());
                                return;
                        }
                    } catch (Throwable e) {
                        Log.debug(Constants.LOG_TAG, "Exception giving callback to the publisher ", e);
                    }
                    Log.debug(Constants.LOG_TAG, "Exception giving callback to the publisher ", e);
                }
            });
        }
    }

    public void setKeywords(String str) {
        if (str == null || "".equals(str.trim())) {
            Log.debug(Constants.LOG_TAG, "Keywords cannot be null or blank.");
        } else if (this.b != null) {
            this.b.setKeywords(str);
        }
    }

    public void setRequestParams(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            Log.debug(Constants.LOG_TAG, "Request params cannot be null or empty.");
        } else if (this.b != null) {
            this.b.setRequestParams(map);
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
            if (this.b != null) {
                this.b.setRequestParams(hashMap);
            }
        }
    }

    private void b() {
        int i;
        int i2 = 320;
        float f = this.e.getResources().getDisplayMetrics().density;
        switch (this.h) {
            case 9:
                i = 48;
                break;
            case 10:
                i2 = 300;
                i = 250;
                break;
            case 11:
                i2 = 729;
                i = 90;
                break;
            case 12:
                i2 = 468;
                i = 60;
                break;
            case 13:
                i2 = 120;
                i = SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT;
                break;
            case 15:
                i = 50;
                break;
            default:
                i2 = getLayoutParams().width;
                i = getLayoutParams().height;
                break;
        }
        i2 = (int) (((float) i2) * f);
        getLayoutParams().height = (int) (((float) i) * f);
        getLayoutParams().width = i2;
        setLayoutParams(getLayoutParams());
    }
}
