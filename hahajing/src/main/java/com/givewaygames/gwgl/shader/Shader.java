package com.givewaygames.gwgl.shader;

import android.content.Context;
import android.opengl.GLES20;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import java.util.ArrayList;
import java.util.List;

public abstract class Shader {
    public static final int FIRST_SHADER = 0;
    private static final String TAG = "Shader";
    public static final int TOTAL_SHADERS = 46;
    protected String shaderSource;
    protected final List<IGLSetValue> userEditableVariables = new ArrayList();
    protected List<GLVariable> variables = new ArrayList();

    public abstract int buildShader(int i);

    public abstract void destroyShader();

    public Shader(Context c, int res) {
        this.shaderSource = ShaderUtils.rawToString(c, res);
    }

    protected Shader() {
    }

    public static int buildShader(String shaderSource, int shaderType) {
        int shader = GLES20.glCreateShader(shaderType);
        GLES20.glShaderSource(shader, shaderSource);
        boolean hasCriticalError = GLErrorChecker.checkGlError(TAG);
        GLES20.glCompileShader(shader);
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        int[] status = new int[1];
        GLES20.glGetShaderiv(shader, 35713, status, 0);
        if (status[0] != 1) {
            String error = GLES20.glGetShaderInfoLog(shader);
            if (Log.isE) {
                Log.e(TAG, "Error while compiling shader: " + error);
            }
            GLErrorChecker.setGLError(new RuntimeException(error));
            GLES20.glDeleteShader(shader);
            return 0;
        }
        if (hasCriticalError) {
            shader = 0;
        }
        return shader;
    }

    public int getVariableCount() {
        return this.variables.size();
    }

    public void sendValuesToOpenGL(GLHelper glHelper) {
        for (GLVariable variable : this.variables) {
            variable.sendValuesToOpenGL(glHelper);
        }
        if (GLErrorChecker.checkGlError(TAG)) {
            Log.e(TAG, "sendValuesToOpenGL: An error occurred.", GLErrorChecker.lastError);
        }
    }

    public GLVariable getVariable(int idx) {
        return (GLVariable) this.variables.get(idx);
    }

    public GLVariable getVariable(String attrib) {
        for (GLVariable variable : this.variables) {
            if (variable.binding.equals(attrib)) {
                return variable;
            }
        }
        return null;
    }

    public void onOrientationChange() {
        for (GLVariable variable : this.variables) {
            variable.onOrientationChange();
        }
    }

    public void markVariablesDirty() {
        for (GLVariable variable : this.variables) {
            variable.forceDirty();
        }
    }

    public void copyVariableValuesFrom(Shader other) {
        int idx = 0;
        for (GLVariable var : other.variables) {
            getVariable(idx).setValues(var.values);
            idx++;
        }
    }

    public List<IGLSetValue> getUserEditableVariables() {
        return this.userEditableVariables;
    }
}
