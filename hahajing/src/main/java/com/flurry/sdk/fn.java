package com.flurry.sdk;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class fn extends gc<gl> {
    private static fn a = null;

    public static synchronized fn a() {
        fn fnVar;
        synchronized (fn.class) {
            if (a == null) {
                a = new fn();
            }
            fnVar = a;
        }
        return fnVar;
    }

    public static synchronized void b() {
        synchronized (fn.class) {
            if (a != null) {
                a.c();
            }
            a = null;
        }
    }

    protected fn() {
        super(fn.class.getName(), 0, 5, 5000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(11, new ga()));
    }
}
