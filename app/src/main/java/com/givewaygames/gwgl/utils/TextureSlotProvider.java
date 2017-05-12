package com.givewaygames.gwgl.utils;

public class TextureSlotProvider {
    int currentTexture = -1;
    int textureType;

    public TextureSlotProvider(int textureType) {
        this.textureType = textureType;
    }

    public void reset() {
        this.currentTexture = -1;
    }

    public int getNextTextureSlot() {
        int i = this.currentTexture + 1;
        this.currentTexture = i;
        return i;
    }

    public int getTextureType(String textureName) {
        return textureName.equals(GLHelper.DEFAULT_TEXTURE) ? this.textureType : 3553;
    }

    public int getGLTextureID(int textureSlot) {
        switch (textureSlot) {
            case -1:
                throw new IllegalArgumentException("Texture was not initialized before using.");
            case 0:
                return 33984;
            case 1:
                return 33985;
            case 2:
                return 33986;
            case 3:
                return 33987;
            case 4:
                return 33988;
            default:
                throw new IllegalArgumentException("To use more textures, implement more of me!");
        }
    }
}
