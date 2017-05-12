package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;

class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    public org.json.JSONObject invoke(io.fabric.sdk.android.services.settings.SettingsRequest r11) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r10 = this;
        r3 = 0;
        r1 = 0;
        r2 = r10.getQueryParamsFor(r11);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r1 = r10.getHttpRequest(r2);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r1 = r10.applyHeadersTo(r1, r11);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r5 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r6 = "Fabric";	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7.<init>();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r8 = "Requesting settings from ";	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r8 = r10.getUrl();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r5.d(r6, r7);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r5 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r6 = "Fabric";	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7.<init>();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r8 = "Settings query params were: ";	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.append(r2);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r5.d(r6, r7);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r4 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r5 = r1.body();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r4.<init>(r5);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        if (r1 == 0) goto L_0x00e5;
    L_0x0055:
        r5 = io.fabric.sdk.android.Fabric.getLogger();
        r6 = "Fabric";
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "Settings request ID: ";
        r7 = r7.append(r8);
        r8 = "X-REQUEST-ID";
        r8 = r1.header(r8);
        r7 = r7.append(r8);
        r7 = r7.toString();
        r5.d(r6, r7);
        r3 = r4;
    L_0x0078:
        return r3;
    L_0x0079:
        r0 = move-exception;
        r5 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r6 = "Fabric";	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7.<init>();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r8 = "Failed to retrieve settings from ";	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r8 = r10.getUrl();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        r5.e(r6, r7, r0);	 Catch:{ Exception -> 0x0079, all -> 0x00bf }
        if (r1 == 0) goto L_0x0078;
    L_0x009c:
        r5 = io.fabric.sdk.android.Fabric.getLogger();
        r6 = "Fabric";
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "Settings request ID: ";
        r7 = r7.append(r8);
        r8 = "X-REQUEST-ID";
        r8 = r1.header(r8);
        r7 = r7.append(r8);
        r7 = r7.toString();
        r5.d(r6, r7);
        goto L_0x0078;
    L_0x00bf:
        r5 = move-exception;
        if (r1 == 0) goto L_0x00e4;
    L_0x00c2:
        r6 = io.fabric.sdk.android.Fabric.getLogger();
        r7 = "Fabric";
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Settings request ID: ";
        r8 = r8.append(r9);
        r9 = "X-REQUEST-ID";
        r9 = r1.header(r9);
        r8 = r8.append(r9);
        r8 = r8.toString();
        r6.d(r7, r8);
    L_0x00e4:
        throw r5;
    L_0x00e5:
        r3 = r4;
        goto L_0x0078;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.settings.DefaultSettingsSpiCall.invoke(io.fabric.sdk.android.services.settings.SettingsRequest):org.json.JSONObject");
    }

    public DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory) {
        this(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, HttpMethod method) {
        super(kit, protocolAndHostOverride, url, requestFactory, method);
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest requestData) {
        Map<String, String> queryParams = new HashMap();
        queryParams.put(BUILD_VERSION_PARAM, requestData.buildVersion);
        queryParams.put(DISPLAY_VERSION_PARAM, requestData.displayVersion);
        queryParams.put(SOURCE_PARAM, Integer.toString(requestData.source));
        if (requestData.iconHash != null) {
            queryParams.put(ICON_HASH, requestData.iconHash);
        }
        String instanceId = requestData.instanceId;
        if (!CommonUtils.isNullOrEmpty(instanceId)) {
            queryParams.put(INSTANCE_PARAM, instanceId);
        }
        return queryParams;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, SettingsRequest requestData) {
        return request.header(AbstractSpiCall.HEADER_API_KEY, requestData.apiKey).header(AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE).header(AbstractSpiCall.HEADER_D, requestData.deviceId).header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion()).header("Accept", "application/json");
    }
}
