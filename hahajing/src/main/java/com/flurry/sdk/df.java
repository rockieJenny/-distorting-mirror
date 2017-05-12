package com.flurry.sdk;

public enum df {
    Unknown(0),
    ClickThrough(1),
    ClickTracking(2),
    CustomClick(3);
    
    private int e;

    private df(int i) {
        this.e = i;
    }
}
