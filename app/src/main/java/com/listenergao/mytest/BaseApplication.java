package com.listenergao.mytest;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

//import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

import leakcanary.LeakCanary;

/**
 * Created by ListenerGao on 2016/7/1.
 */
public class BaseApplication extends Application {
    private static Context sContext;

    final ArrayList<Object> leakViews = new ArrayList<>();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
        sContext = this;
//        LeakCanary.install(this);
    }

    /**
     * 对外提供Application的Context
     *
     * @return
     */
    public static Context getApplication() {
        return sContext;
    }
}
