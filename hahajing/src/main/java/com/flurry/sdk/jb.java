package com.flurry.sdk;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public final class jb implements is, Cloneable {
    public static final jb a = new jb();
    private double b = -1.0d;
    private int c = 136;
    private boolean d = true;
    private boolean e;
    private List<hw> f = Collections.emptyList();
    private List<hw> g = Collections.emptyList();

    protected /* synthetic */ Object clone() throws CloneNotSupportedException {
        return a();
    }

    protected jb a() {
        try {
            return (jb) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
        Class a = jwVar.a();
        final boolean a2 = a(a, true);
        final boolean a3 = a(a, false);
        if (!a2 && !a3) {
            return null;
        }
        final ia iaVar2 = iaVar;
        final jw<T> jwVar2 = jwVar;
        return new ir<T>(this) {
            final /* synthetic */ jb e;
            private ir<T> f;

            public T b(jx jxVar) throws IOException {
                if (!a3) {
                    return a().b(jxVar);
                }
                jxVar.n();
                return null;
            }

            public void a(jz jzVar, T t) throws IOException {
                if (a2) {
                    jzVar.f();
                } else {
                    a().a(jzVar, t);
                }
            }

            private ir<T> a() {
                ir<T> irVar = this.f;
                if (irVar != null) {
                    return irVar;
                }
                irVar = iaVar2.a(this.e, jwVar2);
                this.f = irVar;
                return irVar;
            }
        };
    }

    public boolean a(Field field, boolean z) {
        if ((this.c & field.getModifiers()) != 0) {
            return true;
        }
        if (this.b != -1.0d && !a((iw) field.getAnnotation(iw.class), (ix) field.getAnnotation(ix.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        if (this.e) {
            it itVar = (it) field.getAnnotation(it.class);
            if (itVar == null || (z ? !itVar.a() : !itVar.b())) {
                return true;
            }
        }
        if (!this.d && b(field.getType())) {
            return true;
        }
        if (a(field.getType())) {
            return true;
        }
        List<hw> list = z ? this.f : this.g;
        if (!list.isEmpty()) {
            hx hxVar = new hx(field);
            for (hw a : list) {
                if (a.a(hxVar)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean a(Class<?> cls, boolean z) {
        if (this.b != -1.0d && !a((iw) cls.getAnnotation(iw.class), (ix) cls.getAnnotation(ix.class))) {
            return true;
        }
        if (!this.d && b(cls)) {
            return true;
        }
        if (a((Class) cls)) {
            return true;
        }
        for (hw a : z ? this.f : this.g) {
            if (a.a((Class) cls)) {
                return true;
            }
        }
        return false;
    }

    private boolean a(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean b(Class<?> cls) {
        return cls.isMemberClass() && !c(cls);
    }

    private boolean c(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    private boolean a(iw iwVar, ix ixVar) {
        return a(iwVar) && a(ixVar);
    }

    private boolean a(iw iwVar) {
        if (iwVar == null || iwVar.a() <= this.b) {
            return true;
        }
        return false;
    }

    private boolean a(ix ixVar) {
        if (ixVar == null || ixVar.a() > this.b) {
            return true;
        }
        return false;
    }
}
