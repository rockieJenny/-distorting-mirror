package com.givewaygames.ads;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import java.util.Map;

public class GAUtils {
    private static final String CAMPAIGN_SOURCE_PARAM = "utm_source";

    public static void onStart(Tracker tracker, Activity activity) {
        EasyTracker.getInstance(activity).activityStart(activity);
        tracker.set("&cd", getActivityName(activity));
        tracker.send(MapBuilder.createAppView().setAll(getReferrerMapFromUri(activity.getIntent().getData())).build());
    }

    public static void onStop(Activity activity) {
        EasyTracker.getInstance(activity).activityStop(activity);
    }

    public static Tracker getTracker(Context context, String trackingId, boolean isDebug) {
        boolean gaEnabled;
        boolean z = true;
        if (isDebug) {
            gaEnabled = false;
        } else {
            gaEnabled = true;
        }
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(context);
        Tracker tracker = null;
        if (googleAnalytics != null) {
            tracker = googleAnalytics.getTracker(trackingId);
            if (gaEnabled) {
                z = false;
            }
            googleAnalytics.setAppOptOut(z);
        }
        return tracker;
    }

    public static void sendTiming(Tracker tracker, String category, Long intervalInMilliseconds, String name, String label) {
        if (tracker != null) {
            tracker.send(MapBuilder.createTiming(category, intervalInMilliseconds, name, label).build());
        }
    }

    public static void sendException(Tracker tracker, String moreDetails, Throwable exception, Boolean fatal) {
        if (tracker != null) {
            sendException(tracker, exception.getMessage() + " : " + moreDetails, fatal);
        }
    }

    public static void sendException(Tracker tracker, String exceptionDetails, Boolean fatal) {
        if (tracker != null) {
            tracker.send(MapBuilder.createException(exceptionDetails, fatal).build());
        }
    }

    public static void sendEvent(Tracker tracker, String category, String action, String label, long value) {
        if (tracker != null) {
            tracker.send(MapBuilder.createEvent(category, action, label, Long.valueOf(value)).build());
        }
    }

    private static String getActivityName(Activity activity) {
        return activity.getClass().getCanonicalName();
    }

    public static Map<String, String> getReferrerMapFromUri(Uri uri) {
        MapBuilder paramMap = new MapBuilder();
        if (uri == null) {
            return paramMap.build();
        }
        if (uri.getQueryParameter(CAMPAIGN_SOURCE_PARAM) != null) {
            paramMap.setCampaignParamsFromUrl(uri.toString());
        } else if (uri.getAuthority() != null) {
            paramMap.set(Fields.CAMPAIGN_MEDIUM, "referral");
            paramMap.set(Fields.CAMPAIGN_SOURCE, uri.getAuthority());
        }
        return paramMap.build();
    }
}
