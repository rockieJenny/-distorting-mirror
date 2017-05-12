package com.appflood.f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.View;
import com.appflood.c.d;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public final class h extends View {
    private Paint a;
    private Paint b;
    private Paint c;
    private float d;
    private GradientDrawable e = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{-15254166, -12543784});
    private int f = 25;

    public h(Context context, float f) {
        super(context);
        this.f = (int) (((float) this.f) * f);
        this.b = new Paint();
        this.b.setTextAlign(Align.CENTER);
        this.b.setTextSize((float) this.f);
        this.b.setAntiAlias(true);
        this.b.setTypeface(Typeface.DEFAULT_BOLD);
        this.b.setColor(-1052172);
        this.b.setShadowLayer(2.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION, -12558972);
        FontMetrics fontMetrics = this.b.getFontMetrics();
        this.d = ((fontMetrics.bottom - fontMetrics.top) / 2.0f) - fontMetrics.bottom;
        this.c = new Paint();
        this.c.setColor(-16767916);
        this.a = new Paint();
        this.a.setColor(-16379600);
    }

    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.a);
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) (getHeight() + 1), this.c);
            this.e.setBounds(0, 0, getWidth(), getHeight());
            this.e.draw(canvas);
            canvas.drawText(d.b, (float) (getWidth() / 2), ((float) (getHeight() / 2)) + this.d, this.b);
        } catch (Throwable th) {
        }
    }
}
