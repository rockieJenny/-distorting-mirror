package com.inmobi.monetization.internal.imai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.Constants;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class WebviewLoader {
    static boolean a = false;
    static AtomicBoolean b = null;
    private WebView c = null;

    protected static class MyWebViewClient extends WebViewClient {
        protected MyWebViewClient() {
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            try {
                Log.internal(Constants.LOG_TAG, "Processing click in webview error " + i + " Failing url: " + str2 + "Error description " + str);
                WebviewLoader.b.set(false);
                RequestResponseManager.c.set(false);
                super.onReceivedError(webView, i, str, str2);
                synchronized (RequestResponseManager.a) {
                    RequestResponseManager.a.notify();
                }
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Exception onReceived error callback webview", e);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            try {
                Log.internal(Constants.LOG_TAG, "On page Finished " + str);
                if (WebviewLoader.b.compareAndSet(true, false)) {
                    RequestResponseManager.c.set(true);
                    synchronized (RequestResponseManager.a) {
                        RequestResponseManager.a.notify();
                    }
                }
                super.onPageFinished(webView, str);
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Exception onPageFinished", e);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Log.internal(Constants.LOG_TAG, "Should override " + str);
            webView.loadUrl(str);
            return true;
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            Log.internal(Constants.LOG_TAG, "SSL Error.Webview will proceed " + sslError);
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public WebviewLoader(final Context context) {
        RequestResponseManager.b.post(new Runnable(this) {
            final /* synthetic */ WebviewLoader b;

            public void run() {
                try {
                    this.b.c = new WebView(context);
                    WebviewLoader.b = new AtomicBoolean(false);
                    this.b.c.setWebViewClient(new MyWebViewClient());
                    this.b.c.getSettings().setJavaScriptEnabled(true);
                    this.b.c.getSettings().setPluginState(PluginState.ON);
                    this.b.c.getSettings().setCacheMode(2);
                } catch (Throwable e) {
                    Log.internal(Constants.LOG_TAG, "Exception init webview", e);
                }
            }
        });
    }

    public void deinit(int i) {
        RequestResponseManager.b.postDelayed(new Runnable(this) {
            final /* synthetic */ WebviewLoader a;

            {
                this.a = r1;
            }

            public void run() {
                try {
                    if (this.a.c != null) {
                        this.a.c.stopLoading();
                        this.a.c.destroy();
                        this.a.c = null;
                        WebviewLoader.b.set(false);
                    }
                } catch (Throwable e) {
                    Log.internal(Constants.LOG_TAG, "Exception deinit webview ", e);
                }
            }
        }, (long) i);
    }

    public void stopLoading() {
        RequestResponseManager.b.post(new Runnable(this) {
            final /* synthetic */ WebviewLoader a;

            {
                this.a = r1;
            }

            public void run() {
                try {
                    if (this.a.c != null) {
                        WebviewLoader.b.set(false);
                    }
                    this.a.c.stopLoading();
                } catch (Throwable e) {
                    Log.internal(Constants.LOG_TAG, "Exception stop loading", e);
                }
            }
        });
    }

    public void loadInWebview(final String str, final HashMap<String, String> hashMap) {
        RequestResponseManager.b.post(new Runnable(this) {
            final /* synthetic */ WebviewLoader c;

            public void run() {
                try {
                    WebviewLoader.b.set(true);
                    this.c.c.loadUrl(str, hashMap);
                } catch (Throwable e) {
                    Log.internal(Constants.LOG_TAG, "Exception load in webview", e);
                }
            }
        });
    }
}
