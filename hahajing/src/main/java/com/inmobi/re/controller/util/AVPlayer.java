package com.inmobi.re.controller.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.android.gms.cast.TextTrackStyle;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.WrapperFunctions;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.controller.JSController.Dimensions;
import com.inmobi.re.controller.JSController.PlayerProperties;
import java.lang.ref.WeakReference;

public class AVPlayer extends VideoView implements OnCompletionListener, OnErrorListener, OnPreparedListener {
    public static final int MINIMAL_LAYOUT_PARAM = 1;
    private static String f = "play";
    private static String g = "pause";
    private static String h = "ended";
    private static int i = -1;
    private static int j = 2;
    private static String k = "Loading. Please Wait..";
    private PlayerProperties a;
    private AVPlayerListener b;
    private String c;
    private int d;
    private RelativeLayout e;
    private boolean l;
    private boolean m;
    private IMWebView n;
    private Bitmap o;
    private int p;
    public boolean pseudoPause;
    private int q;
    private playerState r;
    private MediaPlayer s;
    private boolean t;
    private ViewGroup u;
    private Dimensions v;
    private a w;

    static class a extends Handler {
        private final WeakReference<AVPlayer> a;

        public a(AVPlayer aVPlayer) {
            this.a = new WeakReference(aVPlayer);
        }

        public void handleMessage(Message message) {
            AVPlayer aVPlayer = (AVPlayer) this.a.get();
            if (aVPlayer != null) {
                switch (message.what) {
                    case 1001:
                        if (aVPlayer.l()) {
                            int round = Math.round((float) (aVPlayer.getCurrentPosition() / 1000));
                            int round2 = Math.round((float) (aVPlayer.getDuration() / 1000));
                            if (aVPlayer.q != round) {
                                aVPlayer.a(round, round2);
                                aVPlayer.q = round;
                                aVPlayer.d = round;
                            }
                            sendEmptyMessageDelayed(1001, 1000);
                            break;
                        }
                        return;
                }
            }
            super.handleMessage(message);
        }
    }

    public enum playerState {
        INIT,
        PLAYING,
        PAUSED,
        HIDDEN,
        SHOWING,
        COMPLETED,
        RELEASED
    }

    public PlayerProperties getPlayProperties() {
        return this.a;
    }

    private AVPlayer(Context context) {
        super(context);
        this.pseudoPause = false;
        this.d = 0;
        this.l = false;
        this.m = false;
        this.o = null;
        this.q = -1;
        this.w = new a(this);
    }

    public AVPlayer(Context context, IMWebView iMWebView) {
        this(context);
        setZOrderOnTop(true);
        this.r = playerState.INIT;
        setDrawingCacheEnabled(true);
        this.n = iMWebView;
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.p = 100;
    }

