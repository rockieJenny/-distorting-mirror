package com.inmobi.commons.metric;

import com.inmobi.commons.cache.ProductCacheConfig;
import com.inmobi.commons.internal.InternalSDKUtil;
import java.util.Map;

public class MetricConfigParams {
    private int a = ProductCacheConfig.DEFAULT_EXPIRY;
    private int b = 1000;
    private int c = 1000;
    private int d = 10;
    private String e = "https://sdkm.w.inmobi.com/metrics/e.asm";

    public int getNextRetryInterval() {
        return this.a;
    }

    public int getMaxInQueue() {
        return this.b;
    }

    public int getSamplingFactor() {
        return this.c;
    }

    public String getUrl() {
        return this.e;
    }

    public int getDumpThreshhold() {
        return this.d;
    }

    public void setFromMap(Map<String, Object> map) {
        this.c = InternalSDKUtil.getIntFromMap(map, "sf", 1, 2147483647L);
        this.d = InternalSDKUtil.getIntFromMap(map, "dt", 1, 2147483647L);
        this.b = InternalSDKUtil.getIntFromMap(map, "max", 1, 2147483647L);
        this.a = InternalSDKUtil.getIntFromMap(map, "nri", 1, 2147483647L);
        this.e = InternalSDKUtil.getStringFromMap(map, "url");
    }
}
