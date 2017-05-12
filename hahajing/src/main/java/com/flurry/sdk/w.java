package com.flurry.sdk;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import com.flurry.android.impl.ads.protocol.v13.AdViewType;
import com.flurry.android.impl.ads.protocol.v13.NativeAsset;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class w extends o {
    private static final String a = w.class.getSimpleName();
    private GestureDetector b;
    private a c;
    private List<Integer> d = null;
    private List<String> e = null;
    private boolean f = false;
    private WeakReference<View> g = new WeakReference(null);
    private Rect h = new Rect();
    private int i = 0;

    enum a {
        INIT,
        READY
    }

    public w(Context context, String str) {
        super(context, null, str);
        this.b = new GestureDetector(context, new SimpleOnGestureListener(this) {
            final /* synthetic */ w a;

            {
                this.a = r1;
            }

            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                View view = (View) this.a.g.get();
                if (view != null) {
                    Log.i(w.a, "On item clicked" + view.getClass());
                    this.a.A();
                    this.a.B();
                }
                return false;
            }

            public boolean onDown(MotionEvent motionEvent) {
                return true;
            }
        });
        this.c = a.INIT;
    }

    public void a() {
        super.a();
    }

    protected void r() {
        super.r();
        if (a.READY.equals(this.c)) {
            View view = (View) this.g.get();
            if (view != null) {
                b(view);
                if (!this.f) {
                    long width;
                    if (view.getGlobalVisibleRect(this.h)) {
                        width = (long) (this.h.width() * this.h.height());
                    } else {
                        width = 0;
                    }
                    if (width > 0) {
                        int i;
                        if (this.h.top == 0 && this.h.left == 0) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        if (((float) width) < ((float) ((long) (view.getHeight() * view.getWidth()))) * 0.5f || r1 != 0) {
                            this.i = 0;
                            return;
                        }
                        int i2 = this.i + 1;
                        this.i = i2;
                        if (i2 >= 10) {
                            A();
                            return;
                        }
                        return;
                    }
                    this.i = 0;
                }
            }
        }
    }

    protected void a(d dVar) {
        super.a(dVar);
        if (com.flurry.sdk.d.a.kOnFetched.equals(dVar.b)) {
            ap n = n();
            if (n == null) {
                cq.a(this, av.kMissingAdController);
                return;
            }
            AdUnit a = n.a();
            if (a == null) {
                cq.a(this, av.kInvalidAdUnit);
            } else if (AdViewType.NATIVE.equals(a.adViewType)) {
                p();
                synchronized (this) {
                    this.c = a.READY;
                }
            } else {
                cq.a(this, av.kIncorrectClassForAdSpace);
            }
        }
    }

    public void a(List<Integer> list) {
        this.d = list;
    }

    public List<Integer> s() {
        return this.d;
    }

    public List<String> t() {
        return this.e;
    }

    public boolean u() {
        boolean equals;
        synchronized (this) {
            equals = a.READY.equals(this.c);
        }
        return equals;
    }

    public void v() {
        synchronized (this) {
            if (a.INIT.equals(this.c)) {
                h().a((r) this, i(), j());
            } else if (a.READY.equals(this.c)) {
                cq.a(this);
            }
        }
    }

    public void a(View view) {
        w();
        this.g = new WeakReference(view);
    }

    public void w() {
        View view = (View) this.g.get();
        if (view != null) {
            view.setOnTouchListener(null);
            this.g.clear();
        }
    }

    public int x() {
        if (a.READY.equals(this.c)) {
            return k().h().style;
        }
        return 0;
    }

    public List<NativeAsset> y() {
        if (a.READY.equals(this.c)) {
            return new ArrayList(k().i());
        }
        return Collections.emptyList();
    }

    private void b(View view) {
        if (view != null) {
            view.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ w a;

                {
                    this.a = r1;
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    this.a.b.onTouchEvent(motionEvent);
                    return true;
                }
            });
        }
    }

    private synchronized void A() {
        if (!this.f) {
            Log.i(a, "Impression logged");
            co.a(aw.EV_NATIVE_IMPRESSION, Collections.emptyMap(), e(), this, k(), 0);
            this.f = true;
        }
    }

    private synchronized void B() {
        Log.i(a, "Click logged");
        co.a(aw.EV_CLICKED, Collections.emptyMap(), e(), this, k(), 0);
    }
}
