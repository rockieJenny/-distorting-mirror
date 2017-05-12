package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class GlassSpherePixelShader extends PixelShader {
    public static final String UNIFORM_DIRX = "dirx";
    public static final String UNIFORM_DIRY = "diry";
    public static final String UNIFORM_MULT = "mult";
    public static final String UNIFORM_PROGRESS = "progress";
    public static final String UNIFORM_SHAPE = "shape";

    public GlassSpherePixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.glass_sphere_frag);
        this.variables.add(new GLUniform("progress", 1, "Progress").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_DIRX, 1, "Dir X").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_DIRY, 1, "Dir Y").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_SHAPE, 1, "Shape").setValidRange(TextTrackStyle.DEFAULT_FONT_SCALE, 3.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_MULT, 1, "Mult").setValidRange(0.1f, 3.0f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(3)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(4)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.userEditableVariables.add(this.variables.get(4));
        if (inYourFace) {
            this.userEditableVariables.add(this.variables.get(3));
        }
        PixelShader.setupUVBounds(this.variables);
    }
}
