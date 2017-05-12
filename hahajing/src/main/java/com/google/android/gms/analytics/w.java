package com.google.android.gms.analytics;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.internal.ha;
import com.google.android.gms.internal.ld;
import com.google.android.gms.internal.lf;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

class w implements ak, com.google.android.gms.analytics.c.b, com.google.android.gms.analytics.c.c {
    private final Context mContext;
    private ld wb;
    private d yU;
    private final f yV;
    private boolean yX;
    private volatile long zh;
    private volatile a zi;
    private volatile b zj;
    private d zk;
    private final GoogleAnalytics zl;
    private final Queue<d> zm;
    private volatile int zn;
    private volatile Timer zo;
    private volatile Timer zp;
    private volatile Timer zq;
    private boolean zr;
    private boolean zs;
    private boolean zt;
    private long zu;

    private enum a {
        CONNECTING,
        CONNECTED_SERVICE,
        CONNECTED_LOCAL,
        BLOCKED,
        PENDING_CONNECTION,
        PENDING_DISCONNECT,
        DISCONNECTED
    }

    private class b extends TimerTask {
        final /* synthetic */ w zv;

        private b(w wVar) {
            this.zv = wVar;
        }

        public void run() {
            if (this.zv.zi == a.CONNECTED_SERVICE && this.zv.zm.isEmpty() && this.zv.zh + this.zv.zu < this.zv.wb.elapsedRealtime()) {
                ae.V("Disconnecting due to inactivity");
                this.zv.cJ();
                return;
            }
            this.zv.zq.schedule(new b(this.zv), this.zv.zu);
        }
    }

    private class c extends TimerTask {
        final /* synthetic */ w zv;

        private c(w wVar) {
            this.zv = wVar;
        }

        public void run() {
            if (this.zv.zi == a.CONNECTING) {
                this.zv.eE();
            }
        }
    }

    private static class d {
        private final Map<String, String> zF;
        private final long zG;
        private final String zH;
        private final List<ha> zI;

        public d(Map<String, String> map, long j, String str, List<ha> list) {
            this.zF = map;
            this.zG = j;
            this.zH = str;
            this.zI = list;
        }

        public Map<String, String> eH() {
            return this.zF;
        }

        public long eI() {
            return this.zG;
        }

        public List<ha> eJ() {
            return this.zI;
        }

        public String getPath() {
            return this.zH;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PATH: ");
            stringBuilder.append(this.zH);
            if (this.zF != null) {
                stringBuilder.append("  PARAMS: ");
                for (Entry entry : this.zF.entrySet()) {
                    stringBuilder.append((String) entry.getKey());
                    stringBuilder.append("=");
                    stringBuilder.append((String) entry.getValue());
                    stringBuilder.append(",  ");
                }
            }
            return stringBuilder.toString();
        }
    }

    private class e extends TimerTask {
        final /* synthetic */ w zv;

        private e(w wVar) {
            this.zv = wVar;
        }

        public void run() {
            this.zv.eF();
        }
    }

    w(Context context, f fVar) {
        this(context, fVar, null, GoogleAnalytics.getInstance(context));
    }

    w(Context context, f fVar, d dVar, GoogleAnalytics googleAnalytics) {
        this.zm = new ConcurrentLinkedQueue();
        this.zu = 300000;
        this.zk = dVar;
        this.mContext = context;
        this.yV = fVar;
        this.zl = googleAnalytics;
        this.wb = lf.if();
        this.zn = 0;
        this.zi = a.DISCONNECTED;
    }

    private Timer a(Timer timer) {
        if (timer != null) {
            timer.cancel();
        }
        return null;
    }

    private synchronized void cJ() {
        if (this.zj != null && this.zi == a.CONNECTED_SERVICE) {
            this.zi = a.PENDING_DISCONNECT;
            this.zj.disconnect();
        }
    }

