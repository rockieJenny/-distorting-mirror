package rrrrrr;

import com.immersion.hapticmediasdk.MediaPlaybackSDK;
import com.immersion.hapticmediasdk.utils.Log;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class rrccrr implements Runnable {
    private static final String a = "ValidateURL";
    public static int b044A044Aъ044A044A044A = 2;
    public static int b044Aъъ044A044A044A = 24;
    public static int bъ044Aъ044A044A044A = 1;
    private URL b;
    public final /* synthetic */ MediaPlaybackSDK b0425Х0425ХХ0425;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rrccrr(com.immersion.hapticmediasdk.MediaPlaybackSDK r5, java.lang.String r6) throws java.net.MalformedURLException {
        /*
        r4 = this;
        r3 = 1;
        r0 = 0;
        r1 = -1;
        r4.b0425Х0425ХХ0425 = r5;
    L_0x0005:
        r2 = new int[r1];	 Catch:{ Exception -> 0x0024 }
        goto L_0x0005;
    L_0x0008:
        r0 = move-exception;
        r4.<init>();
        r0 = new java.net.URL;
    L_0x000e:
        switch(r3) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0015;
            default: goto L_0x0011;
        };
    L_0x0011:
        switch(r3) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0015;
            default: goto L_0x0014;
        };
    L_0x0014:
        goto L_0x0011;
    L_0x0015:
        r0.<init>(r6);
        r4.b = r0;
        return;
    L_0x001b:
        r0.length();	 Catch:{ Exception -> 0x001f }
        goto L_0x001b;
    L_0x001f:
        r1 = move-exception;
    L_0x0020:
        r0.length();	 Catch:{ Exception -> 0x0008 }
        goto L_0x0020;
    L_0x0024:
        r1 = move-exception;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrccrr.<init>(com.immersion.hapticmediasdk.MediaPlaybackSDK, java.lang.String):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r2) {
        /*
        r1 = this;
        monitor-enter(r1);
        r0 = r1.b0425Х0425ХХ0425;	 Catch:{ all -> 0x0014 }
        com.immersion.hapticmediasdk.MediaPlaybackSDK.bллл043B043Bл(r0, r2);	 Catch:{ all -> 0x0014 }
        r1.notifyAll();	 Catch:{ all -> 0x0014 }
    L_0x0009:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0012;
            case 1: goto L_0x0009;
            default: goto L_0x000d;
        };	 Catch:{ all -> 0x0014 }
    L_0x000d:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0012;
            default: goto L_0x0011;
        };	 Catch:{ all -> 0x0014 }
    L_0x0011:
        goto L_0x000d;
    L_0x0012:
        monitor-exit(r1);	 Catch:{ all -> 0x0014 }
        return;
    L_0x0014:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0014 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrccrr.a(int):void");
    }

    public static int bъъ044A044A044A044A() {
        return 6;
    }

    public void run() {
        String str;
        int responseCode;
        int i = 500;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) this.b.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setUseCaches(false);
            str = HttpRequest.METHOD_HEAD;
            int i2 = b044Aъъ044A044A044A;
            switch ((i2 * (bъ044Aъ044A044A044A + i2)) % b044A044Aъ044A044A044A) {
                case 0:
                    break;
                default:
                    b044Aъъ044A044A044A = 0;
                    bъ044Aъ044A044A044A = bъъ044A044A044A044A();
                    break;
            }
            httpURLConnection.setRequestMethod(str);
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = e;
            str = a;
            responseCode = responseCode.getMessage();
            Log.e(str, responseCode);
            try {
            } catch (Exception e2) {
                throw e2;
            }
        } finally {
            a(
/*
Method generation error in method: rrrrrr.rrccrr.run():void
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x004b: INVOKE  (r6_0 'this' rrrrrr.rrccrr), (wrap: int
  ?: MERGE  (r1_1 int) = (r1_0 'i' int), (r0_8 'responseCode' int)) rrrrrr.rrccrr.a(int):void type: DIRECT in method: rrrrrr.rrccrr.run():void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r1_1 int) = (r1_0 'i' int), (r0_8 'responseCode' int) in method: rrrrrr.rrccrr.run():void
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:679)
	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:649)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:343)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 20 more
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 25 more

*/
        }
