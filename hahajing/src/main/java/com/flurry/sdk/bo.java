package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class bo implements eo {
    private static final String a = bo.class.getSimpleName();
    private static final Map<String, eo> b = a();

    public en a(Context context, r rVar) {
        if (context == null || rVar == null) {
            return null;
        }
        if (rVar.k().a() == null || rVar.k().b() == null) {
            return null;
        }
        List list = rVar.k().a().adFrames;
        if (list == null || list.isEmpty()) {
            return null;
        }
        AdFrame adFrame = (AdFrame) list.get(0);
        if (adFrame == null) {
            return null;
        }
        String str = adFrame.content;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        eo a = a(str.toUpperCase(Locale.US));
        if (a == null) {
            return null;
        }
        gd.a(3, a, "Creating ad network takeover launcher: " + a.getClass().getSimpleName() + " for type: " + str);
        en a2 = a.a(context, rVar);
        if (a2 != null) {
            return a2;
        }
        gd.b(a, "Cannot create ad network takeover launcher for type: " + str);
        return a2;
    }

    private static Map<String, eo> a() {
        Map hashMap = new HashMap();
        hashMap.put("AdMob".toUpperCase(Locale.US), new bt());
        hashMap.put("Millennial Media".toUpperCase(Locale.US), new bz());
        hashMap.put("InMobi".toUpperCase(Locale.US), new bv());
        hashMap.put("Facebook Audience Network".toUpperCase(Locale.US), new bq());
        return Collections.unmodifiableMap(hashMap);
    }

    private static eo a(String str) {
        return (eo) b.get(str);
    }
}
