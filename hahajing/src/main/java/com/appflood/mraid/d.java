package com.appflood.mraid;

import java.util.Map;

public abstract class d {
    protected Map<String, String> a;
    protected AFBannerWebView b;

    d(Map<String, String> map, AFBannerWebView aFBannerWebView) {
        this.a = map;
        this.b = aFBannerWebView;
    }

    protected final int a(String str) {
        String str2 = (String) this.a.get(str);
        if (str2 == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str2, 10);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public abstract void a();

    protected final String b(String str) {
        return (String) this.a.get(str);
    }

    protected final boolean c(String str) {
        return "true".equals(this.a.get(str));
    }
}
