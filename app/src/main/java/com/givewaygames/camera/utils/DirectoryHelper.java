package com.givewaygames.camera.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import com.givewaygames.gwgl.utils.Log;
import java.io.File;

public class DirectoryHelper {
    public static boolean isMediaAvailable() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            return true;
        }
        if ("mounted_ro".equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isMediaWritable() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            return true;
        }
        if ("mounted_ro".equals(state)) {
            return false;
        }
        return false;
    }

    public static File getSaveDirectory(Context context) {
        File external = context.getExternalFilesDir(null);
        if (external != null) {
            return external;
        }
        external = context.getExternalCacheDir();
        if (external != null) {
            return external;
        }
        return context.getCacheDir();
    }

    public static File getTemporaryDirectory(Context context) {
        if (context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir();
        }
        return context.getCacheDir();
    }

    public static File getPicturesDirectory(Context context) {
        File sub = getChildDirectory("4.4.2".equals(VERSION.RELEASE) ? context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "GoofyGlass");
        if (sub == null) {
            return null;
        }
        if (sub.exists() || sub.mkdirs()) {
            return sub;
        }
        return null;
    }

    public static File getChildDirectory(Context context, String childname) {
        return getChildDirectory(getSaveDirectory(context), childname);
    }

    public static File getChildDirectory(File file, String childname) {
        if (file != null) {
            return new File(file, childname);
        }
        return file;
    }

    @TargetApi(18)
    public static long getBytesAvailableOnSdCard() {
        long blockSize = 0;
        long availableBlocks = 0;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        boolean tryOld = true;
        if (VERSION.SDK_INT >= 18) {
            try {
                blockSize = stat.getBlockSizeLong();
                availableBlocks = stat.getAvailableBlocksLong();
                tryOld = false;
            } catch (NoSuchMethodError e) {
                if (Log.isE) {
                    Log.e("DirectoryHelper", "No such method", e);
                }
            }
        }
        if (tryOld) {
            blockSize = (long) stat.getBlockSize();
            availableBlocks = (long) stat.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }
}
