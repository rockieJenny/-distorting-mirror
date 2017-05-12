package com.jirbo.adcolony;

import android.support.v4.media.TransportMediator;
import java.io.IOException;
import java.io.InputStream;

class s {
    char[] a;
    int b;
    int c;

    s(String str) {
        this.c = str.length();
        StringBuilder stringBuilder = new StringBuilder(this.c);
        int i = 0;
        while (i < this.c) {
            char charAt = str.charAt(i);
            if ((charAt >= ' ' && charAt <= '~') || charAt == '\n') {
                stringBuilder.append(charAt);
            } else if ((charAt & 128) == 0) {
                stringBuilder.append(' ');
            } else if ((charAt & 224) == 192 && i + 1 < this.c) {
                stringBuilder.append((char) (((charAt & 31) << 6) | (str.charAt(i + 1) & 63)));
                i++;
            } else if (i + 2 < this.c) {
                stringBuilder.append((char) ((((charAt & 15) << 12) | ((str.charAt(i + 1) & 63) << 6)) | (str.charAt(i + 2) & 63)));
                i += 2;
            } else {
                stringBuilder.append('?');
            }
            i++;
        }
        this.c = stringBuilder.length();
        this.a = new char[this.c];
        stringBuilder.getChars(0, this.c, this.a, 0);
    }

    s(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(inputStream.available());
        int read = inputStream.read();
        while (read != -1) {
            if ((read >= 32 && read <= TransportMediator.KEYCODE_MEDIA_PLAY) || read == 10) {
                stringBuilder.append((char) read);
            } else if ((read & 128) == 0) {
                stringBuilder.append(' ');
            } else if ((read & 224) == 192) {
                stringBuilder.append((char) (((read & 31) << 6) | (inputStream.read() & 63)));
            } else {
                stringBuilder.append((char) ((((read & 15) << 12) | ((inputStream.read() & 63) << 6)) | (inputStream.read() & 63)));
            }
            read = inputStream.read();
        }
        this.c = stringBuilder.length();
        this.a = new char[this.c];
        stringBuilder.getChars(0, this.c, this.a, 0);
    }

    boolean a() {
        return this.b < this.c;
    }

    char b() {
        if (this.b == this.c) {
            return '\u0000';
        }
        return this.a[this.b];
    }

    char c() {
        char[] cArr = this.a;
        int i = this.b;
        this.b = i + 1;
        return cArr[i];
    }

    boolean a(char c) {
        if (this.b == this.c || this.a[this.b] != c) {
            return false;
        }
        this.b++;
        return true;
    }

    void b(char c) {
        if (!a(c)) {
            throw new AdColonyException("'" + c + "' expected.");
        }
    }

    boolean a(String str) {
        int length = str.length();
        if (this.b + length > this.c) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) != this.a[this.b + i]) {
                return false;
            }
        }
        this.b += length;
        return true;
    }

    void b(String str) {
        if (!a(str)) {
            throw new AdColonyException("\"" + str + "\" expected.");
        }
    }

    void d() {
        while (this.b != this.c) {
            char c = this.a[this.b];
            if (c == ' ' || c == '\n') {
                this.b++;
            } else {
                return;
            }
        }
    }

    public static void a(String[] strArr) {
    }
}
