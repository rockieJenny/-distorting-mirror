package com.appflood.mraid;

final class x extends w {
    private final int a;
    private final int b;

    private x(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public static x a(int i, int i2) {
        return new x(i, i2);
    }

    public final String a() {
        return "screenSize: { width: " + this.a + ", height: " + this.b + " }";
    }
}
