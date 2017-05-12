package com.inmobi.monetization.internal;

public class NativeAdObject {
    String a = null;
    String b = null;
    String c = null;
    e d = null;

    public NativeAdObject(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public String getPubContent() {
        return this.a;
    }

    public String getContextCode() {
        return this.b;
    }

    public String getNameSpace() {
        return this.c;
    }
}
