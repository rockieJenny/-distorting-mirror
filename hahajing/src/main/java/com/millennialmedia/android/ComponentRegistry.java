package com.millennialmedia.android;

import java.util.Stack;

class ComponentRegistry {
    static Stack<BridgeMMBanner> bannerBridges = new Stack();
    static Stack<BridgeMMCachedVideo> cachedVideoBridges = new Stack();
    static Stack<BridgeMMCalendar> calendarBridges = new Stack();
    static Stack<BridgeMMDevice> deviceBridges = new Stack();
    static Stack<ExampleComponent> examples = new Stack();
    static Stack<BridgeMMInlineVideo> inlineVideoBridges = new Stack();
    static Stack<BridgeMMInterstitial> interstitialBridges = new Stack();
    static Stack<LoggingComponent> loggingComponents = new Stack();
    static Stack<BridgeMMMedia> mediaBridges = new Stack();
    static Stack<BridgeMMNotification> notificationBridges = new Stack();
    static Stack<BridgeMMSpeechkit> speechkitBridges = new Stack();

    ComponentRegistry() {
    }

    static void addExample(ExampleComponent testComponent) {
        examples.push(testComponent);
    }

    static ExampleComponent getExample() {
        return (ExampleComponent) getComponent(examples);
    }

    static void removeExample(boolean force) {
        removeComponent(force, examples);
    }

    static void addBannerBridge(BridgeMMBanner bannerBridge) {
        bannerBridges.push(bannerBridge);
    }

    static BridgeMMBanner getBannerBridge() {
        return (BridgeMMBanner) getComponent(bannerBridges);
    }

    static void removeBannerBridge(boolean force) {
        removeComponent(force, bannerBridges);
    }

    static void addCachedVideoBridge(BridgeMMCachedVideo cachedVideoBridge) {
        cachedVideoBridges.push(cachedVideoBridge);
    }

    static BridgeMMCachedVideo getCachedVideoBridge() {
        return (BridgeMMCachedVideo) getComponent(cachedVideoBridges);
    }

    static void removeCachedVideoBridge(boolean force) {
        removeComponent(force, cachedVideoBridges);
    }

    static void addCalendarBridge(BridgeMMCalendar calendarBridge) {
        calendarBridges.push(calendarBridge);
    }

    static BridgeMMCalendar getCalendarBridge() {
        return (BridgeMMCalendar) getComponent(calendarBridges);
    }

    static void removeCalendarBridge(boolean force) {
        removeComponent(force, calendarBridges);
    }

    static void addDeviceBridge(BridgeMMDevice deviceBridge) {
        deviceBridges.push(deviceBridge);
    }

    static BridgeMMDevice getDeviceBridge() {
        return (BridgeMMDevice) getComponent(deviceBridges);
    }

    static void removeDeviceBridge(boolean force) {
        removeComponent(force, deviceBridges);
    }

    static void addInlineVideoBridge(BridgeMMInlineVideo inlineVideoBridge) {
        inlineVideoBridges.push(inlineVideoBridge);
    }

    static BridgeMMInlineVideo getInlineVideoBridge() {
        return (BridgeMMInlineVideo) getComponent(inlineVideoBridges);
    }

    static void removeInlineVideoBridge(boolean force) {
        removeComponent(force, inlineVideoBridges);
    }

    static void addInterstitialBridge(BridgeMMInterstitial interstitialBridge) {
        interstitialBridges.push(interstitialBridge);
    }

    static BridgeMMInterstitial getInterstitialBridge() {
        return (BridgeMMInterstitial) getComponent(interstitialBridges);
    }

    static void removeInterstitialBridge(boolean force) {
        removeComponent(force, interstitialBridges);
    }

    static void addMediaBridge(BridgeMMMedia mediaBridge) {
        mediaBridges.push(mediaBridge);
    }

    static BridgeMMMedia getMediaBridge() {
        return (BridgeMMMedia) getComponent(mediaBridges);
    }

    static void removeMediaBridge(boolean force) {
        removeComponent(force, mediaBridges);
    }

    static void addNotificationBridge(BridgeMMNotification notificationBridge) {
        notificationBridges.push(notificationBridge);
    }

    static BridgeMMNotification getNotificationBridge() {
        return (BridgeMMNotification) getComponent(notificationBridges);
    }

    static void removeNotificationBridge(boolean force) {
        removeComponent(force, notificationBridges);
    }

    static void addSpeechkitBridge(BridgeMMSpeechkit speechkitBridge) {
        speechkitBridges.push(speechkitBridge);
    }

    static BridgeMMSpeechkit getSpeechkitBridge() {
        return (BridgeMMSpeechkit) getComponent(speechkitBridges);
    }

    static void removeSpeechkitBridge(boolean force) {
        removeComponent(force, speechkitBridges);
    }

    static void addLoggingComponent(LoggingComponent loggingComponent) {
        loggingComponents.push(loggingComponent);
    }

    static LoggingComponent getLoggingComponent() {
        return (LoggingComponent) getComponent(loggingComponents);
    }

    static void removeLoggingComponent(boolean force) {
        removeComponent(force, loggingComponents);
    }

    private static <T> T getComponent(Stack<T> components) {
        if (components.isEmpty()) {
            return null;
        }
        return components.lastElement();
    }

    private static <T> void removeComponent(boolean force, Stack<T> components) {
        if (!components.isEmpty()) {
            if (components.size() != 1 || force) {
                components.pop();
            }
        }
    }
}
