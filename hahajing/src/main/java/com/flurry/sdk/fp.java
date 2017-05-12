package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

public class fp {
    private static fp a;
    private static final String b = fp.class.getSimpleName();
    private final Context c;
    private final Handler d = new Handler(Looper.getMainLooper());
    private final Handler e;
    private final HandlerThread f = new HandlerThread("FlurryAgent");
    private final String g;
    private final gf h;

    public static fp a() {
        return a;
    }

    public static synchronized void a(Context context, String str) {
        synchronized (fp.class) {
            if (a != null) {
                if (!a.d().equals(str)) {
                    throw new IllegalStateException("Only one API key per application is supported!");
                }
            } else if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            } else if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("API key must be specified");
            } else {
                a = new fp(context, str);
                a.a(context);
            }
        }
    }

    public static synchronized void b() {
        synchronized (fp.class) {
            if (a != null) {
                a.g();
                a = null;
            }
        }
    }

    private fp(Context context, String str) {
        this.c = context.getApplicationContext();
        this.f.start();
        this.e = new Handler(this.f.getLooper());
        this.g = str;
        this.h = new gf();
    }

    private void g() {
        h();
        this.f.quit();
    }

    private void a(Context context) {
        this.h.a(context);
    }

    private void h() {
        this.h.a();
    }

    public Context c() {
        return this.c;
    }

    public String d() {
        return this.g;
    }

    public PackageManager e() {
        return this.c.getPackageManager();
    }

    public void a(Runnable runnable) {
        if (runnable != null) {
            this.d.post(runnable);
        }
    }

    public void a(Runnable runnable, long j) {
        if (runnable != null) {
            this.d.postDelayed(runnable, j);
        }
    }

    public Handler f() {
        return this.e;
    }

    public void b(Runnable runnable) {
        if (runnable != null) {
            this.e.post(runnable);
        }
    }

    public void b(Runnable runnable, long j) {
        if (runnable != null) {
            this.e.postDelayed(runnable, j);
        }
    }

    public void c(Runnable runnable) {
        if (runnable != null) {
            this.e.removeCallbacks(runnable);
        }
    }

    public gg a(Class<? extends gg> cls) {
        return this.h.a((Class) cls);
    }
}
