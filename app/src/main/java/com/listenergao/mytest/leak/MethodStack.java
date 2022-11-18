package com.listenergao.mytest.leak;

import android.os.SystemClock;

/**
 * @description: 描述
 * @date: 2022/11/17 11:09
 * @author: ListenerGao
 */
public class MethodStack implements Runnable {
    private Object ref;

    MethodStack(Object ref) {
        this.ref = ref;
    }

    public static void startWithTarget(Object ref) {
        // 暂时让成员变量引用到泄漏目标
        new Thread(new MethodStack(ref)).start();
    }

    @Override
    public void run() {
        // 把成员变量赋值为局部变量，然后置空成员变量
        Object ref = this.ref;
        this.ref = null;
        while (true) {
            SystemClock.sleep(1000);
            System.out.println(ref);
        }
    }
}
