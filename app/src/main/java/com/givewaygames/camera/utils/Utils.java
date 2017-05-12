package com.givewaygames.camera.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.crashlytics.android.Crashlytics;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.utils.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class Utils {
    public static void setMemForCrashlytics(int milestone) {
        try {
            Runtime info = Runtime.getRuntime();
            long freeSize = info.freeMemory();
            Crashlytics.setLong("used_mem_" + milestone, info.totalMemory() - freeSize);
            Crashlytics.setLong("free_mem_" + milestone, freeSize);
        } catch (Exception e) {
            Crashlytics.setBool("get_mem_failed", true);
        }
    }

    public static void logHeap(String tag, String prefix) {
        Double allocated = Double.valueOf(new Double((double) Debug.getNativeHeapAllocatedSize()).doubleValue() / new Double(1048576.0d).doubleValue());
        Double available = Double.valueOf(new Double((double) Debug.getNativeHeapSize()).doubleValue() / 1048576.0d);
        Double free = Double.valueOf(new Double((double) Debug.getNativeHeapFreeSize()).doubleValue() / 1048576.0d);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        if (Log.isD) {
            Log.d(tag, prefix + "debug. =================================");
        }
        if (Log.isD) {
            Log.d(tag, prefix + "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
        }
        if (Log.isD) {
            Log.d(tag, prefix + "debug.memory: allocated: " + df.format(new Double(((double) Runtime.getRuntime().totalMemory()) / 1048576.0d)) + "MB of " + df.format(new Double(((double) Runtime.getRuntime().maxMemory()) / 1048576.0d)) + "MB (" + df.format(new Double(((double) Runtime.getRuntime().freeMemory()) / 1048576.0d)) + "MB free)");
        }
    }

    public static Bitmap loadBitmapFromPhotoUri(Activity activity, Uri uri, float size) {
        Bitmap bitmap = null;
        if (uri != null) {
            ContentResolver cr = activity.getContentResolver();
            try {
                InputStream in = cr.openInputStream(uri);
                Options options = new Options();
                options.inJustDecodeBounds = true;
                try {
                    BitmapFactory.decodeStream(in, null, options);
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                    int inSampleSize = calculateInSampleSize(options, in, (int) size, (int) size);
                    try {
                        in = cr.openInputStream(uri);
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = inSampleSize;
                        bitmap = BitmapFactory.decodeStream(in, null, options);
                        try {
                            in.close();
                        } catch (IOException e2) {
                        }
                    } catch (FileNotFoundException e3) {
                    }
                } catch (OutOfMemoryError e4) {
                    Toast.makeText(R.string.low_memory, 0);
                }
            } catch (FileNotFoundException e5) {
            }
        }
        return bitmap;
    }

    public static int calculateInSampleSize(Options options, InputStream stream, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        if (width > height) {
            return Math.round(((float) height) / ((float) reqHeight));
        }
        return Math.round(((float) width) / ((float) reqWidth));
    }

    public static Fragment getActiveFragment(FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            return null;
        }
        return fragmentManager.findFragmentByTag(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName());
    }

    public static String replaceModelAndCarrier(Context context, String body) {
        if (body == null || "".equals(body)) {
            return body;
        }
        String app_ver = "";
        try {
            app_ver = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
        }
        return body.replace("{1}", Build.MANUFACTURER + " - " + Build.MODEL).replace("{2}", app_ver).replace("{A}", Build.MANUFACTURER + " - " + Build.MODEL).replace("{B}", app_ver);
    }
}
