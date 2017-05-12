package com.flurry.sdk;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class jo extends jz {
    private static final Writer a = new Writer() {
        public void write(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }

        public void flush() throws IOException {
            throw new AssertionError();
        }

        public void close() throws IOException {
            throw new AssertionError();
        }
    };
    private static final il b = new il("closed");
    private final List<ig> c = new ArrayList();
    private String d;
    private ig e = ii.a;

    public jo() {
        super(a);
    }

    public ig a() {
        if (this.c.isEmpty()) {
            return this.e;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.c);
    }

    private ig j() {
        return (ig) this.c.get(this.c.size() - 1);
    }

    private void a(ig igVar) {
        if (this.d != null) {
            if (!igVar.j() || i()) {
                ((ij) j()).a(this.d, igVar);
            }
            this.d = null;
        } else if (this.c.isEmpty()) {
            this.e = igVar;
        } else {
            ig j = j();
            if (j instanceof id) {
                ((id) j).a(igVar);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public jz b() throws IOException {
        ig idVar = new id();
        a(idVar);
        this.c.add(idVar);
        return this;
    }

    public jz c() throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof id) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public jz d() throws IOException {
        ig ijVar = new ij();
        a(ijVar);
        this.c.add(ijVar);
        return this;
    }

    public jz e() throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof ij) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public jz a(String str) throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof ij) {
            this.d = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public jz b(String str) throws IOException {
        if (str == null) {
            return f();
        }
        a(new il(str));
        return this;
    }

    public jz f() throws IOException {
        a(ii.a);
        return this;
    }

    public jz a(boolean z) throws IOException {
        a(new il(Boolean.valueOf(z)));
        return this;
    }

    public jz a(long j) throws IOException {
        a(new il(Long.valueOf(j)));
        return this;
    }

    public jz a(Number number) throws IOException {
        if (number == null) {
            return f();
        }
        if (!g()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        a(new il(number));
        return this;
    }

    public void flush() throws IOException {
    }

    public void close() throws IOException {
        if (this.c.isEmpty()) {
            this.c.add(b);
            return;
        }
        throw new IOException("Incomplete document");
    }
}
