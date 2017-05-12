package com.givewaygames.gwgl.utils.gl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.CameraWrapper.PhotoMeshOrientation;
import com.givewaygames.gwgl.CameraWrapper.StaticImageMeshOrientation;
import com.givewaygames.gwgl.LibState;
import com.givewaygames.gwgl.events.CameraMeshRefreshEvent;
import com.givewaygames.gwgl.utils.BitmapHelper;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.TextureSlotProvider;
import com.givewaygames.gwgl.utils.gl.camera.GLBufferManagedCamera;
import com.givewaygames.gwgl.utils.gl.camera.GLCamera;
import com.givewaygames.gwgl.utils.gl.meshes.GLBitmapImage;
import com.squareup.otto.Subscribe;

public class GLTakePhoto extends GLTakeScreenshot implements PictureCallback {
    private static final String TAG = "GLTakePhoto";
    GLBitmapImage glBitmap;
    GLMesh glMesh;
    GLProgram glProgram;
    GLTexture glTexture;
    GLWall glWall;
    boolean isFFC;
    boolean isSaving;
    Object isSavingLock;
    int maximumImageSize;
    MeshOrientation meshOrientation;
    String takePhotoSavePath;

    public GLTakePhoto(GLHelper glHelper) {
        this(glHelper, new TextureSlotProvider(3553));
    }

    public GLTakePhoto(GLHelper glHelper, TextureSlotProvider slotProvider) {
        super(glHelper);
        this.maximumImageSize = 1024;
        this.isSaving = false;
        this.isFFC = true;
        this.isSavingLock = new Object();
        LibState.getInstance().getBus().register(this);
        this.glTexture = new GLTexture(slotProvider, GLHelper.DEFAULT_TEXTURE);
        this.glTexture.setTextureType(3553);
        this.glBitmap = new GLBitmapImage(null, this.glTexture);
        this.glBitmap.setRecycle(false);
        this.glMesh = new GLMesh(glHelper, this.glTexture, this.glBitmap, null);
        this.glWall = new GLWall(null, this.glMesh, null);
    }

    public void setMeshOrientation(MeshOrientation orient) {
        this.meshOrientation = new PhotoMeshOrientation(orient);
        this.meshOrientation.fixFlipXForPhotos();
    }

    public void setIsFFC(boolean isFFC) {
        this.isFFC = isFFC;
    }

    @Subscribe
    public void onCameraSwitched(CameraMeshRefreshEvent event) {
        this.isFFC = event.isFrontFacingCamera;
        setMeshOrientation(event.meshOrientation);
        synchronized (this.isSavingLock) {
            this.isSaving = false;
        }
    }

    public void addSecondaryTexture(GLTexture texture) {
        this.glMesh.addTexture(texture);
    }

    public GLTexture getGLTexture() {
        return this.glTexture;
    }

    public GLBitmapImage getGLBitmapImage() {
        return this.glBitmap;
    }

    public GLWall getGLWall() {
        return this.glWall;
    }

    public void setMemoryClass(int memoryClass) {
        if (memoryClass >= 32) {
            this.maximumImageSize = 2048;
        } else if (memoryClass >= 16) {
            this.maximumImageSize = 1024;
        } else {
            this.maximumImageSize = 800;
        }
    }

    public boolean onInitialize() {
        return ((super.onInitialize() & this.glTexture.initialize()) & this.glBitmap.initialize()) & this.glWall.initialize();
    }

    private void setGlProgram(GLProgram glProgram) {
        if (glProgram.getTextureType() != 3553) {
            throw new RuntimeException("Programs must be of type GL_TEXTURE_2D to take pictures.");
        }
        this.glProgram = glProgram;
    }

