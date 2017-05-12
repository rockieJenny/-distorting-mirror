package com.inmobi.monetization.internal.carb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.data.DeviceInfo;
import com.inmobi.commons.internal.EncryptionUtils;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.thinICE.icedatacollector.IceDataCollector;
import com.inmobi.commons.thinICE.wifi.WifiInfo;
import com.inmobi.commons.uid.UID;
import com.inmobi.monetization.internal.configs.PkInitilaizer;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;

public class CARB {
    public static final String LOGGING_TAG = "[InMobi]-[CARB]-4.5.3";
    private static CARB g = new CARB();
    private static SharedPreferences j;
    private static AtomicBoolean k = new AtomicBoolean(false);
    private static Thread l;
    private static AtomicBoolean m = new AtomicBoolean(false);
    private static Thread n;
    private static String o = "";
    private static String p = "";
    private static String q = "";
    byte[] a;
    byte[] b;
    String c = "";
    String d = "";
    String e = "";
    CarbCallback f = null;
    private String h = "carb_last_req_time";
    private String i = "carbpreference";
    private final int r = 8;
    private final int s = 16;
    private byte[] t;
    private byte[] u;
    private byte[] v;
    private byte[] w;
    private ArrayList<CarbInfo> x = new ArrayList();

    public interface CarbCallback {
        void postFailed();

        void postSuccess();
    }

    public static CARB getInstance() {
        return g;
    }

    private CARB() {
        CarbInitializer.initialize();
        j = InternalSDKUtil.getContext().getSharedPreferences(this.i, 0);
    }

    public synchronized void startCarb() {
        if (!CarbInitializer.getConfigParams().isCarbEnabled()) {
            Log.internal(LOGGING_TAG, "CARB feature disabled.");
        } else if (InternalSDKUtil.checkNetworkAvailibility(InternalSDKUtil.getContext())) {
            long j = j.getLong(this.h, 0);
            if (0 != j) {
                if ((j + CarbInitializer.getConfigParams().getRetreiveFrequncy()) - System.currentTimeMillis() > 0) {
                    Log.internal(LOGGING_TAG, "CARB request interval not reached. NO request");
                } else if (k.get()) {
                    Log.internal(LOGGING_TAG, "CARB request is in progress");
                } else {
                    Log.internal(LOGGING_TAG, "CARB request interval reached. Requesting again");
                    b();
                }
            } else if (k.get()) {
                Log.internal(LOGGING_TAG, "First CARB request is in progress");
            } else {
                Log.internal(LOGGING_TAG, "Requesting CARB first time");
                b();
            }
        }
    }

