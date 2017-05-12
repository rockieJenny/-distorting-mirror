package com.flurry.sdk;

import com.flurry.sdk.fc.a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public final class et {
    private static final String a = et.class.getSimpleName();

    public static fc a(File file) {
        Closeable fileInputStream;
        Closeable dataInputStream;
        Throwable e;
        if (file == null || !file.exists()) {
            return null;
        }
        gx aVar = new a();
        fc fcVar;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                dataInputStream = new DataInputStream(fileInputStream);
            } catch (Exception e2) {
                e = e2;
                dataInputStream = null;
                try {
                    gd.a(3, a, "Error loading legacy agent data.", e);
                    hp.a(dataInputStream);
                    hp.a(fileInputStream);
                    fcVar = null;
                    return fcVar;
                } catch (Throwable th) {
                    e = th;
                    hp.a(dataInputStream);
                    hp.a(fileInputStream);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                dataInputStream = null;
                hp.a(dataInputStream);
                hp.a(fileInputStream);
                throw e;
            }
            try {
                if (dataInputStream.readUnsignedShort() != 46586) {
                    gd.a(3, a, "Unexpected file type");
                    hp.a(dataInputStream);
                    hp.a(fileInputStream);
                    return null;
                }
                int readUnsignedShort = dataInputStream.readUnsignedShort();
                if (readUnsignedShort != 2) {
                    gd.a(6, a, "Unknown agent file version: " + readUnsignedShort);
                    hp.a(dataInputStream);
                    hp.a(fileInputStream);
                    return null;
                }
                fcVar = (fc) aVar.b(dataInputStream);
                hp.a(dataInputStream);
                hp.a(fileInputStream);
                return fcVar;
            } catch (Exception e3) {
                e = e3;
                gd.a(3, a, "Error loading legacy agent data.", e);
                hp.a(dataInputStream);
                hp.a(fileInputStream);
                fcVar = null;
                return fcVar;
            }
        } catch (Exception e4) {
            e = e4;
            dataInputStream = null;
            fileInputStream = null;
            gd.a(3, a, "Error loading legacy agent data.", e);
            hp.a(dataInputStream);
            hp.a(fileInputStream);
            fcVar = null;
            return fcVar;
        } catch (Throwable th3) {
            e = th3;
            dataInputStream = null;
            fileInputStream = null;
            hp.a(dataInputStream);
            hp.a(fileInputStream);
            throw e;
        }
    }
}
