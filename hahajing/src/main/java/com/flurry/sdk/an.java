package com.flurry.sdk;

public enum an {
    UNKNOWN(0),
    VIDEO(1),
    IMAGE(2),
    TEXT(3);
    
    private final int e;

    private an(int i) {
        this.e = i;
    }

    public int a() {
        return this.e;
    }

    public static an a(int i) {
        switch (i) {
            case 0:
                return UNKNOWN;
            case 1:
                return VIDEO;
            case 2:
                return IMAGE;
            case 3:
                return TEXT;
            default:
                return null;
        }
    }
}
