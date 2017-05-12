package com.flurry.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.flurry.android.AdCreative;
import java.util.ArrayList;
import java.util.List;

public final class bz extends bj {
    protected String f() {
        return "Millennial Media";
    }

    protected List<bf> g() {
        List<bf> arrayList = new ArrayList();
        arrayList.add(new bf("MMAdView", "5.1.0", "com.millennialmedia.android.MMInterstitial"));
        return arrayList;
    }

    @TargetApi(13)
    protected List<ActivityInfo> j() {
        List<ActivityInfo> arrayList = new ArrayList();
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.name = "com.millennialmedia.android.MMActivity";
        activityInfo.configChanges = 3248;
        arrayList.add(activityInfo);
        return arrayList;
    }

    protected List<String> h() {
        List<String> arrayList = new ArrayList();
        arrayList.add("com.flurry.millennial.MYAPIDINTERSTITIAL");
        return arrayList;
    }

    protected List<bf> k() {
        List<bf> arrayList = new ArrayList();
        arrayList.add(new bf("MMAdView", "5.1.0", "com.millennialmedia.android.MMAdView"));
        return arrayList;
    }

    protected List<String> l() {
        List<String> arrayList = new ArrayList();
        arrayList.add("com.flurry.millennial.MYAPID");
        arrayList.add("com.flurry.millennial.MYAPIDRECTANGLE");
        return arrayList;
    }

    protected List<String> n() {
        return new ArrayList();
    }

    protected List<String> o() {
        List<String> arrayList = new ArrayList();
        arrayList.add("android.permission.INTERNET");
        arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        arrayList.add("android.permission.ACCESS_NETWORK_STATE");
        return arrayList;
    }

    protected en a(Context context, r rVar, Bundle bundle) {
        if (context == null || rVar == null || bundle == null) {
            return null;
        }
        return new cc(context, rVar, bundle);
    }

    protected ec a(Context context, r rVar, AdCreative adCreative, Bundle bundle) {
        if (context == null || rVar == null || adCreative == null || bundle == null) {
            return null;
        }
        return new ca(context, rVar, adCreative, bundle);
    }
}
