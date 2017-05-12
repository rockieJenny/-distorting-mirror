package com.givewaygames.gwgl.preview.configs;

import com.givewaygames.gwgl.LibState;
import com.givewaygames.gwgl.events.OpenGLErrorEvent;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class OptionalStencilConfigChooser extends BaseConfigChooser {
    private boolean isStencilAllowed;
    protected int mAlphaSize;
    protected int mBlueSize;
    protected int mDepthSize;
    protected boolean mDontCareColor;
    protected int mGreenSize;
    protected int mRedSize;
    protected int mStencilSize;
    private int[] mValue;
    OnConfigChosen onConfigChosen;
    private boolean tryMinimalOnFailure;

    public interface OnConfigChosen {
        void onConfigChosen(boolean z);
    }

    public OptionalStencilConfigChooser(boolean isStencilAllowed, int stencilSize) {
        super(new int[]{12324, 0, 12323, 0, 12322, 0, 12321, 0, 12326, 0, 12344});
        this.isStencilAllowed = true;
        this.tryMinimalOnFailure = true;
        this.mValue = new int[1];
        this.mRedSize = 0;
        this.mGreenSize = 0;
        this.mBlueSize = 0;
        this.mAlphaSize = 0;
        this.mStencilSize = stencilSize;
        this.mDontCareColor = true;
        this.isStencilAllowed = isStencilAllowed;
    }

    public OptionalStencilConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        super(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344});
        this.isStencilAllowed = true;
        this.tryMinimalOnFailure = true;
        this.mValue = new int[1];
        this.mRedSize = redSize;
        this.mGreenSize = greenSize;
        this.mBlueSize = blueSize;
        this.mAlphaSize = alphaSize;
        this.mDepthSize = depthSize;
        this.mStencilSize = stencilSize;
    }

    public OptionalStencilConfigChooser setOnConfigChosenListener(OnConfigChosen onConfigChosen) {
        this.onConfigChosen = onConfigChosen;
        return this;
    }

    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
        EGLConfig eglConfig = chooseConfigInner(egl, display, configs, this.mStencilSize);
        if (eglConfig == null) {
            return chooseConfigInner(egl, display, configs, 0);
        }
        return eglConfig;
    }

    EGLConfig onChooseConfigError(EGL10 egl, EGLDisplay display, String error) {
        boolean tryMinimal = this.tryMinimalOnFailure;
        this.tryMinimalOnFailure = false;
        if (tryMinimal) {
            this.isStencilAllowed = true;
            this.mConfigSpec = new int[]{12344};
            return chooseConfig(egl, display);
        }
        LibState.getInstance().getBus().post(new OpenGLErrorEvent("No valid OpenGL config available."));
        return null;
    }

    private EGLConfig chooseConfigInner(EGL10 egl, EGLDisplay display, EGLConfig[] configs, int stencilSize) {
        for (EGLConfig config : configs) {
            int d = findConfigAttrib(egl, display, config, 12325, 0);
            int s = findConfigAttrib(egl, display, config, 12326, 0);
            boolean isStencilValid = (this.isStencilAllowed && s >= stencilSize) || (!this.isStencilAllowed && s == 0);
            if (d >= this.mDepthSize && isStencilValid) {
                boolean isMatching = findConfigAttrib(egl, display, config, 12324, 0) == this.mRedSize && findConfigAttrib(egl, display, config, 12323, 0) == this.mGreenSize && findConfigAttrib(egl, display, config, 12322, 0) == this.mBlueSize && findConfigAttrib(egl, display, config, 12321, 0) == this.mAlphaSize;
                if (this.mDontCareColor || isMatching) {
                    if (this.onConfigChosen == null) {
                        return config;
                    }
                    this.onConfigChosen.onConfigChosen(s > 0);
                    return config;
                }
            }
        }
        if (this.onConfigChosen != null) {
            this.onConfigChosen.onConfigChosen(false);
        }
        return null;
    }

    private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
        if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
            return this.mValue[0];
        }
        return defaultValue;
    }
}
