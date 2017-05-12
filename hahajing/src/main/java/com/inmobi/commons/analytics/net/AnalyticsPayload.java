package com.inmobi.commons.analytics.net;

import java.util.List;

public class AnalyticsPayload {
    private String a;
    private List<Long> b;
    private String c;
    private int d;

    public int getPayloadSize() {
        return this.d;
    }

    public void setPayloadSize(int i) {
        this.d = i;
    }

    public AnalyticsPayload(String str, List<Long> list) {
        this.a = str;
        this.b = list;
    }

    public String getOnlyEvent() {
        return this.a;
    }

    public List<Long> getTableIdList() {
        return this.b;
    }

    public String getCompletePayload() {
        return this.c;
    }

    public void setCompletePayload(String str) {
        this.c = str;
    }
}
