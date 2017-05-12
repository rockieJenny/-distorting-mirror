package com.appflood.mraid;

import java.util.Map;

final class g extends d {
    g(Map<String, String> map, AFBannerWebView aFBannerWebView) {
        super(map, aFBannerWebView);
    }

    public final void a() {
        int a = a("w");
        int a2 = a("h");
        String b = b("url");
        boolean c = c("shouldUseCustomClose");
        boolean c2 = c("lockOrientation");
        if (a <= 0) {
            a = this.b.d().e;
        }
        if (a2 <= 0) {
            a2 = this.b.d().f;
        }
        this.b.d().a(b, a, a2, c, c2);
    }
}
