package com.flurry.sdk;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class fz {
    private static fz a = null;
    private static final String b = fz.class.getSimpleName();
    private final fu<String, gm<fy<?>>> c = new fu();
    private final fu<gm<fy<?>>, String> d = new fu();

    public static synchronized fz a() {
        fz fzVar;
        synchronized (fz.class) {
            if (a == null) {
                a = new fz();
            }
            fzVar = a;
        }
        return fzVar;
    }

    public static synchronized void b() {
        synchronized (fz.class) {
            if (a != null) {
                a.c();
                a = null;
            }
        }
    }

    private fz() {
    }

    public synchronized void a(String str, fy<?> fyVar) {
        if (!(TextUtils.isEmpty(str) || fyVar == null)) {
            Object gmVar = new gm(fyVar);
            if (!this.c.c(str, gmVar)) {
                this.c.a((Object) str, gmVar);
                this.d.a(gmVar, (Object) str);
            }
        }
    }

    public synchronized void b(String str, fy<?> fyVar) {
        if (!TextUtils.isEmpty(str)) {
            gm gmVar = new gm(fyVar);
            this.c.b(str, gmVar);
            this.d.b(gmVar, str);
        }
    }

    public synchronized void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (gm b : this.c.a((Object) str)) {
                this.d.b(b, str);
            }
            this.c.b(str);
        }
    }

    public synchronized void a(fy<?> fyVar) {
        if (fyVar != null) {
            Object gmVar = new gm(fyVar);
            for (String b : this.d.a(gmVar)) {
                this.c.b(b, gmVar);
            }
            this.d.b(gmVar);
        }
    }

    public synchronized void c() {
        this.c.a();
        this.d.a();
    }

    public synchronized int b(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else {
            i = this.c.a((Object) str).size();
        }
        return i;
    }

    public synchronized List<fy<?>> c(String str) {
        List<fy<?>> emptyList;
        if (TextUtils.isEmpty(str)) {
            emptyList = Collections.emptyList();
        } else {
            List<fy<?>> arrayList = new ArrayList();
            Iterator it = this.c.a((Object) str).iterator();
            while (it.hasNext()) {
                fy fyVar = (fy) ((gm) it.next()).get();
                if (fyVar == null) {
                    it.remove();
                } else {
                    arrayList.add(fyVar);
                }
            }
            emptyList = arrayList;
        }
        return emptyList;
    }

    public void a(final fx fxVar) {
        if (fxVar != null) {
            for (final fy fyVar : c(fxVar.a())) {
                fp.a().b(new hq(this) {
                    final /* synthetic */ fz c;

                    public void safeRun() {
                        fyVar.notify(fxVar);
                    }
                });
            }
        }
    }
}
