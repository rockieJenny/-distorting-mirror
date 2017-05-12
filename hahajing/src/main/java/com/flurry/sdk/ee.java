package com.flurry.sdk;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.flurry.sdk.ec.a;

public class ee {
    public static ec a(Context context, r rVar, String str, a aVar, boolean z, boolean z2) {
        if (context == null || rVar == null) {
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            return new eb(context, rVar, aVar, z);
        }
        if (cv.g(str)) {
            ds dsVar = ds.VIDEO_AD_TYPE_CLIPS;
            if (rVar.k().q()) {
                dsVar = ds.VIDEO_AD_TYPE_MRAID;
            }
            dr drVar = new dr();
            ec a = dr.a(context, dsVar, rVar, aVar);
            a.setVideoUri(Uri.parse(str));
            return a;
        } else if (z2) {
            return new eh(context, str, rVar, aVar, z);
        } else {
            return null;
        }
    }
}
