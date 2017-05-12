package com.givewaygames.camera.graphics;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.util.SparseBooleanArray;
import com.crashlytics.android.Crashlytics;
import com.givewaygames.camera.state.AppState;
import com.givewaygames.camera.state.AppState.Mode;
import com.givewaygames.camera.ui.GLTransitionThread;
import com.givewaygames.camera.utils.AnalyticsHelper;
import com.givewaygames.camera.utils.FamousPicker.FamousBitmapProvider;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.camera.utils.TransitionPicker;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.CameraWrapper.DynamicImageMeshOrientation;
import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.CameraWrapper.StaticImageMeshOrientation;
import com.givewaygames.gwgl.face.QualcommFaceProcessor;
import com.givewaygames.gwgl.shader.IGLSetValue;
import com.givewaygames.gwgl.shader.ProgramSet;
import com.givewaygames.gwgl.shader.ShaderFactory;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.TextureSlotProvider;
import com.givewaygames.gwgl.utils.gl.GLClear;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.givewaygames.gwgl.utils.gl.GLScene;
import com.givewaygames.gwgl.utils.gl.GLTakePhoto;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import com.givewaygames.gwgl.utils.gl.GLWall;
import com.givewaygames.gwgl.utils.gl.camera.GLBufferManagedCamera;
import com.givewaygames.gwgl.utils.gl.camera.GLBufferManagedCamera.FauxSurface;
import com.givewaygames.gwgl.utils.gl.camera.GLCamera;
import com.givewaygames.gwgl.utils.gl.camera.GLSurfaceTextureCamera;
import com.givewaygames.gwgl.utils.gl.meshes.GLBitmapImage;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh;
import com.givewaygames.gwgl.utils.gl.meshes.GLWireframeMesh;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider;
import com.givewaygames.gwgl.utils.gl.stencil.GLMeshStencil;
import com.givewaygames.gwgl.utils.gl.stencil.GLMeshStencil.StencilMatchNotOnes;
import com.givewaygames.gwgl.utils.gl.stencil.GLMeshStencil.StencilMatchOnes;
import com.google.android.gms.cast.TextTrackStyle;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GoofyScene {
    public static final String TAG = "GoofyScene";
    private static final long TIME_BEFORE_REUSE = 10;
    FamousBitmapProvider bitmapProvider = new FamousBitmapProvider();
    QualcommFaceProcessor faceProcessor;
    GLScene glAsyncScene;
    GLCamera glCamera;
    GLBitmapImage glImportBitmap;
    GLTexture glImportTexture;
    GLBitmapImage glImportTopBitmap;
    GLWall glImportTopWall;
    GLWall glImportWall;
    GLMesh glMesh;
    GLScene glScene;
    GLBitmapImage glSecondaryImage;
    GLTexture glSecondaryTexture;
    GLTakePhoto glTakePhoto;
    GLMesh glTopMesh;
    GLTransitionThread glTransition;
    GLWall glWall;
    DynamicImageMeshOrientation imageMeshOrientation;
    DynamicImageMeshOrientation imageTopMeshOrientation;
    Queue<Integer> nextItems = new LinkedList();
    ListPointProvider pointProvider;
    ProgramSet programs;
    Random random = new Random();
    ShaderFactory shaderFactory;
    private int shadersSeen = 0;
    TakePhotoHelper takePhotoHelper = new TakePhotoHelper(this);
    GLWall topWall;
    TransitionPicker transitionPicker = new TransitionPicker();
    private final WeakReference<Context> weakContext;
    private float weightScale = TextTrackStyle.DEFAULT_FONT_SCALE;
    WeightedIndices weightedIndices = new WeightedIndices();

    private class ChangeShaderRunnable implements Runnable {
        final boolean doTransition;

        public ChangeShaderRunnable(boolean doTransition) {
            this.doTransition = doTransition;
        }

        public void run() {
            Context context = GoofyScene.this.getContext();
            if (context != null) {
                int size = GoofyScene.this.programs.size();
                for (int i = 0; i < size; i++) {
                    GLProgram program = (GLProgram) GoofyScene.this.programs.get(i);
                    if (program != null) {
                        boolean z;
                        if (i == GoofyScene.this.getState().getCurrentFilter()) {
                            GLProgram importWallProgram;
                            program.initialize();
                            GLProgram wallProgram = GoofyScene.this.topWall.isEnabled() ? GoofyScene.this.glWall.getProgram() : null;
                            if (wallProgram != null) {
                                wallProgram.initialize();
                            }
                            if (GoofyScene.this.glImportWall == null || !GoofyScene.this.glImportTopWall.isEnabled()) {
                                importWallProgram = null;
                            } else {
                                importWallProgram = GoofyScene.this.glImportWall.getProgram();
                            }
                            if (importWallProgram != null) {
                                importWallProgram.initialize();
                            }
                            if (GoofyScene.this.bitmapProvider.dirty()) {
                                GoofyScene.this.glImportBitmap.initialize();
                                GoofyScene.this.bitmapProvider.setDirty(false);
                            }
                            if (this.doTransition) {
                                if (GoofyScene.this.topWall.isEnabled()) {
                                    GoofyScene.this.topWall.setProgram(wallProgram);
                                }
                                if (GoofyScene.this.glImportTopWall.isEnabled()) {
                                    GoofyScene.this.glImportTopWall.setProgram(importWallProgram);
                                }
                                GoofyScene.this.glTransition.start(GoofyScene.this.transitionPicker, GoofyScene.this.random);
                            }
                            if (GoofyScene.this.glWall != null) {
                                GoofyScene.this.glWall.setProgram(program);
                            }
                            ShaderFactory.setupSecondaries(context, program.getTag(), GoofyScene.this.glSecondaryImage, GoofyScene.this.glSecondaryTexture);
                            GLProgram program2d = GoofyScene.this.programs.getOfType(program.getTag(), 3553);
                            program2d.initialize();
                            GoofyScene.this.glImportWall.setProgram(program2d);
                        }
                        if (i == GoofyScene.this.getState().getCurrentFilter()) {
                            z = true;
                        } else {
                            z = false;
                        }
                        program.setEnabled(z);
                    }
                }
                GoofyScene.this.shadersSeen = GoofyScene.this.shadersSeen + 1;
                Crashlytics.setInt("shaders_seen", GoofyScene.this.shadersSeen);
                GoofyScene.this.refreshTextureSize();
            }
        }
    }

    public GoofyScene(Context context) {
        this.weakContext = new WeakReference(context);
    }

    public AnalyticsHelper getAnalytics() {
        return AnalyticsHelper.getInstance();
    }

    public AppState getState() {
        return AppState.getInstance();
    }

    public CameraWrapper getCameraWrapper() {
        return getState().getCameraWrapper();
    }

    public GLHelper getGLHelper() {
        return getState().getGLHelper();
    }

    public GLCamera getGlCamera() {
        return this.glCamera;
    }

    public ProgramSet getPrograms() {
        return this.programs;
    }

    public Context getContext() {
        return (Context) this.weakContext.get();
    }

    public GLScene getScene() {
        return this.glScene;
    }

    public boolean isInitialized() {
        return this.glScene != null;
    }

    public GLScene initializeScene(Activity context, FauxSurface fauxSurface) {
        GLCamera gLBufferManagedCamera;
        if (Log.isD) {
            Log.d(TAG, "initializeScene");
        }
        CameraWrapper cameraWrapper = getCameraWrapper();
        GLHelper glHelper = getGLHelper();
        MeshOrientation meshOrientation = cameraWrapper.getMeshRotation();
        this.imageMeshOrientation = new DynamicImageMeshOrientation(context);
        this.imageTopMeshOrientation = new DynamicImageMeshOrientation(context);
        boolean isBufferManaged = getState().getUiState().isBufferManagedCamera();
        int cameraTextureType = isBufferManaged ? 3553 : 36197;
        GLScene glScene = new GLScene();
        TextureSlotProvider textureSlotProvider = new TextureSlotProvider(cameraTextureType);
        GLTexture gLTexture = new GLTexture(textureSlotProvider, GLHelper.DEFAULT_TEXTURE);
        if (isBufferManaged) {
            gLBufferManagedCamera = new GLBufferManagedCamera(gLTexture);
        } else {
            gLBufferManagedCamera = new GLSurfaceTextureCamera(gLTexture);
        }
        this.glCamera = gLBufferManagedCamera;
        getCameraWrapper().setCameraPreviewSetter(this.glCamera);
        if (this.glCamera instanceof GLBufferManagedCamera) {
            ((GLBufferManagedCamera) this.glCamera).setFauxSurface(fauxSurface);
        }
        this.glSecondaryTexture = new GLTexture(textureSlotProvider, "mosaic");
        this.glSecondaryTexture.setTextureType(3553);
        this.glSecondaryImage = new GLBitmapImage(null, this.glSecondaryTexture);
        this.shaderFactory = new ShaderFactory(context, glHelper);
        this.shaderFactory.setShaderStyle(0);
        int[] iArr = new int[28];
        this.programs = new ProgramSet(new int[]{30, 8, 9, 19, 1, 15, 12, 7, 11, 13, 22, 14, 18, 21, 3, 2, 23, 10, 4, 31, 29, 27, 5, 6, 28, 16, 24, 25}, this.shaderFactory, glScene, cameraTextureType);
        if (Log.isD) {
            Log.d(TAG, "A total of " + this.programs.size() + " programs are being used.");
        }
        this.weightedIndices.calculateWeights(this.programs);
        getNextShaderIdx(false);
        SparseBooleanArray preLoaded = new SparseBooleanArray();
        for (Integer intValue : this.nextItems) {
            int program = intValue.intValue();
            asyncLoadProgram(this.glAsyncScene, (GLProgram) this.programs.get(program));
            preLoaded.put(program, true);
        }
        Iterator i$ = this.programs.iterator();
        while (i$.hasNext()) {
            GLProgram glProgram = (GLProgram) i$.next();
            if (!preLoaded.get(glProgram.getTag(), false)) {
                asyncLoadProgram(this.glAsyncScene, glProgram);
            }
        }
        if (null != null) {
            this.pointProvider = (ListPointProvider) this.programs.getPointProvider(glHelper, getState().getCurrentFilter());
            this.glMesh = new GLEquationMesh(glHelper, null, this.glCamera, this.pointProvider, meshOrientation);
            ((GLEquationMesh) this.glMesh).setAddBorderPoints(true);
            this.glTopMesh = new GLEquationMesh(glHelper, null, this.glCamera, this.pointProvider, meshOrientation);
            ((GLEquationMesh) this.glTopMesh).setAddBorderPoints(true);
        } else {
            this.glMesh = new GLMesh(glHelper, null, this.glCamera, meshOrientation);
            this.glTopMesh = this.glMesh;
        }
        if (isBufferManaged) {
            this.glMesh.hackForBufferManagedCamera();
        }
        this.glMesh.addTexture(gLTexture);
        this.glMesh.addTexture(this.glSecondaryTexture);
        this.glMesh.registerToReceiveCameraMeshCallbacks();
        if (this.glMesh != this.glTopMesh) {
            if (isBufferManaged) {
                this.glTopMesh.hackForBufferManagedCamera();
            }
            this.glTopMesh.addTexture(gLTexture);
            this.glTopMesh.addTexture(this.glSecondaryTexture);
            this.glTopMesh.registerToReceiveCameraMeshCallbacks();
        }
        this.glTransition = new GLTransitionThread();
        this.transitionPicker = new TransitionPicker();
        GLEquationMesh stencilMesh = new GLEquationMesh(glHelper, null, null, this.transitionPicker.getNextTransition(this.random), new StaticImageMeshOrientation());
        GLProgram basicProgram = this.shaderFactory.getProgram(0, 3553);
        GLPiece glStencil = new GLMeshStencil(glHelper, basicProgram, stencilMesh).setMatch(new StencilMatchOnes());
        this.glWall = new GLWall(null, this.glMesh, null);
        if (glStencil != null) {
            this.glTransition.addStencil(glStencil);
            this.glWall.addPostProgramPiece(glStencil);
        }
        this.topWall = new GLWall(null, this.glTopMesh, null);
        GLPiece glReverseStencil = new GLMeshStencil(glHelper, basicProgram, stencilMesh).setMatch(new StencilMatchNotOnes());
        if (glReverseStencil != null) {
            this.glTransition.addStencil(glReverseStencil);
            this.topWall.addPostProgramPiece(glReverseStencil);
        }
        if (stencilMesh != null) {
            this.transitionPicker.add(stencilMesh);
        }
        this.glTakePhoto = new GLTakePhoto(getGLHelper());
        this.glTakePhoto.setMemoryClass(((ActivityManager) context.getSystemService("activity")).getMemoryClass());
        this.glTakePhoto.addSecondaryTexture(this.glSecondaryTexture);
        this.glTakePhoto.setMeshOrientation(meshOrientation);
        this.glImportTexture = new GLTexture(textureSlotProvider, GLHelper.DEFAULT_TEXTURE);
        this.glImportTexture.setTextureType(3553);
        this.glImportBitmap = new GLBitmapImage(null, this.glImportTexture);
        this.glImportBitmap.setBitmapProvider(context, this.bitmapProvider);
        this.glImportBitmap.setMeshOrientation(this.imageMeshOrientation);
        GLMesh gLMesh = new GLMesh(glHelper, this.glImportTexture, this.glImportBitmap, this.imageMeshOrientation);
        gLMesh.addTexture(this.glSecondaryTexture);
        this.glImportWall = new GLWall(null, gLMesh, null);
        if (glStencil != null) {
            this.glImportWall.addPostProgramPiece(glStencil);
        }
        gLTexture = new GLTexture(textureSlotProvider, GLHelper.DEFAULT_TEXTURE);
        gLTexture.setTextureType(3553);
        this.glImportTopBitmap = new GLBitmapImage(null, gLTexture);
        this.glImportTopBitmap.setMeshOrientation(this.imageTopMeshOrientation);
        gLMesh = new GLMesh(glHelper, gLTexture, this.glImportTopBitmap, this.imageTopMeshOrientation);
        gLMesh.addTexture(this.glSecondaryTexture);
        this.glImportTopWall = new GLWall(null, gLMesh, null);
        if (glReverseStencil != null) {
            this.glImportTopWall.addPostProgramPiece(glReverseStencil);
        }
        if (null != null) {
            this.faceProcessor = new QualcommFaceProcessor(glHelper, this.pointProvider, (GLEquationMesh) this.glMesh);
            this.faceProcessor.initOpenGl(this.glCamera);
        }
        glScene.add(this.glTakePhoto);
        glScene.add(new GLClear(CameraWrapper.DEBUG ? -16764314 : ViewCompat.MEASURED_STATE_MASK));
        glScene.add(this.glCamera);
        glScene.add(gLTexture);
        glScene.add(this.glSecondaryImage);
        glScene.add(this.glSecondaryTexture);
        glScene.add(this.glTransition);
        glScene.add(this.glWall);
        glScene.add(this.topWall);
        glScene.add(this.glImportBitmap);
        glScene.add(this.glImportTopBitmap);
        glScene.add(this.glImportTexture);
        glScene.add(gLTexture);
        glScene.add(this.glImportWall);
        glScene.add(this.glImportTopWall);
        if (false) {
            glScene.add(new GLWireframeMesh(this.glTopMesh));
        }
        pickRandomFilter(context);
        cameraWrapper.refreshPreviewMeshSizes(glScene);
        this.glScene = glScene;
        changeMode(context, Mode.CAMERA);
        return glScene;
    }

    private void asyncLoadProgram(GLScene glAsyncScene, GLProgram glProgram) {
    }

    public void pickRandomFilter(Context context) {
        int nextShader = getNextShaderIdx(true);
        if (Log.isV) {
            int id = ((GLProgram) this.programs.get(nextShader)).getTag();
            Log.v(TAG, "currentShader: " + nextShader + "  id: " + id + "  name: " + ((String) ShaderFactory.shaderNameLookup.get(id, "" + id)));
        }
        enableFilter(context, nextShader, true, true);
        randomizeCurrentVariables();
    }

    public void setFilterWithRandomParams(Context context, int filter) {
        enableFilterOfType(context, filter, true, false);
        randomizeCurrentVariables();
    }

    public boolean takePhoto(Context context) {
        int optMoreDetails = 0;
        if (isTakingPhoto()) {
            return false;
        }
        GLProgram program = (GLProgram) this.programs.get(getState().getCurrentFilter());
        boolean res = this.takePhotoHelper.takePhoto(context, this.programs.getOfType(program.getTag(), 3553));
        if (getAnalytics().isEnabled()) {
            if (getState().isModeCamera()) {
                optMoreDetails = this.glImportBitmap.getResourceId();
            }
            int newIdx = program.getTag();
            getAnalytics().trackUiEvent("Save", (String) ShaderFactory.shaderNameLookup.get(newIdx, "" + newIdx), (long) optMoreDetails);
        }
        return res;
    }

    public boolean isTakingPhoto() {
        return this.glTakePhoto != null ? this.glTakePhoto.getIsProcessing() : false;
    }

    public void takeScreenshot(String path) {
        this.glTakePhoto.takePhotoOfCurrentSetup(null, path, 300, 300);
    }

    public void onImageSelected(final Context context, final Uri uri) {
        getGLHelper().runOnOpenGLThread(new Runnable() {
            public void run() {
                GoofyScene.this.glImportBitmap.setUri(context, uri);
                boolean isValid = true & GoofyScene.this.glImportBitmap.initialize();
                GoofyScene.this.glImportTopBitmap.setDependentBitmapImage(GoofyScene.this.glImportBitmap);
                isValid &= GoofyScene.this.glImportTopBitmap.initialize();
                GoofyScene.this.bitmapProvider.setDirty(false);
                if (!isValid) {
                    Toast.makeText(R.string.image_import_failed, 0);
                }
            }
        });
    }

    private int getNextShaderIdx(boolean remove) {
        int iter = 50;
        boolean done = false;
        while (((long) this.nextItems.size()) < TIME_BEFORE_REUSE && !done) {
            int idx = this.weightedIndices.getIndexFor(this.random.nextFloat() * this.weightScale);
            if (!this.nextItems.contains(Integer.valueOf(idx))) {
                this.nextItems.add(Integer.valueOf(idx));
            }
            iter--;
            done = (iter <= 0 && this.nextItems.size() > 0) || ((long) this.programs.size()) < TIME_BEFORE_REUSE;
        }
        if (Log.isW && done) {
            Log.w(TAG, "getNextShaderIdx maxed out its iterations.");
        }
        return (remove ? (Integer) this.nextItems.poll() : (Integer) this.nextItems.peek()).intValue();
    }

    public void enableFilterOfType(Context context, int type, boolean doTransition, boolean loadNewImage) {
        enableFilter(context, this.programs.getIndexForType(type), doTransition, loadNewImage);
    }

    public void enableFilter(Context context, int idx, boolean doTransition, boolean loadNewImage) {
        if (this.glMesh instanceof GLEquationMesh) {
            this.pointProvider.updatePosition(TextTrackStyle.DEFAULT_FONT_SCALE);
            ((GLEquationMesh) this.glTopMesh).swapPointProvider(this.pointProvider);
            this.pointProvider.updatePosition(0.0f);
            ((GLEquationMesh) this.glTopMesh).updateUVValues();
            this.pointProvider = (ListPointProvider) this.programs.getPointProvider(getGLHelper(), idx);
            this.faceProcessor.setPointProvider(this.pointProvider);
            ((GLEquationMesh) this.glMesh).swapPointProvider(this.pointProvider);
        }
        if (doTransition && getState().isModeFamous()) {
            this.glImportTopBitmap.updateImage(this.bitmapProvider.getBitmap(context));
            if (loadNewImage) {
                this.bitmapProvider.loadNewImage(context);
            }
        }
        boolean stencil = getState().canStencil();
        if (this.topWall != null) {
            boolean isEnabled = stencil && getState().isModeCamera();
            this.topWall.setEnabled(isEnabled);
        }
        if (this.glImportWall != null) {
            if (!stencil || getState().isModeCamera()) {
                isEnabled = false;
            } else {
                isEnabled = true;
            }
            this.glImportTopWall.setEnabled(isEnabled);
        }
        getState().setCurrentFilter(idx);
        getGLHelper().runOnOpenGLThread(new ChangeShaderRunnable(doTransition));
    }

    public void randomizeCurrentVariables() {
        List<IGLSetValue> variables = ((GLProgram) this.programs.get(getState().getCurrentFilter())).getPixelShader().getUserEditableVariables();
        for (int i = 0; i < variables.size(); i++) {
            ((IGLSetValue) variables.get(i)).setRandomValue(this.random);
        }
    }

    public void refreshCurrentFilter(Context context) {
        enableFilter(context, getState().getCurrentFilter(), false, false);
    }

    public void changeMode(Context context, Mode oldMode) {
        boolean z = true;
        if (isInitialized()) {
            boolean z2;
            if (oldMode == Mode.IMPORTED && getState().isModeFamous()) {
                this.bitmapProvider.loadNewImage(context);
            }
            boolean isCamera = getState().isModeCamera();
            boolean isFamous = getState().isModeFamous();
            this.glCamera.setEnabled(isCamera);
            this.glMesh.setEnabled(isCamera);
            this.glWall.setEnabled(isCamera);
            this.topWall.setEnabled(isCamera);
            GLBitmapImage gLBitmapImage = this.glImportBitmap;
            if (isCamera) {
                z2 = false;
            } else {
                z2 = true;
            }
            gLBitmapImage.setEnabled(z2);
            GLTexture gLTexture = this.glImportTexture;
            if (isCamera) {
                z2 = false;
            } else {
                z2 = true;
            }
            gLTexture.setEnabled(z2);
            GLWall gLWall = this.glImportWall;
            if (isCamera) {
                z2 = false;
            } else {
                z2 = true;
            }
            gLWall.setEnabled(z2);
            GLWall gLWall2 = this.glImportTopWall;
            if (isCamera) {
                z = false;
            }
            gLWall2.setEnabled(z);
            boolean doRefresh = false;
            if (isFamous) {
                this.glImportBitmap.setBitmapProvider(context, this.bitmapProvider);
                doRefresh = this.bitmapProvider.hasImage();
                if (doRefresh) {
                    enableFilter(context, getState().getCurrentFilter(), false, false);
                }
            }
            if (!doRefresh) {
                refreshTextureSize();
            }
        } else if (Log.isI) {
            Log.i(TAG, "Cannot change mode.  Not initialized.");
        }
    }

    public void updateViewOrientations() {
        if (this.imageMeshOrientation != null) {
            this.imageMeshOrientation.setRotationAmount(0);
        }
        if (this.imageTopMeshOrientation != null) {
            this.imageTopMeshOrientation.setRotationAmount(0);
        }
    }

    private void refreshTextureSize() {
        if (getState().isModeCamera()) {
            getGLHelper().updateTextureSizeToCanvasSize();
        } else {
            getGLHelper().updateTextureSize(this.glImportTopBitmap.getWidth(), this.glImportTopBitmap.getHeight());
        }
    }
}
