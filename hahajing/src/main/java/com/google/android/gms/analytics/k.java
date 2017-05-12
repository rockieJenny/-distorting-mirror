package com.google.android.gms.analytics;

import android.content.Context;
import com.google.analytics.tracking.android.Fields;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

class k implements q {
    private static final Object xO = new Object();
    private static k yD;
    private final Context mContext;
    private String yE;
    private boolean yF = false;
    private final Object yG = new Object();

    protected k(Context context) {
        this.mContext = context;
        ep();
    }

    private boolean ae(String str) {
        try {
            ae.V("Storing clientId.");
            FileOutputStream openFileOutput = this.mContext.openFileOutput("gaClientId", 0);
            openFileOutput.write(str.getBytes());
            openFileOutput.close();
            return true;
        } catch (FileNotFoundException e) {
            ae.T("Error creating clientId file.");
            return false;
        } catch (IOException e2) {
            ae.T("Error writing to clientId file.");
            return false;
        }
    }

    public static k el() {
        k kVar;
        synchronized (xO) {
            kVar = yD;
        }
        return kVar;
    }

    private String en() {
        if (!this.yF) {
            synchronized (this.yG) {
                if (!this.yF) {
                    ae.V("Waiting for clientId to load");
                    do {
                        try {
                            this.yG.wait();
                        } catch (InterruptedException e) {
                            ae.T("Exception while waiting for clientId: " + e);
                        }
                    } while (!this.yF);
                }
            }
        }
        ae.V("Loaded clientId");
        return this.yE;
    }

    private void ep() {
        new Thread(this, "client_id_fetcher") {
            final /* synthetic */ k yH;

            public void run() {
                synchronized (this.yH.yG) {
                    this.yH.yE = this.yH.eq();
                    this.yH.yF = true;
                    this.yH.yG.notifyAll();
                }
            }
        }.start();
    }

    public static void y(Context context) {
        synchronized (xO) {
            if (yD == null) {
                yD = new k(context);
            }
        }
    }

    public boolean ac(String str) {
        return Fields.CLIENT_ID.equals(str);
    }

    String em() {
        String str;
        synchronized (this.yG) {
            this.yE = eo();
            str = this.yE;
        }
        return str;
    }

    protected String eo() {
        String toLowerCase = UUID.randomUUID().toString().toLowerCase();
        try {
            return !ae(toLowerCase) ? "0" : toLowerCase;
        } catch (Exception e) {
            return null;
        }
    }

    String eq() {
        String str = null;
        try {
            FileInputStream openFileInput = this.mContext.openFileInput("gaClientId");
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                ae.T("clientId file seems corrupted, deleting it.");
                openFileInput.close();
                this.mContext.deleteFile("gaClientId");
            } else if (read <= 0) {
                ae.T("clientId file seems empty, deleting it.");
                openFileInput.close();
                this.mContext.deleteFile("gaClientId");
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                    ae.V("Loaded client id from disk.");
                    str = str2;
                } catch (FileNotFoundException e) {
                    str = str2;
                } catch (IOException e2) {
                    str = str2;
                    ae.T("Error reading clientId file, deleting it.");
                    this.mContext.deleteFile("gaClientId");
                }
            }
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
            ae.T("Error reading clientId file, deleting it.");
            this.mContext.deleteFile("gaClientId");
        }
        return str == null ? eo() : str;
    }

    public String getValue(String field) {
        return Fields.CLIENT_ID.equals(field) ? en() : null;
    }
}
