package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.delaunay.Point;
import com.givewaygames.gwgl.utils.gl.delaunay.Triangle;

public class StarPointProvider extends CirclePointProvider {
    double innerRadius;
    double outerRadius;

    public StarPointProvider(int numStarPoints, double innerRadius, double outerRadius, Pnt center) {
        super(numStarPoints * 3, outerRadius, center);
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    public boolean hasMore() {
        return this.index < this.numPoints;
    }

    public Pnt nextPoint() {
        if (this.index % 3 == 1) {
            this.radius = this.innerRadius;
        } else if (this.index % 3 == 2) {
            this.radius = this.outerRadius;
        }
        return super.nextPoint();
    }

    public boolean isCenter() {
        return this.index % 3 == 0;
    }

    public boolean isValid(Triangle triangle) {
        int numSmall = 0;
        if (isSmall(triangle.p1())) {
            numSmall = 0 + 1;
        }
        if (isSmall(triangle.p2())) {
            numSmall++;
        }
        if (isSmall(triangle.p3())) {
            numSmall++;
        }
        return numSmall >= 2;
    }

    private boolean isSmall(Point p) {
        return Math.sqrt((p.x() * p.x()) + (p.y() * p.y())) < this.outerRadius - 0.005d;
    }
}
