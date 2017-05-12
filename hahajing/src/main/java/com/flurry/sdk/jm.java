package com.flurry.sdk;

public final class jm implements is {
    private final ja a;

    public jm(ja jaVar) {
        this.a = jaVar;
    }

    public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
        iu iuVar = (iu) jwVar.a().getAnnotation(iu.class);
        if (iuVar == null) {
            return null;
        }
        return a(this.a, iaVar, jwVar, iuVar);
    }

    static ir<?> a(ja jaVar, ia iaVar, jw<?> jwVar, iu iuVar) {
        Class a = iuVar.a();
        if (ir.class.isAssignableFrom(a)) {
            return (ir) jaVar.a(jw.b(a)).a();
        }
        if (is.class.isAssignableFrom(a)) {
            return ((is) jaVar.a(jw.b(a)).a()).a(iaVar, jwVar);
        }
        throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter or TypeAdapterFactory reference.");
    }
}
