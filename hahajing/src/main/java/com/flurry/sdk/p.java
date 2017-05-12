package com.flurry.sdk;

import android.content.Context;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

public class p {
    private final SparseArray<r> a = new SparseArray();
    private final fu<Context, r> b = new fu(new WeakHashMap());

    public synchronized void a(Context context, r rVar) {
        if (!(context == null || rVar == null)) {
            this.a.put(rVar.d(), rVar);
            this.b.a((Object) context, (Object) rVar);
        }
    }

    public synchronized boolean b(Context context, r rVar) {
        boolean z;
        if (context == null || rVar == null) {
            z = false;
        } else {
            this.a.remove(rVar.d());
            z = this.b.b(context, rVar);
        }
        return z;
    }

    public synchronized r a(int i) {
        return (r) this.a.get(i);
    }

    public synchronized List<r> a(Context context) {
        List<r> emptyList;
        if (context == null) {
            emptyList = Collections.emptyList();
        } else {
            emptyList = new ArrayList(this.b.a((Object) context));
        }
        return emptyList;
    }

    public synchronized void b(Context context) {
        if (context != null) {
            for (r b : this.b.a((Object) context)) {
                b.b();
            }
        }
    }

    public synchronized void c(Context context) {
        if (context != null) {
            for (r c : this.b.a((Object) context)) {
                c.c();
            }
        }
    }

    public synchronized void d(Context context) {
        if (context != null) {
            for (r a : a(context)) {
                a.a();
            }
        }
    }

    public synchronized void a() {
        for (r m : this.b.d()) {
            m.m();
        }
    }
}
