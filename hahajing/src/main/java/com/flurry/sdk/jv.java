package com.flurry.sdk;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

public final class jv {
    public static final ir<StringBuffer> A = new ir<StringBuffer>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public StringBuffer a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return new StringBuffer(jxVar.h());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, StringBuffer stringBuffer) throws IOException {
            jzVar.b(stringBuffer == null ? null : stringBuffer.toString());
        }
    };
    public static final is B = a(StringBuffer.class, A);
    public static final ir<URL> C = new ir<URL>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public URL a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            String h = jxVar.h();
            if ("null".equals(h)) {
                return null;
            }
            return new URL(h);
        }

        public void a(jz jzVar, URL url) throws IOException {
            jzVar.b(url == null ? null : url.toExternalForm());
        }
    };
    public static final is D = a(URL.class, C);
    public static final ir<URI> E = new ir<URI>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public URI a(jx jxVar) throws IOException {
            URI uri = null;
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
            } else {
                try {
                    String h = jxVar.h();
                    if (!"null".equals(h)) {
                        uri = new URI(h);
                    }
                } catch (Throwable e) {
                    throw new ih(e);
                }
            }
            return uri;
        }

        public void a(jz jzVar, URI uri) throws IOException {
            jzVar.b(uri == null ? null : uri.toASCIIString());
        }
    };
    public static final is F = a(URI.class, E);
    public static final ir<InetAddress> G = new ir<InetAddress>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public InetAddress a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return InetAddress.getByName(jxVar.h());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, InetAddress inetAddress) throws IOException {
            jzVar.b(inetAddress == null ? null : inetAddress.getHostAddress());
        }
    };
    public static final is H = b(InetAddress.class, G);
    public static final ir<UUID> I = new ir<UUID>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public UUID a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return UUID.fromString(jxVar.h());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, UUID uuid) throws IOException {
            jzVar.b(uuid == null ? null : uuid.toString());
        }
    };
    public static final is J = a(UUID.class, I);
    public static final is K = new is() {
        public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
            if (jwVar.a() != Timestamp.class) {
                return null;
            }
            final ir a = iaVar.a(Date.class);
            return new ir<Timestamp>(this) {
                final /* synthetic */ AnonymousClass15 b;

                public /* synthetic */ Object b(jx jxVar) throws IOException {
                    return a(jxVar);
                }

                public Timestamp a(jx jxVar) throws IOException {
                    Date date = (Date) a.b(jxVar);
                    return date != null ? new Timestamp(date.getTime()) : null;
                }

                public void a(jz jzVar, Timestamp timestamp) throws IOException {
                    a.a(jzVar, timestamp);
                }
            };
        }
    };
    public static final ir<Calendar> L = new ir<Calendar>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Calendar a(jx jxVar) throws IOException {
            int i = 0;
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            jxVar.c();
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (jxVar.f() != jy.END_OBJECT) {
                String g = jxVar.g();
                int m = jxVar.m();
                if ("year".equals(g)) {
                    i6 = m;
                } else if ("month".equals(g)) {
                    i5 = m;
                } else if ("dayOfMonth".equals(g)) {
                    i4 = m;
                } else if ("hourOfDay".equals(g)) {
                    i3 = m;
                } else if ("minute".equals(g)) {
                    i2 = m;
                } else if ("second".equals(g)) {
                    i = m;
                }
            }
            jxVar.d();
            return new GregorianCalendar(i6, i5, i4, i3, i2, i);
        }

        public void a(jz jzVar, Calendar calendar) throws IOException {
            if (calendar == null) {
                jzVar.f();
                return;
            }
            jzVar.d();
            jzVar.a("year");
            jzVar.a((long) calendar.get(1));
            jzVar.a("month");
            jzVar.a((long) calendar.get(2));
            jzVar.a("dayOfMonth");
            jzVar.a((long) calendar.get(5));
            jzVar.a("hourOfDay");
            jzVar.a((long) calendar.get(11));
            jzVar.a("minute");
            jzVar.a((long) calendar.get(12));
            jzVar.a("second");
            jzVar.a((long) calendar.get(13));
            jzVar.e();
        }
    };
    public static final is M = b(Calendar.class, GregorianCalendar.class, L);
    public static final ir<Locale> N = new ir<Locale>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Locale a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            String nextToken;
            String nextToken2;
            String nextToken3;
            StringTokenizer stringTokenizer = new StringTokenizer(jxVar.h(), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            if (stringTokenizer.hasMoreElements()) {
                nextToken = stringTokenizer.nextToken();
            } else {
                nextToken = null;
            }
            if (stringTokenizer.hasMoreElements()) {
                nextToken2 = stringTokenizer.nextToken();
            } else {
                nextToken2 = null;
            }
            if (stringTokenizer.hasMoreElements()) {
                nextToken3 = stringTokenizer.nextToken();
            } else {
                nextToken3 = null;
            }
            if (nextToken2 == null && nextToken3 == null) {
                return new Locale(nextToken);
            }
            if (nextToken3 == null) {
                return new Locale(nextToken, nextToken2);
            }
            return new Locale(nextToken, nextToken2, nextToken3);
        }

        public void a(jz jzVar, Locale locale) throws IOException {
            jzVar.b(locale == null ? null : locale.toString());
        }
    };
    public static final is O = a(Locale.class, N);
    public static final ir<ig> P = new ir<ig>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public ig a(jx jxVar) throws IOException {
            ig idVar;
            switch (jxVar.f()) {
                case NUMBER:
                    return new il(new jd(jxVar.h()));
                case BOOLEAN:
                    return new il(Boolean.valueOf(jxVar.i()));
                case STRING:
                    return new il(jxVar.h());
                case NULL:
                    jxVar.j();
                    return ii.a;
                case BEGIN_ARRAY:
                    idVar = new id();
                    jxVar.a();
                    while (jxVar.e()) {
                        idVar.a(a(jxVar));
                    }
                    jxVar.b();
                    return idVar;
                case BEGIN_OBJECT:
                    idVar = new ij();
                    jxVar.c();
                    while (jxVar.e()) {
                        idVar.a(jxVar.g(), a(jxVar));
                    }
                    jxVar.d();
                    return idVar;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void a(jz jzVar, ig igVar) throws IOException {
            if (igVar == null || igVar.j()) {
                jzVar.f();
            } else if (igVar.i()) {
                il m = igVar.m();
                if (m.p()) {
                    jzVar.a(m.a());
                } else if (m.o()) {
                    jzVar.a(m.f());
                } else {
                    jzVar.b(m.b());
                }
            } else if (igVar.g()) {
                jzVar.b();
                Iterator it = igVar.l().iterator();
                while (it.hasNext()) {
                    a(jzVar, (ig) it.next());
                }
                jzVar.c();
            } else if (igVar.h()) {
                jzVar.d();
                for (Entry entry : igVar.k().o()) {
                    jzVar.a((String) entry.getKey());
                    a(jzVar, (ig) entry.getValue());
                }
                jzVar.e();
            } else {
                throw new IllegalArgumentException("Couldn't write " + igVar.getClass());
            }
        }
    };
    public static final is Q = b(ig.class, P);
    public static final is R = a();
    public static final ir<Class> a = new ir<Class>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public void a(jz jzVar, Class cls) throws IOException {
            if (cls == null) {
                jzVar.f();
                return;
            }
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + cls.getName() + ". Forgot to register a type adapter?");
        }

        public Class a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    };
    public static final is b = a(Class.class, a);
    public static final ir<BitSet> c = new ir<BitSet>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public BitSet a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            BitSet bitSet = new BitSet();
            jxVar.a();
            jy f = jxVar.f();
            int i = 0;
            while (f != jy.END_ARRAY) {
                boolean z;
                switch (f) {
                    case NUMBER:
                        if (jxVar.m() == 0) {
                            z = false;
                            break;
                        }
                        z = true;
                        break;
                    case BOOLEAN:
                        z = jxVar.i();
                        break;
                    case STRING:
                        String h = jxVar.h();
                        try {
                            if (Integer.parseInt(h) == 0) {
                                z = false;
                                break;
                            }
                            z = true;
                            break;
                        } catch (NumberFormatException e) {
                            throw new io("Error: Expecting: bitset number value (1, 0), Found: " + h);
                        }
                    default:
                        throw new io("Invalid bitset value type: " + f);
                }
                if (z) {
                    bitSet.set(i);
                }
                i++;
                f = jxVar.f();
            }
            jxVar.b();
            return bitSet;
        }

        public void a(jz jzVar, BitSet bitSet) throws IOException {
            if (bitSet == null) {
                jzVar.f();
                return;
            }
            jzVar.b();
            for (int i = 0; i < bitSet.length(); i++) {
                int i2;
                if (bitSet.get(i)) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                jzVar.a((long) i2);
            }
            jzVar.c();
        }
    };
    public static final is d = a(BitSet.class, c);
    public static final ir<Boolean> e = new ir<Boolean>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Boolean a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            } else if (jxVar.f() == jy.STRING) {
                return Boolean.valueOf(Boolean.parseBoolean(jxVar.h()));
            } else {
                return Boolean.valueOf(jxVar.i());
            }
        }

        public void a(jz jzVar, Boolean bool) throws IOException {
            if (bool == null) {
                jzVar.f();
            } else {
                jzVar.a(bool.booleanValue());
            }
        }
    };
    public static final ir<Boolean> f = new ir<Boolean>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Boolean a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return Boolean.valueOf(jxVar.h());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, Boolean bool) throws IOException {
            jzVar.b(bool == null ? "null" : bool.toString());
        }
    };
    public static final is g = a(Boolean.TYPE, Boolean.class, e);
    public static final ir<Number> h = new ir<Number>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Number a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            try {
                return Byte.valueOf((byte) jxVar.m());
            } catch (Throwable e) {
                throw new io(e);
            }
        }

        public void a(jz jzVar, Number number) throws IOException {
            jzVar.a(number);
        }
    };
    public static final is i = a(Byte.TYPE, Byte.class, h);
    public static final ir<Number> j = new ir<Number>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Number a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            try {
                return Short.valueOf((short) jxVar.m());
            } catch (Throwable e) {
                throw new io(e);
            }
        }

        public void a(jz jzVar, Number number) throws IOException {
            jzVar.a(number);
        }
    };
    public static final is k = a(Short.TYPE, Short.class, j);
    public static final ir<Number> l = new ir<Number>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Number a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            try {
                return Integer.valueOf(jxVar.m());
            } catch (Throwable e) {
                throw new io(e);
            }
        }

        public void a(jz jzVar, Number number) throws IOException {
            jzVar.a(number);
        }
    };
    public static final is m = a(Integer.TYPE, Integer.class, l);
    public static final ir<Number> n = new ir<Number>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Number a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            try {
                return Long.valueOf(jxVar.l());
            } catch (Throwable e) {
                throw new io(e);
            }
        }

        public void a(jz jzVar, Number number) throws IOException {
            jzVar.a(number);
        }
    };
    public static final ir<Number> o = new ir<Number>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Number a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return Float.valueOf((float) jxVar.k());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, Number number) throws IOException {
            jzVar.a(number);
        }
    };
    public static final ir<Number> p = new ir<Number>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Number a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return Double.valueOf(jxVar.k());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, Number number) throws IOException {
            jzVar.a(number);
        }
    };
    public static final ir<Number> q = new ir<Number>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Number a(jx jxVar) throws IOException {
            jy f = jxVar.f();
            switch (f) {
                case NUMBER:
                    return new jd(jxVar.h());
                case NULL:
                    jxVar.j();
                    return null;
                default:
                    throw new io("Expecting number, got: " + f);
            }
        }

        public void a(jz jzVar, Number number) throws IOException {
            jzVar.a(number);
        }
    };
    public static final is r = a(Number.class, q);
    public static final ir<Character> s = new ir<Character>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public Character a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            String h = jxVar.h();
            if (h.length() == 1) {
                return Character.valueOf(h.charAt(0));
            }
            throw new io("Expecting character, got: " + h);
        }

        public void a(jz jzVar, Character ch) throws IOException {
            jzVar.b(ch == null ? null : String.valueOf(ch));
        }
    };
    public static final is t = a(Character.TYPE, Character.class, s);
    public static final ir<String> u = new ir<String>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public String a(jx jxVar) throws IOException {
            jy f = jxVar.f();
            if (f == jy.NULL) {
                jxVar.j();
                return null;
            } else if (f == jy.BOOLEAN) {
                return Boolean.toString(jxVar.i());
            } else {
                return jxVar.h();
            }
        }

        public void a(jz jzVar, String str) throws IOException {
            jzVar.b(str);
        }
    };
    public static final ir<BigDecimal> v = new ir<BigDecimal>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public BigDecimal a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            try {
                return new BigDecimal(jxVar.h());
            } catch (Throwable e) {
                throw new io(e);
            }
        }

        public void a(jz jzVar, BigDecimal bigDecimal) throws IOException {
            jzVar.a((Number) bigDecimal);
        }
    };
    public static final ir<BigInteger> w = new ir<BigInteger>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public BigInteger a(jx jxVar) throws IOException {
            if (jxVar.f() == jy.NULL) {
                jxVar.j();
                return null;
            }
            try {
                return new BigInteger(jxVar.h());
            } catch (Throwable e) {
                throw new io(e);
            }
        }

        public void a(jz jzVar, BigInteger bigInteger) throws IOException {
            jzVar.a((Number) bigInteger);
        }
    };
    public static final is x = a(String.class, u);
    public static final ir<StringBuilder> y = new ir<StringBuilder>() {
        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public StringBuilder a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return new StringBuilder(jxVar.h());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, StringBuilder stringBuilder) throws IOException {
            jzVar.b(stringBuilder == null ? null : stringBuilder.toString());
        }
    };
    public static final is z = a(StringBuilder.class, y);

    static final class a<T extends Enum<T>> extends ir<T> {
        private final Map<String, T> a = new HashMap();
        private final Map<T, String> b = new HashMap();

        public /* synthetic */ Object b(jx jxVar) throws IOException {
            return a(jxVar);
        }

        public a(Class<T> cls) {
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    Object a;
                    String name = enumR.name();
                    iv ivVar = (iv) cls.getField(name).getAnnotation(iv.class);
                    if (ivVar != null) {
                        a = ivVar.a();
                    } else {
                        String str = name;
                    }
                    this.a.put(a, enumR);
                    this.b.put(enumR, a);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError();
            }
        }

        public T a(jx jxVar) throws IOException {
            if (jxVar.f() != jy.NULL) {
                return (Enum) this.a.get(jxVar.h());
            }
            jxVar.j();
            return null;
        }

        public void a(jz jzVar, T t) throws IOException {
            jzVar.b(t == null ? null : (String) this.b.get(t));
        }
    }

    public static is a() {
        return new is() {
            public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
                Class a = jwVar.a();
                if (!Enum.class.isAssignableFrom(a) || a == Enum.class) {
                    return null;
                }
                if (!a.isEnum()) {
                    a = a.getSuperclass();
                }
                return new a(a);
            }
        };
    }

    public static <TT> is a(final Class<TT> cls, final ir<TT> irVar) {
        return new is() {
            public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
                return jwVar.a() == cls ? irVar : null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + ",adapter=" + irVar + "]";
            }
        };
    }

    public static <TT> is a(final Class<TT> cls, final Class<TT> cls2, final ir<? super TT> irVar) {
        return new is() {
            public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
                Class a = jwVar.a();
                return (a == cls || a == cls2) ? irVar : null;
            }

            public String toString() {
                return "Factory[type=" + cls2.getName() + "+" + cls.getName() + ",adapter=" + irVar + "]";
            }
        };
    }

    public static <TT> is b(final Class<TT> cls, final Class<? extends TT> cls2, final ir<? super TT> irVar) {
        return new is() {
            public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
                Class a = jwVar.a();
                return (a == cls || a == cls2) ? irVar : null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + "+" + cls2.getName() + ",adapter=" + irVar + "]";
            }
        };
    }

    public static <TT> is b(final Class<TT> cls, final ir<TT> irVar) {
        return new is() {
            public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
                return cls.isAssignableFrom(jwVar.a()) ? irVar : null;
            }

            public String toString() {
                return "Factory[typeHierarchy=" + cls.getName() + ",adapter=" + irVar + "]";
            }
        };
    }
}
