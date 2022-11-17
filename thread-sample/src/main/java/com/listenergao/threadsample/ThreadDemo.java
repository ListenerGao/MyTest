package com.listenergao.threadsample;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @description: 多线程输出 1A 2B 3C 4D 5E 6F
 * @date: 2022/11/16 15:05
 * @author: ListenerGao
 */
public class ThreadDemo implements RunTest {

    private Thread numThread;
    private Thread letterThread;
    final char[] num = "123456".toCharArray();
    final char[] letter = "ABCDEF".toCharArray();


    private boolean isPrintNum = false;
    private boolean isPrintLetter = false;

    @Override
    public void runTest() {

//        methodOne();
//        methodTwo();
        methodThree();

    }

    private void methodOne() {
        numThread = new Thread() {
            @Override
            public void run() {
                for (char c : num) {
                    System.out.println("num:" + c);
                    // 唤醒 letterThread
                    LockSupport.unpark(letterThread);
                    // 阻塞 numThread
                    LockSupport.park();
                }
            }
        };

        letterThread = new Thread() {
            @Override
            public void run() {
                for (char c : letter) {
                    // 阻塞 letterThread
                    LockSupport.park();
                    System.out.println("letter:" + c);
                    // 唤醒 numThread
                    LockSupport.unpark(numThread);
                }
            }
        };

        numThread.start();
        letterThread.start();
    }

    /**
     * 使用 CountDownLatch 保证执行顺序，synchronized + wait()/notify()
     */
    private void methodTwo() {
        // 使用 CountDownLatch 保证执行顺序
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object lock = new Object();
        numThread = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (char c : num) {
                        System.out.println("num:" + c);
                        // 叫醒线程
                        countDownLatch.countDown();
                        try {
                            lock.notify(); // 随机唤醒一个在 lock 上等待的线程
                            lock.wait(); // 使当前线程等待，并释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify(); // 必须有，否则程序无法停止
                }
            }
        };

        letterThread = new Thread() {
            @Override
            public void run() {
                try {
                    countDownLatch.await(); // 阻塞线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock) {
                    for (char c : letter) {
                        System.out.println("letter:" + c);
                        try {
                            lock.notify(); // 随机唤醒一个在 lock 上等待的线程
                            lock.wait(); // 使当前线程等待，并释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify(); // 必须有，否则程序无法停止
                }
            }
        };

        numThread.start();
        letterThread.start();
    }

    /**
     * 使用 原子类型+自旋等待
     */
    private void methodThree() {
        // AtomicInteger 原子类
        final AtomicInteger currentThreadId = new AtomicInteger(1);
        // 也可使用 volatile 修饰的成员变量，原理一样。如：private volatile int currentThreadId = 1;


        numThread = new Thread() {
            @Override
            public void run() {
                for (char c : num) {
                    // 自旋等待
                    while (currentThreadId.get() != 1) {
                        // 等待
                    }
                    System.out.println("num:" + c);
                    currentThreadId.set(2);
                }
            }
        };

        letterThread = new Thread() {
            @Override
            public void run() {
                for (char c : letter) {
                    // 自旋等待
                    while (currentThreadId.get() != 2) {
                        // 等待
                    }
                    System.out.println("letter:" + c);
                    currentThreadId.set(1);
                }
            }
        };

        numThread.start();
        letterThread.start();
    }
}
