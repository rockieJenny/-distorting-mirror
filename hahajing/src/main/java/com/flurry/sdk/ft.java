package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.flurry.sdk.fs.a;

public class ft {
    private static ft a;
    private static final String b = ft.class.getSimpleName();
    private Object c;

    public static synchronized ft a() {
        ft ftVar;
        synchronized (ft.class) {
            if (a == null) {
                a = new ft();
            }
            ftVar = a;
        }
        return ftVar;
    }

    public static synchronized void b() {
        synchronized (ft.class) {
            if (a != null) {
                a.f();
            }
            a = null;
        }
    }

    private ft() {
        e();
    }

    public boolean c() {
        return this.c != null;
    }

    @TargetApi(14)
    private void e() {
        if (VERSION.SDK_INT >= 14 && this.c == null) {
            Context c = fp.a().c();
            if (c instanceof Application) {
                this.c = new ActivityLifecycleCallbacks(this) {
                    final /* synthetic */ ft a;

                    {
                        this.a = r1;
                    }

                    public void onActivityCreated(Activity activity, Bundle bundle) {
                        gd.a(3, ft.b, "onActivityCreated for activity:" + activity);
                        a(activity, a.kCreated);
                    }

                    public void onActivityStarted(Activity activity) {
                        gd.a(3, ft.b, "onActivityStarted for activity:" + activity);
                        a(activity, a.kStarted);
                    }

                    public void onActivityResumed(Activity activity) {
                        gd.a(3, ft.b, "onActivityResumed for activity:" + activity);
                        a(activity, a.kResumed);
                    }

                    public void onActivityPaused(Activity activity) {
                        gd.a(3, ft.b, "onActivityPaused for activity:" + activity);
                        a(activity, a.kPaused);
                    }

                    public void onActivityStopped(Activity activity) {
                        gd.a(3, ft.b, "onActivityStopped for activity:" + activity);
                        a(activity, a.kStopped);
                    }

                    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                        gd.a(3, ft.b, "onActivitySaveInstanceState for activity:" + activity);
                        a(activity, a.kSaveState);
                    }

                    public void onActivityDestroyed(Activity activity) {
                        gd.a(3, ft.b, "onActivityDestroyed for activity:" + activity);
                        a(activity, a.kDestroyed);
                    }

                    protected void a(Activity activity, a aVar) {
                        fs fsVar = new fs();
                        fsVar.a = activity;
                        fsVar.b = aVar;
                        fsVar.b();
                    }
                };
                ((Application) c).registerActivityLifecycleCallbacks((ActivityLifecycleCallbacks) this.c);
            }
        }
    }

    @TargetApi(14)
    private void f() {
        if (VERSION.SDK_INT >= 14 && this.c != null) {
            Context c = fp.a().c();
            if (c instanceof Application) {
                ((Application) c).unregisterActivityLifecycleCallbacks((ActivityLifecycleCallbacks) this.c);
                this.c = null;
            }
        }
    }
}
