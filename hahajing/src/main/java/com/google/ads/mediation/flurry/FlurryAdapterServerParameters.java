package com.google.ads.mediation.flurry;

import com.google.ads.mediation.MediationServerParameters;

public final class FlurryAdapterServerParameters extends MediationServerParameters {
    @Parameter(name = "adSpaceName", required = false)
    public String adSpaceName;
    @Parameter(name = "projectApiKey")
    public String projectApiKey;
}
