package com.inmobi.monetization;

import com.inmobi.monetization.internal.AdErrorCode;

public enum IMErrorCode {
    INVALID_REQUEST("Invalid ad request"),
    INTERNAL_ERROR("An error occurred while fetching the ad"),
    NO_FILL("The ad request was successful, but no ad was returned"),
    DO_MONETIZE("Please load a mediation network"),
    DO_NOTHING("No Ads"),
    NETWORK_ERROR("Ad network failed to retrieve ad");
    
    private String a;

    private IMErrorCode(String str) {
        this.a = str;
    }

    public String toString() {
        return this.a;
    }

    public void setMessage(String str) {
        this.a = str;
    }

    static IMErrorCode a(AdErrorCode adErrorCode) {
        IMErrorCode iMErrorCode = INTERNAL_ERROR;
        switch (adErrorCode) {
            case INVALID_REQUEST:
            case AD_CLICK_IN_PROGRESS:
            case AD_DOWNLOAD_IN_PROGRESS:
            case INVALID_APP_ID:
                iMErrorCode = INVALID_REQUEST;
                break;
            case AD_RENDERING_TIMEOUT:
            case INTERNAL_ERROR:
                iMErrorCode = INTERNAL_ERROR;
                break;
            case NO_FILL:
                iMErrorCode = NO_FILL;
                break;
            case NETWORK_ERROR:
                iMErrorCode = NETWORK_ERROR;
                break;
            case DO_MONETIZE:
                iMErrorCode = DO_MONETIZE;
                break;
            case DO_NOTHING:
                iMErrorCode = DO_NOTHING;
                break;
            default:
                iMErrorCode = INTERNAL_ERROR;
                break;
        }
        iMErrorCode.setMessage(adErrorCode.toString());
        return iMErrorCode;
    }
}
