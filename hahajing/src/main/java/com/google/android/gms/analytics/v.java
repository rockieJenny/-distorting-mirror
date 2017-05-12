package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.google.android.gms.analytics.y.a;

class v extends aj {
    private static final Object yT = new Object();
    private static v zf;
    private Context mContext;
    private Handler mHandler;
    private d yU;
    private volatile f yV;
    private int yW = 1800;
    private boolean yX = true;
    private boolean yY;
    private String yZ;
    private boolean yt = false;
    private boolean za = true;
    private boolean zb = true;
    private e zc = new e(this) {
        final /* synthetic */ v zg;

        {
            this.zg = r1;
        }

        public void B(boolean z) {
            this.zg.a(z, this.zg.za);
        }
    };
    private u zd;
    private boolean ze = false;

    private v() {
    }

    public static v eu() {
        if (zf == null) {
            zf = new v();
        }
        return zf;
    }

    private void ev() {
        this.zd = new u(this);
        this.zd.z(this.mContext);
    }

    private void ew() {
        this.mHandler = new Handler(this.mContext.getMainLooper(), new Callback(this) {
            final /* synthetic */ v zg;

            {
                this.zg = r1;
            }

            public boolean handleMessage(Message msg) {
                if (1 == msg.what && v.yT.equals(msg.obj)) {
                    y.eK().D(true);
                    this.zg.dispatchLocalHits();
                    y.eK().D(false);
                    if (this.zg.yW > 0 && !this.zg.ze) {
                        this.zg.mHandler.sendMessageDelayed(this.zg.mHandler.obtainMessage(1, v.yT), (long) (this.zg.yW * 1000));
                    }
                }
                return true;
            }
        });
        if (this.yW > 0) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, yT), (long) (this.yW * 1000));
        }
    }

    synchronized void C(boolean z) {
        a(this.ze, z);
    }

    synchronized void a(Context context, f fVar) {
        if (this.mContext == null) {
            this.mContext = context.getApplicationContext();
            if (this.yV == null) {
                this.yV = fVar;
                if (this.yX) {
                    dispatchLocalHits();
                    this.yX = false;
                }
                if (this.yY) {
                    dW();
                    this.yY = false;
                }
            }
        }
    }

    synchronized void a(boolean z, boolean z2) {
        if (!(this.ze == z && this.za == z2)) {
            if (z || !z2) {
                if (this.yW > 0) {
                    this.mHandler.removeMessages(1, yT);
                }
            }
            if (!z && z2 && this.yW > 0) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, yT), (long) (this.yW * 1000));
            }
            StringBuilder append = new StringBuilder().append("PowerSaveMode ");
            String str = (z || !z2) ? "initiated." : "terminated.";
            ae.V(append.append(str).toString());
            this.ze = z;
            this.za = z2;
        }
    }

    void dW() {
        if (this.yV == null) {
            ae.V("setForceLocalDispatch() queued. It will be called once initialization is complete.");
            this.yY = true;
            return;
        }
        y.eK().a(a.SET_FORCE_LOCAL_DISPATCH);
        this.yV.dW();
    }

    synchronized void dispatchLocalHits() {
        if (this.yV == null) {
            ae.V("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.yX = true;
        } else {
            y.eK().a(a.DISPATCH);
            this.yV.dispatch();
        }
    }

    synchronized d ex() {
        if (this.yU == null) {
            if (this.mContext == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.yU = new ag(this.zc, this.mContext, new j());
            this.yU.setDryRun(this.yt);
            if (this.yZ != null) {
                this.yU.dV().ad(this.yZ);
                this.yZ = null;
            }
        }
        if (this.mHandler == null) {
            ew();
        }
        if (this.zd == null && this.zb) {
            ev();
        }
        return this.yU;
    }

    synchronized void ey() {
        if (!this.ze && this.za && this.yW > 0) {
            this.mHandler.removeMessages(1, yT);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, yT));
        }
    }

    synchronized void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        if (this.mHandler == null) {
            ae.V("Dispatch period set with null handler. Dispatch will run once initialization is complete.");
            this.yW = dispatchPeriodInSeconds;
        } else {
            y.eK().a(a.SET_DISPATCH_PERIOD);
            if (!this.ze && this.za && this.yW > 0) {
                this.mHandler.removeMessages(1, yT);
            }
            this.yW = dispatchPeriodInSeconds;
            if (dispatchPeriodInSeconds > 0 && !this.ze && this.za) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, yT), (long) (dispatchPeriodInSeconds * 1000));
            }
        }
    }
}
