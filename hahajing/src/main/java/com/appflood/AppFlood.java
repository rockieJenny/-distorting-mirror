package com.appflood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.appflood.b.b;
import com.appflood.c.d;
import com.appflood.c.e;
import com.appflood.c.e.AnonymousClass1;
import com.appflood.c.e.AnonymousClass10;
import com.appflood.c.e.AnonymousClass3;
import com.appflood.c.e.AnonymousClass6;
import com.appflood.c.e.AnonymousClass7;
import com.appflood.c.e.AnonymousClass8;
import com.appflood.c.e.AnonymousClass9;
import com.appflood.c.g;
import com.appflood.e.j;
import com.appflood.e.k;
import java.util.HashMap;
import org.json.JSONObject;

public class AppFlood {
    public static final int AD_ALL = 255;
    public static final int AD_BANNER = 1;
    public static final int AD_DATA = 16;
    public static final int AD_FULLSCREEN = 4;
    public static final int AD_ICON = 64;
    public static final int AD_INTERSTITIAL = 128;
    public static final int AD_LIST = 8;
    public static final int AD_NONE = 0;
    public static final int AD_NOTIFICATION = 32;
    public static final int AD_PANEL = 2;
    public static final int BANNER_CUSTOM = 14;
    public static final int BANNER_INTERSTITIAL = 13;
    public static final int BANNER_LARGE = 10;
    public static final int BANNER_MIDDLE = 16;
    public static final int BANNER_POSITION_BOTTOM = 1;
    public static final int BANNER_POSITION_TOP = 0;
    public static final int BANNER_SMALL = 17;
    public static final int BANNER_WEB_FULLSCREEN = 15;
    public static int ICON_STYLE_MULTIPLE = 2;
    public static int ICON_STYLE_SINGLE = 1;
    public static final int LIST_ALL = 2;
    public static final int LIST_APP = 1;
    public static final int LIST_GAME = 0;
    public static final int LIST_TAB_APP = 4;
    public static final int LIST_TAB_GAME = 3;
    public static int NOTIFICATION_STYLE_BANNER = 2;
    public static int NOTIFICATION_STYLE_TEXT = 1;
    public static final int PANEL_BOTTOM = 1;
    public static final int PANEL_LANDSCAPE = 20;
    public static final int PANEL_PORTRAIT = 21;
    public static final int PANEL_TOP = 0;
    public static final int REGION_AUTO = 2;
    public static final int REGION_CHINA = 0;
    public static final int REGION_GLOBAL = 1;

    public interface AFBannerShowDelegate {
        void onResume();
    }

    public interface AFEventDelegate {
        void onClick(JSONObject jSONObject);

        void onClose(JSONObject jSONObject);

        void onFinish(boolean z, int i);
    }

    public interface AFRequestDelegate {
        void onFinish(JSONObject jSONObject);
    }

    public static void consumeAFPoint(int i, AFRequestDelegate aFRequestDelegate) {
        e a = e.a();
        if (a.b) {
            d.a(new AnonymousClass8(a, aFRequestDelegate, i));
        } else {
            j.c("AppFlood not initialized");
        }
    }

    public static void destroy() {
        e.a().b();
    }

    public static void getADData(AFRequestDelegate aFRequestDelegate) {
        if ((d.F & 16) > 0) {
            e a = e.a();
            if (a.b) {
                d.a(new AnonymousClass10(a, aFRequestDelegate));
                return;
            } else {
                j.c("AppFlood not initialized");
                return;
            }
        }
        j.c("AD_TYPE not supported. Please initialize AppFlood with correct parameters.");
    }

    public static int getAdType() {
        return d.F;
    }

    public static AFEventDelegate getEventDelegate() {
        return e.a().a;
    }

    public static String getVersion() {
        return "V2.21";
    }