    public boolean takePhoto(CameraWrapper wrapper, GLProgram glProgram, GLCamera glCamera, String savePath) {
        if (Log.isD) {
            Log.d(TAG, "takePhotoInner");
        }
        synchronized (this.isSavingLock) {
            if (this.isSaving) {
            } else {
                this.isSaving = true;
                if (this.onPhotoTaken != null) {
                    this.onPhotoTaken.onProgressUpdate(0);
                }
                if (savePath == null) {
                    setException(null, "takePhoto save path is null.");
                    if (this.onPhotoTaken != null) {
                        this.onPhotoTaken.onPictureTaken(false, savePath);
                    }
                    onRelease();
                } else {
                    int progress = 0;
                    try {
                        boolean isSuccess;
                        this.takePhotoSavePath = savePath;
                        setGlProgram(glProgram);
                        progress = 1;
                        if (glCamera instanceof GLBufferManagedCamera) {
                            isSuccess = glCamera.isInitialized();
                            if (isSuccess) {
                                ((GLBufferManagedCamera) glCamera).setGlTakePhoto(this);
                            }
                        } else {
                            isSuccess = wrapper.takePicture(this);
                        }
                        if (!isSuccess) {
                            onRelease();
                        }
                    } catch (Exception e) {
                        setException(e, "takePicture failed: " + progress);
                        if (this.onPhotoTaken != null) {
                            this.onPhotoTaken.onPictureTaken(false, savePath);
                        }
                        onRelease();
                    }
                }
            }
        }
        return true;
    }

    public void takePhotoOf(byte[] data, GLProgram glProgram, String savePath) {
        if (data == null) {
            setException(new Exception("Null data from camera."), "takePhoto null data from camera");
            if (this.onPhotoTaken != null) {
                this.onPhotoTaken.onPictureTaken(false, savePath);
                return;
            }
            return;
        }
        int maxSize = Math.min(getMaxTextureSize(), this.maximumImageSize);
        Bitmap bitmap = null;
        int ds = calculateDownsample(data, maxSize);
        while (bitmap == null) {
            try {
                Options options = new Options();
                options.inSampleSize = this.globalDownsample * ds;
                Bitmap baseBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                if (baseBitmap != null) {
                    bitmap = BitmapHelper.createScaledBitmapIfNeededSafe(baseBitmap, maxSize);
                }
            } catch (OutOfMemoryError e) {
                ds *= 2;
            }
        }
        takePhotoOf(bitmap, glProgram, savePath);
    }

