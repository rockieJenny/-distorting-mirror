package com.inmobi.monetization.internal.configs;

import com.inmobi.commons.internal.InternalSDKUtil;
import java.util.Map;

public class NativeConfigParams {
    int a = 100;
    int b = 1;
    int c = 2;
    long d = 3300;

    public int getmMaxCacheSize() {
        return this.a;
    }

    public int getmMinLimit() {
        return this.b;
    }

    public int getmFetchLimit() {
        return this.c;
    }

    public long getTimeToLive() {
        return this.d;
    }

    public void setFromMap(Map<String, Object> map) {
        this.a = InternalSDKUtil.getIntFromMap(map, "mcl", 1, 2147483647L);
        this.b = InternalSDKUtil.getIntFromMap(map, "mt", 1, 2147483647L);
        this.c = InternalSDKUtil.getIntFromMap(map, "fl", 1, 2147483647L);
        this.d = InternalSDKUtil.getLongFromMap(map, "ttl", 1, Long.MAX_VALUE);
    }
}
