package com.inmobi.re.container.mraidimpl;

import android.app.Activity;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.cast.TextTrackStyle;
import com.inmobi.androidsdk.IMBrowserActivity;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.WrapperFunctions;
import com.inmobi.re.configs.Initializer;
import com.inmobi.re.container.CustomView;
import com.inmobi.re.container.CustomView.SwitchIconType;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.container.IMWebView.ViewState;
import com.inmobi.re.controller.util.Constants;
import java.util.concurrent.atomic.AtomicBoolean;

public class MRAIDInterstitialController {
    protected static final int INT_BACKGROUND_ID = 224;
    public static AtomicBoolean isInterstitialDisplayed = new AtomicBoolean();
    private IMWebView a;
    private Activity b;
    private long c = 0;
    private int d;
    public boolean lockOrientationValueForInterstitial = true;
    public Message mMsgOnInterstitialClosed;
    public Message mMsgOnInterstitialShown;
    public Display mSensorDisplay;
    public String orientationValueForInterstitial;

    public MRAIDInterstitialController(IMWebView iMWebView, Activity activity) {
        this.a = iMWebView;
    }

    public void setActivity(Activity activity) {
        if (activity != null) {
            this.b = activity;
        }
    }

    public void resetContentsForInterstitials() {
        try {
            if (this.a.getParent() != null) {
                this.b.setRequestedOrientation(this.d);
                this.a.mAudioVideoController.releaseAllPlayers();
                if (((RelativeLayout) ((FrameLayout) this.b.findViewById(16908290)).findViewById(INT_BACKGROUND_ID)) != null) {
                    if (this.c > 0) {
                        animateAndDismissWebview();
                    } else {
                        dismissWebview();
                    }
                }
                this.a.fireOnDismissAdScreen();
                this.a.injectJavaScript("window.mraidview.unRegisterOrientationListener()");
                this.a.setState(ViewState.HIDDEN);
                this.b.finish();
            }
        } catch (Throwable e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Failed to close the interstitial ad", e);
        }
    }

    public void animateAndDismissWebview() {
        Animation alphaAnimation = new AlphaAnimation(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setStartOffset(0);
        alphaAnimation.setDuration(this.c);
        alphaAnimation.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ MRAIDInterstitialController a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                this.a.dismissWebview();
            }
        });
        this.a.startAnimation(alphaAnimation);
    }

    public void dismissWebview() {
        final FrameLayout frameLayout = (FrameLayout) this.b.findViewById(16908290);
        final RelativeLayout relativeLayout = (RelativeLayout) frameLayout.findViewById(INT_BACKGROUND_ID);
        this.b.runOnUiThread(new Runnable(this) {
            final /* synthetic */ MRAIDInterstitialController c;

            public void run() {
                relativeLayout.removeView(this.c.a);
                frameLayout.removeView(relativeLayout);
            }
        });
    }

    public void handleOrientationForInterstitial() {
        this.a.lockExpandOrientation(this.b, this.lockOrientationValueForInterstitial, this.orientationValueForInterstitial);
    }

    public void changeContentAreaForInterstitials(long j) {
        try {
            this.c = j;
            int webviewBgColor = Initializer.getConfigParams().getWebviewBgColor();
            this.d = this.b.getRequestedOrientation();
            handleOrientationForInterstitial();
            FrameLayout frameLayout = (FrameLayout) this.b.findViewById(16908290);
            View relativeLayout = new RelativeLayout(this.a.getContext());
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent());
            layoutParams.addRule(10);
            this.a.setFocusable(true);
            this.a.setFocusableInTouchMode(true);
            relativeLayout.addView(this.a, layoutParams);
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) (this.a.getDensity() * 50.0f), (int) (this.a.getDensity() * 50.0f));
            layoutParams2.addRule(11);
            relativeLayout.addView(a(), layoutParams2);
            View customView = new CustomView(this.a.getContext(), this.a.getDensity(), SwitchIconType.CLOSE_BUTTON);
            customView.setVisibility(this.a.getCustomClose() ? 8 : 0);
            customView.setId(IMBrowserActivity.CLOSE_BUTTON_VIEW_ID);
            relativeLayout.addView(customView, layoutParams2);
            layoutParams = new RelativeLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent());
            relativeLayout.setId(INT_BACKGROUND_ID);
            relativeLayout.setBackgroundColor(webviewBgColor);
            frameLayout.addView(relativeLayout, layoutParams);
            this.a.setBackgroundColor(webviewBgColor);
            this.a.requestFocus();
            this.a.setOnKeyListener(new OnKeyListener(this) {
                final /* synthetic */ MRAIDInterstitialController a;

                {
                    this.a = r1;
                }

                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (4 != keyEvent.getKeyCode() || keyEvent.getAction() != 0) {
                        return false;
                    }
                    Log.debug(Constants.RENDERING_LOG_TAG, "Back Button pressed while Interstitial ad is in active state ");
                    this.a.handleInterstitialClose();
                    if (this.a.c > 0) {
                        return true;
                    }
                    return false;
                }
            });
            this.a.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ MRAIDInterstitialController a;

                {
                    this.a = r1;
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case 0:
                            view.requestFocus();
                            break;
                        case 1:
                            view.requestFocus();
                            break;
                    }
                    return false;
                }
            });
            isInterstitialDisplayed.set(true);
            this.a.fireOnShowAdScreen();
        } catch (Throwable e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Failed showing interstitial ad", e);
        }
    }

    private CustomView a() {
        CustomView customView = new CustomView(this.a.getContext(), this.a.getDensity(), SwitchIconType.CLOSE_TRANSPARENT);
        customView.setId(IMBrowserActivity.CLOSE_REGION_VIEW_ID);
        customView.disableView(this.a.getDisableCloseRegion());
        return customView;
    }

    public void handleInterstitialClose() {
        IMWebView.userInitiatedClose = true;
        isInterstitialDisplayed.set(false);
        this.a.close();
    }
}
