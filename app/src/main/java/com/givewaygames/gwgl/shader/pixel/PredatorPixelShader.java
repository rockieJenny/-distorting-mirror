package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.PixelShader;

public class PredatorPixelShader extends PixelShader {
    public PredatorPixelShader(Context c) {
        super(c, R.raw.predatorfrag);
    }
}
