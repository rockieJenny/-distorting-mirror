package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import java.util.Map;

public class b {
    public final aw a;
    public final Map<String, String> b;
    private final Context c;
    private final r d;
    private final ap e;

    public b(aw awVar, Map<String, String> map, Context context, r rVar, ap apVar) {
        this.a = awVar;
        this.b = map;
        this.c = context;
        this.d = rVar;
        this.e = apVar;
    }

    public Context a() {
        return this.c;
    }

    public r b() {
        return this.d;
    }

    public ap c() {
        return this.e;
    }

    public AdFrame d() {
        return this.e.j();
    }

    public AdUnit e() {
        return this.e.a();
    }

    public at f() {
        return this.e.b();
    }

    public static aw a(String str) {
        for (aw awVar : aw.values()) {
            if (awVar.a().equals(str)) {
                return awVar;
            }
        }
        return aw.EV_UNKNOWN;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("event=").append(this.a.toString());
        stringBuilder.append(",params=").append(this.b);
        if (this.e.p() != null) {
            stringBuilder.append(",adspace=").append(this.e.a().adSpace);
        }
        return stringBuilder.toString();
    }
}
