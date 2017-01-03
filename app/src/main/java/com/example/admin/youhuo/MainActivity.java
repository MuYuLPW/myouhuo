package com.example.admin.youhuo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.admin.youhuo.event.GoToChooseEvent;
import com.example.admin.youhuo.view.NoSlidingPaneLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private com.example.admin.youhuo.view.NoSlidingPaneLayout slid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.slid = (NoSlidingPaneLayout) findViewById(R.id.slid);

    }

    @Override
    public void onBackPressed() {
        gotoChooseActivity(null);
    }
    @Subscribe(threadMode=ThreadMode.MAIN)
    public void gotoChooseActivity(GoToChooseEvent event) {
        startActivity(new Intent(this,ChooseActivity.class));
        overridePendingTransition(R.anim.choose_in,R.anim.main_out);
        finish();
    }
}
