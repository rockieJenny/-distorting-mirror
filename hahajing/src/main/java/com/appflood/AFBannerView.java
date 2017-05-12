package com.appflood;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import com.appflood.AppFlood.AFBannerShowDelegate;
import com.appflood.c.a;
import com.appflood.c.b;
import com.appflood.c.e;
import com.appflood.c.f;
import com.appflood.e.c;
import com.appflood.e.j;
import com.appflood.mraid.AFBannerWebView;
import com.flurry.android.AdCreative;
import com.google.android.gms.cast.TextTrackStyle;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AFBannerView extends RelativeLayout implements b {
    private a a;
    private int b;
    private int c;
    private View d;
    private int e;
    private int f;
    private int g;
    private Timer h;
    private boolean i;
    private boolean j;
    private AFBannerShowDelegate k;

    public AFBannerView(Context context) {
        this(context, 14);
    }

    public AFBannerView(Context context, int i) {
        super(context);
        this.b = 0;
        this.c = 0;
        this.e = 14;
        this.f = 470;
        this.g = 73;
        this.i = false;
        this.j = false;
        init(context);
        setType(i);
    }

    public AFBannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = 0;
        this.c = 0;
        this.e = 14;
        this.f = 470;
        this.g = 73;
        this.i = false;
        this.j = false;
        init(context);
        setType(14);
    }

    private synchronized void close(boolean z) {
        if (!this.j) {
            this.j = true;
            if (this.a != null) {
                this.a.d();
                this.a = null;
            }
            if (!z) {
                if (this.d != null && (this.d instanceof AFBannerWebView)) {
                    ((AFBannerWebView) this.d).destroy();
                }
                removeAllViews();
                setVisibility(4);
                if (this.h != null) {
                    this.h.cancel();
                    this.h = null;
                }
                if (getParent() != null) {
                    ((ViewGroup) getParent()).removeView(this);
                }
                e.a().a(false, j.a(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, Integer.valueOf(1)));
            }
        }
    }

    private void init(Context context) {
        setBackgroundColor(0);
        setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
            }
        });
        this.a = new a(context);
        this.a.a((b) this);
        this.a.a();
    }

    private int myResolveSizeAndState(int i, int i2, int i3) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        "ooooooooooooo specMode " + mode;
        j.a();
        switch (mode) {
            case ExploreByTouchHelper.INVALID_ID /*-2147483648*/:
                if (size < i) {
                    i = size | ViewCompat.MEASURED_STATE_TOO_SMALL;
                    break;
                }
                break;
            case 1073741824:
                i = size;
                break;
        }
        return (ViewCompat.MEASURED_STATE_MASK & i3) | i;
    }

    private int resolveAdjustedSize(int i, int i2) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        switch (mode) {
            case ExploreByTouchHelper.INVALID_ID /*-2147483648*/:
                "ooooooooooooo  22222 " + i + " specSize " + size;
                j.a();
                return Math.min(i, size);
            case 0:
                j.a();
                return i;
            case 1073741824:
                j.a();
                return size;
            default:
                return i;
        }
    }

    private void setType(int i) {
        this.e = i;
        if (this.e == 16) {
            this.f = com.appflood.AFListActivity.AnonymousClass1.a(getContext(), 527);
            this.g = com.appflood.AFListActivity.AnonymousClass1.a(getContext(), 88);
        } else if (i == 17) {
            this.f = com.appflood.AFListActivity.AnonymousClass1.a(getContext(), 313);
            this.g = com.appflood.AFListActivity.AnonymousClass1.a(getContext(), 49);
        }
        this.a.b(i);
    }

    private void startFadeIn(View view) {
        Animation alphaAnimation = new AlphaAnimation(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        view.setAnimation(alphaAnimation);
        alphaAnimation.setDuration(500);
        alphaAnimation.setAnimationListener(new AnimationListener() {
            public final void onAnimationEnd(Animation animation) {
            }

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }
        });
        alphaAnimation.start();
    }

    private void startFadeOut(final View view) {
        Animation alphaAnimation = new AlphaAnimation(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        view.setAnimation(alphaAnimation);
        alphaAnimation.setDuration(500);
        alphaAnimation.setAnimationListener(new AnimationListener(this) {
            private /* synthetic */ AFBannerView b;

            public final void onAnimationEnd(Animation animation) {
                if (view instanceof AFBannerWebView) {
                    ((AFBannerWebView) view).destroy();
                }
                this.b.removeAllViews();
                LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.b.getWidth(), this.b.getHeight());
                this.b.d = this.b.a.c();
                this.b.addView(this.b.d, layoutParams);
                this.b.startFadeIn(this.b.d);
            }

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }
        });
        view.setSelected(true);
        alphaAnimation.start();
    }

    public LayoutParams getOriginalParams() {
        return null;
    }

    public int getType() {
        return this.e;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.h == null) {
            this.h = new Timer();
        }
        this.h.schedule(new TimerTask(this) {
            private /* synthetic */ AFBannerView a;

            {
                this.a = r1;
            }

            public final void run() {
                "onAttached to window " + this.a.getWidth() + " height " + this.a.getHeight();
                j.a();
                if (this.a.getWidth() > 0) {
                    this.a.b = this.a.getWidth();
                    this.a.c = this.a.getHeight();
                    int[] iArr = new int[2];
                    this.a.getLocationOnScreen(iArr);
                    int a = com.appflood.AFListActivity.AnonymousClass1.a(this.a.getContext());
                    int i = iArr[1] - a;
                    a = ((c.h - a) - i) - this.a.getHeight();
                    i = i == a ? 7 : i < a ? 4 : 5;
                    if (this.a.a != null) {
                        this.a.a.a(this.a.b, this.a.c, i);
                    }
                    if (this.a.h != null) {
                        this.a.h.cancel();
                        this.a.h = null;
                    }
                }
            }
        }, 500, 500);
    }

    public void onClick() {
    }

    public void onClose() {
        close(false);
    }

    public void onComplete() {
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        j.a();
        close(true);
    }

    public void onFinish(final HashMap<String, Object> hashMap) {
        boolean booleanValue = ((Boolean) hashMap.get("result")).booleanValue();
        "result = " + hashMap;
        j.a();
        if (booleanValue) {
            f.a(new Runnable(this) {
                private /* synthetic */ AFBannerView b;

                public final void run() {
                    if (this.b.getVisibility() != 0) {
                        View c = this.b.a.c();
                        if (c instanceof AFBannerWebView) {
                            ((AFBannerWebView) c).destroy();
                        }
                    } else if (this.b.d == null || !this.b.i) {
                        " ww " + this.b.getWidth() + " hh " + this.b.getHeight();
                        j.a();
                        LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.b.getWidth(), this.b.getHeight());
                        if ("video".equals(hashMap.get(AnalyticsSQLiteHelper.EVENT_LIST_TYPE))) {
                            int intValue = ((Integer) hashMap.get("w")).intValue();
                            int intValue2 = ((Integer) hashMap.get("h")).intValue();
                            if (intValue > 0 && intValue2 > 0) {
                                int width;
                                int i;
                                if (intValue > this.b.getWidth()) {
                                    width = this.b.getWidth();
                                    i = (intValue2 * width) / intValue;
                                } else {
                                    i = intValue2;
                                    width = intValue;
                                }
                                if (i > this.b.getHeight()) {
                                    i = this.b.getHeight();
                                    width = (i * intValue) / intValue2;
                                }
                                " caculate  ww " + width + " hh " + i;
                                j.a();
                                layoutParams.width = width;
                                layoutParams.height = i;
                                layoutParams.addRule(13);
                            }
                        }
                        this.b.d = this.b.a.c();
                        " this " + this + " parent " + this.b.d.getParent() + " webView " + this.b.d;
                        j.a();
                        if (this.b.d.getParent() == null) {
                            this.b.addView(this.b.d, layoutParams);
                        } else {
                            this.b.updateViewLayout(this.b.d, layoutParams);
                        }
                    } else {
                        this.b.startFadeOut(this.b.d);
                        this.b.i = false;
                    }
                }
            });
        } else if (hashMap.containsKey("video_error")) {
            this.i = true;
        }
        e.a().a(booleanValue, 1);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        "onLayout  changed " + z + " l " + i + " t " + i2 + " r " + i3 + " b " + i4;
        j.a();
        super.onLayout(z, i, i2, i3, i4);
    }

    protected void onMeasure(int i, int i2) {
        int mode;
        int myResolveSizeAndState;
        int i3;
        try {
            float f = ((float) this.f) / ((float) this.g);
            mode = MeasureSpec.getMode(i);
            int mode2 = MeasureSpec.getMode(i2);
            "oooooooooooooooo  wid " + mode + " heightSpecMode " + mode2;
            j.a();
            Object obj = mode != 1073741824 ? 1 : null;
            Object obj2 = mode2 != 1073741824 ? 1 : null;
            "oooooooooooooooo banner w " + this.f + " h = " + this.g;
            j.a();
            int max;
            LayoutParams layoutParams;
            if (obj == null && obj2 == null) {
                mode = Math.max(this.f, getSuggestedMinimumWidth());
                max = Math.max(this.g, getSuggestedMinimumHeight());
                mode = myResolveSizeAndState(mode, i, 0);
                try {
                    myResolveSizeAndState = myResolveSizeAndState(max, i2, 0);
                } catch (Throwable th) {
                    myResolveSizeAndState = 0;
                }
                layoutParams = getLayoutParams();
                if (layoutParams.width != -2) {
                }
                "onmeasure " + mode + "  hhh " + myResolveSizeAndState;
                j.a();
                super.onMeasure(i, i);
                setMeasuredDimension(mode, myResolveSizeAndState);
                return;
            }
            myResolveSizeAndState = resolveAdjustedSize(this.f, i);
            mode = resolveAdjustedSize(this.g, i2);
            try {
                "oooooooooooooooo resize w " + myResolveSizeAndState + " h = " + mode;
                j.a();
                if (((double) Math.abs((((float) myResolveSizeAndState) / ((float) mode)) - f)) > 1.0E-7d) {
                    Object obj3;
                    int i4;
                    if (obj != null) {
                        max = (int) (((float) mode) * f);
                        if (max <= myResolveSizeAndState) {
                            obj3 = 1;
                            i4 = max;
                            if (obj3 == null && obj2 != null) {
                                myResolveSizeAndState = (int) (((float) i4) / f);
                                if (myResolveSizeAndState <= mode) {
                                    mode = i4;
                                }
                            }
                            myResolveSizeAndState = mode;
                            mode = i4;
                        }
                    }
                    i4 = myResolveSizeAndState;
                    obj3 = null;
                    myResolveSizeAndState = (int) (((float) i4) / f);
                    if (myResolveSizeAndState <= mode) {
                        mode = i4;
                    }
                    myResolveSizeAndState = mode;
                    mode = i4;
                } else {
                    i3 = mode;
                    mode = myResolveSizeAndState;
                    myResolveSizeAndState = i3;
                }
            } catch (Throwable th2) {
                i3 = mode;
                mode = myResolveSizeAndState;
                myResolveSizeAndState = i3;
            }
            layoutParams = getLayoutParams();
            if ((layoutParams.width != -2 || layoutParams.width == -1) && layoutParams.height == -2) {
                "onmeasure " + mode + "  hhh " + myResolveSizeAndState;
                j.a();
                super.onMeasure(i, i);
                setMeasuredDimension(mode, myResolveSizeAndState);
                return;
            }
            j.a();
            super.onMeasure(i, i2);
        } catch (Throwable th3) {
            mode = 0;
            myResolveSizeAndState = 0;
        }
    }

    public void setAutoFresh(boolean z) {
        if (this.a != null) {
            this.a.b(z);
        }
    }

    public void setPreload(boolean z) {
        this.a.a(z);
    }

    public void setShowDelegate(AFBannerShowDelegate aFBannerShowDelegate) {
        this.k = aFBannerShowDelegate;
        final Timer timer = new Timer(AdCreative.kFormatBanner);
        timer.schedule(new TimerTask(this) {
            private /* synthetic */ AFBannerView b;

            public final void run() {
                " run  shown " + this.b.isShown() + " " + this.b.hasWindowFocus() + " isEnable " + this.b.isEnabled() + " " + this.b.isClickable() + this.b.isFocused() + "   " + this.b.getVisibility();
                j.a();
                if (this.b.hasWindowFocus()) {
                    if (this.b.k != null) {
                        this.b.k.onResume();
                    }
                    timer.cancel();
                }
            }
        }, 500, 500);
    }

    public void setSize(int i, int i2, int i3) {
        this.a.a(i, i2, i3);
    }
}
