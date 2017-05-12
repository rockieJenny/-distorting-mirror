package com.inmobi.monetization.internal.configs;

import android.content.Context;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.cache.CacheController;
import com.inmobi.commons.cache.CacheController.Validator;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.metric.Logger;
import com.inmobi.commons.uid.UID;
import com.inmobi.monetization.internal.Constants;
import java.util.HashMap;
import java.util.Map;

public class Initializer {
    public static final String PRODUCT_ADNETWORK = "adNetwork";
    private static Context a = null;
    private static Map<String, String> b = new HashMap();
    private static Logger c = new Logger(1, "network");
    private static ConfigParams d = new ConfigParams();
    private static Validator e = new Validator() {
        public boolean validate(Map<String, Object> map) {
            return Initializer.b((Map) map);
        }
    };

    public static Logger getLogger() {
        return c;
    }

    public static ConfigParams getConfigParams() {
        if (!(InternalSDKUtil.getContext() == null || InMobi.getAppId() == null)) {
            b(InternalSDKUtil.getContext());
        }
        return d;
    }

    private static void a(Context context) {
        if (context == null || a != null) {
            if (a == null && context == null) {
                throw new NullPointerException();
            }
        } else if (a == null) {
            a = context.getApplicationContext();
            b = getUidMap(context);
            try {
                b(CacheController.getConfig(PRODUCT_ADNETWORK, context, b, e).getData());
            } catch (Exception e) {
            }
        }
    }

    private static void b(Context context) {
        a(context);
        try {
            CacheController.getConfig(PRODUCT_ADNETWORK, context, b, e);
        } catch (Exception e) {
        }
    }

    public static Map<String, String> getUidMap(Context context) {
        return UID.getInstance().getMapForEncryption(d.getDeviceIdMaskMap());
    }

    private static boolean b(Map<String, Object> map) {
        b = getUidMap(a);
        try {
            Map populateToNewMap = InternalSDKUtil.populateToNewMap((Map) map.get("AND"), (Map) map.get("common"), true);
            ConfigParams configParams = new ConfigParams();
            configParams.setFromMap(populateToNewMap);
            d = configParams;
            c.setMetricConfigParams(configParams.getMetric());
            return true;
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Config couldn't be parsed", e);
            return false;
        }
    }
}
