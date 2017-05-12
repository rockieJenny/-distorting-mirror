package com.inmobi.re.controller.util;

import com.flurry.android.AdCreative;
import com.givewaygames.gwgl.shader.pixel.ExtractColorPixelShader;
import com.squareup.otto.Bus;

public enum TransitionStringEnum {
    DEFAULT(Bus.DEFAULT_IDENTIFIER),
    DISSOLVE("dissolve"),
    FADE(ExtractColorPixelShader.FADE_BACKGROUND),
    ROLL("roll"),
    SLIDE("slide"),
    ZOOM("zoom"),
    NONE(AdCreative.kFixNone);
    
    private String a;

    private TransitionStringEnum(String str) {
        this.a = str;
    }

    public String getText() {
        return this.a;
    }

    public static TransitionStringEnum fromString(String str) {
        if (str != null) {
            for (TransitionStringEnum transitionStringEnum : values()) {
                if (str.equalsIgnoreCase(transitionStringEnum.a)) {
                    return transitionStringEnum;
                }
            }
        }
        return null;
    }
}
