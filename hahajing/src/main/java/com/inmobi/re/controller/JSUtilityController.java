package com.inmobi.re.controller;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Parcelable;
import android.os.Vibrator;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import com.google.analytics.tracking.android.HitTypes;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.androidsdk.IMBrowserActivity;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.internal.ApiStatCollector;
import com.inmobi.commons.internal.ApiStatCollector.ApiEventType;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.metric.EventLog;
import com.inmobi.re.configs.Initializer;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.container.mraidimpl.AudioTriggerCallback;
import com.inmobi.re.container.mraidimpl.AudioTriggerer;
import com.inmobi.re.controller.JSController.Dimensions;
import com.inmobi.re.controller.util.Constants;
import com.inmobi.re.controller.util.ImageProcessing;
import com.inmobi.re.controller.util.StartActivityForResultCallback;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class JSUtilityController extends JSController {
    public static SimpleDateFormat[] calendarUntiFormats = new SimpleDateFormat[]{new SimpleDateFormat("yyyyMMdd'T'HHmmssZ"), new SimpleDateFormat("yyyyMMdd'T'HHmm"), new SimpleDateFormat("yyyyMMdd")};
    public static SimpleDateFormat[] formats = new SimpleDateFormat[]{new SimpleDateFormat("yyyy-MM-dd'T'hh:mmZ", Locale.ENGLISH), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz", Locale.ENGLISH), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH), new SimpleDateFormat("yyyyMMddHHmmssZ", Locale.ENGLISH), new SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH), new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH), new SimpleDateFormat("yyyyMM", Locale.ENGLISH), new SimpleDateFormat("yyyy", Locale.ENGLISH)};
    DownloadManager a = null;
    Object b;
    int c = 0;
    private JSDisplayController d;
    private boolean e = false;
    private Map<String, Boolean> f = new HashMap();
    private AudioTriggerCallback g = new AudioTriggerCallback(this) {
        final /* synthetic */ JSUtilityController a;

        {
            this.a = r1;
        }

        public void audioLevel(double d) {
            this.a.imWebView.raiseMicEvent(d);
        }
    };
    private boolean h = false;
    private boolean i = false;

    public JSUtilityController(IMWebView iMWebView, Context context) {
        super(iMWebView, context);
        this.d = new JSDisplayController(iMWebView, context);
        iMWebView.addJavascriptInterface(this.d, "displayController");
    }

    public void setWebViewClosed(boolean z) {
        this.e = z;
    }

    @SuppressLint({"NewApi"})
    public void registerBroadcastListener() {
        c();
        if (this.b == null && VERSION.SDK_INT > 8) {
            try {
                if (this.a == null) {
                    this.a = (DownloadManager) this.imWebView.getActivity().getSystemService(AdTrackerConstants.GOAL_DOWNLOAD);
                }
                this.b = new BroadcastReceiver(this) {
                    final /* synthetic */ JSUtilityController a;

                    {
                        this.a = r1;
                    }

                    public void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.DOWNLOAD_COMPLETE".equals(intent.getAction())) {
                            long longExtra = intent.getLongExtra("extra_download_id", 0);
                            Query query = new Query();
                            query.setFilterById(new long[]{longExtra});
                            Cursor query2 = this.a.a.query(query);
                            if (query2.moveToFirst()) {
                                int columnIndex = query2.getColumnIndex("status");
                                if (16 == query2.getInt(columnIndex)) {
                                    this.a.imWebView.raiseError("download failed", "storePicture");
                                } else if (8 != query2.getInt(columnIndex)) {
                                }
                            }
                        }
                    }
                };
                if (this.e) {
                    this.b = null;
                } else {
                    this.imWebView.getActivity().registerReceiver((BroadcastReceiver) this.b, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
                }
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "JSUtilityController-> registerBroadcastListener. Unable to register download listener", e);
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void unRegisterBroadcastListener() {
        try {
            d();
            if (VERSION.SDK_INT > 8) {
                this.imWebView.getActivity().unregisterReceiver((BroadcastReceiver) this.b);
                this.b = null;
            }
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "JSUtilityController-> unregisterBroadcastListener. Unable to unregister download listener");
        }
    }

    @JavascriptInterface
    public void sendSMS(String str, String str2) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(27), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> sendSMS: recipient: " + str + " body: " + str2);
        try {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + Uri.encode(str)));
            intent.putExtra("sms_body", str2);
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            this.imWebView.getActivity().startActivity(intent);
            this.imWebView.fireOnLeaveApplication();
        } catch (Throwable e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Exception in sending SMS ", e);
            this.imWebView.raiseError("Exception in sending SMS", "sendSMS");
        }
    }

    @JavascriptInterface
    public void sendMail(String str, String str2, String str3) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(28), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> sendMail: recipient: " + str + " subject: " + str2 + " body: " + str3);
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("plain/text");
            intent.putExtra("android.intent.extra.EMAIL", new String[]{str});
            intent.putExtra("android.intent.extra.SUBJECT", str2);
            intent.putExtra("android.intent.extra.TEXT", str3);
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            this.imWebView.getActivity().startActivity(Intent.createChooser(intent, "Choose the Email Client."));
            this.imWebView.fireOnLeaveApplication();
        } catch (Throwable e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Exception in sending mail ", e);
            this.imWebView.raiseError("Exception in sending mail", "sendMail");
        }
    }

    private String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("tel:")) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder("tel:");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    @JavascriptInterface
    public void makeCall(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(29), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> makeCall: number: " + str);
        try {
            String b = b(str);
            if (b == null) {
                this.imWebView.raiseError("Bad Phone Number", "makeCall");
                return;
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(b.toString()));
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            this.imWebView.getActivity().startActivity(intent);
            this.imWebView.fireOnLeaveApplication();
        } catch (Throwable e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Exception in making call ", e);
            this.imWebView.raiseError("Exception in making call", "makeCall");
        }
    }

    private int a() {
        Cursor query;
        String[] strArr = new String[]{AnalyticsSQLiteHelper.GENERAL_ID, "title"};
        if (VERSION.SDK_INT >= 8) {
            query = this.imWebView.getActivity().getContentResolver().query(Uri.parse("content://com.android.calendar/events"), strArr, null, null, null);
        } else {
            query = this.imWebView.getActivity().getContentResolver().query(Uri.parse("content://calendar/events"), strArr, null, null, null);
        }
        if (query != null && query.moveToLast()) {
            int columnIndex = query.getColumnIndex("title");
            int columnIndex2 = query.getColumnIndex(AnalyticsSQLiteHelper.GENERAL_ID);
            String string = query.getString(columnIndex);
            String string2 = query.getString(columnIndex2);
            if (string != null) {
                return Integer.parseInt(string2);
            }
        }
        return 0;
    }

    boolean a(String str) {
        PackageManager packageManager = this.imWebView.getActivity().getPackageManager();
        return packageManager.checkPermission(str, packageManager.getNameForUid(Binder.getCallingUid())) == 0;
    }

    @JavascriptInterface
    public void createCalendarEvent(String str, final String str2, String str3, String str4, String str5, String str6, final String str7, String str8, String str9, String str10) {
        try {
            if (a("android.permission.READ_CALENDAR") && a("android.permission.WRITE_CALENDAR")) {
                this.c = a();
            }
            ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(30), null));
            Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> createEvent: date: " + str2 + " location: " + str4 + " body: " + str5);
            if (supports(Event.INTENT_CALENDAR_EVENT)) {
                Calendar convertDateString = convertDateString(str2);
                Calendar convertDateString2 = convertDateString(str3);
                if (convertDateString == null || convertDateString2 == null) {
                    Log.debug(Constants.RENDERING_LOG_TAG, HitTypes.EXCEPTION);
                    this.imWebView.raiseError("Date format is incorrect", "createCalendarEvent");
                    return;
                }
                Intent intent = new Intent(this.imWebView.getActivity(), IMBrowserActivity.class);
                intent.putExtra(IMBrowserActivity.EXTRA_BROWSER_ACTIVITY_TYPE, 100);
                final String str11 = str10;
                intent.putExtra(AnalyticsEvent.EVENT_ID, IMBrowserActivity.generateId(new StartActivityForResultCallback(this) {
                    final /* synthetic */ JSUtilityController d;

                    public void onActivityResult(int i, Intent intent) {
                        try {
                            if (this.d.a("android.permission.READ_CALENDAR") && this.d.a("android.permission.WRITE_CALENDAR")) {
                                if (this.d.c == this.d.a()) {
                                    Log.internal(Constants.RENDERING_LOG_TAG, "User cancelled the create calendar event");
                                    return;
                                }
                                Uri parse;
                                if (VERSION.SDK_INT >= 8) {
                                    parse = Uri.parse("content://com.android.calendar/events");
                                } else {
                                    parse = Uri.parse("content://calendar/events");
                                }
                                ContentResolver contentResolver = this.d.imWebView.getActivity().getContentResolver();
                                ContentValues contentValues = new ContentValues();
                                if (str7.equals("tentative")) {
                                    contentValues.put("eventStatus", Integer.valueOf(0));
                                } else if (str7.equals("confirmed")) {
                                    contentValues.put("eventStatus", Integer.valueOf(1));
                                } else if (str7.equals("cancelled")) {
                                    contentValues.put("eventStatus", Integer.valueOf(2));
                                }
                                contentResolver.update(ContentUris.withAppendedId(parse, (long) this.d.a()), contentValues, null, null);
                                if (str11 != null && !"".equals(str11)) {
                                    int parseInt;
                                    try {
                                        parseInt = Integer.parseInt(str11) / 60000;
                                    } catch (NumberFormatException e) {
                                        try {
                                            parseInt = ((int) (JSUtilityController.convertDateString(str11).getTimeInMillis() - JSUtilityController.convertDateString(str2).getTimeInMillis())) / 60000;
                                        } catch (Exception e2) {
                                            this.d.imWebView.raiseError("Reminder format is incorrect. Invalid date", "createCalendarEvent");
                                            return;
                                        }
                                    }
                                    if (parseInt > 0) {
                                        this.d.imWebView.raiseError("Reminder format is incorrect. Reminder can be set only before the event starts", "createCalendarEvent");
                                        return;
                                    }
                                    Uri parse2;
                                    int i2 = -parseInt;
                                    contentValues = new ContentValues();
                                    contentValues.put("hasAlarm", Integer.valueOf(1));
                                    contentResolver.update(ContentUris.withAppendedId(parse, (long) this.d.a()), contentValues, null, null);
                                    if (VERSION.SDK_INT >= 8) {
                                        parse2 = Uri.parse("content://com.android.calendar/reminders");
                                    } else {
                                        parse2 = Uri.parse("content://calendar/reminders");
                                    }
                                    ContentValues contentValues2 = new ContentValues();
                                    contentValues2.put("event_id", Integer.valueOf(this.d.a()));
                                    contentValues2.put("method", Integer.valueOf(1));
                                    contentValues2.put("minutes", Integer.valueOf(i2));
                                    contentResolver.insert(parse2, contentValues2);
                                }
                            }
                        } catch (Throwable e3) {
                            e3.printStackTrace();
                            Log.internal(Constants.RENDERING_LOG_TAG, "Exception adding reminder", e3);
                        }
                    }
                }));
                intent.putExtra("eventId", str);
                intent.putExtra("action", "createCalendarEvent");
                intent.putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, str5);
                intent.putExtra("summary", str6);
                intent.putExtra("location", str4);
                intent.putExtra("start", convertDateString.getTimeInMillis());
                intent.putExtra("end", convertDateString2.getTimeInMillis());
                intent.putExtra("status", str7);
                intent.putExtra("transparency", str8);
                intent.putExtra("recurrence", str9);
                if (!(str10 == null || "".equals(str10))) {
                    intent.putExtra("hasAlarm", true);
                }
                this.imWebView.getActivity().startActivity(intent);
                if (this.imWebView.mListener != null) {
                    this.imWebView.mListener.onLeaveApplication();
                    return;
                }
                return;
            }
            Log.internal(Constants.RENDERING_LOG_TAG, "createCalendarEvent called even if it is not supported");
            this.imWebView.raiseError("createCalendarEvent called even if it is not supported", "createCalendarEvent");
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Error creating reminder event", e);
        }
    }

    public static GregorianCalendar convertDateString(String str) {
        SimpleDateFormat[] simpleDateFormatArr = formats;
        int length = simpleDateFormatArr.length;
        int i = 0;
        while (i < length) {
            try {
                Date parse = simpleDateFormatArr[i].parse(str);
                Calendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(parse);
                return (GregorianCalendar) gregorianCalendar;
            } catch (Exception e) {
                i++;
            }
        }
        return null;
    }

    public void stopAllListeners() {
        try {
            this.d.stopAllListeners();
        } catch (Exception e) {
        }
    }

    @JavascriptInterface
    public void showAlert(String str) {
        Log.debug(Constants.RENDERING_LOG_TAG, str);
    }

    @JavascriptInterface
    public void log(String str) {
        Log.debug(Constants.RENDERING_LOG_TAG, "Ad Log Message: " + str);
    }

    @JavascriptInterface
    public void asyncPing(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(31), null));
        try {
            Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> asyncPing: url: " + str);
            if (URLUtil.isValidUrl(str)) {
                c(str);
            } else {
                this.imWebView.raiseError("Invalid url", "asyncPing");
            }
        } catch (Exception e) {
        }
    }

    private void c(final String str) {
        new Thread(this) {
            final /* synthetic */ JSUtilityController b;

            public void run() {
                Throwable th;
                Throwable th2;
                HttpURLConnection httpURLConnection = null;
                try {
                    String replaceAll = str.replaceAll("%25", "%");
                    Log.debug(Constants.RENDERING_LOG_TAG, "Pinging URL: " + replaceAll);
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(replaceAll).openConnection();
                    try {
                        httpURLConnection2.setConnectTimeout(20000);
                        httpURLConnection2.setRequestMethod(HttpRequest.METHOD_GET);
                        httpURLConnection2.setRequestProperty("User-Agent", InternalSDKUtil.getUserAgent());
                        Log.debug(Constants.RENDERING_LOG_TAG, "Async Ping Connection Response Code: " + httpURLConnection2.getResponseCode());
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                    } catch (Throwable e) {
                        th = e;
                        httpURLConnection = httpURLConnection2;
                        th2 = th;
                        try {
                            Log.debug(Constants.RENDERING_LOG_TAG, "Error doing async Ping. ", th2);
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                        } catch (Throwable th3) {
                            th2 = th3;
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            throw th2;
                        }
                    } catch (Throwable e2) {
                        th = e2;
                        httpURLConnection = httpURLConnection2;
                        th2 = th;
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        throw th2;
                    }
                } catch (Exception e3) {
                    th2 = e3;
                    Log.debug(Constants.RENDERING_LOG_TAG, "Error doing async Ping. ", th2);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
        }.start();
    }

    @JavascriptInterface
    public void playAudio(String str, boolean z, boolean z2, boolean z3, String str2, String str3, String str4) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(32), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "playAudio: url: " + str + " autoPlay: " + z + " controls: " + z2 + " loop: " + z3 + " startStyle: " + str2 + " stopStyle: " + str3 + " id:" + str4);
        this.imWebView.playAudio(str, z, z2, z3, str2, str3, str4);
    }

    @JavascriptInterface
    public void muteAudio(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(33), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> muteAudio: ");
        this.imWebView.muteAudio(str);
    }

    @JavascriptInterface
    public void unMuteAudio(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(34), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> unMuteAudio: ");
        this.imWebView.unMuteAudio(str);
    }

    @JavascriptInterface
    public boolean isAudioMuted(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(35), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> isAudioMuted: ");
        return this.imWebView.isAudioMuted(str);
    }

    @JavascriptInterface
    public void setAudioVolume(String str, int i) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(36), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> setAudioVolume: " + str + " " + i);
        this.imWebView.setAudioVolume(str, i);
    }

    @JavascriptInterface
    public int getAudioVolume(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(37), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> getAudioVolume: ");
        return this.imWebView.getAudioVolume(str);
    }

    @JavascriptInterface
    public void seekAudio(String str, int i) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(38), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> seekAudio: ");
        this.imWebView.seekAudio(str, i);
    }

    @JavascriptInterface
    public double getMicIntensity() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(51), null));
        return this.imWebView.getLastGoodKnownMicValue();
    }

    @JavascriptInterface
    public void playVideo(String str, boolean z, boolean z2, boolean z3, boolean z4, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(40), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> playVideo: url: " + str + " audioMuted: " + z + " autoPlay: " + z2 + " controls: " + z3 + " loop: " + z4 + " x: " + str2 + " y: " + str3 + " width: " + str4 + " height: " + str5 + " startStyle: " + str6 + " stopStyle: " + str7 + " id:" + str8);
        Dimensions dimensions = new Dimensions();
        dimensions.x = a(str2, -99999);
        dimensions.y = a(str3, -99999);
        dimensions.width = a(str4, -99999);
        dimensions.height = a(str5, -99999);
        if (dimensions.width == -99999 && dimensions.height == -99999) {
            int[] b = b();
            dimensions.x = 0;
            dimensions.y = 0;
            dimensions.width = b[0];
            dimensions.height = b[1];
        }
        this.imWebView.playVideo(str, z, z2, z3, z4, dimensions, str6, str7, str8);
    }

    private int a(String str, int i) {
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
        }
        return i;
    }

    @JavascriptInterface
    public void pauseAudio(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(39), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> pauseAudio: id :" + str);
        this.imWebView.pauseAudio(str);
    }

    @JavascriptInterface
    public void pauseVideo(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(47), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> pauseVideo: id :" + str);
        this.imWebView.pauseVideo(str);
    }

    @JavascriptInterface
    public void closeVideo(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(50), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> closeVideo: id :" + str);
        this.imWebView.closeVideo(str);
    }

    @JavascriptInterface
    public void hideVideo(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(48), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> hideVideo: id :" + str);
        this.imWebView.hideVideo(str);
    }

    @JavascriptInterface
    public void showVideo(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(49), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> showVideo: id :" + str);
        this.imWebView.showVideo(str);
    }

    @JavascriptInterface
    public void muteVideo(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(41), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> muteVideo: ");
        this.imWebView.muteVideo(str);
    }

    @JavascriptInterface
    public void unMuteVideo(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(42), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> unMuteVideo: ");
        this.imWebView.unMuteVideo(str);
    }

    @JavascriptInterface
    public void seekVideo(String str, int i) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(46), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> seekVideo: ");
        this.imWebView.seekVideo(str, i);
    }

    @JavascriptInterface
    public boolean isVideoMuted(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(43), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> isVideoMuted: ");
        return this.imWebView.isVideoMuted(str);
    }

    @JavascriptInterface
    public void setVideoVolume(String str, int i) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(44), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> setVideoVolume: ");
        this.imWebView.setVideoVolume(str, i);
    }

    @JavascriptInterface
    public int getVideoVolume(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(45), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> getVideoVolume: ");
        return this.imWebView.getVideoVolume(str);
    }

    @JavascriptInterface
    public void openExternal(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(2), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> openExternal: url: " + str);
        this.imWebView.openExternal(str);
    }

    @JavascriptInterface
    public String getScreenSize() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(17), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> getScreenSize");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
        int i2 = (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("width", i);
            jSONObject.put("height", i2);
        } catch (JSONException e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Failed to get screen size");
        }
        return jSONObject.toString();
    }

    @JavascriptInterface
    public String getCurrentPosition() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(18), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> getCurrentPosition");
        synchronized (this.imWebView.mutexcPos) {
            this.imWebView.sendToCPHandler();
            while (this.imWebView.acqMutexcPos.get()) {
                try {
                    this.imWebView.mutexcPos.wait();
                } catch (Throwable e) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "mutexcPos failed ", e);
                }
            }
            this.imWebView.acqMutexcPos.set(true);
        }
        return this.imWebView.curPosition.toString();
    }

    @JavascriptInterface
    public String getDefaultPosition() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(19), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "JSUtilityController-> getDefaultPosition");
        synchronized (this.imWebView.mutexdPos) {
            this.imWebView.sendToDPHandler();
            while (this.imWebView.acqMutexdPos.get()) {
                try {
                    this.imWebView.mutexdPos.wait();
                } catch (Throwable e) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "mutexdPos failed ", e);
                }
            }
            this.imWebView.acqMutexdPos.set(true);
        }
        Log.debug(Constants.RENDERING_LOG_TAG, "mutexdPassed" + this.imWebView.defPosition);
        return this.imWebView.defPosition.toString();
    }

    private int[] b() {
        int[] iArr = new int[2];
        try {
            FrameLayout frameLayout = (FrameLayout) ((ViewGroup) this.imWebView.getOriginalParent()).getRootView().findViewById(16908290);
            iArr[0] = (int) (((float) frameLayout.getWidth()) / this.imWebView.getDensity());
            iArr[1] = (int) (((float) frameLayout.getHeight()) / this.imWebView.getDensity());
        } catch (Exception e) {
            iArr[1] = 0;
            iArr[0] = 0;
        }
        return iArr;
    }

    @JavascriptInterface
    public String getMaxSize() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(20), null));
        int[] b = b();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("width", b[0]);
            jSONObject.put("height", b[1]);
        } catch (JSONException e) {
        }
        return jSONObject.toString();
    }

    @JavascriptInterface
    public void postToSocial(int i, String str, String str2, String str3) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(21), null));
        if (supports("postToSocial." + i)) {
            String str4;
            if (str == null) {
                str4 = "";
            } else {
                str4 = str;
            }
            if (str2 == null) {
                str4 = "";
            }
            if (str3 == null) {
                str3 = "";
            }
            Intent intent = new Intent(this.imWebView.getActivity(), IMBrowserActivity.class);
            int generateId = IMBrowserActivity.generateId(new StartActivityForResultCallback(this) {
                final /* synthetic */ JSUtilityController a;

                {
                    this.a = r1;
                }

                public void onActivityResult(int i, Intent intent) {
                }
            });
            intent.putExtra(IMBrowserActivity.EXTRA_BROWSER_ACTIVITY_TYPE, 100);
            intent.putExtra(AnalyticsEvent.EVENT_ID, generateId);
            intent.putExtra("action", "postToSocial");
            intent.putExtra("socialType", i);
            intent.putExtra("text", str4);
            intent.putExtra("link", str2);
            intent.putExtra("image", str3);
            this.imWebView.getActivity().startActivity(intent);
            if (this.imWebView.mListener != null) {
                this.imWebView.mListener.onLeaveApplication();
                return;
            }
            return;
        }
        this.imWebView.raiseError("Social type " + i + " is not supported.", "postToSocial");
    }

    @SuppressLint({"NewApi"})
    public boolean supports(String str) {
        boolean z = true;
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(22), null));
        Boolean valueOf;
        if (str.equals("html5video") || str.equals("inlineVideo")) {
            if (VERSION.SDK_INT >= 11) {
                boolean z2 = this.imWebView.isHardwareAccelerated() && this.imWebView.isEnabledHardwareAcceleration();
                valueOf = Boolean.valueOf(z2);
            } else {
                valueOf = Boolean.valueOf(true);
            }
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        }
        valueOf = (Boolean) this.f.get(str);
        if (valueOf != null) {
            return valueOf.booleanValue();
        }
        PackageManager packageManager = this.imWebView.getActivity().getPackageManager();
        if (str.equals(Event.INTENT_PHONE_CALL)) {
            this.f.put(str, Boolean.valueOf(true));
            return true;
        } else if (str.equals(Event.INTENT_TXT_MESSAGE)) {
            r0 = new Intent("android.intent.action.VIEW");
            r0.setType("vnd.android-dir/mms-sms");
            if (this.imWebView.getActivity().getPackageManager().resolveActivity(r0, 65536) == null) {
                valueOf = Boolean.valueOf(false);
            } else {
                valueOf = Boolean.valueOf(true);
            }
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        } else if (str.equals(Event.INTENT_CALENDAR_EVENT)) {
            r0 = new Intent("android.intent.action.VIEW");
            r0.setType("vnd.android.cursor.item/event");
            if (this.imWebView.getActivity().getPackageManager().resolveActivity(r0, 65536) == null) {
                valueOf = Boolean.valueOf(false);
            } else {
                valueOf = Boolean.valueOf(true);
            }
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        } else if (str.equals("microphone")) {
            if (packageManager.checkPermission("android.permission.RECORD_AUDIO", packageManager.getNameForUid(Binder.getCallingUid())) != 0) {
                z = false;
            }
            valueOf = Boolean.valueOf(z);
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        } else if (str.equals("storePicture")) {
            if (VERSION.SDK_INT <= 8 || packageManager.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", packageManager.getNameForUid(Binder.getCallingUid())) != 0) {
                z = false;
            }
            valueOf = Boolean.valueOf(z);
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        } else if (str.equals("postToSocial.2") || str.equals("postToSocial.3")) {
            valueOf = Boolean.valueOf(true);
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        } else if (str.equals("takeCameraPicture")) {
            ResolveInfo resolveActivity = this.imWebView.getActivity().getPackageManager().resolveActivity(new Intent("android.media.action.IMAGE_CAPTURE"), 65536);
            z2 = packageManager.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", packageManager.getNameForUid(Binder.getCallingUid())) == 0;
            if (resolveActivity == null) {
                valueOf = Boolean.valueOf(false);
            } else {
                if (!z2) {
                    z = false;
                }
                valueOf = Boolean.valueOf(z);
            }
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        } else if (str.equals("getGalleryImage")) {
            if (this.imWebView.getActivity().getPackageManager().resolveActivity(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 65536) == null) {
                valueOf = Boolean.valueOf(false);
            } else {
                valueOf = Boolean.valueOf(true);
            }
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        } else if (!str.equals("vibrate")) {
            return false;
        } else {
            Boolean valueOf2 = Boolean.valueOf(packageManager.checkPermission("android.permission.VIBRATE", packageManager.getNameForUid(Binder.getCallingUid())) == 0);
            Vibrator vibrator = (Vibrator) this.d.imWebView.getActivity().getSystemService("vibrator");
            if (vibrator == null) {
                valueOf = Boolean.valueOf(false);
            } else if (VERSION.SDK_INT >= 11) {
                if (!(valueOf2.booleanValue() && vibrator.hasVibrator())) {
                    z = false;
                }
                valueOf = Boolean.valueOf(z);
            } else {
                valueOf = valueOf2;
            }
            this.f.put(str, valueOf);
            return valueOf.booleanValue();
        }
    }

    @JavascriptInterface
    public String supportsFeature(String str) {
        return String.valueOf(supports(str));
    }

    @JavascriptInterface
    @SuppressLint({"NewApi"})
    public void storePicture(String str) {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(23), null));
        Log.debug(Constants.RENDERING_LOG_TAG, "Store picture called on URL: " + str);
        try {
            Uri parse = Uri.parse(InternalSDKUtil.getFinalRedirectedUrl(str));
            if (supports("storePicture")) {
                try {
                    Request request = new Request(parse);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, parse.getLastPathSegment());
                    this.a.enqueue(request);
                } catch (Exception e) {
                    this.imWebView.raiseError("Unable to store.", "storePicture");
                }
            }
        } catch (Exception e2) {
            this.imWebView.raiseError("Invalid URL.", "storePicture");
        }
    }

    @JavascriptInterface
    public String takeCameraPicture() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(24), null));
        if (supports("takeCameraPicture")) {
            Intent intent = new Intent(this.imWebView.getActivity(), IMBrowserActivity.class);
            intent.putExtra(IMBrowserActivity.EXTRA_BROWSER_ACTIVITY_TYPE, 100);
            final Parcelable insert = this.mContext.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
            intent.putExtra(AnalyticsEvent.EVENT_ID, IMBrowserActivity.generateId(new StartActivityForResultCallback(this) {
                final /* synthetic */ JSUtilityController b;

                public void onActivityResult(int i, Intent intent) {
                    if (i == -1) {
                        String convertMediaUriToPath;
                        if (intent == null) {
                            convertMediaUriToPath = ImageProcessing.convertMediaUriToPath(insert, this.b.mContext);
                        } else {
                            convertMediaUriToPath = ImageProcessing.convertMediaUriToPath(intent.getData(), this.b.mContext);
                        }
                        Bitmap compressedBitmap = ImageProcessing.getCompressedBitmap(convertMediaUriToPath, this.b.mContext);
                        int width = compressedBitmap.getWidth();
                        int height = compressedBitmap.getHeight();
                        this.b.imWebView.raiseCameraPictureCapturedEvent(ImageProcessing.getBase64EncodedImage(compressedBitmap, this.b.mContext), width, height);
                        return;
                    }
                    this.b.imWebView.raiseError("User did not take a picture", "takeCameraPicture");
                }
            }));
            intent.putExtra("URI", insert);
            intent.putExtra("action", "takeCameraPicture");
            this.imWebView.getActivity().startActivity(intent);
            if (this.imWebView.mListener != null) {
                this.imWebView.mListener.onLeaveApplication();
            }
        } else {
            Log.internal(Constants.RENDERING_LOG_TAG, "takeCameraPicture called even if it is not supported");
        }
        return null;
    }

    @JavascriptInterface
    public String getSdkVersion() {
        return InMobi.getVersion();
    }

    @JavascriptInterface
    public String getGalleryImage() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(25), null));
        if (supports("getGalleryImage")) {
            Intent intent = new Intent(this.imWebView.getActivity(), IMBrowserActivity.class);
            intent.putExtra(IMBrowserActivity.EXTRA_BROWSER_ACTIVITY_TYPE, 100);
            intent.putExtra(AnalyticsEvent.EVENT_ID, IMBrowserActivity.generateId(new StartActivityForResultCallback(this) {
                final /* synthetic */ JSUtilityController a;

                {
                    this.a = r1;
                }

                public void onActivityResult(int i, Intent intent) {
                    if (i == -1) {
                        Bitmap compressedBitmap = ImageProcessing.getCompressedBitmap(ImageProcessing.convertMediaUriToPath(intent.getData(), this.a.mContext), this.a.mContext);
                        int width = compressedBitmap.getWidth();
                        int height = compressedBitmap.getHeight();
                        this.a.imWebView.raiseGalleryImageSelectedEvent(ImageProcessing.getBase64EncodedImage(compressedBitmap, this.a.mContext), width, height);
                        return;
                    }
                    this.a.imWebView.raiseError("User did not select a picture", "getGalleryImage");
                }
            }));
            intent.putExtra("action", "getGalleryImage");
            this.imWebView.getActivity().startActivity(intent);
            if (this.imWebView.mListener != null) {
                this.imWebView.mListener.onLeaveApplication();
            }
        } else {
            Log.internal(Constants.RENDERING_LOG_TAG, "getGalleryImage called even if it is not supported");
        }
        return null;
    }

    @JavascriptInterface
    public void vibrate() {
        ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(26), null));
        if (!this.imWebView.isViewable()) {
            this.imWebView.raiseError("Creative not visible. Will not vibrate.", "vibrate");
        } else if (supports("vibrate")) {
            ((Vibrator) this.imWebView.getActivity().getSystemService("vibrator")).vibrate(2000);
            new Timer().schedule(new TimerTask(this) {
                final /* synthetic */ JSUtilityController a;

                {
                    this.a = r1;
                }

                public void run() {
                    try {
                        this.a.imWebView.raiseVibrateCompleteEvent();
                    } catch (Throwable e) {
                        Log.internal(Constants.RENDERING_LOG_TAG, "Vibrate callback execption", e);
                    }
                }
            }, 2000);
        } else {
            Log.internal(Constants.RENDERING_LOG_TAG, "vibrate called even if it is not supported");
        }
    }

    @JavascriptInterface
    public void vibrate(String str, int i) {
        try {
            ApiStatCollector.getLogger().logEvent(new EventLog(new ApiEventType(26), null));
            if (!this.imWebView.isViewable()) {
                this.imWebView.raiseError("Creative not visible. Will not vibrate.", "vibrate");
            } else if (supports("vibrate")) {
                Vibrator vibrator = (Vibrator) this.imWebView.getActivity().getSystemService("vibrator");
                String replaceAll = str.replaceAll("\\[", "").replaceAll("\\]", "");
                if (replaceAll == null || "".equals(replaceAll.trim())) {
                    vibrator.cancel();
                    return;
                }
                int maxVibPatternLength;
                String[] split = replaceAll.split(",");
                int length = split.length;
                if (length > Initializer.getConfigParams().getMaxVibPatternLength()) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "vibration pattern exceeds max length. Will be truncated to max " + Initializer.getConfigParams().getMaxVibPatternLength() + "ms");
                    maxVibPatternLength = Initializer.getConfigParams().getMaxVibPatternLength();
                } else {
                    maxVibPatternLength = length;
                }
                long[] jArr = new long[maxVibPatternLength];
                length = 0;
                while (length < maxVibPatternLength) {
                    try {
                        jArr[length] = Long.parseLong(split[length]);
                        if (jArr[length] > ((long) Initializer.getConfigParams().getMaxVibDuration())) {
                            Log.internal(Constants.RENDERING_LOG_TAG, "vibration duration exceeds max. Will only vibrate for max " + Initializer.getConfigParams().getMaxVibDuration() + "ms");
                            jArr[length] = (long) Initializer.getConfigParams().getMaxVibDuration();
                        }
                        if (jArr[length] < 0) {
                            this.imWebView.raiseError("Negative duration not allowed in vibration .", "vibrate");
                        }
                        length++;
                    } catch (NumberFormatException e) {
                        this.imWebView.raiseError("Invalid values of pattern in vibration .", "vibrate");
                        return;
                    }
                }
                if (jArr != null && jArr.length != 0) {
                    vibrator.vibrate(jArr, i);
                }
            } else {
                this.imWebView.raiseError("Vibrate called even if it is not supported.", "vibrate");
                Log.internal(Constants.RENDERING_LOG_TAG, "vibrate called even if it is not supported");
            }
        } catch (Throwable e2) {
            Log.internal(Constants.RENDERING_LOG_TAG, "vibrate exception", e2);
        }
    }

    @JavascriptInterface
    public void registerMicListener() {
        if (!this.h) {
            this.h = true;
            AudioTriggerer.addEventListener(this.g);
        }
    }

    @JavascriptInterface
    public void unRegisterMicListener() {
        if (this.h) {
            this.h = false;
            AudioTriggerer.removeEventListener(this.g);
        }
    }

    private void c() {
        if (this.i) {
            registerMicListener();
        }
    }

    private void d() {
        this.i = this.h;
        unRegisterMicListener();
    }

    public void reset() {
        if (this.d != null) {
            this.d.reset();
        }
    }

    @JavascriptInterface
    public void onUserInteraction(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            HashMap hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                try {
                    hashMap.put(str2, jSONObject.getString(str2));
                } catch (JSONException e) {
                }
            }
            this.imWebView.userInteraction(hashMap);
        } catch (Exception e2) {
        }
    }

    @JavascriptInterface
    public void saveContent(String str, String str2) {
        File file = new File(InternalSDKUtil.getContext().getExternalFilesDir(null) + "/im_cached_content/");
        if (file.exists()) {
            file.delete();
        }
        file.mkdir();
        char[] toCharArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            stringBuilder.append(toCharArray[random.nextInt(toCharArray.length)]);
        }
        this.imWebView.saveFile(new File(file, stringBuilder.toString()), str2, str);
    }

    @JavascriptInterface
    public void cancelSaveContent(String str) {
        this.imWebView.cancelSaveContent(str);
    }

    @JavascriptInterface
    public void incentCompleted(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            HashMap hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                try {
                    hashMap.put(str2, jSONObject.get(str2));
                } catch (JSONException e) {
                    this.imWebView.incentCompleted(null);
                    return;
                }
            }
            this.imWebView.incentCompleted(hashMap);
        } catch (JSONException e2) {
            Log.internal(Constants.RENDERING_LOG_TAG, "JSON error");
            this.imWebView.incentCompleted(null);
        }
    }

    @JavascriptInterface
    public void setPlayableSettings(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Map hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                try {
                    hashMap.put(str2, jSONObject.get(str2));
                } catch (JSONException e) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "Playable Ads Settings map key " + str2 + " has invalid value");
                }
            }
            this.imWebView.getPlayableListener().onPlayableSettingsReceived(hashMap);
        } catch (Throwable e2) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception setting playable settings", e2);
        }
    }
}
