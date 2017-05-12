package com.inmobi.commons.analytics.iat.impl.config;

import com.inmobi.commons.metric.EventType;

public enum AdTrackerEventType implements EventType {
    GOAL_SUCCESS(0),
    GOAL_FAILURE(1),
    GOAL_DUMPED(2);
    
    private int a;

    private AdTrackerEventType(int i) {
        this.a = i;
    }

    public int getValue() {
        return this.a;
    }
}
