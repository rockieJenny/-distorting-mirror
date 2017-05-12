package com.appflood.mraid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.appflood.e.j;
import java.io.UnsupportedEncodingException;

public class MraidBrowserActivity extends Activity {
    private WebView a;
    private String b;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = getIntent().getStringExtra("url");
        try {
            this.b = new String(this.b.getBytes("utf-8"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.a = new WebView(this);
        WebSettings settings = this.a.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        setContentView(this.a);
        this.a.setWebViewClient(new WebViewClient(this) {
            private /* synthetic */ MraidBrowserActivity a;

            {
                this.a = r1;
            }

            public final void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
            }

            public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
            }

            public final void onReceivedError(WebView webView, int i, String str, String str2) {
                Toast.makeText((Activity) webView.getContext(), "MRAID error: " + str, 0).show();
            }

            public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (str == null) {
                    return false;
                }
                Uri parse = Uri.parse(str);
                String host = parse.getHost();
                " uri = " + parse + " host " + host;
                j.a();
                if ((str.startsWith("http:") || str.startsWith("https:")) && !"play.google.com".equals(host) && !"market.android.com".equals(host)) {
                    return false;
                }
                try {
                    this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                } catch (Throwable e) {
                    j.b(e, "Not support ");
                }
                this.a.finish();
                return true;
            }
        });
        this.a.setWebChromeClient(new WebChromeClient(this) {
            private /* synthetic */ MraidBrowserActivity a;

            {
                this.a = r1;
            }

            public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
                "jsAlert url " + str + " message " + str2 + " finishing " + this.a.isFinishing();
                j.a();
                return super.onJsAlert(webView, str, str2, jsResult);
            }

            public final void onProgressChanged(WebView webView, int i) {
                " onProgressChanged " + i;
                j.a();
                Activity activity = (Activity) webView.getContext();
                activity.setTitle("Loading...");
                activity.setProgress(i * 100);
                if (i == 100) {
                    activity.setTitle(webView.getUrl());
                }
            }
        });
        this.a.loadUrl(this.b);
    }

    public void onDestroy() {
        j.a();
        super.onDestroy();
        if (this.a != null) {
            this.a.removeAllViews();
            this.a.destroy();
            this.a = null;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        finish();
        return true;
    }
}
