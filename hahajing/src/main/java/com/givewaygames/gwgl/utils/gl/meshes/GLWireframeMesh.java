package com.givewaygames.gwgl.utils.gl.meshes;

import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLPiece;

public class GLWireframeMesh extends GLPiece {
    GLMesh parentMesh;

    public GLWireframeMesh(GLMesh parentMesh) {
        this.parentMesh = parentMesh;
    }

    public boolean onInitialize() {
        return true;
    }

    public boolean draw(int pass, long time) {
        int oldDrawMode = this.parentMesh.getDrawMode();
        float[] oldVerts = this.parentMesh.getTriangleVertices();
        this.parentMesh.setDrawMode(1);
        this.parentMesh.setTriangleVerticesDataAndTransform(this.parentMesh.convertToLines());
        boolean success = this.parentMesh.draw(pass, time);
        this.parentMesh.setDrawMode(oldDrawMode);
        this.parentMesh.setTriangleVerticesData(oldVerts);
        return success;
    }

    public int getNumPasses() {
        return 1;
    }
}
