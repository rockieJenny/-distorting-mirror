package com.appflood.mraid;

public enum r {
    ENABLED,
    DISABLED;

    static {
        ENABLED = new r("ENABLED", 0);
        DISABLED = new r("DISABLED", 1);
        r[] rVarArr = new r[]{ENABLED, DISABLED};
    }
}
