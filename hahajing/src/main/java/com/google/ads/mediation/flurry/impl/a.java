package com.google.ads.mediation.flurry.impl;

import android.content.Context;
import android.util.DisplayMetrics;
import com.google.ads.AdSize;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class a {
    private static final Map<AdSize, String> a = a();

    public String a(Context context, AdSize adSize) {
        return (String) a.get(b(context, adSize));
    }

    public AdSize b(Context context, AdSize adSize) {
        if (context == null || adSize == null || adSize.getWidth() == 0) {
            adSize = AdSize.BANNER;
        } else if (adSize.isFullWidth() || adSize.isAutoHeight()) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int width = adSize.getWidth();
            int height = adSize.getHeight();
            if (adSize.isFullWidth()) {
                width = (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
            }
            if (adSize.isAutoHeight()) {
                height = (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
                if (height <= 400) {
                    height = 32;
                } else if (height <= 720) {
                    height = 50;
                } else {
                    height = 90;
                }
            }
            adSize = new AdSize(width, height);
        }
        return a(adSize);
    }

    private AdSize a(AdSize adSize) {
        Set keySet = a.keySet();
        return adSize.findBestSize((AdSize[]) keySet.toArray(new AdSize[keySet.size()]));
    }

    private static Map<AdSize, String> a() {
        Map hashMap = new HashMap();
        hashMap.put(AdSize.BANNER, "MMA_BANNER_ANDROID");
        hashMap.put(AdSize.IAB_BANNER, "IAB_BANNER_ANDROID");
        hashMap.put(AdSize.IAB_LEADERBOARD, "IAB_LEADERBOARD_ANDROID");
        hashMap.put(AdSize.IAB_MRECT, "IAB_MRECT_ANDROID");
        return Collections.unmodifiableMap(hashMap);
    }
}
