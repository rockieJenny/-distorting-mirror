package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLMultiVariable;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.GLVariable.Randomizer;
import com.givewaygames.gwgl.shader.IGLSetValue;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Random;

public class NoctovisionPixelShader extends PixelShader {
    public static final String UNIFORM_LENS_RAD = "lensRad";

    public NoctovisionPixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.noctovision_frag);
        this.variables.add(new GLUniform(UNIFORM_LENS_RAD, 2, "Lens Radius"));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.5f);
        ((GLVariable) this.variables.get(0)).setValueAt(1, 0.4f);
        if (inYourFace) {
            ((GLVariable) this.variables.get(0)).setRandomizer(new Randomizer() {
                public void setRandomValue(Random random, GLVariable glVariable) {
                    float start = (random.nextFloat() * 1.5f) + 0.2f;
                    float range = (random.nextFloat() * start) * 2.0f;
                    ((GLVariable) NoctovisionPixelShader.this.variables.get(0)).setValueAt(0, start);
                    ((GLVariable) NoctovisionPixelShader.this.variables.get(0)).setValueAt(1, start - range);
                }
            });
            this.userEditableVariables.add(this.variables.get(0));
            return;
        }
        GLMultiVariable glMultiVariable = new GLMultiVariable();
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(0), 0, TextTrackStyle.DEFAULT_FONT_SCALE);
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(0), 1, 0.5f);
        glMultiVariable.setValidRange(0.5f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f);
        this.userEditableVariables.add(glMultiVariable);
    }
}
