package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class cd extends gn {
    private String a;
    private String b;
    private boolean c;

    public static class a implements gx<cd> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, cd cdVar) throws IOException {
            if (outputStream != null && cdVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeLong(cdVar.d());
                anonymousClass1.writeBoolean(cdVar.e());
                anonymousClass1.writeInt(cdVar.f());
                anonymousClass1.writeUTF(cdVar.g());
                anonymousClass1.writeUTF(cdVar.h());
                anonymousClass1.writeUTF(cdVar.a);
                anonymousClass1.writeUTF(cdVar.b);
                anonymousClass1.writeBoolean(cdVar.c);
                anonymousClass1.flush();
            }
        }

        public cd a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            cd cdVar = new cd();
            cdVar.a(anonymousClass2.readLong());
            cdVar.a(anonymousClass2.readBoolean());
            cdVar.a(anonymousClass2.readInt());
            cdVar.b(anonymousClass2.readUTF());
            cdVar.c(anonymousClass2.readUTF());
            cdVar.a = anonymousClass2.readUTF();
            cdVar.b = anonymousClass2.readUTF();
            cdVar.c = anonymousClass2.readBoolean();
            return cdVar;
        }
    }

    public static class b implements gx<cd> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, cd cdVar) throws IOException {
            throw new UnsupportedOperationException("Serialization not supported");
        }

        public cd a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass1 = new DataInputStream(this, inputStream) {
                final /* synthetic */ b a;

                public void close() {
                }
            };
            cd cdVar = new cd();
            cdVar.a = anonymousClass1.readUTF();
            cdVar.b = anonymousClass1.readUTF();
            cdVar.a(anonymousClass1.readUTF());
            cdVar.a(anonymousClass1.readLong());
            cdVar.c = anonymousClass1.readBoolean();
            cdVar.a(anonymousClass1.readBoolean());
            cdVar.a(anonymousClass1.readInt());
            return cdVar;
        }
    }

    private cd() {
    }

    public cd(String str, String str2, String str3, long j, boolean z) {
        a(str3);
        a(j);
        this.a = str;
        this.b = str2;
        this.c = z;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public boolean c() {
        return this.c;
    }
}
