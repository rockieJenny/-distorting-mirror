package com.jirbo.adcolony;

import java.io.File;
import java.io.IOException;

class f {
    static byte[] a = new byte[1024];
    String b;

    f(String str) {
        this.b = a.l.f.d + str;
    }

    y a() {
        return new y(this.b);
    }

    s b() {
        try {
            return new s(new x(this.b));
        } catch (IOException e) {
            return null;
        }
    }

    void a(String str) {
        y a = a();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            a.a(str.charAt(i));
        }
        a.b();
    }

    void c() {
        new File(this.b).delete();
    }
}
