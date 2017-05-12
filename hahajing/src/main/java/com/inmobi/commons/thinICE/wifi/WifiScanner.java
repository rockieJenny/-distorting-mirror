package com.inmobi.commons.thinICE.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public final class WifiScanner {
    private static Context a = null;
    private static WifiScanListener b = null;
    private static Handler c = null;
    private static boolean d = false;
    private static Runnable e = new Runnable() {
        public void run() {
            WifiScanListener a = WifiScanner.b;
            WifiScanner.d();
            if (a != null) {
                a.onTimeout();
            }
        }
    };
    private static boolean f = false;
    private static final BroadcastReceiver g = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                WifiScanListener a = WifiScanner.b;
                WifiManager wifiManager = (WifiManager) WifiScanner.a.getSystemService("wifi");
                WifiScanner.d();
                if (a != null) {
                    a.onResultsReceived(wifiManager.getScanResults());
                }
            } catch (Exception e) {
            }
        }
    };
    private static final IntentFilter h = new IntentFilter("android.net.wifi.SCAN_RESULTS");
    private static WakeLock i = null;
    private static WifiLock j = null;

    public static boolean requestScan(Context context, WifiScanListener wifiScanListener) {
        return requestScan(Looper.myLooper(), context, wifiScanListener, 10000, false);
    }

    public static synchronized boolean requestScan(Looper looper, Context context, WifiScanListener wifiScanListener, long j, boolean z) {
        boolean z2;
        synchronized (WifiScanner.class) {
            if (c != null) {
                z2 = false;
            } else {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                if (wifiManager.isWifiEnabled()) {
                    a = context;
                    b = wifiScanListener;
                    d = z;
                    c = new Handler(looper);
                    c.postDelayed(e, j);
                    if (d) {
                        i();
                        g();
                    }
                    e();
                    z2 = wifiManager.startScan();
                } else {
                    z2 = false;
                }
            }
        }
        return z2;
    }

    private static synchronized void d() {
        synchronized (WifiScanner.class) {
            if (c != null) {
                c.removeCallbacks(e);
                f();
                if (d) {
                    h();
                    j();
                }
                c = null;
                b = null;
                a = null;
                d = false;
            }
        }
    }

    private static void e() {
        if (!f) {
            f = true;
            a.registerReceiver(g, h, null, c);
        }
    }

    private static void f() {
        if (f) {
            f = false;
            try {
                a.unregisterReceiver(g);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    private static void g() {
        if (i == null) {
            i = ((PowerManager) a.getSystemService("power")).newWakeLock(1, "wifiscanrequester.CpuLock");
            i.setReferenceCounted(false);
        }
        if (!i.isHeld()) {
            i.acquire();
        }
    }

    private static void h() {
        if (i != null) {
            if (i.isHeld()) {
                i.release();
            }
            i = null;
        }
    }

    private static void i() {
        if (j == null) {
            j = ((WifiManager) a.getSystemService("wifi")).createWifiLock(2, "wifiscanrequester.WiFiScanLock");
            j.setReferenceCounted(false);
        }
        if (!j.isHeld()) {
            j.acquire();
        }
    }

    private static void j() {
        if (j != null) {
            if (j.isHeld()) {
                j.release();
            }
            j = null;
        }
    }
}
