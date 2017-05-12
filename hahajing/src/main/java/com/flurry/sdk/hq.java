package com.flurry.sdk;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class hq implements Runnable {
    private static final String a = hq.class.getSimpleName();
    PrintStream h;
    PrintWriter i;

    public abstract void safeRun();

    public final void run() {
        try {
            safeRun();
        } catch (Throwable th) {
            if (this.h != null) {
                th.printStackTrace(this.h);
            } else if (this.i != null) {
                th.printStackTrace(this.i);
            } else {
                th.printStackTrace();
            }
            gd.a(6, a, "", th);
        }
    }
}
