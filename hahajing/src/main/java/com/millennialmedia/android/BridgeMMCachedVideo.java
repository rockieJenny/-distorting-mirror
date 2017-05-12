package com.millennialmedia.android;

import android.app.Activity;
import android.content.Context;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;

class BridgeMMCachedVideo extends MMJSObject implements AdCacheTaskListener {
    private static final String AVAILABLE_CACHED_VIDEOS = "availableCachedVideos";
    private static final String CACHE_VIDEO = "cacheVideo";
    private static final String END_VIDEO = "endVideo";
    private static final String PAUSE_VIDEO = "pauseVideo";
    private static final String PLAY_CACHED_VIDEO = "playCachedVideo";
    private static final String PLAY_VIDEO = "playVideo";
    private static final String RESTART_VIDEO = "restartVideo";
    private static final String TAG = "BridgeMMCachedVideo";
    private static final String VIDEO_ID_EXISTS = "videoIdExists";
    private boolean success;

    BridgeMMCachedVideo() {
    }

    @Deprecated
    public MMJSResponse videoIdExists(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String videoId = (String) arguments.get("videoId");
        if (!(videoId == null || context == null)) {
            VideoAd ad = (VideoAd) AdCache.load(context, videoId);
            if (!(ad == null || !ad.isOnDisk(context) || ad.isExpired())) {
                return MMJSResponse.responseWithSuccess(videoId);
            }
        }
        return null;
    }

    public MMJSResponse availableCachedVideos(Map<String, String> map) {
        final Context context = (Context) this.contextRef.get();
        if (context == null) {
            return null;
        }
        final JSONArray array = new JSONArray();
        AdCache.iterateCachedAds(context, 2, new Iterator() {
            boolean callback(CachedAd ad) {
                if ((ad instanceof VideoAd) && ad.isOnDisk(context) && !ad.isExpired()) {
                    array.put(ad.getId());
                }
                return true;
            }
        });
        MMJSResponse response = new MMJSResponse();
        response.result = 1;
        response.response = array;
        return response;
    }

