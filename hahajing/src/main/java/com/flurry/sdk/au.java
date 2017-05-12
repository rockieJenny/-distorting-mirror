package com.flurry.sdk;

public enum au {
    AC_UNKNOWN("unknown"),
    AC_NOTIFY_USER("notifyUser"),
    AC_LOG_EVENT("logEvent"),
    AC_PROCESS_REDIRECT("processRedirect"),
    AC_NEXT_FRAME("nextFrame"),
    AC_NEXT_AD_UNIT("nextAdUnit"),
    AC_CLOSE_AD("closeAd"),
    AC_VERIFY_URL("verifyUrl"),
    AC_SEND_URL_ASYNC("sendUrlAsync"),
    AC_SEND_AD_LOGS("sendAdLogs"),
    AC_LOAD_AD_COMPONENTS("loadAdComponents"),
    AC_DIRECT_OPEN("directOpen"),
    AC_LOAD_COMPLETE("adFullyLoaded"),
    AC_DELETE("delete"),
    AC_CHECK_CAP("checkCap"),
    AC_UPDATE_VIEW_COUNT("updateViewCount"),
    AC_LAUNCH_PACKAGE("launchPackage"),
    AC_MRAID_DO_EXPAND("doExpand"),
    AC_MRAID_DO_COLLAPSE("doCollapse"),
    AC_MRAID_PLAY_VIDEO("MRAIDplayVideo"),
    AC_MRAID_OPEN("MRAIDOpen");
    
    private final String v;

    private au(String str) {
        this.v = str;
    }

    public String toString() {
        return this.v;
    }
}
