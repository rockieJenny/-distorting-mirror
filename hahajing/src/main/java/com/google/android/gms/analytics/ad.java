package com.google.android.gms.analytics;

class ad implements ah {
    private final long BF;
    private final int BG;
    private double BH;
    private long BI;
    private final Object BJ;
    private final String BK;

    public ad(int i, long j, String str) {
        this.BJ = new Object();
        this.BG = i;
        this.BH = (double) this.BG;
        this.BF = j;
        this.BK = str;
    }

    public ad(String str) {
        this(60, 2000, str);
    }

    public boolean fe() {
        boolean z;
        synchronized (this.BJ) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.BH < ((double) this.BG)) {
                double d = ((double) (currentTimeMillis - this.BI)) / ((double) this.BF);
                if (d > 0.0d) {
                    this.BH = Math.min((double) this.BG, d + this.BH);
                }
            }
            this.BI = currentTimeMillis;
            if (this.BH >= 1.0d) {
                this.BH -= 1.0d;
                z = true;
            } else {
                ae.W("Excessive " + this.BK + " detected; call ignored.");
                z = false;
            }
        }
        return z;
    }
}
