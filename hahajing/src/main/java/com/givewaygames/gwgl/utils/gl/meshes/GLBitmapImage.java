package com.givewaygames.gwgl.utils.gl.meshes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.utils.BitmapHelper;
import com.givewaygames.gwgl.utils.GLErrorChecker;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLImage;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import com.givewaygames.gwgl.utils.gl.blends.GLBlendNone;
import com.givewaygames.gwgl.utils.gl.blends.IGLBlend;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

public class GLBitmapImage extends GLImage {
    public static final String TAG = "GLBitmapImage";
    static int maxTextureSize = 0;
    Bitmap bitmap = null;
    IBitmapProvider bitmapProvider;
    IGLBlend blendMethod = new GLBlendNone();
    Context context = null;
    GLBitmapImage dependent = null;
    boolean doRecycle = false;
    String filePath = null;
    private GLTexture glTexture;
    boolean hasBitmap = false;
    int height;
    private long imageID = 0;
    boolean isUserSet = false;
    MeshOrientation orient;
    int resourceId = -1;
    float rotateBy = 0.0f;
    Uri uri = null;
    float userSetRotation = 0.0f;
    int width;

    public interface IBitmapProvider {
        boolean doRecycle();

        Bitmap getBitmap(Context context);
    }

    public GLBitmapImage(Bitmap bitmap, GLTexture glTexture) {
        this.glTexture = glTexture;
        if (bitmap != null) {
            updateImage(bitmap);
        }
    }

    public GLBitmapImage(Context c, IBitmapProvider bitmapProvider, GLTexture glTexture) {
        this.glTexture = glTexture;
        setBitmapProvider(c, bitmapProvider);
    }

    public GLBitmapImage(Context c, int id, GLTexture glTexture) {
        this.glTexture = glTexture;
        setResourceId(c, id);
    }

    public GLBitmapImage(Context c, String path, GLTexture glTexture) {
        this.glTexture = glTexture;
        setFilePath(c, path);
    }

