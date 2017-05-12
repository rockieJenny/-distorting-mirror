package com.flurry.sdk;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class jn extends jx {
    private static final Reader a = new Reader() {
        public int read(char[] cArr, int i, int i2) throws IOException {
            throw new AssertionError();
        }

        public void close() throws IOException {
            throw new AssertionError();
        }
    };
    private static final Object b = new Object();
    private final List<Object> c;

    public void a() throws IOException {
        a(jy.BEGIN_ARRAY);
        this.c.add(((id) r()).iterator());
    }

    public void b() throws IOException {
        a(jy.END_ARRAY);
        s();
        s();
    }

    public void c() throws IOException {
        a(jy.BEGIN_OBJECT);
        this.c.add(((ij) r()).o().iterator());
    }

    public void d() throws IOException {
        a(jy.END_OBJECT);
        s();
        s();
    }

    public boolean e() throws IOException {
        jy f = f();
        return (f == jy.END_OBJECT || f == jy.END_ARRAY) ? false : true;
    }

    public jy f() throws IOException {
        if (this.c.isEmpty()) {
            return jy.END_DOCUMENT;
        }
        Object r = r();
        if (r instanceof Iterator) {
            boolean z = this.c.get(this.c.size() - 2) instanceof ij;
            Iterator it = (Iterator) r;
            if (!it.hasNext()) {
                return z ? jy.END_OBJECT : jy.END_ARRAY;
            } else {
                if (z) {
                    return jy.NAME;
                }
                this.c.add(it.next());
                return f();
            }
        } else if (r instanceof ij) {
            return jy.BEGIN_OBJECT;
        } else {
            if (r instanceof id) {
                return jy.BEGIN_ARRAY;
            }
            if (r instanceof il) {
                il ilVar = (il) r;
                if (ilVar.q()) {
                    return jy.STRING;
                }
                if (ilVar.o()) {
                    return jy.BOOLEAN;
                }
                if (ilVar.p()) {
                    return jy.NUMBER;
                }
                throw new AssertionError();
            } else if (r instanceof ii) {
                return jy.NULL;
            } else {
                if (r == b) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    private Object r() {
        return this.c.get(this.c.size() - 1);
    }

    private Object s() {
        return this.c.remove(this.c.size() - 1);
    }

    private void a(jy jyVar) throws IOException {
        if (f() != jyVar) {
            throw new IllegalStateException("Expected " + jyVar + " but was " + f());
        }
    }

    public String g() throws IOException {
        a(jy.NAME);
        Entry entry = (Entry) ((Iterator) r()).next();
        this.c.add(entry.getValue());
        return (String) entry.getKey();
    }

    public String h() throws IOException {
        jy f = f();
        if (f == jy.STRING || f == jy.NUMBER) {
            return ((il) s()).b();
        }
        throw new IllegalStateException("Expected " + jy.STRING + " but was " + f);
    }

    public boolean i() throws IOException {
        a(jy.BOOLEAN);
        return ((il) s()).f();
    }

    public void j() throws IOException {
        a(jy.NULL);
        s();
    }

    public double k() throws IOException {
        jy f = f();
        if (f == jy.NUMBER || f == jy.STRING) {
            double c = ((il) r()).c();
            if (p() || !(Double.isNaN(c) || Double.isInfinite(c))) {
                s();
                return c;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + c);
        }
        throw new IllegalStateException("Expected " + jy.NUMBER + " but was " + f);
    }

    public long l() throws IOException {
        jy f = f();
        if (f == jy.NUMBER || f == jy.STRING) {
            long d = ((il) r()).d();
            s();
            return d;
        }
        throw new IllegalStateException("Expected " + jy.NUMBER + " but was " + f);
    }

    public int m() throws IOException {
        jy f = f();
        if (f == jy.NUMBER || f == jy.STRING) {
            int e = ((il) r()).e();
            s();
            return e;
        }
        throw new IllegalStateException("Expected " + jy.NUMBER + " but was " + f);
    }

    public void close() throws IOException {
        this.c.clear();
        this.c.add(b);
    }

    public void n() throws IOException {
        if (f() == jy.NAME) {
            g();
        } else {
            s();
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public void o() throws IOException {
        a(jy.NAME);
        Entry entry = (Entry) ((Iterator) r()).next();
        this.c.add(entry.getValue());
        this.c.add(new il((String) entry.getKey()));
    }
}
