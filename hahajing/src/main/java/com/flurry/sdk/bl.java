package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import java.util.List;

public final class bl implements bi {
    private static final String a = bl.class.getSimpleName();

    public boolean a(Context context, bm bmVar) {
        if (bmVar == null) {
            return false;
        }
        Object a = bmVar.a();
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        List<String> d = bmVar.d();
        if (d == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        boolean z = true;
        for (String a2 : d) {
            boolean z2;
            if (a(a, packageManager, packageName, a2)) {
                z2 = z;
            } else {
                z2 = false;
            }
            z = z2;
        }
        return z;
    }

    private boolean a(String str, PackageManager packageManager, String str2, String str3) {
        if (TextUtils.isEmpty(str) || packageManager == null || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return false;
        }
        if (-1 == packageManager.checkPermission(str3, str2)) {
            gd.b(a, str + ": package=\"" + str2 + "\": AndroidManifest.xml should include uses-permission node with " + "android:name=\"" + str3 + "\"");
            return false;
        }
        gd.a(3, a, str + ": package=\"" + str2 + "\": AndroidManifest.xml has uses-permission node with " + "android:name=\"" + str3 + "\"");
        return true;
    }
}
