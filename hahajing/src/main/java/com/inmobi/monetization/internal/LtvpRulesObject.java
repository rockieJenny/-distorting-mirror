package com.inmobi.monetization.internal;

import java.util.HashMap;

public class LtvpRulesObject {
    private String a;
    private long b;
    private HashMap<String, Integer> c;
    private long d;
    private long e;

    public String getRuleId() {
        return this.a;
    }

    public void setRuleId(String str) {
        this.a = str;
    }

    public long getTimeStamp() {
        return this.b;
    }

    public void setTimeStamp(long j) {
        this.b = j;
    }

    public HashMap<String, Integer> getRules() {
        return this.c;
    }

    public void setRules(HashMap<String, Integer> hashMap) {
        this.c = hashMap;
    }

    public long getSoftExpiry() {
        return this.d;
    }

    public void setSoftExpiry(long j) {
        this.d = j;
    }

    public long getHardExpiry() {
        return this.e;
    }

    public void setHardExpiry(long j) {
        this.e = j;
    }
}
