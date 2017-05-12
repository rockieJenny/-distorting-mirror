package com.flurry.sdk;

import java.util.Map.Entry;
import java.util.Set;

public final class ij extends ig {
    private final je<String, ig> a = new je();

    public void a(String str, ig igVar) {
        if (igVar == null) {
            igVar = ii.a;
        }
        this.a.put(str, igVar);
    }

    public Set<Entry<String, ig>> o() {
        return this.a.entrySet();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof ij) && ((ij) obj).a.equals(this.a));
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
