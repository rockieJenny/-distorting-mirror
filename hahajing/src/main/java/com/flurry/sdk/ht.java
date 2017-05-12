package com.flurry.sdk;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class ht {
    private static ht a;
    private final UncaughtExceptionHandler b = Thread.getDefaultUncaughtExceptionHandler();
    private final Map<UncaughtExceptionHandler, Void> c = new WeakHashMap();

    final class a implements UncaughtExceptionHandler {
        final /* synthetic */ ht a;

        private a(ht htVar) {
            this.a = htVar;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            this.a.a(thread, th);
            this.a.b(thread, th);
        }
    }

    public static synchronized ht a() {
        ht htVar;
        synchronized (ht.class) {
            if (a == null) {
                a = new ht();
            }
            htVar = a;
        }
        return htVar;
    }

    public static synchronized void b() {
        synchronized (ht.class) {
            if (a != null) {
                a.d();
            }
            a = null;
        }
    }

    public void a(UncaughtExceptionHandler uncaughtExceptionHandler) {
        synchronized (this.c) {
            this.c.put(uncaughtExceptionHandler, null);
        }
    }

    private Set<UncaughtExceptionHandler> c() {
        Set<UncaughtExceptionHandler> keySet;
        synchronized (this.c) {
            keySet = this.c.keySet();
        }
        return keySet;
    }

    private ht() {
        Thread.setDefaultUncaughtExceptionHandler(new a());
    }

    private void d() {
        Thread.setDefaultUncaughtExceptionHandler(this.b);
    }

    private void a(Thread thread, Throwable th) {
        for (UncaughtExceptionHandler uncaughtException : c()) {
            try {
                uncaughtException.uncaughtException(thread, th);
            } catch (Throwable th2) {
            }
        }
    }

    private void b(Thread thread, Throwable th) {
        if (this.b != null) {
            try {
                this.b.uncaughtException(thread, th);
            } catch (Throwable th2) {
            }
        }
    }
}
