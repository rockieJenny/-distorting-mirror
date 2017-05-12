package com.appflood.e;

import android.net.Uri;
import com.appflood.c.d;
import com.appflood.e.j.b;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class k {
    private static final byte[] a = new byte[8];
    private static final byte[] b = new byte[8];
    private static final b c = new b("papaya social 1.5", a);

    static {
        for (int i = 0; i < 8; i++) {
            a[i] = (byte) 0;
            b[i] = (byte) 0;
        }
        b bVar = new b("papaya social 1.5", b);
    }

    public static String a(String str, Map<String, Object> map) {
        StringBuilder append = new StringBuilder(str.length()).append(str);
        if (!(map == null || map.isEmpty())) {
            if (str.contains("?")) {
                append.append('&');
            } else {
                append.append('?');
            }
            Set<Entry> entrySet = map.entrySet();
            int i = 0;
            for (Entry entry : entrySet) {
                int i2 = i + 1;
                append.append((String) entry.getKey()).append('=').append(entry.getValue() == null ? "" : Uri.encode(entry.getValue().toString()));
                if (i2 < entrySet.size()) {
                    append.append('&');
                }
                i = i2;
            }
        }
        return new String(append);
    }

    public static URL a(String str) {
        return a(str.replace("http://data.appflood.com/", d.g), d.h);
    }

    private static URL a(String str, URL url) {
        if (str == null) {
            return url;
        }
        URL url2;
        if (url == null) {
            try {
                url2 = new URL(str);
            } catch (Throwable th) {
                j.a(th, "failed to makeURL " + str + " " + url);
                return null;
            }
        }
        url2 = new URL(url, str);
        return url2;
    }

    public static boolean a(URL url, URL url2) {
        return url == url2 ? true : (url == null || url2 == null) ? false : url.toString().equals(url2.toString());
    }

    public static URL b(String str, Map<String, Object> map) {
        Map hashMap;
        if (map == null) {
            hashMap = new HashMap();
        }
        if (!(j.g(d.w) || str.contains("tkn=" + d.w))) {
            hashMap.put("tkn", d.w);
        }
        return a(a(str, hashMap));
    }

    public static boolean b(String str) {
        return !str.startsWith("http:");
    }

    public static void c(String str) {
        "trackingEvent  url = " + str;
        j.a();
        if (!j.g(str)) {
            new com.appflood.b.b(str).f();
        }
    }

    public static String d(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        String a;
        synchronized (c) {
            a = c.a(str);
        }
        return a;
    }
}
