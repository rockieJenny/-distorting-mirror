package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class TexturedMosaicPixelShader extends PixelShader {
    public static final String UNIFORM_SCALE = "scale";

    public TexturedMosaicPixelShader(Context c) {
        super(c, R.raw.textured_mosaic_frag);
        this.variables.add(new GLUniform("scale", 1, "Scale").setValidRange(0.75f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.userEditableVariables.add(this.variables.get(0));
        PixelShader.setupUVBounds(this.variables);
    }
}
