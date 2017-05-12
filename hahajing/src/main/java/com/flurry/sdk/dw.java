package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.MediaController.MediaPlayerControl;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.FileInputStream;
import java.io.IOException;

class dw extends SurfaceView implements MediaPlayerControl {
    private static final String d = dw.class.getSimpleName();
    OnPreparedListener a = new OnPreparedListener(this) {
        final /* synthetic */ dw a;

        {
            this.a = r1;
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            this.a.p = a.STATE_PREPARED;
            gd.a(5, dw.d, "OnPreparedListener: " + this.a.g);
            if (this.a.o > 3) {
                this.a.seekTo(this.a.o);
            } else {
                this.a.seekTo(3);
            }
            if (this.a.e != null && this.a.g != null) {
                this.a.e.a(this.a.g.toString());
            }
        }
    };
    OnVideoSizeChangedListener b = new OnVideoSizeChangedListener(this) {
        final /* synthetic */ dw a;

        {
            this.a = r1;
        }

        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            this.a.h = mediaPlayer.getVideoWidth();
            this.a.i = mediaPlayer.getVideoHeight();
            if (this.a.h != 0 && this.a.i != 0 && this.a.getHolder() != null) {
                this.a.getHolder().setFixedSize(this.a.h, this.a.i);
            }
        }
    };
    Callback c = new Callback(this) {
        final /* synthetic */ dw a;

        {
            this.a = r1;
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            this.a.q = true;
            this.a.h();
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            this.a.q = false;
            this.a.c();
        }
    };
    private dz e;
    private MediaPlayer f = null;
    private Uri g = null;
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private int o;
    private a p = a.STATE_UNKNOWN;
    private boolean q = false;
    private float r = 0.0f;
    private final fy<hj> s = new fy<hj>(this) {
        final /* synthetic */ dw a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((hj) fxVar);
        }

        public void a(hj hjVar) {
            if (this.a.f != null) {
                int duration = this.a.getDuration();
                int currentPosition = this.a.getCurrentPosition();
                if (duration >= 0 && this.a.e != null) {
                    if (((float) currentPosition) - this.a.r > 1000.0f || this.a.r <= BitmapDescriptorFactory.HUE_MAGENTA) {
                        this.a.r = (float) currentPosition;
                        this.a.e.a(this.a.g.toString(), (float) duration, (float) currentPosition);
                    }
                }
            }
        }
    };
    private OnCompletionListener t = new OnCompletionListener(this) {
        final /* synthetic */ dw a;

        {
            this.a = r1;
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            this.a.p = a.STATE_PLAYBACK_COMPLETED;
            if (this.a.e != null) {
                this.a.e.b(this.a.g.toString());
            }
        }
    };
    private OnErrorListener u = new OnErrorListener(this) {
        final /* synthetic */ dw a;

        {
            this.a = r1;
        }

        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            gd.a(5, dw.d, "Error: " + this.a.g + " framework_err " + i + " impl_err " + i2);
            this.a.p = a.STATE_ERROR;
            if (this.a.e != null) {
                this.a.e.a(this.a.g.toString(), av.kVideoPlaybackError.a(), i, i2);
            }
            return true;
        }
    };
    private OnBufferingUpdateListener v = new OnBufferingUpdateListener(this) {
        final /* synthetic */ dw a;

        {
            this.a = r1;
        }

        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            this.a.n = i;
        }
    };

    enum a {
        STATE_UNKNOWN,
        STATE_INIT,
        STATE_PREPARING,
        STATE_PREPARED,
        STATE_PLAYING,
        STATE_PAUSED,
        STATE_PLAYBACK_COMPLETED,
        STATE_SUSPEND,
        STATE_ERROR
    }

    public dw(Context context, dz dzVar) {
        super(context);
        a(dzVar);
    }

    public void a(Uri uri, int i) {
        if (uri != null) {
            this.l = i;
            this.g = uri;
        }
    }

    public void a(int i) {
        if (this.p == a.STATE_PAUSED || this.p == a.STATE_PREPARED) {
            int i2 = i < 0 ? this.l > 3 ? this.l : 0 : i;
            seekTo(i2);
            start();
        }
    }

    public boolean a() {
        return this.p == a.STATE_PAUSED || this.p == a.STATE_PREPARED;
    }

    public void b() {
        this.l = getCurrentPosition();
        pause();
    }

    public void c() {
        this.l = getCurrentPosition();
        this.p = a.STATE_SUSPEND;
        pause();
        g();
    }

    public int d() {
        return 3;
    }

    private void g() {
        if (this.f != null) {
            setFocusable(false);
            setFocusableInTouchMode(false);
            j();
            this.f.reset();
            this.f.release();
            hk.a().b(this.s);
            this.f = null;
        }
    }

    public void e() {
        g();
    }

    private void a(dz dzVar) {
        this.e = dzVar;
        this.g = null;
        this.p = a.STATE_INIT;
        this.l = 3;
        if (getHolder() != null) {
            getHolder().addCallback(this.c);
            getHolder().setType(3);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        requestLayout();
        hk.a().a(this.s);
    }

    protected void onMeasure(int i, int i2) {
        int defaultSize = getDefaultSize(this.h, i);
        int defaultSize2 = getDefaultSize(this.i, i2);
        if (this.h > 0 && this.i > 0) {
            if (this.h * defaultSize2 > this.i * defaultSize) {
                defaultSize2 = (this.i * defaultSize) / this.h;
            } else if (this.h * defaultSize2 < this.i * defaultSize) {
                defaultSize = (this.h * defaultSize2) / this.i;
            }
        }
        if (!(this.j == defaultSize && this.k == defaultSize2) && this.q) {
            gd.a(4, d, "setting size: " + defaultSize + 'x' + defaultSize2 + " " + System.currentTimeMillis());
            this.j = defaultSize;
            this.k = defaultSize2;
            a(defaultSize, defaultSize2);
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    private void h() {
        Throwable e;
        if (l()) {
            try {
                g();
                i();
                return;
            } catch (IOException e2) {
                e = e2;
            } catch (IllegalArgumentException e3) {
                e = e3;
            }
        } else {
            gd.a(5, d, "Cannot open video: " + this.g);
            this.u.onError(this.f, 1, 0);
            return;
        }
        gd.a(5, d, "Unable to open content: " + this.g, e);
        this.u.onError(this.f, 1, 0);
    }

    private void i() throws IOException, IllegalArgumentException {
        if (this.f == null && this.g != null) {
            this.f = new MediaPlayer();
            this.f.setOnPreparedListener(this.a);
            this.f.setOnVideoSizeChangedListener(this.b);
            this.m = -1;
            this.f.setOnCompletionListener(this.t);
            this.f.setOnErrorListener(this.u);
            this.f.setOnBufferingUpdateListener(this.v);
            this.f.setDisplay(getHolder());
            if (k()) {
                this.f.setDataSource(getContext(), this.g);
            } else {
                FileInputStream fileInputStream = new FileInputStream(this.g.getPath());
                this.f.setDataSource(fileInputStream.getFD());
                fileInputStream.close();
            }
            this.f.setScreenOnWhilePlaying(true);
            this.f.prepareAsync();
            this.p = a.STATE_PREPARING;
        }
    }

    private void j() {
        if (getContext() != null) {
            Intent intent = new Intent("com.android.music.musicservicecommand");
            intent.putExtra("command", "pause");
            getContext().sendBroadcast(intent);
        }
    }

    private boolean k() {
        return (this.g == null || this.g.getScheme() == null || this.g.getScheme().equalsIgnoreCase("file")) ? false : true;
    }

    private boolean l() {
        boolean isFinishing;
        Context context = getContext();
        if (context != null) {
            isFinishing = ((Activity) context).isFinishing();
        } else {
            isFinishing = false;
        }
        gd.a(4, d, "IsFinishing " + isFinishing);
        if (!this.q || isPlaying() || isFinishing || this.p.equals(a.STATE_SUSPEND)) {
            return false;
        }
        return true;
    }

    public void start() {
        if (this.f != null && m() && !this.f.isPlaying()) {
            this.p = a.STATE_PLAYING;
            this.f.start();
        }
    }

    public void pause() {
        if (this.f != null && m() && this.f.isPlaying()) {
            gd.a(4, d, "Video pause at " + this.f.getCurrentPosition());
            this.p = a.STATE_PAUSED;
            this.f.pause();
        }
    }

    public int getDuration() {
        this.m = m() ? this.f.getDuration() : -1;
        return this.m;
    }

    public int getCurrentPosition() {
        return (this.f == null || !(this.p.equals(a.STATE_PREPARED) || this.p.equals(a.STATE_PLAYING))) ? -1 : this.f.getCurrentPosition();
    }

    public void seekTo(int i) {
        if (m()) {
            this.o = i;
            this.f.seekTo(i);
        }
    }

    public boolean isPlaying() {
        return this.f != null && this.f.isPlaying() && this.p.equals(a.STATE_PLAYING);
    }

    public int getBufferPercentage() {
        return m() ? this.n : 0;
    }

    public boolean canPause() {
        return false;
    }

    public boolean canSeekBackward() {
        return false;
    }

    public boolean canSeekForward() {
        return false;
    }

    public int getAudioSessionId() {
        return 0;
    }

    private void a(int i, int i2) {
        if (this.e != null) {
            gd.a(4, d, "Firing updateView ");
            this.e.a(i, i2);
        }
    }

    private boolean m() {
        return this.f != null && (this.p.equals(a.STATE_PREPARED) || this.p.equals(a.STATE_PLAYING) || this.p.equals(a.STATE_PAUSED));
    }
}
