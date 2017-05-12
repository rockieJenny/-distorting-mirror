package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ei implements ef {
    static final String a = ei.class.getSimpleName();
    private static final Map<String, ef> b = a();

    static final class a implements ef {
        private com.flurry.sdk.ec.a a;

        private a() {
            this.a = new com.flurry.sdk.ec.a(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void a() {
                    this.a.a();
                }

                public void b() {
                    this.a.a();
                }

                public void c() {
                    this.a.a();
                }
            };
        }

        public ec b(Context context, r rVar) {
            return new eb(context, rVar, this.a, false);
        }

        private void a() {
            ea eaVar = new ea();
            eaVar.e = com.flurry.sdk.ea.a.CLOSE_ACTIVITY;
            eaVar.b();
        }
    }

    public ec b(Context context, r rVar) {
        if (context == null || rVar == null) {
            return null;
        }
        String a = a(rVar.k().a());
        if (a == null) {
            return null;
        }
        ef a2 = a(a);
        if (a2 == null) {
            gd.e(a, "Cannot create ad banner for type: " + a);
            return null;
        }
        gd.a(3, a, "Creating ad banner for type: " + a);
        return a2.b(context, rVar);
    }

    private static Map<String, ef> a() {
        Map hashMap = new HashMap();
        hashMap.put("FLURRY", new a());
        hashMap.put("THIRD_PARTY", new bn());
        return Collections.unmodifiableMap(hashMap);
    }

    private static ef a(String str) {
        return (ef) b.get(str);
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
