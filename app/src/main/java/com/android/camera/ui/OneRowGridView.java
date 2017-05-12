package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class OneRowGridView extends GridView {
    private int mInternalRequestedColumnWidth;

    public OneRowGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColumnWidth(int columnWidth) {
        super.setColumnWidth(10);
        if (this.mInternalRequestedColumnWidth != columnWidth) {
            this.mInternalRequestedColumnWidth = columnWidth;
            requestLayout();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mInternalRequestedColumnWidth != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.mInternalRequestedColumnWidth * (getAdapter() == null ? 0 : getAdapter().getCount()), 1073741824);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
