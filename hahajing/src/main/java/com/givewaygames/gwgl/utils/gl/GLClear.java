package com.givewaygames.gwgl.utils.gl;

import android.graphics.Color;
import android.opengl.GLES20;
import com.givewaygames.gwgl.utils.GLErrorChecker;

public class GLClear extends GLPiece {
    private static final String TAG = GLClear.class.getName();
    int color;

    public GLClear(int color) {
        this.color = color;
    }

    public boolean onInitialize() {
        return true;
    }

    public boolean draw(int pass, long time) {
        GLES20.glClearColor(((float) Color.red(this.color)) / 255.0f, ((float) Color.green(this.color)) / 255.0f, ((float) Color.blue(this.color)) / 255.0f, ((float) Color.alpha(this.color)) / 255.0f);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLES20.glClear(16640);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        return true;
    }

    public int getNumPasses() {
        return 1;
    }
}
