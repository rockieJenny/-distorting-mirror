package com.millennialmedia.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import com.givewaygames.gwgl.utils.gl.meshes.GLFlingMesh;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.wallet.WalletConstants;
import java.lang.ref.WeakReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

class AdViewOverlayView extends MMLayout {
    private static final String MRAID_CLOSE_BUTTON_DESRIPTION = "mraidCloseButton";
    private static final String TAG = "AdViewOverlayView";
    private Button mraidCloseButton;
    CloseTopDrawable mraidCloseDrawable;
    WeakReference<AdViewOverlayActivity> overlayActivityRef;
    private ProgressBar progressBar;
    private boolean progressDone;
    OverlaySettings settings;

    private static class AnimationListener implements android.view.animation.Animation.AnimationListener {
        private WeakReference<AdViewOverlayView> overlayRef;

        public AnimationListener(AdViewOverlayView videoView) {
            this.overlayRef = new WeakReference(videoView);
        }

        public void onAnimationStart(Animation animation) {
            AdViewOverlayView overlayView = (AdViewOverlayView) this.overlayRef.get();
            if (overlayView != null && overlayView.mraidCloseButton != null) {
                overlayView.mraidCloseButton.setVisibility(8);
            }
        }

        public void onAnimationEnd(Animation animation) {
            AdViewOverlayView overlayView = (AdViewOverlayView) this.overlayRef.get();
            if (overlayView != null) {
                Activity activity = (Activity) overlayView.getContext();
                MMLog.d(AdViewOverlayView.TAG, "Finishing overlay this is in w/ anim finishOverLayWithAnim()");
                activity.finish();
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private static class CloseDrawable extends Drawable {
        protected boolean enabled = true;
        protected final Paint paint;

        CloseDrawable(boolean enabled) {
            this.enabled = enabled;
            this.paint = new Paint();
            this.paint.setAntiAlias(true);
            this.paint.setStyle(Style.STROKE);
        }

        public void draw(Canvas canvas) {
            Rect bounds = copyBounds();
            int width = bounds.right - bounds.left;
            int height = bounds.bottom - bounds.top;
            float strokeWidth = ((float) width) / GLFlingMesh.minIndexSize;
            this.paint.setStrokeWidth(strokeWidth);
            int grayScale = this.enabled ? 255 : 80;
            this.paint.setARGB(255, grayScale, grayScale, grayScale);
            canvas.drawLine(strokeWidth / 2.0f, strokeWidth / 2.0f, ((float) width) - (strokeWidth / 2.0f), ((float) height) - (strokeWidth / 2.0f), this.paint);
            canvas.drawLine(((float) width) - (strokeWidth / 2.0f), strokeWidth / 2.0f, strokeWidth / 2.0f, ((float) height) - (strokeWidth / 2.0f), this.paint);
        }

        public int getOpacity() {
            return -3;
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter cf) {
        }
    }

    private static class FetchWebViewContentTask extends AsyncTask<Void, Void, String> {
        private WeakReference<AdViewOverlayView> _overlayViewRef;
        private String baseUrl;
        private boolean cancelVideo;

        public FetchWebViewContentTask(AdViewOverlayView view, String baseUrl) {
            this.baseUrl = baseUrl;
            this._overlayViewRef = new WeakReference(view);
        }

        protected void onPreExecute() {
            AdViewOverlayView view = (AdViewOverlayView) this._overlayViewRef.get();
            if (view != null && view.progressBar == null) {
                view.initProgressBar();
            }
            super.onPreExecute();
        }

        protected String doInBackground(Void... arg0) {
            this.cancelVideo = true;
            if (!TextUtils.isEmpty(this.baseUrl)) {
                try {
                    HttpResponse httpResponse = new HttpGetRequest().get(this.baseUrl);
                    if (httpResponse != null) {
                        StatusLine statusLine = httpResponse.getStatusLine();
                        if (!(httpResponse == null || statusLine == null || statusLine.getStatusCode() == WalletConstants.ERROR_CODE_INVALID_PARAMETERS)) {
                            HttpEntity httpEntity = httpResponse.getEntity();
                            if (httpEntity != null) {
                                String webContent = HttpGetRequest.convertStreamToString(httpEntity.getContent());
                                this.cancelVideo = false;
                                return webContent;
                            }
                        }
                    }
                } catch (Exception e) {
                    MMLog.e(AdViewOverlayView.TAG, "Unable to get weboverlay", e);
                }
            }
            return null;
        }

        protected void onPostExecute(String webContent) {
            AdViewOverlayView view = (AdViewOverlayView) this._overlayViewRef.get();
            if (view != null) {
                if (this.cancelVideo) {
                    AdViewOverlayActivity overlayActivity = (AdViewOverlayActivity) view.overlayActivityRef.get();
                    if (overlayActivity != null) {
                        overlayActivity.finish();
                    } else {
                        view.removeProgressBar();
                    }
                }
                if (webContent != null && view.adImpl != null && view.adImpl.controller != null) {
                    view.adImpl.controller.setWebViewContent(webContent, this.baseUrl);
                }
            }
        }
    }

    private static final class NonConfigurationInstance {
        MMAdImplController controller;
        boolean progressDone;
        OverlaySettings settings;

        private NonConfigurationInstance() {
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        public Object customInlineLayoutParams;
        String gson;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.gson = in.readString();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.gson);
        }
    }

    private static class SetCloseButtonTouchDelegateRunnable implements Runnable {
        int bottom;
        private final Button closeButton;
        int left;
        int right;
        int top;

        SetCloseButtonTouchDelegateRunnable(Button closeButton, int topMargin, int leftMargin, int bottomMargin, int rightMargin) {
            this.closeButton = closeButton;
            this.top = topMargin;
            this.left = leftMargin;
            this.bottom = bottomMargin;
            this.right = rightMargin;
        }

        public void run() {
            Rect delegateArea = new Rect();
            this.closeButton.getHitRect(delegateArea);
            delegateArea.top += this.top;
            delegateArea.right += this.right;
            delegateArea.bottom += this.bottom;
            delegateArea.left += this.left;
            TouchDelegate expandedArea = new TouchDelegate(delegateArea, this.closeButton);
            if (View.class.isInstance(this.closeButton.getParent())) {
                ((View) this.closeButton.getParent()).setTouchDelegate(expandedArea);
            }
        }
    }

    private static class CloseTopDrawable extends CloseDrawable {
        final float dist;
        final float scale;

        CloseTopDrawable(boolean enabled, float scale) {
            super(enabled);
            this.scale = scale;
            this.dist = 4.0f * scale;
            this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        }

        public void draw(Canvas canvas) {
            Rect bounds = copyBounds();
            float strokeWidth = ((float) (bounds.right - bounds.left)) / 10.0f;
            float cx = ((float) bounds.right) - (this.scale * 20.0f);
            float cy = ((float) bounds.top) + (this.scale * 20.0f);
            this.paint.setStrokeWidth(strokeWidth);
            this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.paint.setStyle(Style.STROKE);
            canvas.drawCircle(cx, cy, 12.0f * this.scale, this.paint);
            this.paint.setColor(-1);
            this.paint.setStyle(Style.FILL_AND_STROKE);
            canvas.drawCircle(cx, cy, this.scale * 10.0f, this.paint);
            this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawCircle(cx, cy, 7.0f * this.scale, this.paint);
            this.paint.setColor(-1);
            this.paint.setStrokeWidth(strokeWidth / 2.0f);
            this.paint.setStyle(Style.STROKE);
            canvas.drawLine(cx - this.dist, cy - this.dist, cx + this.dist, cy + this.dist, this.paint);
            canvas.drawLine(cx + this.dist, cy - this.dist, cx - this.dist, cy + this.dist, this.paint);
        }
    }

    private static class OverlayWebViewClientListener extends BasicWebViewClientListener {
        OverlayWebViewClientListener(MMAdImpl adImpl) {
            super(adImpl);
        }

        public void onPageFinished(String url) {
            super.onPageFinished(url);
            MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
            if (adImpl != null) {
                adImpl.removeProgressBar();
            }
        }
    }

    class AdViewOverlayViewMMAdImpl extends MMLayoutMMAdImpl {
        public AdViewOverlayViewMMAdImpl(Context context) {
            super(context);
            this.mmWebViewClientListener = new OverlayWebViewClientListener(this);
        }

        void removeProgressBar() {
            AdViewOverlayView.this.removeProgressBar();
        }

        boolean isExpandingToUrl() {
            return AdViewOverlayView.this.settings.hasExpandUrl() && !AdViewOverlayView.this.settings.hasLoadedExpandUrl();
        }

        MMWebViewClient getMMWebViewClient() {
            MMLog.d(AdViewOverlayView.TAG, "Returning a client for user: OverlayWebViewClient, adimpl=" + AdViewOverlayView.this.adImpl);
            if (AdViewOverlayView.this.adImpl.linkForExpansionId != 0 || AdViewOverlayView.this.settings.hasExpandUrl()) {
                MMWebViewClient bannerExpandedWebViewClient = new BannerExpandedWebViewClient(this.mmWebViewClientListener, new OverlayRedirectionListenerImpl(this));
                this.mmWebViewClient = bannerExpandedWebViewClient;
                return bannerExpandedWebViewClient;
            }
            bannerExpandedWebViewClient = new InterstitialWebViewClient(this.mmWebViewClientListener, new OverlayRedirectionListenerImpl(this));
            this.mmWebViewClient = bannerExpandedWebViewClient;
            return bannerExpandedWebViewClient;
        }
    }

    static class OverlayRedirectionListenerImpl extends MMAdImplRedirectionListenerImpl {
        public OverlayRedirectionListenerImpl(MMAdImpl adImpl) {
            super(adImpl);
        }

        public boolean isExpandingToUrl() {
            MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
            if (adImpl == null || !(adImpl instanceof AdViewOverlayViewMMAdImpl)) {
                return false;
            }
            return adImpl.isExpandingToUrl();
        }
    }

    AdViewOverlayView(AdViewOverlayActivity overlayActivity, OverlaySettings settingsIn) {
        LayoutParams params;
        super(overlayActivity.activity);
        this.overlayActivityRef = new WeakReference(overlayActivity);
        this.adImpl = new AdViewOverlayViewMMAdImpl(overlayActivity.activity);
        setId(15062);
        this.settings = settingsIn;
        NonConfigurationInstance nonConfigurationInstance = null;
        if (overlayActivity.activity instanceof Activity) {
            nonConfigurationInstance = (NonConfigurationInstance) overlayActivity.activity.getLastNonConfigurationInstance();
            if (nonConfigurationInstance != null) {
                this.progressDone = nonConfigurationInstance.progressDone;
                this.adImpl.controller = nonConfigurationInstance.controller;
                this.settings = nonConfigurationInstance.settings;
                if (!(this.adImpl == null || this.adImpl.controller == null || this.adImpl.controller.webView == null)) {
                    addView(this.adImpl.controller.webView);
                }
                MMLog.d(TAG, "Restoring configurationinstance w/ controller= " + nonConfigurationInstance.controller);
            } else {
                MMLog.d(TAG, "Null configurationinstance ");
            }
        }
        float scale = overlayActivity.activity.getResources().getDisplayMetrics().density;
        if (this.settings.height == 0 || this.settings.width == 0) {
            params = new LayoutParams(-1, -1);
        } else {
            params = new LayoutParams((int) (((float) this.settings.width) * scale), (int) (((float) this.settings.height) * scale));
        }
        params.addRule(13);
        setLayoutParams(params);
        Integer scaledPadding = Integer.valueOf((int) ((0.0625f * scale) * ((float) this.settings.shouldResizeOverlay)));
        setPadding(scaledPadding.intValue(), scaledPadding.intValue(), scaledPadding.intValue(), scaledPadding.intValue());
        this.mraidCloseButton = initMRaidCloseButton(overlayActivity.activity, scale);
        if (this.settings.isExpanded() && !this.settings.hasExpandUrl()) {
            this.adImpl.linkForExpansionId = this.settings.creatorAdImplId;
        }
        MMAdImplController.assignAdViewController(this.adImpl);
        if (this.mraidCloseButton != null) {
            addView(this.mraidCloseButton);
        }
        if (!(this.progressDone || this.settings.isExpanded() || this.settings.isFromInterstitial())) {
            initProgressBar();
        }
        if (this.settings.getIsTransparent()) {
            if (!(this.adImpl == null || this.adImpl.controller == null || this.adImpl.controller.webView == null)) {
                this.adImpl.controller.webView.setBackgroundColor(0);
            }
            setBackgroundColor(0);
        } else {
            if (!(this.adImpl == null || this.adImpl.controller == null || this.adImpl.controller.webView == null)) {
                this.adImpl.controller.webView.setBackgroundColor(-1);
            }
            setBackgroundColor(-1);
        }
        if (!(!this.settings.enableHardwareAccel() || this.adImpl == null || this.adImpl.controller == null || this.adImpl.controller.webView == null)) {
            this.adImpl.controller.webView.enableHardwareAcceleration();
        }
        if (nonConfigurationInstance == null) {
            animateView();
        }
        setUseCustomClose(this.settings.getUseCustomClose());
    }

    private void initProgressBar() {
        AdViewOverlayActivity overlayActivity = (AdViewOverlayActivity) this.overlayActivityRef.get();
        if (overlayActivity != null) {
            this.progressBar = new ProgressBar(overlayActivity.activity);
            this.progressBar.setIndeterminate(true);
            this.progressBar.setVisibility(0);
            LayoutParams progParams = new LayoutParams(-2, -2);
            progParams.addRule(13);
            addView(this.progressBar, progParams);
        }
    }

    private Button initMRaidCloseButton(Context context, float scale) {
        Button mraidCloseButton = new Button(context);
        mraidCloseButton.setId(301);
        mraidCloseButton.setContentDescription(MRAID_CLOSE_BUTTON_DESRIPTION);
        this.mraidCloseDrawable = new CloseTopDrawable(true, scale);
        mraidCloseButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MMLog.v(AdViewOverlayView.TAG, "Close button clicked.");
                AdViewOverlayView.this.finishOverlayWithAnimation();
            }
        });
        LayoutParams closeButParams = getCloseAreaParams(scale);
        mraidCloseButton.setLayoutParams(closeButParams);
        mraidCloseButton.post(new SetCloseButtonTouchDelegateRunnable(mraidCloseButton, closeButParams.topMargin, closeButParams.leftMargin, closeButParams.bottomMargin, closeButParams.rightMargin));
        return mraidCloseButton;
    }

    private LayoutParams getCloseAreaParams(float scale) {
        int closeHeight = (int) ((50.0f * scale) + 0.5f);
        LayoutParams closeButParams = new LayoutParams(closeHeight, closeHeight);
        closeButParams.addRule(11);
        closeButParams.addRule(10);
        return closeButParams;
    }

    private void animateView() {
        Animation animation;
        if (this.settings.getTransition().equals("slideup")) {
            animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, TextTrackStyle.DEFAULT_FONT_SCALE, 1, 0.0f);
            MMLog.v(TAG, "Translate up");
        } else if (this.settings.getTransition().equals("slidedown")) {
            animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, GroundOverlayOptions.NO_DIMENSION, 1, 0.0f);
            MMLog.v(TAG, "Translate down");
        } else if (this.settings.getTransition().equals("explode")) {
            Animation scaleAnimation = new ScaleAnimation(1.1f, 0.9f, 0.1f, 0.9f, 1, 0.5f, 1, 0.5f);
            MMLog.v(TAG, "Explode");
        } else {
            return;
        }
        animation.setDuration(this.settings.getTransitionDurationInMillis());
        startAnimation(animation);
    }

    void injectJS(String jsString) {
        if (this.adImpl.controller != null) {
            this.adImpl.controller.loadUrl(jsString);
        }
    }

    Object getNonConfigurationInstance() {
        NonConfigurationInstance nonConfigurationInstance = new NonConfigurationInstance();
        if (this.adImpl != null) {
            MMLog.d(TAG, "Saving getNonConfigurationInstance for " + this.adImpl);
            if (!(this.adImpl.controller == null || this.adImpl.controller.webView == null)) {
                this.adImpl.controller.webView.removeFromParent();
            }
            nonConfigurationInstance.controller = this.adImpl.controller;
        }
        nonConfigurationInstance.progressDone = this.progressDone;
        nonConfigurationInstance.settings = this.settings;
        return nonConfigurationInstance;
    }

    private void removeProgressBar() {
        if (!this.progressDone && this.progressBar != null) {
            this.progressDone = true;
            this.progressBar.setVisibility(8);
            removeView(this.progressBar);
            this.progressBar = null;
        }
    }

    void closeAreaTouched() {
        post(new Runnable() {
            public void run() {
                AdViewOverlayView.this.finishOverlayWithAnimation();
            }
        });
    }

    void finishOverlayWithAnimation() {
        MMLog.d(TAG, "Ad overlay closed");
        if (((Activity) getContext()) != null) {
            AlphaAnimation animation = new AlphaAnimation(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
            animation.setAnimationListener(new AnimationListener(this));
            animation.setFillEnabled(true);
            animation.setFillBefore(true);
            animation.setFillAfter(true);
            animation.setDuration(400);
            startAnimation(animation);
        }
    }

    boolean attachWebViewToLink() {
        if (this.adImpl == null || this.adImpl.linkForExpansionId == 0 || !MMAdImplController.attachWebViewFromOverlay(this.adImpl)) {
            return false;
        }
        return true;
    }

    void setUseCustomClose(boolean isHideDrawable) {
        this.settings.setUseCustomClose(isHideDrawable);
        this.mraidCloseButton.setBackgroundDrawable(isHideDrawable ? null : this.mraidCloseDrawable);
    }

    void addInlineVideo() {
        super.addInlineVideo();
        bringMraidCloseToFront();
    }

    void bringMraidCloseToFront() {
        if (this.mraidCloseButton != null) {
            this.mraidCloseButton.bringToFront();
        }
    }

    void repositionVideoLayout() {
        removeView(this.inlineVideoLayout);
        addView(this.inlineVideoLayout, this.inlineVideoView.getCustomLayoutParams());
        bringMraidCloseToFront();
    }

    void fullScreenVideoLayout() {
        removeView(this.inlineVideoLayout);
        addView(this.inlineVideoLayout, new LayoutParams(-1, -1));
        bringMraidCloseToFront();
    }

    void inlineConfigChange() {
        if (this.inlineVideoView != null && this.inlineVideoLayout != null) {
            this.inlineVideoLayout.setLayoutParams(this.inlineVideoView.getCustomLayoutParams());
            bringMraidCloseToFront();
        }
    }

    void getWebContent(String urlToLoad) {
        new FetchWebViewContentTask(this, urlToLoad).execute(new Void[0]);
    }

    void removeSelfAndAll() {
        removeAllViews();
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this);
        }
    }

    void killWebView() {
        BridgeMMSpeechkit.releaseSpeechKit();
        if (this.adImpl != null && this.adImpl.controller != null && this.adImpl.controller.webView != null) {
            this.adImpl.controller.webView.clearFocus();
            this.adImpl.controller.webView.setMraidViewableHidden();
            if (this.adImpl.adType == "i") {
                this.adImpl.controller.webView.setMraidHidden();
            }
            this.adImpl.controller.webView.onPauseWebView();
        }
    }
}
