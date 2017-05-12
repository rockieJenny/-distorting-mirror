package com.jirbo.adcolony;

class k {
    static z a = new z();

    k() {
    }

    static void a(f fVar, i iVar) {
        af a = fVar.a();
        if (iVar == null) {
            a.b("null");
        } else {
            iVar.a(a);
            a.d();
        }
        a.b();
    }

    static void a(f fVar, g gVar) {
        af a = fVar.a();
        if (gVar != null) {
            gVar.a(a);
            a.d();
        } else {
            l.b.b((Object) "Saving empty property table.");
            a.b("{}");
        }
        a.b();
    }

    static void a(f fVar, c cVar) {
        af a = fVar.a();
        if (cVar != null) {
            cVar.a(a);
            a.d();
        } else {
            l.b.b((Object) "Saving empty property list.");
            a.b("[]");
        }
        a.b();
    }

    static i a(f fVar) {
        s b = fVar.b();
        if (b == null) {
            return null;
        }
        return a(b);
    }

    static g b(f fVar) {
        i a = a(fVar);
        if (a == null || !a.m()) {
            return null;
        }
        return a.n();
    }

    static c c(f fVar) {
        i a = a(fVar);
        if (a == null || !a.f()) {
            return null;
        }
        return a.h();
    }

    static i a(String str) {
        if (str == null) {
            return null;
        }
        return a(new s(str));
    }

    static g b(String str) {
        i a = a(str);
        if (a == null || !a.m()) {
            return null;
        }
        return a.n();
    }

    static c c(String str) {
        i a = a(str);
        if (a == null || !a.f()) {
            return null;
        }
        return a.h();
    }

    static i a(s sVar) {
        b(sVar);
        char b = sVar.b();
        if (b == '{') {
            return c(sVar);
        }
        if (b == '[') {
            return d(sVar);
        }
        if (b == '-') {
            return h(sVar);
        }
        if (b >= '0' && b <= '9') {
            return h(sVar);
        }
        String e;
        if (b == '\"' || b == '\'') {
            e = e(sVar);
            if (e.length() == 0) {
                return new f("");
            }
            b = e.charAt(0);
            if (b == 't' && e.equals("true")) {
                return ADCData.a;
            }
            if (b == 'f' && e.equals("false")) {
                return ADCData.b;
            }
            if (b == 'n' && e.equals("null")) {
                return ADCData.c;
            }
            return new f(e);
        } else if ((b < 'a' || b > 'z') && ((b < 'A' || b > 'Z') && b != '_' && b != '$')) {
            return null;
        } else {
            e = g(sVar);
            if (e.length() == 0) {
                return new f("");
            }
            b = e.charAt(0);
            if (b == 't' && e.equals("true")) {
                return ADCData.a;
            }
            if (b == 'f' && e.equals("false")) {
                return ADCData.b;
            }
            if (b == 'n' && e.equals("null")) {
                return ADCData.c;
            }
            return new f(e);
        }
    }

    static void b(s sVar) {
        char b = sVar.b();
        while (sVar.a()) {
            if (b <= ' ' || b > '~') {
                sVar.c();
                b = sVar.b();
            } else {
                return;
            }
        }
    }

    static g c(s sVar) {
        b(sVar);
        if (!sVar.a('{')) {
            return null;
        }
        b(sVar);
        g gVar = new g();
        if (sVar.a('}')) {
            return gVar;
        }
        boolean z = true;
        while (true) {
            if (!z && !sVar.a(',')) {
                break;
            }
            z = false;
            String g = g(sVar);
            b(sVar);
            if (sVar.a(':')) {
                b(sVar);
                gVar.a(g, a(sVar));
            } else {
                gVar.b(g, true);
            }
            b(sVar);
        }
        if (sVar.a('}')) {
            return gVar;
        }
        return null;
    }

