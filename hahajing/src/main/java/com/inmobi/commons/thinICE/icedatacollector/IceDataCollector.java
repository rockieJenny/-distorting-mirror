package com.inmobi.commons.thinICE.icedatacollector;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.inmobi.commons.thinICE.cellular.CellOperatorInfo;
import com.inmobi.commons.thinICE.cellular.CellUtil;
import com.inmobi.commons.thinICE.location.LocationInfo;
import com.inmobi.commons.thinICE.location.LocationUtil;
import com.inmobi.commons.thinICE.wifi.WifiInfo;
import com.inmobi.commons.thinICE.wifi.WifiScanListener;
import com.inmobi.commons.thinICE.wifi.WifiScanner;
import com.inmobi.commons.thinICE.wifi.WifiUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class IceDataCollector {
    public static final String TAG = "IceDataCollector";
    private static Context a = null;
    private static ThinICEConfigSettings b = new ThinICEConfigSettings();
    private static Looper c = null;
    private static Handler d = null;
    private static boolean e = false;
    private static Activity f = null;
    private static Runnable g = new Runnable() {
        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r3 = this;
            r1 = com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.class;
            monitor-enter(r1);
            r0 = com.inmobi.commons.thinICE.icedatacollector.BuildSettings.DEBUG;	 Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x000e;
        L_0x0007:
            r0 = "IceDataCollector";
            r2 = "** stop runnable";
            android.util.Log.d(r0, r2);	 Catch:{ all -> 0x0040 }
        L_0x000e:
            r0 = com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.e;	 Catch:{ all -> 0x0040 }
            if (r0 != 0) goto L_0x0021;
        L_0x0014:
            r0 = com.inmobi.commons.thinICE.icedatacollector.BuildSettings.DEBUG;	 Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x001f;
        L_0x0018:
            r0 = "IceDataCollector";
            r2 = "ignoring, stop not requested";
            android.util.Log.d(r0, r2);	 Catch:{ all -> 0x0040 }
        L_0x001f:
            monitor-exit(r1);	 Catch:{ all -> 0x0040 }
        L_0x0020:
            return;
        L_0x0021:
            r0 = com.inmobi.commons.thinICE.icedatacollector.BuildSettings.DEBUG;	 Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x002c;
        L_0x0025:
            r0 = "IceDataCollector";
            r2 = "terminating sampling and flushing";
            android.util.Log.d(r0, r2);	 Catch:{ all -> 0x0040 }
        L_0x002c:
            com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.o();	 Catch:{ all -> 0x0040 }
            com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.flush();	 Catch:{ all -> 0x0040 }
            r0 = 0;
            com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.a = r0;	 Catch:{ all -> 0x0040 }
            r0 = 0;
            com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.f = r0;	 Catch:{ all -> 0x0040 }
            r0 = 0;
            com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.k = r0;	 Catch:{ all -> 0x0040 }
            monitor-exit(r1);	 Catch:{ all -> 0x0040 }
            goto L_0x0020;
        L_0x0040:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0040 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.inmobi.commons.thinICE.icedatacollector.IceDataCollector.1.run():void");
        }
    };
    private static LinkedList<Sample> h = new LinkedList();
    private static Sample i = null;
    private static final Object j = new Object();
    private static ThinICEListener k = null;
    private static ThinIceDataCollectedListener l = null;
    private static Runnable m = new Runnable() {
        public void run() {
            boolean z = false;
            if (BuildSettings.DEBUG) {
                Log.d(IceDataCollector.TAG, "** sample runnable");
            }
            if (IceDataCollector.c == null) {
                if (BuildSettings.DEBUG) {
                    Log.w(IceDataCollector.TAG, "sampling when looper is null, exiting");
                }
            } else if (VERSION.SDK_INT >= 14 || IceDataCollector.f.hasWindowFocus()) {
                IceDataCollector.d.postDelayed(IceDataCollector.m, IceDataCollector.b.getSampleInterval());
                if (BuildSettings.DEBUG) {
                    Log.d(IceDataCollector.TAG, "next sample in " + IceDataCollector.b.getSampleInterval() + " ms");
                }
                Sample sample = new Sample();
                int wifiFlags = IceDataCollector.b.getWifiFlags();
                if (!ThinICEConfigSettings.bitTest(wifiFlags, 2)) {
                    z = true;
                }
                final boolean bitTest = ThinICEConfigSettings.bitTest(wifiFlags, 1);
                int cellOpFlags = IceDataCollector.b.getCellOpFlags();
                boolean bitTest2 = ThinICEConfigSettings.bitTest(cellOpFlags, 1);
                boolean bitTest3 = ThinICEConfigSettings.bitTest(cellOpFlags, 2);
                if (IceDataCollector.b.isSampleCellOperatorEnabled()) {
                    try {
                        CellOperatorInfo cellNetworkInfo = CellUtil.getCellNetworkInfo(IceDataCollector.a);
                        if (bitTest2) {
                            cellNetworkInfo.simMcc = -1;
                            cellNetworkInfo.simMnc = -1;
                        }
                        if (bitTest3) {
                            cellNetworkInfo.currentMcc = -1;
                            cellNetworkInfo.currentMnc = -1;
                        }
                        if (BuildSettings.DEBUG) {
                            Log.d(IceDataCollector.TAG, "-- cell operator: " + cellNetworkInfo);
                        }
                        sample.cellOperator = cellNetworkInfo;
                    } catch (Throwable e) {
                        if (BuildSettings.DEBUG) {
                            Log.e(IceDataCollector.TAG, "Error getting cell operator data", e);
                        }
                    }
                }
                if (IceDataCollector.b.isSampleCellEnabled()) {
                    try {
                        if (CellUtil.hasGetCurrentServingCellPermission(IceDataCollector.a)) {
                            sample.connectedCellTowerInfo = CellUtil.getCurrentCellTower(IceDataCollector.a);
                            if (BuildSettings.DEBUG) {
                                Log.d(IceDataCollector.TAG, "-- current serving cell: " + sample.connectedCellTowerInfo.id);
                            }
                        } else if (BuildSettings.DEBUG) {
                            Log.w(IceDataCollector.TAG, "application does not have permission to access current serving cell");
                        }
                    } catch (Throwable e2) {
                        if (BuildSettings.DEBUG) {
                            Log.e(IceDataCollector.TAG, "Error getting cell data", e2);
                        }
                    }
                }
                if (IceDataCollector.b.isSampleVisibleCellTowerEnabled()) {
                    try {
                        if (CellUtil.hasGetVisibleCellTowerPermission(IceDataCollector.a)) {
                            sample.visibleCellTowerInfo = CellUtil.getVisibleCellTower(IceDataCollector.a);
                        } else if (BuildSettings.DEBUG) {
                            Log.w(IceDataCollector.TAG, "application does not have permission to access current serving cell");
                        }
                    } catch (Throwable e22) {
                        if (BuildSettings.DEBUG) {
                            Log.e(IceDataCollector.TAG, "Error getting cell data", e22);
                        }
                    }
                }
                sample.connectedWifiAp = IceDataCollector.getConnectedWifiInfo(IceDataCollector.a);
                if (IceDataCollector.b.isSampleLocationEnabled()) {
                    try {
                        if (LocationUtil.hasLocationPermission(IceDataCollector.a)) {
                            HashMap lastKnownLocations = LocationUtil.getLastKnownLocations(IceDataCollector.a);
                            if (BuildSettings.DEBUG) {
                                Log.d(IceDataCollector.TAG, "-- locations:");
                                for (LocationInfo locationInfo : lastKnownLocations.values()) {
                                    Log.d(IceDataCollector.TAG, "   + " + locationInfo);
                                }
                            }
                            sample.lastKnownLocations = lastKnownLocations;
                        } else if (BuildSettings.DEBUG) {
                            Log.w(IceDataCollector.TAG, "application does not have permission to access location");
                        }
                    } catch (Throwable e222) {
                        if (BuildSettings.DEBUG) {
                            Log.e(IceDataCollector.TAG, "Error getting location data", e222);
                        }
                    }
                }
                synchronized (IceDataCollector.j) {
                    IceDataCollector.i = sample;
                    if (IceDataCollector.h != null) {
                        IceDataCollector.h.add(sample);
                        while (IceDataCollector.h.size() > IceDataCollector.b.getSampleHistorySize()) {
                            IceDataCollector.h.removeFirst();
                        }
                    }
                }
                if (IceDataCollector.b.isSampleVisibleWifiEnabled()) {
                    try {
                        if (WifiUtil.hasWifiScanPermission(IceDataCollector.a, false)) {
                            if (!WifiScanner.requestScan(IceDataCollector.a, new WifiScanListener(this) {
                                final /* synthetic */ AnonymousClass2 c;

                                public void onTimeout() {
                                    if (BuildSettings.DEBUG) {
                                        Log.w(IceDataCollector.TAG, "Received Wi-Fi scan timeout");
                                    }
                                }

                                public void onResultsReceived(List<ScanResult> list) {
                                    if (BuildSettings.DEBUG) {
                                        Log.d(IceDataCollector.TAG, "Received Wi-Fi scan results " + list.size());
                                    }
                                    List<WifiInfo> scanResultsToWifiInfos = WifiUtil.scanResultsToWifiInfos(list, z, bitTest);
                                    if (BuildSettings.DEBUG) {
                                        Log.d(IceDataCollector.TAG, "-- wifi scan:");
                                        for (WifiInfo wifiInfo : scanResultsToWifiInfos) {
                                            Log.d(IceDataCollector.TAG, "   + " + wifiInfo);
                                        }
                                    }
                                    synchronized (IceDataCollector.j) {
                                        if (IceDataCollector.i != null) {
                                            IceDataCollector.i.visibleWifiAp = scanResultsToWifiInfos;
                                            this.c.a();
                                        }
                                    }
                                }
                            })) {
                                a();
                                return;
                            }
                            return;
                        }
                        a();
                        if (BuildSettings.DEBUG) {
                            Log.w(IceDataCollector.TAG, "application does not have permission to scan for wifi access points");
                            return;
                        }
                        return;
                    } catch (Throwable e2222) {
                        if (BuildSettings.DEBUG) {
                            Log.e(IceDataCollector.TAG, "Error scanning for wifi", e2222);
                            return;
                        }
                        return;
                    }
                }
                a();
            } else {
                if (BuildSettings.DEBUG) {
                    Log.d(IceDataCollector.TAG, "activity no longer has focus, terminating");
                }
                IceDataCollector.o();
                List e3 = IceDataCollector.h;
                IceDataCollector.flush();
                IceDataCollector.a = null;
                IceDataCollector.f = null;
                if (IceDataCollector.k != null) {
                    IceDataCollector.k.onSamplingTerminated(e3);
                }
                IceDataCollector.k = null;
            }
        }

        private void a() {
            if (IceDataCollector.l != null) {
                IceDataCollector.l.onDataCollected();
            }
        }
    };

    public interface ThinIceDataCollectedListener {
        void onDataCollected();
    }

    public static synchronized void start(Context context) {
        synchronized (IceDataCollector.class) {
            if (BuildSettings.DEBUG) {
                Log.d(TAG, "-- start()");
            }
            if (context == null) {
                if (BuildSettings.DEBUG) {
                    Log.d(TAG, "aborting, context is null");
                }
            } else if (b.isEnabled()) {
                if (VERSION.SDK_INT < 14) {
                    if (context instanceof Activity) {
                        f = (Activity) context;
                    } else if (BuildSettings.DEBUG) {
                        Log.w(TAG, "aborting, build < 14 and context is not an activity");
                    }
                }
                a = context.getApplicationContext();
                n();
                if (e) {
                    if (BuildSettings.DEBUG) {
                        Log.d(TAG, "stop previously requested, clearing request");
                    }
                    e = false;
                    d.removeCallbacks(g);
                }
            } else if (BuildSettings.DEBUG) {
                Log.d(TAG, "ignoring, data collection is disabled in settings");
            }
        }
    }

    private static void n() {
        if (BuildSettings.DEBUG) {
            Log.d(TAG, "startSampling()");
        }
        if (c == null) {
            HandlerThread handlerThread = new HandlerThread("IDC");
            handlerThread.start();
            c = handlerThread.getLooper();
            d = new Handler(c);
            d.postDelayed(m, b.getSampleInterval() / 2);
            if (BuildSettings.DEBUG) {
                Log.d(TAG, "next sample in " + (b.getSampleInterval() / 2) + " ms");
            }
        } else if (BuildSettings.DEBUG) {
            Log.d(TAG, "ignoring, already sampling");
        }
    }

    public static synchronized void stop() {
        synchronized (IceDataCollector.class) {
            if (BuildSettings.DEBUG) {
                Log.d(TAG, "-- stop()");
            }
            if (c == null) {
                Log.d(TAG, "ignoring, not currently running");
            } else if (e) {
                Log.d(TAG, "ignoring, stop already requested");
            } else {
                e = true;
                d.postDelayed(g, b.getStopRequestTimeout());
                if (BuildSettings.DEBUG) {
                    Log.d(TAG, "stop requested, occurring in " + b.getStopRequestTimeout() + " ms");
                }
            }
        }
    }

    private static void o() {
        if (BuildSettings.DEBUG) {
            Log.d(TAG, "stopSampling()");
        }
        if (c != null) {
            d.removeCallbacks(m);
            d = null;
            c.quit();
            c = null;
            if (BuildSettings.DEBUG) {
                Log.d(TAG, "sampling stopped");
            }
        } else if (BuildSettings.DEBUG) {
            Log.d(TAG, "ignoring, not currently sampling");
        }
    }

    public static synchronized void flush() {
        synchronized (IceDataCollector.class) {
            if (BuildSettings.DEBUG) {
                Log.d(TAG, "-- flush()");
            }
            synchronized (j) {
                i = null;
                h = new LinkedList();
            }
        }
    }

    public static synchronized void setConfig(ThinICEConfigSettings thinICEConfigSettings) {
        synchronized (IceDataCollector.class) {
            if (BuildSettings.DEBUG) {
                Log.d(TAG, "-- setConfig(" + thinICEConfigSettings + ")");
            }
            if (thinICEConfigSettings != null) {
                ThinICEConfigSettings thinICEConfigSettings2 = b;
                b = thinICEConfigSettings;
                int sampleHistorySize = thinICEConfigSettings2.getSampleHistorySize();
                int sampleHistorySize2 = b.getSampleHistorySize();
                if (sampleHistorySize2 < sampleHistorySize) {
                    synchronized (j) {
                        if (h.size() > sampleHistorySize2) {
                            if (BuildSettings.DEBUG) {
                                Log.d(TAG, "new sample history size, removing samples from start of list");
                            }
                            h.subList(0, h.size() - sampleHistorySize2).clear();
                        }
                    }
                }
                if (c == null) {
                    if (BuildSettings.DEBUG) {
                        Log.d(TAG, "not restarting sampling, not currently sampling");
                    }
                } else if (a(thinICEConfigSettings2, b)) {
                    o();
                    if (b.isEnabled()) {
                        n();
                    } else {
                        flush();
                        a = null;
                        f = null;
                        k = null;
                    }
                }
            } else if (BuildSettings.DEBUG) {
                Log.d(TAG, "aborting, config is null");
            }
        }
    }

    private static final boolean a(ThinICEConfigSettings thinICEConfigSettings, ThinICEConfigSettings thinICEConfigSettings2) {
        return (thinICEConfigSettings.isEnabled() == thinICEConfigSettings2.isEnabled() && thinICEConfigSettings.getSampleInterval() == thinICEConfigSettings2.getSampleInterval()) ? false : true;
    }

    public static ThinICEConfigSettings getConfig() {
        return new ThinICEConfigSettings(b);
    }

    public static List<Sample> getData() {
        List list;
        if (BuildSettings.DEBUG) {
            Log.d(TAG, "-- getData()");
        }
        synchronized (j) {
            list = h;
            flush();
        }
        return list;
    }

    public static int getSampleCount() {
        int size;
        synchronized (j) {
            size = h.size();
        }
        return size;
    }

    public static void setListener(ThinICEListener thinICEListener) {
        k = thinICEListener;
    }

    public static void setIceDataCollectionListener(ThinIceDataCollectedListener thinIceDataCollectedListener) {
        l = thinIceDataCollectedListener;
    }

    public static WifiInfo getConnectedWifiInfo(Context context) {
        int wifiFlags = b.getWifiFlags();
        boolean z = !ThinICEConfigSettings.bitTest(wifiFlags, 2);
        boolean bitTest = ThinICEConfigSettings.bitTest(wifiFlags, 1);
        if (!b.isSampleConnectedWifiEnabled()) {
            return null;
        }
        try {
            WifiInfo connectedWifiInfo;
            if (WifiUtil.hasGetConnectedWifiInfoPermission(context)) {
                connectedWifiInfo = WifiUtil.getConnectedWifiInfo(context, z, bitTest);
            } else {
                if (BuildSettings.DEBUG) {
                    Log.w(TAG, "application does not have permission to access connected wifi ap");
                }
                connectedWifiInfo = null;
            }
            return connectedWifiInfo;
        } catch (Throwable e) {
            if (!BuildSettings.DEBUG) {
                return null;
            }
            Log.e(TAG, "Error getting wifi data", e);
            return null;
        }
    }

    public static List<Long> getVisbleWifiInfoBssId(Context context) {
        Throwable th;
        List<Long> list = null;
        try {
            if (!b.isSampleVisibleWifiEnabled() || i == null) {
                return null;
            }
            List list2 = i.visibleWifiAp;
            if (list2 == null || list2.size() == 0) {
                return null;
            }
            List<Long> arrayList = new ArrayList();
            int i = 0;
            while (i < list2.size()) {
                try {
                    arrayList.add(Long.valueOf(((WifiInfo) list2.get(i)).bssid));
                    i++;
                } catch (Throwable e) {
                    Throwable th2 = e;
                    list = arrayList;
                    th = th2;
                }
            }
            return arrayList;
        } catch (Exception e2) {
            th = e2;
            if (!BuildSettings.DEBUG) {
                return list;
            }
            Log.e(TAG, "Error getting wifi data", th);
            return list;
        }
    }
}
