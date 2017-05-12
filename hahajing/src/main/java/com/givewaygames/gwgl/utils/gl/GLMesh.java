package com.givewaygames.gwgl.utils.gl;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.LibState;
import com.givewaygames.gwgl.events.CameraMeshRefreshEvent;
import com.givewaygames.gwgl.shader.PixelShader;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.blends.GLBlendNone;
import com.givewaygames.gwgl.utils.gl.blends.IGLBlend;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.squareup.otto.Subscribe;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.concurrent.GuardedBy;

public class GLMesh extends GLPiece {
    private static final int FLOAT_SIZE_BYTES = 4;
    private static final String TAG = GLMesh.class.getName();
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    protected IGLBlend blendMethod = new GLBlendNone();
    protected int drawMode = 4;
    GLHelper glHelper;
    GLImage glImage;
    ArrayList<GLTexture> glTextures = new ArrayList();
    boolean isForBufferManagedCamera = false;
    long lastImageId = -1;
    protected float[] lines;
    @GuardedBy("this")
    protected float[] mConvertedTriangles;
    Object mTriangleVertexLock = new Object();
    @GuardedBy("this")
    protected float[] mTriangleVerticesData = new float[]{GroundOverlayOptions.NO_DIMENSION, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE};
    Matrix matrix = new Matrix();
    protected boolean meshNeedsUpdate = true;
    MeshOrientation meshOrientation;
    boolean needsUpdateTriangles = false;
    float[] resultVector = new float[4];
    float[] textureTransform = new float[16];
    FloatBuffer triangleVertices;

    public class EventBusMethodsGingerbreadSafe {
        @Subscribe
        public void onCameraSwitched(CameraMeshRefreshEvent event) {
            GLMesh.this.onCameraSwitched(event);
        }
    }

    public GLMesh(GLHelper glHelper, GLTexture glTexture, GLImage glImage, MeshOrientation orient) {
        this.glHelper = glHelper;
        if (glTexture != null) {
            this.glTextures.add(glTexture);
        }
        this.glImage = glImage;
        this.meshOrientation = orient;
    }

    public void hackForBufferManagedCamera() {
        this.isForBufferManagedCamera = true;
    }

    public void registerToReceiveCameraMeshCallbacks() {
        LibState.getInstance().getBus().register(new EventBusMethodsGingerbreadSafe());
    }

    @Subscribe
    public void onCameraSwitched(CameraMeshRefreshEvent event) {
        setMeshRotation(event.meshOrientation);
        updateConvertedTriangles();
    }

    public void setMeshRotation(MeshOrientation orient) {
        this.meshOrientation = orient;
    }

    public MeshOrientation getMeshOrientation() {
        return this.meshOrientation;
    }

    public void addTexture(GLTexture glTexture) {
        this.glTextures.add(glTexture);
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }

    public int getDrawMode() {
        return this.drawMode;
    }

    public void setTriangleVerticesDataAndTransform(float[] verts) {
        setTriangleVerticesData(verts);
        transformConvertedTriangles();
    }

    public void setTriangleVerticesData(float[] verts) {
        this.mConvertedTriangles = verts;
        this.needsUpdateTriangles = true;
    }

    public float[] getTriangleVertices() {
        return this.mConvertedTriangles;
    }

    public GLImage getGLImage() {
        return this.glImage;
    }

    public void setBlendMethod(IGLBlend blendMethod) {
        this.blendMethod = blendMethod;
    }

    protected void flipYVertices(float[] verts) {
        for (int i = 0; i < verts.length; i += 5) {
            verts[i + 1] = -verts[i + 1];
        }
    }

    protected void flipXVertices(float[] verts) {
        for (int i = 0; i < verts.length; i += 5) {
            verts[i] = -verts[i];
        }
    }

    protected void rotateVertices(float[] verts, int degrees) {
        double theta = (3.141592653589793d * ((double) degrees)) / 180.0d;
        for (int i = 0; i < verts.length; i += 5) {
            float x = verts[i];
            float y = verts[i + 1];
            double dy = (((double) x) * Math.sin(theta)) + (((double) y) * Math.cos(theta));
            verts[i] = (float) ((((double) x) * Math.cos(theta)) - (((double) y) * Math.sin(theta)));
            verts[i + 1] = (float) dy;
        }
    }

