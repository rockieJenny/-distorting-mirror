package com.inmobi.monetization.internal;

import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.network.ErrorCode;
import com.inmobi.commons.network.Response;
import com.inmobi.commons.network.ServiceProvider;
import com.inmobi.commons.network.abstraction.INetworkListener;

/* compiled from: AdController */
class a {
    private static a c = null;
    private ServiceProvider a = ServiceProvider.getInstance();
    private INetworkListener b;

    private a() {
    }

    public static a a() {
        if (c == null) {
            c = new a();
        }
        return c;
    }

    public void a(String str, c cVar, INetworkListener iNetworkListener) {
        this.b = iNetworkListener;
        if (InternalSDKUtil.checkNetworkAvailibility(InternalSDKUtil.getContext())) {
            Log.internal(Constants.LOG_TAG, "Fetching  Ads");
            this.a.executeTask(cVar, iNetworkListener);
        } else if (this.b != null) {
            this.b.onRequestFailed(cVar, new Response(ErrorCode.NETWORK_ERROR));
        }
    }
}
