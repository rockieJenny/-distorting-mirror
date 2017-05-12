package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.hh.a;
import java.lang.Thread.UncaughtExceptionHandler;

public class hs implements gg, a, UncaughtExceptionHandler {
    private static final String a = hs.class.getSimpleName();
    private boolean b;

    public void a(Context context) {
        hh a = hg.a();
        this.b = ((Boolean) a.a("CaptureUncaughtExceptions")).booleanValue();
        a.a("CaptureUncaughtExceptions", (a) this);
        gd.a(4, a, "initSettings, CrashReportingEnabled = " + this.b);
        ht.a().a(this);
    }

    public void b() {
        ht.b();
        hg.a().b("CaptureUncaughtExceptions", (a) this);
    }

    public void a(String str, Object obj) {
        if (str.equals("CaptureUncaughtExceptions")) {
            this.b = ((Boolean) obj).booleanValue();
            gd.a(4, a, "onSettingUpdate, CrashReportingEnabled = " + this.b);
            return;
        }
        gd.a(6, a, "onSettingUpdate internal error!");
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace();
        if (this.b) {
            String str = "";
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                if (th.getMessage() != null) {
                    stringBuilder.append(" (" + th.getMessage() + ")\n");
                }
                str = stringBuilder.toString();
            } else if (th.getMessage() != null) {
                str = th.getMessage();
            }
            eq.a().a("uncaught", str, th);
        }
        hc.a().g();
        fh.a().d();
    }
}
