package com.google.ads.mediation.flurry.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.android.FlurryAgent;
import java.util.WeakHashMap;

public final class e {
    private static e a;
    private final WeakHashMap<Context, Boolean> b = new WeakHashMap();

    public static synchronized e a() {
        e eVar;
        synchronized (e.class) {
            if (a == null) {
                a = new e();
            }
            eVar = a;
        }
        return eVar;
    }

    private e() {
        FlurryAgent.addOrigin("Flurry_DFP_Android", "5.0.0.r1");
    }

    public synchronized void a(Context context, String str) {
        if (context != null) {
            if (!TextUtils.isEmpty(str)) {
                FlurryAgent.init(context, str);
                if (VERSION.SDK_INT < 14 && this.b.get(context) == null) {
                    this.b.put(context, Boolean.valueOf(true));
                    FlurryAgent.onStartSession(context);
                }
            }
        }
    }

    public synchronized void a(Context context) {
        if (context != null) {
            if (VERSION.SDK_INT < 14 && this.b.get(context) != null) {
                this.b.remove(context);
                FlurryAgent.onEndSession(context);
            }
        }
    }
}
