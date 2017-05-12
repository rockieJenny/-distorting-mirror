package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.delaunay.Triangle;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.MeshPointProvider;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;

public class CustomPointProvider implements MeshPointProvider {
    int index = 0;
    double morphFactor = 0.0d;
    int numPoints;
    ArrayList<Pnt> points;
    ArrayList<Pnt> pointsDir = new ArrayList();
    ArrayList<Pnt> pointsStart = new ArrayList();
    ArrayList<Pnt> pointsTarget = new ArrayList();
    final float scaleXStart = TextTrackStyle.DEFAULT_FONT_SCALE;
    final float scaleYStart = TextTrackStyle.DEFAULT_FONT_SCALE;

    public CustomPointProvider() {
        this.pointsStart.add(new Pnt(-0.4136720895767212d, -0.27682870626449585d));
        this.pointsStart.add(new Pnt(0.47870779037475586d, -0.24375919997692108d));
        this.pointsStart.add(new Pnt(-0.2672539949417114d, -0.5227606296539307d));
        this.pointsStart.add(new Pnt(0.33773860335350037d, -0.5080764293670654d));
        this.pointsStart.add(new Pnt(0.038179151713848114d, -0.5814977288246155d));
        this.pointsTarget.add(new Pnt(-0.41367220878601074d, -0.2733246088027954d));
        this.pointsTarget.add(new Pnt(0.47870779037475586d, -0.24082230031490326d));
        this.pointsTarget.add(new Pnt(-0.2672539949417114d, -0.6431717276573181d));
        this.pointsTarget.add(new Pnt(0.33480170369148254d, -0.6372981071472168d));
        this.pointsTarget.add(new Pnt(0.03230543062090874d, -0.9397944808006287d));
        this.numPoints = this.pointsStart.size();
        this.points = new ArrayList(this.numPoints);
        for (int i = 0; i < this.numPoints; i++) {
            this.points.add(i, new Pnt(((Pnt) this.pointsStart.get(i)).x(), ((Pnt) this.pointsStart.get(i)).y()));
            this.pointsDir.add(i, new Pnt(((Pnt) this.pointsTarget.get(i)).x() - ((Pnt) this.pointsStart.get(i)).x(), ((Pnt) this.pointsTarget.get(i)).y() - ((Pnt) this.pointsStart.get(i)).y()));
        }
    }

    private Pnt getFromListPnt() {
        if (this.index >= this.pointsStart.size()) {
            return new Pnt(0.0d, 0.0d);
        }
        Pnt p = (Pnt) this.points.get(this.index);
        p.setX(((Pnt) this.pointsStart.get(this.index)).x() + (((Pnt) this.pointsDir.get(this.index)).x() * this.morphFactor));
        p.setY(((Pnt) this.pointsStart.get(this.index)).y() + (((Pnt) this.pointsDir.get(this.index)).y() * this.morphFactor));
        return p;
    }

    public void updatePosition(float sx) {
        this.morphFactor = (double) sx;
    }

    public void updatePositionToStart() {
        this.morphFactor = 0.0d;
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
}
