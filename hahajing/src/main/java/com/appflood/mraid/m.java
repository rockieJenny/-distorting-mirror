package com.appflood.mraid;

public enum m {
    ENABLED,
    DISABLED;

    static {
        ENABLED = new m("ENABLED", 0);
        DISABLED = new m("DISABLED", 1);
        m[] mVarArr = new m[]{ENABLED, DISABLED};
    }
}
