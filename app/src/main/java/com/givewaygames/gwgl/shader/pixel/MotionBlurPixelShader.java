package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;

public class MotionBlurPixelShader extends PixelShader {
    public static final String UNIFORM_MOTION = "motion";

    public MotionBlurPixelShader(Context c) {
        super(c, R.raw.motion_blurr_frag);
        this.variables.add(new GLUniform(UNIFORM_MOTION, 1, "Motion").setValidRange(0.1f, 0.75f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.35f);
        this.userEditableVariables.add(this.variables.get(0));
    }
}
