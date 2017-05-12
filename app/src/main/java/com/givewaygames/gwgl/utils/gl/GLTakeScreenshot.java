package com.givewaygames.gwgl.utils.gl;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.CameraWrapper.OnPhotoTaken;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.google.android.gms.location.LocationRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.annotation.concurrent.GuardedBy;

public class GLTakeScreenshot extends GLPiece {
    public static final int PROGRESS_DONE = 5;
    public static final int PROGRESS_GL_SETUP_A = 2;
    public static final int PROGRESS_GL_SETUP_B = 3;
    public static final int PROGRESS_PHOTO_TAKEN = 1;
    public static final int PROGRESS_STARTED = 0;
    public static final int PROGRESS_WAIT_FOR_PROCESS = 4;
    private static final int SHORT_SIZE_BYTES = 2;
    private static final String TAG = "GLTakeScreenshot";
    private static Throwable exception;
    protected static String exceptionDetails;
    int frameBufferID;
    GLHelper glHelper;
    int globalDownsample = 1;
    boolean hasAllocatedTexture = false;
    @GuardedBy("this")
    boolean isProcessing = false;
    int maxTextureSize;
    boolean mustUseRGBA = true;
    int[] oldViewport = new int[4];
    OnPhotoTaken onPhotoTaken;
    private CompressFormat outFormat = CompressFormat.JPEG;
    private int outQuality = 100;
    String photoPath = "";
    boolean resetFrameBuffer = false;
    boolean resetViewport = false;
    boolean singleImage = true;
    boolean takePhoto = false;
    int texH;
    int texW;
    int textureLevel;
    int textureType;
    byte[] tmpBytes;
    ByteBuffer tmpReadBuffer;
    int vboTextureID;

    public static class ScreenshotException extends RuntimeException {
        public ScreenshotException(String error) {
            super(error);
        }
    }

    public GLTakeScreenshot(GLHelper glHelper) {
        this.glHelper = glHelper;
        this.textureType = 3553;
        this.textureLevel = 33984;
    }

    public static Throwable getFailureException() {
        return exception;
    }

    public static String getExceptionDetails() {
        return exceptionDetails;
    }

    public void increaseDownsample() {
        this.globalDownsample *= 2;
    }

    public boolean tryAgain() {
        return this.globalDownsample <= 8 && (exception instanceof OutOfMemoryError);
    }

    public void setException(Throwable t, String extraDetails) {
        if (Log.isE) {
            Log.e(TAG, "setException", t);
        }
        if (t instanceof OutOfMemoryError) {
            increaseDownsample();
        }
        exception = t;
        exceptionDetails = extraDetails;
    }

    public int getMaxTextureSize() {
        return this.maxTextureSize;
    }

    public void setOnPictureTakenCallback(OnPhotoTaken onPhotoTaken) {
        this.onPhotoTaken = onPhotoTaken;
    }

    public boolean onInitialize() {
        int[] textures = new int[1];
        GLES20.glActiveTexture(this.textureLevel);
        GLES20.glGenTextures(1, textures, 0);
        if (GLErrorChecker.checkGlError(TAG)) {
            return false;
        }
        int[] frameBuffer = new int[1];
        GLES20.glGenFramebuffers(1, frameBuffer, 0);
        this.frameBufferID = frameBuffer[0];
        if (this.frameBufferID == 0) {
            String errorStr = "GL error = 0x" + Integer.toHexString(GLES20.glGetError());
            if (Log.isW) {
                Log.w(TAG, "glGenFramebuffers returned 0.  This will cause a photo failure later.");
            }
            if (Log.isW) {
                Log.w(TAG, "glGenFramebuffers error=" + errorStr);
            }
        }
        this.vboTextureID = textures[0];
        GLES20.glBindTexture(this.textureType, this.vboTextureID);
        GLES20.glTexParameteri(this.textureType, 10242, 33071);
        GLES20.glTexParameteri(this.textureType, 10243, 33071);
        GLES20.glTexParameteri(this.textureType, 10240, 9729);
        GLES20.glTexParameteri(this.textureType, 10241, 9729);
        if (GLErrorChecker.checkGlError(TAG)) {
            setException(GLErrorChecker.lastError, "GLES20.glTexParameter Failed.");
            return false;
        }
        IntBuffer maxBuffer = IntBuffer.allocate(1);
        GLES20.glGetIntegerv(3379, maxBuffer);
        if (GLErrorChecker.checkGlError(TAG)) {
            setException(GLErrorChecker.lastError, "GLES20.GL_MAX_TEXTURE_SIZE Failed.");
            return false;
        }
        this.maxTextureSize = maxBuffer.get();
        this.hasAllocatedTexture = false;
        return true;
    }

