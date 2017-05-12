package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class FrozenGlassPixelShader extends PixelShader {
    public static final String DIR1 = "dir1";
    public static final String DIR2 = "dir2";
    public static final String FREEZE = "freez";

    public FrozenGlassPixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.frozen_glass_frag);
        this.variables.add(new GLUniform(FREEZE, 1, "Freeze").setValidRange(TextTrackStyle.DEFAULT_FONT_SCALE, 3.0f, 0.01f));
        this.variables.add(new GLUniform(DIR1, 2, "Fractal 1").setValidRange(-80.0f, 80.0f, 0.01f));
        this.variables.add(new GLUniform(DIR2, 2, "Fractal 2").setValidRange(-80.0f, 80.0f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.7f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, 70.0f);
        ((GLVariable) this.variables.get(1)).setValueAt(1, BitmapDescriptorFactory.HUE_YELLOW);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 15.0f);
        ((GLVariable) this.variables.get(2)).setValueAt(1, 40.0f);
        this.userEditableVariables.add(this.variables.get(0));
        if (inYourFace) {
            this.userEditableVariables.add(this.variables.get(1));
            this.userEditableVariables.add(this.variables.get(2));
        }
        PixelShader.setupUVBounds(this.variables);
    }
}
