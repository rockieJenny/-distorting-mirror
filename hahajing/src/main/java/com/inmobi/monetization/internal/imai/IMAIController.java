package com.inmobi.monetization.internal.imai;

import android.os.Build.VERSION;
import android.webkit.JavascriptInterface;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.internal.ApiStatCollector;
import com.inmobi.commons.internal.ApiStatCollector.ApiEventType;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.metric.EventLog;
import com.inmobi.monetization.internal.Constants;
import com.inmobi.re.container.IMWebView;
import java.lang.ref.WeakReference;

public class IMAIController {
    public static final String IMAI_BRIDGE = "imaiController";
    private transient WeakReference<IMWebView> a;
    private InterstitialAdStateListener b;

    public interface InterstitialAdStateListener {
        void onAdFailed();

        void onAdReady();
    }

    public IMAIController(IMWebView iMWebView) {
        IMAICore.initialize();
        this.a = new WeakReference(iMWebView);
    }

    @JavascriptInterface
    public String getPlatformVersion() {
        Log.debug(Constants.LOG_TAG, "get platform version");
        return Integer.toString(VERSION.SDK_INT);
    }

    @JavascriptInterface
    public String getSdkVersion() {
        Log.debug(Constants.LOG_TAG, "get sdk version");
        return InMobi.getVersion();
    }

    @JavascriptInterface
    public void log(String str) {
        Log.debug(Constants.LOG_TAG, str);
    }

    @JavascriptInterface
    public void openEmbedded(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(1001), null));
        try {
            Log.debug(Constants.LOG_TAG, "IMAI open Embedded");
            if (!IMAICore.validateURL(str)) {
                IMAICore.fireErrorEvent(this.a, "Null url passed", "openEmbedded", str);
            } else if (str.startsWith("http") || str.startsWith("https")) {
                IMAICore.launchEmbeddedBrowser(this.a, str);
                IMAICore.fireOpenEmbeddedSuccessful(this.a, str);
            } else {
                openExternal(str);
            }
        } catch (Throwable e) {
            IMAICore.fireErrorEvent(this.a, e.getMessage(), "openEmbedded", str);
            Log.internal(Constants.LOG_TAG, "IMAI openEmbedded failed", e);
        }
    }

    @JavascriptInterface
    public void openExternal(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(1002), null));
        try {
            Log.debug(Constants.LOG_TAG, "IMAI open external");
            if (IMAICore.validateURL(str)) {
                IMAICore.launchExternalApp(str);
                IMAICore.fireOpenExternalSuccessful(this.a, str);
                return;
            }
            IMAICore.fireErrorEvent(this.a, "Null url passed", "openExternal", str);
        } catch (Throwable e) {
            IMAICore.fireErrorEvent(this.a, e.getMessage(), "openExternal", str);
            Log.internal(Constants.LOG_TAG, "IMAI openExternal failed", e);
        }
    }

    @JavascriptInterface
    public void ping(String str, boolean z) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(ApiEventType.API_IMAI_PING), null));
        try {
            Log.debug(Constants.LOG_TAG, "IMAI ping");
            if (!IMAICore.validateURL(str)) {
                IMAICore.fireErrorEvent(this.a, "Null url passed", "ping", str);
            } else if (str.contains("http") || str.contains("https")) {
                IMAICore.ping(this.a, str, z);
            } else {
                IMAICore.fireErrorEvent(this.a, "Invalid url passed", "ping", str);
            }
        } catch (Throwable e) {
            IMAICore.fireErrorEvent(this.a, e.getMessage(), "ping", str);
            Log.internal(Constants.LOG_TAG, "IMAI ping failed", e);
        }
    }

    @JavascriptInterface
    public void pingInWebView(String str, boolean z) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(ApiEventType.API_IMAI_PING_IN_WEB_VIEW), null));
        try {
            Log.debug(Constants.LOG_TAG, "IMAI ping in webview");
            if (!IMAICore.validateURL(str)) {
                IMAICore.fireErrorEvent(this.a, "Null url passed", "pingInWebView", str);
            } else if (str.contains("http") || str.contains("https")) {
                IMAICore.pingInWebview(this.a, str, z);
            } else {
                IMAICore.fireErrorEvent(this.a, "Invalid url passed", "pingInWebView", str);
            }
        } catch (Throwable e) {
            IMAICore.fireErrorEvent(this.a, e.getMessage(), "pingInWebView", str);
            Log.internal(Constants.LOG_TAG, "IMAI pingInWebView failed", e);
        }
    }

    @JavascriptInterface
    public void fireAdReady() {
        if (this.b != null) {
            this.b.onAdReady();
        }
    }

    @JavascriptInterface
    public void fireAdFailed() {
        if (this.b != null) {
            this.b.onAdFailed();
        }
    }

    public void setInterstitialAdStateListener(InterstitialAdStateListener interstitialAdStateListener) {
        this.b = interstitialAdStateListener;
    }
}
