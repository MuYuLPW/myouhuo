package com.example.admin.youhuo.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.youhuo.R;
import com.example.admin.youhuo.event.GoToChooseEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/27.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private RadioGroup rb;
    private RadioButton shouye;
    private RadioButton fenlei;
    private RadioButton guang;
    private RadioButton gouwuche;
    private RadioButton wode;
    private List<View> list;
    private ShouYeFragment shouYeFragment;
    private FenLeiFragment fenLeiFragment;
    private GuangFragment guangFragment;
    private GouWuCheFragment gouWuCheFragment;
    private WoDeFragment woDeFragment;
    private int currentId;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        rb = (RadioGroup) inflate.findViewById(R.id.frag_main_rg);
        shouye = (RadioButton) inflate.findViewById(R.id.frag_main_shouye);
        fenlei = (RadioButton) inflate.findViewById(R.id.frag_main_fenlei);
        guang = (RadioButton) inflate.findViewById(R.id.frag_main_guang);
        gouwuche = (RadioButton) inflate.findViewById(R.id.frag_main_gouwuche);
        wode = (RadioButton) inflate.findViewById(R.id.frag_main_wode);
        shouye.setChecked(true);

        list = new ArrayList<>();
        list.add(shouye);
        list.add(fenlei);
        list.add(guang);
        list.add(gouwuche);
        list.add(wode);
        initListener();
        return inflate;
    }

    @Override
    public void initData() {
        initFragment();
    }

    private void initFragment() {
        shouYeFragment = new ShouYeFragment();
        fenLeiFragment = new FenLeiFragment();
        guangFragment = new GuangFragment();
        gouWuCheFragment = new GouWuCheFragment();
        woDeFragment = new WoDeFragment();
        getFragmentManager().beginTransaction().replace(R.id.frag_main,shouYeFragment).commit();
    }


    private void initListener() {
        for (int i=0;i<list.size();i++){
            list.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==currentId&& v.getId()==R.id.frag_main_shouye){
            EventBus.getDefault().post(new GoToChooseEvent());
        }
        if (v.getId()==currentId){
            Log.e("tag","xiangtong");
            return;
        }
        switch (v.getId()){
            case R.id.frag_main_shouye:
                getFragmentManager().beginTransaction().replace(R.id.frag_main,shouYeFragment).commit();
                break;
            case R.id.frag_main_fenlei:
                getFragmentManager().beginTransaction().replace(R.id.frag_main,fenLeiFragment).commit();
                break;
            case R.id.frag_main_guang:
                getFragmentManager().beginTransaction().replace(R.id.frag_main,guangFragment).commit();
                break;
            case R.id.frag_main_gouwuche:
                getFragmentManager().beginTransaction().replace(R.id.frag_main,gouWuCheFragment).commit();
                break;
            case R.id.frag_main_wode:
                getFragmentManager().beginTransaction().replace(R.id.frag_main,woDeFragment).commit();
                break;
        }
        currentId=v.getId();
    }
}
