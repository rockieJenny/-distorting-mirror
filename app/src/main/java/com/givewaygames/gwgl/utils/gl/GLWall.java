package com.givewaygames.gwgl.utils.gl;

import com.givewaygames.gwgl.shader.GLVariable;
import com.givewaygames.gwgl.shader.vertex.BasicVertexShader;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;
import java.util.List;

public class GLWall extends GLPiece {
    private static final long TRANSFORM_DISABLE_FADE_TIME = 1000;
    long disableMeAt = 0;
    GLMesh glMesh;
    GLProgram glProgram;
    GLTransform glTransform;
    List<GLPiece> postProgramPieces = new ArrayList();

    public GLWall(GLProgram program, GLMesh mesh, GLTransform glTransform) {
        this.glMesh = mesh;
        this.glTransform = glTransform;
        setProgram(program);
    }

    public void addPostProgramPiece(GLPiece piece) {
        this.postProgramPieces.add(piece);
    }

    public void setProgram(GLProgram glProgram) {
        this.glProgram = glProgram;
    }

    public GLProgram getProgram() {
        return this.glProgram;
    }

    public GLMesh getMesh() {
        return this.glMesh;
    }

    public void setTransform(GLTransform transform) {
        this.glTransform = transform;
    }

    public GLTransform getTransform() {
        return this.glTransform;
    }

    public void updateMatrix(float[] matrix) {
        GLVariable var = this.glProgram.getVertexShader().getVariable(BasicVertexShader.MATRIX);
        if (var != null) {
            var.setValues(matrix);
        }
    }

    public boolean onInitialize() {
        boolean z = true;
        boolean doInit = (this.glProgram == null || this.glProgram.shouldDelayInitialization()) ? false : true;
        if (doInit) {
            z = this.glProgram.initialize();
        }
        for (GLPiece p : this.postProgramPieces) {
            z &= p.initialize();
        }
        return z & this.glMesh.initialize();
    }

    protected void onRelease() {
        super.onRelease();
        if (this.glProgram != null) {
            this.glProgram.release();
        }
        for (GLPiece p : this.postProgramPieces) {
            p.release();
        }
        if (this.glMesh != null) {
            this.glMesh.release();
        }
    }

    public boolean draw(int pass, long time) {
        if (this.glProgram == null || !this.glProgram.isInitialized()) {
            return true;
        }
        if (this.disableMeAt != 0) {
            if (time >= this.disableMeAt) {
                setEnabled(false);
                this.disableMeAt = 0;
                return true;
            } else if (time >= this.disableMeAt - TRANSFORM_DISABLE_FADE_TIME && this.glTransform != null) {
                this.glTransform.setEnabled(((float) (this.disableMeAt - time)) / 1000.0f);
            }
        }
        if (this.glTransform != null) {
            updateMatrix(this.glTransform.getModelViewProjectionMatrix(time));
        }
        boolean otay = this.glProgram.draw(pass, time);
        for (GLPiece p : this.postProgramPieces) {
            otay &= p.draw(pass, time);
        }
        return otay & this.glMesh.draw(pass, time);
    }

    public int getNumPasses() {
        return 1;
    }

    public void enableMe(boolean enable) {
        setEnabled(enable);
        if (this.glTransform != null) {
            this.glTransform.setEnabled(enable ? TextTrackStyle.DEFAULT_FONT_SCALE : 0.0f);
        }
    }

    public void disableAtTime(long time) {
        this.disableMeAt = time;
    }
}
