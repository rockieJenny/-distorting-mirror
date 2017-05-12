package com.google.android.gms.analytics;

public class ae {
    private static GoogleAnalytics BL;
    private static volatile boolean BM = false;
    private static Logger BN;

    private ae() {
    }

    public static void T(String str) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.error(str);
        }
    }

    public static void U(String str) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.info(str);
        }
    }

    public static void V(String str) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.verbose(str);
        }
    }

    public static void W(String str) {
        Logger logger = getLogger();
        if (logger != null) {
            logger.warn(str);
        }
    }

    public static boolean ff() {
        return getLogger() != null && getLogger().getLogLevel() == 0;
    }

    static Logger getLogger() {
        synchronized (ae.class) {
            if (BM) {
                if (BN == null) {
                    BN = new p();
                }
                Logger logger = BN;
                return logger;
            }
            if (BL == null) {
                BL = GoogleAnalytics.eY();
            }
            if (BL != null) {
                logger = BL.getLogger();
                return logger;
            }
            return null;
        }
    }
}
