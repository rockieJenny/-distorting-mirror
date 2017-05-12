package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLMultiVariable;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.IGLSetValue;
import com.givewaygames.gwgl.shader.PixelShader;
import com.google.android.gms.cast.TextTrackStyle;

public class DilatePixelShader extends PixelShader {
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_WIDTH = "width";

    public DilatePixelShader(Context c) {
        super(c, R.raw.dilate_frag);
        this.variables.add(new GLUniform("width", 1, "Width").setValidRange(0.03f, 0.4f, 0.01f));
        this.variables.add(new GLUniform("height", 1, "Height").setValidRange(0.03f, 0.4f, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, 0.5f);
        ((GLVariable) this.variables.get(1)).setValueAt(0, 0.5f);
        ((GLVariable) this.variables.get(0)).setSpecial(1);
        ((GLVariable) this.variables.get(1)).setSpecial(2);
        GLMultiVariable glMultiVariable = new GLMultiVariable();
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(0), 0, TextTrackStyle.DEFAULT_FONT_SCALE);
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(1), 0, TextTrackStyle.DEFAULT_FONT_SCALE);
        glMultiVariable.setValidRange(0.03f, 0.4f, 0.01f);
        this.userEditableVariables.add(glMultiVariable);
        PixelShader.setupUVBounds(this.variables);
    }
}
