package com.flurry.sdk;

public enum aw {
    EV_UNKNOWN("unknown"),
    EV_REQUESTED("requested"),
    EV_REQUEST_AD_COMPONENTS("requestAdComponents"),
    EV_FILLED("filled"),
    EV_UNFILLED("unfilled"),
    EV_RENDERED("rendered"),
    EV_RENDER_FAILED("renderFailed"),
    EV_CLICKED("clicked"),
    EV_VIDEO_START("videoStart"),
    EV_VIDEO_COMPLETED("videoCompleted"),
    EV_VIDEO_PROGRESSED("videoProgressed"),
    EV_SENT_TO_URL("sentToUrl"),
    EV_ASYNC_NOTIFICATION("asyncNotification"),
    EV_URL_VERIFIED("urlVerified"),
    EV_URL_NOT_VERIFIED("urlNotVerified"),
    EV_LAUNCH_URL_FAILED("launchUrlFailed"),
    EV_USER_CONFIRMED("userConfirmed"),
    EV_USER_CANCELLED("userCanceled"),
    EV_AD_WILL_CLOSE("adWillClose"),
    EV_AD_CLOSED("adClosed"),
    EV_REQUEST_AD_EXPAND("expand"),
    EV_REQUEST_AD_COLLAPSE("collapse"),
    EV_REQUEST_LAUNCH_URL("open"),
    EV_AD_LISTENER_ADDED("eventListenerAdded"),
    EV_FILLED_FROM_SERVER("filledFromServer"),
    EV_FILLED_FROM_CACHE("filledFromCache"),
    EV_COMPLETED_FULL_LOAD("didCompleteFullLoad"),
    EV_CAP_EXHAUSTED("capExhausted"),
    EV_CAP_NOT_EXHAUSTED("capNotExhausted"),
    EV_LOADING_VIDEO_CLOSED("loadingVideoClosed"),
    EV_VIDEO_FIRST_QUARTILE("videoFirstQuartile"),
    EV_VIDEO_MIDPOINT("videoMidpoint"),
    EV_VIDEO_THIRD_QUARTILE("videoThirdQuartile"),
    EV_VIDEO_CLICKED("videoClicked"),
    EV_REWARD_GRANTED("rewardGranted"),
    EV_SEND_URL_STATUS_RESULT("sendUrlStatusResult"),
    EV_PREPARED("prepared"),
    EV_AD_UNIT_MERGED("adunitMerged"),
    EV_PRIVACY("privacy"),
    INTERNAL_EV_AD_OPENED("adOpened"),
    INTERNAL_EV_APP_EXIT("appExit"),
    EV_STREAM_IMPRESSION("streamImpression"),
    EV_NATIVE_IMPRESSION("nativeImpression"),
    EV_MRAID_NOT_SUPPORTED("mraidAdNotSupported"),
    EV_MRAID_CLOSE_BUTTON_VISIBLE("mraidCloseButtonVisible");
    
    private final String T;

    private aw(String str) {
        this.T = str;
    }

    public String a() {
        return this.T;
    }

    public String toString() {
        return this.T;
    }
}
