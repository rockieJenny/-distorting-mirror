package com.jirbo.adcolony;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import com.givewaygames.camera.utils.Constants;
import com.google.analytics.tracking.android.HitTypes;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

class m {
    WebView a;
    Activity b;
    ADCVideo c;
    Handler d = new Handler();
    Runnable e = new Runnable(this) {
        final /* synthetic */ m a;

        {
            this.a = r1;
        }

        public void run() {
            a.A = false;
        }
    };
    AdColonyAd f;
    String g;

    public m(ADCVideo aDCVideo, WebView webView, Activity activity) {
        this.a = webView;
        this.b = activity;
        this.c = aDCVideo;
    }

    void a(String str) {
        String str2;
        String[] strArr;
        String replace = str.replace("mraid://", "");
        if (replace.contains("?")) {
            String[] split = replace.split("\\?");
            str2 = split[0];
            strArr = split;
        } else {
            str2 = replace;
            strArr = null;
        }
        if (strArr != null) {
            strArr = strArr[1].split("&");
        } else {
            strArr = new String[0];
        }
        HashMap hashMap = new HashMap();
        for (String str3 : r0) {
            hashMap.put(str3.split("=")[0], str3.split("=")[1]);
        }
        this.f = a.K;
        this.g = "{\"ad_slot\":" + this.f.h.k.d + "}";
        if (str2.equals("send_adc_event")) {
            b((String) hashMap.get(AnalyticsSQLiteHelper.EVENT_LIST_TYPE));
        } else if (str2.equals("close")) {
            b();
        } else if (str2.equals("open_store") && !a.A) {
            c((String) hashMap.get(HitTypes.ITEM));
        } else if (str2.equals("open") && !a.A) {
            d((String) hashMap.get("url"));
        } else if (str2.equals("expand")) {
            e((String) hashMap.get("url"));
        } else if (str2.equals("create_calendar_event") && !a.A) {
            c(hashMap);
        } else if (str2.equals("mail") && !a.A) {
            d(hashMap);
        } else if (str2.equals(Event.INTENT_TXT_MESSAGE) && !a.A) {
            e(hashMap);
        } else if (str2.equals(Event.INTENT_PHONE_CALL) && !a.A) {
            f(hashMap);
        } else if (str2.equals("custom_event")) {
            g(hashMap);
        } else if (str2.equals("launch_app") && !a.A) {
            h(hashMap);
        } else if (str2.equals("check_app_presence")) {
            i(hashMap);
        } else if (str2.equals("auto_play")) {
            j(hashMap);
        } else if (str2.equals("save_screenshot")) {
            a();
        } else if (str2.equals("social_post") && !a.A) {
            b(hashMap);
        } else if (str2.equals("make_in_app_purchase") && !a.A) {
            a(hashMap);
        }
        f("adc_bridge.nativeCallComplete()");
    }

    void a(HashMap hashMap) {
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        a.a("html5_interaction", this.g, this.c.H);
        String g = g((String) hashMap.get("product"));
        Integer.parseInt(g((String) hashMap.get("quantity")));
        this.b.finish();
        this.c.H.m = g;
        this.c.H.u = AdColonyIAPEngagement.END_CARD;
        a.N.a(this.c.H);
    }

    void b(HashMap hashMap) {
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        String g = g((String) hashMap.get("text"));
        String g2 = g((String) hashMap.get("url"));
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", g + " " + g2);
        if (a.G != null) {
            a.G.left_application();
        }
        this.b.startActivity(Intent.createChooser(intent, "Share this post using..."));
    }

