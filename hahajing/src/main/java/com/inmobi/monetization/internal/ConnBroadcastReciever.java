package com.inmobi.monetization.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.imai.RequestResponseManager;

public class ConnBroadcastReciever extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && InternalSDKUtil.checkNetworkAvailibility(context)) {
            Log.internal(Constants.LOG_TAG, "Received CONNECTIVITY BROADCAST");
            try {
                RequestResponseManager requestResponseManager = new RequestResponseManager();
                requestResponseManager.init();
                requestResponseManager.processClick(context.getApplicationContext(), null);
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Connectivity receiver exception", e);
            }
        }
    }
}
