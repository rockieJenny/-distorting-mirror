package com.givewaygames.gwgl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import com.givewaygames.gwgl.events.CameraMeshRefreshEvent;
import com.givewaygames.gwgl.events.SurfaceChangedEvent;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.GLScene;
import com.givewaygames.gwgl.utils.gl.GLTakePhoto;
import com.givewaygames.gwgl.utils.gl.GLWall;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Subscribe;
import java.io.IOException;
import java.util.List;

public class CameraWrapper implements ErrorCallback {
    public static boolean DEBUG = false;
    public static final boolean DISABLE_CAMERA_FOR_TESTING = false;
    public static final boolean IS_MONKEY_TEST = false;
    public static final boolean IS_TEST_BUILD = false;
    public static final int MAX_ALLOWED_FPS = 25;
    public static final int MIN_ALLOWED_FPS = 8;
    public static final boolean NO_CAMERA_FOR_TESTING = false;
    public static final String SETTING_AUTO_ADJUST = "SETTING_AUTO_ADJUST";
    public static final String SETTING_QUALITY_INDEX = "SETTING_QUALITY_INDEX";
    private static final String TAG = "CameraWrapper";
    public static final Object cameraLock = new Object();
    public static OnCrashlytics onCrashlyticsLog = null;
    boolean autoAdjustResolution = false;
    private Camera camera;
    private int cameraId;
    public final Object cameraInitLock = new Object();
    CameraPreviewSetter cameraPreviewSetter = null;
    private CameraSizer cameraSizer = new CameraSizer();
    IColorFilterSurface colorFilterSurface = null;
    boolean ignoreCameraSetup = false;
    boolean isCameraStarted = false;
    boolean isSurfaceReady = false;
    MeshOrientation meshOrientation;
    OnCameraInfoReceived onCameraInfoReceived = null;
    OnInvalidCamera onInvalidCamera = null;
    private int previewSizeIndex = -1;
    LibState state = LibState.getInstance();
    boolean useFrontFacingCamera = false;

    public interface CameraPreviewSetter {
        boolean isReady();

        boolean setCameraPreview(Camera camera) throws IOException;
    }

    public static class MeshOrientation {
        boolean flipX = true;
        boolean flipY = false;
        int imageHeight = 0;
        int imageWidth = 0;
        SafeCameraInfo info;
        boolean isDirty = false;
        boolean isScaleEnabled = true;
        int naturalRotation = -1;
        int previewHeight = 0;
        int previewWidth = 0;

        public MeshOrientation(SafeCameraInfo info, Activity activity) {
            setRotation(activity);
            setCameraInfo(info);
        }

        protected MeshOrientation() {
        }

        public MeshOrientation(MeshOrientation other) {
            this.info = other.info;
            this.naturalRotation = other.naturalRotation;
            this.flipX = other.flipX;
            this.flipY = other.flipY;
            this.previewWidth = other.previewWidth;
            this.previewHeight = other.previewHeight;
            this.imageWidth = other.imageWidth;
            this.imageHeight = other.imageHeight;
            dirty();
        }

        public void setIsScaleEnabled(boolean isScaleEnabled) {
            this.isScaleEnabled = isScaleEnabled;
        }

        public boolean isScaleEnabled() {
            return this.isScaleEnabled;
        }

        public boolean isDirty() {
            return this.isDirty;
        }

        public boolean isFrontFacing() {
            return this.info == null || this.info.facing == SafeCameraInfo.CAMERA_FACING_FRONT;
        }

        public void dirty() {
            this.isDirty = true;
        }

        public void clean() {
            this.isDirty = false;
        }

        public void setFlipX(boolean flip) {
            this.flipX = flip;
            dirty();
        }

        public void setFlipY(boolean flip) {
            this.flipY = flip;
            dirty();
        }

        private void setRotation(Activity activity) {
            switch (activity.getWindow().getWindowManager().getDefaultDisplay().getRotation()) {
                case 0:
                    this.naturalRotation = 0;
                    break;
                case 1:
                    this.naturalRotation = 90;
                    break;
                case 2:
                    this.naturalRotation = 180;
                    break;
                case 3:
                    this.naturalRotation = 270;
                    break;
            }
            dirty();
        }

        private void setCameraInfo(SafeCameraInfo info) {
            this.info = info;
        }

