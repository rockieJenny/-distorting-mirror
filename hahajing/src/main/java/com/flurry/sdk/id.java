package com.flurry.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class id extends ig implements Iterable<ig> {
    private final List<ig> a = new ArrayList();

    public void a(ig igVar) {
        if (igVar == null) {
            igVar = ii.a;
        }
        this.a.add(igVar);
    }

    public Iterator<ig> iterator() {
        return this.a.iterator();
    }

    public Number a() {
        if (this.a.size() == 1) {
            return ((ig) this.a.get(0)).a();
        }
        throw new IllegalStateException();
    }

    public String b() {
        if (this.a.size() == 1) {
            return ((ig) this.a.get(0)).b();
        }
        throw new IllegalStateException();
    }

    public double c() {
        if (this.a.size() == 1) {
            return ((ig) this.a.get(0)).c();
        }
        throw new IllegalStateException();
    }

    public long d() {
        if (this.a.size() == 1) {
            return ((ig) this.a.get(0)).d();
        }
        throw new IllegalStateException();
    }

    public int e() {
        if (this.a.size() == 1) {
            return ((ig) this.a.get(0)).e();
        }
        throw new IllegalStateException();
    }

    public boolean f() {
        if (this.a.size() == 1) {
            return ((ig) this.a.get(0)).f();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof id) && ((id) obj).a.equals(this.a));
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
