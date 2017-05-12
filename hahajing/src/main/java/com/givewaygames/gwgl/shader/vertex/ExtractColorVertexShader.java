package com.givewaygames.gwgl.shader.vertex;

import android.content.Context;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.VertexShader;

public class ExtractColorVertexShader extends VertexShader {
    public ExtractColorVertexShader(Context c) {
        super(c, R.raw.extract_hsv_vert);
    }
}
