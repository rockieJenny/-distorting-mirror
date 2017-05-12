package com.flurry.sdk;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class ig {
    public boolean g() {
        return this instanceof id;
    }

    public boolean h() {
        return this instanceof ij;
    }

    public boolean i() {
        return this instanceof il;
    }

    public boolean j() {
        return this instanceof ii;
    }

    public ij k() {
        if (h()) {
            return (ij) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public id l() {
        if (g()) {
            return (id) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public il m() {
        if (i()) {
            return (il) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public boolean f() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    Boolean n() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public Number a() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String b() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double c() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long d() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int e() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            Writer stringWriter = new StringWriter();
            jz jzVar = new jz(stringWriter);
            jzVar.b(true);
            jh.a(this, jzVar);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
