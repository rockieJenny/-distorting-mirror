package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class NeonPixelShader extends PixelShader {
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_NEON = "neon";
    public static final String UNIFORM_WIDTH = "width";

    public NeonPixelShader(Context c) {
        super(c, R.raw.neon_frag);
        this.variables.add(new GLUniform(UNIFORM_NEON, 1, "Amount").setValidRange(0.1f, 0.5f, 0.01f));
        this.variables.add(new GLUniform("width", 1, "Width").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform("height", 1, "Height").setValidRange(0.5f, 3.0f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setSpecial(1);
        ((GLVariable) this.variables.get(2)).setSpecial(2);
        this.userEditableVariables.add(this.variables.get(0));
        PixelShader.setupUVBounds(this.variables);
    }
}
