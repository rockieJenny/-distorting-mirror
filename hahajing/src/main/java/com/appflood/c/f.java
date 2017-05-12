package com.appflood.c;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import com.appflood.b.a;
import com.appflood.b.b;
import com.appflood.e.j;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public final class f {
    public static String a;
    private static Timer b;
    private static Handler c;
    private static WeakReference<Thread> d;
    private static a e;
    private static com.appflood.a.a f;
    private static Context g;
    private static h h;
    private static File i = null;

    public static void a() {
        if (c == null) {
            c = new Handler();
        }
        if (d == null) {
            d = new WeakReference(Thread.currentThread());
        }
    }

    static /* synthetic */ void a(Context context) {
        if (context == null) {
            j.c("context ctx is null");
            return;
        }
        Intent intent = new Intent("com.papayamobile.ACTION_GET_VIRTUAL_ID");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("version", "V2.21".substring(1));
        context.sendBroadcast(intent);
    }

    public static void a(Context context, boolean z) {
        " ctx = ctx = " + context;
        j.a();
        if (i == null) {
            i = context.getFileStreamPath("com_appflood_provider_file");
        }
        if (b == null) {
            b = new Timer("appflood timer");
        }
        if (e == null) {
            e = new a();
        }
        if (f == null) {
            f = new com.appflood.a.a("ppy_cross", context);
        }
        if (z) {
            byte[] a = com.appflood.e.a.a(f.a("ppyid"));
            if (a != null && a.length == 32) {
                a = j.a(a, null);
            }
            if (a == null || a.length() != 32 || a.charAt(0) < '0' || a.charAt(0) > 'z') {
                a = j.b();
                if (!f.a("ppyid", j.b(a))) {
                    j.c("Can't write cache initially!");
                }
            }
            g = context;
            if (context == null) {
                j.c("context ctx is null");
            } else {
                h = new h();
                context.registerReceiver(h, new IntentFilter("com.papayamobile.ACTION_RETURN_VIRTUAL_ID"));
            }
            d.a(new d.a() {
                public final void a() {
                    f.a(f.g);
                }
            });
        }
    }

    public static void a(Runnable runnable) {
        if (c == null) {
            return;
        }
        if (Thread.currentThread() != d.get()) {
            c.post(runnable);
            return;
        }
        try {
            runnable.run();
        } catch (Throwable th) {
            j.b(th, "Error occurred in handler run thread " + th.toString());
        }
    }

    public static void a(Runnable runnable, long j) {
        if (c != null) {
            c.postDelayed(runnable, j);
        }
    }

    public static void a(TimerTask timerTask, long j) {
        if (b != null) {
            b.schedule(timerTask, j);
        }
    }

    public static Context b() {
        return g;
    }

    public static com.appflood.a.a c() {
        return f;
    }

    public static a d() {
        return e;
    }

    public static File e() {
        return i;
    }

    public static void f() {
        Context context = g;
        if (context == null) {
            j.c("context ctx is null");
        } else if (h != null) {
            try {
                context.unregisterReceiver(h);
                h = null;
            } catch (Throwable e) {
                j.a(e, "failed to unregister Receiver");
            }
        }
        g = null;
        if (e != null) {
            a aVar = e;
            synchronized (aVar) {
                Iterator it = aVar.a.iterator();
                while (it.hasNext()) {
                    try {
                        ((b) it.next()).a(null);
                    } catch (Throwable th) {
                    }
                }
                aVar.a.clear();
            }
            try {
                if (aVar.b != null) {
                    aVar.b.shutdownNow();
                }
                aVar.b = null;
            } catch (Throwable e2) {
                j.b(e2, "failed to shutdown the httpservice");
            }
            e = null;
        }
        if (b != null) {
            b.cancel();
            b = null;
        }
    }

    public static boolean g() {
        return c != null && Thread.currentThread() == d.get();
    }
}
