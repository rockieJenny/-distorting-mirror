package com.givewaygames.gwgl.utils;

public class FPSHelper {
    private static final String TAG = "CameraFPSHelper";
    FPSWatcher fpsWatcher;
    long frames;
    String logPrefix;
    long millis;
    long timeCounter;

    public interface FPSWatcher {
        boolean keepSteadyFrameRate();

        void tick(double d, double d2);
    }

    public FPSHelper() {
        this.frames = 0;
        this.timeCounter = 0;
        this.millis = System.currentTimeMillis();
        this.logPrefix = "FPS: ";
    }

    public FPSHelper(String fpsPrefix) {
        this.frames = 0;
        this.timeCounter = 0;
        this.millis = System.currentTimeMillis();
        this.logPrefix = fpsPrefix;
    }

    public void setFPSWatcher(FPSWatcher fpsWatcher) {
        this.fpsWatcher = fpsWatcher;
    }

    public void reset() {
        this.frames = 0;
        this.timeCounter = 0;
        this.millis = System.currentTimeMillis();
    }

    public void tick() {
        this.frames++;
        this.timeCounter += System.currentTimeMillis() - this.millis;
        this.millis = System.currentTimeMillis();
        if (this.timeCounter > 5000) {
            double seconds = ((double) this.timeCounter) / 1000.0d;
            double fps = ((double) this.frames) / seconds;
            if (Log.isD) {
                Log.d(TAG, this.logPrefix + fps + "   Total seconds: " + seconds);
            }
            this.timeCounter = 0;
            this.frames = 0;
        }
    }
}
