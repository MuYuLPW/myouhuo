package com.example.admin.youhuo.view;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by admin on 2016/12/27.
 */

public class NoSlidingPaneLayout extends SlidingPaneLayout {
    public NoSlidingPaneLayout(Context context) {
        super(context);
    }

    public NoSlidingPaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isOpen()) {
            return super.onInterceptTouchEvent(ev);
        }else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
