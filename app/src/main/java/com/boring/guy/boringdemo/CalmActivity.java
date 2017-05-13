package com.boring.guy.boringdemo;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by rockie on 2017/5/13.
 */
public class CalmActivity extends Activity {

    private ImageView mFerris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivity_calm);

        mFerris = (ImageView) findViewById(R.id.ferris);
        ValueAnimator mValueAnimation = new ValueAnimator();
        mValueAnimation.setInterpolator(new LinearInterpolator());
        mValueAnimation.setRepeatCount(-1);
        mValueAnimation.setDuration(20000);
        mValueAnimation.setObjectValues(0f, 360f);
        mValueAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float angle = (Float) animation.getAnimatedValue();
                mFerris.setRotation(angle);
            }
        });
        mValueAnimation.start();
    }
}
