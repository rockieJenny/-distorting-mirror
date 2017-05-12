package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.flurry.android.impl.ads.protocol.v13.ScreenOrientationType;

public final class cn {
    private static final String a = cn.class.getSimpleName();
    private static final SparseArray<SparseIntArray> b = c();

    public static boolean a(Context context) {
        boolean z = (context.getResources().getConfiguration().screenLayout & 15) >= 3;
        gd.b(a, "isTablet " + z);
        return z;
    }

    public static void a(Activity activity, int i) {
        if (activity == null) {
            gd.b(a, "Context is null. Cannot set requested orientation.");
        } else if (a((Context) activity)) {
            gd.b(a, "setOrientation SCREEN_ORIENTATION_SENSOR");
            activity.setRequestedOrientation(4);
        } else {
            gd.b(a, "setOrientation " + i);
            activity.setRequestedOrientation(i);
        }
    }

    public static int a() {
        return 7;
    }

    public static int b() {
        return 6;
    }

    public static boolean a(Activity activity, int i, boolean z) {
        if (activity == null) {
            return false;
        }
        if (!b(activity)) {
            int b = b(activity, i);
            if (-1 == b) {
                gd.a(5, a, "cannot set requested orientation without restarting activity, requestedOrientation = " + i);
                gd.b(a, "cannot set requested orientation without restarting activity. It is recommended to add keyboardHidden|orientation|screenSize for android:configChanges attribute for activity: " + activity.getComponentName().getClassName());
                return false;
            }
            i = b;
            z = true;
        }
        activity.setRequestedOrientation(i);
        if (z) {
            return true;
        }
        activity.setRequestedOrientation(-1);
        return true;
    }

    public static ActivityInfo a(PackageManager packageManager, ComponentName componentName) {
        ActivityInfo activityInfo = null;
        if (!(packageManager == null || componentName == null)) {
            try {
                activityInfo = packageManager.getActivityInfo(componentName, 0);
            } catch (NameNotFoundException e) {
                gd.a(5, a, "cannot find info for activity: " + componentName);
            }
        }
        return activityInfo;
    }

    public static ActivityInfo a(Activity activity) {
        if (activity == null) {
            return null;
        }
        return a(activity.getPackageManager(), activity.getComponentName());
    }

    public static boolean b(Activity activity) {
        int a = a(a(activity));
        if ((a & 128) == 0 || (a & 1024) == 0) {
            return false;
        }
        return true;
    }

    @TargetApi(13)
    public static int a(ActivityInfo activityInfo) {
        if (activityInfo == null) {
            return 0;
        }
        int i = activityInfo.configChanges;
        if (activityInfo.applicationInfo.targetSdkVersion < 13) {
            return i | 3072;
        }
        return i;
    }

    public static int b(Activity activity, int i) {
        if (activity == null) {
            return -1;
        }
        return a(activity, i, activity.getResources().getConfiguration().orientation);
    }

    public static int a(Activity activity, int i, int i2) {
        if (activity == null) {
            return -1;
        }
        int i3;
        SparseIntArray sparseIntArray = (SparseIntArray) b.get(i2);
        if (sparseIntArray != null) {
            i3 = sparseIntArray.get(i, -1);
        } else {
            i3 = -1;
        }
        return i3;
    }

    public static int a(Activity activity, ScreenOrientationType screenOrientationType) {
        int i = 0;
        if (ScreenOrientationType.PORTRAIT.equals(screenOrientationType)) {
            i = 1;
        } else if (ScreenOrientationType.LANDSCAPE.equals(screenOrientationType)) {
            i = 2;
        }
        return a(activity, -1, i);
    }

    private static SparseArray<SparseIntArray> c() {
        SparseArray<SparseIntArray> sparseArray = new SparseArray();
        sparseArray.put(1, d());
        sparseArray.put(2, e());
        return sparseArray;
    }

    private static SparseIntArray d() {
        int a = a();
        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(-1, a);
        sparseIntArray.put(2, a);
        sparseIntArray.put(3, a);
        sparseIntArray.put(4, a);
        sparseIntArray.put(1, 1);
        sparseIntArray.put(5, 5);
        sparseIntArray.put(7, 7);
        sparseIntArray.put(9, 9);
        sparseIntArray.put(10, 7);
        return sparseIntArray;
    }

    private static SparseIntArray e() {
        int b = b();
        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(-1, b);
        sparseIntArray.put(2, b);
        sparseIntArray.put(3, b);
        sparseIntArray.put(4, b);
        sparseIntArray.put(0, 0);
        sparseIntArray.put(5, 5);
        sparseIntArray.put(6, 6);
        sparseIntArray.put(8, 8);
        sparseIntArray.put(10, 6);
        return sparseIntArray;
    }
}
