package com.givewaygames.gwgl.shader;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.opengl.GLES20;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.google.android.gms.cast.TextTrackStyle;

public class GLUniform extends GLVariable {
    public static final int TYPE_UNIFORM_MATRIX_4 = 16;

    public GLUniform(String binding, int type, String description) {
        super(binding, type, description);
    }

    public boolean initialize(int programID) {
        setAttribIndex(GLES20.glGetUniformLocation(programID, this.binding));
        return (GLErrorChecker.checkGlError(GLVariable.TAG) || this.attribIndex == -1) ? false : true;
    }

    public void saveValues(Editor sharedPreferences, String prefix) {
        for (int i = 0; i < this.values.length; i++) {
            sharedPreferences.putFloat(prefix + i, this.values[i]);
        }
    }

    public void loadValues(SharedPreferences sharedPreferences, String prefix) {
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = sharedPreferences.getFloat(prefix + i, this.values[i]);
        }
    }

    public void sendValuesToOpenGL(GLHelper glHelper) {
        if (this.dirty) {
            float s;
            switch (this.specialValue) {
                case 1:
                    s = (float) glHelper.getTextureWidth();
                    break;
                case 2:
                    s = (float) glHelper.getTextureHeight();
                    break;
                default:
                    s = TextTrackStyle.DEFAULT_FONT_SCALE;
                    break;
            }
            switch (this.size) {
                case 1:
                    GLES20.glUniform1f(getAttribIndex(), this.values[0] * s);
                    break;
                case 2:
                    GLES20.glUniform2f(getAttribIndex(), this.values[0] * s, this.values[1] * s);
                    break;
                case 3:
                    GLES20.glUniform3f(getAttribIndex(), this.values[0] * s, this.values[1] * s, this.values[2] * s);
                    break;
                case 4:
                    GLES20.glUniform4f(getAttribIndex(), this.values[0] * s, this.values[1] * s, this.values[2] * s, this.values[3] * s);
                    break;
                case 16:
                    GLES20.glUniformMatrix4fv(getAttribIndex(), 1, false, this.values, 0);
                    break;
                case 101:
                    GLES20.glUniform1i(getAttribIndex(), (int) (this.values[0] + 0.5f));
                    break;
            }
            if (GLErrorChecker.checkGlError(GLVariable.TAG)) {
                Log.e(GLVariable.TAG, "sendValuesToOpenGL: An error occurred.", GLErrorChecker.lastError);
            }
            this.dirty = false;
        }
    }
}
