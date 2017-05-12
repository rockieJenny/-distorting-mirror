package com.appflood.mraid;

import java.util.HashMap;
import java.util.Map;

public final class i {
    private static Map<String, a> a;

    interface a {
        d a(Map<String, String> map, AFBannerWebView aFBannerWebView);
    }

    static {
        Map hashMap = new HashMap();
        a = hashMap;
        hashMap.put("close", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new f(map, aFBannerWebView);
            }
        });
        a.put("expand", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new g(map, aFBannerWebView);
            }
        });
        a.put("resize", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new j(map, aFBannerWebView);
            }
        });
        a.put("usecustomclose", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new l(map, aFBannerWebView);
            }
        });
        a.put("open", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new h(map, aFBannerWebView);
            }
        });
        a.put("playVideo", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new v(map, aFBannerWebView);
            }
        });
        a.put("storePicture", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new k(map, aFBannerWebView);
            }
        });
        a.put("createCalendarEvent", new a() {
            public final d a(Map<String, String> map, AFBannerWebView aFBannerWebView) {
                return new e(map, aFBannerWebView);
            }
        });
    }

    public static d a(String str, Map<String, String> map, AFBannerWebView aFBannerWebView) {
        a aVar = (a) a.get(str);
        return aVar != null ? aVar.a(map, aFBannerWebView) : null;
    }
}
