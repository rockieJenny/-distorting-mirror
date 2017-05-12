package com.flurry.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"SetJavaScriptEnabled"})
public final class eh extends ec {
    private final String a = getClass().getSimpleName();
    private WebView b;
    private WebViewClient c;
    private WebChromeClient d;
    private boolean e;
    private dq f;
    private ImageButton g;
    private ImageButton h;
    private ImageButton i;
    private ProgressBar j;
    private LinearLayout k;
    private boolean l;
    private com.flurry.sdk.ec.a m = new com.flurry.sdk.ec.a(this) {
        final /* synthetic */ eh a;

        {
            this.a = r1;
        }

        public void a() {
            if (this.a.f != null) {
                this.a.d();
                this.a.removeView(this.a.f);
                this.a.f = null;
            }
        }

        public void b() {
            if (this.a.f != null) {
                this.a.d();
                this.a.removeView(this.a.f);
                this.a.f = null;
            }
        }

        public void c() {
            if (this.a.f != null) {
                this.a.d();
                this.a.removeView(this.a.f);
                this.a.f = null;
            }
        }
    };

    final class a extends WebChromeClient {
        final /* synthetic */ eh a;

        private a(eh ehVar) {
            this.a = ehVar;
        }

        public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
            gd.a(3, this.a.a, "onShowCustomView(7)");
            this.a.e = true;
            this.a.j.setVisibility(0);
            this.a.e();
        }

