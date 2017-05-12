package com.flurry.sdk;

import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;
import com.flurry.android.impl.ads.protocol.v13.SdkLogRequest;
import com.flurry.android.impl.ads.protocol.v13.SdkLogResponse;
import com.flurry.sdk.gp.a;
import com.google.ads.AdRequest;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.nio.ByteBuffer;

public class cf extends gp {
    private static final String a = cf.class.getSimpleName();
    private final gh<SdkLogRequest> b;
    private final gh<SdkLogResponse> g;

    public cf() {
        this(null);
    }

    public cf(a aVar) {
        super(AdRequest.LOGTAG, cf.class.getSimpleName());
        this.b = new gh("sdk log request", new cl(SdkLogRequest.class));
        this.g = new gh("sdk log response", new cl(SdkLogResponse.class));
        this.f = "AdData_";
        a(aVar);
    }

    public void a(SdkLogRequest sdkLogRequest, String str, String str2, String str3) {
        if (sdkLogRequest == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            gd.a(6, this.c, "Ad log that has to be sent is EMPTY or NULL");
            return;
        }
        byte[] a;
        try {
            a = this.b.a((Object) sdkLogRequest);
        } catch (Exception e) {
            gd.a(5, this.c, "Failed to encode sdk log request: " + e);
            a = null;
        }
        if (a != null) {
            b(a(a, str), str2, str3);
        }
    }

    protected byte[] a(byte[] bArr, String str) {
        byte[] bytes = str.getBytes();
        byte[] array = ByteBuffer.allocate(4).putInt(bytes.length).array();
        byte[] bArr2 = new byte[((array.length + bytes.length) + bArr.length)];
        int i = 0;
        while (i < bArr2.length) {
            if (i < array.length) {
                bArr2[i] = array[i];
            } else if (i < array.length || i >= bytes.length + array.length) {
                bArr2[i] = bArr[(i - 4) - bytes.length];
            } else {
                bArr2[i] = bytes[i - 4];
            }
            i++;
        }
        return bArr2;
    }

    protected Pair<String, byte[]> a(byte[] bArr) {
        int i;
        int i2 = 0;
        byte[] bArr2 = new byte[4];
        byte[] bArr3 = new byte[(bArr.length - 4)];
        for (i = 0; i < bArr.length; i++) {
            if (i < 4) {
                bArr2[i] = bArr[i];
            } else {
                bArr3[i - 4] = bArr[i];
            }
        }
        i = ByteBuffer.wrap(bArr2).getInt();
        bArr2 = new byte[i];
        Object obj = new byte[(bArr3.length - i)];
        while (i2 < bArr3.length) {
            if (i2 < i) {
                bArr2[i2] = bArr3[i2];
            } else {
                obj[i2 - i] = bArr3[i2];
            }
            i2++;
        }
        return new Pair(new String(bArr2), obj);
    }

    protected void a(byte[] bArr, final String str, final String str2) {
        try {
            Pair a = a(bArr);
            String str3 = (String) a.first;
            Object obj = (byte[]) a.second;
            gd.a(4, this.c, "FlurryAdLogsManager: start upload data with id = " + str + " to " + str3);
            hr gkVar = new gk();
            gkVar.a(str3);
            gkVar.a(100000);
            gkVar.a(gl.a.kPost);
            gkVar.a(HttpRequest.HEADER_CONTENT_TYPE, "application/x-flurry");
            gkVar.a("Accept", "application/x-flurry");
            gkVar.a("FM-Checksum", Integer.toString(gh.c(obj)));
            gkVar.a(new gt());
            gkVar.b(new gt());
            gkVar.a(obj);
            gkVar.a(new gk.a<byte[], byte[]>(this) {
                final /* synthetic */ cf c;

                public void a(gk<byte[], byte[]> gkVar, byte[] bArr) {
                    SdkLogResponse sdkLogResponse;
                    int e = gkVar.e();
                    if (gkVar.c() && bArr != null) {
                        try {
                            sdkLogResponse = (SdkLogResponse) this.c.g.d(bArr);
                        } catch (Exception e2) {
                            gd.a(5, cf.a, "Failed to decode sdk log response: " + e2);
                        }
                        if (sdkLogResponse == null && sdkLogResponse.result.equals("success")) {
                            gd.a(5, this.c.c, "FlurryAdLogsManager: ad report " + str + " sent. HTTP response: " + e);
                            if (gd.c() <= 3 && gd.d()) {
                                fp.a().a(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass1 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void run() {
                                        Toast.makeText(fp.a().c(), "Ad log report sent", 0).show();
                                    }
                                });
                            }
                            this.c.a(str, str2, e);
                            this.c.e();
                            return;
                        }
                        if (sdkLogResponse != null) {
                            for (String a : sdkLogResponse.errors) {
                                gd.a(6, this.c.c, a);
                            }
                        }
                        this.c.b(str, str2);
                    }
                    sdkLogResponse = null;
                    if (sdkLogResponse == null) {
                    }
                    if (sdkLogResponse != null) {
                        while (r1.hasNext()) {
                            gd.a(6, this.c.c, a);
                        }
                    }
                    this.c.b(str, str2);
                }
            });
            fn.a().a((Object) this, gkVar);
        } catch (Exception e) {
            gd.a(6, this.c, "Internal ERROR! Report is corrupt!");
            c(str, str2);
        }
    }
}
