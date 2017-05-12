package com.givewaygames.gwgl.shader;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.opengl.GLES20;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;

public class GLAttribute extends GLVariable {
    public GLAttribute(String binding, int type, String description) {
        super(binding, type, description);
    }

    public boolean initialize(int programID) {
        setAttribIndex(GLES20.glGetAttribLocation(programID, this.binding));
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
            switch (this.size) {
                case 1:
                    GLES20.glVertexAttrib1f(getAttribIndex(), this.values[0]);
                    break;
                case 2:
                    GLES20.glVertexAttrib2f(getAttribIndex(), this.values[0], this.values[1]);
                    break;
                case 3:
                    GLES20.glVertexAttrib3f(getAttribIndex(), this.values[0], this.values[1], this.values[2]);
                    break;
                case 4:
                    GLES20.glVertexAttrib4f(getAttribIndex(), this.values[0], this.values[1], this.values[2], this.values[3]);
                    break;
            }
            if (GLErrorChecker.checkGlError(GLVariable.TAG)) {
                Log.e(GLVariable.TAG, "sendValuesToOpenGL: An error occurred.", GLErrorChecker.lastError);
            }
            this.dirty = false;
        }
    }
}
