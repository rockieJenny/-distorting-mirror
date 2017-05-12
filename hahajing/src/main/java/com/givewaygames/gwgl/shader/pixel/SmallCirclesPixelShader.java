package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.GLVariable.Randomizer;
import com.givewaygames.gwgl.shader.PixelShader;
import com.givewaygames.gwgl.utils.gl.meshes.GLFlingMesh;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Random;

public class SmallCirclesPixelShader extends PixelShader implements Randomizer {
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_HIGHLIGHT = "highlight";
    public static final String UNIFORM_INNER_SIZE = "innerSize";
    public static final String UNIFORM_SIZE = "size";
    public static final String UNIFORM_WIDTH = "width";

    public SmallCirclesPixelShader(Context c) {
        super(c, R.raw.small_circles_frag);
        this.variables.add(new GLUniform("size", 1, "Size").setValidRange(0.01f, 0.1f, 0.01f));
        this.variables.add(new GLUniform("width", 1, "Width").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform("height", 1, "Height").setValidRange(0.5f, 3.0f, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_INNER_SIZE, 1, "Circle Size").setValidRange(0.3f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        this.variables.add(new GLUniform(UNIFORM_HIGHLIGHT, 1, "Highlight"));
        ((GLVariable) this.variables.get(0)).setValueAt(0, GLFlingMesh.minIndexSize);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(2)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(3)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(4)).setValueAt(0, 0.0f);
        ((GLVariable) this.variables.get(4)).setRandomizer(this);
        ((GLVariable) this.variables.get(1)).setSpecial(1);
        ((GLVariable) this.variables.get(2)).setSpecial(2);
        this.userEditableVariables.add(this.variables.get(0));
        this.userEditableVariables.add(this.variables.get(3));
        this.userEditableVariables.add(this.variables.get(4));
        PixelShader.setupUVBounds(this.variables);
    }

    public void setRandomValue(Random random, GLVariable glVariable) {
        if (random.nextBoolean()) {
            glVariable.setValueAt(0, 0.0f);
        } else {
            glVariable.setValueAt(0, (random.nextFloat() * 0.5f) + 0.5f);
        }
    }
}
