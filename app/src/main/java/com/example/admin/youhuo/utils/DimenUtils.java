package com.example.admin.youhuo.utils;

import com.example.admin.youhuo.MyApplication;

/**
 * Created by admin on 2016/12/27.
 */

public class DimenUtils {
    public static int dp2px(int dp){
        return (int) (MyApplication.app.getResources().getDisplayMetrics().density*dp+0.5f);
    }
}
