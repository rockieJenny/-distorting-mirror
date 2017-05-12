package com.givewaygames.camera.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import com.givewaygames.camera.state.AppState;
import com.givewaygames.camera.utils.Constants;
import com.givewaygames.camera.utils.DirectoryHelper;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.CameraWrapper.OnPhotoTaken;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import java.io.File;

public class TakePhotoHelper implements OnPhotoTaken {
    boolean isWritable;
    Thread photoThread;
    final GoofyScene scene;

    public static class BitmapPhotoThread extends Thread {
        final Context context;
        final String path;
        final GLProgram program;
        final GoofyScene scene;

        public BitmapPhotoThread(Context context, GoofyScene scene, GLProgram program, String path) {
            this.context = context;
            this.scene = scene;
            this.program = program;
            this.path = path;
        }

        public void run() {
            AppState state = AppState.getInstance();
            Bitmap bitmap = (state.canStencil() || state.isModeImported()) ? this.scene.glImportBitmap.reloadBitmap() : this.scene.bitmapProvider.loadOldImage(this.context);
            this.scene.glTakePhoto.takePhotoOfBitmap(this.program, bitmap, this.path);
        }
    }

    public static class CameraPhotoThread extends Thread {
        final String path;
        final GLProgram program;
        final GoofyScene scene;

        public CameraPhotoThread(GoofyScene scene, GLProgram program, String path) {
            this.scene = scene;
            this.program = program;
            this.path = path;
        }

        public void run() {
            CameraWrapper cameraWrapper = AppState.getInstance().getCameraWrapper();
            if (!this.scene.glTakePhoto.takePhoto(cameraWrapper, this.program, this.scene.glCamera, this.path)) {
                Toast.makeText(R.string.save_canceled, 0);
            }
        }
    }

    public TakePhotoHelper(GoofyScene scene) {
        this.scene = scene;
    }

    public boolean takePhoto(Context context, GLProgram program) {
        this.isWritable = DirectoryHelper.isMediaWritable();
        boolean hasSpace = true;
        if (this.isWritable && DirectoryHelper.getBytesAvailableOnSdCard() < 10485760) {
            hasSpace = false;
        }
        String path = getNextImagePath(DirectoryHelper.getPicturesDirectory(context));
        if (Log.isD) {
            Log.d("TakePhotoHelper", "Saving to: " + path);
        }
        if (this.isWritable && path != null) {
            if (this.photoThread != null) {
                try {
                    this.photoThread.join(3000);
                } catch (InterruptedException e) {
                }
                this.photoThread = null;
            }
            if (this.scene.glTakePhoto.isInitialized()) {
                Thread cameraPhotoThread;
                this.scene.glTakePhoto.setOnPictureTakenCallback(this);
                if (AppState.getInstance().isModeCamera()) {
                    cameraPhotoThread = new CameraPhotoThread(this.scene, program, path);
                } else {
                    cameraPhotoThread = new BitmapPhotoThread(context, this.scene, program, path);
                }
                this.photoThread = cameraPhotoThread;
                this.photoThread.start();
                return true;
            }
            Toast.makeText(R.string.picture_failed_title, 1);
            if (!Log.isE) {
                return false;
            }
            Log.e("TakePhotoHelper", "Cannot take photo.  GLTakePhoto not initialized");
            return false;
        } else if (hasSpace) {
            Toast.makeText(R.string.cannot_take_photo_no_sd_card, 1);
            return false;
        } else {
            Toast.makeText(R.string.not_enough_space, 1);
            return false;
        }
    }

    private String getNextImagePath(File groupDir) {
        if (groupDir == null) {
            return null;
        }
        File[] files = groupDir.listFiles();
        if (files == null) {
            return null;
        }
        int lastValidId = 0;
        for (File child : files) {
            int fileId = -1;
            try {
                fileId = Integer.parseInt(child.getName().replace(Constants.IMAGE_FILE_PATH, "").replace(Constants.IMAGE_EXTENSION, ""));
            } catch (Exception e) {
            }
            if (fileId > lastValidId) {
                lastValidId = fileId;
            }
        }
        return groupDir.getAbsolutePath() + File.separator + Constants.IMAGE_FILE_PATH + (lastValidId + 1) + Constants.IMAGE_EXTENSION;
    }

    public void onPictureTaken(boolean success, String filePath) {
        Handler handler = AppState.getInstance().getHandler();
        Message message = handler.obtainMessage(4);
        message.arg1 = success ? 1 : 0;
        message.obj = new Pair(filePath, this.scene.glTakePhoto);
        handler.sendMessage(message);
    }

    public void onRestartPreview() {
        AppState.getInstance().getCameraWrapper().restartPreviewIfReady();
    }

    public void onProgressUpdate(int progress) {
        Handler handler = AppState.getInstance().getHandler();
        handler.sendMessage(handler.obtainMessage(5, progress, 0));
    }
}
