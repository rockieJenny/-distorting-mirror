package com.appflood.e;

import android.content.Context;
import android.content.Intent;

public final class d implements Runnable {
    private /* synthetic */ Context a;
    private /* synthetic */ Intent b;

    public d(Context context, Intent intent) {
        this.a = context;
        this.b = intent;
    }

    public final void run() {
        try {
            this.a.startActivity(this.b);
        } catch (Throwable th) {
            j.b(th, "startActivity failed");
        }
    }
}