    protected void onRelease() {
        super.onRelease();
        deleteFramebuffers();
    }

    private void deleteFramebuffers() {
        if (this.frameBufferID != 0) {
            GLES20.glDeleteFramebuffers(1, new int[]{this.frameBufferID}, 0);
            this.frameBufferID = 0;
        }
    }

    public synchronized boolean getIsProcessing() {
        return this.isProcessing;
    }

    public synchronized void takeScreenshot(final int width, final int height, final String savePath) {
        if (Log.isD) {
            Log.d(TAG, "takeScreenshot: " + width + ", " + height);
        }
        if (!this.isProcessing) {
            this.photoPath = savePath;
            this.isProcessing = true;
            this.glHelper.runOnOpenGLThread(new Runnable() {
                public void run() {
                    boolean needsMore;
                    GLES20.glActiveTexture(GLTakeScreenshot.this.textureLevel);
                    if (width > GLTakeScreenshot.this.texW || height > GLTakeScreenshot.this.texH) {
                        needsMore = true;
                    } else {
                        needsMore = false;
                    }
                    GLTakeScreenshot.this.texW = width;
                    GLTakeScreenshot.this.texH = height;
                    if (needsMore || !GLTakeScreenshot.this.hasAllocatedTexture) {
                        try {
                            ByteBuffer texBuffer = ByteBuffer.allocateDirect((GLTakeScreenshot.this.texW * GLTakeScreenshot.this.texH) * 2).order(ByteOrder.nativeOrder());
                            GLES20.glBindTexture(GLTakeScreenshot.this.textureType, GLTakeScreenshot.this.vboTextureID);
                            GLES20.glTexImage2D(GLTakeScreenshot.this.textureType, 0, 6407, GLTakeScreenshot.this.texW, GLTakeScreenshot.this.texH, 0, 6407, 33635, texBuffer);
                            System.gc();
                        } catch (OutOfMemoryError e) {
                            GLTakeScreenshot.this.setException(e, "ByteBuffer.allocateDirect out of memory");
                            if (GLTakeScreenshot.this.onPhotoTaken != null) {
                                GLTakeScreenshot.this.onPhotoTaken.onPictureTaken(false, savePath);
                                return;
                            }
                            return;
                        }
                    }
                    if (GLErrorChecker.checkGlError(GLTakeScreenshot.TAG)) {
                        GLTakeScreenshot.this.setException(GLErrorChecker.lastError, "takeScreenshot Setup Failed.");
                        if (GLTakeScreenshot.this.onPhotoTaken != null) {
                            GLTakeScreenshot.this.onPhotoTaken.onPictureTaken(false, savePath);
                            return;
                        }
                        return;
                    }
                    GLTakeScreenshot.this.takePhoto = true;
                }
            });
            if (this.onPhotoTaken != null) {
                this.onPhotoTaken.onProgressUpdate(2);
            }
        }
    }

    public boolean draw(int pass, long time) {
        boolean success = true;
        boolean wasTakePhoto = this.takePhoto;
        switch (pass) {
            case 0:
                success = setupFramebuffer(time);
                break;
            case 1:
                success = takePhotoFromVBO();
                break;
        }
        if (wasTakePhoto && !this.takePhoto && !frameBufferCleanup()) {
            return false;
        }
        if (!success) {
            onTakePhotoDone(success, this.photoPath);
        }
        return true;
    }

