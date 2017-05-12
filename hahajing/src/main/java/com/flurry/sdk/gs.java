package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class gs {
    private String a;

    public static class a implements gx<gs> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, gs gsVar) throws IOException {
            if (outputStream != null && gsVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeUTF(gsVar.a);
                anonymousClass1.flush();
            }
        }

        public gs a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            gs gsVar = new gs();
            gsVar.a = anonymousClass2.readUTF();
            return gsVar;
        }
    }

    private gs() {
    }

    public gs(String str) {
        this.a = str;
    }

    public String a() {
        return this.a;
    }
}
