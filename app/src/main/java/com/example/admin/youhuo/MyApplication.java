package com.example.admin.youhuo;

import android.app.Application;

/**
 * Created by admin on 2016/12/27.
 */

public class MyApplication extends Application {
    public static MyApplication app;
    public static boolean isDebug;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        isDebug=true;
    }
}
