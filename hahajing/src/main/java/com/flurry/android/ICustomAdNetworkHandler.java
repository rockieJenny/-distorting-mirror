package com.flurry.android;

import android.content.Context;

public interface ICustomAdNetworkHandler {
    AdNetworkView getAdFromNetwork(Context context, AdCreative adCreative, String str);
}
