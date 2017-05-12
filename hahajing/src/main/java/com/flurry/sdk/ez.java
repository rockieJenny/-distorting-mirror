package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ez {
    private static final String b = ez.class.getSimpleName();
    byte[] a;

    public static class a implements gx<ez> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, ez ezVar) throws IOException {
            if (outputStream != null && ezVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                int i = 0;
                if (ezVar.a != null) {
                    i = ezVar.a.length;
                }
                anonymousClass1.writeShort(i);
                if (i > 0) {
                    anonymousClass1.write(ezVar.a);
                }
                anonymousClass1.flush();
            }
        }

        public ez a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            ez ezVar = new ez();
            int readUnsignedShort = anonymousClass2.readUnsignedShort();
            if (readUnsignedShort > 0) {
                byte[] bArr = new byte[readUnsignedShort];
                anonymousClass2.readFully(bArr);
                ezVar.a = bArr;
            }
            return ezVar;
        }
    }

    private ez() {
    }

    public ez(byte[] bArr) {
        this.a = bArr;
    }

    public ez(fa faVar) throws IOException {
        Throwable e;
        Closeable closeable = null;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                int i;
                int i2;
                dataOutputStream.writeShort(5);
                dataOutputStream.writeUTF(faVar.a());
                dataOutputStream.writeLong(faVar.b());
                dataOutputStream.writeLong(faVar.c());
                dataOutputStream.writeLong(faVar.d());
                Map e2 = faVar.e();
                if (e2 == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(e2.size());
                    for (Entry entry : e2.entrySet()) {
                        dataOutputStream.writeUTF((String) entry.getKey());
                        dataOutputStream.writeUTF((String) entry.getValue());
                        dataOutputStream.writeByte(0);
                    }
                }
                dataOutputStream.writeUTF(faVar.f());
                dataOutputStream.writeUTF(faVar.g());
                dataOutputStream.writeByte(faVar.h());
                dataOutputStream.writeByte(faVar.i());
                dataOutputStream.writeUTF(faVar.j());
                if (faVar.k() == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeDouble(hp.a(faVar.k().getLatitude(), 3));
                    dataOutputStream.writeDouble(hp.a(faVar.k().getLongitude(), 3));
                    dataOutputStream.writeFloat(faVar.k().getAccuracy());
                }
                dataOutputStream.writeInt(faVar.l());
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(faVar.m());
                if (faVar.n() == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeLong(faVar.n().longValue());
                }
                e2 = faVar.o();
                if (e2 == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(e2.size());
                    for (Entry entry2 : e2.entrySet()) {
                        dataOutputStream.writeUTF((String) entry2.getKey());
                        dataOutputStream.writeInt(((ev) entry2.getValue()).a);
                    }
                }
                List<ew> p = faVar.p();
                if (p == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(p.size());
                    for (ew e3 : p) {
                        dataOutputStream.write(e3.e());
                    }
                }
                dataOutputStream.writeBoolean(faVar.q());
                List s = faVar.s();
                if (s != null) {
                    int i3 = 0;
                    i = 0;
                    for (i2 = 0; i2 < s.size(); i2++) {
                        i3 += ((eu) s.get(i2)).a();
                        if (i3 > 160000) {
                            gd.a(5, b, "Error Log size exceeded. No more event details logged.");
                            i2 = i;
                            break;
                        }
                        i++;
                    }
                    i2 = i;
                } else {
                    i2 = 0;
                }
                dataOutputStream.writeInt(faVar.r());
                dataOutputStream.writeShort(i2);
                for (i = 0; i < i2; i++) {
                    dataOutputStream.write(((eu) s.get(i)).b());
                }
                dataOutputStream.writeInt(-1);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                this.a = byteArrayOutputStream.toByteArray();
                hp.a(dataOutputStream);
            } catch (IOException e4) {
                e = e4;
                closeable = dataOutputStream;
                try {
                    gd.a(6, b, "", e);
                    throw e;
                } catch (Throwable th) {
                    e = th;
                    dataOutputStream = closeable;
                    hp.a(dataOutputStream);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                hp.a(dataOutputStream);
                throw e;
            }
        } catch (IOException e5) {
            e = e5;
            gd.a(6, b, "", e);
            throw e;
        } catch (Throwable th3) {
            e = th3;
            dataOutputStream = null;
            hp.a(dataOutputStream);
            throw e;
        }
    }

    public byte[] a() {
        return this.a;
    }
}
