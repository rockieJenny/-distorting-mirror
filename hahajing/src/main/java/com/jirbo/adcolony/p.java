package com.jirbo.adcolony;

import android.os.Handler;
import android.os.Message;

class p implements Runnable {
    public static final int a = 5;
    public static final int b = 10;
    static String c = "MONITOR_MUTEX";
    static volatile p d;
    static volatile long e;

    static class a extends Handler {
        a() {
            sendMessageDelayed(obtainMessage(), 1000);
        }

        public void handleMessage(Message m) {
            if (a.b().isFinishing()) {
                a.b("Monitor pinger exiting.");
                return;
            }
            if (a.b().hasWindowFocus()) {
                p.a();
            }
            sendMessageDelayed(obtainMessage(), 1000);
        }
    }

    p() {
    }

    static void a() {
        synchronized (c) {
            e = System.currentTimeMillis();
            if (d == null) {
                a.b("Creating ADC Monitor singleton.");
                d = new p();
                new Thread(d).start();
            }
        }
    }

    public void run() {
        a.a(a.n);
        l.a.b((Object) "ADC Monitor Started.");
        a.l.b();
        boolean z = false;
        while (!AdColony.activity().isFinishing()) {
            long j;
            long currentTimeMillis = System.currentTimeMillis();
            a.r = false;
            a.l.g();
            if (a.r) {
                j = 50;
            } else {
                j = (long) (z ? 2000 : 250);
            }
            int currentTimeMillis2 = (int) ((System.currentTimeMillis() - e) / 1000);
            a.l.g();
            if (z) {
                if (currentTimeMillis2 >= 10) {
                    break;
                } else if (currentTimeMillis2 < 5) {
                    a.l.b();
                    a.b("AdColony is active.");
                    z = false;
                }
            } else if (currentTimeMillis2 >= 5) {
                a.b("AdColony is idle.");
                a.l.c();
                z = true;
            }
            a(j);
            j = System.currentTimeMillis();
            if (j - currentTimeMillis <= 3000 && j - currentTimeMillis > 0) {
                v vVar = a.l.e;
                vVar.h = (((double) (j - currentTimeMillis)) / 1000.0d) + vVar.h;
            }
        }
        synchronized (c) {
            d = null;
        }
        if (!z) {
            a.l.c();
        }
        if (AdColony.activity().isFinishing()) {
            a.s = true;
            a(5000);
            if (a.s) {
                l.c.b((Object) "ADC.finishing, controller on_stop");
                a.l.d();
                aa.a();
            }
        }
        System.out.println("Exiting monitor");
    }

    void a(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
        }
    }
}
