package com.flurry.sdk;

import com.flurry.sdk.at.a;
import com.flurry.sdk.az.b;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class l {
    private static final String a = l.class.getSimpleName();

    public static List<az> a(File file) {
        Closeable fileInputStream;
        Closeable dataInputStream;
        Throwable e;
        Throwable th;
        Closeable closeable = null;
        if (file == null || !file.exists()) {
            return Collections.emptyList();
        }
        gx bVar = new b();
        List<az> arrayList = new ArrayList();
        try {
            fileInputStream = new FileInputStream(file);
            try {
                dataInputStream = new DataInputStream(fileInputStream);
                while ((short) 1 == dataInputStream.readShort()) {
                    try {
                        arrayList.add(bVar.b(dataInputStream));
                    } catch (Exception e2) {
                        e = e2;
                        closeable = fileInputStream;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                hp.a(dataInputStream);
                hp.a(fileInputStream);
                return arrayList;
            } catch (Exception e3) {
                e = e3;
                dataInputStream = null;
                closeable = fileInputStream;
                try {
                    gd.a(3, a, "Error loading legacy FreqCap data.", e);
                    hp.a(dataInputStream);
                    hp.a(closeable);
                    return arrayList;
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = closeable;
                    hp.a(dataInputStream);
                    hp.a(fileInputStream);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                dataInputStream = null;
                hp.a(dataInputStream);
                hp.a(fileInputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            dataInputStream = null;
            gd.a(3, a, "Error loading legacy FreqCap data.", e);
            hp.a(dataInputStream);
            hp.a(closeable);
            return arrayList;
        } catch (Throwable th5) {
            th = th5;
            dataInputStream = null;
            fileInputStream = null;
            hp.a(dataInputStream);
            hp.a(fileInputStream);
            throw th;
        }
    }

    public static List<at> b(File file) {
        Closeable fileInputStream;
        Closeable dataInputStream;
        Throwable e;
        Throwable th;
        Closeable closeable = null;
        if (file == null || !file.exists()) {
            return Collections.emptyList();
        }
        gx aVar = new a(new as.a());
        List<at> arrayList = new ArrayList();
        try {
            fileInputStream = new FileInputStream(file);
            try {
                dataInputStream = new DataInputStream(fileInputStream);
                try {
                    if (46586 != dataInputStream.readUnsignedShort()) {
                        throw new IOException("Unexpected data format");
                    }
                    while ((short) 1 == dataInputStream.readShort()) {
                        arrayList.add(aVar.b(dataInputStream));
                    }
                    hp.a(dataInputStream);
                    hp.a(fileInputStream);
                    return arrayList;
                } catch (Exception e2) {
                    e = e2;
                    closeable = fileInputStream;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
                dataInputStream = null;
                closeable = fileInputStream;
                try {
                    gd.a(3, a, "Error loading legacy AdLog data.", e);
                    hp.a(dataInputStream);
                    hp.a(closeable);
                    return arrayList;
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = closeable;
                    hp.a(dataInputStream);
                    hp.a(fileInputStream);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                dataInputStream = null;
                hp.a(dataInputStream);
                hp.a(fileInputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            dataInputStream = null;
            gd.a(3, a, "Error loading legacy AdLog data.", e);
            hp.a(dataInputStream);
            hp.a(closeable);
            return arrayList;
        } catch (Throwable th5) {
            th = th5;
            dataInputStream = null;
            fileInputStream = null;
            hp.a(dataInputStream);
            hp.a(fileInputStream);
            throw th;
        }
    }
}
