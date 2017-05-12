package com.inmobi.commons.analytics.db;

import com.inmobi.commons.analytics.util.AnalyticsUtils;
import com.inmobi.commons.internal.Log;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class AnalyticsEventsQueue extends Vector<AnalyticsFunctions> {
    private static AnalyticsEventsQueue a = null;
    private static final long serialVersionUID = -1291938489149189478L;
    private AtomicBoolean b = new AtomicBoolean(false);

    public static synchronized AnalyticsEventsQueue getInstance() {
        AnalyticsEventsQueue analyticsEventsQueue;
        synchronized (AnalyticsEventsQueue.class) {
            if (a == null) {
                a = new AnalyticsEventsQueue();
            }
            analyticsEventsQueue = a;
        }
        return analyticsEventsQueue;
    }

    public synchronized void processFunctions() {
        if (!this.b.get()) {
            this.b.set(true);
            new Thread(this) {
                final /* synthetic */ AnalyticsEventsQueue a;

                {
                    this.a = r1;
                }

                public void run() {
                    while (!this.a.isEmpty()) {
                        try {
                            this.a.a((AnalyticsFunctions) this.a.remove(0));
                        } catch (Throwable e) {
                            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Exception processing function", e);
                            return;
                        }
                    }
                }
            }.start();
        }
    }

    public synchronized boolean isEmpty() {
        boolean isEmpty;
        isEmpty = super.isEmpty();
        if (isEmpty) {
            this.b.set(false);
        }
        return isEmpty;
    }

    private void a(AnalyticsFunctions analyticsFunctions) {
        analyticsFunctions.processFunction();
    }
}
