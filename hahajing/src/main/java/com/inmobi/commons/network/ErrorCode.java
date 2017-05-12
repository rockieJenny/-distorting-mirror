package com.inmobi.commons.network;

public enum ErrorCode {
    INVALID_REQUEST("Invalid request"),
    INTERNAL_ERROR("An internal error occurred while fetching"),
    CONNECTION_ERROR("Socket timeout exception"),
    NETWORK_ERROR("Network failure. Check your connection");
    
    private String a;

    private ErrorCode(String str) {
        this.a = str;
    }

    public String toString() {
        return this.a;
    }

    public void setMessage(String str) {
        this.a = str;
    }
}
