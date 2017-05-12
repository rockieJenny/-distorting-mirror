package com.flurry.sdk;

import android.text.TextUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class as {
    static final List<String> a = Arrays.asList(new String[]{"requested", "filled", "unfilled", "rendered", "clicked", "prepared", "adunitMerged", "sendUrlStatusResult", "videoStart", "videoFirstQuartile", "videoMidpoint", "videoThirdQuartile", "videoCompleted", "videoProgressed", "sentToUrl", "adClosed", "adWillClose", "renderFailed", "requestAdComponents", "urlVerified", "capExhausted", "capNotExhausted"});
    private static final String b = as.class.getSimpleName();
    private String c;
    private boolean d;
    private long e;
    private Map<String, String> f;

    public static class a implements gx<as> {
        public /* synthetic */ Object b(InputStream inputStream) throws IOException {
            return a(inputStream);
        }

        public void a(OutputStream outputStream, as asVar) throws IOException {
            if (outputStream != null && asVar != null) {
                DataOutputStream anonymousClass1 = new DataOutputStream(this, outputStream) {
                    final /* synthetic */ a a;

                    public void close() {
                    }
                };
                anonymousClass1.writeUTF(asVar.c);
                anonymousClass1.writeBoolean(asVar.d);
                anonymousClass1.writeLong(asVar.e);
                anonymousClass1.writeShort(asVar.f.size());
                for (Entry entry : asVar.f.entrySet()) {
                    anonymousClass1.writeUTF((String) entry.getKey());
                    anonymousClass1.writeUTF((String) entry.getValue());
                }
                anonymousClass1.flush();
            }
        }

        public as a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream anonymousClass2 = new DataInputStream(this, inputStream) {
                final /* synthetic */ a a;

                public void close() {
                }
            };
            as asVar = new as();
            asVar.c = anonymousClass2.readUTF();
            asVar.d = anonymousClass2.readBoolean();
            asVar.e = anonymousClass2.readLong();
            asVar.f = new HashMap();
            short readShort = anonymousClass2.readShort();
            for (short s = (short) 0; s < readShort; s = (short) (s + 1)) {
                asVar.f.put(anonymousClass2.readUTF(), anonymousClass2.readUTF());
            }
            return asVar;
        }
    }

    private as() {
    }

    public as(String str, boolean z, long j, Map<String, String> map) {
        if (!a.contains(str)) {
            gd.a(b, "AdEvent initialized with unrecognized type: " + str);
        }
        this.c = str;
        this.d = z;
        this.e = j;
        if (map == null) {
            this.f = new HashMap();
        } else {
            this.f = map;
        }
    }

    public String a() {
        return this.c;
    }

    public boolean b() {
        return this.d;
    }

    public long c() {
        return this.e;
    }

    public Map<String, String> d() {
        return this.f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof as)) {
            return false;
        }
        as asVar = (as) obj;
        if (TextUtils.equals(this.c, asVar.c) && this.d == asVar.d && this.e == asVar.e) {
            if (this.f == asVar.f) {
                return true;
            }
            if (this.f != null && this.f.equals(asVar.f)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = 17;
        if (this.c != null) {
            i = 17 ^ this.c.hashCode();
        }
        if (this.d) {
            i ^= 1;
        }
        i = (int) (((long) i) ^ this.e);
        if (this.f != null) {
            return i ^ this.f.hashCode();
        }
        return i;
    }
}
