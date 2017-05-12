package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.GLVariable.Randomizer;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Random;

public class SketchPixelShader extends PixelShader implements Randomizer {
    public static final String UNIFORM_AMOUNT_COLOR = "color";
    public static final String UNIFORM_SKETCH_INTENSITY = "sketch";

    public SketchPixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.sketch_frag);
        this.variables.add(new GLUniform(UNIFORM_SKETCH_INTENSITY, 1, "Sketch").setValidRange(3.0f, BitmapDescriptorFactory.HUE_ORANGE, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_AMOUNT_COLOR, 1, "Color").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 8.0f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setRandomizer(this);
        this.userEditableVariables.add(this.variables.get(0));
        if (inYourFace) {
            this.userEditableVariables.add(this.variables.get(1));
        }
    }

    public void setRandomValue(Random random, GLVariable glVariable) {
        boolean doColor;
        if (random.nextFloat() >= 0.1f) {
            doColor = true;
        } else {
            doColor = false;
        }
        glVariable.setValueAt(0, doColor ? TextTrackStyle.DEFAULT_FONT_SCALE : 0.0f);
    }
}
