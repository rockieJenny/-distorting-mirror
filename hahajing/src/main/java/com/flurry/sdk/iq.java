package com.flurry.sdk;

import java.io.IOException;

final class iq<T> extends ir<T> {
    private final in<T> a;
    private final if<T> b;
    private final ia c;
    private final jw<T> d;
    private final is e;
    private ir<T> f;

    static class a implements is {
        private final jw<?> a;
        private final boolean b;
        private final Class<?> c;
        private final in<?> d;
        private final if<?> e;

        private a(Object obj, jw<?> jwVar, boolean z, Class<?> cls) {
            this.d = obj instanceof in ? (in) obj : null;
            if (obj instanceof if) {
                obj = (if) obj;
            } else {
                obj = null;
            }
            this.e = obj;
            boolean z2 = (this.d == null && this.e == null) ? false : true;
            iy.a(z2);
            this.a = jwVar;
            this.b = z;
            this.c = cls;
        }

        public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
            boolean isAssignableFrom = this.a != null ? this.a.equals(jwVar) || (this.b && this.a.b() == jwVar.a()) : this.c.isAssignableFrom(jwVar.a());
            if (isAssignableFrom) {
                return new iq(this.d, this.e, iaVar, jwVar, this);
            }
            return null;
        }
    }

    private iq(in<T> inVar, if<T> ifVar, ia iaVar, jw<T> jwVar, is isVar) {
        this.a = inVar;
        this.b = ifVar;
        this.c = iaVar;
        this.d = jwVar;
        this.e = isVar;
    }

    public T b(jx jxVar) throws IOException {
        if (this.b == null) {
            return a().b(jxVar);
        }
        ig a = jh.a(jxVar);
        if (a.j()) {
            return null;
        }
        return this.b.b(a, this.d.b(), this.c.a);
    }

    public void a(jz jzVar, T t) throws IOException {
        if (this.a == null) {
            a().a(jzVar, t);
        } else if (t == null) {
            jzVar.f();
        } else {
            jh.a(this.a.a(t, this.d.b(), this.c.b), jzVar);
        }
    }

    private ir<T> a() {
        ir<T> irVar = this.f;
        if (irVar != null) {
            return irVar;
        }
        irVar = this.c.a(this.e, this.d);
        this.f = irVar;
        return irVar;
    }

    public static is a(jw<?> jwVar, Object obj) {
        return new a(obj, jwVar, false, null);
    }
}
