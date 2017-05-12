package com.givewaygames.gwgl.utils.gl.meshes;

import android.graphics.Matrix;
import android.support.v4.media.TransportMediator;
import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.GLImage;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class GLCircleMesh extends GLMesh {
    Matrix meshMatrix = new Matrix();
    float[] tmpXY = new float[5];
    float[] tmpXYP = new float[5];
    protected float u1;
    protected float u2;
    protected float um;
    protected float v1;
    protected float v2;
    protected float vm;

    public GLCircleMesh(GLHelper glHelper, GLTexture glTexture, GLImage glImage, MeshOrientation orient) {
        super(glHelper, glTexture, glImage, orient);
    }

    public boolean onInitialize() {
        boolean isOkie = super.onInitialize();
        buildFaceMesh(-0.5f, -0.5f, 0.5f, 0.5f);
        return isOkie;
    }

    protected void putValues(int idx, float x, float y, float z, float u, float v) {
        this.tmpXY[0] = x;
        this.tmpXY[1] = y;
        this.meshMatrix.mapPoints(this.tmpXYP, 0, this.tmpXY, 0, 1);
        x = this.tmpXYP[0];
        y = this.tmpXYP[1];
        this.mTriangleVerticesData[idx + 0] = x;
        this.mTriangleVerticesData[idx + 1] = y;
        this.mTriangleVerticesData[idx + 2] = z;
        this.mTriangleVerticesData[idx + 3] = u;
        this.mTriangleVerticesData[idx + 4] = v;
    }

    public float[] morphFaceTo(float x1, float y1, float x2, float y2, float angle) {
        this.meshMatrix.reset();
        this.meshMatrix.postRotate(angle, (x1 + x2) / 2.0f, (y1 + y2) / 2.0f);
        return putValues(x1, y1, x2, y2);
    }

    public float[] buildFaceMesh(float x1, float y1, float x2, float y2) {
        this.meshMatrix.reset();
        calculateUV(x1, y1, x2, y2);
        return putValues(x1, y1, x2, y2);
    }

    protected void calculateUV(float x1, float y1, float x2, float y2) {
        this.u1 = (x1 + TextTrackStyle.DEFAULT_FONT_SCALE) / 2.0f;
        this.u2 = (x2 + TextTrackStyle.DEFAULT_FONT_SCALE) / 2.0f;
        this.um = (this.u1 + this.u2) / 2.0f;
        this.v1 = (y1 + TextTrackStyle.DEFAULT_FONT_SCALE) / 2.0f;
        this.v2 = (y2 + TextTrackStyle.DEFAULT_FONT_SCALE) / 2.0f;
        this.vm = (this.v1 + this.v2) / 2.0f;
    }

    private float[] putValues(float x1, float y1, float x2, float y2) {
        if (this.mTriangleVerticesData.length != 210) {
            this.mTriangleVerticesData = new float[210];
        }
        int idx = -1 + 1;
        putValues(0, GroundOverlayOptions.NO_DIMENSION, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f);
        idx++;
        putValues(5, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, this.um, 0.0f);
        idx++;
        putValues(10, x1, y2, 0.0f, this.u1, this.v1);
        idx++;
        putValues(15, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, this.um, 0.0f);
        idx++;
        putValues(20, x1, y2, 0.0f, this.u1, this.v1);
        idx++;
        putValues(25, x2, y2, 0.0f, this.u2, this.v1);
        idx++;
        putValues(30, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, this.um, 0.0f);
        idx++;
        putValues(35, x2, y2, 0.0f, this.u2, this.v1);
        idx++;
        putValues(40, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        idx++;
        putValues(45, x2, y2, 0.0f, this.u2, this.v1);
        idx++;
        putValues(50, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        idx++;
        putValues(55, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, this.vm);
        idx++;
        putValues(60, x2, y1, 0.0f, this.u2, this.v2);
        idx++;
        putValues(65, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, this.vm);
        idx++;
        putValues(70, x2, y2, 0.0f, this.u2, this.v1);
        idx++;
        putValues(75, x2, y1, 0.0f, this.u2, this.v2);
        idx++;
        putValues(80, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, this.vm);
        idx++;
        putValues(85, TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        idx++;
        putValues(90, GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        idx++;
        putValues(95, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, this.um, TextTrackStyle.DEFAULT_FONT_SCALE);
        idx++;
        putValues(100, x1, y1, 0.0f, this.u1, this.v2);
        idx++;
        putValues(LocationRequest.PRIORITY_NO_POWER, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, this.um, TextTrackStyle.DEFAULT_FONT_SCALE);
        idx++;
        putValues(110, x1, y1, 0.0f, this.u1, this.v2);
        idx++;
        putValues(115, x2, y1, 0.0f, this.u2, this.v2);
        idx++;
        putValues(120, 0.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, this.um, TextTrackStyle.DEFAULT_FONT_SCALE);
        idx++;
        putValues(125, x2, y1, 0.0f, this.u2, this.v2);
        idx++;
        putValues(TransportMediator.KEYCODE_MEDIA_RECORD, TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        idx++;
        putValues(135, x1, y2, 0.0f, this.u1, this.v1);
        idx++;
        putValues(140, GroundOverlayOptions.NO_DIMENSION, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f);
        idx++;
        putValues(145, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, 0.0f, this.vm);
        idx++;
        putValues(150, x1, y2, 0.0f, this.u1, this.v1);
        idx++;
        putValues(155, x1, y1, 0.0f, this.u1, this.v2);
        idx++;
        putValues(160, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, 0.0f, this.vm);
        idx++;
        putValues(165, x1, y1, 0.0f, this.u1, this.v2);
        idx++;
        putValues(170, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, 0.0f, this.vm);
        idx++;
        putValues(175, GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        idx++;
        putValues(180, x1, y2, 0.0f, this.u1, this.v1);
        idx++;
        putValues(185, x2, y2, 0.0f, this.u2, this.v1);
        idx++;
        putValues(190, x1, y1, 0.0f, this.u1, this.v2);
        idx++;
        putValues(195, x2, y1, 0.0f, this.u2, this.v2);
        idx++;
        putValues(200, x2, y2, 0.0f, this.u2, this.v1);
        idx++;
        putValues(205, x1, y1, 0.0f, this.u1, this.v2);
        updateConvertedTriangles();
        return this.mTriangleVerticesData;
    }
}
