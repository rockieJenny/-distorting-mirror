package com.jirbo.adcolony;

import java.io.Serializable;

public abstract class AdColonyAd implements Serializable {
    static final int a = 0;
    static final int b = 1;
    static final int c = 2;
    static final int d = 3;
    static final int e = 4;
    int f = 0;
    String g;
    ab h;
    a i;
    String j = "";
    String k = "";
    String l = "";
    String m = "";
    double n = 0.0d;
    double o = 0.0d;
    int p;
    boolean q;
    boolean r;
    boolean s;
    boolean t;
    AdColonyIAPEngagement u = AdColonyIAPEngagement.NONE;

    abstract void a();

    abstract boolean a(boolean z);

    abstract boolean b();

    abstract boolean isReady();

    public boolean shown() {
        return this.f == 4;
    }

    public boolean notShown() {
        return this.f != 4;
    }

    public boolean canceled() {
        return this.f == 1;
    }

    public boolean noFill() {
        return this.f == 2;
    }

    public boolean skipped() {
        return this.f == 3;
    }

    public boolean iapEnabled() {
        return this.t;
    }

    public AdColonyIAPEngagement iapEngagementType() {
        return this.u;
    }

    public String iapProductID() {
        return this.m;
    }

    public int getAvailableViews() {
        if (isReady() && c()) {
            return this.h.d();
        }
        return 0;
    }

    boolean c() {
        return b(false);
    }

    boolean b(boolean z) {
        if (this.f == 4) {
            return true;
        }
        if (!isReady() && !z) {
            return false;
        }
        if (!a(true) && z) {
            return false;
        }
        boolean z2;
        this.h = a.l.h(this.g);
        this.i = z ? this.h.j() : this.h.i();
        if (this.i != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        return z2;
    }
}
