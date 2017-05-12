package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.hh.a;

public class fm implements a {
    private static fm a;
    private static final String b = fm.class.getSimpleName();
    private String c;
    private String d;

    public static synchronized fm a() {
        fm fmVar;
        synchronized (fm.class) {
            if (a == null) {
                a = new fm();
            }
            fmVar = a;
        }
        return fmVar;
    }

    public static void b() {
        if (a != null) {
            hg.a().b("VersionName", a);
        }
        a = null;
    }

    private fm() {
        hh a = hg.a();
        this.c = (String) a.a("VersionName");
        a.a("VersionName", (a) this);
        gd.a(4, b, "initSettings, VersionName = " + this.c);
    }

    public String c() {
        return VERSION.RELEASE;
    }

    public String d() {
        return Build.DEVICE;
    }

    public synchronized String e() {
        String str;
        if (!TextUtils.isEmpty(this.c)) {
            str = this.c;
        } else if (TextUtils.isEmpty(this.d)) {
            this.d = f();
            str = this.d;
        } else {
            str = this.d;
        }
        return str;
    }

    private String f() {
        try {
            Context c = fp.a().c();
            PackageInfo packageInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            if (packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            if (packageInfo.versionCode != 0) {
                return Integer.toString(packageInfo.versionCode);
            }
            return "Unknown";
        } catch (Throwable th) {
            gd.a(6, b, "", th);
        }
    }

    public void a(String str, Object obj) {
        if (str.equals("VersionName")) {
            this.c = (String) obj;
            gd.a(4, b, "onSettingUpdate, VersionName = " + this.c);
            return;
        }
        gd.a(6, b, "onSettingUpdate internal error!");
    }
}
