package com.jirbo.adcolony;

import android.content.pm.PackageManager.NameNotFoundException;
import com.givewaygames.ads.BuildConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

class ab {
    static byte[] a = new byte[1024];
    static StringBuilder b = new StringBuilder();

    static class a {
        long a = System.currentTimeMillis();

        a() {
        }

        void a() {
            this.a = System.currentTimeMillis();
        }

        double b() {
            return ((double) (System.currentTimeMillis() - this.a)) / 1000.0d;
        }

        public String toString() {
            return ab.a(b(), 2);
        }
    }

    static class b {
        double a = ((double) System.currentTimeMillis());

        b(double d) {
            a(d);
        }

        void a(double d) {
            this.a = (((double) System.currentTimeMillis()) / 1000.0d) + d;
        }

        boolean a() {
            return b() == 0.0d;
        }

        double b() {
            double currentTimeMillis = this.a - (((double) System.currentTimeMillis()) / 1000.0d);
            if (currentTimeMillis <= 0.0d) {
                return 0.0d;
            }
            return currentTimeMillis;
        }

        public String toString() {
            return ab.a(b(), 2);
        }
    }

    ab() {
    }

    static boolean a(String str) {
        try {
            AdColony.activity().getApplication().getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static String a() {
        try {
            return AdColony.activity().getPackageManager().getPackageInfo(AdColony.activity().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            a.e("Failed to retrieve package info.");
            return BuildConfig.VERSION_NAME;
        }
    }

    static String b(String str) {
        try {
            return ai.a(str);
        } catch (Exception e) {
            return null;
        }
    }

    static String b() {
        return UUID.randomUUID().toString();
    }

    static double c() {
        return ((double) System.currentTimeMillis()) / 1000.0d;
    }

    static String a(double d, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        a(d, i, stringBuilder);
        return stringBuilder.toString();
    }

    static void a(double d, int i, StringBuilder stringBuilder) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            stringBuilder.append(d);
            return;
        }
        if (d < 0.0d) {
            d = -d;
            stringBuilder.append('-');
        }
        if (i == 0) {
            stringBuilder.append(Math.round(d));
            return;
        }
        long pow = (long) Math.pow(10.0d, (double) i);
        long round = Math.round(((double) pow) * d);
        stringBuilder.append(round / pow);
        stringBuilder.append('.');
        long j = round % pow;
        if (j == 0) {
            for (int i2 = 0; i2 < i; i2++) {
                stringBuilder.append('0');
            }
            return;
        }
        for (round = j * 10; round < pow; round *= 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(j);
    }

    static String c(String str) {
        return a(str, "");
    }

    static String a(String str, String str2) {
        if (str == null) {
            return "";
        }
        try {
            String stringBuilder;
            l.a.a("Loading ").b((Object) str);
            FileInputStream fileInputStream = new FileInputStream(str);
            synchronized (a) {
                b.setLength(0);
                b.append(str2);
                for (int read = fileInputStream.read(a, 0, a.length); read != -1; read = fileInputStream.read(a, 0, a.length)) {
                    for (int i = 0; i < read; i++) {
                        b.append((char) a[i]);
                    }
                }
                fileInputStream.close();
                stringBuilder = b.toString();
            }
            return stringBuilder;
        } catch (IOException e) {
            l.d.a("Unable to load ").b((Object) str);
            return "";
        }
    }

    static String d() {
        return a.b().getPackageName();
    }
}
