package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import java.util.Map;

class d implements b {
    private final Context lM;

    public d(Context context) {
        this.lM = context;
    }

    public void C(Map<String, Object> map) {
        Object obj;
        Object obj2 = map.get("gtm.url");
        if (obj2 == null) {
            obj = map.get("gtm");
            if (obj != null && (obj instanceof Map)) {
                obj = ((Map) obj).get("url");
                if (obj != null && (obj instanceof String)) {
                    String queryParameter = Uri.parse((String) obj).getQueryParameter(AdTrackerConstants.REFERRER);
                    if (queryParameter != null) {
                        ay.f(this.lM, queryParameter);
                        return;
                    }
                    return;
                }
            }
        }
        obj = obj2;
        if (obj != null) {
        }
    }
}
