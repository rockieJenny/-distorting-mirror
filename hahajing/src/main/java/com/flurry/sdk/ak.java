package com.flurry.sdk;

public class ak<ObjectType> extends al {
    private static final String a = al.class.getSimpleName();
    private final gx<ObjectType> b;

    public ak(gx<ObjectType> gxVar, String str, long j, boolean z) {
        super(str, j, z);
        if (gxVar == null) {
            throw new IllegalArgumentException("Serializer cannot be null");
        }
        this.b = gxVar;
    }
}
