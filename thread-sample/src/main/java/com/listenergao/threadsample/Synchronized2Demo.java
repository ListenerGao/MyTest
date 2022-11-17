package com.listenergao.threadsample;

/**
 * @description: 描述
 * @date: 2022/11/15 10:52
 * @author: ListenerGao
 */
public class Synchronized2Demo implements RunTest {

    private int x = 0;

    /**
     * 由于 x++ 不是原子操作，多线程执行时，结果永远不会等于 2000000 ，加上
     * synchronized 即可。
     */
    private synchronized void count() {
        // 不是原子操作，相当于：
        // int temp = x;
        // x = temp + 1;
        x++;
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from 1:" + x);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from 2:" + x);
            }
        }.start();

    }
}
