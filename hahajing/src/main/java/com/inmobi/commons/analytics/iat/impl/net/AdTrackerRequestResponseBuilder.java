package com.inmobi.commons.analytics.iat.impl.net;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants.StatusCode;
import com.inmobi.commons.analytics.iat.impl.Goal;
import com.inmobi.commons.analytics.iat.impl.config.AdTrackerInitializer;
import com.inmobi.commons.internal.FileOperations;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.uid.UID;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AdTrackerRequestResponseBuilder {
    private static String a = null;
    private static long b = 0;

    protected static String formHttpRequest(String str, String str2, int i, boolean z, String str3) {
        List linkedList = new LinkedList();
        String preferences = FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER);
        if (!(str == null || str.trim().equals(""))) {
            linkedList.add(new BasicNameValuePair("mk-siteid", str));
        }
        Map mapForEncryption = UID.getInstance().getMapForEncryption(AdTrackerInitializer.getConfigParams().getDeviceIdMaskMap());
        if (UID.getInstance().isLimitAdTrackingEnabled()) {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.KEY_LIMIT_ADTRACKING, "1"));
        } else {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.KEY_LIMIT_ADTRACKING, "0"));
        }
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.UIDMAP, (String) mapForEncryption.get(AdTrackerConstants.UIDMAP)));
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.UIDKEY, (String) mapForEncryption.get(AdTrackerConstants.UIDKEY)));
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.UKEYVER, (String) mapForEncryption.get(AdTrackerConstants.UKEYVER)));
        if (!(str2 == null || str2.trim().equals(""))) {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.GOALNAME, str2));
        }
        if (z) {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.LATE_PING, "1"));
        } else {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.LATE_PING, "0"));
        }
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.SOURCE, AdTrackerConstants.ANDROID));
        if (i > 0) {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.GOALCOUNT, Integer.toString(i)));
        }
        String str4 = "pr-SAND-" + InternalSDKUtil.getInMobiInternalVersion(InternalSDKUtil.INMOBI_SDK_RELEASE_VERSION) + "-" + InternalSDKUtil.INMOBI_SDK_RELEASE_DATE;
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.SDKVER, str4));
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.SDKREL_VER, str4));
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.OS_VERSION, String.valueOf(VERSION.SDK_INT)));
        str4 = FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_REFERRER_FROM_LOGCAT);
        if (str4 == null) {
            str4 = "0";
        }
        linkedList.add(new BasicNameValuePair(AdTrackerConstants.KEY_REFERRER_FROM_LOGCAT, str4));
        try {
            str4 = InternalSDKUtil.getContext().getPackageManager().getPackageInfo(InternalSDKUtil.getContext().getPackageName(), 0).versionName;
        } catch (Throwable e) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Cant get appversion", e);
            str4 = null;
        }
        if (!(str4 == null || str4.trim().equals(""))) {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.APPVER, str4));
        }
        if (!(str3 == null || str3.trim().equals(""))) {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.VALIDIDS, str3));
        }
        if (preferences != null) {
            int intPreferences = FileOperations.getIntPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER_FROM_SDK);
            long longPreferences = FileOperations.getLongPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_T2) - FileOperations.getLongPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_T1);
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.REFERRER_FROM_SDK, Integer.toString(intPreferences)));
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.REFERRER_DELAY, Long.toString(longPreferences)));
        }
        if (1 == AdTrackerNetworkInterface.isUnstableNetwork()) {
            linkedList.add(new BasicNameValuePair(AdTrackerConstants.KEY_UNSTABLE_NETWORK, Integer.toString(AdTrackerNetworkInterface.isUnstableNetwork())));
        }
        linkedList.add(new BasicNameValuePair("ts", Long.toString(System.currentTimeMillis())));
        return URLEncodedUtils.format(linkedList, "utf-8");
    }

    public static StatusCode reportGoalOverHttp(String str, Goal goal, String str2) {
        StatusCode statusCode;
        ClientProtocolException e;
        Bundle bundle;
        IOException e2;
        JSONException e3;
        StatusCode statusCode2 = StatusCode.APP_ANALYTICS_UPLOAD_FAILURE;
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        String preferences = FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER);
        String formHttpRequest = formHttpRequest(str, goal.name, goal.count, goal.isDuplicate, str2);
        int connectionTimeout = AdTrackerInitializer.getConfigParams().getConnectionTimeout();
        formHttpRequest = AdTrackerNetworkInterface.a() + formHttpRequest;
        if (preferences != null) {
            formHttpRequest = formHttpRequest + "&referrer=" + preferences;
        }
        Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, formHttpRequest);
        Handler c = AdTrackerNetworkInterface.c();
        Message obtain = Message.obtain();
        obtain.what = 10;
        obtain.obj = goal;
        HttpUriRequest httpGet = new HttpGet(formHttpRequest);
        HttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, connectionTimeout);
        HttpConnectionParams.setSoTimeout(basicHttpParams, connectionTimeout);
        defaultHttpClient.setParams(basicHttpParams);
        try {
            b = System.currentTimeMillis();
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            int statusCode3 = execute.getStatusLine().getStatusCode();
            if (200 == statusCode3) {
                formHttpRequest = EntityUtils.toString(execute.getEntity());
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Response: " + formHttpRequest);
                JSONObject jSONObject = new JSONObject(formHttpRequest);
                String string = jSONObject.getString(AdTrackerConstants.ERRORMSG);
                statusCode3 = jSONObject.getInt(AdTrackerConstants.ERRORCODE);
                if (6000 == statusCode3) {
                    long currentTimeMillis = System.currentTimeMillis() - b;
                    statusCode2 = StatusCode.APP_ANALYTICS_UPLOAD_SUCCESS;
                    obtain.what = 8;
                    obtain.arg2 = (int) currentTimeMillis;
                    statusCode = statusCode2;
                } else {
                    obtain.arg2 = statusCode3;
                    statusCode = statusCode2;
                }
                if (6001 == statusCode3) {
                    try {
                        Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Error uploading ping " + string + "\nReloading webview");
                        FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.VALIDIDS, null);
                        obtain.arg2 = statusCode3;
                    } catch (ClientProtocolException e4) {
                        e = e4;
                        try {
                            obtain.arg2 = AdTrackerConstants.EXCEPTION;
                            e.printStackTrace();
                            return statusCode;
                        } finally {
                            bundle = new Bundle();
                            bundle.putString("appId", str);
                            obtain.setData(bundle);
                            c.sendMessage(obtain);
                        }
                    } catch (IOException e5) {
                        e2 = e5;
                        obtain.arg2 = AdTrackerConstants.EXCEPTION;
                        e2.printStackTrace();
                        bundle = new Bundle();
                        bundle.putString("appId", str);
                        obtain.setData(bundle);
                        c.sendMessage(obtain);
                        return statusCode;
                    } catch (JSONException e6) {
                        e3 = e6;
                        obtain.arg2 = AdTrackerConstants.EXCEPTION;
                        e3.printStackTrace();
                        bundle = new Bundle();
                        bundle.putString("appId", str);
                        obtain.setData(bundle);
                        c.sendMessage(obtain);
                        return statusCode;
                    }
                }
            }
            obtain.arg2 = statusCode3;
            statusCode = statusCode2;
            bundle = new Bundle();
            bundle.putString("appId", str);
            obtain.setData(bundle);
            c.sendMessage(obtain);
        } catch (ClientProtocolException e7) {
            ClientProtocolException clientProtocolException = e7;
            statusCode = statusCode2;
            e = clientProtocolException;
            obtain.arg2 = AdTrackerConstants.EXCEPTION;
            e.printStackTrace();
            return statusCode;
        } catch (IOException e8) {
            IOException iOException = e8;
            statusCode = statusCode2;
            e2 = iOException;
            obtain.arg2 = AdTrackerConstants.EXCEPTION;
            e2.printStackTrace();
            bundle = new Bundle();
            bundle.putString("appId", str);
            obtain.setData(bundle);
            c.sendMessage(obtain);
            return statusCode;
        } catch (JSONException e9) {
            JSONException jSONException = e9;
            statusCode = statusCode2;
            e3 = jSONException;
            obtain.arg2 = AdTrackerConstants.EXCEPTION;
            e3.printStackTrace();
            bundle = new Bundle();
            bundle.putString("appId", str);
            obtain.setData(bundle);
            c.sendMessage(obtain);
            return statusCode;
        }
        return statusCode;
    }

    public static boolean serverReachable(String str) {
        try {
            if (new DefaultHttpClient().execute(new HttpGet(str)).getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Server not reachable..Logging events");
        }
        return false;
    }

    public static void saveWebviewRequestParam(String str, Goal goal) {
        a = formHttpRequest(str, goal.name, goal.count, goal.isDuplicate, null);
    }

    public static String getWebViewRequestParam() {
        return a;
    }
}
