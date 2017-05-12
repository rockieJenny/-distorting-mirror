package com.millennialmedia.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.wallet.WalletConstants;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

class CachedVideoPlayerActivity extends VideoPlayerActivity implements Callback {
    private static final int STATIC_HUD_ID = 402;
    private static final int STATIC_HUD_SECONDS_ID = 401;
    private static final String TAG = "CachedVideoPlayerActivity";
    private RelativeLayout buttonsLayout;
    private boolean hasCountdownHud = true;
    boolean hasLoadedCompletionUrl;
    boolean hasWebOverlay;
    private TextView hudSeconds;
    private TextView hudStaticText;
    private int lastVideoPosition;
    private Handler logTimeAndEventhandler;
    private MMWebView mWebView;
    private VideoAd videoAd;

    class FetchWebViewContentTask extends AsyncTask<Void, Void, String> {
        private String baseUrl;
        private boolean cancelVideo;

        public FetchWebViewContentTask(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        protected void onPostExecute(String webContent) {
            if (this.cancelVideo) {
                CachedVideoPlayerActivity.this.dismiss();
            }
            if (webContent != null && CachedVideoPlayerActivity.this.mWebView != null) {
                CachedVideoPlayerActivity.this.mWebView.setWebViewContent(webContent, this.baseUrl, CachedVideoPlayerActivity.this.activity);
                CachedVideoPlayerActivity.this.hasLoadedCompletionUrl = true;
            }
        }

        protected String doInBackground(Void... arg0) {
            try {
                HttpResponse httpResponse = new HttpGetRequest().get(this.baseUrl);
                StatusLine statusLine = httpResponse.getStatusLine();
                if (httpResponse == null || statusLine == null || statusLine.getStatusCode() == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
                    this.cancelVideo = true;
                    return null;
                }
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    return HttpGetRequest.convertStreamToString(httpEntity.getContent());
                }
                return null;
            } catch (Exception e) {
                MMLog.e(CachedVideoPlayerActivity.TAG, "Error with http web overlay", e);
            }
        }
    }

    private static class CachedVideoWebViewClientListener extends MMWebViewClientListener {
        WeakReference<CachedVideoPlayerActivity> activityRef;

        CachedVideoWebViewClientListener(CachedVideoPlayerActivity activity) {
            this.activityRef = new WeakReference(activity);
        }

        public void onPageFinished(String url) {
            MMLog.d(CachedVideoPlayerActivity.TAG, "@@ ON PAGE FINISHED" + url);
            CachedVideoPlayerActivity activity = (CachedVideoPlayerActivity) this.activityRef.get();
            if (activity != null) {
                activity.onPageFinished(url);
            }
        }
    }

    CachedVideoPlayerActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MMLog.v(TAG, "Is Cached Ad");
    }

    protected void initSavedInstance(Bundle savedInstanceState) {
        super.initSavedInstance(savedInstanceState);
        if (savedInstanceState == null) {
            this.videoAd = (VideoAd) AdCache.load(this.activity, getIntent().getStringExtra("videoId"));
            if (this.videoAd != null) {
                this.hasBottomBar = this.videoAd.showControls;
                this.hasCountdownHud = this.videoAd.showCountdown;
                return;
            }
            return;
        }
        this.videoAd = (VideoAd) savedInstanceState.getParcelable("videoAd");
        this.hasBottomBar = savedInstanceState.getBoolean("shouldShowBottomBar");
        this.lastVideoPosition = savedInstanceState.getInt("lastVideoPosition");
        this.currentVideoPosition = savedInstanceState.getInt("currentVideoPosition");
        this.hasCountdownHud = this.videoAd.showCountdown;
    }

    public void onCompletion(MediaPlayer mp) {
        super.onCompletion(mp);
        if (this.hasCountdownHud) {
            hideHud();
        }
        if (this.videoAd != null) {
            videoPlayerOnCompletion(this.videoAd.onCompletionUrl);
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        errorPlayVideo(String.format("Error while playing, %d - %d", new Object[]{Integer.valueOf(what), Integer.valueOf(extra)}));
        return super.onError(mp, what, extra);
    }

    protected void errorPlayVideo(String error) {
        if (this.videoAd != null) {
            HttpGetRequest.log(this.videoAd.videoError);
        }
        if (this.mWebView != null) {
            this.mWebView.loadUrl("javascript:MMJS.cachedVideo.setError(" + error + ");");
        }
        super.errorPlayVideo(error);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("videoAd", this.videoAd);
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.videoAd = (VideoAd) savedInstanceState.getParcelable("videoAd");
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void videoPlayerOnCompletion(String url) {
        if (url != null) {
            dispatchButtonClick(url);
        }
        if (this.videoAd != null) {
            this.videoAd.logEndEvent();
            if (this.videoAd.stayInPlayer) {
                if (!(this.hasWebOverlay || this.videoAd.buttons == null)) {
                    fadeButtons();
                    if (!this.videoAd.hasEndCard()) {
                        dismiss();
                    }
                }
                if (this.mWebView != null && !TextUtils.isEmpty(this.videoAd.endOverlayURL)) {
                    loadUrlForMraidInjection(this.videoAd.endOverlayURL);
                    this.mWebView.bringToFront();
                } else if (this.hasWebOverlay) {
                    dismiss();
                }
                if (this.videoAd.closeDelayMillis != 0) {
                    dismissAfter(this.videoAd.closeDelayMillis);
                }
                this.logTimeAndEventhandler.removeMessages(1);
                this.logTimeAndEventhandler.removeMessages(2);
                this.logTimeAndEventhandler.removeMessages(3);
                return;
            }
            dismiss();
        }
    }

    private void dismissAfter(long delayMillis) {
        this.logTimeAndEventhandler.postDelayed(new Runnable() {
            public void run() {
                CachedVideoPlayerActivity.this.dismiss();
            }
        }, delayMillis);
    }

    protected void onStart() {
        super.onStart();
        if (!this.hasWebOverlay && this.videoAd != null && this.videoAd.stayInPlayer && this.isVideoCompleted) {
            fadeButtons();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.mWebView != null) {
            this.mWebView.bringToFront();
        }
        if (this.buttonsLayout != null) {
            this.buttonsLayout.bringToFront();
        }
    }

    private void fadeButtons() {
        if (this.videoAd != null && this.videoAd.buttons != null) {
            for (int i = 0; i < this.videoAd.buttons.size(); i++) {
                VideoImage videoImage = (VideoImage) this.videoAd.buttons.get(i);
                if (!videoImage.isLeaveBehind) {
                    setButtonAlpha(videoImage.button, videoImage.fromAlpha);
                }
                if (videoImage.button.getParent() == null) {
                    this.buttonsLayout.addView(videoImage.button, videoImage.layoutParams);
                }
                for (int j = 0; j < this.videoAd.buttons.size(); j++) {
                    this.buttonsLayout.bringChildToFront(((VideoImage) this.videoAd.buttons.get(j)).button);
                }
                MMLog.v(TAG, String.format("Button: %d alpha: %f", new Object[]{Integer.valueOf(i), Float.valueOf(videoImage.fromAlpha)}));
            }
        }
    }

    protected RelativeLayout initLayout() {
        RelativeLayout parent = super.initLayout();
        this.logTimeAndEventhandler = new Handler(this);
        setRequestedOrientation(0);
        if (this.hasCountdownHud) {
            initHudStaticText(parent);
            initHudSeconds(parent);
            showHud();
        }
        if (this.videoAd == null || this.videoAd.webOverlayURL == null) {
            this.hasWebOverlay = false;
            this.buttonsLayout = new RelativeLayout(this.activity);
            this.buttonsLayout.setId(1000);
            List<VideoImage> buttons = null;
            if (this.videoAd != null) {
                buttons = this.videoAd.buttons;
            }
            if (buttons != null) {
                File adDir = AdCache.getInternalCacheDirectory(this.activity);
                for (int i = 0; i < buttons.size(); i++) {
                    LayoutParams newButtonLp;
                    final VideoImage button = (VideoImage) buttons.get(i);
                    final ImageButton newButton = new ImageButton(this.activity);
                    button.button = newButton;
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(adDir.getAbsolutePath() + File.separator + this.videoAd.getId() + Uri.parse(button.imageUrl).getLastPathSegment().replaceFirst("\\.[^\\.]*$", ".dat"));
                        if (bitmap != null) {
                            newButton.setImageBitmap(bitmap);
                        } else {
                            newButton.setImageURI(Uri.parse(adDir.getAbsolutePath() + File.separator + this.videoAd.getId() + Uri.parse(button.imageUrl).getLastPathSegment().replaceFirst("\\.[^\\.]*$", ".dat")));
                        }
                    } catch (Exception e) {
                        MMLog.e(TAG, "Problem creating layout with bitmap buttons: ", e);
                    }
                    setButtonAlpha(newButton, button.fromAlpha);
                    newButton.setId(i + 1);
                    newButton.setPadding(0, 0, 0, 0);
                    if (button.isLeaveBehind) {
                        newButton.setScaleType(ScaleType.FIT_CENTER);
                        newButton.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                        newButtonLp = new LayoutParams(-1, -1);
                    } else {
                        newButton.setBackgroundColor(0);
                        newButtonLp = new LayoutParams(-2, -2);
                        MMLog.v(TAG, String.format("Button: %d Anchor: %d Position: %d Anchor2: %d Position2: %d", new Object[]{Integer.valueOf(newButton.getId()), Integer.valueOf(button.anchor), Integer.valueOf(button.position), Integer.valueOf(button.anchor2), Integer.valueOf(button.position2)}));
                        newButtonLp.addRule(button.position, button.anchor);
                        newButtonLp.addRule(button.position2, button.anchor2);
                        newButtonLp.setMargins(button.paddingLeft, button.paddingTop, button.paddingRight, button.paddingBottom);
                    }
                    if (!TextUtils.isEmpty(button.linkUrl)) {
                        newButton.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                if (newButton != null) {
                                    newButton.setEnabled(false);
                                }
                                CachedVideoPlayerActivity.this.redirectListenerImpl.orientation = button.overlayOrientation;
                                CachedVideoPlayerActivity.this.dispatchButtonClick(button.linkUrl);
                                CachedVideoPlayerActivity.this.logButtonEvent(button);
                            }
                        });
                    }
                    if (button.appearanceDelay > 0) {
                        button.layoutParams = newButtonLp;
                        this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 3, button), button.appearanceDelay);
                    } else {
                        this.buttonsLayout.addView(newButton, newButtonLp);
                    }
                    if (button.inactivityTimeout > 0) {
                        this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 1, button), (button.inactivityTimeout + button.appearanceDelay) + button.fadeDuration);
                    }
                }
                parent.addView(this.buttonsLayout, new LayoutParams(-1, -1));
            }
            if (this.buttonsLayout != null) {
                parent.bringChildToFront(this.buttonsLayout);
            }
            if (this.mWebView != null) {
                parent.bringChildToFront(this.mWebView);
            }
        } else {
            initWebOverlay();
            if (this.mWebView != null) {
                parent.addView(this.mWebView);
                this.hasWebOverlay = true;
            }
        }
        return parent;
    }

    private void onPageFinished(String url) {
        if (this.mVideoView != null && this.videoAd != null && !this.mVideoView.isPlaying() && this.videoAd.webOverlayURL != null && url.equalsIgnoreCase(this.videoAd.webOverlayURL)) {
            playVideo(0);
        }
    }

    private void initWebOverlay() {
        this.mWebView = new MMWebView(this.activity, this.activity.creatorAdImplInternalId);
        this.mWebView.setId(WalletConstants.ERROR_CODE_UNKNOWN);
        this.mWebView.setWebViewClient(new InterstitialWebViewClient(new CachedVideoWebViewClientListener(this), this.redirectListenerImpl));
        LayoutParams webLayoutParams = new LayoutParams(-1, -1);
        webLayoutParams.addRule(13);
        this.mWebView.setLayoutParams(webLayoutParams);
        this.mWebView.setBackgroundColor(0);
        loadUrlForMraidInjection(this.videoAd.webOverlayURL);
    }

    void loadUrlForMraidInjection(String url) {
        new FetchWebViewContentTask(url).execute(new Void[0]);
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                if (canFadeButtons()) {
                    fadeButton((VideoImage) msg.obj);
                    break;
                }
                break;
            case 2:
                try {
                    if (this.mVideoView != null && this.mVideoView.isPlaying()) {
                        int currentPosition = this.mVideoView.getCurrentPosition();
                        if (currentPosition > this.lastVideoPosition) {
                            if (this.videoAd != null) {
                                if (this.lastVideoPosition == 0) {
                                    this.videoAd.logBeginEvent();
                                }
                                for (int i = 0; i < this.videoAd.activities.size(); i++) {
                                    VideoLogEvent videoEvent = (VideoLogEvent) this.videoAd.activities.get(i);
                                    if (videoEvent != null && videoEvent.position >= ((long) this.lastVideoPosition) && videoEvent.position < ((long) currentPosition)) {
                                        for (String logEvent : videoEvent.activities) {
                                            Event.logEvent(logEvent);
                                        }
                                    }
                                }
                            }
                            this.lastVideoPosition = currentPosition;
                        }
                        if (this.hasWebOverlay && this.mWebView != null) {
                            this.mWebView.loadUrl("javascript:MMJS.cachedVideo.updateVideoSeekTime(" + ((float) Math.floor((double) (((float) currentPosition) / 1000.0f))) + ");");
                        }
                        if (this.hasCountdownHud) {
                            long seconds = (this.videoAd.duration - ((long) currentPosition)) / 1000;
                            if (seconds <= 0) {
                                hideHud();
                            } else if (this.hudSeconds != null) {
                                this.hudSeconds.setText(String.valueOf(seconds));
                            }
                        }
                    }
                    this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 2), 500);
                    break;
                } catch (IllegalStateException e) {
                    MMLog.e(TAG, "Error with video check", e);
                    break;
                }
            case 3:
                VideoImage image = msg.obj;
                try {
                    if (this.buttonsLayout.indexOfChild(image.button) == -1) {
                        this.buttonsLayout.addView(image.button, image.layoutParams);
                    }
                } catch (IllegalStateException e2) {
                    MMLog.e(TAG, "Problem adding buttons", e2);
                }
                if (canFadeButtons()) {
                    fadeButton(image);
                    MMLog.v(TAG, String.format("Beginning animation to visibility. Fade duration: %d Button: %d Time: %d", new Object[]{Long.valueOf(image.fadeDuration), Integer.valueOf(image.button.getId()), Long.valueOf(System.currentTimeMillis())}));
                    break;
                }
                break;
        }
        return true;
    }

    private void fadeButton(VideoImage image) {
        VideoImage videoImage = image;
        AlphaAnimation animation = new AlphaAnimation(videoImage.fromAlpha, videoImage.toAlpha);
        animation.setDuration(videoImage.fadeDuration);
        animation.setFillEnabled(true);
        animation.setFillBefore(true);
        animation.setFillAfter(true);
        videoImage.button.startAnimation(animation);
    }

    protected void pauseVideo() {
        super.pauseVideo();
        this.logTimeAndEventhandler.removeMessages(1);
        this.logTimeAndEventhandler.removeMessages(2);
        this.logTimeAndEventhandler.removeMessages(3);
    }

    protected void resumeVideo() {
        if (this.videoAd != null) {
            if (!this.logTimeAndEventhandler.hasMessages(2)) {
                this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 2), 1000);
            }
            if (!this.hasWebOverlay) {
                if (this.hasCountdownHud) {
                    long seconds = (this.videoAd.duration - ((long) this.currentVideoPosition)) / 1000;
                    if (seconds <= 0) {
                        hideHud();
                    } else if (this.hudSeconds != null) {
                        this.hudSeconds.setText(String.valueOf(seconds));
                    }
                }
                if (this.videoAd.buttons != null) {
                    for (int i = 0; i < this.videoAd.buttons.size(); i++) {
                        VideoImage button = (VideoImage) this.videoAd.buttons.get(i);
                        long delay = 0;
                        if (button.appearanceDelay > 0 && this.buttonsLayout.indexOfChild(button.button) == -1) {
                            Message message = Message.obtain(this.logTimeAndEventhandler, 3, button);
                            delay = button.appearanceDelay - ((long) this.currentVideoPosition);
                            if (delay < 0) {
                                delay = 500;
                            }
                            this.logTimeAndEventhandler.sendMessageDelayed(message, delay);
                        }
                        if (button.inactivityTimeout > 0) {
                            this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 1, button), (button.inactivityTimeout + delay) + button.fadeDuration);
                        }
                    }
                }
            }
        }
        super.resumeVideo();
    }

    protected void playVideo(int position) {
        if (this.videoAd == null) {
            Toast.makeText(this.activity, "Sorry. There was a problem playing the video", 1).show();
            return;
        }
        if (!(this.logTimeAndEventhandler.hasMessages(2) || this.videoAd == null)) {
            this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 2), 1000);
        }
        super.playVideo(position);
    }

    protected void restartVideo() {
        if (this.videoAd != null) {
            if (!((!this.hasLoadedCompletionUrl && !this.videoAd.reloadNonEndOverlayOnRestart) || this.videoAd.webOverlayURL == null || this.mWebView == null)) {
                loadUrlForMraidInjection(this.videoAd.webOverlayURL);
                this.hasLoadedCompletionUrl = false;
            }
            List<VideoImage> buttons = this.videoAd.buttons;
            this.logTimeAndEventhandler.removeMessages(1);
            this.logTimeAndEventhandler.removeMessages(2);
            this.logTimeAndEventhandler.removeMessages(3);
            this.lastVideoPosition = 0;
            if (!(this.hasWebOverlay || this.buttonsLayout == null || buttons == null)) {
                for (int i = 0; i < buttons.size(); i++) {
                    VideoImage buttonData = (VideoImage) buttons.get(i);
                    if (buttonData != null) {
                        if (buttonData.appearanceDelay > 0) {
                            this.buttonsLayout.removeView(buttonData.button);
                            this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 3, buttonData), buttonData.appearanceDelay);
                        }
                        if (buttonData.inactivityTimeout > 0) {
                            this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 1, buttonData), (buttonData.inactivityTimeout + buttonData.appearanceDelay) + buttonData.fadeDuration);
                        }
                    }
                }
            }
            if (this.logTimeAndEventhandler != null) {
                this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 2), 1000);
            }
            if (this.hasCountdownHud) {
                showHud();
            }
        }
        super.restartVideo();
    }

    private void showHud() {
        if (this.videoAd != null) {
            this.hudSeconds.setText(String.valueOf(this.videoAd.duration / 1000));
        }
        this.hudStaticText.setVisibility(0);
        this.hudSeconds.setVisibility(0);
    }

    private void initHudStaticText(ViewGroup parent) {
        this.hudStaticText = new TextView(this.activity);
        this.hudStaticText.setText(" seconds remaining ...");
        this.hudStaticText.setTextColor(-1);
        this.hudStaticText.setPadding(0, 0, 5, 0);
        this.hudStaticText.setId(402);
        this.hudStaticText.setShadowLayer(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        LayoutParams hudLp = new LayoutParams(-2, -2);
        hudLp.addRule(10);
        hudLp.addRule(11);
        parent.addView(this.hudStaticText, hudLp);
    }

    private void initHudSeconds(ViewGroup parent) {
        this.hudSeconds = new TextView(this.activity);
        this.hudSeconds.setText(calculateHudSecondsText());
        this.hudSeconds.setTextColor(-1);
        this.hudSeconds.setId(STATIC_HUD_SECONDS_ID);
        this.hudSeconds.setShadowLayer(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        LayoutParams hudSecLp = new LayoutParams(-2, -2);
        hudSecLp.addRule(10);
        hudSecLp.addRule(0, 402);
        parent.addView(this.hudSeconds, hudSecLp);
    }

    private String calculateHudSecondsText() {
        if (this.currentVideoPosition != 0) {
            return String.valueOf(this.currentVideoPosition / 1000);
        }
        if (this.videoAd != null) {
            return String.valueOf(this.videoAd.duration / 1000);
        }
        return "";
    }

    private void hideHud() {
        if (this.hudStaticText != null) {
            this.hudStaticText.setVisibility(4);
        }
        if (this.hudSeconds != null) {
            this.hudSeconds.setVisibility(4);
        }
    }

    protected void enableButtons() {
        super.enableButtons();
        if (!this.hasWebOverlay && this.videoAd != null && this.videoAd.buttons != null) {
            Iterator i$ = this.videoAd.buttons.iterator();
            while (i$.hasNext()) {
                VideoImage image = (VideoImage) i$.next();
                if (image.button != null) {
                    image.button.setEnabled(true);
                }
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.videoAd != null) {
            if (this.logTimeAndEventhandler != null) {
                this.logTimeAndEventhandler.removeMessages(1);
            }
            if (!this.hasWebOverlay) {
                for (int i = 0; i < this.videoAd.buttons.size(); i++) {
                    VideoImage videoImage = (VideoImage) this.videoAd.buttons.get(i);
                    setButtonAlpha(videoImage.button, videoImage.fromAlpha);
                    if (videoImage.inactivityTimeout > 0) {
                        this.logTimeAndEventhandler.sendMessageDelayed(Message.obtain(this.logTimeAndEventhandler, 1, videoImage), videoImage.inactivityTimeout);
                    } else if (ev.getAction() == 1) {
                        if (canFadeButtons()) {
                            AlphaAnimation animation = new AlphaAnimation(videoImage.fromAlpha, videoImage.toAlpha);
                            animation.setDuration(videoImage.fadeDuration);
                            animation.setFillEnabled(true);
                            animation.setFillBefore(true);
                            animation.setFillAfter(true);
                            videoImage.button.startAnimation(animation);
                        }
                    } else if (ev.getAction() == 0) {
                        setButtonAlpha(videoImage.button, videoImage.fromAlpha);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean canFadeButtons() {
        return (this.videoAd.stayInPlayer && super.canFadeButtons()) ? false : true;
    }
}
