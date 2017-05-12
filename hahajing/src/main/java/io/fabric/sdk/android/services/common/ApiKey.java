package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.os.Bundle;
import io.fabric.sdk.android.Fabric;
import org.xmlrpc.android.IXMLRPCSerializer;

public class ApiKey {
    static final String CRASHLYTICS_API_KEY = "com.crashlytics.ApiKey";

    public static String getApiKey(Context context) {
        return getApiKey(context, false);
    }

    public static String getApiKey(Context context, boolean debug) {
        String apiKey = null;
        try {
            Context applicationContext = context.getApplicationContext();
            Bundle bundle = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128).metaData;
            if (bundle != null) {
                apiKey = bundle.getString(CRASHLYTICS_API_KEY);
            }
        } catch (Exception e) {
            Fabric.getLogger().d("Fabric", "Caught non-fatal exception while retrieving apiKey: " + e);
        }
        if (CommonUtils.isNullOrEmpty(apiKey)) {
            int id = CommonUtils.getResourcesIdentifier(context, CRASHLYTICS_API_KEY, IXMLRPCSerializer.TYPE_STRING);
            if (id != 0) {
                apiKey = context.getResources().getString(id);
            }
        }
        if (CommonUtils.isNullOrEmpty(apiKey)) {
            if (debug || CommonUtils.isAppDebuggable(context)) {
                throw new IllegalArgumentException(buildApiKeyInstructions());
            }
            Fabric.getLogger().e("Fabric", buildApiKeyInstructions(), null);
        }
        return apiKey;
    }

    private static String buildApiKeyInstructions() {
        return "Crashlytics could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"com.crashlytics.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }
}
