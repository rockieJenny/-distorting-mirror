package com.inmobi.re.controller.util;

import com.flurry.android.AdCreative;

public enum NavigationStringEnum {
    NONE(AdCreative.kFixNone),
    CLOSE("close"),
    BACK("back"),
    FORWARD("forward"),
    REFRESH("refresh");
    
    private String a;

    private NavigationStringEnum(String str) {
        this.a = str;
    }

    public String getText() {
        return this.a;
    }

    public static NavigationStringEnum fromString(String str) {
        if (str != null) {
            for (NavigationStringEnum navigationStringEnum : values()) {
                if (str.equalsIgnoreCase(navigationStringEnum.a)) {
                    return navigationStringEnum;
                }
            }
        }
        return null;
    }
}
