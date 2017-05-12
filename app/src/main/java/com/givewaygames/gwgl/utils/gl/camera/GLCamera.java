package com.givewaygames.gwgl.utils.gl.camera;

import android.hardware.Camera.PreviewCallback;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.CameraWrapper.CameraPreviewSetter;
import com.givewaygames.gwgl.utils.FPSHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLImage;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.GLTexture;

public abstract class GLCamera extends GLImage implements CameraPreviewSetter {
    FPSHelper cameraFPS = new FPSHelper();
    GLTexture glTexture;
    boolean hasSeenDataFromCamera = false;
    PreviewCallback previewCallback;

    public abstract boolean isFrameReady();

    public abstract boolean updateGLImage();

    public GLCamera(GLTexture glTexture) {
        this.glTexture = glTexture;
    }

    public boolean hasSeenDataFromCamera() {
        return this.hasSeenDataFromCamera;
    }

    public FPSHelper getFPSHelper() {
        return this.cameraFPS;
    }

    public void resetFPSCounter() {
        this.cameraFPS.reset();
    }

    public void setPreviewCallback(PreviewCallback previewCallback) {
        this.previewCallback = previewCallback;
    }

    public void fixTextureCoordinates(GLMesh glMesh) {
    }

    public void onFrameReceived() {
        if (!this.hasSeenDataFromCamera) {
            if (Log.isI) {
                Log.i(GLPiece.TAG, "onPreviewFrame:  First frame seen.");
            }
            CameraWrapper.logCrashlyticsValue("has_seen_data_from_camera", "true");
            this.hasSeenDataFromCamera = true;
        }
    }

    public boolean onInitialize() {
        return true;
    }

    public boolean draw(int pass, long time) {
        updateGLImage();
        return true;
    }

    public int getNumPasses() {
        return 1;
    }
}
