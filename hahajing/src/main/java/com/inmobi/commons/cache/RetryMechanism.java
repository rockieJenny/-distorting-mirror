package com.inmobi.commons.cache;

import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.util.Timer;
import java.util.TimerTask;

public class RetryMechanism {
    private Timer a;
    private int b = 0;
    private int c = 0;
    private int d = 1000;

    public interface RetryRunnable {
        void completed();

        void run() throws Exception;
    }

    public RetryMechanism(int i, int i2, Timer timer) {
        this.c = i;
        this.d = i2;
        this.a = timer;
    }

    public void rescheduleTimer(RetryRunnable retryRunnable) {
        this.b = 0;
        a(retryRunnable, 0);
    }

    private void a(final RetryRunnable retryRunnable, int i) {
        this.a.schedule(new TimerTask(this) {
            final /* synthetic */ RetryMechanism b;

            public void run() {
                try {
                    retryRunnable.run();
                    retryRunnable.completed();
                } catch (Exception e) {
                    this.b.b = this.b.b + 1;
                    if (this.b.b > this.b.c) {
                        Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception occured while running retry mechanism and will the limit for retrying has been reached.");
                        retryRunnable.completed();
                        return;
                    }
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception occured while running retry mechanism and will retry in " + (this.b.b * this.b.d) + " ms");
                    this.b.a(retryRunnable, this.b.b * this.b.d);
                }
            }
        }, (long) i);
    }
}
