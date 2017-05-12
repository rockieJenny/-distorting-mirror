package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.internal.jx;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Promotion {
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_VIEW = "view";
    Map<String, String> CD = new HashMap();

    public Map<String, String> aq(String str) {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : this.CD.entrySet()) {
            hashMap.put(str + ((String) entry.getKey()), entry.getValue());
        }
        return hashMap;
    }

    void put(String name, String value) {
        jx.b((Object) name, (Object) "Name should be non-null");
        this.CD.put(name, value);
    }

    public Promotion setCreative(String value) {
        put("cr", value);
        return this;
    }

    public Promotion setId(String value) {
        put(AnalyticsEvent.EVENT_ID, value);
        return this;
    }

    public Promotion setName(String value) {
        put("nm", value);
        return this;
    }

    public Promotion setPosition(String value) {
        put("ps", value);
        return this;
    }
}
