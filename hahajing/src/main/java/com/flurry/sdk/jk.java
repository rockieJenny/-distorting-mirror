package com.flurry.sdk;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class jk implements is {
    private final ja a;

    static final class a<E> extends ir<Collection<E>> {
        private final ir<E> a;
        private final jf<? extends Collection<E>> b;

        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public a(ia iaVar, Type type, ir<E> irVar, jf<? extends Collection<E>> jfVar) {
            this.a = new ju(iaVar, irVar, type);
            this.b = jfVar;
        }

        public Collection<E> a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            Collection<E> collection = (Collection) this.b.a();
            jxVar.a();
            while (jxVar.e()) {
                collection.add(this.a.b(jxVar));
            }
            jxVar.b();
            return collection;
        }

        public void a(jz jzVar, Collection<E> collection) throws IOException {
            if (collection == null) {
                jzVar.f();
                return;
            }
            jzVar.b();
            for (E a : collection) {
                this.a.a(jzVar, a);
            }
            jzVar.c();
        }
    }

    public jk(ja jaVar) {
        this.a = jaVar;
    }

    public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
        Type b = jwVar.b();
        Class a = jwVar.a();
        if (!Collection.class.isAssignableFrom(a)) {
            return null;
        }
        Type a2 = iz.a(b, a);
        return new a(iaVar, a2, iaVar.a(jw.a(a2)), this.a.a((jw) jwVar));
    }
}
