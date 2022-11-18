package com.listenergao.mytest.leak;

import android.content.Context;
import android.os.Debug;
import android.os.SystemClock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @description: 描述
 * @date: 2022/11/17 11:13
 * @author: ListenerGao
 */
public class LeakUtils {

    public static ArrayList<Object> leakViews = new ArrayList<>();


    public static void gc() {
        new Thread() {
            @Override
            public void run() {
                System.out.println("GC start...");
                Runtime.getRuntime().gc();

                SystemClock.sleep(100);
                System.out.println("GC end...");
            }
        }.start();
    }

    public static void dump(Context ctx) {
        new Thread() {
            @Override
            public void run() {
                File file = new File(externalStorageDirectory(ctx));
                try {
                    Debug.dumpHprofData(file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static String externalStorageDirectory(Context ctx) {
        return "";
    }


}
