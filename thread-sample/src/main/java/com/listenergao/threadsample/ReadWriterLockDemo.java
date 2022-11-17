package com.listenergao.threadsample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 描述
 * @date: 2022/11/15 13:45
 * @author: ListenerGao
 */
public class ReadWriterLockDemo implements RunTest {

    Lock lock = new ReentrantLock();
    private int x = 0;

    private void count() {
        lock.lock();
        try {
            x++;
        } finally {
            // 需要放在 finally 代码块中，避免发生异常，导致锁没有释放。
            lock.unlock();
        }
    }

    /****************************************************************/

    /**
     * 读写锁
     * 读写操作都需要是互斥的。
     * 读读操作是不需要互斥的，可以提高性能。
     * 写写操作是需要互斥的。
     */

    final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    final Lock readLock = readWriteLock.readLock();
    final Lock writeLock = readWriteLock.writeLock();

    private int value = 0;

    private void read() {
        readLock.lock();
        try {
            System.out.println("value:" + value);
        } finally {
            readLock.unlock();
        }
    }

    private void write() {
        writeLock.lock();
        try {
            value++;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void runTest() {

    }
}
