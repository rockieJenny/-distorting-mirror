package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.PixelShader;

public class SolidPixelShader extends PixelShader {
    public SolidPixelShader(Context c) {
        super(c, R.raw.solid_frag);
    }
}
