package com.flurry.sdk;

public enum dd {
    Unknown("unknown"),
    Streaming("streaming"),
    Progressive("progressive");
    
    private String d;

    private dd(String str) {
        this.d = str;
    }

    public String a() {
        return this.d;
    }

    public static dd a(String str) {
        if (Streaming.a().equals(str)) {
            return Streaming;
        }
        if (Progressive.a().equals(str)) {
            return Progressive;
        }
        return Unknown;
    }
}
