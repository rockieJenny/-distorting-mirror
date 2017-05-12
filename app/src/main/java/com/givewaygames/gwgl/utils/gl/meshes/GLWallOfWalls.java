package com.givewaygames.gwgl.utils.gl.meshes;

import android.graphics.Matrix;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.GLTransform;
import com.givewaygames.gwgl.utils.gl.GLWall;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;
import java.util.Iterator;

public class GLWallOfWalls extends GLPiece {
    GLHelper glHelper;
    GLTransform glTransform;
    Matrix matrix = new Matrix();
    ArrayList<GLWall> walls = new ArrayList();

    public GLWallOfWalls(GLHelper glHelper, GLTransform glTransform) {
        this.glHelper = glHelper;
        this.glTransform = glTransform;
    }

    public GLWall getWall(int idx) {
        return (GLWall) this.walls.get(idx);
    }

    public void registerWall(GLWall wall) {
        wall.setTransform(this.glTransform);
        this.walls.add(wall);
    }

    public static void getLayoutParams(float[] params, int idx, int width, int height) {
        float dx = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) width);
        float dy = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) height);
        int y = idx / width;
        params[0] = ((((float) (idx % width)) * dx) * 2.0f) - (TextTrackStyle.DEFAULT_FONT_SCALE - dx);
        params[1] = ((((float) y) * dy) * 2.0f) - (TextTrackStyle.DEFAULT_FONT_SCALE - dy);
        params[2] = dx;
        params[3] = dy;
    }

    public void layoutMeshes() {
        int numWide = 1;
        int numTall = 1;
        boolean wideTurn = true;
        boolean done = false;
        int goalNumber = this.walls.size();
        while (!done) {
            if (numWide * numTall < goalNumber) {
                if (wideTurn) {
                    numWide++;
                } else {
                    numTall++;
                }
                wideTurn = !wideTurn;
            } else {
                done = true;
            }
        }
        Iterator<GLWall> mIt = this.walls.iterator();
        float dx = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) numWide);
        float dy = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) numTall);
        int idx = 0;
        while (mIt.hasNext()) {
            GLMesh mesh = ((GLWall) mIt.next()).getMesh();
            Matrix m = mesh.getMatrix();
            int x = idx % numWide;
            int y = idx / numWide;
            m.reset();
            m.postScale(dx, dy);
            m.postTranslate(((((float) x) * dx) * 2.0f) - (TextTrackStyle.DEFAULT_FONT_SCALE - dx), ((((float) y) * dy) * 2.0f) - (TextTrackStyle.DEFAULT_FONT_SCALE - dy));
            mesh.updateConvertedTriangles();
            idx++;
        }
    }

    public boolean onInitialize() {
        Iterator<GLWall> it = this.walls.iterator();
        boolean safe = true;
        while (safe && it.hasNext()) {
            safe &= ((GLWall) it.next()).initialize();
        }
        return safe;
    }

    public boolean draw(int pass, long time) {
        Iterator<GLWall> it = this.walls.iterator();
        boolean safe = true;
        while (safe && it.hasNext()) {
            GLWall wall = (GLWall) it.next();
            wall.setTransform(this.glTransform);
            safe &= wall.draw(pass, time);
        }
        return safe;
    }

    public int getNumPasses() {
        return 1;
    }
}
