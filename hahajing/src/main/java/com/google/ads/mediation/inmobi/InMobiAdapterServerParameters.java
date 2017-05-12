package com.google.ads.mediation.inmobi;

import com.google.ads.mediation.MediationServerParameters;

public class InMobiAdapterServerParameters extends MediationServerParameters {
    @Parameter(name = "pubid")
    public String appId;
    @Parameter(name = "isUDIDHashAllowed", required = false)
    public String isUDIDHashAllowed = "true";
}
