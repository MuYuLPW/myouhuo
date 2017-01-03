package com.example.admin.youhuo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 第一个闪屏页
 */
public class FirstSplishActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_splish);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FirstSplishActivity.this,WelComeActivity.class));
                finish();
            }
        }, 3000);
    }
}
