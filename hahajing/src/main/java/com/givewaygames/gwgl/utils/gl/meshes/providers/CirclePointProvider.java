package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.delaunay.Triangle;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.MeshPointProvider;
import java.util.ArrayList;

public class CirclePointProvider implements MeshPointProvider {
    Pnt center;
    boolean centerPoint = true;
    int index = 0;
    int numPoints;
    ArrayList<Pnt> points = new ArrayList();
    double radius;

    public CirclePointProvider(int numPoints, double radius, Pnt center) {
        this.numPoints = numPoints;
        this.radius = radius;
        this.center = center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCenter(double x, double y) {
        this.center.setX(x);
        this.center.setY(y);
    }

    public boolean hasMore() {
        return this.index < this.numPoints;
    }

    public Pnt nextPoint() {
        if (this.centerPoint && isCenter()) {
            Pnt p = updatePnt(this.center.y(), this.center.x());
            this.index++;
            return p;
        }
        double angle = (((double) (this.index - 1)) / ((double) (this.numPoints - 1))) * 6.283185307179586d;
        Pnt pnt = updatePnt((Math.sin(angle) * this.radius) + this.center.y(), (Math.cos(angle) * this.radius) + this.center.x());
        this.index++;
        return pnt;
    }

    public boolean isCenter() {
        return this.index == 0;
    }

    private Pnt updatePnt(double x, double y) {
        if (this.index >= this.points.size()) {
            Pnt p = new Pnt(x, y);
            this.points.add(p);
            return p;
        }
        p = (Pnt) this.points.get(this.index);
        p.setX(x);
        p.setY(y);
        return p;
    }

    public void reset() {
        this.index = 0;
    }

    public boolean isValid(Triangle triangle) {
        return true;
    }
}
