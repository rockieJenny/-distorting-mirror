package com.givewaygames.gwgl.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapHelper {
    public static Bitmap decodeResourceWithDownsample(Resources res, int resourceId, int maxDownsample) {
        int ds = 1;
        Bitmap bitmap = null;
        while (ds <= maxDownsample && bitmap == null) {
            try {
                Options options = new Options();
                options.inSampleSize = ds;
                bitmap = BitmapFactory.decodeResource(res, resourceId, options);
            } catch (OutOfMemoryError e) {
                ds *= 2;
            }
        }
        return bitmap;
    }

    public static Bitmap createScaledBitmapSafe(Bitmap bitmap, int dstWidth, int dstHeight, boolean filter) {
        Bitmap bitmap2 = null;
        try {
            bitmap2 = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, filter);
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError e2) {
        }
        return bitmap2;
    }

    public static Bitmap createScaledBitmapIfNeededSafe(Bitmap bitmap, int maxSize) {
        if (bitmap == null) {
            return null;
        }
        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();
        if (bw <= maxSize && bh <= maxSize) {
            return bitmap;
        }
        int dstWidth;
        int dstHeight;
        if (bw > bh) {
            dstWidth = maxSize;
            dstHeight = (int) (((float) maxSize) * (((float) bh) / ((float) bw)));
        } else {
            dstHeight = maxSize;
            dstWidth = (int) (((float) maxSize) * (((float) bw) / ((float) bh)));
        }
        Bitmap oldBitmap = createScaledBitmapSafe(bitmap, Math.min(maxSize, dstWidth), Math.min(maxSize, dstHeight), true);
        if (bitmap != oldBitmap) {
            bitmap.recycle();
            System.gc();
        }
        return oldBitmap;
    }
}
