package com.listenergao.threadsample;

/**
 * @description: 结束线程
 * @date: 2022/11/15 15:40
 * @author: ListenerGao
 */
public class ThreadInteractionDemo implements RunTest {


    @Override
    public void runTest() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    // 使用 isInterrupted() 或者 Thread.interrupted() 判断是否为终止状态。
                    // 注意：Thread.interrupted() 会重置中断状态，将中断状态置为 false
                    if (Thread.interrupted()) {
                        System.out.println("中止了线程");
                        // 收尾工作处理
                        return;
                    } else {
                        System.out.println("number:" + i);
                    }
                }
            }
        };
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 该方法太粗暴了，直接会结束线程。
//        thread.stop();
        // 该方法是将线程标记为终止状态，通过 thread.isInterrupted() 来判断是否需要终止线程。
        thread.interrupt();
    }
}
