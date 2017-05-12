package com.flurry.sdk;

import android.content.Context;

public final class em extends en {
    public em(Context context, r rVar) {
        super(context, rVar);
    }

    public void a() {
        r d = d();
        if (d.k().r()) {
            a(d, null, true);
        } else {
            cu.a(c(), d, null, true, false);
        }
    }

    private void a(r rVar, String str, boolean z) {
        ea eaVar = new ea();
        eaVar.a = rVar;
        eaVar.b = str;
        eaVar.c = z;
        eaVar.b();
    }
}
