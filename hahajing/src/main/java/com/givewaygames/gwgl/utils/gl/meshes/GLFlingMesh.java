package com.givewaygames.gwgl.utils.gl.meshes;

import android.content.Context;
import android.opengl.Matrix;
import android.support.v4.widget.AutoScrollHelper;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLPiece;
import com.givewaygames.gwgl.utils.gl.GLTransform;
import com.google.ads.AdSize;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.qualcomm.snapdragon.sdk.face.FacialProcessingConstants;
import java.util.ArrayList;
import java.util.Random;

public class GLFlingMesh extends GLPiece implements OnTouchListener, OnGestureListener {
    private static final int ANIMATION_SPEED = 270;
    private static final boolean DEBUG = CameraWrapper.DEBUG;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "GLFlingMesh";
    public static final float minIndexSize = 6.0f;
    public static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= TextTrackStyle.DEFAULT_FONT_SCALE;
            return ((((t * t) * t) * t) * t) + TextTrackStyle.DEFAULT_FONT_SCALE;
        }
    };
    private int mActivePointerId;
    private long mAnimationEndTime;
    private long mAnimationStartTime;
    boolean mClockwise;
    private int mCurItem;
    private int mCurrentDegree;
    private FlingRunnable mFlingRunnable;
    private GestureDetector mGestureDetector;
    private boolean mIsBeingDragged;
    private int mPageMargin;
    private int mScrollState;
    private Scroller mScroller;
    private boolean mScrolling;
    private int mStartDegree;
    private int mTargetDegree;
    private VelocityTracker mVelocityTracker;
    OnFlingItemChangedListener onFlingItemChangedListener;
    float[] tmpSubPosition;
    ArrayList<GLTransform> transforms;
    private float userInterfaceSensitivity;
    int wallHeight;
    int wallWidth;
    float x;
    float y;

    private class FlingRunnable implements Runnable {
        private int mLastFlingX;

        public FlingRunnable(Context context) {
            GLFlingMesh.this.mScroller = new Scroller(context);
        }

        public void startUsingVelocity(int initialVelocity) {
            if (initialVelocity != 0) {
                this.mLastFlingX = (int) GLFlingMesh.this.x;
                GLFlingMesh.this.mScrolling = true;
                GLFlingMesh.this.mScroller.fling(this.mLastFlingX, 0, initialVelocity, 0, FacialProcessingConstants.FP_NOT_PROCESSED, Integer.MAX_VALUE, FacialProcessingConstants.FP_NOT_PROCESSED, Integer.MAX_VALUE);
            }
        }

        public void stop(boolean scrollIntoSlots) {
            endFling(scrollIntoSlots);
        }

        private void endFling(boolean scrollIntoSlots) {
            GLFlingMesh.this.mScroller.forceFinished(true);
        }

        public void run() {
        }
    }

    public interface OnFlingItemChangedListener {
        void onFlingItemChanged(int i);
    }

    public GLFlingMesh(Context context) {
        this.userInterfaceSensitivity = 0.1f;
        this.wallWidth = 1;
        this.wallHeight = 1;
        this.mCurrentDegree = 0;
        this.mStartDegree = 0;
        this.mTargetDegree = 0;
        this.mAnimationStartTime = 0;
        this.mAnimationEndTime = 0;
        this.mActivePointerId = -1;
        this.mScrollState = 0;
        this.transforms = new ArrayList();
        this.tmpSubPosition = new float[4];
        this.mScroller = new Scroller(context, sInterpolator);
        this.mFlingRunnable = new FlingRunnable(context);
        this.mGestureDetector = new GestureDetector(context, this);
        this.mGestureDetector.setIsLongpressEnabled(true);
    }

    public void setWallSize(int w, int h) {
        this.wallWidth = w;
        this.wallHeight = h;
    }

    public float getUserInterfaceSensitivity() {
        return this.userInterfaceSensitivity;
    }

    public GLFlingMesh(Scroller scroller) {
        this.userInterfaceSensitivity = 0.1f;
        this.wallWidth = 1;
        this.wallHeight = 1;
        this.mCurrentDegree = 0;
        this.mStartDegree = 0;
        this.mTargetDegree = 0;
        this.mAnimationStartTime = 0;
        this.mAnimationEndTime = 0;
        this.mActivePointerId = -1;
        this.mScrollState = 0;
        this.transforms = new ArrayList();
        this.tmpSubPosition = new float[4];
        this.mScroller = scroller;
    }

    public void setOnFlingItemChangedListener(OnFlingItemChangedListener onFlingItemChangedListener) {
        this.onFlingItemChangedListener = onFlingItemChangedListener;
    }

    public int getCurrentIndex() {
        return this.mCurItem;
    }

    public void addTransform(GLTransform transform) {
        this.transforms.add(transform);
    }

    public void updateUserInterfaceSensitivity(float pSize) {
        this.userInterfaceSensitivity = (TextTrackStyle.DEFAULT_FONT_SCALE / pSize) / 2.0f;
    }

    private void setScrollState(int newState) {
        if (this.mScrollState != newState) {
            this.mScrollState = newState;
        }
    }

    public void computeScroll() {
        if (DEBUG) {
            Log.i(TAG, "computeScroll: finished=" + this.mScroller.isFinished());
        }
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll();
            return;
        }
        if (DEBUG) {
            Log.i(TAG, "computeScroll: still scrolling");
        }
        float oldX = getScrollX();
        float oldY = getScrollY();
        float x = (float) this.mScroller.getCurrX();
        float y = (float) this.mScroller.getCurrY();
        if (oldX != x || oldY != y) {
            scrollTo(x, y);
        }
    }

    private void completeScroll() {
        if (this.mScrolling) {
            this.mScroller.abortAnimation();
            float oldX = getScrollX();
            float oldY = getScrollY();
            float x = (float) this.mScroller.getCurrX();
            float y = (float) this.mScroller.getCurrY();
            if (!(oldX == x && oldY == y)) {
                scrollTo(x, y);
            }
            setScrollState(0);
            this.mScrolling = false;
            animateToEndPosition(x, y);
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        if (ev.getAction() == 0 && ev.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        if (this.mGestureDetector.onTouchEvent(ev)) {
            return true;
        }
        switch (ev.getAction() & 255) {
            case 0:
            case 2:
                return true;
            case 1:
            case 3:
                if (!this.mIsBeingDragged) {
                    return true;
                }
                animateToEndPosition(this.x, this.y);
                this.mActivePointerId = -1;
                endDrag();
                return true;
            case 5:
                this.mActivePointerId = ev.getPointerId(ev.getActionIndex());
                return true;
            case 6:
                onSecondaryPointerUp(ev);
                return true;
            default:
                return true;
        }
    }

    public void setCurrentItem(int item, boolean animate) {
        updateUserInterfaceSensitivity(Math.max(minIndexSize, getPartialSize(true)));
        if (animate) {
            animateToEndIndex(item);
        } else {
            setToEndIndex(item);
        }
    }

    public void setToRandomItem(Random r) {
        float x = r.nextFloat() * (360.0f / this.userInterfaceSensitivity);
        float totSize = 360.0f / this.userInterfaceSensitivity;
        boolean neg = x < 0.0f;
        x = (float) ((int) (x % totSize));
        if (x < 0.0f) {
            x += totSize;
        }
        float subSize = getSubPartialSize(true);
        float pSize = Math.max(getSubPartialSize(true), minIndexSize);
        float rIdx = roundToNearest(positionToIndex(x, pSize) * this.userInterfaceSensitivity, subSize, pSize, neg, true);
        float xp = indexToPosition(rIdx, pSize) / this.userInterfaceSensitivity;
        if (Math.abs((int) (xp - x)) >= 2) {
            scrollTo((float) ((int) xp), 0.0f);
        }
        this.mCurItem = partialToTrueIndex(rIdx, subSize);
        if (this.onFlingItemChangedListener != null) {
            this.onFlingItemChangedListener.onFlingItemChanged(this.mCurItem);
        }
    }

    private boolean isPrimaryItem(int item) {
        float size = (float) (this.wallWidth * this.wallHeight);
        return ((float) item) % size == size - TextTrackStyle.DEFAULT_FONT_SCALE;
    }

    void smoothScrollTo(int x, int y, int velocity, int change) {
        float width = getWidth();
        float maxDX = width * getPartialSize(true);
        int sx = (int) getScrollX();
        int sy = (int) getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        if (Math.abs((((float) sx) - maxDX) - ((float) x)) < ((float) Math.abs(dx))) {
            sx = (int) (((float) sx) - maxDX);
            dx = x - sx;
        }
        if (Math.abs((((float) sx) + maxDX) - ((float) x)) < ((float) Math.abs(dx))) {
            sx = (int) (((float) sx) + maxDX);
            dx = x - sx;
        }
        while (((float) dx) + width < 0.0f && change > 0) {
            dx = (int) (((float) dx) + maxDX);
            sx = (int) (((float) sx) - maxDX);
        }
        while (((float) dx) - width > 0.0f && change < 0) {
            dx = (int) (((float) dx) - maxDX);
            sx = (int) (((float) sx) + maxDX);
        }
        if (dx == 0 && dy == 0) {
            completeScroll();
            setScrollState(0);
            return;
        }
        int duration;
        int halfWidth = ((int) width) / 2;
        float distance = ((float) halfWidth) + (((float) halfWidth) * distanceInfluenceForSnapDuration(Math.min(TextTrackStyle.DEFAULT_FONT_SCALE, (TextTrackStyle.DEFAULT_FONT_SCALE * ((float) Math.abs(dx))) / width)));
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = Math.round(1000.0f * Math.abs(distance / ((float) velocity))) * 4;
        } else {
            duration = (int) ((TextTrackStyle.DEFAULT_FONT_SCALE + (((float) Math.abs(dx)) / (((float) this.mPageMargin) + width))) * 100.0f);
        }
        this.mScroller.startScroll(sx, sy, dx, dy, Math.min(duration, 600));
        this.mScrolling = true;
        setScrollState(2);
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = ev.getActionIndex();
        if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
            this.mActivePointerId = ev.getPointerId(pointerIndex == 0 ? 1 : 0);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private float getScrollX() {
        return this.x;
    }

    private float getScrollY() {
        return this.y;
    }

    private void scrollTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isScrolling() {
        return this.mScrolling;
    }

    private float getWidth() {
        return ((TextTrackStyle.DEFAULT_FONT_SCALE / this.userInterfaceSensitivity) * 360.0f) / getPartialSize(true);
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public boolean onInitialize() {
        return true;
    }

    public static float circularPosition(float n) {
        return 2.0f * (TextTrackStyle.DEFAULT_FONT_SCALE / ((float) (2.0d * Math.tan(3.141592653589793d / ((double) n)))));
    }

    public boolean draw(int pass, long time) {
        if (this.mScrolling) {
            computeScroll();
        }
        if (this.mCurrentDegree != this.mTargetDegree) {
            long animTime = AnimationUtils.currentAnimationTimeMillis();
            if (animTime < this.mAnimationEndTime) {
                int deltaTime = (int) (animTime - this.mAnimationStartTime);
                int i = this.mStartDegree;
                if (!this.mClockwise) {
                    deltaTime = -deltaTime;
                }
                int degree = i + ((deltaTime * ANIMATION_SPEED) / 1000);
                this.mCurrentDegree = degree >= 0 ? degree % 360 : (degree % 360) + 360;
            } else {
                this.mCurrentDegree = this.mTargetDegree;
            }
        }
        double xx = Math.cos(((double) (((float) this.mCurrentDegree) / BitmapDescriptorFactory.HUE_CYAN)) * 3.141592653589793d);
        double yy = Math.sin(((double) (((float) this.mCurrentDegree) / BitmapDescriptorFactory.HUE_CYAN)) * 3.141592653589793d);
        int size = this.transforms.size();
        float pSize = Math.max(minIndexSize, getPartialSize(true));
        updateUserInterfaceSensitivity(pSize);
        for (int i2 = 0; i2 < size; i2++) {
            int fullAmt = (int) (getPartialIndex(i2, pSize, false) + 0.5f);
            float newAngle = ((-this.x) * this.userInterfaceSensitivity) + indexToPosition(getPartialIndex(i2, pSize, true), pSize);
            float z = circularPosition(pSize);
            if (((GLTransform) this.transforms.get(i2)).amountEnabled() + 0.001f < TextTrackStyle.DEFAULT_FONT_SCALE) {
                z = 10000.0f;
            }
            float[] arr = ((GLTransform) this.transforms.get(i2)).getRawTransform();
            Matrix.setIdentityM(arr, 0);
            Matrix.rotateM(arr, 0, -newAngle, (float) yy, (float) xx, 0.0f);
            Matrix.translateM(arr, 0, 0.0f, 0.0f, z);
            subPosition(fullAmt, arr, this.wallWidth, this.wallHeight);
        }
        return true;
    }

    private void subPosition(int pIdx, float[] arr, int numX, int numY) {
        GLWallOfWalls.getLayoutParams(this.tmpSubPosition, getSubIndex(pIdx, numX, numY), numX, numY);
        Matrix.scaleM(arr, 0, this.tmpSubPosition[2], this.tmpSubPosition[3], TextTrackStyle.DEFAULT_FONT_SCALE);
        Matrix.translateM(arr, 0, this.tmpSubPosition[0] * ((float) numX), this.tmpSubPosition[1] * ((float) numY), 0.0f);
    }

    private int getSubIndex(int i, int numX, int numY) {
        return i % (numX * numY);
    }

    public float getSubPartialSize(boolean skipSecondaries) {
        float amt = 0.0f;
        int size = this.transforms.size();
        for (int i = 0; i < size; i++) {
            float amountEnabled = (isPrimaryItem(i) || !skipSecondaries) ? ((GLTransform) this.transforms.get(i)).amountEnabled() : 0.0f;
            amt += amountEnabled;
        }
        return amt;
    }

    public float getPartialSize(boolean skipSecondaries) {
        return Math.max(getSubPartialSize(skipSecondaries), minIndexSize);
    }

    public float getPartialIndex(int i, float pSize, boolean skipSecondaries) {
        float amt = 0.0f;
        boolean flip = false;
        if (i < 0) {
            i += this.transforms.size();
            flip = true;
        }
        int n = i / this.transforms.size();
        int r = i % this.transforms.size();
        for (int k = 0; k < r; k++) {
            float amountEnabled = (isPrimaryItem(k) || !skipSecondaries) ? ((GLTransform) this.transforms.get(k % this.transforms.size())).amountEnabled() : 0.0f;
            amt += amountEnabled;
        }
        amt += ((float) n) * pSize;
        if (flip) {
            return amt - pSize;
        }
        return amt;
    }

    public float indexToPosition(float amt, float partialSize) {
        return (360.0f / partialSize) * amt;
    }

    public float positionToIndex(float pos, float partialSize) {
        return pos / (360.0f / partialSize);
    }

    public float roundToNearest(float idx, float subSize, float pSize, boolean neg, boolean skipSecondaries) {
        float amt = GroundOverlayOptions.NO_DIMENSION;
        float minDist = AutoScrollHelper.NO_MAX;
        float bestIndex = 0.0f;
        for (int k = 0; k < this.transforms.size(); k++) {
            float amountEnabled = (isPrimaryItem(k) || !skipSecondaries) ? ((GLTransform) this.transforms.get(k)).amountEnabled() : 0.0f;
            amt += amountEnabled;
            if (amt >= -1.0E-5f && Math.abs(amt - idx) < minDist) {
                minDist = Math.abs(amt - idx);
                bestIndex = amt;
            }
            if (amt > idx) {
                break;
            }
        }
        if ((subSize - pSize < 1.0E-4f) && Math.abs(subSize - idx) < minDist) {
            if (neg) {
                bestIndex = amt;
            } else {
                bestIndex = pSize;
            }
        }
        if (Math.abs(subSize - idx) < minDist) {
            bestIndex = pSize;
        }
        if (Math.abs(pSize - idx) < minDist) {
            return amt;
        }
        return bestIndex;
    }

    public int partialToTrueIndex(float partial, float pSize) {
        partial %= pSize - TextTrackStyle.DEFAULT_FONT_SCALE;
        float amt = GroundOverlayOptions.NO_DIMENSION;
        int size = this.transforms.size();
        for (int i = 0; i < size; i++) {
            amt += isPrimaryItem(i) ? ((GLTransform) this.transforms.get(i)).amountEnabled() : 0.0f;
            if (((double) Math.abs(amt - partial)) < 0.001d) {
                return i;
            }
        }
        return -1;
    }

    public void animateToEndPosition(float x, float y) {
        float totSize = 360.0f / this.userInterfaceSensitivity;
        boolean neg = x < 0.0f;
        x = (float) ((int) (x % totSize));
        if (x < 0.0f) {
            x += totSize;
        }
        float subSize = getSubPartialSize(true);
        float pSize = Math.max(getSubPartialSize(true), minIndexSize);
        float rIdx = roundToNearest(positionToIndex(x, pSize) * this.userInterfaceSensitivity, subSize, pSize, neg, true);
        float xp = indexToPosition(rIdx, pSize) / this.userInterfaceSensitivity;
        if (Math.abs((int) (xp - x)) >= 2) {
            this.mScroller.startScroll((int) x, (int) y, (int) (xp - x), 0, (int) ((TextTrackStyle.DEFAULT_FONT_SCALE + (Math.abs(xp - x) / (getWidth() + ((float) this.mPageMargin)))) * 100.0f));
            this.mScrolling = true;
            setScrollState(2);
            this.mCurItem = partialToTrueIndex(rIdx, subSize);
            if (this.onFlingItemChangedListener != null) {
                this.onFlingItemChangedListener.onFlingItemChanged(this.mCurItem);
            }
        }
    }

    public void setToEndIndex(int index) {
        float pSize = getPartialSize(true);
        float xp = indexToPosition(getPartialIndex(index, pSize, true), pSize) / this.userInterfaceSensitivity;
        if (Math.abs((int) (xp - this.x)) >= 2) {
            scrollTo((float) ((int) xp), 0.0f);
        }
        this.mCurItem = index;
        if (this.onFlingItemChangedListener != null) {
            this.onFlingItemChangedListener.onFlingItemChanged(this.mCurItem);
        }
    }

    public void animateToEndIndex(int index) {
        float pSize = getPartialSize(true);
        float xp = indexToPosition(getPartialIndex(index, pSize, true), pSize) / this.userInterfaceSensitivity;
        if (Math.abs((int) (xp - this.x)) >= 2) {
            this.mScroller.startScroll((int) this.x, (int) this.y, (int) (xp - this.x), 0, (int) ((TextTrackStyle.DEFAULT_FONT_SCALE + (Math.abs(xp - this.x) / (getWidth() + ((float) this.mPageMargin)))) * 100.0f));
            this.mScrolling = true;
            setScrollState(2);
        }
        this.mCurItem = index;
        if (this.onFlingItemChangedListener != null) {
            this.onFlingItemChangedListener.onFlingItemChanged(this.mCurItem);
        }
    }

    public int getItemsSize() {
        return this.transforms.size();
    }

    public int getNumPasses() {
        return 1;
    }

    public boolean onDown(MotionEvent e) {
        this.mFlingRunnable.stop(false);
        return true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        this.mFlingRunnable.startUsingVelocity((int) (-deorient(velocityX, velocityY)));
        return true;
    }

    public void onLongPress(MotionEvent e) {
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        scrollTo(getScrollX() + ((float) ((int) deorient(distanceX, distanceY))), getScrollY());
        this.mIsBeingDragged = true;
        return true;
    }

    private float deorient(float x, float y) {
        int min = this.mTargetDegree;
        int degs = 0;
        if (Math.abs(this.mTargetDegree - 90) < min) {
            min = Math.abs(this.mTargetDegree - 90);
            degs = 90;
        }
        if (Math.abs(this.mTargetDegree - 180) < min) {
            min = Math.abs(this.mTargetDegree - 180);
            degs = 180;
        }
        if (Math.abs(this.mTargetDegree - 270) < min) {
            min = Math.abs(this.mTargetDegree - 270);
            degs = ANIMATION_SPEED;
        }
        switch (degs) {
            case AdSize.LARGE_AD_HEIGHT /*90*/:
                return -y;
            case 180:
                return -x;
            case ANIMATION_SPEED /*270*/:
                return y;
            default:
                return x;
        }
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public void setOrientation(int degree) {
        degree = degree >= 0 ? degree % 360 : (degree % 360) + 360;
        if (degree != this.mTargetDegree) {
            this.mTargetDegree = degree;
            this.mStartDegree = this.mCurrentDegree;
            this.mAnimationStartTime = AnimationUtils.currentAnimationTimeMillis();
            int diff = this.mTargetDegree - this.mCurrentDegree;
            if (diff < 0) {
                diff += 360;
            }
            if (diff > 180) {
                diff -= 360;
            }
            this.mClockwise = diff >= 0;
            this.mAnimationEndTime = this.mAnimationStartTime + ((long) ((Math.abs(diff) * 1000) / ANIMATION_SPEED));
        }
    }
}
