package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso.LoadedFrom;

class FetchAction extends Action<Object> {
    private final Object target = new Object();

    FetchAction(Picasso picasso, Request data, boolean skipCache, String key, Object tag) {
        super(picasso, null, data, skipCache, false, 0, null, key, tag);
    }

    void complete(Bitmap result, LoadedFrom from) {
    }

    public void error() {
    }

    Object getTarget() {
        return this.target;
    }
}
