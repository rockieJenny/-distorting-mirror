package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class fc {
    private static final String a = fc.class.getSimpleName();
    private boolean b;
    private long c;
    private final List<ez> d = new ArrayList();

    public static class a implements gx<fc> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, fc fcVar) throws IOException {
            throw new UnsupportedOperationException("Serialization not supported");
        }

        public fc a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass1 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            fc fcVar = new fc();
            anonymousClass1.readUTF();
            anonymousClass1.readUTF();
            fcVar.b = anonymousClass1.readBoolean();
            fcVar.c = anonymousClass1.readLong();
            while (true) {
                int readUnsignedShort = anonymousClass1.readUnsignedShort();
                if (readUnsignedShort == 0) {
                    return fcVar;
                }
                byte[] bArr = new byte[readUnsignedShort];
                anonymousClass1.readFully(bArr);
                fcVar.d.add(0, new ez(bArr));
            }
        }
    }

    public boolean a() {
        return this.b;
    }

    public long b() {
        return this.c;
    }

    public List<ez> c() {
        return Collections.unmodifiableList(this.d);
    }
}
