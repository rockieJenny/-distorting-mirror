package com.jirbo.adcolony;

import java.util.ArrayList;
import java.util.Iterator;

class aa {
    static String a = new String("mutex");
    static ArrayList<a> b = new ArrayList();
    static ArrayList<a> c = new ArrayList();
    static ArrayList<Runnable> d = new ArrayList();
    static ArrayList<Runnable> e = new ArrayList();
    static volatile boolean f;

    static class a extends Thread {
        Runnable a;

        a() {
        }

        public void run() {
            while (true) {
                if (this.a != null) {
                    try {
                        this.a.run();
                    } catch (RuntimeException e) {
                        a.e("Exception caught in reusable thread.");
                        a.e(e + "");
                        e.printStackTrace();
                    }
                    this.a = null;
                }
                if (!aa.f) {
                    synchronized (this) {
                        synchronized (aa.a) {
                            aa.b.add(this);
                        }
                        try {
                            wait();
                        } catch (InterruptedException e2) {
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    aa() {
    }

    static void a() {
        c();
        synchronized (a) {
            d.clear();
        }
        b();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(java.lang.Runnable r3) {
        /*
        r0 = 0;
        r2 = a;
        monitor-enter(r2);
        r1 = f;	 Catch:{ all -> 0x0039 }
        if (r1 == 0) goto L_0x000f;
    L_0x0008:
        r0 = d;	 Catch:{ all -> 0x0039 }
        r0.add(r3);	 Catch:{ all -> 0x0039 }
        monitor-exit(r2);	 Catch:{ all -> 0x0039 }
    L_0x000e:
        return;
    L_0x000f:
        r1 = b;	 Catch:{ all -> 0x0039 }
        r1 = r1.size();	 Catch:{ all -> 0x0039 }
        if (r1 <= 0) goto L_0x004a;
    L_0x0017:
        r0 = b;	 Catch:{ all -> 0x0039 }
        r1 = r1 + -1;
        r0 = r0.remove(r1);	 Catch:{ all -> 0x0039 }
        r0 = (com.jirbo.adcolony.aa.a) r0;	 Catch:{ all -> 0x0039 }
        r1 = r0;
    L_0x0022:
        monitor-exit(r2);	 Catch:{ all -> 0x0039 }
        if (r1 != 0) goto L_0x003f;
    L_0x0025:
        r0 = new com.jirbo.adcolony.aa$a;
        r0.<init>();
        r1 = a;
        monitor-enter(r1);
        r2 = c;	 Catch:{ all -> 0x003c }
        r2.add(r0);	 Catch:{ all -> 0x003c }
        monitor-exit(r1);	 Catch:{ all -> 0x003c }
        r0.a = r3;
        r0.start();
        goto L_0x000e;
    L_0x0039:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0039 }
        throw r0;
    L_0x003c:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x003c }
        throw r0;
    L_0x003f:
        monitor-enter(r1);
        r1.a = r3;	 Catch:{ all -> 0x0047 }
        r1.notify();	 Catch:{ all -> 0x0047 }
        monitor-exit(r1);	 Catch:{ all -> 0x0047 }
        goto L_0x000e;
    L_0x0047:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0047 }
        throw r0;
    L_0x004a:
        r1 = r0;
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jirbo.adcolony.aa.a(java.lang.Runnable):void");
    }

    static void b() {
        synchronized (a) {
            f = false;
            e.clear();
            e.addAll(d);
            d.clear();
            c.clear();
        }
        Iterator it = e.iterator();
        while (it.hasNext()) {
            a((Runnable) it.next());
        }
    }

    static void c() {
        synchronized (a) {
            f = true;
            Iterator it = b.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                synchronized (aVar) {
                    aVar.notify();
                }
            }
            synchronized (a) {
                b.clear();
            }
        }
    }
}
