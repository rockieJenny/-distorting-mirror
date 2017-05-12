package com.givewaygames.gwgl.utils.gl;

public class GLMosaicWall extends GLWall {
    GLImage glImage;
    GLTexture glMosiacTexture;

    public GLMosaicWall(GLProgram program, GLMesh mesh, GLTransform glTransform, GLImage glImage, GLTexture glMosaicTexture) {
        super(program, mesh, glTransform);
        this.glImage = glImage;
        this.glMosiacTexture = glMosaicTexture;
    }

    public boolean onInitialize() {
        return (this.glImage.initialize() & this.glMosiacTexture.initialize()) & super.onInitialize();
    }

    protected void onRelease() {
        super.onRelease();
        this.glImage.release();
        this.glMosiacTexture.release();
    }

    public boolean draw(int pass, long time) {
        return (this.glImage.draw(pass, time) & this.glMosiacTexture.draw(pass, time)) & super.draw(pass, time);
    }
}
