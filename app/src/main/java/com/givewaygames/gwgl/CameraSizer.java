package com.givewaygames.gwgl;

import android.hardware.Camera.Size;
import android.support.v4.widget.AutoScrollHelper;
import com.givewaygames.gwgl.utils.Log;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CameraSizer {
    private static final String TAG = "CameraSizer";
    @GuardedBy("this")
    int height;
    @GuardedBy("this")
    List<Size> pictureSizes = null;
    @GuardedBy("this")
    Size previewSize;
    @GuardedBy("this")
    int previewSizeIndex;
    @GuardedBy("this")
    List<Size> previewSizes = null;
    boolean tryMaximumSize = false;
    @GuardedBy("this")
    List<SafeAspectRatio> validAspectRatios = new ArrayList();
    @GuardedBy("this")
    int width;

    class PreviewSizeComparator implements Comparator<Size> {
        double goalAspectRatio = 1.0d;

        public PreviewSizeComparator(double goalAspectRatio) {
            this.goalAspectRatio = goalAspectRatio;
        }

        public int compare(Size lhs, Size rhs) {
            return (rhs.width * rhs.height) - (lhs.width * lhs.height);
        }
    }

    static class SafeAspectRatio {
        int height;
        int smallHeight;
        int smallWidth;
        int width;

        public static SafeAspectRatio getInstance(Size size) {
            return getInstance(size.width, size.height);
        }

        public static SafeAspectRatio getInstance(int width, int height) {
            int i;
            int i2 = 0;
            if (width == 0 || height == 0) {
                CameraWrapper.logCrashlyticsValue("bad_size", width + "x" + height);
            }
            int gcd = GCD(width, height);
            SafeAspectRatio ratio = new SafeAspectRatio();
            ratio.width = width;
            ratio.height = height;
            if (gcd != 0) {
                i = width / gcd;
            } else {
                i = 0;
            }
            ratio.smallWidth = i;
            if (gcd != 0) {
                i2 = height / gcd;
            }
            ratio.smallHeight = i2;
            return ratio;
        }

        public static int GCD(int a, int b) {
            return b == 0 ? a : GCD(b, a % b);
        }

        private SafeAspectRatio() {
        }

        public boolean equals(Object o) {
            if (o.getClass() != getClass()) {
                return false;
            }
            SafeAspectRatio ratio = (SafeAspectRatio) o;
            if (this.smallWidth == ratio.smallWidth && this.smallHeight == ratio.smallHeight) {
                return true;
            }
            return false;
        }
    }

    public synchronized void setSizes(List<Size> previewSizes, List<Size> pictureSizes) {
        this.previewSizes = previewSizes;
        this.pictureSizes = pictureSizes;
        this.previewSize = null;
        List<SafeAspectRatio> previewRatios = new ArrayList();
        setSizes_Add(previewSizes, previewRatios);
        List<SafeAspectRatio> pictureRatios = new ArrayList();
        setSizes_Add(pictureSizes, pictureRatios);
        this.validAspectRatios = new ArrayList();
        if (previewRatios.size() == 0) {
            this.validAspectRatios.addAll(pictureRatios);
        } else if (pictureRatios.size() == 0) {
            this.validAspectRatios.addAll(previewRatios);
        } else {
            this.validAspectRatios.addAll(previewRatios);
            this.validAspectRatios.retainAll(pictureRatios);
        }
        setSizes_Prune(previewSizes);
        setSizes_Prune(pictureSizes);
    }

    private void setSizes_Add(List<Size> sizes, List<SafeAspectRatio> ratios) {
        if (sizes != null) {
            for (Size size : sizes) {
                ratios.add(SafeAspectRatio.getInstance(size));
            }
        }
    }

    private void setSizes_Prune(List<Size> sizes) {
        if (sizes != null) {
            List<Size> toRemove = new ArrayList();
            for (Size size : sizes) {
                if (!this.validAspectRatios.contains(SafeAspectRatio.getInstance(size))) {
                    toRemove.add(size);
                }
            }
            if (toRemove.size() < sizes.size()) {
                sizes.removeAll(toRemove);
            }
        }
    }

    public void setTryMaximumSize(boolean tryMaximumSize) {
        this.tryMaximumSize = tryMaximumSize;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPreviewSizeIndex() {
        return this.previewSizeIndex;
    }

    public Size getCurrentPreviewSize() {
        return this.previewSize;
    }

    public void setPreviewSizeIndex(int index) {
        this.previewSizeIndex = index;
    }

    public void onSizeChanged(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void calculatePreviewSize(boolean forceReset) {
        if (this.tryMaximumSize) {
            changeToMaximumSize(forceReset);
        } else {
            changeToOptimalSize(forceReset);
        }
    }

    public void calculatePreviewSize(boolean forceReset, int w, int h) {
        onSizeChanged(w, h);
        calculatePreviewSize(forceReset);
    }

    private boolean changeToMaximumSize(boolean reset) {
        if (this.previewSizes != null) {
            if (reset) {
                this.previewSizeIndex = -1;
            }
            this.previewSize = getOptimalPreviewSize(this.width, this.height, 0, TextTrackStyle.DEFAULT_FONT_SCALE, true);
            if (Log.isI && this.previewSize != null) {
                Log.i(TAG, "Maximum index of: " + this.previewSize.width + ", " + this.previewSize.height);
            }
            return true;
        } else if (!Log.isD) {
            return false;
        } else {
            Log.d(TAG, "changeToMaximumSize: Ignoring request.  Camera not yet setup.");
            return false;
        }
    }

    private boolean changeToOptimalSize(boolean reset) {
        if (this.previewSizes == null) {
            if (!Log.isD) {
                return false;
            }
            Log.d(TAG, "changeToOptimalSize: Ignoring request.  Camera not yet setup.");
            return false;
        } else if (this.width != 0 && this.height != 0) {
            if (reset) {
                this.previewSizeIndex = -1;
            }
            this.previewSize = getOptimalPreviewSize(this.width, this.height, 0, TextTrackStyle.DEFAULT_FONT_SCALE, false);
            if (Log.isI && this.previewSize != null) {
                Log.i(TAG, "Optimal index of: " + this.previewSize.width + ", " + this.previewSize.height);
            }
            return true;
        } else if (!Log.isD) {
            return false;
        } else {
            Log.d(TAG, "changeToOptimalSize: Aborting, no size set.");
            return false;
        }
    }

    public Size getBiggestPictureSize(Size goalSize, int maxSize) {
        if (this.pictureSizes == null) {
            return null;
        }
        Size trueSize = null;
        int truePixels = 0;
        Size safeSize = null;
        int safePixels = 0;
        SafeAspectRatio goal = SafeAspectRatio.getInstance(goalSize);
        for (Size size : this.pictureSizes) {
            if (goal.equals(SafeAspectRatio.getInstance(size))) {
                int area = size.width * size.height;
                if (area >= safePixels && size.width < maxSize && size.height < maxSize) {
                    safePixels = area;
                    safeSize = size;
                } else if (area >= truePixels) {
                    truePixels = area;
                    trueSize = size;
                }
            } else if (Log.isD) {
                Log.d(TAG, "Skipping size, aspect ratio doesn't match.");
            }
        }
        if (safeSize != null || trueSize != null) {
            return safeSize == null ? trueSize : safeSize;
        } else {
            throw new RuntimeException("Sizer exception.  No aspect ratios match!");
        }
    }

    public boolean playbackTooSlow(float ratio) {
        Size oldPreviewSize = this.previewSize;
        this.previewSize = getOptimalPreviewSize(this.width, this.height, 1, ratio, false);
        if (this.previewSize == null || (oldPreviewSize != null && this.previewSize.height == oldPreviewSize.height && this.previewSize.width == oldPreviewSize.width)) {
            return false;
        }
        if (!Log.isI || this.previewSize == null) {
            return true;
        }
        Log.i(TAG, "Playback too slow.  Changing to: " + this.previewSize.width + ", " + this.previewSize.height);
        return true;
    }

    public boolean playbackTooFast(float ratio) {
        Size oldPreviewSize = this.previewSize;
        this.previewSize = getOptimalPreviewSize(this.width, this.height, -1, ratio, false);
        if (this.previewSize == null) {
            return false;
        }
        if (oldPreviewSize != null && this.previewSize.height == oldPreviewSize.height && this.previewSize.width == oldPreviewSize.width) {
            return false;
        }
        if (Log.isI && this.previewSize != null) {
            Log.i(TAG, "Playback too fast.  Changing to: " + this.previewSize.width + ", " + this.previewSize.height);
        }
        return true;
    }

    public synchronized Size getOptimalPreviewSize(int w, int h, int idx, float speedRatio, boolean maxPreviewSize) {
        Size size;
        if (this.previewSizes == null) {
            size = null;
        } else {
            double rat;
            if (Log.isI) {
                Log.i(TAG, "getOptimalPreviewSize: (" + w + ", " + h + "):" + idx + "," + this.previewSizeIndex);
            }
            if (((double) speedRatio) < 1.0d) {
                rat = Math.min(0.99d, (double) (1.25f * speedRatio));
            } else {
                rat = Math.max(1.01d, (double) (speedRatio / 1.25f));
            }
            Size desiredSize = null;
            if (idx == 0 && this.previewSizeIndex == -1) {
                desiredSize = getOptimalPreviewIndex(w, h, maxPreviewSize);
                if (Log.isI && desiredSize != null) {
                    Log.i(TAG, "getDesiredSize: (" + desiredSize.width + ", " + desiredSize.height + ")");
                }
            }
            double goalAspect = ((double) this.width) / ((double) this.height);
            Collections.sort(this.previewSizes, new PreviewSizeComparator(goalAspect));
            pruneBadValues(goalAspect, this.previewSizes);
            int previousSizeTotal = w * h;
            boolean done = idx == 0;
            while (!done) {
                this.previewSizeIndex += idx;
                if (this.previewSizeIndex >= this.previewSizes.size() || this.previewSizeIndex < 0) {
                    break;
                }
                Size nextSize = (Size) this.previewSizes.get(this.previewSizeIndex);
                double sizeRatio = ((double) (nextSize.width * nextSize.height)) / ((double) previousSizeTotal);
                boolean validRatio = (sizeRatio < rat && rat < 1.0d) || (sizeRatio > rat && rat > 1.0d);
                done = validRatio || this.previewSizeIndex == 0 || this.previewSizeIndex == this.previewSizes.size() - 1;
            }
            if (idx == 0 && desiredSize != null) {
                this.previewSizeIndex = this.previewSizes.indexOf(desiredSize);
            }
            if (this.previewSizeIndex + idx < 0) {
                this.previewSizeIndex = 0;
            } else {
                if (this.previewSizeIndex + idx >= this.previewSizes.size()) {
                    this.previewSizeIndex = this.previewSizes.size() - 1;
                }
            }
            size = (Size) this.previewSizes.get(this.previewSizeIndex);
        }
        return size;
    }

    public synchronized Size getBiggerPreviewIndex(int w, int h) {
        Size size;
        if (this.previewSizes == null) {
            size = null;
        } else {
            float matchAspectRatio = ((float) w) / ((float) h);
            float bestAspectRatio = AutoScrollHelper.NO_MAX;
            size = null;
            for (Size size2 : this.previewSizes) {
                float aspectDiff = Math.abs((((float) size2.width) / ((float) size2.height)) - matchAspectRatio);
                if (size2.width >= w && size2.height >= h && aspectDiff < bestAspectRatio) {
                    size = size2;
                    bestAspectRatio = aspectDiff;
                }
            }
        }
        return size;
    }

    public synchronized Size getOptimalPreviewIndex(int w, int h, boolean tryBiggerOnlyFirst) {
        Size optimalSize = null;
        synchronized (this) {
            if (this.previewSizes != null) {
                int targetWidth = w;
                int targetHeight = h;
                if (tryBiggerOnlyFirst) {
                    optimalSize = getBiggerPreviewIndex(w, h);
                }
                if (optimalSize == null) {
                    int minSide = Integer.MAX_VALUE;
                    int totalDist = Integer.MAX_VALUE;
                    for (Size size : this.previewSizes) {
                        if (size.width <= targetWidth && size.height <= targetHeight) {
                            int dist = Math.min(Math.abs(size.height - targetHeight), Math.abs(size.width - targetWidth));
                            int distL1 = Math.abs(size.height - targetHeight) + Math.abs(size.width - targetWidth);
                            if (dist < minSide || (dist == minSide && distL1 < totalDist)) {
                                optimalSize = size;
                                minSide = dist;
                                totalDist = distL1;
                            }
                        }
                    }
                }
            }
        }
        return optimalSize;
    }

    public static int pruneBadValues(double goalAspect, List<Size> sizes) {
        List<Size> badSizes = new ArrayList();
        for (Size s : sizes) {
            if (!(Math.abs(goalAspect - ((double) (((float) s.width) / ((float) s.height)))) < -0.2d)) {
                badSizes.add(s);
            }
        }
        if (badSizes.size() < sizes.size()) {
            sizes.removeAll(badSizes);
        }
        return badSizes.size();
    }
}
