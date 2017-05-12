package com.appflood.mraid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import com.appflood.b.b;
import com.appflood.e.j;
import java.util.ArrayList;

public final class p extends b {
    boolean a;
    boolean b = false;
    Handler c = new Handler();
    protected float d;
    protected int e = -1;
    protected int f = -1;
    private t g = t.HIDDEN;
    private final m h;
    private final r i;
    private final o j;
    private AFBannerWebView k;
    private FrameLayout l;
    private Runnable m = new Runnable(this) {
        private /* synthetic */ p a;

        {
            this.a = r1;
        }

        public final void run() {
            boolean e = this.a.e();
            if (this.a.a != e) {
                this.a.a = e;
                this.a.a().a(A.a(this.a.a));
            }
            p.a(this.a, (int) (((float) this.a.a().getWidth()) / this.a.d), (int) (((float) this.a.a().getHeight()) / this.a.d));
            if (!this.a.b) {
                this.a.c.postDelayed(this, 3000);
            }
        }
    };
    private final int n;
    private BroadcastReceiver o = new BroadcastReceiver(this) {
        private int a;
        private /* synthetic */ p b;

        {
            this.b = r1;
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                int a = ((WindowManager) super.a().getContext().getSystemService("window")).getDefaultDisplay().getOrientation();
                if (a != this.a) {
                    this.a = a;
                    p pVar = this.b;
                    int i = this.a;
                    p.b(pVar);
                }
            }
        }
    };
    private ImageView p;
    private boolean q = true;
    private int r = -1;
    private int s = -1;
    private int t = -1;
    private int u;
    private FrameLayout v;
    private FrameLayout w;
    private RelativeLayout x;
    private RelativeLayout y;
    private RelativeLayout z;

    public final class a {
        private /* synthetic */ p a;

        a(p pVar) {
            this.a = pVar;
        }

        public final void a() {
            this.a.d();
        }
    }

    public p(AFBannerWebView aFBannerWebView, m mVar, r rVar, o oVar) {
        super(aFBannerWebView);
        this.h = mVar;
        this.i = rVar;
        this.j = oVar;
        Context context = super.a().getContext();
        this.n = context instanceof Activity ? ((Activity) context).getRequestedOrientation() : -1;
        this.w = new FrameLayout(super.a().getContext());
        this.y = new RelativeLayout(super.a().getContext());
        this.v = new FrameLayout(super.a().getContext());
        this.g = t.LOADING;
        f();
        this.c.removeCallbacks(this.m);
        this.c.post(this.m);
        super.a().getContext().registerReceiver(this.o, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
    }

    static /* synthetic */ void a(p pVar, int i, int i2) {
        if (!(i == pVar.s && i2 == pVar.t) && i > 0 && i2 > 0) {
            pVar.s = i;
            pVar.t = i2;
            super.a().b(new y(i, i2));
        }
    }

    static /* synthetic */ void b(p pVar) {
        pVar.f();
        super.a().a(x.a(pVar.e, pVar.f));
        super.a().l();
        super.a().m();
    }

    private void b(boolean z) {
        if (z) {
            this.z.removeAllViews();
            this.l.removeView(this.z);
        } else {
            this.w.removeAllViewsInLayout();
            this.y.removeAllViewsInLayout();
            this.l.removeView(this.y);
        }
        ViewGroup viewGroup = (ViewGroup) this.v.getParent();
        if (viewGroup == this.x) {
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            if (viewGroup2 != null) {
                viewGroup2.setVisibility(0);
            }
        } else {
            viewGroup.setVisibility(0);
        }
        viewGroup.addView(super.a(), this.u, this.v.getLayoutParams());
        viewGroup.removeView(this.v);
        a(!this.q, t.DEFAULT);
        viewGroup.invalidate();
    }

    private void c(boolean z) {
        try {
            Activity activity = (Activity) super.a().getContext();
            activity.setRequestedOrientation(z ? activity.getResources().getConfiguration().orientation : this.n);
        } catch (ClassCastException e) {
            Log.d("MraidDisplayController", "Unable to modify device orientation.");
        }
    }

    private void f() {
        Context context = super.a().getContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.d = displayMetrics.density;
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        this.e = (int) (((double) i) * (160.0d / ((double) displayMetrics.densityDpi)));
        this.f = (int) (((double) i2) * (160.0d / ((double) displayMetrics.densityDpi)));
        this.r = (int) (((double) (i2 - com.appflood.AFListActivity.AnonymousClass1.a(context))) * (160.0d / ((double) displayMetrics.densityDpi)));
    }

    private void g() {
        int i = 0;
        ViewGroup viewGroup = (ViewGroup) super.a().getParent();
        View a = super.a();
        if (viewGroup != null) {
            int i2;
            View view;
            ViewGroup viewGroup2;
            if (viewGroup == this.x) {
                i2 = 1;
                view = this.x;
                viewGroup2 = (ViewGroup) viewGroup.getParent();
            } else {
                view = a;
                viewGroup2 = viewGroup;
                i2 = 0;
            }
            int childCount = viewGroup2.getChildCount();
            while (i < childCount && viewGroup2.getChildAt(i) != view) {
                i++;
            }
            this.u = i;
            if (!(this.v == null || this.v.getParent() == null)) {
                ((ViewGroup) this.v.getParent()).removeView(this.v);
            }
            viewGroup2.addView(this.v, i, view.getLayoutParams());
            ((ViewGroup) super.a().getParent()).removeView(super.a());
            if (i2 != 0) {
                viewGroup2.removeView(view);
            }
            viewGroup2.setVisibility(4);
        }
    }

    public final /* bridge */ /* synthetic */ AFBannerWebView a() {
        return super.a();
    }

    protected final void a(int i, int i2, int i3, int i4, boolean z) {
        if (this.i != r.DISABLED) {
            this.l = (FrameLayout) super.a().getRootView().findViewById(16908290);
            g();
            this.z = new RelativeLayout(super.a().getContext());
            if (!z) {
                if (i3 + i > this.e) {
                    i = this.e - i3;
                }
                if (i2 + i4 > this.r) {
                    i2 = this.r - i4;
                }
            }
            LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (((float) i) * this.d), (int) (((float) i2) * this.d));
            layoutParams.leftMargin = (int) (((float) i3) * this.d);
            layoutParams.topMargin = (int) (((float) i4) * this.d);
            layoutParams.gravity = 51;
            "llllllllll left " + layoutParams.leftMargin + " right " + layoutParams.topMargin + "  " + this.l + " mrootw " + this.l.getWidth();
            j.a();
            this.z.addView(super.a(), new LayoutParams(-1, -1));
            this.l.addView(this.z, layoutParams);
            this.g = t.RESIZED;
            super.a().a(z.a(this.g));
        }
    }

    protected final void a(String str, int i, int i2, boolean z, boolean z2) {
        if (this.h != m.DISABLED) {
            " url = " + str;
            j.a();
            if (str == null || URLUtil.isValidUrl(str)) {
                if (this.g == t.RESIZED) {
                    b(true);
                }
                this.l = (FrameLayout) super.a().getRootView().findViewById(16908290);
                a(z);
                c(z2);
                g();
                View a = super.a();
                if (!j.g(str)) {
                    this.k = new AFBannerWebView(super.a().getContext(), m.DISABLED, r.ENABLED, o.AD_CONTROLLED, q.INLINE);
                    this.k.a(new a(this));
                    this.k.loadUrl(str);
                    a = this.k;
                }
                int i3 = (int) (((float) i) * this.d);
                int i4 = (int) (((float) i2) * this.d);
                int a2 = com.appflood.AFListActivity.AnonymousClass1.a(super.a().getContext(), 50);
                if (i3 < a2) {
                    i3 = a2;
                }
                if (i4 >= a2) {
                    a2 = i4;
                }
                View view = new View(super.a().getContext());
                view.setBackgroundColor(0);
                view.setOnTouchListener(new OnTouchListener() {
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                this.y.addView(view, new RelativeLayout.LayoutParams(-1, -1));
                this.w.addView(a, new RelativeLayout.LayoutParams(-1, -1));
                LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, a2);
                layoutParams.addRule(13);
                this.y.addView(this.w, layoutParams);
                this.l.addView(this.y, new RelativeLayout.LayoutParams(-1, -1));
                if (this.j == o.ALWAYS_VISIBLE || !(this.q || this.j == o.ALWAYS_HIDDEN)) {
                    a(true, t.EXPANDED);
                }
                this.g = t.EXPANDED;
                super.a().a(z.a(this.g));
                super.a().e();
                return;
            }
            super.a().a("expand", "URL passed to expand() was invalid.");
        }
    }

    protected final void a(boolean z) {
        "useCustomClose " + z + " state " + this.g;
        j.a();
        if (z != this.q && this.g == t.DEFAULT) {
            a(!z, t.DEFAULT);
        }
        this.q = z;
        super.a().h();
    }

    protected final void a(boolean z, t tVar) {
        "setNativeCloseButtonEnabled enable " + z + " state " + tVar;
        j.a();
        if (z) {
            if (this.p == null) {
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{-16842919}, null);
                stateListDrawable.addState(new int[]{16842919}, null);
                this.p = new ImageButton(super.a().getContext());
                new b("http://test2.papayamobile.com:8080/engine/demo/assets/close-button.png", (byte) 0).a(this.p);
                this.p.setScaleType(ScaleType.FIT_CENTER);
                this.p.setBackgroundDrawable(null);
                this.p.setOnClickListener(new OnClickListener(this) {
                    private /* synthetic */ p a;

                    {
                        this.a = r1;
                    }

                    public final void onClick(View view) {
                        this.a.d();
                    }
                });
            }
            if (this.p.getParent() != null) {
                ((ViewGroup) this.p.getParent()).removeView(this.p);
            }
            int a = com.appflood.AFListActivity.AnonymousClass1.a(super.a().getContext(), 50);
            if (tVar == t.EXPANDED) {
                this.w.addView(this.p, new FrameLayout.LayoutParams(a, a, 5));
            } else if (tVar == t.DEFAULT || tVar == t.RESIZED) {
                LayoutParams layoutParams = new RelativeLayout.LayoutParams(a, a);
                layoutParams.addRule(11);
                layoutParams.addRule(10);
                layoutParams.leftMargin = 0;
                layoutParams.topMargin = 0;
                if (this.x == null) {
                    this.x = new RelativeLayout(super.a().getContext());
                } else if (this.x.getParent() != null) {
                    ((ViewGroup) this.x.getParent()).removeView(this.x);
                }
                RelativeLayout relativeLayout = (RelativeLayout) super.a().getParent();
                if (relativeLayout != null) {
                    relativeLayout.addView(this.x, super.a().getLayoutParams());
                    relativeLayout.removeView(super.a());
                }
                this.x.addView(super.a(), new RelativeLayout.LayoutParams(-1, -1));
                relativeLayout = tVar == t.DEFAULT ? this.x : this.z;
                " container " + relativeLayout + " mDefaultLayout " + this.x;
                j.a();
                relativeLayout.addView(this.p, layoutParams);
            }
        } else if (this.p != null) {
            if (this.p.getParent() != null) {
                ((ViewGroup) this.p.getParent()).removeView(this.p);
            }
            if (tVar == t.DEFAULT && super.a().getParent() == this.x) {
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.x.getLayoutParams();
                RelativeLayout relativeLayout2 = (RelativeLayout) this.x.getParent();
                this.x.removeView(super.a());
                if (relativeLayout2 != null) {
                    relativeLayout2.removeView(this.x);
                    relativeLayout2.addView(super.a(), layoutParams2);
                }
            }
        }
        super.a().h();
    }

    public final void b() {
        this.c.removeCallbacks(this.m);
        try {
            super.a().getContext().unregisterReceiver(this.o);
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Receiver not registered")) {
                throw e;
            }
        }
    }

    public final void c() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(x.a(this.e, this.f));
        arrayList.add(A.a(this.a));
        super.a().a(arrayList);
        this.g = t.DEFAULT;
        a(false);
        super.a().a(z.a(this.g));
        super.a().a(this.e, this.r);
        super.a().l();
    }

    protected final void d() {
        if (this.g == t.DEFAULT) {
            super.a().setVisibility(4);
            this.g = t.HIDDEN;
            ViewGroup viewGroup = (ViewGroup) super.a().getParent();
            if (viewGroup != null) {
                if (viewGroup == this.x) {
                    ((ViewGroup) viewGroup.getParent()).setVisibility(4);
                    ((ViewGroup) viewGroup.getParent()).removeAllViews();
                }
                viewGroup.setVisibility(4);
            }
            super.a().a(z.a(this.g));
            b();
            this.b = true;
            if (super.a().a() != null) {
                super.a().a().onClose();
            }
        } else if (this.g == t.RESIZED || this.g == t.EXPANDED) {
            b(this.g == t.RESIZED);
            c(false);
            this.g = t.DEFAULT;
            super.a().a(z.a(this.g));
        }
        if (super.a().f() != null) {
            a f = super.a().f();
            super.a();
            t tVar = this.g;
            f.a();
        }
    }

    protected final boolean e() {
        return super.a().hasWindowFocus() && super.a().getVisibility() == 0;
    }
}
