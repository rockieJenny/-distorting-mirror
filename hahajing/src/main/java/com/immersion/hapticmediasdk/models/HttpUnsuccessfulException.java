package com.immersion.hapticmediasdk.models;

public class HttpUnsuccessfulException extends Exception {
    public static int b04270427Ч0427ЧЧ = 2;
    public static int b0427ЧЧ0427ЧЧ = 0;
    public static int bЧ0427Ч0427ЧЧ = 1;
    public static int bЧЧЧ0427ЧЧ = 24;
    private static final long serialVersionUID = -251711421440827767L;
    private int a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HttpUnsuccessfulException(int r3, java.lang.String r4) {
        /*
        r2 = this;
        r2.<init>(r4);
    L_0x0003:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000c;
            default: goto L_0x0007;
        };
    L_0x0007:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x000c;
            case 1: goto L_0x0003;
            default: goto L_0x000b;
        };
    L_0x000b:
        goto L_0x0007;
    L_0x000c:
        r0 = bЧЧЧ0427ЧЧ;
        r1 = bЧ0427Ч0427ЧЧ;
        r0 = r0 + r1;
        r1 = bЧЧЧ0427ЧЧ;
        r0 = r0 * r1;
        r1 = b04270427Ч0427ЧЧ;
        r0 = r0 % r1;
        r1 = b0427ЧЧ0427ЧЧ;
        if (r0 == r1) goto L_0x0023;
    L_0x001b:
        r0 = 74;
        bЧЧЧ0427ЧЧ = r0;
        r0 = 98;
        b0427ЧЧ0427ЧЧ = r0;
    L_0x0023:
        r2.a = r3;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.models.HttpUnsuccessfulException.<init>(int, java.lang.String):void");
    }

    public static int b0427Ч04270427ЧЧ() {
        return 75;
    }

    public static int bЧЧ04270427ЧЧ() {
        return 1;
    }

    public int getHttpStatusCode() {
        if (((bЧЧЧ0427ЧЧ + bЧЧ04270427ЧЧ()) * bЧЧЧ0427ЧЧ) % b04270427Ч0427ЧЧ != b0427ЧЧ0427ЧЧ) {
            bЧЧЧ0427ЧЧ = b0427Ч04270427ЧЧ();
            b0427ЧЧ0427ЧЧ = b0427Ч04270427ЧЧ();
        }
        try {
            return this.a;
        } catch (Exception e) {
            throw e;
        }
    }
}
