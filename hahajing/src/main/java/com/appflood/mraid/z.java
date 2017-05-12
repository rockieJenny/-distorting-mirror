package com.appflood.mraid;

final class z extends w {
    private final t a;

    private z(t tVar) {
        this.a = tVar;
    }

    public static z a(t tVar) {
        return new z(tVar);
    }

    public final String a() {
        return "state: '" + this.a.toString().toLowerCase() + "'";
    }
}