        public boolean getFlipX(boolean isForBufferManagedCamera) {
            if (isFrontFacing() || !isForBufferManagedCamera) {
                return this.flipX;
            }
            return getRotationAmount() % 180 == 90;
        }

        public boolean getFlipY(boolean isForBufferManagedCamera) {
            if (isFrontFacing() || !isForBufferManagedCamera) {
                return this.flipY;
            }
            return getRotationAmount() % 180 == 90;
        }

        protected boolean needsScaleFlip() {
            float amt = (float) getRotationAmount();
            return amt == 90.0f || amt == BitmapDescriptorFactory.HUE_VIOLET;
        }

        public float getScaleX() {
            if (this.imageWidth == 0 || this.imageHeight == 0 || this.previewWidth == 0 || this.previewHeight == 0) {
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            boolean flip = needsScaleFlip();
            float pAspect = ((float) this.previewWidth) / ((float) this.previewHeight);
            float bAspect = ((float) (flip ? this.imageHeight : this.imageWidth)) / ((float) (flip ? this.imageWidth : this.imageHeight));
            if (bAspect <= pAspect) {
                return bAspect / pAspect;
            }
            return TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public float getScaleY() {
            if (this.imageWidth == 0 || this.imageHeight == 0 || this.previewWidth == 0 || this.previewHeight == 0) {
                return TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            boolean flip = needsScaleFlip();
            float pAspect = ((float) this.previewWidth) / ((float) this.previewHeight);
            float bAspect = ((float) (flip ? this.imageHeight : this.imageWidth)) / ((float) (flip ? this.imageWidth : this.imageHeight));
            if (bAspect > pAspect) {
                return pAspect / bAspect;
            }
            return TextTrackStyle.DEFAULT_FONT_SCALE;
        }

        public void setPreviewWidth(int previewWidth) {
            this.previewWidth = previewWidth;
            dirty();
        }

        public void setPreviewHeight(int previewHeight) {
            this.previewHeight = previewHeight;
            dirty();
        }

        public void setPreviewSize(int previewWidth, int previewHeight) {
            if (Log.isD) {
                Log.d("MeshOrientation", "setPreviewSize: " + previewWidth + ", " + previewHeight);
            }
            setPreviewWidth(previewWidth);
            setPreviewHeight(previewHeight);
        }

        public void setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            dirty();
        }

        public void setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            dirty();
        }

        public void setImageSize(Size size) {
            if (Log.isV) {
                Log.v("MeshOrientation", "setImageSize: " + size.width + ", " + size.height);
            }
            if (size != null) {
                setImageWidth(size.width);
                setImageHeight(size.height);
            }
        }

        public int getRotationAmount() {
            if (this.info == null) {
                return 0;
            }
            if (this.info.facing == SafeCameraInfo.CAMERA_FACING_FRONT) {
                return (360 - ((this.info.orientation + this.naturalRotation) % 360)) % 360;
            }
            return ((this.info.orientation - this.naturalRotation) + 360) % 360;
        }

        protected int fix(int degrees) {
            return ((degrees % 360) + 360) % 360;
        }

        public int getRotationForCameraParameter() {
            return 0;
        }

        public int getRotationForPhotoBitmap() {
            return fix(-this.naturalRotation);
        }

        public void fixFlipXForPhotos() {
            if (this.info != null) {
                setFlipX(this.info.facing != SafeCameraInfo.CAMERA_FACING_FRONT);
            }
        }
    }

    public interface OnCameraInfoReceived {
        void onCameraInfoReceived(SafeCameraInfo safeCameraInfo);
    }

    public interface OnCrashlytics {
        void logException(Exception exception);

        void logString(String str, String str2);
    }

    public interface OnInvalidCamera {
        void onCameraCrash(int i, Camera camera);

        void onInvalidCamera(String str, Exception exception);
    }

    public interface OnPhotoTaken {
        void onPictureTaken(boolean z, String str);

        void onProgressUpdate(int i);

        void onRestartPreview();
    }

    public static class SafeCameraInfo {
        public static final int CAMERA_FACING_BACK = 0;
        public static final int CAMERA_FACING_FRONT = 1;
        public int facing;
        public int orientation;

        static {
            if (VERSION.SDK_INT >= 9) {
            }
            if (VERSION.SDK_INT >= 9) {
            }
        }

        public SafeCameraInfo(int facing, int orientation) {
            this.facing = facing;
            this.orientation = orientation;
        }
    }