    public static void handleAFClick(Activity activity, String str, String str2) {
        if (!e.a().b) {
            j.c("AppFlood not initialized");
        } else if (str != null && str2 != null) {
            try {
                new b(str, null).b(true);
                if (!str2.contains("://")) {
                    str2 = k.b(str2, null).toString();
                }
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str2)));
            } catch (Throwable th) {
                j.b(th, "error in handleClick");
            }
        }
    }

    public static void initialize(Context context, String str, String str2, int i) {
        initialize(context, str, str2, null, null, i, 1);
    }

    public static void initialize(Context context, String str, String str2, String str3, String str4, int i, int i2) {
        e.a().a(context, str, str2, str3, str4, i, i2);
    }

    public static boolean isConnected() {
        return d.j;
    }

    public static void preload(int i, AFRequestDelegate aFRequestDelegate) {
        preload(i, aFRequestDelegate, 2);
    }

    public static void preload(int i, AFRequestDelegate aFRequestDelegate, int i2) {
        e a = e.a();
        if (a.b) {
            d.a(new AnonymousClass3(a, i, aFRequestDelegate, i2));
        } else {
            j.c("AppFlood not initialized");
        }
    }

    public static void preloadBanner(int i, AFRequestDelegate aFRequestDelegate) {
        e.a();
        g.a().a(i, aFRequestDelegate, true);
    }

    public static void queryAFPoint(AFRequestDelegate aFRequestDelegate) {
        e a = e.a();
        if (a.b) {
            d.a(new AnonymousClass9(a, aFRequestDelegate));
        } else {
            j.c("AppFlood not initialized");
        }
    }

    public static void setEventDelegate(AFEventDelegate aFEventDelegate) {
        e.a().a = aFEventDelegate;
    }

    public static void setUserData(HashMap<String, Object> hashMap) {
        e.a();
        if (hashMap == null || hashMap.isEmpty()) {
            j.c("UserData is null or empty!");
        }
        d.i = hashMap;
    }

    public static void showBanner(Activity activity, int i, int i2) {
        if (!d.H) {
            return;
        }
        if ((d.F & 1) > 0) {
            e.a();
            View aFBannerView = new AFBannerView((Context) activity, i2);
            LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
            int i3 = 12;
            switch (i) {
                case 0:
                    i3 = 48;
                    break;
                case 1:
                    i3 = 80;
                    break;
            }
            layoutParams.gravity = i3;
            activity.addContentView(aFBannerView, layoutParams);
            return;
        }
        j.c("AD_TYPE not supported. Please initialize AppFlood with correct parameters.");
    }

    public static void showFullScreen(Activity activity) {
        if (!d.H) {
            return;
        }
        if ((d.F & 4) > 0) {
            e.a().a(activity);
        } else {
            j.c("AD_TYPE not supported. Please initialize AppFlood with correct parameters.");
        }
    }

    public static void showInterstitial(Activity activity) {
        if (!d.H) {
            return;
        }
        if ((d.F & 128) > 0) {
            e a = e.a();
            if (!a.b) {
                j.c("AppFlood not initialized");
                return;
            } else if (activity == null) {
                j.c("context con is null");
                return;
            } else {
                d.a(activity, new AnonymousClass7(a, activity));
                return;
            }
        }
        j.c("AD_TYPE not supported. Please initialize AppFlood with correct parameters.");
    }

    public static void showList(Activity activity, int i) {
        if (!d.H) {
            return;
        }
        if ((d.F & 8) <= 0) {
            j.c("AD_TYPE not supported. Please initialize AppFlood with correct parameters.");
        } else if (!e.a().b) {
            j.c("AppFlood not initialized");
        } else if (activity == null) {
            j.c("activity is null");
        } else {
            d.a(activity, new AnonymousClass1(activity, i));
        }
    }

    public static void showPanel(Activity activity, int i) {
        if (!d.H) {
            return;
        }
        if ((d.F & 2) <= 0) {
            j.c("AD_TYPE not supported. Please initialize AppFlood with correct parameters.");
        } else if (!e.a().b) {
            j.c("AppFlood not initialized");
        } else if (activity == null) {
            j.c("context con is null");
        } else {
            d.a(activity, new AnonymousClass6(activity, i));
        }
    }

    public static void showWaitingLoading(boolean z) {
        d.I = z;
    }
}
