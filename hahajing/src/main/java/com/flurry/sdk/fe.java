package com.flurry.sdk;

import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class fe {
    private static final String a = fe.class.getSimpleName();
    private static fe b;
    private final Set<String> c = v();
    private final Map<fl, byte[]> d = new HashMap();
    private a e = a.NONE;
    private Info f;
    private String g;
    private byte[] h;

    enum a {
        NONE,
        ADVERTISING,
        DEVICE,
        HASHED_IMEI,
        REPORTED_IDS,
        FINISHED
    }

    public static synchronized fe a() {
        fe feVar;
        synchronized (fe.class) {
            if (b == null) {
                b = new fe();
            }
            feVar = b;
        }
        return feVar;
    }

    public static void b() {
        b = null;
    }

    private fe() {
        fp.a().b(new hq(this) {
            final /* synthetic */ fe a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.i();
            }
        });
    }

    public boolean c() {
        return a.FINISHED.equals(this.e);
    }

    public String d() {
        if (this.f == null) {
            return null;
        }
        return this.f.getId();
    }

    public boolean e() {
        if (this.f != null && this.f.isLimitAdTrackingEnabled()) {
            return false;
        }
        return true;
    }

    public String f() {
        return this.g;
    }

    public byte[] g() {
        return this.h;
    }

    public Map<fl, byte[]> h() {
        return Collections.unmodifiableMap(this.d);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i() {
        /*
        r5 = this;
    L_0x0000:
        r0 = com.flurry.sdk.fe.a.FINISHED;
        r1 = r5.e;
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x0075;
    L_0x000a:
        r0 = com.flurry.sdk.fe.AnonymousClass3.a;
        r1 = r5.e;
        r1 = r1.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 1: goto L_0x0050;
            case 2: goto L_0x0055;
            case 3: goto L_0x005a;
            case 4: goto L_0x005f;
            case 5: goto L_0x0064;
            default: goto L_0x0017;
        };
    L_0x0017:
        r0 = com.flurry.sdk.fe.AnonymousClass3.a;	 Catch:{ Exception -> 0x0029 }
        r1 = r5.e;	 Catch:{ Exception -> 0x0029 }
        r1 = r1.ordinal();	 Catch:{ Exception -> 0x0029 }
        r0 = r0[r1];	 Catch:{ Exception -> 0x0029 }
        switch(r0) {
            case 2: goto L_0x0025;
            case 3: goto L_0x0069;
            case 4: goto L_0x006d;
            case 5: goto L_0x0071;
            default: goto L_0x0024;
        };	 Catch:{ Exception -> 0x0029 }
    L_0x0024:
        goto L_0x0000;
    L_0x0025:
        r5.j();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0029:
        r0 = move-exception;
        r1 = 4;
        r2 = a;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Exception during id fetch:";
        r3 = r3.append(r4);
        r4 = r5.e;
        r3 = r3.append(r4);
        r4 = ", ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        com.flurry.sdk.gd.a(r1, r2, r0);
        goto L_0x0000;
    L_0x0050:
        r0 = com.flurry.sdk.fe.a.ADVERTISING;
        r5.e = r0;
        goto L_0x0017;
    L_0x0055:
        r0 = com.flurry.sdk.fe.a.DEVICE;
        r5.e = r0;
        goto L_0x0017;
    L_0x005a:
        r0 = com.flurry.sdk.fe.a.HASHED_IMEI;
        r5.e = r0;
        goto L_0x0017;
    L_0x005f:
        r0 = com.flurry.sdk.fe.a.REPORTED_IDS;
        r5.e = r0;
        goto L_0x0017;
    L_0x0064:
        r0 = com.flurry.sdk.fe.a.FINISHED;
        r5.e = r0;
        goto L_0x0017;
    L_0x0069:
        r5.k();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x006d:
        r5.l();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0071:
        r5.x();	 Catch:{ Exception -> 0x0029 }
        goto L_0x0000;
    L_0x0075:
        r0 = new com.flurry.sdk.ff;
        r0.<init>();
        r1 = com.flurry.sdk.fz.a();
        r1.a(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.fe.i():void");
    }

    private void j() {
        hp.b();
        if (m()) {
            this.f = n();
        }
    }

    private void k() {
        hp.b();
        this.g = o();
    }

    private void l() {
        if (fp.a().c().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
            w();
        }
    }

    private boolean m() {
        try {
            int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(fp.a().c());
            if (isGooglePlayServicesAvailable == 0) {
                return true;
            }
            gd.e(a, "Google Play Services not available - connection result: " + isGooglePlayServicesAvailable);
            return false;
        } catch (NoClassDefFoundError e) {
            gd.b(a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return false;
        } catch (Exception e2) {
            gd.b(a, "GOOGLE PLAY SERVICES EXCEPTION: " + e2.getMessage());
            gd.b(a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return false;
        }
    }

    private Info n() {
        Info info = null;
        try {
            info = AdvertisingIdClient.getAdvertisingIdInfo(fp.a().c());
        } catch (Exception e) {
            gd.b(a, "GOOGLE PLAY SERVICES ERROR: " + e.getMessage());
            gd.b(a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
        }
        return info;
    }

    private String o() {
        Object p = p();
        return !TextUtils.isEmpty(p) ? p : q();
    }

    private String p() {
        String string = Secure.getString(fp.a().c().getContentResolver(), "android_id");
        if (a(string)) {
            return "AND" + string;
        }
        return null;
    }

    private String q() {
        String s = s();
        if (TextUtils.isEmpty(s)) {
            s = t();
            if (TextUtils.isEmpty(s)) {
                s = r();
            }
            b(s);
        }
        return s;
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str) || c(str.toLowerCase(Locale.US))) {
            return false;
        }
        return true;
    }

    private String r() {
        return "ID" + Long.toString(Double.doubleToLongBits(Math.random()) + ((System.nanoTime() + (hp.i(hm.c(fp.a().c())) * 37)) * 37), 16);
    }

    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            File fileStreamPath = fp.a().c().getFileStreamPath(u());
            if (ho.a(fileStreamPath)) {
                a(str, fileStreamPath);
            }
        }
    }

    private void a(String str, File file) {
        Closeable dataOutputStream;
        Throwable th;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                a(str, (DataOutput) dataOutputStream);
                hp.a(dataOutputStream);
            } catch (Throwable th2) {
                th = th2;
                try {
                    gd.a(6, a, "Error when saving deviceId", th);
                    hp.a(dataOutputStream);
                } catch (Throwable th3) {
                    th = th3;
                    hp.a(dataOutputStream);
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            dataOutputStream = null;
            hp.a(dataOutputStream);
            throw th;
        }
    }

    private String s() {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        String str = null;
        File fileStreamPath = fp.a().c().getFileStreamPath(u());
        if (fileStreamPath != null && fileStreamPath.exists()) {
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    str = a((DataInput) dataInputStream);
                    hp.a(dataInputStream);
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        gd.a(6, a, "Error when loading deviceId", th);
                        hp.a(dataInputStream);
                        return str;
                    } catch (Throwable th4) {
                        th2 = th4;
                        hp.a(dataInputStream);
                        throw th2;
                    }
                }
            } catch (Throwable th5) {
                dataInputStream = str;
                th2 = th5;
                hp.a(dataInputStream);
                throw th2;
            }
        }
        return str;
    }

    private String t() {
        Throwable th;
        Throwable th2;
        String str = null;
        File filesDir = fp.a().c().getFilesDir();
        if (filesDir != null) {
            String[] list = filesDir.list(new FilenameFilter(this) {
                final /* synthetic */ fe a;

                {
                    this.a = r1;
                }

                public boolean accept(File file, String str) {
                    return str.startsWith(".flurryagent.");
                }
            });
            if (!(list == null || list.length == 0)) {
                filesDir = fp.a().c().getFileStreamPath(list[0]);
                if (filesDir != null && filesDir.exists()) {
                    Closeable dataInputStream;
                    try {
                        dataInputStream = new DataInputStream(new FileInputStream(filesDir));
                        try {
                            str = b((DataInput) dataInputStream);
                            hp.a(dataInputStream);
                        } catch (Throwable th3) {
                            th = th3;
                            try {
                                gd.a(6, a, "Error when loading deviceId", th);
                                hp.a(dataInputStream);
                                return str;
                            } catch (Throwable th4) {
                                th2 = th4;
                                hp.a(dataInputStream);
                                throw th2;
                            }
                        }
                    } catch (Throwable th5) {
                        dataInputStream = str;
                        th2 = th5;
                        hp.a(dataInputStream);
                        throw th2;
                    }
                }
            }
        }
        return str;
    }

    private void a(String str, DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(1);
        dataOutput.writeUTF(str);
    }

    private String a(DataInput dataInput) throws IOException {
        if (1 != dataInput.readInt()) {
            return null;
        }
        return dataInput.readUTF();
    }

    private String b(DataInput dataInput) throws IOException {
        if (46586 != dataInput.readUnsignedShort() || 2 != dataInput.readUnsignedShort()) {
            return null;
        }
        dataInput.readUTF();
        return dataInput.readUTF();
    }

    private String u() {
        return ".flurryb.";
    }

    private boolean c(String str) {
        return this.c.contains(str);
    }

    private Set<String> v() {
        Set hashSet = new HashSet();
        hashSet.add("null");
        hashSet.add("9774d56d682e549c");
        hashSet.add("dead00beef");
        return Collections.unmodifiableSet(hashSet);
    }

    private void w() {
        TelephonyManager telephonyManager = (TelephonyManager) fp.a().c().getSystemService("phone");
        if (telephonyManager != null) {
            String deviceId = telephonyManager.getDeviceId();
            if (deviceId != null && deviceId.trim().length() > 0) {
                try {
                    byte[] f = hp.f(deviceId);
                    if (f == null || f.length != 20) {
                        gd.a(6, a, "sha1 is not " + 20 + " bytes long: " + Arrays.toString(f));
                    } else {
                        this.h = f;
                    }
                } catch (Exception e) {
                    gd.a(6, a, "Exception in generateHashedImei()");
                }
            }
        }
    }

    private void x() {
        String d = d();
        if (d != null) {
            gd.a(3, a, "Fetched advertising id");
            this.d.put(fl.AndroidAdvertisingId, hp.e(d));
        }
        d = f();
        if (d != null) {
            gd.a(3, a, "Fetched device id");
            this.d.put(fl.DeviceId, hp.e(d));
        }
        Object g = g();
        if (g != null) {
            gd.a(3, a, "Fetched hashed IMEI");
            this.d.put(fl.Sha1Imei, g);
        }
    }
}
