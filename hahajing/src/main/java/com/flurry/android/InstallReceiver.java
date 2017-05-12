package com.flurry.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.flurry.sdk.er;
import com.flurry.sdk.gd;
import com.flurry.sdk.hp;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.internal.InternalSDKUtil;

public final class InstallReceiver extends BroadcastReceiver {
    static final String a = InstallReceiver.class.getSimpleName();

    public void onReceive(Context context, Intent intent) {
        gd.a(4, a, "Received an Install nofication of " + intent.getAction());
        String string = intent.getExtras().getString(AdTrackerConstants.REFERRER);
        gd.a(4, a, "Received an Install referrer of " + string);
        if (string == null || !InternalSDKUtil.ACTION_RECEIVER_REFERRER.equals(intent.getAction())) {
            gd.a(5, a, "referrer is null");
            return;
        }
        if (!string.contains("=")) {
            gd.a(4, a, "referrer is before decoding: " + string);
            string = hp.d(string);
            gd.a(4, a, "referrer is: " + string);
        }
        new er(context).a(string);
    }
}
