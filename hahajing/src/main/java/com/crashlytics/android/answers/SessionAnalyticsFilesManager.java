package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.EventsStorage;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.IOException;
import java.util.UUID;

class SessionAnalyticsFilesManager extends EventsFilesManager {
    private static final String SESSION_ANALYTICS_TO_SEND_FILE_PREFIX = "sa";
    private AnalyticsSettingsData analyticsSettingsData;

    SessionAnalyticsFilesManager(Context context, SessionEventTransform transform, CurrentTimeProvider currentTimeProvider, EventsStorage eventStorage, int maxFilesToKeep) throws IOException {
        super(context, transform, currentTimeProvider, eventStorage, maxFilesToKeep);
    }

    SessionAnalyticsFilesManager(Context context, SessionEventTransform transform, CurrentTimeProvider currentTimeProvider, EventsStorage eventStorage) throws IOException {
        this(context, transform, currentTimeProvider, eventStorage, 100);
    }

    protected String generateUniqueRollOverFileName() {
        UUID targetUUIDComponent = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(SESSION_ANALYTICS_TO_SEND_FILE_PREFIX);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(targetUUIDComponent.toString());
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(this.currentTimeProvider.getCurrentTimeMillis());
        sb.append(".tap");
        return sb.toString();
    }

    protected int getMaxByteSizePerFile() {
        return this.analyticsSettingsData == null ? 8000 : this.analyticsSettingsData.maxByteSizePerFile;
    }

    void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData) {
        this.analyticsSettingsData = analyticsSettingsData;
    }
}
