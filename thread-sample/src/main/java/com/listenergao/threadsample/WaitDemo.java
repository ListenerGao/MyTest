package com.listenergao.threadsample;

/**
 * @description: 模拟多线程初始化问题，保证输出正确的值
 * @date: 2022/11/15 16:32
 * @author: ListenerGao
 */
public class WaitDemo implements RunTest {

    private String sharedString;

    private synchronized void initString() {
        sharedString = "ListenerGao";
        // 该方法需要在 synchronized 中被调用，否则会抛异常，找不到 monitor 对象
        notifyAll();
    }

    private synchronized void printString() {
        while (sharedString == null) {
            try {
                // 该方法需要在 synchronized 中被调用，否则会抛异常，找不到 monitor 对象
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("String:" + sharedString);
    }

    @Override
    public void runTest() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                printString();
            }
        };
        thread1.start();

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initString();
            }
        };
        thread2.start();
    }
}
