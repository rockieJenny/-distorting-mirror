package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class HueShiftPixelShader extends PixelShader {
    public static final String UNIFORM_SATURATION = "saturation";
    public static final String UNIFORM_SHIFT = "shift";
    public static final String UNIFORM_VALUE = "value";

    public HueShiftPixelShader(Context c) {
        super(c, R.raw.hue_shift_frag);
        this.variables.add(new GLUniform(UNIFORM_SHIFT, 1, "Shift").setValidRange(0.05f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_SATURATION, 1, "Saturation").setValidRange(0.0f, 2.0f, 0.01f));
        this.variables.add(new GLUniform("value", 1, "Value").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.userEditableVariables.add(this.variables.get(0));
    }
}
