package com.inmobi.commons.internal;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebView;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WrapperFunctions {
    private static int a;

    public static int getParamConfigScreenSize() {
        int i = 0;
        ActivityInfo activityInfo = new ActivityInfo();
        try {
            Field field = ActivityInfo.class.getField("CONFIG_SCREEN_SIZE");
            if (field != null) {
                i = field.getInt(activityInfo);
            }
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get screen size", e);
        } catch (Throwable e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get screen size", e2);
        }
        return i;
    }

    public static int getParamConfigSmallestScreenSize() {
        int i = 0;
        ActivityInfo activityInfo = new ActivityInfo();
        try {
            Field field = ActivityInfo.class.getField("CONFIG_SMALLEST_SCREEN_SIZE");
            if (field != null) {
                i = field.getInt(activityInfo);
            }
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get smallest screen size", e);
        } catch (Throwable e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get smallest screen size", e2);
        }
        return i;
    }

    public static int getParamPortraitOrientation(int i) {
        int i2 = 1;
        if (i == 2) {
            ActivityInfo activityInfo = new ActivityInfo();
            try {
                Field field = ActivityInfo.class.getField("SCREEN_ORIENTATION_REVERSE_PORTRAIT");
                if (field != null) {
                    i2 = field.getInt(activityInfo);
                }
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get portrait orientation", e);
            } catch (Throwable e2) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get portrait orientation", e2);
            }
        }
        return i2;
    }

    public static int getParamLandscapeOrientation(int i) {
        int i2 = 0;
        if (i == 3) {
            ActivityInfo activityInfo = new ActivityInfo();
            try {
                Field field = ActivityInfo.class.getField("SCREEN_ORIENTATION_REVERSE_LANDSCAPE");
                if (field != null) {
                    i2 = field.getInt(activityInfo);
                }
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get landscape orientation", e);
            } catch (Throwable e2) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get landscape orientation", e2);
            }
        }
        return i2;
    }

    public static int getDisplayWidth(Display display) {
        int i;
        Method method = null;
        try {
            method = Display.class.getMethod("getSize", new Class[]{Point.class});
            i = 1;
        } catch (NoSuchMethodException e) {
            try {
                method = Display.class.getMethod("getWidth", (Class[]) null);
                i = 0;
            } catch (Throwable e2) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display width", e2);
                i = 0;
            }
        }
        if (i == 0) {
            return ((Integer) method.invoke(display, (Object[]) null)).intValue();
        }
        try {
            method.invoke(display, new Object[]{new Point()});
            return new Point().x;
        } catch (Throwable e22) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display width", e22);
            return 0;
        } catch (Throwable e222) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display width", e222);
            return 0;
        } catch (Throwable e2222) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display width", e2222);
            return 0;
        }
    }

    public static int getDisplayHeight(Display display) {
        int i;
        Method method = null;
        try {
            method = Display.class.getMethod("getSize", new Class[]{Point.class});
            i = 1;
        } catch (NoSuchMethodException e) {
            try {
                method = Display.class.getMethod("getHeight", (Class[]) null);
                i = 0;
            } catch (Throwable e2) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display height", e2);
                i = 0;
            }
        }
        if (i == 0) {
            return ((Integer) method.invoke(display, (Object[]) null)).intValue();
        }
        try {
            method.invoke(display, new Object[]{new Point()});
            return new Point().y;
        } catch (Throwable e22) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display height", e22);
            return 0;
        } catch (Throwable e222) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display height", e222);
            return 0;
        } catch (Throwable e2222) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get display height", e2222);
            return 0;
        }
    }

    public static int getParamFillParent() {
        if (a == 0) {
            synchronized (WrapperFunctions.class) {
                if (a == 0) {
                    Field field;
                    LayoutParams layoutParams = new LayoutParams(1, 1);
                    try {
                        field = LayoutParams.class.getField("MATCH_PARENT");
                    } catch (NoSuchFieldException e) {
                        try {
                            field = r0.getField("FILL_PARENT");
                        } catch (Throwable e2) {
                            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get fill parent param", e2);
                            field = null;
                        }
                    }
                    if (field != null) {
                        try {
                            a = field.getInt(layoutParams);
                        } catch (Throwable e22) {
                            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get fill parent param", e22);
                        }
                    }
                }
            }
        }
        return a;
    }

    public static Bitmap getVideoBitmap(String str) throws Exception {
        try {
            return (Bitmap) Class.forName("android.media.ThumbnailUtils").getDeclaredMethod("createVideoThumbnail", new Class[]{String.class, Integer.TYPE}).invoke(null, new Object[]{str, Integer.valueOf(1)});
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get video bitmap", e);
            throw e;
        }
    }

    private static void a(WebView webView, int i) {
        try {
            webView.getClass().getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(webView, new Object[]{Integer.valueOf(i), null});
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot set hardware accl", e);
        } catch (Throwable e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot set hardware accl", e2);
        } catch (Throwable e22) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot set hardware accl", e22);
        } catch (Throwable e222) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot set hardware accl", e222);
        }
    }

    public static void disableHardwareAccl(WebView webView) {
        a(webView, 1);
    }

    public static String getSSLErrorUrl(SslError sslError) {
        try {
            return Class.forName("android.net.http.SslError").getDeclaredMethod("getUrl", new Class[0]).invoke(sslError, new Object[0]).toString();
        } catch (Throwable e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Cannot get SSL Url", e);
            return null;
        }
    }

    public static int getCurrentOrientationInFixedValues(Context context) {
        int rotation;
        switch (context.getResources().getConfiguration().orientation) {
            case 1:
                if (VERSION.SDK_INT < 8) {
                    return 1;
                }
                rotation = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
                return (rotation == 1 || rotation == 2) ? 9 : 1;
            case 2:
                if (VERSION.SDK_INT < 8) {
                    return 0;
                }
                rotation = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
                if (rotation == 0 || rotation == 1) {
                    return 0;
                }
                return 8;
            default:
                return 1;
        }
    }
}
