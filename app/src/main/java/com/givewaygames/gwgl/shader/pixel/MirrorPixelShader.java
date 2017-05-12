package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.GLVariable.BinaryRandomizer;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class MirrorPixelShader extends PixelShader {
    public static final String UNIFORM_ENABLED = "enabled";
    public static final String UNIFORM_FLIP = "flip";

    public MirrorPixelShader(Context c) {
        super(c, R.raw.mirror_frag);
        this.variables.add(new GLUniform(UNIFORM_FLIP, 2, "Flip").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE));
        this.variables.add(new GLUniform(UNIFORM_ENABLED, 2, "Enabled").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(0)).setValueAt(1, 0.0f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(1, 0.0f);
        this.userEditableVariables.add(this.variables.get(0));
        this.userEditableVariables.add(this.variables.get(1));
        ((GLVariable) this.variables.get(0)).setRandomizer(new BinaryRandomizer(0.5f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE));
        BinaryRandomizer bRand = new BinaryRandomizer(0.5f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        bRand.setMustHaveOne(true);
        ((GLVariable) this.variables.get(1)).setRandomizer(bRand);
        PixelShader.setupUVBounds(this.variables);
    }
}
