package com.flurry.android.impl.ads.protocol.v13;

import java.util.List;
import java.util.Map;

public class AdUnit {
    public List<AdFrame> adFrames;
    public String adSpace;
    public AdViewType adViewType;
    public String adomain;
    public Map<String, String> clientSideRtbPayload;
    public long closableTimeMillis15SecOrLess;
    public long closableTimeMillisLongerThan15Sec;
    public int combinable;
    public long expiration;
    public List<FrequencyCapResponseInfo> frequencyCapResponseInfoList;
    public String groupId;
    public NativeAdInfo nativeAdInfo;
    public int preCacheAdSkippableTimeLimitMillis;
    public boolean preRender;
    public long preRenderTimeoutMillis;
    public long price;
    public boolean renderTime;
    public boolean rewardable;
    public ScreenOrientationType screenOrientation;
    public boolean supportMRAID;
    public boolean videoAutoPlay;
    public int videoPctCompletionForMoreInfo;
    public int videoPctCompletionForReward;
    public long viewabilityDurationMillis;
    public int viewabilityPercentVisible;
}
