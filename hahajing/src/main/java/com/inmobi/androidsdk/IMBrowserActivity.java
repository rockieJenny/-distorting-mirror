package com.inmobi.androidsdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.WrapperFunctions;
import com.inmobi.re.container.CustomView;
import com.inmobi.re.container.CustomView.SwitchIconType;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.container.IMWebView.IMWebViewListener;
import com.inmobi.re.container.IMWebView.ViewState;
import com.inmobi.re.controller.JSUtilityController;
import com.inmobi.re.controller.util.Constants;
import com.inmobi.re.controller.util.StartActivityForResultCallback;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressLint({"UseSparseArrays"})
public class IMBrowserActivity extends Activity {
    public static final String ANIMATED = "isAnimationEnabledOnDimiss";
    public static final int BROWSER_ACTIVITY = 100;
    public static final int CLOSE_BUTTON_VIEW_ID = 225;
    public static final int CLOSE_REGION_VIEW_ID = 226;
    public static final String EXPANDDATA = "data";
    public static final int EXPAND_ACTIVITY = 102;
    public static final String EXTRA_BROWSER_ACTIVITY_TYPE = "extra_browser_type";
    public static final String EXTRA_URL = "extra_url";
    public static final int GET_IMAGE = 101;
    public static final int INTERSTITIAL_ACTIVITY = 101;
    private static IMWebView b;
    private static IMWebViewListener c;
    private static IMWebView d;
    private static FrameLayout e;
    private static Message f;
    private static Map<Integer, StartActivityForResultCallback> l = new HashMap();
    private static int m = 0;
    private static Activity o;
    private IMWebView a;
    private RelativeLayout g;
    private float h;
    private Boolean i;
    private CustomView j;
    private long k = 0;
    private int n;
    private WebViewClient p = new WebViewClient(this) {
        final /* synthetic */ IMBrowserActivity a;

        {
            this.a = r1;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            if (this.a.j != null) {
                this.a.j.setSwitchInt(SwitchIconType.FORWARD_INACTIVE);
                this.a.j.invalidate();
            }
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (this.a.j != null) {
                if (webView.canGoForward()) {
                    this.a.j.setSwitchInt(SwitchIconType.FORWARD_ACTIVE);
                    this.a.j.invalidate();
                } else {
                    this.a.j.setSwitchInt(SwitchIconType.FORWARD_INACTIVE);
                    this.a.j.invalidate();
                }
            }
            CookieSyncManager.getInstance().sync();
        }
    };

