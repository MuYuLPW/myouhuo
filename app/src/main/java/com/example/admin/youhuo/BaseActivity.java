package com.example.admin.youhuo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.admin.youhuo.utils.FullStatusBar;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by admin on 2016/12/26.
 */

public class BaseActivity extends AppCompatActivity {
    Handler handler=new Handler();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            FullStatusBar.fullStatusBar(this);
        }
       try{
           EventBus.getDefault().register(this);
       }catch (Exception e){

       }
    }
    public void toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {

        handler.removeCallbacksAndMessages(null);
        try{
            EventBus.getDefault().register(this);
        }catch (Exception e){

        }
        super.onDestroy();
    }
}
