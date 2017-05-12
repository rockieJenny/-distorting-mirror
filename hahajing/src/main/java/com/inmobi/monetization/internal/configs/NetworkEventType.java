package com.inmobi.monetization.internal.configs;

import com.inmobi.commons.metric.EventType;

public enum NetworkEventType implements EventType {
    FETCH_COMPLETE(0),
    RENDER_COMPLETE(1),
    CONNECT_ERROR(2),
    RESPONSE_ERROR(3),
    RENDER_TIMEOUT(4);
    
    private int a;

    private NetworkEventType(int i) {
        this.a = i;
    }

    public int getValue() {
        return this.a;
    }
}
