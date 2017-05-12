package com.flurry.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

public final class cw {
    public static String a(Context context) {
        if (context == null) {
            return null;
        }
        String b = b(context);
        if (TextUtils.isEmpty(b)) {
            return c(context);
        }
        return b;
    }

    @TargetApi(17)
    static String b(Context context) {
        if (context != null && VERSION.SDK_INT >= 17) {
            return WebSettings.getDefaultUserAgent(context);
        }
        return null;
    }

    static String c(Context context) {
        if (context == null) {
            return null;
        }
        return new WebView(context).getSettings().getUserAgentString();
    }
}
