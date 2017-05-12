package com.google.android.gms.internal;

import java.io.IOException;

public final class qm extends qq<qm> {
    public a[] ayq;

    public static final class a extends qq<a> {
        private static volatile a[] ayr;
        public a ays;
        public String name;

        public static final class a extends qq<a> {
            private static volatile a[] ayt;
            public a ayu;
            public int type;

            public static final class a extends qq<a> {
                public int ayA;
                public int ayB;
                public boolean ayC;
                public a[] ayD;
                public a[] ayE;
                public String[] ayF;
                public long[] ayG;
                public float[] ayH;
                public long ayI;
                public byte[] ayv;
                public String ayw;
                public double ayx;
                public float ayy;
                public long ayz;

                public a() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r0 = this;
                    r0.<init>();
                    r0.ry();
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.<init>():void");
                }

                public void a(com.google.android.gms.internal.qp r9) throws java.io.IOException {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r8 = this;
                    r6 = 0;
                    r1 = 0;
                    r0 = r8.ayv;
                    r2 = com.google.android.gms.internal.qz.azq;
                    r0 = java.util.Arrays.equals(r0, r2);
                    if (r0 != 0) goto L_0x0013;
                L_0x000d:
                    r0 = 1;
                    r2 = r8.ayv;
                    r9.a(r0, r2);
                L_0x0013:
                    r0 = r8.ayw;
                    r2 = "";
                    r0 = r0.equals(r2);
                    if (r0 != 0) goto L_0x0023;
                L_0x001d:
                    r0 = 2;
                    r2 = r8.ayw;
                    r9.b(r0, r2);
                L_0x0023:
                    r2 = r8.ayx;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = 0;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r0 == 0) goto L_0x0039;
                L_0x0033:
                    r0 = 3;
                    r2 = r8.ayx;
                    r9.a(r0, r2);
                L_0x0039:
                    r0 = r8.ayy;
                    r0 = java.lang.Float.floatToIntBits(r0);
                    r2 = 0;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    if (r0 == r2) goto L_0x004c;
                L_0x0046:
                    r0 = 4;
                    r2 = r8.ayy;
                    r9.b(r0, r2);
                L_0x004c:
                    r2 = r8.ayz;
                    r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r0 == 0) goto L_0x0058;
                L_0x0052:
                    r0 = 5;
                    r2 = r8.ayz;
                    r9.b(r0, r2);
                L_0x0058:
                    r0 = r8.ayA;
                    if (r0 == 0) goto L_0x0062;
                L_0x005c:
                    r0 = 6;
                    r2 = r8.ayA;
                    r9.t(r0, r2);
                L_0x0062:
                    r0 = r8.ayB;
                    if (r0 == 0) goto L_0x006c;
                L_0x0066:
                    r0 = 7;
                    r2 = r8.ayB;
                    r9.u(r0, r2);
                L_0x006c:
                    r0 = r8.ayC;
                    if (r0 == 0) goto L_0x0077;
                L_0x0070:
                    r0 = 8;
                    r2 = r8.ayC;
                    r9.b(r0, r2);
                L_0x0077:
                    r0 = r8.ayD;
                    if (r0 == 0) goto L_0x0094;
                L_0x007b:
                    r0 = r8.ayD;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x0094;
                L_0x0080:
                    r0 = r1;
                L_0x0081:
                    r2 = r8.ayD;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x0094;
                L_0x0086:
                    r2 = r8.ayD;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x0091;
                L_0x008c:
                    r3 = 9;
                    r9.a(r3, r2);
                L_0x0091:
                    r0 = r0 + 1;
                    goto L_0x0081;
                L_0x0094:
                    r0 = r8.ayE;
                    if (r0 == 0) goto L_0x00b1;
                L_0x0098:
                    r0 = r8.ayE;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00b1;
                L_0x009d:
                    r0 = r1;
                L_0x009e:
                    r2 = r8.ayE;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00b1;
                L_0x00a3:
                    r2 = r8.ayE;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x00ae;
                L_0x00a9:
                    r3 = 10;
                    r9.a(r3, r2);
                L_0x00ae:
                    r0 = r0 + 1;
                    goto L_0x009e;
                L_0x00b1:
                    r0 = r8.ayF;
                    if (r0 == 0) goto L_0x00ce;
                L_0x00b5:
                    r0 = r8.ayF;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00ce;
                L_0x00ba:
                    r0 = r1;
                L_0x00bb:
                    r2 = r8.ayF;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00ce;
                L_0x00c0:
                    r2 = r8.ayF;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x00cb;
                L_0x00c6:
                    r3 = 11;
                    r9.b(r3, r2);
                L_0x00cb:
                    r0 = r0 + 1;
                    goto L_0x00bb;
                L_0x00ce:
                    r0 = r8.ayG;
                    if (r0 == 0) goto L_0x00e9;
                L_0x00d2:
                    r0 = r8.ayG;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00e9;
                L_0x00d7:
                    r0 = r1;
                L_0x00d8:
                    r2 = r8.ayG;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00e9;
                L_0x00dd:
                    r2 = 12;
                    r3 = r8.ayG;
                    r4 = r3[r0];
                    r9.b(r2, r4);
                    r0 = r0 + 1;
                    goto L_0x00d8;
                L_0x00e9:
                    r2 = r8.ayI;
                    r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r0 == 0) goto L_0x00f6;
                L_0x00ef:
                    r0 = 13;
                    r2 = r8.ayI;
                    r9.b(r0, r2);
                L_0x00f6:
                    r0 = r8.ayH;
                    if (r0 == 0) goto L_0x0110;
                L_0x00fa:
                    r0 = r8.ayH;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x0110;
                L_0x00ff:
                    r0 = r8.ayH;
                    r0 = r0.length;
                    if (r1 >= r0) goto L_0x0110;
                L_0x0104:
                    r0 = 14;
                    r2 = r8.ayH;
                    r2 = r2[r1];
                    r9.b(r0, r2);
                    r1 = r1 + 1;
                    goto L_0x00ff;
                L_0x0110:
                    super.a(r9);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.a(com.google.android.gms.internal.qp):void");
                }

                public /* synthetic */ com.google.android.gms.internal.qw b(com.google.android.gms.internal.qo r2) throws java.io.IOException {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r1 = this;
                    r0 = r1.w(r2);
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.b(com.google.android.gms.internal.qo):com.google.android.gms.internal.qw");
                }

                protected int c() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r8 = this;
                    r6 = 0;
                    r1 = 0;
                    r0 = super.c();
                    r2 = r8.ayv;
                    r3 = com.google.android.gms.internal.qz.azq;
                    r2 = java.util.Arrays.equals(r2, r3);
                    if (r2 != 0) goto L_0x0019;
                L_0x0011:
                    r2 = 1;
                    r3 = r8.ayv;
                    r2 = com.google.android.gms.internal.qp.b(r2, r3);
                    r0 = r0 + r2;
                L_0x0019:
                    r2 = r8.ayw;
                    r3 = "";
                    r2 = r2.equals(r3);
                    if (r2 != 0) goto L_0x002b;
                L_0x0023:
                    r2 = 2;
                    r3 = r8.ayw;
                    r2 = com.google.android.gms.internal.qp.j(r2, r3);
                    r0 = r0 + r2;
                L_0x002b:
                    r2 = r8.ayx;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = 0;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r2 == 0) goto L_0x0043;
                L_0x003b:
                    r2 = 3;
                    r4 = r8.ayx;
                    r2 = com.google.android.gms.internal.qp.b(r2, r4);
                    r0 = r0 + r2;
                L_0x0043:
                    r2 = r8.ayy;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    r3 = 0;
                    r3 = java.lang.Float.floatToIntBits(r3);
                    if (r2 == r3) goto L_0x0058;
                L_0x0050:
                    r2 = 4;
                    r3 = r8.ayy;
                    r2 = com.google.android.gms.internal.qp.c(r2, r3);
                    r0 = r0 + r2;
                L_0x0058:
                    r2 = r8.ayz;
                    r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r2 == 0) goto L_0x0066;
                L_0x005e:
                    r2 = 5;
                    r4 = r8.ayz;
                    r2 = com.google.android.gms.internal.qp.d(r2, r4);
                    r0 = r0 + r2;
                L_0x0066:
                    r2 = r8.ayA;
                    if (r2 == 0) goto L_0x0072;
                L_0x006a:
                    r2 = 6;
                    r3 = r8.ayA;
                    r2 = com.google.android.gms.internal.qp.v(r2, r3);
                    r0 = r0 + r2;
                L_0x0072:
                    r2 = r8.ayB;
                    if (r2 == 0) goto L_0x007e;
                L_0x0076:
                    r2 = 7;
                    r3 = r8.ayB;
                    r2 = com.google.android.gms.internal.qp.w(r2, r3);
                    r0 = r0 + r2;
                L_0x007e:
                    r2 = r8.ayC;
                    if (r2 == 0) goto L_0x008b;
                L_0x0082:
                    r2 = 8;
                    r3 = r8.ayC;
                    r2 = com.google.android.gms.internal.qp.c(r2, r3);
                    r0 = r0 + r2;
                L_0x008b:
                    r2 = r8.ayD;
                    if (r2 == 0) goto L_0x00ac;
                L_0x008f:
                    r2 = r8.ayD;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00ac;
                L_0x0094:
                    r2 = r0;
                    r0 = r1;
                L_0x0096:
                    r3 = r8.ayD;
                    r3 = r3.length;
                    if (r0 >= r3) goto L_0x00ab;
                L_0x009b:
                    r3 = r8.ayD;
                    r3 = r3[r0];
                    if (r3 == 0) goto L_0x00a8;
                L_0x00a1:
                    r4 = 9;
                    r3 = com.google.android.gms.internal.qp.c(r4, r3);
                    r2 = r2 + r3;
                L_0x00a8:
                    r0 = r0 + 1;
                    goto L_0x0096;
                L_0x00ab:
                    r0 = r2;
                L_0x00ac:
                    r2 = r8.ayE;
                    if (r2 == 0) goto L_0x00cd;
                L_0x00b0:
                    r2 = r8.ayE;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00cd;
                L_0x00b5:
                    r2 = r0;
                    r0 = r1;
                L_0x00b7:
                    r3 = r8.ayE;
                    r3 = r3.length;
                    if (r0 >= r3) goto L_0x00cc;
                L_0x00bc:
                    r3 = r8.ayE;
                    r3 = r3[r0];
                    if (r3 == 0) goto L_0x00c9;
                L_0x00c2:
                    r4 = 10;
                    r3 = com.google.android.gms.internal.qp.c(r4, r3);
                    r2 = r2 + r3;
                L_0x00c9:
                    r0 = r0 + 1;
                    goto L_0x00b7;
                L_0x00cc:
                    r0 = r2;
                L_0x00cd:
                    r2 = r8.ayF;
                    if (r2 == 0) goto L_0x00f2;
                L_0x00d1:
                    r2 = r8.ayF;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00f2;
                L_0x00d6:
                    r2 = r1;
                    r3 = r1;
                    r4 = r1;
                L_0x00d9:
                    r5 = r8.ayF;
                    r5 = r5.length;
                    if (r2 >= r5) goto L_0x00ee;
                L_0x00de:
                    r5 = r8.ayF;
                    r5 = r5[r2];
                    if (r5 == 0) goto L_0x00eb;
                L_0x00e4:
                    r4 = r4 + 1;
                    r5 = com.google.android.gms.internal.qp.dk(r5);
                    r3 = r3 + r5;
                L_0x00eb:
                    r2 = r2 + 1;
                    goto L_0x00d9;
                L_0x00ee:
                    r0 = r0 + r3;
                    r2 = r4 * 1;
                    r0 = r0 + r2;
                L_0x00f2:
                    r2 = r8.ayG;
                    if (r2 == 0) goto L_0x0114;
                L_0x00f6:
                    r2 = r8.ayG;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x0114;
                L_0x00fb:
                    r2 = r1;
                L_0x00fc:
                    r3 = r8.ayG;
                    r3 = r3.length;
                    if (r1 >= r3) goto L_0x010d;
                L_0x0101:
                    r3 = r8.ayG;
                    r4 = r3[r1];
                    r3 = com.google.android.gms.internal.qp.D(r4);
                    r2 = r2 + r3;
                    r1 = r1 + 1;
                    goto L_0x00fc;
                L_0x010d:
                    r0 = r0 + r2;
                    r1 = r8.ayG;
                    r1 = r1.length;
                    r1 = r1 * 1;
                    r0 = r0 + r1;
                L_0x0114:
                    r2 = r8.ayI;
                    r1 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r1 == 0) goto L_0x0123;
                L_0x011a:
                    r1 = 13;
                    r2 = r8.ayI;
                    r1 = com.google.android.gms.internal.qp.d(r1, r2);
                    r0 = r0 + r1;
                L_0x0123:
                    r1 = r8.ayH;
                    if (r1 == 0) goto L_0x0138;
                L_0x0127:
                    r1 = r8.ayH;
                    r1 = r1.length;
                    if (r1 <= 0) goto L_0x0138;
                L_0x012c:
                    r1 = r8.ayH;
                    r1 = r1.length;
                    r1 = r1 * 4;
                    r0 = r0 + r1;
                    r1 = r8.ayH;
                    r1 = r1.length;
                    r1 = r1 * 1;
                    r0 = r0 + r1;
                L_0x0138:
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.c():int");
                }

                public boolean equals(java.lang.Object r7) {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r6 = this;
                    r0 = 0;
                    if (r7 != r6) goto L_0x0005;
                L_0x0003:
                    r0 = 1;
                L_0x0004:
                    return r0;
                L_0x0005:
                    r1 = r7 instanceof com.google.android.gms.internal.qm.a.a.a;
                    if (r1 == 0) goto L_0x0004;
                L_0x0009:
                    r7 = (com.google.android.gms.internal.qm.a.a.a) r7;
                    r1 = r6.ayv;
                    r2 = r7.ayv;
                    r1 = java.util.Arrays.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0015:
                    r1 = r6.ayw;
                    if (r1 != 0) goto L_0x0095;
                L_0x0019:
                    r1 = r7.ayw;
                    if (r1 != 0) goto L_0x0004;
                L_0x001d:
                    r2 = r6.ayx;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = r7.ayx;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r1 != 0) goto L_0x0004;
                L_0x002d:
                    r1 = r6.ayy;
                    r1 = java.lang.Float.floatToIntBits(r1);
                    r2 = r7.ayy;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    if (r1 != r2) goto L_0x0004;
                L_0x003b:
                    r2 = r6.ayz;
                    r4 = r7.ayz;
                    r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r1 != 0) goto L_0x0004;
                L_0x0043:
                    r1 = r6.ayA;
                    r2 = r7.ayA;
                    if (r1 != r2) goto L_0x0004;
                L_0x0049:
                    r1 = r6.ayB;
                    r2 = r7.ayB;
                    if (r1 != r2) goto L_0x0004;
                L_0x004f:
                    r1 = r6.ayC;
                    r2 = r7.ayC;
                    if (r1 != r2) goto L_0x0004;
                L_0x0055:
                    r1 = r6.ayD;
                    r2 = r7.ayD;
                    r1 = com.google.android.gms.internal.qu.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x005f:
                    r1 = r6.ayE;
                    r2 = r7.ayE;
                    r1 = com.google.android.gms.internal.qu.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0069:
                    r1 = r6.ayF;
                    r2 = r7.ayF;
                    r1 = com.google.android.gms.internal.qu.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0073:
                    r1 = r6.ayG;
                    r2 = r7.ayG;
                    r1 = com.google.android.gms.internal.qu.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x007d:
                    r1 = r6.ayH;
                    r2 = r7.ayH;
                    r1 = com.google.android.gms.internal.qu.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0087:
                    r2 = r6.ayI;
                    r4 = r7.ayI;
                    r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r1 != 0) goto L_0x0004;
                L_0x008f:
                    r0 = r6.a(r7);
                    goto L_0x0004;
                L_0x0095:
                    r1 = r6.ayw;
                    r2 = r7.ayw;
                    r1 = r1.equals(r2);
                    if (r1 != 0) goto L_0x001d;
                L_0x009f:
                    goto L_0x0004;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.equals(java.lang.Object):boolean");
                }

                public int hashCode() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r7 = this;
                    r6 = 32;
                    r0 = r7.ayv;
                    r0 = java.util.Arrays.hashCode(r0);
                    r0 = r0 + 527;
                    r1 = r0 * 31;
                    r0 = r7.ayw;
                    if (r0 != 0) goto L_0x0084;
                L_0x0010:
                    r0 = 0;
                L_0x0011:
                    r0 = r0 + r1;
                    r2 = r7.ayx;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r0 = r0 * 31;
                    r4 = r2 >>> r6;
                    r2 = r2 ^ r4;
                    r1 = (int) r2;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayy;
                    r1 = java.lang.Float.floatToIntBits(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r2 = r7.ayz;
                    r4 = r7.ayz;
                    r4 = r4 >>> r6;
                    r2 = r2 ^ r4;
                    r1 = (int) r2;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayA;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayB;
                    r0 = r0 + r1;
                    r1 = r0 * 31;
                    r0 = r7.ayC;
                    if (r0 == 0) goto L_0x008b;
                L_0x0042:
                    r0 = 1231; // 0x4cf float:1.725E-42 double:6.08E-321;
                L_0x0044:
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayD;
                    r1 = com.google.android.gms.internal.qu.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayE;
                    r1 = com.google.android.gms.internal.qu.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayF;
                    r1 = com.google.android.gms.internal.qu.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayG;
                    r1 = com.google.android.gms.internal.qu.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.ayH;
                    r1 = com.google.android.gms.internal.qu.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r2 = r7.ayI;
                    r4 = r7.ayI;
                    r4 = r4 >>> r6;
                    r2 = r2 ^ r4;
                    r1 = (int) r2;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.rQ();
                    r0 = r0 + r1;
                    return r0;
                L_0x0084:
                    r0 = r7.ayw;
                    r0 = r0.hashCode();
                    goto L_0x0011;
                L_0x008b:
                    r0 = 1237; // 0x4d5 float:1.733E-42 double:6.11E-321;
                    goto L_0x0044;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.hashCode():int");
                }

                public com.google.android.gms.internal.qm.a.a.a ry() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r6 = this;
                    r4 = 0;
                    r2 = 0;
                    r0 = com.google.android.gms.internal.qz.azq;
                    r6.ayv = r0;
                    r0 = "";
                    r6.ayw = r0;
                    r0 = 0;
                    r6.ayx = r0;
                    r0 = 0;
                    r6.ayy = r0;
                    r6.ayz = r4;
                    r6.ayA = r2;
                    r6.ayB = r2;
                    r6.ayC = r2;
                    r0 = com.google.android.gms.internal.qm.a.ru();
                    r6.ayD = r0;
                    r0 = com.google.android.gms.internal.qm.a.a.rw();
                    r6.ayE = r0;
                    r0 = com.google.android.gms.internal.qz.azo;
                    r6.ayF = r0;
                    r0 = com.google.android.gms.internal.qz.azk;
                    r6.ayG = r0;
                    r0 = com.google.android.gms.internal.qz.azl;
                    r6.ayH = r0;
                    r6.ayI = r4;
                    r0 = 0;
                    r6.ayW = r0;
                    r0 = -1;
                    r6.azh = r0;
                    return r6;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.ry():com.google.android.gms.internal.qm$a$a$a");
                }

                public com.google.android.gms.internal.qm.a.a.a w(com.google.android.gms.internal.qo r7) throws java.io.IOException {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r6 = this;
                    r1 = 0;
                L_0x0001:
                    r0 = r7.rz();
                    switch(r0) {
                        case 0: goto L_0x000e;
                        case 10: goto L_0x000f;
                        case 18: goto L_0x0016;
                        case 25: goto L_0x001d;
                        case 37: goto L_0x0024;
                        case 40: goto L_0x002b;
                        case 48: goto L_0x0032;
                        case 56: goto L_0x0039;
                        case 64: goto L_0x0040;
                        case 74: goto L_0x0047;
                        case 82: goto L_0x0087;
                        case 90: goto L_0x00c7;
                        case 96: goto L_0x00fb;
                        case 98: goto L_0x012f;
                        case 104: goto L_0x0171;
                        case 114: goto L_0x01ad;
                        case 117: goto L_0x0179;
                        default: goto L_0x0008;
                    };
                L_0x0008:
                    r0 = r6.a(r7, r0);
                    if (r0 != 0) goto L_0x0001;
                L_0x000e:
                    return r6;
                L_0x000f:
                    r0 = r7.readBytes();
                    r6.ayv = r0;
                    goto L_0x0001;
                L_0x0016:
                    r0 = r7.readString();
                    r6.ayw = r0;
                    goto L_0x0001;
                L_0x001d:
                    r2 = r7.readDouble();
                    r6.ayx = r2;
                    goto L_0x0001;
                L_0x0024:
                    r0 = r7.readFloat();
                    r6.ayy = r0;
                    goto L_0x0001;
                L_0x002b:
                    r2 = r7.rB();
                    r6.ayz = r2;
                    goto L_0x0001;
                L_0x0032:
                    r0 = r7.rC();
                    r6.ayA = r0;
                    goto L_0x0001;
                L_0x0039:
                    r0 = r7.rE();
                    r6.ayB = r0;
                    goto L_0x0001;
                L_0x0040:
                    r0 = r7.rD();
                    r6.ayC = r0;
                    goto L_0x0001;
                L_0x0047:
                    r0 = 74;
                    r2 = com.google.android.gms.internal.qz.b(r7, r0);
                    r0 = r6.ayD;
                    if (r0 != 0) goto L_0x0073;
                L_0x0051:
                    r0 = r1;
                L_0x0052:
                    r2 = r2 + r0;
                    r2 = new com.google.android.gms.internal.qm.a[r2];
                    if (r0 == 0) goto L_0x005c;
                L_0x0057:
                    r3 = r6.ayD;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x005c:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x0077;
                L_0x0061:
                    r3 = new com.google.android.gms.internal.qm$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r3 = r2[r0];
                    r7.a(r3);
                    r7.rz();
                    r0 = r0 + 1;
                    goto L_0x005c;
                L_0x0073:
                    r0 = r6.ayD;
                    r0 = r0.length;
                    goto L_0x0052;
                L_0x0077:
                    r3 = new com.google.android.gms.internal.qm$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r0 = r2[r0];
                    r7.a(r0);
                    r6.ayD = r2;
                    goto L_0x0001;
                L_0x0087:
                    r0 = 82;
                    r2 = com.google.android.gms.internal.qz.b(r7, r0);
                    r0 = r6.ayE;
                    if (r0 != 0) goto L_0x00b3;
                L_0x0091:
                    r0 = r1;
                L_0x0092:
                    r2 = r2 + r0;
                    r2 = new com.google.android.gms.internal.qm.a.a[r2];
                    if (r0 == 0) goto L_0x009c;
                L_0x0097:
                    r3 = r6.ayE;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x009c:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x00b7;
                L_0x00a1:
                    r3 = new com.google.android.gms.internal.qm$a$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r3 = r2[r0];
                    r7.a(r3);
                    r7.rz();
                    r0 = r0 + 1;
                    goto L_0x009c;
                L_0x00b3:
                    r0 = r6.ayE;
                    r0 = r0.length;
                    goto L_0x0092;
                L_0x00b7:
                    r3 = new com.google.android.gms.internal.qm$a$a;
                    r3.<init>();
                    r2[r0] = r3;
                    r0 = r2[r0];
                    r7.a(r0);
                    r6.ayE = r2;
                    goto L_0x0001;
                L_0x00c7:
                    r0 = 90;
                    r2 = com.google.android.gms.internal.qz.b(r7, r0);
                    r0 = r6.ayF;
                    if (r0 != 0) goto L_0x00ed;
                L_0x00d1:
                    r0 = r1;
                L_0x00d2:
                    r2 = r2 + r0;
                    r2 = new java.lang.String[r2];
                    if (r0 == 0) goto L_0x00dc;
                L_0x00d7:
                    r3 = r6.ayF;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x00dc:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x00f1;
                L_0x00e1:
                    r3 = r7.readString();
                    r2[r0] = r3;
                    r7.rz();
                    r0 = r0 + 1;
                    goto L_0x00dc;
                L_0x00ed:
                    r0 = r6.ayF;
                    r0 = r0.length;
                    goto L_0x00d2;
                L_0x00f1:
                    r3 = r7.readString();
                    r2[r0] = r3;
                    r6.ayF = r2;
                    goto L_0x0001;
                L_0x00fb:
                    r0 = 96;
                    r2 = com.google.android.gms.internal.qz.b(r7, r0);
                    r0 = r6.ayG;
                    if (r0 != 0) goto L_0x0121;
                L_0x0105:
                    r0 = r1;
                L_0x0106:
                    r2 = r2 + r0;
                    r2 = new long[r2];
                    if (r0 == 0) goto L_0x0110;
                L_0x010b:
                    r3 = r6.ayG;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x0110:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x0125;
                L_0x0115:
                    r4 = r7.rB();
                    r2[r0] = r4;
                    r7.rz();
                    r0 = r0 + 1;
                    goto L_0x0110;
                L_0x0121:
                    r0 = r6.ayG;
                    r0 = r0.length;
                    goto L_0x0106;
                L_0x0125:
                    r4 = r7.rB();
                    r2[r0] = r4;
                    r6.ayG = r2;
                    goto L_0x0001;
                L_0x012f:
                    r0 = r7.rG();
                    r3 = r7.gS(r0);
                    r2 = r7.getPosition();
                    r0 = r1;
                L_0x013c:
                    r4 = r7.rL();
                    if (r4 <= 0) goto L_0x0148;
                L_0x0142:
                    r7.rB();
                    r0 = r0 + 1;
                    goto L_0x013c;
                L_0x0148:
                    r7.gU(r2);
                    r2 = r6.ayG;
                    if (r2 != 0) goto L_0x0166;
                L_0x014f:
                    r2 = r1;
                L_0x0150:
                    r0 = r0 + r2;
                    r0 = new long[r0];
                    if (r2 == 0) goto L_0x015a;
                L_0x0155:
                    r4 = r6.ayG;
                    java.lang.System.arraycopy(r4, r1, r0, r1, r2);
                L_0x015a:
                    r4 = r0.length;
                    if (r2 >= r4) goto L_0x016a;
                L_0x015d:
                    r4 = r7.rB();
                    r0[r2] = r4;
                    r2 = r2 + 1;
                    goto L_0x015a;
                L_0x0166:
                    r2 = r6.ayG;
                    r2 = r2.length;
                    goto L_0x0150;
                L_0x016a:
                    r6.ayG = r0;
                    r7.gT(r3);
                    goto L_0x0001;
                L_0x0171:
                    r2 = r7.rB();
                    r6.ayI = r2;
                    goto L_0x0001;
                L_0x0179:
                    r0 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
                    r2 = com.google.android.gms.internal.qz.b(r7, r0);
                    r0 = r6.ayH;
                    if (r0 != 0) goto L_0x019f;
                L_0x0183:
                    r0 = r1;
                L_0x0184:
                    r2 = r2 + r0;
                    r2 = new float[r2];
                    if (r0 == 0) goto L_0x018e;
                L_0x0189:
                    r3 = r6.ayH;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x018e:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x01a3;
                L_0x0193:
                    r3 = r7.readFloat();
                    r2[r0] = r3;
                    r7.rz();
                    r0 = r0 + 1;
                    goto L_0x018e;
                L_0x019f:
                    r0 = r6.ayH;
                    r0 = r0.length;
                    goto L_0x0184;
                L_0x01a3:
                    r3 = r7.readFloat();
                    r2[r0] = r3;
                    r6.ayH = r2;
                    goto L_0x0001;
                L_0x01ad:
                    r0 = r7.rG();
                    r2 = r7.gS(r0);
                    r3 = r0 / 4;
                    r0 = r6.ayH;
                    if (r0 != 0) goto L_0x01d2;
                L_0x01bb:
                    r0 = r1;
                L_0x01bc:
                    r3 = r3 + r0;
                    r3 = new float[r3];
                    if (r0 == 0) goto L_0x01c6;
                L_0x01c1:
                    r4 = r6.ayH;
                    java.lang.System.arraycopy(r4, r1, r3, r1, r0);
                L_0x01c6:
                    r4 = r3.length;
                    if (r0 >= r4) goto L_0x01d6;
                L_0x01c9:
                    r4 = r7.readFloat();
                    r3[r0] = r4;
                    r0 = r0 + 1;
                    goto L_0x01c6;
                L_0x01d2:
                    r0 = r6.ayH;
                    r0 = r0.length;
                    goto L_0x01bc;
                L_0x01d6:
                    r6.ayH = r3;
                    r7.gT(r2);
                    goto L_0x0001;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a.w(com.google.android.gms.internal.qo):com.google.android.gms.internal.qm$a$a$a");
                }
            }

            public a() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.rx();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.<init>():void");
            }

            public static com.google.android.gms.internal.qm.a.a[] rw() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r0 = ayt;
                if (r0 != 0) goto L_0x0011;
            L_0x0004:
                r1 = com.google.android.gms.internal.qu.azg;
                monitor-enter(r1);
                r0 = ayt;
                if (r0 != 0) goto L_0x0010;
            L_0x000b:
                r0 = 0;
                r0 = new com.google.android.gms.internal.qm.a.a[r0];
                ayt = r0;
            L_0x0010:
                monitor-exit(r1);
            L_0x0011:
                r0 = ayt;
                return r0;
            L_0x0014:
                r0 = move-exception;
                monitor-exit(r1);
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.rw():com.google.android.gms.internal.qm$a$a[]");
            }

            public void a(com.google.android.gms.internal.qp r3) throws java.io.IOException {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r2 = this;
                r0 = 1;
                r1 = r2.type;
                r3.t(r0, r1);
                r0 = r2.ayu;
                if (r0 == 0) goto L_0x0010;
            L_0x000a:
                r0 = 2;
                r1 = r2.ayu;
                r3.a(r0, r1);
            L_0x0010:
                super.a(r3);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.a(com.google.android.gms.internal.qp):void");
            }

            public /* synthetic */ com.google.android.gms.internal.qw b(com.google.android.gms.internal.qo r2) throws java.io.IOException {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r1 = this;
                r0 = r1.v(r2);
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.b(com.google.android.gms.internal.qo):com.google.android.gms.internal.qw");
            }

            protected int c() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r3 = this;
                r0 = super.c();
                r1 = 1;
                r2 = r3.type;
                r1 = com.google.android.gms.internal.qp.v(r1, r2);
                r0 = r0 + r1;
                r1 = r3.ayu;
                if (r1 == 0) goto L_0x0018;
            L_0x0010:
                r1 = 2;
                r2 = r3.ayu;
                r1 = com.google.android.gms.internal.qp.c(r1, r2);
                r0 = r0 + r1;
            L_0x0018:
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.c():int");
            }

            public boolean equals(java.lang.Object r4) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r3 = this;
                r0 = 0;
                if (r4 != r3) goto L_0x0005;
            L_0x0003:
                r0 = 1;
            L_0x0004:
                return r0;
            L_0x0005:
                r1 = r4 instanceof com.google.android.gms.internal.qm.a.a;
                if (r1 == 0) goto L_0x0004;
            L_0x0009:
                r4 = (com.google.android.gms.internal.qm.a.a) r4;
                r1 = r3.type;
                r2 = r4.type;
                if (r1 != r2) goto L_0x0004;
            L_0x0011:
                r1 = r3.ayu;
                if (r1 != 0) goto L_0x001e;
            L_0x0015:
                r1 = r4.ayu;
                if (r1 != 0) goto L_0x0004;
            L_0x0019:
                r0 = r3.a(r4);
                goto L_0x0004;
            L_0x001e:
                r1 = r3.ayu;
                r2 = r4.ayu;
                r1 = r1.equals(r2);
                if (r1 != 0) goto L_0x0019;
            L_0x0028:
                goto L_0x0004;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.equals(java.lang.Object):boolean");
            }

            public int hashCode() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r2 = this;
                r0 = r2.type;
                r0 = r0 + 527;
                r1 = r0 * 31;
                r0 = r2.ayu;
                if (r0 != 0) goto L_0x0014;
            L_0x000a:
                r0 = 0;
            L_0x000b:
                r0 = r0 + r1;
                r0 = r0 * 31;
                r1 = r2.rQ();
                r0 = r0 + r1;
                return r0;
            L_0x0014:
                r0 = r2.ayu;
                r0 = r0.hashCode();
                goto L_0x000b;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.hashCode():int");
            }

            public com.google.android.gms.internal.qm.a.a rx() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r2 = this;
                r1 = 0;
                r0 = 1;
                r2.type = r0;
                r2.ayu = r1;
                r2.ayW = r1;
                r0 = -1;
                r2.azh = r0;
                return r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.rx():com.google.android.gms.internal.qm$a$a");
            }

            public com.google.android.gms.internal.qm.a.a v(com.google.android.gms.internal.qo r2) throws java.io.IOException {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r1 = this;
            L_0x0000:
                r0 = r2.rz();
                switch(r0) {
                    case 0: goto L_0x000d;
                    case 8: goto L_0x000e;
                    case 18: goto L_0x0019;
                    default: goto L_0x0007;
                };
            L_0x0007:
                r0 = r1.a(r2, r0);
                if (r0 != 0) goto L_0x0000;
            L_0x000d:
                return r1;
            L_0x000e:
                r0 = r2.rC();
                switch(r0) {
                    case 1: goto L_0x0016;
                    case 2: goto L_0x0016;
                    case 3: goto L_0x0016;
                    case 4: goto L_0x0016;
                    case 5: goto L_0x0016;
                    case 6: goto L_0x0016;
                    case 7: goto L_0x0016;
                    case 8: goto L_0x0016;
                    case 9: goto L_0x0016;
                    case 10: goto L_0x0016;
                    case 11: goto L_0x0016;
                    case 12: goto L_0x0016;
                    case 13: goto L_0x0016;
                    case 14: goto L_0x0016;
                    case 15: goto L_0x0016;
                    default: goto L_0x0015;
                };
            L_0x0015:
                goto L_0x0000;
            L_0x0016:
                r1.type = r0;
                goto L_0x0000;
            L_0x0019:
                r0 = r1.ayu;
                if (r0 != 0) goto L_0x0024;
            L_0x001d:
                r0 = new com.google.android.gms.internal.qm$a$a$a;
                r0.<init>();
                r1.ayu = r0;
            L_0x0024:
                r0 = r1.ayu;
                r2.a(r0);
                goto L_0x0000;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a.v(com.google.android.gms.internal.qo):com.google.android.gms.internal.qm$a$a");
            }
        }

        public a() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r0 = this;
            r0.<init>();
            r0.rv();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.<init>():void");
        }

        public static com.google.android.gms.internal.qm.a[] ru() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r0 = ayr;
            if (r0 != 0) goto L_0x0011;
        L_0x0004:
            r1 = com.google.android.gms.internal.qu.azg;
            monitor-enter(r1);
            r0 = ayr;
            if (r0 != 0) goto L_0x0010;
        L_0x000b:
            r0 = 0;
            r0 = new com.google.android.gms.internal.qm.a[r0];
            ayr = r0;
        L_0x0010:
            monitor-exit(r1);
        L_0x0011:
            r0 = ayr;
            return r0;
        L_0x0014:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.ru():com.google.android.gms.internal.qm$a[]");
        }

        public void a(com.google.android.gms.internal.qp r3) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r2 = this;
            r0 = 1;
            r1 = r2.name;
            r3.b(r0, r1);
            r0 = r2.ays;
            if (r0 == 0) goto L_0x0010;
        L_0x000a:
            r0 = 2;
            r1 = r2.ays;
            r3.a(r0, r1);
        L_0x0010:
            super.a(r3);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.a(com.google.android.gms.internal.qp):void");
        }

        public /* synthetic */ com.google.android.gms.internal.qw b(com.google.android.gms.internal.qo r2) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r0 = r1.u(r2);
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.b(com.google.android.gms.internal.qo):com.google.android.gms.internal.qw");
        }

        protected int c() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r3 = this;
            r0 = super.c();
            r1 = 1;
            r2 = r3.name;
            r1 = com.google.android.gms.internal.qp.j(r1, r2);
            r0 = r0 + r1;
            r1 = r3.ays;
            if (r1 == 0) goto L_0x0018;
        L_0x0010:
            r1 = 2;
            r2 = r3.ays;
            r1 = com.google.android.gms.internal.qp.c(r1, r2);
            r0 = r0 + r1;
        L_0x0018:
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.c():int");
        }

        public boolean equals(java.lang.Object r4) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r3 = this;
            r0 = 0;
            if (r4 != r3) goto L_0x0005;
        L_0x0003:
            r0 = 1;
        L_0x0004:
            return r0;
        L_0x0005:
            r1 = r4 instanceof com.google.android.gms.internal.qm.a;
            if (r1 == 0) goto L_0x0004;
        L_0x0009:
            r4 = (com.google.android.gms.internal.qm.a) r4;
            r1 = r3.name;
            if (r1 != 0) goto L_0x0020;
        L_0x000f:
            r1 = r4.name;
            if (r1 != 0) goto L_0x0004;
        L_0x0013:
            r1 = r3.ays;
            if (r1 != 0) goto L_0x002b;
        L_0x0017:
            r1 = r4.ays;
            if (r1 != 0) goto L_0x0004;
        L_0x001b:
            r0 = r3.a(r4);
            goto L_0x0004;
        L_0x0020:
            r1 = r3.name;
            r2 = r4.name;
            r1 = r1.equals(r2);
            if (r1 != 0) goto L_0x0013;
        L_0x002a:
            goto L_0x0004;
        L_0x002b:
            r1 = r3.ays;
            r2 = r4.ays;
            r1 = r1.equals(r2);
            if (r1 != 0) goto L_0x001b;
        L_0x0035:
            goto L_0x0004;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r3 = this;
            r1 = 0;
            r0 = r3.name;
            if (r0 != 0) goto L_0x0017;
        L_0x0005:
            r0 = r1;
        L_0x0006:
            r0 = r0 + 527;
            r0 = r0 * 31;
            r2 = r3.ays;
            if (r2 != 0) goto L_0x001e;
        L_0x000e:
            r0 = r0 + r1;
            r0 = r0 * 31;
            r1 = r3.rQ();
            r0 = r0 + r1;
            return r0;
        L_0x0017:
            r0 = r3.name;
            r0 = r0.hashCode();
            goto L_0x0006;
        L_0x001e:
            r1 = r3.ays;
            r1 = r1.hashCode();
            goto L_0x000e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.hashCode():int");
        }

        public com.google.android.gms.internal.qm.a rv() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r2 = this;
            r1 = 0;
            r0 = "";
            r2.name = r0;
            r2.ays = r1;
            r2.ayW = r1;
            r0 = -1;
            r2.azh = r0;
            return r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.rv():com.google.android.gms.internal.qm$a");
        }

        public com.google.android.gms.internal.qm.a u(com.google.android.gms.internal.qo r2) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
        L_0x0000:
            r0 = r2.rz();
            switch(r0) {
                case 0: goto L_0x000d;
                case 10: goto L_0x000e;
                case 18: goto L_0x0015;
                default: goto L_0x0007;
            };
        L_0x0007:
            r0 = r1.a(r2, r0);
            if (r0 != 0) goto L_0x0000;
        L_0x000d:
            return r1;
        L_0x000e:
            r0 = r2.readString();
            r1.name = r0;
            goto L_0x0000;
        L_0x0015:
            r0 = r1.ays;
            if (r0 != 0) goto L_0x0020;
        L_0x0019:
            r0 = new com.google.android.gms.internal.qm$a$a;
            r0.<init>();
            r1.ays = r0;
        L_0x0020:
            r0 = r1.ays;
            r2.a(r0);
            goto L_0x0000;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.qm.a.u(com.google.android.gms.internal.qo):com.google.android.gms.internal.qm$a");
        }
    }

    public qm() {
        rt();
    }

    public static qm n(byte[] bArr) throws qv {
        return (qm) qw.a(new qm(), bArr);
    }

    public void a(qp qpVar) throws IOException {
        if (this.ayq != null && this.ayq.length > 0) {
            for (qw qwVar : this.ayq) {
                if (qwVar != null) {
                    qpVar.a(1, qwVar);
                }
            }
        }
        super.a(qpVar);
    }

    public /* synthetic */ qw b(qo qoVar) throws IOException {
        return t(qoVar);
    }

    protected int c() {
        int c = super.c();
        if (this.ayq != null && this.ayq.length > 0) {
            for (qw qwVar : this.ayq) {
                if (qwVar != null) {
                    c += qp.c(1, qwVar);
                }
            }
        }
        return c;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof qm)) {
            return false;
        }
        qm qmVar = (qm) o;
        return qu.equals(this.ayq, qmVar.ayq) ? a((qq) qmVar) : false;
    }

    public int hashCode() {
        return ((qu.hashCode(this.ayq) + 527) * 31) + rQ();
    }

    public qm rt() {
        this.ayq = a.ru();
        this.ayW = null;
        this.azh = -1;
        return this;
    }

    public qm t(qo qoVar) throws IOException {
        while (true) {
            int rz = qoVar.rz();
            switch (rz) {
                case 0:
                    break;
                case 10:
                    int b = qz.b(qoVar, 10);
                    rz = this.ayq == null ? 0 : this.ayq.length;
                    Object obj = new a[(b + rz)];
                    if (rz != 0) {
                        System.arraycopy(this.ayq, 0, obj, 0, rz);
                    }
                    while (rz < obj.length - 1) {
                        obj[rz] = new a();
                        qoVar.a(obj[rz]);
                        qoVar.rz();
                        rz++;
                    }
                    obj[rz] = new a();
                    qoVar.a(obj[rz]);
                    this.ayq = obj;
                    continue;
                default:
                    if (!a(qoVar, rz)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }
}
