package com.millennialmedia.android;

import android.content.Context;
import android.text.TextUtils;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

class PreCacheWorker extends Thread {
    private static final String TAG = "PreCacheWorker";
    private static boolean busy;
    private Context appContext;
    private DTOCachedVideo[] cachedVideos;
    private volatile boolean downloadedNewVideos = false;
    private String noVideosToCacheURL;

    static synchronized void preCacheVideos(DTOCachedVideo[] cachedVideos, Context context, String noVideosToCacheURL) {
        synchronized (PreCacheWorker.class) {
            if (!busy) {
                busy = true;
                new PreCacheWorker(cachedVideos, context, noVideosToCacheURL).start();
            }
        }
    }

    private PreCacheWorker(DTOCachedVideo[] cachedVideos, Context context, String noVideosToCacheURL) {
        this.cachedVideos = cachedVideos;
        this.noVideosToCacheURL = noVideosToCacheURL;
        this.appContext = context.getApplicationContext();
    }

    public synchronized void run() {
        if (this.cachedVideos != null) {
            for (DTOCachedVideo cachedVideo : this.cachedVideos) {
                try {
                    HttpResponse httpResponse = new HttpGetRequest().get(cachedVideo.url);
                    if (httpResponse == null) {
                        MMLog.d(TAG, "Pre cache worker: HTTP response is null");
                    } else {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        if (httpEntity == null) {
                            MMLog.d(TAG, "Pre cache worker: Null HTTP entity");
                        } else if (httpEntity.getContentLength() == 0) {
                            MMLog.d(TAG, "Pre cache worker: Millennial ad return failed. Zero content length returned.");
                        } else {
                            handleContent(cachedVideo, httpEntity);
                        }
                    }
                } catch (Exception e) {
                    try {
                        MMLog.d(TAG, String.format("Pre cache worker HTTP error: %s", new Object[]{e}));
                    } catch (Throwable th) {
                        synchronized (PreCacheWorker.class) {
                            busy = false;
                            if (!(this.downloadedNewVideos || TextUtils.isEmpty(this.noVideosToCacheURL) || this.cachedVideos != null)) {
                                Event.logEvent(this.noVideosToCacheURL);
                            }
                        }
                    }
                }
            }
        }
        synchronized (PreCacheWorker.class) {
            busy = false;
            if (!(this.downloadedNewVideos || TextUtils.isEmpty(this.noVideosToCacheURL) || this.cachedVideos != null)) {
                Event.logEvent(this.noVideosToCacheURL);
            }
        }
        return;
    }

    private void handleContent(DTOCachedVideo cachedVideo, HttpEntity httpEntity) {
        Header httpHeader = httpEntity.getContentType();
        if (httpHeader != null) {
            String contentType = httpHeader.getValue();
            if (contentType != null && contentType.equalsIgnoreCase("application/json")) {
                handleJson(cachedVideo, httpEntity);
            } else if (contentType != null && contentType.startsWith("video/")) {
                handleVideoFile(cachedVideo, httpEntity);
            }
        }
    }

    private void handleJson(final DTOCachedVideo cachedVideo, HttpEntity httpEntity) {
        VideoAd videoAd = null;
        try {
            String json = HttpGetRequest.convertStreamToString(httpEntity.getContent());
            if (!TextUtils.isEmpty(json)) {
                videoAd = new VideoAd(json);
            }
            if (videoAd != null && videoAd.isValid()) {
                try {
                    videoAd.downloadPriority = 1;
                    if (AdCache.startDownloadTask(this.appContext, null, videoAd, new AdCacheTaskListener() {
                        public void downloadStart(CachedAd ad) {
                            Event.logEvent(cachedVideo.preCacheStartURL);
                        }

                        public synchronized void downloadCompleted(CachedAd ad, boolean success) {
                            if (success) {
                                AdCache.save(PreCacheWorker.this.appContext, ad);
                                PreCacheWorker.this.downloadedNewVideos = true;
                                Event.logEvent(cachedVideo.preCacheCompleteURL);
                            } else {
                                Event.logEvent(cachedVideo.preCacheFailedURL);
                            }
                            notify();
                        }
                    })) {
                        wait();
                        return;
                    }
                    Event.logEvent(cachedVideo.preCacheStartURL);
                    Event.logEvent(cachedVideo.preCacheFailedURL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    MMLog.e(TAG, "Pre cache worker interrupted: ", e);
                }
            }
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
            MMLog.d(TAG, "Pre cache worker: Millennial ad return failed. Invalid response data.");
        } catch (IOException e12) {
            e12.printStackTrace();
            MMLog.d(TAG, "Pre cache worker: Millennial ad return failed. Invalid response data.");
        }
    }

    private void handleVideoFile(DTOCachedVideo cachedVideo, HttpEntity httpEntity) {
        if (!TextUtils.isEmpty(cachedVideo.videoFileId)) {
            Event.logEvent(cachedVideo.preCacheStartURL);
            if (AdCache.downloadComponentInternalCache(cachedVideo.url, cachedVideo.videoFileId + "video.dat", this.appContext)) {
                Event.logEvent(cachedVideo.preCacheCompleteURL);
            } else {
                Event.logEvent(cachedVideo.preCacheFailedURL);
            }
        }
    }
}
