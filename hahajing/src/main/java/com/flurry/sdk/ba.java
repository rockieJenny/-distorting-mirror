package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapType;
import java.util.ArrayList;
import java.util.List;

public final class ba {
    private static final String a = ba.class.getSimpleName();
    private final fu<String, az> b = new fu();

    public synchronized void a(az azVar) {
        if (azVar != null) {
            if (!(azVar.b() == null || TextUtils.isEmpty(azVar.c()))) {
                a(azVar.b(), azVar.c());
                if (azVar.g() != -1) {
                    this.b.a(azVar.c(), (Object) azVar);
                }
            }
        }
    }

    public synchronized void a(FrequencyCapType frequencyCapType, String str) {
        if (frequencyCapType != null) {
            if (!TextUtils.isEmpty(str)) {
                for (Object obj : this.b.a((Object) str)) {
                    if (obj.b().equals(frequencyCapType)) {
                        break;
                    }
                }
                Object obj2 = null;
                if (obj2 != null) {
                    this.b.b(str, obj2);
                }
            }
        }
    }

    public synchronized List<az> a(String str) {
        return new ArrayList(this.b.a((Object) str));
    }

    public synchronized void b(String str) {
        this.b.b(str);
    }

    public synchronized List<az> a() {
        return new ArrayList(this.b.d());
    }

    public synchronized void b() {
        for (az azVar : a()) {
            if (a(azVar.e())) {
                gd.a(3, a, "expiring freq cap for id: " + azVar.c() + " capType:" + azVar.b() + " expiration: " + azVar.e() + " epoch" + System.currentTimeMillis());
                b(azVar.c());
            }
        }
    }

    public boolean a(long j) {
        if (j <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
