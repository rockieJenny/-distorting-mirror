package com.givewaygames.gwgl.shader;

import android.content.Context;
import android.opengl.GLES20;
import com.givewaygames.gwgl.utils.Log;

public class VertexShader extends Shader {
    public static final String TAG = "VertexShader";
    int shaderId = 0;

    public VertexShader(Context c, int res) {
        super(c, res);
    }

    public int buildShader(int type) {
        this.shaderId = Shader.buildShader(this.shaderSource, 35633);
        if (this.shaderId == 0 && Log.isE) {
            Log.e(TAG, "Pixel shader failed: " + this + "  source=" + this.shaderSource);
        }
        return this.shaderId;
    }

    public void destroyShader() {
        if (this.shaderId != 0) {
            GLES20.glDeleteShader(this.shaderId);
        }
    }
}