        public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
            gd.a(3, this.a.a, "onShowCustomView(14)");
            this.a.e = true;
            this.a.j.setVisibility(0);
            this.a.e();
        }

        public void onHideCustomView() {
            gd.a(3, this.a.a, "onHideCustomView()");
            this.a.e = false;
            this.a.j.setVisibility(8);
            this.a.e();
        }

        public void onProgressChanged(WebView webView, int i) {
            this.a.j.setProgress(i);
            super.onProgressChanged(webView, i);
            if (i == 100) {
                this.a.j.setVisibility(8);
            }
        }
    }

    final class b extends WebViewClient {
        final /* synthetic */ eh a;

        private b(eh ehVar) {
            this.a = ehVar;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            gd.a(3, this.a.a, "shouldOverrideUrlLoading: url = " + str);
            if (str == null || webView == null || webView != this.a.b) {
                return false;
            }
            boolean a = this.a.a(str, this.a.l);
            this.a.l = false;
            return a;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            gd.a(3, this.a.a, "onPageStarted: url = " + str);
            if (str != null && webView != null && webView == this.a.b) {
                this.a.dismissProgressDialog();
                if (VERSION.SDK_INT < 11 && this.a.l && this.a.a(str, this.a.l)) {
                    gd.a(3, this.a.a, "onPageStarted: stopLoading is called");
                    webView.stopLoading();
                }
                this.a.j.setVisibility(0);
                this.a.l = true;
                this.a.e();
            }
        }

        public void onPageFinished(WebView webView, String str) {
            gd.a(3, this.a.a, "onPageFinished: url = " + str);
            if (str != null && webView != null && webView == this.a.b) {
                this.a.j.setVisibility(8);
                this.a.l = false;
                this.a.e();
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            gd.a(3, this.a.a, "onReceivedError: error = " + i + " description= " + str + " failingUrl= " + str2);
            super.onReceivedError(webView, i, str, str2);
            webView.clearSslPreferences();
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            gd.a(3, this.a.a, "onReceivedSslError: error = " + sslError.toString());
            webView.clearSslPreferences();
        }
    }

    public enum c {
        WEB_RESULT_UNKNOWN,
        WEB_RESULT_BACK,
        WEB_RESULT_CLOSE
    }

    public void initLayout() {
        super.initLayout();
        setOrientation(4);
    }

    public boolean onBackKey() {
        if (a()) {
            b();
        } else {
            a(c.WEB_RESULT_BACK);
        }
        d();
        return true;
    }

    protected void onViewLoadTimeout() {
        co.a(aw.EV_AD_WILL_CLOSE, Collections.emptyMap(), getContext(), getAdObject(), getAdController(), 0);
    }

    @TargetApi(11)
    public void onActivityResume() {
        super.onActivityResume();
        if (this.b != null && VERSION.SDK_INT >= 11) {
            this.b.onResume();
        }
    }

    @TargetApi(11)
    public void onActivityPause() {
        super.onActivityPause();
        if (this.b != null && VERSION.SDK_INT >= 11) {
            this.b.onPause();
        }
    }

    @TargetApi(11)
    public void onActivityDestroy() {
        super.onActivityDestroy();
        c();
    }

    @TargetApi(11)
    public eh(Context context, String str, r rVar, com.flurry.sdk.ec.a aVar, boolean z) {
        super(context, rVar, aVar);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.k = new LinearLayout(context);
        this.k.setOrientation(1);
        this.k.setLayoutParams(new LayoutParams(-1, -1));
        this.b = new WebView(context);
        this.c = new b();
        this.d = new a();
        this.b.getSettings().setJavaScriptEnabled(true);
        this.b.getSettings().setUseWideViewPort(true);
        this.b.getSettings().setLoadWithOverviewMode(true);
        this.b.getSettings().setBuiltInZoomControls(true);
        if (VERSION.SDK_INT >= 11) {
            this.b.getSettings().setDisplayZoomControls(false);
        }
        this.b.setWebViewClient(this.c);
        this.b.setWebChromeClient(this.d);
        this.b.setPadding(5, 5, 5, 5);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, -1);
        CharSequence d = i.a().h().d();
        if (!z || TextUtils.isEmpty(d)) {
            this.b.loadUrl(str);
        } else {
            Map hashMap = new HashMap();
            hashMap.put("Cookie", d);
            this.b.loadUrl(str, hashMap);
        }
        this.j = new ProgressBar(context, null, 16842872);
        this.j.setMax(100);
        this.j.setProgress(0);
        this.j.setLayoutParams(new RelativeLayout.LayoutParams(-1, hn.b(3)));
        this.g = new ImageButton(context);
        this.g.setPadding(5, 0, 5, 0);
        this.g.setImageBitmap(el.a());
        this.g.setBackgroundColor(getResources().getColor(17170445));
        this.g.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ eh a;

            {
                this.a = r1;
            }

            public void onClick(View view) {
                this.a.a(c.WEB_RESULT_CLOSE);
            }
        });
        this.h = new ImageButton(context);
        this.h.setId(1);
        this.h.setPadding(5, 0, 5, 0);
        this.h.setImageBitmap(el.b());
        this.h.setBackgroundColor(getResources().getColor(17170445));
        this.h.setVisibility(0);
        this.h.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ eh a;

            {
                this.a = r1;
            }

            public void onClick(View view) {
                if (this.a.b == null || !this.a.b.canGoBack()) {
                    this.a.a(c.WEB_RESULT_BACK);
                } else {
                    this.a.b.goBack();
                }
            }
        });
        this.i = new ImageButton(context);
        this.i.setPadding(5, 0, 5, 0);
        this.i.setImageBitmap(el.c());
        this.i.setBackgroundColor(getResources().getColor(17170445));
        this.i.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ eh a;

            {
                this.a = r1;
            }

            public void onClick(View view) {
                if (this.a.b != null && this.a.b.canGoForward()) {
                    this.a.b.goForward();
                }
            }
        });
        View relativeLayout = new RelativeLayout(context);
        relativeLayout.setBackgroundColor(getResources().getColor(17170432));
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        relativeLayout.setPadding(10, 10, 10, 10);
        ViewGroup.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(hn.b(35), hn.b(35));
        layoutParams2.addRule(11);
        layoutParams2.addRule(13);
        layoutParams2.setMargins(10, 10, 10, 10);
        relativeLayout.addView(this.g, layoutParams2);
        ViewGroup.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(hn.b(35), hn.b(35));
        layoutParams3.addRule(9);
        layoutParams2.addRule(13);
        layoutParams3.addRule(0, this.i.getId());
        layoutParams3.setMargins(10, 10, 10, 10);
        relativeLayout.addView(this.h, layoutParams3);
        layoutParams3 = new RelativeLayout.LayoutParams(hn.b(35), hn.b(35));
        layoutParams3.addRule(1, this.h.getId());
        layoutParams2.addRule(13);
        layoutParams3.setMargins(10, 10, 10, 10);
        relativeLayout.addView(this.i, layoutParams3);
        showProgressDialog();
        relativeLayout.setGravity(17);
        e();
        this.k.addView(relativeLayout);
        this.k.addView(this.j);
        this.k.addView(this.b, layoutParams);
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        addView(this.k);
    }

    public String getUrl() {
        if (this.b != null) {
            return this.b.getUrl();
        }
        return null;
    }

    private void e() {
        if (this.b.canGoForward()) {
            this.i.setVisibility(0);
        } else {
            this.i.setVisibility(4);
        }
    }

    public boolean a() {
        return this.e || (this.b != null && this.b.canGoBack());
    }

    public void b() {
        if (this.e) {
            this.d.onHideCustomView();
        } else if (this.b != null) {
            this.b.goBack();
        }
    }

    @TargetApi(11)
    public void c() {
        if (this.b != null) {
            dismissProgressDialog();
            removeView(this.b);
            this.b.stopLoading();
            if (VERSION.SDK_INT >= 11) {
                this.b.onPause();
            }
            this.b.destroy();
            this.b = null;
        }
    }

    public boolean a(String str, boolean z) {
        if (cv.g(str)) {
            if (cv.g(str)) {
                dr drVar;
                if (getAdController().q()) {
                    drVar = new dr();
                    this.f = dr.a(getContext(), ds.VIDEO_AD_TYPE_MRAID, getAdObject(), this.m);
                } else {
                    drVar = new dr();
                    this.f = dr.a(getContext(), ds.VIDEO_AD_TYPE_CLIPS, getAdObject(), this.m);
                }
                this.f.initLayout();
                addView(this.f);
            }
            return true;
        } else if (cv.d(str)) {
            if (!z) {
                z = a(str, getUrl());
            }
            cu.a(getContext(), str);
            if (z) {
                g();
            }
            co.a(aw.INTERNAL_EV_APP_EXIT, Collections.emptyMap(), getContext(), getAdObject(), getAdController(), 0);
            return true;
        } else {
            boolean b;
            if (cv.f(str)) {
                b = cu.b(getContext(), str);
                if (b) {
                    if (!z) {
                        z = a(str, getUrl());
                    }
                    if (z) {
                        g();
                    }
                    co.a(aw.INTERNAL_EV_APP_EXIT, Collections.emptyMap(), getContext(), getAdObject(), getAdController(), 0);
                    return b;
                }
            }
            b = cu.c(getContext(), str);
            if (b) {
                if (!z) {
                    z = a(str, getUrl());
                }
                if (z) {
                    g();
                }
                co.a(aw.INTERNAL_EV_APP_EXIT, Collections.emptyMap(), getContext(), getAdObject(), getAdController(), 0);
            }
            return b;
        }
    }

    public void a(c cVar) {
        if (cVar.equals(c.WEB_RESULT_CLOSE) || cVar.equals(c.WEB_RESULT_UNKNOWN)) {
            g();
        } else {
            h();
        }
    }

    private boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        Object queryParameter = Uri.parse(str2).getQueryParameter("link");
        if (TextUtils.isEmpty(queryParameter) || !queryParameter.equalsIgnoreCase(str)) {
            return false;
        }
        return true;
    }

    private void g() {
        onViewClose();
    }

    private void h() {
        onViewBack();
    }

    public void d() {
        setVisibility(0);
        if (this.f != null) {
            this.f.d();
        }
    }
}
