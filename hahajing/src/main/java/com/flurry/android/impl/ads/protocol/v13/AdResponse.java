package com.flurry.android.impl.ads.protocol.v13;

import java.util.List;

public class AdResponse {
    public List<AdUnit> adUnits;
    public ConfigurationUnion configuration;
    public List<String> errors;
    public List<FrequencyCapResponseInfo> frequencyCapResponseInfoList;
}
