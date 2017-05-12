package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.events.EnabledEventsStrategy;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.concurrent.ScheduledExecutorService;

class EnabledSessionAnalyticsManagerStrategy extends EnabledEventsStrategy<SessionEvent> implements SessionAnalyticsManagerStrategy<SessionEvent> {
    FilesSender filesSender;
    private final HttpRequestFactory httpRequestFactory;

    public EnabledSessionAnalyticsManagerStrategy(Context context, ScheduledExecutorService executorService, SessionAnalyticsFilesManager filesManager, HttpRequestFactory httpRequestFactory) {
        super(context, executorService, filesManager);
        this.httpRequestFactory = httpRequestFactory;
    }

    public FilesSender getFilesSender() {
        return this.filesSender;
    }

    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String protocolAndHostOverride) {
        this.filesSender = new DefaultSessionAnalyticsFilesSender(Answers.getInstance(), protocolAndHostOverride, analyticsSettingsData.analyticsURL, this.httpRequestFactory, ApiKey.getApiKey(this.context));
        ((SessionAnalyticsFilesManager) this.filesManager).setAnalyticsSettingsData(analyticsSettingsData);
        configureRollover(analyticsSettingsData.flushIntervalSeconds);
    }
}
