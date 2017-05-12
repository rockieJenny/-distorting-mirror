package com.flurry.sdk;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ia {
    final ie a;
    final im b;
    private final ThreadLocal<Map<jw<?>, a<?>>> c;
    private final Map<jw<?>, ir<?>> d;
    private final List<is> e;
    private final ja f;
    private final boolean g;
    private final boolean h;
    private final boolean i;
    private final boolean j;

    static class a<T> extends ir<T> {
        private ir<T> a;

        a() {
        }

        public void a(ir<T> irVar) {
            if (this.a != null) {
                throw new AssertionError();
            }
            this.a = irVar;
        }

        public T b(jx jxVar) throws IOException {
            if (this.a != null) {
                return this.a.b(jxVar);
            }
            throw new IllegalStateException();
        }

        public void a(jz jzVar, T t) throws IOException {
            if (this.a == null) {
                throw new IllegalStateException();
            }
            this.a.a(jzVar, t);
        }
    }

    public ia() {
        this(jb.a, hy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, ip.DEFAULT, Collections.emptyList());
    }

    ia(jb jbVar, hz hzVar, Map<Type, ic<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, ip ipVar, List<is> list) {
        this.c = new ThreadLocal();
        this.d = Collections.synchronizedMap(new HashMap());
        this.a = new ie(this) {
            final /* synthetic */ ia a;

            {
                this.a = r1;
            }
        };
        this.b = new im(this) {
            final /* synthetic */ ia a;

            {
                this.a = r1;
            }
        };
        this.f = new ja(map);
        this.g = z;
        this.i = z3;
        this.h = z4;
        this.j = z5;
        List arrayList = new ArrayList();
        arrayList.add(jv.Q);
        arrayList.add(jq.a);
        arrayList.add(jbVar);
        arrayList.addAll(list);
        arrayList.add(jv.x);
        arrayList.add(jv.m);
        arrayList.add(jv.g);
        arrayList.add(jv.i);
        arrayList.add(jv.k);
        arrayList.add(jv.a(Long.TYPE, Long.class, a(ipVar)));
        arrayList.add(jv.a(Double.TYPE, Double.class, a(z6)));
        arrayList.add(jv.a(Float.TYPE, Float.class, b(z6)));
        arrayList.add(jv.r);
        arrayList.add(jv.t);
        arrayList.add(jv.z);
        arrayList.add(jv.B);
        arrayList.add(jv.a(BigDecimal.class, jv.v));
        arrayList.add(jv.a(BigInteger.class, jv.w));
        arrayList.add(jv.D);
        arrayList.add(jv.F);
        arrayList.add(jv.J);
        arrayList.add(jv.O);
        arrayList.add(jv.H);
        arrayList.add(jv.d);
        arrayList.add(jl.a);
        arrayList.add(jv.M);
        arrayList.add(jt.a);
        arrayList.add(js.a);
        arrayList.add(jv.K);
        arrayList.add(jj.a);
        arrayList.add(jv.R);
        arrayList.add(jv.b);
        arrayList.add(new jk(this.f));
        arrayList.add(new jp(this.f, z2));
        arrayList.add(new jm(this.f));
        arrayList.add(new jr(this.f, hzVar, jbVar));
        this.e = Collections.unmodifiableList(arrayList);
    }

    private ir<Number> a(boolean z) {
        if (z) {
            return jv.p;
        }
        return new ir<Number>(this) {
            final /* synthetic */ ia a;

            {
                this.a = r1;
            }

            public /* synthetic */ Object b(jx jxVar) throws IOException {
                return a(jxVar);
            }

            public Double a(jx jxVar) throws IOException {
                if (jxVar.f() != jy.NULL) {
                    return Double.valueOf(jxVar.k());
                }
                jxVar.j();
                return null;
            }

            public void a(jz jzVar, Number number) throws IOException {
                if (number == null) {
                    jzVar.f();
                    return;
                }
                this.a.a(number.doubleValue());
                jzVar.a(number);
            }
        };
    }

    private ir<Number> b(boolean z) {
        if (z) {
            return jv.o;
        }
        return new ir<Number>(this) {
            final /* synthetic */ ia a;

            {
                this.a = r1;
            }

            public /* synthetic */ Object b(jx jxVar) throws IOException {
                return a(jxVar);
            }

            public Float a(jx jxVar) throws IOException {
                if (jxVar.f() != jy.NULL) {
                    return Float.valueOf((float) jxVar.k());
                }
                jxVar.j();
                return null;
            }

            public void a(jz jzVar, Number number) throws IOException {
                if (number == null) {
                    jzVar.f();
                    return;
                }
                this.a.a((double) number.floatValue());
                jzVar.a(number);
            }
        };
    }

    private void a(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private ir<Number> a(ip ipVar) {
        if (ipVar == ip.DEFAULT) {
            return jv.n;
        }
        return new ir<Number>(this) {
            final /* synthetic */ ia a;

            {
                this.a = r1;
            }

            public /* synthetic */ Object b(jx jxVar) throws IOException {
                return a(jxVar);
            }

            public Number a(jx jxVar) throws IOException {
                if (jxVar.f() != jy.NULL) {
                    return Long.valueOf(jxVar.l());
                }
                jxVar.j();
                return null;
            }

            public void a(jz jzVar, Number number) throws IOException {
                if (number == null) {
                    jzVar.f();
                } else {
                    jzVar.b(number.toString());
                }
            }
        };
    }

    public <T> ir<T> a(jw<T> jwVar) {
        Map map;
        ir<T> irVar = (ir) this.d.get(jwVar);
        if (irVar == null) {
            Map map2 = (Map) this.c.get();
            Object obj = null;
            if (map2 == null) {
                HashMap hashMap = new HashMap();
                this.c.set(hashMap);
                map = hashMap;
                obj = 1;
            } else {
                map = map2;
            }
            a aVar = (a) map.get(jwVar);
            if (aVar == null) {
                try {
                    a aVar2 = new a();
                    map.put(jwVar, aVar2);
                    for (is a : this.e) {
                        irVar = a.a(this, jwVar);
                        if (irVar != null) {
                            aVar2.a(irVar);
                            this.d.put(jwVar, irVar);
                            map.remove(jwVar);
                            if (obj != null) {
                                this.c.remove();
                            }
                        }
                    }
                    throw new IllegalArgumentException("GSON cannot handle " + jwVar);
                } catch (Throwable th) {
                    map.remove(jwVar);
                    if (obj != null) {
                        this.c.remove();
                    }
                }
            }
        }
        return irVar;
    }

    public <T> ir<T> a(is isVar, jw<T> jwVar) {
        Object obj = null;
        for (is isVar2 : this.e) {
            if (obj != null) {
                ir<T> a = isVar2.a(this, jwVar);
                if (a != null) {
                    return a;
                }
            } else if (isVar2 == isVar) {
                obj = 1;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + jwVar);
    }

    public <T> ir<T> a(Class<T> cls) {
        return a(jw.b(cls));
    }

    public void a(Object obj, Appendable appendable) throws ih {
        if (obj != null) {
            a(obj, obj.getClass(), appendable);
        } else {
            a(ii.a, appendable);
        }
    }

    public void a(Object obj, Type type, Appendable appendable) throws ih {
        try {
            a(obj, type, a(jh.a(appendable)));
        } catch (Throwable e) {
            throw new ih(e);
        }
    }

    public void a(Object obj, Type type, jz jzVar) throws ih {
        ir a = a(jw.a(type));
        boolean g = jzVar.g();
        jzVar.b(true);
        boolean h = jzVar.h();
        jzVar.c(this.h);
        boolean i = jzVar.i();
        jzVar.d(this.g);
        try {
            a.a(jzVar, obj);
            jzVar.b(g);
            jzVar.c(h);
            jzVar.d(i);
        } catch (Throwable e) {
            throw new ih(e);
        } catch (Throwable th) {
            jzVar.b(g);
            jzVar.c(h);
            jzVar.d(i);
        }
    }

    public void a(ig igVar, Appendable appendable) throws ih {
        try {
            a(igVar, a(jh.a(appendable)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private jz a(Writer writer) throws IOException {
        if (this.i) {
            writer.write(")]}'\n");
        }
        jz jzVar = new jz(writer);
        if (this.j) {
            jzVar.c("  ");
        }
        jzVar.d(this.g);
        return jzVar;
    }

    public void a(ig igVar, jz jzVar) throws ih {
        boolean g = jzVar.g();
        jzVar.b(true);
        boolean h = jzVar.h();
        jzVar.c(this.h);
        boolean i = jzVar.i();
        jzVar.d(this.g);
        try {
            jh.a(igVar, jzVar);
            jzVar.b(g);
            jzVar.c(h);
            jzVar.d(i);
        } catch (Throwable e) {
            throw new ih(e);
        } catch (Throwable th) {
            jzVar.b(g);
            jzVar.c(h);
            jzVar.d(i);
        }
    }

    public <T> T a(Reader reader, Class<T> cls) throws io, ih {
        jx jxVar = new jx(reader);
        Object a = a(jxVar, (Type) cls);
        a(a, jxVar);
        return jg.a((Class) cls).cast(a);
    }

    private static void a(Object obj, jx jxVar) {
        if (obj != null) {
            try {
                if (jxVar.f() != jy.END_DOCUMENT) {
                    throw new ih("JSON document was not fully consumed.");
                }
            } catch (Throwable e) {
                throw new io(e);
            } catch (Throwable e2) {
                throw new ih(e2);
            }
        }
    }

    public <T> T a(jx jxVar, Type type) throws ih, io {
        boolean z = true;
        boolean p = jxVar.p();
        jxVar.a(true);
        try {
            jxVar.f();
            z = false;
            T b = a(jw.a(type)).b(jxVar);
            jxVar.a(p);
            return b;
        } catch (Throwable e) {
            if (z) {
                jxVar.a(p);
                return null;
            }
            throw new io(e);
        } catch (Throwable e2) {
            throw new io(e2);
        } catch (Throwable e22) {
            throw new io(e22);
        } catch (Throwable th) {
            jxVar.a(p);
        }
    }

    public String toString() {
        return "{serializeNulls:" + this.g + "factories:" + this.e + ",instanceCreators:" + this.f + "}";
    }
}
