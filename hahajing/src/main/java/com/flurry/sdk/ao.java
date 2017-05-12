package com.flurry.sdk;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ao extends FilterInputStream {
    private final long a;
    private long b;

    public ao(InputStream inputStream, long j) {
        super(inputStream);
        this.a = j;
    }

    public void close() {
        this.in = null;
    }

    public int read() throws IOException {
        return a(super.read());
    }

    public int read(byte[] bArr) throws IOException {
        return a(super.read(bArr));
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return a(super.read(bArr, i, i2));
    }

    private int a(int i) throws IOException {
        this.b += (long) i;
        if (this.b <= this.a) {
            return i;
        }
        throw new IOException("Size limit exceeded: " + this.a + " bytes, read " + this.b + " bytes!");
    }
}