    protected boolean setupFramebuffer(long time) {
        if (this.takePhoto) {
            if (Log.isD) {
                Log.d(TAG, "setupFramebuffer: textureLevel=" + this.textureLevel + " textureType=" + this.textureType + " vboTextureID=" + this.vboTextureID + " frameBufferID=" + this.frameBufferID);
            }
            GLES20.glActiveTexture(this.textureLevel);
            GLES20.glBindTexture(3553, this.vboTextureID);
            GLES20.glBindFramebuffer(36160, this.frameBufferID);
            if (GLErrorChecker.checkGlError(TAG)) {
                setException(GLErrorChecker.lastError, "glBindFramebuffer failed");
                this.takePhoto = false;
            }
            this.resetFrameBuffer = true;
            GLES20.glFramebufferTexture2D(36160, 36064, this.textureType, this.vboTextureID, 0);
            if (GLErrorChecker.checkGlError(TAG)) {
                setException(GLErrorChecker.lastError, "glFramebufferTexture2D failed");
                this.takePhoto = false;
            }
            int status = GLES20.glCheckFramebufferStatus(36160);
            if (status != 36053) {
                if (Log.isW) {
                    Log.w(TAG, "Cannot take photo frame buffer status not complete: " + status);
                }
                setException(new Exception("Frame buffer incomplete."), "glCheckFramebufferStatus incomplete");
                this.takePhoto = false;
            }
            IntBuffer intBuffer = IntBuffer.allocate(4);
            GLES20.glGetIntegerv(2978, intBuffer);
            if (GLErrorChecker.checkGlError(TAG)) {
                setException(GLErrorChecker.lastError, "glCheckFramebufferStatus incomplete failed");
                this.takePhoto = false;
            }
            intBuffer.get(this.oldViewport);
            GLES20.glViewport(0, 0, this.texW, this.texH);
            if (GLErrorChecker.checkGlError(TAG)) {
                setException(GLErrorChecker.lastError, "glViewport failed");
                this.takePhoto = false;
            }
            this.resetViewport = true;
            if (this.onPhotoTaken != null) {
                this.onPhotoTaken.onProgressUpdate(3);
            }
            if (!this.takePhoto) {
                return false;
            }
        }
        return true;
    }

    protected boolean takePhotoFromVBO() {
        if (this.takePhoto) {
            if (Log.isD) {
                Log.d(TAG, "takePhotoFromVBO");
            }
            if (this.mustUseRGBA) {
                return takePhotoFromVBO_RGBA();
            }
            GLES20.glActiveTexture(this.textureLevel);
            GLES20.glBindTexture(this.textureType, this.vboTextureID);
            final int modW = this.texW % 2 != 0 ? this.texW - 1 : this.texW;
            final int modH = this.texH % 2 != 0 ? this.texH - 1 : this.texH;
            try {
                this.tmpBytes = new byte[((modW * modH) * 2)];
                this.tmpReadBuffer = ByteBuffer.wrap(this.tmpBytes);
                GLES20.glReadPixels(0, 0, modW, modH, 6407, 33635, this.tmpReadBuffer);
                if (GLErrorChecker.checkGlError(TAG)) {
                    this.tmpBytes = null;
                    this.tmpReadBuffer = null;
                    System.gc();
                    setException(GLErrorChecker.lastError, "GLES20.glReadPixels failed");
                    this.mustUseRGBA = takePhotoFromVBO_RGBA();
                    if (this.mustUseRGBA) {
                        CameraWrapper.logCrashlyticsValue("must_use_rgba", "true");
                    }
                    return this.mustUseRGBA;
                }
                if (this.onPhotoTaken != null) {
                    this.onPhotoTaken.onProgressUpdate(4);
                }
                new Thread() {
                    public void run() {
                        GLTakeScreenshot.this.onTakePhotoDone(GLTakeScreenshot.this.takePhotoFromVBO(GLTakeScreenshot.this.photoPath, modW, modH), GLTakeScreenshot.this.photoPath);
                    }
                }.start();
                if (this.onPhotoTaken != null) {
                    this.onPhotoTaken.onProgressUpdate(LocationRequest.PRIORITY_LOW_POWER);
                }
                this.takePhoto = false;
            } catch (OutOfMemoryError e) {
                if (Log.isE) {
                    Log.e(TAG, "takePhotoFromVBO (Out of memory)", e);
                }
                setException(e, "takePhotoFromVBO ByteBuffer.wrap (Out of memory)");
                return false;
            }
        }
        return true;
    }

    protected boolean takePhotoFromVBO_RGBA() {
        if (Log.isD) {
            Log.d(TAG, "takePhotoFromVBO_RGBA");
        }
        if (this.takePhoto) {
            GLES20.glActiveTexture(this.textureLevel);
            GLES20.glBindTexture(this.textureType, this.vboTextureID);
            try {
                this.tmpReadBuffer = ByteBuffer.allocateDirect(((this.texW * this.texH) + 1) * 4);
                GLES20.glReadPixels(0, 0, this.texW, this.texH, 6408, 5121, this.tmpReadBuffer);
                if (GLErrorChecker.checkGlError(TAG)) {
                    setException(GLErrorChecker.lastError, "takePhotoFromVBO_RGBA checkGLError");
                    return false;
                }
                if (this.onPhotoTaken != null) {
                    this.onPhotoTaken.onProgressUpdate(4);
                }
                new Thread() {
                    public void run() {
                        GLTakeScreenshot.this.onTakePhotoDone(GLTakeScreenshot.this.takePhotoFromVBO_RGBA(GLTakeScreenshot.this.photoPath), GLTakeScreenshot.this.photoPath);
                    }
                }.start();
                this.takePhoto = false;
            } catch (OutOfMemoryError e) {
                setException(e, "ByteBuffer.allocateDirect out of memory");
                return false;
            }
        }
        return true;
    }

