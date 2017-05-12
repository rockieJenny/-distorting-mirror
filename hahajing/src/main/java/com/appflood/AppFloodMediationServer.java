package com.appflood;

import com.google.ads.mediation.MediationServerParameters;
import java.util.Map;

public class AppFloodMediationServer extends MediationServerParameters {
    public String appKey;
    public String appSecret;

    public void load(Map<String, String> map) {
        this.appKey = (String) map.get("app_key");
        this.appSecret = (String) map.get("app_secret");
    }
}
