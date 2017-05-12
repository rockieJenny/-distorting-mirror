package com.google.android.gms.analytics;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.ha;
import com.google.android.gms.internal.hb;
import java.util.List;
import java.util.Map;

class c implements b {
    private Context mContext;
    private ServiceConnection xV;
    private b xW;
    private c xX;
    private hb xY;

    final class a implements ServiceConnection {
        final /* synthetic */ c xZ;

        a(c cVar) {
            this.xZ = cVar;
        }

        public void onServiceConnected(ComponentName component, IBinder binder) {
            ae.V("service connected, binder: " + binder);
            try {
                if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(binder.getInterfaceDescriptor())) {
                    ae.V("bound to service");
                    this.xZ.xY = com.google.android.gms.internal.hb.a.D(binder);
                    this.xZ.dT();
                    return;
                }
            } catch (RemoteException e) {
            }
            try {
                this.xZ.mContext.unbindService(this);
            } catch (IllegalArgumentException e2) {
            }
            this.xZ.xV = null;
            this.xZ.xX.a(2, null);
        }

        public void onServiceDisconnected(ComponentName component) {
            ae.V("service disconnected: " + component);
            this.xZ.xV = null;
            this.xZ.xW.onDisconnected();
        }
    }

    public interface b {
        void onConnected();

        void onDisconnected();
    }

    public interface c {
        void a(int i, Intent intent);
    }

    public c(Context context, b bVar, c cVar) {
        this.mContext = context;
        if (bVar == null) {
            throw new IllegalArgumentException("onConnectedListener cannot be null");
        }
        this.xW = bVar;
        if (cVar == null) {
            throw new IllegalArgumentException("onConnectionFailedListener cannot be null");
        }
        this.xX = cVar;
    }

    private hb dR() {
        dS();
        return this.xY;
    }

    private void dT() {
        dU();
    }

    private void dU() {
        this.xW.onConnected();
    }

    public void a(Map<String, String> map, long j, String str, List<ha> list) {
        try {
            dR().a(map, j, str, list);
        } catch (RemoteException e) {
            ae.T("sendHit failed: " + e);
        }
    }

    public void connect() {
        Intent intent = new Intent("com.google.android.gms.analytics.service.START");
        intent.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
        intent.putExtra(AnalyticsGmsCoreClient.KEY_APP_PACKAGE_NAME, this.mContext.getPackageName());
        if (this.xV != null) {
            ae.T("Calling connect() while still connected, missing disconnect().");
            return;
        }
        this.xV = new a(this);
        boolean bindService = this.mContext.bindService(intent, this.xV, 129);
        ae.V("connect: bindService returned " + bindService + " for " + intent);
        if (!bindService) {
            this.xV = null;
            this.xX.a(1, null);
        }
    }

    public void dQ() {
        try {
            dR().dQ();
        } catch (RemoteException e) {
            ae.T("clear hits failed: " + e);
        }
    }

    protected void dS() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public void disconnect() {
        this.xY = null;
        if (this.xV != null) {
            try {
                this.mContext.unbindService(this.xV);
            } catch (IllegalStateException e) {
            } catch (IllegalArgumentException e2) {
            }
            this.xV = null;
            this.xW.onDisconnected();
        }
    }

    public boolean isConnected() {
        return this.xY != null;
    }
}
