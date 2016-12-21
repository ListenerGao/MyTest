package com.listenergao.mytest;

import android.app.Application;
import android.content.Context;

/**
 * Created by ListenerGao on 2016/7/1.
 */
public class BaseApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    /**
     * 对外提供Application的Context
     * @return
     */
    public static Context getApplication(){
        return sContext;
    }
}
