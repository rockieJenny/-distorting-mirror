package com.google.android.gms.analytics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.GZIPOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

public class h implements r {
    private final Context mContext;
    private final String wl;
    private final HttpClient yf;
    private URL yg;
    private int yh;
    private int yi;
    private int yj;
    private String yk;
    private String yl;
    private i ym;
    private l yn;
    private Set<Integer> yo = new HashSet();
    private boolean yp = false;
    private long yq;
    private long yr;
    private o ys;
    private volatile boolean yt = false;

    h(HttpClient httpClient, Context context, o oVar) {
        this.mContext = context.getApplicationContext();
        this.ys = oVar;
        this.wl = a("GoogleAnalytics", "4.0", VERSION.RELEASE, an.a(Locale.getDefault()), Build.MODEL, Build.ID);
        this.yf = httpClient;
    }

    private String a(ab abVar, List<String> list, i iVar) {
        if (iVar == i.NONE) {
            CharSequence fa = (abVar.fa() == null || abVar.fa().length() == 0) ? "" : abVar.fa();
            return TextUtils.isEmpty(fa) ? "" : ac.a(abVar, System.currentTimeMillis());
        } else {
            String str = "";
            for (String str2 : list) {
                if (str2.length() != 0) {
                    if (str.length() != 0) {
                        str = str + "\n";
                    }
                    str = str + str2;
                }
            }
            return str;
        }
    }

    private URL a(ab abVar) {
        if (this.yg != null) {
            return this.yg;
        }
        try {
            return new URL("http:".equals(abVar.fd()) ? "http://www.google-analytics.com/collect" : "https://ssl.google-analytics.com/collect");
        } catch (MalformedURLException e) {
            ae.T("Error trying to parse the hardcoded host url. This really shouldn't happen.");
            return null;
        }
    }

    private void a(af afVar, HttpHost httpHost, i iVar, l lVar) {
        afVar.g("_bs", iVar.toString());
        afVar.g("_cs", lVar.toString());
        Object fg = afVar.fg();
        if (!TextUtils.isEmpty(fg)) {
            HttpHost httpHost2;
            if (httpHost == null) {
                try {
                    URL url = new URL("https://ssl.google-analytics.com");
                    httpHost2 = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
                } catch (MalformedURLException e) {
                    return;
                }
            }
            httpHost2 = httpHost;
            a(fg, httpHost2, 1, afVar, l.NONE);
        }
    }

