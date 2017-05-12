package com.flurry.sdk;

import android.telephony.TelephonyManager;

public class fk {
    private static fk a;
    private static final String b = fk.class.getSimpleName();

    public static synchronized fk a() {
        fk fkVar;
        synchronized (fk.class) {
            if (a == null) {
                a = new fk();
            }
            fkVar = a;
        }
        return fkVar;
    }

    public static void b() {
        a = null;
    }

    private fk() {
    }

    public String c() {
        TelephonyManager telephonyManager = (TelephonyManager) fp.a().c().getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperatorName();
    }

    public String d() {
        TelephonyManager telephonyManager = (TelephonyManager) fp.a().c().getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperator();
    }
}
