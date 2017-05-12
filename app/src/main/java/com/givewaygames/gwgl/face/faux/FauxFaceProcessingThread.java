package com.givewaygames.gwgl.face.faux;

import android.hardware.Camera;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider;

public class FauxFaceProcessingThread extends Thread {
    CameraWrapper cameraWrapper = this.cameraWrapper;
    FaceProvider faceProvider = new FaceProvider();
    GLEquationMesh glMesh = this.glMesh;
    ListPointProvider pointProvider = this.pointProvider;
    boolean running = true;
    int surfaceHeight = 0;
    int surfaceWidth = 0;

    public void notifyOfFrame(Camera camera, byte[] data) {
    }

    public void kill() {
        this.running = false;
    }

    public void run() {
        super.run();
        do {
        } while (this.running);
    }
}
