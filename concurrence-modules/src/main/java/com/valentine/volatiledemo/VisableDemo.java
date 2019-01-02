package com.valentine.volatiledemo;

import java.util.concurrent.TimeUnit;

/**
 * 可见性问题
 */
public class VisableDemo {
    // 所以解决方法加一个volatile关键字，volatile涉及一个内存屏障的概念
    private volatile static boolean stop = false;

    /**
     * 这是典型的可见性问题，通过主线程设置stop = true，但是子线程一般都拿不到，所以子线程一般都会挂死
     */
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
                System.out.println(i);
            }
            System.out.println(Thread.currentThread().getName());
        },"厉害");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        stop = true;
    }
}
