package com.google.android.gms.analytics;

import java.util.Map;

abstract class TrackerHandler {
    TrackerHandler() {
    }

    abstract void u(Map<String, String> map);
}
