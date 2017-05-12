package com.givewaygames.gwgl.utils.gl;

import android.opengl.GLES20;
import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.PixelShader;
import com.givewaygames.gwgl.shader.VertexShader;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;

public class GLProgram extends GLPiece {
    private static final String TAG = GLProgram.class.getName();
    int attribPosition;
    int attribTexCoords;
    GLHelper glHelper;
    PixelShader pixelShader;
    int programID;
    int tag;
    int textureType;
    VertexShader vertexShader;

    public GLProgram(GLHelper glHelper, int textureType, PixelShader pixelShader, VertexShader vertexShader) {
        this.glHelper = glHelper;
        this.pixelShader = pixelShader;
        this.vertexShader = vertexShader;
        this.textureType = textureType;
    }

    public int getTextureType() {
        return this.textureType;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setPixelShader(PixelShader pixelShader) {
        this.pixelShader = pixelShader;
    }

    public void setVertexShader(VertexShader vertexShader) {
        this.vertexShader = vertexShader;
    }

    public int getTag() {
        return this.tag;
    }

    public PixelShader getPixelShader() {
        return this.pixelShader;
    }

    public VertexShader getVertexShader() {
        return this.vertexShader;
    }

    public boolean onInitialize() {
        if (!this.initialized && setupProgram()) {
            return false;
        }
        return true;
    }

    protected void onRelease() {
        super.onRelease();
        if (this.programID != 0) {
            GLES20.glDeleteProgram(this.programID);
        }
        if (this.pixelShader != null) {
            this.pixelShader.destroyShader();
        }
        if (this.vertexShader != null) {
            this.vertexShader.destroyShader();
        }
    }

    public int getProgramID() {
        return this.programID;
    }

    public int getAttribPosition() {
        return this.attribPosition;
    }

    public int getTexCoords() {
        return this.attribTexCoords;
    }

    public int buildProgram() {
        int vertexShaderID;
        if (Log.isD) {
            Log.d(TAG, "Building vertex shader" + this);
        }
        if (this.vertexShader != null) {
            vertexShaderID = this.vertexShader.buildShader(this.textureType);
        } else {
            vertexShaderID = 0;
        }
        if (vertexShaderID == 0) {
            return 0;
        }
        int fragmentShaderID;
        if (Log.isD) {
            Log.d(TAG, "Building fragment shader" + this);
        }
        if (this.pixelShader != null) {
            fragmentShaderID = this.pixelShader.buildShader(this.textureType);
        } else {
            fragmentShaderID = 0;
        }
        if (fragmentShaderID == 0) {
            return 0;
        }
        if (Log.isD) {
            Log.d(TAG, "Creating program" + this);
        }
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShaderID);
        boolean hasCriticalError = GLErrorChecker.checkGlError(TAG);
        GLES20.glAttachShader(program, fragmentShaderID);
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        GLES20.glLinkProgram(program);
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        int[] status = new int[1];
        GLES20.glGetProgramiv(program, 35714, status, 0);
        if (status[0] != 1) {
            String error = GLES20.glGetProgramInfoLog(program);
            if (Log.isE) {
                Log.e(TAG, "Error while linking program:" + error);
            }
            GLES20.glDeleteShader(vertexShaderID);
            GLES20.glDeleteShader(fragmentShaderID);
            GLES20.glDeleteProgram(program);
            return 0;
        }
        if (hasCriticalError) {
            program = 0;
        }
        return program;
    }

    private boolean setupProgram() {
        this.programID = buildProgram();
        if (this.programID == 0) {
            if (Log.isW) {
                Log.w(TAG, "Failed to build program: " + this);
            }
            return true;
        }
        int i;
        int i2;
        this.attribPosition = GLES20.glGetAttribLocation(this.programID, "position");
        boolean hasCriticalError = false | GLErrorChecker.checkGlError(TAG);
        this.attribTexCoords = GLES20.glGetAttribLocation(this.programID, "texCoords");
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        GLES20.glUseProgram(this.programID);
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        GLES20.glEnableVertexAttribArray(this.attribPosition);
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        GLES20.glEnableVertexAttribArray(this.attribTexCoords);
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        for (i = 0; i < this.pixelShader.getVariableCount(); i++) {
            GLVariable variable = this.pixelShader.getVariable(i);
            variable.forceDirty();
            if (variable.initialize(this.programID)) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            hasCriticalError |= i2;
            if (hasCriticalError && Log.isE) {
                Log.e(TAG, "GLVariable, " + variable.binding + ", failed to initialize.");
            }
        }
        hasCriticalError |= GLErrorChecker.checkGlError(TAG);
        for (i = 0; i < this.vertexShader.getVariableCount(); i++) {
            variable = this.vertexShader.getVariable(i);
            variable.forceDirty();
            if (variable.initialize(this.programID)) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            hasCriticalError |= i2;
            if (hasCriticalError && Log.isE) {
                Log.e(TAG, "GLVariable, " + variable.binding + ", failed to initialize.");
            }
        }
        return hasCriticalError | GLErrorChecker.checkGlError(TAG);
    }

    public boolean draw(int pass, long time) {
        return draw(pass, time, true);
    }

    public boolean draw(int pass, long time, boolean activate) {
        if (!this.initialized) {
            initialize();
        }
        if (!this.initialized) {
            return false;
        }
        boolean success;
        GLES20.glUseProgram(this.programID);
        if (GLErrorChecker.checkGlError(TAG)) {
            success = false;
        } else {
            success = true;
        }
        if (!success) {
            if (Log.isE) {
                Log.e(TAG, "Bad setupProgram.  Trying for round 2.");
            }
            setupProgram();
            GLES20.glUseProgram(this.programID);
            if (GLErrorChecker.checkGlError(TAG)) {
                success = false;
            } else {
                success = true;
            }
        }
        if (success && activate) {
            this.glHelper.setActiveObject(GLProgram.class, this);
        }
        this.vertexShader.sendValuesToOpenGL(this.glHelper);
        this.pixelShader.sendValuesToOpenGL(this.glHelper);
        return success;
    }

    public int getNumPasses() {
        return 1;
    }

    public void copyVariableValuesFrom(GLProgram other) {
        this.pixelShader.copyVariableValuesFrom(other.pixelShader);
        this.vertexShader.copyVariableValuesFrom(other.vertexShader);
    }

    public String toString() {
        return super.toString() + ", pixel=" + this.pixelShader + ", vertex=" + this.vertexShader;
    }
}
