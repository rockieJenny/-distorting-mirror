package com.givewaygames.gwgl.utils.gl;

import com.google.android.gms.cast.TextTrackStyle;

public class GLTransform {
    float amountEnabled = TextTrackStyle.DEFAULT_FONT_SCALE;
    GLEye glCamera;
    long lastTime = 0;
    float[] modelViewProj = new float[]{TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE};
    float[] transform = new float[]{TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE};

    public GLTransform(GLEye glCamera) {
        this.glCamera = glCamera;
    }

    public boolean isEnabled() {
        return ((double) this.amountEnabled) > 1.0E-4d;
    }

    public float amountEnabled() {
        return this.amountEnabled;
    }

    public void setEnabled(float amountEnabled) {
        this.amountEnabled = amountEnabled;
    }

    public float[] getRawTransform() {
        return this.transform;
    }

    public float[] getModelViewProjectionMatrix(long time) {
        if (time > this.lastTime) {
            this.glCamera.transformToCamera(this.modelViewProj, this.transform);
            time = this.lastTime;
        }
        return this.modelViewProj;
    }
}
