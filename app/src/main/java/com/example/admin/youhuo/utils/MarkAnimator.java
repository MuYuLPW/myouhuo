package com.example.admin.youhuo.utils;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.admin.youhuo.WelComeActivity;
import com.example.admin.youhuo.listener.SimpleAnimatorListener;

/**
 * Created by admin on 2016/12/26.
 */

public class MarkAnimator implements ValueAnimator.AnimatorUpdateListener {
    private ValueAnimator markAnimator;
    private View view;
    private OnAnimationEnd listener;

    public MarkAnimator(int duration) {
        markAnimator = ValueAnimator.ofInt(Color.parseColor("#cc333333"), Color.parseColor("#00333333"));
        markAnimator.setEvaluator(new ArgbEvaluator());
        markAnimator.setDuration(duration);
        markAnimator.addUpdateListener(this);
        markAnimator.setInterpolator(new LinearInterpolator());
        markAnimator.addListener(new MarkAnimatorListener());
        markAnimator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (view!=null){
            view.setBackgroundColor((Integer) animation.getAnimatedValue());
        }
    }
    public void setView(View view){
        this.view=view;
    }
    class MarkAnimatorListener extends SimpleAnimatorListener {
        @Override
        public void onAnimationEnd(Animator animation) {
           if (listener!=null){
               listener.onAnimationEnd(animation);
           }
        }
    }
    public interface OnAnimationEnd{
        void onAnimationEnd(Animator animation);
    }
    public void setOnAnimationEnd(OnAnimationEnd listener){
        this.listener=listener;
    }
    public void clearAnimation(){
        markAnimator.cancel();
    }
}
