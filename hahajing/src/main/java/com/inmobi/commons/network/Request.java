package com.inmobi.commons.network;

import com.inmobi.commons.internal.InternalSDKUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Request {
    protected static HashMap<String, Object> mHeaders;
    private Format a = Format.KEY_VAL;
    private final String b = "User-Agent";
    private String c = null;
    private Method d = Method.POST;
    private int e = 0;
    protected HashMap<String, Object> mReqParams = new HashMap();

    public enum Format {
        KEY_VAL,
        JSON
    }

    public enum Method {
        GET,
        POST,
        PUT
    }

    public Request(String str, Format format, Method method) {
        mHeaders = new HashMap();
        RequestBuilderUtils.fillIdentityMap(this.mReqParams, null, true);
        mHeaders.put("User-Agent", InternalSDKUtil.getUserAgent());
        this.a = format;
        this.c = str;
        this.d = method;
    }

    private String a() {
        Map encodedMap = InternalSDKUtil.getEncodedMap(this.mReqParams);
        switch (this.a) {
            case KEY_VAL:
                return InternalSDKUtil.encodeMapAndconvertToDelimitedString(this.mReqParams, "&");
            case JSON:
                return new JSONObject(encodedMap).toString();
            default:
                return null;
        }
    }

    protected String getPostBody() {
        if (this.d != Method.GET) {
            return a();
        }
        return null;
    }

    protected String getQueryParams() {
        if (this.d == Method.GET) {
            return a();
        }
        return null;
    }

    protected String getUrl() {
        return this.c;
    }

    protected void setUrl(String str) {
        int i;
        int i2 = 1;
        if (str != null) {
            i = 1;
        } else {
            i = 0;
        }
        if ("".equals(str.trim())) {
            i2 = 0;
        }
        if ((i & i2) != 0) {
            this.c = str;
        }
    }

    protected Method getRequestMethod() {
        return this.d;
    }

    public int getTimeout() {
        return this.e;
    }

    public void setTimeout(int i) {
        this.e = i;
    }

    public Map<String, String> getHeaders() {
        return InternalSDKUtil.getEncodedMap(mHeaders);
    }

    public void fillAppInfo() {
        RequestBuilderUtils.fillAppInfoMap(this.mReqParams);
    }

    public void fillDemogInfo() {
        RequestBuilderUtils.fillDemogMap(this.mReqParams);
    }

    public void fillDeviceInfo() {
        RequestBuilderUtils.fillDeviceMap(this.mReqParams);
    }

    public void fillLocationInfo() {
        RequestBuilderUtils.fillLocationMap(this.mReqParams);
    }

    public void fillCustomInfo(Map<String, Object> map) {
        if (map != null) {
            this.mReqParams.putAll(map);
        }
    }
}
