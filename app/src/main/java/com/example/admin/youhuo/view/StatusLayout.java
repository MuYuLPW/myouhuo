package com.example.admin.youhuo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.admin.youhuo.R;

/**
 * Created by admin on 2017/1/2.
 */

public class StatusLayout extends FrameLayout implements View.OnClickListener {

    private View errorView;
    private View loadView;
    private AnimationDrawable drawable;
    private View normalView;
    private OnErrorViewClickListener listener;

    public StatusLayout(Context context) {
        super(context);
        init();
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initLoad();
        initError();
    }

    private void initError() {
        errorView = View.inflate(getContext(), R.layout.layout_error, null);
        errorView.setBackgroundColor(Color.WHITE);
        errorView.setOnClickListener(this);
        addView(errorView);
    }

    private void initLoad() {
        loadView = View.inflate(getContext(), R.layout.layout_load, null);
        ImageView iv = (ImageView) loadView.findViewById(R.id.loadIv);
        iv.setImageResource(R.drawable.loading);
        loadView.setBackgroundColor(Color.WHITE);
        drawable = (AnimationDrawable) iv.getDrawable();
        addView(loadView);
    }
//errorView的点击监听
    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick();
        }
    }

    @Override
    public void addView(View child) {
        int childCount = getChildCount();
        if (childCount>2){
            throw new RuntimeException("只能添加一个孩子");
        }
        normalView = child;
        super.addView(child);
    }
    public void switchToNomarl(){
        drawable.stop();
        removeView(normalView);
        addView(normalView,getChildCount());
    }
    public void switchToLoad(){
        removeView(loadView);
        addView(loadView,getChildCount());
        loadView.post(new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        });
    }
    public void switchToError(){
        drawable.stop();
        removeView(errorView);
        addView(errorView,getChildCount());
    }
    public interface OnErrorViewClickListener{
        void onClick();
    }
    public void setOnErrorViewClickListener(OnErrorViewClickListener listener){
        this.listener=listener;
    }
}
