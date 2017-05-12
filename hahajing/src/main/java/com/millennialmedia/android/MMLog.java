package com.millennialmedia.android;

import android.util.Log;

public class MMLog {

    interface LogHandler {
        void handleLog(String str);
    }

    static class LoggingComponent {
        private static final String TAG_STARTER = "MMSDK-";
        private static int logLevel = 4;
        private LogHandler registeredLogHandler;

        LoggingComponent() {
        }

        public void setLogLevel(int level) {
            logLevel = level;
        }

        public int getLogLevel() {
            return logLevel;
        }

        void v(String classTag, String logMessage) {
            if (logLevel <= 2) {
                vInternal(classTag, logMessage);
            }
        }

        void v(String classTag, String logMessage, Throwable throwable) {
            if (logLevel <= 2) {
                vInternal(classTag, logMessage + ": " + Log.getStackTraceString(throwable));
            }
        }

        private void vInternal(String tag, String message) {
            Log.v(TAG_STARTER + tag, message);
            callLogHandler(message);
        }

        void d(String classTag, String logMessage) {
            if (logLevel <= 3) {
                dInternal(classTag, logMessage);
            }
        }

        void d(String classTag, String logMessage, Throwable throwable) {
            if (logLevel <= 3) {
                dInternal(classTag, logMessage + ": " + Log.getStackTraceString(throwable));
            }
        }

        private void dInternal(String tag, String message) {
            Log.d(TAG_STARTER + tag, message);
            callLogHandler(message);
        }

        void i(String classTag, String logMessage) {
            if (logLevel <= 4) {
                iInternal(classTag, logMessage);
            }
        }

        void i(String classTag, String logMessage, Throwable throwable) {
            if (logLevel <= 4) {
                iInternal(classTag, logMessage + ": " + Log.getStackTraceString(throwable));
            }
        }

        private void iInternal(String tag, String message) {
            Log.i(TAG_STARTER + tag, message);
            callLogHandler(message);
        }

        void w(String classTag, String logMessage) {
            if (logLevel <= 5) {
                wInternal(classTag, logMessage);
            }
        }

        void w(String classTag, String logMessage, Throwable throwable) {
            if (logLevel <= 5) {
                wInternal(classTag, logMessage + ": " + Log.getStackTraceString(throwable));
            }
        }

        private void wInternal(String tag, String message) {
            Log.w(TAG_STARTER + tag, message);
            callLogHandler(message);
        }

        void e(String classTag, String logMessage) {
            if (logLevel <= 6) {
                eInternal(classTag, logMessage);
            }
        }

        void e(String classTag, String logMessage, Throwable throwable) {
            if (logLevel <= 6) {
                eInternal(classTag, logMessage + ": " + Log.getStackTraceString(throwable));
            }
        }

        private void eInternal(String tag, String message) {
            Log.e(TAG_STARTER + tag, message);
            callLogHandler(message);
        }

        void registerLogHandler(LogHandler logHandler) {
            this.registeredLogHandler = logHandler;
        }

        private void callLogHandler(String message) {
            if (this.registeredLogHandler != null) {
                this.registeredLogHandler.handleLog(message);
            }
        }
    }

    static {
        ComponentRegistry.addLoggingComponent(new LoggingComponent());
    }

    public static void setLogLevel(int level) {
        ComponentRegistry.getLoggingComponent().setLogLevel(level);
    }

    public static int getLogLevel() {
        return ComponentRegistry.getLoggingComponent().getLogLevel();
    }

    static void registerLogHandler(LogHandler logHandler) {
        ComponentRegistry.getLoggingComponent().registerLogHandler(logHandler);
    }

    static void v(String classTag, String logMessage) {
        ComponentRegistry.getLoggingComponent().v(classTag, logMessage);
    }

    static void v(String classTag, String logMessage, Throwable throwable) {
        ComponentRegistry.getLoggingComponent().v(classTag, logMessage, throwable);
    }

    static void d(String classTag, String logMessage) {
        ComponentRegistry.getLoggingComponent().d(classTag, logMessage);
    }

    static void d(String classTag, String logMessage, Throwable throwable) {
        ComponentRegistry.getLoggingComponent().d(classTag, logMessage, throwable);
    }

    static void i(String classTag, String logMessage) {
        ComponentRegistry.getLoggingComponent().i(classTag, logMessage);
    }

    static void i(String classTag, String logMessage, Throwable throwable) {
        ComponentRegistry.getLoggingComponent().i(classTag, logMessage, throwable);
    }

    static void w(String classTag, String logMessage) {
        ComponentRegistry.getLoggingComponent().w(classTag, logMessage);
    }

    static void w(String classTag, String logMessage, Throwable throwable) {
        ComponentRegistry.getLoggingComponent().w(classTag, logMessage, throwable);
    }

    static void e(String classTag, String logMessage) {
        ComponentRegistry.getLoggingComponent().e(classTag, logMessage);
    }

    static void e(String classTag, String logMessage, Throwable throwable) {
        ComponentRegistry.getLoggingComponent().e(classTag, logMessage, throwable);
    }
}
