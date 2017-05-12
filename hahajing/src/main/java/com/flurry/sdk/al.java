package com.flurry.sdk;

import android.os.FileObserver;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class al {
    private static final String a = al.class.getSimpleName();
    private final String b;
    private final long c;
    private final boolean d;
    private FileObserver e;
    private kb f;

    static class a extends BufferedOutputStream {
        private boolean a;

        private a(OutputStream outputStream) {
            super(outputStream);
            this.a = false;
        }

        private boolean a() {
            return this.a;
        }

        public void close() throws IOException {
            try {
                super.close();
            } catch (IOException e) {
                this.a = true;
                throw e;
            }
        }

        public void flush() throws IOException {
            try {
                super.flush();
            } catch (IOException e) {
                this.a = true;
                throw e;
            }
        }

        public void write(int i) throws IOException {
            try {
                super.write(i);
            } catch (IOException e) {
                this.a = true;
                throw e;
            }
        }

        public void write(byte[] bArr) throws IOException {
            try {
                super.write(bArr);
            } catch (IOException e) {
                this.a = true;
                throw e;
            }
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                super.write(bArr, i, i2);
            } catch (IOException e) {
                this.a = true;
                throw e;
            }
        }
    }

    public class b implements Closeable {
        final /* synthetic */ al a;
        private final com.flurry.sdk.kb.c b;
        private final InputStream c;
        private final GZIPInputStream d;
        private final BufferedInputStream e;
        private boolean f;

        private b(al alVar, com.flurry.sdk.kb.c cVar, boolean z) throws IOException {
            this.a = alVar;
            if (cVar == null) {
                throw new IllegalArgumentException("Snapshot cannot be null");
            }
            this.b = cVar;
            this.c = this.b.a(0);
            if (this.c == null) {
                throw new IOException("Snapshot inputstream is null");
            } else if (z) {
                this.d = new GZIPInputStream(this.c);
                if (this.d == null) {
                    throw new IOException("Gzip inputstream is null");
                }
                this.e = new BufferedInputStream(this.d);
            } else {
                this.d = null;
                this.e = new BufferedInputStream(this.c);
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            close();
        }

        public InputStream a() {
            return this.e;
        }

        public void close() {
            if (!this.f) {
                this.f = true;
                hp.a(this.e);
                hp.a(this.d);
                hp.a(this.c);
                hp.a(this.b);
            }
        }
    }

    public class c implements Closeable {
        final /* synthetic */ al a;
        private final com.flurry.sdk.kb.a b;
        private final OutputStream c;
        private final GZIPOutputStream d;
        private final a e;
        private boolean f;

        private c(al alVar, com.flurry.sdk.kb.a aVar, boolean z) throws IOException {
            this.a = alVar;
            if (aVar == null) {
                throw new IllegalArgumentException("Editor cannot be null");
            }
            this.b = aVar;
            this.c = this.b.a(0);
            if (this.c == null) {
                throw new IOException("Editor outputstream is null");
            } else if (z) {
                this.d = new GZIPOutputStream(this.c);
                if (this.d == null) {
                    throw new IOException("Gzip outputstream is null");
                }
                this.e = new a(this.d);
            } else {
                this.d = null;
                this.e = new a(this.c);
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            close();
        }

        public OutputStream a() {
            return this.e;
        }

        public void close() {
            boolean z = true;
            if (!this.f) {
                this.f = true;
                hp.a(this.e);
                hp.a(this.d);
                hp.a(this.c);
                if (this.b != null) {
                    if (this.e != null) {
                        z = this.e.a();
                    }
                    if (z) {
                        try {
                            this.b.b();
                            return;
                        } catch (Throwable e) {
                            gd.a(3, al.a, "Exception closing editor for cache: " + this.a.b, e);
                            return;
                        }
                    }
                    this.b.a();
                }
            }
        }
    }

    public al(String str, long j, boolean z) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("The cache must have a name");
        }
        this.b = str;
        this.c = j;
        this.d = z;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        b();
    }

    public void a() {
        try {
            File file = new File(ct.a(this.b), "canary");
            if (ho.a(file) && (file.exists() || file.createNewFile())) {
                this.e = new FileObserver(this, file.getAbsolutePath()) {
                    final /* synthetic */ al a;

                    public void onEvent(int i, String str) {
                        if ((i & 2048) != 0 || (i & 1024) != 0) {
                            fp.a().f().post(new hq(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void safeRun() {
                                    if (this.a.a.f != null) {
                                        this.a.a.b();
                                        this.a.a.a();
                                    }
                                }
                            });
                        }
                    }
                };
                this.e.startWatching();
                this.f = kb.a(ct.a(this.b), 1, 1, this.c);
                return;
            }
            throw new IOException("Could not create canary file.");
        } catch (IOException e) {
            gd.a(3, a, "Could not open cache: " + this.b);
        }
    }

    public void b() {
        if (this.e != null) {
            this.e.stopWatching();
            this.e = null;
        }
        hp.a(this.f);
    }

    public b a(String str) {
        if (this.f == null || str == null) {
            return null;
        }
        b bVar;
        try {
            com.flurry.sdk.kb.c a = this.f.a(ct.c(str));
            if (a != null) {
                bVar = new b(a, this.d);
            } else {
                bVar = null;
            }
        } catch (Throwable e) {
            gd.a(3, a, "Exception during getReader for cache: " + this.b + " key: " + str, e);
            hp.a(null);
            bVar = null;
        }
        return bVar;
    }

    public c b(String str) {
        if (this.f == null || str == null) {
            return null;
        }
        c cVar;
        try {
            com.flurry.sdk.kb.a b = this.f.b(ct.c(str));
            if (b != null) {
                cVar = new c(b, this.d);
            } else {
                cVar = null;
            }
        } catch (Throwable e) {
            gd.a(3, a, "Exception during getWriter for cache: " + this.b + " key: " + str, e);
            hp.a(null);
            cVar = null;
        }
        return cVar;
    }

    public boolean c(String str) {
        boolean z = false;
        if (!(this.f == null || str == null)) {
            try {
                z = this.f.c(ct.c(str));
            } catch (Throwable e) {
                gd.a(3, a, "Exception during remove for cache: " + this.b + " key: " + str, e);
            }
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean d(java.lang.String r8) {
        /*
        r7 = this;
        r0 = 0;
        r1 = r7.f;
        if (r1 != 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        if (r8 == 0) goto L_0x0005;
    L_0x0008:
        r2 = 0;
        r1 = r7.f;	 Catch:{ IOException -> 0x001a }
        r3 = com.flurry.sdk.ct.c(r8);	 Catch:{ IOException -> 0x001a }
        r1 = r1.a(r3);	 Catch:{ IOException -> 0x001a }
        if (r1 == 0) goto L_0x0016;
    L_0x0015:
        r0 = 1;
    L_0x0016:
        com.flurry.sdk.hp.a(r1);
        goto L_0x0005;
    L_0x001a:
        r1 = move-exception;
        r3 = 3;
        r4 = a;	 Catch:{ all -> 0x003a }
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003a }
        r5.<init>();	 Catch:{ all -> 0x003a }
        r6 = "Exception during exists for cache: ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x003a }
        r6 = r7.b;	 Catch:{ all -> 0x003a }
        r5 = r5.append(r6);	 Catch:{ all -> 0x003a }
        r5 = r5.toString();	 Catch:{ all -> 0x003a }
        com.flurry.sdk.gd.a(r3, r4, r5, r1);	 Catch:{ all -> 0x003a }
        com.flurry.sdk.hp.a(r2);
        goto L_0x0005;
    L_0x003a:
        r0 = move-exception;
        com.flurry.sdk.hp.a(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.al.d(java.lang.String):boolean");
    }

    public void c() {
        d();
        a();
    }

    public void d() {
        if (this.f != null) {
            try {
                this.f.a();
            } catch (Throwable e) {
                gd.a(3, a, "Exception during delete for cache: " + this.b, e);
            }
        }
    }
}
