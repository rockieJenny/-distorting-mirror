package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class cl<ObjectType> implements gx<ObjectType> {
    protected Class<ObjectType> a;
    private final ia b;

    public cl(Class<ObjectType> cls) {
        this(cls, false);
    }

    public cl(Class<ObjectType> cls, boolean z) {
        this.a = cls;
        ib ibVar = new ib();
        if (z) {
            ibVar.a();
        }
        this.b = ibVar.b();
    }

    public void a(OutputStream outputStream, ObjectType objectType) throws IOException {
        if (outputStream != null && objectType != null) {
            Appendable outputStreamWriter = new OutputStreamWriter(outputStream);
            this.b.a((Object) objectType, outputStreamWriter);
            outputStreamWriter.flush();
        }
    }

    public ObjectType b(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        return this.b.a(new InputStreamReader(inputStream), this.a);
    }
}
