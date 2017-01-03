package com.example.admin.youhuo.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 2016/12/27.
 */

public class GouWuCheFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        TextView tv=new TextView(a);
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        tv.setText(getClass().getSimpleName());
        return tv;
    }
}
