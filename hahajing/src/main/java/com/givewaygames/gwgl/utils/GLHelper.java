package com.givewaygames.gwgl.utils;

import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.preview.configs.OptionalStencilConfigChooser.OnConfigChosen;
import com.givewaygames.gwgl.shader.PixelShader;
import com.givewaygames.gwgl.shader.VertexShader;
import com.givewaygames.gwgl.utils.gl.GLClear;
import com.givewaygames.gwgl.utils.gl.GLImage;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.givewaygames.gwgl.utils.gl.GLScene;
import com.givewaygames.gwgl.utils.gl.GLTakePhoto;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import com.givewaygames.gwgl.utils.gl.GLWall;
import com.givewaygames.gwgl.utils.gl.camera.GLSurfaceTextureCamera;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GLHelper implements OnConfigChosen {
    public static final String DEFAULT_TEXTURE = "texture";
    private static final String TAG = "GLHelper";
    Map<Class, Object> activeObject = new HashMap();
    int cameraTextureType;
    boolean canStencil = true;
    String failureString;
    GLScene glScene;
    int height;
    boolean isRendering = true;
    OnInitializationFinished onInitFinished;
    Queue<Runnable> runnableQueue = new ConcurrentLinkedQueue();
    ArrayList<GLWindowSizeListener> sizeChangedListeners = new ArrayList();
    int textureHeight;
    int textureWidth;
    int width;

    public interface GLWindowSizeListener {
        void onSizeChanged(int i, int i2);
    }

    public interface OnInitializationFinished {
        void onInitializedFinished();
    }

    private class OnSizeChangeNotifyScene implements Runnable {
        private OnSizeChangeNotifyScene() {
        }

        public void run() {
            Iterator i$ = GLHelper.this.glScene.iterator();
            while (i$.hasNext()) {
                GLPiece glPiece = (GLPiece) i$.next();
                if (glPiece != null && glPiece.isInitialized() && glPiece.isEnabled() && (glPiece instanceof GLProgram)) {
                    ((GLProgram) glPiece).getPixelShader().onOrientationChange();
                }
            }
        }
    }

    public GLHelper(int cameraTextureType) {
        this.cameraTextureType = cameraTextureType;
    }

    public boolean getCanStencil() {
        return this.canStencil;
    }

    public String getFailureString() {
        return this.failureString;
    }

    public void setActiveObject(Class cls, Object obj) {
        this.activeObject.put(cls, obj);
    }

    public Object getActiveObject(Class cls) {
        return this.activeObject.get(cls);
    }

    public void setOnInitializationFinished(OnInitializationFinished onInitFinished) {
        this.onInitFinished = onInitFinished;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTextureWidth() {
        return this.textureWidth;
    }

    public int getTextureHeight() {
        return this.textureHeight;
    }

    public GLScene getGLScene() {
        return this.glScene;
    }

    public void registerWindowSizeListener(GLWindowSizeListener listener) {
        this.sizeChangedListeners.add(listener);
    }

    public void updateSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.textureWidth = width;
        this.textureHeight = height;
        refreshPreviewMeshSizes(width, height);
        runOnOpenGLThread(new OnSizeChangeNotifyScene());
        Iterator i$ = this.sizeChangedListeners.iterator();
        while (i$.hasNext()) {
            ((GLWindowSizeListener) i$.next()).onSizeChanged(width, height);
        }
    }

    public void updateTextureSizeToCanvasSize() {
        if (this.textureWidth != this.width || this.textureHeight != this.height) {
            this.textureWidth = this.width;
            this.textureHeight = this.height;
            runOnOpenGLThread(new OnSizeChangeNotifyScene());
        }
    }

    public void updateTextureSize(int width, int height) {
        if (this.textureWidth != width || this.textureHeight != height) {
            this.textureWidth = width;
            this.textureHeight = height;
            runOnOpenGLThread(new OnSizeChangeNotifyScene());
        }
    }

    public void setGLScene(GLScene glScene) {
        this.glScene = glScene;
    }

    public void initializeDefaultScene(PixelShader pixelShader, VertexShader vertexShader, MeshOrientation orient) {
        this.glScene = new GLScene();
        GLProgram glProgram = new GLProgram(this, this.cameraTextureType, null, null);
        glProgram.setPixelShader(pixelShader);
        glProgram.setVertexShader(vertexShader);
        this.glScene.add(glProgram);
        TextureSlotProvider slotProvider = new TextureSlotProvider(this.cameraTextureType);
        GLTexture glTexture = new GLTexture(slotProvider, DEFAULT_TEXTURE);
        GLImage glCamera = new GLSurfaceTextureCamera(glTexture);
        this.glScene.add(new GLTakePhoto(this, slotProvider));
        this.glScene.add(new GLClear(-256));
        this.glScene.add(glCamera);
        this.glScene.add(glTexture);
        this.glScene.add(new GLWall(glProgram, new GLMesh(this, glTexture, glCamera, orient), null));
    }

    public void runOnOpenGLThread(Runnable r) {
        this.runnableQueue.add(r);
    }

    public Boolean initPieces() {
        if (this.glScene == null) {
            return null;
        }
        boolean isSuccess = true;
        Iterator i$ = this.glScene.iterator();
        while (i$.hasNext()) {
            GLPiece piece = (GLPiece) i$.next();
            if (!(piece.isInitialized() || piece.initialize())) {
                this.failureString = "Initializing piece: " + piece;
                isSuccess = false;
            }
        }
        return Boolean.valueOf(isSuccess);
    }

    public boolean drawFrame() {
        if (!this.isRendering) {
            return true;
        }
        if (this.glScene == null) {
            return false;
        }
        while (!this.runnableQueue.isEmpty()) {
            ((Runnable) this.runnableQueue.poll()).run();
        }
        long time = System.currentTimeMillis();
        int maxPasses = 1;
        boolean done = false;
        int pass = 0;
        while (pass < maxPasses && !done) {
            Iterator i$ = this.glScene.iterator();
            while (i$.hasNext()) {
                GLPiece piece = (GLPiece) i$.next();
                done = piece.blockNextPieces();
                if (done) {
                    piece.draw(pass, time);
                    break;
                }
                int numPasses = piece.getNumPasses();
                if (numPasses > maxPasses) {
                    maxPasses = numPasses;
                }
                if (piece.isEnabled() && pass < numPasses && piece.isInitialized()) {
                    if (!piece.draw(pass, time)) {
                        this.failureString = "Draw failed: " + piece;
                        if (Log.isE) {
                            Log.e(TAG, this.failureString);
                        }
                        return false;
                    } else if (GLErrorChecker.checkGlError(TAG)) {
                        this.failureString = "Draw failed after with GLErrorChecker.";
                        return false;
                    }
                }
            }
            pass++;
        }
        return true;
    }

    public void refreshPreviewMeshSizes(int w, int h) {
        if (this.glScene != null) {
            for (GLPiece piece : this.glScene.getAllInstancesOf(GLMesh.class)) {
                MeshOrientation meshOrientation = ((GLMesh) piece).getMeshOrientation();
                if (meshOrientation != null) {
                    meshOrientation.setPreviewWidth(w);
                    meshOrientation.setPreviewHeight(h);
                }
            }
            for (GLPiece piece2 : this.glScene.getAllInstancesOf(GLWall.class)) {
                GLMesh mesh = ((GLWall) piece2).getMesh();
                if ((mesh != null ? mesh.getMeshOrientation() : null) != null) {
                    mesh.getMeshOrientation().setPreviewWidth(w);
                    mesh.getMeshOrientation().setPreviewHeight(h);
                }
            }
        }
    }

    public void finishGL() {
        if (Log.isD) {
            Log.d(TAG, "finishGL");
        }
        if (this.glScene != null) {
            Iterator i$ = this.glScene.iterator();
            while (i$.hasNext()) {
                GLPiece piece = (GLPiece) i$.next();
                if (piece.isInitialized()) {
                    piece.release();
                }
            }
        }
    }

    public void onConfigChosen(boolean hasStencil) {
        this.canStencil = hasStencil;
    }
}
