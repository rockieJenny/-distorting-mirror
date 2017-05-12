package com.flurry.sdk;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.Window;

public final class cx {
    @TargetApi(11)
    public static void a(Window window) {
        if (window != null && VERSION.SDK_INT >= 11) {
            window.setFlags(ViewCompat.MEASURED_STATE_TOO_SMALL, ViewCompat.MEASURED_STATE_TOO_SMALL);
        }
    }
}
