package com.givewaygames.gwgl.shader;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.givewaygames.gwgl.utils.Log;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.List;

public class PixelShader extends Shader {
    private static final String ALWAYS_HEADER = "";
    private static final String EXTERNAL_HEADER = "#extension GL_OES_EGL_image_external : require";
    private static final String EXTERNAL_TEXTURE = "uniform samplerExternalOES texture;";
    private static final String NORMAL_TEXTURE = "uniform sampler2D texture;";
    public static final String TAG = "PixelShader";
    public static final String UNIFORM_UV_BOUNDS = "uvBounds";
    public static final boolean USE_OPTIMIZED_SHADERS = false;
    int shaderId = 0;

    public PixelShader(Context c, int res) {
        super(c, res);
    }

    protected PixelShader() {
    }

    public int buildShader(int textureType) {
        this.shaderId = Shader.buildShader(fixPixelShader(this.shaderSource, textureType), 35632);
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

    public void fixTextureCoordinates(float[] textureTransform, float[] resultVector) {
        GLVariable variable = getVariable(UNIFORM_UV_BOUNDS);
        if (variable != null) {
            Matrix.multiplyMV(resultVector, 0, textureTransform, 0, new float[]{0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE}, 0);
            float x1 = resultVector[0];
            float y1 = resultVector[1];
            Matrix.multiplyMV(resultVector, 0, textureTransform, 0, new float[]{TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE}, 0);
            float x2 = resultVector[0];
            float y2 = resultVector[1];
            float lowX = Math.min(x1, x2);
            float highX = Math.max(x1, x2);
            float lowY = Math.min(y1, y2);
            float highY = Math.max(y1, y2);
            variable.setValueAt(0, lowX);
            variable.setValueAt(1, lowY);
            variable.setValueAt(2, highX);
            variable.setValueAt(3, highY);
        }
    }

    public static String fixPixelShader(String shader, int textureType) {
        boolean isExternal;
        boolean hasHeader = shader.contains(EXTERNAL_HEADER);
        if (textureType == 36197) {
            isExternal = true;
        } else {
            isExternal = false;
        }
        if (isExternal) {
            if (!hasHeader) {
                shader = "\n#extension GL_OES_EGL_image_external : require\n" + shader;
            }
            shader = shader.replace(NORMAL_TEXTURE, EXTERNAL_TEXTURE);
        } else {
            if (hasHeader) {
                shader = shader.replace(EXTERNAL_HEADER, "");
            }
            shader = "\n" + shader.replace(EXTERNAL_TEXTURE, NORMAL_TEXTURE);
        }
        return shader.replace('\u0000', ' ').replace("lowp", "mediump");
    }

    public static void setupUVBounds(List<GLVariable> variables) {
        GLUniform uvBounds = new GLUniform(UNIFORM_UV_BOUNDS, 4, "UV Bounds");
        variables.add(uvBounds);
        uvBounds.setValueAt(0, 0.0f);
        uvBounds.setValueAt(1, 0.0f);
        uvBounds.setValueAt(2, TextTrackStyle.DEFAULT_FONT_SCALE);
        uvBounds.setValueAt(3, TextTrackStyle.DEFAULT_FONT_SCALE);
    }
}
