package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class CellShadedPixelShader extends PixelShader {
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_SKETCH_INTENSITY = "sketchIntensity";
    public static final String UNIFORM_WIDTH = "width";

    public CellShadedPixelShader(Context c) {
        super(c, R.raw.cell_shading_frag);
        this.variables.add(new GLUniform("width", 1, "Width"));
        this.variables.add(new GLUniform("height", 1, "Height"));
        this.variables.add(new GLUniform(UNIFORM_SKETCH_INTENSITY, 1, "Intensity").setValidRange(0.4f, 0.9f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 0.6f);
        ((GLVariable) this.variables.get(0)).setSpecial(1);
        ((GLVariable) this.variables.get(1)).setSpecial(2);
        this.userEditableVariables.add(this.variables.get(2));
    }
}
