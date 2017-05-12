package com.inmobi.commons.thinICE.icedatacollector;

public class ThinICEConfigSettings {
    public static final int CELL_OP_FLAG_DISABLE_CURRENT_DETAILS = 2;
    public static final int CELL_OP_FLAG_DISABLE_SIM_DETAILS = 1;
    public static final int WIFI_FLAG_DISABLE_NOMAP_EXCLUSION = 2;
    public static final int WIFI_FLAG_DISABLE_SSID_COLLECTION = 1;
    private boolean a = true;
    private boolean b = true;
    private boolean c = true;
    private boolean d = true;
    private boolean e = true;
    private boolean f = true;
    private boolean g = true;
    private long h = 60000;
    private long i = 3000;
    private int j = 50;
    private int k = 0;
    private int l = 0;

    public ThinICEConfigSettings(ThinICEConfigSettings thinICEConfigSettings) {
        this.a = thinICEConfigSettings.a;
        this.b = thinICEConfigSettings.b;
        this.c = thinICEConfigSettings.c;
        this.d = thinICEConfigSettings.d;
        this.e = thinICEConfigSettings.e;
        this.f = thinICEConfigSettings.f;
        this.g = thinICEConfigSettings.g;
        this.h = thinICEConfigSettings.h;
        this.i = thinICEConfigSettings.i;
        this.j = thinICEConfigSettings.j;
        this.k = thinICEConfigSettings.k;
        this.l = thinICEConfigSettings.l;
    }

    public boolean isEnabled() {
        return this.a;
    }

    public ThinICEConfigSettings setEnabled(boolean z) {
        this.a = z;
        return this;
    }

    public boolean isSampleCellOperatorEnabled() {
        return this.b;
    }

    public ThinICEConfigSettings setSampleCellOperatorEnabled(boolean z) {
        this.b = z;
        return this;
    }

    public boolean isSampleCellEnabled() {
        return this.c;
    }

    public ThinICEConfigSettings setSampleCellEnabled(boolean z) {
        this.c = z;
        return this;
    }

    public boolean isSampleConnectedWifiEnabled() {
        return this.d;
    }

    public ThinICEConfigSettings setSampleConnectedWifiEnabled(boolean z) {
        this.d = z;
        return this;
    }

    public boolean isSampleLocationEnabled() {
        return this.e;
    }

    public ThinICEConfigSettings setSampleLocationEnabled(boolean z) {
        this.e = z;
        return this;
    }

    public boolean isSampleVisibleWifiEnabled() {
        return this.f;
    }

    public boolean isSampleVisibleCellTowerEnabled() {
        return this.g;
    }

    public ThinICEConfigSettings setSampleVisibleWifiEnabled(boolean z) {
        this.f = z;
        return this;
    }

    public ThinICEConfigSettings setSampleVisibleCellTowerEnabled(boolean z) {
        this.g = z;
        return this;
    }

    public long getSampleInterval() {
        return this.h;
    }

    public ThinICEConfigSettings setSampleInterval(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("Sample interval must be greater than 0");
        }
        this.h = j;
        return this;
    }

    public long getStopRequestTimeout() {
        return this.i;
    }

    public ThinICEConfigSettings setStopRequestTimeout(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("Stop request timeout must be greater than 0");
        }
        this.i = j;
        return this;
    }

    public int getSampleHistorySize() {
        return this.j;
    }

    public ThinICEConfigSettings setSampleHistorySize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Sample history size must be greater than 0");
        }
        this.j = i;
        return this;
    }

    public int getWifiFlags() {
        return this.k;
    }

    public ThinICEConfigSettings setWifiFlags(int i) {
        this.k = i;
        return this;
    }

    public int getCellOpFlags() {
        return this.l;
    }

    public ThinICEConfigSettings setCellOpFlags(int i) {
        this.l = i;
        return this;
    }

    public static boolean bitTest(int i, int i2) {
        return (i & i2) == i2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName()).append("[");
        stringBuilder.append("mEnabled=").append(this.a).append(", ");
        stringBuilder.append("mSampleCellOperatorEnabled=").append(this.b).append(", ");
        stringBuilder.append("mSampleCellEnabled=").append(this.c).append(", ");
        stringBuilder.append("mSampleConnectedWifiEnabled=").append(this.d).append(", ");
        stringBuilder.append("mSampleLocationEnabled=").append(this.e).append(", ");
        stringBuilder.append("mSampleVisibleWifiEnabled=").append(this.f).append(", ");
        stringBuilder.append("mSampleVisibleCellTowerEnabled=").append(this.g).append(", ");
        stringBuilder.append("mSampleInterval=").append(this.h).append(", ");
        stringBuilder.append("mStopRequestTimeout=").append(this.i).append(", ");
        stringBuilder.append("mWifiFlags=").append(Integer.toBinaryString(this.k)).append(", ");
        stringBuilder.append("mCellOpFlags=").append(Integer.toBinaryString(this.l));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
