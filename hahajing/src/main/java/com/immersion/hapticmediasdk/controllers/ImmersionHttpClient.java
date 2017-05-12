package com.immersion.hapticmediasdk.controllers;

import com.immersion.hapticmediasdk.utils.Log;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ImmersionHttpClient {
    private static final String a = "ImmersionHttpClient";
    public static int b044604460446ц04460446 = 0;
    public static int b0446ц0446ц04460446 = 1;
    public static int bц04460446ц04460446 = 2;
    public static int bццц044604460446 = 3;
    private DefaultHttpClient b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private ImmersionHttpClient() {
        /*
        r2 = this;
        r2.<init>();
        r0 = bцц0446ц04460446();
        r1 = b0446ц0446ц04460446;
        r0 = r0 + r1;
        r1 = bцц0446ц04460446();
        r0 = r0 * r1;
        r1 = bц04460446ц04460446;
        r0 = r0 % r1;
        r1 = b044604460446ц04460446;
        if (r0 == r1) goto L_0x001c;
    L_0x0016:
        r0 = bцц0446ц04460446();
        b044604460446ц04460446 = r0;
    L_0x001c:
        r0 = 0;
    L_0x001d:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x001d;
            case 1: goto L_0x0026;
            default: goto L_0x0021;
        };
    L_0x0021:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0026;
            case 1: goto L_0x001d;
            default: goto L_0x0025;
        };
    L_0x0025:
        goto L_0x0021;
    L_0x0026:
        r2.b = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.ImmersionHttpClient.<init>():void");
    }

    private HttpResponse a(HttpUriRequest httpUriRequest, Map map, int i) throws Exception {
        URI uri = httpUriRequest.getURI();
        String trim = uri.getHost() != null ? uri.getHost().trim() : "";
        if (trim.length() > 0) {
            httpUriRequest.setHeader("Host", trim);
            if (((bццц044604460446 + b0446ц0446ц04460446) * bццц044604460446) % bц04460446ц04460446 != b044604460446ц04460446) {
                bццц044604460446 = 43;
                b044604460446ц04460446 = 98;
            }
        }
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                httpUriRequest.setHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }
        Header[] allHeaders = httpUriRequest.getAllHeaders();
        Log.d(a, "request URI [" + httpUriRequest.getURI() + "]");
        for (Object obj : allHeaders) {
            Log.d(a, "request header [" + obj.toString() + "]");
        }
        HttpConnectionParams.setSoTimeout(this.b.getParams(), i);
        HttpResponse execute = this.b.execute(httpUriRequest);
        if (execute != null) {
            return execute;
        }
        throw new RuntimeException("Null response returned.");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
        r6 = this;
        r2 = 0;
        r0 = r6.b;
        if (r0 != 0) goto L_0x005f;
    L_0x0005:
        r0 = bццц044604460446;
        r1 = b0446ц0446ц04460446;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bц04460446ц04460446;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0019;
            default: goto L_0x0011;
        };
    L_0x0011:
        r0 = 66;
        bццц044604460446 = r0;
        r0 = 39;
        b044604460446ц04460446 = r0;
    L_0x0019:
        r0 = new org.apache.http.params.BasicHttpParams;
        r0.<init>();
    L_0x001e:
        switch(r2) {
            case 0: goto L_0x0025;
            case 1: goto L_0x001e;
            default: goto L_0x0021;
        };
    L_0x0021:
        switch(r2) {
            case 0: goto L_0x0025;
            case 1: goto L_0x001e;
            default: goto L_0x0024;
        };
    L_0x0024:
        goto L_0x0021;
    L_0x0025:
        r1 = 5;
        org.apache.http.conn.params.ConnManagerParams.setMaxTotalConnections(r0, r1);
        r1 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        org.apache.http.params.HttpConnectionParams.setConnectionTimeout(r0, r1);
        r1 = new org.apache.http.conn.scheme.SchemeRegistry;
        r1.<init>();
        r2 = new org.apache.http.conn.scheme.Scheme;
        r3 = "http";
        r4 = org.apache.http.conn.scheme.PlainSocketFactory.getSocketFactory();
        r5 = 80;
        r2.<init>(r3, r4, r5);
        r1.register(r2);
        r2 = new org.apache.http.conn.scheme.Scheme;
        r3 = "https";
        r4 = org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory();
        r5 = 443; // 0x1bb float:6.21E-43 double:2.19E-321;
        r2.<init>(r3, r4, r5);
        r1.register(r2);
        r2 = new org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
        r2.<init>(r0, r1);
        r1 = new org.apache.http.impl.client.DefaultHttpClient;
        r1.<init>(r2, r0);
        r6.b = r1;
    L_0x005f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.ImmersionHttpClient.a():void");
    }

    public static int b0446цц044604460446() {
        return 1;
    }

    public static int bцц0446ц04460446() {
        return 26;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.immersion.hapticmediasdk.controllers.ImmersionHttpClient getHttpClient() {
        /*
        r0 = new com.immersion.hapticmediasdk.controllers.ImmersionHttpClient;
        r0.<init>();
        r0.a();
    L_0x0008:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0011;
            case 1: goto L_0x0008;
            default: goto L_0x000c;
        };
    L_0x000c:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0011;
            default: goto L_0x0010;
        };
    L_0x0010:
        goto L_0x000c;
    L_0x0011:
        r1 = bцц0446ц04460446();
        r2 = b0446ц0446ц04460446;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bц04460446ц04460446;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x0023;
            default: goto L_0x001f;
        };
    L_0x001f:
        r1 = 31;
        b0446ц0446ц04460446 = r1;
    L_0x0023:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.ImmersionHttpClient.getHttpClient():com.immersion.hapticmediasdk.controllers.ImmersionHttpClient");
    }

    public HttpResponse executeDelete(String str, Map map, int i) throws Exception {
        HttpUriRequest httpDelete = new HttpDelete(str);
        if (((bццц044604460446 + b0446ц0446ц04460446) * bццц044604460446) % bц04460446ц04460446 != b044604460446ц04460446) {
            bццц044604460446 = 82;
            b044604460446ц04460446 = bцц0446ц04460446();
        }
        return a(httpDelete, map, i);
    }

    public HttpResponse executeGet(String str, Map map, int i) throws Exception {
        int i2 = bццц044604460446;
        switch ((i2 * (b0446цц044604460446() + i2)) % bц04460446ц04460446) {
            case 0:
                break;
            default:
                bццц044604460446 = bцц0446ц04460446();
                b044604460446ц04460446 = bцц0446ц04460446();
                break;
        }
        try {
            try {
                return a(new HttpGet(str), map, i);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public HttpResponse executePost(String str, Map map, int i) throws Exception {
        return a(new HttpPost(str), map, i);
    }

    public HttpResponse executePostWithBody(String str, String str2, Map map, int i) throws Exception {
        try {
            HttpUriRequest httpPost = new HttpPost(str);
            try {
                HttpEntity stringEntity = new StringEntity(str2, HttpRequest.CHARSET_UTF8);
                int i2 = bццц044604460446;
                switch ((i2 * (b0446ц0446ц04460446 + i2)) % bц04460446ц04460446) {
                    case 0:
                        break;
                    default:
                        bццц044604460446 = bцц0446ц04460446();
                        b044604460446ц04460446 = 81;
                        break;
                }
                httpPost.setEntity(stringEntity);
                return a(httpPost, map, i);
            } catch (UnsupportedEncodingException e) {
                throw e;
            } catch (Exception e2) {
                throw e2;
            }
        } catch (Exception e22) {
            throw e22;
        }
    }

    public HttpParams getParams() {
        int i = bццц044604460446;
        switch ((i * (b0446ц0446ц04460446 + i)) % bц04460446ц04460446) {
            case 0:
                break;
            default:
                bццц044604460446 = 18;
                b044604460446ц04460446 = bцц0446ц04460446();
                break;
        }
        try {
            try {
                return this.b.getParams();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
