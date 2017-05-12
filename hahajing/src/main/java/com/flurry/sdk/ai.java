package com.flurry.sdk;

import com.flurry.sdk.gl.b;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

public abstract class ai {
    private static final String a = ai.class.getSimpleName();
    private a b;
    private String c;
    private long d = Long.MAX_VALUE;
    private int e = 40000;
    private al f;
    private long g;
    private boolean h;
    private int i;
    private long j = 102400;
    private int k;
    private boolean l;
    private boolean m;

    public interface a {
        void a(ai aiVar);
    }

    protected abstract OutputStream f() throws IOException;

    protected abstract void g();

    protected abstract void h();

    public void a(a aVar) {
        this.b = aVar;
    }

    public void a(String str) {
        this.c = str;
    }

    public void a(int i) {
        this.e = i;
    }

    public void a(al alVar) {
        this.f = alVar;
    }

    public boolean a() {
        return this.l;
    }

    public boolean b() {
        return this.m;
    }

    public long c() {
        return this.g;
    }

    public void d() {
        fp.a().b(new hq(this) {
            final /* synthetic */ ai a;

            {
                this.a = r1;
            }

            public void safeRun() {
                if (this.a.p()) {
                    this.a.k();
                } else {
                    this.a.j();
                }
            }
        });
    }

    public void e() {
        this.m = true;
        fn.a().a((Object) this);
    }

