package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.GLVariable.Randomizer;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Random;

public class SkinnyFatPixelShader extends PixelShader {
    public static final String UNIFORM_AMPLITUDE = "amplitude";
    public static final String UNIFORM_FAT_SKIN = "fatSkin";
    public static final String UNIFORM_HV = "hv";

    public SkinnyFatPixelShader(Context c, boolean inYourFace) {
        super(c, R.raw.skinny_fat_mirror_frag);
        this.variables.add(new GLUniform("amplitude", 1, "Amplitude").setValidRange(8.0f, 14.0f, TextTrackStyle.DEFAULT_FONT_SCALE));
        this.variables.add(new GLUniform("hv", 101, "Horizontal/Vertical").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE));
        this.variables.add(new GLUniform(UNIFORM_FAT_SKIN, 1, "Fat/Skinny").setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 8.0f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(2)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(1)).setRandomizer(new Randomizer() {
            public void setRandomValue(Random random, GLVariable glVariable) {
                glVariable.setValueAt(0, random.nextBoolean() ? TextTrackStyle.DEFAULT_FONT_SCALE : 0.0f);
            }
        });
        ((GLVariable) this.variables.get(2)).setRandomizer(new Randomizer() {
            public void setRandomValue(Random random, GLVariable glVariable) {
                float v = random.nextFloat();
                if (v >= 0.33f && v <= 0.5f) {
                    v -= 0.33f;
                }
                if (v >= 0.5f && v <= 0.67f) {
                    v += TextTrackStyle.DEFAULT_FONT_SCALE - 0.67f;
                }
                glVariable.setValueAt(0, v);
            }
        });
        this.userEditableVariables.add(this.variables.get(2));
        this.userEditableVariables.add(this.variables.get(1));
        if (inYourFace) {
            this.userEditableVariables.add(this.variables.get(0));
        }
        PixelShader.setupUVBounds(this.variables);
    }
}
