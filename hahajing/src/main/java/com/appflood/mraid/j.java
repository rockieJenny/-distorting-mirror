package com.appflood.mraid;

import java.util.Map;

final class j extends d {
    j(Map<String, String> map, AFBannerWebView aFBannerWebView) {
        super(map, aFBannerWebView);
    }

    public final void a() {
        " execute " + this.a + "  this " + this;
        com.appflood.e.j.a();
        int a = a("w");
        int a2 = a("h");
        int a3 = a("x");
        int a4 = a("y");
        boolean c = c("allowOffscreen");
        if (a <= 0) {
            a = this.b.d().e;
        }
        if (a2 <= 0) {
            a2 = this.b.d().f;
        }
        this.b.d().a(a, a2, a3, a4, c);
    }
}
