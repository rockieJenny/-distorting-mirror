package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ae {
    private String a;
    private an b;
    private long c;
    private long d;
    private ah e;
    private long f;
    private long g;

    public static class a implements gx<ae> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, ae aeVar) throws IOException {
            if (outputStream != null && aeVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeUTF(aeVar.a);
                anonymousClass1.writeInt(aeVar.b.a());
                anonymousClass1.writeLong(aeVar.c);
                anonymousClass1.writeLong(aeVar.d);
                anonymousClass1.writeInt(aeVar.e.a());
                anonymousClass1.writeLong(aeVar.f);
                anonymousClass1.writeLong(aeVar.g);
                anonymousClass1.flush();
            }
        }

        public ae a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            ae aeVar = new ae();
            aeVar.a = anonymousClass2.readUTF();
            aeVar.b = an.a(anonymousClass2.readInt());
            aeVar.c = anonymousClass2.readLong();
            aeVar.d = anonymousClass2.readLong();
            aeVar.e = ah.a(anonymousClass2.readInt());
            aeVar.f = anonymousClass2.readLong();
            aeVar.g = anonymousClass2.readLong();
            return aeVar;
        }
    }

    private ae() {
    }

    public ae(String str, an anVar, long j) {
        this.a = str;
        this.b = anVar;
        this.c = System.currentTimeMillis();
        this.d = System.currentTimeMillis();
        this.e = ah.NONE;
        this.f = j;
        this.g = -1;
    }

    public String a() {
        return this.a;
    }

    public synchronized ah b() {
        return this.e;
    }

    public synchronized void a(ah ahVar) {
        this.e = ahVar;
    }

    public long c() {
        return this.f;
    }

    public boolean d() {
        return this.f > 0 && System.currentTimeMillis() > this.f;
    }

    public synchronized void e() {
        this.d = System.currentTimeMillis();
    }

    public long f() {
        return this.c;
    }

    public synchronized void a(long j) {
        this.g = j;
    }

    public String toString() {
        return "url: " + this.a + ", type:" + this.b + ", creation:" + this.c + ", accessed:" + this.d + ", status: " + this.e + ", expiration: " + this.f + ", size: " + this.g;
    }
}
