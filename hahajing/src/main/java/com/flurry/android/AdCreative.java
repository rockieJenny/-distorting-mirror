package com.flurry.android;

public class AdCreative {
    public static final String kAlignmentBottom = "bottom";
    public static final String kAlignmentCenter = "center";
    public static final String kAlignmentLeft = "left";
    public static final String kAlignmentMiddle = "middle";
    public static final String kAlignmentRight = "right";
    public static final String kAlignmentTop = "top";
    public static final String kFixBoth = "both";
    public static final String kFixHeight = "height";
    public static final String kFixNone = "none";
    public static final String kFixWidth = "width";
    public static final String kFormatBanner = "banner";
    public static final String kFormatCustom = "custom";
    public static final String kFormatTakeover = "takeover";
    private int a;
    private int b;
    private String c;
    private String d;
    private String e;

    public AdCreative(int i, int i2, String str, String str2, String str3) {
        this.a = i;
        this.b = i2;
        this.c = str;
        this.d = str2;
        this.e = str3;
    }

    public int getHeight() {
        return this.a;
    }

    public int getWidth() {
        return this.b;
    }

    public String getFormat() {
        return this.c;
    }

    public String getFix() {
        return this.d;
    }

    public String getAlignment() {
        return this.e;
    }
}