    static c d(s sVar) {
        b(sVar);
        if (!sVar.a('[')) {
            return null;
        }
        b(sVar);
        c cVar = new c();
        if (sVar.a(']')) {
            return cVar;
        }
        Object obj = 1;
        while (true) {
            if (obj == null && !sVar.a(',')) {
                break;
            }
            obj = null;
            cVar.a(a(sVar));
            b(sVar);
        }
        if (sVar.a(']')) {
            return cVar;
        }
        return null;
    }

    static String e(s sVar) {
        char c = '\"';
        b(sVar);
        if (!sVar.a('\"') && sVar.a('\'')) {
            c = '\'';
        }
        if (!sVar.a()) {
            return "";
        }
        a.a();
        char c2 = sVar.c();
        while (sVar.a() && c2 != r0) {
            if (c2 == '\\') {
                c2 = sVar.c();
                if (c2 == 'b') {
                    a.b('\b');
                } else if (c2 == 'f') {
                    a.b('\f');
                } else if (c2 == 'n') {
                    a.b('\n');
                } else if (c2 == 'r') {
                    a.b('\r');
                } else if (c2 == 't') {
                    a.b('\t');
                } else if (c2 == 'u') {
                    a.b(f(sVar));
                } else {
                    a.b(c2);
                }
            } else {
                a.b(c2);
            }
            c2 = sVar.c();
        }
        return a.toString();
    }

    static int a(int i) {
        if (i >= 48 && i <= 57) {
            return i - 48;
        }
        if (i >= 97 && i <= 102) {
            return (i - 97) + 10;
        }
        if (i < 65 || i > 70) {
            return 0;
        }
        return (i - 65) + 10;
    }

    static char f(s sVar) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            if (sVar.a()) {
                i = (i << 4) | a(sVar.c());
            }
        }
        return (char) i;
    }

    static String g(s sVar) {
        b(sVar);
        int b = sVar.b();
        if (b == 34 || b == 39) {
            return e(sVar);
        }
        a.a();
        Object obj = null;
        while (obj == null && sVar.a()) {
            if ((b < 97 || b > 122) && !((b >= 65 && b <= 90) || b == 95 || b == 36)) {
                obj = 1;
            } else {
                sVar.c();
                a.b((char) b);
                b = sVar.b();
            }
        }
        return a.toString();
    }

    static i h(s sVar) {
        double d;
        int b;
        b(sVar);
        double d2 = 1.0d;
        if (sVar.a('-')) {
            d2 = -1.0d;
            b(sVar);
        }
        double d3 = 0.0d;
        int b2 = sVar.b();
        while (sVar.a() && b2 >= 48 && b2 <= 57) {
            sVar.c();
            d3 = (d3 * 10.0d) + ((double) (b2 - 48));
            b2 = sVar.b();
        }
        Object obj = null;
        if (sVar.a('.')) {
            d = 0.0d;
            double d4 = 0.0d;
            b = sVar.b();
            while (sVar.a() && b >= 48 && b <= 57) {
                sVar.c();
                d = (d * 10.0d) + ((double) (b - 48));
                d4 += 1.0d;
                b = sVar.b();
            }
            d3 += d / Math.pow(10.0d, d4);
            obj = 1;
        }
        if (sVar.a('e') || sVar.a('E')) {
            Object obj2 = null;
            if (!sVar.a('+') && sVar.a('-')) {
                obj2 = 1;
            }
            d = 0.0d;
            b = sVar.b();
            while (sVar.a() && b >= 48 && b <= 57) {
                sVar.c();
                d = (d * 10.0d) + ((double) (b - 48));
                b = sVar.b();
            }
            if (obj2 != null) {
                d3 /= Math.pow(10.0d, d);
            } else {
                d3 *= Math.pow(10.0d, d);
            }
        }
        d3 *= d2;
        if (obj == null && d3 == ((double) ((int) d3))) {
            return new b((int) d3);
        }
        return new e(d3);
    }

    public static void a(String[] strArr) {
        System.out.println("==== ADCJSON Test ====");
        b(new f("test.txt"));
        a(new f("test2.txt"), a(new f("test.txt")));
        a(new f("test3.txt"), a(new f("test2.txt")));
    }
}
