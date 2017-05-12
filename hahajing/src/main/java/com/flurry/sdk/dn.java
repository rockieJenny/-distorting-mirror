package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.ec.a;
import java.util.Collections;

public class dn extends dq {
    private int b = 0;

    public dn(Context context, r rVar, a aVar) {
        super(context, rVar, aVar);
        setAutoPlay(rVar.k().a().videoAutoPlay);
        setVideoUri(c(a(rVar.k())));
    }

    private String a(ap apVar) {
        return apVar.j().display;
    }

    protected int getViewParams() {
        if (this.b == 0) {
            this.b = getAdController().m().i();
        }
        return this.b;
    }

    public void a() {
        a(aw.EV_AD_WILL_CLOSE, Collections.emptyMap());
    }

    public void setAutoPlay(boolean z) {
        super.setAutoPlay(z);
        if (getAdController().m().a() <= 3) {
            this.b = z ? this.b : this.b | 8;
        }
    }

    public void b() {
        this.b &= -9;
        super.b();
    }

    public void a(String str, float f, float f2) {
        super.a(str, f, f2);
        if (f2 > 3.0f) {
            this.b |= 2;
            this.b &= -9;
        }
        long j = getAdController().a().closableTimeMillis15SecOrLess;
        if (f > 15000.0f) {
            j = getAdController().a().closableTimeMillisLongerThan15Sec;
        }
        if (f2 > ((float) j)) {
            this.b |= 1;
        }
    }
}