    public static int generateId(StartActivityForResultCallback startActivityForResultCallback) {
        m++;
        l.put(Integer.valueOf(m), startActivityForResultCallback);
        return m;
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        try {
            ((StartActivityForResultCallback) l.get(Integer.valueOf(i))).onActivityResult(i2, intent);
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "onActivityResult failed", e);
        }
        l.remove(Integer.valueOf(i2));
        if (this.a == null) {
            finish();
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            CookieSyncManager.createInstance(this);
            CookieSyncManager.getInstance().startSync();
            Intent intent = getIntent();
            this.n = intent.getIntExtra(EXTRA_BROWSER_ACTIVITY_TYPE, 100);
            this.k = intent.getLongExtra(ANIMATED, 0);
            if (this.n == 100) {
                requestWindowFeature(1);
                if (VERSION.SDK_INT < 9 || VERSION.SDK_INT >= 11) {
                    getWindow().setFlags(1024, 1024);
                }
                WindowManager windowManager = (WindowManager) getSystemService("window");
                windowManager.getDefaultDisplay().getMetrics(new DisplayMetrics());
                if (intent.getStringExtra("action") != null) {
                    a(intent);
                }
                this.h = getResources().getDisplayMetrics().density;
                String stringExtra = intent.getStringExtra(EXTRA_URL);
                this.i = Boolean.valueOf(intent.getBooleanExtra("FIRST_INSTANCE", false));
                Log.debug(Constants.RENDERING_LOG_TAG, "IMBrowserActivity-> onCreate");
                this.g = new RelativeLayout(this);
                if (stringExtra != null) {
                    boolean booleanExtra = intent.getBooleanExtra("QAMODE", false);
                    this.a = new IMWebView(this, c, true, true);
                    LayoutParams layoutParams = new RelativeLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent());
                    layoutParams.addRule(10);
                    layoutParams.addRule(2, 100);
                    this.g.setBackgroundColor(-1);
                    this.g.addView(this.a, layoutParams);
                    a(this.g);
                    this.a.getSettings().setJavaScriptEnabled(true);
                    this.a.setExternalWebViewClient(this.p);
                    this.a.getSettings().setLoadWithOverviewMode(true);
                    this.a.getSettings().setUseWideViewPort(true);
                    if (VERSION.SDK_INT >= 8) {
                        this.a.loadUrl(stringExtra, null);
                    } else {
                        this.a.loadUrl(stringExtra);
                    }
                    if (booleanExtra) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("mk-carrier", "117.97.87.6");
                        hashMap.put("x-real-ip", "117.97.87.6");
                    }
                    setContentView(this.g);
                }
            } else if (this.n == 101) {
                b.setActivity(this);
                b.mInterstitialController.setActivity(this);
                b.mInterstitialController.changeContentAreaForInterstitials(this.k);
                r0 = findViewById(CLOSE_BUTTON_VIEW_ID);
                if (r0 != null) {
                    r0.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ IMBrowserActivity a;

                        {
                            this.a = r1;
                        }

                        public void onClick(View view) {
                            this.a.finish();
                        }
                    });
                }
                r0 = findViewById(CLOSE_REGION_VIEW_ID);
                if (r0 != null) {
                    r0.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ IMBrowserActivity a;

                        {
                            this.a = r1;
                        }

                        public void onClick(View view) {
                            this.a.finish();
                        }
                    });
                }
                b.setOnKeyListener(new OnKeyListener(this) {
                    final /* synthetic */ IMBrowserActivity a;

                    {
                        this.a = r1;
                    }

                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (4 == keyEvent.getKeyCode() && keyEvent.getAction() == 0) {
                            this.a.finish();
                        }
                        return false;
                    }
                });
            } else if (this.n == 102) {
                if (!(e == null || e.getParent() == null)) {
                    ((ViewGroup) e.getParent()).removeView(e);
                }
                setContentView(e);
                d.setState(ViewState.EXPANDED);
                d.mIsViewable = true;
                d.mExpandController.setActivity(this);
                d.setBrowserActivity(this);
                d.mExpandController.handleOrientationForExpand();
                r0 = findViewById(CLOSE_BUTTON_VIEW_ID);
                if (r0 != null) {
                    r0.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ IMBrowserActivity a;

                        {
                            this.a = r1;
                        }

                        public void onClick(View view) {
                            if (IMBrowserActivity.d != null) {
                                IMBrowserActivity.d.close();
                            }
                            this.a.finish();
                        }
                    });
                }
                r0 = findViewById(CLOSE_REGION_VIEW_ID);
                if (r0 != null) {
                    r0.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ IMBrowserActivity a;

                        {
                            this.a = r1;
                        }

                        public void onClick(View view) {
                            if (IMBrowserActivity.d != null) {
                                IMBrowserActivity.d.close();
                            }
                            this.a.finish();
                        }
                    });
                }
                d.setOnKeyListener(new OnKeyListener(this) {
                    final /* synthetic */ IMBrowserActivity a;

                    {
                        this.a = r1;
                    }

                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (4 != keyEvent.getKeyCode() || keyEvent.getAction() != 0) {
                            return false;
                        }
                        if (IMBrowserActivity.d != null) {
                            IMBrowserActivity.d.close();
                        }
                        this.a.finish();
                        return true;
                    }
                });
            }
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception rendering ad in ImBrowser Activity", e);
        }
    }

    public static void setWebview(IMWebView iMWebView) {
        if (iMWebView != null) {
            b = iMWebView;
        }
    }

    public static void setExpandedLayout(FrameLayout frameLayout) {
        if (frameLayout != null) {
            e = frameLayout;
        }
    }

    public static void setExpandedWebview(IMWebView iMWebView) {
        if (iMWebView != null) {
            d = iMWebView;
        }
    }

    public static void setOriginalActivity(Activity activity) {
        if (activity != null) {
            o = activity;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, int r11) {
        /*
        r6 = this;
        r5 = 0;
        r1 = 0;
        switch(r7) {
            case 1: goto L_0x003d;
            case 2: goto L_0x0040;
            case 3: goto L_0x0043;
            default: goto L_0x0005;
        };
    L_0x0005:
        r0 = r1;
    L_0x0006:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r2 = r2.append(r8);
        r3 = " ";
        r2 = r2.append(r3);
        r2 = r2.append(r9);
        r3 = " ";
        r2 = r2.append(r3);
        r2 = r2.append(r10);
        r2 = r2.toString();
        r3 = new android.content.Intent;
        r3.<init>();
        r4 = "text/plain";
        r3.setType(r4);
        r3.setPackage(r0);
        r0 = "android.intent.extra.TEXT";
        r3.putExtra(r0, r2);
        r6.startActivityForResult(r3, r11);	 Catch:{ Exception -> 0x0046 }
    L_0x003c:
        return;
    L_0x003d:
        r0 = "";
        goto L_0x0006;
    L_0x0040:
        r0 = "com.google.android.apps.plus";
        goto L_0x0006;
    L_0x0043:
        r0 = "com.twitter.android";
        goto L_0x0006;
    L_0x0046:
        r0 = move-exception;
        switch(r7) {
            case 1: goto L_0x0064;
            case 2: goto L_0x00ae;
            case 3: goto L_0x00c8;
            default: goto L_0x004a;
        };
    L_0x004a:
        r0 = r1;
    L_0x004b:
        if (r0 == 0) goto L_0x00ed;
    L_0x004d:
        r2 = new android.content.Intent;
        r3 = "android.intent.action.VIEW";
        r2.<init>(r3);
        r0 = android.net.Uri.parse(r0);
        r2.setData(r0);
        r6.startActivityForResult(r2, r11);	 Catch:{ Exception -> 0x005f }
        goto L_0x003c;
    L_0x005f:
        r0 = move-exception;
        r6.onActivityResult(r11, r5, r1);
        goto L_0x003c;
    L_0x0064:
        r0 = new java.lang.StringBuilder;	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0.<init>();	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "https://www.facebook.com/dialog/feed?app_id=181821551957328&link=";
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "UTF-8";
        r3 = java.net.URLEncoder.encode(r9, r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "&picture=";
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "UTF-8";
        r3 = java.net.URLEncoder.encode(r10, r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "&name=&description=";
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "UTF-8";
        r3 = java.net.URLEncoder.encode(r8, r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "&redirect_uri=";
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "UTF-8";
        r3 = java.net.URLEncoder.encode(r9, r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.toString();	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        goto L_0x004b;
    L_0x00ae:
        r0 = new java.lang.StringBuilder;	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0.<init>();	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "https://m.google.com/app/plus/x/?v=compose&content=";
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "UTF-8";
        r3 = java.net.URLEncoder.encode(r2, r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.toString();	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        goto L_0x004b;
    L_0x00c8:
        r0 = new java.lang.StringBuilder;	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0.<init>();	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "http://twitter.com/home?status=";
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r3 = "UTF-8";
        r3 = java.net.URLEncoder.encode(r2, r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.append(r3);	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        r0 = r0.toString();	 Catch:{ UnsupportedEncodingException -> 0x00e3 }
        goto L_0x004b;
    L_0x00e3:
        r0 = move-exception;
        r3 = "[InMobi]-[RE]-4.5.3";
        r4 = "UTF-8 encoding not supported? What sorcery is this?";
        com.inmobi.commons.internal.Log.internal(r3, r4, r0);
        goto L_0x004a;
    L_0x00ed:
        r0 = new android.content.Intent;
        r0.<init>();
        r3 = "text/plain";
        r0.setType(r3);
        r3 = "android.intent.extra.TEXT";
        r0.putExtra(r3, r2);
        r6.startActivityForResult(r0, r11);	 Catch:{ Exception -> 0x0101 }
        goto L_0x003c;
    L_0x0101:
        r0 = move-exception;
        r6.onActivityResult(r11, r5, r1);
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.inmobi.androidsdk.IMBrowserActivity.a(int, java.lang.String, java.lang.String, java.lang.String, int):void");
    }

    private void a(long j, long j2, String str, String str2, String str3, String str4, String str5, int i) {
        try {
            Intent intent = new Intent("android.intent.action.EDIT");
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", j);
            intent.putExtra("allDay", false);
            intent.putExtra("endTime", j2);
            intent.putExtra("title", str2);
            intent.putExtra("eventLocation", str);
            intent.putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, str3);
            if (str4.equals("transparent")) {
                intent.putExtra("availability", 1);
            } else {
                if (str4.equals("opaque")) {
                    intent.putExtra("availability", 0);
                }
            }
            if (!"".equals(str5) && VERSION.SDK_INT > 8) {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    JSONObject jSONObject = new JSONObject(str5);
                    String optString = jSONObject.optString("frequency");
                    if (optString == null || "".equals(optString)) {
                        if (this.a != null) {
                            this.a.raiseError("Frequency is incorrect in recurrence rule", "createCalendarEvent");
                        }
                        startActivityForResult(intent, i);
                        return;
                    }
                    if ("daily".equals(optString) || "weekly".equals(optString) || "monthly".equals(optString) || "yearly".equals(optString)) {
                        stringBuilder.append("freq=" + optString + ";");
                    }
                    optString = jSONObject.optString("interval");
                    if (optString != null) {
                        try {
                            if (!"".equals(optString)) {
                                stringBuilder.append("interval=" + Integer.parseInt(optString) + ";");
                            }
                        } catch (Throwable e) {
                            if (this.a != null) {
                                this.a.raiseError("Interval is incorrect in recurrence rule", "createCalendarEvent");
                            }
                            Log.internal(Constants.RENDERING_LOG_TAG, "Invalid interval in recurrence rule", e);
                        }
                    }
                    optString = a(jSONObject.optString("expires"));
                    if (optString != null) {
                        stringBuilder.append("until=" + optString.replace("+", "Z+").replace("-", "Z-") + ";");
                    } else if (this.a != null) {
                        this.a.raiseError("Date format is incorrect in until", "createCalendarEvent");
                    }
                    optString = a(jSONObject.optJSONArray("daysInWeek"));
                    if (optString != null) {
                        stringBuilder.append("byday=" + optString + ";");
                    }
                    optString = a(jSONObject.optJSONArray("daysInMonth"), -30, 31);
                    if (optString != null) {
                        stringBuilder.append("bymonthday=" + optString + ";");
                    }
                    optString = a(jSONObject.optJSONArray("daysInYear"), -364, 365);
                    if (optString != null) {
                        stringBuilder.append("byyearday=" + optString + ";");
                    }
                    optString = a(jSONObject.optJSONArray("weeksInMonth"), -3, 4);
                    if (optString != null) {
                        stringBuilder.append("byweekno=" + optString + ";");
                    }
                    optString = a(jSONObject.optJSONArray("monthsInYear"), 1, 12);
                    if (optString != null) {
                        stringBuilder.append("bymonth=" + optString + ";");
                    }
                    Log.internal(Constants.RENDERING_LOG_TAG, "Recurrence rule : " + stringBuilder.toString());
                    if (!"".equals(stringBuilder.toString())) {
                        intent.putExtra("rrule", stringBuilder.toString());
                    }
                } catch (Throwable e2) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "Exception parsing recurrence rule", e2);
                }
            }
            startActivityForResult(intent, i);
        } catch (Exception e3) {
            onActivityResult(i, 0, null);
        }
    }

    private String a(String str) {
        int i = 0;
        String str2 = null;
        if (str != null && !"".equals(str)) {
            Date parse;
            SimpleDateFormat[] simpleDateFormatArr = JSUtilityController.formats;
            int length = simpleDateFormatArr.length;
            int i2 = 0;
            while (i2 < length) {
                try {
                    parse = simpleDateFormatArr[i2].parse(str);
                    break;
                } catch (Exception e) {
                    i2++;
                }
            }
            parse = null;
            simpleDateFormatArr = JSUtilityController.calendarUntiFormats;
            length = simpleDateFormatArr.length;
            while (i < length) {
                try {
                    str2 = simpleDateFormatArr[i].format(Long.valueOf(parse.getTime()));
                    break;
                } catch (Exception e2) {
                    i++;
                }
            }
        }
        return str2;
    }

    private String a(JSONArray jSONArray, int i, int i2) {
        if (jSONArray != null) {
            try {
                if (jSONArray.length() != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                        int i4 = jSONArray.getInt(i3);
                        if (i4 >= i && i4 <= i2) {
                            stringBuilder.append(i4 + ",");
                        }
                    }
                    String stringBuilder2 = stringBuilder.toString();
                    int length = stringBuilder2.length();
                    if (length == 0) {
                        return null;
                    }
                    try {
                        if (stringBuilder2.charAt(length - 1) == ',') {
                            return stringBuilder2.substring(0, length - 1);
                        }
                        return stringBuilder2;
                    } catch (Throwable e) {
                        Log.internal(InternalSDKUtil.LOGGING_TAG, "Couldn't parse json in create calendar event", e);
                        return stringBuilder2;
                    }
                }
            } catch (Throwable e2) {
                e2.printStackTrace();
                Log.internal(Constants.RENDERING_LOG_TAG, "Exception parsing recurrence rule", e2);
            }
        }
        return null;
    }

    private String a(JSONArray jSONArray) {
        if (jSONArray != null) {
            try {
                if (jSONArray.length() != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        stringBuilder.append(jSONArray.get(i) + ",");
                    }
                    String stringBuilder2 = stringBuilder.toString();
                    int length = stringBuilder2.length();
                    if (length == 0) {
                        return null;
                    }
                    try {
                        if (stringBuilder2.charAt(length - 1) == ',') {
                            return stringBuilder2.substring(0, length - 1);
                        }
                        return stringBuilder2;
                    } catch (Throwable e) {
                        Log.internal(InternalSDKUtil.LOGGING_TAG, "Couldn't parse json in create calendar event", e);
                        return stringBuilder2;
                    }
                }
            } catch (Throwable e2) {
                e2.printStackTrace();
                Log.internal(Constants.RENDERING_LOG_TAG, "Exception parsing recurrence rule", e2);
            }
        }
        return null;
    }

    private void a(Intent intent) {
        String stringExtra = intent.getStringExtra("action");
        int intExtra = intent.getIntExtra(AnalyticsEvent.EVENT_ID, 0);
        if (stringExtra.equals("takeCameraPicture")) {
            Uri uri = (Uri) intent.getExtras().get("URI");
            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
            intent2.putExtra("output", uri);
            try {
                startActivityForResult(intent2, intExtra);
            } catch (Exception e) {
                onActivityResult(intExtra, 0, null);
            }
        } else if (stringExtra.equals("getGalleryImage")) {
            try {
                startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), intExtra);
            } catch (Exception e2) {
                onActivityResult(intExtra, 0, null);
            }
        } else if (stringExtra.equals("postToSocial")) {
            a(intent.getIntExtra("socialType", 0), intent.getStringExtra("text"), intent.getStringExtra("link"), intent.getStringExtra("image"), intExtra);
        } else if (stringExtra.equals("createCalendarEvent")) {
            a(intent.getLongExtra("start", 0), intent.getLongExtra("end", 0), intent.getStringExtra("location"), intent.getStringExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION), intent.getStringExtra("summary"), intent.getStringExtra("transparency"), intent.getStringExtra("recurrence"), intExtra);
        }
    }

    private void a(ViewGroup viewGroup) {
        View linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(0);
        linearLayout.setId(100);
        linearLayout.setWeightSum(100.0f);
        linearLayout.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ IMBrowserActivity a;

            {
                this.a = r1;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        linearLayout.setBackgroundResource(17301658);
        linearLayout.setBackgroundColor(-7829368);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(WrapperFunctions.getParamFillParent(), (int) (44.0f * this.h));
        layoutParams.addRule(12);
        viewGroup.addView(linearLayout, layoutParams);
        layoutParams = new LinearLayout.LayoutParams(WrapperFunctions.getParamFillParent(), WrapperFunctions.getParamFillParent());
        layoutParams.weight = 25.0f;
        View customView = new CustomView(this, this.h, SwitchIconType.CLOSE_ICON);
        linearLayout.addView(customView, layoutParams);
        customView.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ IMBrowserActivity a;

            {
                this.a = r1;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    view.setBackgroundColor(-7829368);
                    this.a.finish();
                } else if (motionEvent.getAction() == 0) {
                    view.setBackgroundColor(-16711681);
                }
                return true;
            }
        });
        customView = new CustomView(this, this.h, SwitchIconType.REFRESH);
        linearLayout.addView(customView, layoutParams);
        customView.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ IMBrowserActivity a;

            {
                this.a = r1;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    view.setBackgroundColor(-7829368);
                    this.a.a.doHidePlayers();
                    this.a.a.reload();
                } else if (motionEvent.getAction() == 0) {
                    view.setBackgroundColor(-16711681);
                }
                return true;
            }
        });
        customView = new CustomView(this, this.h, SwitchIconType.BACK);
        linearLayout.addView(customView, layoutParams);
        customView.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ IMBrowserActivity a;

            {
                this.a = r1;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    view.setBackgroundColor(-7829368);
                    if (this.a.a.canGoBack()) {
                        this.a.a.goBack();
                    } else {
                        this.a.finish();
                    }
                } else if (motionEvent.getAction() == 0) {
                    view.setBackgroundColor(-16711681);
                }
                return true;
            }
        });
        this.j = new CustomView(this, this.h, SwitchIconType.FORWARD_INACTIVE);
        linearLayout.addView(this.j, layoutParams);
        this.j.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ IMBrowserActivity a;

            {
                this.a = r1;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    view.setBackgroundColor(-7829368);
                    if (this.a.a.canGoForward()) {
                        this.a.a.goForward();
                    }
                } else if (motionEvent.getAction() == 0) {
                    view.setBackgroundColor(-16711681);
                }
                return true;
            }
        });
    }

    protected void onPause() {
        super.onPause();
        try {
            CookieSyncManager.getInstance().stopSync();
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception pausing cookies");
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            CookieSyncManager.getInstance().startSync();
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception syncing cookies");
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            c = null;
            if (this.a != null) {
                this.a.mAudioVideoController.releaseAllPlayers();
            }
            if (f != null && this.i.booleanValue()) {
                f.sendToTarget();
                f = null;
            }
            if (b != null) {
                b.mAudioVideoController.releaseAllPlayers();
                b = null;
            }
            if (e != null && this.n == 102) {
                e = null;
            }
            if (d != null && this.n == 102) {
                d.setOnKeyListener(null);
                d = null;
            }
        } catch (Throwable e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Exception in onDestroy ", e);
        }
    }

    public static void requestOnAdDismiss(Message message) {
        f = message;
    }

    public static void setWebViewListener(IMWebViewListener iMWebViewListener) {
        c = iMWebViewListener;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (configuration.orientation == 2) {
            Log.internal(Constants.RENDERING_LOG_TAG, "In allow true,  device orientation:ORIENTATION_LANDSCAPE");
        } else {
            Log.internal(Constants.RENDERING_LOG_TAG, "In allow true,  device orientation:ORIENTATION_PORTRAIT");
        }
        if (this.a != null) {
            this.a.onOrientationEventChange();
        }
        super.onConfigurationChanged(configuration);
    }

    public void onBackPressed() {
        if (d != null && this.n == 102) {
            d.close();
            finish();
        }
        super.onBackPressed();
    }
}
