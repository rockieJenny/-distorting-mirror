package com.flurry.sdk;

public enum dc {
    Unknown(0),
    Linear(1),
    NonLinearAds(2),
    CompanionAds(3);
    
    private int e;

    private dc(int i) {
        this.e = i;
    }
}
