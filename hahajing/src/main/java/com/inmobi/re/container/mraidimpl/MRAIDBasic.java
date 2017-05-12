package com.inmobi.re.container.mraidimpl;

import android.app.Activity;
import android.view.View;
import com.inmobi.commons.internal.Log;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.controller.util.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class MRAIDBasic {
    private IMWebView a;
    private Activity b;

    public MRAIDBasic(IMWebView iMWebView, Activity activity) {
        this.a = iMWebView;
        this.b = activity;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void open(final java.lang.String r5) {
        /*
        r4 = this;
        r0 = "http://";
        r0 = r5.startsWith(r0);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r0 != 0) goto L_0x0028;
    L_0x0008:
        r0 = "https";
        r0 = r5.contains(r0);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r0 == 0) goto L_0x00b8;
    L_0x0010:
        r0 = "play.google.com";
        r0 = r5.contains(r0);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r0 != 0) goto L_0x00b8;
    L_0x0018:
        r0 = "market.android.com";
        r0 = r5.contains(r0);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r0 != 0) goto L_0x00b8;
    L_0x0020:
        r0 = "market%3A%2F%2F";
        r0 = r5.contains(r0);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r0 != 0) goto L_0x00b8;
    L_0x0028:
        r0 = android.webkit.URLUtil.isValidUrl(r5);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r0 != 0) goto L_0x0038;
    L_0x002e:
        r0 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = "Invalid url";
        r2 = "open";
        r0.raiseError(r1, r2);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
    L_0x0037:
        return;
    L_0x0038:
        r0 = new android.content.Intent;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = r4.b;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r2 = com.inmobi.androidsdk.IMBrowserActivity.class;
        r0.<init>(r1, r2);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = "[InMobi]-[RE]-4.5.3";
        r2 = new java.lang.StringBuilder;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r2.<init>();	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r3 = "IMWebView-> open:";
        r2 = r2.append(r3);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r2 = r2.append(r5);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r2 = r2.toString();	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        com.inmobi.commons.internal.Log.debug(r1, r2);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = "extra_url";
        r0.putExtra(r1, r5);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = "extra_browser_type";
        r2 = 100;
        r0.putExtra(r1, r2);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = r1.getStateVariable();	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r2 = com.inmobi.re.container.IMWebView.ViewState.DEFAULT;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r1 != r2) goto L_0x0081;
    L_0x006f:
        r1 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = r1.mIsInterstitialAd;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r1 != 0) goto L_0x0081;
    L_0x0075:
        r1 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = r1.mWebViewIsBrowserActivity;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r1 != 0) goto L_0x0081;
    L_0x007b:
        r1 = "FIRST_INSTANCE";
        r2 = 1;
        r0.putExtra(r1, r2);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
    L_0x0081:
        r1 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = r1.mListener;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        com.inmobi.androidsdk.IMBrowserActivity.setWebViewListener(r1);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = r4.b;	 Catch:{ Exception -> 0x00a2, ActivityNotFoundException -> 0x0099 }
        r1.startActivity(r0);	 Catch:{ Exception -> 0x00a2, ActivityNotFoundException -> 0x0099 }
        r0 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r0 = r0.mWebViewIsBrowserActivity;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        if (r0 != 0) goto L_0x0037;
    L_0x0093:
        r0 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r0.fireOnShowAdScreen();	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        goto L_0x0037;
    L_0x0099:
        r0 = move-exception;
        r0 = "[InMobi]-[RE]-4.5.3";
        r1 = "Failed to perform mraid Open";
        com.inmobi.commons.internal.Log.debug(r0, r1);
        goto L_0x0037;
    L_0x00a2:
        r0 = move-exception;
        r0 = r4.a;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = "Invalid url";
        r2 = "open";
        r0.raiseError(r1, r2);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        goto L_0x0037;
    L_0x00ad:
        r0 = move-exception;
        r0 = r4.a;
        r1 = "Invalid url";
        r2 = "open";
        r0.raiseError(r1, r2);
        goto L_0x0037;
    L_0x00b8:
        r0 = new java.lang.Thread;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1 = new com.inmobi.re.container.mraidimpl.MRAIDBasic$1;	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r1.<init>(r4, r5);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r0.<init>(r1);	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        r0.start();	 Catch:{ ActivityNotFoundException -> 0x0099, Exception -> 0x00ad }
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.inmobi.re.container.mraidimpl.MRAIDBasic.open(java.lang.String):void");
    }

    public void getCurrentPosition() {
        int i;
        int i2;
        int i3;
        Throwable th;
        Throwable th2;
        int i4 = 0;
        Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> getCurrentPosition");
        JSONObject jSONObject = new JSONObject();
        int i5 = 0;
        try {
            if (this.a.isViewable()) {
                int[] iArr = new int[2];
                this.a.getLocationOnScreen(iArr);
                i = iArr[0];
                try {
                    i2 = iArr[1];
                    try {
                        i4 = (int) (((float) this.a.getWidth()) / this.a.getDensity());
                        i3 = i;
                        i = i2;
                        i2 = i4;
                        i4 = (int) (((float) this.a.getHeight()) / this.a.getDensity());
                    } catch (Exception e) {
                        try {
                            Log.debug(Constants.RENDERING_LOG_TAG, "Failed to get current position");
                            try {
                                jSONObject.put("x", i);
                                jSONObject.put("y", i2);
                                jSONObject.put("width", i4);
                                jSONObject.put("height", i5);
                            } catch (JSONException e2) {
                            }
                            synchronized (this.a.mutexcPos) {
                                this.a.curPosition = jSONObject;
                                this.a.acqMutexcPos.set(false);
                                this.a.mutexcPos.notifyAll();
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            i3 = i;
                            i = i2;
                            i2 = i4;
                            th2 = th;
                            try {
                                jSONObject.put("x", i3);
                                jSONObject.put("y", i);
                                jSONObject.put("width", i2);
                                jSONObject.put("height", i5);
                            } catch (JSONException e3) {
                            }
                            throw th2;
                        }
                    }
                } catch (Exception e4) {
                    i2 = 0;
                    Log.debug(Constants.RENDERING_LOG_TAG, "Failed to get current position");
                    jSONObject.put("x", i);
                    jSONObject.put("y", i2);
                    jSONObject.put("width", i4);
                    jSONObject.put("height", i5);
                    synchronized (this.a.mutexcPos) {
                        this.a.curPosition = jSONObject;
                        this.a.acqMutexcPos.set(false);
                        this.a.mutexcPos.notifyAll();
                    }
                } catch (Throwable th4) {
                    i3 = i;
                    i = 0;
                    th2 = th4;
                    i2 = 0;
                    jSONObject.put("x", i3);
                    jSONObject.put("y", i);
                    jSONObject.put("width", i2);
                    jSONObject.put("height", i5);
                    throw th2;
                }
            }
            i2 = 0;
            i = 0;
            i3 = 0;
            try {
                jSONObject.put("x", i3);
                jSONObject.put("y", i);
                jSONObject.put("width", i2);
                jSONObject.put("height", i4);
            } catch (JSONException e5) {
            }
        } catch (Exception e6) {
            i2 = 0;
            i = 0;
            Log.debug(Constants.RENDERING_LOG_TAG, "Failed to get current position");
            jSONObject.put("x", i);
            jSONObject.put("y", i2);
            jSONObject.put("width", i4);
            jSONObject.put("height", i5);
            synchronized (this.a.mutexcPos) {
                this.a.curPosition = jSONObject;
                this.a.acqMutexcPos.set(false);
                this.a.mutexcPos.notifyAll();
            }
        } catch (Throwable th42) {
            i = 0;
            i3 = 0;
            th = th42;
            i2 = 0;
            th2 = th;
            jSONObject.put("x", i3);
            jSONObject.put("y", i);
            jSONObject.put("width", i2);
            jSONObject.put("height", i5);
            throw th2;
        }
        synchronized (this.a.mutexcPos) {
            this.a.curPosition = jSONObject;
            this.a.acqMutexcPos.set(false);
            this.a.mutexcPos.notifyAll();
        }
    }

    public void getDefaultPosition() {
        int i;
        int i2;
        Exception e;
        Throwable th;
        int i3 = 0;
        Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> getDefaultPosition");
        JSONObject jSONObject = new JSONObject();
        int i4 = 0;
        try {
            int i5;
            if (this.a.isViewable()) {
                int[] iArr = new int[2];
                ((View) this.a.getOriginalParent()).getLocationOnScreen(iArr);
                i = iArr[0];
                try {
                    i2 = iArr[1];
                } catch (Exception e2) {
                    e = e2;
                    i2 = 0;
                    try {
                        e.printStackTrace();
                        Log.debug(Constants.RENDERING_LOG_TAG, "Failed to get default position" + e.getMessage());
                        try {
                            jSONObject.put("x", i);
                            jSONObject.put("y", i2);
                            jSONObject.put("width", i3);
                            jSONObject.put("height", i4);
                        } catch (JSONException e3) {
                        }
                        synchronized (this.a.mutexdPos) {
                            this.a.defPosition = jSONObject;
                            this.a.acqMutexdPos.set(false);
                            this.a.mutexdPos.notifyAll();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            jSONObject.put("x", i);
                            jSONObject.put("y", i2);
                            jSONObject.put("width", i3);
                            jSONObject.put("height", i4);
                        } catch (JSONException e4) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    i2 = 0;
                    jSONObject.put("x", i);
                    jSONObject.put("y", i2);
                    jSONObject.put("width", i3);
                    jSONObject.put("height", i4);
                    throw th;
                }
                try {
                    i3 = (int) (((float) ((View) this.a.getOriginalParent()).getWidth()) / this.a.getDensity());
                    i5 = i3;
                    i3 = (int) (((float) ((View) this.a.getOriginalParent()).getHeight()) / this.a.getDensity());
                } catch (Exception e5) {
                    e = e5;
                    e.printStackTrace();
                    Log.debug(Constants.RENDERING_LOG_TAG, "Failed to get default position" + e.getMessage());
                    jSONObject.put("x", i);
                    jSONObject.put("y", i2);
                    jSONObject.put("width", i3);
                    jSONObject.put("height", i4);
                    synchronized (this.a.mutexdPos) {
                        this.a.defPosition = jSONObject;
                        this.a.acqMutexdPos.set(false);
                        this.a.mutexdPos.notifyAll();
                    }
                }
            }
            i5 = 0;
            i2 = 0;
            i = 0;
            try {
                jSONObject.put("x", i);
                jSONObject.put("y", i2);
                jSONObject.put("width", i5);
                jSONObject.put("height", i3);
            } catch (JSONException e6) {
            }
        } catch (Exception e7) {
            e = e7;
            i2 = 0;
            i = 0;
            e.printStackTrace();
            Log.debug(Constants.RENDERING_LOG_TAG, "Failed to get default position" + e.getMessage());
            jSONObject.put("x", i);
            jSONObject.put("y", i2);
            jSONObject.put("width", i3);
            jSONObject.put("height", i4);
            synchronized (this.a.mutexdPos) {
                this.a.defPosition = jSONObject;
                this.a.acqMutexdPos.set(false);
                this.a.mutexdPos.notifyAll();
            }
        } catch (Throwable th4) {
            th = th4;
            i2 = 0;
            i = 0;
            jSONObject.put("x", i);
            jSONObject.put("y", i2);
            jSONObject.put("width", i3);
            jSONObject.put("height", i4);
            throw th;
        }
        synchronized (this.a.mutexdPos) {
            this.a.defPosition = jSONObject;
            this.a.acqMutexdPos.set(false);
            this.a.mutexdPos.notifyAll();
        }
    }
}
