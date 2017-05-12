package com.inmobi.commons.analytics.iat.impl.config;

import android.content.Context;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.cache.CacheController;
import com.inmobi.commons.cache.CacheController.Validator;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.metric.Logger;
import com.inmobi.commons.uid.UID;
import java.util.Map;

public class AdTrackerInitializer {
    public static final String PRODUCT_IAT = "iat";
    private static Context a = null;
    private static Map<String, String> b;
    private static AdTrackerConfigParams c = new AdTrackerConfigParams();
    private static Logger d = new Logger(2, "iat");
    private static Validator e = new Validator() {
        public boolean validate(Map<String, Object> map) {
            return AdTrackerInitializer.b((Map) map);
        }
    };

    public static AdTrackerConfigParams getConfigParams() {
        if (!(InternalSDKUtil.getContext() == null || InMobi.getAppId() == null)) {
            b(InternalSDKUtil.getContext());
        }
        d.setMetricConfigParams(c.getMetric());
        return c;
    }

    public static Logger getLogger() {
        return d;
    }

    private static void a(Context context) {
        if (context == null || a != null) {
            if (a == null && context == null) {
                a.getApplicationContext();
            }
        } else if (a == null) {
            a = context.getApplicationContext();
            b = UID.getInstance().getMapForEncryption(getConfigParams().getDeviceIdMaskMap());
            try {
                b(CacheController.getConfig("iat", context, b, e).getData());
            } catch (Exception e) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Exception while retreiving configs.");
            }
        }
    }

    private static void b(Context context) {
        a(context);
        try {
            CacheController.getConfig("iat", context, b, e);
        } catch (Exception e) {
        }
    }

    public static Map<String, String> getUidMap(Context context) {
        return UID.getInstance().getMapForEncryption(getConfigParams().getDeviceIdMaskMap());
    }

    private static boolean b(Map<String, Object> map) {
        b = getUidMap(a);
        Map populateToNewMap = InternalSDKUtil.populateToNewMap((Map) map.get("AND"), (Map) map.get("common"), true);
        try {
            AdTrackerConfigParams adTrackerConfigParams = new AdTrackerConfigParams();
            adTrackerConfigParams.setFromMap(populateToNewMap);
            c = adTrackerConfigParams;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
