package com.flurry.sdk;

import com.flurry.sdk.jr.a;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class ju<T> extends ir<T> {
    private final ia a;
    private final ir<T> b;
    private final Type c;

    ju(ia iaVar, ir<T> irVar, Type type) {
        this.a = iaVar;
        this.b = irVar;
        this.c = type;
    }

    public T b(jx jxVar) throws IOException {
        return this.b.b(jxVar);
    }

    public void a(jz jzVar, T t) throws IOException {
        ir irVar = this.b;
        Type a = a(this.c, (Object) t);
        if (a != this.c) {
            irVar = this.a.a(jw.a(a));
            if ((irVar instanceof a) && !(this.b instanceof a)) {
                irVar = this.b;
            }
        }
        irVar.a(jzVar, t);
    }

    private Type a(Type type, Object obj) {
        if (obj == null) {
            return type;
        }
        if (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) {
            return obj.getClass();
        }
        return type;
    }
}
