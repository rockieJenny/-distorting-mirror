package com.givewaygames.camera.utils;

import android.util.SparseArray;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.MeshPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.CirclePointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.RectanglePointProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransitionPicker {
    MeshPointProvider[] shapes = new MeshPointProvider[]{new CirclePointProvider(50, 1.0d, new Pnt(0.0d, 0.0d)), new RectanglePointProvider(), new CirclePointProvider(4, 1.0d, new Pnt(0.0d, 0.0d)), new CirclePointProvider(5, 1.0d, new Pnt(0.0d, 0.0d)), new CirclePointProvider(6, 1.0d, new Pnt(0.0d, 0.0d)), new CirclePointProvider(9, 1.0d, new Pnt(0.0d, 0.0d))};
    List<GLEquationMesh> stencilMeshes = new ArrayList();
    SparseArray<float[]> storedVerts = new SparseArray();

    public void add(GLEquationMesh mesh) {
        this.stencilMeshes.add(mesh);
        init(mesh);
    }

    void init(GLEquationMesh mesh) {
        this.storedVerts.clear();
        for (int i = 0; i < this.shapes.length; i++) {
            mesh.swapPointProvider(this.shapes[i]);
            mesh.setTriangleVerticesData(new float[storeTriangleVertices(i, mesh)]);
        }
    }

    public int getNextTransitionIdx(Random r) {
        return r.nextInt(this.shapes.length);
    }

    public MeshPointProvider getNextTransition(Random r) {
        return this.shapes[getNextTransitionIdx(r)];
    }

    public void pickNewTransitionShape(Random r) {
        int next = getNextTransitionIdx(r);
        for (GLEquationMesh stencilMesh : this.stencilMeshes) {
            stencilMesh.setTriangleVerticesData((float[]) this.storedVerts.get(next));
        }
    }

    public int storeTriangleVertices(int idx, GLEquationMesh mesh) {
        float[] verts = mesh.getTriangleVertices();
        this.storedVerts.put(idx, verts);
        return verts.length;
    }
}
