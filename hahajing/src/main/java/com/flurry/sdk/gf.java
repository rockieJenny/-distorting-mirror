package com.flurry.sdk;

import android.content.Context;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class gf {
    private static final String a = gf.class.getSimpleName();
    private static final Map<Class<? extends gg>, ge> b = new LinkedHashMap();
    private final Map<Class<? extends gg>, gg> c = new LinkedHashMap();

    public static void a(Class<? extends gg> cls, int i) {
        if (cls != null) {
            synchronized (b) {
                b.put(cls, new ge(cls, i));
            }
        }
    }

    public synchronized void a(Context context) {
        synchronized (b) {
            List<ge> arrayList = new ArrayList(b.values());
        }
        for (ge geVar : arrayList) {
            try {
                if (geVar.b()) {
                    gg ggVar = (gg) geVar.a().newInstance();
                    ggVar.a(context);
                    this.c.put(geVar.a(), ggVar);
                }
            } catch (Throwable e) {
                gd.a(5, a, "Flurry Module for class " + geVar.a() + " is not available:", e);
            }
        }
        hc.a().a(context);
        ft.a();
    }

    public synchronized void a() {
        ft.b();
        hc.b();
        List b = b();
        for (int size = b.size() - 1; size >= 0; size--) {
            try {
                ((gg) this.c.remove(((gg) b.get(size)).getClass())).b();
            } catch (Throwable e) {
                gd.a(5, a, "Error destroying module:", e);
            }
        }
    }

    public gg a(Class<? extends gg> cls) {
        if (cls == null) {
            return null;
        }
        synchronized (this.c) {
            gg ggVar = (gg) this.c.get(cls);
        }
        if (ggVar != null) {
            return ggVar;
        }
        throw new IllegalStateException("Module was not registered/initialized. " + cls);
    }

    private List<gg> b() {
        List<gg> arrayList = new ArrayList();
        synchronized (this.c) {
            arrayList.addAll(this.c.values());
        }
        return arrayList;
    }
}
