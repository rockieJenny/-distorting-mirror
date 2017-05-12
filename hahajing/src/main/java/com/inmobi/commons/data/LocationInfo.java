package com.inmobi.commons.data;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.util.List;

public class LocationInfo {
    public static int LOCATION_SET_BY_PUB = 0;
    public static int LOCATION_SET_BY_SDK = 1;
    private static LocationManager a;
    private static double b;
    private static double c;
    private static double d;
    private static boolean e;
    private static long f;
    private static int g = LOCATION_SET_BY_SDK;

    private static synchronized LocationManager a() {
        LocationManager locationManager;
        synchronized (LocationInfo.class) {
            locationManager = a;
        }
        return locationManager;
    }

    private static synchronized void a(LocationManager locationManager) {
        synchronized (LocationInfo.class) {
            a = locationManager;
        }
    }

    public static boolean isLocationPermissionsDenied() {
        int checkCallingOrSelfPermission = InternalSDKUtil.getContext().checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION");
        int checkCallingOrSelfPermission2 = InternalSDKUtil.getContext().checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION");
        if (checkCallingOrSelfPermission == 0 || checkCallingOrSelfPermission2 == 0) {
            return false;
        }
        return true;
    }

    public static double getLat() {
        return b;
    }

    private static void a(double d) {
        b = d;
    }

    public static double getLon() {
        return c;
    }

    private static void b(double d) {
        c = d;
    }

    public static double getLocAccuracy() {
        return d;
    }

    private static void c(double d) {
        d = d;
    }

    public static boolean isValidGeoInfo() {
        return e;
    }

    static void a(boolean z) {
        e = z;
    }

    public static long getGeoTS() {
        return f;
    }

    private static void a(long j) {
        f = j;
    }

    private static boolean b() {
        try {
            if (a() == null) {
                a((LocationManager) InternalSDKUtil.getContext().getSystemService("location"));
            }
            if (a() != null) {
                LocationManager a = a();
                Criteria criteria = new Criteria();
                if (InternalSDKUtil.getContext().checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                    criteria.setAccuracy(1);
                } else if (InternalSDKUtil.getContext().checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                    criteria.setAccuracy(2);
                }
                criteria.setCostAllowed(false);
                String bestProvider = a.getBestProvider(criteria, true);
                if (bestProvider != null) {
                    boolean isProviderEnabled = a.isProviderEnabled("network");
                    boolean isProviderEnabled2 = a.isProviderEnabled("gps");
                    if (!isProviderEnabled && !isProviderEnabled2) {
                        return false;
                    }
                    Location lastKnownLocation = a.getLastKnownLocation(bestProvider);
                    if (lastKnownLocation == null) {
                        lastKnownLocation = c();
                        Log.debug(InternalSDKUtil.LOGGING_TAG, "lastKnownLocation: " + lastKnownLocation);
                    }
                    if (lastKnownLocation == null) {
                        return false;
                    }
                    Log.debug(InternalSDKUtil.LOGGING_TAG, "lastBestKnownLocation: " + lastKnownLocation);
                    a(lastKnownLocation);
                    return true;
                }
            }
            return true;
        } catch (Throwable e) {
            Log.debug(InternalSDKUtil.LOGGING_TAG, "Error getting the Location Info ", e);
            return false;
        }
    }

    private static Location c() {
        if (a() == null) {
            a((LocationManager) InternalSDKUtil.getContext().getSystemService("location"));
        }
        if (a() != null) {
            LocationManager a = a();
            List providers = a.getProviders(true);
            for (int size = providers.size() - 1; size >= 0; size--) {
                String str = (String) providers.get(size);
                if (a.isProviderEnabled(str)) {
                    Location lastKnownLocation = a.getLastKnownLocation(str);
                    if (lastKnownLocation != null) {
                        return lastKnownLocation;
                    }
                }
            }
        }
        return null;
    }

    static void a(Location location) {
        if (location != null) {
            a(true);
            a(location.getLatitude());
            b(location.getLongitude());
            c((double) location.getAccuracy());
            a(location.getTime());
            return;
        }
        a(false);
    }

    public static synchronized void collectLocationInfo() {
        synchronized (LocationInfo.class) {
            try {
                if (!isLocationPermissionsDenied() && b()) {
                    setSDKLocation(LOCATION_SET_BY_SDK);
                }
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Exception updating loc info", e);
            }
        }
    }

    public static String currentLocationStr() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!DemogInfo.isLocationInquiryAllowed()) {
            if (DemogInfo.getCurrentLocation() != null) {
                a(DemogInfo.getCurrentLocation());
            } else {
                a(null);
            }
            setSDKLocation(LOCATION_SET_BY_PUB);
        } else if (!(isValidGeoInfo() || DemogInfo.getCurrentLocation() == null)) {
            a(DemogInfo.getCurrentLocation());
            setSDKLocation(LOCATION_SET_BY_PUB);
        }
        if (stringBuilder == null || !isValidGeoInfo()) {
            return "";
        }
        stringBuilder.append(getLat());
        stringBuilder.append(",");
        stringBuilder.append(getLon());
        stringBuilder.append(",");
        stringBuilder.append((int) getLocAccuracy());
        return stringBuilder.toString();
    }

    public static void setSDKLocation(int i) {
        g = i;
    }

    public static int isSDKSetLocation() {
        return g;
    }
}
