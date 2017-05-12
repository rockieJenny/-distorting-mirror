package com.jirbo.adcolony;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class y extends af {
    static final int a = 1024;
    String b;
    OutputStream c;
    byte[] d;
    int e;
    int f;
    int g;

    y(String str) {
        this.d = new byte[1024];
        this.e = 0;
        this.b = str;
        if (a.n != 0) {
            this.g = 23;
            this.f = this.g;
        }
        try {
            if (!(a.l == null || a.l.f == null)) {
                a.l.f.b();
            }
            this.c = new FileOutputStream(str);
        } catch (IOException e) {
            a(e);
        }
    }

    y(String str, OutputStream outputStream) {
        this.d = new byte[1024];
        this.e = 0;
        this.b = str;
        this.c = outputStream;
    }

    void a(char c) {
        this.d[this.e] = (byte) (this.f ^ c);
        this.f += this.g;
        int i = this.e + 1;
        this.e = i;
        if (i == 1024) {
            a();
        }
    }

    void a() {
        if (this.e > 0 && this.c != null) {
            try {
                this.c.write(this.d, 0, this.e);
                this.e = 0;
                this.c.flush();
            } catch (IOException e) {
                this.e = 0;
                a(e);
            }
        }
    }

    void b() {
        a();
        try {
            if (this.c != null) {
                this.c.close();
                this.c = null;
            }
        } catch (IOException e) {
            this.c = null;
            a(e);
        }
    }

    void a(IOException iOException) {
        l.d.a("Error writing \"").a(this.b).b((Object) "\":");
        l.d.b(iOException.toString());
        b();
    }

    public static void a(String[] strArr) {
        y yVar = new y("test.txt");
        yVar.b("A king who was mad at the time");
        yVar.b("Declared limerick writing a crime");
        yVar.i += 2;
        yVar.b("So late in the night");
        yVar.b("All the poets would write");
        yVar.i -= 2;
        yVar.b("Verses without any rhyme or meter");
        yVar.d();
        yVar.i += 4;
        yVar.b("David\nGerrold");
        yVar.i += 2;
        yVar.b(4.0d);
        yVar.i += 2;
        yVar.b(0.0d);
        yVar.i += 2;
        yVar.b(-100023.0d);
        yVar.i += 2;
        yVar.c(-6);
        yVar.i += 2;
        yVar.c(0);
        yVar.i += 2;
        yVar.c(234);
        yVar.i += 2;
        yVar.c(Long.MIN_VALUE);
        yVar.i += 2;
        yVar.b(true);
        yVar.i += 2;
        yVar.b(false);
        yVar.i += 2;
        yVar.b();
    }
}
