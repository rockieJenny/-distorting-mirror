package com.flurry.sdk;

public enum ah {
    NONE(0),
    QUEUED(1),
    IN_PROGRESS(2),
    COMPLETE(3),
    CANCELLED(4),
    EVICTED(5),
    ERROR(6);
    
    private final int h;

    private ah(int i) {
        this.h = i;
    }

    public int a() {
        return this.h;
    }

    public static ah a(int i) {
        switch (i) {
            case 0:
                return NONE;
            case 1:
                return QUEUED;
            case 2:
                return IN_PROGRESS;
            case 3:
                return COMPLETE;
            case 4:
                return CANCELLED;
            case 5:
                return EVICTED;
            case 6:
                return ERROR;
            default:
                return null;
        }
    }
}
