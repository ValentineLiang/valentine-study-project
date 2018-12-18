package com.valentine.lockdemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    // 公平重入锁和非公平重入锁
    static Lock lock = new ReentrantLock();

    private static int count = 0;
    public void incr() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获得锁
        lock.lock();
        count++;
        // 释放锁
        lock.unlock();
    }

}
