package com.inmobi.monetization.internal.carb;

public class CarbInfo {
    private String a;
    private String b;

    public void setBid(String str) {
        this.a = str;
    }

    public void setInmId(String str) {
        this.b = str;
    }

    public String getBid() {
        return this.a;
    }

    public String getInmId() {
        return this.b;
    }
}
