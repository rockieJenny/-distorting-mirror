package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class gu<ObjectType> implements gx<ObjectType> {
    protected final gx<ObjectType> a;

    public gu(gx<ObjectType> gxVar) {
        this.a = gxVar;
    }

    public void a(OutputStream outputStream, ObjectType objectType) throws IOException {
        if (this.a != null && outputStream != null && objectType != null) {
            this.a.a(outputStream, objectType);
        }
    }

    public ObjectType b(InputStream inputStream) throws IOException {
        if (this.a == null || inputStream == null) {
            return null;
        }
        return this.a.b(inputStream);
    }
}
