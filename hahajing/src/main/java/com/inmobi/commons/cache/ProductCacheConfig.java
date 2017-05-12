package com.inmobi.commons.cache;

import com.inmobi.commons.cache.CacheController.Committer;
import com.inmobi.commons.cache.CacheController.Validator;
import com.inmobi.commons.cache.RetryMechanism.RetryRunnable;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.network.RequestBuilderUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductCacheConfig extends ProductConfig {
    public static final int DEFAULT_EXPIRY = 432000;
    public static final int DEFAULT_INTERVAL = 60;
    public static final int DEFAULT_MAX_RETRY = 3;
    private static Timer a = new Timer();
    private AtomicBoolean b;
    private Map<String, String> c;
    private Validator d;
    private RetryMechanism e;
    private Committer f;
    private long g;

    private class a {
        final /* synthetic */ ProductCacheConfig a;
        private String b;
        private long c;

        private a(ProductCacheConfig productCacheConfig) {
            this.a = productCacheConfig;
        }
    }

    public Map<String, String> getMap() {
        return this.c;
    }

    public void setMap(Map<String, String> map) {
        this.c = map;
    }

    public Validator getValidator() {
        return this.d;
    }

    public void setValidator(Validator validator) {
        this.d = validator;
    }

    public ProductCacheConfig(JSONObject jSONObject, Committer committer) {
        this(jSONObject);
        this.f = committer;
    }

    public ProductCacheConfig(JSONObject jSONObject) {
        this.b = new AtomicBoolean(false);
        this.c = new HashMap();
        this.d = null;
        this.e = new RetryMechanism(3, 60000, a);
        try {
            loadFromJSON(jSONObject);
        } catch (Exception e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "JSON retrieved is invalid.");
        }
    }

    public ProductConfig getConfig() {
        return this;
    }

    public final void loadFromJSON(JSONObject jSONObject) {
        setExpiry(InternalSDKUtil.getIntFromJSON(jSONObject, "expiry", DEFAULT_EXPIRY));
        setMaxRetry(InternalSDKUtil.getIntFromJSON(jSONObject, "maxRetry", 3));
        setRetryInterval(InternalSDKUtil.getIntFromJSON(jSONObject, "retryInterval", 60));
        this.g = InternalSDKUtil.getLongFromJSON(jSONObject, "lastModified", 0);
        setUrl(InternalSDKUtil.getStringFromJSON(jSONObject, "url", ""));
        setProtocol(InternalSDKUtil.getStringFromJSON(jSONObject, "protocol", "json"));
        this.e = new RetryMechanism(getMaxRetry(), getRetryInterval() * 1000, a);
        setTimestamp(InternalSDKUtil.getIntFromJSON(jSONObject, "timestamp", 0));
        setData(InternalSDKUtil.getStringFromJSON(jSONObject, "data", null));
    }

    public String getData(Validator validator) {
        if (validator != null) {
            this.d = validator;
        }
        setRetryNumber(0);
        if ((getTimestamp() + getExpiry()) - ((int) (System.currentTimeMillis() / 1000)) <= 0) {
            b();
        }
        return getRawData();
    }

    public JSONObject toJSON() {
        JSONObject jSONObject;
        Throwable e;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject = new JSONObject("{expiry:" + getExpiry() + "," + "maxRetry:" + getMaxRetry() + "," + "retryInterval:" + getRetryInterval() + "," + "protocol:" + getProtocol() + ",timestamp:" + getTimestamp() + "}");
            try {
                jSONObject.put("url", getUrl());
                jSONObject.put("data", getRawData());
                jSONObject.put("lastModified", this.g);
            } catch (JSONException e2) {
                e = e2;
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Ill formed JSON product(" + getUrl() + ") toString", e);
                return jSONObject;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            jSONObject = jSONObject2;
            e = th;
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Ill formed JSON product(" + getUrl() + ") toString", e);
            return jSONObject;
        }
        return jSONObject;
    }

    public String toString() {
        return toJSON().toString();
    }

    private void a() throws Exception {
        try {
            a c = c();
            String a = c.b;
            if (a != null) {
                if (this.d == null) {
                    setData(a);
                } else if (this.d.validate(a(a))) {
                    setData(a);
                } else {
                    throw new IOException("Invalid config.");
                }
                setTimestamp((int) (System.currentTimeMillis() / 1000));
                this.g = c.c;
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Product with url " + getUrl() + " accepted data " + getRawData());
                this.f.onCommit();
                return;
            }
            setTimestamp((int) (System.currentTimeMillis() / 1000));
            this.g = c.c;
            this.f.onCommit();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Error connecting to url, or " + getUrl() + " did not return 200. Purge cache update.", e);
            throw e;
        }
    }

    private void b() {
        if (this.b.compareAndSet(false, true)) {
            this.e.rescheduleTimer(new RetryRunnable(this) {
                final /* synthetic */ ProductCacheConfig a;

                {
                    this.a = r1;
                }

                public void run() throws Exception {
                    try {
                        if (InternalSDKUtil.checkNetworkAvailibility(InternalSDKUtil.getContext())) {
                            this.a.a();
                            return;
                        }
                        throw new IOException("Network unavailable");
                    } catch (Exception e) {
                        throw e;
                    }
                }

                public void completed() {
                    this.a.b.set(false);
                }
            });
        }
    }

    protected void reset() {
        setExpiry(0);
        setRetryInterval(0);
        setMaxRetry(0);
        setTimestamp(0);
        setUrl(null);
        setProtocol(null);
        setData(null);
    }

    private a c() throws IOException {
        String str = "";
        Map hashMap = new HashMap();
        RequestBuilderUtils.fillIdentityMap(hashMap, null, true);
        RequestBuilderUtils.fillAppInfoMap(hashMap);
        String encodeMapAndconvertToDelimitedString = InternalSDKUtil.encodeMapAndconvertToDelimitedString(hashMap, "&");
        if (encodeMapAndconvertToDelimitedString == null || "".equals(encodeMapAndconvertToDelimitedString)) {
            encodeMapAndconvertToDelimitedString = getUrl();
        } else if (getUrl().endsWith("?")) {
            encodeMapAndconvertToDelimitedString = getUrl() + encodeMapAndconvertToDelimitedString;
        } else if (getUrl().contains("?")) {
            encodeMapAndconvertToDelimitedString = getUrl() + "&" + encodeMapAndconvertToDelimitedString;
        } else {
            encodeMapAndconvertToDelimitedString = getUrl() + "?" + encodeMapAndconvertToDelimitedString;
        }
        URL url = new URL(encodeMapAndconvertToDelimitedString);
        Log.internal(InternalSDKUtil.LOGGING_TAG, "Sending request to " + encodeMapAndconvertToDelimitedString + " to retreive cache..");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("User-Agent", InternalSDKUtil.getUserAgent());
        httpURLConnection.setIfModifiedSince(this.g);
        httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 304) {
            a aVar = new a();
            aVar.b = null;
            aVar.c = httpURLConnection.getLastModified();
            return aVar;
        } else if (responseCode != 200) {
            throw new IOException("Server did not return 200. ");
        } else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    str = str + readLine;
                } else {
                    bufferedReader.close();
                    a aVar2 = new a();
                    aVar2.b = str;
                    aVar2.c = httpURLConnection.getLastModified();
                    return aVar2;
                }
            }
        }
    }

    public String getData(Map<String, String> map, Validator validator) {
        if (map != null) {
            this.c = map;
        }
        return getData(validator);
    }
}
