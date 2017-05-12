package com.inmobi.monetization.internal.configs;

import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.metric.MetricConfigParams;
import com.inmobi.commons.uid.UIDMapConfigParams;
import com.inmobi.monetization.internal.Ad;
import java.util.Map;

public class ConfigParams {
    int a = 20;
    int b = 60;
    int c = 60;
    int d = 60;
    IMAIConfigParams e = new IMAIConfigParams();
    MetricConfigParams f = new MetricConfigParams();
    NativeConfigParams g = new NativeConfigParams();
    PlayableAdsConfigParams h = new PlayableAdsConfigParams();
    private UIDMapConfigParams i = new UIDMapConfigParams();

    public int getMinimumRefreshRate() {
        return this.a;
    }

    public int getDefaultRefreshRate() {
        return this.b;
    }

    public int getFetchTimeOut() {
        return this.c * 1000;
    }

    public int getRenderTimeOut() {
        return this.d * 1000;
    }

    public IMAIConfigParams getImai() {
        return this.e;
    }

    public MetricConfigParams getMetric() {
        return this.f;
    }

    public Map<String, Boolean> getDeviceIdMaskMap() {
        return this.i.getMap();
    }

    public PlayableAdsConfigParams getPlayableConfigParams() {
        return this.h;
    }

    public NativeConfigParams getNativeSdkConfigParams() {
        return this.g;
    }

    public void setFromMap(Map<String, Object> map) {
        this.a = InternalSDKUtil.getIntFromMap(map, "mrr", 1, 2147483647L);
        this.b = InternalSDKUtil.getIntFromMap(map, "drr", -1, 2147483647L);
        this.c = InternalSDKUtil.getIntFromMap(map, "fto", 1, 2147483647L);
        this.d = InternalSDKUtil.getIntFromMap(map, "rto", 1, 2147483647L);
        this.e.setFromMap((Map) map.get("imai"));
        this.f.setFromMap((Map) map.get("metric"));
        this.i.setMap(InternalSDKUtil.getObjectFromMap(map, "ids"));
        this.g.setFromMap((Map) map.get(Ad.AD_TYPE_NATIVE));
        this.h.setFromMap((Map) map.get("playable"));
    }
}
