package com.flurry.sdk;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import org.apache.http.client.utils.URIUtils;

public class cv {
    private static final Pattern a = Pattern.compile("/");

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        URI h = h(str);
        if (h == null) {
            return str;
        }
        String scheme = h.getScheme();
        if (TextUtils.isEmpty(scheme)) {
            return "http://" + str;
        }
        String toLowerCase = scheme.toLowerCase();
        if (scheme == null || scheme.equals(toLowerCase)) {
            return str;
        }
        int indexOf = str.indexOf(scheme);
        return indexOf >= 0 ? toLowerCase + str.substring(scheme.length() + indexOf) : str;
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        URI h = h(str);
        if (h == null) {
            return str;
        }
        h = h.normalize();
        if (h.isOpaque()) {
            return str;
        }
        h = a(h.getScheme(), h.getAuthority(), "/", null, null);
        if (h != null) {
            return h.toString();
        }
        return str;
    }

    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        URI h = h(str);
        if (h == null) {
            return str;
        }
        h = h.normalize();
        if (h.isOpaque()) {
            return str;
        }
        h = URIUtils.resolve(h, "./");
        if (h != null) {
            return h.toString();
        }
        return str;
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return str;
        }
        URI h = h(str);
        if (h == null) {
            return str;
        }
        URI normalize = h.normalize();
        h = h(str2);
        if (h == null) {
            return str;
        }
        URI normalize2 = h.normalize();
        if (normalize.isOpaque() || normalize2.isOpaque()) {
            return str;
        }
        String scheme = normalize.getScheme();
        String scheme2 = normalize2.getScheme();
        if (scheme2 == null && scheme != null) {
            return str;
        }
        if (scheme2 != null && !scheme2.equals(scheme)) {
            return str;
        }
        scheme = normalize.getAuthority();
        scheme2 = normalize2.getAuthority();
        if (scheme2 == null && scheme != null) {
            return str;
        }
        if (scheme2 != null && !scheme2.equals(scheme)) {
            return str;
        }
        CharSequence path = normalize.getPath();
        CharSequence path2 = normalize2.getPath();
        String[] split = a.split(path, -1);
        String[] split2 = a.split(path2, -1);
        int length = split.length;
        int length2 = split2.length;
        int i = 0;
        while (i < length2 && i < length && split[i].equals(split2[i])) {
            i++;
        }
        scheme2 = normalize.getQuery();
        String fragment = normalize.getFragment();
        StringBuilder stringBuilder = new StringBuilder();
        if (i == length2 && i == length) {
            path = normalize2.getQuery();
            CharSequence fragment2 = normalize2.getFragment();
            boolean equals = TextUtils.equals(scheme2, path);
            boolean equals2 = TextUtils.equals(fragment, fragment2);
            if (equals && equals2) {
                scheme = null;
                scheme2 = null;
            } else {
                if (!equals || TextUtils.isEmpty(fragment)) {
                    Object obj = scheme2;
                } else {
                    path = null;
                }
                if (TextUtils.isEmpty(path) && TextUtils.isEmpty(fragment)) {
                    stringBuilder.append(split[length - 1]);
                    scheme = fragment;
                } else {
                    path2 = path;
                    scheme = fragment;
                }
            }
            fragment = scheme;
        } else {
            int i2 = length - 1;
            length2--;
            for (int i3 = i; i3 < length2; i3++) {
                stringBuilder.append("..");
                stringBuilder.append("/");
            }
            while (i < i2) {
                stringBuilder.append(split[i]);
                stringBuilder.append("/");
                i++;
            }
            if (i < length) {
                stringBuilder.append(split[i]);
            }
            if (stringBuilder.length() == 0) {
                stringBuilder.append(".");
                stringBuilder.append("/");
            }
        }
        h = a(null, null, stringBuilder.toString(), scheme2, fragment);
        if (h != null) {
            return h.toString();
        }
        return str;
    }

    private static URI h(String str) {
        try {
            return new URI(str);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private static URI a(String str, String str2, String str3, String str4, String str5) {
        try {
            return new URI(str, str2, str3, str4, str5);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static boolean d(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (parse.getScheme() == null || !parse.getScheme().equals(Event.INTENT_MARKET)) {
            return false;
        }
        return true;
    }

    public static boolean e(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (parse.getScheme() == null) {
            return false;
        }
        if (parse.getScheme().equals("http://") || parse.getScheme().equals("https://")) {
            return true;
        }
        return false;
    }

    public static boolean f(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (parse.getHost() == null || !parse.getHost().equals("play.google.com") || parse.getScheme() == null || !parse.getScheme().startsWith("http")) {
            return false;
        }
        return true;
    }

    public static boolean g(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str));
        if (mimeTypeFromExtension == null || !mimeTypeFromExtension.startsWith("video/")) {
            return false;
        }
        return true;
    }

    public static String b(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (!(new URI(str).isAbsolute() || TextUtils.isEmpty(str2))) {
                    URI uri = new URI(str2);
                    str = uri.getScheme() + "://" + uri.getHost() + str;
                }
            } catch (Exception e) {
            }
        }
        return str;
    }
}
