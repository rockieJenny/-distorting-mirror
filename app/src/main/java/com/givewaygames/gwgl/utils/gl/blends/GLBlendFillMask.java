package com.givewaygames.gwgl.utils.gl.blends;

import android.opengl.GLES20;

public class GLBlendFillMask implements IGLBlend {
    public void setupBlend() {
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(772, 773);
    }

    public void teardownBlend() {
    }
}
