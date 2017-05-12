package com.flurry.sdk;

import java.util.Map.Entry;
import java.util.TreeMap;

public class f {
    private static f a;
    private static final String b = f.class.getSimpleName();
    private final TreeMap<String, Integer> c = new TreeMap();

    public static synchronized f a() {
        f fVar;
        synchronized (f.class) {
            if (a == null) {
                a = new f();
            }
            fVar = a;
        }
        return fVar;
    }

    public void a(String str, int i) {
        synchronized (this.c) {
            Integer num = (Integer) this.c.get(str);
            TreeMap treeMap = this.c;
            if (num != null) {
                i += num.intValue();
            }
            treeMap.put(str, Integer.valueOf(i));
        }
    }

    public void b() {
        gd.a(3, b, "========== PRINT COUNTERS ==========");
        synchronized (this.c) {
            for (Entry entry : this.c.entrySet()) {
                gd.a(3, b, ((String) entry.getKey()) + " " + entry.getValue());
            }
        }
        gd.a(3, b, "========== END PRINT COUNTERS ==========");
    }
}