    private void takePhotoOf(Bitmap bitmap, GLProgram glProgram, String savePath) {
        if (bitmap == null) {
            setException(new Exception("Bad data from camera."), "takePhoto bad data from camera");
            if (this.onPhotoTaken != null) {
                this.onPhotoTaken.onPictureTaken(false, savePath);
                return;
            }
            return;
        }
        int width;
        int height;
        float bitmapRotation = (float) this.meshOrientation.getRotationForPhotoBitmap();
        this.glMesh.setMeshRotation(this.meshOrientation);
        setupGLObjects(glProgram, bitmap, savePath, bitmapRotation);
        int rotation = this.meshOrientation.getRotationAmount();
        if (rotation == 0 || rotation == 180) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        } else {
            height = bitmap.getWidth();
            width = bitmap.getHeight();
        }
        takeScreenshot(width, height, savePath);
    }

    public void takePhotoOfCurrentSetup(GLProgram glProgram, String savePath, int width, int height) {
        if (this.onPhotoTaken != null) {
            this.onPhotoTaken.onProgressUpdate(0);
        }
        if (this.onPhotoTaken != null) {
            this.onPhotoTaken.onProgressUpdate(1);
        }
        if (savePath == null) {
            setException(null, "takePhoto save path is null (2).");
            if (this.onPhotoTaken != null) {
                this.onPhotoTaken.onPictureTaken(false, savePath);
                return;
            }
            return;
        }
        takeScreenshot(width, height, savePath);
    }

    public void takePhotoOfBitmap(GLProgram glProgram, Bitmap bitmap, String savePath) {
        if (this.onPhotoTaken != null) {
            this.onPhotoTaken.onProgressUpdate(0);
        }
        if (this.onPhotoTaken != null) {
            this.onPhotoTaken.onProgressUpdate(1);
        }
        if (bitmap == null) {
            setException(null, "takePhotoOfBitmap: Bitmap null");
            onTakePhotoDone(false, savePath);
        } else if (savePath == null) {
            setException(null, "takePhoto save path is null (3).");
            if (this.onPhotoTaken != null) {
                this.onPhotoTaken.onPictureTaken(false, savePath);
            }
        } else {
            MeshOrientation orient = new StaticImageMeshOrientation();
            orient.fixFlipXForPhotos();
            this.glMesh.setMeshRotation(orient);
            setupGLObjects(glProgram, bitmap, savePath, BitmapDescriptorFactory.HUE_CYAN);
            takeScreenshot(bitmap.getWidth(), bitmap.getHeight(), savePath);
        }
    }

    private void setupGLObjects(GLProgram glProgram, Bitmap bitmap, final String savePath, float rotation) {
        if (Log.isD) {
            Log.d(TAG, "setupGLObjects");
        }
        setGlProgram(glProgram);
        if (bitmap != null) {
            this.glBitmap.updateImage(bitmap);
        }
        this.glWall.setProgram(glProgram);
        this.glBitmap.setRotation(rotation);
        this.glHelper.runOnOpenGLThread(new Runnable() {
            public void run() {
                if (!GLTakePhoto.this.glBitmap.draw(0, 0) && GLTakePhoto.this.onPhotoTaken != null) {
                    GLTakePhoto.this.setException(null, "setupGLObjects bitmap is not valid");
                    GLTakePhoto.this.onPhotoTaken.onPictureTaken(false, savePath);
                }
            }
        });
    }

    private int calculateDownsample(byte[] data, int maxSize) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return Math.max(Math.min(options.outWidth / maxSize, options.outHeight / maxSize), 1);
    }

    private int calculateDownsample(Bitmap bitmap, int maxSize) {
        return Math.max(Math.min(bitmap.getWidth() / maxSize, bitmap.getHeight() / maxSize), 1);
    }

    protected boolean setupFramebuffer(long time) {
        boolean takePhoto = this.takePhoto;
        boolean valid = super.setupFramebuffer(time);
        if (valid && takePhoto && this.glProgram != null) {
            valid = ((((valid & this.glProgram.initialize()) & this.glProgram.draw(0, time)) & this.glBitmap.draw(0, time)) & this.glTexture.draw(0, time)) & this.glWall.draw(0, time);
        }
        return valid & super.takePhotoFromVBO();
    }

    public int getNumPasses() {
        return 1;
    }

    public void onPictureTaken(final byte[] data, Camera camera) {
        if (Log.isD) {
            Log.d(TAG, "onPictureTaken");
        }
        if (this.onPhotoTaken != null) {
            this.onPhotoTaken.onProgressUpdate(1);
        }
        new Thread() {
            public void run() {
                GLTakePhoto.this.takePhotoOf(data, GLTakePhoto.this.glProgram, GLTakePhoto.this.takePhotoSavePath);
                GLTakePhoto.this.onPictureTakenReadyForRestart(true);
            }
        }.start();
    }

    public void onPictureTakenOld(Bitmap bitmap) {
        if (Log.isD) {
            Log.d(TAG, "onPictureTakenOld");
        }
        int maxSize = Math.min(getMaxTextureSize(), this.maximumImageSize);
        Bitmap outBitmap = null;
        int ds = calculateDownsample(bitmap, maxSize);
        while (outBitmap == null) {
            try {
                new Options().inSampleSize = this.globalDownsample * ds;
                outBitmap = BitmapHelper.createScaledBitmapIfNeededSafe(bitmap, maxSize);
            } catch (OutOfMemoryError e) {
                ds *= 2;
            }
        }
        final Bitmap bits = outBitmap;
        new Thread() {
            public void run() {
                GLTakePhoto.this.takePhotoOf(bits, GLTakePhoto.this.glProgram, GLTakePhoto.this.takePhotoSavePath);
                GLTakePhoto.this.onPictureTakenReadyForRestart(false);
            }
        }.start();
    }

    protected void onRelease() {
        super.onRelease();
        synchronized (this.isSavingLock) {
            this.isSaving = false;
        }
    }

    private void onPictureTakenReadyForRestart(boolean wasPreviewStopped) {
        if (this.onPhotoTaken != null && wasPreviewStopped) {
            this.onPhotoTaken.onRestartPreview();
        }
        synchronized (this.isSavingLock) {
            this.isSaving = false;
        }
    }
}
