package com.givewaygames.gwgl.utils.gl.camera;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import java.util.ArrayList;
import java.util.List;

@TargetApi(11)
public class GLSurfaceTextureCamera extends GLCamera implements OnFrameAvailableListener {
    private static final String TAG = "GLSurfaceTextureCamera";
    long currentFrame = 0;
    long errorCount = 0;
    long lastFrame = 0;
    private Object surfaceLock = new Object();
    private SurfaceTexture surfaceTexture;
    List<SurfaceTexture> textureToRelease = new ArrayList();

    public GLSurfaceTextureCamera(GLTexture glTexture) {
        super(glTexture);
        this.cameraFPS.reset();
    }

    protected void onRelease() {
        super.onRelease();
        this.lastFrame = 0;
        this.currentFrame = 0;
        if (this.surfaceTexture != null) {
            this.textureToRelease.add(this.surfaceTexture);
            this.surfaceTexture = null;
        }
    }

    public boolean onInitialize() {
        if (!this.glTexture.isInitialized()) {
            this.glTexture.initialize();
        }
        boolean isSetup = false;
        synchronized (this.surfaceLock) {
            onRelease();
            if (this.glTexture.getTextureID() > 0) {
                this.surfaceTexture = new SurfaceTexture(this.glTexture.getTextureID());
            }
            if (this.surfaceTexture != null) {
                this.surfaceTexture.setOnFrameAvailableListener(this);
                this.errorCount = 0;
                isSetup = true;
            }
        }
        return isSetup;
    }

    public boolean isInitialized() {
        return this.surfaceTexture != null;
    }

    public boolean isReady() {
        return isInitialized();
    }

    public boolean updateGLImage() {
        if (!isFrameReady()) {
            return false;
        }
        this.lastFrame = this.currentFrame;
        synchronized (this.surfaceLock) {
            if (this.surfaceTexture != null) {
                try {
                    this.surfaceTexture.updateTexImage();
                } catch (RuntimeException e) {
                    CameraWrapper.logCrashlyticsValue(e.getMessage());
                    this.errorCount++;
                    if (this.errorCount >= 10) {
                        CameraWrapper.logCrashlyticsException(e);
                    }
                }
            }
        }
        this.cameraFPS.tick();
        return true;
    }

    public void fixTextureCoordinates(GLMesh glMesh) {
        if (glMesh != null) {
            synchronized (this.surfaceLock) {
                glMesh.fixTextureCoordinates(this.surfaceTexture);
            }
        }
    }

    public long getImageID() {
        return this.currentFrame;
    }

    public boolean isFrameReady() {
        return isInitialized() && this.currentFrame > this.lastFrame;
    }

    public boolean isDataInitialized() {
        return this.currentFrame > 0;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        onFrameReceived();
        this.currentFrame++;
    }

    public boolean setCameraPreview(Camera camera) {
        if (this.surfaceTexture == null) {
            Log.i(TAG, "Camera can't be setup until we have a valid surface texture.");
            return false;
        }
        resetFPSCounter();
        setCameraPreviewNow(camera);
        return true;
    }

    @TargetApi(14)
    public void releaseForTastyIceCreams(SurfaceTexture t) {
        synchronized (this.surfaceLock) {
            if (VERSION.SDK_INT >= 14 && t != null) {
                t.release();
            }
        }
    }

    private boolean setCameraPreviewNow(Camera camera) {
        try {
            synchronized (this.surfaceLock) {
                camera.setPreviewTexture(this.surfaceTexture);
                for (SurfaceTexture t : this.textureToRelease) {
                    if (t != this.surfaceTexture) {
                        releaseForTastyIceCreams(t);
                    }
                }
                this.textureToRelease.clear();
            }
            if (this.previewCallback != null) {
                camera.setPreviewCallbackWithBuffer(new PreviewCallback() {
                    public void onPreviewFrame(byte[] data, Camera camera) {
                        if (data != null) {
                            GLSurfaceTextureCamera.this.previewCallback.onPreviewFrame(data, camera);
                            camera.addCallbackBuffer(data);
                        }
                    }
                });
                Size size = camera.getParameters().getPreviewSize();
                int BUFFER_SIZE = (((size != null ? size.width : 640) * 3) * (size != null ? size.height : 480)) / 2;
                for (int i = 0; i < 3; i++) {
                    camera.addCallbackBuffer(new byte[BUFFER_SIZE]);
                }
            }
            return true;
        } catch (Exception e) {
            if (Log.isE) {
                Log.e(TAG, "Something bad happened.  Couldn't set camera texture: ", e);
            }
            return false;
        }
    }
}
