package com.immersion.hapticmediasdk;

public class HapticContentSDKFactory {
    private static final String a = "HapticContentSDKFactory";
    public static int b04460446044604460446ц = 19;
    public static int b0446цццц0446 = 2;
    public static int bццццц0446 = 1;

    public HapticContentSDKFactory() {
        int i = b04460446044604460446ц;
        switch ((i * (bццццц0446 + i)) % b0446цццц0446) {
            case 0:
                return;
            default:
                b04460446044604460446ц = bц0446ццц0446();
                bццццц0446 = bц0446ццц0446();
                return;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.immersion.hapticmediasdk.HapticContentSDK GetNewSDKInstance(int r6, android.content.Context r7) {
        /*
        r2 = 1;
        r0 = 0;
        r1 = com.immersion.content.EndpointWarp.loadSharedLibrary();
        if (r1 != 0) goto L_0x0041;
    L_0x0008:
        return r0;
    L_0x0009:
        r1 = new com.immersion.hapticmediasdk.MediaPlaybackSDK;
        r1.<init>(r7);
        r2 = r1.bБ04110411Б04110411();
        if (r2 == 0) goto L_0x005d;
    L_0x0014:
        r1 = "HapticContentSDKFactory";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = b04460446044604460446ц;
        r5 = bццццц0446;
        r5 = r5 + r4;
        r4 = r4 * r5;
        r5 = b0446цццц0446;
        r4 = r4 % r5;
        switch(r4) {
            case 0: goto L_0x002f;
            default: goto L_0x0027;
        };
    L_0x0027:
        r4 = 42;
        b04460446044604460446ц = r4;
        r4 = 92;
        bццццц0446 = r4;
    L_0x002f:
        r4 = "Failed to create Haptic Content SDK instance. error=";
        r3 = r3.append(r4);
        r2 = r3.append(r2);
        r2 = r2.toString();
        com.immersion.hapticmediasdk.utils.Log.e(r1, r2);
        goto L_0x0008;
    L_0x0041:
        if (r7 != 0) goto L_0x004b;
    L_0x0043:
        r1 = "HapticContentSDKFactory";
        r2 = "Failed to create a Haptic Content SDK instance. invalid context: null";
        com.immersion.hapticmediasdk.utils.Log.e(r1, r2);
        goto L_0x0008;
    L_0x004b:
        switch(r6) {
            case 0: goto L_0x0009;
            default: goto L_0x004e;
        };
    L_0x004e:
        r1 = "HapticContentSDKFactory";
    L_0x0050:
        switch(r2) {
            case 0: goto L_0x0050;
            case 1: goto L_0x0057;
            default: goto L_0x0053;
        };
    L_0x0053:
        switch(r2) {
            case 0: goto L_0x0050;
            case 1: goto L_0x0057;
            default: goto L_0x0056;
        };
    L_0x0056:
        goto L_0x0053;
    L_0x0057:
        r2 = "Failed to create a Haptic Content SDK instance. Invalid mode";
        com.immersion.hapticmediasdk.utils.Log.e(r1, r2);
        goto L_0x0008;
    L_0x005d:
        r0 = "HapticContentSDKFactory";
        r2 = "Haptic Content SDK instance was created successfully";
        com.immersion.hapticmediasdk.utils.Log.i(r0, r2);
        r0 = r1;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDKFactory.GetNewSDKInstance(int, android.content.Context):com.immersion.hapticmediasdk.HapticContentSDK");
    }

    public static int bц0446ццц0446() {
        return 96;
    }
}
