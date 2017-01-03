package com.example.admin.youhuo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by admin on 2016/12/27.
 */

public class NoSlideViewPager extends FrameLayout {

    private Scroller scroller;

    public NoSlideViewPager(Context context) {
        this(context,null);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i=0;i<getChildCount();i++){
            View childAt = getChildAt(i);
            childAt.layout(i*getMeasuredWidth(),0,(i+1)*getMeasuredWidth(),getMeasuredHeight());
        }
    }
    public void setCurrentItem(int currentItem){
        if (currentItem !=getCurrentItem()&& scroller.isFinished()){
            int i=currentItem-getCurrentItem();
            scroller.startScroll(getScrollX(),0,i*getWidth(),0,500);
            postInvalidate();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            int currX = scroller.getCurrX();
            scrollTo(currX,0);
            invalidate();
        }
    }

    private int getCurrentItem() {
        return getScrollX()/getMeasuredWidth();
    }
}
