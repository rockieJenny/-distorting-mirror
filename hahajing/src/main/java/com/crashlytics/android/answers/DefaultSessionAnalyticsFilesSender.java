package com.crashlytics.android.answers;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.List;

class DefaultSessionAnalyticsFilesSender extends AbstractSpiCall implements FilesSender {
    static final String FILE_CONTENT_TYPE = "application/vnd.crashlytics.android.events";
    static final String FILE_PARAM_NAME = "session_analytics_file_";
    private final String apiKey;

    public DefaultSessionAnalyticsFilesSender(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, String apiKey) {
        this(kit, protocolAndHostOverride, url, requestFactory, apiKey, HttpMethod.POST);
    }

    DefaultSessionAnalyticsFilesSender(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, String apiKey, HttpMethod method) {
        super(kit, protocolAndHostOverride, url, requestFactory, method);
        this.apiKey = apiKey;
    }

    public boolean send(List<File> files) {
        HttpRequest httpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), this.apiKey), files);
        CommonUtils.logControlled(Answers.getInstance().getContext(), "Sending " + files.size() + " analytics files to " + getUrl());
        int statusCode = httpRequest.code();
        CommonUtils.logControlled(Answers.getInstance().getContext(), "Response code for analytics file send is " + statusCode);
        return ResponseParser.parse(statusCode) == 0;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, String apiKey) {
        return request.header(AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE).header(AbstractSpiCall.HEADER_CLIENT_VERSION, Answers.getInstance().getVersion()).header(AbstractSpiCall.HEADER_API_KEY, apiKey);
    }

    private HttpRequest applyMultipartDataTo(HttpRequest request, List<File> files) {
        int i = 0;
        for (File file : files) {
            CommonUtils.logControlled(Answers.getInstance().getContext(), "Adding analytics session file " + file.getName() + " to multipart POST");
            request.part(FILE_PARAM_NAME + i, file.getName(), FILE_CONTENT_TYPE, file);
            i++;
        }
        return request;
    }
}
