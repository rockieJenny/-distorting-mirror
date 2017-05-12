package com.flurry.sdk;

import com.flurry.android.impl.ads.protocol.v13.ScreenOrientationType;
import java.util.HashMap;
import java.util.Map;

public class y {
    private final Map<b, a> a = new HashMap();

    public static class a {
        private ci a;
        private x b;

        public ci a() {
            return this.a;
        }

        public x b() {
            return this.b;
        }
    }

    static class b {
        String a;
        ScreenOrientationType b;
        e c;

        public b(String str, ScreenOrientationType screenOrientationType, e eVar) {
            this.a = str;
            this.b = screenOrientationType;
            if (eVar != null) {
                this.c = eVar.copy();
            }
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            if (this.a != bVar.a && this.a != null && !this.a.equals(bVar.a)) {
                return false;
            }
            if (this.b != bVar.b && this.b != null && !this.b.equals(bVar.b)) {
                return false;
            }
            if (this.c == bVar.c || this.c == null || this.c.equals(bVar.c)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i = 17;
            if (this.a != null) {
                i = 17 ^ this.a.hashCode();
            }
            if (this.b != null) {
                i ^= this.b.hashCode();
            }
            if (this.c != null) {
                return i ^ this.c.hashCode();
            }
            return i;
        }
    }

    public synchronized void a() {
        for (a aVar : this.a.values()) {
            aVar.a.a();
            aVar.b.a();
        }
        this.a.clear();
    }

    public synchronized a a(String str, ScreenOrientationType screenOrientationType, e eVar) {
        a aVar;
        b bVar = new b(str, screenOrientationType, eVar);
        aVar = (a) this.a.get(bVar);
        if (aVar == null) {
            aVar = new a();
            aVar.a = new ci(str);
            aVar.b = new x(str);
            this.a.put(bVar, aVar);
        }
        return aVar;
    }
}
