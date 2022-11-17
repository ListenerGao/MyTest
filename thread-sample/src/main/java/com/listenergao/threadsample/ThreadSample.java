package com.listenergao.threadsample;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 线程示例
 * @date: 2022/11/15 09:25
 * @author: ListenerGao
 */
public class ThreadSample {

    public static void main(String[] args) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
//        runSynchronized1Demo();
//        runSynchronized2Demo();
//        runSynchronized3Demo();

//        runThreadInteractionDemo();
//        runWaitDemo();
//        runCustomizableThreadDemo();
        runThreadDemo();

//        System.out.println("main start");
//        new Thread() {
//            @Override
//            public void run() {
//                System.out.println("thread run");
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("thread end");
//            }
//        }.start();
//        try {
//            Thread.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("main end");

        /**
         * ThreadLocal 之前不共享数据，即使在多线程中操作，依然可以获取到正确的值
         * 如下：两个线程分别修改 int 的值
         */
        final ThreadLocal<Integer> threadNum = new ThreadLocal<>();
        new Thread() {
            @Override
            public void run() {
                threadNum.set(10);
                threadNum.get(); // 获取的值为 10
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                threadNum.set(20);
                threadNum.get(); // 获取的值为 20
            }
        }.start();
    }


    /**
     * 使用 Thread 类来定义工作
     */
    static void thread() {
        new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started:" + Thread.currentThread().getName());
            }
        }.start();
    }

    /**
     * 使用 Runnable 类来定义工作
     */
    static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        new Thread(runnable).start();
    }

    /**
     * 使用 ThreadFactory 线程工厂类来定义工作
     */
    static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            // 此处使用的 count 存在多线程问题；
            // int count = 0;
            // 应使用 AtomicInteger 来保证多线程安全。
            final AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
//                return new Thread(r, "Thread-" + count++);
                // getAndIncrement count++
                // incrementAndGet ++count
                return new Thread(r, "Thread-" + count.getAndIncrement());
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started!");
            }
        };

        factory.newThread(runnable).start();
        factory.newThread(runnable).start();

    }

    /**
     * 结束 Executor 的两种方法：
     * shutDown：比较温和，保守。执行此方法后，Executor不在添加新的任务，
     * 将正在执行或者排队的任务执行完毕后，结束 Executor。
     * <p>
     * shutDownNow：比较激进。立即结束所有任务。（使用 thread.interrupt()方法结束线程）
     */
    static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!  " + Thread.currentThread().getName());
            }
        };
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
    }

    /**
     * 类似于有返回值的 Runnable
     */
    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done";
            }
        };
        Future<String> future = Executors.newCachedThreadPool().submit(callable);
        try {
            // future.isDone() 检查是否完成
            // 阻塞式的
            String result = future.get();
            System.out.println("result:" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }

    static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

    static void runThreadInteractionDemo() {
        new ThreadInteractionDemo().runTest();
    }

    static void runWaitDemo() {
        new WaitDemo().runTest();
    }

    static void runCustomizableThreadDemo() {
        new CustomizableThreadDemo().runTest();
    }

    static void runThreadDemo() {
        new ThreadDemo().runTest();
    }
}
