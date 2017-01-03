package com.example.admin.youhuo.view;

/**
 * Created by admin on 2017/1/2.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * 画廊效果的viewPager
 */
public class GralleyPager extends ViewPager{
    public Set<View> set=new HashSet<>();

    public GralleyPager(Context context) {
        super(context);
        init();
    }

    public GralleyPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                setPadding(getWidth() / 4, getHeight() / 12, getWidth() / 4, getHeight() / 12);
                setClipToPadding(false);
                setPageMargin(getWidth() / 12);

            }
        });
        initTranslation();
    }

    private void initTranslation() {
        addOnPageChangeListener(new SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                View currentChild = (View) getAdapter().instantiateItem(null, position);
                View lastChild = (View) getAdapter().instantiateItem(null, position + 1);
                currentChild.setScaleY(1.2f - Math.min(1, positionOffset * 5f) * 0.2f);
                currentChild.setScaleX(1.2f - Math.min(1, positionOffset * 5f) * 0.2f);
                currentChild.setAlpha(1 - Math.min(1, positionOffset * 5f) * 0.5f);
                if (positionOffset > 0.8f) {
                    lastChild.setScaleY(1.0f + (5 * positionOffset - 4) * 0.2f);
                    lastChild.setScaleX(1.0f + (5 * positionOffset - 4) * 0.2f);
                    lastChild.setAlpha(0.5f + (5 * positionOffset - 4) * 0.5f);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                View curent = (View) getAdapter().instantiateItem(null, position);
                updateView(curent);
            }
        });
    }

    private void updateView(View curent) {
        for(View view:set){
            if(curent==view) {
                view.setScaleY(1.2f);
                view.setScaleX(1.2f);
                view.setAlpha(1.0f);
            }else{
                view.setAlpha(0.5f);
                view.setScaleY(1.0f);
                view.setScaleX(1.0f);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        set.clear();
        int i=0;
        int pre=0;
        PagerAdapter adapter = getAdapter();
        if (adapter!=null){
            while(true){
                View view = (View) adapter.instantiateItem(null, 0);
                set.add(view);
                view.setAlpha(0.5f);
                if(pre==set.size()){
                    break;
                }
                i++;
                pre++;
            }
        }


    }
}
