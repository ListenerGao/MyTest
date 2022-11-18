package com.listenergao.mytest.leak;

import android.os.SystemClock;

import java.util.ArrayList;

/**
 * @description: 描述
 * @date: 2022/11/17 11:22
 * @author: ListenerGao
 */
public class LeakThread extends Thread {

    public final ArrayList<Object> leakViews = new ArrayList<>();


    @Override
    public void run() {
        while (true) {
            SystemClock.sleep(1000);
            System.out.println(SystemClock.uptimeMillis() + ": " + leakViews.get(0));
        }
    }
}
