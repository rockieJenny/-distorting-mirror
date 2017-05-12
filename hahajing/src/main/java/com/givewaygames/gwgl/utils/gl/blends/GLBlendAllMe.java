package com.givewaygames.gwgl.utils.gl.blends;

import android.opengl.GLES20;

public class GLBlendAllMe implements IGLBlend {
    public void setupBlend() {
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(1, 0);
    }

    public void teardownBlend() {
    }
}
