package com.inmobi.monetization.internal.imai;

import android.content.Context;
import android.os.Handler;
import com.inmobi.commons.analytics.net.AnalyticsCommon.HttpRequestCallback;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.Constants;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.imai.db.ClickData;
import java.util.concurrent.atomic.AtomicBoolean;

public final class RequestResponseManager {
    static Thread a;
    static Handler b = new Handler();
    static AtomicBoolean c = null;
    private static AtomicBoolean g = null;
    private static AtomicBoolean i = null;
    public static AtomicBoolean isSynced;
    public static IMAIClickEventList mDBWriterQueue = new IMAIClickEventList();
    public static IMAIClickEventList mNetworkQueue = null;
    String d = "";
    String e = "";
    String f = "";
    private WebviewLoader h = null;

    public void init() {
        try {
            if (mNetworkQueue == null) {
                mNetworkQueue = IMAIClickEventList.getLoggedClickEvents();
            }
            if (g == null) {
                g = new AtomicBoolean(false);
            }
            i = new AtomicBoolean(true);
            isSynced = new AtomicBoolean(false);
            c = new AtomicBoolean(false);
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Request Response Manager init failed", e);
        }
    }

    public void deinit() {
        try {
            if (g != null) {
                g.set(false);
            }
            if (mDBWriterQueue != null) {
                mDBWriterQueue.saveClickEvents();
                mDBWriterQueue.clear();
            }
            isSynced.set(false);
            if (mNetworkQueue != null) {
                mNetworkQueue.clear();
            }
            mNetworkQueue = null;
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Request Response Manager deinit failed", e);
        }
    }