    public static class PhotoMeshOrientation extends MeshOrientation {
        MeshOrientation cameraOrientation;

        public PhotoMeshOrientation(MeshOrientation orient) {
            super(orient);
            this.cameraOrientation = orient;
        }

        public int getRotationAmount() {
            return fix(180 - (this.cameraOrientation.naturalRotation + this.info.orientation));
        }

        public int getRotationForPhotoBitmap() {
            if (isFrontFacing() || fix(this.cameraOrientation.naturalRotation) % 180 != 90) {
                return 0;
            }
            return 180;
        }
    }

    public static class StaticImageMeshOrientation extends MeshOrientation {
        public StaticImageMeshOrientation() {
            this.flipX = false;
            this.flipY = false;
        }

        public int getRotationAmount() {
            return 0;
        }

        public int getRotationForPhotoBitmap() {
            return 0;
        }

        public void fixFlipXForPhotos() {
            this.flipX = !this.flipX;
        }
    }

    public static class DynamicImageMeshOrientation extends StaticImageMeshOrientation {
        int rotation = 0;

        public DynamicImageMeshOrientation(Activity activity) {
            setWindowRotation(activity);
        }

        public void setRotationAmount(int rotation) {
            this.rotation = rotation;
            dirty();
        }

        public int getRotationAmount() {
            return ((this.rotation - 90) + 360) % 360;
        }

        protected boolean needsScaleFlip() {
            float amt = (float) getRotationAmount();
            return amt == 0.0f || amt == BitmapDescriptorFactory.HUE_CYAN;
        }

        private void setWindowRotation(Activity activity) {
            switch (activity.getWindow().getWindowManager().getDefaultDisplay().getRotation()) {
                case 0:
                    this.naturalRotation = 0;
                    break;
                case 1:
                    this.naturalRotation = 90;
                    break;
                case 2:
                    this.naturalRotation = 180;
                    break;
                case 3:
                    this.naturalRotation = 270;
                    break;
            }
            dirty();
        }
    }

    public static void logCrashlyticsValue(String log) {
        if (Log.isV) {
            Log.v(TAG, "CrashlyticsLog: " + log);
        }
        if (onCrashlyticsLog != null) {
            onCrashlyticsLog.logString(null, log);
        }
    }

    public static void logCrashlyticsValue(String key, String value) {
        if (Log.isV) {
            Log.v(TAG, "CrashlyticsLog: " + key + ": " + value);
        }
        if (onCrashlyticsLog != null) {
            onCrashlyticsLog.logString(key, value);
        }
    }

    public static void logCrashlyticsException(Exception e) {
        if (Log.isE) {
            Log.e(TAG, "CrashlyticsException", e);
        }
        if (onCrashlyticsLog != null) {
            onCrashlyticsLog.logException(e);
        }
    }

    public boolean onCreate(Activity c) {
        if (DEBUG) {
            Log.v(TAG, "onCreate");
        }
        this.state.getBus().register(this);
        this.meshOrientation = new StaticImageMeshOrientation();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        this.previewSizeIndex = settings.getInt(SETTING_QUALITY_INDEX, -1);
        this.autoAdjustResolution = settings.getBoolean(SETTING_AUTO_ADJUST, true);
        if (!this.autoAdjustResolution) {
            this.previewSizeIndex = -1;
        }
        if (c instanceof OnInvalidCamera) {
            this.onInvalidCamera = (OnInvalidCamera) c;
        }
        if (c instanceof OnCameraInfoReceived) {
            this.onCameraInfoReceived = (OnCameraInfoReceived) c;
        }
        if (DEBUG) {
            Log.v(TAG, "previewSizeIndex: " + this.previewSizeIndex);
        }
        this.ignoreCameraSetup = !hasCamera(c);
        return true;
    }

    public MeshOrientation getMeshRotation() {
        return this.meshOrientation;
    }

    public void onSurfaceReady() {
        this.isSurfaceReady = true;
    }

    public void setCameraPreviewSetter(CameraPreviewSetter cameraPreviewSetter) {
        this.cameraPreviewSetter = cameraPreviewSetter;
        restartPreviewIfReady();
    }

    public void setAutoAdjustResolution(boolean enabled) {
        this.autoAdjustResolution = enabled;
        if (!enabled) {
            this.cameraSizer.calculatePreviewSize(true);
        }
    }

    public void setTryMaximumPreviewSize(boolean tryMax) {
        this.cameraSizer.setTryMaximumSize(tryMax);
    }

