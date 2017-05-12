package com.appflood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.appflood.c.d;
import com.appflood.c.g;
import com.appflood.e.a;
import com.appflood.e.c;
import com.appflood.e.j;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;

public class AFReferralReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(AdTrackerConstants.REFERRER);
        j.d("onReceive: " + intent.getAction() + " data " + stringExtra);
        if (!j.g(stringExtra)) {
            c.y = stringExtra;
            a.a(context, "google_refer", stringExtra);
            if (AppFlood.isConnected() && !j.g(d.w)) {
                g.a().a(context);
            }
        }
    }
}
