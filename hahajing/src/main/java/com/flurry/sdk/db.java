package com.flurry.sdk;

public enum db {
    Unknown(0),
    InLine(1),
    Wrapper(2);
    
    private int d;

    private db(int i) {
        this.d = i;
    }
}
