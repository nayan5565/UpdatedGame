package com.example.nayan.game3.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by JEWEL on 8/21/2016.
 */
public class MyAnimation {
    public static void rotation(View view, boolean isLeft) {
        view.setPivotX(view.getX() + view.getWidth() / 2);
        view.setPivotY(view.getY() + view.getHeight() / 2);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationY", 0, 180);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        animator.setDuration(2000);
        animator.start();
    }
}
