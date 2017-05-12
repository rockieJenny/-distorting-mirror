package com.flurry.android.impl.ads.protocol.v13;

import java.util.List;

public class AdFrame {
    public String adGuid;
    public AdSpaceLayout adSpaceLayout;
    public long assetExpirationTimestampUTCMillis;
    public int binding;
    public List<String> cacheBlacklistedAssets;
    public List<String> cacheWhitelistedAssets;
    public int cachingEnum;
    public List<Callback> callbacks;
    public String content;
    public String display;
}
