package com.inmobi.commons.ads.cache;

public class AdData {
    private long a;
    private String b;
    private long c;
    private String d;
    private String e;

    public long getAdId() {
        return this.a;
    }

    public void setAdId(long j) {
        this.a = j;
    }

    public String getAppId() {
        return this.b;
    }

    public void setAppId(String str) {
        this.b = str;
    }

    public long getTimestamp() {
        return this.c;
    }

    public void setTimestamp(long j) {
        this.c = j;
    }

    public String getContent() {
        return this.d;
    }

    public void setContent(String str) {
        this.d = str;
    }

    public String getAdType() {
        return this.e;
    }

    public void setAdType(String str) {
        this.e = str;
    }
}
