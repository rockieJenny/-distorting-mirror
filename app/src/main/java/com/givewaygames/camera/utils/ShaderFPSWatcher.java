package com.givewaygames.camera.utils;

import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.gwgl.utils.FPSHelper;
import com.givewaygames.gwgl.utils.FPSHelper.FPSWatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderFPSWatcher implements FPSWatcher {
    CameraFilterActivity activity;
    int currentIdx = 0;
    FPSHelper fpsHelper;
    boolean isRunning = false;
    List<Double> order = new ArrayList();
    int runningTotal = 4;
    Map<Double, String> shaders = new HashMap();
    List<Integer> shadersToTest;
    float value = 0.0f;

    public ShaderFPSWatcher(FPSHelper fpsHelper, CameraFilterActivity activity) {
        this.fpsHelper = fpsHelper;
        this.activity = activity;
    }

    public void startTest() {
    }

    public void setTestAllShaders() {
        this.shadersToTest = new ArrayList();
        for (int i = 0; i < 46; i++) {
            this.shadersToTest.add(Integer.valueOf(i));
        }
    }

    public void setShadersToTest(List<Integer> shadersToTest) {
        this.shadersToTest = shadersToTest;
    }

    public void tick(double fps, double seconds) {
    }

    public boolean keepSteadyFrameRate() {
        return true;
    }
}
