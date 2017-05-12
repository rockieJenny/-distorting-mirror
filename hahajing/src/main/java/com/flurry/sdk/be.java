package com.flurry.sdk;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.List;

public final class be implements bi {
    private static final String a = be.class.getSimpleName();
    private static final SparseArray<String> b = a();

    public boolean a(Context context, bm bmVar) {
        if (bmVar == null) {
            return false;
        }
        Object a = bmVar.a();
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        List<ActivityInfo> e = bmVar.e();
        if (e == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        boolean z = true;
        for (ActivityInfo a2 : e) {
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

    private boolean a(String str, PackageManager packageManager, String str2, ActivityInfo activityInfo) {
        if (TextUtils.isEmpty(str) || packageManager == null || TextUtils.isEmpty(str2) || activityInfo == null || TextUtils.isEmpty(activityInfo.name)) {
            return false;
        }
        ActivityInfo a = cn.a(packageManager, new ComponentName(str2, activityInfo.name));
        if (a == null) {
            gd.b(a, str + ": package=\"" + str2 + "\": AndroidManifest.xml should include activity node with android:name=\"" + activityInfo.name + "\"");
            return false;
        }
        boolean z;
        gd.a(3, a, str + ": package=\"" + str2 + "\": AndroidManifest.xml has activity node with android:name=\"" + activityInfo.name + "\"");
        int i = activityInfo.configChanges;
        if (i != 0) {
            int a2 = cn.a(a);
            SparseArray b = b();
            z = true;
            for (int i2 = 0; i2 < b.size(); i2++) {
                int keyAt = b.keyAt(i2);
                if ((keyAt & i) != 0) {
                    if ((keyAt & a2) == 0) {
                        gd.b(a, str + ": package=\"" + str2 + "\": AndroidManifest.xml activity node with android:name=\"" + activityInfo.name + "\" should include android:configChanges value=\"" + ((String) b.valueAt(i2)) + "\"");
                        z = false;
                    } else {
                        gd.a(3, a, str + ": package=\"" + str2 + "\": AndroidManifest.xml activity node with " + "android:name=\"" + activityInfo.name + "\" has android:configChanges value=\"" + ((String) b.valueAt(i2)) + "\"");
                    }
                }
            }
        } else {
            z = true;
        }
        return z;
    }

    @TargetApi(13)
    private static SparseArray<String> a() {
        SparseArray<String> sparseArray = new SparseArray();
        sparseArray.append(1, "mcc");
        sparseArray.append(2, "mnc");
        sparseArray.append(4, "locale");
        sparseArray.append(8, "touchscreen");
        sparseArray.append(16, "keyboard");
        sparseArray.append(32, "keyboardHidden");
        sparseArray.append(64, "navigation");
        sparseArray.append(128, "orientation");
        sparseArray.append(256, "screenLayout");
        sparseArray.append(512, "uiMode");
        sparseArray.append(1024, "screenSize");
        sparseArray.append(2048, "smallestScreenSize");
        return sparseArray;
    }

    private static SparseArray<String> b() {
        return b;
    }
}
