package com.givewaygames.gwgl.utils.gl.stencil;

import android.opengl.GLES20;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.GLProgram;

public abstract class GLStencil extends GLPiece {
    private static final String TAG = GLStencil.class.getName();
    GLProgram glBasicProgram;
    GLHelper glHelper;

    public abstract boolean setupStencil(GLProgram gLProgram, long j);

    public GLStencil(GLHelper glHelper, GLProgram basicProgram) {
        this.glHelper = glHelper;
        this.glBasicProgram = basicProgram;
    }

    public boolean onInitialize() {
        if (this.glHelper.getCanStencil()) {
            return this.glBasicProgram.initialize();
        }
        return true;
    }

    protected void onRelease() {
        super.onRelease();
        if (this.glBasicProgram != null) {
            this.glBasicProgram.release();
        }
    }

    public boolean draw(int pass, long time) {
        int i = 1;
        if (!this.glHelper.getCanStencil()) {
            return true;
        }
        GLProgram activeProgram = (GLProgram) this.glHelper.getActiveObject(GLProgram.class);
        this.glBasicProgram.draw(pass, time, false);
        boolean valid = setupStencil(this.glBasicProgram, time);
        GLES20.glUseProgram(activeProgram.getProgramID());
        if (GLErrorChecker.checkGlError(TAG)) {
            i = 0;
        }
        return valid & i;
    }

    public int getNumPasses() {
        return 1;
    }
}
