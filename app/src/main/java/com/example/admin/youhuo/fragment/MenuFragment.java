package com.example.admin.youhuo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.admin.youhuo.R;
import com.example.admin.youhuo.view.NoSlideViewPager;

/**
 * Created by admin on 2016/12/27.
 */

public class MenuFragment extends BaseFragment {

    private ListView lift;
    private ListView right;
    private ImageView back;
    private NoSlideViewPager inflate;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        inflate = (NoSlideViewPager) inflater.inflate(R.layout.fragment_menu, container, false);
        lift = (ListView) inflate.findViewById(R.id.lv_lift);
        back = (ImageView) inflate.findViewById(R.id.iv_back);
        initListener();
        return inflate;
    }

    private void initListener() {
        lift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==parent.getCount()-1){
                    inflate.setCurrentItem(1);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflate.setCurrentItem(0);
            }
        });
    }

}
