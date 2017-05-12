package it.sephiroth.android.library.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.AutoScrollHelper;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import it.sephiroth.android.library.R;

public class EdgeEffect {
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 0;
    private static final float EPSILON = 0.001f;
    private static final float HELD_EDGE_SCALE_Y = 0.5f;
    private static final float MAX_ALPHA = 1.0f;
    private static final float MAX_GLOW_HEIGHT = 4.0f;
    private static final int MIN_VELOCITY = 100;
    private static final int MIN_WIDTH = 300;
    private static final int PULL_DECAY_TIME = 1000;
    private static final float PULL_DISTANCE_ALPHA_GLOW_FACTOR = 1.1f;
    private static final int PULL_DISTANCE_EDGE_FACTOR = 7;
    private static final int PULL_DISTANCE_GLOW_FACTOR = 7;
    private static final float PULL_EDGE_BEGIN = 0.6f;
    private static final float PULL_GLOW_BEGIN = 1.0f;
    private static final int PULL_TIME = 167;
    private static final int RECEDE_TIME = 1000;
    private static final int STATE_ABSORB = 2;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_PULL_DECAY = 4;
    private static final int STATE_RECEDE = 3;
    private static final String TAG = "EdgeEffect";
    private static final int VELOCITY_EDGE_FACTOR = 8;
    private static final int VELOCITY_GLOW_FACTOR = 16;
    private final Rect mBounds = new Rect();
    private final int mDirection;
    private float mDuration;
    private final Drawable mEdge;
    private float mEdgeAlpha;
    private float mEdgeAlphaFinish;
    private float mEdgeAlphaStart;
    private final int mEdgeHeight;
    private float mEdgeScaleY;
    private float mEdgeScaleYFinish;
    private float mEdgeScaleYStart;
    private final Drawable mGlow;
    private float mGlowAlpha;
    private float mGlowAlphaFinish;
    private float mGlowAlphaStart;
    private final int mGlowHeight;
    private float mGlowScaleY;
    private float mGlowScaleYFinish;
    private float mGlowScaleYStart;
    private final int mGlowWidth;
    private int mHeight;
    private final Interpolator mInterpolator;
    private final int mMaxEffectHeight;
    private final int mMinWidth;
    private float mPullDistance;
    private long mStartTime;
    private int mState = 0;
    private int mWidth;
    private int mX;
    private int mY;

    public EdgeEffect(Context context, int direction) {
        Resources res = context.getResources();
        this.mEdge = res.getDrawable(R.drawable.hlv_overscroll_edge);
        this.mGlow = res.getDrawable(R.drawable.hlv_overscroll_glow);
        this.mDirection = direction;
        this.mEdgeHeight = this.mEdge.getIntrinsicHeight();
        this.mGlowHeight = this.mGlow.getIntrinsicHeight();
        this.mGlowWidth = this.mGlow.getIntrinsicWidth();
        this.mMaxEffectHeight = (int) (Math.min((((((float) this.mGlowHeight) * MAX_GLOW_HEIGHT) * ((float) this.mGlowHeight)) / ((float) this.mGlowWidth)) * PULL_EDGE_BEGIN, ((float) this.mGlowHeight) * MAX_GLOW_HEIGHT) + HELD_EDGE_SCALE_Y);
        this.mMinWidth = (int) ((res.getDisplayMetrics().density * BitmapDescriptorFactory.HUE_MAGENTA) + HELD_EDGE_SCALE_Y);
        this.mInterpolator = new DecelerateInterpolator();
    }

