package com.givewaygames.gwgl.utils.gl.meshes;

import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLImage;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import com.givewaygames.gwgl.utils.gl.delaunay.DelaunayTriangulation;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.delaunay.Point;
import com.givewaygames.gwgl.utils.gl.delaunay.Triangle;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GLEquationMesh extends GLMesh {
    boolean addBorderPoints = false;
    Point[] border = new Point[4];
    DelaunayTriangulation dt;
    MeshPointProvider pointProvider = new RandomPointProvider();

    public interface MeshPointProvider {
        boolean hasMore();

        boolean isValid(Triangle triangle);

        Point nextPoint();

        void reset();
    }

    public interface ExtendedMeshPointProvider extends MeshPointProvider {
        void updatePosition(float f);

        void updatePositionToStart();
    }

    public static class RandomPointProvider implements MeshPointProvider {
        int index = 0;
        Random r = new Random();

        public boolean hasMore() {
            int i = this.index;
            this.index = i + 1;
            return i < 4;
        }

        public Pnt nextPoint() {
            return new Pnt((this.r.nextDouble() * 2.0d) - 1.0d, (this.r.nextDouble() * 2.0d) - 1.0d);
        }

        public void reset() {
            this.index = 0;
        }

        public boolean isValid(Triangle triangle) {
            return true;
        }
    }

    public GLEquationMesh(GLHelper glHelper, GLTexture glTexture, GLImage glImage, MeshPointProvider pointProvider, MeshOrientation orient) {
        super(glHelper, glTexture, glImage, orient);
        this.pointProvider = pointProvider;
        if (this.pointProvider instanceof ListPointProvider) {
            ((ListPointProvider) this.pointProvider).setMeshOrientation(orient);
        }
    }

    public void setMeshRotation(MeshOrientation orient) {
        if (Log.isD) {
            Log.d("GLEquationMesh", "setMeshRotation: " + orient);
        }
        super.setMeshRotation(orient);
        if (this.pointProvider instanceof ListPointProvider) {
            ((ListPointProvider) this.pointProvider).setMeshOrientation(orient);
        }
    }

    public void setAddBorderPoints(boolean addBorderPoints) {
        this.addBorderPoints = addBorderPoints;
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }

    public void swapPointProvider(MeshPointProvider pointProvider) {
        this.pointProvider = pointProvider;
        if (this.pointProvider instanceof ListPointProvider) {
            ((ListPointProvider) this.pointProvider).setMeshOrientation(getMeshOrientation());
        }
        buildMesh();
    }

    public boolean onInitialize() {
        boolean isOkie = super.onInitialize();
        buildMesh();
        return isOkie;
    }

    public float[] buildMesh() {
        return buildValues();
    }

    public synchronized float[] buildValues() {
        this.pointProvider.reset();
        List<Point> list = new ArrayList();
        resetBorderPoints();
        int i = 0;
        while (this.addBorderPoints && i < this.border.length) {
            list.add(this.border[i]);
            i++;
        }
        while (this.pointProvider.hasMore()) {
            list.add(this.pointProvider.nextPoint());
        }
        this.dt = new DelaunayTriangulation((Point[]) list.toArray(new Point[list.size()]));
        int size = this.dt.trianglesSize();
        Iterator<Triangle> it = this.dt.trianglesIterator();
        if (this.mTriangleVerticesData.length != (size * 5) * 3) {
            this.mTriangleVerticesData = new float[((size * 5) * 3)];
        }
        int idx = -1;
        while (it.hasNext()) {
            Triangle triangle = (Triangle) it.next();
            if (!triangle.isHalfplane()) {
                idx++;
                putValues(idx * 5, (float) triangle.p1().x(), (float) (-triangle.p1().y()), 0.0f, convertXtoU((float) triangle.p1().x()), convertXtoU((float) triangle.p1().y()));
                idx++;
                putValues(idx * 5, (float) triangle.p2().x(), (float) (-triangle.p2().y()), 0.0f, convertXtoU((float) triangle.p2().x()), convertXtoU((float) triangle.p2().y()));
                idx++;
                putValues(idx * 5, (float) triangle.p3().x(), (float) (-triangle.p3().y()), 0.0f, convertXtoU((float) triangle.p3().x()), convertXtoU((float) triangle.p3().y()));
            }
        }
        updateConvertedTriangles();
        return this.mTriangleVerticesData;
    }

    private void resetBorderPoints() {
        if (this.addBorderPoints) {
            for (int i = 0; i < this.border.length; i++) {
                if (this.border[i] == null) {
                    this.border[i] = new Point();
                }
            }
            this.border[0].setX(-2.0d);
            this.border[0].setY(-2.0d);
            this.border[1].setX(2.0d);
            this.border[1].setY(-2.0d);
            this.border[2].setX(2.0d);
            this.border[2].setY(2.0d);
            this.border[3].setX(-2.0d);
            this.border[3].setY(2.0d);
        }
    }

    public synchronized void updateUVandXYValues() {
        this.pointProvider.reset();
        if (this.pointProvider instanceof ListPointProvider) {
            resetBorderPoints();
            for (Point fixBorderPoint : this.border) {
                ((ListPointProvider) this.pointProvider).fixBorderPoint(fixBorderPoint);
            }
        }
        int idx = -1;
        while (this.pointProvider.hasMore()) {
            this.pointProvider.nextPoint();
        }
        Iterator<Triangle> it = this.dt.trianglesIterator();
        while (it.hasNext()) {
            Triangle triangle = (Triangle) it.next();
            if (!triangle.isHalfplane()) {
                idx++;
                putValues(idx * 5, (float) triangle.p1().x(), (float) (-triangle.p1().y()), 0.0f, convertXtoU((float) triangle.p1().x()), convertXtoU((float) triangle.p1().y()));
                idx++;
                putValues(idx * 5, (float) triangle.p2().x(), (float) (-triangle.p2().y()), 0.0f, convertXtoU((float) triangle.p2().x()), convertXtoU((float) triangle.p2().y()));
                idx++;
                putValues(idx * 5, (float) triangle.p3().x(), (float) (-triangle.p3().y()), 0.0f, convertXtoU((float) triangle.p3().x()), convertXtoU((float) triangle.p3().y()));
            }
        }
        updateConvertedTriangles();
    }

    public void updateUVValues() {
        this.pointProvider.reset();
        int idx = -1;
        while (this.pointProvider.hasMore()) {
            this.pointProvider.nextPoint();
        }
        int size = this.dt.trianglesSize();
        Iterator<Triangle> it = this.dt.trianglesIterator();
        while (it.hasNext()) {
            Triangle triangle = (Triangle) it.next();
            if (!triangle.isHalfplane()) {
                idx++;
                putUVValues(idx * 5, convertXtoU((float) triangle.p1().x()), convertXtoU((float) triangle.p1().y()));
                idx++;
                putUVValues(idx * 5, convertXtoU((float) triangle.p2().x()), convertXtoU((float) triangle.p2().y()));
                idx++;
                putUVValues(idx * 5, convertXtoU((float) triangle.p3().x()), convertXtoU((float) triangle.p3().y()));
            }
        }
        updateConvertedTriangles();
    }
}
