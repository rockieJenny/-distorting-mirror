package com.givewaygames.gwgl.utils.gl.meshes.providers;

import android.graphics.Matrix;
import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.CameraWrapper.StaticImageMeshOrientation;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.delaunay.Point;
import com.givewaygames.gwgl.utils.gl.delaunay.Triangle;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.ExtendedMeshPointProvider;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ListPointProvider implements ExtendedMeshPointProvider {
    final double baseScaleX = 1.0d;
    final double baseScaleY = 1.0d;
    Transform forward = new Transform();
    final GLHelper glHelper;
    int index = 0;
    MeshOrientation meshOrientation = new StaticImageMeshOrientation();
    double morphFactor = 0.0d;
    int numPoints;
    ArrayList<Pnt> points;
    ArrayList<Pnt> pointsDir = new ArrayList();
    protected ArrayList<TPnt> pointsStart = new ArrayList();
    protected ArrayList<Pnt> pointsTarget = new ArrayList();
    Transform reverse = new Transform();

    public enum PointType {
        FACE,
        LEFT_EYE,
        RIGHT_EYE,
        MOUTH,
        UNKNOWN
    }

    public class Transform {
        Matrix matrix = new Matrix();
        float[] src = new float[2];

        public void rotate(double x, double y, float angle) {
            this.src[0] = (float) x;
            this.src[1] = (float) y;
            this.matrix.reset();
            this.matrix.postRotate(angle);
            this.matrix.mapPoints(this.src);
            if (angle == 90.0f || angle == BitmapDescriptorFactory.HUE_VIOLET) {
                float[] fArr = this.src;
                fArr[0] = fArr[0] * ((float) ListPointProvider.this.glHelper.getWidth());
                fArr = this.src;
                fArr[0] = fArr[0] / ((float) ListPointProvider.this.glHelper.getHeight());
                fArr = this.src;
                fArr[1] = fArr[1] * ((float) ListPointProvider.this.glHelper.getHeight());
                fArr = this.src;
                fArr[1] = fArr[1] / ((float) ListPointProvider.this.glHelper.getWidth());
            }
        }

        public float x() {
            return this.src[0];
        }

        public float y() {
            return this.src[1];
        }
    }

    public class TPnt extends Pnt {
        double offsetX = 0.0d;
        double offsetY = 0.0d;
        double scaleX = 1.0d;
        double scaleY = 1.0d;
        PointType type;

        public TPnt(double x, double y, PointType type) {
            super(x, y);
            this.type = type;
        }

        public double getScaleX() {
            return this.scaleX;
        }

        public double getScaleY() {
            return this.scaleY;
        }

        public double getOffsetX() {
            return this.offsetX;
        }

        public double getOffsetY() {
            return this.offsetY;
        }

        public PointType getType() {
            return this.type;
        }

        void setScale(double sx, double sy) {
            this.scaleX = sx;
            this.scaleY = sy;
        }

        void setOffset(double x, double y) {
            this.offsetX = x;
            this.offsetY = y;
        }
    }

    public abstract void setupPoints();

    public ListPointProvider(GLHelper glHelper) {
        this.glHelper = glHelper;
        setupPoints();
        this.numPoints = this.pointsStart.size();
        this.points = new ArrayList(this.numPoints);
        for (int i = 0; i < this.numPoints; i++) {
            Point start = (Point) this.pointsStart.get(i);
            this.points.add(i, new Pnt(start.x(), start.y()));
            Point target = (Point) this.pointsTarget.get(i);
            this.pointsDir.add(i, new Pnt(target.x() - start.x(), target.y() - start.y()));
        }
    }

    public void updatePosition(float sx) {
        this.morphFactor = (double) sx;
    }

    public void updatePositionToStart() {
        this.morphFactor = 0.0d;
    }

    public void setMeshOrientation(MeshOrientation meshOrientation) {
        this.meshOrientation = meshOrientation;
    }

    public void updateFace(double l, double t, double r, double b, double elx, double ely, double erx, double ery, double mx, double my) {
        Iterator i$ = this.pointsStart.iterator();
        while (i$.hasNext()) {
            TPnt tp = (TPnt) i$.next();
            double sx = Math.abs(r - l) * 1.0d;
            double sy = Math.abs(b - t) * 1.0d;
            double offX = ((l + r) / 2.0d) * sx;
            double offY = ((t + b) / 2.0d) * sy;
            tp.setScale(sx, sy);
            this.reverse.rotate(offX, offY, (float) (-this.meshOrientation.getRotationAmount()));
            tp.setOffset((double) this.reverse.x(), (double) this.reverse.y());
        }
    }

    public void fixBorderPoint(Point p) {
        this.forward.rotate(p.x(), p.y(), (float) this.meshOrientation.getRotationAmount());
        p.setX((double) this.forward.x());
        p.setY((double) this.forward.y());
    }

    public boolean hasMore() {
        return this.index < this.numPoints;
    }

    public Pnt nextPoint() {
        Pnt pnt = getFromListPnt();
        this.index++;
        return pnt;
    }

    public boolean isValid(Triangle triangle) {
        return true;
    }

    public void reset() {
        this.index = 0;
    }

    private Pnt getFromListPnt() {
        if (this.index >= this.pointsStart.size()) {
            return new Pnt(0.0d, 0.0d);
        }
        Pnt p = (Pnt) this.points.get(this.index);
        TPnt sPt = (TPnt) this.pointsStart.get(this.index);
        Pnt dPt = (Pnt) this.pointsDir.get(this.index);
        this.forward.rotate(((sPt.x() + (dPt.x() * this.morphFactor)) + sPt.offsetX) * sPt.scaleX, ((sPt.y() + (dPt.y() * this.morphFactor)) + sPt.offsetY) * sPt.scaleY, (float) this.meshOrientation.getRotationAmount());
        p.setX((double) this.forward.x());
        p.setY((double) this.forward.y());
        return p;
    }
}
