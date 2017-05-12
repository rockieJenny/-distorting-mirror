package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class eu {
    private int a;
    private long b;
    private String c;
    private String d;
    private String e;
    private Throwable f;

    public eu(int i, long j, String str, String str2, String str3, Throwable th) {
        this.a = i;
        this.b = j;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = th;
    }

    public int a() {
        return b().length;
    }

    public byte[] b() {
        Closeable dataOutputStream;
        byte[] bytes;
        Throwable th;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(this.a);
                dataOutputStream.writeLong(this.b);
                dataOutputStream.writeUTF(this.c);
                dataOutputStream.writeUTF(this.d);
                dataOutputStream.writeUTF(this.e);
                if (this.f != null) {
                    if (this.c == "uncaught") {
                        dataOutputStream.writeByte(3);
                    } else {
                        dataOutputStream.writeByte(2);
                    }
                    dataOutputStream.writeByte(2);
                    StringBuilder stringBuilder = new StringBuilder("");
                    String property = System.getProperty("line.separator");
                    for (Object append : this.f.getStackTrace()) {
                        stringBuilder.append(append);
                        stringBuilder.append(property);
                    }
                    if (this.f.getCause() != null) {
                        stringBuilder.append(property);
                        stringBuilder.append("Caused by: ");
                        for (Object append2 : this.f.getCause().getStackTrace()) {
                            stringBuilder.append(append2);
                            stringBuilder.append(property);
                        }
                    }
                    bytes = stringBuilder.toString().getBytes();
                    dataOutputStream.writeInt(bytes.length);
                    dataOutputStream.write(bytes);
                } else {
                    dataOutputStream.writeByte(1);
                    dataOutputStream.writeByte(0);
                }
                dataOutputStream.flush();
                bytes = byteArrayOutputStream.toByteArray();
                hp.a(dataOutputStream);
            } catch (IOException e) {
                try {
                    bytes = new byte[0];
                    hp.a(dataOutputStream);
                    return bytes;
                } catch (Throwable th2) {
                    th = th2;
                    hp.a(dataOutputStream);
                    throw th;
                }
            }
        } catch (IOException e2) {
            dataOutputStream = null;
            bytes = new byte[0];
            hp.a(dataOutputStream);
            return bytes;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            dataOutputStream = null;
            th = th4;
            hp.a(dataOutputStream);
            throw th;
        }
        return bytes;
    }

    public String c() {
        return this.c;
    }
}
