package com.flurry.sdk;

import com.flurry.android.impl.ads.protocol.v13.FrequencyCapResponseInfo;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class az {
    private FrequencyCapType a;
    private String b;
    private long c;
    private long d;
    private long e;
    private int f;
    private int g;
    private int h;
    private int i;
    private long j;

    public static class a implements gx<az> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, az azVar) throws IOException {
            if (outputStream != null && azVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeUTF(azVar.a.name());
                anonymousClass1.writeUTF(azVar.b);
                anonymousClass1.writeLong(azVar.c);
                anonymousClass1.writeLong(azVar.d);
                anonymousClass1.writeLong(azVar.e);
                anonymousClass1.writeInt(azVar.f);
                anonymousClass1.writeInt(azVar.g);
                anonymousClass1.writeInt(azVar.h);
                anonymousClass1.writeInt(azVar.i);
                anonymousClass1.writeLong(azVar.j);
                anonymousClass1.flush();
            }
        }

        public az a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            az azVar = new az();
            azVar.a = (FrequencyCapType) Enum.valueOf(FrequencyCapType.class, anonymousClass2.readUTF());
            azVar.b = anonymousClass2.readUTF();
            azVar.c = anonymousClass2.readLong();
            azVar.d = anonymousClass2.readLong();
            azVar.e = anonymousClass2.readLong();
            azVar.f = anonymousClass2.readInt();
            azVar.g = anonymousClass2.readInt();
            azVar.h = anonymousClass2.readInt();
            azVar.i = anonymousClass2.readInt();
            azVar.j = anonymousClass2.readLong();
            return azVar;
        }
    }

    public static class b implements gx<az> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, az azVar) throws IOException {
            throw new UnsupportedOperationException("Serialization not supported");
        }

        public az a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass1 = new DataInputStream(this, inputStream) {
                final /* synthetic */ b a;

                public void close() {
                }
            };
            az azVar = new az();
            azVar.a = FrequencyCapType.ADSPACE;
            azVar.e = 0;
            azVar.j = 0;
            azVar.b = anonymousClass1.readUTF();
            azVar.c = anonymousClass1.readLong();
            azVar.d = anonymousClass1.readLong();
            azVar.i = anonymousClass1.readInt();
            azVar.f = anonymousClass1.readInt();
            azVar.g = anonymousClass1.readInt();
            azVar.h = anonymousClass1.readInt();
            return azVar;
        }
    }

    private az() {
    }

    public az(FrequencyCapResponseInfo frequencyCapResponseInfo) {
        this.a = frequencyCapResponseInfo.capType;
        this.b = frequencyCapResponseInfo.id;
        this.c = frequencyCapResponseInfo.serveTime;
        this.d = frequencyCapResponseInfo.expirationTime;
        this.e = frequencyCapResponseInfo.streamCapDurationMillis;
        this.f = frequencyCapResponseInfo.capRemaining;
        this.g = frequencyCapResponseInfo.totalCap;
        this.h = frequencyCapResponseInfo.capDurationType;
        this.i = 0;
        this.j = 0;
    }

    public void a() {
        this.i++;
        this.j = System.currentTimeMillis();
    }

    public FrequencyCapType b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public long d() {
        return this.c;
    }

    public long e() {
        return this.d;
    }

    public long f() {
        return this.e;
    }

    public int g() {
        return this.f;
    }

    public int h() {
        return this.g;
    }

    public int i() {
        return this.h;
    }

    public int j() {
        return this.i;
    }

    public long k() {
        return this.j;
    }
}
