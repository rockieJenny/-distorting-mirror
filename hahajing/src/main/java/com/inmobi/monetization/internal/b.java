package com.inmobi.monetization.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.util.Log;
import com.inmobi.androidsdk.IMBrowserActivity;

/* compiled from: AdNetworkUtil */
class b {
    private static boolean a = false;

    public static void a(Context context) throws InvalidManifestConfigException {
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_INTERNET_PERMISSION);
            throw new InvalidManifestConfigException(-1);
        } else if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_ACCESS_NETWORK_PERMISSION);
            throw new InvalidManifestConfigException(-9);
        } else {
            for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(new Intent(context, IMBrowserActivity.class), 65536)) {
                if (resolveInfo.activityInfo.name.contentEquals("com.inmobi.androidsdk.IMBrowserActivity")) {
                    break;
                }
            }
            ResolveInfo resolveInfo2 = null;
            if (resolveInfo2 == null) {
                Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_ACTIVITY_DECLARATION);
                throw new InvalidManifestConfigException(-2);
            }
            int i = resolveInfo2.activityInfo.configChanges;
            if (i == 0) {
                Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_CONFIG_CHANGES);
                throw new InvalidManifestConfigException(-3);
            } else if ((i & 16) == 0) {
                Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_CONFIG_KEYBOARD);
                throw new InvalidManifestConfigException(-4);
            } else if ((i & 32) == 0) {
                Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_CONFIG_KEYBOARDHIDDEN);
                throw new InvalidManifestConfigException(-5);
            } else if ((i & 128) == 0) {
                Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_CONFIG_ORIENTATION);
                throw new InvalidManifestConfigException(-6);
            } else if (VERSION.SDK_INT < 13) {
            } else {
                if ((i & 1024) == 0) {
                    Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_CONFIG_SCREENSIZE);
                    throw new InvalidManifestConfigException(-7);
                } else if ((i & 2048) == 0) {
                    Log.e(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_MISSING_CONFIG_SMALLEST_SCREENSIZE);
                    throw new InvalidManifestConfigException(-8);
                }
            }
        }
    }

    public static Activity a(Activity activity) {
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        return activity;
    }
}
