package com.google.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;

public final class InstallReferrerReceiver extends BroadcastReceiver {
    static final String INSTALL_ACTION = "com.android.vending.INSTALL_REFERRER";

    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra(AdTrackerConstants.REFERRER);
        if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction()) && referrer != null) {
            InstallReferrerUtil.cacheInstallReferrer(referrer);
            Intent serviceIntent = new Intent(context, InstallReferrerService.class);
            serviceIntent.putExtra(AdTrackerConstants.REFERRER, referrer);
            context.startService(serviceIntent);
        }
    }
}
