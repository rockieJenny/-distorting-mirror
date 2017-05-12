package com.google.android.gms.analytics;

import com.google.analytics.tracking.android.Fields;
import com.inmobi.commons.analytics.db.AnalyticsEvent;

public final class s {
    public static String A(int i) {
        return d("&pr", i);
    }

    public static String B(int i) {
        return d("&promo", i);
    }

    public static String C(int i) {
        return d(AnalyticsEvent.TYPE_TAG_TRANSACTION, i);
    }

    public static String D(int i) {
        return d("&il", i);
    }

    public static String E(int i) {
        return d("cd", i);
    }

    public static String F(int i) {
        return d("cm", i);
    }

    private static String d(String str, int i) {
        if (i >= 1) {
            return str + i;
        }
        ae.T("index out of range for " + str + " (" + i + ")");
        return "";
    }

    static String y(int i) {
        return d("&cd", i);
    }

    static String z(int i) {
        return d(Fields.CAMPAIGN_MEDIUM, i);
    }
}
