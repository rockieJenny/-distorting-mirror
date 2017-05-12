package com.inmobi.commons.analytics.bootstrapper;

import android.content.Context;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.analytics.util.AnalyticsUtils;
import com.inmobi.commons.cache.CacheController;
import com.inmobi.commons.cache.CacheController.Validator;
import com.inmobi.commons.cache.ProductConfig;
import com.inmobi.commons.internal.CommonsException;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.ThinICE;
import com.inmobi.commons.uid.UID;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsInitializer {
    public static final String PRODUCT_ANALYTICS = "ltvp";
    private static Context a = null;
    private static Map<String, String> b = new HashMap();
    private static AnalyticsConfigParams c = new AnalyticsConfigParams();
    private static Validator d = new Validator() {
        public boolean validate(Map<String, Object> map) {
            return AnalyticsInitializer.b((Map) map);
        }
    };

    public static AnalyticsConfigParams getRawConfigParams() {
        return c;
    }

    public static AnalyticsConfigParams getConfigParams() {
        if (!(InternalSDKUtil.getContext() == null || InMobi.getAppId() == null)) {
            b(InternalSDKUtil.getContext());
        }
        return c;
    }

    private static void a(Context context) {
        if (context == null || a != null) {
            if (a == null && context == null) {
                a.getApplicationContext();
            }
        } else if (a == null) {
            a = context.getApplicationContext();
            b = getUidMap(context);
            try {
                ProductConfig config = CacheController.getConfig(PRODUCT_ANALYTICS, context, b, d);
                if (config.getRawData() != null) {
                    b(config.getData());
                }
            } catch (CommonsException e) {
                Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Exception while retreiving configs due to commons Exception with code " + e.getCode());
            } catch (Throwable e2) {
                Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Exception while retreiving configs.", e2);
            }
        }
    }

    private static void b(Context context) {
        a(context);
        b = getUidMap(a);
        try {
            CacheController.getConfig(PRODUCT_ANALYTICS, context, b, d);
        } catch (Exception e) {
        }
    }

    public static Map<String, String> getUidMap(Context context) {
        return UID.getInstance().getMapForEncryption(null);
    }

    private static boolean b(Map<String, Object> map) {
        AnalyticsConfigParams analyticsConfigParams = new AnalyticsConfigParams();
        try {
            analyticsConfigParams.setFromMap((Map) map.get("common"));
            c = analyticsConfigParams;
            ThinICE.setConfig(analyticsConfigParams.getThinIceConfig());
            return true;
        } catch (Throwable e) {
            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Exception while saving configs.", e);
            return false;
        }
    }
}
