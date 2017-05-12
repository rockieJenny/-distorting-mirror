package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.analytics.tracking.android.Fields;

class g implements q {
    private static Object xO = new Object();
    private static g ye;
    protected String ya;
    protected String yb;
    protected String yc;
    protected String yd;

    protected g() {
    }

    private g(Context context) {
        PackageManager packageManager = context.getPackageManager();
        this.yc = context.getPackageName();
        this.yd = packageManager.getInstallerPackageName(this.yc);
        String str = this.yc;
        String str2 = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                str = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                str2 = packageInfo.versionName;
            }
        } catch (NameNotFoundException e) {
            ae.T("Error retrieving package info: appName set to " + str);
        }
        this.ya = str;
        this.yb = str2;
    }

    public static g dZ() {
        return ye;
    }

    public static void y(Context context) {
        synchronized (xO) {
            if (ye == null) {
                ye = new g(context);
            }
        }
    }

    public boolean ac(String str) {
        return Fields.APP_NAME.equals(str) || Fields.APP_VERSION.equals(str) || Fields.APP_ID.equals(str) || Fields.APP_INSTALLER_ID.equals(str);
    }

    public String getValue(String field) {
        if (field == null) {
            return null;
        }
        if (field.equals(Fields.APP_NAME)) {
            return this.ya;
        }
        if (field.equals(Fields.APP_VERSION)) {
            return this.yb;
        }
        if (field.equals(Fields.APP_ID)) {
            return this.yc;
        }
        return field.equals(Fields.APP_INSTALLER_ID) ? this.yd : null;
    }
}
