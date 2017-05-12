package com.flurry.sdk;

import java.util.Comparator;

public class ga implements Comparator<Runnable> {
    private static final String a = ga.class.getSimpleName();

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((Runnable) obj, (Runnable) obj2);
    }

    public int a(Runnable runnable, Runnable runnable2) {
        int a = a(runnable);
        int a2 = a(runnable2);
        if (a < a2) {
            return -1;
        }
        if (a > a2) {
            return 1;
        }
        return 0;
    }

    private int a(Runnable runnable) {
        if (runnable == null) {
            return Integer.MAX_VALUE;
        }
        if (runnable instanceof gb) {
            int i;
            hr hrVar = (hr) ((gb) runnable).a();
            if (hrVar != null) {
                i = hrVar.i();
            } else {
                i = Integer.MAX_VALUE;
            }
            return i;
        } else if (runnable instanceof hr) {
            return ((hr) runnable).i();
        } else {
            gd.a(6, a, "Unknown runnable class: " + runnable.getClass().getName());
            return Integer.MAX_VALUE;
        }
    }
}
