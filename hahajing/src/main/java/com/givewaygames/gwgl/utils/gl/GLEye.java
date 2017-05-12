package com.givewaygames.gwgl.utils.gl;

import android.annotation.TargetApi;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import com.givewaygames.gwgl.utils.gl.meshes.GLFlingMesh;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class GLEye extends GLPiece {
    public static final float DISTANCE_OUT = GLFlingMesh.circularPosition(46.0f);
    public static final float START_POSITION = (DISTANCE_OUT - TextTrackStyle.DEFAULT_FONT_SCALE);
    float[] R = new float[16];
    float[] T = new float[16];
    float[] Z = new float[16];
    float[] ZI = new float[16];
    float[] look = new float[16];
    float[] look_new = new float[16];
    boolean needUpdate = false;
    float[] or = new float[3];
    float[] proj = new float[16];
    float[] tmp = new float[16];
    float ypos = 0.0f;
    float zpos = START_POSITION;

    public static void perspectiveM(float[] m, int offset, float fovy, float aspect, float zNear, float zFar) {
        float f = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) Math.tan(((double) fovy) * 0.008726646259971648d));
        float rangeReciprocal = TextTrackStyle.DEFAULT_FONT_SCALE / (zNear - zFar);
        m[offset + 0] = f / aspect;
        m[offset + 1] = 0.0f;
        m[offset + 2] = 0.0f;
        m[offset + 3] = 0.0f;
        m[offset + 4] = 0.0f;
        m[offset + 5] = f;
        m[offset + 6] = 0.0f;
        m[offset + 7] = 0.0f;
        m[offset + 8] = 0.0f;
        m[offset + 9] = 0.0f;
        m[offset + 10] = (zFar + zNear) * rangeReciprocal;
        m[offset + 11] = GroundOverlayOptions.NO_DIMENSION;
        m[offset + 12] = 0.0f;
        m[offset + 13] = 0.0f;
        m[offset + 14] = ((2.0f * zFar) * zNear) * rangeReciprocal;
        m[offset + 15] = 0.0f;
    }

    public synchronized void transformToCamera(float[] out, float[] mat) {
        Matrix.multiplyMM(this.tmp, 0, this.look, 0, mat, 0);
        Matrix.multiplyMM(out, 0, this.proj, 0, this.tmp, 0);
    }

    public boolean onInitialize() {
        calculateMatrices();
        return true;
    }

    public synchronized void calculateMatrices() {
        System.arraycopy(this.look_new, 0, this.look, 0, 16);
        perspectiveM(this.proj, 0, 90.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 1.0E-4f, 1000.0f);
    }

    public void forceUpdate() {
        this.needUpdate = true;
    }

    public synchronized void setZ(float z) {
        this.zpos = z;
        this.needUpdate = true;
        Matrix.setLookAtM(this.look_new, 0, 0.0f, 0.0f, this.zpos + TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, this.zpos, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
    }

    public boolean draw(int pass, long time) {
        if (this.needUpdate) {
            calculateMatrices();
            this.needUpdate = false;
        }
        return true;
    }

    public int getNumPasses() {
        return 1;
    }

    @TargetApi(9)
    public synchronized void fromSensor(SensorEvent event) {
        SensorManager.getRotationMatrixFromVector(this.look_new, event.values);
        this.needUpdate = true;
        Matrix.setIdentityM(this.T, 0);
        SensorManager.getAngleChange(this.or, this.look_new, this.T);
        Matrix.setLookAtM(this.look_new, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        float degsX = ((float) (((double) this.or[0]) / 3.141592653589793d)) * BitmapDescriptorFactory.HUE_CYAN;
        Matrix.rotateM(this.look_new, 0, -(90.0f + (((float) (((double) this.or[2]) / 3.141592653589793d)) * BitmapDescriptorFactory.HUE_CYAN)), TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f);
        Matrix.rotateM(this.look_new, 0, degsX, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
    }

    @TargetApi(9)
    public synchronized void fromSensorOld(SensorEvent event) {
        SensorManager.getRotationMatrixFromVector(this.look_new, event.values);
        this.needUpdate = true;
    }
}
