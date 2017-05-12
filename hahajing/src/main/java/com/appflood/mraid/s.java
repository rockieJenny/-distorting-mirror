package com.appflood.mraid;

public enum s {
    IMPRESSION,
    CLICK,
    PAUSE,
    RESUME,
    REWIND,
    COMPLETE,
    CLOSE,
    NEXT;

    public static s[] a() {
        return (s[]) i.clone();
    }
}
