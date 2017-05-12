package com.appflood.mraid;

public enum o {
    ALWAYS_VISIBLE,
    ALWAYS_HIDDEN,
    AD_CONTROLLED;

    static {
        ALWAYS_VISIBLE = new o("ALWAYS_VISIBLE", 0);
        ALWAYS_HIDDEN = new o("ALWAYS_HIDDEN", 1);
        AD_CONTROLLED = new o("AD_CONTROLLED", 2);
        o[] oVarArr = new o[]{ALWAYS_VISIBLE, ALWAYS_HIDDEN, AD_CONTROLLED};
    }
}
