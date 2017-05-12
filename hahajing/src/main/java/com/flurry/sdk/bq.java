package com.flurry.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.flurry.android.AdCreative;
import java.util.ArrayList;
import java.util.List;

public class bq extends bj {
    protected String f() {
        return "Facebook Audience Network";
    }

    protected List<bf> g() {
        List<bf> arrayList = new ArrayList();
        arrayList.add(new bf("AudienceNetwork", "3.14.+", "com.facebook.ads.InterstitialAd"));
        return arrayList;
    }

    @TargetApi(13)
    protected List<ActivityInfo> j() {
        List<ActivityInfo> arrayList = new ArrayList();
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.name = "com.facebook.ads.InterstitialAdActivity";
        activityInfo.configChanges = 4016;
        arrayList.add(activityInfo);
        return arrayList;
    }

    protected List<bf> k() {
        List<bf> arrayList = new ArrayList();
        arrayList.add(new bf("AudienceNetwork", "3.14.+", "com.facebook.ads.Ad"));
        return arrayList;
    }

    protected List<String> n() {
        List<String> arrayList = new ArrayList();
        arrayList.add("com.flurry.fan.MY_APP_ID");
        return arrayList;
    }

    protected List<String> o() {
        List<String> arrayList = new ArrayList();
        arrayList.add("android.permission.INTERNET");
        arrayList.add("android.permission.ACCESS_NETWORK_STATE");
        return arrayList;
    }

    protected en a(Context context, r rVar, Bundle bundle) {
        if (context == null || rVar == null || bundle == null) {
            return null;
        }
        return new br(context, rVar, bundle);
    }

    protected ec a(Context context, r rVar, AdCreative adCreative, Bundle bundle) {
        if (context == null || rVar == null || adCreative == null || bundle == null) {
            return null;
        }
        return new bp(context, rVar, adCreative, bundle);
    }
}