    protected void scaleVertices(float[] verts, float sx, float sy) {
        for (int i = 0; i < verts.length; i += 5) {
            float x = verts[i];
            double dy = (double) (verts[i + 1] * sy);
            verts[i] = (float) ((double) (x * sx));
            verts[i + 1] = (float) dy;
        }
    }

    public boolean onInitialize() {
        updateConvertedTriangles();
        this.triangleVertices = ByteBuffer.allocateDirect(this.mTriangleVerticesData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.triangleVertices.put(this.mTriangleVerticesData).position(0);
        if (false) {
            return false;
        }
        return true;
    }

    public synchronized void updateConvertedTriangles() {
        if (Log.isV) {
            Log.v(TAG, "updateConvertedTriangles: " + this);
        }
        if (this.mTriangleVerticesData != null) {
            if (this.mConvertedTriangles == null || this.mConvertedTriangles.length != this.mTriangleVerticesData.length) {
                this.mConvertedTriangles = new float[this.mTriangleVerticesData.length];
            }
            for (int i = 0; i < this.mTriangleVerticesData.length; i += 5) {
                this.matrix.mapPoints(this.mConvertedTriangles, i, this.mTriangleVerticesData, i, 1);
                this.mConvertedTriangles[i + 2] = this.mTriangleVerticesData[i + 2];
                this.mConvertedTriangles[i + 3] = this.mTriangleVerticesData[i + 3];
                this.mConvertedTriangles[i + 4] = this.mTriangleVerticesData[i + 4];
            }
            transformConvertedTriangles();
            this.needsUpdateTriangles = true;
        } else if (Log.isI) {
            Log.i(TAG, "Cannot update converted triangle vertices at this time.");
        }
    }

    private void transformConvertedTriangles() {
        if (this.meshOrientation != null) {
            rotateVertices(this.mConvertedTriangles, this.meshOrientation.getRotationAmount());
            if (this.meshOrientation.getFlipX(this.isForBufferManagedCamera)) {
                flipXVertices(this.mConvertedTriangles);
            }
            if (this.meshOrientation.getFlipY(this.isForBufferManagedCamera)) {
                flipYVertices(this.mConvertedTriangles);
            }
            scaleVertices(this.mConvertedTriangles, this.meshOrientation.getScaleX(), this.meshOrientation.getScaleY());
            this.meshOrientation.clean();
        }
    }

    public boolean draw(int pass, long time) {
        GLProgram glProgram = (GLProgram) this.glHelper.getActiveObject(GLProgram.class);
        if (glProgram == null) {
            return true;
        }
        if (this.glImage != null && !this.glImage.isDataInitialized()) {
            return true;
        }
        Iterator i$ = this.glTextures.iterator();
        while (i$.hasNext()) {
            GLTexture glTexture = (GLTexture) i$.next();
            if (glTexture.isEnabled() && glTexture.isInitialized()) {
                if (!glTexture.bindTexture(this.blendMethod)) {
                    return false;
                }
                if (!glTexture.attachToProgram((long) glProgram.getProgramID())) {
                    return false;
                }
            }
        }
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        if (this.meshOrientation != null && this.meshOrientation.isDirty()) {
            updateConvertedTriangles();
        }
        long imageId = 0;
        if (this.glImage != null) {
            imageId = this.glImage.getImageID();
        }
        if (!(this.glImage == null || this.lastImageId == imageId)) {
            this.glImage.fixTextureCoordinates(this);
            this.needsUpdateTriangles = true;
            this.lastImageId = imageId;
        }
        if (this.needsUpdateTriangles) {
            updateTriangleVertices();
            this.needsUpdateTriangles = false;
        }
        this.triangleVertices.position(0);
        GLES20.glVertexAttribPointer(glProgram.getAttribPosition(), 3, 5126, false, 20, this.triangleVertices);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        this.triangleVertices.position(3);
        GLES20.glVertexAttribPointer(glProgram.getTexCoords(), 3, 5126, false, 20, this.triangleVertices);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        GLES20.glDrawArrays(this.drawMode, 0, this.mConvertedTriangles.length / 5);
        if (GLErrorChecker.checkGlError(TAG)) {
            return true;
        }
        i$ = this.glTextures.iterator();
        while (i$.hasNext()) {
            glTexture = (GLTexture) i$.next();
            if (glTexture.isEnabled() && glTexture.isInitialized()) {
                glTexture.unbindTexture(this.blendMethod);
            }
        }
        return true;
    }

    public synchronized void updateTriangleVertices() {
        if (this.mConvertedTriangles != null) {
            if (this.triangleVertices == null || this.triangleVertices.capacity() != this.mConvertedTriangles.length) {
                if (Log.isV) {
                    Log.v(TAG, "Wrong size vertices.  Resizing mTriangleVertices");
                }
                this.triangleVertices = ByteBuffer.allocateDirect(this.mConvertedTriangles.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            }
            this.triangleVertices.position(0);
            this.triangleVertices.put(this.mConvertedTriangles).position(0);
            this.meshNeedsUpdate = true;
        } else if (Log.isI) {
            Log.i(TAG, "Cannot update triangle vertices at this time.");
        }
    }

    @TargetApi(11)
    public synchronized void fixTextureCoordinates(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            surfaceTexture.getTransformMatrix(this.textureTransform);
            boolean changed = false;
            for (int i = 0; i < this.mTriangleVerticesData.length; i += 5) {
                android.opengl.Matrix.multiplyMV(this.resultVector, 0, this.textureTransform, 0, new float[]{TextTrackStyle.DEFAULT_FONT_SCALE - this.mTriangleVerticesData[i + 3], TextTrackStyle.DEFAULT_FONT_SCALE - this.mTriangleVerticesData[i + 4], 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE}, 0);
                this.mConvertedTriangles[i + 3] = this.resultVector[0];
                this.mConvertedTriangles[i + 4] = this.resultVector[1];
                changed = true;
            }
            GLProgram glProgram = (GLProgram) this.glHelper.getActiveObject(GLProgram.class);
            PixelShader pixelShader = glProgram != null ? glProgram.getPixelShader() : null;
            if (pixelShader != null) {
                pixelShader.fixTextureCoordinates(this.textureTransform, this.resultVector);
            }
            if (changed) {
                this.needsUpdateTriangles = true;
            }
        }
    }

    public void fixTextureCoordinate(float[] resultVector, float x, float y) {
        if (this.textureTransform != null) {
            float[] fArr = resultVector;
            android.opengl.Matrix.multiplyMV(fArr, 0, this.textureTransform, 0, new float[]{x, y, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE}, 0);
        }
    }

    public int getNumPasses() {
        return 1;
    }

    protected void putValues(int idx, float x, float y, float z, float u, float v) {
        this.mTriangleVerticesData[idx + 0] = x;
        this.mTriangleVerticesData[idx + 1] = y;
        this.mTriangleVerticesData[idx + 2] = z;
        this.mTriangleVerticesData[idx + 3] = u;
        this.mTriangleVerticesData[idx + 4] = v;
    }

    protected void putUVValues(int idx, float u, float v) {
        this.mTriangleVerticesData[idx + 3] = u;
        this.mTriangleVerticesData[idx + 4] = v;
    }

    public float convertXtoU(float x1) {
        return (TextTrackStyle.DEFAULT_FONT_SCALE + x1) / 2.0f;
    }

    public float[] convertToLines() {
        if (!this.meshNeedsUpdate && this.lines != null) {
            return this.lines;
        }
        this.lines = new float[(this.mTriangleVerticesData.length * 2)];
        int idx = 0;
        for (int i = 0; i < this.mTriangleVerticesData.length; i += 15) {
            for (int v = 0; v < 3; v++) {
                int v1 = i + (v * 5);
                int v2 = i + (((v + 1) % 3) * 5);
                for (int k = 0; k < 5; k++) {
                    this.lines[(idx * 5) + k] = this.mTriangleVerticesData[v1 + k];
                    this.lines[((idx + 1) * 5) + k] = this.mTriangleVerticesData[v2 + k];
                }
                idx += 2;
            }
        }
        this.meshNeedsUpdate = false;
        return this.lines;
    }
}
