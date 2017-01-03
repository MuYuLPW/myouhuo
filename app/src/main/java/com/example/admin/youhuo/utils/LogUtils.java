package com.example.admin.youhuo.utils;

import android.util.Log;

import com.example.admin.youhuo.MyApplication;

/**
 * Created by admin on 2016/12/27.
 */

/**
 * 打印log工具类
 */
public class LogUtils {
    public static void log(String tag,String msg){
        if (MyApplication.isDebug){
            Log.e(tag,msg);
        }
    }
}
