package com.flurry.sdk;

import com.flurry.android.AdCreative;
import com.inmobi.monetization.internal.Ad;

public enum ax {
    BANNER(AdCreative.kFormatBanner),
    TAKEOVER(AdCreative.kFormatTakeover),
    STREAM("stream"),
    NATIVE(Ad.AD_TYPE_NATIVE),
    UNKNOWN("unknown");
    
    private final String f;

    private ax(String str) {
        this.f = str;
    }

    public String toString() {
        return this.f;
    }
}
