package com.flurry.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.cast.TextTrackStyle;

public class ep {
    private static int a;
    private static int b;
    private Path c = null;
    private PathShape d = null;
    private ShapeDrawable e = null;
    private TextView f = null;
    private int g = 0;
    private float h = 0.0f;
    private RectF i = null;
    private final float j = -90.0f;

    public ep(Context context, int i, int i2) {
        a = hn.b(2);
        b = hn.b(1);
        this.g = a(i, i2);
        a(context);
    }

    public ep() {
        a = 3;
        b = 1;
    }

    public void a(int i) {
        if (this.f != null) {
            e(i);
            f(i);
        }
    }

    public void b(final int i) {
        this.h = 360.0f / ((float) (i / 1000));
        fp.a().a(new Runnable(this) {
            final /* synthetic */ ep b;

            public void run() {
                this.b.d(i);
            }
        });
    }

    private void a(Context context) {
        this.f = new TextView(context);
        this.f.setTextColor(-1);
        this.f.setTypeface(Typeface.MONOSPACE);
        this.f.setTextSize(1, 12.0f);
        this.f.setGravity(17);
    }

    public View a() {
        return this.f;
    }

    @SuppressLint({"NewApi"})
    private void d(int i) {
        Drawable c = c(i);
        if (VERSION.SDK_INT >= 16) {
            this.f.setBackground(c);
        } else {
            this.f.setBackgroundDrawable(c);
        }
    }

    public Drawable c(int i) {
        if (null != null) {
            return null;
        }
        this.i = new RectF();
        this.i.set((float) a, (float) a, (float) (this.g - a), (float) (this.g - a));
        this.c = new Path();
        this.c.arcTo(this.i, -90.0f, (((float) (-i)) * this.h) + TextTrackStyle.DEFAULT_FONT_SCALE, false);
        this.d = new PathShape(this.c, (float) this.g, (float) this.g);
        this.e = new ShapeDrawable(this.d);
        this.e.setIntrinsicHeight(this.g * 2);
        this.e.setIntrinsicWidth(this.g * 2);
        this.e.getPaint().setStyle(Style.STROKE);
        this.e.getPaint().setColor(-1);
        this.e.getPaint().setStrokeWidth((float) b);
        this.e.getPaint().setAntiAlias(true);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(ViewCompat.MEASURED_STATE_MASK);
        gradientDrawable.setShape(1);
        gradientDrawable.setAlpha(178);
        return new LayerDrawable(new Drawable[]{gradientDrawable, this.e});
    }

    private void e(final int i) {
        fp.a().a(new Runnable(this) {
            final /* synthetic */ ep b;

            public void run() {
                this.b.d(i);
            }
        });
    }

    private void f(final int i) {
        fp.a().a(new Runnable(this) {
            final /* synthetic */ ep b;

            public void run() {
                this.b.f.setText(Integer.toString(i));
            }
        });
    }

    private int a(int i, int i2) {
        if (i < i2) {
            return i / 2;
        }
        return i2 / 2;
    }
}
