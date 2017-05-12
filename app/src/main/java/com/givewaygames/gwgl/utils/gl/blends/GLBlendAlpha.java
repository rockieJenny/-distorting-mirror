package com.givewaygames.gwgl.utils.gl.blends;

import android.opengl.GLES20;

public class GLBlendAlpha implements IGLBlend {
    public void setupBlend() {
        GLES20.glDisable(2929);
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
    }

    public void teardownBlend() {
    }
}
