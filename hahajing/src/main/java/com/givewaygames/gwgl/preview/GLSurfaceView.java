package com.givewaygames.gwgl.preview;

import android.content.Context;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.LibState;
import com.givewaygames.gwgl.events.OpenGLErrorEvent;
import com.givewaygames.gwgl.events.SurfaceChangedEvent;
import com.givewaygames.gwgl.preview.configs.OptionalStencilConfigChooser;
import com.givewaygames.gwgl.shader.GLAttribute;
import com.givewaygames.gwgl.utils.FPSHelper;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLScene;
import com.squareup.otto.Produce;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLSurfaceView extends android.opengl.GLSurfaceView {
    public static boolean IS_STENCILING_ALLOWED = true;
    private static final String TAG = "GLSurfaceView";
    FPSHelper cameraFPS = new FPSHelper();
    GLHelper glHelper = new GLHelper(36197);
    int height;
    boolean isPaused = false;
    MyRenderer renderer;
    LibState state = LibState.getInstance();
    WeakReference<Camera> weakReference;
    int width;

    private class MyRenderer implements Renderer {
        int continuousErrorCount;
        boolean drawError;
        boolean hasSeenSuccess;
        boolean isInitRequired;

        private MyRenderer() {
            this.hasSeenSuccess = false;
            this.isInitRequired = true;
            this.continuousErrorCount = 0;
            this.drawError = false;
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            if (Log.isI) {
                Log.i(GLSurfaceView.TAG, "onSurfaceCreated");
            }
            GLSurfaceView.this.weakReference = null;
            GLSurfaceView.this.getGLHelper().finishGL();
            initPiecesIfNeeded();
            this.isInitRequired = true;
            this.hasSeenSuccess = true;
            this.continuousErrorCount = 0;
        }

        public void onSurfaceChanged(GL10 gl, int w, int h) {
            if (Log.isI) {
                Log.i(GLSurfaceView.TAG, "onSurfaceChanged: " + w + ", " + h);
            }
            GLSurfaceView.this.width = w;
            GLSurfaceView.this.height = h;
            gl.glViewport(0, 0, w, h);
            GLSurfaceView.this.getGLHelper().updateSize(w, h);
            if (this.isInitRequired) {
                GLSurfaceView.this.state.getBus().post(new SurfaceChangedEvent(w, h));
            }
        }

        private void initPiecesIfNeeded() {
            if (this.isInitRequired) {
                Boolean isSuccess = GLSurfaceView.this.getGLHelper().initPieces();
                if (isSuccess == null) {
                    return;
                }
                if (isSuccess.booleanValue()) {
                    this.isInitRequired = false;
                } else {
                    notifyOfError();
                }
            }
        }

        private void notifyOfError() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            this.continuousErrorCount++;
            this.drawError = this.continuousErrorCount >= 10;
            if (this.continuousErrorCount > 0) {
                CameraWrapper.logCrashlyticsValue("continuousErrorCount", String.valueOf(this.continuousErrorCount));
            }
        }

        private void notifyOfSuccess() {
            this.continuousErrorCount = 0;
            this.hasSeenSuccess = true;
        }

        public void onDrawFrame(GL10 gl) {
            initPiecesIfNeeded();
            if (!this.isInitRequired) {
                if (GLSurfaceView.this.glHelper.drawFrame()) {
                    notifyOfSuccess();
                } else {
                    notifyOfError();
                }
            }
            if (this.drawError) {
                GLSurfaceView.this.state.getBus().post(new OpenGLErrorEvent(GLSurfaceView.this.glHelper.getFailureString()));
                if (Log.isE) {
                    Log.e(GLSurfaceView.TAG, "Critical OpenGL Error, Aborting.");
                }
            }
            if (!GLSurfaceView.this.isPaused) {
                GLSurfaceView.this.requestRender();
            }
        }
    }

    public GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public GLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.renderer = new MyRenderer();
        if (VERSION.SDK_INT >= 11) {
            setPreserveEGLContextOnPause(true);
        }
        setEGLContextClientVersion(2);
        setEGLConfigChooser(new OptionalStencilConfigChooser(IS_STENCILING_ALLOWED, 1).setOnConfigChosenListener(this.glHelper));
        setRenderer(this.renderer);
        setRenderMode(0);
    }

    @Produce
    public SurfaceChangedEvent produceSurface() {
        return new SurfaceChangedEvent(this.width, this.height);
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (!this.isPaused) {
            requestRender();
        }
    }

    public void setGLScene(GLScene scene) {
        getGLHelper().setGLScene(scene);
    }

    public FPSHelper getFPSHelper() {
        return this.cameraFPS;
    }

    public void onPause() {
        super.onPause();
        this.state.getBus().unregister(this);
    }

    public void onResume() {
        super.onResume();
        this.state.getBus().register(this);
    }

    public void runOnOpenGLThread(Runnable runnable) {
        if (this.glHelper != null) {
            this.glHelper.runOnOpenGLThread(runnable);
        }
    }

    public GLHelper getGLHelper() {
        return this.glHelper;
    }

    public boolean isReadyToRock() {
        return this.renderer.hasSeenSuccess;
    }

    public void setAttribute1f(final GLAttribute attrib, final float x) {
        runOnOpenGLThread(new Runnable() {
            public void run() {
                GLES20.glVertexAttrib1f(attrib.getAttribIndex(), x);
                if (GLErrorChecker.checkGlError(GLSurfaceView.TAG)) {
                    Log.e(GLSurfaceView.TAG, "setAttribute1f: An error occurred.", GLErrorChecker.lastError);
                }
            }
        });
    }

    public void setAttribute2f(final GLAttribute attrib, final float x, final float y) {
        runOnOpenGLThread(new Runnable() {
            public void run() {
                GLES20.glVertexAttrib2f(attrib.getAttribIndex(), x, y);
                if (GLErrorChecker.checkGlError(GLSurfaceView.TAG)) {
                    Log.e(GLSurfaceView.TAG, "setAttribute1f: An error occurred.", GLErrorChecker.lastError);
                }
            }
        });
    }

    public void setAttribute3f(GLAttribute attrib, float x, float y, float z) {
        final GLAttribute gLAttribute = attrib;
        final float f = x;
        final float f2 = y;
        final float f3 = z;
        runOnOpenGLThread(new Runnable() {
            public void run() {
                GLES20.glVertexAttrib3f(gLAttribute.getAttribIndex(), f, f2, f3);
                if (GLErrorChecker.checkGlError(GLSurfaceView.TAG)) {
                    Log.e(GLSurfaceView.TAG, "setAttribute1f: An error occurred.", GLErrorChecker.lastError);
                }
            }
        });
    }
}
