package com.listenergao.threadsample;

/**
 * @description: 多线程同步
 * @date: 2022/11/15 10:52
 * @author: ListenerGao
 */
public class Synchronized1Demo implements RunTest {

    private int x = 0;
    private int y = 0;

    /**
     * 加上 synchronized 关键字，会使 count 方法互斥，这样 x 与 y 的值，就永远可能相等了。
     */
    private synchronized void count(int newValue) {
        x = newValue;
        y = newValue;
        if (x != y) {
            System.out.println("x:" + x + ", y:" + y);
        }
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
                System.out.println("one thread:" + Thread.currentThread().getName() + "执行结束...");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
                System.out.println("two thread:" + Thread.currentThread().getName() + "执行结束...");
            }
        }.start();
    }
}
