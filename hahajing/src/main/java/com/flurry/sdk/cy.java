package com.flurry.sdk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class cy {
    private int a;
    private List<dg> b;
    private boolean c;
    private boolean d;

    public static class a {
        private cy a = new cy();

        public a a(List<dg> list) {
            this.a.b = list;
            return this;
        }

        public a a(int i) {
            this.a.a = i;
            return this;
        }

        public a a(boolean z) {
            this.a.c = z;
            return this;
        }

        public a a() {
            this.a.d = true;
            return this;
        }

        public cy b() {
            return this.a;
        }
    }

    private cy() {
    }

    public static cy a(cy cyVar, cy cyVar2) {
        if (cyVar == null || cyVar2 == null) {
            return null;
        }
        List a = cyVar.a();
        List a2 = cyVar2.a();
        if (a == null || a.isEmpty() || a2 == null || a2.isEmpty()) {
            return null;
        }
        dg dgVar = (dg) a.get(0);
        dg dgVar2 = (dg) a2.get(0);
        di c = dgVar.c();
        di c2 = dgVar2.c();
        if (c == null || c2 == null) {
            return null;
        }
        if (!db.Wrapper.equals(c.a()) || (!db.InLine.equals(c2.a()) && !db.Wrapper.equals(c2.a()))) {
            return null;
        }
        a2 = c.g();
        Collection g = c2.g();
        if (g == null || g.isEmpty()) {
            return null;
        }
        List arrayList = new ArrayList(1);
        if (a2 == null || a2.isEmpty()) {
            arrayList.addAll(g);
        } else {
            dj djVar = (dj) g.get(0);
            dk d = ((dj) a2.get(0)).d();
            dk d2 = djVar.d();
            if (d == null || d2 == null) {
                return null;
            }
            d = dk.a(d, d2);
            if (d == null) {
                return null;
            }
            com.flurry.sdk.dj.a aVar = new com.flurry.sdk.dj.a();
            aVar.a(djVar.a());
            aVar.a(djVar.b());
            aVar.a(djVar.c());
            aVar.a(d);
            arrayList.add(aVar.a());
        }
        com.flurry.sdk.di.a aVar2 = new com.flurry.sdk.di.a();
        aVar2.a(db.Wrapper);
        aVar2.a(c2.b());
        aVar2.a(c.c());
        List arrayList2 = new ArrayList();
        Collection d3 = c.d();
        if (d3 != null) {
            arrayList2.addAll(d3);
        }
        Collection<String> d4 = c2.d();
        if (d4 != null) {
            for (String contains : d4) {
                if (arrayList2.contains(contains)) {
                    return null;
                }
            }
            arrayList2.addAll(d4);
        }
        aVar2.a(arrayList2);
        a2 = new ArrayList();
        Collection e = c.e();
        if (e != null) {
            a2.addAll(e);
        }
        e = c2.e();
        if (e != null) {
            a2.addAll(e);
        }
        aVar2.b(a2);
        a2 = new ArrayList();
        e = c.f();
        if (e != null) {
            a2.addAll(e);
        }
        e = c2.f();
        if (e != null) {
            a2.addAll(e);
        }
        aVar2.c(a2);
        aVar2.d(arrayList);
        di a3 = aVar2.a();
        com.flurry.sdk.dg.a aVar3 = new com.flurry.sdk.dg.a();
        aVar3.a(dgVar.a());
        aVar3.a(dgVar.b());
        aVar3.a(a3);
        dgVar = aVar3.a();
        a2 = new ArrayList(1);
        a2.add(dgVar);
        a aVar4 = new a();
        aVar4.a(a2);
        aVar4.a(cyVar.b());
        aVar4.a(db.InLine.equals(c2.a()));
        return aVar4.b();
    }

    public List<dg> a() {
        return this.b;
    }

    public int b() {
        return this.a;
    }

    public boolean c() {
        return this.c;
    }

    public boolean d() {
        return this.d;
    }

    public String e() {
        List a = a();
        if (!(a == null || a.isEmpty())) {
            di c = ((dg) a.get(0)).c();
            if (c != null) {
                a = c.d();
                if (!(a == null || a.isEmpty())) {
                    return (String) a.get(a.size() - 1);
                }
            }
        }
        return null;
    }

    public String f() {
        List a = a();
        if (!(a == null || a.isEmpty())) {
            di c = ((dg) a.get(0)).c();
            if (c != null) {
                a = c.g();
                if (!(a == null || a.isEmpty())) {
                    dk d = ((dj) a.get(0)).d();
                    if (d != null) {
                        dl e = d.e();
                        if (!(e == null || e.a() == null)) {
                            return e.a();
                        }
                    }
                }
            }
        }
        return null;
    }

    public List<String> g() {
        List a = a();
        if (!(a == null || a.isEmpty())) {
            di c = ((dg) a.get(0)).c();
            if (c != null) {
                return c.f();
            }
        }
        return null;
    }

    public List<String> h() {
        List a = a();
        if (!(a == null || a.isEmpty())) {
            di c = ((dg) a.get(0)).c();
            if (c != null) {
                return c.e();
            }
        }
        return null;
    }

    public List<String> a(df dfVar) {
        List<String> arrayList = new ArrayList();
        List a = a();
        if (!(a == null || a.isEmpty())) {
            di c = ((dg) a.get(0)).c();
            if (c != null) {
                a = c.g();
                if (!(a == null || a.isEmpty())) {
                    dk d = ((dj) a.get(0)).d();
                    if (d != null) {
                        fu d2 = d.d();
                        if (d2 != null) {
                            arrayList.addAll(d2.a((Object) dfVar));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public String i() {
        List a = a(df.ClickThrough);
        if (a == null || a.size() <= 0) {
            return null;
        }
        return (String) a.get(0);
    }

    public List<String> a(de deVar) {
        List a = a();
        if (!(a == null || a.isEmpty())) {
            di c = ((dg) a.get(0)).c();
            if (c != null) {
                a = c.g();
                if (!(a == null || a.isEmpty())) {
                    dk d = ((dj) a.get(0)).d();
                    if (d != null) {
                        fu c2 = d.c();
                        if (c2 != null) {
                            return c2.a((Object) deVar);
                        }
                    }
                }
            }
        }
        return null;
    }
}
