package com.listenergao.threadsample;

/**
 * @description: 描述
 * @date: 2022/11/15 10:52
 * @author: ListenerGao
 */
public class Synchronized3Demo implements RunTest {

    private int x = 0;
    private int y = 0;
    private String name;
    private final Object nameMonitor = new Object();

    /**
     * 如果仅有 count() 和 minus() 方法时，只需在函数上加上 synchronized 关键字即可，
     * 但是还有 setName() 方法，如果该方法也加上 synchronized，那就不合适了，因为修改 x、y 的
     * 值时，是可以修改 name 值的。
     * 解决方法：使用不同 monitor
     *
     * 注：方法上加 synchronized 和方法内加 synchronized (this) 的monitor对象都是同一个，当前类对象
     */

    private synchronized void count(int newValue) {
        synchronized (this){
            x = newValue;
            y = newValue;
        }
    }

    private synchronized void minus(int delta) {
        x -= delta;
        y -= delta;
    }

    private /*synchronized*/ void setName(String newName) {
        synchronized (nameMonitor) {
            name = newName;
        }
    }

    @Override
    public void runTest() {

    }
}
