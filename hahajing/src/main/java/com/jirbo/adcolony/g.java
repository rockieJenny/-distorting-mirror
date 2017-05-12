package com.jirbo.adcolony;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import java.util.Locale;

class g {
    static String a;
    static boolean b;

    g() {
    }

    static String a() {
        return Secure.getString(AdColony.activity().getContentResolver(), "android_id");
    }

    static String b() {
        String networkOperatorName = ((TelephonyManager) AdColony.activity().getSystemService("phone")).getNetworkOperatorName();
        if (networkOperatorName.length() == 0) {
            return "unknown";
        }
        return networkOperatorName;
    }

    static int c() {
        Context applicationContext = a.b().getApplicationContext();
        a.b();
        return ((ActivityManager) applicationContext.getSystemService("activity")).getMemoryClass();
    }

    static long d() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / ((long) AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START);
    }

    static String e() {
        return aj.a(a.b());
    }

    static int f() {
        return a.b().getWindowManager().getDefaultDisplay().getWidth();
    }

    static int g() {
        return a.b().getWindowManager().getDefaultDisplay().getHeight();
    }

    static String h() {
        return "";
    }

    static boolean i() {
        if (a.Y != null) {
            return a.Y.equals("tablet");
        } else {
            DisplayMetrics displayMetrics = AdColony.activity().getResources().getDisplayMetrics();
            float f = ((float) displayMetrics.widthPixels) / displayMetrics.xdpi;
            float f2 = ((float) displayMetrics.heightPixels) / displayMetrics.ydpi;
            if (Math.sqrt((double) ((f2 * f2) + (f * f))) < 6.0d) {
                return false;
            }
            return true;
        }
    }

    static String j() {
        return Locale.getDefault().getLanguage();
    }

    static String k() {
        try {
            return ((WifiManager) AdColony.activity().getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (RuntimeException e) {
            return null;
        }
    }

    static String l() {
        return Build.MANUFACTURER;
    }

    static String m() {
        return Build.MODEL;
    }

    static String n() {
        return "";
    }

    static String o() {
        return VERSION.RELEASE;
    }
}
