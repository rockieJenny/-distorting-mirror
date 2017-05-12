package com.flurry.sdk;

public enum af {
    NOT_STARTED(0),
    IN_PROGRESS(1),
    INCOMPLETE(2),
    COMPLETE(3),
    EVICTED(4),
    ERROR(5);
    
    private final int g;

    private af(int i) {
        this.g = i;
    }
}
