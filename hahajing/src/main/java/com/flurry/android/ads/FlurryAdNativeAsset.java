package com.flurry.android.ads;

import android.content.Context;
import android.view.View;
import com.flurry.android.impl.ads.protocol.v13.NativeAsset;
import com.flurry.sdk.i;

public final class FlurryAdNativeAsset {
    private NativeAsset a;
    private int b;

    FlurryAdNativeAsset(NativeAsset nativeAsset, int i) {
        if (nativeAsset == null) {
            throw new IllegalArgumentException("asset cannot be null");
        }
        this.a = nativeAsset;
        this.b = i;
    }

    public String getName() {
        return this.a.name;
    }

    public String getValue() {
        switch (this.a.type) {
            case STRING:
                return this.a.value;
            case IMAGE:
                return i.a().j().a(this.a, this.b);
            default:
                return null;
        }
    }

    public FlurryAdNativeAssetType getType() {
        switch (this.a.type) {
            case STRING:
                return FlurryAdNativeAssetType.TEXT;
            case IMAGE:
                return FlurryAdNativeAssetType.IMAGE;
            default:
                return null;
        }
    }

    public void loadAssetIntoView(View view) {
        i.a().j().a(this.a, view, this.b);
    }

    public View getAssetView(Context context) {
        return i.a().j().a(context, this.a, this.b);
    }
}
