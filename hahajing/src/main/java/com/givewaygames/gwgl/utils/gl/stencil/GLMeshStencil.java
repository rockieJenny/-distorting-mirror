package com.givewaygames.gwgl.utils.gl.stencil;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.vertex.BasicVertexShader;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.google.android.gms.cast.TextTrackStyle;

public class GLMeshStencil extends GLStencil {
    private static final String TAG = GLMeshStencil.class.getName();
    GLMesh glMesh;
    float[] identity = new float[16];
    StencilMatch stencilMatch;
    float[] transform = new float[16];

    public interface StencilMatch {
        boolean setupMatch();
    }

    public static class StencilMatchNotOnes implements StencilMatch {
        public boolean setupMatch() {
            GLES20.glStencilMask(0);
            GLES20.glStencilFunc(517, 1, 255);
            GLES20.glStencilOp(7680, 7680, 7680);
            return true;
        }
    }

    public static class StencilMatchOnes implements StencilMatch {
        public boolean setupMatch() {
            GLES20.glStencilMask(0);
            GLES20.glStencilFunc(514, 1, 255);
            GLES20.glStencilOp(7680, 7680, 7680);
            return true;
        }
    }

    public GLMeshStencil(GLHelper glHelper, GLProgram glBasicProgram, GLMesh glMesh) {
        super(glHelper, glBasicProgram);
        this.glMesh = glMesh;
        Matrix.setIdentityM(this.transform, 0);
        Matrix.setIdentityM(this.identity, 0);
    }

    public boolean onInitialize() {
        if (!this.glHelper.getCanStencil()) {
            return true;
        }
        if (super.onInitialize() && this.glMesh.initialize()) {
            return true;
        }
        return false;
    }

    public float[] getTransform() {
        return this.transform;
    }

    public GLMeshStencil setMatch(StencilMatch match) {
        this.stencilMatch = match;
        return this;
    }

    public boolean setupStencil(GLProgram glProgram, long time) {
        if (!this.glHelper.getCanStencil()) {
            return true;
        }
        if (!this.glMesh.isInitialized()) {
            this.glMesh.initialize();
        }
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLES20.glColorMask(false, false, false, false);
        GLES20.glDepthMask(false);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLES20.glClearStencil(0);
        GLES20.glEnable(2960);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLES20.glStencilMask(255);
        GLES20.glStencilFunc(512, 1, 255);
        GLES20.glStencilOp(7681, 7680, 7680);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLES20.glClear(1024);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLVariable var = glProgram.getVertexShader().getVariable(BasicVertexShader.MATRIX);
        setupMatrix(var, (((float) Math.abs(1500 - (time % 3000))) / 750.0f) - TextTrackStyle.DEFAULT_FONT_SCALE);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        this.glMesh.draw(0, time);
        resetMatrix(var);
        GLES20.glColorMask(true, true, true, true);
        if (this.stencilMatch == null) {
            return true;
        }
        this.stencilMatch.setupMatch();
        return true;
    }

    public void setupMatrix(GLVariable var, float x) {
        if (var != null) {
            var.setValues(this.transform);
            var.sendValuesToOpenGL(this.glHelper);
        }
    }

    public void resetMatrix(GLVariable var) {
        if (var != null) {
            var.setValues(this.identity);
            var.sendValuesToOpenGL(this.glHelper);
        }
    }
}
