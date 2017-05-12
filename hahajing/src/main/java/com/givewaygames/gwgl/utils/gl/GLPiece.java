package com.givewaygames.gwgl.utils.gl;

import com.givewaygames.gwgl.utils.Log;

public abstract class GLPiece {
    public static final String TAG = "GLPiece";
    protected boolean delayInitialization = false;
    protected boolean enabled = true;
    protected boolean initialized = false;

    public abstract boolean draw(int i, long j);

    public abstract int getNumPasses();

    protected abstract boolean onInitialize();

    protected void onRelease() {
    }

    public final boolean initialize() {
        if (Log.isD) {
            Log.v(TAG, "initialize: " + this);
        }
        boolean success = onInitialize();
        setInitialized(success);
        return success;
    }

    public final void release() {
        if (Log.isD) {
            Log.d(TAG, "released: " + this);
        }
        onRelease();
        setInitialized(false);
    }

    public boolean blockNextPieces() {
        return false;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean shouldDelayInitialization() {
        return this.delayInitialization;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void delayInitialization(boolean delay) {
        this.delayInitialization = delay;
    }
}
