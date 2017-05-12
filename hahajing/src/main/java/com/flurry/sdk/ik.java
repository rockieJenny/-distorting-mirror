package com.flurry.sdk;

public class ik extends RuntimeException {
    public ik(String str) {
        super(str);
    }

    public ik(String str, Throwable th) {
        super(str, th);
    }

    public ik(Throwable th) {
        super(th);
    }
}
