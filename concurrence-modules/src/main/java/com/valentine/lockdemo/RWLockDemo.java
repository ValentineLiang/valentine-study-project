package com.valentine.lockdemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockDemo {

    /**
     * 共享锁-在同一时刻可以有多个线程获得锁，读锁和写锁（读多写少）
     */
    private static Map<String,Object> cacheMap = new HashMap<>();

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static Lock read = reentrantReadWriteLock.readLock();
    private static Lock write = reentrantReadWriteLock.writeLock();

    /**
     * 使用读写锁可以更大化地提升性能（在读多写少的情况下）
     *
     * 读锁，可以允许多个线程进入
     */
    public static Object get(String key) {
        read.lock();
        try {
            return cacheMap.get(key);
        }finally {
            read.unlock();
        }
    }

    /**
     * 其他线程阻塞
     */
    public static Object set(String key, Object value) {
        // 写锁
        write.lock();
        try {
            return cacheMap.put(key,value);
        }finally {
            write.unlock();
        }
    }

}
