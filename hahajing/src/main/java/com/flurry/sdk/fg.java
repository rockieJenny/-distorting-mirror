package com.flurry.sdk;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Locale;
import java.util.TimeZone;

public class fg {
    private static fg a;

    public static synchronized fg a() {
        fg fgVar;
        synchronized (fg.class) {
            if (a == null) {
                a = new fg();
            }
            fgVar = a;
        }
        return fgVar;
    }

    public static void b() {
        a = null;
    }

    private fg() {
    }

    public String c() {
        return Locale.getDefault().getLanguage() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Locale.getDefault().getCountry();
    }

    public String d() {
        return TimeZone.getDefault().getID();
    }
}
