package com.example.admin.youhuo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * Created by admin on 2016/12/26.
 */

public class FullStatusBar {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void fullStatusBar(Activity a){
        View decorView = a.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        a.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
