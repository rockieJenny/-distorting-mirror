package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;

public class BasicPixelShader extends PixelShader {
    public BasicPixelShader(Context c) {
        super(c, R.raw.basic_frag);
    }

    public BasicPixelShader(Context c, int res) {
        super(c, res);
    }

    public void addToVariables(GLVariable glVariable) {
        this.variables.add(glVariable);
    }

    public void setupUVBounds() {
        PixelShader.setupUVBounds(this.variables);
    }
}
