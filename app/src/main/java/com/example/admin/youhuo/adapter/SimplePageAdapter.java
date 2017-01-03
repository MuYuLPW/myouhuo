package com.example.admin.youhuo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by admin on 2017/1/2.
 */

public class SimplePageAdapter<T extends View> extends PagerAdapter {
    public SimplePageAdapter(List<T> list) {
        this.list = list;
    }

    List<T> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T t = list.get(position);
        if (container!=null){
            container.addView(t);
        }
        return t;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
