package com.givewaygames.camera.ui;

import android.opengl.Matrix;
import com.givewaygames.camera.utils.TransitionPicker;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.stencil.GLMeshStencil;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GLTransitionThread extends GLPiece {
    float angle = 0.0f;
    boolean done = true;
    final float endSize = 40.0f;
    List<GLMeshStencil> glStencils = new ArrayList();
    final float scale = 0.05f;
    long startTime = -1;
    final float timeToFull = 500.0f;

    public void addStencil(GLMeshStencil stencil) {
        this.glStencils.add(stencil);
    }

    public GLTransitionThread start(TransitionPicker picker, Random r) {
        picker.pickNewTransitionShape(r);
        this.angle = r.nextFloat() * 360.0f;
        this.startTime = -1;
        this.done = false;
        if (this.done) {
            enableStencils(true);
        }
        return this;
    }

    public boolean onInitialize() {
        return true;
    }

    public boolean draw(int pass, long time) {
        if (!this.done) {
            if (this.startTime == -1) {
                this.startTime = time;
            }
            float s = (40.0f * ((((float) (System.currentTimeMillis() - this.startTime)) / 500.0f) + 0.05f)) * 0.05f;
            for (GLMeshStencil glStencil : this.glStencils) {
                float[] trans = glStencil.getTransform();
                Matrix.setIdentityM(trans, 0);
                Matrix.rotateM(trans, 0, this.angle, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
                Matrix.scaleM(trans, 0, s, s, TextTrackStyle.DEFAULT_FONT_SCALE);
            }
            this.done = s >= 40.0f;
            if (this.done) {
                enableStencils(false);
            }
        }
        return true;
    }

    private void enableStencils(boolean enable) {
        for (GLMeshStencil s : this.glStencils) {
            s.setEnabled(enable);
        }
    }

    public int getNumPasses() {
        return 1;
    }
}
