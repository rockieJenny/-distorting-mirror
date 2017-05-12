package com.inmobi.monetization.internal;

public final class InvalidManifestConfigException extends Exception {
    public static final int MISSING_ACCESS_NETWORK_STATE_PERMISSION = -9;
    public static final int MISSING_ACTIVITY_DECLARATION = -2;
    public static final int MISSING_CONFIG_CHANGES = -3;
    public static final int MISSING_CONFIG_KEYBOARD = -4;
    public static final int MISSING_CONFIG_KEYBOARDHIDDEN = -5;
    public static final int MISSING_CONFIG_ORIENTATION = -6;
    public static final int MISSING_CONFIG_SCREENSIZE = -7;
    public static final int MISSING_CONFIG_SMALLEST_SCREENSIZE = -8;
    public static final int MISSING_INTERNET_PERMISSION = -1;
    private static final long serialVersionUID = 1;
    private int a;

    InvalidManifestConfigException(int i) {
        this.a = i;
    }

    public int getExceptionCode() {
        return this.a;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("IMConfigException : ");
        switch (this.a) {
            case MISSING_ACCESS_NETWORK_STATE_PERMISSION /*-9*/:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_ACCESS_NETWORK_PERMISSION);
                break;
            case MISSING_CONFIG_SMALLEST_SCREENSIZE /*-8*/:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_CONFIG_SMALLEST_SCREENSIZE);
                break;
            case MISSING_CONFIG_SCREENSIZE /*-7*/:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_CONFIG_SCREENSIZE);
                break;
            case MISSING_CONFIG_ORIENTATION /*-6*/:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_CONFIG_ORIENTATION);
                break;
            case -5:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_CONFIG_KEYBOARDHIDDEN);
                break;
            case -4:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_CONFIG_KEYBOARD);
                break;
            case -3:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_CONFIG_CHANGES);
                break;
            case -2:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_ACTIVITY_DECLARATION);
                break;
            case -1:
                stringBuffer.append(InvalidManifestErrorMessages.MSG_MISSING_INTERNET_PERMISSION);
                break;
        }
        return stringBuffer.toString();
    }
}
