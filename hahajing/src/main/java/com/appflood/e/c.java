package com.appflood.e;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import java.io.File;
import java.util.Locale;

public final class c {
    private static boolean A = false;
    public static String a;
    public static String b;
    public static String c;
    public static String d;
    public static String e;
    public static String f;
    public static int g;
    public static int h;
    public static boolean i = false;
    public static int j;
    public static String k;
    public static String l;
    public static String m;
    public static String n;
    public static String o;
    public static String p;
    public static double q;
    public static double r;
    public static int s;
    public static boolean t = false;
    public static boolean u;
    public static boolean v;
    public static String w;
    public static String x;
    public static String y;
    public static String z;

    public static void a(Context context) {
        "sysutils initialize is_initialed = " + A;
        j.a();
        if (!A) {
            TelephonyManager telephonyManager;
            try {
                telephonyManager = (TelephonyManager) context.getSystemService("phone");
            } catch (Throwable th) {
                telephonyManager = null;
                j.a();
            }
            try {
                a = telephonyManager.getDeviceId();
            } catch (Throwable th2) {
            }
            try {
                c = telephonyManager.getSubscriberId();
            } catch (Throwable th3) {
            }
            try {
                o = telephonyManager.getSimOperator();
            } catch (Throwable th4) {
            }
            try {
                p = telephonyManager.getSimOperatorName();
            } catch (Throwable th5) {
            }
            if (a == null) {
                a = "";
            }
            if (c == null) {
                c = "";
            }
            if (f == null) {
                f = "";
            }
            if (o == null) {
                o = "";
            }
            if (p == null) {
                p = "";
            }
            try {
                LocationManager locationManager = (LocationManager) context.getSystemService("location");
                Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
                if (lastKnownLocation != null) {
                    q = lastKnownLocation.getLatitude();
                    r = lastKnownLocation.getLongitude();
                }
            } catch (Throwable th6) {
            }
            j = j.a(VERSION.SDK, 3);
            k = Build.MODEL;
            l = Build.BRAND;
            String str = Build.DEVICE;
            m = Build.MANUFACTURER;
            d = VERSION.RELEASE;
            n = Build.DISPLAY;
            str = Build.PRODUCT;
            str = Build.BOARD;
            str = VERSION.INCREMENTAL;
            str = Build.USER;
            str = Build.HOST;
            if (j < 8) {
                System.setProperty("http.keepAlive", "false");
            }
            try {
                b = Secure.getString(context.getContentResolver(), "android_id");
            } catch (Throwable th7) {
                j.b(th7, "Failed to get ANDROID_ID");
            }
            if (b == null) {
                b = "";
            }
            try {
                Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
                g = defaultDisplay.getWidth();
                h = defaultDisplay.getHeight();
            } catch (Throwable th72) {
                j.a(th72, "Failed to get display info");
            }
            try {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory(), "__af_tmp");
                    if (file.exists()) {
                        file.delete();
                    }
                    if (file.createNewFile()) {
                        i = true;
                        file.delete();
                    }
                }
            } catch (Throwable th722) {
                j.b(th722, "Failed to test external storage writable");
            }
            try {
                e = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            } catch (Throwable th8) {
            }
            if (e == null) {
                e = "";
            }
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                u = connectivityManager.getNetworkInfo(0).getState() == State.CONNECTED;
                v = connectivityManager.getNetworkInfo(1).getState() == State.CONNECTED;
            } catch (Throwable th9) {
            }
            try {
                s = 0;
                "Google Play is supported on this device. (" + context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.tencent.mobileqq")), 0).size() + ")";
                j.a();
                s = 1;
            } catch (Throwable th10) {
            }
            try {
                Locale locale = Locale.getDefault();
                w = locale.getISO3Country();
                x = locale.getISO3Language();
            } catch (Throwable th11) {
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.densityDpi;
            str = a.a(context, "google_refer");
            if (j.g(y)) {
                y = str;
            }
            "Sysutils refer =  " + y;
            j.a();
            z = new WebView(context).getSettings().getUserAgentString();
            if (context.checkCallingOrSelfPermission("android.permission.CALL_PHONE") == 0) {
                t = true;
            }
            A = true;
        }
    }

    public static boolean a() {
        if (j.g(o)) {
            if (Locale.getDefault().toString().equals(Locale.CHINA.toString())) {
                return true;
            }
        } else if (o.startsWith("460")) {
            return true;
        }
        return false;
    }
}
