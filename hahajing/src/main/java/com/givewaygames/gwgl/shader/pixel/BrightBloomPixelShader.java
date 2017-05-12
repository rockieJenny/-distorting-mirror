package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.PixelShader;

public class BrightBloomPixelShader extends PixelShader {
    public BrightBloomPixelShader(Context c) {
        super(c, R.raw.bright_bloom_frag);
    }
}
