package com.givewaygames.gwgl.utils.gl;

import android.opengl.GLES20;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.TextureSlotProvider;
import com.givewaygames.gwgl.utils.gl.blends.IGLBlend;

public class GLTexture extends GLPiece {
    private static final String TAG = GLTexture.class.getName();
    protected TextureSlotProvider slotProvider;
    protected int textureID = -1;
    protected String textureName;
    protected int textureSlot = -1;
    protected int textureType = -1;

    protected GLTexture() {
    }

    public GLTexture(TextureSlotProvider slotProvider, String textureName) {
        this.slotProvider = slotProvider;
        this.textureName = textureName;
    }

    public GLTexture(TextureSlotProvider slotProvider, String textureName, int textureSlot) {
        this.slotProvider = slotProvider;
        this.textureName = textureName;
        this.textureSlot = textureSlot;
    }

    public boolean onInitialize() {
        boolean hasCriticalError = false;
        if (isInitialized()) {
            release();
        }
        if (this.textureSlot == -1) {
            this.textureSlot = this.slotProvider.getNextTextureSlot();
        }
        this.textureID = loadEmptyTexture();
        if (this.textureID == 0) {
            if (Log.isW) {
                Log.w(TAG, "Failed to load texture");
            }
            hasCriticalError = true;
        }
        return !hasCriticalError;
    }

    protected void onRelease() {
        super.onRelease();
        if (this.textureID == 0) {
            GLES20.glDeleteTextures(1, new int[]{this.textureID}, 0);
            this.textureID = 0;
        }
    }

    public boolean draw(int pass, long time) {
        return true;
    }

    public int getNumPasses() {
        return 0;
    }

    public int getTextureType() {
        return this.textureType != -1 ? this.textureType : this.slotProvider.getTextureType(this.textureName);
    }

    public int getTextureID() {
        return this.textureID;
    }

    public int slotProvidedTextureID() {
        return this.slotProvider.getGLTextureID(this.textureSlot);
    }

    public void setTextureType(int textureType) {
        this.textureType = textureType;
    }

    private int loadEmptyTexture() {
        int[] textures = new int[1];
        int textureType = getTextureType();
        GLES20.glActiveTexture(slotProvidedTextureID());
        GLES20.glGenTextures(1, textures, 0);
        if (textures[0] == 0) {
            GLErrorChecker.checkGlError(TAG);
            return 0;
        }
        int texture = textures[0];
        GLES20.glBindTexture(textureType, texture);
        GLES20.glTexParameteri(textureType, 10241, 9729);
        GLES20.glTexParameteri(textureType, 10240, 9729);
        GLES20.glTexParameteri(textureType, 10242, 33071);
        GLES20.glTexParameteri(textureType, 10243, 33071);
        return texture;
    }

    public boolean bindTexture(IGLBlend blend) {
        GLES20.glActiveTexture(slotProvidedTextureID());
        if (blend != null) {
            blend.setupBlend();
        }
        GLES20.glBindTexture(getTextureType(), this.textureID);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        return true;
    }

    public boolean attachToProgram(long glProgramID) {
        int uniformTexture = GLES20.glGetUniformLocation((int) glProgramID, this.textureName);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLES20.glUniform1i(uniformTexture, this.textureSlot);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        return true;
    }

    public boolean unbindTexture(IGLBlend blend) {
        if (blend != null) {
            blend.teardownBlend();
        }
        GLES20.glActiveTexture(slotProvidedTextureID());
        GLES20.glBindTexture(getTextureType(), 0);
        GLES20.glActiveTexture(33984);
        return true;
    }
}