    private void j() {
        if (!b()) {
            gd.a(3, a, "Downloader: Requesting file from url: " + this.c);
            hr glVar = new gl();
            glVar.a(this.c);
            glVar.a(com.flurry.sdk.gl.a.kGet);
            glVar.a(this.e);
            glVar.a(new b(this) {
                final /* synthetic */ ai a;

                {
                    this.a = r1;
                }

                public void a(gl glVar, InputStream inputStream) throws Exception {
                    Closeable aoVar;
                    Throwable th;
                    if (this.a.b()) {
                        throw new IOException("Downloader: request cancelled");
                    }
                    this.a.g = this.a.a(glVar);
                    if (this.a.g > this.a.d) {
                        throw new IOException("Downloader: content length: " + this.a.g + " exceeds size limit: " + this.a.d);
                    }
                    try {
                        aoVar = new ao(inputStream, this.a.d);
                        try {
                            hp.a((InputStream) aoVar, this.a.f());
                            this.a.g();
                            hp.a(aoVar);
                        } catch (Throwable th2) {
                            th = th2;
                            this.a.g();
                            hp.a(aoVar);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        aoVar = null;
                        this.a.g();
                        hp.a(aoVar);
                        throw th;
                    }
                }

                public void a(gl glVar) {
                    if (!this.a.b()) {
                        gd.a(3, ai.a, "Downloader: Download status code is:" + glVar.e() + " for url: " + this.a.c);
                        this.a.l = glVar.c();
                        fp.a().b(new hq(this) {
                            final /* synthetic */ AnonymousClass2 a;

                            {
                                this.a = r1;
                            }

                            public void safeRun() {
                                if (!this.a.a.l) {
                                    this.a.a.h();
                                }
                                this.a.a.o();
                            }
                        });
                    }
                }
            });
            fn.a().a((Object) this, glVar);
        }
    }

    private void k() {
        if (!b()) {
            hr gkVar = new gk();
            gkVar.a(this.c);
            gkVar.a(com.flurry.sdk.gl.a.kHead);
            gkVar.a(new com.flurry.sdk.gk.a<Void, Void>(this) {
                final /* synthetic */ ai a;

                {
                    this.a = r1;
                }

                public void a(gk<Void, Void> gkVar, Void voidR) {
                    if (!this.a.b()) {
                        gd.a(3, ai.a, "Downloader: HTTP HEAD status code is:" + gkVar.e() + " for url: " + this.a.c);
                        if (gkVar.c()) {
                            this.a.g = this.a.a((gl) gkVar);
                            List b = gkVar.b("Accept-Ranges");
                            if (this.a.g <= 0 || b == null || b.isEmpty()) {
                                this.a.i = 1;
                            } else {
                                this.a.h = "bytes".equals(((String) b.get(0)).trim());
                                this.a.i = (int) (((long) (this.a.g % this.a.j > 0 ? 1 : 0)) + (this.a.g / this.a.j));
                            }
                            if (this.a.d <= 0 || this.a.g <= this.a.d) {
                                fp.a().b(new hq(this) {
                                    final /* synthetic */ AnonymousClass3 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void safeRun() {
                                        this.a.a.l();
                                    }
                                });
                                return;
                            }
                            gd.a(3, ai.a, "Downloader: Size limit exceeded -- limit: " + this.a.d + ", content-length: " + this.a.g + " bytes!");
                            fp.a().b(new hq(this) {
                                final /* synthetic */ AnonymousClass3 a;

                                {
                                    this.a = r1;
                                }

                                public void safeRun() {
                                    this.a.a.o();
                                }
                            });
                            return;
                        }
                        fp.a().b(new hq(this) {
                            final /* synthetic */ AnonymousClass3 a;

                            {
                                this.a = r1;
                            }

                            public void safeRun() {
                                this.a.a.o();
                            }
                        });
                    }
                }
            });
            gd.a(3, a, "Downloader: requesting HTTP HEAD for url: " + this.c);
            fn.a().a((Object) this, gkVar);
        }
    }

    private void l() {
        if (!b()) {
            if (q()) {
                for (int i = 0; i < this.i; i++) {
                    this.f.d(b(i));
                }
                m();
                return;
            }
            j();
        }
    }

    private void m() {
        while (this.k < this.i) {
            if (!b()) {
                final String b = b(this.k);
                final String c = c(this.k);
                if (this.f.d(b)) {
                    gd.a(3, a, "Downloader: Skipping chunk with range:" + c + " for url: " + this.c + " chunk: " + this.k);
                    this.k++;
                } else {
                    gd.a(3, a, "Downloader: Requesting chunk with range:" + c + " for url: " + this.c + " chunk: " + this.k);
                    hr glVar = new gl();
                    glVar.a(this.c);
                    glVar.a(com.flurry.sdk.gl.a.kGet);
                    glVar.a(this.e);
                    glVar.a("Range", c);
                    glVar.a(new b(this) {
                        final /* synthetic */ ai c;

                        public void a(gl glVar, InputStream inputStream) throws Exception {
                            Closeable aoVar;
                            IOException e;
                            Object obj;
                            Throwable th;
                            Closeable closeable = null;
                            if (this.c.b()) {
                                throw new IOException("Downloader: request cancelled");
                            }
                            Closeable b = this.c.f.b(b);
                            if (b != null) {
                                try {
                                    aoVar = new ao(inputStream, this.c.d);
                                    try {
                                        hp.a((InputStream) aoVar, b.a());
                                        hp.a(aoVar);
                                        hp.a(b);
                                    } catch (IOException e2) {
                                        e = e2;
                                        hp.a(aoVar);
                                        hp.a(b);
                                        obj = e;
                                        if (closeable == null) {
                                            this.c.f.c(b);
                                            throw closeable;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        closeable = aoVar;
                                        hp.a(closeable);
                                        hp.a(b);
                                        throw th;
                                    }
                                } catch (IOException e3) {
                                    e = e3;
                                    aoVar = null;
                                    hp.a(aoVar);
                                    hp.a(b);
                                    obj = e;
                                    if (closeable == null) {
                                        this.c.f.c(b);
                                        throw closeable;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    hp.a(closeable);
                                    hp.a(b);
                                    throw th;
                                }
                            }
                            if (closeable == null) {
                                this.c.f.c(b);
                                throw closeable;
                            }
                        }

                        public void a(gl glVar) {
                            if (!this.c.b()) {
                                int e = glVar.e();
                                gd.a(3, ai.a, "Downloader: Download status code is:" + e + " for url: " + this.c.c + " chunk: " + this.c.k);
                                String str = null;
                                List b = glVar.b("Content-Range");
                                if (!(b == null || b.isEmpty())) {
                                    str = (String) b.get(0);
                                    gd.a(3, ai.a, "Downloader: Content range is:" + str + " for url: " + this.c.c + " chunk: " + this.c.k);
                                }
                                if (glVar.c() && e == 206 && r0 != null && r0.startsWith(c.replaceAll("=", " "))) {
                                    this.c.k = this.c.k + 1;
                                    fp.a().b(new hq(this) {
                                        final /* synthetic */ AnonymousClass4 a;

                                        {
                                            this.a = r1;
                                        }

                                        public void safeRun() {
                                            this.a.c.m();
                                        }
                                    });
                                    return;
                                }
                                fp.a().b(new hq(this) {
                                    final /* synthetic */ AnonymousClass4 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void safeRun() {
                                        this.a.c.o();
                                    }
                                });
                            }
                        }
                    });
                    fn.a().a((Object) this, glVar);
                    return;
                }
            }
            return;
        }
        n();
    }

    private void n() {
        String str;
        Object obj = null;
        int i = 0;
        Closeable a;
        if (!b()) {
            str = a;
            gd.a(3, str, "Downloader: assembling output file for url: " + this.c);
            try {
                OutputStream f = f();
                str = null;
                while (str < this.i) {
                    if (b()) {
                        throw new IOException("Download cancelled");
                    }
                    String b = b((int) str);
                    try {
                        a = this.f.a(b);
                        if (a == null) {
                            try {
                                throw new IOException("Could not create reader for chunk key: " + b);
                            } catch (Throwable th) {
                                str = th;
                            }
                        } else {
                            hp.a(a.a(), f);
                            hp.a(a);
                            this.f.c(b);
                            str++;
                        }
                    } catch (Throwable th2) {
                        str = th2;
                        a = null;
                    }
                }
            } catch (IOException e) {
                str = e;
                String str2 = str;
                if (obj != null) {
                    gd.a(3, a, "Downloader: assemble failed for url: " + this.c + " failed with exception: " + obj);
                    while (i < this.i) {
                        this.f.c(b(i));
                        i++;
                    }
                    h();
                } else {
                    gd.a(3, a, "Downloader: assemble succeeded for url: " + this.c);
                    this.l = true;
                }
                o();
                return;
            } finally {
                g();
            }
            if (obj != null) {
                gd.a(3, a, "Downloader: assemble succeeded for url: " + this.c);
                this.l = true;
            } else {
                gd.a(3, a, "Downloader: assemble failed for url: " + this.c + " failed with exception: " + obj);
                while (i < this.i) {
                    this.f.c(b(i));
                    i++;
                }
                h();
            }
            o();
            return;
        }
        return;
        hp.a(a);
        throw str;
    }

    private void o() {
        if (!b() && this.b != null) {
            gd.a(3, a, "Downloader: finished -- success: " + this.l + " for url: " + this.c);
            this.b.a(this);
        }
    }

    private boolean p() {
        return this.f != null;
    }

    private boolean q() {
        return this.f != null && this.h && this.i > 1;
    }

    private String b(int i) {
        return String.format(Locale.US, "%s__%03d", new Object[]{this.c, Integer.valueOf(i)});
    }

    private String c(int i) {
        return String.format("%s=%d-%d", new Object[]{"bytes", Long.valueOf(((long) i) * this.j), Long.valueOf(Math.min(this.g, ((long) (i + 1)) * this.j) - 1)});
    }

    private long a(gl glVar) {
        List b = glVar.b(HttpRequest.HEADER_CONTENT_LENGTH);
        if (!(b == null || b.isEmpty())) {
            try {
                return Long.parseLong((String) b.get(0));
            } catch (NumberFormatException e) {
                gd.a(3, a, "Downloader: could not determine content length for url: " + this.c);
            }
        }
        return -1;
    }
}