    public void processClick(final Context context, final HttpRequestCallback httpRequestCallback) {
        try {
            if (g.compareAndSet(false, true)) {
                a = new Thread(new Runnable(this) {
                    final /* synthetic */ RequestResponseManager c;

                    public void run() {
                        loop0:
                        while (!RequestResponseManager.isSynced.get()) {
                            try {
                                ClickData clickData;
                                int retryCount;
                                RequestResponseManager.isSynced.set(true);
                                if (RequestResponseManager.mNetworkQueue == null || RequestResponseManager.mNetworkQueue.isEmpty()) {
                                    if (RequestResponseManager.mDBWriterQueue == null && RequestResponseManager.mDBWriterQueue.isEmpty()) {
                                        Log.internal(Constants.LOG_TAG, "Click event list empty");
                                        this.c.deinit();
                                        return;
                                    }
                                    RequestResponseManager.mNetworkQueue.addAll(RequestResponseManager.mDBWriterQueue);
                                    RequestResponseManager.mDBWriterQueue.clear();
                                }
                                while (true) {
                                    if (!RequestResponseManager.mNetworkQueue.isEmpty()) {
                                        clickData = (ClickData) RequestResponseManager.mNetworkQueue.remove(0);
                                        String clickUrl = clickData.getClickUrl();
                                        retryCount = clickData.getRetryCount();
                                        boolean isPingWv = clickData.isPingWv();
                                        boolean isFollowRedirects = clickData.isFollowRedirects();
                                        int retryInterval = Initializer.getConfigParams().getImai().getRetryInterval();
                                        if (!InternalSDKUtil.checkNetworkAvailibility(context)) {
                                            break loop0;
                                        }
                                        try {
                                            int maxRetry = Initializer.getConfigParams().getImai().getMaxRetry();
                                            if (!RequestResponseManager.i.get() && retryCount < maxRetry) {
                                                Log.internal(Constants.LOG_TAG, "Retrying to ping in background after " + (retryInterval / 1000) + " secs");
                                                synchronized (RequestResponseManager.a) {
                                                    try {
                                                        RequestResponseManager.a.wait((long) retryInterval);
                                                    } catch (Throwable e) {
                                                        Log.internal(Constants.LOG_TAG, "Network thread wait failure", e);
                                                    }
                                                }
                                            }
                                            Log.internal(Constants.LOG_TAG, "Processing click in background: " + clickUrl);
                                            if (isPingWv) {
                                                if (this.c.processClickUrlInWebview(clickUrl)) {
                                                    Log.internal(Constants.LOG_TAG, "Ping in webview successful: " + clickUrl);
                                                    if (httpRequestCallback != null) {
                                                        httpRequestCallback.notifyResult(0, null);
                                                    }
                                                } else {
                                                    clickData.setRetryCount(retryCount - 1);
                                                    if (retryCount > 1) {
                                                        RequestResponseManager.mDBWriterQueue.add(clickData);
                                                    }
                                                    Log.internal(Constants.LOG_TAG, "Ping in webview failed: " + clickUrl);
                                                    if (httpRequestCallback != null) {
                                                        httpRequestCallback.notifyResult(1, null);
                                                    }
                                                }
                                            } else if (this.c.processClickHttpClient(clickUrl, isFollowRedirects)) {
                                                Log.internal(Constants.LOG_TAG, "Ping successful: " + clickUrl);
                                                if (httpRequestCallback != null) {
                                                    httpRequestCallback.notifyResult(0, null);
                                                }
                                            } else {
                                                clickData.setRetryCount(retryCount - 1);
                                                if (retryCount > 1) {
                                                    RequestResponseManager.mDBWriterQueue.add(clickData);
                                                }
                                                Log.internal(Constants.LOG_TAG, "Ping  failed: " + clickUrl);
                                                if (httpRequestCallback != null) {
                                                    httpRequestCallback.notifyResult(1, null);
                                                }
                                                Log.internal(Constants.LOG_TAG, "Ping failed: " + clickUrl);
                                            }
                                            if (RequestResponseManager.mDBWriterQueue.size() > Initializer.getConfigParams().getImai().getmDefaultEventsBatch()) {
                                                RequestResponseManager.mDBWriterQueue.saveClickEvents();
                                                RequestResponseManager.mDBWriterQueue.clear();
                                            }
                                        } catch (Throwable e2) {
                                            Log.internal(Constants.LOG_TAG, "Exception pinging click in background", e2);
                                            this.c.deinit();
                                            return;
                                        }
                                    }
                                }
                                Log.internal(Constants.LOG_TAG, "Cannot process click. Network Not available");
                                if (retryCount > 1) {
                                    RequestResponseManager.mDBWriterQueue.add(clickData);
                                }
                                if (httpRequestCallback != null) {
                                    httpRequestCallback.notifyResult(1, null);
                                }
                                this.c.deinit();
                                return;
                            } catch (Throwable e22) {
                                Log.internal(Constants.LOG_TAG, "Exception ping to server ", e22);
                                return;
                            }
                        }
                        this.c.deinit();
                    }
                });
                a.setPriority(1);
                a.start();
            }
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Exception ping ", e);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean processClickHttpClient(java.lang.String r8, boolean r9) {
        /*
        r7 = this;
        r3 = 1;
        r2 = 0;
        r1 = 0;
        r0 = "[InMobi]-[Monetization]";
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r4.<init>();	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r5 = "Processing click in http client ";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r4 = r4.append(r8);	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r4 = r4.toString();	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        com.inmobi.commons.internal.Log.internal(r0, r4);	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r0 = com.inmobi.monetization.internal.configs.Initializer.getConfigParams();	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r0 = r0.getImai();	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r4 = r0.getPingTimeOut();	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r0.<init>(r8);	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x009c, all -> 0x008d }
        r0.setInstanceFollowRedirects(r9);	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r1 = "User-Agent";
        r5 = com.inmobi.commons.internal.InternalSDKUtil.getUserAgent();	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r0.setRequestProperty(r1, r5);	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r0.setConnectTimeout(r4);	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r0.setReadTimeout(r4);	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r1 = 0;
        r0.setUseCaches(r1);	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r1 = "user-agent";
        r4 = com.inmobi.commons.internal.InternalSDKUtil.getSavedUserAgent();	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r0.setRequestProperty(r1, r4);	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r1 = "GET";
        r0.setRequestMethod(r1);	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r1 = r0.getResponseCode();	 Catch:{ Exception -> 0x00a2, all -> 0x0094 }
        r4 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r1 >= r4) goto L_0x00a9;
    L_0x005e:
        r1 = r3;
    L_0x005f:
        if (r3 != r1) goto L_0x006e;
    L_0x0061:
        r2 = i;	 Catch:{ Exception -> 0x0075, all -> 0x0094 }
        r3 = 1;
        r2.set(r3);	 Catch:{ Exception -> 0x0075, all -> 0x0094 }
    L_0x0067:
        if (r0 == 0) goto L_0x00a7;
    L_0x0069:
        r0.disconnect();
        r0 = r1;
    L_0x006d:
        return r0;
    L_0x006e:
        r2 = i;	 Catch:{ Exception -> 0x0075, all -> 0x0094 }
        r3 = 0;
        r2.set(r3);	 Catch:{ Exception -> 0x0075, all -> 0x0094 }
        goto L_0x0067;
    L_0x0075:
        r2 = move-exception;
        r6 = r2;
        r2 = r0;
        r0 = r1;
        r1 = r6;
    L_0x007a:
        r3 = i;	 Catch:{ all -> 0x0099 }
        r4 = 0;
        r3.set(r4);	 Catch:{ all -> 0x0099 }
        r3 = "[InMobi]-[Monetization]";
        r4 = "Click in background exception";
        com.inmobi.commons.internal.Log.internal(r3, r4, r1);	 Catch:{ all -> 0x0099 }
        if (r2 == 0) goto L_0x006d;
    L_0x0089:
        r2.disconnect();
        goto L_0x006d;
    L_0x008d:
        r0 = move-exception;
    L_0x008e:
        if (r1 == 0) goto L_0x0093;
    L_0x0090:
        r1.disconnect();
    L_0x0093:
        throw r0;
    L_0x0094:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x008e;
    L_0x0099:
        r0 = move-exception;
        r1 = r2;
        goto L_0x008e;
    L_0x009c:
        r0 = move-exception;
        r6 = r0;
        r0 = r2;
        r2 = r1;
        r1 = r6;
        goto L_0x007a;
    L_0x00a2:
        r1 = move-exception;
        r6 = r0;
        r0 = r2;
        r2 = r6;
        goto L_0x007a;
    L_0x00a7:
        r0 = r1;
        goto L_0x006d;
    L_0x00a9:
        r1 = r2;
        goto L_0x005f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.inmobi.monetization.internal.imai.RequestResponseManager.processClickHttpClient(java.lang.String, boolean):boolean");
    }

    public boolean processClickUrlInWebview(String str) {
        try {
            Log.internal(Constants.LOG_TAG, "Processing click in webview " + str);
            this.h = new WebviewLoader(InternalSDKUtil.getContext());
            int pingTimeOut = Initializer.getConfigParams().getImai().getPingTimeOut();
            this.h.loadInWebview(str, null);
            synchronized (a) {
                try {
                    a.wait((long) pingTimeOut);
                } catch (Throwable e) {
                    Log.internal(Constants.LOG_TAG, "Network thread wait failure", e);
                }
            }
            if (true == c.get()) {
                i.set(true);
            } else {
                i.set(false);
                WebviewLoader.b.set(false);
            }
            this.h.deinit(pingTimeOut);
            return c.get();
        } catch (Throwable e2) {
            Log.internal(Constants.LOG_TAG, "ping in webview exception", e2);
            return c.get();
        }
    }
}
