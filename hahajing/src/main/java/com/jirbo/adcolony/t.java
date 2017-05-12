package com.jirbo.adcolony;

import java.util.ArrayList;
import java.util.HashMap;

class t {
    d a;
    ArrayList<a> b = new ArrayList();
    HashMap<Integer, Integer> c = new HashMap();
    HashMap<String, Integer> d = new HashMap();
    boolean e = false;
    boolean f = false;

    static class a {
        String a;
        int b;
        String c;
        double d;

        a(String str, double d, String str2, int i) {
            this.c = str;
            this.d = d;
            this.a = str2;
            this.b = i;
        }
    }

    t(d dVar) {
        this.a = dVar;
    }

    void a() {
        b();
        this.e = false;
    }

    void a(String str, int i) {
        l.a.b((Object) "Adding play event to play history");
        this.e = true;
        this.b.add(new a(this.a.e.j, ab.c(), str, i));
        Object obj = (Integer) this.c.get(Integer.valueOf(i));
        l.a.a("Got play count of ").a(obj).b((Object) " for this ad");
        if (obj == null) {
            this.c.put(Integer.valueOf(i), Integer.valueOf(1));
        } else {
            this.c.put(Integer.valueOf(i), Integer.valueOf(obj.intValue() + 1));
        }
    }

    synchronized int a(String str) {
        int i = 0;
        synchronized (this) {
            String str2 = this.a.e.j;
            int size = this.b.size() - 1;
            int i2 = 0;
            while (size >= 0 && this.b.get(size) != null && ((a) this.b.get(size)).c != null) {
                int i3;
                if (((a) this.b.get(size)).c.equals(str2)) {
                    i2 = 1;
                    if (((a) this.b.get(size)).a.equals(str)) {
                        i3 = i + 1;
                        i = 1;
                    }
                    i3 = i;
                    i = i2;
                } else {
                    if (i2 != 0) {
                        break;
                    }
                    i3 = i;
                    i = i2;
                }
                size--;
                i2 = i;
                i = i3;
            }
        }
        return i;
    }

    synchronized int a(String str, double d) {
        int i;
        double c = ab.c() - d;
        i = 0;
        int size = this.b.size() - 1;
        while (size >= 0 && ((a) this.b.get(size)).d >= c) {
            int i2;
            if (str.equals(((a) this.b.get(size)).a)) {
                i2 = i + 1;
            } else {
                i2 = i;
            }
            size--;
            i = i2;
        }
        return i;
    }

    int b(String str) {
        return a(str, 86400.0d);
    }

    synchronized int a(int i, double d) {
        int i2;
        double c = ab.c() - d;
        i2 = 0;
        int size = this.b.size() - 1;
        while (size >= 0 && ((a) this.b.get(size)).d >= c) {
            int i3;
            if (i == ((a) this.b.get(size)).b) {
                i3 = i2 + 1;
            } else {
                i3 = i2;
            }
            size--;
            i2 = i3;
        }
        return i2;
    }

    int c(String str) {
        Integer num = (Integer) this.d.get(str);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    void b(String str, int i) {
        this.d.put(str, Integer.valueOf(i));
        this.e = true;
    }

    int a(int i) {
        return a(i, 86400.0d);
    }

    int b(int i) {
        return a(i, 604800.0d);
    }

    int c(int i) {
        return a(i, 2628000.0d);
    }

    int d(int i) {
        return a(i, 1.5768E7d);
    }

    void b() {
        int i = 0;
        a.r = true;
        if (!this.f || ((a) this.b.get(this.b.size() - 1)).c != this.a.e.j) {
            g b = k.b(new f("play_history_info.txt"));
            if (b != null) {
                g b2;
                this.b.clear();
                this.d = new HashMap();
                g b3 = b.b("reward_credit");
                for (int i2 = 0; i2 < b3.o(); i2++) {
                    String a = b3.a(i2);
                    this.d.put(a, Integer.valueOf(b3.g(a)));
                }
                c c = b.c("play_events");
                for (int i3 = 0; i3 < c.i(); i3++) {
                    b2 = c.b(i3);
                    double f = b2.f("timestamp");
                    String e = b2.e("zone_id");
                    int g = b2.g("ad_id");
                    if (!(f == 0.0d || e == null || g == 0)) {
                        this.b.add(new a(null, f, e, g));
                    }
                }
                this.c = new HashMap();
                b2 = b.b("play_counts");
                while (i < b2.o()) {
                    int parseInt = Integer.parseInt(b2.a(i));
                    this.c.put(Integer.valueOf(parseInt), Integer.valueOf(b2.g("" + parseInt)));
                    i++;
                }
                this.f = true;
            }
        }
    }

    void c() {
        i cVar = new c();
        g gVar = new g();
        i gVar2 = new g();
        double c = ab.c() - 2678400.0d;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            a aVar = (a) this.b.get(size);
            if (aVar.d < c) {
                break;
            }
            i gVar3 = new g();
            gVar3.b("zone_id", aVar.a);
            gVar3.b("ad_id", aVar.b);
            gVar3.b("timestamp", aVar.d);
            cVar.a(gVar3);
        }
        gVar.a("play_events", cVar);
        for (Integer intValue : this.c.keySet()) {
            int intValue2 = intValue.intValue();
            gVar2.b("" + intValue2, ((Integer) this.c.get(Integer.valueOf(intValue2))).intValue());
        }
        gVar.a("play_counts", gVar2);
        cVar = new g();
        if (this.d.size() > 0) {
            for (String str : this.d.keySet()) {
                cVar.b(str, ((Integer) this.d.get(str)).intValue());
            }
        }
        gVar.a("reward_credit", cVar);
        l.a.a("Saving play history");
        k.a(new f("play_history_info.txt"), gVar);
    }

    void d() {
        if (this.e) {
            this.e = false;
            c();
        }
    }
}
