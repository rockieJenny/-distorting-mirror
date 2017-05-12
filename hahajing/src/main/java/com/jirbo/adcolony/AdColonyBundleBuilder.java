package com.jirbo.adcolony;

import android.os.Bundle;

public class AdColonyBundleBuilder {
    private static String a;
    private static boolean b;
    private static boolean c;

    public static void setZoneId(String zone_id) {
        a = zone_id;
    }

    public static void setShowPrePopup(boolean show_pre_popup) {
        b = show_pre_popup;
    }

    public static void setShowPostPopup(boolean show_post_popup) {
        c = show_post_popup;
    }

    public static Bundle build() {
        Bundle bundle = new Bundle();
        bundle.putString("zone_id", a);
        bundle.putBoolean("show_pre_popup", b);
        bundle.putBoolean("show_post_popup", c);
        return bundle;
    }
}
