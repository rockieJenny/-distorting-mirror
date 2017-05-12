package com.android.camera.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.gms.cast.TextTrackStyle;

public class TwoStateImageView extends ImageView {
    private final float DISABLED_ALPHA;
    private boolean mFilterEnabled;

    public TwoStateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.DISABLED_ALPHA = 0.4f;
        this.mFilterEnabled = true;
    }

    public TwoStateImageView(Context context) {
        this(context, null);
    }

    @TargetApi(11)
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.mFilterEnabled && VERSION.SDK_INT > 11) {
            if (enabled) {
                setAlpha(TextTrackStyle.DEFAULT_FONT_SCALE);
            } else {
                setAlpha(0.4f);
            }
        }
    }

    public void enableFilter(boolean enabled) {
        this.mFilterEnabled = enabled;
    }
}
