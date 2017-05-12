package com.inmobi.monetization.internal;

import com.inmobi.commons.internal.EncryptionUtils;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.network.Request;
import com.inmobi.commons.network.Request.Format;
import com.inmobi.commons.network.Request.Method;
import com.inmobi.commons.network.RequestBuilderUtils;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.configs.PkInitilaizer;
import java.util.HashMap;
import java.util.Map;

/* compiled from: AdRequest */
class c extends Request {
    protected static String a = "http://i.w.inmobi.com/showad.asm";
    private static byte[] e;
    String b = "";
    String c = "";
    String d = "";
    private byte[] f;
    private byte[] g;

    public c() {
        super(a, Format.KEY_VAL, Method.POST);
        RequestBuilderUtils.fillIdentityMap(this.mReqParams, Initializer.getConfigParams().getDeviceIdMaskMap(), false);
        RequestBuilderUtils.fillAppInfoMap(this.mReqParams);
        RequestBuilderUtils.fillDemogMap(this.mReqParams);
        RequestBuilderUtils.fillDeviceMap(this.mReqParams);
        RequestBuilderUtils.fillLocationMap(this.mReqParams);
        setTimeout(Initializer.getConfigParams().getFetchTimeOut());
    }

    void a(Map<String, String> map) {
        if (this.mReqParams != null && map != null && !map.isEmpty()) {
            this.mReqParams.putAll(map);
        }
    }

    void b(Map<String, String> map) {
        if (this.mReqParams != null && map != null) {
            this.mReqParams.putAll(map);
        }
    }

    protected String getPostBody() {
        String postBody = super.getPostBody();
        Log.internal(Constants.LOG_TAG, "Raw Postbody: " + postBody);
        return a(postBody);
    }

    String a(String str) {
        Map hashMap = new HashMap();
        this.g = EncryptionUtils.generateKey(8);
        this.f = EncryptionUtils.generateKey(16);
        e = EncryptionUtils.keag();
        this.b = PkInitilaizer.getConfigParams().getExponent();
        this.c = PkInitilaizer.getConfigParams().getModulus();
        this.d = PkInitilaizer.getConfigParams().getVersion();
        if (this.b.equals("") || this.c.equals("") || this.d.equals("")) {
            Log.debug(Constants.LOG_TAG, "Exception retreiving Ad due to key problem");
            return null;
        }
        hashMap.put("sm", EncryptionUtils.SeMeGe(str, e, this.f, this.g, this.c, this.b));
        hashMap.put("sn", this.d);
        return InternalSDKUtil.encodeMapAndconvertToDelimitedString(hashMap, "&");
    }

    byte[] a() {
        return this.f;
    }

    byte[] b() {
        return e;
    }

    protected void setUrl(String str) {
        super.setUrl(str);
    }
}
