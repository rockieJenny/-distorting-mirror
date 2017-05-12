package com.appflood.f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import com.appflood.c.d;
import com.appflood.e.b;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public final class i extends a {
    private int d = 15;
    private int e = 15;
    private int f = 15;
    private float g = TextTrackStyle.DEFAULT_FONT_SCALE;
    private Paint h;
    private b i = new b(182.0f, 89.0f);
    private b j = new b(182.0f, 32.0f);
    private int k = 20;
    private boolean l;
    private int m = 5;

    public i(Context context, float f, boolean z) {
        super(context);
        this.g = f;
        this.l = z;
        if (this.l) {
            this.d = (int) (this.g * 15.0f);
            this.e = this.d;
            this.f = (int) (this.g * 15.0f);
            this.i.a(f);
            this.j.a(f);
            this.m = (int) (((float) this.m) * f);
            this.k = (int) (20.0f * f);
        } else {
            this.d = (int) (11.0f * this.g);
            this.e = (int) (this.g * 8.0f);
            this.f = (int) (this.g * 8.0f);
            this.i = new b(155.0f, 76.0f);
            this.i.a(f);
            this.j = new b(155.0f, 25.0f);
            this.j.a(f);
            this.m = (int) (((float) this.m) * f);
            this.k = (int) (15.0f * f);
        }
        this.h = new Paint();
    }

    public final void a(String str, String str2) {
        this.a = d.d;
        this.b = str2;
        postInvalidate();
    }

    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (this.l) {
                this.h.setColor(-526083);
                this.h.clearShadowLayer();
                this.h.setStyle(Style.FILL);
                canvas.drawRoundRect(new RectF(new Rect(this.m + 1, this.m + 1, (getWidth() - 2) - this.m, (getHeight() - 2) - this.m)), 3.0f, 3.0f, this.h);
            }
            RectF rectF = new RectF((float) (this.f + this.m), (float) (this.d + this.m), (float) ((this.f + ((int) this.i.a)) + this.m), (float) ((this.d + ((int) this.i.b)) + this.m));
            if (this.c != null) {
                canvas.drawBitmap(this.c, null, rectF, null);
            }
            GradientDrawable gradientDrawable = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{-2863104, -885504});
            gradientDrawable.setCornerRadius(3.0f);
            int i = this.f + this.m;
            int i2 = ((this.e + this.d) + ((int) this.i.b)) + this.m;
            gradientDrawable.setBounds(new Rect(i, i2, ((int) this.j.a) + i, ((int) this.j.b) + i2));
            gradientDrawable.draw(canvas);
            if (this.a != null) {
                this.h.setColor(-1052172);
                this.h.setAntiAlias(true);
                this.h.setTypeface(Typeface.DEFAULT_BOLD);
                this.h.setTextAlign(Align.CENTER);
                this.h.setShadowLayer(2.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION, -5939712);
                this.h.setTextSize((float) this.k);
                FontMetrics fontMetrics = this.h.getFontMetrics();
                canvas.drawText(this.a, (float) gradientDrawable.getBounds().centerX(), (((fontMetrics.bottom - fontMetrics.top) / 2.0f) - fontMetrics.bottom) + ((float) gradientDrawable.getBounds().centerY()), this.h);
            }
        } catch (Throwable th) {
        }
    }
}