    public void setSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public void setPosition(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public boolean isFinished() {
        return this.mState == 0;
    }

    public void finish() {
        this.mState = 0;
    }

    public void onPull(float deltaDistance) {
        long now = AnimationUtils.currentAnimationTimeMillis();
        if (this.mState != 4 || ((float) (now - this.mStartTime)) >= this.mDuration) {
            if (this.mState != 1) {
                this.mGlowScaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            this.mState = 1;
            this.mStartTime = now;
            this.mDuration = 167.0f;
            this.mPullDistance += deltaDistance;
            float distance = Math.abs(this.mPullDistance);
            float max = Math.max(PULL_EDGE_BEGIN, Math.min(distance, TextTrackStyle.DEFAULT_FONT_SCALE));
            this.mEdgeAlphaStart = max;
            this.mEdgeAlpha = max;
            max = Math.max(HELD_EDGE_SCALE_Y, Math.min(distance * 7.0f, TextTrackStyle.DEFAULT_FONT_SCALE));
            this.mEdgeScaleYStart = max;
            this.mEdgeScaleY = max;
            max = Math.min(TextTrackStyle.DEFAULT_FONT_SCALE, this.mGlowAlpha + (Math.abs(deltaDistance) * PULL_DISTANCE_ALPHA_GLOW_FACTOR));
            this.mGlowAlphaStart = max;
            this.mGlowAlpha = max;
            float glowChange = Math.abs(deltaDistance);
            if (deltaDistance > 0.0f && this.mPullDistance < 0.0f) {
                glowChange = -glowChange;
            }
            if (this.mPullDistance == 0.0f) {
                this.mGlowScaleY = 0.0f;
            }
            max = Math.min(MAX_GLOW_HEIGHT, Math.max(0.0f, this.mGlowScaleY + (glowChange * 7.0f)));
            this.mGlowScaleYStart = max;
            this.mGlowScaleY = max;
            this.mEdgeAlphaFinish = this.mEdgeAlpha;
            this.mEdgeScaleYFinish = this.mEdgeScaleY;
            this.mGlowAlphaFinish = this.mGlowAlpha;
            this.mGlowScaleYFinish = this.mGlowScaleY;
        }
    }

    public void onRelease() {
        this.mPullDistance = 0.0f;
        if (this.mState == 1 || this.mState == 4) {
            this.mState = 3;
            this.mEdgeAlphaStart = this.mEdgeAlpha;
            this.mEdgeScaleYStart = this.mEdgeScaleY;
            this.mGlowAlphaStart = this.mGlowAlpha;
            this.mGlowScaleYStart = this.mGlowScaleY;
            this.mEdgeAlphaFinish = 0.0f;
            this.mEdgeScaleYFinish = 0.0f;
            this.mGlowAlphaFinish = 0.0f;
            this.mGlowScaleYFinish = 0.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 1000.0f;
        }
    }

    public void onAbsorb(int velocity) {
        this.mState = 2;
        velocity = Math.max(100, Math.abs(velocity));
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = 0.1f + (((float) velocity) * 0.03f);
        this.mEdgeAlphaStart = 0.0f;
        this.mEdgeScaleYStart = 0.0f;
        this.mEdgeScaleY = 0.0f;
        this.mGlowAlphaStart = HELD_EDGE_SCALE_Y;
        this.mGlowScaleYStart = 0.0f;
        this.mEdgeAlphaFinish = (float) Math.max(0, Math.min(velocity * 8, 1));
        this.mEdgeScaleYFinish = Math.max(HELD_EDGE_SCALE_Y, Math.min((float) (velocity * 8), TextTrackStyle.DEFAULT_FONT_SCALE));
        this.mGlowScaleYFinish = Math.min(0.025f + (((float) ((velocity / 100) * velocity)) * 1.5E-4f), 1.75f);
        this.mGlowAlphaFinish = Math.max(this.mGlowAlphaStart, Math.min(((float) (velocity * 16)) * 1.0E-5f, TextTrackStyle.DEFAULT_FONT_SCALE));
    }

    public boolean draw(Canvas canvas) {
        update();
        this.mGlow.setAlpha((int) (Math.max(0.0f, Math.min(this.mGlowAlpha, TextTrackStyle.DEFAULT_FONT_SCALE)) * 255.0f));
        int glowBottom = (int) Math.min((((((float) this.mGlowHeight) * this.mGlowScaleY) * ((float) this.mGlowHeight)) / ((float) this.mGlowWidth)) * PULL_EDGE_BEGIN, ((float) this.mGlowHeight) * MAX_GLOW_HEIGHT);
        if (this.mDirection == 0) {
            this.mGlow.setBounds(0, 0, this.mWidth, glowBottom);
        } else {
            this.mGlow.setBounds(0, 0, this.mWidth, glowBottom);
        }
        this.mGlow.draw(canvas);
        this.mEdge.setAlpha((int) (Math.max(0.0f, Math.min(this.mEdgeAlpha, TextTrackStyle.DEFAULT_FONT_SCALE)) * 255.0f));
        int edgeBottom = (int) (((float) this.mEdgeHeight) * this.mEdgeScaleY);
        if (this.mDirection == 0) {
            this.mEdge.setBounds(0, 0, this.mWidth, edgeBottom);
        } else {
            this.mEdge.setBounds(0, 0, this.mWidth, edgeBottom);
        }
        this.mEdge.draw(canvas);
        if (this.mState == 3 && glowBottom == 0 && edgeBottom == 0) {
            this.mState = 0;
        }
        if (this.mState != 0) {
            return true;
        }
        return false;
    }

    public Rect getBounds(boolean reverse) {
        int i = 0;
        this.mBounds.set(0, 0, this.mWidth, this.mMaxEffectHeight);
        Rect rect = this.mBounds;
        int i2 = this.mX;
        int i3 = this.mY;
        if (reverse) {
            i = this.mMaxEffectHeight;
        }
        rect.offset(i2, i3 - i);
        return this.mBounds;
    }

    private void update() {
        float t = Math.min(((float) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime)) / this.mDuration, TextTrackStyle.DEFAULT_FONT_SCALE);
        float interp = this.mInterpolator.getInterpolation(t);
        this.mEdgeAlpha = this.mEdgeAlphaStart + ((this.mEdgeAlphaFinish - this.mEdgeAlphaStart) * interp);
        this.mEdgeScaleY = this.mEdgeScaleYStart + ((this.mEdgeScaleYFinish - this.mEdgeScaleYStart) * interp);
        this.mGlowAlpha = this.mGlowAlphaStart + ((this.mGlowAlphaFinish - this.mGlowAlphaStart) * interp);
        this.mGlowScaleY = this.mGlowScaleYStart + ((this.mGlowScaleYFinish - this.mGlowScaleYStart) * interp);
        if (t >= 0.999f) {
            switch (this.mState) {
                case 1:
                    this.mState = 4;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 1000.0f;
                    this.mEdgeAlphaStart = this.mEdgeAlpha;
                    this.mEdgeScaleYStart = this.mEdgeScaleY;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mEdgeAlphaFinish = 0.0f;
                    this.mEdgeScaleYFinish = 0.0f;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                    return;
                case 2:
                    this.mState = 3;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 1000.0f;
                    this.mEdgeAlphaStart = this.mEdgeAlpha;
                    this.mEdgeScaleYStart = this.mEdgeScaleY;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mEdgeAlphaFinish = 0.0f;
                    this.mEdgeScaleYFinish = 0.0f;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                    return;
                case 3:
                    this.mState = 0;
                    return;
                case 4:
                    this.mEdgeScaleY = this.mEdgeScaleYStart + (((this.mEdgeScaleYFinish - this.mEdgeScaleYStart) * interp) * (this.mGlowScaleYFinish != 0.0f ? TextTrackStyle.DEFAULT_FONT_SCALE / (this.mGlowScaleYFinish * this.mGlowScaleYFinish) : AutoScrollHelper.NO_MAX));
                    this.mState = 3;
                    return;
                default:
                    return;
            }
        }
    }
}
