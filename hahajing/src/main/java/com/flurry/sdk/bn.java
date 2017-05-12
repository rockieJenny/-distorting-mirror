package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class bn implements ef {
    private static final String a = bn.class.getSimpleName();
    private static final Map<String, ef> b = a();

    public ec b(Context context, r rVar) {
        if (context == null || rVar == null) {
            return null;
        }
        if (rVar.k().a() == null) {
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
        ef a = a(str.toUpperCase(Locale.US));
        if (a == null) {
            return null;
        }
        gd.a(3, a, "Creating ad network view for type: " + str);
        ec b = a.b(context, rVar);
        if (b != null) {
            return b;
        }
        gd.b(a, "Cannot create ad network view for type: " + str);
        return null;
    }

    private static Map<String, ef> a() {
        Map hashMap = new HashMap();
        hashMap.put("AdMob".toUpperCase(Locale.US), new bt());
        hashMap.put("Millennial Media".toUpperCase(Locale.US), new bz());
        hashMap.put("InMobi".toUpperCase(Locale.US), new bv());
        hashMap.put("Facebook Audience Network".toUpperCase(Locale.US), new bq());
        return Collections.unmodifiableMap(hashMap);
    }

    private static ef a(String str) {
        return (ef) b.get(str);
    }
}
