package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class RightAlignedHorizontalScrollView extends HorizontalScrollView {
    public RightAlignedHorizontalScrollView(Context context) {
        super(context);
    }

    public RightAlignedHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            View child = getChildAt(0);
            if (child != null) {
                scrollTo(child.getHeight(), 0);
            }
        }
    }
}
