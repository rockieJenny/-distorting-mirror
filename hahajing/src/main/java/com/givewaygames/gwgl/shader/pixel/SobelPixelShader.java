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

public class SobelPixelShader extends PixelShader implements Randomizer {
    public static final String UNIFORM_BLACK_OR_WHITE = "blackOrWhite";
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_WIDTH = "width";

    public SobelPixelShader(Context c) {
        super(c, R.raw.sobel_frag);
        this.variables.add(new GLUniform("width", 1, "Width"));
        this.variables.add(new GLUniform("height", 1, "Height"));
        this.variables.add(new GLUniform(UNIFORM_BLACK_OR_WHITE, 1, "Black or White"));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(0)).setSpecial(1);
        ((GLVariable) this.variables.get(1)).setSpecial(2);
        ((GLVariable) this.variables.get(2)).setRandomizer(this);
        GLMultiVariable glMultiVariable = new GLMultiVariable();
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(0), 0, TextTrackStyle.DEFAULT_FONT_SCALE);
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(1), 0, TextTrackStyle.DEFAULT_FONT_SCALE);
        glMultiVariable.setValidRange(0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f);
        this.userEditableVariables.add(glMultiVariable);
        this.userEditableVariables.add(this.variables.get(2));
        PixelShader.setupUVBounds(this.variables);
    }

    public void setRandomValue(Random random, GLVariable glVariable) {
        boolean doBlack;
        if (random.nextFloat() >= 0.5f) {
            doBlack = true;
        } else {
            doBlack = false;
        }
        glVariable.setValueAt(0, doBlack ? 0.0f : TextTrackStyle.DEFAULT_FONT_SCALE);
    }
}
