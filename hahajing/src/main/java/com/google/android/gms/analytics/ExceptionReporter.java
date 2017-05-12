package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

public class ExceptionReporter implements UncaughtExceptionHandler {
    private final Context mContext;
    private final UncaughtExceptionHandler yN;
    private final Tracker yO;
    private ExceptionParser yP;
    private GoogleAnalytics yQ;

    public ExceptionReporter(Tracker tracker, UncaughtExceptionHandler originalHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context == null) {
            throw new NullPointerException(AdTrackerConstants.MSG_APP_CONTEXT_NULL);
        } else {
            this.yN = originalHandler;
            this.yO = tracker;
            this.yP = new StandardExceptionParser(context, new ArrayList());
            this.mContext = context.getApplicationContext();
            ae.V("ExceptionReporter created, original handler is " + (originalHandler == null ? "null" : originalHandler.getClass().getName()));
        }
    }

    GoogleAnalytics es() {
        if (this.yQ == null) {
            this.yQ = GoogleAnalytics.getInstance(this.mContext);
        }
        return this.yQ;
    }

    UncaughtExceptionHandler et() {
        return this.yN;
    }

    public ExceptionParser getExceptionParser() {
        return this.yP;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.yP = exceptionParser;
    }

    public void uncaughtException(Thread t, Throwable e) {
        String str = "UncaughtException";
        if (this.yP != null) {
            str = this.yP.getDescription(t != null ? t.getName() : null, e);
        }
        ae.V("Tracking Exception: " + str);
        this.yO.send(new ExceptionBuilder().setDescription(str).setFatal(true).build());
        GoogleAnalytics es = es();
        es.dispatchLocalHits();
        es.dY();
        if (this.yN != null) {
            ae.V("Passing exception to original handler.");
            this.yN.uncaughtException(t, e);
        }
    }
}
