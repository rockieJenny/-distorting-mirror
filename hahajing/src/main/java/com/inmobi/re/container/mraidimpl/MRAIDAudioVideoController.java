package com.inmobi.re.container.mraidimpl;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.WrapperFunctions;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.controller.JSController.Dimensions;
import com.inmobi.re.controller.JSController.PlayerProperties;
import com.inmobi.re.controller.util.AVPlayer;
import com.inmobi.re.controller.util.AVPlayer.playerState;
import com.inmobi.re.controller.util.AVPlayerListener;
import com.inmobi.re.controller.util.Constants;
import java.util.Hashtable;
import java.util.Map.Entry;

public class MRAIDAudioVideoController {
    private IMWebView a;
    public Hashtable<String, AVPlayer> audioPlayerList = new Hashtable();
    public AVPlayer audioplayer;
    public AVPlayer videoPlayer;
    public Hashtable<String, AVPlayer> videoPlayerList = new Hashtable();
    public int videoValidateWidth;

    public MRAIDAudioVideoController(IMWebView iMWebView) {
        this.a = iMWebView;
    }

    public void playAudioImpl(Bundle bundle, Activity activity) {
        final PlayerProperties playerProperties = (PlayerProperties) bundle.getParcelable(IMWebView.PLAYER_PROPERTIES);
        String string = bundle.getString(IMWebView.EXPAND_URL);
        if (string == null) {
            string = "";
        }
        if (!a(playerProperties.id, string, activity)) {
            return;
        }
        if ((string.length() != 0 && !URLUtil.isValidUrl(string)) || (string.length() == 0 && !this.audioPlayerList.containsKey(playerProperties.id))) {
            this.a.raiseError("Request must specify a valid URL", "playAudio");
        } else if (this.audioplayer != null) {
            if (string.length() != 0) {
                this.audioplayer.setPlayData(playerProperties, string);
            }
            this.audioPlayerList.put(playerProperties.id, this.audioplayer);
            FrameLayout frameLayout = (FrameLayout) activity.findViewById(16908290);
            if (playerProperties.isFullScreen()) {
                LayoutParams layoutParams = new RelativeLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent());
                layoutParams.addRule(13);
                this.audioplayer.setLayoutParams(layoutParams);
                View relativeLayout = new RelativeLayout(activity);
                relativeLayout.setOnTouchListener(new OnTouchListener(this) {
                    final /* synthetic */ MRAIDAudioVideoController a;

                    {
                        this.a = r1;
                    }

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                relativeLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                frameLayout.addView(relativeLayout, new RelativeLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent()));
                relativeLayout.addView(this.audioplayer);
                this.audioplayer.setBackGroundLayout(relativeLayout);
                this.audioplayer.requestFocus();
                this.audioplayer.setOnKeyListener(new OnKeyListener(this) {
                    final /* synthetic */ MRAIDAudioVideoController a;

                    {
                        this.a = r1;
                    }

                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (4 == keyEvent.getKeyCode() && keyEvent.getAction() == 0) {
                            Log.debug(Constants.RENDERING_LOG_TAG, "Back button pressed while fullscreen audio was playing");
                            this.a.audioplayer.releasePlayer(true);
                        }
                        return false;
                    }
                });
            } else {
                this.audioplayer.setLayoutParams(new LayoutParams(1, 1));
                frameLayout.addView(this.audioplayer);
            }
            this.audioplayer.setListener(new AVPlayerListener(this) {
                final /* synthetic */ MRAIDAudioVideoController b;

                public void onPrepared(AVPlayer aVPlayer) {
                }

                public void onError(AVPlayer aVPlayer) {
                    onComplete(aVPlayer);
                }

                public void onComplete(AVPlayer aVPlayer) {
                    try {
                        ViewGroup viewGroup;
                        if (playerProperties.isFullScreen()) {
                            viewGroup = (ViewGroup) aVPlayer.getBackGroundLayout().getParent();
                            if (viewGroup != null) {
                                viewGroup.removeView(aVPlayer.getBackGroundLayout());
                                return;
                            }
                            return;
                        }
                        viewGroup = (ViewGroup) aVPlayer.getParent();
                        if (viewGroup != null) {
                            viewGroup.removeView(aVPlayer);
                        }
                    } catch (Throwable e) {
                        Log.debug(Constants.RENDERING_LOG_TAG, "Problem removing the audio relativelayout", e);
                    }
                }
            });
            this.audioplayer.play();
        }
    }

    private boolean a(String str, String str2, Activity activity) {
        if (this.audioPlayerList.isEmpty()) {
            this.audioplayer = new AVPlayer(activity, this.a);
        } else {
            this.audioplayer = (AVPlayer) this.audioPlayerList.get(str);
            if (this.audioplayer == null) {
                if (this.audioPlayerList.size() > 4) {
                    this.a.raiseError("Too many audio players", "playAudio");
                    return false;
                }
                this.audioplayer = new AVPlayer(activity, this.a);
            } else if (!this.audioplayer.getMediaURL().equals(str2) && str2.length() != 0) {
                this.audioplayer.releasePlayer(false);
                this.audioPlayerList.remove(str);
                this.audioplayer = new AVPlayer(activity, this.a);
            } else if (this.audioplayer.getState() == playerState.PLAYING) {
                return false;
            } else {
                if (this.audioplayer.getState() == playerState.INIT) {
                    if (this.audioplayer.isPrepared()) {
                        this.audioplayer.start();
                    } else {
                        this.audioplayer.setAutoPlay(true);
                    }
                    return false;
                } else if (this.audioplayer.getState() == playerState.PAUSED) {
                    this.audioplayer.start();
                    return false;
                } else {
                    PlayerProperties properties = this.audioplayer.getProperties();
                    String mediaURL = this.audioplayer.getMediaURL();
                    this.audioplayer.releasePlayer(false);
                    this.audioPlayerList.remove(str);
                    this.audioplayer = new AVPlayer(activity, this.a);
                    this.audioplayer.setPlayData(properties, mediaURL);
                }
            }
        }
        return true;
    }

    public synchronized AVPlayer getCurrentAudioPlayer(String str) {
        AVPlayer aVPlayer;
        aVPlayer = null;
        if (this.audioplayer != null && this.audioplayer.getPropertyID().equalsIgnoreCase(str)) {
            aVPlayer = this.audioplayer;
        } else if (!this.audioPlayerList.isEmpty() && this.audioPlayerList.containsKey(str)) {
            aVPlayer = (AVPlayer) this.audioPlayerList.get(str);
        }
        return aVPlayer;
    }

    public void playVideoImpl(Bundle bundle, Activity activity) {
        PlayerProperties playerProperties = (PlayerProperties) bundle.getParcelable(IMWebView.PLAYER_PROPERTIES);
        Dimensions dimensions = (Dimensions) bundle.getParcelable(IMWebView.DIMENSIONS);
        Log.debug(Constants.RENDERING_LOG_TAG, "Final dimensions: " + dimensions);
        String string = bundle.getString(IMWebView.EXPAND_URL);
        if (a(playerProperties.id, string, activity, dimensions)) {
            PlayerProperties playerProperties2;
            this.a.setBusy(true);
            if (string.length() == 0) {
                playerProperties = this.videoPlayer.getProperties();
                dimensions = this.videoPlayer.getPlayDimensions();
                this.videoPlayer.getMediaURL();
                playerProperties2 = playerProperties;
            } else {
                this.videoPlayer.setPlayData(playerProperties, string);
                this.videoPlayer.setPlayDimensions(dimensions);
                playerProperties2 = playerProperties;
            }
            if (this.videoPlayer.getState() == playerState.HIDDEN) {
                this.videoPlayer.pseudoPause = true;
                this.videoPlayer.show();
                return;
            }
            FrameLayout frameLayout = (FrameLayout) activity.findViewById(16908290);
            if (playerProperties2.isFullScreen()) {
                LayoutParams layoutParams = new RelativeLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent());
                layoutParams.addRule(13);
                this.videoPlayer.setLayoutParams(layoutParams);
                View relativeLayout = new RelativeLayout(activity);
                relativeLayout.setOnTouchListener(new OnTouchListener(this) {
                    final /* synthetic */ MRAIDAudioVideoController a;

                    {
                        this.a = r1;
                    }

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                relativeLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                frameLayout.addView(relativeLayout, new FrameLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent()));
                relativeLayout.addView(this.videoPlayer);
                this.videoPlayer.setBackGroundLayout(relativeLayout);
                this.videoPlayer.requestFocus();
                this.videoPlayer.setOnKeyListener(new OnKeyListener(this) {
                    final /* synthetic */ MRAIDAudioVideoController a;

                    {
                        this.a = r1;
                    }

                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (4 != keyEvent.getKeyCode() || keyEvent.getAction() != 0) {
                            return false;
                        }
                        Log.debug(Constants.RENDERING_LOG_TAG, "Back pressed while fullscreen video is playing");
                        this.a.videoPlayer.releasePlayer(true);
                        return true;
                    }
                });
            } else {
                LayoutParams layoutParams2 = new FrameLayout.LayoutParams(dimensions.width, dimensions.height);
                View frameLayout2 = new FrameLayout(activity);
                if (this.a.mExpandController.expandProperties == null) {
                    layoutParams2.leftMargin = dimensions.x;
                    layoutParams2.topMargin = dimensions.y;
                } else {
                    layoutParams2.leftMargin = dimensions.x + this.a.mExpandController.expandProperties.currentX;
                    layoutParams2.topMargin = dimensions.y + this.a.mExpandController.expandProperties.currentY;
                }
                layoutParams2.gravity = 3;
                this.videoPlayer.setLayoutParams(layoutParams2);
                frameLayout.addView(frameLayout2, new FrameLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent()));
                this.videoPlayer.setBackGroundLayout(frameLayout2);
                frameLayout2.addView(this.videoPlayer);
            }
            this.videoPlayer.setListener(new AVPlayerListener(this) {
                final /* synthetic */ MRAIDAudioVideoController a;

                {
                    this.a = r1;
                }

                public void onPrepared(AVPlayer aVPlayer) {
                }

                public void onError(AVPlayer aVPlayer) {
                    onComplete(aVPlayer);
                }

                public void onComplete(AVPlayer aVPlayer) {
                    this.a.a.setBusy(false);
                    try {
                        ViewGroup backGroundLayout = aVPlayer.getBackGroundLayout();
                        if (backGroundLayout != null) {
                            ((ViewGroup) backGroundLayout.getParent()).removeView(aVPlayer.getBackGroundLayout());
                        }
                        aVPlayer.setBackGroundLayout(null);
                    } catch (Throwable e) {
                        Log.debug(Constants.RENDERING_LOG_TAG, "Problem removing the video framelayout or relativelayout depending on video startstyle", e);
                    }
                    synchronized (this) {
                        if (this.a.videoPlayer != null && aVPlayer.getPropertyID().equalsIgnoreCase(this.a.videoPlayer.getPropertyID())) {
                            this.a.videoPlayer = null;
                        }
                    }
                }
            });
            this.videoPlayer.play();
        }
    }

    public void validateVideoDimensions(Dimensions dimensions) {
        dimensions.width = (int) (((float) dimensions.width) * this.a.getDensity());
        dimensions.height = (int) (((float) dimensions.height) * this.a.getDensity());
        dimensions.x = (int) (((float) dimensions.x) * this.a.getDensity());
        dimensions.y = (int) (((float) dimensions.y) * this.a.getDensity());
    }

    private boolean a(String str, String str2, Activity activity, Dimensions dimensions) {
        if (this.videoPlayer == null || !str.equalsIgnoreCase(this.videoPlayer.getPropertyID())) {
            return b(str, str2, activity);
        }
        playerState state = this.videoPlayer.getState();
        if (str.equalsIgnoreCase(this.videoPlayer.getPropertyID())) {
            String mediaURL = this.videoPlayer.getMediaURL();
            if (str2.length() == 0 || str2.equalsIgnoreCase(mediaURL)) {
                switch (state) {
                    case PAUSED:
                        this.videoPlayer.start();
                        a(this.videoPlayer, dimensions);
                        return false;
                    case PLAYING:
                        a(this.videoPlayer, dimensions);
                        return false;
                    case COMPLETED:
                        if (!this.videoPlayer.getProperties().doLoop()) {
                            this.videoPlayer.start();
                        }
                        a(this.videoPlayer, dimensions);
                        return false;
                    case INIT:
                        if (this.videoPlayer.isPrepared()) {
                            this.videoPlayer.start();
                        } else {
                            this.videoPlayer.setAutoPlay(true);
                        }
                        a(this.videoPlayer, dimensions);
                        return false;
                    default:
                        return false;
                }
            } else if (URLUtil.isValidUrl(str2)) {
                this.videoPlayer.releasePlayer(false);
                this.videoPlayer = new AVPlayer(activity, this.a);
            } else {
                this.a.raiseError("Request must specify a valid URL", "playVideo");
                return false;
            }
        }
        return true;
    }

    private void a(AVPlayer aVPlayer, Dimensions dimensions) {
        int density = (int) (-99999.0f * this.a.getDensity());
        if (aVPlayer.isInlineVideo()) {
            LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensions.width, dimensions.height);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) aVPlayer.getLayoutParams();
            if (dimensions.x == density && dimensions.y == density) {
                layoutParams.leftMargin = layoutParams2.leftMargin;
                layoutParams.topMargin = layoutParams2.topMargin;
            } else {
                layoutParams.leftMargin = dimensions.x;
                layoutParams.topMargin = dimensions.y;
            }
            layoutParams.gravity = 3;
            aVPlayer.setLayoutParams(layoutParams);
        }
    }

    private boolean b(String str, String str2, Activity activity) {
        if ((str2.length() == 0 || URLUtil.isValidUrl(str2)) && (str2.length() != 0 || this.videoPlayerList.containsKey(str))) {
            if (this.videoPlayer != null) {
                this.videoPlayer.hide();
                this.videoPlayerList.put(this.videoPlayer.getPropertyID(), this.videoPlayer);
            }
            AVPlayer a = a(str);
            if (a == null) {
                this.videoPlayer = new AVPlayer(activity, this.a);
            } else {
                this.videoPlayer = a;
            }
            if (str2.length() == 0) {
                this.videoPlayer.setPlayData(a.getProperties(), a.getMediaURL());
                this.videoPlayer.setPlayDimensions(a.getPlayDimensions());
            }
            this.videoPlayerList.remove(str);
            return true;
        }
        this.a.raiseError("Request must specify a valid URL", "playVideo");
        return false;
    }

    public synchronized AVPlayer getVideoPlayer(String str) {
        AVPlayer aVPlayer;
        aVPlayer = null;
        if (this.videoPlayer != null && this.videoPlayer.getPropertyID().equalsIgnoreCase(str)) {
            aVPlayer = this.videoPlayer;
        } else if (!this.videoPlayerList.isEmpty() && this.videoPlayerList.containsKey(str)) {
            aVPlayer = (AVPlayer) this.videoPlayerList.get(str);
        }
        return aVPlayer;
    }

    private AVPlayer a(String str) {
        if (this.videoPlayerList.isEmpty()) {
            return null;
        }
        return (AVPlayer) this.videoPlayerList.get(str);
    }

    public void releaseAllPlayers() {
        if (this.videoPlayer != null) {
            this.videoPlayerList.put(this.videoPlayer.getPropertyID(), this.videoPlayer);
        }
        try {
            for (Object obj : this.videoPlayerList.values().toArray()) {
                try {
                    ((AVPlayer) obj).releasePlayer(IMWebView.userInitiatedClose);
                } catch (Exception e) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "Unable to release player");
                }
            }
        } catch (Throwable e2) {
            Log.internal(Constants.RENDERING_LOG_TAG, "IMwebview release all players ", e2);
        }
        this.videoPlayerList.clear();
        this.videoPlayer = null;
        try {
            for (Object obj2 : this.audioPlayerList.values().toArray()) {
                try {
                    ((AVPlayer) obj2).releasePlayer(IMWebView.userInitiatedClose);
                } catch (Exception e3) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "Unable to release player");
                }
            }
        } catch (Throwable e22) {
            Log.internal(Constants.RENDERING_LOG_TAG, "IMwebview release all players ", e22);
        }
        IMWebView.userInitiatedClose = false;
        this.audioPlayerList.clear();
        this.audioplayer = null;
    }

    public void hidePlayers() {
        Log.debug(Constants.RENDERING_LOG_TAG, "MRAIDAudioVideoController: hiding all players");
        if (!(this.videoPlayer == null || this.videoPlayer.getState() == playerState.RELEASED)) {
            this.videoPlayerList.put(this.videoPlayer.getPropertyID(), this.videoPlayer);
            this.videoPlayer.hide();
        }
        for (Entry entry : this.audioPlayerList.entrySet()) {
            AVPlayer aVPlayer = (AVPlayer) entry.getValue();
            switch (aVPlayer.getState()) {
                case PLAYING:
                    aVPlayer.pause();
                    break;
                case INIT:
                    aVPlayer.releasePlayer(false);
                    this.audioPlayerList.remove(entry.getKey());
                    break;
                default:
                    break;
            }
        }
    }

    public void mediaPlayerReleased(AVPlayer aVPlayer) {
        if (aVPlayer == this.audioplayer) {
            this.audioplayer = null;
        }
        if (aVPlayer == this.videoPlayer) {
            this.videoPlayer = null;
        }
        if (!a(this.audioPlayerList, aVPlayer)) {
            a(this.videoPlayerList, aVPlayer);
        }
    }

    private boolean a(Hashtable<String, AVPlayer> hashtable, AVPlayer aVPlayer) {
        Object obj;
        for (Entry entry : hashtable.entrySet()) {
            if (entry.getValue() == aVPlayer) {
                obj = (String) entry.getKey();
                break;
            }
        }
        obj = null;
        if (obj == null) {
            return false;
        }
        this.audioPlayerList.remove(obj);
        return true;
    }
}
