package com.flurry.sdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class ja {
    private final Map<Type, ic<?>> a;

    public ja(Map<Type, ic<?>> map) {
        this.a = map;
    }

    public <T> jf<T> a(jw<T> jwVar) {
        final Type b = jwVar.b();
        Class a = jwVar.a();
        final ic icVar = (ic) this.a.get(b);
        if (icVar != null) {
            return new jf<T>(this) {
                final /* synthetic */ ja c;

                public T a() {
                    return icVar.a(b);
                }
            };
        }
        icVar = (ic) this.a.get(a);
        if (icVar != null) {
            return new jf<T>(this) {
                final /* synthetic */ ja c;

                public T a() {
                    return icVar.a(b);
                }
            };
        }
        jf<T> a2 = a(a);
        if (a2 != null) {
            return a2;
        }
        a2 = a(b, a);
        return a2 == null ? b(b, a) : a2;
    }

    private <T> jf<T> a(Class<? super T> cls) {
        try {
            final Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new jf<T>(this) {
                final /* synthetic */ ja b;

                public T a() {
                    try {
                        return declaredConstructor.newInstance(null);
                    } catch (Throwable e) {
                        throw new RuntimeException("Failed to invoke " + declaredConstructor + " with no args", e);
                    } catch (InvocationTargetException e2) {
                        throw new RuntimeException("Failed to invoke " + declaredConstructor + " with no args", e2.getTargetException());
                    } catch (IllegalAccessException e3) {
                        throw new AssertionError(e3);
                    }
                }
            };
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private <T> jf<T> a(final Type type, Class<? super T> cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            if (SortedSet.class.isAssignableFrom(cls)) {
                return new jf<T>(this) {
                    final /* synthetic */ ja a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(cls)) {
                return new jf<T>(this) {
                    final /* synthetic */ ja b;

                    public T a() {
                        if (type instanceof ParameterizedType) {
                            Type type = ((ParameterizedType) type).getActualTypeArguments()[0];
                            if (type instanceof Class) {
                                return EnumSet.noneOf((Class) type);
                            }
                            throw new ih("Invalid EnumSet type: " + type.toString());
                        }
                        throw new ih("Invalid EnumSet type: " + type.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(cls)) {
                return new jf<T>(this) {
                    final /* synthetic */ ja a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(cls)) {
                return new jf<T>(this) {
                    final /* synthetic */ ja a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new LinkedList();
                    }
                };
            }
            return new jf<T>(this) {
                final /* synthetic */ ja a;

                {
                    this.a = r1;
                }

                public T a() {
                    return new ArrayList();
                }
            };
        } else if (!Map.class.isAssignableFrom(cls)) {
            return null;
        } else {
            if (SortedMap.class.isAssignableFrom(cls)) {
                return new jf<T>(this) {
                    final /* synthetic */ ja a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new TreeMap();
                    }
                };
            }
            if (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(jw.a(((ParameterizedType) type).getActualTypeArguments()[0]).a())) {
                return new jf<T>(this) {
                    final /* synthetic */ ja a;

                    {
                        this.a = r1;
                    }

                    public T a() {
                        return new je();
                    }
                };
            }
            return new jf<T>(this) {
                final /* synthetic */ ja a;

                {
                    this.a = r1;
                }

                public T a() {
                    return new LinkedHashMap();
                }
            };
        }
    }

    private <T> jf<T> b(final Type type, final Class<? super T> cls) {
        return new jf<T>(this) {
            final /* synthetic */ ja c;
            private final ji d = ji.a();

            public T a() {
                try {
                    return this.d.a(cls);
                } catch (Throwable e) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e);
                }
            }
        };
    }

    public String toString() {
        return this.a.toString();
    }
}
