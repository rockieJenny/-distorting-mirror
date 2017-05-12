package com.inmobi.monetization.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.imai.IMAIController;
import com.inmobi.re.container.IMWebView;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

/* compiled from: TrackerView */
class e extends View {
    WebViewClient a = new WebViewClient(this) {
        final /* synthetic */ e a;

        {
            this.a = r1;
        }

        public void onPageFinished(WebView webView, String str) {
            Log.internal(Constants.LOG_TAG, "Native ad context code loaded");
            this.a.f = true;
            if (this.a.e != null && !this.a.e.isEmpty()) {
                for (int i = 0; i < this.a.e.size(); i++) {
                    this.a.b((String) this.a.e.get(i));
                }
                this.a.e.clear();
                this.a.e = null;
            }
        }
    };
    private IMWebView b;
    private boolean c = false;
    private String d;
    private ArrayList<String> e = null;
    private boolean f = false;

    public e(Context context, String str, String str2) {
        super(context);
        if (str != null && str2 != null) {
            this.d = str2;
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(10);
            setLayoutParams(layoutParams);
            setBackgroundColor(0);
            IMWebView.setIMAIController(IMAIController.class);
            this.b = new IMWebView(context, null, false, false);
            this.b.getSettings().setJavaScriptEnabled(true);
            this.b.getSettings().setCacheMode(2);
            this.b.setWebViewClient(this.a);
            this.b.loadData(str, "text/html", HttpRequest.CHARSET_UTF8);
            this.e = new ArrayList();
            setId(999);
        }
    }

    public void a() {
        if (this.b != null) {
            this.b.destroy();
            this.b = null;
        }
        if (this.e != null) {
            this.e.clear();
            this.e = null;
        }
        this.c = false;
        this.f = false;
    }

    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0 && !this.c) {
            this.c = true;
            if (this.f) {
                a(b());
            } else if (this.e != null) {
                this.e.add(b());
            }
        }
    }

    private String b() {
        return this.d + "recordEvent(18)";
    }

    private String b(HashMap<String, String> hashMap) {
        StringBuilder stringBuilder = new StringBuilder();
        if (hashMap == null || hashMap.isEmpty()) {
            stringBuilder.append(this.d + "recordEvent(8)");
            return stringBuilder.toString();
        }
        stringBuilder.append(this.d + "recordEvent(8, ");
        stringBuilder.append(new JSONObject(hashMap).toString());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void a(String str) {
        Log.debug(Constants.LOG_TAG, "Handle Impression");
        b(str);
    }

    public void a(HashMap<String, String> hashMap) {
        Log.debug(Constants.LOG_TAG, "Handle Click");
        String b = b((HashMap) hashMap);
        if (this.f) {
            b(b);
        } else if (this.e != null) {
            this.e.add(b);
        }
    }

    public void b(String str) {
        if (str != null) {
            try {
                if (str.length() < 400) {
                    Log.internal(Constants.LOG_TAG, str);
                }
                if (this.b != null) {
                    String str2 = "javascript:try{" + str + "}catch(e){}";
                    if (VERSION.SDK_INT < 19) {
                        c(str2);
                    } else {
                        d(str2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void c(String str) {
        this.b.loadUrl(str);
    }

    @TargetApi(19)
    private void d(String str) {
        this.b.evaluateJavascript(str, null);
    }
}