    private void eA() {
        this.zo = a(this.zo);
        this.zp = a(this.zp);
        this.zq = a(this.zq);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void eC() {
        /*
        r8 = this;
        monitor-enter(r8);
        r2 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0074 }
        r3 = r8.yV;	 Catch:{ all -> 0x0074 }
        r3 = r3.getThread();	 Catch:{ all -> 0x0074 }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0074 }
        if (r2 != 0) goto L_0x0021;
    L_0x0011:
        r2 = r8.yV;	 Catch:{ all -> 0x0074 }
        r2 = r2.dX();	 Catch:{ all -> 0x0074 }
        r3 = new com.google.android.gms.analytics.w$1;	 Catch:{ all -> 0x0074 }
        r3.<init>(r8);	 Catch:{ all -> 0x0074 }
        r2.add(r3);	 Catch:{ all -> 0x0074 }
    L_0x001f:
        monitor-exit(r8);
        return;
    L_0x0021:
        r2 = r8.zr;	 Catch:{ all -> 0x0074 }
        if (r2 == 0) goto L_0x0028;
    L_0x0025:
        r8.dQ();	 Catch:{ all -> 0x0074 }
    L_0x0028:
        r2 = com.google.android.gms.analytics.w.AnonymousClass2.zw;	 Catch:{ all -> 0x0074 }
        r3 = r8.zi;	 Catch:{ all -> 0x0074 }
        r3 = r3.ordinal();	 Catch:{ all -> 0x0074 }
        r2 = r2[r3];	 Catch:{ all -> 0x0074 }
        switch(r2) {
            case 1: goto L_0x0036;
            case 2: goto L_0x008a;
            case 3: goto L_0x0035;
            case 4: goto L_0x0035;
            case 5: goto L_0x0035;
            case 6: goto L_0x00e5;
            case 7: goto L_0x0077;
            default: goto L_0x0035;
        };	 Catch:{ all -> 0x0074 }
    L_0x0035:
        goto L_0x001f;
    L_0x0036:
        r2 = r8.zm;	 Catch:{ all -> 0x0074 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0074 }
        if (r2 != 0) goto L_0x0082;
    L_0x003e:
        r2 = r8.zm;	 Catch:{ all -> 0x0074 }
        r2 = r2.poll();	 Catch:{ all -> 0x0074 }
        r0 = r2;
        r0 = (com.google.android.gms.analytics.w.d) r0;	 Catch:{ all -> 0x0074 }
        r7 = r0;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0074 }
        r2.<init>();	 Catch:{ all -> 0x0074 }
        r3 = "Sending hit to store  ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0074 }
        r2 = r2.append(r7);	 Catch:{ all -> 0x0074 }
        r2 = r2.toString();	 Catch:{ all -> 0x0074 }
        com.google.android.gms.analytics.ae.V(r2);	 Catch:{ all -> 0x0074 }
        r2 = r8.yU;	 Catch:{ all -> 0x0074 }
        r3 = r7.eH();	 Catch:{ all -> 0x0074 }
        r4 = r7.eI();	 Catch:{ all -> 0x0074 }
        r6 = r7.getPath();	 Catch:{ all -> 0x0074 }
        r7 = r7.eJ();	 Catch:{ all -> 0x0074 }
        r2.a(r3, r4, r6, r7);	 Catch:{ all -> 0x0074 }
        goto L_0x0036;
    L_0x0074:
        r2 = move-exception;
        monitor-exit(r8);
        throw r2;
    L_0x0077:
        r2 = "Blocked. Dropping hits.";
        com.google.android.gms.analytics.ae.V(r2);	 Catch:{ all -> 0x0074 }
        r2 = r8.zm;	 Catch:{ all -> 0x0074 }
        r2.clear();	 Catch:{ all -> 0x0074 }
        goto L_0x001f;
    L_0x0082:
        r2 = r8.yX;	 Catch:{ all -> 0x0074 }
        if (r2 == 0) goto L_0x001f;
    L_0x0086:
        r8.eD();	 Catch:{ all -> 0x0074 }
        goto L_0x001f;
    L_0x008a:
        r2 = r8.zm;	 Catch:{ all -> 0x0074 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0074 }
        if (r2 != 0) goto L_0x00db;
    L_0x0092:
        r2 = r8.zm;	 Catch:{ all -> 0x0074 }
        r2 = r2.peek();	 Catch:{ all -> 0x0074 }
        r0 = r2;
        r0 = (com.google.android.gms.analytics.w.d) r0;	 Catch:{ all -> 0x0074 }
        r7 = r0;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0074 }
        r2.<init>();	 Catch:{ all -> 0x0074 }
        r3 = "Sending hit to service   ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0074 }
        r2 = r2.append(r7);	 Catch:{ all -> 0x0074 }
        r2 = r2.toString();	 Catch:{ all -> 0x0074 }
        com.google.android.gms.analytics.ae.V(r2);	 Catch:{ all -> 0x0074 }
        r2 = r8.zl;	 Catch:{ all -> 0x0074 }
        r2 = r2.isDryRunEnabled();	 Catch:{ all -> 0x0074 }
        if (r2 != 0) goto L_0x00d5;
    L_0x00ba:
        r2 = r8.zj;	 Catch:{ all -> 0x0074 }
        r3 = r7.eH();	 Catch:{ all -> 0x0074 }
        r4 = r7.eI();	 Catch:{ all -> 0x0074 }
        r6 = r7.getPath();	 Catch:{ all -> 0x0074 }
        r7 = r7.eJ();	 Catch:{ all -> 0x0074 }
        r2.a(r3, r4, r6, r7);	 Catch:{ all -> 0x0074 }
    L_0x00cf:
        r2 = r8.zm;	 Catch:{ all -> 0x0074 }
        r2.poll();	 Catch:{ all -> 0x0074 }
        goto L_0x008a;
    L_0x00d5:
        r2 = "Dry run enabled. Hit not actually sent to service.";
        com.google.android.gms.analytics.ae.V(r2);	 Catch:{ all -> 0x0074 }
        goto L_0x00cf;
    L_0x00db:
        r2 = r8.wb;	 Catch:{ all -> 0x0074 }
        r2 = r2.elapsedRealtime();	 Catch:{ all -> 0x0074 }
        r8.zh = r2;	 Catch:{ all -> 0x0074 }
        goto L_0x001f;
    L_0x00e5:
        r2 = "Need to reconnect";
        com.google.android.gms.analytics.ae.V(r2);	 Catch:{ all -> 0x0074 }
        r2 = r8.zm;	 Catch:{ all -> 0x0074 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0074 }
        if (r2 != 0) goto L_0x001f;
    L_0x00f2:
        r8.eF();	 Catch:{ all -> 0x0074 }
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.w.eC():void");
    }

    private void eD() {
        this.yU.dispatch();
        this.yX = false;
    }

    private synchronized void eE() {
        if (this.zi != a.CONNECTED_LOCAL) {
            if (this.mContext == null || !"com.google.android.gms".equals(this.mContext.getPackageName())) {
                eA();
                ae.V("falling back to local store");
                if (this.zk != null) {
                    this.yU = this.zk;
                } else {
                    v eu = v.eu();
                    eu.a(this.mContext, this.yV);
                    this.yU = eu.ex();
                }
                this.zi = a.CONNECTED_LOCAL;
                eC();
            } else {
                this.zi = a.BLOCKED;
                this.zj.disconnect();
                ae.W("Attempted to fall back to local store from service.");
            }
        }
    }

    private synchronized void eF() {
        if (this.zt || this.zj == null || this.zi == a.CONNECTED_LOCAL) {
            ae.W("client not initialized.");
            eE();
        } else {
            try {
                this.zn++;
                a(this.zp);
                this.zi = a.CONNECTING;
                this.zp = new Timer("Failed Connect");
                this.zp.schedule(new c(), 3000);
                ae.V("connecting to Analytics service");
                this.zj.connect();
            } catch (SecurityException e) {
                ae.W("security exception on connectToService");
                eE();
            }
        }
    }

    private void eG() {
        this.zo = a(this.zo);
        this.zo = new Timer("Service Reconnect");
        this.zo.schedule(new e(), 5000);
    }

    public synchronized void a(int i, Intent intent) {
        this.zi = a.PENDING_CONNECTION;
        if (this.zn < 2) {
            ae.W("Service unavailable (code=" + i + "), will retry.");
            eG();
        } else {
            ae.W("Service unavailable (code=" + i + "), using local store.");
            eE();
        }
    }

    public void b(Map<String, String> map, long j, String str, List<ha> list) {
        ae.V("putHit called");
        this.zm.add(new d(map, j, str, list));
        eC();
    }

    public void dQ() {
        ae.V("clearHits called");
        this.zm.clear();
        switch (this.zi) {
            case CONNECTED_LOCAL:
                this.yU.l(0);
                this.zr = false;
                return;
            case CONNECTED_SERVICE:
                this.zj.dQ();
                this.zr = false;
                return;
            default:
                this.zr = true;
                return;
        }
    }

    public synchronized void dW() {
        if (!this.zt) {
            ae.V("setForceLocalDispatch called.");
            this.zt = true;
            switch (this.zi) {
                case CONNECTED_LOCAL:
                case PENDING_CONNECTION:
                case PENDING_DISCONNECT:
                case DISCONNECTED:
                    break;
                case CONNECTED_SERVICE:
                    cJ();
                    break;
                case CONNECTING:
                    this.zs = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void dispatch() {
        switch (this.zi) {
            case CONNECTED_LOCAL:
                eD();
                return;
            case CONNECTED_SERVICE:
                return;
            default:
                this.yX = true;
                return;
        }
    }

    public void eB() {
        if (this.zj == null) {
            this.zj = new c(this.mContext, this, this);
            eF();
        }
    }

    public synchronized void onConnected() {
        this.zp = a(this.zp);
        this.zn = 0;
        ae.V("Connected to service");
        this.zi = a.CONNECTED_SERVICE;
        if (this.zs) {
            cJ();
            this.zs = false;
        } else {
            eC();
            this.zq = a(this.zq);
            this.zq = new Timer("disconnect check");
            this.zq.schedule(new b(), this.zu);
        }
    }

    public synchronized void onDisconnected() {
        if (this.zi == a.BLOCKED) {
            ae.V("Service blocked.");
            eA();
        } else if (this.zi == a.PENDING_DISCONNECT) {
            ae.V("Disconnected from service");
            eA();
            this.zi = a.DISCONNECTED;
        } else {
            ae.V("Unexpected disconnect.");
            this.zi = a.PENDING_CONNECTION;
            if (this.zn < 2) {
                eG();
            } else {
                eE();
            }
        }
    }
}
