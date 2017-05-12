package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.ec.a;

public class do extends dq {
    private int b = 0;

    public do(Context context, r rVar, a aVar) {
        super(context, rVar, aVar);
        setAutoPlay(rVar.k().a().videoAutoPlay);
    }

    protected int getViewParams() {
        if (this.b == 0) {
            this.b = getAdController().m().i();
        }
        return this.b;
    }

    public void setAutoPlay(boolean z) {
        super.setAutoPlay(true);
    }

    public void a(String str, float f, float f2) {
        super.a(str, f, f2);
        if (f2 > 0.0f) {
            this.b |= 1;
        }
    }
}
