package com.crashlytics.android;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ReportUploader {
    private static final String CLS_FILE_EXT = ".cls";
    static final Map<String, String> HEADER_INVALID_CLS_FILE = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
    private static final short[] RETRY_INTERVALS = new short[]{(short) 10, (short) 20, (short) 30, (short) 60, (short) 120, (short) 300};
    private static final FilenameFilter crashFileFilter = new FilenameFilter() {
        public boolean accept(File dir, String filename) {
            return filename.endsWith(".cls") && !filename.contains("Session");
        }
    };
    private final CreateReportSpiCall createReportCall;
    private final Object fileAccessLock = new Object();
    private Thread uploadThread;

    private class Worker extends BackgroundPriorityRunnable {
        private final float delay;

        Worker(float delay) {
            this.delay = delay;
        }

        public void onRun() {
            try {
                attemptUploadWithRetry();
            } catch (Exception e) {
                Fabric.getLogger().e("Fabric", "An unexpected error occurred while attempting to upload crash reports.", e);
            }
            ReportUploader.this.uploadThread = null;
        }

        private void attemptUploadWithRetry() {
            Fabric.getLogger().d("Fabric", "Starting report processing in " + this.delay + " second(s)...");
            if (this.delay > 0.0f) {
                try {
                    Thread.sleep((long) (this.delay * 1000.0f));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            Crashlytics crashlytics = Crashlytics.getInstance();
            CrashlyticsUncaughtExceptionHandler handler = crashlytics.getHandler();
            List<Report> reports = ReportUploader.this.findReports();
            if (!handler.isHandlingException()) {
                if (reports.isEmpty() || crashlytics.canSendWithUserApproval()) {
                    int retryCount = 0;
                    while (!reports.isEmpty() && !Crashlytics.getInstance().getHandler().isHandlingException()) {
                        Fabric.getLogger().d("Fabric", "Attempting to send " + reports.size() + " report(s)");
                        for (Report report : reports) {
                            ReportUploader.this.forceUpload(report);
                        }
                        reports = ReportUploader.this.findReports();
                        if (!reports.isEmpty()) {
                            int retryCount2 = retryCount + 1;
                            long interval = (long) ReportUploader.RETRY_INTERVALS[Math.min(retryCount, ReportUploader.RETRY_INTERVALS.length - 1)];
                            Fabric.getLogger().d("Fabric", "Report submisson: scheduling delayed retry in " + interval + " seconds");
                            try {
                                Thread.sleep(1000 * interval);
                                retryCount = retryCount2;
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                    return;
                }
                Fabric.getLogger().d("Fabric", "User declined to send. Removing " + reports.size() + " Report(s).");
                for (Report report2 : reports) {
                    report2.remove();
                }
            }
        }
    }

    public ReportUploader(CreateReportSpiCall createReportCall) {
        if (createReportCall == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.createReportCall = createReportCall;
    }

    public void uploadReports() {
        uploadReports(0.0f);
    }

    public synchronized void uploadReports(float delay) {
        if (this.uploadThread == null) {
            this.uploadThread = new Thread(new Worker(delay), "Crashlytics Report Uploader");
            this.uploadThread.start();
        }
    }

    boolean isUploading() {
        return this.uploadThread != null;
    }

    boolean forceUpload(Report report) {
        boolean removed = false;
        synchronized (this.fileAccessLock) {
            try {
                boolean sent = this.createReportCall.invoke(new CreateReportRequest(ApiKey.getApiKey(Crashlytics.getInstance().getContext(), Fabric.isDebuggable()), report));
                Fabric.getLogger().i("Fabric", "Crashlytics report upload " + (sent ? "complete: " : "FAILED: ") + report.getFileName());
                if (sent) {
                    report.remove();
                    removed = true;
                }
            } catch (Exception e) {
                Fabric.getLogger().e("Fabric", "Error occurred sending report " + report, e);
            }
        }
        return removed;
    }

    List<Report> findReports() {
        Fabric.getLogger().d("Fabric", "Checking for crash reports...");
        synchronized (this.fileAccessLock) {
            File[] clsFiles = Crashlytics.getInstance().getSdkDirectory().listFiles(crashFileFilter);
        }
        List<Report> reports = new LinkedList();
        for (File file : clsFiles) {
            Fabric.getLogger().d("Fabric", "Found crash report " + file.getPath());
            reports.add(new SessionReport(file));
        }
        if (reports.size() == 0) {
            Fabric.getLogger().d("Fabric", "No reports found.");
        }
        return reports;
    }
}
