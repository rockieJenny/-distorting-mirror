package com.givewaygames.camera.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import com.givewaygames.goofyglass.R;

public class RandomButton extends View {
    private RectF oval;
    private Paint paint;
    private Path path;
    String random;
    private float textPad;

    public RandomButton(Context context) {
        super(context);
        init();
    }

    public RandomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RandomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        fixLayerType();
        this.random = getContext().getResources().getString(R.string.random);
        this.paint = new Paint(1);
        this.paint.setStyle(Style.FILL_AND_STROKE);
        this.paint.setColor(-1);
        this.paint.setTextSize(getResources().getDimension(R.dimen.random_text_size));
        this.textPad = getContext().getResources().getDimension(R.dimen.random_text_padding);
    }

    @TargetApi(11)
    private void fixLayerType() {
        if (VERSION.SDK_INT >= 11 && VERSION.SDK_INT < 16) {
            setLayerType(1, null);
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        innerOnLayout(right - left, bottom - top);
    }

    private void innerOnLayout(int width, int height) {
        this.path = new Path();
        this.oval = new RectF(this.textPad, this.textPad, ((float) width) - this.textPad, ((float) height) - this.textPad);
        this.path.addArc(this.oval, 90.0f, -359.0f);
    }

    protected void onDraw(Canvas canvas) {
        if (this.oval == null) {
            innerOnLayout(getWidth(), getHeight());
        }
        String text = this.random;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            builder.append(text.charAt(i));
            if (i + 1 < text.length()) {
                builder.append(" ");
            }
        }
        String finalText = builder.toString();
        float rotateAmount = ((this.paint.measureText(finalText) / ((float) (3.141592653589793d * ((double) this.oval.width())))) * 360.0f) / 2.0f;
        canvas.save();
        canvas.rotate(rotateAmount, ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
        canvas.drawTextOnPath(finalText, this.path, 0.0f, 0.0f, this.paint);
        canvas.restore();
    }
}
