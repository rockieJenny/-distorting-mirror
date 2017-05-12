package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.flurry.sdk.ec.a;
import java.util.Collections;

public class dp extends dq {
    private static final String b = dp.class.getSimpleName();
    private int c = 0;
    private boolean d = false;
    private float e = 0.0f;
    private float f = 0.0f;

    public dp(Context context, r rVar, a aVar) {
        boolean z = false;
        super(context, rVar, aVar);
        setAutoPlay(rVar.k().a().videoAutoPlay);
        setVideoUri(c(a(rVar.k())));
        if (!TextUtils.isEmpty(b(rVar.k()))) {
            z = true;
        }
        this.d = z;
        this.e = ((float) rVar.k().a().videoPctCompletionForMoreInfo) / 100.0f;
        this.f = ((float) rVar.k().a().videoPctCompletionForReward) / 100.0f;
    }

    private String a(ap apVar) {
        cy g = apVar.g();
        if (g != null) {
            return cv.a(g.f());
        }
        return null;
    }

    private String b(ap apVar) {
        cy g = apVar.g();
        if (g != null) {
            Object i = g.i();
            if (!TextUtils.isEmpty(i)) {
                return cv.a(i);
            }
        }
        return null;
    }

    protected int getViewParams() {
        if (this.c == 0) {
            this.c = getAdController().m().i();
        }
        return this.c;
    }

    public void a(String str, float f, float f2) {
        super.a(str, f, f2);
        if (f2 > 3000.0f) {
            this.c = this.d ? this.c | 4 : this.c;
        }
        if (f2 > 3.0f) {
            this.c |= 2;
            this.c &= -9;
        }
        long j = getAdController().a().closableTimeMillis15SecOrLess;
        if (f > 15000.0f) {
            j = getAdController().a().closableTimeMillisLongerThan15Sec;
        }
        if (f2 > ((float) j)) {
            this.c |= 1;
        }
        dt m = getAdController().m();
        if (this.f > 0.0f && f2 >= this.f * f && !m.g()) {
            g();
            a(aw.EV_REWARD_GRANTED, Collections.emptyMap());
        }
    }

    public void setAutoPlay(boolean z) {
        super.setAutoPlay(z);
        if (getAdController().m().a() <= 3) {
            this.c = z ? this.c : this.c | 8;
        }
    }

    public void b() {
        this.c &= -9;
        super.b();
    }

    public void a(String str) {
        super.a(str);
        if (this.f == 0.0f) {
            a(aw.EV_REWARD_GRANTED, Collections.emptyMap());
        }
    }

    private void g() {
        gd.a(3, b, "Reward granted: ");
        getAdController().m().f(true);
    }
}
