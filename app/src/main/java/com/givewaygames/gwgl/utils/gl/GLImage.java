package com.givewaygames.gwgl.utils.gl;

public abstract class GLImage extends GLPiece {
    public abstract void fixTextureCoordinates(GLMesh gLMesh);

    public abstract long getImageID();

    public abstract boolean isDataInitialized();
}