    private boolean frameBufferCleanup() {
        if (this.resetFrameBuffer) {
            GLES20.glBindFramebuffer(36160, 0);
            if (GLErrorChecker.checkGlError(TAG)) {
                setException(GLErrorChecker.lastError, "glBindFramebuffer failed");
                return false;
            }
            this.resetFrameBuffer = false;
        }
        if (this.resetViewport) {
            GLES20.glViewport(this.oldViewport[0], this.oldViewport[1], this.oldViewport[2], this.oldViewport[3]);
            if (GLErrorChecker.checkGlError(TAG)) {
                setException(GLErrorChecker.lastError, "glViewport failed");
                return false;
            }
            this.resetViewport = false;
        }
        return true;
    }

    private boolean takePhotoFromVBO(String path, int modW, int modH) {
        boolean hasError = false;
        try {
            Bitmap sb = Bitmap.createBitmap(modW, modH, Config.RGB_565);
            if (sb == null) {
                throw new OutOfMemoryError();
            }
            sb.copyPixelsFromBuffer(this.tmpReadBuffer);
            File file = new File(path);
            if (!file.exists()) {
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    throw new ScreenshotException("Could not create parent directory");
                } else if (!file.createNewFile()) {
                    throw new ScreenshotException("Could not create save file.");
                }
            }
            FileOutputStream fos = new FileOutputStream(path);
            boolean isValid = sb.compress(this.outFormat, this.outQuality, fos);
            fos.close();
            sb.recycle();
            this.tmpReadBuffer = null;
            System.gc();
            if (!isValid) {
                throw new ScreenshotException("Out of space, please free up space on your device and try again.");
            }
            if (hasError) {
                return false;
            }
            return true;
        } catch (Exception e) {
            if (Log.isE) {
                Log.e(TAG, "takePhotoFromVBO: " + e.getMessage(), e);
            }
            setException(e, "takePhotoFromVBO: " + e.getMessage());
            hasError = true;
        } catch (OutOfMemoryError e2) {
            if (Log.isE) {
                Log.e(TAG, "takePhotoFromVBO (Out of memory)", e2);
            }
            setException(e2, "takePhotoFromVBO (Out of memory)");
            hasError = true;
        }
    }

    private boolean takePhotoFromVBO_RGBA(String path) {
        boolean hasError = false;
        try {
            this.tmpReadBuffer.position(3);
            IntBuffer ib = this.tmpReadBuffer.asIntBuffer();
            int[] b = new int[ib.capacity()];
            ib.get(b, 0, this.texW * this.texH);
            this.tmpReadBuffer = null;
            System.gc();
            Bitmap sb = Bitmap.createBitmap(b, this.texW, this.texH, Config.ARGB_8888);
            File file = new File(path);
            if (!file.exists()) {
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    throw new ScreenshotException("RGBA: Could not create parent directory");
                } else if (!file.createNewFile()) {
                    throw new ScreenshotException("RGBA: Could not create save file.");
                }
            }
            FileOutputStream fos = new FileOutputStream(path);
            sb.compress(this.outFormat, this.outQuality, fos);
            fos.close();
            sb.recycle();
            System.gc();
        } catch (OutOfMemoryError e) {
            if (Log.isE) {
                Log.e(TAG, "takePhotoFromVBO_RGBA (Out of memory)", e);
            }
            setException(e, "takePhotoFromVBO_RGBA (Out of memory)");
            hasError = true;
        } catch (Exception e2) {
            if (Log.isE) {
                Log.e(TAG, "takePhotoFromVBO_RGBA", e2);
            }
            setException(e2, "takePhotoFromVBO_RGBA");
            hasError = true;
        }
        if (hasError) {
            return false;
        }
        return true;
    }

    protected synchronized void onTakePhotoDone(boolean success, String path) {
        if (this.onPhotoTaken != null) {
            this.onPhotoTaken.onProgressUpdate(5);
            this.onPhotoTaken.onPictureTaken(success, path);
        }
        this.takePhoto = false;
        this.isProcessing = false;
        notifyAll();
    }

    public int getNumPasses() {
        return 2;
    }
}
