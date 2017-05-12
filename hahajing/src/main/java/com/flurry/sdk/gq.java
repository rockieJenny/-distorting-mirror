package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class gq {
    private static final String b = gq.class.getSimpleName();
    String a;
    private byte[] c;

    public static class a implements gx<gq> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, gq gqVar) throws IOException {
            if (outputStream != null && gqVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeShort(gqVar.c.length);
                anonymousClass1.write(gqVar.c);
                anonymousClass1.writeShort(0);
                anonymousClass1.flush();
            }
        }

        public gq a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            gq gqVar = new gq();
            short readShort = anonymousClass2.readShort();
            if (readShort == (short) 0) {
                return null;
            }
            gqVar.c = new byte[readShort];
            anonymousClass2.readFully(gqVar.c);
            if (anonymousClass2.readUnsignedShort() == 0) {
            }
            return gqVar;
        }
    }

    private gq() {
        this.a = null;
        this.c = null;
    }

    public gq(byte[] bArr) {
        this.a = null;
        this.c = null;
        this.a = UUID.randomUUID().toString();
        this.c = bArr;
    }

    public String a() {
        return this.a;
    }

    public byte[] b() {
        return this.c;
    }

    public static String a(String str) {
        return ".yflurrydatasenderblock." + str;
    }
}