    private synchronized void b() {
        this.x.clear();
        if (k.compareAndSet(false, true)) {
            l = new Thread(new Runnable(this) {
                int a = 1;
                final /* synthetic */ CARB b;

                {
                    this.b = r2;
                }

                public void run() {
                    a();
                }

                private void a() {
                    this.b.c();
                    this.b.t = EncryptionUtils.keag();
                    this.b.c = PkInitilaizer.getConfigParams().getExponent();
                    this.b.d = PkInitilaizer.getConfigParams().getModulus();
                    this.b.e = PkInitilaizer.getConfigParams().getVersion();
                    if (this.b.c.equals("") || this.b.d.equals("") || this.b.e.equals("")) {
                        Log.internal(CARB.LOGGING_TAG, "Exception retreiving Carb info due to key problem");
                        this.b.e();
                        return;
                    }
                    String b = b();
                    Log.internal(CARB.LOGGING_TAG, "Unencrypted postbody :" + b);
                    String a = this.b.a(b, this.b.t, this.b.u, this.b.a, this.b.d, this.b.c);
                    if (a == null) {
                        this.b.e();
                    } else if (a(a) != 200) {
                        long retryInterval = CarbInitializer.getConfigParams().getRetryInterval() * 1000;
                        if (this.a <= CarbInitializer.getConfigParams().getRetryCount()) {
                            try {
                                Thread.sleep(((long) this.a) * retryInterval);
                            } catch (InterruptedException e) {
                            }
                            Log.internal(CARB.LOGGING_TAG, "Get carb info Failed. Retrying count: " + this.a);
                            this.a++;
                            if (Looper.myLooper() == null) {
                                Looper.prepare();
                            }
                            new Handler().postDelayed(this, 0);
                            Looper.loop();
                            Looper.myLooper().quit();
                            return;
                        }
                        this.a = 0;
                        Editor edit = CARB.j.edit();
                        edit.putLong(this.b.h, System.currentTimeMillis());
                        edit.commit();
                        this.b.f();
                    }
                }

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                private int a(java.lang.String r11) {
                    /*
                    r10 = this;
                    r4 = 0;
                    r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                    r5 = 0;
                    r1 = -1;
                    r0 = com.inmobi.commons.internal.InternalSDKUtil.getContext();
                    r0 = com.inmobi.commons.internal.InternalSDKUtil.checkNetworkAvailibility(r0);
                    if (r0 != 0) goto L_0x0011;
                L_0x000f:
                    r0 = r1;
                L_0x0010:
                    return r0;
                L_0x0011:
                    r3 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = com.inmobi.monetization.internal.carb.CarbInitializer.getConfigParams();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r0.getCarbEndpoint();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3.<init>(r0);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r3.openConnection();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = "User-Agent";
                    r7 = com.inmobi.commons.internal.InternalSDKUtil.getUserAgent();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.setRequestProperty(r6, r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = com.inmobi.monetization.internal.carb.CarbInitializer.getConfigParams();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r6.getTimeoutInterval();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
                    r6 = r6 * r8;
                    r8 = (int) r6;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.setConnectTimeout(r8);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = (int) r6;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.setReadTimeout(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = "user-agent";
                    r7 = com.inmobi.commons.data.DeviceInfo.getPhoneDefaultUserAgent();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.setRequestProperty(r6, r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = 0;
                    r0.setUseCaches(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = 1;
                    r0.setDoOutput(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = 1;
                    r0.setDoInput(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = "POST";
                    r0.setRequestMethod(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = "content-type";
                    r7 = "application/x-www-form-urlencoded";
                    r0.setRequestProperty(r6, r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = "Content-Length";
                    r7 = r11.length();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7 = java.lang.Integer.toString(r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.setRequestProperty(r6, r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r3.getHost();	 Catch:{ UnknownHostException -> 0x00f5 }
                    java.net.InetAddress.getByName(r6);	 Catch:{ UnknownHostException -> 0x00f5 }
                    r3 = new java.io.BufferedWriter;	 Catch:{ all -> 0x0139 }
                    r6 = new java.io.OutputStreamWriter;	 Catch:{ all -> 0x0139 }
                    r7 = r0.getOutputStream();	 Catch:{ all -> 0x0139 }
                    r6.<init>(r7);	 Catch:{ all -> 0x0139 }
                    r3.<init>(r6);	 Catch:{ all -> 0x0139 }
                    r3.write(r11);	 Catch:{ all -> 0x0260 }
                    if (r3 == 0) goto L_0x008b;
                L_0x0088:
                    r3.close();	 Catch:{ IOException -> 0x0115, MalformedURLException -> 0x00ea, JSONException -> 0x0130 }
                L_0x008b:
                    r3 = "[InMobi]-[CARB]-4.5.3";
                    r6 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7 = "Get CARB list status: ";
                    r6 = r6.append(r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7 = r0.getResponseCode();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r6.append(r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r6.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    com.inmobi.commons.internal.Log.internal(r3, r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r0.getResponseCode();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    if (r3 != r2) goto L_0x025a;
                L_0x00ad:
                    r3 = com.inmobi.monetization.internal.carb.CARB.j;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.edit();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r6.h;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r8 = java.lang.System.currentTimeMillis();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3.putLong(r6, r8);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3.commit();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = new java.io.BufferedReader;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r0.getInputStream();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7 = "UTF-8";
                    r6.<init>(r0, r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3.<init>(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                L_0x00da:
                    r6 = r3.readLine();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    if (r6 == 0) goto L_0x015b;
                L_0x00e0:
                    r6 = r0.append(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7 = "\n";
                    r6.append(r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    goto L_0x00da;
                L_0x00ea:
                    r0 = move-exception;
                    r0 = "[InMobi]-[CARB]-4.5.3";
                    r2 = "Malformed URL";
                    com.inmobi.commons.internal.Log.internal(r0, r2);
                L_0x00f2:
                    r0 = r1;
                    goto L_0x0010;
                L_0x00f5:
                    r0 = move-exception;
                    r0 = new java.net.MalformedURLException;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r2 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r2.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r4 = "Malformed URL: ";
                    r2 = r2.append(r4);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r2 = r2.append(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r2 = r2.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.<init>(r2);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    throw r0;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                L_0x0113:
                    r0 = move-exception;
                    goto L_0x00f2;
                L_0x0115:
                    r6 = move-exception;
                    r7 = "[InMobi]-[Monetization]";
                    r8 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r8.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r9 = "Exception closing resource: ";
                    r8 = r8.append(r9);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r8.append(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    com.inmobi.commons.internal.Log.internal(r7, r3, r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    goto L_0x008b;
                L_0x0130:
                    r0 = move-exception;
                    r0 = "[InMobi]-[CARB]-4.5.3";
                    r2 = "Invalid JSON response";
                    com.inmobi.commons.internal.Log.internal(r0, r2);
                    goto L_0x00f2;
                L_0x0139:
                    r0 = move-exception;
                    r2 = r4;
                L_0x013b:
                    if (r2 == 0) goto L_0x0140;
                L_0x013d:
                    r2.close();	 Catch:{ IOException -> 0x0141, MalformedURLException -> 0x00ea, JSONException -> 0x0130 }
                L_0x0140:
                    throw r0;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                L_0x0141:
                    r3 = move-exception;
                    r4 = "[InMobi]-[Monetization]";
                    r5 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r5.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = "Exception closing resource: ";
                    r5 = r5.append(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r2 = r5.append(r2);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r2 = r2.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    com.inmobi.commons.internal.Log.internal(r4, r2, r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    goto L_0x0140;
                L_0x015b:
                    r0 = r0.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = "[InMobi]-[CARB]-4.5.3";
                    r6 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7 = "RESPONSE: ";
                    r6 = r6.append(r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r6.append(r0);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = r6.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    com.inmobi.commons.internal.Log.internal(r3, r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r0.getBytes();	 Catch:{ Exception -> 0x01dd }
                    r3 = 0;
                    r0 = com.inmobi.commons.thirdparty.Base64.decode(r0, r3);	 Catch:{ Exception -> 0x01dd }
                    r3 = r10.b;	 Catch:{ Exception -> 0x01dd }
                    r3 = r3.t;	 Catch:{ Exception -> 0x01dd }
                    r6 = r10.b;	 Catch:{ Exception -> 0x01dd }
                    r6 = r6.u;	 Catch:{ Exception -> 0x01dd }
                    r3 = com.inmobi.commons.internal.EncryptionUtils.DeAe(r0, r3, r6);	 Catch:{ Exception -> 0x01dd }
                    r0 = new java.lang.String;	 Catch:{ Exception -> 0x01dd }
                    r0.<init>(r3);	 Catch:{ Exception -> 0x01dd }
                    r4 = r0;
                L_0x0196:
                    if (r4 == 0) goto L_0x01e6;
                L_0x0198:
                    r0 = "[InMobi]-[CARB]-4.5.3";
                    r3 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r6 = "Get list after decryption: ";
                    r3 = r3.append(r6);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.append(r4);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.toString();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    com.inmobi.commons.internal.Log.internal(r0, r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = new org.json.JSONObject;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.<init>(r4);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = "success";
                    r3 = r0.getBoolean(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    if (r3 == 0) goto L_0x0252;
                L_0x01bd:
                    r3 = "data";
                    r0 = r0.getJSONObject(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = "req_id";
                    r4 = r0.getString(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = "p_apps";
                    r6 = r0.getJSONArray(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r6.length();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    if (r0 != 0) goto L_0x01f5;
                L_0x01d5:
                    r0 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.e();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r1;
                    goto L_0x0010;
                L_0x01dd:
                    r0 = move-exception;
                    r3 = "[InMobi]-[CARB]-4.5.3";
                    r6 = "Exception in carb ";
                    com.inmobi.commons.internal.Log.internal(r3, r6, r0);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    goto L_0x0196;
                L_0x01e6:
                    r0 = "[InMobi]-[CARB]-4.5.3";
                    r2 = "Unable to decrypt response or response not encrypted";
                    com.inmobi.commons.internal.Log.internal(r0, r2);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.e();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r1;
                    goto L_0x0010;
                L_0x01f5:
                    r0 = r5;
                L_0x01f6:
                    r3 = r6.length();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    if (r0 >= r3) goto L_0x0245;
                L_0x01fc:
                    r3 = r6.getJSONObject(r0);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r5 = "";
                    r5 = "";
                    r5 = "bid";
                    r5 = r3.getString(r5);	 Catch:{ JSONException -> 0x021f, MalformedURLException -> 0x00ea, IOException -> 0x0113 }
                    r7 = "inm_id";
                    r3 = r3.getString(r7);	 Catch:{ JSONException -> 0x021f, MalformedURLException -> 0x00ea, IOException -> 0x0113 }
                    if (r3 == 0) goto L_0x021a;
                L_0x0212:
                    r7 = "";
                    r7 = r7.equals(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    if (r7 == 0) goto L_0x0228;
                L_0x021a:
                    r3 = r0 + 1;
                    r5 = r0;
                    r0 = r3;
                    goto L_0x01f6;
                L_0x021f:
                    r3 = move-exception;
                    r3 = "[InMobi]-[CARB]-4.5.3";
                    r5 = "BID or INM missing";
                    com.inmobi.commons.internal.Log.internal(r3, r5);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    goto L_0x021a;
                L_0x0228:
                    r7 = new com.inmobi.monetization.internal.carb.CarbInfo;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7.<init>();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7.setBid(r5);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r7.setInmId(r3);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.a(r5);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    if (r3 != 0) goto L_0x021a;
                L_0x023b:
                    r3 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.x;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3.add(r7);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    goto L_0x021a;
                L_0x0245:
                    r0 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r3 = r3.x;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r5 = r5 + 1;
                    r0.a(r3, r4, r5);	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                L_0x0252:
                    r0 = r10.b;	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0.e();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    r0 = r2;
                    goto L_0x0010;
                L_0x025a:
                    r0 = r0.getResponseCode();	 Catch:{ MalformedURLException -> 0x00ea, IOException -> 0x0113, JSONException -> 0x0130 }
                    goto L_0x0010;
                L_0x0260:
                    r0 = move-exception;
                    r2 = r3;
                    goto L_0x013b;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.inmobi.monetization.internal.carb.CARB.1.a(java.lang.String):int");
                }

                private String b() {
                    StringBuffer stringBuffer = new StringBuffer();
                    CARB.fillCarbInfo();
                    stringBuffer.append("mk-siteid=");
                    stringBuffer.append(CARB.getURLEncoded(InMobi.getAppId()));
                    String str = "pr-SAND-" + InternalSDKUtil.getInMobiInternalVersion(InternalSDKUtil.INMOBI_SDK_RELEASE_VERSION) + "-" + InternalSDKUtil.INMOBI_SDK_RELEASE_DATE;
                    stringBuffer.append("&mk-version=");
                    stringBuffer.append(CARB.getURLEncoded(str));
                    str = UID.getInstance().getJSON(CarbInitializer.getConfigParams().getDeviceIdMaskMap());
                    stringBuffer.append("&u-id-map=");
                    stringBuffer.append(CARB.getURLEncoded(str));
                    stringBuffer.append("&u-appbid=");
                    stringBuffer.append(CARB.getURLEncoded(CARB.getAppBid()));
                    stringBuffer.append("&u-appver=");
                    stringBuffer.append(CARB.getURLEncoded(CARB.getAppVer()));
                    stringBuffer.append("&h-user-agent=");
                    stringBuffer.append(CARB.getURLEncoded(DeviceInfo.getPhoneDefaultUserAgent()));
                    stringBuffer.append("&d-localization=");
                    stringBuffer.append(CARB.getURLEncoded(DeviceInfo.getLocalization()));
                    stringBuffer.append("&d-nettype=");
                    stringBuffer.append(CARB.getURLEncoded(DeviceInfo.getNetworkType()));
                    WifiInfo wifiInfo = null;
                    try {
                        wifiInfo = IceDataCollector.getConnectedWifiInfo(InternalSDKUtil.getContext());
                    } catch (Exception e) {
                        Log.internal(InternalSDKUtil.LOGGING_TAG, "No wifi permissions set, unable to send wifi data");
                    }
                    if (wifiInfo != null) {
                        stringBuffer.append("&c-ap-bssid=");
                        stringBuffer.append(Long.toString(wifiInfo.bssid));
                    }
                    return stringBuffer.toString();
                }
            });
            l.start();
        }
    }

    private String a(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, String str2, String str3) {
        String SeMeGe = EncryptionUtils.SeMeGe(str, bArr, bArr2, bArr3, str2, str3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sm=");
        stringBuilder.append(SeMeGe);
        stringBuilder.append("&sn=");
        stringBuilder.append(this.e);
        SeMeGe = stringBuilder.toString();
        Log.internal(LOGGING_TAG, SeMeGe);
        return SeMeGe;
    }

    private void c() {
        try {
            this.a = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(this.a);
            this.u = new byte[16];
            secureRandom.nextBytes(this.u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void a(final ArrayList<CarbInfo> arrayList, final String str, final int i) {
        if (m.compareAndSet(false, true)) {
            m.set(true);
            n = new Thread(new Runnable(this) {
                int a = 1;
                final /* synthetic */ CARB e;

                public void run() {
                    this.e.d();
                    this.e.v = EncryptionUtils.keag();
                    this.e.c = PkInitilaizer.getConfigParams().getExponent();
                    this.e.d = PkInitilaizer.getConfigParams().getModulus();
                    this.e.e = PkInitilaizer.getConfigParams().getVersion();
                    if (this.e.c.equals("") || this.e.d.equals("") || this.e.e.equals("")) {
                        Log.internal(CARB.LOGGING_TAG, "Exception retreiving Carb info due to key problem");
                        this.e.f();
                        return;
                    }
                    String a = a(arrayList, str, i);
                    Log.internal(CARB.LOGGING_TAG, "PostBody Before encryption: " + a);
                    String a2 = this.e.a(a, this.e.v, this.e.w, this.e.b, this.e.d, this.e.c);
                    if (a2 == null) {
                        Log.internal(CARB.LOGGING_TAG, "POST message cannot be encrypted");
                        this.e.e();
                        return;
                    }
                    int a3 = a(a2);
                    Log.internal(CARB.LOGGING_TAG, "Post Response to CARB server: " + a3);
                    if (200 == a3) {
                        if (this.e.f != null) {
                            this.e.f.postSuccess();
                        }
                        this.e.f();
                        return;
                    }
                    if (this.e.f != null) {
                        this.e.f.postFailed();
                    }
                    long retryInterval = CarbInitializer.getConfigParams().getRetryInterval() * 1000;
                    if (this.a <= CarbInitializer.getConfigParams().getRetryCount()) {
                        try {
                            Thread.sleep(((long) this.a) * retryInterval);
                        } catch (InterruptedException e) {
                        }
                        Log.internal(CARB.LOGGING_TAG, "POSt to carb failed. Retrying count: " + this.a);
                        this.a++;
                        if (Looper.myLooper() == null) {
                            Looper.prepare();
                        }
                        new Handler().postDelayed(this, 0);
                        Looper.loop();
                        Looper.myLooper().quit();
                        return;
                    }
                    this.a = 0;
                    this.e.f();
                }

                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                private int a(java.lang.String r9) {
                    /*
                    r8 = this;
                    r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
                    r1 = -1;
                    r0 = com.inmobi.commons.internal.InternalSDKUtil.getContext();
                    r0 = com.inmobi.commons.internal.InternalSDKUtil.checkNetworkAvailibility(r0);
                    if (r0 != 0) goto L_0x000f;
                L_0x000d:
                    r0 = r1;
                L_0x000e:
                    return r0;
                L_0x000f:
                    r3 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0 = com.inmobi.monetization.internal.carb.CarbInitializer.getConfigParams();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0 = r0.getCarbPostpoint();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r3.<init>(r0);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0 = r3.openConnection();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = "User-Agent";
                    r5 = com.inmobi.commons.internal.InternalSDKUtil.getUserAgent();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0.setRequestProperty(r4, r5);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = com.inmobi.monetization.internal.carb.CarbInitializer.getConfigParams();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = r4.getTimeoutInterval();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
                    r4 = r4 * r6;
                    r6 = (int) r4;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0.setConnectTimeout(r6);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = (int) r4;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0.setReadTimeout(r4);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = "user-agent";
                    r5 = com.inmobi.commons.data.DeviceInfo.getPhoneDefaultUserAgent();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0.setRequestProperty(r4, r5);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = 0;
                    r0.setUseCaches(r4);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = 1;
                    r0.setDoOutput(r4);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = 1;
                    r0.setDoInput(r4);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = "POST";
                    r0.setRequestMethod(r4);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = "content-type";
                    r5 = "application/x-www-form-urlencoded";
                    r0.setRequestProperty(r4, r5);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = "Content-Length";
                    r5 = r9.length();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r5 = java.lang.Integer.toString(r5);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0.setRequestProperty(r4, r5);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = r3.getHost();	 Catch:{ UnknownHostException -> 0x0093 }
                    java.net.InetAddress.getByName(r4);	 Catch:{ UnknownHostException -> 0x0093 }
                    r4 = 0;
                    r3 = new java.io.BufferedWriter;	 Catch:{ all -> 0x00d8 }
                    r5 = new java.io.OutputStreamWriter;	 Catch:{ all -> 0x00d8 }
                    r6 = r0.getOutputStream();	 Catch:{ all -> 0x00d8 }
                    r5.<init>(r6);	 Catch:{ all -> 0x00d8 }
                    r3.<init>(r5);	 Catch:{ all -> 0x00d8 }
                    r3.write(r9);	 Catch:{ all -> 0x0100 }
                    if (r3 == 0) goto L_0x008a;
                L_0x0087:
                    r3.close();	 Catch:{ IOException -> 0x00bc, MalformedURLException -> 0x00b1 }
                L_0x008a:
                    r3 = r0.getResponseCode();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    if (r3 != r2) goto L_0x00fa;
                L_0x0090:
                    r0 = r2;
                    goto L_0x000e;
                L_0x0093:
                    r0 = move-exception;
                    r0 = new java.net.MalformedURLException;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r2 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r2.<init>();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r4 = "Malformed URL: ";
                    r2 = r2.append(r4);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r3 = r3.toString();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r2 = r2.append(r3);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r2 = r2.toString();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r0.<init>(r2);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    throw r0;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                L_0x00b1:
                    r0 = move-exception;
                    r0 = "[InMobi]-[CARB]-4.5.3";
                    r2 = "Malformed URL";
                    com.inmobi.commons.internal.Log.internal(r0, r2);
                L_0x00b9:
                    r0 = r1;
                    goto L_0x000e;
                L_0x00bc:
                    r4 = move-exception;
                    r5 = "[InMobi]-[Monetization]";
                    r6 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r6.<init>();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r7 = "Exception closing resource: ";
                    r6 = r6.append(r7);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r3 = r6.append(r3);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r3 = r3.toString();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    com.inmobi.commons.internal.Log.internal(r5, r3, r4);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    goto L_0x008a;
                L_0x00d6:
                    r0 = move-exception;
                    goto L_0x00b9;
                L_0x00d8:
                    r0 = move-exception;
                    r2 = r4;
                L_0x00da:
                    if (r2 == 0) goto L_0x00df;
                L_0x00dc:
                    r2.close();	 Catch:{ IOException -> 0x00e0, MalformedURLException -> 0x00b1 }
                L_0x00df:
                    throw r0;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                L_0x00e0:
                    r3 = move-exception;
                    r4 = "[InMobi]-[Monetization]";
                    r5 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r5.<init>();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r6 = "Exception closing resource: ";
                    r5 = r5.append(r6);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r2 = r5.append(r2);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    r2 = r2.toString();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    com.inmobi.commons.internal.Log.internal(r4, r2, r3);	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    goto L_0x00df;
                L_0x00fa:
                    r0 = r0.getResponseCode();	 Catch:{ MalformedURLException -> 0x00b1, IOException -> 0x00d6 }
                    goto L_0x000e;
                L_0x0100:
                    r0 = move-exception;
                    r2 = r3;
                    goto L_0x00da;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.inmobi.monetization.internal.carb.CARB.2.a(java.lang.String):int");
                }

                private String a(ArrayList<CarbInfo> arrayList, String str, int i) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("req_id=");
                    stringBuffer.append(CARB.getURLEncoded(str));
                    JSONArray jSONArray = new JSONArray();
                    int size = arrayList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        jSONArray.put(((CarbInfo) arrayList.get(i2)).getInmId());
                    }
                    stringBuffer.append("&p_a_apps=");
                    stringBuffer.append(CARB.getURLEncoded(jSONArray.toString()));
                    stringBuffer.append("&i_till=");
                    stringBuffer.append(i);
                    String json = UID.getInstance().getJSON(CarbInitializer.getConfigParams().getDeviceIdMaskMap());
                    stringBuffer.append("&u-id-map=");
                    stringBuffer.append(CARB.getURLEncoded(json));
                    return stringBuffer.toString();
                }
            });
            n.start();
        }
    }

    private void d() {
        try {
            this.b = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(this.b);
            this.w = new byte[16];
            secureRandom.nextBytes(this.w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void e() {
        if (k != null) {
            k.set(false);
        }
    }

    private void f() {
        if (m != null) {
            m.set(false);
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

    public static void fillCarbInfo() {
        try {
            Context context = InternalSDKUtil.getContext();
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                setAppBId(applicationInfo.packageName);
                setAppDisplayName(applicationInfo.loadLabel(packageManager).toString());
            }
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 128);
            String str = null;
            if (packageInfo != null) {
                str = packageInfo.versionName;
                if (str == null || str.equals("")) {
                    str = packageInfo.versionCode + "";
                }
            }
            if (str != null && !str.equals("")) {
                setAppVer(str);
            }
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Failed to fill CarbInfo", e);
        }
    }

    private boolean a(String str) {
        try {
            InternalSDKUtil.getContext().getPackageManager().getPackageInfo(str, 256);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static void setAppBId(String str) {
        o = str;
    }

    public static String getAppBid() {
        return o;
    }

    public static void setAppVer(String str) {
        p = str;
    }

    public static String getAppVer() {
        return p;
    }

    public static void setAppDisplayName(String str) {
        q = str;
    }

    public static String getAppDisplayName() {
        return q;
    }

    public static Object getCountryISO(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager.getNetworkCountryIso().equals("")) {
            return context.getResources().getConfiguration().locale.getISO3Country();
        }
        return telephonyManager.getNetworkCountryIso();
    }

    public void setCallBack(CarbCallback carbCallback) {
        this.f = carbCallback;
    }

    public static String getURLDecoded(String str, String str2) {
        String str3 = "";
        try {
            str3 = URLDecoder.decode(str, str2);
        } catch (Exception e) {
        }
        return str3;
    }
}
