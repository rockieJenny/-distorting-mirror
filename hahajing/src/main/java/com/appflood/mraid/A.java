package com.appflood.mraid;

final class A extends w {
    private final boolean a;

    private A(boolean z) {
        this.a = z;
    }

    public static A a(boolean z) {
        return new A(z);
    }

    public final String a() {
        return "viewable: " + (this.a ? "true" : "false");
    }
}
