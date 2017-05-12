package it.sephiroth.android.library.util.v14;

import android.annotation.TargetApi;
import android.view.View;
import it.sephiroth.android.library.util.ViewHelperFactory.ViewHelperDefault;

public class ViewHelper14 extends ViewHelperDefault {
    public ViewHelper14(View view) {
        super(view);
    }

    @TargetApi(14)
    public void setScrollX(int value) {
        this.view.setScrollX(value);
    }

    @TargetApi(11)
    public boolean isHardwareAccelerated() {
        return this.view.isHardwareAccelerated();
    }
}
