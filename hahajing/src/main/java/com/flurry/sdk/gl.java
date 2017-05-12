package com.flurry.sdk;

import com.inmobi.monetization.internal.Constants;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

public class gl extends hr {
    private static final String a = gl.class.getSimpleName();
    private String b;
    private a c;
    private int d = 10000;
    private int e = Constants.HTTP_TIMEOUT;
    private boolean f = true;
    private final fu<String, String> g = new fu();
    private c j;
    private HttpURLConnection k;
    private boolean l;
    private boolean m;
    private Exception n;
    private int o = -1;
    private final fu<String, String> p = new fu();
    private final Object q = new Object();

    public enum a {
        kUnknown,
        kGet,
        kPost,
        kPut,
        kDelete,
        kHead;

        public String toString() {
            switch (this) {
                case kPost:
                    return HttpRequest.METHOD_POST;
                case kPut:
                    return HttpRequest.METHOD_PUT;
                case kDelete:
                    return HttpRequest.METHOD_DELETE;
                case kHead:
                    return HttpRequest.METHOD_HEAD;
                case kGet:
                    return HttpRequest.METHOD_GET;
                default:
                    return null;
            }
        }
    }

    public interface c {
        void a(gl glVar);

        void a(gl glVar, InputStream inputStream) throws Exception;

        void a(gl glVar, OutputStream outputStream) throws Exception;
    }

    public static class b implements c {
        public void a(gl glVar, OutputStream outputStream) throws Exception {
        }

        public void a(gl glVar, InputStream inputStream) throws Exception {
        }

        public void a(gl glVar) {
        }
    }

    public void a(String str) {
        this.b = str;
    }

    public String a() {
        return this.b;
    }

    public void a(a aVar) {
        this.c = aVar;
    }

    public void a(boolean z) {
        this.f = z;
    }

    public void a(String str, String str2) {
        this.g.a((Object) str, (Object) str2);
    }

    public void a(c cVar) {
        this.j = cVar;
    }

    public boolean b() {
        boolean z;
        synchronized (this.q) {
            z = this.m;
        }
        return z;
    }

    public boolean c() {
        return !f() && d();
    }

    public boolean d() {
        return this.o >= 200 && this.o < 400;
    }

    public int e() {
        return this.o;
    }

    public boolean f() {
        return this.n != null;
    }

    public List<String> b(String str) {
        if (str == null) {
            return null;
        }
        return this.p.a((Object) str);
    }

    public void g() {
        synchronized (this.q) {
            this.m = true;
        }
        p();
    }

    public void safeRun() {
        try {
            if (this.b != null) {
                if (fj.a().c()) {
                    if (this.c == null || a.kUnknown.equals(this.c)) {
                        this.c = a.kGet;
                    }
                    m();
                    gd.a(4, a, "HTTP status: " + this.o + " for url: " + this.b);
                    n();
                    return;
                }
                gd.a(3, a, "Network not available, aborting http request: " + this.b);
                n();
            }
        } catch (Throwable e) {
            gd.a(4, a, "HTTP status: " + this.o + " for url: " + this.b);
            gd.a(3, a, "Exception during http request: " + this.b, e);
            this.n = e;
        } finally {
            n();
        }
    }

    public void h() {
        g();
    }

    private void m() throws Exception {
        Closeable outputStream;
        Closeable bufferedOutputStream;
        Throwable th;
        Closeable closeable = null;
        if (!this.m) {
            this.b = hp.a(this.b);
            this.k = (HttpURLConnection) new URL(this.b).openConnection();
            this.k.setConnectTimeout(this.d);
            this.k.setReadTimeout(this.e);
            this.k.setRequestMethod(this.c.toString());
            this.k.setInstanceFollowRedirects(this.f);
            this.k.setDoOutput(a.kPost.equals(this.c));
            this.k.setDoInput(true);
            for (Entry entry : this.g.b()) {
                this.k.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
            if (!(a.kGet.equals(this.c) || a.kPost.equals(this.c))) {
                this.k.setRequestProperty(HttpRequest.HEADER_ACCEPT_ENCODING, "");
            }
            if (this.m) {
                o();
                return;
            }
            if (a.kPost.equals(this.c)) {
                try {
                    outputStream = this.k.getOutputStream();
                    try {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                        try {
                            a((OutputStream) bufferedOutputStream);
                            hp.a(bufferedOutputStream);
                            hp.a(outputStream);
                        } catch (Throwable th2) {
                            th = th2;
                            closeable = outputStream;
                            hp.a(bufferedOutputStream);
                            hp.a(closeable);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedOutputStream = null;
                        closeable = outputStream;
                        hp.a(bufferedOutputStream);
                        hp.a(closeable);
                        throw th;
                    }
                } catch (Throwable th4) {
                    o();
                }
            }
            this.o = this.k.getResponseCode();
            for (Entry entry2 : this.k.getHeaderFields().entrySet()) {
                for (Object a : (List) entry2.getValue()) {
                    this.p.a(entry2.getKey(), a);
                }
            }
            if (!a.kGet.equals(this.c) && !a.kPost.equals(this.c)) {
                o();
            } else if (this.m) {
                o();
            } else {
                try {
                    outputStream = this.k.getInputStream();
                    try {
                        bufferedOutputStream = new BufferedInputStream(outputStream);
                    } catch (Throwable th5) {
                        th = th5;
                        bufferedOutputStream = outputStream;
                        hp.a(closeable);
                        hp.a(bufferedOutputStream);
                        throw th;
                    }
                    try {
                        a((InputStream) bufferedOutputStream);
                        hp.a(bufferedOutputStream);
                        hp.a(outputStream);
                        o();
                    } catch (Throwable th6) {
                        th = th6;
                        closeable = bufferedOutputStream;
                        bufferedOutputStream = outputStream;
                        hp.a(closeable);
                        hp.a(bufferedOutputStream);
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    bufferedOutputStream = null;
                    hp.a(closeable);
                    hp.a(bufferedOutputStream);
                    throw th;
                }
            }
        }
    }

    private void a(OutputStream outputStream) throws Exception {
        if (this.j != null && !b() && outputStream != null) {
            this.j.a(this, outputStream);
        }
    }

    private void a(InputStream inputStream) throws Exception {
        if (this.j != null && !b() && inputStream != null) {
            this.j.a(this, inputStream);
        }
    }

    private void n() {
        if (this.j != null && !b()) {
            this.j.a(this);
        }
    }

    private void o() {
        if (!this.l) {
            this.l = true;
            if (this.k != null) {
                this.k.disconnect();
            }
        }
    }

    private void p() {
        if (!this.l) {
            this.l = true;
            if (this.k != null) {
                new Thread(this) {
                    final /* synthetic */ gl a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        try {
                            if (this.a.k != null) {
                                this.a.k.disconnect();
                            }
                        } catch (Throwable th) {
                        }
                    }
                }.start();
            }
        }
    }
}
