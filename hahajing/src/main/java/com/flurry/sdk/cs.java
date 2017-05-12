package com.flurry.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.Arrays;
import java.util.List;

public class cs {
    private static final String a = cs.class.getSimpleName();
    private static final List<String> b = Arrays.asList(new String[]{"com.android.chrome", "org.mozilla.firefox", "mobi.mgeek.TunnyBrowser", "com.UCMobile.intl", "com.opera.mini.android", "com.jiubang.browser", "com.opera.browser", "com.uc.browser.en", "acr.browser.barebones", "com.boatbrowser.free", "com.mx.browser", "com.ilegendsoft.mercury", "gpc.myweb.hinet.net.PopupWeb", "mobi.browser.flashfox", "com.baidu.browser.inter", "com.sec.webbrowserminiapp", "com.android.browser", GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE});

    private static Intent a(ActivityInfo activityInfo, Intent intent) {
        gd.a(3, a, "Launching App in package: " + activityInfo.packageName);
        ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setComponent(componentName);
        return intent;
    }

    public static Intent a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
        if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
            for (ResolveInfo resolveInfo : queryIntentActivities) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (a(activityInfo.packageName)) {
                    return a(activityInfo, intent);
                }
            }
        }
        return null;
    }

    private static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE.equalsIgnoreCase(str) || "com.google.market".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }
}
