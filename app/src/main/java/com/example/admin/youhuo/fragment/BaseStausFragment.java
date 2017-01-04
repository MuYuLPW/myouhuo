package com.example.admin.youhuo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.youhuo.R;
import com.example.admin.youhuo.view.StatusLayout;

/**
 * Created by admin on 2017/1/3.
 */

public  abstract class BaseStausFragment extends BaseFragment implements View.OnClickListener {

    private StatusLayout statusLayout;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        statusLayout = (StatusLayout) inflater.inflate(R.layout.fragment_base_staus, container, false);
        initChildView();
        statusLayout.setOnClickListener(this);
        return statusLayout;
    }

    protected abstract void initChildView();
    public void setChildView(View v){
        statusLayout.addView(v);
    }
    public void switchToNormal(){
        statusLayout.switchToNomarl();
    }
    public void switchToError(){
        statusLayout.switchToError();
    }
    public void switchToLoad(){
        statusLayout.switchToLoad();
    }

    @Override
    public void onClick(View v) {
        onreLoad();
    }

    protected abstract void onreLoad();
}
