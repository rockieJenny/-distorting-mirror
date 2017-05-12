package com.givewaygames.gwgl.utils.gl.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.RelativeLayout.LayoutParams;
import com.givewaygames.gwgl.preview.BufferManager;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLTakePhoto;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import com.givewaygames.gwgl.utils.gl.blends.GLBlendNone;
import com.givewaygames.gwgl.utils.gl.blends.IGLBlend;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GLBufferManagedCamera extends GLCamera implements PreviewCallback {
    private static final String TAG = "GLBufferManagedCamera";
    IGLBlend blendMethod = new GLBlendNone();
    private BufferManager bufferManager = new BufferManager();
    private int bufferSize;
    private ArrayList<byte[]> buffers = new ArrayList();
    Rect destRect = new Rect();
    FauxSurface fauxSurface;
    GLTakePhoto glTakePhoto = null;
    boolean hasData = false;
    private long imageID = 0;
    byte[] lastReadBuffer = null;

    public static class FauxSurface extends SurfaceView implements Callback {
        boolean hasValidSurface = false;

        public FauxSurface(Context context) {
            super(context);
            init();
        }

        public FauxSurface(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public FauxSurface(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        private void init() {
            setLayoutParams(new LayoutParams(1, 1));
            SurfaceHolder mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(3);
        }

        public boolean hasValidSurface() {
            return this.hasValidSurface;
        }

        public void surfaceCreated(SurfaceHolder holder) {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.hasValidSurface = true;
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            this.hasValidSurface = false;
        }
    }

    public GLBufferManagedCamera(GLTexture glTexture) {
        super(glTexture);
        this.cameraFPS.reset();
    }

    public void setFauxSurface(FauxSurface fauxSurface) {
        this.fauxSurface = fauxSurface;
    }

    public void setGlTakePhoto(GLTakePhoto glTakePhoto) {
        this.glTakePhoto = glTakePhoto;
    }

    public boolean isReady() {
        return isInitialized();
    }

    public boolean isInitialized() {
        return this.fauxSurface != null && this.fauxSurface.hasValidSurface();
    }

    public boolean onInitialize() {
        if (this.fauxSurface != null) {
            return super.onInitialize();
        }
        throw new RuntimeException("FauxSurface required.  Please set one up.");
    }

    protected void onRelease() {
        super.onRelease();
        this.hasData = false;
    }

    public boolean isDataInitialized() {
        return this.hasData;
    }

    public boolean updateGLImage() {
        int i = 0;
        if (!isFrameReady()) {
            return false;
        }
        Size previewSize = this.bufferManager.getPreviewSize();
        int width = getWidth(previewSize);
        int height = getHeight(previewSize);
        if (((width * height) * 3) / 2 != this.lastReadBuffer.length) {
            return false;
        }
        YuvImage yuvImage = new YuvImage(this.lastReadBuffer, 17, width, height, null);
        this.destRect.set(0, 0, width, height);
        ByteArrayOutputStream output_stream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(this.destRect, 90, output_stream);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeByteArray(output_stream.toByteArray(), 0, output_stream.size());
        } catch (OutOfMemoryError e) {
        }
        try {
            output_stream.close();
        } catch (IOException e2) {
        }
        boolean success = true;
        if (!(bitmap == null || bitmap.isRecycled())) {
            if (this.glTakePhoto != null) {
                this.glTakePhoto.onPictureTakenOld(bitmap);
                this.glTakePhoto = null;
            } else {
                success = true & this.glTexture.bindTexture(this.blendMethod);
                GLUtils.texImage2D(3553, 0, bitmap, 0);
                if (!GLErrorChecker.checkGlError(TAG)) {
                    i = 1;
                }
                success &= i;
                if (success) {
                    this.hasData = true;
                }
                bitmap.recycle();
            }
        }
        this.cameraFPS.tick();
        this.lastReadBuffer = null;
        this.imageID = System.currentTimeMillis();
        return success;
    }

    public long getImageID() {
        return this.imageID;
    }

    public boolean isFrameReady() {
        if (this.bufferManager == null) {
            return false;
        }
        if (this.glTexture.getTextureID() == -1) {
            this.glTexture.initialize();
            return false;
        }
        this.lastReadBuffer = this.bufferManager.getBufferForRead();
        if (this.lastReadBuffer != null) {
            return true;
        }
        return false;
    }

    public BufferManager getBufferManager() {
        return this.bufferManager;
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        if (data != null) {
            onFrameReceived();
            int id = Integer.MAX_VALUE;
            if (this.bufferManager != null) {
                id = this.bufferManager.setBufferForWrite(data);
            }
            if (!(this.previewCallback == null || data == null)) {
                this.previewCallback.onPreviewFrame(data, camera);
            }
            if (id != -1) {
                camera.addCallbackBuffer(data);
            }
        }
    }

    public boolean setCameraPreview(Camera camera) throws IOException {
        if (Log.isD) {
            Log.d(TAG, "setPreview");
        }
        Parameters parameters = camera.getParameters();
        initializeBuffers(camera, parameters, parameters.getPreviewSize());
        camera.setPreviewCallbackWithBuffer(this);
        camera.setPreviewDisplay(this.fauxSurface.getHolder());
        return true;
    }

    private int getWidth(Size previewSize) {
        return previewSize != null ? previewSize.width : 640;
    }

    private int getHeight(Size previewSize) {
        return previewSize != null ? previewSize.height : 480;
    }

    private void initializeBuffers(Camera camera, Parameters parameters, Size previewSize) {
        if (Log.isD) {
            Log.d(TAG, "initializeBuffers");
        }
        int bitsPerPixel = 12;
        try {
            PixelFormat p = new PixelFormat();
            PixelFormat.getPixelFormatInfo(parameters.getPreviewFormat(), p);
            bitsPerPixel = p.bitsPerPixel;
        } catch (IllegalArgumentException e) {
        }
        this.bufferSize = ((getWidth(previewSize) * getHeight(previewSize)) * bitsPerPixel) / 8;
        if (this.buffers.size() > 0 && ((byte[]) this.buffers.get(0)).length != this.bufferSize) {
            this.buffers.clear();
        }
        if (this.buffers.size() == 0) {
            for (int i = 0; i < 3; i++) {
                byte[] buffer = new byte[this.bufferSize];
                this.buffers.add(buffer);
                camera.addCallbackBuffer(buffer);
                if (this.bufferManager != null) {
                    this.bufferManager.addBuffer(buffer);
                }
            }
            if (this.bufferManager != null) {
                this.bufferManager.doneAddingBuffers(previewSize);
                return;
            }
            return;
        }
        Iterator i$ = this.buffers.iterator();
        while (i$.hasNext()) {
            camera.addCallbackBuffer((byte[]) i$.next());
        }
    }

    public static FauxSurface newFauxSurface(Context context) {
        return new FauxSurface(context);
    }
}
