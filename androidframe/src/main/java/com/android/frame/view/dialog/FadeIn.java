package com.android.frame.view.dialog;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 正常显示
 */
public class FadeIn extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(mDuration)

        );
    }
}