package com.millennialmedia.android;

import java.util.Map;
import java.util.concurrent.Callable;

class BridgeMMInlineVideo extends MMJSObject {
    private static final String ADJUST_VIDEO = "adjustVideo";
    private static final String INSERT_VIDEO = "insertVideo";
    private static final String PAUSE_VIDEO = "pauseVideo";
    private static final String PLAY_VIDEO = "playVideo";
    private static final String REMOVE_VIDEO = "removeVideo";
    private static final String RESUME_VIDEO = "resumeVideo";
    private static final String SET_STREAM_VIDEO_SOURCE = "setStreamVideoSource";
    private static final String STOP_VIDEO = "stopVideo";

    BridgeMMInlineVideo() {
    }

    public MMJSResponse insertVideo(final Map<String, String> parameters) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView mmWebView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (mmWebView == null) {
                    return MMJSResponse.responseWithError();
                }
                MMLayout mmLayout = mmWebView.getMMLayout();
                mmLayout.initInlineVideo(new InlineParams(parameters, mmWebView.getContext()));
                return MMJSResponse.responseWithSuccess("usingStreaming=" + mmLayout.isVideoPlayingStreaming());
            }
        });
    }

    public MMJSResponse removeVideo(Map<String, String> map) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView webView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (webView != null) {
                    MMLayout mmLayout = webView.getMMLayout();
                    if (mmLayout != null) {
                        mmLayout.removeVideo();
                        return MMJSResponse.responseWithSuccess();
                    }
                }
                return MMJSResponse.responseWithError();
            }
        });
    }

    public MMJSResponse playVideo(Map<String, String> map) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView webView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (webView != null) {
                    MMLayout mmLayout = webView.getMMLayout();
                    if (mmLayout != null) {
                        mmLayout.playVideo();
                        return MMJSResponse.responseWithSuccess();
                    }
                }
                return MMJSResponse.responseWithError();
            }
        });
    }

    public MMJSResponse adjustVideo(final Map<String, String> parameters) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView webView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (webView == null || webView == null || !webView.getMMLayout().adjustVideo(new InlineParams(parameters, webView.getContext()))) {
                    return MMJSResponse.responseWithError();
                }
                return MMJSResponse.responseWithSuccess();
            }
        });
    }

    public MMJSResponse stopVideo(Map<String, String> map) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView webView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (webView != null) {
                    MMLayout mmLayout = webView.getMMLayout();
                    if (mmLayout != null) {
                        mmLayout.stopVideo();
                        return MMJSResponse.responseWithSuccess();
                    }
                }
                return MMJSResponse.responseWithError();
            }
        });
    }

    public MMJSResponse pauseVideo(Map<String, String> map) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView webView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (webView != null) {
                    MMLayout mmLayout = webView.getMMLayout();
                    if (mmLayout != null) {
                        mmLayout.pauseVideo();
                        return MMJSResponse.responseWithSuccess();
                    }
                }
                return MMJSResponse.responseWithError();
            }
        });
    }

    public MMJSResponse resumeVideo(Map<String, String> map) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView webView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (webView != null) {
                    MMLayout mmLayout = webView.getMMLayout();
                    if (mmLayout != null) {
                        mmLayout.resumeVideo();
                        return MMJSResponse.responseWithSuccess();
                    }
                }
                return MMJSResponse.responseWithError();
            }
        });
    }

    public MMJSResponse setStreamVideoSource(final Map<String, String> parameters) {
        return runOnUiThreadFuture(new Callable<MMJSResponse>() {
            public MMJSResponse call() {
                MMWebView webView = (MMWebView) BridgeMMInlineVideo.this.mmWebViewRef.get();
                if (webView != null) {
                    MMLayout mmLayout = webView.getMMLayout();
                    String streamVideoURI = (String) parameters.get("streamVideoURI");
                    if (!(mmLayout == null || streamVideoURI == null)) {
                        mmLayout.setVideoSource(streamVideoURI);
                        return MMJSResponse.responseWithSuccess();
                    }
                }
                return MMJSResponse.responseWithError();
            }
        });
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (ADJUST_VIDEO.equals(name)) {
            return adjustVideo(arguments);
        }
        if (INSERT_VIDEO.equals(name)) {
            return insertVideo(arguments);
        }
        if (PAUSE_VIDEO.equals(name)) {
            return pauseVideo(arguments);
        }
        if (PLAY_VIDEO.equals(name)) {
            return playVideo(arguments);
        }
        if (REMOVE_VIDEO.equals(name)) {
            return removeVideo(arguments);
        }
        if (RESUME_VIDEO.equals(name)) {
            return resumeVideo(arguments);
        }
        if (SET_STREAM_VIDEO_SOURCE.equals(name)) {
            return setStreamVideoSource(arguments);
        }
        if (STOP_VIDEO.equals(name)) {
            return stopVideo(arguments);
        }
        return null;
    }
}
