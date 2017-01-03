package com.example.admin.youhuo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2016/12/27.
 */

public abstract class BaseFragment extends Fragment {

    private View rootView;
    public Activity a;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null) {
            rootView = initView(inflater, container);
            initData();
            initAdapter();
        }
        return rootView;
    }

    public void initAdapter() {

    }

    public void initData() {

    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);
}
