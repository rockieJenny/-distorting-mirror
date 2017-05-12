package com.jirbo.adcolony;

import java.util.ArrayList;

class ah {
    d a;
    boolean b = false;
    ArrayList<ag> c = new ArrayList();

    ah(d dVar) {
        this.a = dVar;
    }

    void a() {
        int i = 0;
        c c = k.c(new f("zone_state.txt"));
        if (c != null) {
            this.c.clear();
            for (int i2 = 0; i2 < c.i(); i2++) {
                g b = c.b(i2);
                ag agVar = new ag();
                if (agVar.a(b)) {
                    this.c.add(agVar);
                }
            }
        }
        String[] strArr = this.a.a.k;
        int length = strArr.length;
        while (i < length) {
            a(strArr[i]);
            i++;
        }
    }

    void b() {
        int i = 0;
        l.a.b((Object) "Saving zone state...");
        this.b = false;
        c cVar = new c();
        String[] strArr = this.a.a.k;
        int length = strArr.length;
        while (i < length) {
            cVar.a(a(strArr[i]).a());
            i++;
        }
        k.a(new f("zone_state.txt"), cVar);
        l.a.b((Object) "Saved zone state");
    }

    int c() {
        return this.c.size();
    }

    ag a(int i) {
        return (ag) this.c.get(i);
    }

    ag a(String str) {
        ag agVar;
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            agVar = (ag) this.c.get(i);
            if (agVar.a.equals(str)) {
                return agVar;
            }
        }
        this.b = true;
        agVar = new ag(str);
        this.c.add(agVar);
        return agVar;
    }

    void d() {
        if (this.b) {
            b();
        }
    }
}
