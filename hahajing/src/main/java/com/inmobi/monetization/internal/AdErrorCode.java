package com.inmobi.monetization.internal;

public enum AdErrorCode {
    INVALID_REQUEST("Invalid ad request"),
    INTERNAL_ERROR("An error occurred while fetching the ad"),
    NO_FILL("The ad request was successful, but no ad was returned"),
    AD_CLICK_IN_PROGRESS("Ad click is in progress, cannot load new ad"),
    AD_DOWNLOAD_IN_PROGRESS("Ad download is in progress, cannot load new ad"),
    INVALID_APP_ID("Invalid App Id"),
    ADREQUEST_CANCELLED("Stop loading invoked on the ad"),
    AD_RENDERING_TIMEOUT("Failed to render ad"),
    DO_MONETIZE("Please load a mediation network"),
    DO_NOTHING("No Ads"),
    NETWORK_ERROR("Ad network failed to retrieve ad");
    
    private String a;

    private AdErrorCode(String str) {
        this.a = str;
    }

    public String toString() {
        return this.a;
    }

    public void setMessage(String str) {
        this.a = str;
    }
}
