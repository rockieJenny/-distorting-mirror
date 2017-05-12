package com.appflood.mraid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.Map;

final class v extends d {
    v(Map<String, String> map, AFBannerWebView aFBannerWebView) {
        super(map, aFBannerWebView);
    }

    public final void a() {
        String b = b("uri");
        Context context = this.b.getContext();
        Intent intent = new Intent("android.intent.action.VIEW");
        String str = "";
        if (b.toLowerCase().endsWith(".mp4")) {
            str = "mp4";
        } else if (b.toLowerCase().endsWith(".3gp")) {
            str = "3gp";
        } else if (b.toLowerCase().endsWith(".mov")) {
            str = "mov";
        } else if (b.toLowerCase().endsWith(".wmv")) {
            str = "wmv";
        }
        intent.setDataAndType(Uri.parse(b), "video/" + str);
        context.startActivity(intent);
    }
}
