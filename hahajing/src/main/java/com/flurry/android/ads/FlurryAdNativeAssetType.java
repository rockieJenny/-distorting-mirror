package com.flurry.android.ads;

public enum FlurryAdNativeAssetType {
    TEXT(1),
    IMAGE(2);
    
    private int a;

    private FlurryAdNativeAssetType(int i) {
        this.a = i;
    }

    public int getValue() {
        return this.a;
    }
}
