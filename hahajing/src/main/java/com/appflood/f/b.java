package com.appflood.f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

public final class b extends View {
    private int a = -1;
    private int b;
    private Paint c;
    private RectF d = new RectF();

    public b(Context context, int i) {
        super(context);
        this.b = i;
        this.c = new Paint();
        this.c.setStyle(Style.STROKE);
        this.c.setColor(this.a);
        this.c.setStrokeWidth((float) this.b);
        this.c.setTypeface(Typeface.DEFAULT_BOLD);
    }

    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            this.d.set((float) this.b, (float) this.b, (float) (getWidth() - this.b), (float) (getHeight() - this.b));
            canvas.drawRoundRect(this.d, 0.0f, 0.0f, this.c);
        } catch (Throwable th) {
        }
    }
}