    private void a(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object obj : httpEntityEnclosingRequest.getAllHeaders()) {
            stringBuffer.append(obj.toString()).append("\n");
        }
        stringBuffer.append(httpEntityEnclosingRequest.getRequestLine().toString()).append("\n");
        if (httpEntityEnclosingRequest.getEntity() != null) {
            try {
                InputStream content = httpEntityEnclosingRequest.getEntity().getContent();
                if (content != null) {
                    int available = content.available();
                    if (available > 0) {
                        byte[] bArr = new byte[available];
                        content.read(bArr);
                        stringBuffer.append("POST:\n");
                        stringBuffer.append(new String(bArr)).append("\n");
                    }
                }
            } catch (IOException e) {
                ae.W("Error Writing hit to log...");
            }
        }
        ae.U(stringBuffer.toString());
    }

    private boolean a(String str, HttpHost httpHost, int i, af afVar, l lVar) {
        boolean z = i > 1;
        if (str.getBytes().length > this.yj || str.getBytes().length > this.yi) {
            ae.W("Request too long (> " + Math.min(this.yi, this.yj) + " bytes)--not sent");
            return true;
        } else if (this.yt) {
            ae.U("Dry run enabled. Hit not actually sent.");
            return true;
        } else {
            HttpEntityEnclosingRequest d = d(str, z);
            if (d == null) {
                return true;
            }
            int length;
            int length2;
            if (d.getRequestLine().getMethod().equals(HttpRequest.METHOD_GET)) {
                length = str.getBytes().length;
                length2 = str.getBytes().length;
            } else {
                try {
                    switch (lVar) {
                        case GZIP:
                            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                            gZIPOutputStream.write(str.getBytes());
                            gZIPOutputStream.close();
                            byte[] toByteArray = byteArrayOutputStream.toByteArray();
                            length2 = toByteArray.length + 0;
                            d.setEntity(new ByteArrayEntity(toByteArray));
                            d.addHeader(HttpRequest.HEADER_CONTENT_ENCODING, HttpRequest.ENCODING_GZIP);
                            break;
                        default:
                            length2 = str.getBytes().length;
                            d.setEntity(new StringEntity(str));
                            break;
                    }
                    length = str.getBytes().length;
                } catch (UnsupportedEncodingException e) {
                    ae.T("Encoding error, hit will be discarded");
                    return true;
                } catch (IOException e2) {
                    ae.T("Unexpected IOException: " + e2.getMessage());
                    ae.T("Request will be discarded");
                    return true;
                }
            }
            d.addHeader("Host", httpHost.toHostString());
            if (ae.ff()) {
                a(d);
            }
            try {
                HttpResponse execute = this.yf.execute(httpHost, d);
                afVar.e("_td", length);
                afVar.e("_cd", length2);
                length2 = execute.getStatusLine().getStatusCode();
                HttpEntity entity = execute.getEntity();
                if (entity != null) {
                    entity.consumeContent();
                }
                if (length2 == 200) {
                    return true;
                }
                if (z && this.yo.contains(Integer.valueOf(length2))) {
                    ae.U("Falling back to single hit per request mode.");
                    this.yp = true;
                    this.yq = System.currentTimeMillis();
                    return false;
                }
                ae.W("Bad response: " + execute.getStatusLine().getStatusCode());
                return true;
            } catch (ClientProtocolException e3) {
                ae.W("ClientProtocolException sending hit; discarding hit...");
                return true;
            } catch (IOException e22) {
                ae.W("Exception sending hit: " + e22.getClass().getSimpleName());
                ae.W(e22.getMessage());
                return false;
            }
        }
    }

    private HttpEntityEnclosingRequest d(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            System.out.println("Empty hit, discarding.");
            return null;
        }
        String str2 = this.yk + "?" + str;
        HttpEntityEnclosingRequest basicHttpEntityEnclosingRequest = (str2.length() >= this.yh || z) ? z ? new BasicHttpEntityEnclosingRequest(HttpRequest.METHOD_POST, this.yl) : new BasicHttpEntityEnclosingRequest(HttpRequest.METHOD_POST, this.yk) : new BasicHttpEntityEnclosingRequest(HttpRequest.METHOD_GET, str2);
        basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.wl);
        return basicHttpEntityEnclosingRequest;
    }

    int a(List<ab> list, int i) {
        if (list.isEmpty()) {
            return 0;
        }
        if (i > list.size()) {
            i = list.size();
        }
        int i2 = i - 1;
        long j = 0;
        int i3 = i;
        while (i2 > 0) {
            int i4;
            ab abVar = (ab) list.get(i2);
            long fc = ((ab) list.get(i2 - 1)).fc();
            long fc2 = abVar.fc();
            if (fc == 0 || fc2 == 0 || fc2 - fc <= j) {
                fc2 = j;
                i4 = i3;
            } else {
                fc2 -= fc;
                i4 = i2;
            }
            i2--;
            i3 = i4;
            j = fc2;
        }
        return i3;
    }

    public int a(List<ab> list, af afVar, boolean z) {
        l lVar;
        i iVar;
        int i;
        int i2;
        String str;
        List subList;
        HttpHost httpHost;
        int i3;
        URL a;
        int max;
        this.yh = this.ys.eb();
        this.yi = this.ys.ec();
        this.yj = this.ys.ed();
        int ee = this.ys.ee();
        this.yk = this.ys.eg();
        this.yl = this.ys.eh();
        this.ym = this.ys.ei();
        this.yn = this.ys.ej();
        this.yo.clear();
        this.yo.addAll(this.ys.ek());
        this.yr = this.ys.ef();
        if (!this.yp && this.yo.isEmpty()) {
            this.yp = true;
            this.yq = System.currentTimeMillis();
        }
        if (this.yp && System.currentTimeMillis() - this.yq > 1000 * this.yr) {
            this.yp = false;
        }
        i iVar2;
        if (this.yp) {
            iVar2 = i.NONE;
            lVar = l.NONE;
            iVar = iVar2;
        } else {
            iVar2 = this.ym;
            lVar = this.yn;
            iVar = iVar2;
        }
        int i4 = 0;
        int min = Math.min(list.size(), ee);
        afVar.e("_hr", list.size());
        long currentTimeMillis = System.currentTimeMillis();
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        long j = 0;
        if (iVar != i.NONE) {
            ee = 0;
            for (ab abVar : list) {
                ab abVar2;
                Object a2 = TextUtils.isEmpty(abVar2.fa()) ? "" : ac.a(abVar2, currentTimeMillis);
                if (a2.getBytes().length > this.yi) {
                    a2 = "";
                }
                arrayList.add(a2);
                if (!TextUtils.isEmpty(a2)) {
                    j += (long) ((ee == 0 ? 0 : 1) + a2.getBytes().length);
                }
                arrayList2.add(Long.valueOf(j));
                i = j <= ((long) this.yj) ? ee + 1 : ee;
                if (i == min) {
                    ee = i;
                    i2 = 1;
                    break;
                }
                ee = i;
            }
            i2 = 1;
        } else {
            ee = 0;
            i2 = min;
        }
        while (ee > 1 && ((Long) arrayList2.get(ee - 1)).longValue() > ((long) this.yj)) {
            ee--;
        }
        if (j > ((long) this.yj)) {
            switch (iVar) {
                case BATCH_BY_COUNT:
                    i = arrayList.size() / 2;
                    if (ee <= i) {
                        i = ee;
                        break;
                    }
                    break;
                case BATCH_BY_TIME:
                    i = a(list, ee);
                    break;
                case BATCH_BY_SESSION:
                    i = b(list, ee);
                    break;
                case BATCH_BY_SIZE:
                    if (j >= ((long) (this.yj * 2))) {
                        i = ee;
                        break;
                    }
                    ee = arrayList2.size() - 1;
                    while (ee > 0 && ((Long) arrayList2.get(ee)).longValue() > j / 2) {
                        ee--;
                    }
                    i = ee;
                    break;
                case BATCH_BY_BRUTE_FORCE:
                    i = ee;
                    break;
                default:
                    ae.W("Unexpected batching strategy encountered; sending a single hit.");
                    str = (String) arrayList.get(0);
                    arrayList.clear();
                    arrayList.add(str);
                    i = 1;
                    break;
            }
            if (i < arrayList.size()) {
                subList = arrayList.subList(0, i);
                min = 0;
                httpHost = null;
                i3 = 0;
                while (i3 < i2) {
                    abVar2 = (ab) list.get(i3);
                    a = a(abVar2);
                    max = Math.max(1, r9.size());
                    if (a != null) {
                        ae.W("No destination: discarding hit.");
                        ee = min + max;
                        i = i4 + max;
                    } else {
                        httpHost = new HttpHost(a.getHost(), a.getPort(), a.getProtocol());
                        if (a(a(abVar2, (List) r9, iVar), httpHost, max, afVar, lVar)) {
                            afVar.e("_de", 1);
                            afVar.e("_hd", min);
                            afVar.e("_hs", i4);
                            a(afVar, httpHost, iVar, lVar);
                            return i4;
                        }
                        ee = min;
                        for (String str2 : r9) {
                            ee = TextUtils.isEmpty(str2) ? ee + 1 : ee;
                        }
                        afVar.e("_rs", 1);
                        i = i4 + max;
                    }
                    i3++;
                    min = ee;
                    i4 = i;
                }
                afVar.e("_hd", min);
                afVar.e("_hs", i4);
                if (z) {
                    a(afVar, httpHost, iVar, lVar);
                }
                return i4;
            }
        }
        subList = arrayList;
        min = 0;
        httpHost = null;
        i3 = 0;
        while (i3 < i2) {
            abVar2 = (ab) list.get(i3);
            a = a(abVar2);
            max = Math.max(1, r9.size());
            if (a != null) {
                httpHost = new HttpHost(a.getHost(), a.getPort(), a.getProtocol());
                if (a(a(abVar2, (List) r9, iVar), httpHost, max, afVar, lVar)) {
                    ee = min;
                    while (r6.hasNext()) {
                        if (TextUtils.isEmpty(str2)) {
                        }
                        ee = TextUtils.isEmpty(str2) ? ee + 1 : ee;
                    }
                    afVar.e("_rs", 1);
                    i = i4 + max;
                } else {
                    afVar.e("_de", 1);
                    afVar.e("_hd", min);
                    afVar.e("_hs", i4);
                    a(afVar, httpHost, iVar, lVar);
                    return i4;
                }
            }
            ae.W("No destination: discarding hit.");
            ee = min + max;
            i = i4 + max;
            i3++;
            min = ee;
            i4 = i;
        }
        afVar.e("_hd", min);
        afVar.e("_hs", i4);
        if (z) {
            a(afVar, httpHost, iVar, lVar);
        }
        return i4;
    }

    String a(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{str, str2, str3, str4, str5, str6});
    }

    public void ad(String str) {
        try {
            this.yg = new URL(str);
        } catch (MalformedURLException e) {
            this.yg = null;
        }
    }

    int b(List<ab> list, int i) {
        if (list.isEmpty()) {
            return 0;
        }
        for (int i2 = i - 1; i2 > 0; i2--) {
            String fa = ((ab) list.get(i2)).fa();
            if (!TextUtils.isEmpty(fa)) {
                if (fa.contains("sc=start")) {
                    return i2;
                }
                if (fa.contains("sc=end")) {
                    return i2 + 1;
                }
            }
        }
        return i;
    }

    public boolean ea() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        ae.V("...no network connectivity");
        return false;
    }

    public void setDryRun(boolean dryRun) {
        this.yt = dryRun;
    }
}
