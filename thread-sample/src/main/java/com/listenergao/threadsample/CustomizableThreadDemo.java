package com.listenergao.threadsample;

/**
 * @description: HandlerThread 简单原型
 * @date: 2022/11/15 16:33
 * @author: ListenerGao
 */
public class CustomizableThreadDemo implements RunTest {

    CustomizableThread thread = new CustomizableThread();

    @Override
    public void runTest() {
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.quit();
        thread.setTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("hahahhaha");
            }
        });

    }

    static class CustomizableThread extends Thread {
        Looper looper = new Looper();

        private void quit() {
            looper.quit();
        }

        private void setTask(Runnable runnable) {
            looper.setTask(runnable);
        }

        @Override
        public void run() {
            looper.loop();
        }
    }

    static class Looper {

        private Runnable task;
        private volatile boolean quit = false;

        void quit() {
            quit = true;
        }

        synchronized void setTask(Runnable runnable) {
            this.task = runnable;
        }

        void loop() {
            while (!quit) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }
}
