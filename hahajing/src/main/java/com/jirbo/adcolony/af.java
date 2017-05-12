package com.jirbo.adcolony;

import io.fabric.sdk.android.services.common.IdManager;

abstract class af {
    boolean h = true;
    int i = 0;

    abstract void a(char c);

    af() {
    }

    void c() {
        if (this.h) {
            this.h = false;
            int i = this.i;
            while (true) {
                i--;
                if (i >= 0) {
                    a(' ');
                } else {
                    return;
                }
            }
        }
    }

    void b(char c) {
        if (this.h) {
            c();
        }
        a(c);
        if (c == '\n') {
            this.h = true;
        }
    }

    void a(Object obj) {
        if (this.h) {
            c();
        }
        if (obj == null) {
            a("null");
        } else {
            a(obj.toString());
        }
    }

    void a(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            b(str.charAt(i));
        }
    }

    void a(double d) {
        if (this.h) {
            c();
        }
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            a(IdManager.DEFAULT_VERSION_NAME);
            return;
        }
        if (d < 0.0d) {
            d = -d;
            a('-');
        }
        long pow = (long) Math.pow(10.0d, (double) 4);
        long round = Math.round(((double) pow) * d);
        a(round / pow);
        a('.');
        round %= pow;
        if (round == 0) {
            for (int i = 0; i < 4; i++) {
                a('0');
            }
            return;
        }
        for (long j = round * 10; j < pow; j *= 10) {
            a('0');
        }
        a(round);
    }

    void a(long j) {
        if (this.h) {
            c();
        }
        if (j == 0) {
            a('0');
        } else if (j == (-j)) {
            a("-9223372036854775808");
        } else if (j < 0) {
            a('-');
            a(-j);
        } else {
            b(j);
        }
    }

    void b(long j) {
        if (j != 0) {
            b(j / 10);
            a((char) ((int) (48 + (j % 10))));
        }
    }

    void a(boolean z) {
        if (z) {
            a("true");
        } else {
            a("false");
        }
    }

    void c(char c) {
        b(c);
        b('\n');
    }

    void b(Object obj) {
        a(obj);
        b('\n');
    }

    void b(String str) {
        a(str);
        b('\n');
    }

    void b(double d) {
        a(d);
        b('\n');
    }

    void c(long j) {
        a(j);
        b('\n');
    }

    void b(boolean z) {
        a(z);
        b('\n');
    }

    void d() {
        b('\n');
    }

    public static void b(String[] strArr) {
        System.out.println("Test...");
    }
}