    public void setUseFrontFacingCamera(boolean ffc) {
        this.useFrontFacingCamera = ffc;
    }

    public boolean getUseFrontFacingCamera() {
        return this.useFrontFacingCamera;
    }

    public boolean getAutoAdjustResolution() {
        return this.autoAdjustResolution;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public CameraSizer getCameraSizer() {
        return this.cameraSizer;
    }

    public boolean hasCamera(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature("android.hardware.camera") || pm.hasSystemFeature("android.hardware.camera.front");
    }

    @TargetApi(9)
    public boolean hasMultipleCameras(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean back = pm.hasSystemFeature("android.hardware.camera");
        boolean front = pm.hasSystemFeature("android.hardware.camera.front");
        if (front && back && Camera.getNumberOfCameras() == 1) {
            back = false;
        }
        if (front && back) {
            return true;
        }
        return false;
    }

    public boolean onResume(Activity activity) {
        if (DEBUG) {
            Log.v(TAG, "onResume");
        }
        this.camera = initCameraInstance(activity, this.useFrontFacingCamera, true);
        if (DEBUG && this.camera == null) {
            Log.v(TAG, "onResume failed, null camera.");
        }
        restartPreviewIfReady();
        if (this.camera != null) {
            return true;
        }
        return false;
    }

    public void onPause(Context c) {
        if (DEBUG) {
            Log.v(TAG, "onPause");
        }
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putInt(SETTING_QUALITY_INDEX, this.cameraSizer != null ? this.cameraSizer.getPreviewSizeIndex() : -1);
        editor.commit();
        this.isSurfaceReady = false;
        stopCamera();
    }

    private void stopCamera() {
        synchronized (cameraLock) {
            if (this.camera != null) {
                if (DEBUG) {
                    Log.v(TAG, "camera.release()");
                }
                if (this.isCameraStarted) {
                    try {
                        this.camera.stopPreview();
                    } catch (Exception e) {
                    }
                }
                this.isCameraStarted = false;
                try {
                    this.camera.setPreviewCallback(null);
                } catch (Exception e2) {
                    logCrashlyticsValue("stopCamera: setPreviewCallback failed.");
                }
                try {
                    this.camera.release();
                } catch (Exception e3) {
                    logCrashlyticsValue("stopCamera: release failed.");
                }
                this.camera = null;
            }
        }
    }

    public boolean restartPreviewIfReady() {
        boolean ret = false;
        synchronized (cameraLock) {
            boolean isSetterReady = this.cameraPreviewSetter != null && this.cameraPreviewSetter.isReady();
            if (this.isSurfaceReady && isSetterReady) {
                ret = restartPreviewInner();
            }
        }
        return ret;
    }

    @Subscribe
    public void onSurfaceChanged(SurfaceChangedEvent event) {
        onSurfaceReady();
        this.cameraSizer.calculatePreviewSize(false, event.width, event.height);
        restartPreviewIfReady();
    }

    public void setOrientation(Activity activity) {
        this.meshOrientation.setRotation(activity);
    }

    @TargetApi(14)
    private boolean restartPreviewInner() {
        if (DEBUG) {
            Log.v(TAG, "restartPreviewInner");
        }
        if (this.camera == null) {
            if (DEBUG) {
                Log.v(TAG, "restartPreviewInner: Null camera");
            }
            return false;
        }
        if (this.isCameraStarted) {
            try {
                this.camera.stopPreview();
            } catch (Exception e) {
            }
        }
        if (DEBUG) {
            Log.v(TAG, "restartPreviewInner milestone 1");
        }
        this.isCameraStarted = false;
        if (!this.isSurfaceReady) {
            if (DEBUG) {
                Log.v(TAG, "restartPreviewInner isSurfaceReady == false");
            }
            return false;
        } else if (this.cameraPreviewSetter == null) {
            if (DEBUG) {
                Log.v(TAG, "restartPreviewInner cameraPreviewSetter == null");
            }
            return false;
        } else {
            Parameters parameters;
            Size size = this.cameraSizer.getCurrentPreviewSize();
            String errDetails = null;
            Exception err = null;
            if (DEBUG) {
                Log.v(TAG, "restartPreviewInner milestone 2");
            }
            if (size != null) {
                Size oldSize;
                if (this.meshOrientation != null && this.meshOrientation.isScaleEnabled()) {
                    this.meshOrientation.setImageSize(size);
                }
                try {
                    parameters = this.camera.getParameters();
                    oldSize = parameters.getPreviewSize();
                    if (oldSize.width == size.width && oldSize.height == size.height) {
                        parameters.setPreviewSize(size.width, size.height);
                        this.camera.setParameters(parameters);
                        if (DEBUG) {
                            Log.v(TAG, "restartPreviewInner setPreviewSize(" + size.width + ", " + size.height + ")");
                        }
                    }
                } catch (Exception e2) {
                    err = e2;
                    errDetails = "restartPreviewInner setPreviewSize";
                    Log.e(TAG, errDetails, e2);
                    logCrashlyticsValue("set_preview_size_failed", "true");
                }
                if (DEBUG) {
                    Log.v(TAG, "restartPreviewInner milestone 3");
                }
                try {
                    Parameters params = this.camera.getParameters();
                    Size pictureSize = params.getSupportedPictureSizes().contains(size) ? size : this.cameraSizer.getBiggestPictureSize(size, 2048);
                    if (pictureSize == null) {
                        pictureSize = this.cameraSizer.getBiggestPictureSize(size, Integer.MAX_VALUE);
                    }
                    oldSize = params.getPictureSize();
                    if (oldSize.width == pictureSize.width && oldSize.height == pictureSize.height) {
                        params.setPictureSize(pictureSize.width, pictureSize.height);
                        this.camera.setParameters(params);
                        if (DEBUG) {
                            Log.v(TAG, "restartPreviewInner setPictureSize(" + pictureSize.width + ", " + pictureSize.height + ")");
                        }
                    }
                } catch (Exception e3) {
                    logCrashlyticsValue("set_picture_size_failed", "true");
                }
            }
            if (DEBUG) {
                Log.v(TAG, "restartPreviewInner milestone 4");
            }
            try {
                parameters = this.camera.getParameters();
                List<String> whiteBalances = parameters.getSupportedWhiteBalance();
                String whiteBalance = parameters.getWhiteBalance();
                if (!((whiteBalance != null ? whiteBalance.equals("auto") : false) || whiteBalances == null || !whiteBalances.contains("auto"))) {
                    parameters.setWhiteBalance("auto");
                    this.camera.setParameters(parameters);
                    if (DEBUG) {
                        Log.v(TAG, "restartPreviewInner setWhiteBalance(WHITE_BALANCE_AUTO)");
                    }
                }
            } catch (Exception e22) {
                err = e22;
                errDetails = "restartPreviewInner setWhiteBalance";
                Log.e(TAG, errDetails, e22);
                logCrashlyticsValue("set_white_balance_failed", "true");
            }
            if (Log.isD) {
                if (size != null) {
                    Log.d(TAG, "restartPreviewInner width: " + size.width + "   height: " + size.height);
                } else {
                    Log.d(TAG, "restartPreviewInner size is null");
                }
            }
            boolean result = true;
            if (DEBUG) {
                Log.v(TAG, "restartPreviewInner milestone 6");
            }
            try {
                if (this.cameraPreviewSetter.setCameraPreview(this.camera)) {
                    this.camera.startPreview();
                    this.isCameraStarted = true;
                    if (DEBUG) {
                        Log.v(TAG, "restartPreviewInner milestone finished");
                    }
                    if (err == null || this.onInvalidCamera == null) {
                        return result;
                    }
                    this.onInvalidCamera.onInvalidCamera(errDetails, err);
                    return result;
                }
                throw new Exception("setPreview Returned False");
            } catch (Exception e222) {
                if (Log.isE) {
                    Log.e(TAG, "startPreview Error: ", e222);
                }
                result = false;
                err = e222;
                errDetails = "startPreview Error";
                Log.e(TAG, errDetails, e222);
            }
        }
    }

    public void playbackTooSlow(float ratio) {
        if (DEBUG) {
            Log.v(TAG, "playbackTooSlow");
        }
        if (this.autoAdjustResolution) {
            this.cameraSizer.playbackTooSlow(ratio);
            restartPreviewIfReady();
        }
    }

    public void playbackTooFast(float ratio) {
        if (DEBUG) {
            Log.v(TAG, "playbackTooFast");
        }
        if (this.autoAdjustResolution) {
            this.cameraSizer.playbackTooFast(ratio);
            restartPreviewIfReady();
        }
    }

    public void refreshPreviewMeshSizes(GLScene glScene) {
        int w = getCameraSizer().width;
        int h = getCameraSizer().height;
        if (glScene != null) {
            for (GLPiece piece : glScene.getAllInstancesOf(GLMesh.class)) {
                MeshOrientation meshOrientation = ((GLMesh) piece).getMeshOrientation();
                if (meshOrientation != null) {
                    meshOrientation.setPreviewWidth(w);
                    meshOrientation.setPreviewHeight(h);
                }
            }
            for (GLPiece piece2 : glScene.getAllInstancesOf(GLWall.class)) {
                GLMesh mesh = ((GLWall) piece2).getMesh();
                if ((mesh != null ? mesh.getMeshOrientation() : null) != null) {
                    mesh.getMeshOrientation().setPreviewWidth(w);
                    mesh.getMeshOrientation().setPreviewHeight(h);
                }
            }
        }
    }

    public boolean switchFFC(Activity c) {
        return rebootCamera(c, !this.useFrontFacingCamera);
    }

    public boolean rebootCamera(Activity c) {
        return rebootCamera(c, this.useFrontFacingCamera);
    }

    public boolean rebootCamera(Activity c, boolean useFFC) {
        if (Log.isD) {
            Log.d(TAG, "rebootCamera");
        }
        if (this.camera == null || !this.isSurfaceReady) {
            return true;
        }
        stopCamera();
        this.useFrontFacingCamera = useFFC;
        this.camera = initCameraInstance(c, this.useFrontFacingCamera, true);
        this.cameraSizer.calculatePreviewSize(true);
        restartPreviewIfReady();
        if (this.camera == null) {
            return false;
        }
        return true;
    }

    public boolean takePicture(GLTakePhoto glTakePhoto) {
        boolean z;
        if (Log.isD) {
            Log.d(TAG, "takePicture");
        }
        synchronized (cameraLock) {
            if (this.camera == null || !this.isCameraStarted) {
                z = false;
            } else {
                this.camera.takePicture(null, null, glTakePhoto);
                z = true;
            }
        }
        return z;
    }

    private Camera initCameraInstance(Activity activity, boolean ffc, boolean canDoBoth) {
        Camera camera;
        synchronized (this.cameraInitLock) {
            if (this.camera != null) {
                camera = this.camera;
            } else if (this.ignoreCameraSetup) {
                camera = null;
            } else {
                camera = null;
                long bef = System.currentTimeMillis();
                boolean timeOut = false;
                int k = 0;
                while (k < 10 && camera == null && !timeOut) {
                    if (camera == null && k != 0) {
                        if (DEBUG) {
                            Log.v(TAG, "Slow setup, trying again soon.");
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                    synchronized (cameraLock) {
                        camera = getCameraInstanceSingle(activity, ffc, canDoBoth);
                    }
                    if (System.currentTimeMillis() - bef > 10000) {
                        timeOut = true;
                    } else {
                        timeOut = false;
                    }
                    k++;
                }
                if (camera == null && !this.ignoreCameraSetup) {
                    if (this.onInvalidCamera != null) {
                        this.onInvalidCamera.onInvalidCamera("No camera instance.", null);
                    }
                    this.ignoreCameraSetup = true;
                }
                this.camera = camera;
                initCameraSizer();
            }
        }
        return camera;
    }

    private void initCameraSizer() {
        if (DEBUG) {
            Log.v(TAG, "initCameraSizer");
        }
        if (this.camera != null) {
            List<Size> previewSizes = null;
            List<Size> pictureSizes = null;
            try {
                Parameters parameters = this.camera.getParameters();
                previewSizes = parameters.getSupportedPreviewSizes();
                pictureSizes = parameters.getSupportedPictureSizes();
            } catch (Exception e) {
                logCrashlyticsValue("no_camera_sizes", "true");
            }
            this.cameraSizer.setSizes(previewSizes, pictureSizes);
            this.cameraSizer.setPreviewSizeIndex(this.previewSizeIndex);
            if (Log.isD) {
                Log.d(TAG, "initCameraSizer: previewSizeIndex=" + this.previewSizeIndex);
            }
        } else if (DEBUG) {
            Log.v(TAG, "initCameraSizer: Ignoring request, no camera.");
        }
    }

    private Camera getCameraInstanceSingle(Activity activity, boolean ffc, boolean canDoBoth) {
        Camera c;
        boolean z = true;
        if (ffc) {
            try {
                c = getCameraInstanceFFC();
                if (c != null) {
                    this.useFrontFacingCamera = true;
                }
            } catch (Exception e) {
                if (DEBUG) {
                    Log.w(TAG, "getCameraInstance Exception (" + ffc + "): ", e);
                }
                c = null;
            }
        } else {
            c = getCameraInstanceBFC();
            if (c != null) {
                this.useFrontFacingCamera = false;
            }
        }
        if (c == null && canDoBoth) {
            if (ffc) {
                z = false;
            }
            return getCameraInstanceSingle(activity, z, false);
        }
        if (c == null && DEBUG) {
            Log.w(TAG, "Camera failed to open.");
        }
        if (c == null) {
            return c;
        }
        this.meshOrientation = new MeshOrientation(getSafeCameraInfo(c), activity);
        this.meshOrientation.setIsScaleEnabled(false);
        this.meshOrientation.setFlipX(true);
        LibState.getInstance().getBus().post(new CameraMeshRefreshEvent(ffc, this.meshOrientation));
        c.setErrorCallback(this);
        return c;
    }

    @TargetApi(9)
    private Camera openCameraType(int type) {
        Camera c = null;
        if (VERSION.SDK_INT >= 9) {
            try {
                c = openCameraTypeSingle(type);
            } catch (Exception e) {
                if (Log.isE) {
                    Log.e(TAG, "Error on camera init", e);
                }
            }
        }
        return c;
    }

    private Camera openCameraTypeSingle(int type) {
        Camera c = null;
        if (VERSION.SDK_INT >= 9) {
            CameraInfo info = new CameraInfo();
            int idx = -1;
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, info);
                if (info.facing == type) {
                    idx = i;
                    break;
                }
            }
            c = idx != -1 ? Camera.open(idx) : null;
            if (c != null) {
                this.cameraId = idx;
                return c;
            }
        }
        return c;
    }

    private Camera getCameraInstanceBFC() {
        this.cameraId = 0;
        Camera c = openCameraType(0);
        return c != null ? c : Camera.open();
    }

    @TargetApi(9)
    private Camera getCameraInstanceFFC() {
        this.cameraId = 0;
        Camera c = openCameraType(SafeCameraInfo.CAMERA_FACING_FRONT);
        if (c != null) {
            return c;
        }
        if (VERSION.SDK_INT <= 8) {
            try {
                c = (Camera) Class.forName("android.hardware.HtcFrontFacingCamera").getDeclaredMethod("getCamera", (Class[]) null).invoke((Object[]) null, (Object[]) null);
                if (c != null) {
                    return c;
                }
            } catch (Exception e) {
            }
            try {
                c = (Camera) Class.forName("com.sprint.hardware.twinCamDevice.FrontFacingCamera").getDeclaredMethod("getFrontFacingCamera", (Class[]) null).invoke((Object[]) null, (Object[]) null);
                if (c != null) {
                    return c;
                }
            } catch (Exception e2) {
            }
            try {
                c = Camera.open();
                Parameters parameters = c.getParameters();
                parameters.set("camera-id", 2);
                c.setParameters(parameters);
            } catch (Exception e3) {
            }
        }
        return c;
    }

    private SafeCameraInfo getSafeCameraInfo(Camera c) {
        SafeCameraInfo info = null;
        if (c != null) {
            info = new SafeCameraInfo();
            if (VERSION.SDK_INT >= 9) {
                CameraInfo cinfo = new CameraInfo();
                Camera.getCameraInfo(this.cameraId, cinfo);
                info.facing = cinfo.facing;
                info.orientation = cinfo.orientation;
            }
            if (DEBUG) {
                Log.v(TAG, "Camera orientation: " + info.orientation);
            }
            if (info.orientation == 0 && VERSION.SDK_INT < 11) {
                info.orientation = 90;
            }
            this.useFrontFacingCamera = info.facing == SafeCameraInfo.CAMERA_FACING_FRONT;
            if (this.onCameraInfoReceived != null) {
                this.onCameraInfoReceived.onCameraInfoReceived(info);
            }
        }
        return info;
    }

    public void onError(int error, Camera camera) {
        if (DEBUG) {
            Log.v(TAG, "onCameraCrash: " + error + "   camera=" + camera);
        }
        this.onInvalidCamera.onCameraCrash(error, camera);
    }
}
