package com.inmobi.monetization.internal.imai.db;

import com.inmobi.monetization.internal.imai.IMAICore;

public class ClickData {
    private long a;
    private String b;
    private boolean c;
    private int d;
    private boolean e;
    private long f;

    public ClickData(String str, boolean z, boolean z2, int i) {
        setClickId((long) IMAICore.getRandomNumber());
        setClickUrl(str);
        setFollowRedirect(z);
        setPingWv(z2);
        setRetryCount(i);
        setTimestamp(System.currentTimeMillis());
    }

    public long getClickId() {
        return this.a;
    }

    public void setClickId(long j) {
        this.a = j;
    }

    public String getClickUrl() {
        return this.b;
    }

    public void setClickUrl(String str) {
        this.b = str;
    }

    public long getTimestamp() {
        return this.f;
    }

    public void setTimestamp(long j) {
        this.f = j;
    }

    public boolean isPingWv() {
        return this.c;
    }

    public void setPingWv(boolean z) {
        this.c = z;
    }

    public int getRetryCount() {
        return this.d;
    }

    public void setRetryCount(int i) {
        this.d = i;
    }

    public boolean isFollowRedirects() {
        return this.e;
    }

    public void setFollowRedirect(boolean z) {
        this.e = z;
    }
}
