package com.givewaygames.gwgl.preview.configs;

import android.opengl.GLSurfaceView.EGLConfigChooser;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public abstract class BaseConfigChooser implements EGLConfigChooser {
    protected int[] mConfigSpec;

    abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

    abstract EGLConfig onChooseConfigError(EGL10 egl10, EGLDisplay eGLDisplay, String str);

    public BaseConfigChooser(int[] configSpec) {
        this.mConfigSpec = filterConfigSpec(configSpec);
    }

    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
        int[] num_config = new int[1];
        if (!egl.eglChooseConfig(display, this.mConfigSpec, null, 0, num_config)) {
            return onChooseConfigError(egl, display, "eglChooseConfig failed");
        }
        int numConfigs = num_config[0];
        if (numConfigs <= 0) {
            return onChooseConfigError(egl, display, "No configs match configSpec");
        }
        EGLConfig[] configs = new EGLConfig[numConfigs];
        if (!egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config)) {
            return onChooseConfigError(egl, display, "eglChooseConfig#2 failed");
        }
        EGLConfig config = chooseConfig(egl, display, configs);
        if (config == null) {
            return onChooseConfigError(egl, display, "No config chosen");
        }
        return config;
    }

    private int[] filterConfigSpec(int[] configSpec) {
        int len = configSpec.length;
        int[] newConfigSpec = new int[(len + 2)];
        System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
        newConfigSpec[len - 1] = 12352;
        newConfigSpec[len] = 4;
        newConfigSpec[len + 1] = 12344;
        return newConfigSpec;
    }
}
