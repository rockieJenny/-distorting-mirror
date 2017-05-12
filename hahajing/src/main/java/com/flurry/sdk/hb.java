package com.flurry.sdk;

import android.content.Context;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class hb {
    private static final List<Class<? extends hf>> b = new ArrayList();
    private final String a = hb.class.getSimpleName();
    private final Map<Class<? extends hf>, hf> c = new LinkedHashMap();

    public static void a(Class<? extends hf> cls) {
        if (cls != null) {
            synchronized (b) {
                b.add(cls);
            }
        }
    }

    public static void b(Class<? extends hf> cls) {
        if (cls != null) {
            synchronized (b) {
                b.remove(cls);
            }
        }
    }

    public hb() {
        synchronized (b) {
            List<Class> arrayList = new ArrayList(b);
        }
        for (Class cls : arrayList) {
            try {
                hf hfVar = (hf) cls.newInstance();
                synchronized (this.c) {
                    this.c.put(cls, hfVar);
                }
            } catch (Throwable e) {
                gd.a(5, this.a, "Module session " + cls + " is not available:", e);
            }
        }
    }

    public hf c(Class<? extends hf> cls) {
        if (cls == null) {
            return null;
        }
        hf hfVar;
        synchronized (this.c) {
            hfVar = (hf) this.c.get(cls);
        }
        return hfVar;
    }

    public synchronized void a(Context context) {
        for (hf a : b()) {
            a.a(context);
        }
    }

    public synchronized void b(Context context) {
        for (hf b : b()) {
            b.b(context);
        }
    }

    public synchronized void c(Context context) {
        for (hf c : b()) {
            c.c(context);
        }
    }

    public synchronized void a() {
        for (hf a : b()) {
            a.a();
        }
    }

    private List<hf> b() {
        List<hf> arrayList = new ArrayList();
        synchronized (this.c) {
            arrayList.addAll(this.c.values());
        }
        return arrayList;
    }
}
