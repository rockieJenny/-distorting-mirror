package com.jirbo.adcolony;

import android.os.Build.VERSION;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

class ADCDownload extends j implements Runnable {
    d a;
    Listener b;
    String c;
    File d;
    Object e;
    String f;
    String g;
    boolean h;
    boolean i;
    boolean j;
    Map<String, List<String>> k;
    SSLContext l;
    int m;
    String n;

    public interface Listener {
        void on_download_finished(ADCDownload aDCDownload);
    }

    ADCDownload(d controller, String url, Listener listener) {
        this(controller, url, listener, null);
    }

    ADCDownload(d controller, String url, Listener listener, String filepath) {
        super(controller, false);
        this.c = url;
        this.b = listener;
        if (filepath != null) {
            this.d = new File(filepath);
        }
    }

    ADCDownload a(Object obj) {
        this.e = obj;
        return this;
    }

    ADCDownload a(String str, String str2) {
        this.f = str;
        this.g = str2;
        return this;
    }

    public void b() {
        aa.a(this);
    }

    public void run() {
        int i = 1;
        while (i <= 3) {
            HttpsURLConnection httpsURLConnection = null;
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.c).openConnection();
                InputStream inputStream;
                int read;
                int i2;
                if (this.f != null) {
                    HttpsURLConnection httpsURLConnection2;
                    l.a.b((Object) "Performing POST");
                    if (!this.c.startsWith("https://") || VERSION.SDK_INT < 10) {
                        httpsURLConnection2 = null;
                    } else {
                        httpsURLConnection = (HttpsURLConnection) new URL(this.c).openConnection();
                        this.j = true;
                        httpsURLConnection2 = httpsURLConnection;
                    }
                    if (this.j) {
                        httpsURLConnection2.setRequestMethod(HttpRequest.METHOD_POST);
                    } else {
                        httpURLConnection.setRequestMethod(HttpRequest.METHOD_POST);
                    }
                    if (this.j) {
                        httpsURLConnection2.setDoOutput(true);
                    } else {
                        httpURLConnection.setDoOutput(true);
                    }
                    (this.j ? new PrintStream(httpsURLConnection2.getOutputStream()) : new PrintStream(httpURLConnection.getOutputStream())).println(this.g);
                    l.a.a("Post data: ").b(this.g);
                    if (this.j) {
                        httpsURLConnection2.connect();
                    } else {
                        httpURLConnection.connect();
                    }
                    if ((this.j && httpsURLConnection2.getResponseCode() == 200) || (!this.j && httpURLConnection.getResponseCode() == 200)) {
                        inputStream = this.j ? httpsURLConnection2.getInputStream() : httpURLConnection.getInputStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        this.k = this.j ? httpsURLConnection2.getHeaderFields() : httpURLConnection.getHeaderFields();
                        byte[] bArr = new byte[1024];
                        for (read = inputStream.read(bArr, 0, 1024); read != -1; read = inputStream.read(bArr, 0, 1024)) {
                            i2 = -1;
                            while (true) {
                                i2++;
                                if (i2 >= read) {
                                    break;
                                }
                                stringBuilder.append((char) bArr[i2]);
                            }
                        }
                        inputStream.close();
                        this.n = stringBuilder.toString();
                        this.m = this.n.length();
                        this.i = true;
                        a.a((j) this);
                        return;
                    }
                    if (i == 3) {
                        break;
                    }
                    try {
                        Thread.sleep((long) (((i + 1) * 10) * 1000));
                    } catch (InterruptedException e) {
                    }
                    l.b.a("Trying again (").a(i + 1).b((Object) "/3)");
                    i++;
                } else {
                    httpURLConnection.setReadTimeout(30000);
                    if (this.h) {
                        httpURLConnection.setInstanceFollowRedirects(false);
                    }
                    if (this.d != null) {
                        if (!(this.a == null || this.a.f == null)) {
                            this.a.f.b();
                        }
                        Object absolutePath = this.d.getAbsolutePath();
                        OutputStream fileOutputStream = new FileOutputStream(absolutePath);
                        InputStream inputStream2 = httpURLConnection.getInputStream();
                        read = httpURLConnection.getContentLength();
                        this.m = 0;
                        byte[] bArr2 = new byte[1024];
                        i2 = inputStream2.read(bArr2, 0, 1024);
                        while (i2 != -1) {
                            if (read > 0) {
                                if (i2 > read) {
                                    i2 = read;
                                }
                                read -= i2;
                            }
                            this.m += i2;
                            fileOutputStream.write(bArr2, 0, i2);
                            i2 = inputStream2.read(bArr2, 0, 1024);
                            if (read == 0) {
                                break;
                            }
                        }
                        inputStream2.close();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        l.b.a("Downloaded ").a(this.c).a(" to ").b(absolutePath);
                    } else {
                        if (this.h) {
                            if (this.c.startsWith("https://") && VERSION.SDK_INT >= 10) {
                                httpsURLConnection = (HttpsURLConnection) new URL(this.c).openConnection();
                                this.j = true;
                            }
                            int responseCode = this.j ? httpsURLConnection.getResponseCode() : httpURLConnection.getResponseCode();
                            if (responseCode > 0) {
                                l.a.a("Got HTTP response ").a(responseCode).b((Object) " - counting as completed submission for 3rd party tracking.");
                                l.b.a("Downloaded ").b(this.c);
                                this.n = "";
                                this.m = 0;
                                this.i = true;
                                a.a((j) this);
                                return;
                            }
                        }
                        if (!this.c.startsWith("https://") || VERSION.SDK_INT < 10) {
                            this.j = false;
                        } else {
                            httpsURLConnection = (HttpsURLConnection) new URL(this.c).openConnection();
                            this.j = true;
                            l.a.b((Object) "ADCDownload - use ssl!");
                        }
                        l.a.b((Object) "ADCDownload - before pause");
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e2) {
                        }
                        l.a.b((Object) "ADCDownload - getInputStream");
                        inputStream = this.j ? httpsURLConnection.getInputStream() : httpURLConnection.getInputStream();
                        StringBuilder stringBuilder2 = new StringBuilder();
                        byte[] bArr3 = new byte[1024];
                        for (read = inputStream.read(bArr3, 0, 1024); read != -1; read = inputStream.read(bArr3, 0, 1024)) {
                            i2 = -1;
                            while (true) {
                                i2++;
                                if (i2 >= read) {
                                    break;
                                }
                                try {
                                    stringBuilder2.append((char) bArr3[i2]);
                                } catch (OutOfMemoryError e3) {
                                    l.d.b((Object) "Out of memory, disabling AdColony");
                                    AdColony.disable();
                                    return;
                                }
                            }
                        }
                        inputStream.close();
                        try {
                            this.n = stringBuilder2.toString();
                            this.m = this.n.length();
                            if (this.c.contains("androidads21")) {
                                a.ac = System.currentTimeMillis();
                            }
                            l.b.a("Downloaded ").b(this.c);
                        } catch (OutOfMemoryError e4) {
                            l.d.b((Object) "Out of memory, disabling AdColony");
                            AdColony.disable();
                            return;
                        }
                    }
                    this.i = true;
                    a.a((j) this);
                    return;
                }
            } catch (IOException e5) {
                a.c("Download of " + this.c + " failed:\n" + e5.toString());
            }
        }
        this.i = false;
        a.a((j) this);
    }

    void a() {
        this.b.on_download_finished(this);
    }
}
