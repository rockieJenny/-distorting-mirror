package com.flurry.sdk;

import android.content.Context;
import java.util.Map;

public abstract class bd extends en {
    private static final String b = bd.class.getSimpleName();

    public bd(Context context, r rVar) {
        super(context, rVar);
    }

    public void a(Map<String, String> map) {
        a(aw.EV_RENDERED, map);
    }

    public void b(Map<String, String> map) {
        a(aw.EV_CLICKED, map);
    }

    public void c(Map<String, String> map) {
        a(aw.EV_AD_CLOSED, map);
    }

    public void d(Map<String, String> map) {
        a(aw.EV_RENDER_FAILED, map);
    }

    private void a(aw awVar, Map<String, String> map) {
        r d = d();
        ap k = d.k();
        if (d() != null && k.a() != null) {
            co.a(awVar, map, c(), d, k, 0);
        }
    }
}
