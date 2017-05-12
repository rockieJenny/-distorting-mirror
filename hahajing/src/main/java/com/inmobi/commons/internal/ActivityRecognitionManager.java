package com.inmobi.commons.internal;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ActivityRecognitionManager extends IntentService {
    static Object a = null;
    static Object b = null;
    static int c = -1;
    private static Object d = null;
    private static Object e = null;

    private static class a implements InvocationHandler {
        private a() {
        }

        public void a(Bundle bundle) {
            try {
                PendingIntent service = PendingIntent.getService(InternalSDKUtil.getContext().getApplicationContext(), 0, new Intent(InternalSDKUtil.getContext().getApplicationContext(), ActivityRecognitionManager.class), 134217728);
                if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(InternalSDKUtil.getContext()) == 0) {
                    try {
                        Class.forName("com.google.android.gms.location.ActivityRecognitionClient").getMethod("requestActivityUpdates", new Class[]{Integer.class, PendingIntent.class}).invoke(ActivityRecognitionManager.b, new Object[]{Integer.valueOf(1000), service});
                        return;
                    } catch (Exception e) {
                        Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to request activity updates from ActivityRecognition client");
                        Class.forName("com.google.android.gms.common.GooglePlayServicesClient").getMethod("disconnect", (Class[]) null).invoke(ActivityRecognitionManager.b, (Object[]) null);
                        return;
                    }
                }
                Class.forName("com.google.android.gms.common.GooglePlayServicesClient").getMethod("disconnect", (Class[]) null).invoke(ActivityRecognitionManager.b, (Object[]) null);
            } catch (Exception e2) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to request activity updates from ActivityRecognition client");
            }
        }

        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            if (objArr != null) {
                try {
                    if (method.getName().equals("onConnected")) {
                        a((Bundle) objArr[0]);
                    }
                } catch (Throwable e) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to invoke method", e);
                }
            }
            return null;
        }
    }

    public ActivityRecognitionManager() {
        super("InMobi activity service");
    }

    private static boolean a() {
        boolean z = true;
        if (VERSION.SDK_INT < 8) {
            return false;
        }
        if (c == -1) {
            try {
                a = Class.forName("com.google.android.gms.location.DetectedActivity").getConstructor(new Class[]{Integer.TYPE, Integer.TYPE}).newInstance(new Object[]{Integer.valueOf(-1), Integer.valueOf(100)});
                c = 1;
                if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(InternalSDKUtil.getContext()) != 0) {
                    c = 0;
                    return false;
                }
            } catch (ClassNotFoundException e) {
                Log.debug(InternalSDKUtil.LOGGING_TAG, "Google play services not included.");
                c = 0;
            } catch (Exception e2) {
                Log.debug(InternalSDKUtil.LOGGING_TAG, "Google play services not included.");
                c = 0;
            }
        }
        if (c != 1) {
            z = false;
        }
        return z;
    }

    public static void init(Context context) {
        if (a()) {
            a(context);
        }
    }

    private static void a(Context context) {
        Class cls = null;
        try {
            if (a != null) {
                Class cls2 = Class.forName("com.google.android.gms.common.GooglePlayServicesClient");
                Class cls3 = Class.forName("com.google.android.gms.location.ActivityRecognitionClient");
                if (b != null) {
                    cls2.getMethod("disconnect", (Class[]) null).invoke(b, (Object[]) null);
                }
                Class[] declaredClasses = cls2.getDeclaredClasses();
                int length = declaredClasses.length;
                int i = 0;
                Class cls4 = null;
                while (i < length) {
                    Class cls5 = declaredClasses[i];
                    if (cls5.getSimpleName().equalsIgnoreCase("ConnectionCallbacks")) {
                        d = Proxy.newProxyInstance(cls5.getClassLoader(), new Class[]{cls5}, new a());
                    } else if (cls5.getSimpleName().equalsIgnoreCase("OnConnectionFailedListener")) {
                        e = Proxy.newProxyInstance(cls5.getClassLoader(), new Class[]{cls5}, new a());
                        cls4 = cls5;
                        cls5 = cls;
                    } else {
                        cls5 = cls;
                    }
                    i++;
                    cls = cls5;
                }
                b = cls3.getDeclaredConstructor(new Class[]{Context.class, cls, cls4}).newInstance(new Object[]{context, d, e});
                cls2.getMethod("connect", (Class[]) null).invoke(b, (Object[]) null);
            }
        } catch (ClassCastException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Init: Google play services not included. Cannot get current activity.");
        } catch (Throwable e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Init: Something went wrong during ActivityRecognitionManager.init", e2);
        }
    }

    protected void onHandleIntent(Intent intent) {
        if (a()) {
            a(intent);
        }
    }

    private void a(Intent intent) {
        try {
            Class cls = Class.forName("com.google.android.gms.location.ActivityRecognitionResult");
            if (((Boolean) cls.getMethod("hasResult", new Class[]{Intent.class}).invoke(null, new Object[]{intent})).booleanValue()) {
                a = cls.getMethod("getMostProbableActivity", (Class[]) null).invoke(cls.getMethod("extractResult", new Class[]{Intent.class}).invoke(null, new Object[]{intent}), (Object[]) null);
            }
        } catch (ClassNotFoundException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "HandleIntent: Google play services not included. Cannot get current activity.");
        } catch (Exception e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "HandleIntent: Google play services not included. Cannot get current activity.");
        }
    }

    public static int getDetectedActivity() {
        try {
            if (a == null) {
                return -1;
            }
            return ((Integer) Class.forName("com.google.android.gms.location.DetectedActivity").getMethod("getType", (Class[]) null).invoke(a, (Object[]) null)).intValue();
        } catch (ClassNotFoundException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "getDetectedActivity: Google play services not included. Returning null.");
            return -1;
        } catch (Exception e2) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "getDetectedActivity: Google play services not included. Returning null.");
            return -1;
        }
    }
}
