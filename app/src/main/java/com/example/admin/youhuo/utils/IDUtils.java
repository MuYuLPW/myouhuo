package com.example.admin.youhuo.utils;

/**
 * Created by admin on 2016/12/27.
 */

import com.example.admin.youhuo.MyApplication;

/**
 * 获取id
 */
public class IDUtils {
    public static int getId(String name, String type, String pm){
        return MyApplication.app.getResources().getIdentifier(name,type,pm);
    }
    public static int getDrawableId(String name){
        return getId(name,"mipmap",MyApplication.app.getPackageName());
    }
    public static int getPackageName(String name,String type){
        return getId(name,type,MyApplication.app.getPackageName());
    }
}
