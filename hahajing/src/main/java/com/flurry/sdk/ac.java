package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.NativeAsset;
import com.flurry.android.impl.ads.protocol.v13.NativeAssetType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ac {
    private static final String[] a = new String[0];

    public static List<String> a(ap apVar, int i) {
        if (apVar == null) {
            return Collections.emptyList();
        }
        AdFrame b = apVar.b(i);
        if (b == null) {
            return Collections.emptyList();
        }
        if (ag.STREAM_ONLY.equals(ag.a(b.cachingEnum))) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList();
        String str;
        if (b.cacheWhitelistedAssets != null && b.cacheWhitelistedAssets.size() > 0) {
            for (String str2 : b.cacheWhitelistedAssets) {
                if (!TextUtils.isEmpty(str2)) {
                    arrayList.add(str2);
                }
            }
        } else if (b.binding == 7) {
            for (NativeAsset nativeAsset : apVar.i()) {
                if (NativeAssetType.IMAGE.equals(nativeAsset.type)) {
                    str2 = nativeAsset.value;
                    if (!(TextUtils.isEmpty(str2) || a(b.cacheBlacklistedAssets, str2))) {
                        arrayList.add(str2);
                    }
                }
            }
        } else {
            str2 = b(apVar, i);
            if (!(TextUtils.isEmpty(str2) || a(b.cacheBlacklistedAssets, str2))) {
                arrayList.add(str2);
            }
            str2 = a(b);
            if (!(TextUtils.isEmpty(str2) || a(b.cacheBlacklistedAssets, str2))) {
                arrayList.add(str2);
            }
            for (String a : a) {
                String a2 = a(b, a2);
                if (!(TextUtils.isEmpty(a2) || a(b.cacheBlacklistedAssets, a2))) {
                    arrayList.add(a2);
                }
            }
        }
        return arrayList;
    }

    private static String b(ap apVar, int i) {
        cy d = apVar.d(i);
        if (d != null) {
            return d.f();
        }
        return null;
    }

    private static String a(AdFrame adFrame) {
        if (adFrame == null || adFrame.display == null || adFrame.binding != 3) {
            return null;
        }
        return adFrame.display;
    }

    private static String a(AdFrame adFrame, String str) {
        if (adFrame == null || TextUtils.isEmpty(str) || adFrame.content == null) {
            return null;
        }
        CharSequence a = a(adFrame.content, str);
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        return a;
    }

    private static boolean a(List<String> list, String str) {
        if (list == null) {
            return false;
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String equals : list) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private static String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String str3 = "";
        try {
            String str4 = str;
            for (String str32 : Arrays.asList(str2.split("\\s*-\\s*"))) {
                CharSequence charSequence;
                CharSequence string = new JSONObject(str4).getString(str32);
                if (TextUtils.isEmpty(string)) {
                    Object obj = str4;
                } else {
                    charSequence = string;
                }
                CharSequence charSequence2 = charSequence;
                charSequence = string;
            }
            return str32;
        } catch (JSONException e) {
            return null;
        }
    }
}
