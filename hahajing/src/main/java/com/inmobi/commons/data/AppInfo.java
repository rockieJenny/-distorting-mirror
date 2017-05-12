package com.inmobi.commons.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;

public class AppInfo {
    private static String a;
    private static String b;
    private static String c;

    public static String getAppDisplayName() {
        return b;
    }

    private static void a(String str) {
        b = str;
    }

    public static String getAppBId() {
        return a;
    }

    private static void b(String str) {
        a = str;
    }

    public static String getAppVer() {
        return c;
    }

    private static void c(String str) {
        c = str;
    }

    public static void updateAppInfo() {
        try {
            Context context = InternalSDKUtil.getContext();
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                b(applicationInfo.packageName);
                a(applicationInfo.loadLabel(packageManager).toString());
            }
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 128);
            String str = null;
            if (packageInfo != null) {
                str = packageInfo.versionName;
                if (str == null || str.equals("")) {
                    str = packageInfo.versionCode + "";
                }
            }
            if (str != null && !str.equals("")) {
                c(str);
            }
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to fill AppInfo", e);
        }
    }
}
