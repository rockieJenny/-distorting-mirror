package com.givewaygames.gwgl.utils.gl.blends;

import android.opengl.GLES20;

public class GLBlendNone implements IGLBlend {
    public void setupBlend() {
        GLES20.glDisable(3042);
    }

    public void teardownBlend() {
    }
}
