package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ej implements eo {
    static final String a = ej.class.getSimpleName();
    private static final Map<String, eo> b = a();

    static final class a implements eo {
        private a() {
        }

        public en a(Context context, r rVar) {
            return new em(context, rVar);
        }
    }

    public en a(Context context, r rVar) {
        if (context == null || rVar == null) {
            return null;
        }
        String a = a(rVar.k().a());
        if (a == null) {
            return null;
        }
        eo a2 = a(a);
        if (a2 == null) {
            gd.e(a, "Cannot create ad takeover for type: " + a);
            return null;
        }
        gd.a(3, a, "Creating ad takeover for type: " + a);
        return a2.a(context, rVar);
    }

    private static Map<String, eo> a() {
        Map hashMap = new HashMap();
        hashMap.put("FLURRY", new a());
        hashMap.put("THIRD_PARTY", new bo());
        return Collections.unmodifiableMap(hashMap);
    }

    private static eo a(String str) {
        return (eo) b.get(str);
    }

    private static String a(AdUnit adUnit) {
        if (adUnit == null) {
            return null;
        }
        List list = adUnit.adFrames;
        if (list == null || list.isEmpty()) {
            return null;
        }
        AdFrame adFrame = (AdFrame) list.get(0);
        if (adFrame == null) {
            return null;
        }
        int i = adFrame.binding;
        if (adUnit.combinable == 1 || i == 2 || i == 1 || i == 3) {
            return "FLURRY";
        }
        return i == 4 ? "THIRD_PARTY" : null;
    }
}
