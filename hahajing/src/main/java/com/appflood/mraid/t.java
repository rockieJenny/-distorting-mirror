package com.appflood.mraid;

public enum t {
    LOADING,
    DEFAULT,
    EXPANDED,
    HIDDEN,
    RESIZED;

    static {
        LOADING = new t("LOADING", 0);
        DEFAULT = new t("DEFAULT", 1);
        EXPANDED = new t("EXPANDED", 2);
        HIDDEN = new t("HIDDEN", 3);
        RESIZED = new t("RESIZED", 4);
        t[] tVarArr = new t[]{LOADING, DEFAULT, EXPANDED, HIDDEN, RESIZED};
    }
}