    void a() {
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        a.a("html5_interaction", this.g, this.c.H);
        String str = Environment.getExternalStorageDirectory().toString() + "/Pictures/AdColony_Screenshots/" + "AdColony_Screenshot_" + System.currentTimeMillis() + Constants.IMAGE_EXTENSION;
        View rootView = this.a.getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures");
        File file2 = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/AdColony_Screenshots");
        try {
            file.mkdir();
            file2.mkdir();
        } catch (Exception e) {
        }
        try {
            OutputStream fileOutputStream = new FileOutputStream(new File(str));
            createBitmap.compress(CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            MediaScannerConnection.scanFile(this.b, new String[]{str}, null, new OnScanCompletedListener(this) {
                final /* synthetic */ m a;

                {
                    this.a = r1;
                }

                public void onScanCompleted(String path, Uri uri) {
                    Toast.makeText(this.a.b, "Screenshot saved to Gallery!", 0).show();
                }
            });
        } catch (FileNotFoundException e2) {
            Toast.makeText(this.b, "Error saving screenshot.", 0).show();
            l.a.a("ADC [info] FileNotFoundException in MRAIDCommandTakeScreenshot");
        } catch (IOException e3) {
            Toast.makeText(this.b, "Error saving screenshot.", 0).show();
            l.a.a("ADC [info] IOException in MRAIDCommandTakeScreenshot");
        }
    }

    void b(String str) {
        l.a.a("ADC [info] MRAIDCommandSendADCEvent called with type: ").b((Object) str);
        a.a(str, this.c.H);
    }

    void b() {
        l.a.b((Object) "ADC [info] MRAIDCommandClose called");
        this.b.finish();
        a.N.a(this.c.H);
    }

    void c(String str) {
        l.a.a("ADC [info] MRAIDCommandOpenStore called with item: ").b((Object) str);
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        a.a("html5_interaction", this.g, this.c.H);
        String g = g(str);
        if (a.G != null) {
            a.G.left_application();
        }
        try {
            this.b.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(g)));
        } catch (Exception e) {
            Toast.makeText(this.b, "Unable to open store.", 0).show();
        }
    }

    void d(String str) {
        Intent intent;
        l.a.a("ADC [info] MRAIDCommandOpen called with url: ").b((Object) str);
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        String g = g(str);
        if (g.startsWith("adcvideo")) {
            this.c.a(g.replace("adcvideo", "http"));
        } else if (str.contains("youtube")) {
            try {
                intent = new Intent("android.intent.action.VIEW", Uri.parse("vnd.youtube:" + g.substring(g.indexOf(118) + 2)));
                if (a.G != null) {
                    a.G.left_application();
                }
                this.b.startActivity(intent);
            } catch (Exception e) {
                g = g(str);
                if (g.contains("safari")) {
                    g = g.replace("safari", "http");
                }
                intent = new Intent("android.intent.action.VIEW", Uri.parse(g));
                if (a.G != null) {
                    a.G.left_application();
                }
                this.b.startActivity(intent);
            }
        } else if (g.startsWith(Event.INTENT_EXTERNAL_BROWSER)) {
            a.a("html5_interaction", this.c.H);
            g = g.replace(Event.INTENT_EXTERNAL_BROWSER, "http");
            if (a.G != null) {
                a.G.left_application();
            }
            this.b.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(g)));
        } else {
            a.a("html5_interaction", this.g, this.c.H);
            AdColonyBrowser.url = g;
            this.b.startActivity(new Intent(this.b, AdColonyBrowser.class));
        }
    }

    void e(String str) {
        l.a.a("ADC [info] MRAIDCommandExpand called with url: ").b((Object) str);
        f("adc_bridge.fireChangeEvent({state:'expanded'});");
    }

    void c(HashMap hashMap) {
        Date parse;
        Date parse2;
        Date date;
        long time;
        long time2;
        long j;
        Intent putExtra;
        l.a.a("ADC [info] MRAIDCommandCreateCalendarEvent called with parameters: ").b((Object) hashMap);
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        a.a("html5_interaction", this.g, this.c.H);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mmZ");
        DateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        DateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
        String g = g((String) hashMap.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION));
        g((String) hashMap.get("location"));
        String g2 = g((String) hashMap.get("start"));
        String g3 = g((String) hashMap.get("end"));
        String g4 = g((String) hashMap.get("summary"));
        String g5 = g((String) hashMap.get("recurrence"));
        String str = "";
        String str2 = "";
        HashMap hashMap2 = new HashMap();
        String replace = g5.replace("\"", "").replace("{", "").replace("}", "");
        if (!replace.equals("")) {
            for (String str3 : replace.split(",")) {
                hashMap2.put(str3.split(":")[0], str3.split(":")[1]);
            }
            str = g((String) hashMap2.get("expires"));
            str2 = g((String) hashMap2.get("frequency")).toUpperCase();
            l.a.a("Calendar Recurrence - ").b((Object) replace);
            l.a.a("Calendar Recurrence - frequency = ").b((Object) str2);
            l.a.a("Calendar Recurrence - expires =  ").b((Object) str);
        }
        g5 = str;
        str = str2;
        if (g4.equals("")) {
            g4 = g((String) hashMap.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION));
        }
        try {
            parse = simpleDateFormat.parse(g2);
            try {
                parse2 = simpleDateFormat.parse(g3);
            } catch (Exception e) {
                parse2 = null;
                if (parse == null) {
                    try {
                        parse = simpleDateFormat2.parse(g2);
                        parse2 = simpleDateFormat2.parse(g3);
                        date = parse;
                    } catch (Exception e2) {
                        date = parse;
                    }
                } else {
                    date = parse;
                }
                parse = simpleDateFormat3.parse(g5);
                if (date == null) {
                    Toast.makeText(this.b, "Unable to create Calendar Event.", 0).show();
                    return;
                }
                time = date.getTime();
                time2 = parse2.getTime();
                j = 0;
                if (parse != null) {
                    j = (parse.getTime() - date.getTime()) / 1000;
                }
                if (!str.equals("DAILY")) {
                    j = (j / 86400) + 1;
                } else if (!str.equals("WEEKLY")) {
                    j = (j / 604800) + 1;
                } else if (!str.equals("MONTHLY")) {
                    j = (j / 2629800) + 1;
                } else if (str.equals("YEARLY")) {
                    j = (j / 31557600) + 1;
                } else {
                    j = 0;
                }
                if (replace.equals("")) {
                    putExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", g4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, g).putExtra("beginTime", time).putExtra("endTime", time2);
                } else {
                    putExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", g4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, g).putExtra("beginTime", time).putExtra("endTime", time2).putExtra("rrule", "FREQ=" + str + ";" + "COUNT=" + j);
                    l.a.a("Calendar Recurrence - count = ").b((double) j);
                }
                try {
                    if (a.G != null) {
                        a.G.left_application();
                    }
                    this.b.startActivity(putExtra);
                } catch (Exception e3) {
                    Toast.makeText(this.b, "Unable to create Calendar Event.", 0).show();
                    return;
                }
            }
        } catch (Exception e4) {
            parse = null;
            parse2 = null;
            if (parse == null) {
                date = parse;
            } else {
                parse = simpleDateFormat2.parse(g2);
                parse2 = simpleDateFormat2.parse(g3);
                date = parse;
            }
            parse = simpleDateFormat3.parse(g5);
            if (date == null) {
                time = date.getTime();
                time2 = parse2.getTime();
                j = 0;
                if (parse != null) {
                    j = (parse.getTime() - date.getTime()) / 1000;
                }
                if (!str.equals("DAILY")) {
                    j = (j / 86400) + 1;
                } else if (!str.equals("WEEKLY")) {
                    j = (j / 604800) + 1;
                } else if (!str.equals("MONTHLY")) {
                    j = (j / 2629800) + 1;
                } else if (str.equals("YEARLY")) {
                    j = 0;
                } else {
                    j = (j / 31557600) + 1;
                }
                if (replace.equals("")) {
                    putExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", g4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, g).putExtra("beginTime", time).putExtra("endTime", time2);
                } else {
                    putExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", g4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, g).putExtra("beginTime", time).putExtra("endTime", time2).putExtra("rrule", "FREQ=" + str + ";" + "COUNT=" + j);
                    l.a.a("Calendar Recurrence - count = ").b((double) j);
                }
                if (a.G != null) {
                    a.G.left_application();
                }
                this.b.startActivity(putExtra);
            }
            Toast.makeText(this.b, "Unable to create Calendar Event.", 0).show();
            return;
        }
        if (parse == null) {
            parse = simpleDateFormat2.parse(g2);
            parse2 = simpleDateFormat2.parse(g3);
            date = parse;
        } else {
            date = parse;
        }
        try {
            parse = simpleDateFormat3.parse(g5);
        } catch (Exception e5) {
            parse = null;
        }
        if (date == null) {
            Toast.makeText(this.b, "Unable to create Calendar Event.", 0).show();
            return;
        }
        time = date.getTime();
        time2 = parse2.getTime();
        j = 0;
        if (parse != null) {
            j = (parse.getTime() - date.getTime()) / 1000;
        }
        if (!str.equals("DAILY")) {
            j = (j / 86400) + 1;
        } else if (!str.equals("WEEKLY")) {
            j = (j / 604800) + 1;
        } else if (!str.equals("MONTHLY")) {
            j = (j / 2629800) + 1;
        } else if (str.equals("YEARLY")) {
            j = (j / 31557600) + 1;
        } else {
            j = 0;
        }
        if (replace.equals("")) {
            putExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", g4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, g).putExtra("beginTime", time).putExtra("endTime", time2).putExtra("rrule", "FREQ=" + str + ";" + "COUNT=" + j);
            l.a.a("Calendar Recurrence - count = ").b((double) j);
        } else {
            putExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", g4).putExtra(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, g).putExtra("beginTime", time).putExtra("endTime", time2);
        }
        if (a.G != null) {
            a.G.left_application();
        }
        this.b.startActivity(putExtra);
    }

    void d(HashMap hashMap) {
        l.a.a("ADC [info] MRAIDCommandMail called with parameters: ").b((Object) hashMap);
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        String g = g((String) hashMap.get("subject"));
        String g2 = g((String) hashMap.get("body"));
        String g3 = g((String) hashMap.get("to"));
        a.a("html5_interaction", this.g, this.c.H);
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("plain/text");
            intent.putExtra("android.intent.extra.SUBJECT", g).putExtra("android.intent.extra.TEXT", g2).putExtra("android.intent.extra.EMAIL", new String[]{g3});
            if (a.G != null) {
                a.G.left_application();
            }
            this.b.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.b, "Unable to launch email client.", 0).show();
        }
    }

    void e(HashMap hashMap) {
        l.a.a("ADC [info] MRAIDCommandSMS called with parameters: ").b((Object) hashMap);
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        String g = g((String) hashMap.get("to"));
        String g2 = g((String) hashMap.get("body"));
        a.a("html5_interaction", this.g, this.c.H);
        try {
            if (a.G != null) {
                a.G.left_application();
            }
            this.b.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("sms:" + g)).putExtra("sms_body", g2));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.b, "Failed to create sms.", 0).show();
        }
    }

    void f(HashMap hashMap) {
        l.a.a("ADC [info] MRAIDCommandTel called with parameters: ").b((Object) hashMap);
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        String g = g((String) hashMap.get("number"));
        a.a("html5_interaction", this.g, this.c.H);
        try {
            if (a.G != null) {
                a.G.left_application();
            }
            this.b.startActivity(new Intent("android.intent.action.DIAL").setData(Uri.parse("tel:" + g)));
        } catch (Exception e) {
            Toast.makeText(this.b, "Failed to dial number.", 0).show();
        }
    }

    void g(HashMap hashMap) {
        l.a.a("ADC [info] MRAIDCommandSendCustomADCEvent called with parameters: ").b((Object) hashMap);
        String str = "custom_event";
        a.a(str, "{\"event_type\":\"" + g((String) hashMap.get("event_type")) + "\",\"ad_slot\":" + this.f.h.k.d + "}", this.c.H);
    }

    void h(HashMap hashMap) {
        l.a.a("ADC [info] MRAIDCommandLaunchApp called with parameters: ").b((Object) hashMap);
        a.A = true;
        this.d.postDelayed(this.e, 1000);
        String g = g((String) hashMap.get("handle"));
        a.a("html5_interaction", this.g, this.c.H);
        try {
            if (a.G != null) {
                a.G.left_application();
            }
            this.b.startActivity(this.b.getPackageManager().getLaunchIntentForPackage(g));
        } catch (Exception e) {
            Toast.makeText(this.b, "Failed to launch external application.", 0).show();
        }
    }

    void i(HashMap hashMap) {
        l.a.a("ADC [info] MRAIDCommandCheckAppPresence called with parameters: ").b((Object) hashMap);
        String g = g((String) hashMap.get("handle"));
        f("adc_bridge.fireAppPresenceEvent('" + g + "'," + ab.a(g) + ")");
    }

    void j(HashMap hashMap) {
        l.a.a("ADC [info] MRAIDCommandCheckAutoPlay called with parameters: ").b((Object) hashMap);
    }

    void f(String str) {
        this.a.loadUrl("javascript:" + str);
    }

    String g(String str) {
        if (str == null) {
            return "";
        }
        return URLDecoder.decode(str);
    }
}
