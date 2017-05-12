package com.appflood.mraid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.appflood.AFListActivity.AnonymousClass1;
import com.appflood.e.j;
import java.net.URLDecoder;

public final class c extends b {
    public c(AFBannerWebView aFBannerWebView) {
        super(aFBannerWebView);
    }

    public final /* bridge */ /* synthetic */ AFBannerWebView a() {
        return super.a();
    }

    protected final void a(String str) {
        "Opening in-app browser: " + str;
        j.a();
        super.a().i();
        Context context = super.a().getContext();
        String scheme = Uri.parse(str).getScheme();
        if (Event.INTENT_PHONE_CALL.equals(scheme)) {
            AnonymousClass1.a(context, str.substring(scheme.length() + 1, str.length()));
        } else if (Event.INTENT_TXT_MESSAGE.equals(scheme)) {
            String decode = URLDecoder.decode(str);
            String str2 = "";
            if (decode.contains("?body=")) {
                String[] split = decode.split("body=");
                decode = split[0].substring(0, split[0].length() - 1);
                str2 = split[1];
            }
            AnonymousClass1.a(context, decode.substring(scheme.length() + 3, decode.length()), str2);
        } else {
            Intent intent = new Intent(context, MraidBrowserActivity.class);
            intent.putExtra("url", str);
            context.startActivity(intent);
        }
    }
}
