package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.Iterator;
import java.util.List;

public class v {
    private final SparseArray<u> a = new SparseArray();
    private final fu<String, u> b = new fu();

    public synchronized void a(Context context, String str, u uVar) {
        if (context != null) {
            if (!(TextUtils.isEmpty(str) || uVar == null)) {
                this.a.put(uVar.d(), uVar);
                this.b.a((Object) str, (Object) uVar);
            }
        }
    }

    public synchronized boolean a(String str, u uVar) {
        boolean z;
        if (TextUtils.isEmpty(str) || uVar == null) {
            z = false;
        } else {
            this.a.remove(uVar.d());
            z = this.b.b(str, uVar);
        }
        return z;
    }

    public synchronized u a(int i) {
        return (u) this.a.get(i);
    }

    public synchronized u a(String str) {
        u uVar = null;
        synchronized (this) {
            if (!TextUtils.isEmpty(str)) {
                List a = this.b.a((Object) str);
                if (!a.isEmpty()) {
                    uVar = (u) a.get(a.size() - 1);
                }
            }
        }
        return uVar;
    }

    public synchronized u a(Context context, String str) {
        u uVar;
        if (context != null) {
            if (!TextUtils.isEmpty(str)) {
                for (u uVar2 : this.b.a((Object) str)) {
                    if (context.equals(uVar2.e())) {
                        break;
                    }
                }
                uVar2 = null;
                if (r0 != null) {
                    this.b.b(str, r0);
                    this.b.a((Object) str, r0);
                }
            }
        }
        uVar2 = null;
        return uVar2;
    }

    public synchronized void b(Context context, String str) {
        if (context != null) {
            if (!TextUtils.isEmpty(str)) {
                u a = a(context, str);
                if (a != null) {
                    this.b.b(str, a);
                    a.a();
                }
            }
        }
    }

    public synchronized void a(Context context) {
        if (context != null) {
            for (u uVar : this.b.d()) {
                if (context.equals(uVar.e())) {
                    uVar.b();
                }
            }
        }
    }

    public synchronized void b(Context context) {
        if (context != null) {
            for (u uVar : this.b.d()) {
                if (context.equals(uVar.e())) {
                    uVar.c();
                }
            }
        }
    }

    public synchronized void c(Context context) {
        if (context != null) {
            Iterator it = this.b.d().iterator();
            while (it.hasNext()) {
                u uVar = (u) it.next();
                if (context.equals(uVar.e())) {
                    it.remove();
                    uVar.a();
                }
            }
        }
    }

    public synchronized void a() {
        for (u m : this.b.d()) {
            m.m();
        }
    }
}
