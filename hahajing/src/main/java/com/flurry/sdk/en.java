package com.flurry.sdk;

import android.content.Context;
import java.lang.ref.WeakReference;

public abstract class en {
    static final String a = en.class.getSimpleName();
    private final WeakReference<Context> b;
    private final r c;

    public abstract void a();

    public en(Context context, r rVar) {
        this.b = new WeakReference(context);
        this.c = rVar;
    }

    public Context c() {
        return (Context) this.b.get();
    }

    public r d() {
        return this.c;
    }
}
