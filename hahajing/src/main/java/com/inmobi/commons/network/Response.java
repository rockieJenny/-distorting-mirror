package com.inmobi.commons.network;

import java.util.List;
import java.util.Map;

public class Response {
    String a = null;
    int b = 0;
    Map<String, List<String>> c = null;
    ErrorCode d;

    public Response(ErrorCode errorCode) {
        this.d = errorCode;
    }

    public Response(String str, int i, Map<String, List<String>> map) {
        this.a = str;
        this.b = i;
        this.c = map;
    }

    public int getStatusCode() {
        return this.b;
    }

    public String getResponseBody() {
        return this.a;
    }

    public ErrorCode getError() {
        return this.d;
    }

    public Map<String, List<String>> getHeaders() {
        return this.c;
    }
}
