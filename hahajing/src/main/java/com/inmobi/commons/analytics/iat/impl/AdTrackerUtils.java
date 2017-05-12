package com.inmobi.commons.analytics.iat.impl;

import android.content.Context;
import android.content.Intent;
import com.inmobi.commons.analytics.iat.impl.config.AdTrackerEventType;
import com.inmobi.commons.analytics.iat.impl.config.AdTrackerInitializer;
import com.inmobi.commons.analytics.iat.impl.net.AdTrackerNetworkInterface;
import com.inmobi.commons.internal.FileOperations;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.metric.EventLog;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class AdTrackerUtils {
    public static boolean updateStatus() {
        if (InternalSDKUtil.getContext() == null) {
            return false;
        }
        FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.IAT_DOWNLOAD_STATUS, true);
        return true;
    }

    public static boolean resetStatus() {
        if (InternalSDKUtil.getContext() == null) {
            return false;
        }
        FileOperations.setPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.IAT_DOWNLOAD_STATUS, false);
        return true;
    }

    public static boolean isPermissionGranted(String str) {
        try {
            if (InternalSDKUtil.getContext().checkCallingOrSelfPermission(str) == 0) {
                return true;
            }
        } catch (Throwable e) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Cant check permissions", e);
        }
        return false;
    }

    public static boolean sendBroadcastMessage(int i) {
        try {
            Intent intent = new Intent();
            intent.setAction(AdTrackerConstants.TESTMODE_INTENT);
            intent.putExtra(AdTrackerConstants.ADTRACKER_ERROR, i);
            InternalSDKUtil.getContext().sendBroadcast(intent);
            return true;
        } catch (Throwable e) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Cant send test broadcast", e);
            return false;
        }
    }

    public static boolean checkDownloadGoalUploaded() {
        if (InternalSDKUtil.getContext() != null) {
            return FileOperations.getBooleanPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.IAT_DOWNLOAD_STATUS);
        }
        Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Application Context NULL cannot checkStatusUpload");
        return false;
    }

    public static boolean checkDownloadGoalAdded() {
        if (InternalSDKUtil.getContext() != null) {
            return FileOperations.getBooleanPreferences(InternalSDKUtil.getContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.IAT_DOWNLOAD_INSERT_STATUS);
        }
        Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Application Context NULL cannot checkStatusUpload");
        return false;
    }

    public static void reportMetric(AdTrackerEventType adTrackerEventType, Goal goal, int i, long j, int i2, String str) {
        try {
            if (!AdTrackerNetworkInterface.isMetricSample()) {
                return;
            }
            JSONObject jSONObject;
            if (AdTrackerEventType.GOAL_SUCCESS.equals(adTrackerEventType)) {
                jSONObject = new JSONObject();
                jSONObject.put("g", goal.name);
                jSONObject.put("n", goal.retryCount);
                jSONObject.put("t", j);
                jSONObject.put("r", i);
                AdTrackerInitializer.getLogger().logEvent(new EventLog(AdTrackerEventType.GOAL_SUCCESS, jSONObject));
            } else if (AdTrackerEventType.GOAL_FAILURE.equals(adTrackerEventType)) {
                jSONObject = new JSONObject();
                jSONObject.put("g", goal.name);
                jSONObject.put("n", goal.retryCount);
                jSONObject.put("e", i2);
                if (str != null) {
                    jSONObject.put("m", str);
                }
                AdTrackerInitializer.getLogger().logEvent(new EventLog(AdTrackerEventType.GOAL_FAILURE, jSONObject));
            } else {
                jSONObject = new JSONObject();
                jSONObject.put("g", goal.name);
                jSONObject.put("n", goal.retryCount);
                AdTrackerInitializer.getLogger().logEvent(new EventLog(AdTrackerEventType.GOAL_DUMPED, jSONObject));
            }
        } catch (Throwable e) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Error reporting metric", e);
        }
    }

    public static String getReferrerFromLogs() {
        String str;
        Throwable e;
        String[] strArr = new String[]{"logcat", "-d", "ActivityManager:I"};
        try {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Getting referrer from logs");
            Pattern compile = Pattern.compile(AdTrackerInitializer.getConfigParams().getLogcatPattern());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(strArr).getInputStream()));
            str = null;
            while (true) {
                try {
                    CharSequence readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    Matcher matcher = compile.matcher(readLine);
                    if (matcher.find()) {
                        str = matcher.group(1);
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Received referrer from logs: " + str);
        } catch (Throwable e3) {
            Throwable th = e3;
            str = null;
            e = th;
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Getting referrer from logs failed", e);
            return str;
        }
        return str;
    }

    public static void setInternalReferrer(Context context, String str) {
        if (context == null || str == null) {
            try {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Cannot set Market Referrer..Referrer NULL");
                return;
            } catch (Throwable e) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Cannot set referrer", e);
                return;
            }
        }
        if (0 == FileOperations.getLongPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_T2)) {
            FileOperations.setPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_T2, System.currentTimeMillis());
        }
        Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Saving referrer from broadcast receiver: " + str);
        if (FileOperations.getPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER) != null) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Install Receiver already set. Download Goal queued");
            return;
        }
        FileOperations.setPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER, str);
        FileOperations.setPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_REFERRER_FROM_LOGCAT, "0");
        String preferences = FileOperations.getPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, "mk-siteid");
        if (preferences == null || "".equals(preferences.trim())) {
            Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Initialization incomplete. Please call InMobi initialize with a valid app Id");
        } else if (FileOperations.getBooleanPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.WAIT_FOR_REFERRER)) {
            InternalSDKUtil.setContext(context);
            AdTrackerNetworkInterface.init();
            AdTrackerNetworkInterface.getGoalList().addGoal(AdTrackerConstants.GOAL_DOWNLOAD, 1, 0, 0, true);
            AdTrackerNetworkInterface.reportToServer(preferences);
        } else {
            AdTrackerNetworkInterface.onReceiveReferrer(preferences);
        }
    }

    public static void setReferrerFromLogs(Context context, String str) {
        if (context == null || str == null) {
            try {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Cannot set Market Referrer from logs..Referrer NULL");
                return;
            } catch (Throwable e) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Cannot set referrer from logs", e);
                return;
            }
        }
        if (0 == FileOperations.getLongPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_T2)) {
            FileOperations.setPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_T2, System.currentTimeMillis());
        }
        FileOperations.setPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.REFERRER, str);
        FileOperations.setPreferences(context.getApplicationContext(), AdTrackerConstants.IMPREF_FILE, AdTrackerConstants.KEY_REFERRER_FROM_LOGCAT, "1");
    }
}
