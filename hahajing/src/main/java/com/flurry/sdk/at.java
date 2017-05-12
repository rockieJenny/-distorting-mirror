package com.flurry.sdk;

import android.text.TextUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class at {
    private static int a = 1;
    private int b;
    private long c;
    private String d;
    private List<as> e;

    public static class a implements gx<at> {
        private final com.flurry.sdk.as.a a;

        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public a(com.flurry.sdk.as.a aVar) {
            this.a = aVar;
        }

        public void a(OutputStream outputStream, at atVar) throws IOException {
            if (outputStream != null && atVar != null && this.a != null) {
                OutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeInt(atVar.b);
                anonymousClass1.writeLong(atVar.c);
                anonymousClass1.writeUTF(atVar.d == null ? "" : atVar.d);
                anonymousClass1.writeShort(atVar.e.size());
                for (as a : atVar.e) {
                    this.a.a(anonymousClass1, a);
                }
                anonymousClass1.flush();
            }
        }

        public at a(InputStream inputStream) throws IOException {
            String str = null;
            if (inputStream == null || this.a == null) {
                return null;
            }
            InputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            at atVar = new at();
            atVar.b = anonymousClass2.readInt();
            atVar.c = anonymousClass2.readLong();
            String readUTF = anonymousClass2.readUTF();
            if (!readUTF.equals("")) {
                str = readUTF;
            }
            atVar.d = str;
            atVar.e = new ArrayList();
            short readShort = anonymousClass2.readShort();
            for (short s = (short) 0; s < readShort; s = (short) (s + 1)) {
                atVar.e.add(this.a.a(anonymousClass2));
            }
            return atVar;
        }
    }

    private at() {
    }

    public at(String str) {
        int i = a;
        a = i + 1;
        this.b = i;
        this.c = fd.a().c();
        this.d = str;
        this.e = new ArrayList();
    }

    public void a(as asVar) {
        this.e.add(asVar);
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.d;
    }

    public long c() {
        return this.c;
    }

    public List<as> d() {
        return this.e;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof at)) {
            return false;
        }
        at atVar = (at) obj;
        if (this.b == atVar.b && this.c == atVar.c && TextUtils.equals(this.d, atVar.d)) {
            if (this.e == atVar.e) {
                return true;
            }
            if (this.e != null && this.e.equals(atVar.e)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = (int) (((long) (17 ^ this.b)) ^ this.c);
        if (this.d != null) {
            i ^= this.d.hashCode();
        }
        if (this.e != null) {
            return i ^ this.e.hashCode();
        }
        return i;
    }
}
