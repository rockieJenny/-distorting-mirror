package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.s;
import com.google.android.gms.internal.jx;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Product {
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

    public Product setBrand(String value) {
        put("br", value);
        return this;
    }

    public Product setCategory(String value) {
        put("ca", value);
        return this;
    }

    public Product setCouponCode(String value) {
        put("cc", value);
        return this;
    }

    public Product setCustomDimension(int index, String value) {
        put(s.E(index), value);
        return this;
    }

    public Product setCustomMetric(int index, int value) {
        put(s.F(index), Integer.toString(value));
        return this;
    }

    public Product setId(String value) {
        put(AnalyticsEvent.EVENT_ID, value);
        return this;
    }

    public Product setName(String value) {
        put("nm", value);
        return this;
    }

    public Product setPosition(int value) {
        put("ps", Integer.toString(value));
        return this;
    }

    public Product setPrice(double value) {
        put("pr", Double.toString(value));
        return this;
    }

    public Product setQuantity(int value) {
        put("qt", Integer.toString(value));
        return this;
    }

    public Product setVariant(String value) {
        put("va", value);
        return this;
    }
}
