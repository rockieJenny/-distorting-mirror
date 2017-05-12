package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class SwirlPixelShader extends PixelShader {
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_SCALE = "scale";
    public static final String UNIFORM_SWIRL_FACTOR = "swirlfactor";
    public static final String UNIFORM_WIDTH = "width";

    public SwirlPixelShader(Context c) {
        super(c, R.raw.swirl_frag);
        this.variables.add(new GLUniform("width", 1, "Width").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform("height", 1, "Height").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_SWIRL_FACTOR, 1, "Swirl Factor").setValidRange(TextTrackStyle.DEFAULT_FONT_SCALE, 5.0f, 0.01f));
        this.variables.add(new GLUniform("scale", 1, "Scale").setValidRange(TextTrackStyle.DEFAULT_FONT_SCALE, 2.0f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 2.0f);
        ((GLVariable) this.variables.get(3)).setValueAt(0, 1.5f);
        ((GLVariable) this.variables.get(0)).setSpecial(1);
        ((GLVariable) this.variables.get(1)).setSpecial(2);
        this.userEditableVariables.add(this.variables.get(2));
        this.userEditableVariables.add(this.variables.get(3));
        PixelShader.setupUVBounds(this.variables);
    }
}