    public MMJSResponse playCachedVideo(Map<String, String> arguments) {
        Context context = (Context) this.contextRef.get();
        String videoId = (String) arguments.get("videoId");
        if (videoId == null || context == null) {
            return null;
        }
        VideoAd ad = (VideoAd) AdCache.load(context, videoId);
        if (ad == null || !ad.canShow(context, null, false)) {
            return null;
        }
        ad.show(context, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        return MMJSResponse.responseWithSuccess(String.format("Playing Video(%s)", new Object[]{videoId}));
    }

    public void downloadCompleted(CachedAd ad, boolean success) {
        synchronized (this) {
            Context context = (Context) this.contextRef.get();
            if (success && context != null) {
                AdCache.save(context, ad);
            }
            this.success = success;
            notify();
        }
    }

    public synchronized MMJSResponse cacheVideo(Map<String, String> arguments) {
        MMJSResponse mMJSResponse = null;
        synchronized (this) {
            Context context = (Context) this.contextRef.get();
            String url = (String) arguments.get("url");
            if (!(url == null || context == null)) {
                try {
                    HttpResponse httpResponse = new HttpGetRequest().get(url);
                    if (httpResponse == null) {
                        MMLog.i(TAG, "HTTP response is null");
                    } else {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        if (httpEntity == null) {
                            MMLog.d(TAG, "Null HTTP entity");
                        } else if (httpEntity.getContentLength() == 0) {
                            MMLog.d(TAG, "Millennial ad return failed. Zero content length returned.");
                        } else {
                            Header httpHeader = httpEntity.getContentType();
                            if (!(httpHeader == null || httpHeader.getValue() == null || !httpHeader.getValue().equalsIgnoreCase("application/json"))) {
                                try {
                                    VideoAd videoAd = new VideoAd(HttpGetRequest.convertStreamToString(httpEntity.getContent()));
                                    if (videoAd != null && videoAd.isValid()) {
                                        videoAd.downloadPriority = 3;
                                        if (AdCache.startDownloadTask(context, null, videoAd, this)) {
                                            try {
                                                wait();
                                                if (this.success) {
                                                    mMJSResponse = MMJSResponse.responseWithSuccess(String.format("Cached video(%s)", new Object[]{url}));
                                                } else {
                                                    notify();
                                                }
                                            } catch (InterruptedException e) {
                                                MMLog.e(TAG, "Caching interrupted: ", e);
                                            } finally {
                                                notify();
                                            }
                                        } else {
                                            mMJSResponse = MMJSResponse.responseWithError(String.format("Unable to start download for Cached video(%s)", new Object[]{url}));
                                        }
                                    }
                                } catch (IllegalStateException e1) {
                                    MMLog.e(TAG, "Millennial ad return failed. Invalid response data.", e1);
                                } catch (IOException e12) {
                                    MMLog.e(TAG, "Millennial ad return failed. Invalid response data.", e12);
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    MMLog.e(TAG, "HTTP error: ", e2);
                }
            }
        }
        return mMJSResponse;
    }

    public MMJSResponse playVideo(Map<String, String> map) {
        final VideoPlayerActivity vpa = getVPA();
        if (vpa != null) {
            return runOnUiThreadFuture(new Callable<MMJSResponse>() {
                public MMJSResponse call() {
                    vpa.resumeVideo();
                    return MMJSResponse.responseWithSuccess();
                }
            });
        }
        return null;
    }

    public MMJSResponse endVideo(Map<String, String> map) {
        final VideoPlayerActivity vpa = getVPA();
        if (vpa != null) {
            return runOnUiThreadFuture(new Callable<MMJSResponse>() {
                public MMJSResponse call() {
                    vpa.endVideo();
                    return MMJSResponse.responseWithSuccess();
                }
            });
        }
        return null;
    }

    public MMJSResponse pauseVideo(Map<String, String> map) {
        final VideoPlayerActivity vpa = getVPA();
        if (vpa != null) {
            return runOnUiThreadFuture(new Callable<MMJSResponse>() {
                public MMJSResponse call() {
                    vpa.pauseVideoByUser();
                    return MMJSResponse.responseWithSuccess();
                }
            });
        }
        return null;
    }

    public MMJSResponse restartVideo(Map<String, String> map) {
        final VideoPlayerActivity vpa = getVPA();
        if (vpa != null) {
            return runOnUiThreadFuture(new Callable<MMJSResponse>() {
                public MMJSResponse call() {
                    vpa.restartVideo();
                    return MMJSResponse.responseWithSuccess();
                }
            });
        }
        return null;
    }

    private VideoPlayerActivity getVPA() {
        if (this.mmWebViewRef == null || this.mmWebViewRef.get() == null || !(((MMWebView) this.mmWebViewRef.get()).getActivity() instanceof MMActivity)) {
            return null;
        }
        MMWebView webView = (MMWebView) this.mmWebViewRef.get();
        if (webView == null) {
            return null;
        }
        Activity activity = webView.getActivity();
        if (activity == null || !(activity instanceof MMActivity)) {
            return null;
        }
        MMActivity mmActivity = (MMActivity) activity;
        if (mmActivity.getWrappedActivity() == null || !(mmActivity.getWrappedActivity() instanceof VideoPlayerActivity)) {
            return null;
        }
        return (VideoPlayerActivity) mmActivity.getWrappedActivity();
    }

    public void downloadStart(CachedAd ad) {
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (AVAILABLE_CACHED_VIDEOS.equals(name)) {
            return availableCachedVideos(arguments);
        }
        if (CACHE_VIDEO.equals(name)) {
            return cacheVideo(arguments);
        }
        if (END_VIDEO.equals(name)) {
            return endVideo(arguments);
        }
        if (PAUSE_VIDEO.equals(name)) {
            return pauseVideo(arguments);
        }
        if (PLAY_CACHED_VIDEO.equals(name)) {
            return playCachedVideo(arguments);
        }
        if (PLAY_VIDEO.equals(name)) {
            return playVideo(arguments);
        }
        if (RESTART_VIDEO.equals(name)) {
            return restartVideo(arguments);
        }
        if (VIDEO_ID_EXISTS.equals(name)) {
            return videoIdExists(arguments);
        }
        return null;
    }
}
