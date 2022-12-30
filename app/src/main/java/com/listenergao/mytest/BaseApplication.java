package com.listenergao.mytest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;

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

        // 线上环境慎用
        // 拦截 java 异常，native异常是无法捕获的
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        MessageQueue.IdleHandler handler = new MessageQueue.IdleHandler() {

            @Override
            public boolean queueIdle() {
                ToastUtils.showShort("IdleHandler...");
                LogUtils.d("IdleHandler...");
                return false;
            }
        };
        Looper.myQueue().addIdleHandler(handler);
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
