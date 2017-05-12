package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class CellPixelShader extends PixelShader {
    public static final String UNIFORM_BLACK_SCALE = "blackScale";
    public static final String UNIFORM_NUM_COLORS = "numColors";
    public static final String UNIFORM_THRESHOLD = "threshold";

    public CellPixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.cell_shaded_frag);
        this.variables.add(new GLUniform(UNIFORM_THRESHOLD, 1, "Threshold").setValidRange(0.001f, 0.2f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_BLACK_SCALE, 1, "Black Amount").setValidRange(0.0f, 4.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_NUM_COLORS, 1, "Number of Colors").setValidRange(2.0f, 16.0f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.05f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 8.0f);
        if (inYourFace) {
            ((GLVariable) this.variables.get(2)).setValidRange(2.0f, 10.0f, 0.5f);
        }
        this.userEditableVariables.add(this.variables.get(0));
        this.userEditableVariables.add(this.variables.get(1));
        this.userEditableVariables.add(this.variables.get(2));
    }
}
