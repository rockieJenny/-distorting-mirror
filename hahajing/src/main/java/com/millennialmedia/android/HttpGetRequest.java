package com.millennialmedia.android;

import android.text.TextUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

class HttpGetRequest {
    private static final String TAG = "HttpGetRequest";
    private HttpClient client;
    private HttpGet getRequest = new HttpGet();

    HttpGetRequest() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);
        this.client = new DefaultHttpClient(params);
    }

    HttpGetRequest(int timeout) {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);
        HttpConnectionParams.setSoTimeout(params, timeout);
        this.client = new DefaultHttpClient(params);
    }

    HttpResponse get(String url) throws Exception {
        HttpResponse response = null;
        if (!TextUtils.isEmpty(url)) {
            try {
                this.getRequest.setURI(new URI(url));
                response = this.client.execute(this.getRequest);
            } catch (OutOfMemoryError e) {
                MMLog.e(TAG, "Out of memory!", e);
                return null;
            } catch (Exception ex) {
                if (ex == null) {
                    return null;
                }
                MMLog.e(TAG, "Error connecting:", ex);
                return null;
            }
        }
        return response;
    }

    void trackConversion(String goalId, boolean isFirstLaunch, long installTime, TreeMap<String, String> extraParams) throws Exception {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://cvt.mydas.mobi/handleConversion?firstlaunch=" + (isFirstLaunch ? 1 : 0));
            if (goalId != null) {
                urlBuilder.append("&goalId=" + goalId);
            }
            if (installTime > 0) {
                urlBuilder.append("&installtime=" + (installTime / 1000));
            }
            if (extraParams != null) {
                for (Entry<String, String> entry : extraParams.entrySet()) {
                    urlBuilder.append(String.format("&%s=%s", new Object[]{entry.getKey(), URLEncoder.encode((String) entry.getValue(), HttpRequest.CHARSET_UTF8)}));
                }
            }
            MMLog.d(TAG, String.format("Sending conversion tracker report: %s", new Object[]{urlBuilder.toString()}));
            this.getRequest.setURI(new URI(url));
            if (this.client.execute(this.getRequest).getStatusLine().getStatusCode() == 200) {
                MMLog.v(TAG, String.format("Successful conversion tracking event: %d", new Object[]{Integer.valueOf(this.client.execute(this.getRequest).getStatusLine().getStatusCode())}));
            } else {
                MMLog.e(TAG, String.format("Conversion tracking error: %d", new Object[]{Integer.valueOf(this.client.execute(this.getRequest).getStatusLine().getStatusCode())}));
            }
        } catch (IOException e) {
            MMLog.e(TAG, "Conversion tracking error: ", e);
        }
    }

    static String convertStreamToString(InputStream is) throws IOException {
        OutOfMemoryError e;
        Throwable th;
        BufferedReader bufferedReader = null;
        if (is == null) {
            throw new IOException("Stream is null.");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is), 4096);
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line + "\n");
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                        MMLog.e(TAG, "Error closing file", e2);
                    }
                }
                return sb.toString();
            } catch (OutOfMemoryError e3) {
                e = e3;
                bufferedReader = reader;
                try {
                    MMLog.e(TAG, "Out of Memeory: ", e);
                    throw new IOException("Out of memory.");
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e22) {
                            MMLog.e(TAG, "Error closing file", e22);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = reader;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th;
            }
        } catch (OutOfMemoryError e4) {
            e = e4;
            MMLog.e(TAG, "Out of Memeory: ", e);
            throw new IOException("Out of memory.");
        }
    }

    static void log(final String[] urls) {
        if (urls != null && urls.length > 0) {
            ThreadUtils.execute(new Runnable() {
                public void run() {
                    for (String url : urls) {
                        MMLog.v(HttpGetRequest.TAG, String.format("Logging event to: %s", new Object[]{url}));
                        try {
                            new HttpGetRequest().get(arr$[i$]);
                        } catch (Exception e) {
                            MMLog.e(HttpGetRequest.TAG, "Logging request failed.", e);
                        }
                    }
                }
            });
        }
    }
}
