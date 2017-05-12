package com.inmobi.commons.analytics.bootstrapper;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.inmobi.commons.internal.InternalSDKUtil;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Map;

public class AutomaticCaptureConfig {
    private boolean a = true;
    private boolean b = false;
    private boolean c = false;

    public boolean isAutoPurchaseCaptureEnabled() {
        return this.b;
    }

    public boolean isAutoSessionCaptureEnabled() {
        return this.a;
    }

    public boolean isAutoLocationCaptureEnabled() {
        return this.c;
    }

    public void setFromMap(Map<String, Object> map) {
        this.a = InternalSDKUtil.getBooleanFromMap(map, SettingsJsonConstants.SESSION_KEY);
        this.b = InternalSDKUtil.getBooleanFromMap(map, ProductAction.ACTION_PURCHASE);
        this.c = InternalSDKUtil.getBooleanFromMap(map, "location");
    }
}
