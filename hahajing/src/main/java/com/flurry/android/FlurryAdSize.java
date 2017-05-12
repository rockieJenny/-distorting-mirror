package com.flurry.android;

public enum FlurryAdSize {
    BANNER_TOP(1),
    BANNER_BOTTOM(2),
    FULLSCREEN(3);
    
    private int a;

    private FlurryAdSize(int i) {
        this.a = i;
    }

    public int getValue() {
        return this.a;
    }
}
