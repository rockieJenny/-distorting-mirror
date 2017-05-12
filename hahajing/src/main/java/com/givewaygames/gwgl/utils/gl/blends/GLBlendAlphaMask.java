package com.givewaygames.gwgl.utils.gl.blends;

import android.opengl.GLES20;

public class GLBlendAlphaMask implements IGLBlend {
    public void setupBlend() {
        GLES20.glEnable(3042);
        GLES20.glColorMask(false, false, false, true);
    }

    public void teardownBlend() {
        GLES20.glColorMask(true, true, true, true);
    }
}
