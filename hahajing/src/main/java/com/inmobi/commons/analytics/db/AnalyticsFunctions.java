package com.inmobi.commons.analytics.db;

import android.util.Log;
import com.inmobi.commons.analytics.util.AnalyticsUtils;

public abstract class AnalyticsFunctions {
    private FunctionName a = null;

    public enum FunctionName {
        SS,
        ES,
        LB,
        LE,
        CE,
        PI
    }

    public abstract AnalyticsEvent processFunction();

    public FunctionName getFunctionName() {
        return this.a;
    }

    protected void insertInDatabase(AnalyticsEvent analyticsEvent) {
        try {
            AnalyticsDatabaseManager.getInstance().insertEvents(analyticsEvent);
        } catch (Throwable e) {
            Log.w(AnalyticsUtils.ANALYTICS_LOGGING_TAG, e);
        }
    }

    protected void printWarning(String str) {
        Log.d(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "IllegalStateException", new IllegalStateException(str));
    }
}
