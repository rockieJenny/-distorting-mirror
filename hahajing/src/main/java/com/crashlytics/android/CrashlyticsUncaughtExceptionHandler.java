package com.crashlytics.android;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import io.fabric.sdk.android.services.common.QueueFile;
import io.fabric.sdk.android.services.common.QueueFile.ElementReader;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CrashlyticsUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private static final int ANALYZER_VERSION = 1;
    static final FilenameFilter ANY_SESSION_FILENAME_FILTER = new FilenameFilter() {
        public boolean accept(File file, String filename) {
            return CrashlyticsUncaughtExceptionHandler.SESSION_FILE_PATTERN.matcher(filename).matches();
        }
    };
    static final String CLS_CRASH_MARKER_FILE_NAME = "crash_marker";
    private static final String EVENT_TYPE_CRASH = "crash";
    private static final String EVENT_TYPE_LOGGED = "error";
    private static final String GENERATOR_FORMAT = "Crashlytics Android SDK/%s";
    static final String INITIALIZATION_MARKER_FILE_NAME = "initialization_marker";
    static final String INVALID_CLS_CACHE_DIR = "invalidClsFiles";
    static final Comparator<File> LARGEST_FILE_NAME_FIRST = new Comparator<File>() {
        public int compare(File file1, File file2) {
            return file2.getName().compareTo(file1.getName());
        }
    };
    private static final int MAX_COMPLETE_SESSIONS_COUNT = 4;
    private static final int MAX_LOCAL_LOGGED_EXCEPTIONS = 64;
    static final int MAX_LOG_SIZE = 65536;
    static final int MAX_OPEN_SESSIONS = 8;
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
    static final String SESSION_APP_TAG = "SessionApp";
    static final String SESSION_BEGIN_TAG = "BeginSession";
    static final String SESSION_DEVICE_TAG = "SessionDevice";
    static final String SESSION_FATAL_TAG = "SessionCrash";
    static final FilenameFilter SESSION_FILE_FILTER = new FilenameFilter() {
        public boolean accept(File dir, String filename) {
            return filename.length() == ClsFileOutputStream.SESSION_FILE_EXTENSION.length() + 35 && filename.endsWith(ClsFileOutputStream.SESSION_FILE_EXTENSION);
        }
    };
    private static final Pattern SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    private static final int SESSION_ID_LENGTH = 35;
    static final String SESSION_NON_FATAL_TAG = "SessionEvent";
    static final String SESSION_OS_TAG = "SessionOS";
    static final String SESSION_USER_TAG = "SessionUser";
    private static final String SIGNAL_DEFAULT = "0";
    private static final ByteString SIGNAL_DEFAULT_BYTE_STRING = ByteString.copyFromUtf8(SIGNAL_DEFAULT);
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST = new Comparator<File>() {
        public int compare(File file1, File file2) {
            return file1.getName().compareTo(file2.getName());
        }
    };
    private final Crashlytics crashlytics;
    private final UncaughtExceptionHandler defaultHandler;
    private final AtomicInteger eventCounter = new AtomicInteger(0);
    private StackTraceElement[] exceptionStack;
    private final ExecutorService executorService;
    private final File filesDir;
    private final String generator;
    private final IdManager idManager;
    private final File initializationMarkerFile;
    private final AtomicBoolean isHandlingException;
    private QueueFile logFile;
    private final int maxChainedExceptionsDepth;
    private final ByteString optionalBuildIdBytes;
    private final ByteString packageName;
    private boolean powerConnected;
    private final BroadcastReceiver powerConnectedReceiver;
    private final BroadcastReceiver powerDisconnectedReceiver;
    private final AtomicBoolean receiversRegistered = new AtomicBoolean(false);
    private RunningAppProcessInfo runningAppProcessInfo;
    private List<StackTraceElement[]> stacks;
    private Thread[] threads;

    private static class AnySessionPartFileFilter implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        public boolean accept(File file, String fileName) {
            return !CrashlyticsUncaughtExceptionHandler.SESSION_FILE_FILTER.accept(file, fileName) && CrashlyticsUncaughtExceptionHandler.SESSION_FILE_PATTERN.matcher(fileName).matches();
        }
    }

    static class FileNameContainsFilter implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String s) {
            this.string = s;
        }

        public boolean accept(File dir, String filename) {
            return filename.contains(this.string) && !filename.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    }

    static class SessionPartFileFilter implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String sessionId) {
            this.sessionId = sessionId;
        }

        public boolean accept(File file, String fileName) {
            if (fileName.equals(this.sessionId + ClsFileOutputStream.SESSION_FILE_EXTENSION) || !fileName.contains(this.sessionId) || fileName.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION)) {
                return false;
            }
            return true;
        }
    }

    CrashlyticsUncaughtExceptionHandler(UncaughtExceptionHandler handler, CrashlyticsListener listener, ExecutorService executorService, String buildId, IdManager idManager, Crashlytics crashlytics) {
        this.defaultHandler = handler;
        this.executorService = executorService;
        this.idManager = idManager;
        this.crashlytics = crashlytics;
        this.isHandlingException = new AtomicBoolean(false);
        this.filesDir = crashlytics.getSdkDirectory();
        this.initializationMarkerFile = new File(this.filesDir, INITIALIZATION_MARKER_FILE_NAME);
        this.generator = String.format(Locale.US, GENERATOR_FORMAT, new Object[]{crashlytics.getVersion()});
        this.maxChainedExceptionsDepth = 8;
        notifyCrashlyticsListenerOfPreviousCrash(listener);
        this.packageName = ByteString.copyFromUtf8(crashlytics.getPackageName());
        this.optionalBuildIdBytes = buildId == null ? null : ByteString.copyFromUtf8(buildId.replace("-", ""));
        this.powerConnectedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                CrashlyticsUncaughtExceptionHandler.this.powerConnected = true;
            }
        };
        IntentFilter powerConnectedFilter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
        this.powerDisconnectedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                CrashlyticsUncaughtExceptionHandler.this.powerConnected = false;
            }
        };
        IntentFilter powerDisconnectedFilter = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
        Context context = crashlytics.getContext();
        context.registerReceiver(this.powerConnectedReceiver, powerConnectedFilter);
        context.registerReceiver(this.powerDisconnectedReceiver, powerDisconnectedFilter);
        this.receiversRegistered.set(true);
    }

    boolean isHandlingException() {
        return this.isHandlingException.get();
    }

    public synchronized void uncaughtException(final Thread thread, final Throwable ex) {
        this.isHandlingException.set(true);
        try {
            Fabric.getLogger().d("Fabric", "Crashlytics is handling uncaught exception \"" + ex + "\" from thread " + thread.getName());
            if (!this.receiversRegistered.getAndSet(true)) {
                Fabric.getLogger().d("Fabric", "Unregistering power receivers.");
                Context context = this.crashlytics.getContext();
                context.unregisterReceiver(this.powerConnectedReceiver);
                context.unregisterReceiver(this.powerDisconnectedReceiver);
            }
            final Date now = new Date();
            executeSyncLoggingException(new Callable<Void>() {
                public Void call() throws Exception {
                    CrashlyticsUncaughtExceptionHandler.this.handleUncaughtException(now, thread, ex);
                    return null;
                }
            });
            Fabric.getLogger().d("Fabric", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, ex);
            this.isHandlingException.set(false);
        } catch (Exception e) {
            Fabric.getLogger().e("Fabric", "An error occurred in the uncaught exception handler", e);
            Fabric.getLogger().d("Fabric", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, ex);
            this.isHandlingException.set(false);
        } catch (Throwable th) {
            Fabric.getLogger().d("Fabric", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, ex);
            this.isHandlingException.set(false);
        }
    }

    boolean finalizeSessions() {
        return ((Boolean) executeSyncLoggingException(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                if (CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
                    Fabric.getLogger().d("Fabric", "Skipping session finalization because a crash has already occurred.");
                    return Boolean.valueOf(false);
                }
                CrashlyticsUncaughtExceptionHandler.this.doCloseSessions();
                CrashlyticsUncaughtExceptionHandler.this.doOpenSession();
                Fabric.getLogger().d("Fabric", "Open sessions were closed and a new session was opened.");
                return Boolean.valueOf(true);
            }
        })).booleanValue();
    }

    void writeNonFatalException(final Thread thread, final Throwable ex) {
        final Date now = new Date();
        executeAsync(new Runnable() {
            public void run() {
                if (!CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
                    CrashlyticsUncaughtExceptionHandler.this.doWriteNonFatal(now, thread, ex);
                }
            }
        });
    }

    void writeToLog(final long timestamp, final String msg) {
        executeAsync(new Callable<Void>() {
            public Void call() throws Exception {
                if (!CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
                    if (CrashlyticsUncaughtExceptionHandler.this.logFile == null) {
                        CrashlyticsUncaughtExceptionHandler.this.initLogFile();
                    }
                    CrashlyticsUncaughtExceptionHandler.this.doWriteToLog(CrashlyticsUncaughtExceptionHandler.this.logFile, 65536, timestamp, msg);
                }
                return null;
            }
        });
    }

    QueueFile getLogFile() {
        return this.logFile;
    }

    ByteString getByteStringForLog(QueueFile logFile) {
        if (logFile == null) {
            return null;
        }
        final int[] offsetHolder = new int[]{0};
        final byte[] logBytes = new byte[logFile.usedBytes()];
        try {
            logFile.forEach(new ElementReader() {
                public void read(InputStream in, int length) throws IOException {
                    try {
                        in.read(logBytes, offsetHolder[0], length);
                        int[] iArr = offsetHolder;
                        iArr[0] = iArr[0] + length;
                    } finally {
                        in.close();
                    }
                }
            });
        } catch (IOException e) {
            Fabric.getLogger().e("Fabric", "A problem occurred while reading the Crashlytics log file.", e);
        }
        return ByteString.copyFrom(logBytes, 0, offsetHolder[0]);
    }

    void ensureOpenSessionExists() {
        executeAsync(new Callable<Void>() {
            public Void call() throws Exception {
                if (!CrashlyticsUncaughtExceptionHandler.this.hasOpenSession()) {
                    CrashlyticsUncaughtExceptionHandler.this.doOpenSession();
                }
                return null;
            }
        });
    }

    void markInitializationStarted() {
        executeSyncLoggingException(new Callable<Void>() {
            public Void call() throws Exception {
                CrashlyticsUncaughtExceptionHandler.this.initializationMarkerFile.createNewFile();
                Fabric.getLogger().d("Fabric", "Initialization marker file created.");
                return null;
            }
        });
    }

    void markInitializationComplete() {
        executeAsync(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                try {
                    boolean removed = CrashlyticsUncaughtExceptionHandler.this.initializationMarkerFile.delete();
                    Fabric.getLogger().d("Fabric", "Initialization marker file removed: " + removed);
                    return Boolean.valueOf(removed);
                } catch (Exception e) {
                    Fabric.getLogger().e("Fabric", "Problem encountered deleting Crashlytics initialization marker.", e);
                    return Boolean.valueOf(false);
                }
            }
        });
    }

    boolean didPreviousInitializationComplete() {
        return ((Boolean) executeSyncLoggingException(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Boolean.valueOf(CrashlyticsUncaughtExceptionHandler.this.initializationMarkerFile.exists());
            }
        })).booleanValue();
    }

    private void handleUncaughtException(Date time, Thread thread, Throwable ex) throws Exception {
        writeFatal(time, thread, ex);
        doCloseSessions();
        doOpenSession();
        trimSessionFiles();
        if (!this.crashlytics.shouldPromptUserBeforeSendingCrashReports()) {
            sendSessionReports();
        }
    }

    private void notifyCrashlyticsListenerOfPreviousCrash(CrashlyticsListener listener) {
        Fabric.getLogger().d("Fabric", "Checking for previous crash marker.");
        File markerFile = new File(this.crashlytics.getSdkDirectory(), CLS_CRASH_MARKER_FILE_NAME);
        if (markerFile.exists()) {
            markerFile.delete();
            if (listener != null) {
                try {
                    listener.crashlyticsDidDetectCrashDuringPreviousExecution();
                } catch (Exception e) {
                    Fabric.getLogger().e("Fabric", "Exception thrown by CrashlyticsListener while notifying of previous crash.", e);
                }
            }
        }
    }

    void doWriteToLog(QueueFile logFile, int maxLogSize, long timestamp, String msg) {
        if (logFile != null) {
            if (msg == null) {
                msg = "null";
            }
            try {
                int quarterMaxLogSize = maxLogSize / 4;
                if (msg.length() > quarterMaxLogSize) {
                    msg = "..." + msg.substring(msg.length() - quarterMaxLogSize);
                }
                msg = msg.replaceAll("\r", " ").replaceAll("\n", " ");
                logFile.add(String.format(Locale.US, "%d %s%n", new Object[]{Long.valueOf(timestamp), msg}).getBytes(HttpRequest.CHARSET_UTF8));
                while (!logFile.isEmpty() && logFile.usedBytes() > maxLogSize) {
                    logFile.remove();
                }
            } catch (IOException e) {
                Fabric.getLogger().e("Fabric", "There was a problem writing to the Crashlytics log.", e);
            }
        }
    }

    boolean hasOpenSession() {
        return listSessionBeginFiles().length > 0;
    }

    private boolean initLogFile() {
        Exception e;
        if (CommonUtils.getBooleanResourceValue(this.crashlytics.getContext(), "com.crashlytics.CollectCustomLogs", true)) {
            CommonUtils.closeOrLog(this.logFile, "Could not close log file: " + this.logFile);
            File f = null;
            try {
                File f2 = new File(this.crashlytics.getSdkDirectory(), "crashlytics-userlog-" + UUID.randomUUID().toString() + ".temp");
                try {
                    this.logFile = new QueueFile(f2);
                    f2.delete();
                    return true;
                } catch (Exception e2) {
                    e = e2;
                    f = f2;
                    Fabric.getLogger().e("Fabric", "Could not create log file: " + f, e);
                    return false;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().e("Fabric", "Could not create log file: " + f, e);
                return false;
            }
        }
        Fabric.getLogger().d("Fabric", "Preferences requested no custom logs. Aborting log file creation.");
        return false;
    }

    private void writeFatal(Date time, Thread thread, Throwable ex) {
        Exception e;
        OutputStream fos;
        Throwable th;
        ClsFileOutputStream fos2 = null;
        CodedOutputStream cos = null;
        try {
            new File(this.filesDir, CLS_CRASH_MARKER_FILE_NAME).createNewFile();
            String currentSessionId = getCurrentSessionId();
            if (currentSessionId != null) {
                Crashlytics.recordFatalExceptionEvent(currentSessionId);
                OutputStream fos3 = new ClsFileOutputStream(this.filesDir, currentSessionId + SESSION_FATAL_TAG);
                try {
                    cos = CodedOutputStream.newInstance(fos3);
                    writeSessionEvent(time, cos, thread, ex, EVENT_TYPE_CRASH, true);
                    fos2 = fos3;
                } catch (Exception e2) {
                    e = e2;
                    fos = fos3;
                    try {
                        Fabric.getLogger().e("Fabric", "An error occurred in the fatal exception logger", e);
                        writeStackTraceIfNotNull(e, fos2);
                        CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(fos2, "Failed to close fatal exception file output stream.");
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(fos2, "Failed to close fatal exception file output stream.");
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fos = fos3;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close fatal exception file output stream.");
                    throw th;
                }
            }
            Fabric.getLogger().e("Fabric", "Tried to write a fatal exception while no session was open.", null);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos2, "Failed to close fatal exception file output stream.");
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().e("Fabric", "An error occurred in the fatal exception logger", e);
            writeStackTraceIfNotNull(e, fos2);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos2, "Failed to close fatal exception file output stream.");
        }
    }

    private void doWriteNonFatal(Date time, Thread thread, Throwable ex) {
        OutputStream outputStream;
        Exception e;
        Throwable th;
        String currentSessionId = getCurrentSessionId();
        if (currentSessionId != null) {
            Crashlytics.recordLoggedExceptionEvent(currentSessionId);
            ClsFileOutputStream fos = null;
            CodedOutputStream cos = null;
            try {
                Fabric.getLogger().d("Fabric", "Crashlytics is logging non-fatal exception \"" + ex + "\" from thread " + thread.getName());
                OutputStream fos2 = new ClsFileOutputStream(this.filesDir, currentSessionId + SESSION_NON_FATAL_TAG + CommonUtils.padWithZerosToMaxIntWidth(this.eventCounter.getAndIncrement()));
                try {
                    cos = CodedOutputStream.newInstance(fos2);
                    writeSessionEvent(time, cos, thread, ex, EVENT_TYPE_LOGGED, false);
                    CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close non-fatal file output stream.");
                    outputStream = fos2;
                } catch (Exception e2) {
                    e = e2;
                    outputStream = fos2;
                    try {
                        Fabric.getLogger().e("Fabric", "An error occurred in the non-fatal exception logger", e);
                        writeStackTraceIfNotNull(e, fos);
                        CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                        CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                        trimSessionEventFiles(currentSessionId, 64);
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                        CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = fos2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                    CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().e("Fabric", "An error occurred in the non-fatal exception logger", e);
                writeStackTraceIfNotNull(e, fos);
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                trimSessionEventFiles(currentSessionId, 64);
                return;
            }
            try {
                trimSessionEventFiles(currentSessionId, 64);
                return;
            } catch (Exception e4) {
                Fabric.getLogger().e("Fabric", "An error occurred when trimming non-fatal files.", e4);
                return;
            }
        }
        Fabric.getLogger().e("Fabric", "Tried to write a non-fatal exception while no session was open.", null);
    }

    private void doOpenSession() throws Exception {
        Date startedAt = new Date();
        String sessionIdentifier = new CLSUUID(this.idManager).toString();
        Fabric.getLogger().d("Fabric", "Opening an new session with ID " + sessionIdentifier);
        writeBeginSession(startedAt, sessionIdentifier);
        writeSessionApp(sessionIdentifier);
        writeSessionOS(sessionIdentifier);
        writeSessionDevice(sessionIdentifier);
    }

    private void doCloseSessions() throws Exception {
        trimOpenSessions(8);
        String currentSessionId = getCurrentSessionId();
        if (currentSessionId != null) {
            writeSessionUser(currentSessionId);
            SessionSettingsData settingsData = this.crashlytics.getSessionSettingsData();
            if (settingsData != null) {
                int maxLoggedExceptionsCount = settingsData.maxCustomExceptionEvents;
                Fabric.getLogger().d("Fabric", "Closing all open sessions.");
                File[] sessionBeginFiles = listSessionBeginFiles();
                if (sessionBeginFiles != null && sessionBeginFiles.length > 0) {
                    for (File sessionBeginFile : sessionBeginFiles) {
                        String sessionIdentifier = getSessionIdFromSessionFile(sessionBeginFile);
                        Fabric.getLogger().d("Fabric", "Closing session: " + sessionIdentifier);
                        writeSessionPartsToSessionFile(sessionBeginFile, sessionIdentifier, maxLoggedExceptionsCount);
                    }
                    return;
                }
                return;
            }
            Fabric.getLogger().d("Fabric", "Unable to close session. Settings are not loaded.");
            return;
        }
        Fabric.getLogger().d("Fabric", "No open sessions exist.");
    }

    private String getCurrentSessionId() {
        File[] sessionBeginFiles = listFilesMatching(new FileNameContainsFilter(SESSION_BEGIN_TAG));
        Arrays.sort(sessionBeginFiles, LARGEST_FILE_NAME_FIRST);
        return sessionBeginFiles.length > 0 ? getSessionIdFromSessionFile(sessionBeginFiles[0]) : null;
    }

    private String getSessionIdFromSessionFile(File sessionFile) {
        return sessionFile.getName().substring(0, 35);
    }

    private void writeSessionPartsToSessionFile(File sessionBeginFile, String sessionId, int maxLoggedExceptionsCount) {
        Exception e;
        Throwable th;
        Fabric.getLogger().d("Fabric", "Collecting session parts for ID " + sessionId);
        File[] fatalFiles = listFilesMatching(new FileNameContainsFilter(sessionId + SESSION_FATAL_TAG));
        boolean hasFatal = fatalFiles != null && fatalFiles.length > 0;
        Fabric.getLogger().d("Fabric", String.format(Locale.US, "Session %s has fatal exception: %s", new Object[]{sessionId, Boolean.valueOf(hasFatal)}));
        File[] nonFatalFiles = listFilesMatching(new FileNameContainsFilter(sessionId + SESSION_NON_FATAL_TAG));
        boolean hasNonFatal = nonFatalFiles != null && nonFatalFiles.length > 0;
        Fabric.getLogger().d("Fabric", String.format(Locale.US, "Session %s has non-fatal exceptions: %s", new Object[]{sessionId, Boolean.valueOf(hasNonFatal)}));
        if (hasFatal || hasNonFatal) {
            ClsFileOutputStream fos = null;
            try {
                OutputStream fos2 = new ClsFileOutputStream(this.filesDir, sessionId);
                OutputStream outputStream;
                try {
                    CodedOutputStream cos = CodedOutputStream.newInstance(fos2);
                    Fabric.getLogger().d("Fabric", "Collecting SessionStart data for session ID " + sessionId);
                    writeToCosFromFile(cos, sessionBeginFile);
                    cos.writeUInt64(4, new Date().getTime() / 1000);
                    cos.writeBool(5, hasFatal);
                    writeInitialPartsTo(cos, sessionId);
                    if (hasNonFatal) {
                        if (nonFatalFiles.length > maxLoggedExceptionsCount) {
                            Fabric.getLogger().d("Fabric", String.format(Locale.US, "Trimming down to %d logged exceptions.", new Object[]{Integer.valueOf(maxLoggedExceptionsCount)}));
                            trimSessionEventFiles(sessionId, maxLoggedExceptionsCount);
                            nonFatalFiles = listFilesMatching(new FileNameContainsFilter(sessionId + SESSION_NON_FATAL_TAG));
                        }
                        writeNonFatalEventsTo(cos, nonFatalFiles, sessionId);
                    }
                    if (hasFatal) {
                        writeToCosFromFile(cos, fatalFiles[0]);
                    }
                    cos.writeUInt32(11, 1);
                    cos.writeEnum(12, 3);
                    CommonUtils.flushOrLog(cos, "Error flushing session file stream");
                    if (null != null) {
                        closeWithoutRenamingOrLog(fos2);
                        outputStream = fos2;
                    } else {
                        CommonUtils.closeOrLog(fos2, "Failed to close CLS file");
                        outputStream = fos2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    outputStream = fos2;
                    try {
                        Fabric.getLogger().e("Fabric", "Failed to write session file for session ID: " + sessionId, e);
                        writeStackTraceIfNotNull(e, fos);
                        CommonUtils.flushOrLog(null, "Error flushing session file stream");
                        if (true) {
                            CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                        } else {
                            closeWithoutRenamingOrLog(fos);
                        }
                        Fabric.getLogger().d("Fabric", "Removing session part files for ID " + sessionId);
                        deleteSessionPartFilesFor(sessionId);
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(null, "Error flushing session file stream");
                        if (null == null) {
                            CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                        } else {
                            closeWithoutRenamingOrLog(fos);
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = fos2;
                    CommonUtils.flushOrLog(null, "Error flushing session file stream");
                    if (null == null) {
                        closeWithoutRenamingOrLog(fos);
                    } else {
                        CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().e("Fabric", "Failed to write session file for session ID: " + sessionId, e);
                writeStackTraceIfNotNull(e, fos);
                CommonUtils.flushOrLog(null, "Error flushing session file stream");
                if (true) {
                    CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                } else {
                    closeWithoutRenamingOrLog(fos);
                }
                Fabric.getLogger().d("Fabric", "Removing session part files for ID " + sessionId);
                deleteSessionPartFilesFor(sessionId);
            }
        }
        Fabric.getLogger().d("Fabric", "No events present for session ID " + sessionId);
        Fabric.getLogger().d("Fabric", "Removing session part files for ID " + sessionId);
        deleteSessionPartFilesFor(sessionId);
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream fos) {
        if (fos != null) {
            try {
                fos.closeInProgressStream();
            } catch (IOException ex) {
                Fabric.getLogger().e("Fabric", "Error closing session file stream in the presence of an exception", ex);
            }
        }
    }

    private void writeNonFatalEventsTo(CodedOutputStream cos, File[] nonFatalFiles, String sessionId) {
        Arrays.sort(nonFatalFiles, CommonUtils.FILE_MODIFIED_COMPARATOR);
        for (File nonFatalFile : nonFatalFiles) {
            try {
                Fabric.getLogger().d("Fabric", String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[]{sessionId, nonFatalFile.getName()}));
                writeToCosFromFile(cos, nonFatalFile);
            } catch (Exception e) {
                Fabric.getLogger().e("Fabric", "Error writting non-fatal to session.", e);
            }
        }
    }

    private void writeInitialPartsTo(CodedOutputStream cos, String sessionId) throws IOException {
        for (String tag : new String[]{SESSION_USER_TAG, SESSION_APP_TAG, SESSION_OS_TAG, SESSION_DEVICE_TAG}) {
            File[] sessionPartFiles = listFilesMatching(new FileNameContainsFilter(sessionId + tag));
            if (sessionPartFiles.length == 0) {
                Fabric.getLogger().e("Fabric", "Can't find " + tag + " data for session ID " + sessionId, null);
            } else {
                Fabric.getLogger().d("Fabric", "Collecting " + tag + " data for session ID " + sessionId);
                writeToCosFromFile(cos, sessionPartFiles[0]);
            }
        }
    }

    private void deleteSessionPartFilesFor(String sessionId) {
        for (File file : listSessionPartFilesFor(sessionId)) {
            file.delete();
        }
    }

    private File[] listSessionPartFilesFor(String sessionId) {
        return listFilesMatching(new SessionPartFileFilter(sessionId));
    }

    private File[] listCompleteSessionFiles() {
        return listFilesMatching(SESSION_FILE_FILTER);
    }

    File[] listSessionBeginFiles() {
        return listFilesMatching(new FileNameContainsFilter(SESSION_BEGIN_TAG));
    }

    private File[] listFilesMatching(FilenameFilter filter) {
        return ensureFileArrayNotNull(this.filesDir.listFiles(filter));
    }

    private File[] ensureFileArrayNotNull(File[] files) {
        return files == null ? new File[0] : files;
    }

    private void writeSessionUser(String sessionId) throws Exception {
        Exception e;
        OutputStream fos;
        Throwable th;
        ByteString email = null;
        FileOutputStream fos2 = null;
        CodedOutputStream cos = null;
        try {
            OutputStream fos3 = new ClsFileOutputStream(this.filesDir, sessionId + SESSION_USER_TAG);
            try {
                cos = CodedOutputStream.newInstance(fos3);
                String idStr = this.crashlytics.getUserIdentifier();
                String nameStr = this.crashlytics.getUserName();
                String emailStr = this.crashlytics.getUserEmail();
                if (idStr == null && nameStr == null && emailStr == null) {
                    CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                    CommonUtils.closeOrLog(fos3, "Failed to close session user file.");
                    return;
                }
                if (idStr == null) {
                    idStr = "";
                }
                ByteString identifier = ByteString.copyFromUtf8(idStr);
                ByteString name = nameStr == null ? null : ByteString.copyFromUtf8(nameStr);
                if (emailStr != null) {
                    email = ByteString.copyFromUtf8(emailStr);
                }
                int size = 0 + CodedOutputStream.computeBytesSize(1, identifier);
                if (name != null) {
                    size += CodedOutputStream.computeBytesSize(2, name);
                }
                if (email != null) {
                    size += CodedOutputStream.computeBytesSize(3, email);
                }
                cos.writeTag(6, 2);
                cos.writeRawVarint32(size);
                cos.writeBytes(1, identifier);
                if (name != null) {
                    cos.writeBytes(2, name);
                }
                if (email != null) {
                    cos.writeBytes(3, email);
                }
                CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                CommonUtils.closeOrLog(fos3, "Failed to close session user file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos3;
                try {
                    writeStackTraceIfNotNull(e, fos2);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close session user file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos3;
                CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                CommonUtils.closeOrLog(fos2, "Failed to close session user file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            writeStackTraceIfNotNull(e, fos2);
            throw e;
        }
    }

    private void sendSessionReports() {
        for (final File toSend : listCompleteSessionFiles()) {
            executeAsync(new Runnable() {
                public void run() {
                    if (CommonUtils.canTryConnection(CrashlyticsUncaughtExceptionHandler.this.crashlytics.getContext())) {
                        Fabric.getLogger().d("Fabric", "Attempting to send crash report at time of crash...");
                        CreateReportSpiCall call = CrashlyticsUncaughtExceptionHandler.this.crashlytics.getCreateReportSpiCall(Settings.getInstance().awaitSettingsData());
                        if (call != null) {
                            new ReportUploader(call).forceUpload(new SessionReport(toSend, CrashlyticsUncaughtExceptionHandler.SEND_AT_CRASHTIME_HEADER));
                        }
                    }
                }
            });
        }
    }

    private void writeBeginSession(Date startedAt, String sessionIdentifier) throws Exception {
        Exception e;
        OutputStream fos;
        Throwable th;
        FileOutputStream fos2 = null;
        CodedOutputStream cos = null;
        try {
            OutputStream fos3 = new ClsFileOutputStream(this.crashlytics.getSdkDirectory(), sessionIdentifier + SESSION_BEGIN_TAG);
            try {
                cos = CodedOutputStream.newInstance(fos3);
                cos.writeBytes(1, ByteString.copyFromUtf8(this.generator));
                cos.writeBytes(2, ByteString.copyFromUtf8(sessionIdentifier));
                cos.writeUInt64(3, startedAt.getTime() / 1000);
                CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(fos3, "Failed to close begin session file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos3;
                try {
                    writeStackTraceIfNotNull(e, fos2);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close begin session file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos3;
                CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(fos2, "Failed to close begin session file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            writeStackTraceIfNotNull(e, fos2);
            throw e;
        }
    }

    private void writeSessionApp(String sessionIdentifier) throws Exception {
        Exception e;
        OutputStream fos;
        Throwable th;
        FileOutputStream fos2 = null;
        CodedOutputStream cos = null;
        try {
            OutputStream fos3 = new ClsFileOutputStream(this.crashlytics.getSdkDirectory(), sessionIdentifier + SESSION_APP_TAG);
            try {
                cos = CodedOutputStream.newInstance(fos3);
                ByteString packageName = ByteString.copyFromUtf8(this.crashlytics.getPackageName());
                ByteString versionCode = ByteString.copyFromUtf8(this.crashlytics.getVersionCode());
                ByteString versionName = ByteString.copyFromUtf8(this.crashlytics.getVersionName());
                ByteString installationId = ByteString.copyFromUtf8(this.idManager.getAppInstallIdentifier());
                int deliveryMechanism = DeliveryMechanism.determineFrom(this.crashlytics.getInstallerPackageName()).getId();
                cos.writeTag(7, 2);
                cos.writeRawVarint32(getSessionAppSize(packageName, versionCode, versionName, installationId, deliveryMechanism));
                cos.writeBytes(1, packageName);
                cos.writeBytes(2, versionCode);
                cos.writeBytes(3, versionName);
                cos.writeTag(5, 2);
                cos.writeRawVarint32(getSessionAppOrgSize());
                cos.writeString(1, ApiKey.getApiKey(this.crashlytics.getContext()));
                cos.writeBytes(6, installationId);
                cos.writeEnum(10, deliveryMechanism);
                CommonUtils.flushOrLog(cos, "Failed to flush to session app file.");
                CommonUtils.closeOrLog(fos3, "Failed to close session app file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos3;
                try {
                    writeStackTraceIfNotNull(e, fos2);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session app file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close session app file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos3;
                CommonUtils.flushOrLog(cos, "Failed to flush to session app file.");
                CommonUtils.closeOrLog(fos2, "Failed to close session app file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            writeStackTraceIfNotNull(e, fos2);
            throw e;
        }
    }

    private void writeStackTraceIfNotNull(Throwable ex, OutputStream os) {
        if (os != null) {
            writeStackTrace(ex, os);
        }
    }

    private void writeStackTrace(Throwable ex, String filename) {
        Writer writer;
        Exception e;
        Throwable th;
        PrintWriter writer2 = null;
        try {
            Writer writer3 = new PrintWriter(this.crashlytics.getContext().openFileOutput(filename, 0));
            try {
                writeStackTrace(ex, writer3);
                CommonUtils.closeOrLog(writer3, "Failed to close stack trace writer.");
                writer = writer3;
            } catch (Exception e2) {
                e = e2;
                writer = writer3;
                try {
                    Fabric.getLogger().e("Fabric", "Failed to create PrintWriter", e);
                    CommonUtils.closeOrLog(writer2, "Failed to close stack trace writer.");
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.closeOrLog(writer2, "Failed to close stack trace writer.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                writer = writer3;
                CommonUtils.closeOrLog(writer2, "Failed to close stack trace writer.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().e("Fabric", "Failed to create PrintWriter", e);
            CommonUtils.closeOrLog(writer2, "Failed to close stack trace writer.");
        }
    }

    private void writeStackTrace(Throwable ex, OutputStream os) {
        Exception e;
        Throwable th;
        PrintWriter writer = null;
        try {
            Writer writer2 = new PrintWriter(os);
            Writer writer3;
            try {
                writeStackTrace(ex, writer2);
                CommonUtils.closeOrLog(writer2, "Failed to close stack trace writer.");
                writer3 = writer2;
            } catch (Exception e2) {
                e = e2;
                writer3 = writer2;
                try {
                    Fabric.getLogger().e("Fabric", "Failed to create PrintWriter", e);
                    CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                writer3 = writer2;
                CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().e("Fabric", "Failed to create PrintWriter", e);
            CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
        }
    }

    private void writeStackTrace(Throwable ex, Writer writer) {
        boolean first = true;
        while (ex != null) {
            try {
                String message = getMessage(ex);
                if (message == null) {
                    message = "";
                }
                writer.write((first ? "" : "Caused by: ") + ex.getClass().getName() + ": " + message + "\n");
                first = false;
                for (StackTraceElement element : ex.getStackTrace()) {
                    writer.write("\tat " + element.toString() + "\n");
                }
                ex = ex.getCause();
            } catch (Exception e) {
                Fabric.getLogger().e("Fabric", "Could not write stack trace", e);
                return;
            }
        }
    }

    private int getSessionAppSize(ByteString packageName, ByteString versionCode, ByteString versionName, ByteString installUuid, int deliveryMechanism) {
        int size = ((0 + CodedOutputStream.computeBytesSize(1, packageName)) + CodedOutputStream.computeBytesSize(2, versionCode)) + CodedOutputStream.computeBytesSize(3, versionName);
        int orgSize = getSessionAppOrgSize();
        return ((size + ((CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(orgSize)) + orgSize)) + CodedOutputStream.computeBytesSize(6, installUuid)) + CodedOutputStream.computeEnumSize(10, deliveryMechanism);
    }

    private int getSessionAppOrgSize() {
        return 0 + CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(ApiKey.getApiKey(this.crashlytics.getContext(), Fabric.isDebuggable())));
    }

    private void writeSessionOS(String sessionIdentifier) throws Exception {
        Exception e;
        OutputStream fos;
        Throwable th;
        FileOutputStream fos2 = null;
        CodedOutputStream cos = null;
        try {
            Context context = this.crashlytics.getContext();
            OutputStream fos3 = new ClsFileOutputStream(this.crashlytics.getSdkDirectory(), sessionIdentifier + SESSION_OS_TAG);
            try {
                cos = CodedOutputStream.newInstance(fos3);
                ByteString release = ByteString.copyFromUtf8(VERSION.RELEASE);
                ByteString codeName = ByteString.copyFromUtf8(VERSION.CODENAME);
                boolean isRooted = CommonUtils.isRooted(context);
                cos.writeTag(8, 2);
                cos.writeRawVarint32(getSessionOSSize(release, codeName, isRooted));
                cos.writeEnum(1, 3);
                cos.writeBytes(2, release);
                cos.writeBytes(3, codeName);
                cos.writeBool(4, isRooted);
                CommonUtils.flushOrLog(cos, "Failed to flush to session OS file.");
                CommonUtils.closeOrLog(fos3, "Failed to close session OS file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos3;
                try {
                    writeStackTraceIfNotNull(e, fos2);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session OS file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close session OS file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos3;
                CommonUtils.flushOrLog(cos, "Failed to flush to session OS file.");
                CommonUtils.closeOrLog(fos2, "Failed to close session OS file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            writeStackTraceIfNotNull(e, fos2);
            throw e;
        }
    }

    private int getSessionOSSize(ByteString release, ByteString codeName, boolean isRooted) {
        return (((0 + CodedOutputStream.computeEnumSize(1, 3)) + CodedOutputStream.computeBytesSize(2, release)) + CodedOutputStream.computeBytesSize(3, codeName)) + CodedOutputStream.computeBoolSize(4, isRooted);
    }

    private ByteString stringToByteString(String s) {
        return s == null ? null : ByteString.copyFromUtf8(s);
    }

    private void writeSessionDevice(String sessionIdentifier) throws Exception {
        Throwable e;
        Throwable th;
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            Context context = this.crashlytics.getContext();
            OutputStream clsFileOutputStream = new ClsFileOutputStream(this.crashlytics.getSdkDirectory(), sessionIdentifier + SESSION_DEVICE_TAG);
            try {
                cos = CodedOutputStream.newInstance(clsFileOutputStream);
                StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
                int arch = CommonUtils.getCpuArchitectureInt();
                ByteString model = stringToByteString(Build.MODEL);
                ByteString manufacturer = stringToByteString(Build.MANUFACTURER);
                ByteString modelClass = stringToByteString(Build.PRODUCT);
                int availableProcessors = Runtime.getRuntime().availableProcessors();
                long totalRam = CommonUtils.getTotalRamInBytes();
                long diskSpace = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
                boolean isEmulator = CommonUtils.isEmulator(context);
                ByteString clsDeviceID = ByteString.copyFromUtf8(this.idManager.getDeviceUUID());
                Map<DeviceIdentifierType, String> ids = this.idManager.getDeviceIdentifiers();
                int state = CommonUtils.getDeviceState(context);
                cos.writeTag(9, 2);
                cos.writeRawVarint32(getSessionDeviceSize(arch, clsDeviceID, model, availableProcessors, totalRam, diskSpace, isEmulator, ids, state, manufacturer, modelClass));
                cos.writeBytes(1, clsDeviceID);
                cos.writeEnum(3, arch);
                cos.writeBytes(4, model);
                cos.writeUInt32(5, availableProcessors);
                cos.writeUInt64(6, totalRam);
                cos.writeUInt64(7, diskSpace);
                cos.writeBool(10, isEmulator);
                for (Entry<DeviceIdentifierType, String> id : ids.entrySet()) {
                    cos.writeTag(11, 2);
                    cos.writeRawVarint32(getDeviceIdentifierSize((DeviceIdentifierType) id.getKey(), (String) id.getValue()));
                    cos.writeEnum(1, ((DeviceIdentifierType) id.getKey()).protobufIndex);
                    cos.writeBytes(2, ByteString.copyFromUtf8((String) id.getValue()));
                }
                cos.writeUInt32(12, state);
                if (manufacturer != null) {
                    cos.writeBytes(13, manufacturer);
                }
                if (modelClass != null) {
                    cos.writeBytes(14, modelClass);
                }
                CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
            } catch (Exception e2) {
                e = e2;
                fos = clsFileOutputStream;
                try {
                    writeStackTraceIfNotNull(e, fos);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
                    CommonUtils.closeOrLog(fos, "Failed to close session device file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                OutputStream fos2 = clsFileOutputStream;
                CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
                CommonUtils.closeOrLog(fos, "Failed to close session device file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            writeStackTraceIfNotNull(e, fos);
            throw e;
        }
    }

    private int getDeviceIdentifierSize(DeviceIdentifierType type, String value) {
        return CodedOutputStream.computeEnumSize(1, type.protobufIndex) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(value));
    }

    private int getSessionDeviceSize(int arch, ByteString clsDeviceID, ByteString model, int availableProcessors, long totalRam, long diskSpace, boolean isEmulator, Map<DeviceIdentifierType, String> ids, int state, ByteString manufacturer, ByteString modelClass) {
        int i;
        int size = (0 + CodedOutputStream.computeBytesSize(1, clsDeviceID)) + CodedOutputStream.computeEnumSize(3, arch);
        if (model == null) {
            i = 0;
        } else {
            i = CodedOutputStream.computeBytesSize(4, model);
        }
        size = ((((size + i) + CodedOutputStream.computeUInt32Size(5, availableProcessors)) + CodedOutputStream.computeUInt64Size(6, totalRam)) + CodedOutputStream.computeUInt64Size(7, diskSpace)) + CodedOutputStream.computeBoolSize(10, isEmulator);
        if (ids != null) {
            for (Entry<DeviceIdentifierType, String> id : ids.entrySet()) {
                int idSize = getDeviceIdentifierSize((DeviceIdentifierType) id.getKey(), (String) id.getValue());
                size += (CodedOutputStream.computeTagSize(11) + CodedOutputStream.computeRawVarint32Size(idSize)) + idSize;
            }
        }
        return ((size + CodedOutputStream.computeUInt32Size(12, state)) + (manufacturer == null ? 0 : CodedOutputStream.computeBytesSize(13, manufacturer))) + (modelClass == null ? 0 : CodedOutputStream.computeBytesSize(14, modelClass));
    }

    private void writeToCosFromFile(CodedOutputStream cos, File file) throws IOException {
        Throwable th;
        if (file.exists()) {
            byte[] bytes = new byte[((int) file.length())];
            FileInputStream fis = null;
            try {
                FileInputStream fis2 = new FileInputStream(file);
                int offset = 0;
                while (offset < bytes.length) {
                    try {
                        int numRead = fis2.read(bytes, offset, bytes.length - offset);
                        if (numRead < 0) {
                            break;
                        }
                        offset += numRead;
                    } catch (Throwable th2) {
                        th = th2;
                        fis = fis2;
                    }
                }
                CommonUtils.closeOrLog(fis2, "Failed to close file input stream.");
                cos.writeRawBytes(bytes);
                return;
            } catch (Throwable th3) {
                th = th3;
                CommonUtils.closeOrLog(fis, "Failed to close file input stream.");
                throw th;
            }
        }
        Fabric.getLogger().e("Fabric", "Tried to include a file that doesn't exist: " + file.getName(), null);
    }

    private void trimSessionEventFiles(String sessionId, int limit) {
        Utils.capFileCount(this.filesDir, new FileNameContainsFilter(sessionId + SESSION_NON_FATAL_TAG), limit, SMALLEST_FILE_NAME_FIRST);
    }

    void trimSessionFiles() {
        Utils.capFileCount(this.filesDir, SESSION_FILE_FILTER, 4, SMALLEST_FILE_NAME_FIRST);
    }

    private void trimOpenSessions(int maxOpenSessionCount) {
        Set<String> sessionIdsToKeep = new HashSet();
        File[] beginSessionFiles = listSessionBeginFiles();
        Arrays.sort(beginSessionFiles, LARGEST_FILE_NAME_FIRST);
        int count = Math.min(maxOpenSessionCount, beginSessionFiles.length);
        for (int i = 0; i < count; i++) {
            sessionIdsToKeep.add(getSessionIdFromSessionFile(beginSessionFiles[i]));
        }
        for (File sessionPartFile : listFilesMatching(new AnySessionPartFileFilter())) {
            String fileName = sessionPartFile.getName();
            Matcher matcher = SESSION_FILE_PATTERN.matcher(fileName);
            matcher.matches();
            if (!sessionIdsToKeep.contains(matcher.group(1))) {
                Fabric.getLogger().d("Fabric", "Trimming open session file: " + fileName);
                sessionPartFile.delete();
            }
        }
    }

    private void writeSessionEvent(Date time, CodedOutputStream cos, Thread thread, Throwable ex, String eventType, boolean includeAllThreads) throws Exception {
        Map<String, String> attributes;
        Context context = this.crashlytics.getContext();
        long eventTime = time.getTime() / 1000;
        float batteryLevel = CommonUtils.getBatteryLevel(context);
        int batterVelocity = CommonUtils.getBatteryVelocity(context, this.powerConnected);
        boolean proximityEnabled = CommonUtils.getProximitySensorEnabled(context);
        int orientation = context.getResources().getConfiguration().orientation;
        long usedRamBytes = CommonUtils.getTotalRamInBytes() - CommonUtils.calculateFreeRamInBytes(context);
        long diskUsedBytes = CommonUtils.calculateUsedDiskSpaceInBytes(Environment.getDataDirectory().getPath());
        this.runningAppProcessInfo = CommonUtils.getAppProcessInfo(this.crashlytics.getPackageName(), context);
        this.stacks = new LinkedList();
        this.exceptionStack = ex.getStackTrace();
        if (includeAllThreads) {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            this.threads = new Thread[allStackTraces.size()];
            int i = 0;
            for (Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
                this.threads[i] = (Thread) entry.getKey();
                this.stacks.add(entry.getValue());
                i++;
            }
        } else {
            this.threads = new Thread[0];
        }
        ByteString logByteString = getByteStringForLog(this.logFile);
        if (logByteString == null) {
            Fabric.getLogger().d("Fabric", "No log data to include with this event.");
        }
        CommonUtils.closeOrLog(this.logFile, "There was a problem closing the Crashlytics log file.");
        this.logFile = null;
        if (CommonUtils.getBooleanResourceValue(context, "com.crashlytics.CollectCustomKeys", true)) {
            attributes = this.crashlytics.getAttributes();
            if (attributes != null && attributes.size() > 1) {
                attributes = new TreeMap(attributes);
            }
        } else {
            attributes = new TreeMap();
        }
        cos.writeTag(10, 2);
        cos.writeRawVarint32(getSessionEventSize(thread, ex, eventType, eventTime, attributes, batteryLevel, batterVelocity, proximityEnabled, orientation, usedRamBytes, diskUsedBytes, logByteString));
        cos.writeUInt64(1, eventTime);
        cos.writeBytes(2, ByteString.copyFromUtf8(eventType));
        writeSessionEventApp(cos, thread, ex, attributes);
        writeSessionEventDevice(cos, batteryLevel, batterVelocity, proximityEnabled, orientation, usedRamBytes, diskUsedBytes);
        writeSessionEventLog(cos, logByteString);
    }

    private void writeSessionEventApp(CodedOutputStream cos, Thread thread, Throwable ex, Map<String, String> customAttributes) throws Exception {
        cos.writeTag(3, 2);
        cos.writeRawVarint32(getEventAppSize(thread, ex, customAttributes));
        writeSessionEventAppExecution(cos, thread, ex);
        if (!(customAttributes == null || customAttributes.isEmpty())) {
            writeSessionEventAppCustomAttributes(cos, customAttributes);
        }
        if (this.runningAppProcessInfo != null) {
            cos.writeBool(3, this.runningAppProcessInfo.importance != 100);
        }
        cos.writeUInt32(4, this.crashlytics.getContext().getResources().getConfiguration().orientation);
    }

    private void writeSessionEventAppExecution(CodedOutputStream cos, Thread exceptionThread, Throwable ex) throws Exception {
        cos.writeTag(1, 2);
        cos.writeRawVarint32(getEventAppExecutionSize(exceptionThread, ex));
        writeThread(cos, exceptionThread, this.exceptionStack, 4, true);
        int len = this.threads.length;
        for (int i = 0; i < len; i++) {
            writeThread(cos, this.threads[i], (StackTraceElement[]) this.stacks.get(i), 0, false);
        }
        writeSessionEventAppExecutionException(cos, ex, 1, 2);
        cos.writeTag(3, 2);
        cos.writeRawVarint32(getEventAppExecutionSignalSize());
        cos.writeBytes(1, SIGNAL_DEFAULT_BYTE_STRING);
        cos.writeBytes(2, SIGNAL_DEFAULT_BYTE_STRING);
        cos.writeUInt64(3, 0);
        cos.writeTag(4, 2);
        cos.writeRawVarint32(getBinaryImageSize());
        cos.writeUInt64(1, 0);
        cos.writeUInt64(2, 0);
        cos.writeBytes(3, this.packageName);
        if (this.optionalBuildIdBytes != null) {
            cos.writeBytes(4, this.optionalBuildIdBytes);
        }
    }

    private void writeSessionEventAppCustomAttributes(CodedOutputStream cos, Map<String, String> customAttributes) throws Exception {
        for (Entry<String, String> entry : customAttributes.entrySet()) {
            cos.writeTag(2, 2);
            cos.writeRawVarint32(getEventAppCustomAttributeSize((String) entry.getKey(), (String) entry.getValue()));
            cos.writeBytes(1, ByteString.copyFromUtf8((String) entry.getKey()));
            String value = (String) entry.getValue();
            if (value == null) {
                value = "";
            }
            cos.writeBytes(2, ByteString.copyFromUtf8(value));
        }
    }

    private int getBinaryImageSize() {
        int size = ((0 + CodedOutputStream.computeUInt64Size(1, 0)) + CodedOutputStream.computeUInt64Size(2, 0)) + CodedOutputStream.computeBytesSize(3, this.packageName);
        if (this.optionalBuildIdBytes != null) {
            return size + CodedOutputStream.computeBytesSize(4, this.optionalBuildIdBytes);
        }
        return size;
    }

    private void writeSessionEventAppExecutionException(CodedOutputStream cos, Throwable ex, int chainDepth, int field) throws Exception {
        cos.writeTag(field, 2);
        cos.writeRawVarint32(getEventAppExecutionExceptionSize(ex, 1));
        cos.writeBytes(1, ByteString.copyFromUtf8(ex.getClass().getName()));
        String message = ex.getLocalizedMessage();
        if (message != null) {
            cos.writeBytes(3, ByteString.copyFromUtf8(message));
        }
        for (StackTraceElement element : ex.getStackTrace()) {
            writeFrame(cos, 4, element, true);
        }
        Throwable cause = ex.getCause();
        if (cause == null) {
            return;
        }
        if (chainDepth < this.maxChainedExceptionsDepth) {
            writeSessionEventAppExecutionException(cos, cause, chainDepth + 1, 6);
            return;
        }
        int overflowCount = 0;
        while (cause != null) {
            cause = cause.getCause();
            overflowCount++;
        }
        cos.writeUInt32(7, overflowCount);
    }

    private int getThreadSize(Thread thread, StackTraceElement[] stackTraceElements, int importance, boolean isCrashedThread) {
        int size = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(thread.getName())) + CodedOutputStream.computeUInt32Size(2, importance);
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            int frameSize = getFrameSize(stackTraceElement, isCrashedThread);
            size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(frameSize)) + frameSize;
        }
        return size;
    }

    private void writeThread(CodedOutputStream cos, Thread thread, StackTraceElement[] stackTraceElements, int importance, boolean isCrashedThread) throws Exception {
        cos.writeTag(1, 2);
        cos.writeRawVarint32(getThreadSize(thread, stackTraceElements, importance, isCrashedThread));
        cos.writeBytes(1, ByteString.copyFromUtf8(thread.getName()));
        cos.writeUInt32(2, importance);
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            writeFrame(cos, 3, stackTraceElement, isCrashedThread);
        }
    }

    private int getFrameSize(StackTraceElement element, boolean isCrashedThread) {
        int size;
        int i = 2;
        if (element.isNativeMethod()) {
            size = 0 + CodedOutputStream.computeUInt64Size(1, (long) Math.max(element.getLineNumber(), 0));
        } else {
            size = 0 + CodedOutputStream.computeUInt64Size(1, 0);
        }
        size += CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(element.getClassName() + "." + element.getMethodName()));
        if (element.getFileName() != null) {
            size += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(element.getFileName()));
        }
        if (!element.isNativeMethod() && element.getLineNumber() > 0) {
            size += CodedOutputStream.computeUInt64Size(4, (long) element.getLineNumber());
        }
        if (!isCrashedThread) {
            i = 0;
        }
        return size + CodedOutputStream.computeUInt32Size(5, i);
    }

    private void writeFrame(CodedOutputStream cos, int fieldIndex, StackTraceElement element, boolean isCrashedThread) throws Exception {
        int i = 4;
        cos.writeTag(fieldIndex, 2);
        cos.writeRawVarint32(getFrameSize(element, isCrashedThread));
        if (element.isNativeMethod()) {
            cos.writeUInt64(1, (long) Math.max(element.getLineNumber(), 0));
        } else {
            cos.writeUInt64(1, 0);
        }
        cos.writeBytes(2, ByteString.copyFromUtf8(element.getClassName() + "." + element.getMethodName()));
        if (element.getFileName() != null) {
            cos.writeBytes(3, ByteString.copyFromUtf8(element.getFileName()));
        }
        if (!element.isNativeMethod() && element.getLineNumber() > 0) {
            cos.writeUInt64(4, (long) element.getLineNumber());
        }
        if (!isCrashedThread) {
            i = 0;
        }
        cos.writeUInt32(5, i);
    }

    private void writeSessionEventDevice(CodedOutputStream cos, float batteryLevel, int batterVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed) throws Exception {
        cos.writeTag(5, 2);
        cos.writeRawVarint32(getEventDeviceSize(batteryLevel, batterVelocity, proximityEnabled, orientation, heapAllocatedSize, diskUsed));
        cos.writeFloat(1, batteryLevel);
        cos.writeSInt32(2, batterVelocity);
        cos.writeBool(3, proximityEnabled);
        cos.writeUInt32(4, orientation);
        cos.writeUInt64(5, heapAllocatedSize);
        cos.writeUInt64(6, diskUsed);
    }

    private void writeSessionEventLog(CodedOutputStream cos, ByteString log) throws Exception {
        if (log != null) {
            cos.writeTag(6, 2);
            cos.writeRawVarint32(getEventLogSize(log));
            cos.writeBytes(1, log);
        }
    }

    private int getSessionEventSize(Thread thread, Throwable ex, String eventType, long eventTime, Map<String, String> custAttrs, float batteryLevel, int batterVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed, ByteString log) {
        int size = (0 + CodedOutputStream.computeUInt64Size(1, eventTime)) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(eventType));
        int eventAppSize = getEventAppSize(thread, ex, custAttrs);
        size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(eventAppSize)) + eventAppSize;
        int eventDeviceSize = getEventDeviceSize(batteryLevel, batterVelocity, proximityEnabled, orientation, heapAllocatedSize, diskUsed);
        size += (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(eventDeviceSize)) + eventDeviceSize;
        if (log == null) {
            return size;
        }
        int logSize = getEventLogSize(log);
        return size + ((CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(logSize)) + logSize);
    }

    private int getEventAppSize(Thread thread, Throwable ex, Map<String, String> customAttributes) {
        int executionSize = getEventAppExecutionSize(thread, ex);
        int size = 0 + ((CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(executionSize)) + executionSize);
        if (customAttributes != null) {
            for (Entry<String, String> entry : customAttributes.entrySet()) {
                int entrySize = getEventAppCustomAttributeSize((String) entry.getKey(), (String) entry.getValue());
                size += (CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(entrySize)) + entrySize;
            }
        }
        if (this.runningAppProcessInfo != null) {
            size += CodedOutputStream.computeBoolSize(3, this.runningAppProcessInfo.importance != 100);
        }
        return size + CodedOutputStream.computeUInt32Size(4, this.crashlytics.getContext().getResources().getConfiguration().orientation);
    }

    private int getEventAppExecutionSize(Thread exceptionThread, Throwable ex) {
        int threadSize = getThreadSize(exceptionThread, this.exceptionStack, 4, true);
        int size = 0 + ((CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize)) + threadSize);
        int len = this.threads.length;
        for (int i = 0; i < len; i++) {
            threadSize = getThreadSize(this.threads[i], (StackTraceElement[]) this.stacks.get(i), 0, false);
            size += (CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize)) + threadSize;
        }
        int exceptionSize = getEventAppExecutionExceptionSize(ex, 1);
        size += (CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(exceptionSize)) + exceptionSize;
        int signalSize = getEventAppExecutionSignalSize();
        size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(signalSize)) + signalSize;
        int binaryImageSize = getBinaryImageSize();
        return size + ((CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(binaryImageSize)) + binaryImageSize);
    }

    private int getEventAppCustomAttributeSize(String key, String value) {
        int size = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(key));
        if (value == null) {
            value = "";
        }
        return size + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(value));
    }

    private int getEventDeviceSize(float batteryLevel, int batterVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed) {
        return (((((0 + CodedOutputStream.computeFloatSize(1, batteryLevel)) + CodedOutputStream.computeSInt32Size(2, batterVelocity)) + CodedOutputStream.computeBoolSize(3, proximityEnabled)) + CodedOutputStream.computeUInt32Size(4, orientation)) + CodedOutputStream.computeUInt64Size(5, heapAllocatedSize)) + CodedOutputStream.computeUInt64Size(6, diskUsed);
    }

    private int getEventLogSize(ByteString log) {
        return CodedOutputStream.computeBytesSize(1, log);
    }

    private int getEventAppExecutionExceptionSize(Throwable ex, int chainDepth) {
        int size = 0 + CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(ex.getClass().getName()));
        String message = ex.getLocalizedMessage();
        if (message != null) {
            size += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(message));
        }
        for (StackTraceElement element : ex.getStackTrace()) {
            int frameSize = getFrameSize(element, true);
            size += (CodedOutputStream.computeTagSize(4) + CodedOutputStream.computeRawVarint32Size(frameSize)) + frameSize;
        }
        Throwable cause = ex.getCause();
        if (cause == null) {
            return size;
        }
        if (chainDepth < this.maxChainedExceptionsDepth) {
            int exceptionSize = getEventAppExecutionExceptionSize(cause, chainDepth + 1);
            return size + ((CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(exceptionSize)) + exceptionSize);
        }
        int overflowCount = 0;
        while (cause != null) {
            cause = cause.getCause();
            overflowCount++;
        }
        return size + CodedOutputStream.computeUInt32Size(7, overflowCount);
    }

    private int getEventAppExecutionSignalSize() {
        return ((0 + CodedOutputStream.computeBytesSize(1, SIGNAL_DEFAULT_BYTE_STRING)) + CodedOutputStream.computeBytesSize(2, SIGNAL_DEFAULT_BYTE_STRING)) + CodedOutputStream.computeUInt64Size(3, 0);
    }

    private static String getMessage(Throwable t) {
        String message = t.getLocalizedMessage();
        if (message == null) {
            return null;
        }
        return message.replaceAll("(\r\n|\n|\f)", " ");
    }

    void cleanInvalidTempFiles() {
        executeAsync(new Runnable() {
            public void run() {
                CrashlyticsUncaughtExceptionHandler.this.doCleanInvalidTempFiles(CrashlyticsUncaughtExceptionHandler.this.listFilesMatching(ClsFileOutputStream.TEMP_FILENAME_FILTER));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] invalidFiles) {
        deleteLegacyInvalidCacheDir();
        for (File invalidFile : invalidFiles) {
            Fabric.getLogger().d("Fabric", "Found invalid session part file: " + invalidFile);
            final String sessionId = getSessionIdFromSessionFile(invalidFile);
            FilenameFilter sessionFilter = new FilenameFilter() {
                public boolean accept(File f, String name) {
                    return name.startsWith(sessionId);
                }
            };
            Fabric.getLogger().d("Fabric", "Deleting all part files for invalid session: " + sessionId);
            for (File sessionFile : listFilesMatching(sessionFilter)) {
                Fabric.getLogger().d("Fabric", "Deleting session file: " + sessionFile);
                sessionFile.delete();
            }
        }
    }

    private void deleteLegacyInvalidCacheDir() {
        File cacheDir = new File(this.crashlytics.getSdkDirectory(), INVALID_CLS_CACHE_DIR);
        if (cacheDir.exists()) {
            if (cacheDir.isDirectory()) {
                for (File cacheFile : cacheDir.listFiles()) {
                    cacheFile.delete();
                }
            }
            cacheDir.delete();
        }
    }

    private <T> T executeSyncLoggingException(Callable<T> callable) {
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                return this.executorService.submit(callable).get(4, TimeUnit.SECONDS);
            }
            return this.executorService.submit(callable).get();
        } catch (RejectedExecutionException e) {
            Fabric.getLogger().d("Fabric", "Executor is shut down because we're handling a fatal crash.");
            return null;
        } catch (Exception e2) {
            Fabric.getLogger().e("Fabric", "Failed to execute task.", e2);
            return null;
        }
    }

    private Future<?> executeAsync(final Runnable runnable) {
        try {
            return this.executorService.submit(new Runnable() {
                public void run() {
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        Fabric.getLogger().e("Fabric", "Failed to execute task.", e);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            Fabric.getLogger().d("Fabric", "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }

    private <T> Future<T> executeAsync(final Callable<T> callable) {
        try {
            return this.executorService.submit(new Callable<T>() {
                public T call() throws Exception {
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        Fabric.getLogger().e("Fabric", "Failed to execute task.", e);
                        return null;
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            Fabric.getLogger().d("Fabric", "Executor is shut down because we're handling a fatal crash.");
            return null;
        }
    }
}
