package com.flurry.sdk;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public final class jr implements is {
    private final ja a;
    private final hz b;
    private final jb c;

    static abstract class b {
        final String g;
        final boolean h;
        final boolean i;

        abstract void a(jx jxVar, Object obj) throws IOException, IllegalAccessException;

        abstract void a(jz jzVar, Object obj) throws IOException, IllegalAccessException;

        protected b(String str, boolean z, boolean z2) {
            this.g = str;
            this.h = z;
            this.i = z2;
        }
    }

    public static final class a<T> extends ir<T> {
        private final jf<T> a;
        private final Map<String, b> b;

        private a(jf<T> jfVar, Map<String, b> map) {
            this.a = jfVar;
            this.b = map;
        }

        public T b(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            T a = this.a.a();
            try {
                jxVar.c();
                while (jxVar.e()) {
                    b bVar = (b) this.b.get(jxVar.g());
                    if (bVar == null || !bVar.i) {
                        jxVar.n();
                    } else {
                        bVar.a(jxVar, (Object) a);
                    }
                }
                jxVar.d();
                return a;
            } catch (Throwable e) {
                throw new io(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public void a(jz jzVar, T t) throws IOException {
            if (t == null) {
                jzVar.f();
                return;
            }
            jzVar.d();
            try {
                for (b bVar : this.b.values()) {
                    if (bVar.h) {
                        jzVar.a(bVar.g);
                        bVar.a(jzVar, (Object) t);
                    }
                }
                jzVar.e();
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }
    }

    public jr(ja jaVar, hz hzVar, jb jbVar) {
        this.a = jaVar;
        this.b = hzVar;
        this.c = jbVar;
    }

    public boolean a(Field field, boolean z) {
        return (this.c.a(field.getType(), z) || this.c.a(field, z)) ? false : true;
    }

    private String a(Field field) {
        iv ivVar = (iv) field.getAnnotation(iv.class);
        return ivVar == null ? this.b.a(field) : ivVar.a();
    }

    public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
        Class a = jwVar.a();
        if (Object.class.isAssignableFrom(a)) {
            return new a(this.a.a((jw) jwVar), a(iaVar, (jw) jwVar, a));
        }
        return null;
    }

    private b a(ia iaVar, Field field, String str, jw<?> jwVar, boolean z, boolean z2) {
        final boolean a = jg.a(jwVar.a());
        final ia iaVar2 = iaVar;
        final Field field2 = field;
        final jw<?> jwVar2 = jwVar;
        return new b(this, str, z, z2) {
            final ir<?> a = this.f.a(iaVar2, field2, jwVar2);
            final /* synthetic */ jr f;

            void a(jz jzVar, Object obj) throws IOException, IllegalAccessException {
                new ju(iaVar2, this.a, jwVar2.b()).a(jzVar, field2.get(obj));
            }

            void a(jx jxVar, Object obj) throws IOException, IllegalAccessException {
                Object b = this.a.b(jxVar);
                if (b != null || !a) {
                    field2.set(obj, b);
                }
            }
        };
    }

    private ir<?> a(ia iaVar, Field field, jw<?> jwVar) {
        iu iuVar = (iu) field.getAnnotation(iu.class);
        if (iuVar != null) {
            ir<?> a = jm.a(this.a, iaVar, jwVar, iuVar);
            if (a != null) {
                return a;
            }
        }
        return iaVar.a((jw) jwVar);
    }

    private Map<String, b> a(ia iaVar, jw<?> jwVar, Class<?> cls) {
        Map<String, b> linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type b = jwVar.b();
        Class a;
        while (a != Object.class) {
            for (Field field : a.getDeclaredFields()) {
                boolean a2 = a(field, true);
                boolean a3 = a(field, false);
                if (a2 || a3) {
                    field.setAccessible(true);
                    b a4 = a(iaVar, field, a(field), jw.a(iz.a(jwVar.b(), a, field.getGenericType())), a2, a3);
                    a4 = (b) linkedHashMap.put(a4.g, a4);
                    if (a4 != null) {
                        throw new IllegalArgumentException(b + " declares multiple JSON fields named " + a4.g);
                    }
                }
            }
            jwVar = jw.a(iz.a(jwVar.b(), a, a.getGenericSuperclass()));
            a = jwVar.a();
        }
        return linkedHashMap;
    }
}
