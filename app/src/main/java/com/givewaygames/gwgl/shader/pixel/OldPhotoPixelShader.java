package com.givewaygames.gwgl.shader.pixel;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.PixelShader;

public class OldPhotoPixelShader extends PixelShader {
    public OldPhotoPixelShader(Context c) {
        super(c, R.raw.oldphoto_frag);
    }
}
