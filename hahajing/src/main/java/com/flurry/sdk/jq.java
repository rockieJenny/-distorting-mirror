package com.flurry.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class jq extends ir<Object> {
    public static final is a = new is() {
        public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
            if (jwVar.a() == Object.class) {
                return new jq(iaVar);
            }
            return null;
        }
    };
    private final ia b;

    private jq(ia iaVar) {
        this.b = iaVar;
    }

    public Object b(jx jxVar) throws IOException {
        switch (jxVar.f()) {
            case BEGIN_ARRAY:
                List arrayList = new ArrayList();
                jxVar.a();
                while (jxVar.e()) {
                    arrayList.add(b(jxVar));
                }
                jxVar.b();
                return arrayList;
            case BEGIN_OBJECT:
                Object jeVar = new je();
                jxVar.c();
                while (jxVar.e()) {
                    jeVar.put(jxVar.g(), b(jxVar));
                }
                jxVar.d();
                return jeVar;
            case STRING:
                return jxVar.h();
            case NUMBER:
                return Double.valueOf(jxVar.k());
            case BOOLEAN:
                return Boolean.valueOf(jxVar.i());
            case NULL:
                jxVar.j();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public void a(jz jzVar, Object obj) throws IOException {
        if (obj == null) {
            jzVar.f();
            return;
        }
        ir a = this.b.a(obj.getClass());
        if (a instanceof jq) {
            jzVar.d();
            jzVar.e();
            return;
        }
        a.a(jzVar, obj);
    }
}
