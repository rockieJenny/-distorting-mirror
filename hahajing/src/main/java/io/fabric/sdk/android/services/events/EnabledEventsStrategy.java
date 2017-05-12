package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class EnabledEventsStrategy<T> implements EventsStrategy<T> {
    protected static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
    protected final Context context;
    protected final ScheduledExecutorService executorService;
    protected final EventsFilesManager<T> filesManager;
    protected volatile int rolloverIntervalSeconds = -1;
    protected final AtomicReference<ScheduledFuture<?>> scheduledRolloverFutureRef;

    public EnabledEventsStrategy(Context context, ScheduledExecutorService executorService, EventsFilesManager<T> filesManager) {
        this.context = context;
        this.executorService = executorService;
        this.filesManager = filesManager;
        this.scheduledRolloverFutureRef = new AtomicReference();
    }

    public void scheduleTimeBasedRollOverIfNeeded() {
        boolean hasRollOverInterval;
        if (this.rolloverIntervalSeconds != -1) {
            hasRollOverInterval = true;
        } else {
            hasRollOverInterval = false;
        }
        boolean noRollOverIsScheduled;
        if (this.scheduledRolloverFutureRef.get() == null) {
            noRollOverIsScheduled = true;
        } else {
            noRollOverIsScheduled = false;
        }
        if (hasRollOverInterval && noRollOverIsScheduled) {
            scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
        }
    }

    void sendAndCleanUpIfSuccess() {
        FilesSender filesSender = getFilesSender();
        if (filesSender == null) {
            CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        CommonUtils.logControlled(this.context, "Sending all files");
        int filesSent = 0;
        List<File> batch = this.filesManager.getBatchOfFilesToSend();
        try {
            CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", new Object[]{Integer.valueOf(batch.size())}));
            while (batch.size() > 0) {
                boolean cleanup = filesSender.send(batch);
                if (cleanup) {
                    filesSent += batch.size();
                    this.filesManager.deleteSentFiles(batch);
                }
                if (!cleanup) {
                    break;
                }
                batch = this.filesManager.getBatchOfFilesToSend();
            }
        } catch (Exception e) {
            CommonUtils.logControlledError(this.context, "Failed to send batch of analytics files to server: " + e.getMessage(), e);
        }
        if (filesSent == 0) {
            this.filesManager.deleteOldestInRollOverIfOverMax();
        }
    }

    public void sendEvents() {
        sendAndCleanUpIfSuccess();
    }

    protected void scheduleTimeBasedFileRollOver(int initialDelaySecs, int frequencySecs) {
        try {
            Runnable rollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
            CommonUtils.logControlled(this.context, "Scheduling time based file roll over every " + frequencySecs + " seconds");
            this.scheduledRolloverFutureRef.set(this.executorService.scheduleAtFixedRate(rollOverRunnable, (long) initialDelaySecs, (long) frequencySecs, TimeUnit.SECONDS));
        } catch (RejectedExecutionException e) {
            CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", e);
        }
    }

    public void cancelTimeBasedFileRollOver() {
        if (this.scheduledRolloverFutureRef.get() != null) {
            CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
            ((ScheduledFuture) this.scheduledRolloverFutureRef.get()).cancel(false);
            this.scheduledRolloverFutureRef.set(null);
        }
    }

    protected void configureRollover(int rolloverIntervalSeconds) {
        this.rolloverIntervalSeconds = rolloverIntervalSeconds;
        scheduleTimeBasedFileRollOver(0, this.rolloverIntervalSeconds);
    }

    public void deleteAllEvents() {
        this.filesManager.deleteAllEventsFiles();
    }

    public void recordEvent(T event) {
        CommonUtils.logControlled(this.context, event.toString());
        try {
            this.filesManager.writeEvent(event);
        } catch (IOException e) {
            CommonUtils.logControlledError(this.context, "Failed to write event.", e);
        }
        scheduleTimeBasedRollOverIfNeeded();
    }

    public boolean rollFileOver() {
        try {
            return this.filesManager.rollFileOver();
        } catch (IOException e) {
            CommonUtils.logControlledError(this.context, "Failed to roll file over.", e);
            return false;
        }
    }
}