    public void setMeshOrientation(MeshOrientation orient) {
        this.orient = orient;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public static void calcMaxTextureSize() {
        if (maxTextureSize == 0) {
            IntBuffer maxBuffer = IntBuffer.allocate(1);
            GLES20.glGetIntegerv(3379, maxBuffer);
            if (!GLErrorChecker.checkGlError(TAG)) {
                maxTextureSize = maxBuffer.get();
            }
        }
    }

    public void setRecycle(boolean doRecycle) {
        this.doRecycle = doRecycle;
    }

    public boolean isDataInitialized() {
        return this.hasBitmap;
    }

    public int getResourceId() {
        return this.resourceId;
    }

    public void setBitmapProvider(Context c, IBitmapProvider bitmapProvider) {
        cleanupImage();
        this.context = c;
        this.bitmapProvider = bitmapProvider;
    }

    public void setResourceId(Context c, int id) {
        cleanupImage();
        this.context = c;
        this.resourceId = id;
    }

    public void setFilePath(Context c, String path) {
        cleanupImage();
        this.context = c;
        this.filePath = path;
    }

    public void setUri(Context c, Uri uri) {
        cleanupImage();
        this.context = c;
        this.uri = uri;
    }

    public void setDependentBitmapImage(GLBitmapImage dependent) {
        release();
        this.dependent = dependent;
    }

    public void updateImage(Bitmap bitmap) {
        this.hasBitmap = bitmap != null;
        this.bitmap = bitmap;
        if (bitmap != null) {
            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();
            if (this.orient != null) {
                this.orient.setImageWidth(this.width);
                this.orient.setImageHeight(this.height);
            }
            this.imageID++;
        }
    }

    public long getImageID() {
        return this.imageID;
    }

    public void fixTextureCoordinates(GLMesh glMesh) {
        if (this.bitmap != null) {
            calculateRotation();
        }
        Matrix m = glMesh.getMatrix();
        m.reset();
        m.postRotate(this.rotateBy);
        glMesh.updateConvertedTriangles();
    }

    public boolean onInitialize() {
        calcMaxTextureSize();
        updateImage(reloadBitmap());
        return true;
    }

    private void cleanupImage() {
        this.filePath = null;
        this.resourceId = -1;
        this.bitmapProvider = null;
        this.context = null;
        this.dependent = null;
        this.uri = null;
    }

    public Bitmap reloadBitmap() {
        Options options = new Options();
        options.inSampleSize = 1;
        Bitmap bitmap = null;
        boolean done = false;
        while (!done && options.inSampleSize <= 32) {
            try {
                if (this.context != null && this.bitmapProvider != null) {
                    bitmap = this.bitmapProvider.getBitmap(this.context);
                } else if (this.context != null && this.resourceId != -1) {
                    bitmap = BitmapFactory.decodeResource(this.context.getResources(), this.resourceId, options);
                } else if (this.context != null && this.filePath != null) {
                    bitmap = BitmapFactory.decodeFile(this.filePath, options);
                } else if (this.context != null && this.uri != null) {
                    bitmap = reloadFromUri(options);
                } else if (this.dependent != null) {
                    bitmap = this.dependent.getBitmap();
                    if (bitmap == null) {
                        bitmap = this.dependent.reloadBitmap();
                    }
                }
                if (!(maxTextureSize == 0 || bitmap == null)) {
                    bitmap = BitmapHelper.createScaledBitmapIfNeededSafe(bitmap, maxTextureSize);
                }
                done = true;
            } catch (OutOfMemoryError e) {
                if (!(bitmap == null || bitmap.isRecycled() || this.dependent != null)) {
                    bitmap.recycle();
                }
                bitmap = null;
                options.inSampleSize *= 2;
                if (Log.isW) {
                    Log.w(TAG, "Out of memory... downsampling image: " + options.inSampleSize);
                }
            }
        }
        return bitmap;
    }

    private Bitmap reloadFromUri(Options options) {
        Bitmap bitmap = null;
        try {
            InputStream is = this.context.getContentResolver().openInputStream(this.uri);
            if (is != null) {
                bitmap = BitmapFactory.decodeStream(is, null, options);
                is.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
        }
        if (bitmap != null) {
            return bitmap;
        }
        String[] filePathColumn = new String[]{"_data"};
        Cursor cursor = this.context.getContentResolver().query(this.uri, filePathColumn, null, null, null);
        if (cursor == null) {
            return null;
        }
        String filePath;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        if (columnIndex < 0 || columnIndex >= cursor.getCount()) {
            filePath = null;
        } else {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        this.filePath = filePath;
        if (filePath != null) {
            bitmap = BitmapFactory.decodeFile(filePath, options);
        }
        return bitmap;
    }

    public boolean draw(int pass, long time) {
        int i = 0;
        boolean success = true;
        if (this.bitmap != null && this.bitmap.isRecycled()) {
            this.bitmap = null;
        }
        if (this.bitmap != null && this.glTexture.isInitialized()) {
            calculateRotation();
            success = this.glTexture.bindTexture(this.blendMethod);
            GLUtils.texImage2D(this.glTexture.getTextureType(), 0, this.bitmap, 0);
            if (!GLErrorChecker.checkGlError(TAG)) {
                i = 1;
            }
            success &= i;
            if (this.bitmapProvider != null ? this.bitmapProvider.doRecycle() : this.doRecycle) {
                this.bitmap.recycle();
                this.bitmap = null;
                System.gc();
            }
            this.bitmap = null;
            this.glTexture.unbindTexture(this.blendMethod);
        }
        return success;
    }

    public void setRotation(float rotation) {
        this.imageID++;
        this.userSetRotation = rotation;
        this.isUserSet = true;
    }

    private void calculateRotation() {
        this.rotateBy = this.isUserSet ? this.userSetRotation : 90.0f;
    }

    public int getNumPasses() {
        return 1;
    }
}
