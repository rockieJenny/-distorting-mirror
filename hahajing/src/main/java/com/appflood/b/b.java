package com.appflood.b;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import com.appflood.c.f;
import com.appflood.e.j;
import com.appflood.e.k;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import org.json.JSONObject;

public final class b implements Runnable {
    protected boolean a;
    private URL b;
    private a c;
    private int d;
    private int e;
    private byte[] f;

    public interface a {
        void a(int i);

        void a(b bVar);
    }

    class AnonymousClass1 implements a {
        final /* synthetic */ View a;

        AnonymousClass1(View view) {
            this.a = view;
        }

        public final void a(int i) {
            j.c("UI image download failed. statuscode = " + i);
        }

        public final void a(b bVar) {
            final Bitmap d = bVar.d();
            f.a(new Runnable(this) {
                private /* synthetic */ AnonymousClass1 b;

                public final void run() {
                    try {
                        if (this.b.a instanceof ImageView) {
                            ((ImageView) this.b.a).setImageBitmap(d);
                        } else {
                            this.b.a.setBackgroundDrawable(new BitmapDrawable(d));
                        }
                    } catch (Throwable th) {
                        j.a(th, "set view image failed!");
                    }
                }
            });
        }
    }

    public b(String str) {
        this.c = null;
        this.d = -1;
        this.e = -1;
        this.a = false;
        this.b = k.a(str);
    }

    public b(String str, byte b) {
        this.c = null;
        this.d = -1;
        this.e = -1;
        this.a = false;
        this.b = k.a(str);
        this.a = true;
    }

    public b(String str, Map<String, Object> map) {
        this.c = null;
        this.d = -1;
        this.e = -1;
        this.a = false;
        this.b = k.b(str, map);
    }

    public b(JSONObject jSONObject) {
        this(j.a(jSONObject, "url", null), (byte) 0);
    }

    private void a(int i) {
        if (this.c != null) {
            this.c.a(i);
        }
        a d = f.d();
        synchronized (d) {
            try {
                j.c("connection failed " + i + " " + this.b.toString());
                d.a.remove(this);
            } catch (Throwable th) {
            }
        }
        d.a();
    }

    public final b a(a aVar) {
        this.c = aVar;
        return this;
    }

    public final URL a() {
        return this.b;
    }

    public final void a(View view) {
        this.c = new AnonymousClass1(view);
        f();
    }

    protected final void a(boolean z) {
        if (z && this.a && this.f != null) {
            f.c().a(this.b.toString(), this.f);
        }
        if (this.c != null) {
            this.c.a(this);
        }
        if (z) {
            f.d().a(this);
        }
    }

    public final void b(boolean z) {
        if (z) {
            f.d().c(this);
        } else {
            f.d().b(this);
        }
    }

    public final byte[] b() {
        return this.f;
    }

    public final String c() {
        return j.a(this.f, "");
    }

    public final Bitmap d() {
        try {
            return BitmapFactory.decodeByteArray(this.f, 0, this.f.length);
        } catch (Throwable th) {
            j.b(th, "broken image");
            return null;
        }
    }

    public final boolean e() {
        File a = f.c().a(this.b.toString());
        if (!a.exists()) {
            return false;
        }
        this.f = com.appflood.e.a.a(a);
        return true;
    }

    public final void f() {
        if (f.d() != null) {
            f.d().b(this);
        }
    }

    public final void run() {
        Closeable byteArrayOutputStream;
        Throwable th;
        Object obj;
        Closeable closeable = null;
        byte[] bArr = new byte[1024];
        try {
            URLConnection uRLConnection = (HttpURLConnection) this.b.openConnection();
            uRLConnection.setConnectTimeout(this.d > 0 ? this.d : 45000);
            if (this.e > 0) {
                uRLConnection.setReadTimeout(this.e);
            } else if (this.a) {
                uRLConnection.setReadTimeout(180000);
            } else {
                uRLConnection.setReadTimeout(60000);
            }
            uRLConnection.connect();
            int responseCode = uRLConnection.getResponseCode();
            if (responseCode == 200) {
                ByteArrayOutputStream byteArrayOutputStream2;
                try {
                    Closeable a = com.appflood.e.a.a(uRLConnection);
                    try {
                        int read;
                        byteArrayOutputStream = new ByteArrayOutputStream(uRLConnection.getContentLength() == -1 ? 1024 : uRLConnection.getContentLength());
                        while (true) {
                            try {
                                read = a.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                byteArrayOutputStream.write(bArr, 0, read);
                            } catch (Throwable th2) {
                                th = th2;
                                closeable = byteArrayOutputStream;
                                byteArrayOutputStream = a;
                            }
                        }
                        com.appflood.e.a.a(a);
                        com.appflood.e.a.a(byteArrayOutputStream);
                        read = 1;
                    } catch (Throwable th3) {
                        th = th3;
                        byteArrayOutputStream = a;
                        com.appflood.e.a.a(byteArrayOutputStream);
                        com.appflood.e.a.a(closeable);
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    byteArrayOutputStream = null;
                    com.appflood.e.a.a(byteArrayOutputStream);
                    com.appflood.e.a.a(closeable);
                    throw th;
                }
                if (obj != null) {
                    this.f = byteArrayOutputStream2.toByteArray();
                    a(true);
                    return;
                }
                a(-2);
                return;
            }
            a(responseCode);
        } catch (Throwable th5) {
            a(-1);
        }
    }
}
