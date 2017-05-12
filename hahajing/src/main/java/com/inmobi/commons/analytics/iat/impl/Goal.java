package com.inmobi.commons.analytics.iat.impl;

import java.io.Serializable;

public class Goal implements Serializable {
    public int count;
    public boolean isDuplicate;
    public String name;
    public int retryCount;
    public long retryTime;
    public State state;

    public enum State {
        ENQUEUE_PENDING,
        ENQUEUE_REQUESTED,
        ENQUEUE_SUCCEEDED,
        REFERRER_REQUESTED,
        REFERRER_ACQUIRED,
        REPORTING_REQUESTED,
        REPORTING_COMPLETED,
        REPORTING_TIMED_OUT,
        REPORTING_FAILED
    }

    public Goal() {
        this("", 0, 0, 0, false);
    }

    public Goal(String str, int i, long j, int i2, boolean z) {
        this(str, State.ENQUEUE_PENDING, i, j, i2, z);
    }

    public Goal(String str, State state, int i, long j, int i2, boolean z) {
        int i3 = 0;
        this.name = str;
        this.state = state;
        if (i < 0) {
            i = 0;
        }
        this.count = i;
        if (j < 0) {
            j = 0;
        }
        this.retryTime = j;
        if (i2 >= 0) {
            i3 = i2;
        }
        this.retryCount = i3;
        this.isDuplicate = z;
    }
}
