package com.flurry.sdk;

public class dk {
    private int a;
    private int b;
    private fu<de, String> c;
    private fu<df, String> d;
    private dl e;

    public static class a {
        private dk a = new dk();

        public a a(int i) {
            this.a.a = i;
            return this;
        }

        public a b(int i) {
            this.a.b = i;
            return this;
        }

        public a a(fu<de, String> fuVar) {
            this.a.c = fuVar;
            return this;
        }

        public a b(fu<df, String> fuVar) {
            this.a.d = fuVar;
            return this;
        }

        public a a(dl dlVar) {
            this.a.e = dlVar;
            return this;
        }

        public dk a() {
            return this.a;
        }
    }

    private dk() {
    }

    public static dk a(dk dkVar, dk dkVar2) {
        if (dkVar == null || dkVar2 == null) {
            return null;
        }
        fu fuVar = new fu();
        fu c = dkVar.c();
        if (c != null) {
            fuVar.a(c);
        }
        c = dkVar2.c();
        if (c != null) {
            fuVar.a(c);
        }
        c = new fu();
        fu d = dkVar.d();
        if (d != null) {
            c.a(d);
        }
        d = dkVar2.d();
        if (d != null) {
            c.a(d);
        }
        a aVar = new a();
        aVar.a(dkVar2.a());
        aVar.b(dkVar2.b());
        aVar.a(fuVar);
        aVar.b(c);
        aVar.a(dkVar2.e());
        return aVar.a();
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public fu<de, String> c() {
        return this.c;
    }

    public fu<df, String> d() {
        return this.d;
    }

    public dl e() {
        return this.e;
    }
}
