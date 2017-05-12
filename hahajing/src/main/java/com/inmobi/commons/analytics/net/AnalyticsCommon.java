package com.inmobi.commons.analytics.net;

import android.util.Log;
import com.inmobi.commons.analytics.util.AnalyticsUtils;
import com.inmobi.commons.internal.InternalSDKUtil;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class AnalyticsCommon {

    public interface HttpRequestCallback {
        public static final int HTTP_FAILURE = 1;
        public static final int HTTP_SUCCESS = 0;

        void notifyResult(int i, Object obj);
    }

    public HttpURLConnection setupConnection(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        a(httpURLConnection);
        return httpURLConnection;
    }

    private static void a(HttpURLConnection httpURLConnection) throws ProtocolException {
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestMethod(HttpRequest.METHOD_POST);
        httpURLConnection.setRequestProperty(HttpRequest.HEADER_CONTENT_TYPE, HttpRequest.CONTENT_TYPE_FORM);
        httpURLConnection.setRequestProperty("User-Agent", InternalSDKUtil.getUserAgent());
    }

    public void postData(HttpURLConnection httpURLConnection, String str) throws IOException {
        Closeable bufferedWriter;
        Throwable th;
        httpURLConnection.setRequestProperty(HttpRequest.HEADER_CONTENT_LENGTH, Integer.toString(str.length()));
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
            try {
                bufferedWriter.write(str);
                closeResource(bufferedWriter);
            } catch (Throwable th2) {
                th = th2;
                closeResource(bufferedWriter);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter = null;
            closeResource(bufferedWriter);
            throw th;
        }
    }

    public void closeResource(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                Log.d(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Exception closing resource: " + closeable, e);
            }
        }
    }

    public static String getURLEncoded(String str) {
        String str2 = "";
        try {
            return URLEncoder.encode(str, HttpRequest.CHARSET_UTF8);
        } catch (Exception e) {
            return "";
        }
    }
}
