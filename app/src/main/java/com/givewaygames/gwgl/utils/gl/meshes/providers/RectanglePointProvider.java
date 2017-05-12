package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.delaunay.Triangle;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.MeshPointProvider;

public class RectanglePointProvider implements MeshPointProvider {
    int index = 0;
    int numPoints = 50;
    double x1 = -1.0d;
    double x2 = 1.0d;
    double y1 = -1.0d;
    double y2 = 1.0d;

    public boolean hasMore() {
        int i = this.index;
        this.index = i + 1;
        return i < this.numPoints;
    }

    public Pnt nextPoint() {
        double p = ((double) (this.index - 1)) / ((double) (this.numPoints - 1));
        return new Pnt(((this.x2 - this.x1) * Math.max(0.0d, Math.min(1.0d, 1.5d - Math.abs(1.5d - (4.0d * p))))) + this.x1, ((this.y2 - this.y1) * Math.max(0.0d, Math.min(1.0d, 1.5d - Math.abs(1.5d - ((1.0d - p) * 4.0d))))) + this.y1);
    }

    public void reset() {
        this.index = 0;
    }

    public boolean isValid(Triangle triangle) {
        return true;
    }
}
