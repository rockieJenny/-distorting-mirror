package com.inmobi.commons.analytics.iat.impl.net;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.analytics.iat.impl.AdTrackerUtils;
import com.inmobi.commons.analytics.iat.impl.Goal;
import com.inmobi.commons.analytics.iat.impl.Goal.State;
import com.inmobi.commons.analytics.iat.impl.GoalList;
import com.inmobi.commons.analytics.iat.impl.config.AdTrackerEventType;
import com.inmobi.commons.analytics.iat.impl.config.AdTrackerInitializer;
import com.inmobi.commons.internal.FileOperations;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdTrackerNetworkInterface {
    private static GoalList a;
    private static AdTrackerWebViewLoader b;
    private static Handler c;
    private static HandlerThread d;
    private static AtomicBoolean e;
    private static int f = 0;
    private static boolean g = false;
    private static String h = "https://d.appsdt.com/download/tracker/?";
    private static String i = "https://d.appsdt.com/sdkdwnldbeacon.html";
    private static String j = "https://d.appsdt.com/download/tracker/iatsdkconfs?";
    private static Handler k;

    static final class a extends Handler {
        static final int a = AdTrackerInitializer.getConfigParams().getReferrerWaitTimeRetryInterval();
        static final int b = AdTrackerInitializer.getConfigParams().getReferrerWaitTime();
        static final int c = AdTrackerInitializer.getConfigParams().getWebviewTimeout();

        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            Message obtain;
            int referrerWaitTimeRetryCount;
            long longPreferences;
            Goal goal;
            String str;
            Message obtain2;
            Goal goal2;
            switch (message.what) {
                case 2:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Trying to fetch referrer ...");
                    ((Goal) AdTrackerNetworkInterface.a.get(message.arg1)).state = State.REFERRER_REQUESTED;
                    if (AdTrackerNetworkInterface.p()) {
                        obtain = Message.obtain();
                        obtain.what = 4;
                        obtain.arg1 = message.arg1;
                        obtain.obj = (String) message.obj;
                        sendMessage(obtain);
                        return;
                    } else if (!AdTrackerNetworkInterface.o()) {
                        referrerWaitTimeRetryCount = AdTrackerInitializer.getConfigParams().getReferrerWaitTimeRetryCount() + 1;
                        int i = message.arg2 - 1;
                        message.arg2 = i;
                        if (i == 0) {
                            Object obj = (AdTrackerNetworkInterface.n() || AdTrackerNetworkInterface.p()) ? 1 : null;
                            if (obj != null) {
                                obtain = Message.obtain();
                                obtain.what = 4;
                                obtain.arg1 = message.arg1;
                                obtain.obj = (String) message.obj;
                                sendMessage(obtain);
                                return;
                            }
                            obtain = Message.obtain();
                            obtain.what = 3;
                            obtain.arg1 = message.arg1;
                            obtain.arg2 = b;
                            obtain.obj = (String) message.obj;
                            sendMessageDelayed(obtain, (long) b);
                            return;
                        }
                        Message obtain3 = Message.obtain();
                        obtain3.what = 2;
                        obtain3.arg1 = message.arg1;
                        obtain3.arg2 = i;
                        obtain3.obj = (String) message.obj;
                        sendMessageDelayed(obtain3, (long) ((referrerWaitTimeRetryCount - i) * a));
                        return;
                    } else if (AdTrackerNetworkInterface.n()) {
                        obtain = Message.obtain();
                        obtain.what = 4;
                        obtain.arg1 = message.arg1;
                        obtain.obj = (String) message.obj;
                        sendMessage(obtain);
                        return;
                    } else {
                        long j;
                        longPreferences = FileOperations.getLongPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_REF_WAIT);
                        Message obtain4 = Message.obtain();
                        obtain4.what = 3;
                        obtain4.arg1 = message.arg1;
                        if (longPreferences < 0) {
                            j = (long) b;
                        } else {
                            j = longPreferences;
                        }
                        obtain4.arg2 = (int) j;
                        obtain4.obj = (String) message.obj;
                        sendMessageDelayed(obtain4, longPreferences);
                        return;
                    }
                case 3:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Referrer wait timed out. MESSAGE_GET_REFERRER received ...");
                    if (AdTrackerNetworkInterface.n()) {
                        obtain = Message.obtain();
                        obtain.what = 4;
                        obtain.arg1 = message.arg1;
                        obtain.obj = (String) message.obj;
                        sendMessage(obtain);
                        return;
                    }
                    obtain = Message.obtain();
                    obtain.what = 5;
                    obtain.arg1 = message.arg1;
                    obtain.obj = (String) message.obj;
                    sendMessage(obtain);
                    return;
                case 4:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "GET_REFFERRER_SUCCEEDED message received");
                    FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.WAIT_FOR_REFERRER, true);
                    ((Goal) AdTrackerNetworkInterface.a.get(message.arg1)).state = State.REFERRER_ACQUIRED;
                    obtain = Message.obtain();
                    obtain.arg1 = message.arg1;
                    obtain.obj = (String) message.obj;
                    if (AdTrackerNetworkInterface.m()) {
                        obtain.what = 6;
                    } else {
                        obtain.what = 7;
                    }
                    sendMessage(obtain);
                    return;
                case 5:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Getting referrer timed out...");
                    String referrerFromLogs = AdTrackerUtils.getReferrerFromLogs();
                    if (referrerFromLogs != null) {
                        Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Saving referrer from logs: " + referrerFromLogs);
                        AdTrackerUtils.setReferrerFromLogs(InternalSDKUtil.getContext(), referrerFromLogs);
                    }
                    obtain = Message.obtain();
                    obtain.what = 4;
                    obtain.arg1 = message.arg1;
                    obtain.obj = (String) message.obj;
                    sendMessage(obtain);
                    return;
                case 6:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Reporting Goal via network ...");
                    goal = (Goal) AdTrackerNetworkInterface.a.get(message.arg1);
                    str = (String) message.obj;
                    goal.state = State.REPORTING_REQUESTED;
                    AdTrackerRequestResponseBuilder.reportGoalOverHttp(str, goal, FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.VALIDIDS));
                    return;
                case 7:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Reporting Goal via webview");
                    goal = (Goal) AdTrackerNetworkInterface.a.get(message.arg1);
                    goal.state = State.REPORTING_REQUESTED;
                    str = (String) message.obj;
                    AdTrackerNetworkInterface.b = new AdTrackerWebViewLoader();
                    AdTrackerRequestResponseBuilder.saveWebviewRequestParam(str, goal);
                    AdTrackerNetworkInterface.b.loadWebview(str, goal);
                    obtain2 = Message.obtain();
                    obtain2.what = 9;
                    obtain2.arg1 = message.arg1;
                    obtain2.obj = str;
                    sendMessageDelayed(obtain2, (long) c);
                    return;
                case 8:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Reporting Goal succeeded...");
                    goal2 = (Goal) message.obj;
                    if (State.REPORTING_REQUESTED == goal2.state) {
                        goal2.state = State.REPORTING_COMPLETED;
                        AdTrackerUtils.reportMetric(AdTrackerEventType.GOAL_SUCCESS, goal2, 1, (long) message.arg2, 0, null);
                        FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_FIRST_GOAL_SUCCESS, true);
                        if (AdTrackerConstants.GOAL_DOWNLOAD.equals(goal2.name)) {
                            AdTrackerUtils.updateStatus();
                        }
                        String string = message.getData().getString("appId");
                        Iterator it = AdTrackerNetworkInterface.a.iterator();
                        referrerWaitTimeRetryCount = 0;
                        while (it.hasNext() && !((Goal) it.next()).equals(goal2)) {
                            referrerWaitTimeRetryCount++;
                        }
                        Message obtain5 = Message.obtain();
                        if (referrerWaitTimeRetryCount == AdTrackerNetworkInterface.a.size() - 1) {
                            obtain5.what = 11;
                            obtain5.arg1 = referrerWaitTimeRetryCount;
                            obtain5.obj = string;
                        } else {
                            obtain5.what = 1;
                            obtain5.arg1 = referrerWaitTimeRetryCount + 1;
                            obtain5.obj = string;
                            ((Goal) AdTrackerNetworkInterface.a.get(obtain5.arg1)).state = State.ENQUEUE_REQUESTED;
                        }
                        AdTrackerNetworkInterface.f = 0;
                        sendMessage(obtain5);
                        return;
                    }
                    return;
                case 9:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Reporting message goal timed out...");
                    goal2 = (Goal) AdTrackerNetworkInterface.a.get(message.arg1);
                    goal2.state = State.REPORTING_TIMED_OUT;
                    AdTrackerUtils.reportMetric(AdTrackerEventType.GOAL_FAILURE, goal2, 0, 0, AdTrackerConstants.NETWORK_TIMEOUT, null);
                    AdTrackerNetworkInterface.b.deinit(c);
                    AdTrackerNetworkInterface.a.increaseRetryTime(goal2.name, goal2.count, goal2.isDuplicate);
                    obtain = Message.obtain();
                    obtain.what = 1;
                    obtain.arg1 = message.arg1;
                    obtain.obj = (String) message.obj;
                    sendMessage(obtain);
                    return;
                case 10:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Message report goal failed...");
                    goal2 = (Goal) message.obj;
                    if (State.REPORTING_REQUESTED == goal2.state) {
                        goal2.state = State.REPORTING_FAILED;
                        Iterator it2 = AdTrackerNetworkInterface.a.iterator();
                        referrerWaitTimeRetryCount = 0;
                        while (it2.hasNext() && !((Goal) it2.next()).equals(goal2)) {
                            referrerWaitTimeRetryCount++;
                        }
                        obtain2 = Message.obtain();
                        obtain2.arg1 = referrerWaitTimeRetryCount;
                        obtain2.obj = message.getData().getString("appId");
                        if (6001 == message.arg2) {
                            obtain2.what = 7;
                        } else {
                            AdTrackerUtils.reportMetric(AdTrackerEventType.GOAL_FAILURE, goal2, 1, 0, message.arg2, null);
                            AdTrackerNetworkInterface.a.increaseRetryTime(goal2.name, goal2.count, goal2.isDuplicate);
                            if (goal2.retryTime > 0) {
                                Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Retrying goalname: " + goal2 + " after " + (goal2.retryTime / 1000) + " second");
                            }
                            obtain2.what = 1;
                        }
                        sendMessage(obtain2);
                        return;
                    }
                    return;
                case 11:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "No more goals to report ...");
                    AdTrackerNetworkInterface.a.clear();
                    AdTrackerNetworkInterface.a.saveGoals();
                    AdTrackerNetworkInterface.c.sendEmptyMessage(1);
                    return;
                case 12:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Message report goal aborted...");
                    AdTrackerNetworkInterface.f = 1;
                    if (!(AdTrackerNetworkInterface.a == null || AdTrackerNetworkInterface.a.isEmpty())) {
                        Iterator it3 = AdTrackerNetworkInterface.a.iterator();
                        while (it3.hasNext()) {
                            if (State.REPORTING_COMPLETED == ((Goal) it3.next()).state) {
                                it3.remove();
                            }
                        }
                        AdTrackerNetworkInterface.a.saveGoals();
                    }
                    AdTrackerNetworkInterface.a = null;
                    AdTrackerNetworkInterface.c.sendEmptyMessage(1);
                    return;
                default:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Enqueuing message goal ...");
                    goal = (Goal) AdTrackerNetworkInterface.a.get(message.arg1);
                    if (InternalSDKUtil.checkNetworkAvailibility(InternalSDKUtil.getContext())) {
                        boolean z;
                        int referrerWaitTimeRetryCount2;
                        AdTrackerNetworkInterface.a.saveGoals();
                        AdTrackerNetworkInterface.g = AdTrackerInitializer.getLogger().startNewSample();
                        if (State.ENQUEUE_REQUESTED == goal.state) {
                            if (message.arg1 == 0) {
                                z = true;
                            } else {
                                if (State.REPORTING_COMPLETED == ((Goal) AdTrackerNetworkInterface.a.get(message.arg1 - 1)).state) {
                                    z = true;
                                }
                            }
                            goal.state = State.ENQUEUE_SUCCEEDED;
                            Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Goal " + goal.name + " enqueued successfully for reporting");
                            longPreferences = AdTrackerNetworkInterface.b(goal, z);
                            referrerWaitTimeRetryCount2 = AdTrackerInitializer.getConfigParams().getReferrerWaitTimeRetryCount() + 1;
                            obtain = Message.obtain();
                            obtain.what = 2;
                            obtain.arg1 = message.arg1;
                            obtain.arg2 = referrerWaitTimeRetryCount2;
                            obtain.obj = (String) message.obj;
                            sendMessageDelayed(obtain, longPreferences);
                            return;
                        }
                        z = false;
                        goal.state = State.ENQUEUE_SUCCEEDED;
                        Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Goal " + goal.name + " enqueued successfully for reporting");
                        longPreferences = AdTrackerNetworkInterface.b(goal, z);
                        referrerWaitTimeRetryCount2 = AdTrackerInitializer.getConfigParams().getReferrerWaitTimeRetryCount() + 1;
                        obtain = Message.obtain();
                        obtain.what = 2;
                        obtain.arg1 = message.arg1;
                        obtain.arg2 = referrerWaitTimeRetryCount2;
                        obtain.obj = (String) message.obj;
                        sendMessageDelayed(obtain, longPreferences);
                        return;
                    }
                    goal.state = State.ENQUEUE_PENDING;
                    Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Network Unavailable. Aborting attempt to report goal ...");
                    sendEmptyMessage(12);
                    return;
            }
        }
    }

    static final class b extends Handler {
        b() {
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "All goals reported ... De-initializing AdTrackerNetworkInterface!");
                    if (AdTrackerNetworkInterface.d != null) {
                        AdTrackerNetworkInterface.e.set(false);
                        AdTrackerNetworkInterface.d.quit();
                        Thread d = AdTrackerNetworkInterface.d;
                        AdTrackerNetworkInterface.d = null;
                        d.interrupt();
                        AdTrackerNetworkInterface.k = null;
                        return;
                    }
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    static String a() {
        return h;
    }

    static String b() {
        return i;
    }

    public static int isUnstableNetwork() {
        return f;
    }

    public static boolean isMetricSample() {
        return g;
    }

    public static Handler getUIHandler() {
        return c;
    }

    public static GoalList getGoalList() {
        return a;
    }

    public static void init() {
        if (e == null) {
            e = new AtomicBoolean(false);
        }
        if (a == null) {
            a = GoalList.getLoggedGoals();
        }
        if (c == null) {
            c = new b();
        }
        if (d == null) {
            d = new HandlerThread("AdTrackerNetworkHandler");
            d.start();
        }
        if (k == null) {
            k = new a(d.getLooper());
        }
    }

    public static synchronized void reportToServer(String str) {
        synchronized (AdTrackerNetworkInterface.class) {
            if (a == null || a.isEmpty()) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "No goals to report");
            } else if (e.compareAndSet(false, true)) {
                k.removeMessages(11);
                Iterator it = a.iterator();
                while (it.hasNext()) {
                    if (State.REPORTING_COMPLETED == ((Goal) it.next()).state) {
                        it.remove();
                    }
                }
                it = a.iterator();
                while (it.hasNext()) {
                    ((Goal) it.next()).state = State.ENQUEUE_PENDING;
                }
                a.saveGoals();
                ((Goal) a.get(0)).state = State.ENQUEUE_REQUESTED;
                Message obtain = Message.obtain();
                obtain.what = 1;
                obtain.arg1 = 0;
                obtain.obj = str;
                k.sendMessage(obtain);
            }
        }
    }

    private static long b(Goal goal, boolean z) {
        String str = goal.name;
        long j = goal.retryTime;
        int maxWaitTime = AdTrackerInitializer.getConfigParams().getRetryParams().getMaxWaitTime();
        if (true == z) {
            return 0;
        }
        if (j > ((long) maxWaitTime)) {
            return (long) maxWaitTime;
        }
        return j;
    }

    private static boolean m() {
        String preferences = FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.TIMETOLIVE);
        String preferences2 = FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.VALIDIDS);
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.getTime().toString();
        if (preferences == null || preferences2 == null) {
            return false;
        }
        Date time = instance.getTime();
        instance.add(11, Integer.parseInt(preferences));
        if (instance.getTime().after(time)) {
            return true;
        }
        return false;
    }

    private static boolean n() {
        if (FileOperations.getPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER) != null) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean o() {
        /*
        r1 = 1;
        r0 = 0;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = j;
        r2 = r2.append(r3);
        r3 = "t=";
        r2 = r2.append(r3);
        r4 = java.lang.System.currentTimeMillis();
        r2 = r2.append(r4);
        r2 = r2.toString();
        r3 = "[InMobi]-[AdTracker]-4.5.3";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Fetch referrer wait time URL: ";
        r4 = r4.append(r5);
        r4 = r4.append(r2);
        r4 = r4.toString();
        com.inmobi.commons.internal.Log.internal(r3, r4);
        r3 = new org.apache.http.impl.client.DefaultHttpClient;
        r3.<init>();
        r4 = new org.apache.http.client.methods.HttpGet;
        r4.<init>(r2);
        r2 = r3.execute(r4);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r4 = r2.getStatusLine();	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r4 = r4.getStatusCode();	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        if (r3 == r4) goto L_0x0052;
    L_0x0051:
        return r0;
    L_0x0052:
        r2 = r2.getEntity();	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r2 = org.apache.http.util.EntityUtils.toString(r2);	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r3 = "[InMobi]-[AdTracker]-4.5.3";
        r4 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r4.<init>();	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r5 = "Wait time received for referrer: ";
        r4 = r4.append(r5);	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r4 = r4.append(r2);	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r4 = r4.toString();	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        com.inmobi.commons.internal.Log.internal(r3, r4);	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r3 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r3.<init>(r2);	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r2 = "referrerWaitTime";
        r2 = r3.getLong(r2);	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = r2 * r4;
        r4 = com.inmobi.commons.internal.InternalSDKUtil.getContext();	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r5 = "IMAdTrackerStatusUpload";
        r6 = "referrerWaitTime";
        com.inmobi.commons.internal.FileOperations.setPreferences(r4, r5, r6, r2);	 Catch:{ JSONException -> 0x008d, ParseException -> 0x00ac, IOException -> 0x00cb, ClientProtocolException -> 0x00d5 }
        r0 = r1;
        goto L_0x0051;
    L_0x008d:
        r2 = move-exception;
        r3 = com.inmobi.commons.analytics.iat.impl.net.AdTrackerNetworkInterface.a.b;	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r4 = (long) r3;	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r3 = com.inmobi.commons.internal.InternalSDKUtil.getContext();	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r6 = "IMAdTrackerStatusUpload";
        r7 = "referrerWaitTime";
        com.inmobi.commons.internal.FileOperations.setPreferences(r3, r6, r7, r4);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r3 = "[InMobi]-[AdTracker]-4.5.3";
        r4 = "Error fetching wait time for referrer";
        com.inmobi.commons.internal.Log.internal(r3, r4, r2);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r2 = "[InMobi]-[AdTracker]-4.5.3";
        r3 = "Setting default wait time...";
        com.inmobi.commons.internal.Log.internal(r2, r3);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r0 = r1;
        goto L_0x0051;
    L_0x00ac:
        r2 = move-exception;
        r3 = com.inmobi.commons.analytics.iat.impl.net.AdTrackerNetworkInterface.a.b;	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r4 = (long) r3;	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r3 = com.inmobi.commons.internal.InternalSDKUtil.getContext();	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r6 = "IMAdTrackerStatusUpload";
        r7 = "referrerWaitTime";
        com.inmobi.commons.internal.FileOperations.setPreferences(r3, r6, r7, r4);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r3 = "[InMobi]-[AdTracker]-4.5.3";
        r4 = "Error fetching wait time for referrer";
        com.inmobi.commons.internal.Log.internal(r3, r4, r2);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r2 = "[InMobi]-[AdTracker]-4.5.3";
        r3 = "Setting default wait time...";
        com.inmobi.commons.internal.Log.internal(r2, r3);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        r0 = r1;
        goto L_0x0051;
    L_0x00cb:
        r1 = move-exception;
        r2 = "[InMobi]-[AdTracker]-4.5.3";
        r3 = "Error fetching wait time for referrer";
        com.inmobi.commons.internal.Log.internal(r2, r3, r1);	 Catch:{ ClientProtocolException -> 0x00d5, IOException -> 0x00df }
        goto L_0x0051;
    L_0x00d5:
        r1 = move-exception;
        r2 = "[InMobi]-[AdTracker]-4.5.3";
        r3 = "Error fetching wait time for referrer";
        com.inmobi.commons.internal.Log.internal(r2, r3, r1);
        goto L_0x0051;
    L_0x00df:
        r1 = move-exception;
        r2 = "[InMobi]-[AdTracker]-4.5.3";
        r3 = "Error fetching wait time for referrer";
        com.inmobi.commons.internal.Log.internal(r2, r3, r1);
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.inmobi.commons.analytics.iat.impl.net.AdTrackerNetworkInterface.o():boolean");
    }

    private static boolean p() {
        return FileOperations.getBooleanPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.WAIT_FOR_REFERRER);
    }

    static final Handler c() {
        return k;
    }

    public static void onReceiveReferrer(String str) {
        Message obtain = Message.obtain();
        if (c() != null && c().hasMessages(3)) {
            Message obtainMessage = c().obtainMessage(3);
            obtain.what = 4;
            obtain.arg1 = obtainMessage.arg1;
            obtain.obj = str;
            c().removeMessages(3);
            c().sendMessage(obtain);
        }
    }
}
