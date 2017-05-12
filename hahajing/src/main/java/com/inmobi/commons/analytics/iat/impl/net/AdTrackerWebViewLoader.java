package com.inmobi.commons.analytics.iat.impl.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.analytics.iat.impl.Goal;
import com.inmobi.commons.internal.FileOperations;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled"})
public class AdTrackerWebViewLoader {
    private WebView a;
    private Goal b;
    private String c;
    private long d = 0;
    private long e = 0;

    protected final class AdTrackerWebViewClient extends WebViewClient {
        final /* synthetic */ AdTrackerWebViewLoader a;

        protected AdTrackerWebViewClient(AdTrackerWebViewLoader adTrackerWebViewLoader) {
            this.a = adTrackerWebViewLoader;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Handler c = AdTrackerNetworkInterface.c();
            if (c.hasMessages(9)) {
                c.removeMessages(9);
                this.a.e = System.currentTimeMillis() - this.a.d;
                if (str.contains("iat")) {
                    a a = this.a.a(str.substring(7));
                    FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.ERRORCODE, Integer.toString(a.a));
                    Message obtain = Message.obtain();
                    obtain.what = 10;
                    obtain.arg2 = a.a;
                    obtain.obj = this.a.b;
                    Bundle bundle = new Bundle();
                    bundle.putString("appId", this.a.c);
                    obtain.setData(bundle);
                    if (5000 == a.a) {
                        int b = this.a.b(a.b);
                        if (6000 == b) {
                            obtain.what = 8;
                            obtain.arg2 = (int) this.a.e;
                        } else {
                            obtain.arg2 = b;
                        }
                    }
                    c.sendMessage(obtain);
                }
            }
            webView.loadUrl(str);
            return true;
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            Handler c = AdTrackerNetworkInterface.c();
            if (c != null && c.hasMessages(9)) {
                c.removeMessages(9);
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Webview Received Error");
                Message obtain = Message.obtain();
                obtain.what = 10;
                obtain.arg2 = i;
                obtain.obj = this.a.b;
                c.sendMessage(obtain);
            }
        }

        @TargetApi(8)
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            Handler c = AdTrackerNetworkInterface.c();
            if (c.hasMessages(9)) {
                c.removeMessages(9);
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Webview Received SSL Error");
                Message obtain = Message.obtain();
                obtain.what = 10;
                obtain.arg2 = sslError.getPrimaryError();
                obtain.obj = this.a.b;
                c.sendMessage(obtain);
            }
        }
    }

    protected static class JSInterface {
        protected JSInterface() {
        }

        @JavascriptInterface
        public String getParams() {
            String preferences = FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER);
            String webViewRequestParam = AdTrackerRequestResponseBuilder.getWebViewRequestParam();
            if (preferences != null) {
                webViewRequestParam = webViewRequestParam + "&referrer=" + preferences;
            }
            Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Request param for webview" + webViewRequestParam);
            return webViewRequestParam;
        }
    }

    private static class a {
        private int a = 0;
        private String b = null;
    }

    public AdTrackerWebViewLoader() {
        AdTrackerNetworkInterface.getUIHandler().post(new Runnable(this) {
            final /* synthetic */ AdTrackerWebViewLoader a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a = new WebView(InternalSDKUtil.getContext());
                this.a.a.setWebViewClient(new AdTrackerWebViewClient(this.a));
                this.a.a.getSettings().setJavaScriptEnabled(true);
                this.a.a.getSettings().setCacheMode(2);
                this.a.a.addJavascriptInterface(new JSInterface(), AdTrackerConstants.IATNAMESPACE);
            }
        });
    }

    public boolean loadWebview(String str, Goal goal) {
        this.b = goal;
        this.c = str;
        AdTrackerNetworkInterface.getUIHandler().post(new Runnable(this) {
            final /* synthetic */ AdTrackerWebViewLoader a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.d = System.currentTimeMillis();
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Load Webview: " + AdTrackerNetworkInterface.b());
                this.a.a.loadUrl(AdTrackerNetworkInterface.b());
            }
        });
        return true;
    }

    public void deinit(int i) {
        AdTrackerNetworkInterface.getUIHandler().postDelayed(new Runnable(this) {
            final /* synthetic */ AdTrackerWebViewLoader a;

            {
                this.a = r1;
            }

            public void run() {
                if (this.a.a != null) {
                    this.a.a.stopLoading();
                    this.a.a.destroy();
                }
            }
        }, (long) i);
    }

    private a a(String str) {
        String str2 = null;
        a aVar = new a();
        try {
            String[] split = str.split("&");
            int i = 0;
            for (String split2 : split) {
                String[] split3 = split2.split("=");
                for (int i2 = 0; i2 < split3.length; i2++) {
                    if ("err".equals(split3[i2])) {
                        i = Integer.parseInt(split3[i2 + 1]);
                    } else if (AdTrackerConstants.RESPONSE.equals(split3[i2])) {
                        str2 = split3[i2 + 1];
                    }
                }
            }
            aVar.a = i;
            aVar.b = str2;
            if (5003 == i) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Webview Timeout " + str2);
            } else if (5001 == i) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Invalid params passed " + str2);
            } else if (5002 == i) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "XMLHTTP request not supported " + str2);
            } else if (5005 == i) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Invalid JSON Response " + str2);
            } else if (5004 == i) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Webview Server Error " + str2);
            } else if (5000 == i) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Webview response " + URLDecoder.decode(str2, "utf-8"));
            }
            return aVar;
        } catch (Throwable e) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Check content Exception", e);
            return aVar;
        }
    }

    private int b(String str) {
        int i;
        try {
            JSONObject jSONObject = new JSONObject(URLDecoder.decode(str, "utf-8"));
            JSONObject jSONObject2 = jSONObject.getJSONObject(AdTrackerConstants.VALIDIDS);
            String string = jSONObject.getString(AdTrackerConstants.ERRORMSG);
            int i2 = jSONObject.getInt(AdTrackerConstants.TIMETOLIVE);
            i = jSONObject.getInt(AdTrackerConstants.ERRORCODE);
            if (i != 6000) {
                try {
                    Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Failed to upload goal in webview" + string);
                } catch (UnsupportedEncodingException e) {
                    e = e;
                    e.printStackTrace();
                    return i;
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    return i;
                }
            }
            String jSONObject3 = jSONObject2.toString();
            if (i == 6001) {
                jSONObject3 = null;
            }
            FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.VALIDIDS, jSONObject3);
            FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.TIMETOLIVE, Integer.toString(i2));
        } catch (UnsupportedEncodingException e3) {
            UnsupportedEncodingException e4;
            UnsupportedEncodingException unsupportedEncodingException = e3;
            i = 6000;
            e4 = unsupportedEncodingException;
            e4.printStackTrace();
            return i;
        } catch (JSONException e5) {
            JSONException e6;
            JSONException jSONException = e5;
            i = 6000;
            e6 = jSONException;
            e6.printStackTrace();
            return i;
        }
        return i;
    }
}
