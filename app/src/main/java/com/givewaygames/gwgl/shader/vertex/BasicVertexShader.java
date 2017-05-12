package com.givewaygames.gwgl.shader.vertex;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLUniform;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.VertexShader;
import com.google.android.gms.cast.TextTrackStyle;

public class BasicVertexShader extends VertexShader {
    public static final String MATRIX = "modelViewProj";

    public BasicVertexShader(Context c) {
        super(c, R.raw.basic_vert);
        setupMVP();
    }

    public BasicVertexShader(Context c, int res) {
        super(c, res);
        setupMVP();
    }

    private void setupMVP() {
        this.variables.add(new GLUniform(MATRIX, 16, "Model View Projection"));
        ((GLVariable) this.variables.get(0)).setValueAt(0, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(0)).setValueAt(5, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(0)).setValueAt(10, TextTrackStyle.DEFAULT_FONT_SCALE);
        ((GLVariable) this.variables.get(0)).setValueAt(15, TextTrackStyle.DEFAULT_FONT_SCALE);
    }
}
