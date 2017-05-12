package com.google.android.gms.analytics;

import android.text.TextUtils;

public class ab {
    private String Bu;
    private final long Bv;
    private final long Bw;
    private final String Bx;
    private String By;
    private String Bz = "https:";

    public ab(String str, long j, long j2, String str2) {
        this.Bu = str;
        this.Bv = j;
        this.Bw = j2;
        this.Bx = str2;
    }

    public void aj(String str) {
        this.Bu = str;
    }

    public void ak(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim())) {
            this.By = str;
            if (str.toLowerCase().startsWith("http:")) {
                this.Bz = "http:";
            }
        }
    }

    public String fa() {
        return this.Bu;
    }

    public long fb() {
        return this.Bv;
    }

    public long fc() {
        return this.Bw;
    }

    public String fd() {
        return this.Bz;
    }
}
