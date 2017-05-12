package com.inmobi.commons.analytics.iat.impl.config;

import com.inmobi.commons.internal.InternalSDKUtil;
import java.util.Map;

public class AdTrackerGoalRetryParams {
    private int a = 1000;
    private int b = 900;

    public int getMaxWaitTime() {
        return this.b * 1000;
    }

    public int getMaxRetry() {
        return this.a;
    }

    public void setFromMap(Map<String, Object> map) {
        this.a = InternalSDKUtil.getIntFromMap(map, "mr", 0, 2147483647L);
        this.b = InternalSDKUtil.getIntFromMap(map, "mw", 0, 2147483647L);
    }
}
