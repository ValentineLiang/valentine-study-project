package com.valentine.lockdemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    // 公平重入锁和非公平重入锁
    private static Lock lock = new ReentrantLock();

    private static int count = 0;

    public static void incr() {
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

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                LockDemo.incr();
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("result:" + count);
    }

}
