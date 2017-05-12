package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class StackLayout extends ViewGroup {
    private static final String TAG = "StackLayout";

    public StackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(child.getMeasuredWidth(), widthMeasureSpec), resolveSize(child.getMeasuredHeight(), heightMeasureSpec));
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = super.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                child.layout(0, 0, r - l, b - t);
            }
        }
    }
}
