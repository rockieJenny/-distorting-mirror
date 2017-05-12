package com.flurry.android.impl.ads.protocol.v13;

import java.util.List;
import java.util.Map;

public class AdRequest {
    public List<AdReportedId> adReportedIds;
    public String adSpaceName;
    public boolean adTrackingEnabled;
    public AdViewContainer adViewContainer;
    public AdViewType adViewType;
    public String agentVersion;
    public String apiKey;
    public String appBundleId;
    public String bCookie;
    public List<String> bcat;
    public List<Integer> bindings;
    public boolean canDoSKAppStore;
    public Map<String, String> clientSideRtbPayload;
    public String devicePlatform;
    public List<FrequencyCapRequestInfo> frequencyCapRequestInfoList;
    public Map<String, String> keywords;
    public String locale;
    public Location location;
    public NativeAdConfiguration nativeAdConfiguration;
    public int networkStatus;
    public List<String> origins;
    public String osVersion;
    public String preferredLanguage;
    public boolean renderTime;
    public long requestTime;
    public boolean sendConfiguration;
    public long sessionId;
    public List<StreamInfo> streamInfoList;
    public TargetingOverride targetingOverride;
    public boolean testDevice;
    public String timezone;
    public String userAgent;
}
