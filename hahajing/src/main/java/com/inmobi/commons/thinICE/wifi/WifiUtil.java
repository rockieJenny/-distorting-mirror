package com.inmobi.commons.thinICE.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.inmobi.commons.thinICE.icedatacollector.IceDataCollector;
import com.inmobi.commons.thinICE.icedatacollector.ThinICEConfigSettings;
import java.util.ArrayList;
import java.util.List;

public final class WifiUtil {
    private static final String[] a = new String[]{"android.permission.ACCESS_WIFI_STATE"};
    private static final String[] b = new String[]{"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE"};
    private static final String[] c = new String[]{"android.permission.WAKE_LOCK"};

    public static WifiInfo getConnectedWifiInfo(Context context, boolean z, boolean z2) {
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo == null) {
            return null;
        }
        WifiInfo wifiInfo = new WifiInfo();
        String bssid = connectionInfo.getBSSID();
        String ssid = connectionInfo.getSSID();
        if (bssid == null || a(z, ssid)) {
            return null;
        }
        wifiInfo.bssid = macToLong(bssid);
        if (z2) {
            ssid = null;
        }
        wifiInfo.ssid = ssid;
        wifiInfo.rssi = connectionInfo.getRssi();
        wifiInfo.ip = connectionInfo.getIpAddress();
        return wifiInfo;
    }

    public static WifiInfo getConnectedWifiInfo(Context context) {
        int wifiFlags = IceDataCollector.getConfig().getWifiFlags();
        return getConnectedWifiInfo(context, !ThinICEConfigSettings.bitTest(wifiFlags, 2), ThinICEConfigSettings.bitTest(wifiFlags, 1));
    }

    public static boolean hasGetConnectedWifiInfoPermission(Context context) {
        for (String checkCallingOrSelfPermission : a) {
            if (context.checkCallingOrSelfPermission(checkCallingOrSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasWifiScanPermission(Context context, boolean z) {
        for (String checkCallingOrSelfPermission : b) {
            if (context.checkCallingOrSelfPermission(checkCallingOrSelfPermission) != 0) {
                return false;
            }
        }
        if (z) {
            for (String checkCallingOrSelfPermission2 : c) {
                if (context.checkCallingOrSelfPermission(checkCallingOrSelfPermission2) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<WifiInfo> scanResultsToWifiInfos(List<ScanResult> list, boolean z, boolean z2) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (ScanResult scanResult : list) {
            if (!a(z, scanResult.SSID)) {
                arrayList.add(scanResultToWifiInfo(scanResult, z2));
            }
        }
        return arrayList;
    }

    private static boolean a(boolean z, String str) {
        return z && str != null && str.endsWith("_nomap");
    }

    public static WifiInfo scanResultToWifiInfo(ScanResult scanResult, boolean z) {
        String str = null;
        if (scanResult == null) {
            return null;
        }
        WifiInfo wifiInfo = new WifiInfo();
        wifiInfo.bssid = macToLong(scanResult.BSSID);
        if (!z) {
            str = scanResult.SSID;
        }
        wifiInfo.ssid = str;
        wifiInfo.rssi = scanResult.level;
        return wifiInfo;
    }

    public static long macToLong(String str) {
        String[] split = str.split("\\:");
        byte[] bArr = new byte[6];
        for (int i = 0; i < 6; i++) {
            bArr[i] = (byte) Integer.parseInt(split[i], 16);
        }
        return a(bArr);
    }

    private static long a(byte[] bArr) {
        if (bArr == null || bArr.length != 6) {
            return 0;
        }
        return ((((a(bArr[5]) | (a(bArr[4]) << 8)) | (a(bArr[3]) << 16)) | (a(bArr[2]) << 24)) | (a(bArr[1]) << 32)) | (a(bArr[0]) << 40);
    }

    private static long a(byte b) {
        return ((long) b) & 255;
    }
}
