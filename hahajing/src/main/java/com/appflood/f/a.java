package com.appflood.f;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

public class a extends View {
    protected String a;
    protected String b;
    protected Bitmap c;

    public a(Context context) {
        super(context);
    }

    public final void a(Bitmap bitmap) {
        this.c = bitmap;
        postInvalidate();
    }

    public void a(String str, String str2) {
        this.a = str;
        this.b = str2;
        postInvalidate();
    }
}
