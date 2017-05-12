package com.flurry.sdk;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class gv<ObjectType> extends gu<ObjectType> {
    public gv(gx<ObjectType> gxVar) {
        super(gxVar);
    }

    public void a(OutputStream outputStream, ObjectType objectType) throws IOException {
        Closeable gZIPOutputStream;
        Throwable th;
        if (outputStream != null) {
            try {
                gZIPOutputStream = new GZIPOutputStream(outputStream);
                try {
                    super.a(gZIPOutputStream, objectType);
                    hp.a(gZIPOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    hp.a(gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                gZIPOutputStream = null;
                hp.a(gZIPOutputStream);
                throw th;
            }
        }
    }

    public ObjectType b(InputStream inputStream) throws IOException {
        Closeable gZIPInputStream;
        Throwable th;
        ObjectType objectType = null;
        if (inputStream != null) {
            try {
                gZIPInputStream = new GZIPInputStream(inputStream);
                try {
                    objectType = super.b(gZIPInputStream);
                    hp.a(gZIPInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    hp.a(gZIPInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                gZIPInputStream = null;
                th = th4;
                hp.a(gZIPInputStream);
                throw th;
            }
        }
        return objectType;
    }
}
