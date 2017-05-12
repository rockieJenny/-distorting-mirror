package com.flurry.sdk;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class kb implements Closeable {
    static final Pattern a = Pattern.compile("[a-z0-9_-]{1,64}");
    private static final OutputStream p = new OutputStream() {
        public void write(int i) throws IOException {
        }
    };
    final ThreadPoolExecutor b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final File c;
    private final File d;
    private final File e;
    private final File f;
    private final int g;
    private long h;
    private final int i;
    private long j = 0;
    private Writer k;
    private final LinkedHashMap<String, b> l = new LinkedHashMap(0, 0.75f, true);
    private int m;
    private long n = 0;
    private final Callable<Void> o = new Callable<Void>(this) {
        final /* synthetic */ kb a;

        {
            this.a = r1;
        }

        public /* synthetic */ Object call() throws Exception {
            return a();
        }

        public Void a() throws Exception {
            synchronized (this.a) {
                if (this.a.k == null) {
                } else {
                    this.a.h();
                    if (this.a.f()) {
                        this.a.e();
                        this.a.m = 0;
                    }
                }
            }
            return null;
        }
    };

    public final class a {
        final /* synthetic */ kb a;
        private final b b;
        private final boolean[] c;
        private boolean d;
        private boolean e;

        class a extends FilterOutputStream {
            final /* synthetic */ a a;

            private a(a aVar, OutputStream outputStream) {
                this.a = aVar;
                super(outputStream);
            }

            public void write(int i) {
                try {
                    this.out.write(i);
                } catch (IOException e) {
                    this.a.d = true;
                }
            }

            public void write(byte[] bArr, int i, int i2) {
                try {
                    this.out.write(bArr, i, i2);
                } catch (IOException e) {
                    this.a.d = true;
                }
            }

            public void close() {
                try {
                    this.out.close();
                } catch (IOException e) {
                    this.a.d = true;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                } catch (IOException e) {
                    this.a.d = true;
                }
            }
        }

        private a(kb kbVar, b bVar) {
            this.a = kbVar;
            this.b = bVar;
            this.c = bVar.d ? null : new boolean[kbVar.i];
        }

        public OutputStream a(int i) throws IOException {
            OutputStream b;
            synchronized (this.a) {
                OutputStream fileOutputStream;
                if (this.b.e != this) {
                    throw new IllegalStateException();
                }
                if (!this.b.d) {
                    this.c[i] = true;
                }
                r1 = this.b.b(i);
                try {
                    fileOutputStream = new FileOutputStream(r1);
                } catch (FileNotFoundException e) {
                    this.a.c.mkdirs();
                    try {
                        File b2;
                        fileOutputStream = new FileOutputStream(b2);
                    } catch (FileNotFoundException e2) {
                        b = kb.p;
                    }
                }
                b = new a(fileOutputStream);
            }
            return b;
        }

        public void a() throws IOException {
            if (this.d) {
                this.a.a(this, false);
                this.a.c(this.b.b);
            } else {
                this.a.a(this, true);
            }
            this.e = true;
        }

        public void b() throws IOException {
            this.a.a(this, false);
        }
    }

    final class b {
        final /* synthetic */ kb a;
        private final String b;
        private final long[] c;
        private boolean d;
        private a e;
        private long f;

        private b(kb kbVar, String str) {
            this.a = kbVar;
            this.b = str;
            this.c = new long[kbVar.i];
        }

        public String a() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            for (long append : this.c) {
                stringBuilder.append(' ').append(append);
            }
            return stringBuilder.toString();
        }

        private void a(String[] strArr) throws IOException {
            if (strArr.length != this.a.i) {
                throw b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.c[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw b(strArr);
                }
            }
        }

        private IOException b(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        public File a(int i) {
            return new File(this.a.c, this.b + "." + i);
        }

        public File b(int i) {
            return new File(this.a.c, this.b + "." + i + ".tmp");
        }
    }

    public final class c implements Closeable {
        final /* synthetic */ kb a;
        private final String b;
        private final long c;
        private final InputStream[] d;
        private final long[] e;

        private c(kb kbVar, String str, long j, InputStream[] inputStreamArr, long[] jArr) {
            this.a = kbVar;
            this.b = str;
            this.c = j;
            this.d = inputStreamArr;
            this.e = jArr;
        }

        public InputStream a(int i) {
            return this.d[i];
        }

        public void close() {
            for (Closeable a : this.d) {
                kd.a(a);
            }
        }
    }

    private kb(File file, int i, int i2, long j) {
        this.c = file;
        this.g = i;
        this.d = new File(file, "journal");
        this.e = new File(file, "journal.tmp");
        this.f = new File(file, "journal.bkp");
        this.i = i2;
        this.h = j;
    }

    public static kb a(File file, int i, int i2, long j) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            File file2 = new File(file, "journal.bkp");
            if (file2.exists()) {
                File file3 = new File(file, "journal");
                if (file3.exists()) {
                    file2.delete();
                } else {
                    a(file2, file3, false);
                }
            }
            kb kbVar = new kb(file, i, i2, j);
            if (kbVar.d.exists()) {
                try {
                    kbVar.c();
                    kbVar.d();
                    kbVar.k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(kbVar.d, true), kd.a));
                    return kbVar;
                } catch (IOException e) {
                    System.out.println("DiskLruCache " + file + " is corrupt: " + e.getMessage() + ", removing");
                    kbVar.a();
                }
            }
            file.mkdirs();
            kbVar = new kb(file, i, i2, j);
            kbVar.e();
            return kbVar;
        }
    }

    private void c() throws IOException {
        int i;
        Closeable kcVar = new kc(new FileInputStream(this.d), kd.a);
        try {
            String a = kcVar.a();
            String a2 = kcVar.a();
            String a3 = kcVar.a();
            String a4 = kcVar.a();
            String a5 = kcVar.a();
            if ("libcore.io.DiskLruCache".equals(a) && "1".equals(a2) && Integer.toString(this.g).equals(a3) && Integer.toString(this.i).equals(a4) && "".equals(a5)) {
                i = 0;
                while (true) {
                    d(kcVar.a());
                    i++;
                }
            } else {
                throw new IOException("unexpected journal header: [" + a + ", " + a2 + ", " + a4 + ", " + a5 + "]");
            }
        } catch (EOFException e) {
            this.m = i - this.l.size();
            kd.a(kcVar);
        } catch (Throwable th) {
            kd.a(kcVar);
        }
    }

    private void d(String str) throws IOException {
        int indexOf = str.indexOf(32);
        if (indexOf == -1) {
            throw new IOException("unexpected journal line: " + str);
        }
        String str2;
        int i = indexOf + 1;
        int indexOf2 = str.indexOf(32, i);
        if (indexOf2 == -1) {
            String substring = str.substring(i);
            if (indexOf == "REMOVE".length() && str.startsWith("REMOVE")) {
                this.l.remove(substring);
                return;
            }
            str2 = substring;
        } else {
            str2 = str.substring(i, indexOf2);
        }
        b bVar = (b) this.l.get(str2);
        if (bVar == null) {
            bVar = new b(str2);
            this.l.put(str2, bVar);
        }
        if (indexOf2 != -1 && indexOf == "CLEAN".length() && str.startsWith("CLEAN")) {
            String[] split = str.substring(indexOf2 + 1).split(" ");
            bVar.d = true;
            bVar.e = null;
            bVar.a(split);
        } else if (indexOf2 == -1 && indexOf == "DIRTY".length() && str.startsWith("DIRTY")) {
            bVar.e = new a(bVar);
        } else if (indexOf2 != -1 || indexOf != "READ".length() || !str.startsWith("READ")) {
            throw new IOException("unexpected journal line: " + str);
        }
    }

    private void d() throws IOException {
        a(this.e);
        Iterator it = this.l.values().iterator();
        while (it.hasNext()) {
            b bVar = (b) it.next();
            int i;
            if (bVar.e == null) {
                for (i = 0; i < this.i; i++) {
                    this.j += bVar.c[i];
                }
            } else {
                bVar.e = null;
                for (i = 0; i < this.i; i++) {
                    a(bVar.a(i));
                    a(bVar.b(i));
                }
                it.remove();
            }
        }
    }

    private synchronized void e() throws IOException {
        if (this.k != null) {
            this.k.close();
        }
        Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.e), kd.a));
        try {
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.g));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.i));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (b bVar : this.l.values()) {
                if (bVar.e != null) {
                    bufferedWriter.write("DIRTY " + bVar.b + '\n');
                } else {
                    bufferedWriter.write("CLEAN " + bVar.b + bVar.a() + '\n');
                }
            }
            bufferedWriter.close();
            if (this.d.exists()) {
                a(this.d, this.f, true);
            }
            a(this.e, this.d, false);
            this.f.delete();
            this.k = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.d, true), kd.a));
        } catch (Throwable th) {
            bufferedWriter.close();
        }
    }

    private static void a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void a(File file, File file2, boolean z) throws IOException {
        if (z) {
            a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    public synchronized c a(String str) throws IOException {
        int i;
        c cVar = null;
        synchronized (this) {
            g();
            e(str);
            b bVar = (b) this.l.get(str);
            if (bVar != null) {
                if (bVar.d) {
                    r6 = new InputStream[this.i];
                    int i2 = 0;
                    while (i2 < this.i) {
                        try {
                            r6[i2] = new FileInputStream(bVar.a(i2));
                            i2++;
                        } catch (FileNotFoundException e) {
                            i = 0;
                            while (i < this.i && r6[i] != null) {
                                InputStream[] inputStreamArr;
                                kd.a(inputStreamArr[i]);
                                i++;
                            }
                        }
                    }
                    this.m++;
                    this.k.append("READ " + str + '\n');
                    if (f()) {
                        this.b.submit(this.o);
                    }
                    cVar = new c(str, bVar.f, inputStreamArr, bVar.c);
                }
            }
        }
        return cVar;
    }

    public a b(String str) throws IOException {
        return a(str, -1);
    }

    private synchronized a a(String str, long j) throws IOException {
        a aVar;
        g();
        e(str);
        b bVar = (b) this.l.get(str);
        if (j == -1 || (bVar != null && bVar.f == j)) {
            b bVar2;
            if (bVar == null) {
                bVar = new b(str);
                this.l.put(str, bVar);
                bVar2 = bVar;
            } else if (bVar.e != null) {
                aVar = null;
            } else {
                bVar2 = bVar;
            }
            aVar = new a(bVar2);
            bVar2.e = aVar;
            this.k.write("DIRTY " + str + '\n');
            this.k.flush();
        } else {
            aVar = null;
        }
        return aVar;
    }

    private synchronized void a(a aVar, boolean z) throws IOException {
        int i = 0;
        synchronized (this) {
            b a = aVar.b;
            if (a.e != aVar) {
                throw new IllegalStateException();
            }
            if (z) {
                if (!a.d) {
                    int i2 = 0;
                    while (i2 < this.i) {
                        if (!aVar.c[i2]) {
                            aVar.b();
                            throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                        } else if (!a.b(i2).exists()) {
                            aVar.b();
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            while (i < this.i) {
                File b = a.b(i);
                if (!z) {
                    a(b);
                } else if (b.exists()) {
                    File a2 = a.a(i);
                    b.renameTo(a2);
                    long j = a.c[i];
                    long length = a2.length();
                    a.c[i] = length;
                    this.j = (this.j - j) + length;
                }
                i++;
            }
            this.m++;
            a.e = null;
            if ((a.d | z) != 0) {
                a.d = true;
                this.k.write("CLEAN " + a.b + a.a() + '\n');
                if (z) {
                    long j2 = this.n;
                    this.n = 1 + j2;
                    a.f = j2;
                }
            } else {
                this.l.remove(a.b);
                this.k.write("REMOVE " + a.b + '\n');
            }
            this.k.flush();
            if (this.j > this.h || f()) {
                this.b.submit(this.o);
            }
        }
    }

    private boolean f() {
        return this.m >= 2000 && this.m >= this.l.size();
    }

    public synchronized boolean c(String str) throws IOException {
        boolean z;
        int i = 0;
        synchronized (this) {
            g();
            e(str);
            b bVar = (b) this.l.get(str);
            if (bVar == null || bVar.e != null) {
                z = false;
            } else {
                while (i < this.i) {
                    File a = bVar.a(i);
                    if (!a.exists() || a.delete()) {
                        this.j -= bVar.c[i];
                        bVar.c[i] = 0;
                        i++;
                    } else {
                        throw new IOException("failed to delete " + a);
                    }
                }
                this.m++;
                this.k.append("REMOVE " + str + '\n');
                this.l.remove(str);
                if (f()) {
                    this.b.submit(this.o);
                }
                z = true;
            }
        }
        return z;
    }

    private void g() {
        if (this.k == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void close() throws IOException {
        if (this.k != null) {
            Iterator it = new ArrayList(this.l.values()).iterator();
            while (it.hasNext()) {
                b bVar = (b) it.next();
                if (bVar.e != null) {
                    bVar.e.b();
                }
            }
            h();
            this.k.close();
            this.k = null;
        }
    }

    private void h() throws IOException {
        while (this.j > this.h) {
            c((String) ((Entry) this.l.entrySet().iterator().next()).getKey());
        }
    }

    public void a() throws IOException {
        close();
        kd.a(this.c);
    }

    private void e(String str) {
        if (!a.matcher(str).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,64}: \"" + str + "\"");
        }
    }
}
