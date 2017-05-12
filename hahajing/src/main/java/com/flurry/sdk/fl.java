package com.flurry.sdk;

public enum fl {
    DeviceId(0, true),
    Sha1Imei(5, false),
    AndroidAdvertisingId(13, true);
    
    public final int d;
    public final boolean e;

    private fl(int i, boolean z) {
        this.d = i;
        this.e = z;
    }
}
