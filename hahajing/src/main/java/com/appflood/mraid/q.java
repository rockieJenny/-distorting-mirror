package com.appflood.mraid;

public enum q {
    INLINE,
    INTERSTITIAL;

    static {
        INLINE = new q("INLINE", 0);
        INTERSTITIAL = new q("INTERSTITIAL", 1);
        q[] qVarArr = new q[]{INLINE, INTERSTITIAL};
    }
}
