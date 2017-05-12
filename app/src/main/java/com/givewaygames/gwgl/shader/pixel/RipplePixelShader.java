package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class RipplePixelShader extends PixelShader {
    public static final String UNIFORM_AMPLITUDE = "amplitude";
    public static final String UNIFORM_HV = "hv";
    public static final String UNIFORM_PROGRESS = "progress";
    public static final String UNIFORM_RIPPLE_AMOUNT = "rippleamount";

    public RipplePixelShader(Context c) {
        super(c, R.raw.ripple_frag);
        this.variables.add(new GLUniform("progress", 1, "Progress").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform("amplitude", 1, "Amplitude").setValidRange(0.2f, 3.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_RIPPLE_AMOUNT, 1, "Ripple Amount").setValidRange(TextTrackStyle.DEFAULT_FONT_SCALE, 16.0f, 0.01f));
        this.variables.add(new GLUniform("hv", 101, "Horizontal/Vertical"));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.02f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, 0.8f);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 15.0f);
        ((GLVariable) this.variables.get(3)).setValueAt(0, 0.0f);
        this.userEditableVariables.add(this.variables.get(2));
        this.userEditableVariables.add(this.variables.get(1));
        PixelShader.setupUVBounds(this.variables);
    }
}
