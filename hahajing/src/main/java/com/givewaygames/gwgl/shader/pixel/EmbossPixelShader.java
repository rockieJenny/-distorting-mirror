package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLMultiVariable;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.IGLSetValue;
import com.givewaygames.gwgl.shader.PixelShader;
import com.givewaygames.gwgl.utils.GLHelper;
import com.google.android.gms.cast.TextTrackStyle;

public class EmbossPixelShader extends PixelShader {
    public static final String UNIFORM_HEIGHT = "height";
    public static final String UNIFORM_WIDTH = "width";

    public EmbossPixelShader(Context c, GLHelper glHelper) {
        super(c, R.raw.emboss_frag);
        this.variables.add(new GLUniform("width", 1, "Width").setValidRange(0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        this.variables.add(new GLUniform("height", 1, "Height").setValidRange(0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.01f));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(1)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(0)).setSpecial(1);
        ((GLVariable) this.variables.get(1)).setSpecial(2);
        GLMultiVariable glMultiVariable = new GLMultiVariable();
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(0), 0, TextTrackStyle.DEFAULT_FONT_SCALE);
        glMultiVariable.addVariable((IGLSetValue) this.variables.get(1), 0, TextTrackStyle.DEFAULT_FONT_SCALE);
        glMultiVariable.setValidRange(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.1f);
        this.userEditableVariables.add(glMultiVariable);
        PixelShader.setupUVBounds(this.variables);
    }
}
