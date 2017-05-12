package com.inmobi.monetization.internal.carb;

import com.givewaygames.gwgl.shader.pixel.MirrorPixelShader;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.uid.UIDMapConfigParams;
import java.util.Map;

public class CarbConfigParams {
    boolean a = false;
    String b = "http://dock.inmobi.com/carb/v1/i";
    String c = "http://dock.inmobi.com/carb/v1/o";
    long d = 86400;
    int e = 3;
    long f = 60;
    long g = 60;
    private UIDMapConfigParams h = new UIDMapConfigParams();

    public Map<String, Boolean> getDeviceIdMaskMap() {
        return this.h.getMap();
    }

    public boolean isCarbEnabled() {
        return this.a;
    }

    public String getCarbEndpoint() {
        return this.b;
    }

    public String getCarbPostpoint() {
        return this.c;
    }

    public long getRetreiveFrequncy() {
        return this.d * 1000;
    }

    public int getRetryCount() {
        return this.e;
    }

    public long getRetryInterval() {
        return this.f;
    }

    public long getTimeoutInterval() {
        return this.g;
    }

    public void setFromMap(Map<String, Object> map) {
        try {
            this.h.setMap(InternalSDKUtil.getObjectFromMap(map, "ids"));
            this.a = InternalSDKUtil.getBooleanFromMap(map, MirrorPixelShader.UNIFORM_ENABLED);
            this.b = InternalSDKUtil.getStringFromMap(map, "gep");
            if (this.b.startsWith("http") || this.b.startsWith("https")) {
                this.c = InternalSDKUtil.getStringFromMap(map, "pep");
                if (this.c.startsWith("http") || this.c.startsWith("https")) {
                    this.d = InternalSDKUtil.getLongFromMap(map, "fq_s", 1, Long.MAX_VALUE);
                    this.e = InternalSDKUtil.getIntFromMap(map, "mr", 0, 2147483647L);
                    this.f = InternalSDKUtil.getLongFromMap(map, "ri", 1, Long.MAX_VALUE);
                    this.g = InternalSDKUtil.getLongFromMap(map, "to", 1, Long.MAX_VALUE);
                    return;
                }
                throw new IllegalArgumentException("URL wrong");
            }
            throw new IllegalArgumentException("URL wrong");
        } catch (IllegalArgumentException e) {
            Log.internal("CarbConfigParams", "Invalid value");
            this.a = false;
            this.b = "http://dock.inmobi.com/carb/v1/i";
            this.c = "http://dock.inmobi.com/carb/v1/o";
            this.d = 86400;
            this.e = 3;
            this.f = 60;
            this.g = 60;
            throw new IllegalArgumentException();
        }
    }
}
