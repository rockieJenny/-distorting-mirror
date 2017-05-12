package com.givewaygames.gwgl.shader.vertex;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.VertexShader;

public class ColorMatrixVertexShader extends VertexShader {
    public ColorMatrixVertexShader(Context c) {
        super(c, R.raw.color_matrix_vert);
    }
}
