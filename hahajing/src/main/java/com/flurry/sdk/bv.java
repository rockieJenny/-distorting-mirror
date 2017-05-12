package com.flurry.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.flurry.android.AdCreative;
import java.util.ArrayList;
import java.util.List;

public final class bv extends bj {
    protected String f() {
        return "InMobi";
    }

    protected List<bf> g() {
        List<bf> arrayList = new ArrayList();
        arrayList.add(new bf("InMobiAndroidSDK", "4.1.0", "com.inmobi.monetization.IMInterstitial"));
        return arrayList;
    }

    @TargetApi(13)
    protected List<ActivityInfo> j() {
        List<ActivityInfo> arrayList = new ArrayList();
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.name = "com.inmobi.androidsdk.IMBrowserActivity";
        activityInfo.configChanges = 3248;
        arrayList.add(activityInfo);
        return arrayList;
    }

    protected List<bf> k() {
        List<bf> arrayList = new ArrayList();
        arrayList.add(new bf("InMobiAndroidSDK", "4.1.0", "com.inmobi.monetization.IMBanner"));
        return arrayList;
    }

    protected List<String> n() {
        List<String> arrayList = new ArrayList();
        arrayList.add("com.flurry.inmobi.MY_APP_ID");
        return arrayList;
    }

    protected List<String> o() {
        List<String> arrayList = new ArrayList();
        arrayList.add("android.permission.INTERNET");
        return arrayList;
    }

    protected en a(Context context, r rVar, Bundle bundle) {
        if (context == null || rVar == null || bundle == null) {
            return null;
        }
        return new by(context, rVar, bundle);
    }

    protected ec a(Context context, r rVar, AdCreative adCreative, Bundle bundle) {
        if (context == null || rVar == null || adCreative == null || bundle == null) {
            return null;
        }
        return new bw(context, rVar, adCreative, bundle);
    }
}
