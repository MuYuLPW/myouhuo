package com.example.admin.youhuo;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.admin.youhuo.utils.MarkAnimator;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.RadioButton choosebtboys;
    private android.widget.RadioButton choosebtgirls;
    private android.widget.RadioButton choosebtkids;
    private android.widget.RadioButton choosebtlife;
    private android.widget.RadioGroup rgroup;
    private android.widget.RelativeLayout activitychoose;
    private List<View> list;
    private long lastPressed;
    private View mark;
    private MarkAnimator markAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initView();
        initMark();
        initListener();

    }

    private void initMark() {
        markAnimator = new MarkAnimator(1000);
        markAnimator.setView(mark);
    }

    private void initListener() {
        for (View v : list) {
            v.setOnClickListener(this);
        }
    }

    private void initView() {
        this.activitychoose = (RelativeLayout) findViewById(R.id.activity_choose);
        this.rgroup = (RadioGroup) findViewById(R.id.rgroup);
        this.choosebtlife = (RadioButton) findViewById(R.id.choose_bt_life);
        this.choosebtkids = (RadioButton) findViewById(R.id.choose_bt_kids);
        this.choosebtgirls = (RadioButton) findViewById(R.id.choose_bt_girls);
        this.choosebtboys = (RadioButton) findViewById(R.id.choose_bt_boys);
        this.mark = (View) findViewById(R.id.mark);
        list = new ArrayList<>();
        list.add(choosebtboys);
        list.add(choosebtgirls);
        list.add(choosebtkids);
        list.add(choosebtlife);
    }

    @Override
    public void onClick(View v) {
        markAnimator.clearAnimation();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.main_in, R.anim.choose_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastPressed > 1000) {
            toast("双击退出");
        } else {
            markAnimator.clearAnimation();
            super.onBackPressed();
        }
        lastPressed = now;
    }
}
