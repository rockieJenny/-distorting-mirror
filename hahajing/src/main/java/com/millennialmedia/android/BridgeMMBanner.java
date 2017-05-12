package com.millennialmedia.android;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import java.util.Map;

class BridgeMMBanner extends MMJSObject {
    static final String RESIZE = "resize";

    BridgeMMBanner() {
    }

    public MMJSResponse resize(Map<String, String> arguments) {
        MMWebView mmWebView = (MMWebView) this.mmWebViewRef.get();
        if (mmWebView == null) {
            return null;
        }
        if (mmWebView.isMraidResized()) {
            return MMJSResponse.responseWithError("State is currently resized");
        }
        String widthArg = (String) arguments.get("width");
        String heightArg = (String) arguments.get("height");
        int width = 0;
        int height = 0;
        if (!TextUtils.isEmpty(widthArg)) {
            width = (int) Float.parseFloat(widthArg);
        }
        if (!TextUtils.isEmpty(heightArg)) {
            height = (int) Float.parseFloat(heightArg);
        }
        String customClosePosition = (String) arguments.get("customClosePosition");
        String offsetXArg = (String) arguments.get("offsetX");
        String offsetYArg = (String) arguments.get("offsetY");
        int offsetY = 0;
        int offsetX = 0;
        if (!TextUtils.isEmpty(offsetYArg)) {
            offsetY = (int) Float.parseFloat(offsetYArg);
        }
        if (!TextUtils.isEmpty(offsetXArg)) {
            offsetX = (int) Float.parseFloat(offsetXArg);
        }
        boolean allowOffScreen = Boolean.parseBoolean((String) arguments.get("allowOffscreen"));
        Context context = mmWebView.getContext();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mmWebView.setMraidResize(new DTOResizeParameters(metrics.density, width, height, customClosePosition, offsetX, offsetY, allowOffScreen, getScreenWidth(context), getScreenHeight(context)));
        return MMJSResponse.responseWithSuccess();
    }

    int getScreenHeight(Context context) {
        return Integer.parseInt(MMSDK.getDpiHeight(context));
    }

    int getScreenWidth(Context context) {
        return Integer.parseInt(MMSDK.getDpiWidth(context));
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (RESIZE.equals(name)) {
            return resize(arguments);
        }
        return null;
    }
}
