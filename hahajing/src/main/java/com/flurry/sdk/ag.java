package com.flurry.sdk;

public enum ag {
    STREAM_ONLY(0),
    CACHE_ONLY(1),
    CACHE_OR_STREAM(2);
    
    private final int d;

    private ag(int i) {
        this.d = i;
    }

    public static ag a(int i) {
        switch (i) {
            case 0:
                return STREAM_ONLY;
            case 1:
                return CACHE_ONLY;
            case 2:
                return CACHE_OR_STREAM;
            default:
                return null;
        }
    }
}
