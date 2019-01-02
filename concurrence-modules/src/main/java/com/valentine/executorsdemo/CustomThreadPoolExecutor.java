package com.valentine.executorsdemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor {

    /**
     * 设置为私有构造函数
     */
    private CustomThreadPoolExecutor() {
    }

    /**
     * 使用volatile关键字保证可见性和防止指令重排
     * 指令重排的意思：比如java中的简单一句instance = new Singleton，会被编译器编译成如下JVM指令：
     * memory = allocate(); // 1: 分配对象的内存空间
     * ctorInstance(memory); // 2: 初始化对象
     * instance = memory; 3: 设置instance指向刚分配的内存地址
     * 但是这些指令顺序并非一成不变，有可能会经过JVM和CPU的优化，指令重排成下面的顺序：
     * memory = allocate(); // 1: 分配对象的内存空间
     * instance = memory; 3: 设置instance指向刚分配的内存地址
     * ctorInstance(memory); // 2: 初始化对象
     * 当线程A执行完1,3的时候，instance对象还没完成初始化，但已经不再指向null。此时如果线程B抢占到CPU资源，执行if(instance == null)的结果会返回false，
     * 从而会返回一个没有初始化完成的instance对象。所以这里要用到volatile关键字
     */
    private static volatile ExecutorService executorService = null;

    /**
     * 静态工厂方法
     */
    private static ExecutorService getPool() {
        // 双重检查机制
        if (null == executorService) {
            // 同步锁，注意这里不能使用对象锁
            synchronized (CustomThreadPoolExecutor.class) {
                // 双重检测机制
                // 进入synchronized临界区以后，还要再做一次判空。因为当两个线程同时访问的时候，线程A构建完对象，
                // 线程B也已经通过了最初的判空验证，不做二次判空的话，线程B还是会再次构建executorService对象。
                if (executorService == null) {
                    executorService = new ThreadPoolExecutor(5, 50, 10L,
                            TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new CustomThreadFactory());
                }
            }

        }
        return executorService;
    }

    public static void submit(Runnable runnable) {
        getPool().submit(runnable);
    }


    public static Future<Object> submit(Callable<Object> callable) {
        return getPool().submit(callable);
    }
}