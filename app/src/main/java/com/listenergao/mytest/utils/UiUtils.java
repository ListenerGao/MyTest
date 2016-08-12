package com.listenergao.mytest.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.listenergao.mytest.BaseApplication;

/**
 * Created by hnthgys on 2016/7/1.
 */
public class UiUtils {

    /**
     * 对外提供上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * 根据手机分辨率,将dp转换为px(像素)
     * density值表示每英寸有多少个显示点
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        Log.d("TAG", "dip2px scale=" + scale);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机分辨率,将px转换为dp
     * density值表示每英寸有多少个显示点
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        Log.d("TAG", "px2dip scale=" + scale);
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取版本名称
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String versionName = packageInfo.versionName;
        return versionName;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getVersionCode(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        int versionCode = packageInfo.versionCode;
        return versionCode;
    }


}
