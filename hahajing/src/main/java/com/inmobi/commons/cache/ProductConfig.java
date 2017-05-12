package com.inmobi.commons.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class ProductConfig {
    private String a = null;
    private String b = null;
    private String c = null;
    private int d = 0;
    private int e = 3;
    private int f = 60;
    private int g = 0;
    private int h = -1;
    private boolean i = true;

    Map<String, Object> a(String str) throws Exception {
        if (getProtocol().equals("json")) {
            return new JSONMapBuilder().buildMap(str);
        }
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("data", str);
        return hashMap;
    }

    public String getUrl() {
        return this.a;
    }

    public String getProtocol() {
        return this.b;
    }

    public Map<String, Object> getData() throws Exception {
        return a(this.c);
    }

    public String getRawData() {
        return this.c;
    }

    public int getExpiry() {
        return this.d;
    }

    public int getMaxRetry() {
        return this.e;
    }

    public int getTimestamp() {
        return this.h;
    }

    public boolean isSendUidMap() {
        return this.i;
    }

    public void setUrl(String str) {
        this.a = str;
    }

    public void setProtocol(String str) {
        this.b = str;
    }

    public void setData(String str) {
        this.c = str;
    }

    public void setExpiry(int i) {
        this.d = i;
    }

    public void setMaxRetry(int i) {
        this.e = i;
    }

    public void setRetryInterval(int i) {
        this.f = i;
    }

    public void setRetryNumber(int i) {
        this.g = i;
    }

    public void setTimestamp(int i) {
        this.h = i;
    }

    public void setSendUidMap(boolean z) {
        this.i = z;
    }

    public int getRetryInterval() {
        return this.f;
    }

    public int getRetryNumber() {
        return this.g;
    }
}
