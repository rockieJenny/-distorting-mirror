package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.ec.a;

public class dr {
    public static dq a(Context context, ds dsVar, r rVar, a aVar) {
        switch (dsVar) {
            case VIDEO_AD_TYPE_VAST:
                return new dp(context, rVar, aVar);
            case VIDEO_AD_TYPE_MRAID:
                return new do(context, rVar, aVar);
            case VIDEO_AD_TYPE_CLIPS:
                return new dn(context, rVar, aVar);
            default:
                return null;
        }
    }
}
