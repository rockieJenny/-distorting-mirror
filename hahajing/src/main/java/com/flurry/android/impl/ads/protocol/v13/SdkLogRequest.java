package com.flurry.android.impl.ads.protocol.v13;

import java.util.List;

public class SdkLogRequest {
    public List<AdReportedId> adReportedIds;
    public long agentTimestamp;
    public String agentVersion;
    public String apiKey;
    public List<SdkAdLog> sdkAdLogs;
    public boolean testDevice;
}
