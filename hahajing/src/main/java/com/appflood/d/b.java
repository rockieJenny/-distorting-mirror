package com.appflood.d;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.appflood.e.j;
import java.io.IOException;
import java.util.HashMap;

public final class b implements OnBufferingUpdateListener, OnInfoListener, OnPreparedListener, Callback {
    private b a;
    private c b;
    private a c;
    private d d;
    private boolean e = false;
    private OnErrorListener f;
    private boolean g = false;
    private int h = 0;
    private OnCompletionListener i;
    private OnSeekCompleteListener j;
    private int k = 0;
    private int l = 0;
    private MediaPlayer m;
    private SurfaceHolder n;
    private Context o;
    private String p;

    public interface a {
    }

    public interface b {
        void b(int i);
    }

    public interface c {
        void a(boolean z);
    }

    public interface d {
        void a(HashMap<String, Integer> hashMap);
    }

    public b(SurfaceView surfaceView, String str) {
        this.p = str;
        this.n = surfaceView.getHolder();
        this.n.addCallback(this);
        this.o = surfaceView.getContext();
        this.n.setType(3);
    }

    private void a(boolean z) {
        if (this.b != null) {
            this.b.a(z);
        }
    }

    public final void a(int i) {
        if (this.m != null) {
            "setCurrentposition " + i;
            j.a();
            this.m.seekTo(i);
        }
    }

    public final void a(OnCompletionListener onCompletionListener) {
        this.i = onCompletionListener;
    }

    public final void a(OnErrorListener onErrorListener) {
        this.f = onErrorListener;
    }

    public final void a(OnSeekCompleteListener onSeekCompleteListener) {
        this.j = onSeekCompleteListener;
    }

    public final void a(a aVar) {
        this.c = aVar;
    }

    public final void a(b bVar) {
        this.a = bVar;
    }

    public final void a(c cVar) {
        this.b = cVar;
    }

    public final void a(d dVar) {
        this.d = dVar;
    }

    public final boolean a() {
        return this.g;
    }

    public final void b() {
        this.g = true;
    }

    public final void c() {
        if (this.m != null && this.m.isPlaying()) {
            this.m.pause();
            "pausedVideo " + this.m.isPlaying();
            j.a();
        }
    }

    public final void d() {
        if (this.m != null) {
            "restart " + this.m.isPlaying();
            j.a();
            this.m.start();
            if (this.h > 0) {
                this.m.seekTo(this.h);
                this.h = 0;
            }
            this.g = false;
        }
    }

    public final boolean e() {
        if (this.m == null) {
            return false;
        }
        "isPlaying " + this.m.isPlaying();
        j.a();
        return this.m.isPlaying();
    }

    public final int f() {
        if (this.m == null) {
            return 0;
        }
        "getDuration " + this.m.getDuration();
        j.a();
        return this.m.getDuration();
    }

    public final int g() {
        return this.m != null ? this.m.getCurrentPosition() : 0;
    }

    public final void h() {
        this.g = true;
        if (this.m != null) {
            this.m.stop();
            this.m.release();
            this.m = null;
        }
    }

    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        "onBufferingUpdate " + i + " mpwwww  " + mediaPlayer.getVideoWidth() + " hhh " + mediaPlayer.getVideoHeight();
        j.a();
        this.k = mediaPlayer.getVideoWidth();
        this.l = mediaPlayer.getVideoHeight();
        int i2 = this.k;
        i2 = this.l;
        if (this.d != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("result_height", Integer.valueOf(this.l));
            hashMap.put("result_width", Integer.valueOf(this.k));
            if (!this.e && this.k > 0 && this.l > 0) {
                hashMap.put("result_code", Integer.valueOf(1));
                this.d.a(hashMap);
                this.e = true;
            }
        }
    }

    public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        "onInfo             buffer what " + i;
        j.a();
        a aVar;
        switch (i) {
            case 701:
                j.a();
                if (this.c != null) {
                    aVar = this.c;
                    break;
                }
                break;
            case 702:
                if (this.c != null) {
                    aVar = this.c;
                }
                j.a();
                break;
        }
        return false;
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        this.k = mediaPlayer.getVideoWidth();
        this.l = mediaPlayer.getVideoHeight();
        "Media player onPrepared " + this.k;
        j.a();
        if (this.l <= 0 || this.k <= 0) {
            a(false);
        } else {
            a(true);
        }
    }

    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        "Media player surface changed " + i + i2 + i3;
        j.a();
    }

    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        j.a();
        try {
            if (!j.g(this.p)) {
                this.e = false;
                if (this.m != null) {
                    this.m.reset();
                }
                if (this.m == null) {
                    this.m = new MediaPlayer();
                }
                if (this.h > 0 && this.a != null) {
                    this.a.b(this.h);
                }
                this.m.setDataSource(this.o, Uri.parse(this.p));
                this.m.setDisplay(surfaceHolder);
                this.m.setAudioStreamType(3);
                this.m.setOnPreparedListener(this);
                this.m.setOnBufferingUpdateListener(this);
                this.m.setOnInfoListener(this);
                this.m.setOnErrorListener(this.f);
                this.m.setOnCompletionListener(this.i);
                this.m.setOnSeekCompleteListener(this.j);
                this.m.prepareAsync();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        j.a();
        if (this.m != null) {
            this.h = this.m.getCurrentPosition();
            this.m.stop();
        }
    }
}
