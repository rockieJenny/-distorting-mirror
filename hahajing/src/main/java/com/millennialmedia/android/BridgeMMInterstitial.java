package com.millennialmedia.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.flurry.android.AdCreative;
import java.util.Map;

class BridgeMMInterstitial extends MMJSObject {
    private static final String CLOSE = "close";
    private static final String EXPAND_TO_EXTERNAL_BROWSER = "expandToExternalBrowser";
    private static final String EXPAND_WITH_PROPERTIES = "expandWithProperties";
    private static final String OPEN = "open";
    private static final String SET_ORIENTATION = "setOrientation";
    private static final String TAG = BridgeMMInterstitial.class.getName();
    private static final String USE_CUSTOM_CLOSE = "useCustomClose";

    BridgeMMInterstitial() {
    }

    public MMJSResponse close(Map<String, String> map) {
        MMWebView mmWebView = (MMWebView) this.mmWebViewRef.get();
        if (mmWebView == null) {
            return null;
        }
        mmWebView.getMMLayout().closeAreaTouched();
        return MMJSResponse.responseWithSuccess();
    }

    public MMJSResponse open(Map<String, String> arguments) {
        String url = (String) arguments.get("url");
        Context context = (Context) this.contextRef.get();
        if (url == null || context == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        Event.intentStarted(context, Event.INTENT_EXTERNAL_BROWSER, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        IntentUtils.startActivity(context, intent);
        return MMJSResponse.responseWithSuccess();
    }

    public MMJSResponse expandToExternalBrowser(Map<String, String> arguments) {
        return open(arguments);
    }

    public MMJSResponse expandWithProperties(Map<String, String> arguments) {
        String isBanner = (String) arguments.get("PROPERTY_BANNER_TYPE");
        if (isBanner != null && !Boolean.parseBoolean(isBanner)) {
            return MMJSResponse.responseWithError("Cannot expand a non banner ad");
        }
        String url = (String) arguments.get("url");
        String transparent = (String) arguments.get("transparent");
        String useCustomClose = (String) arguments.get(USE_CUSTOM_CLOSE);
        String transition = (String) arguments.get("transition");
        String orientation = (String) arguments.get("orientation");
        String transitionDuration = (String) arguments.get("transitionDuration");
        String height = (String) arguments.get("height");
        String width = (String) arguments.get("width");
        String modal = (String) arguments.get("modal");
        String creatorAdImplId = (String) arguments.get("PROPERTY_EXPANDING");
        String allowOrientationChange = (String) arguments.get("allowOrientationChange");
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return null;
        }
        OverlaySettings settings = new OverlaySettings();
        if (url != null) {
            settings.urlToLoad = url;
        }
        if (creatorAdImplId != null) {
            settings.creatorAdImplId = (long) ((int) Float.parseFloat(creatorAdImplId));
        }
        if (transparent != null) {
            settings.setIsTransparent(Boolean.parseBoolean(transparent));
        }
        if (useCustomClose != null) {
            settings.setUseCustomClose(Boolean.parseBoolean(useCustomClose));
        }
        if (transition != null) {
            settings.setTransition(transition);
        }
        if (allowOrientationChange != null) {
            settings.allowOrientationChange = Boolean.parseBoolean(allowOrientationChange);
        }
        if (orientation == null) {
            orientation = (String) arguments.get("forceOrientation");
        }
        if (orientation != null) {
            settings.orientation = orientation;
        }
        if (height != null) {
            settings.height = (int) Float.parseFloat(height);
        }
        if (width != null) {
            settings.width = (int) Float.parseFloat(width);
        }
        if (modal != null) {
            settings.modal = Boolean.parseBoolean(modal);
        }
        if (transitionDuration != null) {
            try {
                settings.setTransitionDurationInMillis(Long.parseLong(transitionDuration) * 1000);
            } catch (Exception e) {
                MMLog.e(TAG, "Problem converting transitionDuration", e);
            }
        }
        IntentUtils.startAdViewOverlayActivity(context, getExpandExtrasIntent(url, settings));
        Event.overlayOpenedBroadCast(context, getAdImplId((String) arguments.get("PROPERTY_EXPANDING")));
        return MMJSResponse.responseWithSuccess();
    }

    private Intent getExpandExtrasIntent(String url, OverlaySettings settings) {
        Intent intent = new Intent();
        if (url != null) {
            intent.setData(Uri.parse(url));
        }
        intent.putExtra("settings", settings);
        intent.putExtra("internalId", settings.creatorAdImplId);
        return intent;
    }

    public MMJSResponse useCustomClose(Map<String, String> arguments) {
        MMWebView mmWebView = (MMWebView) this.mmWebViewRef.get();
        String useCustomClose = (String) arguments.get(USE_CUSTOM_CLOSE);
        if (!(useCustomClose == null || mmWebView == null)) {
            AdViewOverlayView overlayView = mmWebView.getAdViewOverlayView();
            if (overlayView != null) {
                overlayView.setUseCustomClose(Boolean.parseBoolean(useCustomClose));
                return MMJSResponse.responseWithSuccess();
            }
        }
        return null;
    }

    public MMJSResponse setOrientation(Map<String, String> arguments) {
        MMJSResponse response = setForceOrientation(arguments);
        if (response == null || !isForcingOrientation(response)) {
            return setAllowOrientationChange(arguments);
        }
        return response;
    }

    private boolean isForcingOrientation(MMJSResponse response) {
        if (response.result != 1 || !(response.response instanceof String)) {
            return false;
        }
        String result = response.response;
        if (result.contains("portrait") || result.contains("landscape")) {
            return true;
        }
        return false;
    }

    private MMJSResponse setAllowOrientationChange(Map<String, String> arguments) {
        String allowOrientationChange = (String) arguments.get("allowOrientationChange");
        if (allowOrientationChange != null) {
            AdViewOverlayActivity overlayActivity = getBaseActivity();
            if (overlayActivity != null) {
                overlayActivity.setAllowOrientationChange(Boolean.parseBoolean(allowOrientationChange));
                return MMJSResponse.responseWithSuccess();
            }
        }
        return null;
    }

    private MMJSResponse setForceOrientation(Map<String, String> arguments) {
        String forceOrientation = (String) arguments.get("forceOrientation");
        AdViewOverlayActivity overlayActivity = getBaseActivity();
        if (overlayActivity != null) {
            if (AdCreative.kFixNone.equals(forceOrientation)) {
                if (AdCreative.kFixNone.equals(forceOrientation)) {
                    overlayActivity.setAllowOrientationChange(true);
                    return MMJSResponse.responseWithSuccess(AdCreative.kFixNone);
                }
            } else if ("portrait".equals(forceOrientation)) {
                overlayActivity.setRequestedOrientationPortrait();
                return MMJSResponse.responseWithSuccess("portrait");
            } else if ("landscape".equals(forceOrientation)) {
                overlayActivity.setRequestedOrientationLandscape();
                return MMJSResponse.responseWithSuccess("landscape");
            }
        }
        return null;
    }

    MMJSResponse executeCommand(String name, Map<String, String> arguments) {
        if (CLOSE.equals(name)) {
            return close(arguments);
        }
        if (EXPAND_TO_EXTERNAL_BROWSER.equals(name)) {
            return expandToExternalBrowser(arguments);
        }
        if (EXPAND_WITH_PROPERTIES.equals(name)) {
            return expandWithProperties(arguments);
        }
        if (OPEN.equals(name)) {
            return open(arguments);
        }
        if (SET_ORIENTATION.equals(name)) {
            return setOrientation(arguments);
        }
        if (USE_CUSTOM_CLOSE.equals(name)) {
            return useCustomClose(arguments);
        }
        return null;
    }
}
