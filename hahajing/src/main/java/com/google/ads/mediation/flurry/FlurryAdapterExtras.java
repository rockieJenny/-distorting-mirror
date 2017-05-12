package com.google.ads.mediation.flurry;

import com.google.ads.mediation.NetworkExtras;

public final class FlurryAdapterExtras implements NetworkExtras {
    private String a;
    private boolean b;

    public FlurryAdapterExtras setAdSpace(String adSpace) {
        this.a = adSpace;
        return this;
    }

    public FlurryAdapterExtras clearAdSpace() {
        return setAdSpace(null);
    }

    public String getAdSpace() {
        return this.a;
    }

    public FlurryAdapterExtras setLogEnabled(boolean isEnabled) {
        this.b = isEnabled;
        return this;
    }

    public boolean isLogEnabled() {
        return this.b;
    }
}
