package com.flurry.sdk;

import android.content.Context;
import java.util.Map;

public class co {
    public static void a(aw awVar, Map<String, String> map, Context context, r rVar, ap apVar, int i) {
        if (context != null && rVar != null && apVar != null) {
            b bVar = new b(awVar, map, context, rVar, apVar);
            c cVar = new c();
            cVar.a = bVar;
            cVar.b = i;
            cVar.b();
        }
    }
}
