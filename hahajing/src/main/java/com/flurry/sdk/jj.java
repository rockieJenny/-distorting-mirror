package com.flurry.sdk;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class jj<E> extends ir<Object> {
    public static final is a = new is() {
        public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
            Type b = jwVar.b();
            if (!(b instanceof GenericArrayType) && (!(b instanceof Class) || !((Class) b).isArray())) {
                return null;
            }
            b = iz.g(b);
            return new jj(iaVar, iaVar.a(jw.a(b)), iz.e(b));
        }
    };
    private final Class<E> b;
    private final ir<E> c;

    public jj(ia iaVar, ir<E> irVar, Class<E> cls) {
        this.c = new ju(iaVar, irVar, cls);
        this.b = cls;
    }

    public Object b(jx jxVar) throws IOException {
        if (jxVar.f() == jy.NULL) {
            jxVar.j();
            return null;
        }
        List arrayList = new ArrayList();
        jxVar.a();
        while (jxVar.e()) {
            arrayList.add(this.c.b(jxVar));
        }
        jxVar.b();
        Object newInstance = Array.newInstance(this.b, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }

    public void a(jz jzVar, Object obj) throws IOException {
        if (obj == null) {
            jzVar.f();
            return;
        }
        jzVar.b();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.c.a(jzVar, Array.get(obj, i));
        }
        jzVar.c();
    }
}
