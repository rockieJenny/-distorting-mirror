package com.inmobi.commons.network;

import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.network.Request.Method;
import com.inmobi.commons.network.abstraction.INetworkListener;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class NetworkRequestTask implements Runnable {
    private Request a;
    private INetworkListener b;
    private HttpURLConnection c;

    NetworkRequestTask(Request request, INetworkListener iNetworkListener) {
        this.a = request;
        this.b = iNetworkListener;
    }

    public void run() {
        try {
            String queryParams = this.a.getQueryParams();
            String postBody = this.a.getPostBody();
            String url = this.a.getUrl();
            if (!(queryParams == null || "".equals(queryParams.trim()))) {
                url = url + "?" + queryParams;
            }
            Log.internal(InternalSDKUtil.LOGGING_TAG, "URL:" + url);
            this.c = a(url);
            if (this.a.getRequestMethod() == Method.GET || !(postBody == null || "".equals(postBody))) {
                if (this.a.getRequestMethod() != Method.GET) {
                    Log.debug(InternalSDKUtil.LOGGING_TAG, "Post body:" + postBody);
                    b(postBody);
                }
                a();
                return;
            }
            this.b.onRequestFailed(this.a, new Response(ErrorCode.INTERNAL_ERROR));
        } catch (Throwable e) {
            Response response = new Response(ErrorCode.NETWORK_ERROR);
            if (this.b != null) {
                this.b.onRequestFailed(this.a, response);
            }
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Failed to make network request", e);
        }
    }

    private HttpURLConnection a(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        a(httpURLConnection);
        return httpURLConnection;
    }

    private void a(HttpURLConnection httpURLConnection) throws ProtocolException {
        httpURLConnection.setConnectTimeout(this.a.getTimeout());
        httpURLConnection.setReadTimeout(this.a.getTimeout());
        for (String str : this.a.getHeaders().keySet()) {
            httpURLConnection.setRequestProperty(str, (String) this.a.getHeaders().get(str));
        }
        httpURLConnection.setUseCaches(false);
        Method requestMethod = this.a.getRequestMethod();
        if (requestMethod != Method.GET) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
        }
        httpURLConnection.setRequestMethod(requestMethod.toString());
        httpURLConnection.setRequestProperty("content-type", HttpRequest.CONTENT_TYPE_FORM);
    }

    private void b(String str) throws IOException {
        Closeable bufferedWriter;
        Throwable th;
        this.c.setRequestProperty(HttpRequest.HEADER_CONTENT_LENGTH, Integer.toString(str.length()));
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.c.getOutputStream()));
            try {
                bufferedWriter.write(str);
                a(bufferedWriter);
            } catch (Throwable th2) {
                th = th2;
                a(bufferedWriter);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter = null;
            a(bufferedWriter);
            throw th;
        }
    }

    private void a() {
        Throwable th;
        Closeable closeable = null;
        Response response;
        try {
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Http Status Code: " + this.c.getResponseCode());
            int responseCode = this.c.getResponseCode();
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Status Code: " + responseCode);
            if (responseCode == 200) {
                Closeable bufferedReader;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(this.c.getInputStream(), HttpRequest.CHARSET_UTF8));
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            stringBuilder.append(readLine).append("\n");
                        }
                        String stringBuilder2 = stringBuilder.toString();
                        Log.debug(InternalSDKUtil.LOGGING_TAG, "Response: " + stringBuilder2);
                        Response response2 = new Response(stringBuilder2, this.c.getResponseCode(), this.c.getHeaderFields());
                        if (this.b != null) {
                            this.b.onRequestSucceded(this.a, response2);
                        }
                        closeable = bufferedReader;
                    } catch (Throwable th2) {
                        th = th2;
                        this.c.disconnect();
                        a(bufferedReader);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                    this.c.disconnect();
                    a(bufferedReader);
                    throw th;
                }
            }
            response = new Response(null, this.c.getResponseCode(), this.c.getHeaderFields());
            if (this.b != null) {
                this.b.onRequestFailed(this.a, response);
            }
            this.c.disconnect();
            a(closeable);
        } catch (Throwable th4) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to retrieve response", th4);
            response = new Response(ErrorCode.CONNECTION_ERROR);
            if (this.b != null) {
                this.b.onRequestFailed(this.a, response);
            }
        } catch (Throwable th42) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Out of memory error received while retieving network response", th42);
            response = new Response(ErrorCode.INTERNAL_ERROR);
            if (this.b != null) {
                this.b.onRequestFailed(this.a, response);
            }
        }
    }

    private void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                Log.debug(InternalSDKUtil.LOGGING_TAG, "Exception closing resource: " + closeable, e);
            }
        }
    }
}
