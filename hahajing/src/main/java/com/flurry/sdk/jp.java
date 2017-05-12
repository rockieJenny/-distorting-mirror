package com.flurry.sdk;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class jp implements is {
    private final ja a;
    private final boolean b;

    final class a<K, V> extends ir<Map<K, V>> {
        final /* synthetic */ jp a;
        private final ir<K> b;
        private final ir<V> c;
        private final jf<? extends Map<K, V>> d;

        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public a(jp jpVar, ia iaVar, Type type, ir<K> irVar, Type type2, ir<V> irVar2, jf<? extends Map<K, V>> jfVar) {
            this.a = jpVar;
            this.b = new ju(iaVar, irVar, type);
            this.c = new ju(iaVar, irVar2, type2);
            this.d = jfVar;
        }

        public Map<K, V> a(jx jxVar) throws IOException {
            jy f = jxVar.f();
            if (f == jy.NULL) {
                jxVar.j();
                return null;
            }
            Map<K, V> map = (Map) this.d.a();
            Object b;
            if (f == jy.BEGIN_ARRAY) {
                jxVar.a();
                while (jxVar.e()) {
                    jxVar.a();
                    b = this.b.b(jxVar);
                    if (map.put(b, this.c.b(jxVar)) != null) {
                        throw new io("duplicate key: " + b);
                    }
                    jxVar.b();
                }
                jxVar.b();
                return map;
            }
            jxVar.c();
            while (jxVar.e()) {
                jc.a.a(jxVar);
                b = this.b.b(jxVar);
                if (map.put(b, this.c.b(jxVar)) != null) {
                    throw new io("duplicate key: " + b);
                }
            }
            jxVar.d();
            return map;
        }

        public void a(jz jzVar, Map<K, V> map) throws IOException {
            int i = 0;
            if (map == null) {
                jzVar.f();
            } else if (this.a.b) {
                List arrayList = new ArrayList(map.size());
                List arrayList2 = new ArrayList(map.size());
                int i2 = 0;
                for (Entry entry : map.entrySet()) {
                    int i3;
                    ig a = this.b.a(entry.getKey());
                    arrayList.add(a);
                    arrayList2.add(entry.getValue());
                    if (a.g() || a.h()) {
                        i3 = 1;
                    } else {
                        i3 = 0;
                    }
                    i2 = i3 | i2;
                }
                if (i2 != 0) {
                    jzVar.b();
                    while (i < arrayList.size()) {
                        jzVar.b();
                        jh.a((ig) arrayList.get(i), jzVar);
                        this.c.a(jzVar, arrayList2.get(i));
                        jzVar.c();
                        i++;
                    }
                    jzVar.c();
                    return;
                }
                jzVar.d();
                while (i < arrayList.size()) {
                    jzVar.a(a((ig) arrayList.get(i)));
                    this.c.a(jzVar, arrayList2.get(i));
                    i++;
                }
                jzVar.e();
            } else {
                jzVar.d();
                for (Entry entry2 : map.entrySet()) {
                    jzVar.a(String.valueOf(entry2.getKey()));
                    this.c.a(jzVar, entry2.getValue());
                }
                jzVar.e();
            }
        }

        private String a(ig igVar) {
            if (igVar.i()) {
                il m = igVar.m();
                if (m.p()) {
                    return String.valueOf(m.a());
                }
                if (m.o()) {
                    return Boolean.toString(m.f());
                }
                if (m.q()) {
                    return m.b();
                }
                throw new AssertionError();
            } else if (igVar.j()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }
    }

    public jp(ja jaVar, boolean z) {
        this.a = jaVar;
        this.b = z;
    }

    public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
        Type b = jwVar.b();
        if (!Map.class.isAssignableFrom(jwVar.a())) {
            return null;
        }
        Type[] b2 = iz.b(b, iz.e(b));
        ir a = a(iaVar, b2[0]);
        ir a2 = iaVar.a(jw.a(b2[1]));
        jf a3 = this.a.a((jw) jwVar);
        return new a(this, iaVar, b2[0], a, b2[1], a2, a3);
    }

    private ir<?> a(ia iaVar, Type type) {
        if (type == Boolean.TYPE || type == Boolean.class) {
            return jv.f;
        }
        return iaVar.a(jw.a(type));
    }
}
