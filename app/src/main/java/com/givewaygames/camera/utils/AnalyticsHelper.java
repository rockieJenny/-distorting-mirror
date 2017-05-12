package com.givewaygames.camera.utils;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.givewaygames.ads.GAUtils;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.utils.Log;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.analytics.tracking.android.Tracker;

public class AnalyticsHelper {
    public static final String TAG = "AnalyticsHelper";
    private static final AnalyticsHelper instance = new AnalyticsHelper();
    boolean gaEnabled = false;
    Tracker gaTracker;
    long lastShaderChangeTime = 0;

    public static AnalyticsHelper getInstance() {
        return instance;
    }

    public Tracker getTracker() {
        return this.gaTracker;
    }

    public boolean isEnabled() {
        return this.gaTracker != null && this.gaEnabled;
    }

    public void init(Context context) {
        boolean z;
        boolean z2 = true;
        if (CameraWrapper.DEBUG) {
            z = false;
        } else {
            z = true;
        }
        this.gaEnabled = z;
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(context);
        if (googleAnalytics != null) {
            this.gaTracker = googleAnalytics.getTracker("UA-33231202-2");
            if (this.gaEnabled) {
                z2 = false;
            }
            googleAnalytics.setAppOptOut(z2);
            if (Log.isV) {
                googleAnalytics.getLogger().setLogLevel(LogLevel.VERBOSE);
            } else if (Log.isI) {
                googleAnalytics.getLogger().setLogLevel(LogLevel.INFO);
            } else if (Log.isW) {
                googleAnalytics.getLogger().setLogLevel(LogLevel.WARNING);
            } else if (Log.isE) {
                googleAnalytics.getLogger().setLogLevel(LogLevel.ERROR);
            }
        }
    }

    public void sendException(String moreDetails, Throwable exception, Boolean fatal) {
        if (this.gaEnabled) {
            Crashlytics.logException(exception);
            GAUtils.sendException(this.gaTracker, moreDetails, exception, fatal);
        }
    }

    public void sendEvent(String category, String action, String label, long value) {
        if (this.gaEnabled) {
            GAUtils.sendEvent(this.gaTracker, category, action, label, value);
        }
    }

    public void trackUiEvent(String uiElement, String extraDetails, long optMoreDetails) {
        sendEvent("ui_action", uiElement, extraDetails, optMoreDetails);
    }
}
