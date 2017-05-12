package com.google.android.gms.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.google.analytics.tracking.android.Fields;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

class a implements q {
    private static Object xO = new Object();
    private static a xP;
    private Context mContext;
    private Info xQ;
    private long xR;
    private String xS;
    private boolean xT = false;
    private Object xU = new Object();

    a(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private boolean a(Info info, Info info2) {
        String str = null;
        String id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        k.y(this.mContext);
        k el = k.el();
        String value = el.getValue(Fields.CLIENT_ID);
        synchronized (this.xU) {
            if (!this.xT) {
                this.xS = x(this.mContext);
                this.xT = true;
            } else if (TextUtils.isEmpty(this.xS)) {
                if (info != null) {
                    str = info.getId();
                }
                if (str == null) {
                    boolean ab = ab(id + value);
                    return ab;
                }
                this.xS = aa(str + value);
            }
            Object aa = aa(id + value);
            if (TextUtils.isEmpty(aa)) {
                return false;
            } else if (aa.equals(this.xS)) {
                return true;
            } else {
                if (TextUtils.isEmpty(this.xS)) {
                    str = value;
                } else {
                    ae.V("Resetting the client id because Advertising Id changed.");
                    str = el.em();
                    ae.V("New client Id: " + str);
                }
                ab = ab(id + str);
                return ab;
            }
        }
    }

    static String aa(String str) {
        if (an.ap(CommonUtils.MD5_INSTANCE) == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, an.ap(CommonUtils.MD5_INSTANCE).digest(str.getBytes()))});
    }

    private boolean ab(String str) {
        try {
            String aa = aa(str);
            ae.V("Storing hashed adid.");
            FileOutputStream openFileOutput = this.mContext.openFileOutput("gaClientIdData", 0);
            openFileOutput.write(aa.getBytes());
            openFileOutput.close();
            this.xS = aa;
            return true;
        } catch (FileNotFoundException e) {
            ae.T("Error creating hash file.");
            return false;
        } catch (IOException e2) {
            ae.T("Error writing to hash file.");
            return false;
        }
    }

    public static q w(Context context) {
        if (xP == null) {
            synchronized (xO) {
                if (xP == null) {
                    xP = new a(context);
                }
            }
        }
        return xP;
    }

    static String x(Context context) {
        String str = null;
        try {
            FileInputStream openFileInput = context.openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                ae.W("Hash file seems corrupted, deleting it.");
                openFileInput.close();
                context.deleteFile("gaClientIdData");
                return null;
            } else if (read <= 0) {
                ae.U("Hash file is empty.");
                openFileInput.close();
                return null;
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                    return str2;
                } catch (FileNotFoundException e) {
                    return str2;
                } catch (IOException e2) {
                    str = str2;
                    ae.W("Error reading Hash file, deleting it.");
                    context.deleteFile("gaClientIdData");
                    return str;
                }
            }
        } catch (FileNotFoundException e3) {
            return null;
        } catch (IOException e4) {
            ae.W("Error reading Hash file, deleting it.");
            context.deleteFile("gaClientIdData");
            return str;
        }
    }

    Info dP() {
        Info info = null;
        try {
            info = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
        } catch (IllegalStateException e) {
            ae.W("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
        } catch (GooglePlayServicesRepairableException e2) {
            ae.W("GooglePlayServicesRepairableException getting Ad Id Info");
        } catch (IOException e3) {
            ae.W("IOException getting Ad Id Info");
        } catch (GooglePlayServicesNotAvailableException e4) {
            ae.W("GooglePlayServicesNotAvailableException getting Ad Id Info");
        } catch (Throwable th) {
            ae.W("Unknown exception. Could not get the ad Id.");
        }
        return info;
    }

    public String getValue(String field) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.xR > 1000) {
            Info dP = dP();
            if (a(this.xQ, dP)) {
                this.xQ = dP;
            } else {
                this.xQ = new Info("", false);
            }
            this.xR = currentTimeMillis;
        }
        if (this.xQ != null) {
            if ("&adid".equals(field)) {
                return this.xQ.getId();
            }
            if ("&ate".equals(field)) {
                return this.xQ.isLimitAdTrackingEnabled() ? "0" : "1";
            }
        }
        return null;
    }
}
