package com.flurry.sdk;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.flurry.android.FlurryFullscreenTakeoverActivity;

public class cu {
    private static final String a = cu.class.getSimpleName();

    public static boolean a(Context context, String str, boolean z, r rVar, boolean z2, boolean z3) {
        boolean z4 = false;
        if (cv.d(str)) {
            z4 = a(context, str);
        }
        if (!z4 && cv.f(str)) {
            z4 = b(context, str);
        }
        if (z4) {
            return z4;
        }
        if (z) {
            return a(context, str, z3);
        }
        return a(context, rVar, str, z2, z3);
    }

    public static boolean a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str) || !cv.d(str)) {
            return false;
        }
        return a(context, new Intent("android.intent.action.VIEW").setData(Uri.parse(str)));
    }

    public static boolean b(Context context, String str) {
        Intent a = cs.a(context, str);
        if (a != null) {
            return a(context, a);
        }
        return false;
    }

    public static boolean a(Context context, String str, r rVar, boolean z) {
        Intent launchIntentForPackage = fp.a().e().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null && hp.a(launchIntentForPackage)) {
            return a(context, launchIntentForPackage);
        }
        return a(context, "https://play.google.com/store/apps/details?id=" + str, false, rVar, true, z);
    }

    public static boolean c(Context context, String str) {
        Intent a = cs.a(context, str);
        if (a == null || !hp.a(a)) {
            return false;
        }
        return a(context, a);
    }

    public static boolean a(Context context, r rVar, String str, boolean z, boolean z2) {
        if (context == null) {
            return false;
        }
        Intent intent = new Intent(context, FlurryFullscreenTakeoverActivity.class);
        intent.putExtra(FlurryFullscreenTakeoverActivity.EXTRA_KEY_AD_OBJECT_LEGACY, rVar instanceof u);
        intent.putExtra(FlurryFullscreenTakeoverActivity.EXTRA_KEY_AD_OBJECT_ID, rVar.d());
        intent.putExtra("url", str);
        intent.putExtra(FlurryFullscreenTakeoverActivity.EXTRA_KEY_CLOSE_AD, z);
        intent.putExtra(FlurryFullscreenTakeoverActivity.EXTRA_KEY_SEND_Y_COOKIES, z2);
        return a(context, intent);
    }

    public static boolean a(Context context, r rVar, boolean z, boolean z2) {
        if (context == null) {
            return false;
        }
        return a(context, rVar, null, z, z2);
    }

    public static boolean a(Context context, String str, boolean z) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        Intent data = new Intent("android.intent.action.VIEW").setData(Uri.parse(str));
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putString("Cookie", i.a().h().d());
            data.putExtra("com.android.browser.headers", bundle);
        }
        return a(context, data);
    }

    private static boolean a(Context context, Intent intent) {
        if (context == null || intent == null) {
            return false;
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
