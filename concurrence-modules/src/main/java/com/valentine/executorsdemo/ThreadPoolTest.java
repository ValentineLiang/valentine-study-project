package com.valentine.executorsdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author lrj
 */
public class ThreadPoolTest implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    static ExecutorService service = Executors.newFixedThreadPool(3);
    static ExecutorService serviceCached = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return null;
        }
    });

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            service.execute(new ThreadPoolTest());
        }
        service.shutdown();
    }
}