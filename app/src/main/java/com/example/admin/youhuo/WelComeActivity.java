package com.example.admin.youhuo;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.youhuo.listener.SimpleAnimatorListener;
import com.example.admin.youhuo.utils.MarkAnimator;

public class WelComeActivity extends BaseActivity implements ValueAnimator.AnimatorUpdateListener {

    private android.view.View mark;
    private android.widget.RelativeLayout activitywelcome;
//    private ValueAnimator markAnimator;
    private android.widget.ImageView iv;
    private ObjectAnimator scaleAnimator;
    private boolean isCancel;
    private MarkAnimator markAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);
        this.iv = (ImageView) findViewById(R.id.iv);
        this.activitywelcome = (RelativeLayout) findViewById(R.id.activity_wel_come);
        this.mark = (View) findViewById(R.id.mark);
        initMark();
        initScaleAnimator();
    }

    private void initScaleAnimator() {
        iv.setScaleX(1.1f);
        iv.setScaleY(1.1f);
        PropertyValuesHolder proX=PropertyValuesHolder.ofFloat("scaleX",1.1f,1.0f);
        PropertyValuesHolder proY=PropertyValuesHolder.ofFloat("scaleY",1.1f,1.0f);
        scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(iv,proX,proY);
        scaleAnimator.setDuration(3000);
        scaleAnimator.setInterpolator(new LinearInterpolator());
        scaleAnimator.setEvaluator(new FloatEvaluator());
        scaleAnimator.addListener(new ScaleAnimatorListener());
    }

    private void initMark() {
//        markAnimator = ValueAnimator.ofInt(Color.parseColor("#cc333333"), Color.parseColor("#00333333"));
//        markAnimator.setEvaluator(new ArgbEvaluator());
//        markAnimator.setDuration(3000);
//        markAnimator.addUpdateListener(this);
//        markAnimator.setInterpolator(new LinearInterpolator());
//        markAnimator.addListener(new MarkAnimatorListener());
//        markAnimator.start();
        markAnimator = new MarkAnimator(3000);
        markAnimator.setView(mark);
        markAnimator.setOnAnimationEnd(new MarkAnimator.OnAnimationEnd() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isCancel) {
                    scaleAnimator.start();
                }
            }
        });
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mark.setBackgroundColor((Integer) animation.getAnimatedValue());
    }
//    class MarkAnimatorListener extends SimpleAnimatorListener{
//        @Override
//        public void onAnimationEnd(Animator animation) {
//            Log.e("tag","取消mark");
//            if (!isCancel) {
//                scaleAnimator.start();
//            }
//        }
//    }
    class ScaleAnimatorListener extends SimpleAnimatorListener{
        @Override
        public void onAnimationEnd(Animator animation) {
            Log.e("tag","取消scale");
            if (!isCancel) {
                gotoChooseActivity();
            }
        }
    }

    @Override
    public void onBackPressed() {
        clearAnimation();
        super.onBackPressed();
    }
//取消动画
    private void clearAnimation() {
        isCancel=true;
//        markAnimator.cancel();
        markAnimator.clearAnimation();
        scaleAnimator.cancel();
    }

    //跳转到下一个界面
    private void gotoChooseActivity() {
        startActivity(new Intent(this,ChooseActivity.class));
        finish();
    }
    public void click(View v){
        clearAnimation();
        gotoChooseActivity();
    }
}
