package com.flurry.sdk;

import java.io.IOException;

public abstract class ir<T> {
    public abstract void a(jz jzVar, T t) throws IOException;

    public abstract T b(jx jxVar) throws IOException;

    public final ig a(T t) {
        try {
            jz joVar = new jo();
            a(joVar, t);
            return joVar.a();
        } catch (Throwable e) {
            throw new ih(e);
        }
    }
}