    public void setPlayData(PlayerProperties playerProperties, String str) {
        this.a = playerProperties;
        this.c = str;
        if (playerProperties.audioMuted) {
            this.t = true;
        }
        this.c = this.c.trim();
        this.c = a(this.c);
        if (this.o == null) {
            this.o = Bitmap.createBitmap(10, 10, Config.ARGB_8888);
            if (VERSION.SDK_INT >= 8) {
                try {
                    this.o = WrapperFunctions.getVideoBitmap(this.c);
                } catch (Exception e) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "IMAVPlayer: unable to get video bitmap");
                }
            }
        }
    }

    private void a() {
        if (this.a.showControl()) {
            MediaController mediaController = new MediaController(getContext());
            setMediaController(mediaController);
            mediaController.setAnchorView(this);
        }
    }

    private void b() {
        this.r = playerState.INIT;
        e();
        setVideoPath(this.c);
        a();
        setOnCompletionListener(this);
        setOnErrorListener(this);
        setOnPreparedListener(this);
    }

    private static String a(String str) {
        try {
            byte[] bytes = str.getBytes();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                if ((bytes[i] & 128) > 0) {
                    stringBuffer.append("%" + InternalSDKUtil.byteToHex(bytes[i]));
                } else {
                    stringBuffer.append((char) bytes[i]);
                }
            }
            return new String(stringBuffer.toString().getBytes(), "ISO-8859-1");
        } catch (Exception e) {
            return null;
        }
    }

    private void c() {
        if (this.r == playerState.SHOWING) {
            this.r = this.m ? playerState.COMPLETED : playerState.PAUSED;
        } else if (this.a.isAutoPlay() && this.r == playerState.INIT) {
            if (this.a.doMute()) {
                mute();
            }
            start();
        }
    }

    public void play() {
        b();
    }

    public void setListener(AVPlayerListener aVPlayerListener) {
        this.b = aVPlayerListener;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.debug(Constants.RENDERING_LOG_TAG, "AVPlayer-> onCompletion");
        this.r = playerState.COMPLETED;
        this.m = true;
        b(h);
        i();
        try {
            if (this.a.doLoop()) {
                synchronized (this) {
                    if (!j()) {
                        this.d = 0;
                        start();
                    }
                }
            } else if (this.a.exitOnComplete()) {
                releasePlayer(false);
            }
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "IMAvplayer onCompletion exception ", e);
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        Log.debug(Constants.RENDERING_LOG_TAG, "AVPlayer-> Player error : " + i);
        f();
        releasePlayer(false);
        if (this.b != null) {
            this.b.onError(this);
        }
        int i3 = i;
        if (i == 100) {
            i3 = j;
        }
        a(i3);
        return false;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this.s = mediaPlayer;
        super.seekTo(this.d * 1000);
        if (this.t) {
            try {
                this.s.setVolume(0.0f, 0.0f);
            } catch (Exception e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "IMAVPlayer: unable to set volume");
            }
        }
        Log.debug(Constants.RENDERING_LOG_TAG, "AVPlayer-> onPrepared");
        f();
        if (this.b != null) {
            this.b.onPrepared(this);
        }
        this.l = true;
        c();
    }

    private void d() {
        try {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this);
            }
            viewGroup = (ViewGroup) this.u.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.u);
            }
            setBackgroundColor(0);
            setBackgroundDrawable(null);
            setBackGroundLayout(null);
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "IMAVPlayer: unable to remove view");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void releasePlayer(boolean r4) {
        /*
        r3 = this;
        r2 = 0;
        r0 = r3.n;
        if (r0 == 0) goto L_0x000f;
    L_0x0005:
        r0 = r3.n;
        r1 = new com.inmobi.re.controller.util.AVPlayer$1;
        r1.<init>(r3);
        r0.setOnTouchListener(r1);
    L_0x000f:
        monitor-enter(r3);
        r0 = r3.k();	 Catch:{ all -> 0x003f }
        if (r0 == 0) goto L_0x0018;
    L_0x0016:
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
    L_0x0017:
        return;
    L_0x0018:
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
        r0 = com.inmobi.re.controller.util.AVPlayer.playerState.RELEASED;
        r3.r = r0;
        r0 = r3.q;
        r1 = -1;
        if (r0 == r1) goto L_0x0042;
    L_0x0022:
        r0 = r3.q;
    L_0x0024:
        r3.a(r4, r0);
        r3.i();
        r3.stopPlayback();
        super.setMediaController(r2);
        r3.d();
        r0 = r3.b;
        if (r0 == 0) goto L_0x0017;
    L_0x0037:
        r0 = r3.b;
        r0.onComplete(r3);
        r3.b = r2;
        goto L_0x0017;
    L_0x003f:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
        throw r0;
    L_0x0042:
        r0 = r3.getCurrentPosition();
        r0 = r0 / 1000;
        r0 = (float) r0;
        r0 = java.lang.Math.round(r0);
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.inmobi.re.controller.util.AVPlayer.releasePlayer(boolean):void");
    }

    private void e() {
        this.e = new RelativeLayout(getContext());
        this.e.setLayoutParams(getLayoutParams());
        this.e.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        View textView = new TextView(getContext());
        textView.setText(k);
        textView.setTextColor(-1);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        this.e.addView(textView, layoutParams);
        ((ViewGroup) getParent()).addView(this.e);
    }

    private void f() {
        if (this.e != null) {
            ((ViewGroup) getParent()).removeView(this.e);
        }
    }

    public synchronized void start() {
        if (!(this.n == null || m())) {
            this.n.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ AVPlayer a;

                {
                    this.a = r1;
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return motionEvent.getAction() == 2;
                }
            });
        }
        if (this.r == null || this.r != playerState.PLAYING) {
            seekPlayer(this.d * 1000);
            super.start();
            this.r = playerState.PLAYING;
            this.m = false;
            h();
            Log.debug(Constants.RENDERING_LOG_TAG, "AVPlayer-> start playing");
            if (this.l) {
                b(f);
            }
        }
    }

    public synchronized void pause() {
        if (this.r == null || this.r != playerState.PAUSED) {
            super.pause();
            this.r = playerState.PAUSED;
            i();
            Log.debug(Constants.RENDERING_LOG_TAG, "AVPlayer-> pause");
            b(g);
        }
    }

    private void b(String str) {
        if (this.n != null) {
            this.n.injectJavaScript("window.mraidview.fireMediaTrackingEvent('" + str + "','" + this.a.id + "');");
        }
    }

    private void a(int i) {
        if (this.n != null) {
            this.n.injectJavaScript("window.mraidview.fireMediaErrorEvent('" + this.a.id + "'," + i + ");");
        }
    }

    private void a(int i, int i2) {
        if (this.n != null) {
            this.n.injectJavaScript("window.mraidview.fireMediaTimeUpdateEvent('" + this.a.id + "'," + i + "," + i2 + ");");
        }
    }

    private void a(boolean z, int i) {
        if (this.n != null) {
            this.n.injectJavaScript("window.mraidview.fireMediaCloseEvent('" + this.a.id + "'," + z + "," + i + ");");
            this.n.mediaPlayerReleased(this);
        }
    }

    private void g() {
        if (this.n != null) {
            this.n.injectJavaScript("window.mraidview.fireMediaVolumeChangeEvent('" + this.a.id + "'," + getVolume() + "," + isMediaMuted() + ");");
        }
    }

    private void h() {
        this.w.sendEmptyMessage(1001);
    }

    private void i() {
        this.w.removeMessages(1001);
    }

    public String getPropertyID() {
        return this.a.id;
    }

    public boolean isInlineVideo() {
        return !this.a.isFullScreen();
    }

    private boolean j() {
        return this.r == playerState.PAUSED || this.r == playerState.HIDDEN;
    }

    private boolean k() {
        return this.r == playerState.RELEASED;
    }

    private boolean l() {
        return this.r == playerState.PLAYING;
    }

    public String getMediaURL() {
        return this.c;
    }

    public playerState getState() {
        return this.r;
    }

    private void b(int i) {
        if (this.s != null && this.t) {
            this.t = false;
        }
        this.p = i;
        float log = TextTrackStyle.DEFAULT_FONT_SCALE - ((float) (Math.log((double) (101 - i)) / Math.log(101.0d)));
        try {
            this.s.setVolume(log, log);
        } catch (Exception e) {
            this.n.raiseError("Player has been released. Cannot set the volume.", "setVolume");
        }
        g();
    }

    public void mute() {
        if (this.s != null && !this.t) {
            this.t = true;
            try {
                this.s.setVolume(0.0f, 0.0f);
            } catch (Exception e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "IMAVPlayer: unable to set volume (mute)");
            }
            g();
        }
    }

    public void unMute() {
        if (this.s != null && this.t) {
            b(this.p);
        }
    }

    public boolean isMediaMuted() {
        return this.t;
    }

    public void setVolume(int i) {
        if (i != this.p && this.r != playerState.RELEASED) {
            b(i);
        }
    }

    public int getVolume() {
        return this.p;
    }

    public void hide() {
        try {
            setVisibility(4);
            this.u.setVisibility(4);
            this.r = playerState.HIDDEN;
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "IMAVPlayer: unable to hide video");
        }
    }

    public void show() {
        this.r = playerState.SHOWING;
        setVisibility(0);
    }

    public void seekPlayer(int i) {
        if (i <= getDuration()) {
            seekTo(i);
        }
    }

    public void setBackGroundLayout(ViewGroup viewGroup) {
        this.u = viewGroup;
    }

    public ViewGroup getBackGroundLayout() {
        return this.u;
    }

    public PlayerProperties getProperties() {
        return this.a;
    }

    public Dimensions getPlayDimensions() {
        return this.v;
    }

    public void setPlayDimensions(Dimensions dimensions) {
        this.v = dimensions;
    }

    protected void onWindowVisibilityChanged(int i) {
        try {
            super.onWindowVisibilityChanged(i);
            if (VERSION.SDK_INT < 8) {
                onIMVisibilityChanged(i == 0);
            }
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "IMAVPlayer: onWindowVisibilityChanged: Something went wrong");
        }
    }

    private boolean m() {
        return getLayoutParams().width == 1 && getLayoutParams().height == 1;
    }

    public void onIMVisibilityChanged(boolean z) {
        Log.debug(Constants.RENDERING_LOG_TAG, "AVPlayer-> onIMVisibilityChanged: " + z);
        if (z && !this.pseudoPause) {
            setBackgroundDrawable(new BitmapDrawable(this.o));
        }
        if (z && this.pseudoPause) {
            this.pseudoPause = false;
            play();
        }
        if (!z && this.r == playerState.PLAYING) {
            this.pseudoPause = true;
            pause();
        }
    }

    protected void onVisibilityChanged(View view, int i) {
        onIMVisibilityChanged(i == 0);
    }

    public boolean getAutoPlay() {
        if (this.a != null) {
            return this.a.autoPlay;
        }
        return false;
    }

    public void setAutoPlay(boolean z) {
        if (this.a != null) {
            this.a.autoPlay = z;
        }
    }

    public boolean isPrepared() {
        return this.l;
    }
}
