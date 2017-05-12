package com.flurry.sdk;

public enum av {
    kUnknown(-1),
    kSuccess(0),
    kNoNetworkConnectivity(1),
    kMissingAdController(2),
    kNoContext(3),
    kInvalidAdUnit(4),
    kInvalidVASTAd(5),
    kPrecachingDownloadFailed(6),
    kPrecachingCopyFailed(7),
    kPrecachingMissingAssets(8),
    kPrerenderDownloadFailed(9),
    kAdRequestTimeout(10),
    kVASTResolveTimeout(11),
    kCSRTBAuctionTimeout(12),
    kPrerenderDownloadTimeout(13),
    kPrepareFailed(14),
    kAdDisplayError(15),
    kVideoPlaybackError(16),
    kNotReady(17),
    kWrongOrientation(18),
    kNoViewGroup(19),
    kUnfilled(20),
    kIncorrectClassForAdSpace(21),
    kDeviceLocked(22);
    
    private int y;

    private av(int i) {
        this.y = i;
    }

    public int a() {
        return this.y;
    }
}
