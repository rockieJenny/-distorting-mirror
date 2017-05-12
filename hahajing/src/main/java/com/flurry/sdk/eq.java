package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.FlurryEventRecordStatus;
import java.util.Map;

public class eq implements gg {
    private ey a;

    public static synchronized eq a() {
        eq eqVar;
        synchronized (eq.class) {
            eqVar = (eq) fp.a().a(eq.class);
        }
        return eqVar;
    }

    public void a(Context context) {
        hb.a(fb.class);
        this.a = new ey();
    }

    public void b() {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
        hb.b(fb.class);
    }

    public ey c() {
        return this.a;
    }

    public void d() {
        fb f = f();
        if (f != null) {
            f.b();
        }
    }

    public FlurryEventRecordStatus a(String str) {
        fb f = f();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (f != null) {
            return f.a(str, null, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus a(String str, Map<String, String> map) {
        fb f = f();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (f != null) {
            return f.a(str, map, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus a(String str, boolean z) {
        fb f = f();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (f != null) {
            return f.a(str, null, z);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus a(String str, Map<String, String> map, boolean z) {
        fb f = f();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (f != null) {
            return f.a(str, map, z);
        }
        return flurryEventRecordStatus;
    }

    public void b(String str) {
        fb f = f();
        if (f != null) {
            f.a(str, null);
        }
    }

    public void b(String str, Map<String, String> map) {
        fb f = f();
        if (f != null) {
            f.a(str, (Map) map);
        }
    }

    @Deprecated
    public void a(String str, String str2, String str3) {
        StackTraceElement[] stackTraceElementArr;
        Object stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length <= 2) {
            Object obj = stackTrace;
        } else {
            stackTraceElementArr = new StackTraceElement[(stackTrace.length - 2)];
            System.arraycopy(stackTrace, 2, stackTraceElementArr, 0, stackTraceElementArr.length);
        }
        Throwable th = new Throwable(str2);
        th.setStackTrace(stackTraceElementArr);
        fb f = f();
        if (f != null) {
            f.a(str, str2, str3, th);
        }
    }

    public void a(String str, String str2, Throwable th) {
        fb f = f();
        if (f != null) {
            f.a(str, str2, th.getClass().getName(), th);
        }
    }

    public void c(String str) {
        fb f = f();
        if (f != null) {
            f.a(str, null, false);
        }
    }

    public void c(String str, Map<String, String> map) {
        fb f = f();
        if (f != null) {
            f.a(str, map, false);
        }
    }

    public void e() {
        fb f = f();
        if (f != null) {
            f.d();
        }
    }

    private fb f() {
        return a(hc.a().e());
    }

    private fb a(hb hbVar) {
        if (hbVar == null) {
            return null;
        }
        return (fb) hbVar.c(fb.class);
    }
}
